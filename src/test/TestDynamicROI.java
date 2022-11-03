/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.gui.FrameImage;
import jazari.matrix.CMatrix;
import jazari.matrix.CPoint;
import jazari.factory.FactoryUtils;
import java.awt.Rectangle;

/**
 *
 * @author hakmesyo
 */
public class TestDynamicROI {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance().imread("images\\kaplan1.jpg").imshow("rgb").rgb2gray().imshow("gray");
        float[][] d=FactoryUtils.readFromFile("data\\roi.txt",",");
        CPoint[] p=FactoryUtils.getRoiBoundary(d);
//        CMatrix cm2=cm.subMatrix(p[0],p[1]).imshow(true);//.commandParser(p[0].row+":"+p[1].row, p[0].column+":"+p[1].column).imshow();
        float t=0;
        float n=0;
        FrameImage frm=new FrameImage(cm,"","");
        for (int i = p[0].row; i <p[1].row ; i++) {
            for (int j =p[0].column; j < p[1].column; j++) {
                CPoint cp=new CPoint(i, j);
                boolean q=FactoryUtils.isPointInROI(cp, d);
                if (q) {
                    n++;
                    float val=cm.getValue(i, j);
                    t+=val;
                    System.out.println("in ROI row:"+i+" col:"+j);
                    cm.setValue(cp,255);
                }else{
//                    System.out.println("out of ROI row:"+i+" col:"+j);
                }
            }
        }
        cm.imshow(frm);
        float roi_mean=t/n;
        System.out.println("roi mean:"+roi_mean);
//        float roi_mean=cm.getRoiMean(d,p);
        
    }
    
}
