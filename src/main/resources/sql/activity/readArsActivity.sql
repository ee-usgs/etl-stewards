select row_number() over () activity_id,
       a.*
  from (select distinct ars_result.activity_start_date,
                        ars_result.activity_identifier,
                        ars_result.activity_media_name,
                        ars_result.activity_type_code,
                        ars_result.activity_start_time,
                        ars_result.activity_start_time_zone_code,
                        ars_result.project_identifier,
                        ars_result.activity_comment_text,
                        ars_result.sample_collection_method_identifier,
                        ars_result.sample_collection_method_identifier_context,
                        ars_result.sample_collection_method_name,
                        ars_result.sample_collection_method_description_text,
                        ars_result.sample_collection_equipment_name,
                        ars_result.sample_collection_equipment_comment_text,
                        station_swap_stewards.station_id,
                        station_swap_stewards.site_id,
                        station_swap_stewards.organization,
                        station_swap_stewards.organization_name,
                        station_swap_stewards.site_type,
                        station_swap_stewards.huc,
                        station_swap_stewards.governmental_unit_code,
                        station_swap_stewards.geom,
                        station_swap_stewards.station_name,
                        project_data_swap_stewards.project_name
          from ars_result
               join station_swap_stewards
                 on ars_result.monitoring_location_identifier = substring(station_swap_stewards.site_id, 5)
               join project_data_swap_stewards
                 on ars_result.project_identifier = project_data_swap_stewards.project_identifier
             order by ars_result.activity_identifier
       ) a
