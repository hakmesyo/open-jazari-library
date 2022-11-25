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
public class TestImageDataGenerator {
    public static void main(String[] args) {
//on batch images
        String folderPath="D:\\zeytin_remote_sensing\\tif\\deneme";
        CMatrix cm = CMatrix.getInstance()
                .range(0, 1200)
                .shape()
                .imageDataGenerator(folderPath,100,50)  //NCWH
                .shape()
                .slice(":",":-1")
                .shape()
                .flatten()
                .shape()
                .println()
//                .reshape(50,100)
//                .imshow()
                ;
        
    }    
}
