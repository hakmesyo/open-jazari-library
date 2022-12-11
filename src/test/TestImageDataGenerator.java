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
                .imageDataGenerator(folderPath,1000,500)  //NCWH
                .shape()
                .slice(0)
                .slice(1)
                .shape()
                .reduce()
                .reshape(500,1000)
                .shape()
//                .slice(0)
//                .slice(0)
//                .flatten()
//                .shape()
//                .println()
                //.permute(1,0)
                //.reshape(500,1000)
                .imshow()
                ;
        
    }    
}
