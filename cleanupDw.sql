show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'cleanup start time: ' || systimestamp from dual;

begin
  /* Find all of the tables similar to the synonymed tables and delete them if the name is not an exact match or a 00000 suffix */
  for tbl in (select user_tables.table_name
                from user_tables
                     join user_synonyms
                       on user_tables.table_name like substr(user_synonyms.table_name, 1, (length(user_synonyms.table_name) - 6)) || '______'
               where user_tables.table_name != user_synonyms.table_name and
                     user_tables.table_name != substr(user_synonyms.table_name, 1, (length(user_synonyms.table_name) - 6)) || '_00000' loop

    execute immediate 'drop table ' || tbl.table_name || ' cascade constraints purge';
    
    dbms_output.put_line('dropped table: ' || tbl.table_name);
    
    /* heavy handed delete of user_sdo_geom_metadata - most executions will not delete anything. */
    execute immediate 'delete from user_sdo_geom_metadata where table_name = ' || tbl.table_name;
  end loop;
  
  commit;

end;
/

select 'cleanup end time: ' || systimestamp from dual;
