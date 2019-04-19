insert
  into ars_org_project (organization,
                        organization_name,
                        project_identifier,
                        project_name,
                        project_description_text
                       )
                values (:organizationIdentifier,
                        :organizationName,
                        :projectIdentifier,
                        :projectName,
                        :projectDescriptionText)
