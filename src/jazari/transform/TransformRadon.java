/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.transform;

import jazari.image_processing.ImageProcess;
import jazari.factory.FactoryNormalization;
import jazari.factory.FactoryUtils;
import java.awt.image.BufferedImage;

public class TransformRadon {

    public static BufferedImage forwardProjectionImg(BufferedImage img, int numberOfAngles, int numberOfProjections) {
        int w = img.getWidth();
        int h = img.getHeight();
        float originX = w / 2;
        float originY = h / 2;
        float projectionLine = (float)Math.sqrt(w * w + h * h); //imajın diagonal uzunluğu
        float t = 0.0f;
        //#projection aslında array uzunluğu dolayısıyla bununla gerçek 
        //t hesaplamalarındaki maxsimum diagonal arasında 1D interpolasyon gerekir
        float interpolationRatio = projectionLine / (float) numberOfProjections;
        float[][] projectedData = new float[numberOfAngles][numberOfProjections];
        int[][] imagePixels = ImageProcess.imageToPixelsInt(img);
        for (int i = 0; i < numberOfAngles; i++) {
            float teta = (float)(i * (Math.PI / numberOfAngles));
            float cosTeta = (float)Math.cos(teta);
            float sinTeta = (float)Math.sin(teta);
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    t = (x - originX) * cosTeta + (y - originY) * sinTeta;
                    float dIndex = (float)(t / interpolationRatio + (numberOfProjections / 2)) - 0.5f;
                    int upNeighbourCell = (int) Math.ceil(dIndex);
                    int downNeighbourCell = (int) dIndex;
                    float downCellContribution = imagePixels[y][x] * (upNeighbourCell - dIndex);
                    float upCellContribution = imagePixels[y][x] * (dIndex - downNeighbourCell);
                    if (upNeighbourCell < numberOfProjections && downNeighbourCell > -1) {
                        projectedData[i][downNeighbourCell] += downCellContribution;
                        projectedData[i][upNeighbourCell] += upCellContribution;
                    }
                }
            }
        }
        BufferedImage backImage = ImageProcess.pixelsToImageGray(FactoryUtils.toIntArray2D(FactoryNormalization.normalizeMinMax(projectedData)));
        return backImage;
    }
    
    public static float[][] forwardProjection(float[][] imagePixels, int numberOfAngles, int numberOfProjections) {
        int w = imagePixels.length;
        int h = imagePixels[0].length;
        float originX = w / 2;
        float originY = h / 2;
        float projectionLine = (float)Math.sqrt(w * w + h * h); //imajın diagonal uzunluğu
        float t = 0.0f;
        //#projection aslında array uzunluğu dolayısıyla bununla gerçek 
        //t hesaplamalarındaki maxsimum diagonal arasında 1D interpolasyon gerekir
        float interpolationRatio = projectionLine / (float) numberOfProjections;
        float[][] projectedData = new float[numberOfAngles][numberOfProjections];
        for (int i = 0; i < numberOfAngles; i++) {
            float teta = (float) (i * (Math.PI / (float) numberOfAngles));
            float cosTeta = (float)Math.cos(teta);
            float sinTeta = (float)Math.sin(teta);
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    t = (x - originX) * cosTeta + (y - originY) * sinTeta;
                    float dIndex = (float)(t / interpolationRatio + (numberOfProjections / 2)) - 0.5f;
                    int upNeighbourCell = (int) Math.ceil(dIndex);
                    int downNeighbourCell = (int) dIndex;
                    float downCellContribution = imagePixels[y][x] * (upNeighbourCell - dIndex);
                    float upCellContribution = imagePixels[y][x] * (dIndex - downNeighbourCell);
                    if (upNeighbourCell < numberOfProjections && downNeighbourCell > -1) {
                        projectedData[i][downNeighbourCell] += downCellContribution;
                        projectedData[i][upNeighbourCell] += upCellContribution;
                    }
                }
            }
        }
        projectedData=FactoryUtils.transpose(projectedData);
        return projectedData;
    }

    public static BufferedImage backwardProjectionImg(float[][] projArr, int imgSize) {
        float originX = imgSize / 2;
        float originY = imgSize / 2;
        int numberOfAngles = projArr.length;
        int numberOfProjections = projArr[0].length;
        float projectionLine = (float)(Math.sqrt(2) * imgSize);
        float t = 0.0f;
        float interpolationRatio = projectionLine / (float) numberOfProjections;
        float[][] backProjectedData = new float[imgSize][imgSize];

        for (int i = 0; i < numberOfAngles; i++) {
            float teta = (float) (i * (Math.PI / (float) numberOfAngles));
            float cosTeta = (float)Math.cos(teta);
            float sinTeta =(float) Math.sin(teta);
            for (int y = 0; y < imgSize; y++) {
                for (int x = 0; x < imgSize; x++) {
                    t = (x - originX) * cosTeta + (y - originY) * sinTeta;
                    float dIndex = (float)(t / interpolationRatio + (numberOfProjections / 2)) - 0.5f;
                    int upNeighbourCell = (int) Math.ceil(dIndex);
                    int downNeighbourCell = (int) dIndex;
                    if (upNeighbourCell < numberOfProjections && downNeighbourCell > -1) {
                        float upCellContribution = projArr[i][upNeighbourCell] * (upNeighbourCell - dIndex);
                        float downCellContribution = projArr[i][downNeighbourCell] * (dIndex - downNeighbourCell);
                        backProjectedData[y][x] += upCellContribution;
                        backProjectedData[y][x] += downCellContribution;
                    }
                }
            }
        }
        BufferedImage backImage = ImageProcess.pixelsToImageGray(FactoryUtils.toIntArray2D(FactoryNormalization.normalizeMinMax(backProjectedData)));
        return backImage;
    }
    
    public static float[][] backwardProjection(float[][] projArr, int imgSize) {
        float originX = imgSize / 2;
        float originY = imgSize / 2;
        int numberOfAngles = projArr.length;
        int numberOfProjections = projArr[0].length;
        float projectionLine = (float)Math.sqrt(2) * imgSize;
        float t = 0.0f;
        float interpolationRatio = projectionLine / (float) numberOfProjections;
        float[][] backProjectedData = new float[imgSize][imgSize];

        for (int i = 0; i < numberOfAngles; i++) {
            float teta = (float) (i * (Math.PI / numberOfAngles));
            float cosTeta = (float)Math.cos(teta);
            float sinTeta = (float)Math.sin(teta);
            for (int y = 0; y < imgSize; y++) {
                for (int x = 0; x < imgSize; x++) {
                    t = (x - originX) * cosTeta + (y - originY) * sinTeta;
                    float dIndex = (float)(t / interpolationRatio + (numberOfProjections / 2)) - 0.5f;
                    int upNeighbourCell = (int) Math.ceil(dIndex);
                    int downNeighbourCell = (int) dIndex;
                    if (upNeighbourCell < numberOfProjections && downNeighbourCell > -1) {
                        float upCellContribution = projArr[i][upNeighbourCell] * (upNeighbourCell - dIndex);
                        float downCellContribution = projArr[i][downNeighbourCell] * (dIndex - downNeighbourCell);
                        backProjectedData[y][x] += upCellContribution;
                        backProjectedData[y][x] += downCellContribution;
                    }
                }
            }
        }
        return backProjectedData;
    }

    public static BufferedImage doSheppLoganFilter(BufferedImage img, int numberOfAngles, int numberOfProjections) {
        float[][] dd=FactoryUtils.toFloatArray2D(ImageProcess.imageToPixelsInt(img));
        float[][] ProjectionArray = forwardProjection(dd, numberOfAngles, numberOfProjections);
        int w = img.getWidth();
        int h = img.getHeight();
        float projectionLine = (float)Math.sqrt(w * w + h * h); //imajın diagonal uzunluğu
        float interpolationRatio = projectionLine / (float) numberOfProjections;
        float distance = interpolationRatio;
        float temp = 4.0f / (float)(Math.PI * distance * distance);
        float[] PHI = new float[numberOfProjections];
        PHI[0] = temp;
        for (int K = 1; K < numberOfProjections; ++K) {
            PHI[K] = -temp / (4 * K * K - 1.0f);
        }
        float[][] d = doConvolution(ProjectionArray, numberOfAngles, numberOfProjections, PHI);
        BufferedImage imgRet = ImageProcess.pixelsToImageGray(FactoryUtils.toIntArray2D(d));
        return imgRet;
    }

    public static BufferedImage doRampFilter(BufferedImage img, int numberOfAngles, int numberOfProjections) {
        float[][] dd=FactoryUtils.toFloatArray2D(ImageProcess.imageToPixelsInt(img));
        float[][] ProjectionArray = forwardProjection(dd, numberOfAngles, numberOfProjections);
        float distance = 0.1f;
        float temp = (1 / (2 * distance)) * (1 / (2 * distance));
        float[] PHI = new float[numberOfProjections];
        PHI[0] = temp;
        for (int K = 1; K < numberOfProjections; ++K) {
            if ((2 * (K / 2) - K) == 0.0) {
                PHI[K] = 0.0f;
            } else {
                PHI[K] = (float)((-4.0 * temp) / (K * K * Math.PI * Math.PI));
            }
        }
        float[][] d = doConvolution(ProjectionArray, numberOfAngles, numberOfProjections, PHI);
        BufferedImage imgRet = ImageProcess.pixelsToImageGray(FactoryUtils.toIntArray2D(d));
        return imgRet;
    }

    public static float[][] doConvolution(float[][] ProjectionArray, int numberOfAngles, int numberOfProjections, float[] PHI) {
        float[][] convolutionArray = new float[numberOfAngles][numberOfProjections];
        for (int m = 0; m < numberOfAngles; m++) {
            for (int i = 0; i < numberOfProjections; i++) {
                for (int j = 0; j < numberOfProjections; j++) {
                    convolutionArray[m][i] += ProjectionArray[m][j] * PHI[Math.abs(i - j)];
                }
            }
        }
        return convolutionArray;
    }

}
