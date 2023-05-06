/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;
import jazari.utils.pascalvoc.AnnotationPascalVOCFormat;

/**
 *
 * @author cezerilab
 */
public class TestConvertJsonToPascalVoc {

    public static void main(String[] args) {
        String folderPath = "C:\\Users\\cezerilab\\Desktop\\dataset\\butterfly_segmentation_yolov8\\butterfly_dataset\\Nature\\test";
        //convertJsonToPascalVoc(folderPath);
        //String filePath = folderPath + "\\butterfly (1).xml";
        //CMatrix cm = CMatrix.getInstance().annotateImagesByFolderPath(folderPath);
        
        String path=FactoryUtils.convertPascalVoc2YoloFormatBatch(folderPath,"yolov8","segmentation");
        
        //String ret = FactoryUtils.convertPascalVoc2YoloFormatPolygonBatch(filePath, new String[]{"0:butterfly"});
        //FactoryUtils.writeToFile(folderPath + "/" + FactoryUtils.getFileName(filePath) + ".txt", ret);

    }

    private static void convertJsonToPascalVoc(String folderPath) {
        File[] files = FactoryUtils.getFileArrayInFolderByExtension(folderPath, "json");
        int k = 0;
        for (File file : files) {
            AnnotationPascalVOCFormat apv = FactoryUtils.convertJSONtoPascalVoc4Polygon(file.getAbsolutePath());
            if (FactoryUtils.isFileExist(folderPath + "/" + FactoryUtils.getFileName(file.getName()) + ".png")) {
                FactoryUtils.serializePascalVocXML(folderPath, FactoryUtils.getFileName(file.getName()) + ".png", folderPath + "/" + FactoryUtils.getFileName(file.getName()) + ".png", apv.source, apv.lstObjects);
            } else {
                FactoryUtils.serializePascalVocXML(folderPath, FactoryUtils.getFileName(file.getName()) + ".jpg", folderPath + "/" + FactoryUtils.getFileName(file.getName()) + ".jpg", apv.source, apv.lstObjects);
            }
            System.out.println((k++) + ". file = " + file.getName() + " converted to pascalvoc format successfully");
        }
    }
}
