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
                //.imread("C:\\Users\\dell_lab\\Desktop\\Arşiv (1)\\225006_1.png")
                //.imread("images/artificial.jpg")
                .imread("images/bf.png")
                .imshow()
                ;
    }
}
