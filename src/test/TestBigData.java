/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;
import jazari.types.TLearningType;

/**
 *
 * @author BAP1
 */
public class TestBigData {
    public static void main(String[] args) {
        CMatrix cm=CMatrix.getInstanceFromFile("data\\Kaggle_Digits_1000.txt", ",");
        CMatrix cm1=cm.cmd(":","1:end");
        CMatrix cm2=cm.cmd(":","0");
        CMatrix cm3=cm1.cat(1, cm2).showDataGrid().toWekaArff("data\\digit.arff",TLearningType.CLASSIFICATION);
    }
}
