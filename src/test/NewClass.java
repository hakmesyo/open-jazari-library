/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author dell_lab
 */
public class NewClass {

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            long t1=FactoryUtils.tic();
//            LongStream.range(1,100).boxed().map(e -> e*e);
//                    //.forEach(System.out::println);
//            t1=FactoryUtils.toc(t1);
//        }
        
        long t1=FactoryUtils.tic();
        int n=100;
        int m=1000;
        CMatrix cm = CMatrix.getInstance().range(1000);
        int[] d=cm.toIntArray1D();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //cm=cm.pow(2);
                for (int k = 0; k < m; k++) {
                    d[k]=d[k]*d[k];
                }
            }
            
        }
        t1=FactoryUtils.toc(t1);
    }

}
