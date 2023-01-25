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
public class TestReIndexFilesBasedOnNanoTimeUniqueness {
    public static void main(String[] args) {
        String pathFolder="D:\\DATASETS\\_bla_bla";
        CMatrix cm = CMatrix.getInstance()
                .reIndexFilesBasedOnPrefixAndTimeStamp(pathFolder,"jpg,xml") //file names change to current nanotime where files having same name with different extension s ie. jpg and xml in case of annotation dataset to satisfy uniqueness 
                ;
    }
    
}
