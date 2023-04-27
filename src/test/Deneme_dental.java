/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author dell_lab
 */
public class Deneme_dental {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                //.imread("images/artificial.jpg")
                //.imread("images/bird.jpg")
                //.imread("C:\\Users\\cezerilab\\Desktop\\dataset\\ds_robotaksi\\robotaksi_dataset\\levhalar\\images\\train")
                //.imread("images/pistachio/Yeni klasör")
                //.imread("images/dental_1.png")
                .imread("C:\\Users\\cezerilab\\Desktop\\istanbul dental\\arsiv_1")
                .imshow()
                ;
    }
}
