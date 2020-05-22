package tsp.csc4509.dm.tcp;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 * Classe qui crée un serveur en attende de connexion et qui fournit une méthode pour construire une instance de TcpSocket connectée à chaque client accepté.
 * @author Shihui HUANG
 *
 */
public class TcpServer  implements AutoCloseable {
	/**
	 * le canal d'écoute du serveur.
	 */
	private final ServerSocketChannel listenChannel;
	
	
	/**
	 * Constructeur qui crée un serveur TCP en écoute sur le port passé en paramètre.
	 * @param port
	 *            numéro du port d'écoute.
	 * @throws IOException
	 *            toutes les exceptions d'entrées/sorties.
	 */
	public TcpServer(final int port) throws IOException {
		InetSocketAddress servAddr = new InetSocketAddress(port);
		listenChannel = ServerSocketChannel.open();
		listenChannel.bind(servAddr);
		
	}
	
	/**
	 * Accepte les nouveaux clients et crée une instance de TcpScoket à partir de cette nouvelle connexion.
	 * @return
	 *         la référence sur l'instance de TcpSocket créée.
	 * @throws IOException
	 *          toutes les exceptions d'entrées/sorties.
	 */
	public TcpSocket acceptClient() throws IOException {
		SocketChannel servChan = this.listenChannel.accept();
		return new TcpSocket(servChan);
	}

	@Override
	public void close() throws Exception {
		listenChannel.close();
	}

}
