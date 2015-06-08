show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'transform station start time: ' || systimestamp from dual;

prompt dropping stewards station indexes
exec etl_helper.drop_indexes('station_swap_stewards');

prompt populating stewards station
truncate table station_swap_stewards;

insert /*+ append parallel(4) */
  into station_swap_stewards (data_source_id, data_source, station_id, site_id, organization, site_type, huc, governmental_unit_code,
                              geom, station_name, organization_name, description_text, station_type_name, latitude, longitude, map_scale,
                              geopositioning_method, hdatum_id_code, elevation_value, elevation_unit, elevation_method, vdatum_id_code,
                              drain_area_value, drain_area_unit, contrib_drain_area_value, contrib_drain_area_unit,
                              geoposition_accy_value, geoposition_accy_unit, vertical_accuracy_value, vertical_accuracy_unit,
                              nat_aqfr_name, aqfr_name, aqfr_type_name, construction_date, well_depth_value, well_depth_unit,
                              hole_depth_value, hole_depth_unit)
select 1 data_source_id,
       'STEWARDS' data_source,
       site.station_id,
       site.organization || '-' || site.site_id site_id,
       site.organization,
       nvl(site_type_to_primary.primary_site_type, 'Not Assigned') site_type, 
       nvl(site.huc_12, site.huc_8) huc,
       site.country_cd || ':' || site.state_cd || ':' || site.county_cd governmental_unit_code,
       mdsys.sdo_geometry(2001,4269,mdsys.sdo_point_type(round(site.longitude, 7),round(site.latitude, 7), null), null, null) geom,
       site.station_name, 
       site.organization_name,
       site.description_text,
       site.site_type,
       site.latitude,
       site.longitude,
       site.map_scale,
       site.geopositioning_method,
       site.hdatum_id_code,
       site.elevation_value,
       site.elevation_unit,
       site.elevation_method,
       site.vdatum_id_code,
       site.drain_area_value,
       site.drain_area_unit,
       site.contrib_drain_area_value,
       site.contrib_drain_area_unit,
       site.geoposition_accy_value,
       site.geoposition_accy_unit,
       site.vertical_accuracy_value,
       site.vertical_accuracy_unit,
       site.nat_aqfr_name,
       site.aqfr_name,
       site.aqfr_type_name,
       site.construction_date,
       site.well_depth_value,
       site.well_depth_unit,
       site.hole_depth_value,
       site.hole_depth_unit
  from (select /*+ parallel(4) */ *
          from ars_stewards.raw_station_xml,
               xmltable('/WQX/Organization'
                        passing raw_xml
                        columns organization varchar2(500 char) path '/Organization/OrganizationDescription/OrganizationIdentifier',
                                organization_name varchar2(2000 char) path '/Organization/OrganizationDescription/OrganizationFormalName',
                                details xmltype path '/Organization'), 
               xmltable('for $i in /Organization return $i/MonitoringLocation'
                        passing details
                        columns station_id for ordinality,
                                site_id varchar2(100 char) path '/MonitoringLocation/MonitoringLocationIdentity/MonitoringLocationIdentifier',
                                station_name varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationIdentity/MonitoringLocationName',
                                description_text varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationIdentity/MonitoringLocationDescriptionText',
                                huc_12 varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationIdentity/HUCTwelveDigitCode',
                                drain_area_value number path '/MonitoringLocation/MonitoringLocationIdentity/DrainageAreaMeasure/MeasureValue',
                                drain_area_unit varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationIdentity/DrainageAreaMeasure/MeasureUnitCode',
                                contrib_drain_area_value number path '/MonitoringLocation/MonitoringLocationIdentity/ContributingDrainageAreaMeasure/MeasureValue',
                                contrib_drain_area_unit  varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationIdentity/ContributingDrainageAreaMeasure/MeasureUnitCode',
                                site_type varchar2(500 char) path '/MonitoringLocation/MonitoringLocationIdentity/MonitoringLocationTypeName',
                                country_cd varchar2(2 char) path '/MonitoringLocation/MonitoringLocationGeospatial/CountryCode',
                                county_cd varchar2(3 char) path '/MonitoringLocation/MonitoringLocationGeospatial/CountyCode',
                                huc_8 varchar2(8 char) path '/MonitoringLocation/MonitoringLocationIdentity/HUCEightDigitCode',
                                state_cd varchar2(2 char) path '/MonitoringLocation/MonitoringLocationGeospatial/StateCode',
                                latitude number path '/MonitoringLocation/MonitoringLocationGeospatial/LatitudeMeasure',
                                longitude number path '/MonitoringLocation/MonitoringLocationGeospatial/LongitudeMeasure',
                                map_scale varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/SourceMapScaleNumeric',
                                geoposition_accy_value varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/HorizontalAccuracyMeasure/MeasureValue',
                                geoposition_accy_unit varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/HorizontalAccuracyMeasure/MeasureUnitCode',
                                geopositioning_method varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/HorizontalCollectionMethodName',
                                hdatum_id_code varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/HorizontalCoordinateReferenceSystemDatumName',
                                elevation_value varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/VerticalMeasure/MeasureValue',
                                elevation_unit varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/VerticalMeasure/MeasureUnitCode',
                                vertical_accuracy_value varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/VerticalAccuracyMeasure/MeasureValue',
                                vertical_accuracy_unit varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/VerticalAccuracyMeasure/MeasureUnitCode',
                                elevation_method varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/VerticalCollectionMethodName',
                                vdatum_id_code varchar2(4000 char) path '/MonitoringLocation/MonitoringLocationGeospatial/VerticalCoordinateReferenceSystemDatumName',
                                nat_aqfr_name  varchar2(4000 char) path '/MonitoringLocation/WellInformation/AquiferName',
                                aqfr_name varchar2(4000 char) path '/MonitoringLocation/WellInformation/FormationTypeText',
                                aqfr_type_name varchar2(4000 char) path '/MonitoringLocation/WellInformation/AquiferTypeName',
                                construction_date  varchar2(4000 char) path '/MonitoringLocation/WellInformation/ConstructionDateText',
                                well_depth_value number path '/MonitoringLocation/WellInformation/WellDepthMeasure/MeasureValue',
                                well_depth_unit  varchar2(4000 char) path '/MonitoringLocation/WellInformation/WellDepthMeasure/MeasureUnitCode',
                                hole_depth_unit number path '/MonitoringLocation/WellInformation/WellHoleDepthMeasure/MeasureValue',
                                hole_depth_value varchar2(4000 char) path '/MonitoringLocation/WellInformation/WellHoleDepthMeasure/MeasureUnitCode')
               ) site
       left join ars_stewards.site_type_to_primary
         on site.site_type = site_type_to_primary.site_type;

commit;

select 'transform station end time: ' || systimestamp from dual;
