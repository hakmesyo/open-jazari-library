/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.tensorflow_js.test.yolov5;

import java.io.File;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class PascalVoc2Yolo {
    public static void main(String[] args) {
        String path="C:\\Users\\cezerilab\\Desktop\\robotaksi_dataset\\images";
//        FactoryUtils.renameFilesAsNanoTime(path);
        String pathImg=path+"\\165862147376900.jpg";
        CMatrix cm = CMatrix.getInstance()
                .imread(pathImg)
                .imshow()
                ;
//        FactoryUtils.convertPascalVoc2YoloFormat(pathImg, new String[]{"0:kangaroo","1:person"});
    }
}
