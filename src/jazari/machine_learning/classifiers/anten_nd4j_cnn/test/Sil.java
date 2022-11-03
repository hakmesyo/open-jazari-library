/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.machine_learning.classifiers.anten_nd4j_cnn.test;

import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class Sil {
    public static void main(String[] args) {
        CMatrix cm_1 = CMatrix.getInstance().rand(529,26);
        CMatrix cm_2 = CMatrix.getInstance().rand(20,26).transpose();
        CMatrix cm = cm_1.mmul(cm_2).println();
    }
}
