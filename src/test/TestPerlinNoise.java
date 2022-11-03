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
public class TestPerlinNoise {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .range(-100, 100,1,3)
                .shape()
                .perlinNoise(0.1f)
                .plot()
                ;
        
    }
    
}
