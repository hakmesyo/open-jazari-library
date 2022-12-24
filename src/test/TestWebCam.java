/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author dell_lab
 */
public class TestWebCam {

    public static void main(String[] args) {
        Dimension dim=new Dimension(1920,1080);
//        Dimension dim=new Dimension(1280,720);
//        Dimension dim=new Dimension(640,480);
        Webcam webcam = Webcam.getDefault();
        webcam.setCustomViewSizes(dim);
        webcam.setViewSize(dim);
//        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.addWebcamListener(new WebcamListener() {
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
                System.out.println("fps:"+webcam.getFPS());
            }
        }
        );

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setImageSizeDisplayed(true);

        JFrame window = new JFrame("Webcam");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(640,480));
        window.pack();
        window.setVisible(true);
    }

}
