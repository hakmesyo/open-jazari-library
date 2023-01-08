/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils.pascalvoc;

/**
 *
 * @author cezerilab
 */
public class PascalVocSize {
    public int width;
    public int height;
    public int depth;

    public PascalVocSize(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "\t<size>\n"
                + "\t\t<width>" + width + "</width>\n"
                + "\t\t<height>" + height + "</height>\n"
                + "\t\t<depth>" + depth + "</depth>\n"
                + "\t</size>\n";

    }
    
}
