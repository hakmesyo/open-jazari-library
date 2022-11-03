/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.h2d_mlp;

import jazari.matrix.CMatrix;
import java.util.Random;

/**
 *
 * @author DELL LAB
 */
public class Channel {
    Node[][] nodes;
    Bias bias;
    int nchannels;
    int nrows;
    int ncols;
    int size;
    ActivationType activationType;
    LayerType layerType;
    public Random rnd;
    MlpLayer layer;
    MlpLayer prevLayer;
    MlpLayer nextLayer;

    public Channel(MlpLayer layer,int nrows,int ncols,int nch) {
        this.layer=layer;
        this.activationType=layer.activationType;
        this.layerType=layer.layerType;
        this.nrows=nrows;
        this.ncols=ncols;
        this.nchannels=nch;
        this.nodes = prepareLayer();
        this.rnd=layer.rnd;
        this.bias=new Bias(Utils.getRandomWeight(this.rnd));
    }
    
    private Node[][] prepareLayer() {
        nodes = new Node[nrows][ncols];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                nodes[i][j] = new Node(this, i, j);
            }
        }
        return nodes;
    }

    public void feed(float[][] d) {
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                nodes[i][j].data = d[i][j] / 255.0f;
            }
        }
    }

    public float applyActivation(float sum) {
        if (activationType == ActivationType.sigmoid) {
            return sigmoid(sum);
        } else if (activationType == ActivationType.relu) {
            return relu(sum);
        } else {
            return -1.0f;
        }
    }

    public float applyActivation(float sum, ActivationType type) {
        if (type == ActivationType.sigmoid) {
            return sigmoid(sum);
        } else if (type == ActivationType.relu) {
            return relu(sum);
        } else {
            return -1.0f;
        }
    }

    private float sigmoid(float x) {
        float ret = (float)(1 / (1 + Math.pow(Math.E, -1 * x)));
        return ret;
    }
    
    public float sigmoidDerivative(float x){
        float ret=sigmoid(x)*(1-sigmoid(x));
        return ret;
    }

    private float relu(float x) {
        float ret = Math.max(0, x);
        return ret;
    }
    
    private float reluDerivative(float x) {
        float ret = 0;
        if (x>0) {
            ret=1;
        }
        return ret;
    }

    public void softmax() {
        float exp_z_sum = 0.0f;
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                exp_z_sum += Math.pow(Math.E, nodes[i][j].data);
            }
        }
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                nodes[i][j].data = (float)Math.pow(Math.E, nodes[i][j].data) / exp_z_sum;
            }
        }
    }

    public void streamData() {
        System.out.println("");
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                System.out.println(nodes[i][j].data);
            }

        }
    }

    public float[][] toArray2D() {
        float[][] d = new float[nrows][ncols];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                d[i][j] = nodes[i][j].data;
            }
        }
        return d;
    }

    public float getLossFromAbsDifference(float[] y) {
        float ret = 0;
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                ret += Math.abs(nodes[i][j].data - y[i]);
            }
        }
        return ret;
    }

    public float getLoss() {
        float ret = CMatrix.getInstance(toArray2D()).sumTotal();
        return ret;
    }

    public float[][] getWeights() {
        /*
        float[][] ret;
        if (nextLayer.layerType == LayerType.output) {
            ret = new float[size][nextLayer.size];
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    Node node = nodes[i][j];
                    ret[i*ncols+j] = node.weightsToOutputLayer;
                }
            }
        } else {
            ret = new float[1][size];
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    Node node = nodes[i][j];
                    ret[0][i*ncols+j] = node.weight;
                }
            }
        }
        return ret;
*/
        return null;
    }
    
}
