package tsp.csc4509.dm.graphique;

import javax.swing.JFrame;
import java.io.IOException;

/**
 * Une tâche envoyée au Thread de Swing.
 * Crée la fenêtre d'affichage et la rend visible
 * 
 * @author Éric Lallet.
 */
public class RunnableSwing implements Runnable {
	/**
	 * nom de la machine du serveur.
	 */
	private String hostname;
	
	/**
	 * numéro du port TCP du serveur.
	 */
	private int port;
	
	/**
	 * Constructeur qui transmet les informations sur le serveur RPC.
	 * @param hostname
	 *           nom de la machine du serveur.
	 * @param port
	 *           numéro du port TCP du serveur.
	 */
	public RunnableSwing(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	// méthode appelée par Swing pour déclencher cette tâche.
	@Override
	public void run() {
	        JFrame frame = new JFrame("Mandelbrot");
			WindowSwing panel = null;
			try {
				panel = new WindowSwing(hostname, port);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			frame.add(panel);
	        frame.pack();
	        frame.setVisible(true);
	        frame.setResizable(false);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}
