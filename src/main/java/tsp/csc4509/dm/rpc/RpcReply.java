package tsp.csc4509.dm.rpc;
import static tsp.csc4509.dm.common.Log.COMM;
import static tsp.csc4509.dm.common.Log.LOG_ON;

import java.io.Serializable;

/**
 * La classe qui transmet la réponse au RPC du serveur vers le client.
 * @author Eric Lallet.
 *
 */
public class RpcReply implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * status du RPC (OK, mauvaise requête, ou erreur de calcul).
	 */
	private RpcStatus status;
	/**
	 * classe de l'objet sérialisable contenant le résultat à transmettre.
	 */
	private Class<? extends Serializable> resultClass;
	/**
	 * référence sur l'objet sérialisable contenant le résultat à transmettre.
	 */
	private Serializable result;
	
	
	/**
	 * Contructeur de la réponse au RPC.
	 * @param status
	 *              status du RPC (OK, mauvaise requête, ou erreur de calcul)
	 * @param resultClass
	 *              classe de l'objet sérialisable contenant le résultat à transmettre.
	 * @param result
	 *              référence sur l'objet sérialisable contenant le résultat à transmettre.
	 */
	public RpcReply(final RpcStatus status, final Class<? extends Serializable> resultClass,  final Serializable result) {
		if (LOG_ON && COMM.isInfoEnabled()) {
			COMM.info("RPC Reply: création d'une réponse au RPC avec le status " + status);
		}
		
		this.status = status;
		this.resultClass = resultClass;
		this.result = result;
	}

	/**
	 * getter sur le status du RPC.
	 * @return
	 *        status du RPC (OK, mauvaise requête, ou erreur de calcul)
	 */
	public RpcStatus getStatus() {
		return status;
	}

	/**
	 * getter sur la classe de l'objet sérialisable contenant le résultat à transmettre.
	 * @return
	 *        classe de l'objet sérialisable contenant le résultat à transmettre.
	 */
	public Class<? extends Serializable> getResultClass() {
		return resultClass;
	}

	/**
	 * getter sur la référence sur l'objet sérialisable contenant le résultat à transmettre.
	 * @return
	 *        référence sur l'objet sérialisable contenant le résultat à transmettre.
	 */
	public Serializable getResult() {
		return result;
	}


}
