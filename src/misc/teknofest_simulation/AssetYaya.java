/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_simulation;

import static processing.core.PApplet.radians;
import static processing.core.PConstants.PI;

/**
 *
 * @author cezerilab
 */
public class AssetYaya extends Asset{
    int state = 0;
    int prev_time = 0;

    public AssetYaya(Simulation sim, String filePath, String type, float px, float py, float rot) {
        super(sim, filePath, type, px, py, rot);
        shape = sim.loadShape(System.getProperty("user.dir") + "/" + filePath);
        //shape.scale(0.001f);
    }

    @Override
    public void display() {
        sim.pushMatrix();
        sim.translate(px, py, pz);
        sim.rotateZ(PI);
        sim.rotateZ(radians(-rot));
        sim.rotateX(PI/2);
        sim.shape(shape);
        sim.popMatrix();
    }
    
}
