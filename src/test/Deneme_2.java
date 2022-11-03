/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author BAP1
 */
public class Deneme_2 {
    public static void main(String[] args) {
        CMatrix cm_x = CMatrix.getInstance().range(-5, 5,0.1f);
        CMatrix cm_1 = cm_x
                //.sigmoid()
                .negate()
                .multiplyScalar(2)
                .exp()
                //.divideByScalar(1)
                .addScalar(1)
                //.divideByScalar(1)
                .pow(-1)
                .plot()
                ;
//        CMatrix cm = CMatrix.getInstance().range(1,5).divideByScalar(1).println();        
        
                ;
    }
    
}
