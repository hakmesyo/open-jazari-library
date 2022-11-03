/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.teknofest_scenario;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import jazari.factory.FactoryUtils;
import jazari.image_processing.ImageProcess;

/**
 *
 * @author cezerilab
 */
public class AssetComponent {

    BufferedImage img;
    PanelScenario ps;
    int mx;
    int my;
    float v_px;
    float v_py;
    int width = 20;
    int height = 20;
    boolean isSelected = false;
    boolean isDoubleClicked = false;
    int rot = 0;
    String name;
    List<AssetComponent> listNeighborNodes = new ArrayList();
    String uuid;

    public AssetComponent(PanelScenario ps, String uuid, BufferedImage img, String name, int rot, int mx, int my, float v_px, float v_py) {
        this.img = ImageProcess.clone(img);
        this.mx = mx;
        this.my = my;
        this.v_px = v_px;
        this.v_py = v_py;
        this.ps = ps;
        this.name = name;
        this.rot = rot;
        this.uuid = (uuid == null) ? FactoryUtils.getUUID() : uuid;
    }

    public void draw() {
        if (isSelected && !isDoubleClicked) {
            ps.gr.drawImage(this.img, this.mx, this.my, this.width, this.height, ps);
            ps.gr.setColor(Color.green);
            ps.gr.setStroke(new BasicStroke(5));
            ps.gr.drawRect(mx - 5, my - 5, width + 10, height + 10);
            ps.gr.setColor(Color.red);
            ps.gr.setStroke(new BasicStroke(2));
            ps.gr.drawRect(mx - 10, my - 10, 10, 10);
            ps.gr.drawRect(mx + width, my - 10, 10, 10);
            ps.gr.drawRect(mx - 10, my + height, 10, 10);
            ps.gr.drawRect(mx + width, my + height, 10, 10);
        } else if (isDoubleClicked) {
            ps.gr.drawImage(this.img, this.mx, this.my, this.width, this.height, ps);
            ps.gr.setColor(Color.yellow);
            ps.gr.setStroke(new BasicStroke(7));
            ps.gr.drawRect(mx - 5, my - 5, width + 10, height + 10);
        } else {
            ps.gr.drawImage(this.img, this.mx, this.my, this.width, this.height, ps);
        }
    }

    public void addNeighborNode(AssetComponent node) {
        for (AssetComponent nd : listNeighborNodes) {
            if (nd.mx == node.mx && nd.my == node.my && nd.v_px == node.v_px && nd.v_py == node.v_py) {
                return;
            }
        }
        listNeighborNodes.add(node);
    }

    public int reducedHashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Float.floatToIntBits(this.v_px);
        hash = 29 * hash + Float.floatToIntBits(this.v_py);
        hash = 29 * hash + Float.floatToIntBits(this.rot);
        return hash;
    }

    @Override
    public String toString() {
        return "AssetComponent{" + "rotation=" + rot + ", px=" + mx + ", py=" + my + ", v_px=" + v_px + ", v_py=" + v_py + ", width=" + width + ", height=" + height + '}';
    }

    public String toScript() {
        String s = "asset=name:" + name + ",uuid:" + uuid + ",rot:" + rot + ",mx:" + mx + ",my:" + my + ",vx:" + v_px + ",vy:" + v_py;
        if (name.equals("node_pin_point") && !listNeighborNodes.isEmpty()) {
            s += ",";
            for (AssetComponent node : listNeighborNodes) {
//                s+="[mx:"+node.mx+";my:"+node.my+";vx:"+node.v_px+";vy:"+node.v_py+"] ";
                s += node.uuid + ";";
            }
        }
        return s;
    }

    public void setPosition(int mx, int my, float px, float py) {
        this.mx = mx;
        this.my = my;
        this.v_px = px;
        this.v_py = py;
    }

    public void setRotationDegree(int rot, boolean isUpdateScript) {
        if (rot <= 360 && rot >= -360) {
            if (isUpdateScript) {
                this.img = ImageProcess.rotateImage(img, -this.rot);
                this.rot = rot;
                this.img = ImageProcess.rotateImage(img, rot);
                ps.updateScript();
            }else{
                this.rot = rot;
                this.img = ImageProcess.rotateImage(img, this.rot);                
            }
        } else {
            JOptionPane.showMessageDialog(null, "rotation degree must in between -360 - 360");
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.img);
        hash = 97 * hash + Objects.hashCode(this.ps);
        hash = 97 * hash + this.mx;
        hash = 97 * hash + this.my;
        hash = 97 * hash + Float.floatToIntBits(this.v_px);
        hash = 97 * hash + Float.floatToIntBits(this.v_py);
        hash = 97 * hash + this.width;
        hash = 97 * hash + this.height;
        hash = 97 * hash + (this.isSelected ? 1 : 0);
        hash = 97 * hash + Float.floatToIntBits(this.rot);
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final AssetComponent other = (AssetComponent) obj;
        if (this.mx != other.mx) {
            return false;
        }
        if (this.my != other.my) {
            return false;
        }
        if (Float.floatToIntBits(this.v_px) != Float.floatToIntBits(other.v_px)) {
            return false;
        }
        if (Float.floatToIntBits(this.v_py) != Float.floatToIntBits(other.v_py)) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (this.isSelected != other.isSelected) {
            return false;
        }
        if (Float.floatToIntBits(this.rot) != Float.floatToIntBits(other.rot)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.img, other.img)) {
            return false;
        }
        if (!Objects.equals(this.ps, other.ps)) {
            return false;
        }
        return true;
    }

}
