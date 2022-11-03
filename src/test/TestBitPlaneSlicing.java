/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;
import jazari.factory.FactoryUtils;

/**
 *
 * @author BAP1
 */
public class TestBitPlaneSlicing {

    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance().imread("images\\kaplan1.jpg").rgb2gray().imshow();
                cm.clone().bitPlaneMSB().imshow("MSB image");
                cm.clone().bitPlane(7).add(cm.clone().bitPlane(6)).add(cm.clone().bitPlane(5)).imshow("first three msb image");
                cm.clone().bitPlane(0).imshow("first slice image");
                cm.clone().bitPlane(1).imshow("second slice image");
                cm.clone().bitPlane(2).imshow("third slice image");
                cm.clone().bitPlane(3).imshow("forth slice image");
                cm.clone().bitPlane(4).imshow("fifth slice image");
                cm.clone().bitPlane(5).imshow("sixth slice image");
                cm.clone().bitPlane(6).imshow("seventh slice image");
                cm.clone().bitPlane(7).imshow("eighth slice image the most significant bit");

//        int a = 23;
//        System.out.println("a = " + Integer.toBinaryString(a));
//        System.out.println("a = " + FactoryUtils.formatBinary(8,a));
//        for (int i = 0; i < 8; i++) {
//            char c=Integer.toBinaryString(a).toCharArray()[i];
//            System.out.print(c);
//        }
//        System.out.println("");

    }

    private static String formatBinary(int n,int p) {
        char[] chars = new char[n];
        for (int j = 0; j < n; j++) {
            chars[j] = (char) (((p >>> (n - j - 1)) & 1) + '0');
        }
        System.out.println(chars);
        return String.valueOf(chars);
    }
}
