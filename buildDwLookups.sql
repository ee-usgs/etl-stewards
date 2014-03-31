show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'build dw lookups start time: ' || systimestamp from dual;

begin
  declare new_suffix varchar2(6 char);

begin
  select new_suffix
    into new_suffix
    from suffix_magic;
  dbms_output.put_line('using suffix:' || new_suffix); 

  execute immediate 'create table characteristicname' || new_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */
                                    distinct characteristic_name code_value,
                                             cast(null as varchar2(4000 char)) description
                               from result' || new_suffix || '
                              where characteristic_name is not null
                                 order by 1)';
  dbms_output.put_line('created table characteristicname' || new_suffix);

  execute immediate 'create table characteristictype' || new_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */
                                    distinct characteristic_type code_value,
                                             cast(null as varchar2(4000 char)) description
                               from result' || new_suffix || '
                              where characteristic_type is not null
                                 order by 1)';
  dbms_output.put_line('created table characteristictype' || new_suffix);

  execute immediate 'create table samplemedia' || new_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */
                                    distinct sample_media code_value,
                                             cast(null as varchar2(4000 char)) description
                               from result' || new_suffix || '
                              where sample_media is not null
                                 order by 1)';
  dbms_output.put_line('created table samplemedia' || new_suffix);

  execute immediate 'create table county' || new_suffix || ' as
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
                               from station' || new_suffix || q'! a
                                    join nwis_ws_star.county b
                                      on a.country_cd || ':' || a.state_cd || ':' || a.county_cd = b.code_value
                              where a.country_cd is not null
                                order by a.country_cd desc,
                                         a.state_cd,
                                         a.county_cd)!';
  dbms_output.put_line('created table county' || new_suffix);

  execute immediate 'create table country' || new_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */ 
                                    distinct a.country_cd code_value,
                                             b.description description
                               from station' || new_suffix || ' a
                                    join nwis_ws_star.country b
                                      on a.country_cd = b.code_value
                              where a.country_cd is not null
                                 order by b.description desc)';
  dbms_output.put_line('created table country' || new_suffix);

  execute immediate 'create table state' || new_suffix || ' as
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
                               from station' || new_suffix || q'! a
                                    join nwis_ws_star.state b
                                      on a.country_cd || ':' ||a.state_cd = b.code_value
                              where a.country_cd is not null
                                 order by a.country_cd desc,
                                          a.state_cd)!';
  dbms_output.put_line('created table state' || new_suffix);

  execute immediate 'create table sitetype' || new_suffix || ' as
                     select code_value,
                            description,
                            rownum sort_order
                       from (select /*+ parallel (4) */ 
                                    distinct site_type code_value,
                                             cast(null as varchar2(4000 char)) description
                               from station' || new_suffix || '
                              where site_type is not null
                                order by 1)';
  dbms_output.put_line('created table sitetype' || new_suffix);

end;
end;
/

select 'build dw lookups end time: ' || systimestamp from dual;
