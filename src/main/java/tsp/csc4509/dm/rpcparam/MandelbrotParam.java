package tsp.csc4509.dm.rpcparam;
import java.io.Serializable;

import tsp.csc4509.dm.mandelbrot.CoordMandelbrot;
import tsp.csc4509.dm.mandelbrot.Mandelbrot;
import tsp.csc4509.dm.rpc.RpcParam;
public class MandelbrotParam implements Serializable,RpcParam {
    /**
     *
     */
    private static final long serialVersionUID = 5L;
    private final CoordMandelbrot coordSection;
    public MandelbrotParam(CoordMandelbrot coordSection){
        this.coordSection = coordSection;
    }

    @Override
    public Class<? extends Serializable> getResultClass() {
        return MandelbrotParam.class;
    }

    @Override
    public Serializable compute() {
        return new Mandelbrot(coordSection);
    }
}
