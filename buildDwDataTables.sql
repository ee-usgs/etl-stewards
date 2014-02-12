show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'build dw data tables start time: ' || systimestamp from dual;

begin
  declare new_suffix varchar2(6 char);
          dblink user_db_links.db_link%type := '&1';

begin
  select '_' || to_char(nvl(max(to_number(substr(table_name, length('RESULT_') + 1))) + 1, 1), 'fm00000')
    into new_suffix
    from user_tables
   where translate(table_name, '0123456789', '0000000000') = 'RESULT_00000';
  dbms_output.put_line('new suffix:' || new_suffix); 
  
  execute immediate 'create table organization' || new_suffix || ' as select /*+ parallel (4) */ organization_temp.*, xmlserialize(content organization_details as clob no indent) organization_clob from organization_temp@' || dblink;
  execute immediate 'alter table organization' || new_suffix || ' add constraint organization' || new_suffix || '_pk primary key (code_value)';
  dbms_output.put_line('created table organization' || new_suffix);
  
  execute immediate 'create table station' || new_suffix || ' as select /*+ parallel (4) */ station_temp.*, xmlserialize(content station_details as clob no indent) station_clob from station_temp@' || dblink;
  execute immediate 'alter table station' || new_suffix || ' add constraint station' || new_suffix || '_pk primary key (station_pk)';
  execute immediate 'alter table station' || new_suffix || ' add constraint station' || new_suffix || '_org foreign key (organization_id) references organization' ||
                     new_suffix || ' (code_value) disable';
  dbms_output.put_line('created table station' || new_suffix);

  execute immediate 'create table activity' || new_suffix || ' as select /*+ parallel (4) */ activity_temp.*, xmlserialize(content activity_details as clob no indent) activity_clob from activity_temp@' || dblink;
  execute immediate 'alter table activity' || new_suffix || ' add constraint activity' || new_suffix || '_pk primary key (activity_pk)';
  dbms_output.put_line('created table activity' || new_suffix);

  execute immediate 'create table result' || new_suffix || ' as select /*+ parallel (4) */ result_temp.*, characteristic_type, xmlserialize(content result_details as clob no indent) result_clob from result_temp@' || dblink
                     || ' left join characteristic_name_to_type on result_temp.characteristic_name = characteristic_name_to_type.characteristic_name';
  execute immediate 'alter table result' || new_suffix || ' add constraint result' || new_suffix || '_pk primary key (result_pk)';
  execute immediate 'alter table result' || new_suffix || ' add constraint result' || new_suffix || '_station foreign key (station_pk) references station' ||
                     new_suffix || ' (station_pk) disable';
  execute immediate 'alter table result' || new_suffix || ' add constraint result' || new_suffix || '_activity foreign key (activity_pk) references activity' ||
                     new_suffix || ' (activity_pk) disable';
  dbms_output.put_line('created table result' || new_suffix);

end;
end;
/

select 'build dw data tables end time: ' || systimestamp from dual;
