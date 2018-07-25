show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'transform org_data start time: ' || systimestamp from dual;

prompt building org_data_swap_stewards 

prompt dropping stewards org_data indexes
exec etl_helper_org_data.drop_indexes('stewards');

set define off;
prompt populating org_data_swap_stewards
truncate table org_data_swap_stewards;
insert /*+ append parallel(4) */
  into org_data_swap_stewards (data_source_id, data_source, organization_id, organization, organization_name)
select DISTINCT /* parallel(4) */
       1 data_source_id,
	   'STEWARDS' data_source,
       3000000 organization_id,
	   organization,
       organization_name
  from STATION_SWAP_STEWARDS
  
commit;
  
prompt building stewards orgData indexes
exec etl_helper_org_data.create_indexes('stewards');

select 'transform orgData end time: ' || systimestamp from dual;