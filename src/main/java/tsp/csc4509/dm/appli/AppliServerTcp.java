package tsp.csc4509.dm.appli;


import tsp.csc4509.dm.tcp.TcpServer;
import tsp.csc4509.dm.tcp.TcpSocket;

import java.io.IOException;

public class AppliServerTcp {
	
	/**
	 * nombre d'arguments attendus par le main.
	 */
	private static final int NBARGS = 1;
	/**
	 * position du numéro du port dans argv[].
	 */
	private static  final int PORTARG = 0;
	
	
	public static void main(String[] argv) throws Exception {

		// Vérifier que le main reçoit bien 1 arguments.
		if (argv.length != NBARGS) {
			System.out.println("usage: java AppliClientTcp <machine> <port>");
			return;
		}
		
		// créer un serveur TCP en attente sur le port contenu dans argv[0].
		TcpServer tcpServer = new TcpServer(Integer.parseInt(argv[PORTARG]));
		
		// accepter un client.
		TcpSocket tcpSocket = tcpServer.acceptClient();
		
		// TODO renvoyer en echo tout ce que le client éccrit jusqu'à sa déconnexion.
	}
	
	

}
