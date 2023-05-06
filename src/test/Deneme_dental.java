/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Random;
import jazari.factory.FactoryUtils;
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
                .imread("C:\\Users\\cezerilab\\Desktop\\dataset\\pistachio\\train\\undefined")
                //.imread("C:\\Users\\cezerilab\\Desktop\\dataset\\istanbul_dis_segmentation\\ds_1") //.imread("C:\\Users\\cezerilab\\Desktop\\dataset\\butterfly_segmentation_yolov8\\butterfly_dataset\\Nature\\train\\butterfly (1).png")
                //.imshow()
                ;
        String folderPath = "C:\\Users\\cezerilab\\Desktop\\dataset\\pistachio\\train\\all";
        FactoryUtils.generateYoloDetectionDataSet(folderPath, "yolov8_pistachio", "detection");
    }
}
