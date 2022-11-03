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
public class TestAlphaBlendingDistributionHistogram {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance().imread("images\\pullar.png").imshow().imhist();
        CMatrix cm1 = CMatrix.getInstance().randn(1,1000, -12.3f,23.5f).transpose();
        CMatrix cm2 = CMatrix.getInstance().randn(1, 1000, 144.5f, 165.65f).transpose();
        CMatrix cm3 = CMatrix.getInstance().randn(1, 1000, -500.5f, -15.65f).transpose();
        CMatrix cm4 = cm1.cat(1, cm2).cat(1, cm3).hist(100);
    }
}
