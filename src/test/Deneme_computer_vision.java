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
public class Deneme_computer_vision {

    static void crossProduct(int vect_A[], int vect_B[],int cross_P[]) {
        cross_P[0] = vect_A[1] * vect_B[2] - vect_A[2] * vect_B[1];
        cross_P[1] = vect_A[2] * vect_B[0] - vect_A[0] * vect_B[2];
    }

    public static void main(String[] args) {
        int[][] d={{3,12},{2,8}};
        CMatrix cm = CMatrix.getInstance().setArray(d).println().pinv().println();
        
//        CMatrix cm = CMatrix.getInstance().rand(5,5).println();
//        CMatrix cm_inv = cm.clone().inv().println();
//        CMatrix cm_eye = cm.clone().dot(cm_inv).round().println();
//        CMatrix v1 = CMatrix.getInstance().rand(5,1).println();
//        CMatrix cm_2 = v1.clone().transpose().dot(CMatrix.getInstance().eye(5)).println(); 
        
        //matrix multiplication için dot kullanılır
        //dot product dotProduct
//        CMatrix cm1 = CMatrix.getInstance().rand(5,5).println();
//        CMatrix cm2 = CMatrix.getInstance().rand(5,5).println();
//        CMatrix cm3 = cm1.clone().dot(cm2).println();
        
//        int vect_A[] = { 2,0};
//        int vect_B[] = { 0,2 };
//        int cross_P[] = new int[2];
//        System.out.println(Arrays.toString(cross_P));
//        
//        //9. cross product
//        CMatrix v1 = CMatrix.getInstance().setArray(2, 0).println();
//        CMatrix v2 = CMatrix.getInstance().setArray(0, 2).println();
//
//        CMatrix cm = v1.clone().transpose().mul(v2).println();

//        //8. vector projection or dot
//        CMatrix v1 = CMatrix.getInstance().setArray(2,2).println();
//        CMatrix v2 = CMatrix.getInstance().setArray(-2,2).println();
//        CMatrix cm1 = v2.clone().transpose().dot(v1).println();
//        //7. inner dot product
//        CMatrix v1 = CMatrix.getInstance()
//                .setArray(new float[]{3,4})
//                .println()
//                ;
//        CMatrix v2 = CMatrix.getInstance()
//                .setArray(new float[]{5,12})
//                .println()
//                ;
//        
//        CMatrix v3 = v1.clone().dotProduct(v2.clone()).println("v3");
//        
//        CMatrix derece = v1.clone().getAngle(v2.clone().transpose()).println();
//        
//        double t1=Math.toDegrees(Math.atan(4.0/3.0));
//        System.out.println("t1 = " + t1);
//        double t2=Math.toDegrees(Math.atan(12.0/5.0));
//        System.out.println("t2 = " + t2);
//        double t=t2-t1;
//        System.out.println("t = " + t);
//        CMatrix cm = v1.clone().magnitude().println("m1").timesScalar(v2.clone().magnitude().println("m2").getValue()).timesScalar(Math.cos(t*Math.PI/180)).println();
//        //6. multiply a vector with scalar
//        CMatrix cm = CMatrix.getInstance()
//                .rand(2,1)
//                .println()
//                .scale(2)
//                .println()
//                ;
//        //5. add two vectors
////        CMatrix v1 = CMatrix.getInstance().rand(2, 1).println();
////        CMatrix v2 = CMatrix.getInstance().rand(2, 1).println();
////        CMatrix cm = v1.clone().add(v2).println();
////        v1.println();
//        
//        CMatrix cm1 = CMatrix.getInstance()
//                .rand(2,1)
//                .println()
//                .minus(CMatrix.getInstance().rand(2, 1).println())
//                .println()
//                ;
//        double teta=45;
//        double tan_teta=Math.tan(teta*Math.PI/180);
//        double derece=Math.toDegrees(Math.asin(0.5));
//        System.out.println("derece = " + derece);
        //System.out.println("tan_teta = " + tan_teta);
        //4. make a vector having 5 elements
//        CMatrix cm = CMatrix.getInstance()                
//                .range(100) //vector
//                .println()
//                ;
//        CMatrix cm = CMatrix.getInstance()
//                .rand(1, 5)                
//                .println()
//                .transpose()
//                .println()
//                .plot()
//                ;
//        //3. show binary, grayscale, colored image
//        CMatrix cm = CMatrix.getInstance()
//                .imread("D:\\Dropbox\\NetbeansProjects\\open-jazari-library\\images\\bird.jpg")
//                .imshow()
//                ;
//        //2. make a sound (random) 1-D signal and represent it
//           CMatrix cm = CMatrix.getInstance()
//                   .rand(100, 1, 25, 75)
//                   //.transpose()
//                   .plot("1-D random signal")
//                   ;     
//        //1. make a 256x256 image with random binary pixels
//        CMatrix cm = CMatrix.getInstance()
//                .rand(256, 256)
//                .round()
//                .map(0, 255)
//                .println()
//                .imshow()
//                
//                ;
    }
}
