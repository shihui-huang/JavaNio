package tsp.csc4509.dm.mandelbrot;

import java.io.Serializable;

/**
 * Fonctions de base pour les complexes.
 * @author Eric Lallet.
 *
 */
public class Complexe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * partie réelle.
	 */
	private double zr;
	/**
	 * partie imaginaire.
	 */
	private double zi;
	
	/**
	 * Contruction d'un complexe à partir de deux réels.
	 * @param zr
	 *           partie réelle.
	 * @param zi
	 *           partie imaginaire.
	 */
	public Complexe(double zr, double zi) {
		this.zr = zr;
		this.zi = zi;
	}
	
	
	/**
	 * getter sur la partie réelle du complexe.
	 * @return
	 *        la partie réelle du complexe.
	 */
	public double valeurReelle() {
		return zr;
	}
	
	/**
	 * getter sur la partie imaginaire du complexe.
	 * @return
	 *        la partie imaginaire du complexe.
	 */
	public double valeurImaginaire() {
		return zi;
	}
	
	/**
	 * Calcule le module au carré du complexe.
	 * @return
	 *        le module au carré du complexe.
	 */
	public double moduleCarre() {
		return zr * zr + zi * zi;
	}

	/**
	 * Calcule la valeur du complexe multiplié par un autre complexe.
	 * @param facteur
	 *               le facteur de la muliplication.
	 * @return
	 *        la référence sur un nouveau complexe valant le complexe multiplié par le facteur.
	 */
	public Complexe multiplier(Complexe facteur) {
		double zrMul = zr * facteur.zr - zi * facteur.zi;
		double ziMul = zr * facteur.zi  + zi * facteur.zr;
		return new Complexe(zrMul, ziMul);
	}
	
	/**
	 * Calcule le carré du complexe.
	 * @return
	 *         la référence sur un nouveau complexe valant le complexe au carré.
	 */
	public Complexe carre() {
		return multiplier(this);
	}
	
	/**
	 * Calcule la valeur du complexe additionné d'un autre complexe.
	 * @param operande
	 *                l'opérande de l'addition.
	 * @return
	 *         la référence sur un nouveau complexe valant le complexe additionné avec l'opérande.
	 */
	public Complexe additionner(Complexe operande) {
		double zrAdd = zr + operande.zr;
		double ziAdd = zi + operande.zi;
		return new Complexe(zrAdd, ziAdd);
	}
	
	@Override
	public String toString() {
		return "( " + zr + " + " + zi + " i )";
	}
	
}
