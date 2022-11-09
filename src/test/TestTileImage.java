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
public class TestTileImage {
    public static void main(String[] args) {
        //tif imgeleri 15_000 x 10_000 piksel çözünürlüğündedir. Bunları 1000 x 500 lük imgelere bölütleyeceğiz
        String path="D:\\zeytin_remote_sensing\\tif\\TM39_6125_4134.tif";
        CMatrix cm = CMatrix.getInstance()
                //.imread("images/peppers.png")
                .imread(path)
                //.imshow("zeytin")
                .cropImages(20,15,"D:\\zeytin_remote_sensing\\tif\\cropped_images","temp","png",false);
                ;

//        CMatrix cm = CMatrix.getInstance()
//                .imread("D:\\zeytin_remote_sensing\\tif\\cropped_images\\temp_0_0.png")
//                .imshow()
//                ;
        
    }
}
