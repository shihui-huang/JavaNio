package tsp.csc4509.dm.appli;
import tsp.csc4509.dm.tcp.TcpServer;
import tsp.csc4509.dm.tcp.TcpSocket;


public class AppliReceiveObject {
    /**
     * nombre d'arguments attendus par le main.
     */
    private static final int NBARGS = 1;
    /**
     * position du numéro du port dans argv[].
     */
    private static final int PORTARG = 0;


    public static void main(final String[] argv) throws Exception {
        // Vérifier que le main reçoit bien 1 arguments.
        if (argv.length != NBARGS) {
            System.out.println("usage: java AppliReceiveObject <port>");
            return;
        }

        // créer un serveur TCP en attente sur le port contenu dans argv[0].
        TcpServer tcpServer = new TcpServer(Integer.parseInt(argv[PORTARG]));

        // accepter un client.
        TcpSocket tcpSocket = tcpServer.acceptClient();

        Person person = (Person) tcpSocket.receiveObject();
        System.out.println(person.getName());

        tcpSocket.close();
        tcpServer.close();
    }
}
