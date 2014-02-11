load data
infile *
into table raw_result_xml
truncate
(file_name char(100)
,load_timestamp expression "current_timestamp"
,raw_xml LOBFILE (file_name) TERMINATED BY EOF
)
begindata
/opt/tomcat/.jenkins/ars_stewards/resultAlabama.xml
/opt/tomcat/.jenkins/ars_stewards/resultAlaska.xml
/opt/tomcat/.jenkins/ars_stewards/resultArizona.xml
/opt/tomcat/.jenkins/ars_stewards/resultArkansas.xml
/opt/tomcat/.jenkins/ars_stewards/resultCalifornia.xml
/opt/tomcat/.jenkins/ars_stewards/resultColorado.xml
/opt/tomcat/.jenkins/ars_stewards/resultConnecticut.xml
/opt/tomcat/.jenkins/ars_stewards/resultDelaware.xml
/opt/tomcat/.jenkins/ars_stewards/resultDistrictofColumbia.xml
/opt/tomcat/.jenkins/ars_stewards/resultFlorida.xml
/opt/tomcat/.jenkins/ars_stewards/resultGeorgia.xml
/opt/tomcat/.jenkins/ars_stewards/resultHawaii.xml
/opt/tomcat/.jenkins/ars_stewards/resultIdaho.xml
/opt/tomcat/.jenkins/ars_stewards/resultIllinois.xml
/opt/tomcat/.jenkins/ars_stewards/resultIndiana.xml
/opt/tomcat/.jenkins/ars_stewards/resultIowa.xml
/opt/tomcat/.jenkins/ars_stewards/resultKansas.xml
/opt/tomcat/.jenkins/ars_stewards/resultKentucky.xml
/opt/tomcat/.jenkins/ars_stewards/resultLouisiana.xml
/opt/tomcat/.jenkins/ars_stewards/resultMaine.xml
/opt/tomcat/.jenkins/ars_stewards/resultMaryland.xml
/opt/tomcat/.jenkins/ars_stewards/resultMassachusetts.xml
/opt/tomcat/.jenkins/ars_stewards/resultMichigan.xml
/opt/tomcat/.jenkins/ars_stewards/resultMinnesota.xml
/opt/tomcat/.jenkins/ars_stewards/resultMississippi.xml
/opt/tomcat/.jenkins/ars_stewards/resultMissouri.xml
/opt/tomcat/.jenkins/ars_stewards/resultMontana.xml
/opt/tomcat/.jenkins/ars_stewards/resultNebraska.xml
/opt/tomcat/.jenkins/ars_stewards/resultNevada.xml
/opt/tomcat/.jenkins/ars_stewards/resultNewHampshire.xml
/opt/tomcat/.jenkins/ars_stewards/resultNewJersey.xml
/opt/tomcat/.jenkins/ars_stewards/resultNewMexico.xml
/opt/tomcat/.jenkins/ars_stewards/resultNewYork.xml
/opt/tomcat/.jenkins/ars_stewards/resultNorthCarolina.xml
/opt/tomcat/.jenkins/ars_stewards/resultNorthDakota.xml
/opt/tomcat/.jenkins/ars_stewards/resultOhio.xml
/opt/tomcat/.jenkins/ars_stewards/resultOklahoma.xml
/opt/tomcat/.jenkins/ars_stewards/resultOregon.xml
/opt/tomcat/.jenkins/ars_stewards/resultPennsylvania.xml
/opt/tomcat/.jenkins/ars_stewards/resultRhodeIsland.xml
/opt/tomcat/.jenkins/ars_stewards/resultSouthCarolina.xml
/opt/tomcat/.jenkins/ars_stewards/resultSouthDakota.xml
/opt/tomcat/.jenkins/ars_stewards/resultTennessee.xml
/opt/tomcat/.jenkins/ars_stewards/resultTexas.xml
/opt/tomcat/.jenkins/ars_stewards/resultUtah.xml
/opt/tomcat/.jenkins/ars_stewards/resultVermont.xml
/opt/tomcat/.jenkins/ars_stewards/resultVirginia.xml
/opt/tomcat/.jenkins/ars_stewards/resultWashington.xml
/opt/tomcat/.jenkins/ars_stewards/resultWestVirginia.xml
/opt/tomcat/.jenkins/ars_stewards/resultWisconsin.xml
/opt/tomcat/.jenkins/ars_stewards/resultWyoming.xml
/opt/tomcat/.jenkins/ars_stewards/resultAmericanSamoa.xml
/opt/tomcat/.jenkins/ars_stewards/resultGuam.xml
/opt/tomcat/.jenkins/ars_stewards/resultNorthernMarianaIslands.xml
/opt/tomcat/.jenkins/ars_stewards/resultPuertoRico.xml
/opt/tomcat/.jenkins/ars_stewards/resultVirginIslandsoftheUS.xml