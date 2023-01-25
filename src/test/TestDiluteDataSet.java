/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestDiluteDataSet {
    public static void main(String[] args) {
        String pathFolder="D:\\DATASETS\\_bla_bla";
        CMatrix cm = CMatrix.getInstance()
                .diluteDataSet(pathFolder, 0.1f, 123, "jpg")  //dataset reordered randomly based on the seed "123" given and 10% of the dataset is selected. Aim is shrinking the dataset 
                //.reIndexFilesBasedOnPrefixAndTimeStamp(pathFolder,"jpg,xml")
                ;
        
    }    
}
