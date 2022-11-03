/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.kazova;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jazari.factory.FactoryUtils;
import jazari.image_processing.ImageProcess;
import jazari.matrix.CMatrix;
import jazari.matrix.CPoint;
import jazari.matrix.CRectangle;
import jazari.types.TMatrixCell;

/**
 * 
 * tiff image ve kmz de referans olabilmesi adına üç yer işaretlendi. Bunlar hava alanı pisti ve civarındaki kavşak noktalarıdır
 * tahmini hata 10 metre civarı
 * 
 * kavşak     : 40°17'54.95"K, 36°21'37.85"D  tiff image üstünde row:3859 column:4359  -> 275665.58 4464242.7700000005 37.Zone
 * pist_start : 40°18'4.19"K,  36°21'29.11"D  tiff image üstünde row:3852 column:4352  -> 275467.72000000003 4464533.87 37.Zone
 * pist_end   : 40°18'44.64"K, 36°22'30.57"D  tiff image üstünde row:3814 column:4401  -> 276955.82 4465738.08 37.Zone
 * 
    CORNER_UL_LAT_PRODUCT = 41.29422    
    CORNER_UL_LON_PRODUCT = 34.75931
    CORNER_UR_LAT_PRODUCT = 41.36465
    CORNER_UR_LON_PRODUCT = 37.66328
    CORNER_LL_LAT_PRODUCT = 39.31565
    CORNER_LL_LON_PRODUCT = 34.88149
    CORNER_LR_LAT_PRODUCT = 39.38135
    CORNER_LR_LON_PRODUCT = 37.70191
 
    gis width  = 37.70191 - 34.75931 = 2.94260
    gis height = 41.29422 - 39.38135 = 1.91287
    image_w = 8111
    image_h = 7341
    
    ratio x rx = 2.94260 / 8111 = 3.6279E-4
    ratio y ry = 1.91287 / 7341 = 2.6057E-4
 * 
 * tüm çevrimler için kullanılacak web sitesi
 * https://coordinates-converter.com/en/decimal/51.000000,10.000000?karte=OpenStreetMap&zoom=8
 *
 * @author cezerilab
 */
public class TestVisualizeTiffImage {
    public static double rx=3.6279E-4;
    public static double ry=2.6057E-4;
    
    public static void main(String[] args) {
        String path="C:\\Users\\cezerilab\\Desktop\\Kazova";
        
//        convert2DMSData(path,"geo_loc.txt","datum.txt");
//        calculateReferenceRatio();
        
//        String deg=FactoryUtils.UTM2DMS(36, 197375, 4578289, 'N');
//        System.out.println("deg = " + deg);
//        String deg=FactoryUtils.UTM2Deg(37, 249307.67,4461456.00, 'N');
//        System.out.println("deg = " + deg);
//        String degMS=FactoryUtils.UTM2DMS(37, 249307.67,4461456.00, 'N');
//        System.out.println("degMS = " + degMS);
//        String utmdeg=FactoryUtils.deg2UTM(40.2660365, 36.0518495);
//        System.out.println("utm deg= " + utmdeg);
//        String utmdms=FactoryUtils.degMS2UTM("40.0:15.0:57.73139999999444","36.0:3.0:6.6582000000107655");
//        System.out.println("utm dms = " + utmdms);

//        String kavsak=FactoryUtils.degMS2UTM("40:18:44.64", "36:22:30.57");
//        System.out.println("kavsak dms = " + kavsak);
        
//        CMatrix cm = CMatrix.getInstance()
//                .imread(path+"/LT05_L1TP_175032_20081017_20180116_01_T1_B1.TIF")
//                .imshow()
                ;
//        extractPointClouds(path,"datum.txt","points.txt");
        
        pinOutPointCloudsOnTifImage(path,"LT05_L1TP_175032_20081017_20180116_01_T1_B1.TIF");
        
//        cropImagesByPointClouds(path,"LT05_L1TP_175032_20081017_20180116_01_T1_B1.TIF","B1");
//        cropImagesByPointClouds(path,"LT05_L1TP_175032_20081017_20180116_01_T1_B2.TIF","B2");
//        cropImagesByPointClouds(path,"LT05_L1TP_175032_20081017_20180116_01_T1_B3.TIF","B3");
//        cropImagesByPointClouds(path,"LT05_L1TP_175032_20081017_20180116_01_T1_B4.TIF","B4");
//        cropImagesByPointClouds(path,"LT05_L1TP_175032_20081017_20180116_01_T1_B5.TIF","B5");
//        cropImagesByPointClouds(path,"LT05_L1TP_175032_20081017_20180116_01_T1_B6.TIF","B6");
//        cropImagesByPointClouds(path,"LT05_L1TP_175032_20081017_20180116_01_T1_B7.TIF","B7");
    }

    private static void extractPointClouds(String path, String file,String target) {
        String str=FactoryUtils.readFile(path+"/"+file);
        String[] s=str.split("\n");
        String[] features=s[0].split(",");
        System.out.println("features = " + Arrays.toString(features));
        float[][] data=new float[s.length-1][features.length];
        String rows="";
        for (int i = 1; i < s.length; i++) {
            data[i-1]=FactoryUtils.toFloatArray1D(s[i].split(","));
            int py=7341-(int)((data[i-1][1]-39.38135)/ry);
            int px=(int)((data[i-1][2]-34.75931)/rx);
            rows+=py+","+px+"\n";
        }
        FactoryUtils.writeToFile(path+"/"+target, rows);
    }

    private static void cropImagesByPointClouds(String path, String tiffImageName,String folder) {
        FactoryUtils.makeDirectory(path+"/TIF_IMAGES/cropped");
        FactoryUtils.makeDirectory(path+"/TIF_IMAGES/cropped/"+folder);
        BufferedImage img=ImageProcess.imread(path+"/TIF_IMAGES/"+tiffImageName);
        String[] points=FactoryUtils.readFile(path+"/points.txt").split("\n");
        CMatrix cm = CMatrix.getInstance();
        int dt=10;
        for (int i = 0; i < points.length; i++) {
            int py=Integer.parseInt(points[i].split(",")[0]);
            int px=Integer.parseInt(points[i].split(",")[1]);
            BufferedImage im=ImageProcess.cropImage(img,new CRectangle(py-dt,px-dt,2*dt,2*dt));
            ImageProcess.saveImage(im, path+"/TIF_IMAGES/cropped/"+folder+"/"+(i+1)+".jpg");
            cm.setImage(im).imshowRefresh();
        }
    }

    private static void convert2DMSData(String path,String file,String target) {
        String str=FactoryUtils.readFile(path+"/"+file);
        String[] s=str.split("\n");
        String[] features=s[0].split(",");
        String labels=s[0];
        System.out.println("labels = " + labels);
        float[][] data=new float[s.length-1][features.length+2];
        String rows=labels+"\n";
        for (int i = 1; i < s.length; i++) {
            data[i-1]=FactoryUtils.toFloatArray1D(s[i].split(","));
            String dms=FactoryUtils.UTM2Deg(37, data[i-1][1], data[i-1][2], 'N');
            s[i]=s[i].replace(s[i].split(",")[1],dms.split(" ")[0]);
            s[i]=s[i].replace(s[i].split(",")[2],dms.split(" ")[1]);
            rows+=s[i]+"\n";
        }
        FactoryUtils.writeToFile(path+"/"+target, rows);
    }

    private static void calculateReferenceRatio() {
        int py=7341-(int)((40.2660365-39.38135)/ry);
        int px=(int)((36.0518495-34.75931)/rx);
        System.out.println("px = " + px);
        System.out.println("py = " + py);
    }

    private static void pinOutPointCloudsOnTifImage(String path, String tif) {
        CMatrix cm = CMatrix.getInstance().imread(path+"/TIF_IMAGES/"+tif);
        BufferedImage img=cm.getImage();
        String[] points=FactoryUtils.readFile(path+"/points.txt").split("\n");
        int dt=10;
        for (int i = 0; i < points.length; i++) {
            int py=Integer.parseInt(points[i].split(",")[0]);
            int px=Integer.parseInt(points[i].split(",")[1]);
            img=ImageProcess.drawRectangle(img, py-10, px-1, 2*dt, 2*dt, 2, Color.yellow);
//            for (int j = -dt; j < dt; j++) {
//                for (int k = -dt; k < dt; k++) {
//                    cm.setValue(py+j, px+k, 255);
//                }
//            }
//            cm.setValue(py-1, px-1, 0);
//            cm.setValue(py-1, px, 0);
//            cm.setValue(py, px-1, 0);
//            cm.setValue(py, px, 0);
        }
        cm.setImage(img);
        cm.imshow();
    }
}
