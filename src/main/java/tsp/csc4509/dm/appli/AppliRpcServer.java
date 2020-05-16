package tsp.csc4509.dm.appli;

import tsp.csc4509.dm.rpc.RpcServer;

import java.io.IOException;

public class AppliRpcServer {
    /**
     * nombre d'arguments attendus par le main.
     */
    private static final int NBARGS = 1;
    /**
     * position du numéro du port dans argv[].
     */
    private static  final int PORTARG = 0;


    public static void main(String[] argv) throws IOException, ClassNotFoundException {

        if (argv.length != NBARGS) {
            System.out.println("usage: java AppliRpcServer <port>");
            return;
        }
        int port = Integer.parseInt(argv[PORTARG]);
        new RpcServer(port);
    }
}
