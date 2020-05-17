package tsp.csc4509.dm.graphique;

import java.awt.Color;

public class ColorieurSwing {
	private Color [][] couleurs;
	private int valeurMax;
	private int [][] valeurs;

	
	public ColorieurSwing(int [][] valeurs, int valeurMax) {
		int nbX = valeurs.length;
		int nbY = valeurs[0].length;
		couleurs = new Color [nbX][nbY];
		this.valeurMax = valeurMax;
		this.valeurs = valeurs;
	}
	
	
	
	public void colorier() {
		for (int indX = 0; indX < couleurs.length; indX++) {
			for (int indY = 0; indY < couleurs[0].length; indY++) {
				double val = valeurs[indX][indY];
				double teinte;
				double lum = 1;
				if (val < valeurMax/10) {
					teinte = Math.sin((val*Math.PI*40)/valeurMax);
				}
				else {
					teinte = Math.sin((val*0.9*Math.PI*6)/valeurMax);
				}
				if (val > 0.9*valeurMax) {
					lum = 10 - (10 * val) / valeurMax;
				}
				
			
				val = Math.sin((val*Math.PI*10)/valeurMax);
				couleurs[indX][indY] = Color.getHSBColor((float)teinte, 1 ,(float) lum );
			}
		}
	}
	
	
	public Color [][] getCouleurs() {
		return couleurs;
	}
	
	

}
