package tsp.csc4509.dm.appli;

import tsp.csc4509.dm.tcp.TcpSocket;

import java.io.IOException;
import java.nio.ByteBuffer;


public class AppliClientTcp {
	/**
	 * nombre d'argument du main
	 */
	private static final int NBARGS = 2;
	/**
	 * position du nom de la machine du serveur dans  argv[].
	 */
	private static  final int HOSTARG = 0;
	/**
	 * position du port du serveur dans argv[].
	 */
	private static  final int PORTARG = 1;
	
	public static void main(String[] argv) throws IOException {
		
		
		// Vérifier que le main reçoit bien 2 arguments
		if (argv.length != NBARGS) {
			System.out.println("usage: java AppliClientTcp <machine> <port>");
			return;
		}
		
		// se connecter au serveur argv[0] = machine du seveur, argv[1] = port du serveur
		TcpSocket tcpSocket = new TcpSocket(argv[HOSTARG], Integer.parseInt(argv[PORTARG]));
		
		// envoyer une chaîne de caractère au serveur.
		/* Astuce: la méthode getBytes() de String retourne un tableau de byte contenant les octets de la chaîne de caractères.
		           String exemple = "toto";  byte [] exempleByte = exemple.getBytes(); */
		String exemple = "toto";
		byte[] exempleByte = exemple.getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(exempleByte.length);
		buffer.put(exempleByte);
		tcpSocket.sendBuffer(buffer);
		
		// lire et afficher la chaîne de caractères que le serveur nous a envoyé en echo
	    /*	Attention: la méthode receiveBuffer() de TcpSocket ne se termine que lorsque la connexion est fermée ou que
		 le buffer est rempli. Comme le serveur ne fermera pas sa connexion prévoyez un buffer de réception ayant
		 exactement la bonne taille.
		 Atuce: pour transformer le contenu d'un ByteBuffer en String, il faut d'abord le transformer en tableau de bytes
		        et ensuite construire une chaîne de caratères avec:
		String exemple = new String(buffer.array());	*/
		buffer.clear();
		tcpSocket.receiveBuffer(buffer);
		System.out.println(new String(buffer.array()));
		
		//  envoyer un entier au serveur.
		tcpSocket.sendSize(40);

		//TODO  lire et afficher l'entier que le serveur nous a envoyé en echo
		System.out.println( tcpSocket.receiveSize());
		tcpSocket.close();
		
	}
	

}
