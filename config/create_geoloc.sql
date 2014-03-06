CREATE TABLE IF NOT EXISTS GEOLOC_IP
(
NETWORK_START_IP BIGINT PRIMARY KEY , 	
NETWORK_END_IP BIGINT,
GEONAME_ID INT,
REGISTERED_COUNTRY_GEONAME_ID INT,
REPRESENTED_COUNTRY_GEONAME_ID INT,
POSTAL_CODE VARCHAR(256),
LATITUDE DOUBLE,
LONGITUDE DOUBLE,
IS_ANONYMOUS_PROXY INT,
IS_SATELLITE_PROVIDER INT
)
AS SELECT * FROM CSVREAD('data/geoloc_ip.csv');

CREATE TABLE IF NOT EXISTS GEOLOC_LOC
(
geoname_id INT PRIMARY KEY,
continent_code VARCHAR(128),
continent_name VARCHAR(128),
country_iso_code VARCHAR(128),
country_name VARCHAR(128),
subdivision_iso_code VARCHAR(128),
subdivision_name VARCHAR(128),
city_name VARCHAR(128),
metro_code VARCHAR(128),
time_zone VARCHAR(128),
)
AS SELECT * FROM CSVREAD('data/geoloc_loc.csv');
