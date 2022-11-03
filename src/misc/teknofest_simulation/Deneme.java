/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_simulation;

import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class Deneme {
    public static void main(String[] args) {
        //y 150-500 : 350
        //x 600-1185 : 585
//        CMatrix cm = CMatrix.getInstance()
//                .imread("images/resm.jpg")
//                .imshow()
//                ;
        CMatrix cm = CMatrix.getInstance()
                .imread("data/obj_detection/recorded_images/1_screen.png")
                .imshow();

    }
}
