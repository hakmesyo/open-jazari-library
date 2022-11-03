/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.RenderingHints;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestDotProduct {
    public static void main(String[] args) {
        /**
         * dot product sadece vektörlerde çalışır matrisler için dot veya mul veya muli yi deneyiniz.
         */
//        CMatrix cm1 = CMatrix.getInstance(4,2,0,3).reshape(4, 1).dump();
//        CMatrix cm2 = CMatrix.getInstance(4,0,1,4).reshape(4, 1).dump();
//        cm1.dotProduct(cm2).dump();
        
        CMatrix cm = CMatrix.getInstance(11,23,34,7,5,45,19,17,21)
                .reshape(3, 3).dump()
//                .imresize(5,5,RenderingHints.VALUE_INTERPOLATION_BILINEAR).dump()
                .imresize(2,2,RenderingHints.VALUE_INTERPOLATION_BILINEAR).dump()
                
                ;
    }
}
