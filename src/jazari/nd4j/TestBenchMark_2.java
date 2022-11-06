/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.nd4j;

import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 *
 * @author DELL LAB
 */
public class TestBenchMark_2 {
    public static void main(String[] args) {
        int n = 5000;
        CMatrix.getInstance().randn(n, n).mul(100).round();
        CMatrix.getInstance().randn(n, n).mul(100).round();
        long t = FactoryUtils.tic();
        int m=100;
        for (int i = 0; i < m; i++) {
//            float[][] d1=new float[m][m];
//            float[][] d2=new float[m][m];
            CMatrix cm1=CMatrix.getInstance().ones(n, n).mul(1.3f);
            CMatrix cm2=CMatrix.getInstance().ones(n, n).mul(3.4f);;
            System.gc();
        }
        t=FactoryUtils.toc(t);
    }
    
//equivalent python numpy code
/**
* 

import numpy as np
import timeit

n=5000
m=100
start_1 = timeit.default_timer() 
for i in range(m):
        # start = timeit.default_timer() 
        # d1=np.random.randn(n,n).dot(100).round()
        # d2=np.random.randn(n,n).dot(100).round()
        # d3=np.dot(d1,d2)
        # stop = timeit.default_timer()
        # print('Time: ', stop - start)
        
        d1=np.ones((n,n),dtype=np.float32())
        d1=d1*3.0;
        d2=np.ones((n,n),dtype=np.float32())
        d2=d2*1.5;

stop_1 = timeit.default_timer()
print('\nOverall Time: ', stop_1 - start_1)  
     
     */
    
}
