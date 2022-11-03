/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;
import java.util.Arrays;
import jazari.types.TMatrixOperator;

/**
 *
 * @author cezerilab
 */
public class TestFindIndexes {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .rand(14,13,0,30)
                .round()
                .dump();
        float[] d=cm.toFloatArray1D();
        System.out.println("d = " + Arrays.toString(d));
        CMatrix cm_index = cm
                .findIndex(TMatrixOperator.BETWEEN, 15,20)
                .dump()                
                ;
        cm=cm.getMatrixValueByIndex(cm_index.toIntArray1D()).dump();
        
        float[][] f={{1,2,3},{4,5,6}};
        CMatrix cmx = CMatrix.getInstance(f).dump()
                ;
        
        
//        CMatrix cm = CMatrix.getInstance()
//                .imread("images/cezeri_logo.bmp")
//                .rgb2gray()
//                .imshow()
//                .println()
//                .findIndex(TMatrixOperator.EQUALS, 0)
//                .println()
//                ;
//        int[] d=cm.toIntArray1D();
//        int[] satirlar=new int[d.length];
//        int[] sutunlar=new int[d.length];
//        for (int i = 0; i < d.length; i++) {
//            satirlar[i]=d[i]/84;
//            sutunlar[i]=d[i]%84;
//        }
//        System.out.println(Arrays.toString(satirlar));
//        System.out.println(Arrays.toString(sutunlar));
//        System.out.println(1894/84);
//        System.out.println(1894%84);
        
    }
    
}
