/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils.pascalvoc;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Objects;

/**
 *
 * @author cezerilab
 */
public class PascalVocBoundingBox {

    public int xmin;
    public int ymin;
    public int xmax;
    public int ymax;
    private Rectangle rect;
    public int fromLeft = 0;
    public int fromTop = 0;
    public String name;
    public Color color = Color.yellow;

    public PascalVocBoundingBox(String name, Rectangle rect, int fromLeft, int fromTop, Color color) {
        this.rect = new Rectangle(rect);
        this.xmin = rect.x;
        this.ymin = rect.y;
        this.xmax = rect.x + rect.width;
        this.ymax = rect.y + rect.height;
        this.fromLeft = fromLeft;
        this.fromTop = fromTop;
        this.name = name;
        this.color = (color != null) ? color : Color.yellow;
    }

    public Rectangle getRectangle(int fromLeft, int fromTop, int padding) {
        rect.x = xmin + fromLeft - padding;
        rect.y = ymin + fromTop - padding;
        rect.width = xmax - xmin + 2 * padding;
        rect.height = ymax - ymin + 2 * padding;
        return rect;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.xmin;
        hash = 31 * hash + this.ymin;
        hash = 31 * hash + this.xmax;
        hash = 31 * hash + this.ymax;
        hash = 31 * hash + this.fromLeft;
        hash = 31 * hash + this.fromTop;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PascalVocBoundingBox other = (PascalVocBoundingBox) obj;
        if (this.xmin != other.xmin) {
            return false;
        }
        if (this.ymin != other.ymin) {
            return false;
        }
        if (this.xmax != other.xmax) {
            return false;
        }
        if (this.ymax != other.ymax) {
            return false;
        }
        if (this.fromLeft != other.fromLeft) {
            return false;
        }
        if (this.fromTop != other.fromTop) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\t\t<bndbox>\n"
                + "\t\t\t<xmin>" + (xmin - fromLeft) + "</xmin>\n"
                + "\t\t\t<ymin>" + (ymin - fromTop) + "</ymin>\n"
                + "\t\t\t<xmax>" + (xmax - fromLeft) + "</xmax>\n"
                + "\t\t\t<ymax>" + (ymax - fromTop) + "</ymax>\n"
                + "\t\t</bndbox>\n";
    }

    public int getWidth() {
        return Math.abs(xmax - xmin);
    }

    public int getHeight() {
        return Math.abs(ymax - ymin);
    }

}
