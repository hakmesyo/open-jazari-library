/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils.pascalvoc;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_BGR;
import java.util.List;

/**
 *
 * @author cezerilab
 */
public class AnnotationPascalVOCFormat {

    public String folder;
    public String fileName;
    public PascalVocSource source = new PascalVocSource();
    public String imagePath;
    public PascalVocSize size;
    public BufferedImage image;
    public List<PascalVocObject> lstObjects;

    public AnnotationPascalVOCFormat(String folder, String fileName, PascalVocSource source, String path, BufferedImage image, List<PascalVocObject> lstObjects) {
        this.folder = folder;
        this.fileName = fileName;
        this.imagePath = path;
        this.source = (source != null) ? source : new PascalVocSource();
        int type = image.getType();
        int depth = 3;
        if (type == TYPE_INT_BGR) {
            depth = 3;
        }
        this.size = new PascalVocSize(image.getWidth(), image.getHeight(), depth);
        this.lstObjects = lstObjects;
    }

    public AnnotationPascalVOCFormat() {

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
                + "\t<path>" + imagePath + "</path>\n"
                + source.toString()
                + size.toString()
                + "\t<segmented>0</segmented>\n"
                + lst
                + "</annotation>\n";

    }

}
