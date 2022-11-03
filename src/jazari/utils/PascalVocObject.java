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
public class PascalVocObject {
    public BoundingBox bndbox;

    public PascalVocObject(BoundingBox bndbox) {
        this.bndbox = bndbox;
    }

    @Override
    public String toString() {
        return "\t<object>\n"
                + "\t\t<name>" + bndbox.name + "</name>\n"
                + "\t\t<pose>Unspecified</pose>\n"
                + "\t\t<truncated>0</truncated>\n"
                + "\t\t<difficult>0</difficult>\n"
                + bndbox.toString()
                + "\t</object>\n";
    }
    
}
