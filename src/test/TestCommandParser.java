/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author BAP1
 */
public class TestCommandParser {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance().rand(10,5,0,100).round().println();
        CMatrix cm1=cm.cmd(5+":end",":").println();
        cm.cmd("1:3", "1:3").println();
    }
}
