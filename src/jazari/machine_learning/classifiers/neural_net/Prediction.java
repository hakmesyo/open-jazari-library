/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.machine_learning.classifiers.neural_net;

/**
 *
 * @author cezerilab
 */
public class Prediction {
    double classIndex=0;
    double confidenceLevel=0;
    boolean hit=false;

    @Override
    public String toString() {
        return "Prediction{" + "hit=" + hit+", classIndex=" + classIndex + ", confidenceLevel=" + confidenceLevel + '}';
    }
    
    
}
