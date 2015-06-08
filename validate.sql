show user;
select * from global_name;
set timing on;
set serveroutput on;
whenever sqlerror exit failure rollback;
whenever oserror exit failure rollback;
select 'validate dw start time: ' || systimestamp from dual;

begin
  declare old_rows    int;
          new_rows    int;
          pass_fail   varchar2(15);
          end_job     boolean := false;

begin
  
  	--char_name--
  	select count(*) into old_rows from char_name partition (char_name_stewards);
  	select count(*) into new_rows from char_name_swap_stewards;
  	if new_rows > 8 and new_rows > old_rows - 5 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for char_name: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  	--country--
  	select count(*) into old_rows from country partition (country_stewards);
  	select count(*) into new_rows from country_swap_stewards;
  	if new_rows > 0 and new_rows > old_rows - 5 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for country: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  	--county--
  	select count(*) into old_rows from county partition (county_stewards);
  	select count(*) into new_rows from county_swap_stewards;
  	if new_rows > 2 and new_rows > old_rows - 5 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for county: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  	--organization--
  	select count(*) into old_rows from organization partition (organization_stewards);
  	select count(*) into new_rows from organization_swap_stewards;
  	if new_rows > 0 and new_rows > old_rows - 5 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for organization: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  	--esult--
  	select count(*) into old_rows from result partition (result_stewards);
  	select count(*) into new_rows from result_swap_stewards;
  	if new_rows > 200000 and new_rows > old_rows - 10000 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for result: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  	--sample_media--
  	select count(*) into old_rows from sample_media partition (sample_media_stewards);
  	select count(*) into new_rows from sample_media_swap_stewards;
  	if new_rows > 0 and new_rows > old_rows - 5 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for sample_media: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  	--site_type--
  	select count(*) into old_rows from site_type partition (site_type_stewards);
  	select count(*) into new_rows from site_type_swap_stewards;
  	if new_rows > 0 and new_rows > old_rows - 5 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for site_type: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  	--state--
  	select count(*) into old_rows from state partition (state_stewards);
  	select count(*) into new_rows from state_swap_stewards;
  	if new_rows > 0 and new_rows > old_rows - 5 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for state: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
      
  	--station--
  	select count(*) into old_rows from station partition (station_stewards);
  	select count(*) into new_rows from station_swap_stewards;
  	if new_rows > 45 and new_rows > old_rows - 10 then
    	pass_fail := 'PASS';
  	else
    	pass_fail := 'FAIL';
    	end_job := true;
  	end if;
  	dbms_output.put_line(pass_fail || ': table comparison for station: was ' || trim(to_char(old_rows, '999,999,999')) || ', now ' || trim(to_char(new_rows, '999,999,999')));
   
  
  	if end_job then
    	raise_application_error(-20666, 'Failed to pass one or more validation checks.');
  	end if;
end;
end;
/

select 'validate dw tables end time: ' || systimestamp from dual;
