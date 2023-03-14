/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Point;
import java.awt.Polygon;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;
import jazari.types.TMatrixOperator;
import jazari.utils.pascalvoc.AnnotationPascalVOCFormat;

/**
 *
 * @author cezerilab
 */
public class Deneme2 {
    public static void main(String[] args) {
        Polygon poly=new Polygon();
        for (int i = 0; i < 10; i++) {
            Point p=new Point(i,2*i);
            poly.addPoint(p.x, p.y);
            System.out.println(poly.npoints);
        }
        //AnnotationPascalVOCFormat apv= FactoryUtils.deserializePascalVocXML("images/pullar.xml");
        //FactoryUtils.serializePascalVocXML("images", "pullar_2.xml", "images/pullar.png", apv.source, apv.lstObjects);
//        CMatrix cm = CMatrix.getInstance()
//                .rand(5,3)
//                .dump()
//                ;
//        cm.clone().mean().mean().println();
//        cm.clone().std().std().println();
        
//        CMatrix cm = CMatrix.getInstance()
//                .rand(4,3,0,30)
//                .round()
//                .println()
//                
//                
//                ;
//        CMatrix cm_index = cm.clone().findIndex(TMatrixOperator.GREATER, 15).println();
//        CMatrix cm_value = cm.clone().findValuesByIndex(cm_index).println();

//        CMatrix cm_value = cm.clone().reshape(12, 1).println();
//        float f=cm_value.getValue((int)cm_index.getValue(0, 0), 0);
//        System.out.println("cm_value = " +f );
    }
}
