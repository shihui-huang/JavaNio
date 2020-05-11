//CHECKSTYLE:OFF
package tsp.csc4509.dm.tcp;

import static tsp.csc4509.common.Log.LOGGER_NAME_TEST;
import static tsp.csc4509.common.Log.LOG_ON;
import static tsp.csc4509.common.Log.TEST;

import org.apache.log4j.Level;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import tsp.csc4509.common.Log;
import tsp.csc4509.tcpservices.TcpTestTools;

public class TestTcpServer {
	
	static private TcpTestTools tcpTestTools;
	
	@BeforeClass
	static public void lanceTestTools() {
		tcpTestTools = new TcpTestTools();
		Log.configureALogger(LOGGER_NAME_TEST, Level.WARN);
		
	}
	
	
	// test unitaire de l'accept du serveur.
	@Test
	public void testAccept() throws Exception {
		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("TestTcpServer::testAccept -> test d'acceptation d'un client");
		}
		
		// Ici test unitaire du constructeur du serveur
		TcpServer serveur = new TcpServer(40002);
		
		// si aucune exception n'est levé, le test OK.
		
		tcpTestTools.alarmStart(5000); // on laisse 5 secondes pour que la connexion s'établisse
		tcpTestTools.clienStart(40002); // on demande à un client de se connecter à notre serveur
		
		// Ici test unitaire de la méthode acceptClient()
		TcpSocket client = serveur.acceptClient();	
		// on n'arrive ici que si il n'y pas eu d'exception. C'est donc un succès. 
		
		
		tcpTestTools.alarmStop(); // connexion ok. On stope l'alarme.

		client.close();
		
		// Ici test unitaire de la méthode close() du serveur
		serveur.close();
		
		// si aucune exception n'a été lévée, le test est OK.
	}
	
	

}
