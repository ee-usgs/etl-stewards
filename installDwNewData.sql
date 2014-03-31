show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'install dw new data start time: ' || systimestamp from dual;

begin
  declare new_suffix varchar2(6 char);

begin
  select new_suffix
    into new_suffix
    from suffix_magic;
  dbms_output.put_line('using suffix:' || new_suffix); 
  
  execute immediate 'create or replace synonym characteristicname for characteristicname' || new_suffix;
  dbms_output.put_line('created synonym characteristicname');
  execute immediate 'create or replace synonym characteristictype for characteristictype' || new_suffix;
  dbms_output.put_line('created synonym characteristictype');
  execute immediate 'create or replace synonym country for country' || new_suffix;
  dbms_output.put_line('created synonym country');
  execute immediate 'create or replace synonym county for county' || new_suffix;
  dbms_output.put_line('created synonym county');
  execute immediate 'create or replace synonym organization for organization' || new_suffix;
  dbms_output.put_line('created synonym organization');
  execute immediate 'create or replace synonym result for result' || new_suffix;
  dbms_output.put_line('created synonym result');
  execute immediate 'create or replace synonym samplemedia for samplemedia' || new_suffix;
  dbms_output.put_line('created synonym samplemedia');
  execute immediate 'create or replace synonym sitetype for sitetype' || new_suffix;
  dbms_output.put_line('created synonym sitetype');
  execute immediate 'create or replace synonym state for state' || new_suffix;
  dbms_output.put_line('created synonym state');
  execute immediate 'create or replace synonym station for station' || new_suffix;
  dbms_output.put_line('created synonym station');

end;
end;
/

select 'install dw new data end time: ' || systimestamp from dual;
