package co.adrimol.quasar.servicios;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService {

    public double[] getUbicacion(double[][] posiciones, double[] distancias) {

        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(posiciones, distancias);
        NonLinearLeastSquaresSolver nSolver = new NonLinearLeastSquaresSolver(trilaterationFunction,
                new LevenbergMarquardtOptimizer());

        return nSolver.solve().getPoint().toArray();
    }
}
