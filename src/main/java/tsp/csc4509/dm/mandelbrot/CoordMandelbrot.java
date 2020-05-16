package tsp.csc4509.dm.mandelbrot;

import java.io.Serializable;

/**
 * Fonctions pour le cadrillage du rectangle couvert par l'image de Mandelbrot.
 * @author Eric Lallet.
 *
 */
public class CoordMandelbrot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * abcisse du point haut gauche.
	 */
	private double x1; 
	/**
	 * ordonnée du point haut gauche.
	 */
	private double y1;
	/**
	 * abcisse du point bas droit.
	 */
	private double x2;
	/**
	 * ordonnée du point bas droit.
	 */
	private double y2;
	/**
	 * nombre de points sur l'axe horizontal.
	 */
	private int nbX;
	/**
	 * nombre de points sur l'axe vertical.
	 */
	private int nbY;
	/**
	 * nombre d'itérations à faire sur un point.
	 */
	private int maxIterrations;
	
	/**
	 * @param x1
	 *           abcisse du point haut gauche.
	 * @param y1
	 *           ordonnée du point haut gauche.
	 * @param x2
	 *          abcisse du point bas droit.
	 * @param y2
	 *          ordonnée du point bas droit.
	 * @param nbX
	 *           nombre de points sur l'axe horizontal.
	 * @param nbY
	 *           nombre de points sur l'axe vertical.
	 * @param maxIterrations
	 *          nombre d'itérations à faire sur un point.
	 */
	public CoordMandelbrot(double x1, double y1, double x2, double y2, int nbX, int nbY, int maxIterrations) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.nbX = nbX;
		this.nbY = nbY;
		this.maxIterrations = maxIterrations;
	}
	
	/**
	 * 
	 * @return
	 *         abcisse du point haut gauche.
	 */
	public double getX1() {
		return x1;
	}

	/**
	 * 
	 * @param x1
	 *         abcisse du point haut gauche.
	 */
	public void setX1(double x1) {
		this.x1 = x1;
	}

	/**
	 * 
	 * @return
	 *         ordonnée du point haut gauche.
	 */
	public double getY1() {
		return y1;
	}

	/**
	 * 
	 * @param y1
	 *          ordonnée du point haut gauche.
	 */
	public void setY1(double y1) {
		this.y1 = y1;
	}

	/**
	 * 
	 * @return
	 *         abcisse du point bas droit.
	 */
	public double getX2() {
		return x2;
	}

	/**
	 * 
	 * @param x2
	 *          abcisse du point bas droit.
	 */
	public void setX2(double x2) {
		this.x2 = x2;
	}

	/**
	 * 
	 * @return
	 *         ordonnée du point bas droit.
	 */
	public double getY2() {
		return y2;
	}

	/**
	 * 
	 * @param y2
	 *         ordonnée du point bas droit.
	 */
	public void setY2(double y2) {
		this.y2 = y2;
	}

	/**
	 * 
	 * @return
	 *         nombre de points sur l'axe horizontal.
	 */
	public int getNbX() {
		return nbX;
	}

	/**
	 * 
	 * @param nbX
	 *          nombre de points sur l'axe horizontal.
	 */
	public void setNbX(int nbX) {
		this.nbX = nbX;
	}

	/**
	 * 
	 * @return
	 *         nombre de points sur l'axe vertical.
	 */
	public int getNbY() {
		return nbY;
	}

	/**
	 * 
	 * @param nbY
	 *          nombre de points sur l'axe vertical.
	 */
	public void setNbY(int nbY) {
		this.nbY = nbY;
	}

	/**
	 * 
	 * @return
	 *        nombre d'itérations à faire sur un point.
	 */
	public int getMaxIterrations() {
		return maxIterrations;
	}

	/**
	 * 
	 * @param nbIterrations
	 *        nombre d'itérations à faire sur un point.
	 */
	public void setMaxIterrations(int nbIterrations) {
		this.maxIterrations = nbIterrations;
	}

	

}
