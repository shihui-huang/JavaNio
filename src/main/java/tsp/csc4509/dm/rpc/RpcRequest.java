package tsp.csc4509.dm.rpc;
import static tsp.csc4509.dm.common.Log.COMM;
import static tsp.csc4509.dm.common.Log.LOG_ON;

import java.io.Serializable;


/**
 * La classe qui transmet la demande Rpc du client vers le serveur.
 * @author Eric Lallet.
 *
 */
public class RpcRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le nom de Rpc que l'on veut déclencher.
	 */
	private String id;
	/**
	 * référence sur l'objet qui transmet la méthode à déclencher et ses paramètres nécessaires.
	 */
	private RpcParam param;
	/**
	 * classe de l'objet référencé par param.
	 */
	private Class<? extends RpcParam> paramClass;
	
	
	/**
	 * Contructeur pour les requêtes avec paramètre.
	 * @param id
	 *          le nom du RPC demandé.
	 * @param paramClass
	 *          la classe de l'objet transportant la méthode à déclencher et ses paramètres.
	 * @param param
	 *          référence sur l'objet transportant la méthode à déclencher et ses paramètres.
	 */
	public RpcRequest(final String id, final Class<? extends RpcParam> paramClass, final RpcParam param) {
		if (LOG_ON && COMM.isInfoEnabled()) {
			COMM.info("RPC Request: création d'un RPC Request avec paramètre pour " + id);
		}
		this.id = id;
		this.paramClass = paramClass;
		this.param = param;
		
		
	}

	/**
	 * Constucteur pour les requêtes sans paramètre.
	 * @param id
	 *          le nom du RPC demandé.
	 */
	public RpcRequest(final String id) {
		if (LOG_ON && COMM.isInfoEnabled()) {
			COMM.info("RPC Request: création d'un RPC Request sans paramètre pour " + id);
		}
		this.id = id;
		this.paramClass = null;
		this.param = null;
		
		
	}


	/**
	 * getter sur le nom du RPC demandé.
	 * @return
	 *         le nom du RPC demandé.
	 */
	public String getId() {
		return id;
	}


	/**
	 * getter sur la référence de l'objet transportant la méthode à déclencher et ses paramètres.
	 * @return
	 *         la référence de l'objet transportant la méthode à déclencher et ses paramètres.
	 */
	public RpcParam getParam() {
		return param;
	}


	/**
	 * getter sur la classe de l'objet transportant la méthode à déclencher et ses paramètres.
	 * @return
	 *        la classe de l'objet transportant la méthode à déclencher et ses paramètres.
	 */
	public Class<? extends RpcParam> getParamClass() {
		return paramClass;
	}

	

	

	
}
