/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

import java.util.List;

/**
 *
 * @author cezerilab
 */
public class YoloPolygonJson {
    public String version;
    public Flag flags;
    public List<Shape> shapes;
    public String imagePath;
    public String imageData;
    public int imageHeight;
    public int imageWidth;

    public static class Shape {
        public String label;
        public float[][] points;
        public String group_id;
        public String shape_type;
        public Flag flags;

        @Override
        public String toString() {
            return "Shape{" + "label=" + label + ", points=" + points + ", group_id=" + group_id + ", shape_type=" + shape_type + ", flags=" + flags + '}';
        }
        
        
    }

    @Override
    public String toString() {
        return "YoloPolygonJson{" + "version=" + version + ", flags=" + flags + ", shapes=" + shapes + ", imagePath=" + imagePath + ", imageData=" + imageData + ", imageHeight=" + imageHeight + ", imageWidth=" + imageWidth + '}';
    }

    public static class Flag {
    }
    
    
}
