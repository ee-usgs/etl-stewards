show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'install dw new data start time: ' || systimestamp from dual;

begin

	dbms_output.put_line('station');
    execute immediate 'alter table station exchange partition station_stewards with table station_swap_stewards including indexes';
    
    dbms_output.put_line('pc_result');
   	execute immediate 'alter table pc_result exchange partition pc_result_stewards with table pc_result_swap_stewards including indexes';
    
   	dbms_output.put_line('station_sum');
	execute immediate 'alter table station_sum exchange partition station_sum_stewards with table station_sum_swap_stewards including indexes';
    
	dbms_output.put_line('pc_result_sum');
	execute immediate 'alter table pc_result_sum exchange partition pc_result_sum_stewards with table pc_result_sum_swap_stewards including indexes';
    
	dbms_output.put_line('pc_result_ct_sum');
	execute immediate 'alter table pc_result_ct_sum exchange partition pc_result_ct_sum_stewards with table pc_result_ct_sum_swap_stewards including indexes';
    
	dbms_output.put_line('pc_result_nr_sum');
	execute immediate 'alter table pc_result_nr_sum exchange partition pc_res_nr_sum_stewards with table pc_result_nr_sum_swap_stewards including indexes';
    
	dbms_output.put_line('characteristic_name');
	execute immediate 'alter table char_name exchange partition char_name_stewards with table char_name_swap_stewards including indexes';
    
	dbms_output.put_line('characteristic_type');
	execute immediate 'alter table char_type exchange partition char_type_stewards with table char_type_swap_stewards including indexes';
    
	dbms_output.put_line('country');
	execute immediate 'alter table country exchange partition country_stewards with table country_swap_stewards including indexes';
    
	dbms_output.put_line('county');
	execute immediate 'alter table county exchange partition county_stewards with table county_swap_stewards including indexes';
    
	dbms_output.put_line('organization');
	execute immediate 'alter table organization exchange partition organization_stewards with table organization_swap_stewards including indexes';
    
	dbms_output.put_line('sample_media');
	execute immediate 'alter table sample_media exchange partition sample_media_stewards with table sample_media_swap_stewards including indexes';
    
	dbms_output.put_line('site_type');
	execute immediate 'alter table site_type exchange partition site_type_stewards with table site_type_swap_stewards including indexes';
    
	dbms_output.put_line('state');
	execute immediate 'alter table state exchange partition state_stewards with table state_swap_stewards including indexes';

end;
/

select 'install dw new data end time: ' || systimestamp from dual;
