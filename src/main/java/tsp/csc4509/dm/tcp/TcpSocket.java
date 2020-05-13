package tsp.csc4509.dm.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/**
 * Classe qui
 *  -> établit la connexion d'un client ; 
 *  -> fournit les méthodes d'échange de données sur un canal TCP connecté (par un client ou un serveur).
 * 
 * @author Eric Lallet.
 *
 */
public class TcpSocket  implements AutoCloseable {

	/**
	 * un canal TCP connecté (soit par la connexion coté client, soit par l'accept coté serveur)
	 */
	private SocketChannel rwChan = null;
	
	
	/**
	 * Constructeur utilisé par les serveurs pour créer une instance TcpSocket à partir d'une connexion obtenue suite à un accept.
	 * @param rwChan 
	 *              le canal déjà connecté suite à l'accept.
	 */
	public TcpSocket(SocketChannel rwChan) {
		 this.rwChan = rwChan;
	}
	
	/**
	 * Constructeur utilisé par le client pour ouvrir une connexion vers un serveur, et créer une instance TcpSocket.
	 * @param serverHost
	 * 	                le nom de la machine où tourne le serveur.
	 * @param serverPort
	 *                  le numéro du port TCP utilisé par le serveur.
	 * @throws IOException
	 *                  toutes les exceptions d'entrées/sorties.
	 */
	public TcpSocket(String serverHost, int serverPort) throws IOException {
		InetAddress servAddr = InetAddress.getByName(serverHost);
		InetSocketAddress servSockAddr = new InetSocketAddress(servAddr, serverPort);
		rwChan = SocketChannel.open(servSockAddr);
	}
	
	/**
	 * Envoie les données contenues dans le buffer sur la connexion TCP.
	 * @param buffer
	 *              le buffer contenant les données à envoyer.
	 * @return
	 *              le nombre d'octets envoyés.
	 * @throws IOException
	 *                  toutes les exceptions d'entrées/sorties.
	 */
	public int sendBuffer(ByteBuffer buffer) throws IOException {	
		buffer.flip();
		return rwChan.write(buffer);
	}
	
	/**
	 * Envoie un objet instancié sérialisable sur la connexion TCP.
	 * @param s
	 *         référence sur l'objet à envoyer.
	 * @return
	 *         le nombre d'octets envoyés.
	 * @throws IOException
	 *          toutes les exceptions d'entrées/sorties.
	 */
	public int sendObject(final Serializable s) throws IOException {
		// sérialiser l'objet
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(s);
		oo.close();

		// calculer la taille de l'objet sérialisé
		int size = bo.size();

		// envoyer un entier contenant cette taille
		sendSize(size);

		// envoyer l'objet sérialisé
		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.put(bo.toByteArray());
		bo.close();

		sendBuffer(buffer);

		return size;
	}
	
	
	/**
	 * Envoie un entier sur la connexion TCP.
	 * @param size
	 *            la valeur de l'entier à envoyer.
	 * @return
	 *            le nombre d'octets envoyés.
	 * @throws IOException
	 *            toutes les exceptions d'entrées/sorties.
	 */
	public int sendSize(int size) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
		buffer.putInt(size);
		buffer.flip();
		return rwChan.write(buffer);
	}
	
	/**
	 * Reçoit des données sur la connexion TCP jusqu'à la fermeture la connexion entrante ou le remplissage du buffer passé en paramètre.
	 * La méthode remplit le buffer à partir du premier octet de celui-ci, en écrasant éventuellement des données déjà présente.
	 * @param buffer
	 *             le buffer où stocker les données.
	 * @return
	 *             le nombre d'octets placés dans le buffer.
	 * @throws IOException
	 *            toutes les exceptions d'entrées/sorties.
	 */
	public int receiveBuffer(ByteBuffer buffer) throws IOException {
		buffer.clear();
		int buf;
		int sommes = 0;
		buf = rwChan.read(buffer);
		while(buf > 0){
			sommes =+ buf;
			buf = rwChan.read(buffer);
		}
		return sommes;

	}
	
	/**
	 * Reçoit un entier sur la connexion TCP.
	 * @return
	 *         la valeur de l'entier reçu.
	 * @throws IOException
	 *             le nombre d'octets placés dans le buffer.
	 */
	public int receiveSize() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
		rwChan.read(buffer);
		buffer.flip();
		return buffer.getInt();

	}
	
	
	/**
	 * Reçoit un objet sérialisable sur la connexion TCP.
	 * @return
	 *        la référence sur l'objet reçu.
	 * @throws IOException
	 *        toutes les exceptions d'entrées/sorties
	 * @throws ClassNotFoundException
	 *        l'exception levée si l'objet reçu est d'une classe inconnue de notre programme.
	 */
	public Serializable receiveObject() throws IOException, ClassNotFoundException {
		//recevoir un entier qui contient la taille de l'objet sérialisé ;
			int size = receiveSize();

		//allouer un ByteBuffer faisant exactement cette taille ;
			ByteBuffer buffer = ByteBuffer.allocate(size);

		//recevoir des octets du réseau jusqu'à remplir ce buffer ;
		receiveBuffer(buffer);


		//extraire les données de ce buffer et les désérialiser.
		Serializable reference = null;
		ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
		ObjectInputStream oi = new ObjectInputStream(bi);
		reference = (Serializable) oi.readObject();

		oi.close();
		bi.close();
		return reference;
	}
	
	@Override
	public void close() throws IOException {
		rwChan.close();
	}
	
	
	/**
	 * Renvoie tous les octets reçus sur la connexion TCP tant que celle-ci reste ouverte. 
	 * @return
	 *        le nombre total d'octets reçus.
	 * @throws IOException
	 *        toutes les exceptions d'entrées/sorties.
	 */
	public int echo() throws IOException {
		int totalReveice = 0;
		int receive;
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		receive = receiveBuffer(buffer);
		while (receive > 0){
			totalReveice += receive;
			receive = receiveBuffer(buffer);
		}
		return totalReveice;
	}
	
}
