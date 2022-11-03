/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_simulation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cezerilab
 */
public class AssetNode extends Asset{
    public boolean isVisible=false;
    public String uuid;
    public List<AssetNode> listNeighbor=new ArrayList();
    
    public AssetNode(Simulation sim, String uuid,String filePath, String type, float px, float py, float rot,boolean isVisible) {
        super(sim, filePath, type, px, py, rot);
        this.isVisible=isVisible;
        this.uuid=uuid;
    }

    @Override
    public void display() {
    }
    
}
