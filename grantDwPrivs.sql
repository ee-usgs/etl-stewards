show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'grant dw privs start time: ' || systimestamp from dual;

begin
  declare the_suffix varchar2(6 char);
          cursor cur(p_the_suffix varchar2) is
            select user_tab_privs.grantee, 
                   user_tab_privs.table_name,
                   substr(user_tab_privs.table_name, 1, (length(user_tab_privs.table_name) - 6)) || p_the_suffix new_table_name,
                   user_tab_privs.privilege,
                   user_tab_privs.grantable
              from user_tab_privs
                   join user_tables
                     on user_tab_privs.table_name = user_tables.table_name
                   join user_synonyms
                     on user_tab_privs.table_name = user_synonyms.table_name
             where user_tab_privs.owner = user and
                   user_tab_privs.grantor = user;
      
          type grants_table is table of cur%rowtype;
          my_grants grants_table;
          grant_text varchar2(4000 char);

begin
  select '_' || to_char(nvl(max(to_number(substr(table_name, length('RESULT_') + 1))), 1), 'fm00000')
    into the_suffix
    from user_tables
   where translate(table_name, '0123456789', '0000000000') = 'RESULT_00000';
  dbms_output.put_line('using suffix:' || the_suffix); 
  
  open cur(the_suffix);
  fetch cur bulk collect into my_grants;
  if cur%rowcount = 0 then
    dbms_output.put_line('something bad happened - all the grants are missing');
    raise no_data_found;
  elsif the_suffix = substr(my_grants(1).table_name, -6) then
    dbms_output.put_line('something bad happened - the existing grant points at the new table');
    raise value_error;
  end if;
  close cur;
    
  for i in my_grants.first .. my_grants.last loop
    grant_text := 'grant ' || my_grants(i).privilege || ' on ' || my_grants(i).new_table_name || ' to ' || my_grants(i).grantee || 
                  case my_grants(i).grantable
                    when 'YES' then ' with grant option'
                  end;
    dbms_output.put_line('about to run grant: ' || grant_text);
    
    begin
      execute immediate grant_text;
    exception
      when sqlcode = -00942 then
        dbms_output.put_line('table not found for grant: ' || grant_text);
    end;

  end loop;

end;
end;
/

select 'grant dw privs end time: ' || systimestamp from dual;
