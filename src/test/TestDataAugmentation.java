/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import jazari.image_processing.ImageProcess;
import jazari.matrix.CMatrix;
import jazari.utils.DataAugmentationOpt;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 *
 * @author cezerilab
 */
public class TestDataAugmentation {
    public static void main(String[] args) {
        List<BufferedImage> lst=new ArrayList();
        lst.add(ImageProcess.imread("images/bird.jpg"));
        DataAugmentationOpt opt=new DataAugmentationOpt();
        opt.horizontal_flip=true;
        opt.vertical_flip=true;
        opt.brightness_range=new float[]{0.2f,0.99f};
        opt.zoom_range=new float[]{0.7f,1.5f};
        opt.width_shift_range=new float[]{-200,200};
        opt.height_shift_range=new float[]{-200,200};
        CMatrix cm = CMatrix.getInstance()
                .imread("images/bird.jpg")
                .imshow();
        List<BufferedImage> ret=cm.imDataAug(10, lst, opt);
        for (BufferedImage img : ret) {
            cm.setImage(img).imshow();
        }
    }
}
