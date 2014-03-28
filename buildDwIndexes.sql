show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'build dw indexes start time: ' || systimestamp from dual;

begin
  declare the_suffix varchar2(6 char);

begin
  select current_suffix
    into the_suffix
    from suffix_magic;
  dbms_output.put_line('using suffix:' || the_suffix); 

  insert into user_sdo_geom_metadata
  values('STATION' || the_suffix, 'GEOM', mdsys.sdo_dim_array( mdsys.sdo_dim_element('X', -180, 180, 0.005), mdsys.sdo_dim_element('Y', -90, 90, 0.005)), 8265);
  insert into user_sdo_geom_metadata
  values('RESULT' || the_suffix, 'GEOM', mdsys.sdo_dim_array( mdsys.sdo_dim_element('X', -180, 180, 0.005), mdsys.sdo_dim_element('Y', -90, 90, 0.005)), 8265);
  dbms_output.put_line('Added user_sdo_geom_metadata');
  commit;

  execute immediate 'create bitmap index station' || the_suffix || '_country on station' || the_suffix || ' (country_cd)';
  dbms_output.put_line('created index station' || the_suffix || '_country');
  execute immediate 'create bitmap index station' || the_suffix || '_county on station' || the_suffix || ' (county_cd)';
  dbms_output.put_line('created index station' || the_suffix || '_county');
  execute immediate 'create index station' || the_suffix || '_geom on station' || the_suffix || q'! (geom) indextype is mdsys.spatial_index parameters('sdo_indx_dims=2 layer_gtype="POINT"')!';
  dbms_output.put_line('created index station' || the_suffix || '_geom');
  execute immediate 'create bitmap index station' || the_suffix || '_huc on station' || the_suffix || ' (huc_8)';
  dbms_output.put_line('created index station' || the_suffix || '_huc');
  execute immediate 'create bitmap index station' || the_suffix || '_org on station' || the_suffix || ' (organization_id)';
  dbms_output.put_line('created index station' || the_suffix || '_org');
  execute immediate 'create bitmap index station' || the_suffix || '_site_type on station' || the_suffix || ' (site_type)';
  dbms_output.put_line('created index station' || the_suffix || '_site_type');
  execute immediate 'create bitmap index station' || the_suffix || '_station on station' || the_suffix || ' (station_id)';
  dbms_output.put_line('created index station' || the_suffix || '_station');
  execute immediate 'create bitmap index station' || the_suffix || '_state on station' || the_suffix || ' (state_cd)';
  dbms_output.put_line('created index station' || the_suffix || '_state');

  execute immediate 'create bitmap index result' || the_suffix || '_activity on result' || the_suffix || ' (activity_pk)';
  dbms_output.put_line('created index result' || the_suffix || '_activity');
  execute immediate 'create bitmap index result' || the_suffix || '_date on result' || the_suffix || ' (activity_start_date)';
  dbms_output.put_line('created index result' || the_suffix || '_date');
  execute immediate 'create bitmap index result' || the_suffix || '_char on result' || the_suffix || ' (characteristic_name)';
  dbms_output.put_line('created index result' || the_suffix || '_char');
  execute immediate 'create bitmap index result' || the_suffix || '_country on result' || the_suffix || ' (country_cd)';
  dbms_output.put_line('created index result' || the_suffix || '_country');
  execute immediate 'create bitmap index result' || the_suffix || '_county on result' || the_suffix || ' (county_cd)';
  dbms_output.put_line('created index result' || the_suffix || '_county');
  execute immediate 'create bitmap index result' || the_suffix || '_huc on result' || the_suffix || ' (huc_8)';
  dbms_output.put_line('created index result' || the_suffix || '_huc');
  execute immediate 'create bitmap index result' || the_suffix || '_org on result' || the_suffix || ' (organization_id)';
  dbms_output.put_line('created index result' || the_suffix || '_org');
  execute immediate 'create bitmap index result' || the_suffix || '_media on result' || the_suffix || ' (sample_media)';
  dbms_output.put_line('created index result' || the_suffix || '_media');
  execute immediate 'create bitmap index result' || the_suffix || '_state on result' || the_suffix || ' (state_cd)';
  dbms_output.put_line('created index result' || the_suffix || '_state');
  execute immediate 'create bitmap index result' || the_suffix || '_station on result' || the_suffix || ' (station_id)';
  dbms_output.put_line('created index result' || the_suffix || '_station');
  execute immediate 'create bitmap index result' || the_suffix || '_station_pk on result' || the_suffix || '(station_pk)';
  dbms_output.put_line('created index result' || the_suffix || '_station_pk');
  execute immediate 'create bitmap index result' || the_suffix || '_site_type on result' || the_suffix || ' (site_type)';
  dbms_output.put_line('created index result' || the_suffix || '_site_type');
  execute immediate 'create bitmap index result' || the_suffix || '_char_type on result' || the_suffix || ' (characteristic_type)';
  dbms_output.put_line('created index result' || the_suffix || '_char_type');
  execute immediate 'create index result' || the_suffix || '_geom on result' || the_suffix || q'! (geom) indextype is mdsys.spatial_index parameters('sdo_indx_dims=2 layer_gtype="POINT"')!';
  dbms_output.put_line('created index result' || the_suffix || '_geom');

end;
end;
/

select 'build dw indexes end time: ' || systimestamp from dual;
