package tsp.csc4509.dm.graphique;

import javax.swing.SwingUtilities;

public class AppliSwing {
	/**
	 * default server hostname.
	 */
	final static String DEFAULTHOST = "localhost";
	/**
	 * default tcp port number.
	 */
	final static int DEFAULTPORT = 4545;
	/**
	 * number of expected arguments of main method.
	 */
	private static final int NBARGS = 2;
	/**
	 * position of the hostname in argv[].
	 */
	private static  final int HOSTARG = 0;
	/**
	 * position of the port number in argv[].
	 */
	private static  final int PORTARG = 1;
	
	public static void main(String[] argv) {
		if (argv.length != NBARGS) {
			SwingUtilities.invokeLater(new RunnableSwing(DEFAULTHOST, DEFAULTPORT));
			return;
		}
		
		
		SwingUtilities.invokeLater(new RunnableSwing(argv[HOSTARG], Integer.parseInt(argv[PORTARG])));
	}

}
