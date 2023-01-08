/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;
import jazari.utils.pascalvoc.PascalVocBoundingBox;
import jazari.utils.pascalvoc.AnnotationPascalVOCFormat;
import jazari.utils.pascalvoc.PascalVocObject;

/**
 *
 * @author cezerilab
 */
public class TestBoundingBox {
    public static void main(String[] args) {
        String pathXML="D:\\Dropbox\\NetbeansProjects\\OpenJazariLibrary\\images\\images\\images";
//        //yazmak için
//        List<PascalVocObject> lst = new ArrayList();
//        lst.add(new PascalVocObject(new BoundingBox("levha_1", new Rectangle(12, 13, 100, 100),0,0)));
//        lst.add(new PascalVocObject(new BoundingBox("levha_2", new Rectangle(40, 43, 50, 30),0,0)));
//        String xml=BoundingBoxUtils.serializePascalVocXML("images", "bird.jpg", "C:\\Dropbox\\NetbeansProjects\\OpenJazariLibrary\\images\\bird.jpg", lst);
//        System.out.println("xml = " + xml);
//        
//        //okumak için
        AnnotationPascalVOCFormat bb=FactoryUtils.deserializePascalVocXML(pathXML+"\\43.xml");
        System.out.println(bb.lstObjects.get(0).bndbox);
        
        //pascal voc formatından csv formatına almak için
        String csvString=FactoryUtils.convertPascalVoc2CsvFormat(pathXML,"D:/train.csv");
        System.out.println("csvString = " + csvString);
        
        //resimden bounding box yapmak için
        //CMatrix cm = CMatrix.getInstance().imread("images/bird.jpg").imshow();

        //pascalvocxml dosyalarını reference table a göre yolo formatına döndürmek
//        BoundingBoxUtils.convertPascalVoc2YoloFormat("images/images/images", "labels_map.txt");
        
    }
    
}
