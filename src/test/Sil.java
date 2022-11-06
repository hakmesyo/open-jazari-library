/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import jazari.factory.FactoryMatrix;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 *
 * @author cezerilab
 */
public class Sil {

    private static INDArray array;

    public static void main(String[] args) {

        int n=10000;
        long t1=FactoryUtils.tic();
        float[][] d=new float[n][n];
        for (int i = 0; i < 1000; i++) {            
          //cm.pow(2);
          //CMatrix cm = CMatrix.getInstance().randn(n,n);
          //d=FactoryMatrix.randMatrix(n, n);
          //CMatrix.getInstance().rand(n, n);
          array = Nd4j.rand(n, n);
        }
        t1=FactoryUtils.toc(t1);

//        FactoryUtils.makeDirectory("images/sil");
//        CMatrix cm = CMatrix.getInstance();
//        File[] files=FactoryUtils.getFileListInFolderForImages("images");
//        for (File file : files) {
//            cm.imread(file).rgb2gray().imresize(224, 224).imsave("images/sil/"+file.getName());
//        }
//        CMatrix cm = CMatrix.getInstance()
//                .imread("images/pullar.png")
//                .imshow()
//                ;
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
