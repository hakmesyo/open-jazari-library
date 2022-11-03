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
public class TestPlot {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .linspace(-360, 360, 360)
                .toRadians()
                
                .sin()
                .jitter(0.05f)
                .plot()
//                .rand(10,5)
//                .plot()
               
                ;
    }
    
}
