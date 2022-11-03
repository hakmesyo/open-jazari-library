/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_simulation;

import ch.dieseite.colladaloader.wrappers.ColladaModel;
import static processing.core.PConstants.PI;
import processing.core.PShape;

/**
 *
 * @author cezerilab
 */
/*-----------------------------------------------------------------------------------------------------------*/
public class EntityParkur {
    Simulation sim;
    float size;
    float px;
    float py;
    float pz = -0.0f;
    PShape ps;
    String name;
    ColladaModel model;

    public EntityParkur(Simulation sim,ColladaModel model, String name, float px, float py) {
        this.sim=sim;
        this.model = model;
        this.model.scale(0.04f);
        this.name = name;
        this.py = py;
        this.px = px;
    }

    public void display() {
        sim.pushMatrix();
        sim.translate(px, py, pz);
        sim.rotateX(PI / 2);
        sim.rotateZ(PI);
        sim.rotateY(PI);
        model.draw();
        sim.popMatrix();
    }

}
