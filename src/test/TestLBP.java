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
public class TestLBP {
    public static void main(String[] args) {
//        CMatrix cm_1 = CMatrix.getInstance().range(-360, 1, 360).multiplyScalar(0.5f).plot().addNoise(2).toRadians().cos();
//        CMatrix cm_2 = CMatrix.getInstance().range(-360, 1, 360).multiplyScalar(3).toRadians().sin().divideScalar(10);
//        CMatrix cm_3 = cm_1.add(cm_2).plot();
        CMatrix cm_1 = CMatrix.getInstance().imread("images/pullar.png")
                .rgb2gray()
                .imshow()
                .convolveLBP(3)
                .map(0, 255)
                .imshow();
        CMatrix cm_2 = CMatrix.getInstance().imread("images/pullar.png")
                .rgb2gray()
                .imshow()
                .getLBP1D(8,true)
                .plot();
                //.getLBP1D(8,true).plot();
//        CMatrix cm_5 = cm_3.getLBP1D(8,false).plot();
    }
}
