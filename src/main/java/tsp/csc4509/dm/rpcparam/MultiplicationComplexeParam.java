package tsp.csc4509.dm.rpcparam;

import tsp.csc4509.dm.mandelbrot.Complexe;
import tsp.csc4509.dm.rpc.RpcParam;
import java.io.Serializable;

public class MultiplicationComplexeParam implements Serializable,RpcParam{
    /**
     *
     */
    private static final long serialVersionUID = 5L;
    private final Complexe complexe1;
    private final Complexe complexe2;

    public MultiplicationComplexeParam(final Complexe complexe1, final Complexe complexe2) {
        this.complexe1 = complexe1;
        this.complexe2 = complexe2;
    }

    @Override
    public Class<? extends Serializable> getResultClass() {
        return Integer.class;
    }

    @Override
    public Serializable compute() {
        return complexe1.multiplier(complexe2);
    }




}
