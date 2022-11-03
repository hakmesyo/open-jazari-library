/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author BAP1
 */
public class TestMap {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance().range(0, 255);        
                cm.cat(1,cm.pow(0.04f).map(0,255))
                .cat(1,cm.pow(0.1f).map(0, 255))
                .cat(1,cm.pow(0.2f).map(0, 255))
                .cat(1,cm.pow(0.4f).map(0, 255))
                .cat(1,cm.pow(0.67f).map(0, 255))
                .cat(1,cm.pow(1).map(0, 255))
                .cat(1,cm.pow(1.5f).map(0, 255))
                .cat(1,cm.pow(2.5f).map(0, 255))
                .cat(1,cm.pow(5).map(0, 255))
                .cat(1,cm.pow(10).map(0, 255))
                .cat(1,cm.pow(20).map(0, 255))
                
                .plot();
    }
        
    
}
