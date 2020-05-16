package tsp.csc4509.dm.rpc;
import static tsp.csc4509.dm.common.Log.COMM;
import static tsp.csc4509.dm.common.Log.LOG_ON;

import java.io.IOException;
import java.io.Serializable;

import tsp.csc4509.dm.tcp.TcpSocket;

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
		// TODO Etape 3.2
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
		// TODO Etape 3.3
	}


	/**
	 * getter sur le résulat du RPC.
	 * @return
	 *        référence sur l'objet sérialisable contenant la réponse du RPC. null en cas d'erreur.
	 */
	public Serializable getResult() {
		// TODO Etape 3.2
		return null;
	}

}

