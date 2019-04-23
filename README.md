# etl\-stewards

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/92fd891cf75846cda0a5a817e2d7ebc7)](https://app.codacy.com/app/usgs_wma_dev/etl-stewards?utm_source=github.com&utm_medium=referral&utm_content=NWQMC/etl-stewards&utm_campaign=Badge_Grade_Settings)

ETL Water Quality Data from the ARS Stewards (USDA) System

[![Build Status](https://travis-ci.org/NWQMC/etl-stewards.svg?branch=master)](https://travis-ci.org/NWQMC/etl-stewards)

### Environment variables
Create an application.yml file in the project directory containing the following (shown are example values - they should match the values you used in creating the etlDB):

```
WQP_DATABASE_ADDRESS: <localhost>
WQP_DATABASE_PORT: <5437>
WQP_DATABASE_NAME: <wqp_db>
WQP_SCHEMA_NAME: <wqp>
WQP_SCHEMA_OWNER_USERNAME: <wqp_core>
WQP_SCHEMA_OWNER_PASSWORD: <changeMe>

ARS_DATABASE_ADDRESS: <localhost>
ARS_DATABASE_PORT: <5437>
ARS_DATABASE_NAME: <wqp_db>
ARS_SCHEMA_NAME: <ars>
ARS_SCHEMA_OWNER_USERNAME: <ars_owner>
ARS_SCHEMA_OWNER_PASSWORD: <changeMe>

NWIS_DATABASE_ADDRESS: <localhost>
NWIS_DATABASE_PORT: <5437>
NWIS_DATABASE_NAME: <wqp_db>
NWIS_SCHEMA_OWNER_USERNAME: <nwis_ws_star>
NWIS_SCHEMA_OWNER_PASSWORD: <changeMe>

ETL_OWNER_USERNAME: <ars_owner>
GEO_SCHEMA_NAME: <nwis>
ETL_DATA_SOURCE_ID: <1>
ETL_DATA_SOURCE: <STEWARDS>
QWPORTAL_SUMMARY_ETL: <false>
NWIS_OR_EPA: <>

```

#### Environment variable definitions
