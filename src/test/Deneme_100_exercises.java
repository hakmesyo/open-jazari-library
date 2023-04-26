/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Point;
import java.util.Arrays;
import jazari.matrix.CMatrix;
import jazari.matrix.CPoint;
import jazari.types.TMatrixOperator;

/**
 *
 * @author cezerilab
 */
public class Deneme_100_exercises {

    public static void main(String[] args) {
               CMatrix cm = CMatrix.getInstance()
                    .ones(16)
                    .setValue("1:17:2", "0:16:2", 0)
                    .setValue("0:16:2", "1:17:2", 0)
                    .resize(40)
                    .map(0, 255)
                    .imshow()
                    .println()
               ;

        
        
        
//        //Q24- How to stack two matrices vertically?
//        CMatrix cm1 = CMatrix.getInstance().rand(5,2).dump();
//        CMatrix cm2 = CMatrix.getInstance().rand(3,2).dump();
//        CMatrix cm = cm1.clone().catFirst(2,cm2).println();
        
        
//        //Q23- How to reshape a matrix of any shape to new shape?
//        CMatrix cm = CMatrix.getInstance()
//                //.range(0, 20)
//                .ones(20,1)
//                .println()
//                .reshape(1,2,5,2,1)
//                .println()
//                ;
        
//        //Q22- How to add a border (filled with 0's) around an existing array?
//        CMatrix cm = CMatrix.getInstance().zeros(10, 10);
//        cm.clone().setValue("2:-2", "2:-2", 1).println().map(0, 255).resize(50).imshow();
        
//        //*Q21- Find indices of non-zero elements from [1,2,0,0,4,0]
//        CMatrix cm = CMatrix.getInstance(1, 2, 0, 0, 4, 0).println();
//        cm.clone(cm).nonZeroIndices().println();
//        cm.clone(cm).nonZeroValues().println();

        //CMatrix cm = CMatrix.getInstance().rand(5, 1).println().replicate(3,2).println();
//        //Q19- Extract the integer part of a random array using 4 different methods.
//        //System.out.println(Math.floor(-58.0768));
//        CMatrix cm = CMatrix.getInstance().rand(5, 3, 0, 100).println();
//        
//        cm.clone().trunc().println();
//        cm.clone().floor().println();
//        cm.clone().round().println();
        //error Q17- Given a random 1D array with size 11 in range 0..10, negate all elements which are between 3 and 8, in place. 
//        CMatrix cm = CMatrix.getInstance()
//                .rand(1, 11,0,10)
//                .println()
//                ;
//        CMatrix cm_index = cm.clone().findIndex(TMatrixOperator.BETWEEN, 3,8).dump();
//        cm.clone().negate(cm_index).dump();
//        //CMatrix cm1 = cm.clone().negate().dump();
        //Q16_Extended- Multiply (dot product) a 5x3 matrix by a 5x3 matrix.
//        CMatrix cm = CMatrix.getInstance()
//                .rand(5,3)
//                .dump()
//                .dotProduct(CMatrix.getInstance().rand(5,3).dump())
//                .dump()
//                ;
//        //Q16- Multiply a 5x3 matrix by a 3x2 matrix (real matrix dot) .
//        CMatrix cm1 = CMatrix.getInstance().rand(5, 2).dump();
//        CMatrix cm2 = CMatrix.getInstance().rand(2, 2).dump();
//        CMatrix cm = cm1.dot(cm2).dump();
//        //Q15- Normalize as min-max a 5x5 random matrix [0..100] range.
//        CMatrix cm = CMatrix.getInstance()
//                .rand(5,1,-100,100)
//                .replicateColumn(5)
//                .dump();
//        CMatrix cm1 = cm.clone()
//                .normalizeMinMax()
//                .dump() ;
//        CMatrix cm2 = cm.clone()
//                .map(0, 1)
//                .dump() ;
//        //Round a 5x5 random matrix from double to int for [0..100] range
//        
//        CMatrix cm = CMatrix.getInstance()
//                .rand(5, 5)
//                .map(0, 100)
//                .println()
//                .round()
//                .println()
//                ;
//        int[][] d=cm.toIntArray2D();
//        System.out.println(Arrays.toString(d[0]));
        //Build a diagonal 5x5 matrix with values 0,1,2,3,4
        //CMatrix ret = CMatrix.getInstance().range(0, 5).replicateColumn(5).println();
        //CMatrix cm = CMatrix.getInstance().diag("1:11:2").println();
        //Build a diagonal 5x5 matrix with values 0,1,2,3,4
//        //1.yöntem geleneksel Java yaklaşımı
//        float[][] d=new float[5][5];
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                d[i][i]=i;
//            }
//        }
//        CMatrix.getInstance(d).println();
        //2.yöntem OJL yaklaşımı
        // CMatrix.getInstance().diag(5).println();
//       CMatrix cm = CMatrix.getInstance()
//                    .zeros(8)
//                    .setValue("1:9:2", "0:8:2", 1)
//                    .setValue("0:8:2", "1:9:2", 1)
//                    .map(0, 255)
//                    .resize(80)
//                    //.imshow()
//                    .println()
//               ;
        //3.yöntem tile approach
        //atomic tile pattern
//        CMatrix cm_atomic = CMatrix.getInstance(new int[][]{{0,1},{1,0}});
//        CMatrix cm = cm_atomic.clone().replicateColumn(3).println().replicateRow(3).println();
//        //Build a 2d array [10x10] with 1 on the border and 0 inside
//        //CMatrix cm = CMatrix.getInstance().rand(10, 10, -10, 10).round().println();
//        //CMatrix cm_index = cm.clone().findIndex(TMatrixOperator.SMALLER_EQUALS, -2).println();
//        CMatrix cm = CMatrix.getInstance().ones(10,10).println();
//        cm.clone().setValue("1:-1", "1:-1", 0).println();
//        //System.out.println(Arrays.toString(cm_index.toFloatArray1D()));
//        //How to add a border (filled with 0's) around an existing array?
//        CMatrix cm = CMatrix.getInstance().ones(5, 5).padarray(1, 1, 0).println();
//        //Find indices of non-zero elements from [1,2,0,0,4,0]
//        CMatrix cm = CMatrix.getInstance(1,2,0,0,4,0)                
//                .println()                 
//                ;
//        CMatrix.getInstance().clone(cm).println();
//        CMatrix.getInstance(1,2,0,0,4,0).clone().nonZeroIndices().println();
//        CMatrix.getInstance(1,2,0,0,4,0).clone().nonZeroValues().println();
//        
////        CMatrix cm_index = cm.clone().findIndex(TMatrixOperator.NOT_EQUALS, 0).println();
////        CMatrix cm_values = cm.clone().findItemsByIndex(cm_index).println();
    }
}
