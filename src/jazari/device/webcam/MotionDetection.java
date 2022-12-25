/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.device.webcam;

import jazari.factory.FactoryWebCam;

/**
 *
 * @author Science
 */
public class MotionDetection {
    public static void main(String[] args) {
       new FactoryWebCam().openWebCam(0).startWithGUI().startMotionDetectionVideo("C:\\recorded_videos");
        
    }
}
