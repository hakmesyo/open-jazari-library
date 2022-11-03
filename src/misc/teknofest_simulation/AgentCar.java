/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_simulation;

import static processing.core.PApplet.radians;
import processing.core.PImage;
import processing.core.PShape;

/**
 *
 * @author cezerilab
 */
/*-----------------------------------------------------------------------------------------------------------*/
public class AgentCar {
    Simulation sim;
    PShape shape;
    float size = 150;      //ground width and height
    float t = 1;           //ground thickness
    float dim1, dim2, dim3;
    float x, y, z;
    float angle = 0;
    public boolean isBraking = false;
    public boolean isTurnLeft = false;
    public boolean isTurnRight = false;
    public boolean isParking = false;
    int cnt = 0;
    PImage img;

    public AgentCar(Simulation sim,float dim1, float dim2, float dim3, float scale) {
        this.sim=sim;
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        shape = sim.loadShape(System.getProperty("user.dir") + "/data/obj/car_agent/car_agent.obj");
        shape.scale(scale);
    }

    public AgentCar(Simulation sim,AgentCar car, float dim1, float dim2, float dim3, float scale) {
        this.sim=sim;
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        shape = car.shape;
    }

    public void display(int ID, float x, float y, float z, float angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
        sim.pushStyle();
        sim.pushMatrix();
        if (ID == 0) {
            sim.translate(x, y, z);
            sim.rotateZ(radians(angle));

            //arabaının etrafındaki kırmızı dikdörtgeni çiz
            sim.strokeWeight(3);
            sim.stroke(0xFFFF0000);
            sim.noFill();
            sim.rect(-dim1 / 2, -dim2 / 2, dim1, dim2);

            //araba modelini 90 derece döndür ve çiz
            sim.rotateX(radians(90));
            sim.shape(shape);
        }
        if (ID == 1) {
            sim.rotateX(radians(90 + angle));
            sim.translate(x, y, z);
            sim.shape(shape);
            //rotateX(radians(-90));
            showSignal(ID);
        }
        sim.popMatrix();
        sim.popStyle();
    }

    private void showSignal(int ID) {
        sim.pushMatrix();
        float depth = -1.05f;
        float px = -0.675f;
        float py = 2.72f;
        float w = 0.3f;
        float h = 0.05f;
        float delta = 1f;
        int thr = 20;
        if (ID == 1) {
            sim.strokeWeight(1);
            sim.stroke(0, 0, 255);
            sim.fill(255, 0, 0);
            if (isParking || isTurnLeft || isTurnRight) {
                cnt++;
                if (cnt > 0 && cnt <= thr) {
                    if (isParking) {
                        sim.translate(px, py, depth);
                        sim.rect(x, y, w, h);
                        sim.translate(delta, 0, 0);
                        sim.rect(x, y, w, h);
                        sim.translate(-0.5f, 1.3f, 0);
                        sim.rect(x, y, w, h);
                        isBraking = false;
                    } else if (isTurnLeft) {
                        sim.translate(px, py, depth);
                        sim.rect(x, y, w, h);
                    } else if (isTurnRight) {
                        sim.translate(px + delta, py, depth);
                        sim.rect(x, y, w, h);
                    }
                    if (cnt == thr) {
                        cnt = -thr;
                    }
                }
            } else if (isBraking) {
                sim.translate(px, py, depth);
                sim.rect(x, y, w, h);
                sim.translate(delta, 0, 0);
                sim.rect(x, y, w, h);
                sim.translate(-0.5f, 1.3f, 0);
                sim.rect(x, y, w, h);
                isBraking = false;
            }
        }
        sim.popMatrix();
    }

    public void rotate(float angle) {
        this.angle = angle;
        sim.pushMatrix();
        sim.rotateZ(radians(angle));
        sim.shape(shape);
        sim.popMatrix();
    }
}
