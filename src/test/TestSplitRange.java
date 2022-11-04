/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestSplitRange {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .range2D(0, 10, 2, 20)
                .println()
                
                ;
        CMatrix[] cms = cm.clone().split(5);
        for (CMatrix cm1 : cms) {
            cm1.println();
        }
    }
}
