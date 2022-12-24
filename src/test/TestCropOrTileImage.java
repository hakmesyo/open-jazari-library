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
 * @author cezerilab
 */
public class TestCropOrTileImage {
    public static void main(String[] args) {
        String path="D:\\zeytin_remote_sensing\\tif\\TM39_6125_4134.tif";
        //To Crop or Tile
        CMatrix cm = CMatrix.getInstance()
                .imread(path)
                .imshow("original tif image")
                .cropImages(20,15,"D:\\zeytin_gis\\cropped_images","temp","png",false);
                ;

        //To Annotate the images
        CMatrix cm1 = CMatrix.getInstance()
                .imread("D:\\zeytin_remote_sensing\\tif\\cropped_images\\temp_0_0.png")
                .imshow()
                ;
        
        //to convert pascal voc format to yolo format
        
        FactoryUtils.convertPascalVoc2YoloFormat("D:\\zeytin_remote_sensing\\tif\\cropped_images", new String[]{"0:zeytin","1:ev"});
        
    }
}
