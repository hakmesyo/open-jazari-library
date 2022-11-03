/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_BGR;
import java.util.List;

/**
 *
 * @author cezerilab
 */
public class BoundingBoxPascalVOC {

    public String folder;
    public String fileName;
    public String path;
    public PascalVocSize size;
    public BufferedImage image;
    public List<PascalVocObject> lstObjects;

    public BoundingBoxPascalVOC(String folder, String fileName, String path, BufferedImage image, List<PascalVocObject> lstObjects) {
        this.folder = folder;
        this.fileName = fileName;
        this.path = path;
        int type = image.getType();
        int depth = 3;
        if (type == TYPE_INT_BGR) {
            depth = 3;
        }
        this.size = new PascalVocSize(image.getWidth(), image.getHeight(), depth);
        this.lstObjects = lstObjects;
    }

    public BoundingBoxPascalVOC() {

    }

    @Override
    public String toString() {
        String lst = "";
        for (PascalVocObject obj : lstObjects) {
            lst += obj.toString();
        }
        return "<annotation>\n"
                + "\t<folder>" + folder + "</folder>\n"
                + "\t<filename>" + fileName + "</filename>\n"
                + "\t<path>" + path + "</path>\n"
                + "\t<source>\n"
                + "\t\t<database>Unknown</database>\n"
                + "\t</source>\n"
                + size.toString()
                + "\t<segmented>0</segmented>\n"
                + lst
                + "</annotation>\n";

    }

}


