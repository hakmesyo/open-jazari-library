/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class JOptionPaneExample {

    private JLabel label;
    private JTextField tfield;
    private JLabel statusLabel;

    private static final int GAP = 5;

    private void displayGUI() {
        JOptionPane.showMessageDialog(null, getPanel());
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        label = new JLabel("Enter something: ", JLabel.CENTER);
        tfield = new JTextField(10);
        tfield.getDocument().addDocumentListener(new MyDocumentListener());
        JPanel controlPanel = new JPanel();
        controlPanel.add(label);
        controlPanel.add(tfield);
        panel.add(controlPanel);

        statusLabel = new JLabel("", JLabel.CENTER);
        panel.add(statusLabel);

        return panel;
    }

    private class MyDocumentListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent de) {
            updateStatus();
        }

        @Override
        public void insertUpdate(DocumentEvent de) {
            updateStatus();
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            updateStatus();
        }

        private void updateStatus() {
            statusLabel.setText(tfield.getText());
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new JOptionPaneExample().displayGUI();
            }
        };
        EventQueue.invokeLater(runnable);
    }
}
