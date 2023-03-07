/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;
import jazari.types.TMatrixOperator;

/**
 *
 * @author cezerilab
 */
public class Deneme2 {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .rand(5,3)
                .dump()
                ;
        cm.clone().mean().mean().println();
        cm.clone().std().std().println();
        
//        CMatrix cm = CMatrix.getInstance()
//                .rand(4,3,0,30)
//                .round()
//                .println()
//                
//                
//                ;
//        CMatrix cm_index = cm.clone().findIndex(TMatrixOperator.GREATER, 15).println();
//        CMatrix cm_value = cm.clone().findValuesByIndex(cm_index).println();

//        CMatrix cm_value = cm.clone().reshape(12, 1).println();
//        float f=cm_value.getValue((int)cm_index.getValue(0, 0), 0);
//        System.out.println("cm_value = " +f );
    }
}
