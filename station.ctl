LOAD DATA
INTO TABLE stewards_raw_xml append
(file_name char(100)
,load_timestamp systimestamp
,raw_xml LOBFILE (file_name) TERMINATED BY EOF
)