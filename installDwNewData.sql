show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'install dw new data start time: ' || systimestamp from dual;

begin
  declare the_suffix varchar2(6 char);

begin
  select '_' || to_char(nvl(max(to_number(substr(table_name, length('RESULT_') + 1))), 1), 'fm00000')
    into the_suffix
    from user_tables
   where translate(table_name, '0123456789', '0000000000') = 'RESULT_00000';
  dbms_output.put_line('using suffix:' || the_suffix); 
  
  execute immediate 'create or replace synonym characteristicname for characteristicname' || the_suffix;
  dbms_output.put_line('created synonym characteristicname');
  execute immediate 'create or replace synonym characteristictype for characteristictype' || the_suffix;
  dbms_output.put_line('created synonym characteristictype');
  execute immediate 'create or replace synonym country for country' || the_suffix;
  dbms_output.put_line('created synonym country');
  execute immediate 'create or replace synonym county for county' || the_suffix;
  dbms_output.put_line('created synonym county');
  execute immediate 'create or replace synonym organization for organization' || the_suffix;
  dbms_output.put_line('created synonym organization');
  execute immediate 'create or replace synonym result for result' || the_suffix;
  dbms_output.put_line('created synonym result');
  execute immediate 'create or replace synonym samplemedia for samplemedia' || the_suffix;
  dbms_output.put_line('created synonym samplemedia');
  execute immediate 'create or replace synonym sitetype for sitetype' || the_suffix;
  dbms_output.put_line('created synonym sitetype');
  execute immediate 'create or replace synonym state for state' || the_suffix;
  dbms_output.put_line('created synonym state');
  execute immediate 'create or replace synonym station for station' || the_suffix;
  dbms_output.put_line('created synonym station');

end;
end;
/

select 'install dw new data end time: ' || systimestamp from dual;
