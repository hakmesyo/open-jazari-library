/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_simulation;

/**
 *
 * @author cezerilab
 */
public class AssetDynamicObject extends Asset{

    public AssetDynamicObject(Simulation sim, String filePath, String type, float px, float py, float rot) {
        super(sim, filePath, type, px, py, rot);
    }

    @Override
    public void display() {
    }
    
}
