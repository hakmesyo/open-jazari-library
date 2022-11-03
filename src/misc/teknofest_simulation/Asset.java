/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_simulation;

import java.util.Objects;
import processing.core.PShape;

/**
 *
 * @author cezerilab
 */
public abstract class Asset {
    Simulation sim;
    PShape shape;
    String type;
    String filePath;
    float px;
    float py;
    float pz;
    float rot;
    float scale;
    boolean isVisible=false;
    
    public Asset(Simulation sim,String filePath,String type,float px,float py,float rot){
        this.sim=sim;
        this.filePath=filePath;
        this.px=px;
        this.py=py;
        this.rot=rot;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Float.floatToIntBits(this.px);
        hash = 29 * hash + Float.floatToIntBits(this.py);
        hash = 29 * hash + Float.floatToIntBits(this.rot);
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
        final Asset other = (Asset) obj;
        return true;
    }
    
    public abstract void display();
    
}
