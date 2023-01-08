/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils.pascalvoc;

import java.awt.Color;
import javax.swing.JColorChooser;

/**
 *
 * @author cezerilab
 */
public class TestColorChooser {
    public static void main(String[] args) {
        Color color=JColorChooser.showDialog(null, "", Color.yellow);
        System.out.println("color = " + color);
    }
}
