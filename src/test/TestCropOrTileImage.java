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
public class TestCropOrTileImage {
    public static void main(String[] args) {
        String path="D:\\zeytin_gis\\TM39_6125_4134.tif";
//        CMatrix cm = CMatrix.getInstance()
//                //.imread("images/peppers.png")
//                .imread(path)
//                .imshow("peppers")
//                .cropImages(20,15,"D:\\zeytin_gis\\cropped_images","temp","png",false);
//                ;

        CMatrix cm = CMatrix.getInstance()
                .imread("D:\\zeytin_gis\\cropped_images\\temp_0_0.png")
                .imshow()
                ;
    }
}
