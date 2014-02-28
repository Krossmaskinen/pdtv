INSERT INTO LOCATIONS (COUNTRY, CITY, LATITUDE, LONGITUDE) VALUES ('SV', 'Stockholm', 59.3333, 18.05);
INSERT INTO LOCATIONS (COUNTRY, CITY, LATITUDE, LONGITUDE) VALUES ('US', 'Ashburn', 39.0436, -77.4878);
INSERT INTO LOCATIONS (COUNTRY, CITY, LATITUDE, LONGITUDE) VALUES ('US', 'Dallas', 32.780140,-96.800451);
INSERT INTO LOCATIONS (COUNTRY, CITY, LATITUDE, LONGITUDE) VALUES ('SV', 'Göteborg', 62.0,15.0);

INSERT INTO PROTOCOLS (PROTOCOLID, NAME) VALUES (0, 'Unknown');
INSERT INTO PROTOCOLS (PROTOCOLID, NAME) VALUES (1, 'TCP');
INSERT INTO PROTOCOLS (PROTOCOLID, NAME) VALUES (2, 'UDP');
INSERT INTO PROTOCOLS (PROTOCOLID, NAME) VALUES (3, 'ICMP');

INSERT INTO TYPES (TYPEID, NAME) VALUES (0, 'Unknown');
INSERT INTO TYPES (TYPEID, NAME) VALUES (1, 'HTTP');
INSERT INTO TYPES (TYPEID, NAME) VALUES (2, 'HTTPS');
INSERT INTO TYPES (TYPEID, NAME) VALUES (3, 'Skype');

INSERT INTO ADDRESSES (ADDR, LOCATIONID) VALUES ('23.34.213.82', 1);
INSERT INTO ADDRESSES (ADDR, LOCATIONID) VALUES ('43.28.38.12', 2);
INSERT INTO ADDRESSES (ADDR, LOCATIONID) VALUES ('65.67.226.73', 3);
INSERT INTO ADDRESSES (ADDR, LOCATIONID) VALUES ('35.32.164.43', 4);
INSERT INTO ADDRESSES (ADDR, LOCATIONID) VALUES ('135.132.64.143', 4);

INSERT INTO CONNECTIONS (FROMADDR, TOADDR, ROUTEID) VALUES (1, 2, 0);
INSERT INTO CONNECTIONS (FROMADDR, TOADDR, ROUTEID) VALUES (1, 3, 0);
INSERT INTO CONNECTIONS (FROMADDR, TOADDR, ROUTEID) VALUES (1, 4, 0);
INSERT INTO CONNECTIONS (FROMADDR, TOADDR, ROUTEID) VALUES (2, 3, 0);
INSERT INTO CONNECTIONS (FROMADDR, TOADDR, ROUTEID) VALUES (2, 4, 0);

INSERT INTO PACKETS (CONNECTIONID, TIME, TIMEPERIOD, HITCOUNT, PROTOCOLID, TYPEID) VALUES (4, '2014-01-27 20:26:05', '00:00:05', 17, 1, 1);
INSERT INTO PACKETS (CONNECTIONID, TIME, TIMEPERIOD, HITCOUNT, PROTOCOLID, TYPEID) VALUES (1, '2014-01-27 20:26:05', '00:00:05', 31, 1, 1);
INSERT INTO PACKETS (CONNECTIONID, TIME, TIMEPERIOD, HITCOUNT, PROTOCOLID, TYPEID) VALUES (2, '2014-01-27 20:26:05', '00:00:05', 12, 1, 1);
INSERT INTO PACKETS (CONNECTIONID, TIME, TIMEPERIOD, HITCOUNT, PROTOCOLID, TYPEID) VALUES (3, '2014-01-27 20:26:10', '00:00:05', 19, 1, 1);

