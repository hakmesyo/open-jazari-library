/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import jazari.gui.FrameImage;
import jazari.gui.FrameImageAdvanced;
import jazari.image_processing.ImageProcess;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author BAP1
 */
public class TestFramePicture {
    public static void main(String args[]) {
//        FramePicture frm=new FramePicture(FactoryMatrix.rand(100).multiplyScalar(100).toIntegerArray());
//        FramePicture frm=new FramePicture(FactoryMatrix.rand(400).multiplyScalar(300));
//        FramePicture frm=new FramePicture();
        FrameImage frm=new FrameImage(CMatrix.getInstance(ImageProcess.readImageFromFile("images//kaplan1.jpg")),"","Test");
        frm.setVisible(true);
//        FrameImageAdvanced frm2=new FrameImageAdvanced(ImageProcess.readImageFromFile(".//images//kaplan1.jpg"),"images");
//        frm2.setVisible(true);
        
    }
}
