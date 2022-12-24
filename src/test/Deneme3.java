/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author dell_lab
 */
public class Deneme3 {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .imread("images/pullar.png")
                .imshow()
                
                ;
//        String path="D:\\DATASETS\\VisDrone\\visdrone_selected\\test";
//        FactoryUtils.convertPascalVoc2YoloFormat(path, "class.txt");
    }
}
