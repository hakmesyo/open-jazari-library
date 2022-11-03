/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestVegetationAgiri {
    public static void main(String[] args) {
        String formatted = String.format("%03d", 1);
        System.out.println("formatted = " + formatted);
        String labelPath="C:\\Users\\cezerilab\\Downloads\\Ağrı Dağı Görüntü-20220209T160314Z-001\\ground_truth.txt";
        String imgPath="C:\\Users\\cezerilab\\Downloads\\Ağrı Dağı Görüntü-20220209T160314Z-001\\Ağrı Dağı Görüntü\\";
        CMatrix cmLabel = CMatrix.getInstance().readFile(labelPath, ";").println();
        CMatrix cm1 = CMatrix.getInstance().imread(imgPath+String.format("%03d", 110)+".png").imresize(0.5f).imshow();
        CMatrix cm2 = CMatrix.getInstance().imread(imgPath+String.format("%03d", 24)+".png").imresize(0.5f).imshow();
    }
}
