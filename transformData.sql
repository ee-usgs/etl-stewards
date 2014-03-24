show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'start time: ' || systimestamp from dual;

truncate table organization_temp;

prompt inserting into organization_temp
insert /*+ append nologging parallel(4) */ into organization_temp (code_value, description, sort_order)
select /*+ parallel(4) */
       code_value,
       description,
       rownum sort_order
  from (select /*+ parallel(4) */
               code_value, description,
               dense_rank() over (partition by code_value order by description, rownum) myrank
          from (select xmlcast(xmlquery('WQX/Organization/OrganizationDescription/OrganizationIdentifier' passing raw_xml returning content) as varchar2(2000 char)) code_value,
                       xmlcast(xmlquery('WQX/Organization/OrganizationDescription/OrganizationFormalName' passing raw_xml returning content) as varchar2(2000 char)) description
                  from raw_station_xml
               )
       )
 where myrank = 1
   order by 1;
   
commit;

truncate table station_temp;

prompt inserting into station_temp
insert /*+ append nologging parallel(4) */
  into station_temp (station_pk, station_id, station_details, country_cd, county_cd, geom, huc_8, organization_id, state_cd, site_type, station_clob, organization_clob)
select station_pk,
       station_id,
       station_details,
       country_cd,
       county_cd,
       geom,
       huc_8,
       organization_id,
       state_cd,
       nvl(primary_site_type, 'Not Assigned') primary_site_type,
       xmlserialize(content station_details as clob no indent) station_clob,
       xmlserialize(content organization_details as clob no indent) organization_clob
  from (select /*+ parallel(4) */
               rownum station_pk,
               organization_id || '-' || station_id station_id,
               updatexml(station_details, 'MonitoringLocation/MonitoringLocationIdentity/MonitoringLocationIdentifier/text()', organization_id || '-' || station_id) station_details,
               country_cd,
               county_cd,
               mdsys.sdo_geometry(2001,8265,mdsys.sdo_point_type(round(longitude, 7),round(latitude, 7), null), null, null) geom,
               huc_8,
               organization_id,
               state_cd,
               site_type,
               xmlelement("OrganizationDescription",
                          xmlelement("OrganizationIdentifier", code_value),
                          xmlelement("OrganizationFormalName", description)
                         ) organization_details
          from raw_station_xml,
               xmltable('/WQX/Organization'
                        passing raw_xml
                        columns organization_id varchar2(500 char) path '/Organization/OrganizationDescription/OrganizationIdentifier',
                                code_value varchar2(2000 char) path '/Organization/OrganizationDescription/OrganizationIdentifier',
                                description varchar2(2000 char) path 'Organization/OrganizationDescription/OrganizationFormalName',
                                details xmltype path '/Organization'), 
               xmltable('for $i in /Organization return $i/MonitoringLocation'
                        passing details
                        columns station_id varchar2(100 char) path '/MonitoringLocation/MonitoringLocationIdentity/MonitoringLocationIdentifier',
                                country_cd varchar2(2 char) path '/MonitoringLocation/MonitoringLocationGeospatial/CountryCode',
                                county_cd varchar2(3 char) path '/MonitoringLocation/MonitoringLocationGeospatial/CountyCode',
                                huc_8 varchar2(8 char) path '/MonitoringLocation/MonitoringLocationIdentity/HUCEightDigitCode',
                                state_cd varchar2(2 char) path '/MonitoringLocation/MonitoringLocationGeospatial/StateCode',
                                site_type varchar2(500 char) path '/MonitoringLocation/MonitoringLocationIdentity/MonitoringLocationTypeName',
                                latitude number path '/MonitoringLocation/MonitoringLocationGeospatial/LatitudeMeasure',
                                longitude number path '/MonitoringLocation/MonitoringLocationGeospatial/LongitudeMeasure',
                                station_details xmltype path '/MonitoringLocation')
       ) station
       left join site_type_to_primary
         on site_type_to_primary.site_type = station.site_type;

commit;

truncate table split_activity;

prompt inserting into split_activity
insert all /*+ append nologging parallel(4) */    
  into split_activity
select /*+ parallel(4) */
       rownum activity_pk,
       organization_id,
       activity_details
          from raw_result_xml,
               xmltable('/WQX/Organization'
                        passing raw_xml
                        columns organization_id varchar2(500 char) path '/Organization/OrganizationDescription/OrganizationIdentifier',
                                organization_details xmltype path '/Organization'), 
               xmltable('for $i in /Organization return $i/Activity'
                        passing organization_details
                        columns activity_details xmltype path '/Activity'
                       );

truncate table activity_temp;

truncate table result_temp;

prompt inserting into activity_temp and result_temp
insert /*+ append nologging parallel(4) */    
  into result_temp (result_pk, activity_pk, station_pk, station_id, activity_start_date, characteristic_name, country_cd,
                    county_cd, huc_8, organization_id, sample_media, state_cd, site_type, characteristic_type, organization_clob,
                    activity_clob, result_clob, geom)
select /*+ parallel(4) */
       rownum result_pk,
       a.activity_pk,
       station.station_pk,
       station.station_id,
       a.activity_start,
       a.characteristic_name,
       station.country_cd,
       station.county_cd,
       station.huc_8,
       station.organization_id,
       a.sample_media,
       station.state_cd,
       station.site_type,
       characteristic_name_to_type.characteristic_type,
       station.organization_clob,
       xmlserialize(content activity_details as clob no indent) activity_clob,
       xmlserialize(content results as clob no indent) result_clob,
       station.geom
  from (select /*+ parallel(4) */
               activity_pk,
               xmlelement("Activity", xmlconcat(ActivityDescription, SampleDescription)) activity_details,
               to_date(activity_start_date||' '||activity_start_time, 'mm/dd/yyyy hh24:mi:ss') activity_start,
               results,
               characteristic_name,
               sample_media,
               organization_id || '-' || station_id station_id,
               activity_id
          from split_activity,
               xmltable('/Activity'
                        passing activity_details
                        columns station_id varchar2(100 char) path '/Activity/ActivityDescription/MonitoringLocationIdentifier',
                                activity_start_date varchar2(10 char) path '/Activity/ActivityDescription/ActivityStartDate',
                                activity_start_time varchar2(8 char) path '/Activity/ActivityDescription/ActivityStartTime/Time',
                                ActivityDescription xmltype path '/Activity/ActivityDescription',
                                SampleDescription xmltype path '/Activity/SampleDescription',
                                results xmltype path '/Activity/Result',
                                characteristic_name varchar2(32 char) path '/Activity/Result/ResultDescription/CharacteristicName',
                                sample_media varchar2(30 char) path '/Activity/ActivityDescription/ActivityMediaName',
                                activity_id varchar2(30 char) path '/Activity/ActivityDescription/ActivityIdentifier'
                       ) y
       ) a
       join station_temp station
         on station.station_id = a.station_id
       left join characteristic_name_to_type
         on a.characteristic_name = characteristic_name_to_type.characteristic_name;

commit; 

select 'end time: ' || systimestamp from dual;
