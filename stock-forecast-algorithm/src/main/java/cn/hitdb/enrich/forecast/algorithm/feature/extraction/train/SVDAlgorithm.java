package cn.hitdb.enrich.forecast.algorithm.feature.extraction.train;

import cn.hitdb.enrich.core.algorithm.Predictable;
import cn.hitdb.enrich.core.model.ml.FeatureVector;
import cn.hitdb.enrich.forecast.algorithm.feature.extraction.model.JMatrix;
import cn.hitdb.enrich.forecast.algorithm.feature.extraction.model.SingularValueDecompositionConfig;

import java.util.ArrayList;
import java.util.List;

public class SVDAlgorithm implements Predictable<FeatureVector>{
    private SingularValueDecompositionConfig config;
    private List<FeatureVector> inputData;
    public SVDAlgorithm(SingularValueDecompositionConfig config, List<FeatureVector> vectorList)
    {
        this.config=config;
        this.inputData=vectorList;
    }

    private void svdCommented(double[][] twoByTwo) {
        JMatrix JMatrix   = new JMatrix(twoByTwo);
        JMatrix transpose = JMatrix.transposeMatrix();
        JMatrix square    = transpose.multiplyMatrix(JMatrix);

        System.out.println("util.JMatrix: Original");
        JMatrix.printMatrixTrim();

        System.out.println("util.JMatrix: Transposed");
        transpose.printMatrixTrim();

        System.out.println("util.JMatrix: Square");
        square.printMatrixTrim();

        Jama.EigenvalueDecomposition r = new Jama.EigenvalueDecomposition(new Jama.Matrix(square.getMatrix()));

        List<JMatrix> eigenMatrices = new ArrayList<>();

        for (double root : r.getRealEigenvalues()) {
            JMatrix eigenMatrix = new JMatrix();
            eigenMatrix.createZeroIdentityFromVal(root, r.getRealEigenvalues().length);
            eigenMatrix = square.subtractionMatrix(eigenMatrix);
            eigenMatrices.add(eigenMatrix);
        }

        MatrixDecomposition svd = new MatrixDecomposition();

        svd.createMiddleMatrix(r.getRealEigenvalues(), JMatrix.getRowCount(), JMatrix.getColumnCount());
        JMatrix S = new JMatrix(svd.getMiddleMatrix());
        System.out.println("util.JMatrix: S");
        S.printMatrixTrim();

        svd.createRightMatrix(eigenMatrices);
        JMatrix V = new JMatrix(svd.getRightMatrix());
        System.out.println("util.JMatrix: V");
        V.printMatrixTrim();

        svd.createLeftMatrix(JMatrix);
        JMatrix U = new JMatrix(svd.getLeftMatrix());
        System.out.println("util.JMatrix: U");
        U.printMatrixTrim();
    }
    @Override
    public void open() {

    }

    @Override
    public void handle() {

    }

    @Override
    public void close() {

    }
}
