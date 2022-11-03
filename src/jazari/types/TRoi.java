/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.types;

import jazari.matrix.CPoint;
import java.awt.Point;

/**
 *
 * @author HP-pc
 */
public class TRoi {

    public Point cp;
    public CPoint centerPoint;
    public int pr;
    public int pc;
    public int width;
    public int height;

    @Override
    public String toString() {
        return pr + "," + pc + "," + width + "," + height;
    }
}
