/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_simulation;

import org.opencv.core.Mat;
import static processing.core.PApplet.radians;
import static processing.core.PConstants.PI;

/**
 *
 * @author cezerilab
 */
public class AssetTrafficLight extends Asset{
    int state = 0;
    int prev_time = 0;

    public AssetTrafficLight(Simulation sim, String filePath, String type, float px, float py, float rot) {
        super(sim, filePath, type, px, py, rot);
        shape = sim.loadShape(System.getProperty("user.dir") + "/" + filePath);
        shape.scale(0.001f);
        state=(int)(Math.random()*3);
    }

    @Override
    public void display() {
        sim.pushMatrix();
        sim.translate(px, py, pz);
//        sim.rotateZ(radians(180 - rot));
        sim.rotateZ(PI);
        sim.rotateZ(radians(-rot));
        sim.rotateX(PI/2);
        sim.shape(shape);
        sim.popMatrix();
//            println("state:"+state);

        //RED
        if (state == 0) {
            if (sim.millis() - prev_time > sim.WAITING_TIME_FOR_RED * 1000) {
                state++;
                prev_time = sim.millis();
                return;
            }
            sim.pushMatrix();
            sim.fill(sim.color(255, 0, 0));
            if (rot == 0) {
                sim.translate(px, py + 0.11f, pz + 2.75f);
                sim.rotateX(PI / 2);
            } else if (rot == 90) {
                sim.translate(px + 0.11f, py, pz + 2.75f);
                sim.rotateY(radians(rot));
            } else if (rot == 180) {
                sim.translate(px, py - 0.11f, pz + 2.75f);
                sim.rotateX(PI / 2);
            } else if (rot == 270) {
                sim.translate(px - 0.11f, py, pz + 2.75f);
                sim.rotateY(radians(rot));
            }
            sim.ellipse(0, 0, 0.22f, 0.22f);
            sim.popMatrix();
        } //YELLOW
        else if (state == 1) {
            if (sim.millis() - prev_time > sim.WAITING_TIME_FOR_YELLOW * 1000) {
                state++;
                prev_time = sim.millis();
                return;
            }
            sim.pushMatrix();
            sim.fill(sim.color(255, 255, 0));
            if (rot == 0) {
                sim.translate(px, py + 0.11f, pz + 2.45f);
                sim.rotateX(PI / 2);
            } else if (rot == 90) {
                sim.translate(px + 0.11f, py, pz + 2.45f);
                sim.rotateY(radians(rot));
            } else if (rot == 180) {
                sim.translate(px, py - 0.11f, pz + 2.45f);
                sim.rotateX(PI / 2);
            } else if (rot == 270) {
                sim.translate(px - 0.11f, py, pz + 2.45f);
                sim.rotateY(radians(rot));
            }
            sim.ellipse(0, 0, 0.22f, 0.22f);
            sim.popMatrix();
        } //GREEN
        else if (state == 2) {
            if (sim.millis() - prev_time > sim.WAITING_TIME_FOR_GREEN * 1000) {
                state = 0;
                prev_time = sim.millis();
                return;
            }
            sim.pushMatrix();
            sim.fill(sim.color(0, 255, 0));
            if (rot == 0) {
                sim.translate(px, py + 0.11f, pz + 2.15f);
                sim.rotateX(PI / 2);
            } else if (rot == 90) {
                sim.translate(px + 0.11f, py, pz + 2.15f);
                sim.rotateY(radians(rot));
            } else if (rot == 180) {
                sim.translate(px, py - 0.11f, pz + 2.15f);
                sim.rotateX(PI / 2);
            } else if (rot == 270) {
                sim.translate(px - 0.11f, py, pz + 2.15f);
                sim.rotateY(radians(rot));
            }
            sim.ellipse(0, 0, 0.22f, 0.22f);
            sim.popMatrix();
        }
    }
    
}
