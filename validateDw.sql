show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'validate dw start time: ' || systimestamp from dual;

begin
  declare the_suffix  varchar2(6 char);
          old_rows    int;
          new_rows    int;
          stage_rows  int;
          index_count int;
          grant_count int;
          pass_fail   varchar2(15);
          end_job     boolean := false;

begin
  select '_' || to_char(nvl(max(to_number(substr(table_name, length('RESULT_') + 1))), 1), 'fm00000')
    into the_suffix
    from user_tables
   where translate(table_name, '0123456789', '0000000000') = 'RESULT_00000';
  dbms_output.put_line('using suffix:' || the_suffix); 
  
  --Activity--
  select count(*) into old_rows from activity;
  select count(*) into stage_rows from activity_temp;
  execute immediate 'select count(*) from activity' || the_suffix into new_rows;
  if new_rows > 200000 and new_rows > old_rows - 10000 and new_rows = stage_rows then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for activity: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999'))
                                 || ', stage ' || trim(to_char(stage_rows, '999,999,999')));
      
  --Characteristicname--
  select count(*) into old_rows from characteristicname;
  execute immediate 'select count(*) from characteristicname' || the_suffix into new_rows;
  if new_rows > 8 and new_rows > old_rows - 5 then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for characteristicname: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  --Country--
  select count(*) into old_rows from country;
  execute immediate 'select count(*) from country' || the_suffix into new_rows;
  if new_rows > 0 and new_rows > old_rows - 5 then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for country: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  --County--
  select count(*) into old_rows from county;
  execute immediate 'select count(*) from county' || the_suffix into new_rows;
  if new_rows > 2 and new_rows > old_rows - 5 then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for county: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  --Organization--
  select count(*) into old_rows from organization;
  select count(*) into stage_rows from organization_temp;
  execute immediate 'select count(*) from organization' || the_suffix into new_rows;
  if new_rows > 0 and new_rows > old_rows - 5 then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for organization: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999'))
                                 || ', stage ' || trim(to_char(stage_rows, '999,999,999')));
      
  --Result--
  select count(*) into old_rows from result;
  select count(*) into stage_rows from result_temp;
  execute immediate 'select count(*) from result' || the_suffix into new_rows;
  if new_rows > 200000 and new_rows > old_rows - 10000 and new_rows = stage_rows then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for result: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999'))
                                 || ', stage ' || trim(to_char(stage_rows, '999,999,999')));
      
  --Samplemedia--
  select count(*) into old_rows from samplemedia;
  execute immediate 'select count(*) from samplemedia' || the_suffix into new_rows;
  if new_rows > 0 and new_rows > old_rows - 5 then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for samplemedia: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  --Sitetype--
  select count(*) into old_rows from sitetype;
  execute immediate 'select count(*) from sitetype' || the_suffix into new_rows;
  if new_rows > 0 and new_rows > old_rows - 5 then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for sitetype: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  --State--
  select count(*) into old_rows from state;
  execute immediate 'select count(*) from state' || the_suffix into new_rows;
  if new_rows > 0 and new_rows > old_rows - 5 then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for state: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  --Station--
  select count(*) into old_rows from station;
  select count(*) into stage_rows from station_temp;
  execute immediate 'select count(*) from station' || the_suffix into new_rows;
  if new_rows > 45 and new_rows > old_rows - 10 and new_rows = stage_rows then
    pass_fail := 'PASS';
  else
    pass_fail := 'FAIL';
    end_job := true;
  end if;
  dbms_output.put_line(pass_fail || ': table comparison for station: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999'))
                                 || ', stage ' || trim(to_char(stage_rows, '999,999,999')));
   
  --Indexes
  select count(*)
    into index_count
    from user_indexes
   where translate(table_name, '0123456789', '0000000000') in ('ACTIVITY_00000', 'CHARACTERISTICNAME_00000', 'COUNTRY_00000', 'COUNTY_00000', 'ORGANIZATION_00000',
                                                               'RESULT_00000', 'SAMPLEMEDIA_00000', 'SITETYPE_00000', 'STATE_00000', 'STATION_00000') and
         substr(table_name, -6) = the_suffix;
  if index_count < 30 then  /* there are exactly 30 as of 20NOV2013 */
    pass_fail := 'FAIL';
    end_job := true;
  else
    pass_fail := 'PASS';
  end if;
  dbms_output.put_line(pass_fail || ': found ' || to_char(index_count) || ' indexes.');

  select count(*)
    into grant_count
    from user_tab_privs
   where grantee = 'ARS_STEWARDS_USER' and 
         translate(table_name, '0123456789', '0000000000') in ('ACTIVITY_00000', 'CHARACTERISTICNAME_00000', 'COUNTRY_00000', 'COUNTY_00000', 'ORGANIZATION_00000',
                                                               'RESULT_00000', 'SAMPLEMEDIA_00000', 'SITETYPE_00000', 'STATE_00000', 'STATION_00000') and
         substr(table_name, -6) = the_suffix;
  if grant_count < 10 then  /* there are exactly 10 as of 20NOV2013 */
    pass_fail := 'FAIL';
    end_job := true;
  else
    pass_fail := 'PASS';
  end if;
  dbms_output.put_line(pass_fail || ': found ' || to_char(grant_count) || ' grants.');
  
  if end_job then
    raise_application_error(-20666, 'Failed to pass one or more validation checks.');
  end;
end;
end;
/

select 'validate dw tables end time: ' || systimestamp from dual;
