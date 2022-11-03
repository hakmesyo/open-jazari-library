/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_scenario;

import java.awt.BasicStroke;
import java.awt.Color;
import jazari.factory.FactoryUtils;

/**
 *
 * @author cezerilab
 */
public class WayPoint {

    public int mx1;
    public int my1;
    public int mx2;
    public int my2;
    public int mlength;
    public float vx1;
    public float vy1;
    public float vx2;
    public float vy2;
    public float vlength;
    PanelScenario ps;

    public WayPoint(PanelScenario ps, int mx1, int my1, int mx2, int my2, float vx1, float vy1, float vx2, float vy2) {
        this.mx1 = mx1;
        this.my1 = my1;
        this.mx2 = mx2;
        this.my2 = my2;
        this.mlength = (int) FactoryUtils.getEucledianDistance(mx1, my1, mx2, my2);
        this.vx1 = vx1;
        this.vy1 = vy1;
        this.vx2 = vx2;
        this.vy2 = vy2;
        this.vlength = FactoryUtils.getEucledianDistance(vx1, vy1, vx2, vy2);
        this.ps=ps;
    }

    public void updatePinPoint(int mx1, int my1, int mx2, int my2, float vx1, float vy1, float vx2, float vy2){
        this.mx1 = mx1;
        this.my1 = my1;
        this.mx2 = mx2;
        this.my2 = my2;
        this.mlength = (int) FactoryUtils.getEucledianDistance(mx1, my1, mx2, my2);
        this.vx1 = vx1;
        this.vy1 = vy1;
        this.vx2 = vx2;
        this.vy2 = vy2;
        this.vlength = FactoryUtils.getEucledianDistance(vx1, vy1, vx2, vy2);
    }
    
    public int getMouseDistance(){
        return mlength;
    }

    public float getVirtualDistance(){
        return vlength;
    }

    @Override
    public String toString() {
        return "WayPoint{" + "mx1=" + mx1 + ", my1=" + my1 + ", mx2=" + mx2 + ", my2=" + my2 + ", mlength=" + mlength + ", vx1=" + vx1 + ", vy1=" + vy1 + ", vx2=" + vx2 + ", vy2=" + vy2 + ", vlength=" + vlength + '}';
    }
    
    public void draw() {
        int r=6;
        int rr=4;
        ps.gr.setStroke(new BasicStroke(2));
        ps.gr.setColor(Color.yellow);
        ps.gr.drawOval(mx1-r, my1-r, 2*r, 2*r);
        ps.gr.drawOval(mx2-r, my2-r, 2*r, 2*r);
        ps.gr.setColor(Color.blue);
        ps.gr.fillOval(mx1-r, my1-r, 2*r, 2*r);
        ps.gr.fillOval(mx2-r, my2-r, 2*r, 2*r);
        //
        ps.gr.setColor(Color.green);
        ps.gr.drawLine(mx1, my1, mx2, my2);
    }

}
