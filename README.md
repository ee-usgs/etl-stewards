# etl\-stewards

ETL Water Quality Data from the ARS Stewards (USDA) System

This ETL is unique in that the source data is in XML.

These scripts are run by the OWI Jenkins Job Runners. The job names are WQP\_STEWARDS\_ETL and WQP\_STEWARDS\_ETL\_Pull\_Data. They follow the general OWI ETL pattern using ant to control the execution of PL/SQL scripts.

The basic flow is:

* Download the data from the ARS webservice using curl. (WQP\_STEWARDS\_ETL\_Pull\_Data job)

* Load the station data into the nolog ars_stewards schema with sqlldr.

* Load the result data into the nolog ars_stewards scheam with sqlldr.

* Drop the referential integrity constraints on the stewards swap tables of the wqp_core schema. (dropRI.sql)

* Drop the indexes on the stewards station swap table, populate with transformed data, and rebuild the indexes. (transformStation.sql)

* Drop the indexes on the stewards result swap table, populate with transformed data, and rebuild the indexes. (transformResult.sql)

* Drop the indexes on the stewards summary swap tables, populate with transformed data, and rebuild the indexes. (createSummaries.sql)

* Drop the indexes on the stewards code lookup swap tables, populate with transformed data, and rebuild the indexes. (createCodes.sql)

	**Note:** Several code lookup values are dependent on data from the WQP\_NWIS\_ETL correctly collecting data from natprod.


* Add back the referential integrity constraints on the stewards swap tables of the wqp_core schema. (addRI.sql)

* Analyze the stewards swap tables of the wqp_core schema. (analyze.sql)

* Validate that rows counts and change in row counts are within the tolerated values. (validate.sql)

* Install the new data using partition exchanges. (install.sql)

The translation of data is specific to this repository. The heavy lifting (indexing, RI, partition exchanges, etc.) is done using common packages in the wqp_core schema. These are defined in the schema-wqp-core repository.
