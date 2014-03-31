show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'build dw indexes start time: ' || systimestamp from dual;

begin
  declare new_suffix varchar2(6 char);

begin
  select new_suffix
    into new_suffix
    from suffix_magic;
  dbms_output.put_line('using suffix:' || new_suffix); 

  insert into user_sdo_geom_metadata
  values('STATION' || new_suffix, 'GEOM', mdsys.sdo_dim_array( mdsys.sdo_dim_element('X', -180, 180, 0.005), mdsys.sdo_dim_element('Y', -90, 90, 0.005)), 8265);
  insert into user_sdo_geom_metadata
  values('RESULT' || new_suffix, 'GEOM', mdsys.sdo_dim_array( mdsys.sdo_dim_element('X', -180, 180, 0.005), mdsys.sdo_dim_element('Y', -90, 90, 0.005)), 8265);
  dbms_output.put_line('Added user_sdo_geom_metadata');
  commit;

  execute immediate 'create bitmap index station' || new_suffix || '_country on station' || new_suffix || ' (country_cd)';
  dbms_output.put_line('created index station' || new_suffix || '_country');
  execute immediate 'create bitmap index station' || new_suffix || '_county on station' || new_suffix || ' (county_cd)';
  dbms_output.put_line('created index station' || new_suffix || '_county');
  execute immediate 'create index station' || new_suffix || '_geom on station' || new_suffix || q'! (geom) indextype is mdsys.spatial_index parameters('sdo_indx_dims=2 layer_gtype="POINT"')!';
  dbms_output.put_line('created index station' || new_suffix || '_geom');
  execute immediate 'create bitmap index station' || new_suffix || '_huc on station' || new_suffix || ' (huc_8)';
  dbms_output.put_line('created index station' || new_suffix || '_huc');
  execute immediate 'create bitmap index station' || new_suffix || '_org on station' || new_suffix || ' (organization_id)';
  dbms_output.put_line('created index station' || new_suffix || '_org');
  execute immediate 'create bitmap index station' || new_suffix || '_site_type on station' || new_suffix || ' (site_type)';
  dbms_output.put_line('created index station' || new_suffix || '_site_type');
  execute immediate 'create bitmap index station' || new_suffix || '_station on station' || new_suffix || ' (station_id)';
  dbms_output.put_line('created index station' || new_suffix || '_station');
  execute immediate 'create bitmap index station' || new_suffix || '_state on station' || new_suffix || ' (state_cd)';
  dbms_output.put_line('created index station' || new_suffix || '_state');

  execute immediate 'create bitmap index result' || new_suffix || '_activity on result' || new_suffix || ' (activity_pk)';
  dbms_output.put_line('created index result' || new_suffix || '_activity');
  execute immediate 'create bitmap index result' || new_suffix || '_date on result' || new_suffix || ' (activity_start_date)';
  dbms_output.put_line('created index result' || new_suffix || '_date');
  execute immediate 'create bitmap index result' || new_suffix || '_char on result' || new_suffix || ' (characteristic_name)';
  dbms_output.put_line('created index result' || new_suffix || '_char');
  execute immediate 'create bitmap index result' || new_suffix || '_country on result' || new_suffix || ' (country_cd)';
  dbms_output.put_line('created index result' || new_suffix || '_country');
  execute immediate 'create bitmap index result' || new_suffix || '_county on result' || new_suffix || ' (county_cd)';
  dbms_output.put_line('created index result' || new_suffix || '_county');
  execute immediate 'create bitmap index result' || new_suffix || '_huc on result' || new_suffix || ' (huc_8)';
  dbms_output.put_line('created index result' || new_suffix || '_huc');
  execute immediate 'create bitmap index result' || new_suffix || '_org on result' || new_suffix || ' (organization_id)';
  dbms_output.put_line('created index result' || new_suffix || '_org');
  execute immediate 'create bitmap index result' || new_suffix || '_media on result' || new_suffix || ' (sample_media)';
  dbms_output.put_line('created index result' || new_suffix || '_media');
  execute immediate 'create bitmap index result' || new_suffix || '_state on result' || new_suffix || ' (state_cd)';
  dbms_output.put_line('created index result' || new_suffix || '_state');
  execute immediate 'create bitmap index result' || new_suffix || '_station on result' || new_suffix || ' (station_id)';
  dbms_output.put_line('created index result' || new_suffix || '_station');
  execute immediate 'create bitmap index result' || new_suffix || '_station_pk on result' || new_suffix || '(station_pk)';
  dbms_output.put_line('created index result' || new_suffix || '_station_pk');
  execute immediate 'create bitmap index result' || new_suffix || '_site_type on result' || new_suffix || ' (site_type)';
  dbms_output.put_line('created index result' || new_suffix || '_site_type');
  execute immediate 'create bitmap index result' || new_suffix || '_char_type on result' || new_suffix || ' (characteristic_type)';
  dbms_output.put_line('created index result' || new_suffix || '_char_type');
  execute immediate 'create index result' || new_suffix || '_geom on result' || new_suffix || q'! (geom) indextype is mdsys.spatial_index parameters('sdo_indx_dims=2 layer_gtype="POINT"')!';
  dbms_output.put_line('created index result' || new_suffix || '_geom');

end;
end;
/

select 'build dw indexes end time: ' || systimestamp from dual;
