/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.image_processing;

/**
 *
 * @author musa-atas
 */

public class NonMax {

    static double[] input;
    static double[] output;
    int progress=0;
    int width;
    int height;
    int convolveX[] = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
    int convolveY[] = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
    int templateSize = 3;


    public void init(double[] inputIn, int widthIn, int heightIn) {
        width = widthIn;
        height = heightIn;
        input = inputIn;
        output = new double[width * height];

    }

    public double[] process() {
        progress = 0;

        // first convolute image with x and y templates
        double diffx[] = new double[width * height];
        double diffy[] = new double[width * height];
        double mag[] = new double[width * height];

        double valx, valy;

        for (int x = templateSize / 2; x < width - (templateSize / 2); x++) {
            for (int y = templateSize / 2; y < height - (templateSize / 2); y++) {
                valx = 0;
                valy = 0;
                for (int x1 = 0; x1 < templateSize; x1++) {
                    for (int y1 = 0; y1 < templateSize; y1++) {
                        int pos = (y1 * templateSize + x1);
                        int imPos = (x + (x1 - (templateSize / 2))) + ((y + (y1 - (templateSize / 2))) * width);

                        valx += ((input[imPos]) * convolveX[pos]);
                        valy += ((input[imPos]) * convolveY[pos]);
                    }
                }
                diffx[x + (y * width)] = valx;
                diffy[x + (y * width)] = valy;
                mag[x + (y * width)] = Math.sqrt((valx * valx) + (valy * valy));
            }
        }

        for (int x = 1; x < width - 1; x++) {
            progress++;
            for (int y = 1; y < height - 1; y++) {
                int dx, dy;

                if (diffx[x + (y * width)] > 0) {
                    dx = 1;
                } else {
                    dx = -1;
                }

                if (diffy[x + (y * width)] > 0) {
                    dy = 1;
                } else {
                    dy = -1;
                }

                double a1, a2, b1, b2, A, B, point, val;
                if (Math.abs(diffx[x + (y * width)]) > Math.abs(diffy[x + (y * width)])) {
                    a1 = mag[(x + dx) + ((y) * width)];
                    a2 = mag[(x + dx) + ((y - dy) * width)];
                    b1 = mag[(x - dx) + ((y) * width)];
                    b2 = mag[(x - dx) + ((y + dy) * width)];
                    A = (Math.abs(diffx[x + (y * width)]) - Math.abs(diffy[x + (y * width)])) * a1 + Math.abs(diffy[x + (y * width)]) * a2;
                    B = (Math.abs(diffx[x + (y * width)]) - Math.abs(diffy[x + (y * width)])) * b1 + Math.abs(diffy[x + (y * width)]) * b2;
                    point = mag[x + (y * width)] * Math.abs(diffx[x + (y * width)]);
                    if (point >= A && point > B) {
                        val = Math.abs(diffx[x + (y * width)]);
                        output[x + (y * width)] = val;
                    } else {
                        val = 0;
                        output[x + (y * width)] = val;
                    }
                } else {
                    a1 = mag[(x) + ((y - dy) * width)];
                    a2 = mag[(x + dx) + ((y - dy) * width)];
                    b1 = mag[(x) + ((y + dy) * width)];
                    b2 = mag[(x - dx) + ((y + dy) * width)];
                    A = (Math.abs(diffy[x + (y * width)]) - Math.abs(diffx[x + (y * width)])) * a1 + Math.abs(diffx[x + (y * width)]) * a2;
                    B = (Math.abs(diffy[x + (y * width)]) - Math.abs(diffx[x + (y * width)])) * b1 + Math.abs(diffx[x + (y * width)]) * b2;
                    point = mag[x + (y * width)] * Math.abs(diffy[x + (y * width)]);
                    if (point >= A && point > B) {
                        //System.out.println("Setting value: " + (diffy[x + (y * width)]&0xff));
                        val = Math.abs(diffy[x + (y * width)]);
                        output[x + (y * width)] = val;
                    } else {
                        val = 0;
                        output[x + (y * width)] = val;
                    }
                }
            }
        }

        return output;
    }

    public int getProgress() {
        return progress;
    }
}
