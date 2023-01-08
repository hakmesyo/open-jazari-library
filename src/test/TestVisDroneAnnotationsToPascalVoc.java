/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;
import jazari.utils.pascalvoc.PascalVocBoundingBox;
import jazari.utils.pascalvoc.PascalVocObject;
import jazari.utils.pascalvoc.PascalVocSource;

/**
 *
 * @author cezerilab
 */
public class TestVisDroneAnnotationsToPascalVoc {

    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .imread("C:\\Users\\cezerilab\\Desktop\\visdrone_selected_train\\images\\0000002_00005_d_0000014.jpg")
//                .imread("images/pullar.png")
                //.imread("D:\\zeytin_remote_sensing\\tif\\cropped_images\\temp_0_0.png")
                .imshow()
                ;
        
//        copyAnnotations();

//        convertVisDroneToPascalVOC();

    }

    private static void copyAnnotations() {
        File[] files = FactoryUtils.getFileListInFolderForImages("C:\\Users\\dell_lab\\Desktop\\Yeni klasör (2)\\images");
        String path_from = "D:\\DATASETS\\VisDrone\\image\\VisDrone2019-DET-train\\VisDrone2019-DET-train\\annotations";
        String path_to = "C:\\Users\\dell_lab\\Desktop\\Yeni klasör (2)\\annotations";
        for (File file : files) {
            String f = FactoryUtils.getFileName(file.getName()) + ".txt";
            FactoryUtils.copyFile(new File(path_from + "\\" + f), new File(path_to + "\\" + f));
            System.out.println("file = " + f + " copied");
        }
    }

    private static void convertVisDroneToPascalVOC() {
        String path = "C:\\Users\\cezerilab\\Desktop\\visdrone_selected_train\\annotations";
        File[] files = FactoryUtils.getFileListInFolder(path);
        for (File file : files) {
            String[] str = FactoryUtils.readFile(file.getAbsolutePath()).split("\n");
            List<PascalVocObject> lstObjects = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                System.out.println("str[i] = " + str[i]);
                String[] s = str[i].split(",");
                //http://aiskyeye.com/evaluate/results-format/ linkindeki bilgilere göre
                int px = Integer.parseInt(s[0]);
                int py = Integer.parseInt(s[1]);
                int w = Integer.parseInt(s[2]);
                int h = Integer.parseInt(s[3]);
                int object_id = Integer.parseInt(s[5]);
                String object_name = "";
                switch (object_id) {
                    case 0:
                        object_name = "ignored";
                        break;
                    case 1:
                        object_name = "pedestrian";
                        break;
                    case 2:
                        object_name = "people";
                        break;
                    case 3:
                        object_name = "bicycle";
                        break;
                    case 4:
                        object_name = "car";
                        break;
                    case 5:
                        object_name = "van";
                        break;
                    case 6:
                        object_name = "truck";
                        break;
                    case 7:
                        object_name = "tricycle";
                        break;
                    case 8:
                        object_name = "awning-tricycle";
                        break;
                    case 9:
                        object_name = "bus";
                        break;
                    case 10:
                        object_name = "motor";
                        break;
                    case 11:
                        object_name = "others";
                        break;
                    default:
                        throw new AssertionError();
                }
                PascalVocObject voc = new PascalVocObject(object_name,"",0,0,0,
                        new PascalVocBoundingBox(object_name,
                                new Rectangle(px, py, w, h),
                                0,
                                0,null),null);
                lstObjects.add(voc);
            }
            String folderName = "images";
            String imageFileName = file.getName();
            String fullPath = "C:\\Users\\cezerilab\\Desktop\\visdrone_selected_train\\images\\" + FactoryUtils.getFileName(file.getName()) + ".jpg";
            FactoryUtils.serializePascalVocXML(folderName, imageFileName, fullPath, new PascalVocSource(), lstObjects);
        }

    }
}
