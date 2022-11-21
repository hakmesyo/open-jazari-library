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
public class Deneme_3 {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .imread("images/peppers.png")
                //.imread("D:\\zeytin_remote_sensing\\tif\\cropped_images\\temp_0_0.png")
                .imshow()
                ;
    }
}
