/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.modsim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author cezerilab
 */
public class MyButton extends JButton {

    public String str = "";
    Color backColor;
    Color textColor;
    int width;
    Thread thr;
    float py = 15;
    float delta = 0.5f;

    public MyButton(String str, Color backColor, Color textColor) {
        this.str = str;
        this.backColor = backColor;
        this.textColor = textColor;
    }

    public void startAnimation(String str) {
        this.str = str;
        this.py = 15;
        this.delta = 0.5f;
        if (thr != null) {
            
        } else {
            thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (delta > 0) {
                            delta *= 1.01f;
                        } else {
                            delta *= 0.987f;
                        }
                        if (py > getHeight() - 15 || (delta < 0 && Math.abs(delta) >= 0.0f && Math.abs(delta) <= 0.15f)) {
                            delta = -delta;
                        }
                        py += delta;
                        System.out.println("py:" + py + " delta = " + delta);
                        repaint();
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MyButton.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            thr.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        width = this.getGraphics().getFontMetrics().stringWidth(str);
        g.setColor(backColor);
        g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
        g.setColor(Color.red);
        g.drawRect(10, 10, getWidth() - 20, getHeight() - 20);
        g.setColor(textColor);
        g.drawString(str, (getWidth() - width) / 2, Math.round(py));
    }

}
