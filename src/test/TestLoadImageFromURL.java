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
public class TestLoadImageFromURL {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .imread("https://www.hepsiburada.com/hayatburada/wp-content/uploads/2021/09/shutterstock_653575417.jpg")
                .imshow()
                ;
    }
}
