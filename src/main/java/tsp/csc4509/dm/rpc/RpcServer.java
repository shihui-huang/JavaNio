package tsp.csc4509.dm.rpc;


import tsp.csc4509.dm.tcp.TcpServer;
import tsp.csc4509.dm.tcp.TcpSocket;

import java.io.IOException;
import java.io.Serializable;


/**
 * Implémentation du serveur RPC.
 * @author Eric Lallet.
 *
 */
public class RpcServer {

	/**
	 * Constructeur du RpcServeur: réalise tout le serveur (création du serveur TCP, réception des requêtes, et envoi des réponses).
	 * @param port
	 *            numéro du port TCP occupé par le serveur.
	 * @throws IOException
	 *           toutes les exceptions d'entrées/sorties.
	 */
	public RpcServer(final int port) throws IOException, ClassNotFoundException {

//		création d'un serveur TCP écoutant sur le port passé en paramètre ;
		TcpServer tcpServer = new TcpServer(port);

//		pour tous les futurs clients qui vont se connecter :
		while (true) {

//		accepter le client ;
			TcpSocket tcpSocket = tcpServer.acceptClient();

//		recevoir un objet de ce client ;
			Serializable receiveRequest = tcpSocket.receiveObject();

//		vérifier que c'est un objet RpcRequest ;
			if(receiveRequest instanceof  RpcRequest){

//		appeler la procédure transportée dans le  RpcParam ;
				RpcParam rpcParam = ((RpcRequest) receiveRequest).getParam();
				Class<? extends Serializable> resultClass = rpcParam.getResultClass();
				Serializable result = rpcParam.compute();

//		créer un objet RpcReply avec le résultat de cette procédure ;
				RpcReply rpcReply = new RpcReply(RpcStatus.RPC_REPLY_OK, resultClass, result);

//		envoyer cet objet RpcReply au client ;
				tcpSocket.sendObject(rpcReply);

//		assurer la fermeture de la connexion avec ce client.
				tcpSocket.close();

			}
		}
	}


}
