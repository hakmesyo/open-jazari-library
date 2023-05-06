/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.DefaultListCellRenderer;
import javax.swing.JColorChooser;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jazari.factory.FactoryUtils;

public class MyDialog extends JDialog implements ActionListener {

    private String data;
    private JTextField descBox;
    private JButton btnColorChooser;
    private JList<ColorItem> colorList;
    private JList<String> classNameList;
    private JButton btnOk;
    private JButton btnCancel;
    private String[] cs;
    private String imageFolder;
    private String className;
    private String selectedClassName;
    private String selectedColor;

    public MyDialog(Frame parent, String imageFolder, String className) {
        super(parent, "Enter class label", true);
        this.imageFolder = imageFolder;
        this.className = className;
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

        btnColorChooser = new JButton("choose color");
        btnColorChooser.addActionListener(this);
        gbc.gridwidth = 2;
        gbc.gridx = 3;
        gbc.gridy = 0;
        btnColorChooser.setVisible(false);
        panel.add(btnColorChooser, gbc);

        JLabel classLabel = new JLabel("Choose Class Label:");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(classLabel, gbc);
        if (!FactoryUtils.isFileExist(imageFolder + "/class_labels.txt")) {
            FactoryUtils.writeToFile(imageFolder + "/class_labels.txt", "");
        }
        cs = FactoryUtils.readFile(imageFolder + "/class_labels.txt").split("\n");
        
        if (!descBox.getText().isEmpty()) {
            selectedColor=resolveColor(cs,this.className);
        }

        String[] names = buildClassNameList(cs);
        classNameList = new JList(names);
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(classNameList, gbc);

        ColorItem[] itemList = buildColorItemList(cs);
        colorList = new JList(itemList);
        colorList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                ColorItem item = (ColorItem) value;
                setText(" ");
                setBackground(item.color);
                if (isSelected) {
                    setBackground(getBackground().darker());
                }
                return c;
            }

        });

        colorList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    classNameList.setSelectedIndex(colorList.getSelectedIndex());
                    Color color = JColorChooser.showDialog(null, "Choose Color for BoundingBox", Color.yellow);
                    selectedClassName = descBox.getText();
                    selectedColor = "Color " + color.getRed() + " " + color.getGreen() + " " + color.getBlue();
                    colorList.getSelectedValue().color = color;
                    revalidate();
                } else if (e.getClickCount() == 1) {
                    classNameList.setSelectedIndex(colorList.getSelectedIndex());
                    selectedClassName = cs[colorList.getSelectedIndex()].split(":")[0];
                    selectedColor = cs[colorList.getSelectedIndex()].split(":")[1];
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
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(colorList, gbc);

        classNameList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                descBox.setText(classNameList.getSelectedValue().toString());
                //colorList.setSelectedIndex(classNameList.getSelectedIndex());
            }
        });
        classNameList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    descBox.setText(classNameList.getSelectedValue().toString());
                    btnOk.doClick();
                } else if (e.getClickCount() == 1) {
                    colorList.setSelectedIndex(classNameList.getSelectedIndex());
                    selectedClassName = cs[classNameList.getSelectedIndex()].split(":")[0];
                    selectedColor = cs[classNameList.getSelectedIndex()].split(":")[1];
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
        gbc.gridx = 3;
        gbc.gridy = 3;
        panel.add(btnCancel, gbc);
        getContentPane().add(panel);
        pack();
    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        boolean isFound = false;
        if (source == btnOk) {
            selectedColor = (selectedColor == null || selectedColor.isEmpty()) ? "Color 255 255 0" : selectedColor;
            data = descBox.getText() + ":" + selectedColor;
            int k=0;
            for (String c : cs) {
                if (cs.equals("") || data.equals("")) {
                    continue;
                }
                if ((data).equals(c)) {
                    dispose();
                    isFound = true;
                }
                if (data.split(":")[0].equals(c.split(":")[0])) {
                    cs[k]=data;
                    FactoryUtils.writeToFile(imageFolder + "/class_labels.txt", cs);
                    dispose();
                    isFound = true;
                    break;
                }
                k++;
            }
            if (!isFound && !data.isEmpty()) {
                FactoryUtils.writeOnFile(imageFolder + "/class_labels.txt", data + "\n");
            }

        } else if (source == btnCancel) {
            data = null;
        }
        dispose();
    }

    public String run() {
        this.setVisible(true);
        return data;
    }

    private ColorItem[] buildColorItemList(String[] cs) {
        ColorItem[] ret = new ColorItem[cs.length];
        if (cs.length==1 && cs[0].isEmpty()) {
            ret[0]=new ColorItem("", Color.yellow);
            return ret;
        }
        
        for (int i = 0; i < cs.length; i++) {
            String[] str = cs[i].split(":");
            if (str.length > 0) {
                String className = str[0];
                String cls[] = str[1].split(" ");
                int red = Integer.parseInt(cls[1]);
                int green = Integer.parseInt(cls[2]);
                int blue = Integer.parseInt(cls[3]);
                ret[i] = new ColorItem(className, new Color(red, green, blue));
            }
        }
        return ret;
    }

    private String[] buildClassNameList(String[] cs) {
        String[] ret = new String[cs.length];
        for (int i = 0; i < cs.length; i++) {
            ret[i] = cs[i].split(":")[0];
        }
        return ret;
    }

    private String resolveColor(String[] cs, String className) {
        String ret="";
        for (String c : cs) {
            if (c.split(":")[0].equals(className)) {
                ret=c.split(":")[1];
            }
        }
        return ret;
    }

    public class ColorItem {

        public String className;
        public Color color;

        public ColorItem(String className, Color color) {
            this.className = className;
            this.color = color;
        }

        @Override
        public String toString() {
            return className + ":Color " + color.getRed() + " " + color.getGreen() + " " + color.getBlue();
        }
    }
}
