/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

/**
 *
 * @author cezerilab
 */
public class BoundingBoxYolo {
    public int class_index;
    public float x1,y1,x2,y2;

    public BoundingBoxYolo(int class_index, float x1, float y1, float x2, float y2) {
        this.class_index = class_index;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public String toString() {
        return class_index + " " + x1 + " " + y1 + " " + x2 + " " + y2;
    }
    
    
    
    
}
