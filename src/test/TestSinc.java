/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;
import jazari.factory.FactoryUtils;

public class TestSinc {

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            long t1 = FactoryUtils.tic();
            CMatrix x = CMatrix.getInstance().range(-10, 10, 0.01f).shape();
            CMatrix cm = x.clone().sinc().jitter(0.01f);//.plot(x.toFloatArray1D());
            CMatrix cm2 = cm.cat(2, cm.clone().addScalar(1.5f)).transpose().plot(x.getArray1Dfloat());
            long t2 = FactoryUtils.toc(t1);
        }
    }
}
