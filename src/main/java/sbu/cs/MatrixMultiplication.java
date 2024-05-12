package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> tempMatrixProduct = new ArrayList<>();
        List<List<Integer>> firstMatrix;
        List<List<Integer>> secondMatrix;
        int startRow;
        int endRow;
        int startColumn;
        int endColumn;
        int q;

        public BlockMultiplier(List<List<Integer>> firstMatrix, List<List<Integer>> secondMatrix, int startRow, int endRow, int startColumn, int endColumn, int q) {
            this.firstMatrix = firstMatrix;
            this.secondMatrix = secondMatrix;
            this.startRow = startRow;
            this.endRow = endRow;
            this.startColumn = startColumn;
            this.endColumn = endColumn;
            this.q = q;
        }

        @Override
        public void run() {
            for (int i = startRow; i < endRow; i++) {
                for (int j = startColumn; j < endColumn; j++) {
                    int sum = 0;
                    for (int k = 0; k < q; k++) {
                        sum += firstMatrix.get(i).get(k) * secondMatrix.get(k).get(j);
                    }
                    tempMatrixProduct.get(i).set(j, sum);
                }
            }

        }
    }

    /*
    Matrix A is of the form p x q
    Matrix B is of the form q x r
    both p and r are even numbers
    */

    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B)
    {
        int p = matrix_A.size();
        int q = matrix_B.size();
        int r = matrix_B.getFirst().size();

        BlockMultiplier quarter1 = new BlockMultiplier(matrix_A, matrix_B, 0, p / 2, 0, r / 2, q);
        BlockMultiplier quarter2 = new BlockMultiplier(matrix_A, matrix_B, 0, p / 2, r / 2, r, q);
        BlockMultiplier quarter3 = new BlockMultiplier(matrix_A, matrix_B, p / 2, p, 0, r / 2, q);
        BlockMultiplier quarter4 = new BlockMultiplier(matrix_A, matrix_B, p / 2, p, r / 2, r, q);

        Thread task1 = new Thread(quarter1);
        Thread task2 = new Thread(quarter2);
        Thread task3 = new Thread(quarter3);
        Thread task4 = new Thread(quarter4);

        task1.start();
        task2.start();
        task3.start();
        task4.start();

        try {
            task1.join();
            task2.join();
            task3.join();
            task4.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < p / 2; i++) {
            quarter1.tempMatrixProduct.get(i).addAll(quarter2.tempMatrixProduct.get(i));
            quarter3.tempMatrixProduct.get(i).addAll(quarter4.tempMatrixProduct.get(i));
        }
        quarter1.tempMatrixProduct.addAll(quarter3.tempMatrixProduct);

        return quarter1.tempMatrixProduct;
    }

    public static void main(String[] args) {

    }
}
