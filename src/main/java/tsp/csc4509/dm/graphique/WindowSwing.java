package tsp.csc4509.dm.graphique;

import static tsp.csc4509.dm.graphique.ImagesPredefiniesSwing.IMAGES;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import tsp.csc4509.dm.mandelbrot.CoordMandelbrot;
import tsp.csc4509.dm.mandelbrot.Mandelbrot;

/**
 * Classe qui calcul et affiche l'image de Mandelbrot.
 * @author Eric Lallet.
 *
 */
public class WindowSwing extends JPanel {

	private final static int NBSECTIONS = 5; 
	
    private BufferedImage canvas;

    /**
     * Contructeur de Panel
     * @param hostname
     *        nom de la machine du serveur RPC.
     * @param port
     *        numéro du port TCP du serveur RPC.
     */
    public WindowSwing(String hostname, int port) {
    	// une image au hasard parmi les images prédéfinies
    	int indImage = (int) (Math.random() * IMAGES.length); 
    	
    	// on divise le calcul de l'image en plusieurs sections.
    	int nbSections = NBSECTIONS;
    	
    	// tableau pour contenir chaque couleur de chaque point (coordX x coordY) de chaque section.
    	// Color[section][coordX][coordY]
    	Color [][][] couleursSections = new  Color [nbSections][][];
    	
    	// on divise le calcul en nbSection 
		for (int num = 0; num < nbSections; num++) {
			CoordMandelbrot coordSection = diviserCoor(IMAGES[indImage], nbSections, num);
			
			// ************************************************************
			// Ici se trouve le calcul à déporter sur le serveur RPC
		    Mandelbrot mandelbrotSection = new Mandelbrot(coordSection);
			//*************************************************************
			
			// transformation des entiers de chaque point de l'espace en couleur.
			ColorieurSwing colorieur = new ColorieurSwing(mandelbrotSection.getValeurs(), mandelbrotSection.getMaxIterations());	
			colorieur.colorier();
			couleursSections[num] = colorieur.getCouleurs();
		}
    	
        canvas = new BufferedImage(IMAGES[indImage].getNbX(), IMAGES[indImage].getNbY(), BufferedImage.TYPE_INT_ARGB);
        int delatX = IMAGES[indImage].getNbX() / nbSections;
        for(int pixelX = 0 ; pixelX <IMAGES[indImage].getNbX(); pixelX++) {
			int indColx = pixelX % delatX;
			int indColSec = pixelX / delatX;
			for (int pixelY = 0; pixelY < IMAGES[indImage].getNbY() ; pixelY++) {
				
				canvas.setRGB( pixelX, pixelY, couleursSections[indColSec][indColx][pixelY].getRGB());
			}
		}
    }

    
    // redéfinition de la méthode pour fixer la taille de fenêtre de l'application à la taille de ce JPanel.
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    // méthode appelée par Swing lorsqu'il veut afficher ce Panel.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }


    /**
	 * Coupe une sous section de l'espace de Mandelbrot à calculer. Chaque section est une bande verticale de l'espace.
	 * @param coord
	 *             coordonnée de la section complète.
	 * @param nbSections
	 *             nombre de sous sections.
	 * @param num
	 *             numéro de la sous section du résultat (0 = première à gauche, nbSection-1 = dernière à droite).
	 * @return
	 *             la sous section numéro num.
	 * @throws IllegalArgumentException
	 */
	private CoordMandelbrot diviserCoor(CoordMandelbrot coord, int nbSections, int num) throws IllegalArgumentException{
		if ((num < 0) || (num >= nbSections)) {
			throw new IllegalArgumentException();
		}
		double deltaX = (coord.getX2() - coord.getX1()) / nbSections;
		 double x1 = coord.getX1() + num * deltaX; 
		 double y1 = coord.getY1();;
		 double x2 = x1 + deltaX;
		 double y2 = coord.getY2();
		 int nbX = coord.getNbX() / nbSections;
		int nbY = coord.getNbY();
		 int nbIterrations = coord.getMaxIterrations();
		 return new CoordMandelbrot(x1, y1, x2, y2, nbX, nbY, nbIterrations);
		 
	}

   

}