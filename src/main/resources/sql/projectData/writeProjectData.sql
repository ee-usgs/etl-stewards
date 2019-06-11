insert
  into project_data_swap_stewards (data_source_id,
                                   data_source,
                                   organization,
                                   organization_name,
                                   project_id,
                                   project_identifier,
                                   project_name,
                                   description
                                  )
                           values (:dataSourceId,
                                   :dataSource,
                                   :organization,
                                   :organizationName,
                                   :projectId,
                                   :projectIdentifier,
                                   :projectName,
                                   :description
                                  )
