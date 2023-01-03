/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import jazari.image_processing.ImageProcess;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestWebCam2 {
    public static void main(String[] args) {
        //değişti
        CMatrix cm = CMatrix.getInstance().startCamera(new Dimension(1280, 720),5);
//        CMatrix cm = CMatrix.getInstance().startCamera();
        
        //if you want to make image process on the image taken and then show on the current frame
        cm.addWebCamListener(new WebcamListener() {
            @Override
            public void webcamOpen(WebcamEvent we) {
            }

            @Override
            public void webcamClosed(WebcamEvent we) {
            }

            @Override
            public void webcamDisposed(WebcamEvent we) {
            }

            @Override
            public void webcamImageObtained(WebcamEvent we) {
                cm.webCam.setImageTransformer(new WebcamImageTransformer() {
                    @Override
                    public BufferedImage transform(BufferedImage img) {
                        img = ImageProcess.drawRectangle(img, 50, 50, 250, 150, 3, Color.yellow);
                        return img;
                    }
                });
            }
        });
    }
}
