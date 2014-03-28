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
  select next_suffix
    into new_suffix
    from suffix_magic;
  dbms_output.put_line('new suffix:' || new_suffix); 
  
  execute immediate 'create table organization' || new_suffix || ' as select /*+ parallel (4) */ * from organization_temp@' || dblink;
  execute immediate 'alter table organization' || new_suffix || ' add constraint organization' || new_suffix || '_pk primary key (code_value)';
  dbms_output.put_line('created table organization' || new_suffix);
  
  execute immediate 'create table station' || new_suffix || ' as select /*+ parallel (4) */ * from station_temp@' || dblink;
  execute immediate 'alter table station' || new_suffix || ' add constraint station' || new_suffix || '_pk primary key (station_pk)';
  execute immediate 'alter table station' || new_suffix || ' add constraint station' || new_suffix || '_org foreign key (organization_id) references organization' ||
                     new_suffix || ' (code_value) disable';
  dbms_output.put_line('created table station' || new_suffix);

  execute immediate 'create table result' || new_suffix || ' as select /*+ parallel (4) */ * from result_temp@' || dblink;
  execute immediate 'alter table result' || new_suffix || ' add constraint result' || new_suffix || '_pk primary key (result_pk)';
  execute immediate 'alter table result' || new_suffix || ' add constraint result' || new_suffix || '_station foreign key (station_pk) references station' ||
                     new_suffix || ' (station_pk) disable';
  dbms_output.put_line('created table result' || new_suffix);

end;
end;
/

select 'build dw data tables end time: ' || systimestamp from dual;
