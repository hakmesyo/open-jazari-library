/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.device.webcam;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import misc.InterfaceStringCallBack;
import misc.JimUtils;
import org.java_websocket.client.WebSocketClient;

/**
 *
 * @author cezerilab
 */
public class JimConnection {

    private BufferedImage img;
    private int thread_no;
    public String response = "null";
    public WebSocketClient client;

    public WebSocketClient startJimCommunication(String hostIP, String socketServerPort, int ms_wait) {
        System.out.println(hostIP + " " + socketServerPort + " triggered");
        JimUtils jimUtils = new JimUtils();

        client = jimUtils.connectToJavaServer(hostIP, socketServerPort, new InterfaceStringCallBack() {
            @Override
            public void onMessageReceived(String msg) {
                if (!jimUtils.isConnected && msg.contains("[new client connection]")) {
                    jimUtils.isConnected = true;
                    System.out.println("connection was accepted, now we send register request to the JIM in order to get ID.. ");
                    client.send("CJ:NTHR");
                } else if (msg.contains("JC:THR:")) {
                    thread_no = Integer.parseInt(msg.split(":")[2]);
                    if (msg.split(":").length < 4) {
                        client.send("CJ:THR:" + thread_no + ":" + "real time test image" + ":./temp.jpg");
                    } else {
                        response = msg.split(":")[4];
                        //System.out.println("thread no:" + thread_no + " detection response = " + response);
                    }
//                    try {
//                        Thread.sleep(ms_wait);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(JimConnection.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    if (img != null) {
                        System.out.println("thread_no:" + thread_no + " img:" + img.getWidth());
                        jimUtils.sendByteBufferData(img, thread_no);
                    }
                }

            }
        });
        return client;
    }

    public void setImage(BufferedImage img) {
        this.img = img;
    }

}
