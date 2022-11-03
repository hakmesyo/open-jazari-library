/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.gui;

import jazari.types.TPanelData;
import jazari.matrix.CMatrix;
import jazari.factory.FactoryUtils;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

/**
 *
 * @author BAP1
 */
public class PanelHistogram extends TPanelData {

    private int[][] hist;
    private CMatrix cm;
    private float scale = 1;
    private Color[] color;
    private String title;
    private boolean isStatisticsVisible = false;

    public PanelHistogram(CMatrix cmatrix, CMatrix histogram, String title, boolean isStatisticsVisible) {
        super(histogram);
        this.hist = FactoryUtils.transpose(histogram.toIntArray2D());
        this.cm = cmatrix;
        this.title = title;
        this.isStatisticsVisible = isStatisticsVisible;
        color = FactoryUtils.generateColor(hist[0].length);
        initialize();
        repaint();
    }

    public int[][] getHistogramData() {
        return this.hist;
    }

    private void initialize() {
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        this.updateUI();
    }

    @Override
    public void paint(Graphics gr1) {
        Graphics2D gr = (Graphics2D) gr1;
        gr.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
         Font fnt = gr.getFont();
//        gr.setFont(new Font(fnt.getFontName(), 1, 18));
        gr.setColor(Color.lightGray);
        int w = getWidth();
        int h = getHeight();
        gr.fillRect(0, 0, w, h);
        int px = 50;
        int py = 50;
        int dx = w - 2 * px;
        int dy = h - 2 * py;

        Point[][] mp = mappingDataToScreenCoordinates(getMatrix().toDoubleArray2D(), dx, dy, px, py);

        if (isActivateDataCursor()) {
            gr.setColor(Color.red);
            checkDataPoints(gr1, mp);
        }


//        if (hist.length==2) {
//            gr.setColor(color[0]);
//            gr.drawString("Closed-Shell Pistachio", dx-100,py+50);
//            gr.setColor(color[1]);
//            gr.drawString("Open-Shell Pistachio", dx-100,py+70);
//        }
        gr.setColor(Color.black);
//        gr.drawString("Histogram of Closed (left) and open (right) shell pistachio", px+100,py-20);
        
        if (isStatisticsVisible) {
            float[] mean = cm.dup().mean().toFloatArray1D();
            float[] std = cm.dup().std().toFloatArray1D();
            float fisher_score = FactoryUtils.formatFloat((float)Math.pow(mean[0] - mean[1], 2) / (std[0] * std[0] + std[1] * std[1]));
            String sss=title+", fisher score:"+fisher_score;
            int length = sss.length() * 5;
            gr.drawString(sss, (w - length) / 2, py - 20);
        }else{
            int length = title.length() * 5;
            gr.drawString(title, (w - length) / 2, py - 20);
        }

        gr.setColor(Color.black);
        gr.drawRect(px - 2, py, dx + 5, dy);
        drawAxisX(gr, dx, dy, px, py);
        drawAxisY(gr, dx, dy, px, py);
        gr.setColor(Color.red);
        gr.drawRect(0, 0, w - 1, h - 1);
        gr.drawRect(1, 1, w - 3, h - 3);
        this.paintComponents(gr);
    }

    private void drawAxisX(Graphics2D gr, int dx, int dy, int px, int py) {
        int k = 5;
//        float[] d = FactoryUtils.toDoubleArray(hist[0]);
        float[] d = new float[hist[0].length];
//        float min = FactoryUtils.getMinimum(cm.toDoubleArray2D());
//        float max = FactoryUtils.getMaximum(cm.toDoubleArray2D());
        float min = 0;
        float max = hist[0].length;
        float delta = Math.abs(max - min) / k;
        int n = d.length / k;
        int nd = FactoryUtils.getDigitNumber(delta);
        float coeff = (float)Math.pow(10, nd);
        if (nd > 0) {
            float yuvarlatilmis_min = ((int) (min / coeff) * coeff);
            for (int i = 0; i <= k; i++) {
                int x = px + (int) (dx * n * 1.0 / d.length * i);
                int yz = (int) (yuvarlatilmis_min + (int) (i * delta / coeff) * coeff);
                gr.drawString(yz + "", x - 5, dy + py + 20);
                gr.drawLine(x, py, x, py + 5);
                gr.drawLine(x, dy + py, x, dy + py + 5);
            }
        } else {
            float yuvarlatilmis_min = ((min / coeff) * coeff);
            for (int i = 0; i <= k; i++) {
                int x = px + (int) (dx * n * 1.0 / d.length * i);
                float yz = (float)FactoryUtils.formatDouble(yuvarlatilmis_min + (i * delta) / coeff * coeff, 3);
                gr.drawString(yz + "", x - 5, dy + py + 20);
                gr.drawLine(x, py, x, py + 5);
                gr.drawLine(x, dy + py, x, dy + py + 5);
            }
        }
    }

    private void drawAxisY(Graphics2D gr, int dx, int dy, int px, int py) {
        int maxY = FactoryUtils.getMaximum(hist);
        float normY = (dy - 10) * 1.0f / maxY;
        int qy = (int) (maxY * normY);
        qy = ((qy / 10) + 1) * 10;
        int deltaY = qy / 5;
        int nd = FactoryUtils.getDigitNumber(maxY);
        float coeff = (float)Math.pow(10, nd - 1);
        //biiznillah yuvarlıyor 0,50,100 mesela
        int n = (int) ((maxY / coeff + 1) * coeff / 5);
        int a = 50;
        int enBuyukY = 0;
        for (int i = 0; i <= 5; i++) {
            int y = py + deltaY * i;
            enBuyukY = n * i;
            gr.drawString(enBuyukY + "", px - 40, dy + py - y + a);
            gr.drawLine(px - 5, dy + py - y + a, px + 5, dy + py - y + a);
            gr.drawLine(px + dx - 5, dy + py - y + a, px + dx, dy + py - y + a);
        }

        float min1 = FactoryUtils.getMinimum(cm.toFloatArray2D());
        float max1 = FactoryUtils.getMaximum(cm.toFloatArray2D());
        float delta1 = Math.abs(max1 - min1);
        CMatrix ctm = cm.transpose();
        int ppx = px;
        for (int k = 0; k < hist.length; k++) {
            px = ppx;
            float min2 = FactoryUtils.getMinimum(ctm.toFloatArray2D()[k]);
            float max2 = FactoryUtils.getMaximum(ctm.toFloatArray2D()[k]);
            float delta2 = Math.abs(max2 - min2);
            float oran = delta2 / delta1;
            float norm = (dx * 1.0f / delta2) * oran;
            float normX = (dx * 1.0f / hist[0].length) * oran;
            int x = 0;
            int x2 = 0;
            int y = 0;
            px += (min2 - min1) * norm;
            for (int i = 0; i < hist[0].length; i++) {
                x = (int) (i * normX);
                x2 = (int) ((i + 1) * normX);
                y = (int) (hist[k][i] * normY);
                float alpha = 0.35f;
                AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                gr.setComposite(alcom);
                gr.setColor(color[k]);
                gr.fillRect(px + x, py + dy - y, (x2 - x), y);
                gr.setColor(Color.black);
                gr.drawRect(px + x, py + dy - y, (x2 - x), y);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
