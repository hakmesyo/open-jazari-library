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
public class TestHistWithInfo {
    public static void main(String[] args) {
        String path="C:\\ai\\djl\\dataset_pdr\\AnonimTC";
        
        String path_female=path+"\\female_tc_age.txt";
        CMatrix cm_female = CMatrix.getInstance().readFile(path_female,";");
        CMatrix cm_hist_female=cm_female.cmd(":","1").sort().println().hist(100).println();
        
//        String path_male=path+"\\male_tc_age.txt";
//        CMatrix cm_male = CMatrix.getInstance().readFile(path_male,";");
//        CMatrix cm_hist_male=cm_male.cmd(":","1").sort().println().hist(100).println();
        
//        CMatrix cm=cm_hist_female.cat(1, cm_hist_male).bar();
        //CMatrix cm_ages=cm.cmd(":","1").distinct().println();
        
    }
    
}
