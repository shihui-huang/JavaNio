package tsp.csc4509.dm.mandelbrot;

import java.io.Serializable;


public class Mandelbrot implements Serializable {

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
	private int maxIterations = 100;
	
	/**
	 * nombre d'itérations avant divergence en un point (limité par  maxIterations).
	 */
	private int [][] valeurs;


	/**
	 * Constructeur à partir des coordonnées détailles.
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
	public Mandelbrot(double x1,  double y1, double x2, double y2, int nbX, int nbY, 
			 int maxIteration ) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.nbX = nbX;
		this.nbY = nbY;
		this.maxIterations = maxIteration;
		this.valeurs = new int[nbX][nbY];
		
		calculValeurs();
		
	}
	
	/**
	 * Constucteur à partir d'une réference sur un CoordMandelbrot.
	 * @param coord
	 *            la référence sur une instance de CoordMandelbrot.
	 */
	public Mandelbrot(CoordMandelbrot coord ) {
		this.x1 = coord.getX1();
		this.x2 = coord.getX2();
		this.y1 = coord.getY1();
		this.y2 = coord.getY2();
		this.nbX = coord.getNbX();
		this.nbY = coord.getNbY();
		this.maxIterations = coord.getMaxIterrations();
		this.valeurs = new int[nbX][nbY];
	
		
		
		calculValeurs();
		
	}
		
	/**
	 * remplit toute les cases du tableau valeurs en calculant le nombre d'itération avant divergence.
	 */
	public void calculValeurs( ) {
		
		
		double deltaX = (x2 - x1) / nbX;
		double deltaY = (y2 - y1) / nbY;
		
		double y ;
		double x = x1 ;
		int val;
		for (int indX = 0; indX < nbX ; indX ++) {
			y = y1 ;
			for (int indY = 0; indY < nbY ; indY++) {
				Complexe point = new Complexe(x, y);
				val = trouverDivergence(point);
				
				valeurs[indX][indY] = val; 
				y += deltaY;
			}
			x += deltaX;
		}
		
	}
	
			
	/**
	 * getter sur le tableau valeurs.
	 * @return
	 *        le tableau valeurs qui contient le nombre d'itérations avant divergence pour chaque point.
	 */
	public int [] [] getValeurs() {
		return valeurs;
	}
	
	
	
	/**
	 * Calcule le nombre d'itérations avant divergence pour le point passé en paramètre.
	 * @param point
	 *             le point du plan complexe pour lequel le calcul de divergence doit être fait.
	 *            
	 * @return
	 *        le nombre d'itérations avant divergence pour le point passé en paramètre (limité par maxIteration).
	 *      
	 */
	public int trouverDivergence(Complexe point) {
		int nbInterations = 0;
		Complexe valeur = new Complexe(0, 0);
		
		while ((valeur.moduleCarre() < 4) && (nbInterations < maxIterations-1)) {
			valeur = valeur.carre().additionner(point);
			nbInterations++;
		}
		return nbInterations;
	}
	
	/**
	 * getter sur le nombre maximum d'itérations.
	 * @return
	 *        le nombre maximum d'itérations.
	 */
	public int getMaxIterations() {
		return maxIterations;
	}
	
	
}
