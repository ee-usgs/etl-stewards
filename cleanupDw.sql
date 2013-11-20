show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'cleanup start time: ' || systimestamp from dual;

begin
  declare current_suffix varchar2(6 char);

begin
  select '_' || to_char(nvl(max(to_number(substr(table_name, length('RESULT_') + 1))), 1), 'fm00000')
    into current_suffix
    from user_tables
   where translate(table_name, '0123456789', '0000000000') = 'RESULT_00000';
  dbms_output.put_line('using suffix:' || current_suffix); 

  for tbl in (select user_tables.table_name
                from user_tables
                     join user_synonyms
                       on user_tables.table_name like substr(user_synonyms.table_name, 1, (length(user_synonyms.table_name) - 6)) || '______'
               where user_tables.table_name != user_synonyms.table_name and
                     user_tables.table_name != substr(user_synonyms.table_name, 1, (length(user_synonyms.table_name) - 6)) || '_00000' and
                     substr(user_tables.table_name, -5) < to_char(to_number(substr(current_suffix, 2) - 1), 'fm00000')) loop

    execute immediate 'drop table ' || tbl.table_name || ' cascade constraints purge';
    
    dbms_output.put_line('dropped table: ' || tbl.table_name);
  end loop;
  
  execute immediate q'!delete from user_sdo_geom_metadata where table_name like 'STATION%' and table_name <> 'STATION!' || current_suffix || q'!'!';
  dbms_output.put_line('deleted old user_sdo_geom_metadata entries');
  commit;

end;
end;
/

select 'cleanup end time: ' || systimestamp from dual;
