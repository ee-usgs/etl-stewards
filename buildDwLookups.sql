show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'build dw lookups start time: ' || systimestamp from dual;

begin
  declare the_suffix varchar2(6 char);

begin
  select '_' || to_char(nvl(max(to_number(substr(table_name, length('RESULT_') + 1))), 1), 'fm00000')
    into the_suffix
    from user_tables
   where translate(table_name, '0123456789', '0000000000') = 'RESULT_00000';
  dbms_output.put_line('using suffix:' || the_suffix); 

  execute immediate 'create table characteristicname' || the_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */
                                    distinct characteristic_name code_value,
                                             cast(null as varchar2(4000 char)) description
                               from result' || the_suffix || '
                              where characteristic_name is not null
                                 order by 1)';
  dbms_output.put_line('created table characteristicname' || the_suffix);

  execute immediate 'create table samplemedia' || the_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */
                                    distinct sample_media code_value,
                                             cast(null as varchar2(4000 char)) description
                               from result' || the_suffix || '
                              where sample_media is not null
                                 order by 1)';
  dbms_output.put_line('created table samplemedia' || the_suffix);

  execute immediate 'create table county' || the_suffix || ' as
                     select code_value,
                            description,
                            country_cd,
                            state_cd,
                            rownum sort_order
                       from (select /*+ parallel (4) */ 
                                    distinct b.code_value,
                                             b.description,
                                             a.country_cd,
                                             a.state_cd,
                                             a.county_cd
                               from station' || the_suffix || q'! a
                                    join nwis_ws_star.county b
                                      on a.country_cd || ':' || a.state_cd || ':' || a.county_cd = b.code_value
                              where a.country_cd is not null
                                order by a.country_cd desc,
                                         a.state_cd,
                                         a.county_cd)!';
  dbms_output.put_line('created table county' || the_suffix);

  execute immediate 'create table country' || the_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */ 
                                    distinct a.country_cd code_value,
                                             b.description description
                               from station' || the_suffix || ' a
                                    join nwis_ws_star.country b
                                      on a.country_cd = b.code_value
                              where a.country_cd is not null
                                 order by b.description desc)';
  dbms_output.put_line('created table country' || the_suffix);

  execute immediate 'create table state' || the_suffix || ' as
                     select code_value,
                            description_with_country,
                            description_with_out_country,
                            country_cd,
                            rownum sort_order
                       from (select /*+ parallel (4) */ 
                                    distinct b.code_value,
                                             b.description_with_country,
                                             b.description_with_out_country,
                                             a.country_cd,
                                             a.state_cd
                               from station' || the_suffix || q'! a
                                    join nwis_ws_star.state b
                                      on a.country_cd || ':' ||a.state_cd = b.code_value
                              where a.country_cd is not null
                                 order by a.country_cd desc,
                                          a.state_cd)!';
  dbms_output.put_line('created table state' || the_suffix);

  execute immediate 'create table sitetype' || the_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */ 
                                    distinct site_type code_value,
                                             cast(null as varchar2(4000 char)) description
                               from station' || the_suffix || '
                              where site_type is not null
                                order by 1)';
  dbms_output.put_line('created table sitetype' || the_suffix);

end;
end;
/

select 'build dw lookups end time: ' || systimestamp from dual;
