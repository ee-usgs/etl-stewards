LOAD DATA
INFILE *
TRUNCATE
INTO TABLE raw_station_xml append
(file_name char(100)
,load_timestamp expression "current_timestamp"
,raw_xml LOBFILE (file_name) TERMINATED BY EOF
)
BEGINDATA
/opt/tomcat/.jenkins/ars_stewards/station.xml