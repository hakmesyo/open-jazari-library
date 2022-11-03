/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cezerilab
 */
public class TestCamera {
    public static void main(String[] args) {
       
        CMatrix cm = CMatrix.getInstance()
                .startCamera(0)               
                ;
        System.out.println("merhaba");
        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).start();
    }
}
