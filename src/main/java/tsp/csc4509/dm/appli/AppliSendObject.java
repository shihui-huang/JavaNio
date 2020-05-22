package tsp.csc4509.dm.appli;
import tsp.csc4509.dm.tcp.TcpSocket;
import java.io.IOException;

public class AppliSendObject {
    /**
     * nombre d'argument.
     */
    private static final int NBARGS = 2;
    /**
     * position du nom de la machine du serveur dans  argv[].
     */
    private static final int HOSTARG = 0;
    /**
     * position du port du serveur dans argv[].
     */
    private static final int PORTARG = 1;

    public static void main(final String[] argv) throws IOException {
        // Vérifier que le main reçoit bien 2 arguments
        if (argv.length != NBARGS) {
            System.out.println("usage: java AppliSendObject <machine> <port> <x> <y>");
            return;
        }
        // se connecter au serveur argv[0] = machine du seveur, argv[1] = port du serveur, argv[2] = x, argv[3] = y
        TcpSocket tcpSocket = new TcpSocket(argv[HOSTARG], Integer.parseInt(argv[PORTARG]));

        // envoyer un objet au serveur.
        Person mike = new Person("mike",20);
        tcpSocket.sendObject(mike);

        tcpSocket.close();
    }
}
