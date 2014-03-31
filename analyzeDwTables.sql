show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'analyze dw tables start time: ' || systimestamp from dual;

begin
  declare new_suffix varchar2(6 char);

begin
  select new_suffix
    into new_suffix
    from suffix_magic;
  dbms_output.put_line('using suffix:' || new_suffix); 

  dbms_output.put_line('analyze characteristicname' || new_suffix);
  dbms_stats.gather_table_stats(user, 'CHARACTERISTICNAME' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze characteristictype' || new_suffix);
  dbms_stats.gather_table_stats(user, 'CHARACTERISTICTYPE' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze country' || new_suffix);
  dbms_stats.gather_table_stats(user, 'COUNTRY' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze county' || new_suffix);
  dbms_stats.gather_table_stats(user, 'COUNTY' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze organization' || new_suffix);
  dbms_stats.gather_table_stats(user, 'ORGANIZATION' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze result' || new_suffix);
  dbms_stats.gather_table_stats(user, 'RESULT' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze samplemedia' || new_suffix);
  dbms_stats.gather_table_stats(user, 'SAMPLEMEDIA' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze sitetype' || new_suffix);
  dbms_stats.gather_table_stats(user, 'SITETYPE' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze state' || new_suffix);
  dbms_stats.gather_table_stats(user, 'STATE' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);
  dbms_output.put_line('analyze station' || new_suffix);
  dbms_stats.gather_table_stats(user, 'STATION' || new_suffix, null, 100, false, 'FOR ALL COLUMNS SIZE AUTO', 1, 'ALL', true);

end;
end;
/

select 'analyze dw tables end time: ' || systimestamp from dual;
