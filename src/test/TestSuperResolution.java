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
public class TestSuperResolution {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .imread("images/fox.png")
                .imshow()
                .imresize(4)
                .imshow()
                .imread("images/fox_1.png")
                .imshow()
                ;
    }
}
