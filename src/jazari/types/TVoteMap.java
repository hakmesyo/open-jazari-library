/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jazari.types;

import jazari.factory.FactoryUtils;
import java.io.Serializable;

/**
 *
 * @author BAP1
 */
public class TVoteMap implements Serializable{
    public String name;
    public int index;
    public float value;

    public String toString() {
        String s = name + ":" + FactoryUtils.formatDouble(value);
        System.out.println(s);
        return s;
    }
    
}
