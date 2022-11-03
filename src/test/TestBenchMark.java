/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import jazari.matrix.CMatrix;
import jazari.types.TMatrixCell;

/**
 *
 * @author cezerilab
 */
public class TestBenchMark {

    public static void main(String[] args) {
        List<String> lst=new ArrayList();
//        test_1(3,4096);
//        test_multiply_matrices();
        test_find_max_min(10, 4096);
    }

    private static void test_multiply_matrices() {
        CMatrix cm_1 = CMatrix.getInstance().range(4).reshape(2, 2).println();
        CMatrix cm_2 = CMatrix.getInstance().range(4).reshape(2, 2).println();
        CMatrix cm_3 = cm_1.mul(cm_2).println();
        CMatrix cm_4 = cm_1.dot(cm_2).println();
    }

    private static void test_1(int n, int size) {
        CMatrix cm_1 = CMatrix.getInstance().randn(size, size).mul(100).round();
        CMatrix cm_2 = CMatrix.getInstance().randn(size, size).mul(100).round();
        CMatrix cm_3 = cm_1.dot(cm_2);
        long t = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long cmt = System.currentTimeMillis();
                CMatrix cm1 = CMatrix.getInstance().randn(size, size).multiplyScalar(100).round();
                CMatrix cm2 = CMatrix.getInstance().randn(size, size).multiplyScalar(100).round();
                CMatrix cm3 = cm1.mul(cm2);
                System.out.println((i * n + j) + ". loop cmatrix cost = " + (System.currentTimeMillis() - cmt) + " ms");
            }
            System.gc();
        }
        System.out.println("\naverage overall cost = " + (1.0 * (System.currentTimeMillis() - t) / (n * n)) + " ms");
    }

    private static void test_find_max_min(int n, int size) {
        CMatrix.getInstance().randn(size, size).mul(10000).round().max();
//        float f=cmx.argMax();
//        float val=cmx.getArray1Dfloat()[(int)f];
//        System.out.println("val = " + val);
//        System.out.println("f = " + f);
//        System.out.println("max="+cmx.max());
        long t = System.currentTimeMillis();
        CMatrix cm = CMatrix.getInstance().randn(size, size).mul(10000).round();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long cmt = System.currentTimeMillis();
//                CMatrix.getInstance().randn(size, size).mul(10000).round().max();
//                CMatrix.getInstance().randn(size, size).mul(10000).round().min();
//                float max=cm.argMax();
//                float min=cm.argMin();
//                CMatrix cm = CMatrix.getInstance().randn(size, size).mul(10000).round();
                TMatrixCell mc_max = cm.max();
                TMatrixCell mc_min = cm.min();
                System.out.println((i * n + j) + ". loop cmatrix cost = " + (System.currentTimeMillis() - cmt) + " ms " + mc_max + " " + mc_min);
            }
            System.gc();
        }
        System.out.println("\naverage overall cost = " + (1.0 * (System.currentTimeMillis() - t) / (n * n)) + " ms");
    }

}
