/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.modsim;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import jazari.matrix.CMatrix;

/**
 *
 * @author dell_lab
 */
public class Deneme {
    public static void main(String[] args) {
        CMatrix cm = CMatrix.getInstance()
                .imread("images/teknofest/test/0.jpg")
                .imshow()
                
                ;
        
    }

    
}
