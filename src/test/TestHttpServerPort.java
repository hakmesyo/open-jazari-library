/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cezerilab
 */
public class TestHttpServerPort {
    public static void main(String[] args) {
        String cmd="cmd /c http-server D:\\Dropbox\\NetbeansProjects\\TEKNOFEST_CEZERICAMPUSCAR_2022\\models\\full_detection -p 8087";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            Logger.getLogger(TestHttpServerPort.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestHttpServerPort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
