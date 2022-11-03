/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

import java.awt.Rectangle;

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
    
    public Rectangle getRectangle(int fromLeft,int fromTop){
        rect.x=xmin+fromLeft;
        rect.y=ymin+fromTop;
        rect.width=xmax-xmin;
        rect.height=ymax-ymin;
        return rect;
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
    
}
