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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import jazari.utils.MyDialog;
import jazari.utils.pascalvoc.AnnotationPascalVOCFormat;
import jazari.utils.pascalvoc.PascalVocAttribute;
import jazari.utils.pascalvoc.PascalVocBoundingBox;
import jazari.utils.pascalvoc.PascalVocObject;
import jazari.utils.pascalvoc.PascalVocSource;

public class PanelPicture extends JPanel implements KeyListener, MouseWheelListener {

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
    private boolean lblShow = true;
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
    private boolean activateBinarize = false;
    private boolean activateROI = false;
    private boolean isMouseDraggedForImageMovement;
    public boolean activateBoundingBox = false;
    public boolean activatePolygon = false;
    public boolean activateDrawableROI = false;
    private boolean activateCloneROI = false;
    private boolean activateGreenChannel = false;
    private boolean activateBlueChannel = false;
    private boolean activateRGB = false;
    private boolean activateGray = false;
    private boolean activateHSV = false;
    private boolean activateEdge = false;
    private boolean activateEqualize = false;
    private boolean activateAutoSize = false;
    private AnnotationPascalVOCFormat pascalVocXML;
    private List<PascalVocObject> listPascalVocObject = new ArrayList();
    private PascalVocSource source = new PascalVocSource();
    private PascalVocBoundingBox selectedBBox = null;
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
    private FrameImage frame;
    private String caption;
    private String lastSelectedClassName;
    private Color lastSelectedBoundingBoxColor;
    private Color defaultBoundingBoxColor = new Color(255, 255, 0);
    private boolean isMouseDraggedForBoundingBoxMovement = false;
    public boolean isSeqenceVideoFrame;
    private Map<String, Color> mapBBoxColor = new HashMap();
    private String xmlFileName;
    private String currentFolderName;
    private PascalVocObject selectedPascalVocObject;
    private Point[] lastPositionOfDraggedBBox;
    private Point referenceMousePositionForImageMovement;
    private Point currentMousePositionForImageMovement;
    private int defaultStrokeWidth = 2;
    private int indexOfCurrentImageFile = 0;
    private Color colorDashedLine=new Color(255, 255, 0);

    public PanelPicture(FrameImage frame) {
        this.frame = frame;
        initialize();
        this.addMouseWheelListener(this);
    }

    public PanelPicture(JFrame frame) {
        this.frame = (FrameImage) frame;
        initialize();
        this.addMouseWheelListener(this);
    }

    private void setImagePath(String path) {
        if (path == null || path.equals("")) {
            imagePath = path;
            return;
        }
        imagePath = path;
        File f = new File(imagePath);
        imageFolder = f.getParent();
        imageFiles = FactoryUtils.getFileArrayInFolderForImages(imageFolder);
        //imageFiles = sortFileListByNumber(imageFiles);
        fileName = f.getName();
        for (int i = 0; i < imageFiles.length; i++) {
            if (imageFiles[i].getName().equals(fileName)) {
                imageIndex = i;
                break;
            }
        }
        frame.imagePath = imagePath;
        frame.setTitle(imagePath);
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

    public BufferedImage rawImage;

    public void setImage(BufferedImage image, String imagePath, String caption, boolean isClearBbox) {
        System.gc();
        this.caption = caption;
        if (activateBoundingBox && imagePath != null && !imagePath.isEmpty()) {
            String fileName = FactoryUtils.getFileName(imagePath) + ".xml";
            String folderName = FactoryUtils.getFolderPath(imagePath);
            currentFolderName = folderName;
            xmlFileName = folderName + "/" + fileName;
            boolean checkXML = new File(folderName, fileName).exists();
            if (checkXML) {
                if (FactoryUtils.isFileExist(folderName + "/class_labels.txt")) {
                    mapBBoxColor = buildHashMap(folderName + "/class_labels.txt");
                }
                AnnotationPascalVOCFormat bb = FactoryUtils.deserializePascalVocXML(folderName + "/" + fileName);
                if (isClearBbox && !isSeqenceVideoFrame) {
                    listPascalVocObject.clear();
                    listPascalVocObject = bb.lstObjects;
                }

                showRegion = true;
                activateBoundingBox = true;
                source = bb.source;
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
        //setPreferredSize(new Dimension(originalBufferedImage.getWidth() + 100, originalBufferedImage.getHeight() + 100));
        zoom_factor = original_zoom_factor;

        updateImagePosition();

        repaint();
        if (!this.imagePath.equals(imagePath)) {
            setImagePath(imagePath);
        }
    }

    public void setImage(BufferedImage image, String imagePath, String caption) {
        setImage(image, imagePath, caption, true);
    }

    public void setZoomImage(BufferedImage image, String imagePath, String caption) {
        this.caption = caption;
        if (activateBoundingBox && imagePath != null && !imagePath.isEmpty()) {
            String fileName = FactoryUtils.getFileName(imagePath) + ".xml";
            String folderName = FactoryUtils.getFolderPath(imagePath);
            boolean checkXML = new File(folderName, fileName).exists();
//            if (checkXML) {
//                AnnotationPascalVOCFormat bb = FactoryUtils.deserializePascalVocXML(folderName + "/" + fileName);
//                listPascalVocObject.clear();
//                listPascalVocObject = bb.lstObjects;
//                showRegion = true;
//                source = bb.source;
//                activateBoundingBox = true;
//            }
        }

        currBufferedImage = image;
        //updateImagePosition();
        imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
        lbl.setText(getImageSize() + "X:Y");
        //setPreferredSize(new Dimension(originalBufferedImage.getWidth() + 100, originalBufferedImage.getHeight() + 100));
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

    FrameImage frm = null;

    public void setFrame(FrameImage frm) {
        this.frm = frm;
    }

    int flag_once = 0;
    int prev_width = 500;
    int prev_height = 500;
    private boolean isBBoxCancelled = false;
    private boolean isBBoxDragged = false;
    private Point referenceDragPos;
    private Point relativeDragPosFromTop = new Point();

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

            updatePanelOffsetValuesWhileImageMoving();

            paintIfImageAutoSized(gr);

            gr.drawImage(currBufferedImage, fromLeft, fromTop, this);

            gr.setColor(Color.blue);
            gr.drawRect(fromLeft, fromTop, currBufferedImage.getWidth(), currBufferedImage.getHeight());

            if (activateHistogram && histFrame != null) {
                showHistogram();
            }
            if (activateStatistics && stat != null) {
                paintStatistics(gr);
            }
            if (!activateROI && !activateDrawableROI && !activateBoundingBox) {
                //paintPixelInfo(gr, wImg, hImg);
                paintDrawableROI(gr);
            } else {
                if (showRegion && activateROI) {
                    paintROI(gr);
                } else if (showRegion && activateBoundingBox) {
                    paintBoundingBoxes(gr);
                } else if (showDrawableRegion && drawableRoiList.size() > 0) {
                    paintShowDrawableROI(gr);
                }
            }
            if (isMouseInCanvas()) {
                paintPixelInfo(gr, currBufferedImage.getWidth(), currBufferedImage.getHeight());
                paintMouseDashedLines(gr, wPanel, hPanel, colorDashedLine);
            }
            this.paintComponents(gr);
        }
        paintFrameRectangle(gr, wPanel, hPanel);
    }

    private String setBoundingBoxProperties(String className) {
        MyDialog dlg = new MyDialog(frame, imageFolder, className);
        String results = dlg.run();
        return results;
    }

    private Color buildColor(String str) {
        String[] s = str.split(" ");
        int r = Integer.parseInt(s[1]);
        int g = Integer.parseInt(s[2]);
        int b = Integer.parseInt(s[3]);
        Color ret = new Color(r, g, b);
        return ret;
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
            "Binarize",
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
            //"Save Bounding Box as Pascal VOC XML",
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
        lbl.setBounds(new Rectangle(10, 0, 700, 30));
        lbl.setBackground(Color.yellow);
        lbl.setForeground(Color.GREEN);
        lbl.setVisible(true);
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        this.updateUI();

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateImagePosition();
                repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    if (activateBoundingBox && selectedBBox != null) {
                        String ret = setBoundingBoxProperties(selectedBBox.name);
                        mapBBoxColor = buildHashMap(currentFolderName + "/class_labels.txt");
                        if (ret.split(":").length == 0) {
                            System.err.println("MyDialog dan gelen mesaj ikiye bölünemedi");
                        }
                        lastSelectedClassName = ret.split(":")[0];
                        lastSelectedBoundingBoxColor = buildColor(ret.split(":")[1]);
                        selectedPascalVocObject.name = lastSelectedClassName;

                        //System.out.println("updated classLabel = " + selectedClass);
                        if (!(lastSelectedClassName == null || lastSelectedClassName.isEmpty())) {
                            isBBoxCancelled = false;
                            selectedBBox.name = lastSelectedClassName;
                            repaint();
                            return;
                        }
                    } else {
                        currBufferedImage = ImageProcess.clone(originalBufferedImage);
                        adjustImageToPanel(currBufferedImage, false);
                        repaint();
                    }
                }
            }

            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    if (currBufferedImage.getWidth() > getWidth() || currBufferedImage.getHeight() > getHeight()) {
                        isMouseDraggedForImageMovement = true;
                        referenceMousePositionForImageMovement = FactoryUtils.clone(e.getPoint());
                        currentMousePositionForImageMovement = FactoryUtils.clone(e.getPoint());
                        setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    } else {
                        isMouseDraggedForImageMovement = false;
                    }
                }

                if ((activateROI || activateBoundingBox) && e.getButton() == MouseEvent.BUTTON1) {
                    showRegion = true;
                    mousePosTopLeft = constraintMousePosition(e);
                    if (!isBBoxDragged) {
                        //selectedBBox = isMouseClickedOnBoundingBox();
                    }
                    if (selectedBBox != null) {
                        Point p = new Point(e.getPoint().x - fromLeft, e.getPoint().y - fromTop);
                        //Point p = e.getPoint();
                        isBBoxResizeTopLeft = false;
                        isBBoxResizeTopRight = false;
                        isBBoxResizeBottomLeft = false;
                        isBBoxResizeBottomRight = false;
                        int t = 10;
                        if (p.x > scaleWithZoomFactor(selectedBBox.xmin) - t && p.x < scaleWithZoomFactor(selectedBBox.xmin) + t && p.y > scaleWithZoomFactor(selectedBBox.ymin) - t && p.y < scaleWithZoomFactor(selectedBBox.ymin) + t) {
                            isBBoxResizeTopLeft = true;
                        } else if (p.x > scaleWithZoomFactor(selectedBBox.xmax) - t && p.x < scaleWithZoomFactor(selectedBBox.xmax) + t && p.y > scaleWithZoomFactor(selectedBBox.ymin) - t && p.y < scaleWithZoomFactor(selectedBBox.ymin) + t) {
                            isBBoxResizeTopRight = true;
                        } else if (p.x > scaleWithZoomFactor(selectedBBox.xmax) - t && p.x < scaleWithZoomFactor(selectedBBox.xmax) + t && p.y > scaleWithZoomFactor(selectedBBox.ymax) - t && p.y < scaleWithZoomFactor(selectedBBox.ymax) + t) {
                            isBBoxResizeBottomRight = true;
                        } else if (p.x > scaleWithZoomFactor(selectedBBox.xmin) - t && p.x < scaleWithZoomFactor(selectedBBox.xmin) + t && p.y > scaleWithZoomFactor(selectedBBox.ymax) - t && p.y < scaleWithZoomFactor(selectedBBox.ymax) + t) {
                            isBBoxResizeBottomLeft = true;
                        } else if (p.x > scaleWithZoomFactor(selectedBBox.xmin) && p.x < scaleWithZoomFactor(selectedBBox.xmax) && p.y > scaleWithZoomFactor(selectedBBox.ymin) && p.y < scaleWithZoomFactor(selectedBBox.ymax)) {
                            isBBoxDragged = true;
                            referenceDragPos = e.getPoint();
                            relativeDragPosFromTop.x = referenceDragPos.x - (scaleWithZoomFactor(selectedBBox.xmin) + fromLeft);
                            relativeDragPosFromTop.y = referenceDragPos.y - (scaleWithZoomFactor(selectedBBox.ymin) + fromTop);
                            //System.out.println("rel:"+relativeDragPosFromTop);
                        } else {
                            isBBoxDragged = false;
                        }
                    } else {
                        isBBoxCancelled = false;
                        isBBoxDragged = false;
                    }
                } else if (activateDrawableROI && e.getButton() == MouseEvent.BUTTON1) {
                    showDrawableRegion = true;
                }
                repaint();
            }

            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    if (isMouseDraggedForImageMovement) {
                        isMouseDraggedForImageMovement = false;
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        repaint();
                        return;
                    }
                }

                if ((activateROI || activateBoundingBox) && e.getButton() == MouseEvent.BUTTON1) {
                    setDefaultCursor();
                    mousePos = constraintMousePosition(e);

                    if (activateBoundingBox) {
                        if (isBBoxResizeTopLeft && selectedBBox != null) {
                            mousePosTopLeft = constraintMousePosition(e);
                            selectedBBox.xmin = unScaleWithZoomFactorX(mousePosTopLeft.x) - fromLeft;
                            selectedBBox.ymin = unScaleWithZoomFactorY(mousePosTopLeft.y) - fromTop;
                            isBBoxResizeTopLeft = false;
                            repaint();
                            return;
                        } else if (isBBoxResizeTopRight && selectedBBox != null) {
                            mousePosTopRight = constraintMousePosition(e);
                            selectedBBox.xmax = unScaleWithZoomFactorX(mousePosTopRight.x) - fromLeft;
                            selectedBBox.ymin = unScaleWithZoomFactorY(mousePosTopRight.y) - fromTop;
                            isBBoxResizeTopRight = false;
                            repaint();
                            return;
                        } else if (isBBoxResizeBottomLeft && selectedBBox != null) {
                            mousePosBottomLeft = constraintMousePosition(e);
                            selectedBBox.xmin = unScaleWithZoomFactorX(mousePosBottomLeft.x) - fromLeft;
                            selectedBBox.ymax = unScaleWithZoomFactorY(mousePosBottomLeft.y) - fromTop;
                            isBBoxResizeBottomLeft = false;
                            repaint();
                            return;
                        } else if (isBBoxResizeBottomRight && selectedBBox != null) {
                            mousePosBottomRight = constraintMousePosition(e);
                            selectedBBox.xmax = unScaleWithZoomFactorX(mousePosBottomRight.x) - fromLeft;
                            selectedBBox.ymax = unScaleWithZoomFactorY(mousePosBottomRight.y) - fromTop;
                            isBBoxResizeBottomRight = false;
                            repaint();
                            return;
                        }
                        if (!isBBoxDragged) {
                            selectedBBox = isMouseClickedOnBoundingBox();
                            repaint();
                        }
                    }
                    mousePosBottomRight = constraintMousePosition(e);
                    if (activateBoundingBox && !FactoryUtils.isMousePosEqual(mousePosTopLeft, mousePosBottomRight)) {
                        if (isBBoxDragged) {
                            updateSelectedBBoxPosition();
                            isBBoxDragged = false;
                            repaint();
                            return;
                        }
                        int w = unScaleWithZoomFactor(Math.abs(mousePosTopLeft.x - mousePosBottomRight.x));
                        int h = unScaleWithZoomFactor(Math.abs(mousePosTopLeft.y - mousePosBottomRight.y));
                        if (w < 5 || h < 5) {
                            return;
                        }
                        if (lastSelectedClassName == null || lastSelectedClassName.isEmpty()) {
                            String ret = setBoundingBoxProperties("");
                            mapBBoxColor = buildHashMap(currentFolderName + "/class_labels.txt");
                            lastSelectedClassName = ret.split(":")[0];
                            lastSelectedBoundingBoxColor = buildColor(ret.split(":")[1]);
                        }

                        if (lastSelectedClassName == null || lastSelectedClassName.isEmpty()) {
                            isBBoxCancelled = true;
                            selectedBBox = null;
                            repaint();
                            return;
                        } else {
                            isBBoxCancelled = false;
                        }
                        //System.out.println("fromLeft:"+fromLeft+" fromTop:"+fromTop);
                        Rectangle r = new Rectangle(unScaleWithZoomFactor(mousePosTopLeft.x - fromLeft), unScaleWithZoomFactor(mousePosTopLeft.y - fromTop), w, h);
                        PascalVocBoundingBox bbox = new PascalVocBoundingBox(lastSelectedClassName, r, 0, 0, lastSelectedBoundingBoxColor);
                        selectedBBox = bbox;
                        listPascalVocObject.add(new PascalVocObject(selectedBBox.name, "Unspecified", 0, 0, 0, selectedBBox, null));
                        repaint();
                        selectedBBox = null;
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
                    //lblShow = false;
                }
                checkForTriggerEvent(e);
            }

            private void checkForTriggerEvent(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            private PascalVocBoundingBox isMouseClickedOnBoundingBox() {
                PascalVocBoundingBox ret = null;
                Point relativeMousePos = new Point(mousePos.x - fromLeft, mousePos.y - fromTop);
                //System.out.println("scaledMousePos = " + scaledMousePos);
                if (listPascalVocObject.size() == 0) {
                    return null;
                } else {
                    for (PascalVocObject obj : listPascalVocObject) {
                        PascalVocBoundingBox bbox = obj.bndbox;
                        if (FactoryUtils.isPointInROI(relativeMousePos, scaleWithZoomFactor(bbox.getRectangle(5)))) {
                            ret = bbox;
                            selectedPascalVocObject = obj;
                            return ret;
                        }
                    }
                    return ret;
                }
            }

        });

        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseMoved(java.awt.event.MouseEvent e) {
                isMouseDraggedForBoundingBoxMovement = false;
                mousePos = e.getPoint();
                if (activateBoundingBox && selectedBBox != null) {
                    Point p = new Point(e.getPoint().x - fromLeft, e.getPoint().y - fromTop);
                    int t = 10;
                    if (p.x > scaleWithZoomFactor(selectedBBox.xmin) - t && p.x < scaleWithZoomFactor(selectedBBox.xmin) + t && p.y > scaleWithZoomFactor(selectedBBox.ymin) - t && p.y < scaleWithZoomFactor(selectedBBox.ymin) + t) {
                        setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
                    } else if (p.x > scaleWithZoomFactor(selectedBBox.xmax) - t && p.x < scaleWithZoomFactor(selectedBBox.xmax) + t && p.y > scaleWithZoomFactor(selectedBBox.ymin) - t && p.y < scaleWithZoomFactor(selectedBBox.ymin) + t) {
                        setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
                    } else if (p.x > scaleWithZoomFactor(selectedBBox.xmax) - t && p.x < scaleWithZoomFactor(selectedBBox.xmax) + t && p.y > scaleWithZoomFactor(selectedBBox.ymax) - t && p.y < scaleWithZoomFactor(selectedBBox.ymax) + t) {
                        setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                    } else if (p.x > scaleWithZoomFactor(selectedBBox.xmin) - t && p.x < scaleWithZoomFactor(selectedBBox.xmin) + t && p.y > scaleWithZoomFactor(selectedBBox.ymax) - t && p.y < scaleWithZoomFactor(selectedBBox.ymax) + t) {
                        setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
                    } else if (p.x > scaleWithZoomFactor(selectedBBox.xmin) && p.x < scaleWithZoomFactor(selectedBBox.xmax) && p.y > scaleWithZoomFactor(selectedBBox.ymin) && p.y < scaleWithZoomFactor(selectedBBox.ymax)) {
                        setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                }
                repaint();
            }

            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (activateBoundingBox) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        isMouseDraggedForBoundingBoxMovement = true;
                        mousePos = constraintMousePosition(e);
                    }
                }
                if (SwingUtilities.isMiddleMouseButton(e)) {
                    if (currBufferedImage.getWidth() > getWidth() || currBufferedImage.getHeight() > getHeight()) {
                        isMouseDraggedForImageMovement = true;
                        currentMousePositionForImageMovement = FactoryUtils.clone(e.getPoint());
                        //System.out.println("image b. hareket ediyor");
                    }
                }
                repaint();
            }
        }
        );
    }

    private AffineTransform afft = new AffineTransform();
    private AffineTransform temp_afft = new AffineTransform();
    private AffineTransform inverseScale = new AffineTransform();
    float zoom_factor = 1;
    float original_zoom_factor = 1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        zoom_factor -= e.getWheelRotation() / 32.0f;
        float w = (1.0f * originalBufferedImage.getWidth() * zoom_factor);
        float h = (1.0f * originalBufferedImage.getHeight() * zoom_factor);
        if (original_zoom_factor < 1) {
            originalBufferedImage = ImageProcess.clone(rawImage);
            w = (1.0f * rawImage.getWidth() * zoom_factor);
            h = (1.0f * rawImage.getHeight() * zoom_factor);
        }
        //System.out.println(e.getWheelRotation()+" zoom factor:"+zoom_factor);
        frm.setZoomFactor(zoom_factor);

        currBufferedImage = ImageProcess.resizeAspectRatio(originalBufferedImage, (int) w, (int) h);
        setZoomImage(currBufferedImage, imagePath, caption);
//        frm.setFrameSize(currBufferedImage);
//        
        e.consume();
    }

    private void setCursorWith(Cursor cursor) {
        this.setCursor(cursor);
    }

    private void setDefaultCursor() {
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private Point constraintMousePosition(MouseEvent e) {
        int frLeft = fromLeft;
        int frTop = fromTop;
        int imgW = currBufferedImage.getWidth();
        int imgH = currBufferedImage.getHeight();
        Point pp = e.getPoint();
        if (pp.x < frLeft) {
            pp.x = frLeft;
        }
        if (pp.x > frLeft + imgW) {
            pp.x = frLeft + imgW - 1;
        }
        if (pp.y < frTop) {
            pp.y = frTop;
        }
        if (pp.y > frTop + imgH) {
            pp.y = frTop + imgH - 1;
        }
        return pp;
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

    private void drawNewBoundingBoxOnScreen(Graphics2D gr, int stroke, Point topLeft, Point currentMousePos, Color col) {
        if (FactoryUtils.isMousePosEqual(topLeft, currentMousePos)) {
            return;
        }
        gr.setColor(col);
        int sp1_x = topLeft.x;
        int sp1_y = topLeft.y;
        int sp2_x = currentMousePos.x;
        int sp2_y = currentMousePos.y;
        int w = 0;
        int h = 0;
        w = (Math.abs(sp2_x - sp1_x));
        h = (Math.abs(sp2_y - sp1_y));
        gr.setStroke(new BasicStroke(stroke));
        gr.setColor(col);
        gr.drawRect(sp1_x, sp1_y, w, h);
    }

    private void resizeSelectedBoundingBoxOnScreen(Graphics2D gr, PascalVocBoundingBox bbox, int stroke, Point p1, Point p2, Color col) {
        if (FactoryUtils.isMousePosEqual(p1, p2)) {
            return;
        }

        if (bbox != null) {
            gr.setColor(col);
            int sp1_x = (p1.x);
            int sp1_y = (p1.y);
            int sp2_x = (p2.x);
            int sp2_y = (p2.y);

            int width = gr.getFontMetrics().stringWidth(bbox.name) + 8;
            gr.fillRect(sp1_x - 1, sp1_y - 22, width, 22);

            gr.setStroke(new BasicStroke(stroke));
            gr.setColor(Color.BLACK);
            gr.setFont(new Font("Dialog", Font.BOLD, 12));
            gr.drawString(bbox.name, sp1_x + 2, sp1_y - 5);

            int w = (sp2_x - sp1_x);
            int h = (sp2_y - sp1_y);
            gr.setStroke(new BasicStroke(stroke));
            gr.setColor(col);
            gr.drawRect(sp1_x, sp1_y, w, h);

            if (bbox.equals(selectedBBox)) {
                //draw corners rectangles
                int wx = 12;
                int ww = 6;
                gr.setColor(Color.white);
                gr.fillRect(sp1_x - ww, sp1_y - ww, wx, wx);
                gr.fillRect(sp1_x - ww + w, sp1_y - ww, wx, wx);
                gr.fillRect(sp1_x - ww + w, sp1_y - ww + h, wx, wx);
                gr.fillRect(sp1_x - ww, sp1_y - ww + h, wx, wx);
                gr.setStroke(new BasicStroke(1));
                gr.setColor(Color.black);
                gr.drawRect(sp1_x - ww, sp1_y - ww, wx, wx);
                gr.drawRect(sp1_x - ww + w, sp1_y - ww, wx, wx);
                gr.drawRect(sp1_x - ww + w, sp1_y - ww + h, wx, wx);
                gr.drawRect(sp1_x - ww, sp1_y - ww + h, wx, wx);
            }
        }
    }

    private void draggedSelectedBoundingBoxOnScreen(Graphics2D gr, PascalVocBoundingBox bbox, int stroke, Point p1, Point p2, Color col) {
        if (FactoryUtils.isMousePosEqual(p1, p2)) {
            return;
        }

        if (bbox != null) {
            gr.setColor(col);
            int sp1_x = (p1.x);
            int sp1_y = (p1.y);
//            int sp2_x = (p2.x);
//            int sp2_y = (p2.y);

            int width = gr.getFontMetrics().stringWidth(bbox.name) + 8;
            gr.fillRect(sp1_x - 1, sp1_y - 22, width, 22);

            gr.setStroke(new BasicStroke(stroke));
            gr.setColor(Color.BLACK);
            gr.setFont(new Font("Dialog", Font.BOLD, 12));
            gr.drawString(bbox.name, sp1_x + 2, sp1_y - 5);
            int w = scaleWithZoomFactor(bbox.xmax - bbox.xmin);
            int h = scaleWithZoomFactor(bbox.ymax - bbox.ymin);
//            int w = (sp2_x - sp1_x);
//            int h = (sp2_y - sp1_y);
            gr.setStroke(new BasicStroke(stroke));
            gr.setColor(col);
            gr.drawRect(sp1_x, sp1_y, w, h);

            if (bbox.equals(selectedBBox)) {
                //draw corners rectangles
                int wx = 12;
                int ww = 6;
                gr.setColor(Color.white);
                gr.fillRect(sp1_x - ww, sp1_y - ww, wx, wx);
                gr.fillRect(sp1_x - ww + w, sp1_y - ww, wx, wx);
                gr.fillRect(sp1_x - ww + w, sp1_y - ww + h, wx, wx);
                gr.fillRect(sp1_x - ww, sp1_y - ww + h, wx, wx);
                gr.setStroke(new BasicStroke(1));
                gr.setColor(Color.black);
                gr.drawRect(sp1_x - ww, sp1_y - ww, wx, wx);
                gr.drawRect(sp1_x - ww + w, sp1_y - ww, wx, wx);
                gr.drawRect(sp1_x - ww + w, sp1_y - ww + h, wx, wx);
                gr.drawRect(sp1_x - ww, sp1_y - ww + h, wx, wx);
            }
        }
    }

    private void drawBoundingBoxOnImage(Graphics2D gr, PascalVocBoundingBox bbox, int stroke, Point p1, Point p2, Color col) {
        if (FactoryUtils.isMousePosEqual(p1, p2)) {
            return;
        }

        if (bbox != null) {
            gr.setColor(col);
            int sp1_x = scaleWithZoomFactorX(p1.x);
            int sp1_y = scaleWithZoomFactorY(p1.y);
            int sp2_x = scaleWithZoomFactorX(p2.x);
            int sp2_y = scaleWithZoomFactorY(p2.y);

            int width = gr.getFontMetrics().stringWidth(bbox.name) + 8;
            gr.fillRect(sp1_x - 1, sp1_y - 22, width, 22);

            gr.setStroke(new BasicStroke(stroke));
            gr.setColor(Color.BLACK);
            gr.setFont(new Font("Dialog", Font.BOLD, 12));
            gr.drawString(bbox.name, sp1_x + 2, sp1_y - 5);
            int w = 0;
            int h = 0;
            w = scaleWithZoomFactor(Math.abs(p2.x - p1.x));
            h = scaleWithZoomFactor(Math.abs(p2.y - p1.y));
            gr.setStroke(new BasicStroke(stroke));
            gr.setColor(col);
            gr.drawRect(sp1_x, sp1_y, w, h);

            if (bbox.equals(selectedBBox)) {
                //draw corners rectangles
                int wx = 12;
                int ww = 6;
                gr.setColor(Color.white);
                gr.fillRect(sp1_x - ww, sp1_y - ww, wx, wx);
                gr.fillRect(sp1_x - ww + w, sp1_y - ww, wx, wx);
                gr.fillRect(sp1_x - ww + w, sp1_y - ww + h, wx, wx);
                gr.fillRect(sp1_x - ww, sp1_y - ww + h, wx, wx);
                gr.setStroke(new BasicStroke(1));
                gr.setColor(Color.black);
                gr.drawRect(sp1_x - ww, sp1_y - ww, wx, wx);
                gr.drawRect(sp1_x - ww + w, sp1_y - ww, wx, wx);
                gr.drawRect(sp1_x - ww + w, sp1_y - ww + h, wx, wx);
                gr.drawRect(sp1_x - ww, sp1_y - ww + h, wx, wx);
            }
        }
    }

    private boolean isMouseInCanvas() {
        return (mousePos.x > fromLeft && mousePos.x < fromLeft + currBufferedImage.getWidth() && mousePos.y > fromTop && mousePos.y < fromTop + currBufferedImage.getHeight());
    }

    private void paintBoundingBoxes(Graphics2D gr) {
        if (isBBoxResizeTopLeft) {
            this.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
            mousePosTopLeft = FactoryUtils.clone(mousePos);
            Point p = new Point(scaleWithZoomFactorX(selectedBBox.xmax + fromLeft), scaleWithZoomFactorY(selectedBBox.ymax + fromTop));
            resizeSelectedBoundingBoxOnScreen(gr, selectedBBox, defaultStrokeWidth, mousePosTopLeft, p, mapBBoxColor.get(selectedBBox.name));
        } else if (isBBoxResizeTopRight) {
            this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
            mousePosTopRight = FactoryUtils.clone(mousePos);
            Point p1 = new Point(scaleWithZoomFactorX(selectedBBox.xmin + fromLeft), mousePosTopRight.y);
            Point p2 = new Point(mousePosTopRight.x, scaleWithZoomFactorY(selectedBBox.ymax + fromTop));
            resizeSelectedBoundingBoxOnScreen(gr, selectedBBox, defaultStrokeWidth, p1, p2, mapBBoxColor.get(selectedBBox.name));
        } else if (isBBoxResizeBottomLeft) {
            this.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
            mousePosBottomLeft = FactoryUtils.clone(mousePos);
            Point p1 = new Point(mousePosBottomLeft.x, scaleWithZoomFactorY(selectedBBox.ymin + fromTop));
            Point p2 = new Point(scaleWithZoomFactorX(selectedBBox.xmax + fromLeft), mousePosBottomLeft.y);
            resizeSelectedBoundingBoxOnScreen(gr, selectedBBox, defaultStrokeWidth, p1, p2, mapBBoxColor.get(selectedBBox.name));
        } else if (isBBoxResizeBottomRight) {
            this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
            mousePosBottomRight = FactoryUtils.clone(mousePos);
            Point p1 = new Point(scaleWithZoomFactorX(selectedBBox.xmin + fromLeft), scaleWithZoomFactorY(selectedBBox.ymin + fromTop));
            resizeSelectedBoundingBoxOnScreen(gr, selectedBBox, defaultStrokeWidth, p1, mousePosBottomRight, mapBBoxColor.get(selectedBBox.name));
        } else if (selectedBBox != null && isBBoxDragged && isMouseDraggedForBoundingBoxMovement) {
            //eğer bbox tutulup hareket ettiriliyorsa
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            lastPositionOfDraggedBBox = calculateDraggingBBoxPosition();
            draggedSelectedBoundingBoxOnScreen(gr, selectedBBox, defaultStrokeWidth, lastPositionOfDraggedBBox[0], lastPositionOfDraggedBBox[1], Color.orange);
        } else if (selectedBBox == null && !isBBoxCancelled && isMouseDraggedForBoundingBoxMovement) {
            //şayet herhangi bir bbox seçilmeden ekranda yeni bir bbox çiziliyorsa
            drawNewBoundingBoxOnScreen(gr, defaultStrokeWidth, mousePosTopLeft, mousePos, defaultBoundingBoxColor);
        }

        //draw all bboxes including selectedbbox
        for (PascalVocObject obj : listPascalVocObject) {
            PascalVocBoundingBox bbox = obj.bndbox;
            Point p1 = new Point(bbox.xmin + fromLeft, bbox.ymin + fromTop);
            Point p2 = new Point(bbox.xmax + fromLeft, bbox.ymax + fromTop);
            drawBoundingBoxOnImage(gr, bbox, defaultStrokeWidth, p1, p2, mapBBoxColor.get(obj.name));
        }
    }

    private void paintROI(Graphics2D gr) {
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
    }

    private void paintMouseDashedLines(Graphics2D gr, int wPanel, int hPanel, Color color) {
        Stroke dashed = new BasicStroke(3,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                0,
                new float[]{9},
                0);
        gr.setStroke(dashed);
        gr.setColor(color);
        gr.drawLine(0, mousePos.y, wPanel - 1, mousePos.y);
        gr.drawLine(mousePos.x, 0, mousePos.x, hPanel - 1);
    }

    private void paintFrameRectangle(Graphics2D gr, int wPanel, int hPanel) {
        gr.setStroke(new BasicStroke(1));
        gr.setColor(Color.red);
        gr.drawRect(0, 0, wPanel - 1, hPanel - 1);
        gr.drawRect(1, 1, wPanel - 3, hPanel - 3);
    }

    private void paintStatistics(Graphics2D gr) {
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

    private void showHistogram() {
        imgData = ImageProcess.imageToPixelsFloat(currBufferedImage);
        histFrame.setHistogramData(CMatrix.getInstance(imgData));
        histFrame.setVisible(true);
    }

    private void paintPixelInfo(Graphics2D gr, int wImg, int hImg) {
        if (lblShow && mousePos.x > fromLeft && mousePos.y > fromTop && mousePos.x < fromLeft + wImg && mousePos.y < fromTop + hImg) {
            p.x = mousePos.x - fromLeft;
            p.y = mousePos.y - fromTop;
            if (currBufferedImage.getType() == BufferedImage.TYPE_BYTE_GRAY) {
                lbl.setText(getImageSize() + " Pos=(" + p.y + ":" + p.x + ") Value=" + imgData[p.y][p.x] + " Img Type=TYPE_BYTE_GRAY");
            } else if (currBufferedImage.getType() == BufferedImage.TYPE_INT_RGB) {
                String s = "" + new Color((int) imgData[p.y][p.x], true);
                s = s.replace("java.awt.Color", "");
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
        }
    }

    private void paintDrawableROI(Graphics2D gr) {
        if (drawableRoiList.size() > 0) {
            CPoint prev = drawableRoiList.get(0);
            for (CPoint p : drawableRoiList) {
                gr.setColor(Color.red);
                int wx = 5;
                gr.fillRect(p.column + fromLeft - 2, p.row + fromTop - 2, wx, wx);
                gr.setColor(new Color(255, 255, 0));
                gr.drawLine(prev.column + fromLeft, prev.row + fromTop, p.column + fromLeft, p.row + fromTop);
                prev = p;
            }
        }
    }

    private void paintShowDrawableROI(Graphics2D gr) {
        CPoint prev = drawableRoiList.get(0);
        for (CPoint p : drawableRoiList) {
            gr.setColor(Color.red);
            int wx = 5;
            gr.fillRect(p.column + fromLeft - 2, p.row + fromTop - 2, wx, wx);
            gr.setColor(new Color(255, 255, 0));
            gr.drawLine(prev.column + fromLeft, prev.row + fromTop, p.column + fromLeft, p.row + fromTop);
            prev = p;
        }
        CPoint p = drawableMousePos;
        gr.drawLine(prev.column + fromLeft, prev.row + fromTop, p.column + fromLeft, p.row + fromTop);
    }

    private void paintIfImageAutoSized(Graphics2D gr) {
        if (activateAutoSize) {
            currBufferedImage = ImageProcess.resize(originalBufferedImage, this.getWidth() - 2 * panWidth, this.getHeight() - 2 * panWidth);
            imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
            lbl.setText(getImageSize() + "X:Y");
        } else if (activateAutoSizeAspect) {
            if (this.getWidth() - 2 * panWidth > 5 && this.getHeight() - 2 * panWidth > 5) {
                currBufferedImage = ImageProcess.resizeAspectRatio(originalBufferedImageTemp, this.getWidth() - 2 * panWidth, this.getHeight() - 2 * panWidth);
                imgData = ImageProcess.bufferedImageToArray2D(currBufferedImage);
                lbl.setText(getImageSize() + "X:Y");

            }
        }
    }

    private Map<String, Color> buildHashMap(String path) {
        String[] s = FactoryUtils.readFile(path).split("\n");
        Map<String, Color> ret = new HashMap();
        for (String str : s) {
            String[] msg = str.split(":");
            ret.put(msg[0], buildColor(msg[1]));
        }
        return ret;
    }

    private BufferedImage adjustImageToPanel(BufferedImage bf, boolean isClearBbox) {
        if (bf.getHeight() > 950) {
            float zoom_factor = 950.0f / bf.getHeight();
            int w = (int) (bf.getWidth() * zoom_factor);
            int h = (int) (bf.getHeight() * zoom_factor);
            frm.setZoomFactor(FactoryUtils.formatFloat(zoom_factor, 4));
            original_zoom_factor = FactoryUtils.formatFloat(zoom_factor, 4);
            zoom_factor = original_zoom_factor;
            bf = ImageProcess.resizeAspectRatio(bf, w, h);
        } else {
            zoom_factor = original_zoom_factor = 1.0f;
            frm.setZoomFactor(FactoryUtils.formatFloat(zoom_factor, 4));
        }
        frm.setTitle(imageFiles[imageIndex].getPath() + "      [ " + imageIndex + " / " + imageFiles.length + " ]");
        fileName = imageFiles[imageIndex].getName();
        imagePath = imageFiles[imageIndex].getAbsolutePath();

        if (!isSeqenceVideoFrame) {
            if (isClearBbox) {
                listPascalVocObject.clear();
                selectedBBox = null;
            }
        }

        //setDefaultValues();
        setImage(bf, imagePath, caption, isClearBbox);
        frm.setFrameSize(bf);
        frm.img = bf;
        frm.imagePath = imagePath;
        return bf;
    }

    private int scaleWithZoomFactor(int val) {
        return (int) (val * zoom_factor);
    }

    private int unScaleWithZoomFactor(int val) {
        return (int) (val / zoom_factor);
    }

    private Point shiftPointTo(Point p, int valX, int valY) {
        return new Point(p.x + valX, p.y + valY);
    }

    private Point scaleWithZoomFactor(Point val) {
        return new Point(scaleWithZoomFactorX(val.x), scaleWithZoomFactorY(val.y));
    }
//
//    private Point unScaleWithZoomFactor(Point val) {
//        return new Point(unScaleWithZoomFactorX(val.x), unScaleWithZoomFactorY(val.y));
//    }

    private Rectangle scaleWithZoomFactor(Rectangle p) {
        return new Rectangle(scaleWithZoomFactor(p.x), scaleWithZoomFactor(p.y), scaleWithZoomFactor(p.width), scaleWithZoomFactor(p.height));
    }

    private int scaleWithZoomFactorX(int val) {
        int pureVal = val - fromLeft;
        return Math.round(pureVal * zoom_factor) + fromLeft;
    }

    private int scaleWithZoomFactorY(int val) {
        int pureVal = val - fromTop;
        return Math.round(pureVal * zoom_factor) + fromTop;
    }

    private int unScaleWithZoomFactorX(int val) {
        int pureVal = val - fromLeft;
        return Math.round(pureVal / zoom_factor) + fromLeft;
    }

    private int unScaleWithZoomFactorY(int val) {
        int pureVal = val - fromTop;
        return Math.round(pureVal / zoom_factor) + fromTop;
    }

    private void updateSelectedBBoxPosition() {
        if (selectedBBox != null && lastPositionOfDraggedBBox != null) {
            int w = selectedBBox.xmax - selectedBBox.xmin;
            int h = selectedBBox.ymax - selectedBBox.ymin;
            selectedBBox.xmin = unScaleWithZoomFactorX(lastPositionOfDraggedBBox[0].x) - fromLeft;
            selectedBBox.ymin = unScaleWithZoomFactorY(lastPositionOfDraggedBBox[0].y) - fromTop;
            selectedBBox.xmax = selectedBBox.xmin + w;
            selectedBBox.ymax = selectedBBox.ymin + h;
        }
    }

    private Point[] calculateDraggingBBoxPosition() {
        Point[] ret = new Point[2];
        int p1x = mousePos.x - relativeDragPosFromTop.x;
        p1x = (p1x > fromLeft) ? p1x : fromLeft;
        int p1y = mousePos.y - relativeDragPosFromTop.y;
        p1y = (p1y > fromTop) ? p1y : fromTop;
        int p2x = p1x + (selectedBBox.xmax - selectedBBox.xmin);
        if (p2x > fromLeft + currBufferedImage.getWidth()) {
            p2x = fromLeft + currBufferedImage.getWidth();
            p1x = p2x - scaleWithZoomFactor(selectedBBox.getWidth());
        }
        int p2y = p1y + (selectedBBox.ymax - selectedBBox.ymin);
        if (p2y > fromTop + currBufferedImage.getHeight()) {
            p2y = fromTop + currBufferedImage.getHeight();
            p1y = p2y - scaleWithZoomFactor(selectedBBox.getHeight());
        }
        Point p1 = new Point(p1x, p1y);
        Point p2 = new Point(p2x, p2y);
        ret[0] = p1;
        ret[1] = p2;
        return ret;
    }

    private Point[] calculateDraggingBBoxPositionV3() {
        Point[] ret = new Point[2];
        int p1x = selectedBBox.xmin + fromLeft + (mousePos.x - referenceDragPos.x);
        p1x = (p1x > fromLeft) ? p1x : fromLeft;
        int p1y = selectedBBox.ymin + fromTop + (mousePos.y - referenceDragPos.y);
        p1y = (p1y > fromTop) ? p1y : fromTop;
        int p2x = p1x + (selectedBBox.xmax - selectedBBox.xmin);
        if (p2x > fromLeft + currBufferedImage.getWidth()) {
            p2x = fromLeft + currBufferedImage.getWidth();
            p1x = p2x - selectedBBox.getWidth();
        }
        int p2y = p1y + (selectedBBox.ymax - selectedBBox.ymin);
        if (p2y > fromTop + currBufferedImage.getHeight()) {
            p2y = fromTop + currBufferedImage.getHeight();
            p1y = p2y - selectedBBox.getHeight();
        }
        Point p1 = new Point(p1x, p1y);
        Point p2 = new Point(p2x, p2y);
        ret[0] = p1;
        ret[1] = p2;
        //System.out.println("fromLeft:"+fromLeft+" width:"+currBufferedImage.getWidth()+" tot:"+(fromLeft+currBufferedImage.getWidth())+" p1:"+p1+" p2:"+p2);
        return ret;

    }

    private void updateImagePosition() {
        if (currBufferedImage != null) {
            int wPanel = this.getWidth();
            int hPanel = this.getHeight();

            int wImg = currBufferedImage.getWidth();
            int hImg = currBufferedImage.getHeight();

            fromLeft = (wPanel - wImg) / 2;
            fromTop = (hPanel - hImg) / 2;

        }
    }

    private void updatePanelOffsetValuesWhileImageMoving() {
        if (isMouseDraggedForImageMovement) {
            fromLeft += (currentMousePositionForImageMovement.x - referenceMousePositionForImageMovement.x);
            fromTop += (currentMousePositionForImageMovement.y - referenceMousePositionForImageMovement.y);
            referenceMousePositionForImageMovement = FactoryUtils.clone(currentMousePositionForImageMovement);
        }
    }

    public void setDashedLineColor() {
        colorDashedLine = JColorChooser.showDialog(null, "Choose Color for BoundingBox", colorDashedLine);
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
                        imagePath = fl.getAbsolutePath();
                        setImagePath(imagePath);
                        caption = imagePath;
                        setImage(bf, imagePath, caption, true);
                        frame.img = originalBufferedImage;

//                        currBufferedImage = (originalBufferedImage);
//                        imagePath = fl.getAbsolutePath();
//                        imageFolder = FactoryUtils.getFolderPath(imagePath);
//                        fileName = fl.getName();
////                    fileList = FactoryUtils.getFileListInFolder(imageFolder);
////                    currentImageIndex = getCurrentImageIndex();
//                        imgData = ImageProcess.imageToPixelsFloat(currBufferedImage);
//                        if (activateStatistics) {
//                            stat = TStatistics.getStatistics(currBufferedImage);
//                        }
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
                } else if (obj.getText().equals("Binarize")) {
                    activateBinarize = true;
                    currBufferedImage = ImageProcess.binarizeColorImage(currBufferedImage);
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
        if (imageIndex < imageFiles.length - 1) {
            imageIndex++;
        }
        if (!isSeqenceVideoFrame) {
            listPascalVocObject.clear();
            selectedBBox = null;
        }
        BufferedImage bf = ImageProcess.readImageFromFile(imageFiles[imageIndex]);
        setImage(bf, imagePath, caption, true);
        frm.setTitle(imageFiles[imageIndex].getPath());
        frm.setFrameSize(bf);
        fileName = imageFiles[imageIndex].getName();
        imagePath = imageFiles[imageIndex].getAbsolutePath();
    }

    private void loadPrevImage() {
//        imageIndex--;
//        if (imageIndex < 0) {
//            imageIndex++;
//            return;
//        }
        if (imageIndex > 0) {
            imageIndex--;
        }
        if (!isSeqenceVideoFrame) {
            listPascalVocObject.clear();
            selectedBBox = null;
        }
        BufferedImage bf = ImageProcess.readImageFromFile(imageFiles[imageIndex]);
        setImage(bf, imagePath, caption, true);
        frm.setTitle(imageFiles[imageIndex].getPath());
        frm.setFrameSize(bf);
        fileName = imageFiles[imageIndex].getName();
        imagePath = imageFiles[imageIndex].getAbsolutePath();
    }

    private void savePascalVocXML() {
        List<PascalVocObject> lstObject = new ArrayList();
        for (PascalVocObject obj : listPascalVocObject) {
            PascalVocBoundingBox bbox = obj.bndbox;
            List<PascalVocAttribute> attributeList = obj.attributeList;
            lstObject.add(new PascalVocObject(bbox.name, "", 0, 0, 0, bbox, attributeList));
        }
        if (lstObject.size() > 0) {
            String xml = FactoryUtils.serializePascalVocXML(imageFolder, fileName, imagePath, new PascalVocSource(), lstObject);
        } else {
            File file = new File(imagePath);
            if (FactoryUtils.isFileExist(imageFolder + "/" + FactoryUtils.getFileName(file.getName()) + ".xml")) {
                FactoryUtils.deleteFile(imageFolder + "/" + FactoryUtils.getFileName(file.getName()) + ".xml");
            }
        }
        //loadNextImage();
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
        activateBinarize = false;
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
            if (activateBoundingBox) {
                savePascalVocXML();
                imageIndex++;
                if (!isSeqenceVideoFrame) {
                    listPascalVocObject.clear();
                    selectedBBox = null;
                }
                BufferedImage bf = ImageProcess.readImageFromFile(imageFiles[imageIndex]);
                rawImage = ImageProcess.clone(bf);
                adjustImageToPanel(bf, true);
            }
            return;
        } else if (key == KeyEvent.VK_DELETE) {
            if (selectedBBox != null) {
                PascalVocObject temp_obj = null;
                for (PascalVocObject obj : listPascalVocObject) {
                    if (obj.bndbox.equals(selectedBBox)) {
                        temp_obj = obj;
                        break;
                    }
                }
                if (temp_obj != null) {
                    listPascalVocObject.remove(temp_obj);
                }
                selectedBBox = null;
            } else {
                listPascalVocObject.clear();
            }
            repaint();
            return;
        }
        BufferedImage bf = ImageProcess.readImageFromFile(imageFiles[imageIndex]);
        rawImage = ImageProcess.clone(bf);
        adjustImageToPanel(bf, true);
        e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
