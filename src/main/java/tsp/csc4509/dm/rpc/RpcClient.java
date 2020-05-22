package tsp.csc4509.dm.rpc;

import tsp.csc4509.dm.tcp.TcpSocket;

import java.io.IOException;
import java.io.Serializable;

/**
 * Implémentation du client RPC.
 * @author Eric Lallet.
 *
 */
public class RpcClient {

	/**
	 * rérérence sur  sur l'objet transportant la méthode à  déclencher et ses paramètres.
	 */
	private RpcParam rpcParam;

	/**
	 * rérérence sur  sur  l'objet transportant la réponse au RPC.
	 */
	private RpcReply rpcReply;

	/**
	 * Contructeur pour un RPC avec paramètre: établit le connexion TCP, envoie la requête, et reçoit la réponse.
	 * @param serverHost
	 *                  nom de la machine où tourne le serveur.
	 * @param serverPort
	 *                  numéro du port TCP du serveur.
	 * @param rpcId
	 *                  nom du RPC que l'on veut appeler.
	 * @param rpcParamClass
	 *                  classe de l'objet transportant la méthode à  déclencher et ses paramètres.
	 * @param rpcParam
	 *                  rérérence sur  sur l'objet transportant la méthode à  déclencher et ses paramètres.
	 * @throws IOException
	 *                  toutes de exceptions d'entrées/sorties
	 * @throws ClassNotFoundException
	 *                  l'exception sur le serveur RPC ne nous envoie pas un RpcReply. 
	 */
	public RpcClient(final String serverHost, final int serverPort, final String rpcId, final Class<? extends RpcParam> rpcParamClass, 
			final RpcParam rpcParam) throws IOException, ClassNotFoundException {

		this.rpcParam = rpcParam;

//	    connexion TCP au serveur « serverHost:serverPort »
		TcpSocket tcpSocket = new TcpSocket(serverHost, serverPort);

//		construction d'un objet RpcRequest en utilisant les paramètres du constructeur
		RpcRequest rpcRequest = new RpcRequest(rpcId, rpcParamClass, rpcParam);

//		envoi de cet objet au serveur
		tcpSocket.sendObject(rpcRequest);

//		réception de la réponse, qui est un objet de la classe  RpcReply
	    rpcReply = (RpcReply) tcpSocket.receiveObject();

//		fermeture de la connexion avec le serveur.
		tcpSocket.close();

	}


	/**
	 * Contructeur pour un RPC sans paramètre: établit le connexion TCP, envoie la requête, et reçoit la réponse.
	 * @param serverHost
	 *                  nom de la machine où tourne le serveur.
	 * @param serverPort
	 *                  numéro du port TCP du serveur.
	 * @param rpcId
	 *                  nom du RPC que l'on veut appeler.
	 * @throws IOException
	 *                  toutes de exceptions d'entrées/sorties
	 * @throws ClassNotFoundException
	 *                  l'exception sur le serveur RPC ne nous envoie pas un RpcReply. 
	 */
	public RpcClient(final String serverHost, final int serverPort, final String rpcId) throws IOException, ClassNotFoundException {

//	    connexion TCP au serveur « serverHost:serverPort »
		TcpSocket tcpSocket = new TcpSocket(serverHost, serverPort);

//		construction d'un objet RpcRequest en utilisant les paramètres du constructeur
		RpcRequest rpcRequest = new RpcRequest(rpcId);

//		envoi de cet objet au serveur
		tcpSocket.sendObject(rpcRequest);

//		réception de la réponse, qui est un objet de la classe  RpcReply
		rpcReply = (RpcReply) tcpSocket.receiveObject();

//		fermeture de la connexion avec le serveur.
		tcpSocket.close();
	}


	/**
	 * getter sur le résulat du RPC.
	 * @return
	 *        référence sur l'objet sérialisable contenant la réponse du RPC. null en cas d'erreur.
	 */
	public Serializable getResult() {
		RpcStatus status = this.rpcReply.getStatus();
		return status== RpcStatus.RPC_REPLY_OK ? this.rpcReply.getResult() : null;
	}

}

