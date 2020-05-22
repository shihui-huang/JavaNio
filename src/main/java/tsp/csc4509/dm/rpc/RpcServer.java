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

//		création d'un serveur TCP écoutant sur le port passé en paramètre
		TcpServer tcpServer = new TcpServer(port);

//		pour tous les futurs clients qui vont se connecter :
		while (true) {

//		accepter le client
			TcpSocket tcpSocket = tcpServer.acceptClient();

//		recevoir un objet de ce client
			Serializable receiveRequest = tcpSocket.receiveObject();

//		vérifier que c'est un objet RpcRequest
			if(receiveRequest instanceof RpcRequest){

//		retrouver la valeur du  RpcType à partir de l'« id » de cette requête.

				/*
                La requête ne contient pas directement la valeur du le RpcType.
                En effet, l'énumération RpcType appartient au code du serveur et le client n'a aucune raison de connaître cette liste a priori.
                Par convention, la chaîne de caractères contenu dans l'attribut « id » de la requête doit correspondre à la valeur de l'énumération.
                Pour transformer une chaîne de caractères en valeur de l'énumération, utilisez la méthode de classe Enum.valueOf() :
                « RpcType rpcType = Enum.valueOf(RpcType.class, idStr); » (où idStr est l'attribut id que vous avez extrait de la requête).
                 */


				String requestId = ((RpcRequest) receiveRequest).getId();
				RpcReply rpcReply;
				try {
					RpcType rpcType = Enum.valueOf(RpcType.class, requestId);
//		appeler la méthode qui a été associée au RpcType pour calculer le résultat du RPC
					RpcParam rpcParam = ((RpcRequest) receiveRequest).getParam();
					Class<? extends Serializable> resultClass;
					Serializable result;
					RpcStatus status;

					if (rpcParam == null) {
						status = RpcStatus.RPC_REPLY_OK;
						result = rpcType.getRpcSupplier().get();
						resultClass = rpcType.getResultClass();

					} else if(RpcParamList.isImplemented(rpcParam.getClass())){
						status = RpcStatus.RPC_REPLY_OK;
						result = rpcType.getRpcFunction().apply(rpcParam);    //rpcParam.compute()
						resultClass = rpcParam.getResultClass();

					}else {
						status = RpcStatus.RPC_REPLY_BAD_REQUEST;
						result = null;
						resultClass = null;

					}

//		créer un objet RpcReply avec le résultat de cette procédure
					rpcReply = new RpcReply(status, resultClass, result);
				}catch (IllegalArgumentException e) {
					/*
					Si un client envoie un « id » qui ne correspond à aucune valeur de l'énumération, cette instruction lève l'exception  IllegalArgumentException.
					Il ne faut pas stopper le serveur à cause des requêtes qui lèvent une exception.
					Vous devez donc attraper toutes les exceptions et renvoyer au client une « RpcReply » avec le status fixé à  RPC_REPLY_BAD_REQUEST
					 */

					rpcReply = new RpcReply(RpcStatus.RPC_REPLY_BAD_REQUEST, null, null);
				}

//		envoyer cet objet RpcReply au client
					tcpSocket.sendObject(rpcReply);

//		assurer la fermeture de la connexion avec ce client.
					tcpSocket.close();


			}
		}
	}


}
