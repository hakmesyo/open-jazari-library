/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jazari.factory.FactoryUtils;

public class MyDialog extends JDialog implements ActionListener {

    private String data;
    private JTextField descBox;
    private JComboBox<String> colorList;
    private JList<String> labelList;
    private JButton btnOk;
    private JButton btnCancel;
    private String[] cs;
    private String imageFolder;
    private String className;

    public MyDialog(Frame parent,String imageFolder,String className) {
        super(parent, "Enter class label", true);
        this.imageFolder=imageFolder;
        this.className=className;
        setTitle("press 's' to save");
        Point loc = parent.getLocation();
        setLocation(loc.x + 80, loc.y + 80);
        data = ""; // set to amount of data items
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        JLabel descLabel = new JLabel("Class Label:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(descLabel, gbc);
        descBox = new JTextField(30);
        descBox.setText(this.className);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(descBox, gbc);

//      JLabel colorLabel = new JLabel("Choose color:");
//      gbc.gridwidth = 1;
//      gbc.gridx = 0;
//      gbc.gridy = 1;
//      panel.add(colorLabel,gbc);
//      String[] colorStrings = {"red","yellow","orange","green","blue"};
//      colorList = new JComboBox<String>(colorStrings);
//      gbc.gridwidth = 2;
//      gbc.gridx = 1;
//      gbc.gridy = 1;
//      panel.add(colorList,gbc);
        JLabel classLabel = new JLabel("Choose Class Label:");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(classLabel, gbc);
        if (!FactoryUtils.isFileExist(imageFolder+"/class_labels.txt")) {
            FactoryUtils.writeToFile(imageFolder+"/class_labels.txt", "");
        }
        cs = FactoryUtils.readFile(imageFolder+"/class_labels.txt").split("\n");
        labelList = new JList<String>(cs);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(labelList, gbc);
        labelList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                descBox.setText(labelList.getSelectedValue());
            }
        });
        labelList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    descBox.setText(labelList.getSelectedValue());
                    btnOk.doClick();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        JLabel spacer = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(spacer, gbc);
        btnOk = new JButton("Ok");
        btnOk.addActionListener(this);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnOk, gbc);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(btnCancel, gbc);
        getContentPane().add(panel);
        pack();
    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        boolean isFound=false;
        if (source == btnOk) {
            data = descBox.getText();
            for (String c : cs) {
                if (cs.equals("") || data.equals("")) {
                    continue;
                }
                if ((data).equals(c)) {
                    dispose();
                    isFound=true;
                }
            }
            if (!isFound && !data.isEmpty()){
                FactoryUtils.writeOnFile(imageFolder+"/class_labels.txt", data+"\n");
            }
            
        } else {
            data = null;
        }
        dispose();
    }

    public String run() {
        this.setVisible(true);
        return data;
    }
}
