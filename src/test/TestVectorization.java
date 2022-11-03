/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Arrays;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestVectorization {
    public static void main(String[] args) {
        testArray1DBuild();
    }

    private static void testArray1DBuild() {
        int[] d1=CMatrix.intArray1D(100);
        System.out.println("d1 = " + Arrays.toString(d1));
        
        int[] d2=CMatrix.intArray1D(100, 45);
        System.out.println("d2 = " + Arrays.toString(d2));
        
        int[] d3=CMatrix.intArray1D(-10, 10, 3);
        System.out.println("d3 = " + Arrays.toString(d3));
        
    }
}
