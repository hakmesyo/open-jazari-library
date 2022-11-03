/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.machine_learning.extraction;

import jazari.gui.FrameImage;
import jazari.image_processing.AdaptiveRegionGrowing;
import jazari.image_processing.EdgeTracking;
import jazari.image_processing.ImageProcess;
import jazari.matrix.CMatrix;
import jazari.matrix.CPoint;
import jazari.factory.FactoryMatrix;
import jazari.types.TMatrixOperator;
import jazari.factory.FactoryUtils;
import jazari.utils.GCodeWorkList;
import jazari.gui.PanelPicture;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL LAB
 */
public class FeatureExtractionPistachio {

   
    public static float getDimensionRatio(float[][] d,boolean isShow) {
        float[] dim = FactoryUtils.getObjectDimensions(d, 30,isShow);
        float m1 = dim[0];
        float m2 = dim[2];
        float dimension1 = dim[1];
        float dimension2 = dim[3];
        float dimRatio = FactoryUtils.formatFloat(dimension1 / dimension2);
        return dimRatio;
    }
    
    public static float getPistachioDiameter(float[][] d,boolean isShow) {
        float[] dim = FactoryUtils.getObjectDimensions(d, 30,isShow);
        return dim[3];
    }

    public static float getEntropy(float[][] d) {
        return FactoryUtils.formatFloat(ImageProcess.getEntropy(d));
    }

    public static float getPixelArea(float[][] d, int thr) {
        float ret = 0;
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (d[i][j] > thr) {
                    ret++;
                }
            }
        }
        return FactoryUtils.formatFloat(ret);
    }

    public static float getPixelArea(float[][] d) {
        float ret = 0;
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (d[i][j] > 0) {
                    ret++;
                }
            }
        }
        return FactoryUtils.formatFloat(ret);
    }

    public static float getCannyEdgeRatio(CMatrix cm, float pixelArea,boolean isShow) {
        float ret = 0;
        if (isShow) {
            cm.imshow();
        }
        int total = cm.findIndex(TMatrixOperator.EQUALS, 255).toDoubleArray1D().length;
        ret = FactoryUtils.formatFloat(total / (float)Math.sqrt(pixelArea));
        return ret;
    }

    public static float getNormalizedCrackAmountStandard(float[][] d, CPoint cp, float pixelArea, float thr) {
        float ret = 0;
        float[][] m = AdaptiveRegionGrowing.performStandardRegionGrowing(d, cp, thr);
        CMatrix.getInstance(m).imshow("standard growing region");
        return ret;
    }

    public static float getNormalizedCrackAmountAdaptive(float[][] d, CPoint cp, float pixelArea, float thr, float var) {
        float ret = 0;
        int t = ImageProcess.getOtsuTresholdValue(d);
        float[][] m = AdaptiveRegionGrowing.performAdaptiveRegionGrowing(d, t, cp, thr, var);
        CMatrix.getInstance(m).imshow("adaptive grawing region");
        return ret;
    }

    public static float getProposedCrackedRegionRatio(int index, float[][] d, CMatrix cm_edge,float pixelArea, float thr, int otsuThr, boolean showFigure) {
        d=ImageProcess.swapColor(d, 0, 11);
        float ret = 0;
        int r = d.length;
        int c = d[0].length;
        //1.stage: detect edges by canny operator
        float[][] dc =cm_edge.toFloatArray2D();// FactoryUtils.toDoubleArray(ImageProcess.edgeDetectionCanny(d));
        //2.stage:overlay canny edges on the orginal image
        d = FactoryUtils.overlayIdenticalMatrix(d, dc);
        CMatrix cm = CMatrix.getInstance(d);
        //3.stage: get ridoff outer edge of the pistachio shell
        CMatrix cm1 = cm.findIndex(TMatrixOperator.EQUALS, 255);
        float[][] dd = FactoryMatrix.clone(d);
        float[] lst = cm1.toFloatArray1D();
        for (int i = 0; i < lst.length; i++) {
            CPoint cp = FactoryUtils.to2D((int) lst[i], r, c);
//            if (cp.row == 84 && cp.column == 20) {
//                int a = 1;
//            }
            float[][] win = FactoryUtils.crop(d, cp, 4);
            float min = FactoryUtils.getMinimum(win);
            if (win.length == d.length || (min > 0 && min < otsuThr / 2)) {
                d[cp.row][cp.column] = 127;
                dd[cp.row][cp.column] = 127;
                for (int j = -4; j <= 4; j++) {
                    if (cp.column + j >= 0 && cp.column + j < d[0].length) {
                        float deg = d[cp.row][cp.column + j];
                        if (deg != 255 && deg != 127 && deg > 0 && deg < thr) {
                            dd[cp.row][cp.column + j] = 0;
                        }
                    }
                }
            }
        }
        d = FactoryMatrix.clone(dd);
        CMatrix cm2 = CMatrix.getInstance(d);//.imshow(true);
        lst = cm2.findIndex(TMatrixOperator.EQUALS, 255).toFloatArray1D();

        //***********************************************
        if (showFigure) {
            FrameImage frm2 = new FrameImage(cm2,"");
            frm2.setVisible(true);
            frm2.setTitle(index + ".Proposed Pistachio");
            float[][] rg = regionGrowingByBreadthFirst(frm2.getPicturePanel(), cm2.toFloatArray2D(), lst, 15, otsuThr * 0.9f);
            float[] crackArray = CMatrix.getInstance(rg).findIndex(TMatrixOperator.EQUALS, 255).toFloatArray1D();
            ret = FactoryUtils.formatFloat(crackArray.length / (pixelArea) * 100);
            frm2.setTitle(index + ".fıstık CrackRatio:" + ret);
        }else{
            float[][] rg = regionGrowingByBreadthFirst(cm2.toFloatArray2D(), lst, 15, otsuThr * 0.9f);
            float[] crackArray = CMatrix.getInstance(rg).findIndex(TMatrixOperator.EQUALS, 255).toFloatArray1D();
            ret = FactoryUtils.formatFloat(crackArray.length / (pixelArea) * 100);
        }
        return ret;
    }

    public static float[][] regionGrowingByBreadthFirst(PanelPicture pan, float[][] d, float[] lst, float t1, float t2) {
        d = regionGrowingByBreadthFirst(d, lst, t1, t2);
        BufferedImage img = ImageProcess.pixelsToImageGray(d);
        pan.setImage(img,"");
        return d;
    }

    public static float[][] regionGrowingByBreadthFirst(float[][] d, float[] lst, float t1, float t2) {
        for (int i = 0; i < lst.length; i++) {
            boolean isFinished = false;
            CPoint cm = FactoryUtils.to2D((int) lst[i], d.length, d[0].length);

            //totalList holds region pixels coordinates
            List<CPoint> totalList = new ArrayList<>();
            totalList.add(cm);
            //activeNodeList holds current alive nodes
            List<CPoint> activeNodeList = new ArrayList<>();
            activeNodeList.add(cm);
            //by default we use 3x3 window with 8 neighbour pixels/nodes
            while (!isFinished) {
                List<CPoint> cList = new ArrayList<>();
                for (CPoint node : activeNodeList) {
                    List<CPoint> nList = getNeigbourNodes(d, node);
                    boolean isBuldu = false;
                    for (CPoint nd : nList) {
                        int r = nd.row;
                        int c = nd.column;
                        if (d[r][c] >= t1 && d[r][c] <= t2) {
                            d[r][c] = 255;
                            cList.add(nd);
                            isBuldu = true;
                        }
                    }
                    if (!isBuldu) {
                        d[node.row][node.column] = 127;
                    }
                }
                if (cList.size() == 0) {
                    isFinished = true;
                }
                activeNodeList.clear();
                for (CPoint cl : cList) {
                    CPoint cpp = new CPoint(cl.row, cl.column);
                    activeNodeList.add(cpp);
                }
                cList.clear();

            }
        }
        return d;
    }

    private static List<CPoint> getNeigbourNodes(float[][] d, CPoint node) {
        List<CPoint> ret = new ArrayList<>();
        int r = node.row;
        int c = node.column;
        if (r - 1 >= 0 && c - 1 >= 0 && d[r - 1][c - 1] != 255) {
            ret.add(new CPoint(r - 1, c - 1));
        }
        if (r - 1 >= 0 && c >= 0 && c < d[0].length && d[r - 1][c] != 255) {
            ret.add(new CPoint(r - 1, c));
        }
        if (r - 1 >= 0 && c + 1 < d[0].length && d[r - 1][c + 1] != 255) {
            ret.add(new CPoint(r - 1, c + 1));
        }
        if (r >= 0 && r < d.length && c - 1 >= 0 && d[r][c - 1] != 255) {
            ret.add(new CPoint(r, c - 1));
        }
        if (r >= 0 && r < d.length && c + 1 < d[0].length && d[r][c + 1] != 255) {
            ret.add(new CPoint(r, c + 1));
        }
        if (r + 1 < d.length && c - 1 >= 0 && d[r + 1][c - 1] != 255) {
            ret.add(new CPoint(r + 1, c - 1));
        }
        if (r + 1 < d.length && c >= 0 && c < d[0].length && d[r + 1][c] != 255) {
            ret.add(new CPoint(r + 1, c));
        }
        if (r + 1 < d.length && c + 1 < d[0].length && d[r + 1][c + 1] != 255) {
            ret.add(new CPoint(r + 1, c + 1));
        }
        return ret;
    }

    private static float[][] regionGrowing(float[][] d, CMatrix cm, float threshold, int size) {
        int r = d.length;
        int c = d[0].length;
        CMatrix cm3 = cm.findIndex(TMatrixOperator.EQUALS, 255);
        float[] lst = cm3.toFloatArray1D();
        if (lst.length == 1) {
            return d;
        }
        for (int i = 0; i < lst.length; i++) {
            CPoint cp = FactoryUtils.to2D((int) lst[i], r, c);
            if (cp.row == 87 && cp.column == 19) {
                int a = 1;
            }
            float[][] win = FactoryUtils.crop(d, cp, size);
            for (int j = 0; j < win.length; j++) {
                for (int k = 0; k < win[0].length; k++) {
                    float w = win[j][k];
                    if (w < threshold) {
                        win[j][k] = 255;
                    }
                }
            }
            d = FactoryUtils.overlayMatrix(d, win, cp);
        }
        return d;
    }

    private static float getMin(float[][] d, float[] lst, int r, int c) {
        float ret = 255;
        for (int i = 0; i < lst.length; i++) {
            CPoint cp = FactoryUtils.to2D((int) lst[i], r, c);
            if (cp.row == 87 && cp.column == 19) {
                int a = 1;
            }
            float[][] win = FactoryUtils.crop(d, cp, 4);
            float min = FactoryUtils.getMinimum(win);
            if (ret > min) {
                ret = min;
            }
        }
        return ret;
    }

}

//4.stage:  dropping inner edges that are not belong to cracked region
//        float threshold = 45;
//        int size=3;
//        d=regionGrowing(d,cm2,threshold,size);
//        CMatrix cm3 = CMatrix.getInstance(d).imshow(true);
//        d=regionGrowing(d,cm3,threshold);
//        CMatrix cm4 = CMatrix.getInstance(d).imshow(true);
//        //4.stage:  dropping inner edges that are not belong to cracked region
//        CMatrix cm3 = cm2.findIndex(TMatrixOperator.EQUALS, 255);
//        lst = cm3.get1DArrayDouble();
//        float tMin=getMin(d,lst,r,c);
//        System.out.println("tMin:"+tMin);
//        for (int i = 0; i < lst.length; i++) {
//            CPoint cp = FactoryUtils.to2D((int) lst[i], r, c);
//            if (cp.row==87 && cp.column==19) {
//                int a=1;
//            }
//            float[][] win = FactoryUtils.crop(d, cp, 2);
//            float min = FactoryUtils.getMinimum(win);
//            if (win.length == d.length || (min > 2.5*tMin)) {
//                d[cp.row][cp.column] = 127;
//            }
//        }
//        CMatrix cm4 = CMatrix.getInstance(d).imshow(true); 
//        
//5.stage: perform edgetracking algorithm first to drop small edges, second to close the contour of cracked region
//        FrameImage frm = new FrameImage(cm2);
//        frm.setVisible(true);
//        GCodeWorkList wl = EdgeTracking.processImageForGCode(cm2.getImage(), frm.getPicturePanel(), 3, 15, 30);
