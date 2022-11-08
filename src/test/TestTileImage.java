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
        String mainPath = "D:\\zeytin_remote_sensing\\tif\\TM39_6125_4134.tif";
        
        CMatrix cm = CMatrix.getInstance().imread(mainPath);//.tileImage(20,15,"D:\\zeytin_remote_sensing\\tif\\cropped_images","cropped_image","png");
        int a=3;
        
    }
}
