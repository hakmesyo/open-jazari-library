/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.factory.FactoryMatrix;

/**
 *
 * @author BAP1
 */
public class TestFactoryMatrixClone {
    public static void main(String[] args) {
        float[] d=FactoryMatrix.matrixFloatRandom(1,100)[0];
        float[] d2=FactoryMatrix.clone(d);
        FactoryMatrix.toString(d);
        FactoryMatrix.toString(d2);
    }
}
