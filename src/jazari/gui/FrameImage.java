/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatPropertiesLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import jazari.image_processing.ImageProcess;
import jazari.matrix.CMatrix;
import jazari.factory.FactoryUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author BAP1
 */
public class FrameImage extends javax.swing.JFrame {

    static {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FlatLaf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BufferedImage img;
    int pw = 100;
    int ph = 150;
    int panWidth = 40;
    //BufferedImage currBufferedImage = null;
    String imagePath;
    private Vector<String> listImageFile = new Vector<String>();
    int listIndex = 0;
    CMatrix cm = null;

    /**
     * Creates new form FrameImage
     */
    public FrameImage() {
        initComponents();
        isSequence.setVisible(false);

//        getPicturePanel().setFrame(this);
    }

    /**
     * Draws A Picture Frame
     *
     * @param cm : image to be drawn
     * @param imagePath : image path
     */
    public FrameImage(CMatrix cm, String imagePath, String caption) {
        initComponents();
        this.setTitle(caption);
        this.cm = cm;
        this.img = cm.getImage();
        this.imagePath = imagePath;
        getPicturePanel().activateBoundingBox = isBBox.isSelected();
        getPicturePanel().setImage(img, imagePath, caption); 
        getPicturePanel().setFrame(this);
        setFrameSize(img);
        getPicturePanel().setFocusable(true);
        getPicturePanel().requestFocus();
        isSequence.setVisible(false);
        
    }

    public void setImage(BufferedImage img, String imagePath, String caption) {
        this.img = img;
        this.imagePath = imagePath;
        getPicturePanel().setImage(this.img, imagePath, caption);
        getPicturePanel().setFrame(this);
        this.setSize(img.getWidth() + 300, img.getHeight() + 183);
        String[] s = FactoryUtils.splitPath(imagePath);
        //this.setTitle(s[s.length-1]);
        this.setTitle(imagePath + "/" + caption);
    }

    public void setImage(BufferedImage img) {
        this.img = img;
        setImage(this.img, "", "");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btn_dataGrid = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        txt_dpi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        isBBox = new javax.swing.JCheckBox();
        isSequence = new javax.swing.JCheckBox();
        scroll_pane = new javax.swing.JScrollPane();
        panelPicture = new jazari.gui.PanelPicture(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_dataGrid.setText("Data Grid");
        btn_dataGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dataGridActionPerformed(evt);
            }
        });

        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        txt_dpi.setText("300");

        jLabel2.setText("dpi");

        isBBox.setText("bbox");
        isBBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                isBBoxItemStateChanged(evt);
            }
        });
        isBBox.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                isBBoxMouseMoved(evt);
            }
        });
        isBBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isBBoxActionPerformed(evt);
            }
        });

        isSequence.setText("sequence");
        isSequence.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                isSequenceItemStateChanged(evt);
            }
        });
        isSequence.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                isSequenceMouseMoved(evt);
            }
        });
        isSequence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSequenceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_dataGrid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_dpi, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(isBBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(isSequence)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_dataGrid)
                .addComponent(btn_save)
                .addComponent(txt_dpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2)
                .addComponent(isBBox)
                .addComponent(isSequence))
        );

        panelPicture.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPicture.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                panelPictureKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelPictureLayout = new javax.swing.GroupLayout(panelPicture);
        panelPicture.setLayout(panelPictureLayout);
        panelPictureLayout.setHorizontalGroup(
            panelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 733, Short.MAX_VALUE)
        );
        panelPictureLayout.setVerticalGroup(
            panelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );

        scroll_pane.setViewportView(panelPicture);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scroll_pane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_pane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dataGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dataGridActionPerformed
        CMatrix cm = CMatrix.getInstance(((PanelPicture) panelPicture).getImage());
//        CMatrix cm = CMatrix.getInstance(img);
        new FrameDataGrid(cm).setVisible(true);
    }//GEN-LAST:event_btn_dataGridActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        savePanel();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void panelPictureKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelPictureKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panelPictureKeyPressed

    private void isBBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_isBBoxItemStateChanged
        getPicturePanel().activateBoundingBox = isBBox.isSelected();
        getPicturePanel().setImage(this.img, imagePath, this.getTitle());
        isSequence.setVisible(isBBox.isSelected());
        getPicturePanel().requestFocus();
    }//GEN-LAST:event_isBBoxItemStateChanged

    private void isBBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isBBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isBBoxActionPerformed

    private void isSequenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSequenceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isSequenceActionPerformed

    private void isSequenceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_isSequenceItemStateChanged
        getPicturePanel().isSeqenceVideoFrame = isSequence.isSelected();
        getPicturePanel().requestFocus();
    }//GEN-LAST:event_isSequenceItemStateChanged

    private void isBBoxMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_isBBoxMouseMoved
        isBBox.setToolTipText("Activate BoundingBox annotation for object detection");
    }//GEN-LAST:event_isBBoxMouseMoved

    private void isSequenceMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_isSequenceMouseMoved
        isSequence.setToolTipText("Check if your images are similar to the video sequences/frames.\nPreserves bboxs from previous image");
    }//GEN-LAST:event_isSequenceMouseMoved

    public PanelPicture getPicturePanel() {
        return ((PanelPicture) panelPicture);
    }

    public void savePanel() {
        PanelPicture cp = ((PanelPicture) panelPicture);
        FactoryUtils.savePanel(cp, txt_dpi.getText());
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FrameImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrameImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrameImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrameImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FrameImage().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_dataGrid;
    private javax.swing.JButton btn_save;
    private javax.swing.JCheckBox isBBox;
    private javax.swing.JCheckBox isSequence;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelPicture;
    private javax.swing.JScrollPane scroll_pane;
    private javax.swing.JTextField txt_dpi;
    // End of variables declaration//GEN-END:variables

    public void setFrameSize(BufferedImage img) {
        pw = 70;
        int w=img.getWidth() + 2 * pw;
        //System.out.println("w = " + w);
        int h=img.getHeight() + 190;
        //System.out.println("h = " + h);
        this.setSize(w, h);
        //this.setPreferredSize(new Dimension(w, h));
        this.pack();
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        double width = screenSize.getWidth();
//        double height = screenSize.getHeight();
//
////        //on a multimonitor
////        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
////        int width = gd.getDisplayMode().getWidth();
////        int height = gd.getDisplayMode().getHeight();
//        
//        this.setLocation((int)(width-w)/2, (int)(height-h)/2);
        
    }
}
