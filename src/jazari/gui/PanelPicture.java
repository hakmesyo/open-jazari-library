package jazari.gui;

import java.awt.BasicStroke;
import jazari.factory.FactoryMatrix;
import jazari.gui.FrameImageHistogram;
import jazari.gui.FrameImage;
import jazari.image_processing.ImageProcess;
import jazari.types.TStatistics;
import jazari.matrix.CMatrix;
import jazari.matrix.CPoint;
import jazari.matrix.CRectangle;
import jazari.factory.FactoryUtils;
import jazari.utils.TimeWatch;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import jazari.utils.MyDialog;
import jazari.utils.BoundingBoxPascalVOC;
import jazari.utils.BoundingBox;
import jazari.utils.PascalVocObject;

public class PanelPicture extends JPanel implements KeyListener {

    private Point p = new Point();
    public boolean isChainProcessing = false;
    private Point mousePos = new Point(0, 0);
    private CPoint drawableMousePos = new CPoint(0, 0);
    ArrayList<CPoint> drawableRoiList = new ArrayList<>();
    private Point mousePosTopLeft = new Point(0, 0);
    private Point mousePosTopRight = new Point(0, 0);
    private Point mousePosBottomLeft = new Point(0, 0);
    private Point mousePosBottomRight = new Point(0, 0);
    private JLabel lbl = null;
    private boolean lblShow = false;
    private boolean showRegion = false;
    private boolean showDrawableRegion = false;
    private BufferedImage currBufferedImage;
    private BufferedImage originalBufferedImage;
    private BufferedImage originalBufferedImageTemp;
    private TimeWatch watch = TimeWatch.start();
    private JRadioButtonMenuItem items[];
    private final JPopupMenu popupMenu = new JPopupMenu();
    private int fromLeft = 10;
    private int fromTop = 30;
    private boolean activateOriginal = false;
    private boolean activateSaveImage = false;
    private boolean activateHistogram = false;
    private boolean activateStatistics = false;
    private boolean activateRedChannel = false;
    private boolean activateRevert = false;
    private boolean activateROI = false;
    private boolean activateBoundingBox = false;
    private boolean activateDrawableROI = false;
    private boolean activateCloneROI = false;
    private boolean activateGreenChannel = false;
    private boolean activateBlueChannel = false;
    private boolean activateRGB = false;
    private boolean activateGray = false;
    private boolean activateHSV = false;
    private boolean activateEdge = false;
    private boolean activateEqualize = false;
    private boolean activateAutoSize = false;
    private BoundingBoxPascalVOC pascalVocXML;
    private List<BoundingBox> listBBoxRect = new ArrayList();
    private BoundingBox selectedBBox = null;
    private boolean activateAutoSizeAspect = false;
    private TStatistics stat;
    private DecimalFormat df = new DecimalFormat("#");
    private String imagePath = "-1";
    private String imageFolder;
    private String fileName;
    private float[][] imgData;
    private FrameImageHistogram histFrame = null;
    private final int panWidth = 50;
    private boolean isRedChannel = false;
    private boolean isGreenChannel = false;
    private boolean isBlueChannel = false;
    private boolean isBBoxResizeTopLeft = false;
    private boolean isBBoxResizeTopRight = false;
    private boolean isBBoxResizeBottomLeft = false;
    private boolean isBBoxResizeBottomRight = false;
    private File[] imageFiles;
    private int imageIndex = 0;
    private JFrame frame;
    private String caption;
    private String lastSelectedClass;

    public PanelPicture(JFrame frame) {
        this.frame = frame;
        initialize();
    }

    private void setImagePath(String path) {
        if (path == null || path.equals("")) {
            imagePath = path;
            return;
        }
        imagePath = path;
        File f = new File(imagePath);
        imageFolder = f.getParent();
        imageFiles = FactoryUtils.getFileListInFolderForImages(imageFolder);
        imageFiles = sortFileListByNumber(imageFiles);
        fileName = f.getName();
        for (int i = 0; i < imageFiles.length; i++) {
            if (imageFiles[i].getName().equals(fileName)) {
                imageIndex = i;
                break;
            }
        }
    }

    public File[] sortFileListByNumber(File[] files) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int n1 = extractNumber(o1.getName());
                int n2 = extractNumber(o2.getName());
                return n1 - n2;
            }

            private int extractNumber(String name) {
                int i = 0;
                try {
                    String number = name.split("_")[0];
                    i = Integer.parseInt(number);
                } catch (Exception e) {
                    i = 0; // if filename does not match the format
                    // then default to 0
                }
                return i;
            }
        });
        return files;

    }

    public void setImage(BufferedImage image, String imagePath,String caption) {
        this.caption=caption;
        if (imagePath != null && !imagePath.isEmpty()) {
            String fileName = FactoryUtils.getFileName(imagePath) + ".xml";
            String folderName = FactoryUtils.getFolderPath(imagePath);
            boolean checkXML = new File(folderName, fileName).exists();
            if (checkXML) {
                BoundingBoxPascalVOC bb = FactoryUtils.deserializePascalVocXML(folderName + "/" + fileName);
                listBBoxRect.clear();
                showRegion = true;
                activateBoundingBox = true;
                for (PascalVocObject obj : bb.lstObjects) {
                    listBBoxRect.add(obj.bndbox);
                }
            }
        }
        currBufferedImage = ImageProcess.clone(image);
        originalBufferedImage = ImageProcess.clone(image);
        originalBufferedImageTemp = ImageProcess.clone(image);
        imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
        lbl.setText(getImageSize() + "X:Y");
        if (activateStatistics) {
            currBufferedImage = ImageProcess.toGrayLevel(originalBufferedImage);
            imgData = ImageProcess.imageToPixelsFloat(currBufferedImage);
            stat = TStatistics.getStatistics(currBufferedImage);
        }
        setPreferredSize(new Dimension(originalBufferedImage.getWidth() + 100, originalBufferedImage.getHeight() + 100));
        repaint();
        if (!this.imagePath.equals(imagePath)) {
            setImagePath(imagePath);
        }
    }

    public BufferedImage getImage() {
        return currBufferedImage;
    }

    public TStatistics getStatistics() {
        stat = TStatistics.getStatistics(currBufferedImage);
        return stat;
    }

    JFrame frm = null;

    public void setFrame(JFrame frm) {
        this.frm = frm;
    }

    int flag_once = 0;
    int prev_width = 500;
    int prev_height = 500;
    private boolean isBBoxCancelled = false;
    private boolean isBBoxDragged = false;
    private Point referenceDragPos;

    @Override
    public void paint(Graphics grn) {
        Graphics2D gr = (Graphics2D) grn;
        if (flag_once == 0) {
            activateAutoSizeAspect = false;
            if (originalBufferedImage != null) {
                prev_width = originalBufferedImage.getWidth() + 300;
                prev_height = originalBufferedImage.getHeight() + 183;
            }
            flag_once = 1;
        }
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.setColor(Color.GREEN);
        int wPanel = this.getWidth();
        int hPanel = this.getHeight();
        if (currBufferedImage != null) {
            if (activateAutoSize) {
                currBufferedImage = ImageProcess.resize(originalBufferedImage, this.getWidth() - 2 * panWidth, this.getHeight() - 2 * panWidth);
                imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                lbl.setText(getImageSize() + "X:Y");
//                gr.drawImage(currBufferedImage, fromLeft, fromTop, this);
            } else if (activateAutoSizeAspect) {
                if (this.getWidth() - 2 * panWidth > 5 && this.getHeight() - 2 * panWidth > 5) {
                    currBufferedImage = ImageProcess.resizeAspectRatio(originalBufferedImageTemp, this.getWidth() - 2 * panWidth, this.getHeight() - 2 * panWidth);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                    lbl.setText(getImageSize() + "X:Y");
                    //activateAutoSizeAspect = false;
                }
            } else if (activateRevert) {
//                currBufferedImage = ImageProcess.ocv_negativeImage(originalBufferedImage);
            } else if (activateOriginal) {
                //currBufferedImage= ImageProcess.ocv_cloneImage(originalBufferedImage);;
            } else if (activateGray) {
//                currBufferedImage = ImageProcess.ocv_rgb2gray(originalBufferedImage);
            } else if (activateHSV) {
//                currBufferedImage = ImageProcess.ocv_rgb2hsv(originalBufferedImage);
            } else if (activateRedChannel) {
//                currBufferedImage = ImageProcess.getRedChannelColor(originalBufferedImage);
            } else if (activateGreenChannel) {
//                currBufferedImage = ImageProcess.getGreenChannelColor(originalBufferedImage);
            } else if (activateBlueChannel) {
//                currBufferedImage = ImageProcess.getBlueChannelColor(originalBufferedImage);
            } else if (activateEdge) {
//                currBufferedImage = ImageProcess.ocv_edgeDetectionCanny(originalBufferedImage);
            } else if (activateEqualize) {
//                currBufferedImage = ImageProcess.ocv_equalizeHistogram(originalBufferedImage);
            }
//            else {
//                gr.drawImage(currBufferedImage, fromLeft, fromTop, this);
//            }
            int wImg = currBufferedImage.getWidth();
            int hImg = currBufferedImage.getHeight();
            fromLeft = (wPanel - wImg) / 2;
            fromTop = (hPanel - hImg) / 2;
            gr.drawImage(currBufferedImage, fromLeft, fromTop, this);

            gr.setColor(Color.blue);
            gr.drawRect(fromLeft, fromTop, currBufferedImage.getWidth(), currBufferedImage.getHeight());

            if (activateHistogram && histFrame != null) {
                imgData = ImageProcess.imageToPixelsFloat(currBufferedImage);
                histFrame.setHistogramData(CMatrix.getInstance(imgData));
                histFrame.setVisible(true);
            }
            if (activateStatistics && stat != null) {
                int sW = 150;
                int sH = 200;
                int sPX = this.getWidth() - sW - 5;
                int sPY = 5;
                int dh = 24;

                gr.setColor(Color.black);
                gr.fillRect(sPX, sPY, sW, sH);
                gr.setColor(Color.GREEN);
                gr.drawRect(sPX, sPY, sW, sH);
                sPX += 20;
                gr.drawString("Mean = " + (stat.mean), sPX, sPY += dh);
                gr.drawString("Std Dev = " + (stat.std), sPX, sPY += dh);
                gr.drawString("Entropy = " + (stat.entropy), sPX, sPY += dh);
                gr.drawString("Contrast = " + (stat.contrast), sPX, sPY += dh);
                gr.drawString("Kurtosis = " + (stat.kurtosis), sPX, sPY += dh);
                gr.drawString("Skewness = " + (stat.skewness), sPX, sPY += dh);
                gr.setColor(Color.ORANGE);
                gr.drawString("Ideal Exposure Score", sPX, sPY += dh);
                gr.drawString("= " + stat.adaptiveExposureScore, sPX + 60, sPY += dh);
            }
            if (!activateROI && !activateDrawableROI && !activateBoundingBox) {
                if (lblShow && mousePos.x > fromLeft && mousePos.y > fromTop && mousePos.x < fromLeft + wImg && mousePos.y < fromTop + hImg) {
                    p.x = mousePos.x - fromLeft;
                    p.y = mousePos.y - fromTop;
                    if (currBufferedImage.getType() == BufferedImage.TYPE_BYTE_GRAY) {
                        lbl.setText(getImageSize() + " Pos=(" + p.y + ":" + p.x + ") Value=" + imgData[p.y][p.x] + " Img Type=TYPE_BYTE_GRAY");
                    } else if (currBufferedImage.getType() == BufferedImage.TYPE_INT_RGB) {
                        String s = "" + new Color((int) imgData[p.y][p.x], true);
                        s = s.replace("java.awt.Color", "");
                        //s = s.replace("java.awt.Color", "").replace("r", "h").replace("g", "s").replace("b", "v");
                        lbl.setText(getImageSize() + " Pos=(" + p.y + ":" + p.x + ") Value=" + s + " Img Type=TYPE_INT_RGB");// + " RGB=" + "(" + r + "," + g + "," + b + ")");
                    } else if (currBufferedImage.getType() == BufferedImage.TYPE_3BYTE_BGR) {
                        String s = "" + new Color((int) imgData[p.y][p.x], true);
                        s = s.replace("java.awt.Color", "");
                        lbl.setText(getImageSize() + " Pos=(" + p.y + ":" + p.x + ") Value=" + s + " Img Type=TYPE_3BYTE_BGR");// + " RGB=" + "(" + r + "," + g + "," + b + ")");
                    } else {
                        String s = "" + new Color((int) imgData[p.y][p.x], true);
                        s = s.replace("java.awt.Color", "");
                        lbl.setText(getImageSize() + " Pos=(" + p.y + ":" + p.x + ") Value=" + s + " Img Type=" + currBufferedImage.getType());// + " RGB=" + "(" + r + "," + g + "," + b + ")");
                    }
                    gr.setColor(Color.blue);
                    gr.drawLine(0, mousePos.y, wPanel - 1, mousePos.y);
                    gr.drawLine(mousePos.x, 0, mousePos.x, hPanel - 1);
                    gr.setColor(Color.red);
                    gr.drawRect(mousePos.x - 1, mousePos.y - 1, 2, 2);
                    gr.drawRect(mousePos.x - 10, mousePos.y - 10, 20, 20);
                }
                if (drawableRoiList.size() > 0) {
                    CPoint prev = drawableRoiList.get(0);
                    for (CPoint p : drawableRoiList) {
                        gr.setColor(Color.red);
                        int wx = 5;
                        gr.fillRect(p.column + fromLeft - 2, p.row + fromTop - 2, wx, wx);
                        gr.setColor(Color.green);
                        gr.drawLine(prev.column + fromLeft, prev.row + fromTop, p.column + fromLeft, p.row + fromTop);
                        prev = p;
                    }
                }
            } else {
                if (showRegion && activateROI) {
                    gr.setStroke(new BasicStroke(3));
                    gr.setColor(Color.blue);
                    int w = Math.abs(mousePos.x - mousePosTopLeft.x);
                    int h = Math.abs(mousePos.y - mousePosTopLeft.y);
                    gr.drawRect(mousePosTopLeft.x, mousePosTopLeft.y, w, h);
                    gr.setColor(Color.red);
                    int wx = 5;
                    gr.drawRect(mousePosTopLeft.x - 2, mousePosTopLeft.y - 2, wx, wx);
                    gr.drawRect(mousePosTopLeft.x - 2 + w, mousePosTopLeft.y - 2, wx, wx);
                    gr.drawRect(mousePosTopLeft.x - 2 + w, mousePosTopLeft.y - 2 + h, wx, wx);
                    gr.drawRect(mousePosTopLeft.x - 2, mousePosTopLeft.y - 2 + h, wx, wx);
                    gr.setStroke(new BasicStroke(1));
                } else if (showRegion && activateBoundingBox) {
                    int sw = 2;//stroke width
                    if (isBBoxResizeTopLeft) {
                        Point p = new Point(selectedBBox.xmax + fromLeft, selectedBBox.ymax + fromTop);
                        drawBoundingBox(gr, selectedBBox, sw, mousePos, p, Color.green, true);
                    } else if (isBBoxResizeTopRight) {
                        int dy1=mousePos.y-(selectedBBox.ymin + fromTop);
                        int dx2=mousePos.x-(selectedBBox.xmax + fromLeft);
                        Point p1 = new Point(selectedBBox.xmax+fromLeft + dx2, selectedBBox.ymax + fromTop);
                        Point p2 = new Point(selectedBBox.xmin+fromLeft,selectedBBox.ymin+fromTop+dy1);
                        drawBoundingBox(gr, selectedBBox, sw, p2, p1, Color.green, true);
                    } else if (isBBoxResizeBottomLeft) {
                        int dy1=mousePos.y-(selectedBBox.ymax + fromTop);
                        int dx2=mousePos.x-(selectedBBox.xmin + fromLeft);
                        Point p1 = new Point(selectedBBox.xmax+fromLeft, selectedBBox.ymax+dy1 + fromTop);
                        Point p2 = new Point(selectedBBox.xmin+fromLeft+dx2,selectedBBox.ymin+fromTop);
                        drawBoundingBox(gr, selectedBBox, sw, p2, p1, Color.green, true);
                    } else if (isBBoxResizeBottomRight) {
                        Point p = new Point(selectedBBox.xmin + fromLeft, selectedBBox.ymin + fromTop);
                        drawBoundingBox(gr, selectedBBox, sw, p, mousePos, Color.green, true);
                    } else if (selectedBBox != null && isBBoxDragged) {
                        Point p1 = new Point(selectedBBox.xmin + fromLeft + (mousePos.x - referenceDragPos.x),
                                selectedBBox.ymin + fromTop + (mousePos.y - referenceDragPos.y));
                        Point p2 = new Point(p1.x + (selectedBBox.xmax - selectedBBox.xmin), p1.y + (selectedBBox.ymax - selectedBBox.ymin));
                        drawBoundingBox(gr, selectedBBox, sw, p1, p2, Color.orange, true);
                    } else if (selectedBBox == null && !isBBoxCancelled) {
                        drawBoundingBox(gr, selectedBBox, sw, mousePosTopLeft, mousePos, Color.green, true);
                    }

                    for (BoundingBox bbox : listBBoxRect) {
                        Point p1 = new Point(bbox.xmin + fromLeft, bbox.ymin + fromTop);
                        Point p2 = new Point(bbox.xmax + fromLeft, bbox.ymax + fromTop);
                        drawBoundingBox(gr, bbox, sw, p1, p2, Color.green, false);
                    }
                    if (selectedBBox != null) {
                        Point p1 = new Point(selectedBBox.xmin + fromLeft, selectedBBox.ymin + fromTop);
                        Point p2 = new Point(selectedBBox.xmax + fromLeft, selectedBBox.ymax + fromTop);
                        drawBoundingBox(gr, selectedBBox, sw, p1, p2, Color.green, true);
                    } else {
                    }
                } else if (showDrawableRegion && drawableRoiList.size() > 0) {
                    CPoint prev = drawableRoiList.get(0);
                    for (CPoint p : drawableRoiList) {
                        gr.setColor(Color.red);
                        int wx = 5;
                        gr.fillRect(p.column + fromLeft - 2, p.row + fromTop - 2, wx, wx);
                        gr.setColor(Color.green);
                        gr.drawLine(prev.column + fromLeft, prev.row + fromTop, p.column + fromLeft, p.row + fromTop);
                        prev = p;
                    }
                    CPoint p = drawableMousePos;
                    gr.drawLine(prev.column + fromLeft, prev.row + fromTop, p.column + fromLeft, p.row + fromTop);
                }
            }
            this.paintComponents(gr);
        }
        gr.setColor(Color.red);
        gr.drawRect(0, 0, wPanel - 1, hPanel - 1);
        gr.drawRect(1, 1, wPanel - 3, hPanel - 3);

    }

    private String inputMessage() {
        MyDialog dlg = new MyDialog(frame);
        String results = dlg.run();
        System.out.println("results = " + results);
        return results;
    }

    private void initialize() {
        addKeyListener(this);
        setFocusable(true);
        requestFocus();

        ItemHandler handler = new ItemHandler();
        String[] elements = {
            "Next Image",
            "Prev Image",
            "Clone",
            "Load Image",
            "Save Image",
            "AutoSize",
            "AutoSizeAspect",
            "Statistics",
            "Histogram",
            "Revert",
            "Original",
            "Gray",
            "HSV",
            "Red",
            "Green",
            "Blue",
            "Edge",
            "Equalize",
            "Smooth",
            "Sharpen",
            "Bounding Box",
            "Save Bounding Box as Pascal VOC XML",
            "ROI",
            "Clone ROI",
            "DROI",
            "Save DROI Corners",
            "Save DROI Pixels",
            "Load DROI Corners"
        };

        ButtonGroup itemsGroup = new ButtonGroup();
        items = new JRadioButtonMenuItem[elements.length];

        // construct each menu item and add to popup menu; also  
        // enable event handling for each menu item  
        for (int i = 0; i < elements.length; i++) {
            items[i] = new JRadioButtonMenuItem(elements[i]);
            popupMenu.add(items[i]);
            itemsGroup.add(items[i]);
            items[i].addActionListener(handler);
        }
        setComponentPopupMenu(popupMenu);

        lbl = new JLabel("X:Y");
        this.add(lbl);
        lbl.setBounds(new Rectangle(10, 0, 500, 30));
        lbl.setBackground(Color.yellow);
        lbl.setForeground(Color.GREEN);
        lbl.setVisible(true);
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        this.updateUI();

        addMouseListener(new java.awt.event.MouseAdapter() {
            

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    if (activateBoundingBox) {
                        lastSelectedClass = inputMessage();
                        //System.out.println("updated classLabel = " + selectedClass);
                        if (lastSelectedClass != null) {
                            isBBoxCancelled = false;
                            selectedBBox.name = lastSelectedClass;
                            repaint();
                            return;
                        }
                    } else {
////                    drawableMousePos = e.getPoint();
////                    drawableRoiList.add(drawableMousePos);
                        activateDrawableROI = false;
                    }
//                    if (frm != null) {
//                        flag_once = 0;
//                        frm.setSize(prev_width, prev_height);
//                    }
                }
            }

            public void mousePressed(java.awt.event.MouseEvent e) {

                if ((activateROI || activateBoundingBox) && e.getButton() == MouseEvent.BUTTON1) {
                    showRegion = true;
                    mousePosTopLeft = e.getPoint();
                    if (selectedBBox != null) {
                        Point p = e.getPoint();
                        isBBoxResizeTopLeft = false;
                        isBBoxResizeTopRight = false;
                        isBBoxResizeBottomLeft = false;
                        isBBoxResizeBottomRight = false;
                        int t = 5;
                        if (p.x > selectedBBox.xmin + fromLeft - t && p.x < selectedBBox.xmin + fromLeft + t && p.y > selectedBBox.ymin + fromTop - t && p.y < selectedBBox.ymin + fromTop + t) {
                            isBBoxResizeTopLeft = true;
                        } else if (p.x > selectedBBox.xmax + fromLeft - t && p.x < selectedBBox.xmax + fromLeft + t && p.y > selectedBBox.ymin + fromTop - t && p.y < selectedBBox.ymin + fromTop + t) {
                            isBBoxResizeTopRight = true;
                        } else if (p.x > selectedBBox.xmax + fromLeft - t && p.x < selectedBBox.xmax + fromLeft + t && p.y > selectedBBox.ymax + fromTop - t && p.y < selectedBBox.ymax + fromTop + t) {
                            isBBoxResizeBottomRight = true;
                        } else if (p.x > selectedBBox.xmin + fromLeft - t && p.x < selectedBBox.xmin + fromLeft + t && p.y > selectedBBox.ymax + fromTop - t && p.y < selectedBBox.ymax + fromTop + t) {
                            isBBoxResizeBottomLeft = true;
                        } else if (p.x > selectedBBox.xmin + fromLeft && p.x < selectedBBox.xmax + fromLeft
                                && p.y > selectedBBox.ymin + fromTop && p.y < selectedBBox.ymax + fromTop) {
                            isBBoxDragged = true;
                            referenceDragPos = e.getPoint();
                        } else {
                            isBBoxDragged = false;
                        }
                    } else {
                        isBBoxCancelled = false;
                        isBBoxDragged = false;
                    }
                } else if (activateDrawableROI && e.getButton() == MouseEvent.BUTTON1) {
                    showDrawableRegion = true;
                } else {
                    lblShow = true;
                }
//                checkForTriggerEvent(e);
            }

            public void mouseReleased(java.awt.event.MouseEvent e) {
                if ((activateROI || activateBoundingBox) && e.getButton() == MouseEvent.BUTTON1) {
                    mousePos = e.getPoint();
                    mousePosBottomRight = e.getPoint();

                    if (activateBoundingBox) {
                        if (isBBoxResizeTopLeft && selectedBBox != null) {
                            mousePosTopLeft = e.getPoint();
                            selectedBBox.xmin = mousePosTopLeft.x - fromLeft;
                            selectedBBox.ymin = mousePosTopLeft.y - fromTop;
                            repaint();
                            return;
                        } else if (isBBoxResizeTopRight && selectedBBox != null) {
                            mousePosTopRight = e.getPoint();
                            selectedBBox.xmax = mousePosTopRight.x - fromLeft;
                            selectedBBox.ymin = mousePosTopRight.y - fromTop;
                            repaint();
                            return;
                        } else if (isBBoxResizeBottomLeft && selectedBBox != null) {
                            mousePosBottomLeft = e.getPoint();
                            selectedBBox.xmin = mousePosBottomLeft.x - fromLeft;
                            selectedBBox.ymax = mousePosBottomLeft.y - fromTop;
                            repaint();
                            return;
                        } else if (isBBoxResizeBottomRight && selectedBBox != null) {
                            mousePosBottomRight = e.getPoint();
                            selectedBBox.xmax = mousePosBottomRight.x - fromLeft;
                            selectedBBox.ymax = mousePosBottomRight.y - fromTop;
                            repaint();
                            return;
                        } 
                        if (!isBBoxDragged) {
                            selectedBBox = isMouseClickedOnBoundingBox();
                        }
                    }
                    if (activateBoundingBox && !FactoryUtils.isMousePosEqual(mousePosTopLeft, mousePosBottomRight)) {
                        if (isBBoxDragged) {
                            Point p1 = new Point(selectedBBox.xmin + (mousePos.x - referenceDragPos.x),
                                    selectedBBox.ymin + (mousePos.y - referenceDragPos.y));
                            Point p2 = new Point(p1.x + (selectedBBox.xmax - selectedBBox.xmin), p1.y + (selectedBBox.ymax - selectedBBox.ymin));
                            selectedBBox.xmin = p1.x;
                            selectedBBox.ymin = p1.y;
                            selectedBBox.xmax = p2.x;
                            selectedBBox.ymax = p2.y;
                            isBBoxDragged = false;
                            repaint();
                            return;
                        }
                        int w = Math.abs(mousePosTopLeft.x - mousePosBottomRight.x);
                        int h = Math.abs(mousePosTopLeft.y - mousePosBottomRight.y);
                        if (w < 5 || h < 5) {
                            return;
                        }
                        if (lastSelectedClass==null || lastSelectedClass.isEmpty()) {
                            lastSelectedClass = inputMessage();
                        }
                        
                        //System.out.println("classLabel = " + selectedClass);
                        if (lastSelectedClass == null) {
                            isBBoxCancelled = true;
                            selectedBBox = null;
                            repaint();
                            return;
                        } else {
                            isBBoxCancelled = false;
                        }
                        Rectangle r = new Rectangle(mousePosTopLeft.x - fromLeft, mousePosTopLeft.y - fromTop, w, h);
                        BoundingBox bbox = new BoundingBox(lastSelectedClass, r, 0, 0);
                        selectedBBox = bbox;
                        listBBoxRect.add(bbox);
                        repaint();
                        selectedBBox=null;
                        return;
                    }
                    repaint();
                } else if (activateDrawableROI && e.getButton() == MouseEvent.BUTTON1) {
                    showDrawableRegion = true;
                    drawableMousePos = new CPoint(e.getPoint().y - fromTop, e.getPoint().x - fromLeft);
                    System.out.println("row:" + drawableMousePos.row + " col:" + drawableMousePos.column);
                    if (drawableRoiList.size() == 0) {
                        drawableRoiList.add(drawableMousePos);
                    } else if (drawableRoiList.get(drawableRoiList.size() - 1).column != drawableMousePos.column || drawableRoiList.get(drawableRoiList.size() - 1).row != drawableMousePos.row) {
                        drawableRoiList.add(drawableMousePos);
                    }

                } else {
                    lblShow = false;
                }
                checkForTriggerEvent(e);
            }

            private void checkForTriggerEvent(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            private BoundingBox isMouseClickedOnBoundingBox() {
                BoundingBox ret = null;
                if (listBBoxRect.size() == 0) {
                    return null;
                } else {
                    for (BoundingBox bbox : listBBoxRect) {
                        if (FactoryUtils.isPointInROI(mousePos, bbox.getRectangle(fromLeft, fromTop))) {
                            ret = bbox;
                            return ret;
                        }
                    }
                    return ret;
                }
            }

        });

        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseMoved(java.awt.event.MouseEvent e) {
                drawableMousePos = new CPoint(e.getPoint().y - fromTop, e.getPoint().x - fromLeft);
//                repaint();
            }

            public void mouseDragged(java.awt.event.MouseEvent e) {
                mousePos = e.getPoint();
                repaint();
            }
        }
        );

//        this.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                activateAutoSizeAspect = true;
//                repaint();
//            }
//        });
    }

    private String getImageSize() {
        if (originalBufferedImage == null) {
            return "";
        }
        String str = "[" + currBufferedImage.getHeight() + "," + currBufferedImage.getWidth() + "] ";
        return str;
    }

    public void saveImage() {
        ImageProcess.imwrite(currBufferedImage);
    }

    public void saveImage(String fileName) {
        ImageProcess.imwrite(currBufferedImage, fileName);
    }

    private void drawBoundingBox(Graphics2D gr, BoundingBox bbox, int stroke, Point p1, Point p2, Color col, boolean isCornerVisible) {
        if (FactoryUtils.isMousePosEqual(p1, p2)) {
            return;
        }
        int w = Math.abs(p2.x - p1.x);
        int h = Math.abs(p2.y - p1.y);

        if (bbox != null) {
            gr.setStroke(new BasicStroke(3));
//            gr.setColor(Color.black);
//            int width = gr.getFontMetrics().stringWidth(bbox.name);
//            gr.fillRect(mousePosTopLeft.x, mousePosTopLeft.y - 20, width, 20);
            gr.setColor(Color.green);
            gr.drawString(bbox.name, p1.x, p1.y - 5);
        }

        gr.setStroke(new BasicStroke(stroke));
        gr.setColor(col);
        gr.drawRect(p1.x, p1.y, w, h);
        if (isCornerVisible) {
            gr.setColor(Color.red);
            int wx = 8;
            int ww = 4;
            gr.fillRect(p1.x - ww, p1.y - ww, wx, wx);
            gr.fillRect(p1.x - ww + w, p1.y - ww, wx, wx);
            gr.fillRect(p1.x - ww + w, p1.y - ww + h, wx, wx);
            gr.fillRect(p1.x - ww, p1.y - ww + h, wx, wx);
            gr.setStroke(new BasicStroke(1));
        }
    }

    private class ItemHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // determine which menu item was selected  
            try {
                setDefaultValues();
                JRadioButtonMenuItem obj = (JRadioButtonMenuItem) e.getSource();
                if (obj.getText().equals("Next Image")) {
                    loadNextImage();
                    return;
                } else if (obj.getText().equals("Prev Image")) {
                    loadPrevImage();
                    return;
                } else if (obj.getText().equals("Clone")) {
                    new FrameImage(CMatrix.getInstance(currBufferedImage), imagePath, caption).setVisible(true);
                    return;
                } else if (obj.getText().equals("Load Image")) {
                    activateStatistics = false;
                    File fl = ImageProcess.readImageFileFromFolderWithDirectoryPath(imagePath);
                    if (fl == null) {
                        return;
                    }
                    BufferedImage bf = ImageProcess.readImageFromFile(fl.getAbsolutePath());
                    if (bf != null) {
                        originalBufferedImage = bf;
                        currBufferedImage = (originalBufferedImage);
                        imagePath = fl.getAbsolutePath();
                        imageFolder = FactoryUtils.getFolderPath(imagePath);
                        fileName = fl.getName();
//                    fileList = FactoryUtils.getFileListInFolder(imageFolder);
//                    currentImageIndex = getCurrentImageIndex();
                        imgData = ImageProcess.imageToPixelsFloat(currBufferedImage);
                        if (activateStatistics) {
                            stat = TStatistics.getStatistics(currBufferedImage);
                        }
                    } else {
                        return;
                    }
                } else if (obj.getText().equals("Save Image")) {
                    ImageProcess.imwrite(currBufferedImage);
                } else if (obj.getText().equals("Histogram")) {
                    activateHistogram = true;
                    if (isRedChannel) {
                        BufferedImage temp = ImageProcess.getRedChannelGray(currBufferedImage);
                        CMatrix.getInstance(temp).imhist();
                    } else if (isGreenChannel) {
                        BufferedImage temp = ImageProcess.getGreenChannelGray(currBufferedImage);
                        CMatrix.getInstance(temp).imhist();
                    } else if (isBlueChannel) {
                        BufferedImage temp = ImageProcess.getBlueChannelGray(currBufferedImage);
                        CMatrix.getInstance(temp).imhist();
                    } else {
                        CMatrix.getInstance(currBufferedImage).imhist();
                        //CMatrix.getInstance(currBufferedImage).ocv_imhist("");
                    }

                } else if (obj.getText().equals("Statistics")) {
                    activateStatistics = true;
                    currBufferedImage = ImageProcess.toGrayLevel(originalBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                    stat = TStatistics.getStatistics(currBufferedImage);
                } else if (obj.getText().equals("Original")) {
                    activateOriginal = true;
                    activateStatistics = false;
//                originalBufferedImage = ImageProcess.resizeAspectRatio(originalBufferedImage, getWidth() - 2 * panWidth, getHeight() - 2 * panWidth);
                    currBufferedImage = ImageProcess.clone(originalBufferedImage);
                    originalBufferedImageTemp = ImageProcess.clone(originalBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                    repaint();
                } else if (obj.getText().equals("Revert")) {
                    activateRevert = true;
                    currBufferedImage = ImageProcess.revert(currBufferedImage);
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("Gray")) {
                    activateGray = true;
                    currBufferedImage = ImageProcess.rgb2gray(currBufferedImage);
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("HSV")) {
                    activateHSV = true;
                    currBufferedImage = ImageProcess.toHSVColorSpace(currBufferedImage);
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("Red")) {
                    isRedChannel = true;
                    isBlueChannel = isGreenChannel = false;
                    activateRedChannel = true;
                    currBufferedImage = ImageProcess.isolateChannel(currBufferedImage, "red");
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("Green")) {
                    isGreenChannel = true;
                    isRedChannel = isBlueChannel = false;
                    activateGreenChannel = true;
                    currBufferedImage = ImageProcess.isolateChannel(currBufferedImage, "green");
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("Blue")) {
                    isBlueChannel = true;
                    isRedChannel = isGreenChannel = false;
                    activateBlueChannel = true;
                    currBufferedImage = ImageProcess.isolateChannel(currBufferedImage, "blue");
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("Equalize")) {
                    activateEqualize = true;
                    currBufferedImage = ImageProcess.equalizeHistogram(ImageProcess.rgb2gray(currBufferedImage));
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("Edge")) {
                    activateEdge = true;
                    currBufferedImage = ImageProcess.edgeDetectionCanny(currBufferedImage, 0.3f, 1.0f, 2.5f, 3, false);
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("Smooth")) {
                    currBufferedImage = ImageProcess.filterGaussian(currBufferedImage, 3);
                    originalBufferedImageTemp = ImageProcess.clone(currBufferedImage);
                    imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                } else if (obj.getText().equals("AutoSize")) {
                    activateAutoSize = true;
                } else if (obj.getText().equals("AutoSizeAspect")) {
                    activateAutoSizeAspect = true;
                } else if (obj.getText().equals("ROI")) {
                    activateROI = true;
                } else if (obj.getText().equals("Bounding Box")) {
                    listBBoxRect.clear();
                    activateBoundingBox = true;
                } else if (obj.getText().equals("Save Bounding Box as Pascal VOC XML")) {
                    savePascalVocXML();
                } else if (obj.getText().equals("Clone ROI")) {
                    showRegion = false;
                    activateCloneROI = true;
                    mousePosTopLeft.x -= fromLeft;
                    mousePosTopLeft.y -= fromTop;
                    mousePosBottomRight.x -= fromLeft;
                    mousePosBottomRight.y -= fromTop;
//                CMatrix cm = CMatrix.getInstance(currBufferedImage).subMatrix(mousePosTopLeft, mousePosBottomRight);
                    CRectangle cr = new CRectangle(mousePosTopLeft.y, mousePosTopLeft.x,
                            Math.abs(mousePosBottomRight.x - mousePosTopLeft.x), Math.abs(mousePosBottomRight.y - mousePosTopLeft.y));
                    BufferedImage bf = ImageProcess.cropImage(currBufferedImage, cr);
//                BufferedImage bf = ImageProcess.pixelsToImageGray(cm.toIntArray2D());
//                    new FrameImage(CMatrix.getInstance(bf), obj.getText()).setVisible(true);
                    new FrameImage(CMatrix.getInstance(bf), imagePath, caption).setVisible(true);
                } else if (obj.getText().equals("DROI")) {
                    activateDrawableROI = true;
                    activateROI = false;
                    drawableRoiList.clear();
                } else if (obj.getText().equals("Save DROI Corners")) {
                    if (drawableRoiList.size() > 0) {
                        CPoint[] plst = new CPoint[drawableRoiList.size()];
                        plst = drawableRoiList.toArray(plst);
                        int[][] d = new int[drawableRoiList.size()][2];
                        String line = "";
                        for (int i = 0; i < drawableRoiList.size(); i++) {
                            d[i][0] = plst[i].row;
                            d[i][1] = plst[i].column;
                            line += plst[i].row + "," + plst[i].column + ";";
                        }
                        String fileName = FactoryUtils.inputMessage("Write Unique ID Label", "");
                        if (!FactoryUtils.isFolderExist("data")) {
                            FactoryUtils.makeDirectory("data");
                        }
                        line = imagePath + ";" + fileName + ";" + line + "\n";
                        FactoryUtils.writeOnFile("data/DROI_Corners.txt", line);
                        //FactoryUtils.writeToFile("data\\" + fileName, d);
                    }
                } else if (obj.getText().equals("Save DROI Pixels")) {
                    if (drawableRoiList.size() > 0) {
                        CPoint[] plst = new CPoint[drawableRoiList.size()];
                        plst = drawableRoiList.toArray(plst);
                        CPoint[] pixels = FactoryUtils.getPointsInROI(plst);
                        FactoryUtils.savePointsInROI(pixels);
                    }
                } else if (obj.getText().equals("Load DROI Corners")) {
                    drawableRoiList.clear();
                    float[][] d = FactoryUtils.readFromFile(",");
                    int[][] dd = FactoryUtils.toIntArray2D(d);
                    for (int i = 0; i < d.length; i++) {
                        CPoint p = new CPoint(dd[i][0], dd[i][1]);
                        drawableRoiList.add(p);
                    }
                }
                repaint();
            } catch (Exception ex) {
                System.err.println(ex.toString());
            }
        }

    }

    private void loadNextImage() {
        imageIndex++;
        if (imageIndex > imageFiles.length - 1) {
            imageIndex--;
            return;
        }
        BufferedImage bf = ImageProcess.readImageFromFile(imageFiles[imageIndex]);
        setImage(bf, imagePath,caption);
        frm.setTitle(imageFiles[imageIndex].getPath());
        fileName = imageFiles[imageIndex].getName();
        imagePath = imageFiles[imageIndex].getAbsolutePath();
    }

    private void loadPrevImage() {
        imageIndex--;
        if (imageIndex < 0) {
            imageIndex++;
            return;
        }
        BufferedImage bf = ImageProcess.readImageFromFile(imageFiles[imageIndex]);
        setImage(bf, imagePath,caption);
        frm.setTitle(imageFiles[imageIndex].getPath());
        fileName = imageFiles[imageIndex].getName();
        imagePath = imageFiles[imageIndex].getAbsolutePath();
    }

    private void savePascalVocXML() {
        List<PascalVocObject> lstObject = new ArrayList();
        for (BoundingBox bbox : listBBoxRect) {
            lstObject.add(new PascalVocObject(bbox));
        }
        if (lstObject.size()>0) {
            String xml = FactoryUtils.serializePascalVocXML(imageFolder, fileName, imagePath, lstObject);
        }else{
            File file=new File(imagePath);
            if (FactoryUtils.isFileExist(imageFolder+"/"+FactoryUtils.getFileName(file.getName()) + ".xml")){
                FactoryUtils.deleteFile(imageFolder+"/"+FactoryUtils.getFileName(file.getName()) + ".xml");
            }
        }        
        loadNextImage();
        activateBoundingBox = true;
    }

    public void setActivateStatistics(boolean activateStatistics) {
        this.activateStatistics = activateStatistics;
//        currBufferedImage = ImageProcess.toGrayLevel(originalBufferedImage);
//        imgData = ImageProcess.imageToPixels255(currBufferedImage);
        //stat = TStatistics.getStatistics(currBufferedImage);
        //repaint();
    }

    private void setDefaultValues() {
        activateROI = false;
        activateBoundingBox = false;
        activateDrawableROI = false;
        activateSaveImage = false;
        activateRevert = false;
        activateCloneROI = false;
        activateOriginal = false;
        activateHistogram = false;
        activateStatistics = false;
        activateRedChannel = false;
        activateGreenChannel = false;
        activateBlueChannel = false;
        activateRGB = false;
        activateGray = false;
        activateHSV = false;
        activateEdge = false;
        activateEqualize = false;
        activateAutoSize = false;
        activateAutoSizeAspect = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP) {
            if (imageIndex < imageFiles.length - 1) {
                imageIndex++;
            }
        } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_DOWN) {
            if (imageIndex > 0) {
                imageIndex--;
            }
        } else if (key == KeyEvent.VK_S) {
            savePascalVocXML();
            BufferedImage bf = ImageProcess.readImageFromFile(imageFiles[imageIndex]);
            setImage(bf, imagePath,caption);
            frm.setTitle(imageFiles[imageIndex].getPath());
            fileName = imageFiles[imageIndex].getName();
            imagePath = imageFiles[imageIndex].getAbsolutePath();
            repaint();
            return;
        } else if (key == KeyEvent.VK_DELETE) {
            listBBoxRect.remove(selectedBBox);
            selectedBBox = null;
            repaint();
            return;
        }
        BufferedImage bf = ImageProcess.readImageFromFile(imageFiles[imageIndex]);
        frm.setTitle(imageFiles[imageIndex].getPath());
        fileName = imageFiles[imageIndex].getName();
        imagePath = imageFiles[imageIndex].getAbsolutePath();
        listBBoxRect.clear();
        selectedBBox = null;
        setDefaultValues();
        setImage(bf, imagePath,caption);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
//    public boolean isActivateAutoSize() {
//        return activateAutoSize;
//    }
//
//    public void setActivateAutoSize(boolean activateAutoSize) {
//        this.activateAutoSize = activateAutoSize;
//        repaint();
//    }
//
//    public boolean isActivateAutoSizeAspect() {
//        return activateAutoSizeAspect;
//    }
//
//    public void setActivateAutoSizeAspect(boolean activateAutoSizeAspect) {
//        this.activateAutoSizeAspect = activateAutoSizeAspect;
//        repaint();
//    }
}
