/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

import java.awt.Rectangle;
import java.util.Objects;

/**
 *
 * @author cezerilab
 */
public class BoundingBox {
    public int xmin;
    public int ymin;
    public int xmax;
    public int ymax;
    private Rectangle rect;
    public String name;
    public int fromLeft=0;
    public int fromTop=0;

    public BoundingBox(String name, Rectangle rect,int fromLeft,int fromTop) {
        this.name=name;
        this.rect=new Rectangle(rect);
        this.xmin = rect.x;
        this.ymin = rect.y;
        this.xmax = rect.x+rect.width;
        this.ymax = rect.y+rect.height;
        this.fromLeft=fromLeft;
        this.fromTop=fromTop;
    }
    
    public Rectangle getRectangle(int fromLeft,int fromTop,int padding){
        rect.x=xmin+fromLeft-padding;
        rect.y=ymin+fromTop-padding;
        rect.width=xmax-xmin+2*padding;
        rect.height=ymax-ymin+2*padding;
        return rect;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.xmin;
        hash = 31 * hash + this.ymin;
        hash = 31 * hash + this.xmax;
        hash = 31 * hash + this.ymax;
        hash = 31 * hash + Objects.hashCode(this.name);
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
        final BoundingBox other = (BoundingBox) obj;
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
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "\t\t<bndbox>\n"
                + "\t\t\t<xmin>" + (xmin-fromLeft) + "</xmin>\n"
                + "\t\t\t<ymin>" + (ymin-fromTop) + "</ymin>\n"
                + "\t\t\t<xmax>" + (xmax-fromLeft) + "</xmax>\n"
                + "\t\t\t<ymax>" + (ymax-fromTop) + "</ymax>\n"
                + "\t\t</bndbox>\n";
    }

    public int getWidth() {
        return Math.abs(xmax-xmin);
    }
    
    public int getHeight() {
        return Math.abs(ymax-ymin);
    }
    
}
