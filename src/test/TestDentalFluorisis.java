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
public class TestDentalFluorisis {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .imread("C:\\Users\\dell_lab\\Downloads\\1.jpg")
                .imresize(0.3f)
                .imshow()
                
                ;
    }
}
