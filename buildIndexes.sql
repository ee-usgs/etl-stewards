show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'build indexes start time: ' || systimestamp from dual;

begin
	etl_helper.create_station_indexes('stewards');
	etl_helper.create_result_indexes('stewards');
	etl_helper.create_station_sum_indexes('stewards');
	etl_helper.create_result_sum_indexes('stewards');
	etl_helper.create_result_ct_sum_indexes('stewards');
	etl_helper.create_result_nr_sum_indexes('stewards');
end;
/

select 'build indexes end time: ' || systimestamp from dual;
