/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author HP-pc
 */
public class TestAnimatedPlot {
    public static void main(String[] args) {
        CMatrix cm=CMatrix.getInstance();
        cm.holdOn();
//        FramePlot frm=new FramePlot(cm);
//        frm.setVisible(true);
        for (int i = 0; i < 1000; i++) {
            float incr=i/10.0f;
            //cm=cm.vector(12, 0.1,24).gaussmf(1.23, 17+i/100.0);
            cm=cm
                    .range(0.0f-incr, 0.1f,100-incr)
                    .timesScalar(10*(float)Math.PI/180).cos();
            
            cm.plot();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        cm.holdOff();
//        cm.plot();
        
    }
}
