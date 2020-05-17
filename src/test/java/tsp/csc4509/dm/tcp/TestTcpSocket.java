//CHECKSTYLE:OFF
package tsp.csc4509.dm.tcp;

import static org.junit.Assert.assertTrue;
import static tsp.csc4509.common.Log.LOGGER_NAME_TEST;
import static tsp.csc4509.common.Log.LOG_ON;
import static tsp.csc4509.common.Log.TEST;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Level;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import tsp.csc4509.common.Log;
import tsp.csc4509.tcpservices.TcpTestTools;

public class TestTcpSocket {
	private static final int BUFFERSIZE = 1024;
	
	static private TcpTestTools tcpTestTools;
	
	@BeforeClass
	static public void lanceTestTools() {
		tcpTestTools = new TcpTestTools();
		Log.configureALogger(LOGGER_NAME_TEST, Level.ALL);
	}
	
	// test unitaire de la connxion d'un client
	@Test
	public void testTcpSocketClient() throws Exception {
		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("(TestTcpSocket::testTcpSocketClient] ->  test de création d'un client");
		}
		// lancement d'un serveur de test  TCP
		tcpTestTools.serverStart(40001);
		
		// on laisse 5 secondes pour que la connexion s'établisse.
		tcpTestTools.alarmStart(5000);
		
		// connexion du client
		TcpSocket client = new TcpSocket("localhost", 40001);
		// si aucune exception n'est levé, le test OK.
		
		client.close();
		tcpTestTools.alarmStop();
		tcpTestTools.serverStop();
		

	}
	
	// test unitaire de la méthode sendBuffer()
	// principe du test:
	// On demande à la bibliothèque de test d'ouvrir un serveur qui va sauver tout ce qu'il reçoit dans un fichier.
	// On connecte notre client à tester.
	// On envoie tout le contenu d'un fichier au serveur.
	// On compare le fichier créé par le serveur avec le fichier émis par le client.
	@Test
	public void testSendBuffer() throws Exception {
		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("[TestTcpSocket::testSendBuffer] -> test d'envoie d'un message avec un client");
		}
		
		// lancement d'un serveur de test TCP
		tcpTestTools.serverReceiveFileStart(40001, "./tmp/recu.txt");
		
		// création d'un client
		TcpSocket client = new TcpSocket("localhost", 40001);
		
		FileInputStream fin = new FileInputStream("./data/fichierTemoin.txt");
		FileChannel fcin = fin.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BUFFERSIZE);
		int lu; 
		do {
			buffer.clear();
			lu = fcin.read(buffer);
//			buffer.flip();
			client.sendBuffer(buffer);
		} while (lu == BUFFERSIZE);
		client.close(); // attention, la lecture de l'autre coté ne débloque que
		                // si le buffer est plein, ou la connexion fermée.
		                // il faut donc fermer le client pour que le serveur termine sa lecture
		                // et sauve la fin du fichier.
		               
		Thread.sleep(1000); // on laisse un peu de temps au serveur.
		
		tcpTestTools.serverReceiveFileStop();
		fin.close();
		
		assertTrue("Données reçus identiques aux données envoyées", compareFile("./tmp/recu.txt", "./data/fichierTemoin.txt"));
	}


	// test unitaire de la méthode receiveBuffer()
	// On compare  le fichier émis par le client et le ficher recu par serveur.
	@Test
	public void testReceiveBuffer() throws Exception {
		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("[TestTcpSocket::testReceiveBuffer] -> test de recoit d'un message avec un client");
		}

		// lancement d'un serveur de test TCP
		tcpTestTools.serverSendFileStart(40001, "./data/fichierTemoin.txt");

		// création d'un client
		TcpSocket client = new TcpSocket("localhost", 40001);

		FileOutputStream fout = new FileOutputStream("./tmp/envoye.txt");
		FileChannel fcout = fout.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BUFFERSIZE);

		//receive fichier
		int write;
		do {
			buffer.clear();
			client.receiveBuffer(buffer);
			buffer.flip();
			write = fcout.write(buffer);
		} while (write == BUFFERSIZE);

		client.close();

		Thread.sleep(1000); // on laisse un peu de temps au serveur.

		tcpTestTools.serverSendFileStop();
		fout.close();

		assertTrue("Données reçus identiques aux données envoyées", compareFile("./tmp/envoye.txt", "./data/fichierTemoin.txt"));


	}
	
	
	// test unitaire de la méthode sendObject()
	// principe du test:
	// On demande à la bibliothèque de test d'ouvrir un serveur qui va sauver tout ce qu'il reçoit dans un fichier.
	// On connecte notre client à tester.
	// On crée un objet à envoyer (ici une chaine)
	// On envoie l'objet au serveur.
	// On lit la trame reçue par le serveur dans le fichier
	// On en extrait l'objet serialisé, on désérialise l'objet.
	// On compare l'objet reçu avec l'objet émis
	@Test
	public void testSendObject() throws Exception {

		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("[TestTcpSocket::testSendObject] ->  test d'envoi d'un objet avec un client");
		}
		
		// lancement d'un serveur de test TCP
		tcpTestTools.serverReceiveFileStart(40001, "./tmp/objet.data");
		
		
		// création d'un client
		TcpSocket client = new TcpSocket("localhost", 40001);
		
		// création d'un objet:
		String str = "L'objet de test sera cette chaîne de caractères";
		
		// test unitaire de la méthode sendObject()
		client.sendObject(str);
		client.close();
		Thread.sleep(1000); // on laisse un peu de temps au serveur.
		tcpTestTools.serverReceiveFileStop();
		
		// l'objet serialisé doit être arrivé dans le fichier du serveur
		FileInputStream fin = new FileInputStream("./tmp/objet.data");
		FileChannel fcin = fin.getChannel();
		
		 // lecture de la taille en début de trame
		ByteBuffer bufferSize = ByteBuffer.allocate(Integer.SIZE/ Byte.SIZE);
		fcin.read(bufferSize);
		bufferSize.flip();
		int size = bufferSize.getInt();
		
		// lecture de l'objet sérialisé
		ByteBuffer buffer = ByteBuffer.allocate(size);
		fcin.read(buffer);
		buffer.flip();
		
		// désérialisation de l'objet
		ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
		ObjectInputStream oi = new ObjectInputStream(bi);
		String strRecu = (String) oi.readObject();
		oi.close();
		bi.close();
		fin.close();
			
		assertTrue("Objet reçu identique à l'objet envoyé", str.equals(strRecu));
	}



	// test unitaire de la méthode receiveObject()
	// On compare l'objet reçu avec l'objet émis
	@Test
	public void testReceiveObject() throws Exception {

		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("[TestTcpSocket::testReveiceObject] ->  test de réception d'un objet avec un client");
		}
		// création d'un objet:
		String str = "L'objet de test sera cette chaîne de caractères";

		FileOutputStream fout = new FileOutputStream("./tmp/objet.data");
		FileChannel fcout = fout.getChannel();

		// Sérialisation de l'objet
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(str);
		oo.close();

		// send size
		ByteBuffer bufferSize = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
		bufferSize.putInt(bo.size());
		bufferSize.flip();
		fcout.write(bufferSize);

		// send buffer
		ByteBuffer buffer = ByteBuffer.allocate(bo.size());
		buffer.put(bo.toByteArray());
		buffer.flip();
		fcout.write(buffer);

		bo.close();
		fout.close();
		fout.close();

		// lancement d'un serveur de test TCP
		tcpTestTools.serverSendFileStart(40001, "./tmp/objet.data");

		// création d'un client
		TcpSocket client = new TcpSocket("localhost", 40001);


		// test unitaire de la méthode receiveObject()
		String strRecev = (String) client.receiveObject();
		client.close();
		Thread.sleep(1000); // on laisse un peu de temps au serveur.
		tcpTestTools.serverSendFileStop();


		assertTrue("Objet reçu identique à l'objet envoyé", str.equals(strRecev));
		
	}
	
	
	
	/**
	 * Compare le contenu de deux fichiers et retourne true s'ils sont identiques.
	 * @param fileName1 
	 * 					nom du premier fichier.
	 * @param fileName2
	 * 					nom du second fichier.
	 * @return
	 * 					true si les deux fichiers ont le même contenu, false sinon.
	 * @throws Exception
	 * 					toutes les exceptions possibles.
	 */
	private static  boolean compareFile(String fileName1, String fileName2) throws Exception {
		FileInputStream fin1 = new FileInputStream(fileName1);
		FileInputStream fin2 = new FileInputStream(fileName2);
		FileChannel fcin1 = fin1.getChannel();
		FileChannel fcin2 = fin2.getChannel();
		ByteBuffer buffer1 = ByteBuffer.allocate(BUFFERSIZE);
		ByteBuffer buffer2 = ByteBuffer.allocate(BUFFERSIZE);
		int lu1, lu2;
		do {
			buffer1.clear();
			buffer2.clear();
			lu1 = fcin1.read(buffer1);
			lu2 = fcin2.read(buffer2);
			if (lu1 != lu2) {
				return false;
			}
			buffer1.flip();
			buffer2.flip();
			for (int ind=0; ind < lu1; ind++) {
				if (buffer1.get() != buffer2.get()) {
					return false;
				}
			}
			
		} while (lu1 == BUFFERSIZE);
		
		return true;
	}

	// Ce test supose que la classe TcpServeur fonctionne
	// et que le méthode TcpSocket.sendBuffer() fonctionne
	@Test
	public void testReceiveBufferParMorceaux() throws Exception {
		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("[TestTcpSocket::testReceiveBufferParMorceaux] -> test de la méthode receiveBuffer() pour une réception morcellée");
		}


		// lancement d'un serveur qui envoie une information coupée en trois morceaux
		// pour simuler le rique de morcellage d'un envoi sur TCP
		Thread serveur = new Thread(new Runnable() {
			@Override
			public void run() {
				try (
						TcpServer serveur = new TcpServer(40001);
						TcpSocket client = serveur.acceptClient();
				) {
					ByteBuffer buffer = ByteBuffer.allocate(BUFFERSIZE);
					buffer.put("Un".getBytes());

					client.sendBuffer(buffer);
					Thread.sleep(1000);
					buffer.clear();
					buffer.put("Deux".getBytes());

					client.sendBuffer(buffer);
					Thread.sleep(1000);
					buffer.clear();
					buffer.put("Trois".getBytes());

					client.sendBuffer(buffer);
					Thread.sleep(1000);

				} catch (Exception e) {
					return;
				}

			}

		});

		serveur.start();

		// attente du démarage du serveur
		Thread.sleep(1000);

		// création d'un client
		TcpSocket client = new TcpSocket("localhost", 40001);

		// création d'un buffer assez grand pour contenir tout ce que le serveur envoie
		ByteBuffer buffer = ByteBuffer.allocate(BUFFERSIZE);

		// réception des envois du serveur
		client.receiveBuffer(buffer);
		client.close();
		if (serveur.isAlive()) {
			serveur.interrupt();
			serveur.join();
		}
		buffer.flip();
		String str = StandardCharsets.UTF_8.decode(buffer).toString();
		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("[TestTcpSocket::testReceiveBufferParMorceaux] -> chaîne reçue:" + str);
		}

		assertTrue("Chaîne reçue contient tout", str.contentEquals("UnDeuxTrois"));
	}


}
