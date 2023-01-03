/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.device.webcam;

import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamListener;
import java.awt.image.BufferedImage;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestJimClientWebCam {

    public static long t1 = System.currentTimeMillis();
    public static long t2 = FactoryUtils.tic();
    public static JimConnection ref;
    public static int fps=10;
    public static int wait=1000/fps;

    public static void main(String[] args) {
        ref = new JimConnection();
        ref.startJimCommunication("localhost", "8887",wait);

        //değişti
//        CMatrix cm = CMatrix.getInstance().startCamera(new Dimension(1280, 720),5);
        CMatrix cm = CMatrix.getInstance().startCamera(fps);

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
                        //img = ImageProcess.drawRectangle(img, 50, 50, 250, 150, 3, Color.yellow);
//                        
                        //System.out.println("merhaba");
                        if (System.currentTimeMillis() - t1 > 1000.0/fps) {
                            t1 = System.currentTimeMillis();
                            t2 = FactoryUtils.toc(t2);
                            ref.setImage(img);
                        }
                        return img;
                    }

                });
            }
        });
    }

    private static void sendImageToJimServer(BufferedImage img) {
        ref.setImage(img);
    }

}
