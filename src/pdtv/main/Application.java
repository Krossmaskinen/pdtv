package pdtv.main;

import java.util.Properties;

import pdtv.database.Database;
import pdtv.sniffer.Sniffer;
import pdtv.ui.Window;
import pdtv.ui.WindowListener;
import pdtv.webserver.WebServer;

public class Application implements WindowListener{
	
	Window window;
	
	Database database;
	Sniffer sniffer;
	WebServer webServer;
 
	public Application(Properties properties) {	
		database = new Database(properties);
		sniffer = new Sniffer(properties, database);
		webServer = new WebServer(properties, database);
		
		database.createWorkers(webServer);

		window = new Window(database, sniffer, webServer);
		window.addListener(this);
	}
	
	public void start() {
		database.start();
		//sniffer.start();
		webServer.start();
	}

	@Override
	public void requestClose() {	
		window.close();
		
		webServer.stop();
		//sniffer.stop();
		database.stop();
	}

	@Override
	public void buttonPressed(int id) {
		
	}
}
