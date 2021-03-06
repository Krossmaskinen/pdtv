package pdtv.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.concurrent.ArrayBlockingQueue;

import pdtv.database.Database;
import pdtv.sniffer.Packet;

public class dataTest {
	private Database database;
	

	private String time;
	private String type;
	private String protocol;
	private int hits;
	private String timeP;
	//to be done: real latitudes and longitudes
	private String fromlat;
	private String fromlong;
	private String tolat;
	private String tolong;
	
	public dataTest(Database datab) {
		database = datab;
	}
	
	public void insertData(Packet packet){
		//byte[] dest = packet.destination;
		//byte[] sor = packet.source;
		
		Time packettime = packet.time;
		time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(packettime);
		type = packet.type;
		protocol = packet.protocol;
		hits = packet.hits;
		
//ej r�tt format p� timeP
		long timePeriod = packet.timePeriod / 10000;
		timeP = new SimpleDateFormat("HH:mm:ss").format(timePeriod);
		System.out.println("Processing packet! (Worker id: ");
//h�mta HITCOUNT
		
		//to be done: real latitudes and longitudes
		fromlat = "59.3333";
		fromlong = "18.05";
		tolat = "32.78014";
		tolong = "-96.800451";
		
		
		//insert i databas				
		try {
			Connection connection = database.getConnectionPool().getConnection();			
			Statement s = connection.createStatement();
			in(s);
			
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void in(Statement s){
		try {	
		//Find corresponding connectid
		int conectid = getConnectionId(s);
		
		//Find corresponding protocolid
		int protocolid = getProtocolId(s);	
		
		//Find corresponding typeid
		int typeid = getTypeId(s);
		
		//Find next packetid 
		int packid = getMaxPackId(s)+1;
		
		//Insert to dabase
		s.execute("INSERT INTO PACKETS (PACKETID, CONNECTIONID, TIME, TIMEPERIOD, HITCOUNT, PROTOCOLID, TYPEID) VALUES ("+ packid +", " + conectid + ", '"+ time +"' , '"+ timeP+ "', 17," + protocolid + ","+ typeid+");");			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Find corresponding connectid	
	private synchronized int getConnectionId(Statement s){
		int conectid = 0;
		try {	
			ResultSet resultconnectid = s.executeQuery("SELECT CONNECTIONID FROM Connections INNER JOIN Addresses AS FromAddr ON Connections.FromAddr = FromAddr.AddrId INNER JOIN Locations AS FromLoc ON FromLoc.LocationId = FromAddr.LocationId INNER JOIN Addresses AS ToAddr ON Connections.ToAddr = ToAddr.AddrId INNER JOIN Locations AS ToLoc ON ToLoc.LocationId = ToAddr.LocationId WHERE FromLoc.LATITUDE = '"+ fromlat +"' AND FromLoc.LONGITUDE = '" + fromlong +"' AND ToLoc.LATITUDE = '" + tolat + "' AND ToLoc.LONGITUDE = '" + tolong +"';"); 
			if(resultconnectid.next()){
				conectid  = resultconnectid.getInt("CONNECTIONID");
			}
        
			//Create New connections values if connectid is not find
			else{
				//find fromaddr id
				int fromaddrmax = getAddId(s, fromlat, fromlong);
			
				//find toaddr id
				int toaddrmax = getAddId(s, tolat, tolong);
			
				resultconnectid = s.executeQuery("SELECT MAX(CONNECTIONID) AS MAX FROM Connections;");
				resultconnectid.next();
				conectid = resultconnectid.getInt("MAX")+1;
				s.execute("INSERT INTO CONNECTIONS (FROMADDR, TOADDR, ROUTEID) VALUES ("+fromaddrmax+", "+toaddrmax+", 0);");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conectid;
	}
	
	
	//Find corresponding addrid
	private synchronized int getAddId(Statement s, String lat, String longitude){
		int addrmax = 0;
		try {	
			ResultSet resultaddtid = s.executeQuery("SELECT ADDRID FROM Addresses AS Addr INNER JOIN Locations AS Loc ON Loc.LocationId = Addr.LocationId WHERE Loc.LATITUDE = '"+ lat +"' AND Loc.LONGITUDE = '" + longitude +"';");
			if(resultaddtid.next()){
				addrmax = resultaddtid.getInt("ADDRID");
			}
			else {
				int locid = createLoc(s, lat, longitude);
				resultaddtid = s.executeQuery("SELECT MAX(ADDRID) AS MAX FROM Addresses;");
				resultaddtid.next();
				addrmax = resultaddtid.getInt("MAX")+1;

				//create addr
//todo: get addr!!!
				s.execute("INSERT INTO ADDRESSES (ADDRID, ADDR, LOCATIONID) VALUES ("+ addrmax +",'23.34.213.82', " + locid +")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addrmax;
	}
	
	//Create location
//todo: get country/city!!!
	private synchronized int createLoc(Statement s, String lat, String longitude){
		int locmax = 0;
		try {	
			ResultSet resultloctid = s.executeQuery("SELECT MAX(LOCATIONID) AS MAX FROM Locations;");
			resultloctid.next();
			locmax = resultloctid.getInt("MAX")+1;

//todo: get country/city!!!
			//create location
			s.execute("INSERT INTO LOCATIONS (LOCATIONID, COUNTRY, CITY, LATITUDE, LONGITUDE) VALUES ("+ locmax + ",'SV', 'Stockholm','"+ lat +"', '" + longitude +"');");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return locmax;
	}
	
	
	//Find corresponding protocolid
	private synchronized int getProtocolId(Statement s){
		int protocolid = 0;
		try {	
			ResultSet resultprotocoltid = s.executeQuery("SELECT PROTOCOLID FROM PROTOCOLS WHERE NAME = '"+ protocol + "';");
			if(resultprotocoltid.next()){
				protocolid  = resultprotocoltid.getInt("PROTOCOLID");
			}
			//Create New protocol values if type is not find
			else{
				resultprotocoltid = s.executeQuery("SELECT MAX(PROTOCOLID) AS MAX FROM PROTOCOLS;");
				resultprotocoltid.next();
				int promax = resultprotocoltid.getInt("MAX")+1;
				
				s.execute("INSERT INTO PROTOCOLS (PROTOCOLID,NAME) VALUES (" + promax + ", '"+ protocol +"');");
				resultprotocoltid = s.executeQuery("SELECT PROTOCOLID FROM PROTOCOLS WHERE NAME = '"+ protocol + "';");
				resultprotocoltid.next();
				protocolid  = resultprotocoltid.getInt("PROTOCOLID");
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return protocolid;
	}
	
	
	//Find corresponding typeid
	private synchronized int getTypeId(Statement s){
		int typeid = 0;
		try {	
			ResultSet resulttypetid = s.executeQuery("SELECT TYPEID FROM TYPES WHERE NAME = '"+ type + "';");
			if(resulttypetid.next()){;
				typeid  = resulttypetid.getInt("TYPEID");
			}
			//Create New type values if type is not find
			else{
				resulttypetid = s.executeQuery("SELECT MAX(TYPEID) AS MAX FROM TYPES;");
				resulttypetid.next();
				int tymax = resulttypetid.getInt("MAX")+1;
				
				s.execute("INSERT INTO TYPES (TYPEID,NAME) VALUES (" + tymax +", '"+ type +"');");
				resulttypetid = s.executeQuery("SELECT TYPEID FROM TYPES WHERE NAME = '"+ type + "';");
				resulttypetid.next();
				typeid  = resulttypetid.getInt("TYPEID");
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return typeid;
	}
	
	//Get current max packetid
	private synchronized int getMaxPackId(Statement s){
		int packid = 0;
		try {	
		ResultSet resultpacktid = s.executeQuery("SELECT MAX(PACKETID) AS MAX FROM PACKETS;");
		resultpacktid.next();
		packid = resultpacktid.getInt("MAX");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packid;
	}
}
