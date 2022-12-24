/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Dimension;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestCamera {
    public static void main(String[] args) {
       
        CMatrix cm = CMatrix.getInstance()
//                .startCamera(0, new Dimension(1280, 720),new Dimension(640,360))               
//                .startCamera(0, new Dimension(1280, 720))               
//                .startCamera(0, new Dimension(1920, 1080), new Dimension(640,360))               
//                .startCamera(0, new Dimension(1920, 1080))               
                .startCamera(0)               
                ;
//        while(true){
//            System.out.println("merhaba");
//        }
    }
}
