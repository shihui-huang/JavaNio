package tsp.csc4509.dm.rpc;

/**
 * Liste de status possible pour la réponse d'un RPC.
 * @author Eric Lallet.
 *
 */
public enum RpcStatus {
	/**
	 * la requête ok, et la réponse a pu être calculée.
	 */
	RPC_REPLY_OK("OK"),
	/**
	 *   la requête n'est pas correcte.
	 */
	RPC_REPLY_BAD_REQUEST("Bad Request"),
	/**
	 * la requête est correcte, mais le calcul s'est fini en erreur.
	 */
	RPC_REPLY_ERROR("Error");

	/**
	 * nom du status sous forme de chaîne.
	 */
	private String rpcStatusStr;
	
	/**
	 * 
	 * @param rpcStatusStr
	 *        le nom du status sous forme de chaîne.
	 */
	RpcStatus(final String rpcStatusStr) {
		this.rpcStatusStr = rpcStatusStr;
	}
	
	@Override
	public String toString() {
		return "Rpc status: " + rpcStatusStr;
	}
}
