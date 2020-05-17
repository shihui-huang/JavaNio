package tsp.csc4509.dm.rpc;
import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;
import java.lang.management.ManagementFactory;


/**
 * Liste de RPC réalisables par le serveur.
 * @author Eric Lallet.
 *
 */
public enum RpcType {
	//******************************************************************************
	// liste des RPC sans paramètre: constructeur avec deux paramètres:
	//   le premier: la classe du résultat
	//   le second une instance de l'interface fonctionnelle  Supplier<Serializable> 
	
	// exemple avec une instance construite avec classe + new
	/**
	 * service qui renvoie la liste ici présente des RPC présente ici dans cette enum.
	 */
	LIST(String[].class, new RpcListSupplier()),
	
	// exemples avec instance construite une classe anonyme
	/**
	 * service qui renvoie la charge moyenne de la machine où tourne le serveur RCP.
	 */
	AVERAGELOAD(Double.class, new Supplier<Serializable>() {
		@Override
		public Serializable get() {
			return new Double(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
		}
	}),
	// exemples avec code de la méthode fournie par une expression lambda.
	/**
	 * service qui renvoie la liste des services implémentés pour le RPC COMPUTE.
	 */
	COMPUTELIST(String[].class, () -> (RpcParamList.getRpcParamList())),
	
	//******************************************************************************
	// RPC avec paramètre: constructeur avec un paramètre
	//   une instance de l'interface fonctionnelle Function<RpcParam, Serializable>
	/**
	 * service qui réalise un calcul avec résultat déporté sur le serveur RPC.
	 */
	COMPUTE((RpcParam p)  -> (p.compute()));
	
	
	
	/**
	 * référence sur l'instance implémentant la méthode Serializable get() pour
	 * le RPC sans paramètre. 
	 */
	private Supplier<Serializable> rpcSupplier = null;
	
	/**
	 * référence sur l'instance implémentant la méthode Serializable apply(RpcParam) pour
	 * le RPC avec paramètre. 
	 */
	private  Function<RpcParam, Serializable> rpcFunction = null;

	
	/**
	 * type du résultat pour les RPC sans paramètre. C'est le serveur qui fournit la méthode,
	 * le client ne connait pas le type de la résulat, il faut le placer dans la RpcReply.
	 */
	private Class<? extends Serializable> resultClass = null;
	
	/**
	 * Contructeur pour les RPC sans paramètre.
	 * @param resultClass
	 *                   le type du résultat du RPC.
	 * @param rpcSupplier
	 *                   la référence sur l'instance qui fournit la méthode du RPC (Serializable get())
	 */
	RpcType(final Class<? extends Serializable> resultClass, final Supplier<Serializable> rpcSupplier) {
		this.rpcSupplier = rpcSupplier;
		this.resultClass = resultClass;
		
		
	}
	
	/**
	 * Contructeur pour les RPC avec paramètre.
	 * @param rpcFunction
	 *                  la référence sur l'instance qui fournit la méthode du RPC (Serializable apply(RpcParam))
	 */
	RpcType(final Function<RpcParam, Serializable> rpcFunction) {
		this.rpcFunction = rpcFunction;
		
		
	}

	
	/**
	 * getter sur le type du résultat. Ne doit être utiliser que pour les RPC sans paramètre.
	 * pour les RPC avec paraètre, il faut utiliser la méthode RpcParam::getResultClass().
	 * @return
	 *        la classe du résultat du RPC.
	 */
	public Class<? extends Serializable> getResultClass() {
		return resultClass;
	}

	/**
	 * getter sur la réference de l'instance qui implémente la méthode "Serializable get()" pour les RPC sans paramètre.
	 * @return
	 *        la réference de l'instance qui implémente la méthode "Serializable get()" pour les RPC sans paramètre. null  pour les RPC avec paramètre.
	 */
	public Supplier<Serializable> getRpcSupplier() {
		
		return this.rpcSupplier;
		
	}
	
	/**
	 * getter sur la réference de l'instance qui implémente la méthode "Serializable apply(RpcParam)" pour les RPC avec paramètre.
	 * @return
	 *         la réference de l'instance qui implémente la méthode "Serializable apply(RpcParam)" pour les RPC avec paramètre. null pour les RPC sans paramètre.
	 */
	public Function<RpcParam, Serializable> getRpcFunction() {
		return rpcFunction;
	}


}
