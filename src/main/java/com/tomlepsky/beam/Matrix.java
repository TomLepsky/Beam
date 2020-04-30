package com.tomlepsky.beam;

public class Matrix {

    public static double[] cramer(double[][] a, double[] b) {
        if (a.length != a[0].length)
            throw new ArithmeticException("Wrong matrix dimension");

        double det = determinant(a);
        if (det == 0.0)
            throw new ArithmeticException("Determinant is 0!");

        double[] x = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            double[][] temp = arrayCopy(a);
            for (int j = 0; j < a.length; j++) {
                temp[j][i] = b[j];
            }
            x[i] = determinant(temp) / det;
        }

        return x;
    }


    public static double determinant(double[][] matrix) {
        if (matrix.length != matrix[0].length)
            throw new ArithmeticException("Wrong matrix dimension");

        if (matrix.length == 2)
            return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];

        double det = 0.0;
        for (int i = 0; i < matrix.length; i++) {
            det += Math.pow(-1, i) * matrix[0][i] * determinant(minor(matrix, 0, i));
        }

        return det;
    }


    public static double[][] minor(double[][] matrix, int row, int col) {
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];

        int offsetCol = 0;
        int offsetRow = 0;
        for (int i = 0; i < minor.length; i++) {
            if (i >= row)
                offsetRow = 1;

            for (int j = 0; j < minor.length; j++) {
                if (j >= col)
                    offsetCol = 1;
                minor[i][j] = matrix[i + offsetRow][j + offsetCol];
            }
            offsetCol = 0;
        }

        return minor;
    }

    private static double[][] arrayCopy(double[][] copyFrom) {
        double[][] arr = new double[copyFrom.length][];
        for (int i = 0; i < copyFrom.length; i++) {
            arr[i] = new  double[copyFrom[i].length];
            for (int j = 0; j < copyFrom[i].length; j++) {
                arr[i][j] = copyFrom[i][j];
            }
        }
        return arr;
    }

}
