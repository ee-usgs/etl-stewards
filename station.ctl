load data
infile *
into table raw_station_xml
truncate
(file_name char(100)
,load_timestamp expression "current_timestamp"
,raw_xml LOBFILE (file_name) TERMINATED BY EOF
)
begindata
/opt/tomcat/.jenkins/ars_stewards/station.xml