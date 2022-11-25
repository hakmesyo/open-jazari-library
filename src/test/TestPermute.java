/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.factory.FactoryMatrix;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;
import org.nd4j.linalg.cpu.nativecpu.NDArray;

/**
 *
 * @author cezerilab
 */
public class TestPermute {
    public static void main(String[] args) {
                
//        //raw data
//        NDArray d=new NDArray(FactoryUtils.vector(0, 1200),new int[]{60,20});
//        CMatrix cm1 = CMatrix.getInstance(d)
//                .println()
//                .reshape(5,4,6,5,2)
//                .println()
//                .permute(3,4,0,1,2)
//                .println()
//                ;
        
//on batch images
        String folderPath="D:\\zeytin_remote_sensing\\tif\\deneme";
        CMatrix cm = CMatrix.getInstance()
                .range(0, 1200)
                .shape()
                .imageDataGenerator(folderPath,100,50)  //NCWH
                .shape()
                .slice(":",":-1")
                .shape()
                .flatten()
                .shape()
                .println()
//                .reshape(50,100)
//                .imshow()
                ;
        
//        float[][] d2=CMatrix.getInstance().rand(2, 3, 0, 10).round().toFloatArray2D();
//        CMatrix.getInstance(d2).println();
//        float[] d3=FactoryUtils.flatten(d2);
//        CMatrix.getInstance(d3).println();
        

    }
}
