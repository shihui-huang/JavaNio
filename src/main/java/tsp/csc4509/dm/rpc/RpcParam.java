package tsp.csc4509.dm.rpc;
import java.io.Serializable;

/**
 * Interface des objets transmis en paramètre dans les RpcQuest.
 * @author Eric Lallet.
 *
 */
public interface RpcParam {
	/**
	 * Getter sur la classe de l'objet sérialisable qui contient le résultat du RPC.
	 * @return
	 *        La classe du résultat attendu pour le RPC.
	 */
	Class<? extends Serializable> getResultClass();
	
	/**
	 * Méthode qui implémente le calcul que le RPC doit réaliser.
	 * @return
	 *        la référence sur l'objet sérialisable qui contiendra le résultat du RPC.
	 */
	Serializable compute();
}
