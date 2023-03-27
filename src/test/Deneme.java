/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;
import jazari.utils.pascalvoc.AnnotationPascalVOCFormat;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author cezerilab
 */
public class Deneme {

    public static boolean intersects(Point k, Point z, Point p) {
        return new Line2D.Float(k, z).ptLineDist(p) <= 3;
    }

    public static boolean intersects(int x_from, int y_from, int x_to, int y_to, Point p) {
        return new Line2D.Double(x_from, y_from, x_to, y_to).ptLineDist(p) <= 3;
    }

    public static void main(String[] args) {
//        boolean b = intersects(new Point(10, 10), new Point(10, 20), new Point(11, 15));
        boolean b = intersects(20,10,10,20, new Point(15, 16));
        System.out.println("b = " + b);
//        String path_1 = "D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\last_years\\images";
//        CMatrix cm = CMatrix.getInstance().imread(path_1+"/frame_000000.jpg").imresize(0.5f).imshow().imsave(path_1, "frame_000001.jpg");
//        String path_1="D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\simulation\\_images";
        //String path_1="D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\last_years\\gonderilecek_veriler\\B160519_V1_K1\\dilute";
        String path_1 = "D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\simulation\\_images";
        //        CMatrix cm = CMatrix.getInstance()
        //                .annotateImages(path_1);
        //        String path="D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\last_years\\images\\0000002_00005_d_0000014.xml";
        //        String s1=FactoryUtils.updatePascalVocObjectNames(path,"car:Taşıt","van:Taşıt","truck:Taşıt","motor:Taşıt","pedestrian:İnsan","people:İnsan","bicycle:İnsan");
        //        String s2=FactoryUtils.removePascalVocObjectNames(path,"ignored","tricycle");
        //        System.out.println("s2 = " + s2);

        //        String path="D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\last_years\\images";
        //        FactoryUtils.updatePascalVocObjectNamesBatchProcess(path,"car:Taşıt","van:Taşıt","truck:Taşıt","motor:Taşıt","bus:Taşıt","pedestrian:İnsan","people:İnsan","bicycle:İnsan");
        //        FactoryUtils.removePascalVocObjectNamesBatchProcess(path,"ignored","tricycle","awning-tricycle");

        //FactoryUtils.removeFilesContains(path,"Kopya");
        //        String path="D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\last_years\\gonderilecek_veriler\\B160519_V1_K1";
        //        FactoryUtils.diluteDataSet(path, 0.25f, 123, "jpg");

        //        String path="D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\last_years\\gonderilecek_veriler\\veriler.json";
        //        //String str=FactoryUtils.readJSONFile(path);
        //        CMatrix cm2 = CMatrix.getInstance().readJsonFile(path);
        //        System.out.println(cm2.valueString);

        //        CMatrix cm = CMatrix.getInstance()
        //                .range(0, 720)
        //                .toRadians()
        //                .sin()
        //                .println()
        //                .plot()
        //                ;

        //        CMatrix cm = CMatrix.getInstance()
        //                .imread("C:\\Users\\dell_lab\\Downloads\\Resim 2\\Resim 2\\cropped_image_0_0.png")
        //                .imshow()
        //                
        //                ;
        //        CMatrix cm = CMatrix.getInstance()
        //                .range2D(0, 10, 100)
        //                .println();
        ////        for (int i = 9; i < 10; i++) {
        ////            cm.clone().cmd(i*10+":"+(i*10+10), ":").println();
        ////        }
        //
        //        cm.clone().cmd("90:100", ":").println();

        //        CMatrix cm = CMatrix.getInstance()
        //                //.imread("images/pullar.png")
        //                .imread("C:\\Users\\dell_lab\\Downloads\\Resim 2\\Resim 2\\TM39_6125_4134.tif")
        //                ;
        //
        //        int w = 1000;
        //        int h = 500;
        //        for (int i = 0; i < 15; i++) {
        //            for (int j = 0; j < 20; j++) {
        //                System.out.println("j = " + j);
        //                CMatrix cm1 = cm.clone()
        //                        .imcrop(i * w, j * h, w, h) //.imshow()
        //                        .imsave("C:\\Users\\dell_lab\\Downloads\\Resim 2\\Resim 2", "cropped_image_"+i+"_"+j+".png")
        //                        ;
        //            }
        //        }
        //      .imshow()
        //.rgb2gray()
        //.imshow("RGB")
        //.rgb2gray()
        //.imshow("Gray")

        //.imnegative()
        //.imshow()
        //.map(0, 7)
        //.heatmap(Color.red)
        //.round()
        //.head()
        //.map(0,255)
        //.imshow()
        //                .map(0, 255)
        //                .imRefresh()
        //                .imshow()
        //.imhist()
        //.showDataGrid()
        ;
        //        float[] hist=cm.getHistogramData(256).println().toFloatArray1D();
        //System.out.println("hist = " + Arrays.toString(hist));

        /*
        float[][] d=cm.toFloatArray2D();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                //d[i][j]=(float)Math.log10(d[i][j]+1);
                d[i][j]=(float)Math.sqrt(d[i][j]);
            }
        }
        
        cm.setArray(d).imshow().map(0, 255).imshow();
         */
        //        float[][] imgd=cm.toFloatArray2D();
        //        imgd=toBinaryImage(imgd);
        //        cm.setArray(imgd)
        //                //.map(0, 255)
        //                //.histeq()
        //                .heatmap(Color.yellow)
        //                //.imshow()
        //                ;
        /*
        CMatrix cm = CMatrix.getInstance()
                .range("12")                
                .println()
                .range("12:19")                
                .println()
                .range("12:19:2")                
                .println()
                .transpose()
                .dump()
                .replicateRow(5)
                .dump()
                ;
         */
        //        int nr = 5_000;
        //        int nc = 5_000;
        //        long t=System.currentTimeMillis();
        //        for (int i = 0; i < 10; i++) {
        //            CMatrix cm = CMatrix.getInstance()
        //                    .rand(nr, nc)
        //                    //.dotProduct(CMatrix.getInstance().rand(nr, nc))
        //                    .mmul(CMatrix.getInstance().rand(nr, nc));
        //                    //.dotProduct(CMatrix.getInstance().rand(3, 2),CMatrix.getInstance().rand(2,3))
        //                    //.dump();
        //            System.out.println("elapsed time:"+(System.currentTimeMillis()-t));
        //            t=System.currentTimeMillis();
        //        }

        //        CMatrix cm = CMatrix.getInstance()
        //                .rand(5, 3)
        //                .dump()
        //                .dotProduct(CMatrix.getInstance().rand(3, 2).dump())
        //                .dump();
        //                .dotProduct(CMatrix.getInstance().rand(3, 2).dump())
        //                .dump();
        //        CMatrix cm = CMatrix.getInstance()
        //                .ones(10)
        //                .setValue("1:-1","1:-1", 0)
        //                .dump()
        //                ;

        //        CMatrix cm = CMatrix.getInstance()
        //                .imread("images/bf.jpg")
        //                .imshow("original")
        //                .imresize(8,8)
        //                .imshow("minimized image")
        //                .imresize(773, 600)
        //                .imshow("resized to original")

        //.rgb2gray()
        //(.cmd(":",":")
        //.println()
        //.sum()
        //(.println()

        //                .imshow()
        //                .sum()
        //                .imshow(true)
        ;
    }

    private static float[][] toBinaryImage(float[][] imgd) {
        for (int i = 0; i < imgd.length; i++) {
            for (int j = 0; j < imgd[0].length; j++) {
                if (imgd[i][j] < 127) {
                    imgd[i][j] = 0;
                } else {
                    imgd[i][j] = 1;
                }
            }
        }
        return imgd;
    }

//    private static float[][] toQuantize(float[][] imgd,int bn) { 
//        int n=256/bn;
//        for (int i = 0; i < imgd.length; i++) {
//            for (int j = 0; j < imgd[0].length; j++) {
//                for (int k = 0; k < bn; k++) {
//                    
//                }
//            
//                if (imgd[i][j]<127) {
//                    imgd[i][j]=0;
//                }else{
//                    imgd[i][j]=1;
//                }
//            }
//        }
//        return imgd;
//        
//    }
}

//class Book{
//    private String name;
//    private String author;
//    private String publisher;
//    private String isbn;
//
//    @XmlElement(name = "title")
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(String publisher) {
//        this.publisher = publisher;
//    }
//
//    public String getIsbn() {
//        return isbn;
//    }
//
//    public void setIsbn(String isbn) {
//        this.isbn = isbn;
//    }    
//}
