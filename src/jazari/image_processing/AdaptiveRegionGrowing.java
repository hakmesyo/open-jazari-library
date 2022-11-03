/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.image_processing;

import jazari.matrix.CMatrix;
import jazari.matrix.CPoint;
import jazari.factory.FactoryMatrix;
import jazari.factory.FactoryUtils;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BAP1
 */
public class AdaptiveRegionGrowing {

    /**
     * perform traditional mean adaptation based region growing algorithm at the seed point centering
     *
     * @param d :image
     * @param cp :seed point
     * @param thr:threshold default is 20
     * @return pixel coordinates of the region as a list of CPoint
     */
    public static float[][] performStandardRegionGrowing(float[][] d, CPoint cp, float thr) {
        float[][] m = FactoryMatrix.clone(d);
        int r = 0;
        boolean isFinished = false;
        float mean = d[cp.row][cp.column];
        float meanFirst=mean;
        m[cp.row][cp.column] = 255;
        while (!isFinished) {
            r++;
            CPoint[] p = getWindowPixels(m, cp.row, cp.column, r,(int)thr);
            if (p.length == 0) {
                isFinished = true;
            }
            int toplam = 0;
            int k = 0;
            for (int i = 0; i < p.length; i++) {
                int pr = p[i].row;
                int pc = p[i].column;
                if (Math.abs(mean - d[pr][pc]) < thr) {
                    m[pr][pc] = 255;
                    toplam += d[pr][pc];   //standart mean adaptation
                    k++;
                }
            }
            float mean2 = (k > 0) ? toplam / k : 0;
            mean = (mean2 > 0) ? (mean + mean2) / 2 : mean;
        }
        return m;
    }
    
    /**
     * perform gaussian mean adaptation based region growing algorithm
     * note that neigbour pixels are only admitted if they satisfied threshold constraint and
     * contributions are calculated based on the gaussian membership function in the -threshold and +threshold range as well.
     *
     * @param d :image
     * @param otsuThr:otsu threshold
     * @param cp :seed point
     * @param thr:threshold default is 20
     * @return pixel coordinates of the region as a list of CPoint
     */
    public static float[][] performAdaptiveRegionGrowing(float[][] d,int otsuThr, CPoint cp, float thr,float variance) {
        float[][] m = FactoryMatrix.clone(d);
        int r = 0; //window size
        boolean isFinished = false;
//        float mean = d[cp.row][cp.column];
//        float meanFirst=mean;
        cp.row=d.length/2;
        cp.column=d[0].length/2;
        float mean = otsuThr/2;
        float meanFirst=otsuThr/2;
        
        m[cp.row][cp.column] = 255;
        float[] contributionVector=CMatrix.getInstance().range(-thr,thr).gaussmf(variance,0).toFloatArray1D();
        while (!isFinished) {
            r++;
            CPoint[] p = getWindowPixels(m, cp.row, cp.column, r,(int)thr);
            if (p.length == 0) {
                isFinished = true;
            }
            int toplam = 0;
            int k = 0;
            for (int i = 0; i < p.length; i++) {
                int pr = p[i].row;
                int pc = p[i].column;
                if (Math.abs(mean - d[pr][pc]) < thr) {
                    m[pr][pc] = 255;
                    float contribution=0;
                    float dist=thr+Math.abs(meanFirst-d[pr][pc]);
                    if ((int)dist<contributionVector.length) {
                        contribution=contributionVector[(int)dist];
                    }                    
                    toplam += contribution*d[pr][pc];
                    k++;
                }
            }
            float mean2 = (k > 0) ? toplam / k : 0;
            mean = (mean2 > 0) ? (mean + mean2) / 2 : mean;
        }
        return m;
    }

    private static CPoint[] getWindowPixels(float[][] m, int row, int col, int r,int thr) {
        List<CPoint> lst = new ArrayList<>();

        {
            int j = row - r;

            for (int i = col - r; i <= col + r; i++) {
                //if the coordinates are valid?
                if (j >= 0 && j < m.length && i >= 0 && i < m[0].length) {
                    if (m[j][i] != 255 && m[j][i]>thr) {
                        CPoint p = new CPoint(j, i);
                        lst.add(p);
                    }
                }
            }
        }
        {
            int j = row + r;
            for (int i = col - r; i <= col + r; i++) {
                //if the coordinates are valid?
                if (j >= 0 && j < m.length && i >= 0 && i < m[0].length) {
                    if (m[j][i] != 255 && m[j][i]>thr) {
                        CPoint p = new CPoint(j, i);
                        lst.add(p);
                    }
                }
            }
        }
        {
            int i = col - r;
            for (int j = row - r+1; j <= row + r-1; j++) {
                //if the coordinates are valid?
                if (j >= 0 && j < m.length && i >= 0 && i < m[0].length) {
                    if (m[j][i] != 255 && m[j][i]>thr) {
                        CPoint p = new CPoint(j, i);
                        lst.add(p);
                    }
                }
            }
        }
        {
            int i = col + r;
            for (int j = row - r+1; j <= row + r-1; j++) {
                //if the coordinates are valid?
                if (j >= 0 && j < m.length && i >= 0 && i < m[0].length) {
                    if (m[j][i] != 255 && m[j][i]>thr) {
                        CPoint p = new CPoint(j, i);
                        lst.add(p);
                    }
                }
            }
        }
        CPoint[] ret = new CPoint[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            ret[i] = lst.get(i);
        }
        return ret;
    }
}
