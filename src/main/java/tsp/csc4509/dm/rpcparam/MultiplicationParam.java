package tsp.csc4509.dm.rpcparam;
import java.io.Serializable;

import tsp.csc4509.dm.rpc.RpcParam;

public class MultiplicationParam implements Serializable, RpcParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	
	private Integer val1, val2;
	
	public MultiplicationParam(Integer val1, Integer val2) {
		this.val1 = val1;
		this.val2 = val2;
	}

	

	@Override
	public Class<? extends Serializable> getResultClass() {
		return Integer.class;
	}

	@Override
	public Serializable compute() {
		System.out.println("Version 1");
		return new Integer(val1 * val2);
	}
	
	
	
	

}
