/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Arrays;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class DenemeMatlabFunctions {
    public static void main(String[] args) {
//        CMatrix cm = CMatrix.getInstance()
//                .imread("images/babun.jpg")
//                .imshow("original babun image")
//                .rgb2gray()
//                .imshow()
//                //.imwrite("images/babun_1.png")
//                ;
//        CMatrix cm2 = cm.clone().hist(25).println();
        //CMatrix cm_red = CMatrix.getInstance().values(90, 150, 0).reshape(3,90,50).cmd("0", ":").println();
//        CMatrix cm_green = cm_red.clone();
//        CMatrix cm_blue = cm_red.clone();

        CMatrix cm = CMatrix.getInstance()
                .imread("C:\\Users\\cezerilab\\Desktop/ideal_contrast.jpg")
                .rgb2gray()
                .imshow()
                
                ;
        int[] indexes=cm.clone().shufflePixelImage().imshow().shuffleIndexes;
        System.out.println(Arrays.toString(indexes));
        
        //cm.clone().threshold(100).imshow();
        //cm.clone().rgb2hsv().imcomplement().imshow();
        
//                cm.clone().getRedChannelColor().imshow();
//                cm.clone().getGreenChannelColor().imshow();
//                cm.clone().getBlueChannelColor().imshow();
        
        
    }
}
