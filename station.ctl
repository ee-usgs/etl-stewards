LOAD DATA
INFILE *
INTO TABLE ars_xml_test append
(
 file_name char(100)
,ars_xml LOBFILE (file_name) TERMINATED BY EOF
)
BEGINDATA
/opt/tomcat/.jenkins/ars_stewards/station.xml