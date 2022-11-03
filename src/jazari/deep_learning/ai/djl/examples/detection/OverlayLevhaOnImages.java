/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.detection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import jazari.factory.FactoryUtils;
import jazari.image_processing.ImageProcess;
import jazari.matrix.CMatrix;
import jazari.matrix.CPoint;

/**
 *
 * @author cezerilab
 */
public class OverlayLevhaOnImages {

    public static void main(String[] args) {
        //overlayImage();
        //annotateImageForCV();
        xml2csv();
    }

    private static void overlayImage() {
        String path_template = "images/teknofest/template_augment";
        File[] files_template = FactoryUtils.getFileListInFolderByExtension(path_template, "png");
        String path_org = "C:\\ds_teknofest\\recorded_images\\parkur_copy";
        File[] files_org = FactoryUtils.getFileListInFolderByExtension(path_org, "png");
        String path_overlayed = "C:\\ds_teknofest\\recorded_images\\parkur_overlayed";
        Random r = new Random();
        int cnt = 0;
        for (File f : files_org) {
            int size = 50 + r.nextInt(128);
            BufferedImage img = ImageProcess.resize(ImageProcess.imread(path_template + "/5.png"), size, size);
            //CMatrix.getInstance(img).imshow();
            CPoint cp = new CPoint(r.nextInt(270), r.nextInt(500));
            CMatrix cm = CMatrix.getInstance()
                    .imread(f)
                    .overlay(img, cp)
                    .imsave(path_overlayed + "/" + FactoryUtils.getFileName(f.getName()) + ".jpg")
                    .imshowRefresh();
            System.out.println((files_org.length - cnt++) + " images remain");
        }
    }

    private static void annotateImageForCV() {
        CMatrix cm = CMatrix.getInstance()
                .imread("C:\\ds_teknofest\\recorded_images\\ds_cv\\test\\100.jpg")
                .imshow();
    }

    private static void xml2csv() {
        String p_train="C:\\ds_teknofest\\recorded_images\\ds_cv\\images\\train";
        String p_test="C:\\ds_teknofest\\recorded_images\\ds_cv\\images\\test";
        String p_data="C:\\ds_teknofest\\recorded_images\\ds_cv\\data";
        
        String sTrain=FactoryUtils.convertPascalVoc2CsvFormat(p_train);
        FactoryUtils.writeToFile(p_data+"/train_labels.csv", sTrain);
        
        String sTest=FactoryUtils.convertPascalVoc2CsvFormat(p_test);
        FactoryUtils.writeToFile(p_data+"/test_labels.csv", sTest);
    }
}
