/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.detection;

import ai.djl.Device;
import ai.djl.engine.Engine;

/**
 *
 * @author dell_lab
 */
public class DetectGPU {

    public static void main(String[] args) {
        System.out.println("GPU count: " + Engine.getInstance().getGpuCount());
        Device d = Device.gpu(1);
    }
}
