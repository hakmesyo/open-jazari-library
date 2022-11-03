/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author dell_lab
 */
public class TestFlatten {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .rand(5, 3)
                .map(10, 50)
                .round()
                .dump()
                //.flatten()
                //.dump()
//                .saveNewFileAsCSV("data/p1.txt")
                .saveOnFileAsCSV("data/p1.txt")
                ;
        
    }
}
