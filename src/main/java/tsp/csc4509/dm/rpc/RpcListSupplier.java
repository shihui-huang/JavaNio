package tsp.csc4509.dm.rpc;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Implémentation de l'interface fonctionnelle Supplier<Serializable> pour le
 * cas du RPC "LIST".
 * @author Eric Lallet.
 *
 */
public class RpcListSupplier implements Supplier<Serializable> {

	@Override
	public Serializable[] get() {
		RpcType[] rpcList = RpcType.values();
		String[] rpcListStr = new String[rpcList.length];
		for (int ind = 0; ind < rpcList.length; ind++) {
			rpcListStr[ind] = rpcList[ind].toString();
		}
		return rpcListStr;
	}

}
