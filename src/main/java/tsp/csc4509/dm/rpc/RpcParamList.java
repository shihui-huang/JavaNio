package tsp.csc4509.dm.rpc;


import tsp.csc4509.dm.rpcparam.AdditionParam;
import tsp.csc4509.dm.rpcparam.MandelbrotParam;
import tsp.csc4509.dm.rpcparam.MultiplicationParam;
import tsp.csc4509.dm.rpcparam.MultiplicationComplexeParam;

/**
 * Listes des services implémentés pour le RPC "COMPUTE".
 * @author Eric Lallet.
 *
 */
public enum RpcParamList {
	/**
	 * Addition de deux entiers.
	 */
	ADDITION(AdditionParam.class, "Addition de deux entiers"),
	/**
	 * Multilication de deux entiers.
	 */
	MULTIPLICATION(MultiplicationParam.class, "Multiplication de deux entiers"),
	/**
	 * Multiplication de deux complexes.
	 */
	MULTIPLICATIONCOMPLEXE(MultiplicationComplexeParam.class, "Multiplication de deux complexes"),
	/**
	 * Mandelbrot.
	 */
	MANDELBROT(MandelbrotParam.class, "mandelbrot");


	/**
	 * Classe transportant les paramêtres du RCP COMPUTE.
	 */
	private Class<? extends RpcParam> rpcParamaclass;
	
	/**
	 * Description du services implémenté.
	 */
	private String description;
	
	
	/**
	 * 
	 * @param rpcParamaclass
	 *        Classe transportant les paramêtres du RCP COMPUTE.
	 * @param description
	 *        Description du services implémenté.
	 */
	RpcParamList(final Class<? extends RpcParam> rpcParamaclass, final String description) {
		this.rpcParamaclass = rpcParamaclass;
		this.description = description;
	}
	
	/**
	 * Remplit le tableau contenant la liste des services connus pour le RPC COMPUTE.
	 * @return
	 *       tableau de chaîne de caractère contenant la liste des services connus pour le RPC COMPUTE.
	 */
	public static String[] getRpcParamList() {
		RpcParamList[] values = RpcParamList.values();
		String[] strRpcParamList = new String[values.length];
		
		for (int ind = 0; ind < values.length; ind++) {
			strRpcParamList[ind] = values[ind] + " (" + values[ind].rpcParamaclass + ", " + values[ind].description + ")";
		}
		
		return strRpcParamList;
	}
	
	/**
	 * Indique si le calcul demandé par le  RPC COMPUTE fait parti des calculs implémentés par le serveur.
	 * @param objectClass
	 *           le paramètre du RPC compute décrivant le calcul à réaliser.
	 * @return
	 *        true si le calcul est implémenté, false sinon.
	 */
	public static boolean isImplemented(final Class<? extends RpcParam> objectClass) {
		for (RpcParamList value : RpcParamList.values()) {
			if (value.rpcParamaclass.equals(objectClass)) {
				return true;
			}
		}
		return false;
	}

}
