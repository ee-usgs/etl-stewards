select ars_monitoring_location.station_id,
       ars_org_project.organization,
       ars_org_project.organization_name,
       ars_monitoring_location.monitoring_location_identifier,
       ars_monitoring_location.monitoring_location_name,
       ars_monitoring_location.monitoring_location_type_name,
       site_type_to_primary.primary_site_type resolved_monitoring_location_type_name,
       ars_monitoring_location.monitoring_location_description_text,
       ars_monitoring_location.huc_eight_digit_code,
       ars_monitoring_location.huc_twelve_digit_code,
       ars_monitoring_location.drainage_area_measure_value,
       ars_monitoring_location.drainage_area_measure_unit_code,
       ars_monitoring_location.latitude_measure,
       ars_monitoring_location.longitude_measure,
       ars_monitoring_location.horizontal_collection_method_name,
       ars_monitoring_location.horizontal_coordinate_reference_system_datum_name,
       ars_monitoring_location.country_code,
       ars_monitoring_location.state_code,
       ars_monitoring_location.county_code
  from ars_monitoring_location
       join ars_org_project
         on 1=1
       left join site_type_to_primary
         on ars_monitoring_location.monitoring_location_type_name = site_type_to_primary.site_type
