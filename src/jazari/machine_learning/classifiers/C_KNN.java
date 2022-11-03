/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.machine_learning.classifiers;

import jazari.matrix.CMatrix;
import jazari.factory.FactoryUtils;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author BAP1
 */
public class C_KNN {
    public static float[][] train=CMatrix.getInstance().rand(200,7).showDataGrid().toFloatArray2D();
    public static float[][] test=CMatrix.getInstance().rand(50,7).toFloatArray2D();
    
    public static void main(String[] args) {
        float[] predict=computeKNN(train, test, 3);
        float[] actual=FactoryUtils.transpose(test)[4];
        CMatrix cm=CMatrix.getInstance(predict).cat(1, CMatrix.getInstance(actual)).plot();
    }
    
    public static float[] computeKNN(float[][] train,float[][] test,int K){
        float[] p=new float[test.length];
        for (int i = 0; i < p.length; i++) {
            p[i]=getKNN(train,test[i],K);
        }
        return p;
    }

    public static float getKNN(float[][] train, float[] test, int K) {
        float ret=0;
        ArrayList<Float> lst=new ArrayList<Float>();
        for (int i = 0; i < train.length; i++) {
            float[] tr=train[i];
            float dist=FactoryUtils.getEucledianDistanceExceptLastElement(tr,test);
            System.out.println("dist = " + dist);
            float dist2=CMatrix.getInstance(tr).cmd(":","0:end").minus(CMatrix.getInstance(test)).pow(2).sum().sqrt().getValue();
            System.out.println("dist2 = " + dist2);
            lst.add(dist);
        }
        Double[] d=new Double[lst.size()];
        d=lst.toArray(d);
        Arrays.sort(d);
        for (int i = 0; i < K; i++) {
            ret+=d[i];
        }
        ret=ret/K;
        return ret;
    }
    
}
