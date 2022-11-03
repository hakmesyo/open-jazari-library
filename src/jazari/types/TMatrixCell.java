/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jazari.types;

import jazari.matrix.CPoint;
import java.awt.Point;

/**
 *
 * @author BAP1
 */
public class TMatrixCell {
    public CPoint p=new CPoint();
    public double value;
    public long index;
    
    public String toString(){
        return "[index:"+index+", value:"+value+", position(row,column):["+p.row+","+p.column+"]]";
    }
    
    public void println(){
        System.out.println(this.toString());
    }
}
