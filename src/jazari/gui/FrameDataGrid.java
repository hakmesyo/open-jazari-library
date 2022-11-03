/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.gui;

import jazari.matrix.CMatrix;
import jazari.utils.LineNumberTableRowHeader;
import java.awt.Color;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
//import org.ujmp.core.Matrix;
//import org.ujmp.core.MatrixFactory;

/**
 *
 * @author BAP1
 */
public class FrameDataGrid extends javax.swing.JFrame {

    CMatrix cm = null;

    /**
     * Creates new form DataGrid
     */
    public FrameDataGrid() {
        initComponents();
        LineNumberTableRowHeader tableLineNumber = new LineNumberTableRowHeader(jScrollPane2, jTable_artificialDS);
        tableLineNumber.setBackground(Color.LIGHT_GRAY);
        jScrollPane2.setRowHeaderView(tableLineNumber);
    }

    public FrameDataGrid(CMatrix m) {
        initComponents();
        LineNumberTableRowHeader tableLineNumber = new LineNumberTableRowHeader(jScrollPane2, jTable_artificialDS);
        tableLineNumber.setBackground(Color.LIGHT_GRAY);
        jScrollPane2.setRowHeaderView(tableLineNumber);
        setMatrix(m);
        this.lbl_size.setText("[" + m.getSize().row + " x " + m.getSize().column + "]");
        this.setTitle(cm.name + " :: DataGrid");
    }

    public void setMatrix(CMatrix m) {
        this.cm = m;
        jTable_artificialDS.setModel(getTableModelForArtificialData(cm,false));
    }

    public CMatrix getMatrix() {
        return cm;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel26 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_artificialDS = new javax.swing.JTable();
        lbl_size = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton_buildArtificialDS = new javax.swing.JButton();
        jTextField_colNumber = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField_rowNumber = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jComboBox_randomType = new javax.swing.JComboBox();
        btn_visualize = new javax.swing.JButton();
        btn_plot = new javax.swing.JButton();
        btn_scatter = new javax.swing.JButton();
        btn_visualize1 = new javax.swing.JButton();
        btn_convert_2_int = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable_artificialDS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable_artificialDS.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_artificialDS.setCellSelectionEnabled(true);
        jScrollPane2.setViewportView(jTable_artificialDS);

        lbl_size.setText("5x5");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(lbl_size, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addComponent(lbl_size)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton_buildArtificialDS.setText("Build Artificial Data Set");
        jButton_buildArtificialDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_buildArtificialDSActionPerformed(evt);
            }
        });

        jTextField_colNumber.setText("11");

        jLabel29.setText("ncols:");

        jTextField_rowNumber.setText("100");

        jLabel30.setText("nrows:");

        jComboBox_randomType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Uniform Distribution", "Gaussian Distribution" }));

        btn_visualize.setText("Visualize");
        btn_visualize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_visualizeActionPerformed(evt);
            }
        });

        btn_plot.setText("Plot");
        btn_plot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_plotActionPerformed(evt);
            }
        });

        btn_scatter.setText("Scatter");
        btn_scatter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_scatterActionPerformed(evt);
            }
        });

        btn_visualize1.setText("Transpose");
        btn_visualize1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_visualize1ActionPerformed(evt);
            }
        });

        btn_convert_2_int.setText("Convert to Int");
        btn_convert_2_int.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_convert_2_intActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btn_plot, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_scatter, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_visualize, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_visualize1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_convert_2_int, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton_buildArtificialDS, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_rowNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_colNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_randomType, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 199, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_convert_2_int, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_plot, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_scatter, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_visualize, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_visualize1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_buildArtificialDS, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29)
                    .addComponent(jTextField_colNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_rowNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_randomType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_buildArtificialDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_buildArtificialDSActionPerformed
        buildArtificialDataSet();
    }//GEN-LAST:event_jButton_buildArtificialDSActionPerformed
    private Vector<String> artificialDS = new Vector<String>();  //  @jve:decl-index=0:

    public TableModel getTableModelForArtificialData(CMatrix m, boolean isInt) {
        if (m == null || m.getRowNumber() == 0) {
            return null;
        }
        try {
            int colNumber = m.getColumnNumber();
            String columnNames[] = new String[colNumber];
            for (int i = 0; i < colNumber; i++) {
                columnNames[i] = "" + (i);
            }
//            columnNames[colNumber - 1] = "Class Label";
            if (!isInt) {
                Object data[][] = m.toStringArray2D();
                TableModel model = new DefaultTableModel(data, columnNames) {
                };
                return model;
            }else{
                Object data[][] = m.toStringArray2DAsInt();
                TableModel model = new DefaultTableModel(data, columnNames) {
                };
                return model;
            }
        } catch (Exception e) {
            return null;
        }
    }

    protected void buildArtificialDataSet() {
        int nCols = Integer.parseInt(jTextField_colNumber.getText());
        int nRows = Integer.parseInt(jTextField_rowNumber.getText());
        String type = (jComboBox_randomType.getSelectedIndex() == 0) ? "uniform" : "gaussian";
        artificialDS = getArtificialDataSet(nCols, nRows, type);
        clearTable(jTable_artificialDS);
        jTable_artificialDS.setModel(getTableModelForArtificialData(artificialDS));
        this.cm = null;
    }

    public static Vector<String> getArtificialDataSet(int cols, int rows, String type) {
        Vector<String> ret = new Vector<String>();
        String str = "";
        SecureRandom a = new SecureRandom();
        for (int i = 0; i < rows; i++) {
            str = (i < (rows / 2)) ? "0;" : "1;";
            SecureRandom ms = new SecureRandom();
            for (int j = 0; j < cols; j++) {
                double f = 0;
                if (type.equals("uniform")) {
                    f = formatDouble(ms.nextDouble() - 0.5);
                } else {
                    f = formatDouble(ms.nextGaussian());
                }
                str = f + ";" + str;
            }
            ret.add(str);
        }
        return ret;
    }

    public static double formatDouble(double num) {
        double q = 0;
        try {
            DecimalFormat df = new DecimalFormat("#.###");
            q = Double.parseDouble(df.format(num).replace(",", "."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return q;
    }

    private void btn_visualizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_visualizeActionPerformed
        plotMatrix(jTable_artificialDS);
    }//GEN-LAST:event_btn_visualizeActionPerformed

    private void btn_plotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_plotActionPerformed
        if (getMatrix() != null) {
            getMatrix().plot();
        } else {
            double[][] d = tableToArray(jTable_artificialDS);
            CMatrix cm = CMatrix.getInstance(d);
            setMatrix(cm);
            cm.plot();
        }

    }//GEN-LAST:event_btn_plotActionPerformed

    private void btn_scatterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_scatterActionPerformed
        if (getMatrix() != null) {
            getMatrix().scatter();
        } else {
            double[][] d = tableToArray(jTable_artificialDS);
            CMatrix cm = CMatrix.getInstance(d);
            setMatrix(cm);
            cm.scatter();
        }
    }//GEN-LAST:event_btn_scatterActionPerformed

    private void btn_visualize1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_visualize1ActionPerformed
        cm = cm.transpose();
        LineNumberTableRowHeader tableLineNumber = new LineNumberTableRowHeader(jScrollPane2, jTable_artificialDS);
        tableLineNumber.setBackground(Color.LIGHT_GRAY);
        jScrollPane2.setRowHeaderView(tableLineNumber);
        setMatrix(cm);
    }//GEN-LAST:event_btn_visualize1ActionPerformed

    private void btn_convert_2_intActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_convert_2_intActionPerformed
        jTable_artificialDS.setModel(getTableModelForArtificialData(cm,true));
    }//GEN-LAST:event_btn_convert_2_intActionPerformed

    public static void plotMatrix(JTable table) {
        double[][] d = tableToArray(table);
//        Matrix mm = MatrixFactory.importFromArray(d);
//        mm.transpose().showGUI();
    }

    public static double[][] tableToArray(JTable table) {
        int m = table.getRowCount();
        int n = table.getColumnCount();
        double[][] ret = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ret[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());
            }
        }
        return ret;
    }

    public static void clearTable(JTable table) {
        while (table.getRowCount() > 0) {
            ((DefaultTableModel) table.getModel()).removeRow(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameDataGrid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameDataGrid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameDataGrid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameDataGrid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameDataGrid().setVisible(true);
            }
        });
    }

    public TableModel getTableModelForArtificialData(Vector<String> v) {
        if (v == null || v.size() == 0) {
            return null;
        }
        try {
            String[][] d = vectorToFullStringArray(v);
            int colNumber = v.get(0).split(";").length;
            String columnNames[] = new String[colNumber];
            for (int i = 0; i < colNumber - 1; i++) {
                columnNames[i] = "" + i;
            }
            columnNames[colNumber - 1] = "Class Label";
            Object data[][] = d;
            TableModel model = new DefaultTableModel(data, columnNames) {
            };
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    public static String[][] vectorToFullStringArray(Vector<String> v) {
        String[][] ret = new String[v.size()][v.get(0).split(";").length];
        for (int i = 0; i < v.size(); i++) {
            String[] str = v.get(i).split(";");
            for (int j = 0; j < str.length; j++) {
                ret[i][j] = str[j];
            }
        }
        return ret;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_convert_2_int;
    private javax.swing.JButton btn_plot;
    private javax.swing.JButton btn_scatter;
    private javax.swing.JButton btn_visualize;
    private javax.swing.JButton btn_visualize1;
    private javax.swing.JButton jButton_buildArtificialDS;
    private javax.swing.JComboBox jComboBox_randomType;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_artificialDS;
    private javax.swing.JTextField jTextField_colNumber;
    private javax.swing.JTextField jTextField_rowNumber;
    private javax.swing.JLabel lbl_size;
    // End of variables declaration//GEN-END:variables

    private void sortWithRespectToFirstRow() {
//        getMatrix().sort()
    }
}
