LOAD DATA
INTO TABLE stewards_raw_xml append
(file_name char(100)
,load_timestamp expression "current_timestamp"
,raw_xml LOBFILE (file_name) TERMINATED BY EOF
)