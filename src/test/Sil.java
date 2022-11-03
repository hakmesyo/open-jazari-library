/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class Sil {

    public static void main(String[] args) {
//        FactoryUtils.makeDirectory("images/sil");
//        CMatrix cm = CMatrix.getInstance();
//        File[] files=FactoryUtils.getFileListInFolderForImages("images");
//        for (File file : files) {
//            cm.imread(file).rgb2gray().imresize(224, 224).imsave("images/sil/"+file.getName());
//        }
        CMatrix cm = CMatrix.getInstance()
                .imread("images/pullar.png")
                .imshow()
                ;
        
        
//        System.out.println("time:"+System.currentTimeMillis());
//        CMatrix cm = CMatrix.getInstance()
//                .range(-300, 300) 
//                .gaussmf(50, 0)
//                .transpose()
//                .plot()
//                
//                ;
//        CMatrix cm = CMatrix.getInstance()
////                .imread("images/pullar.png")
//                .imread("images/kaplan.jpg")
//                //.imhist()
//                .imshow()
//                ;
//        CMatrix cm = CMatrix.getInstance()
//                .range(0,720)
//                .println()
//                .perlinNoise(0.03f)
////                .toRadians()
////                .sin()
//                .plot()
//                ;
//        CMatrix cm1 = cm.addScalar(5).jitter(0.5f).cat(1, cm).plot();
//        double n=-1.57E6;
//        System.out.println("n = " + n);
//        CMatrix cm = CMatrix.getInstance()
//                .randn(500,20)
//                .getHistogramData(30)
//                .plot()
//                //.bar()
//                ;
//        double a=n*6;
//        System.out.println("a = " + a);
    }
}

