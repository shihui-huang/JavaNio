package tsp.csc4509.dm.appli;

import tsp.csc4509.dm.mandelbrot.Complexe;
import tsp.csc4509.dm.rpc.RpcClient;
import tsp.csc4509.dm.rpc.RpcParam;
import tsp.csc4509.dm.rpcparam.AdditionParam;
import tsp.csc4509.dm.rpc.RpcReply;
import tsp.csc4509.dm.rpcparam.MultiplicationComplexeParam;

import java.io.IOException;
import java.io.Serializable;

public class AppliRpcClient {
        /**
         * nombre d'arguments attendus par le main.
         */
        private static final int NBARGS = 7;
        /**
         * position du nom de la machine du serveur dans  argv[].
         */
        private static final int HOSTARG = 0;
        /**
         * position du port du serveur dans argv[].
         */
        private static final int PORTARG = 1;
        /**
         * position du port du type du param dans argv[].
         */
        private static final int PARAMARG = 2;

    public static void main(final String[] argv) throws IOException, ClassNotFoundException {
        // Vérifier que le main reçoit bien 4 arguments
        if (argv.length < 4) {
            System.out.println("usage: java AppliRpcClient <machine> <port> <param> <val1> <val2> (<val3> <val4>)");
            System.out.println("param: add or multipli. add: val1 + val2. multipli: (val1 + i val2) * (val3 + i val4)");
            return;
        }

        String serverHost = argv[HOSTARG];
        int serverPort = Integer.parseInt(argv[PORTARG]);
        String param = argv[PARAMARG];

        switch (param) {
            case "add" :
                int valInt1 = Integer.parseInt(argv[3]);
                int valInt2 = Integer.parseInt(argv[4]);
                AdditionParam additionParam = new AdditionParam(valInt1, valInt2);

                RpcClient rpcClientAdd = new RpcClient(serverHost, serverPort, "Add", (Class<? extends RpcParam>) additionParam.getResultClass(), additionParam);
                Serializable resultAdd = rpcClientAdd.getResult();
                System.out.println(resultAdd);
                break;

            case "multipli":
                if (argv.length != NBARGS) {
                    System.out.println("usage: java AppliRpcClient <machine> <port> <param> <val1> <val2> (<val3> <val4>)");
                    System.out.println("param: add or multipli. add: val1 + val2. multipli: (val1 + i val2) * (val3 + i val4)");
                    return;
                }
                double valReal1 = Double.parseDouble(argv[3]);
                double valImagin1 = Double.parseDouble(argv[4]);
                double valReal2 = Double.parseDouble(argv[5]);
                double valImagin2 = Double.parseDouble(argv[6]);
                Complexe complex1 = new Complexe(valReal1,valImagin1);
                Complexe complexe2 = new Complexe(valReal2,valImagin2);
                MultiplicationComplexeParam multipliParam = new MultiplicationComplexeParam (complex1,complexe2);

                RpcClient rpcClientMultipli = new RpcClient(serverHost, serverPort, "Multipli", (Class<? extends RpcParam>) multipliParam.getResultClass(), multipliParam);
                Serializable resultMultipli = rpcClientMultipli.getResult();
                System.out.println(resultMultipli);
                break;
            default:
                System.out.println("param: add or multipli. add: val1 + val2. multipli: (val1 + i val2) * (val3 + i val4)");
                return;
        }
    }
}
