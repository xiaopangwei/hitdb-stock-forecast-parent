package cn.hitdb.window.forecast.algorithm.feature.extraction.model;


import java.text.DecimalFormat;

/**
 * 自研矩阵
 */
public class JMatrix {
    /**
     * 二维表
     */
    private double[][] matrix;

    public JMatrix(){

    }
    public JMatrix(JMatrix matrix) {
        this.matrix = matrix.getMatrix();
    }



    public JMatrix(double[][] values) {
        this.matrix = values;
    }

    public double[][] getMatrix() {
        return this.matrix;
    }

    /**
     * 矩阵行数
     * @return
     */
    public int getRowCount() {
        return this.matrix.length;
    }

    /**
     * 矩阵列数
     * @return
     */
    public int getColumnCount() {
        return this.matrix[0].length;
    }

    /**
     * 矩阵转置
     * @return
     */
    public JMatrix transposeMatrix() {
        return new JMatrix(transpose());
    }

    /**
     * 矩阵转置
     * @return
     */
    private double[][] transpose() {
        int rows    = getRowCount();
        int columns = getColumnCount();

        double[][] transposedMatrix = new double[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }

        return transposedMatrix;
    }


    /**
     * 矩阵乘法
     * @param multiplicand 右边的矩阵
     * @return
     */
    private double[][] multiplicationMatrix(JMatrix multiplicand) {
        int multiplierRows    = getRowCount();
        int multiplierColumns = getColumnCount();

        int multiplicandRows    = multiplicand.getRowCount();
        int multiplicandColumns = multiplicand.getColumnCount();

        if (multiplierColumns != multiplicandRows) {
            throw new RuntimeException(String.format("Matrices inner dimensions need to be equal, found %s x [%s] and [%s] x %s",
                    multiplierRows, multiplierColumns, multiplicandRows, multiplicandColumns));
        }

        double[][] product = new double[multiplierRows][multiplicandColumns];
        double[][] r       = multiplicand.getMatrix();

        for (int i = 0; i < multiplierRows; i++) {
            for (int j = 0; j < multiplicandColumns; j++) {
                for (int k = 0; k < multiplierColumns; k++) {
                    product[i][j] += matrix[i][k] * r[k][j];
                }
            }
        }
        return product;
    }

    public JMatrix multiplyMatrix(JMatrix multiplicand) {
        return new JMatrix(multiplicationMatrix(multiplicand));
    }


    /**
     * 矩阵相减
     * @param b 右边的矩阵
     * @return
     */
    private double[][] subMatrix(JMatrix b) {
        int        rows    = getRowCount();
        int        columns = getColumnCount();
        double[][] sub     = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sub[i][j] = this.matrix[i][j] - b.getMatrix()[i][j];
            }
        }
        return sub;
    }


    /**
     * 矩阵相减
     * @param b
     * @return
     */
    public JMatrix subtractionMatrix(JMatrix b) {
        return new JMatrix(subMatrix(b));
    }


    /**
     * 矩阵打印
     * @param format
     */
    private void print(boolean format) {
        int columns = getColumnCount();
        for (double[] aMatrix : this.matrix) {
            for (int j = 0; j < columns; j++) {
                String val;
                if (format) {
                    val = new DecimalFormat("0.##").format(aMatrix[j]);
                } else {
                    val = aMatrix[j] + "";
                }
                while (val.length() < 6) {
                    val += " ";
                }

                System.out.print(val + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printMatrix() {
        print(false);
    }

    public void printMatrixTrim() {
        print(true);
    }

    /**
     * 创建一个零矩阵
     * @param vector
     * @param size
     */
    public void createZeroIdentityFromVal(double vector, int size) {
        diagMatrix(vector, size, 0);
    }

    /**
     * 创建一个对角线是vector，非对角线是val的方阵
     * @param vector 对角线上的元素
     * @param size 方阵大小
     * @param val 非对角线上的元素
     */
    private void diagMatrix(double vector, int size, double val) {
        double[][] identity = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    identity[i][j] = vector;
                } else {
                    identity[i][j] = val;
                }
            }
        }

        this.matrix = identity;
    }

    /**
     * 创建一个非对角线上全为0的对角阵
     * @param vector 对角线元素的集合
     * @param rows 行数
     * @param columns 列数
     */
    public void createZeroIdentityFromVector(double[] vector, int rows, int columns) {
        int        size     = vector.length;
        double[][] identity = new double[rows][columns];

        int index = size - 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == j) {
                    identity[j][j] = vector[index];
                    index--;
                } else {
                    identity[i][j] = 0;
                }
            }
        }
        this.matrix = identity;
    }

    public void createUnitMatrix(JMatrix US) {
        int        rows    = US.getRowCount();
        int        columns = US.getColumnCount();
        double[][] U       = new double[rows][rows];
        double[][] temp    = US.getMatrix();

        for (int i = 0; i < columns; i++) {
            double x = 0;

            for (int j = 0; j < rows; j++) {
                double y = Math.abs(temp[j][i]);
                x += y * y;
            }

            x = Math.sqrt(x);

            for (int j = 0; j < rows; j++) {
                U[j][i] = temp[j][i] / x;

            }

            for (int j = columns; j < rows; j++) {
                for (int k = columns; k < rows; k++) {
                    if (j != k & U[j][k] == 0) {
                        U[j][k] = 1;
                    }
                }
            }
        }

        this.matrix = U;
    }

    void convertToInt() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                this.matrix[i][j] = (int) Math.round(matrix[i][j]);
            }
        }
    }

    public static double[][] rankedMatrixS(JMatrix matrix, int rank) {
        double[][] rankedMatrix = new double[rank][rank];
        for (int i = 0; i < rank; i++) {
            double[] rowMatrix = matrix.getMatrix()[i];
            System.arraycopy(rowMatrix, 0, rankedMatrix[i], 0, rank);
        }
        return rankedMatrix;
    }

    public static double[][] rankedMatrixUV(JMatrix matrix, int rank) {
        int        rows         = matrix.getRowCount();
        double[][] rankedMatrix = new double[rows][rank];
        for (int i = 0; i < rows; i++) {
            double[] rowMatrix = matrix.getMatrix()[i];
            System.arraycopy(rowMatrix, 0, rankedMatrix[i], 0, rank);
        }
        return rankedMatrix;
    }


}
