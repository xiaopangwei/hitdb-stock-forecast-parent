package cn.hitdb.window.forecast.algorithm.feature.extraction.train;

import cn.hitdb.window.forecast.algorithm.feature.extraction.model.JMatrix;

import java.util.List;

public class MatrixDecomposition {
    private double[][] leftMatrix;
    private double[][] middleMatrix;
    private double[][] rightMatrix;

    public MatrixDecomposition(){

    }
    public MatrixDecomposition(double[][] leftMatrix, double[][] middleMatrix, double[][] rightMatrix) {
        this.leftMatrix = leftMatrix;
        this.middleMatrix = middleMatrix;
        this.rightMatrix = rightMatrix;

    }

    public MatrixDecomposition(JMatrix leftMatrix, JMatrix middleMatrix, JMatrix rightMatrix) {
        this.leftMatrix = leftMatrix.getMatrix();
        this.middleMatrix = middleMatrix.getMatrix();
        this.rightMatrix = rightMatrix.getMatrix();

    }

    public JMatrix multiply(JMatrix... matrices) {
        JMatrix result = matrices[0];
        for (int i = 1; i < matrices.length; i++) {
            result = result.multiplyMatrix(matrices[i]);
        }
        return result;
    }

    public void createMiddleMatrix(double[] eigenValues, int rows, int columns) {
        for (int i = 0, eigenLength = eigenValues.length; i < eigenLength; i++) {
            eigenValues[i] = Math.sqrt(eigenValues[i]);
        }

        JMatrix matrixS = new JMatrix();
        matrixS.createZeroIdentityFromVector(eigenValues, rows, columns);
        middleMatrix = matrixS.getMatrix();
    }

    public void createRightMatrix(List<JMatrix> eigenMatrices) {
        JMatrix V = new JMatrix(
                new Jama.Matrix(eigenMatrices
                        .get(0)
                        .getMatrix())
                        .eig()
                        .getV()
                        .getArray());

        double[][] e = V.getMatrix();
        int size = e.length;
        for (int i = 0; i < e.length; i++) {
            double[] temp = e[i];
            e[i] = e[size - 1];
            e[size - 1] = temp;
        }

        this.rightMatrix= new JMatrix(e).transposeMatrix().getMatrix();
    }

    public void createLeftMatrix(JMatrix matrix) {
        JMatrix US = new JMatrix(matrix.multiplyMatrix(new JMatrix(this.rightMatrix)));

        JMatrix U = new JMatrix();
        U.createUnitMatrix(US);

        this.leftMatrix= U.getMatrix();
    }



    public double[][] getLeftMatrix() {
        return leftMatrix;
    }

    public void setLeftMatrix(double[][] leftMatrix) {
        this.leftMatrix = leftMatrix;
    }

    public double[][] getMiddleMatrix() {
        return middleMatrix;
    }

    public void setMiddleMatrix(double[][] middleMatrix) {
        this.middleMatrix = middleMatrix;
    }

    public double[][] getRightMatrix() {
        return rightMatrix;
    }

    public void setRightMatrix(double[][] rightMatrix) {
        this.rightMatrix = rightMatrix;
    }
}
