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
        //diag sorunu
        //CMatrix ret = CMatrix.getInstance().range(0, 5).replicateColumn(5).println();
        CMatrix cm = CMatrix.getInstance().diag("1:11:2").println();
        
        
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
