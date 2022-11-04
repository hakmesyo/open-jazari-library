package jazari.factory;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import static jazari.factory.FactoryUtils.showMessage;
import static jazari.factory.FactoryUtils.toFloatArray1D;
import jazari.matrix.Dimension;
import jazari.utils.PerlinNoise;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jwave.Transform;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.haar.Haar1;
import jwave.transforms.wavelets.daubechies.Daubechies2;
import jwave.transforms.wavelets.daubechies.Daubechies3;
import jwave.transforms.wavelets.daubechies.Daubechies4;
import jwave.transforms.wavelets.daubechies.Daubechies5;
import jwave.transforms.wavelets.daubechies.Daubechies6;
import jwave.transforms.wavelets.daubechies.Daubechies7;

/**
 * @author Dr. Musa ATAŞ It is developed for performance issues for
 * resource-constrained devices
 */
public final class FactoryMatrix implements Serializable {

    private final static int BLOCK_WIDTH = 60;
    private final static int BLOCK_WIDTH_CHOL = 20;
    private final static int BLOCK_SIZE = BLOCK_WIDTH * BLOCK_WIDTH;
    private final static int BLOCK_THR = 10000;
    private final static float MACHEPS = 2E-16f;

    /**
     * generates two dimensional Type array include default values
     *
     * @param nRows:i
     * @param nCols:i
     * @param obj:i
     * @return q
     */
    public static Object[][] matrixObjectDefault(int nRows, int nCols, Object obj) {
        Object[][] ret = new Object[nRows][nCols];
        for (Object[] ret1 : ret) {
            for (Object item : ret1) {
                item = new Object();
            }
        }
        return ret;
    }

    /**
     * generates two dimensional float array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
    public static float[][] matrixFloatZeros(int nRows, int nCols) {
        return new float[nRows][nCols];
    }

    public static float[] matrixFloatZeros1D(int nRows) {
        return new float[nRows];
    }

    public static float[][] matrixFloatZeros(int p) {
        return matrixFloatZeros(p, p);
    }

    public static float[] matrixFloatOnes1D(int nRows) {
        float[] d = new float[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = 1.0f;
        }
        return d;
    }

    public static float[] matrixFloatValue1D(int nRows, float val) {
        float[] d = new float[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = val;
        }
        return d;
    }

    public static float[][] matrixFloatOnes(int p) {
        return matrixFloatValue(p, p, 1.0f);
    }

    public static float[][] matrixFloatOnes(int nRows, int nCols) {
        return matrixFloatValue(nRows, nCols, 1.0f);
    }

    public static float[][] matrixFloatValue(int p, float val) {
        return matrixFloatValue(p, p, val);
    }

    public static float[][] matrixFloatValue(int nRows, int nCols, float val) {
        float[][] d = new float[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    /**
     * generates two dimensional float array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
//    public static float[][] matrixFloatZeros(int nRows, int nCols) {
//        return new float[nRows][nCols];
//    }
//
//    public static float[] matrixFloatZeros1D(int nRows) {
//        return new float[nRows];
//    }
//
//    public static float[][] matrixFloatZeros(int p) {
//        return matrixFloatZeros(p, p);
//    }
//
//    public static float[] matrixFloatOnes1D(int nRows) {
//        float[] d = new float[nRows];
//        for (int i = 0; i < d.length; i++) {
//            d[i] = 1.0f;
//        }
//        return d;
//    }
//
//    public static float[] matrixFloatValue1D(int nRows, float val) {
//        float[] d = new float[nRows];
//        for (int i = 0; i < d.length; i++) {
//            d[i] = val;
//        }
//        return d;
//    }
//
//    public static float[][] matrixFloatOnes(int p) {
//        return matrixFloatValue(p, p, 1.0f);
//    }
//
//    public static float[][] matrixFloatOnes(int nRows, int nCols) {
//        return matrixFloatValue(nRows, nCols, 1.0f);
//    }
//
//    public static float[][] matrixFloatValue(int p, float val) {
//        return matrixFloatValue(p, p, val);
//    }
//
//    public static float[][] matrixFloatValue(int nRows, int nCols, float val) {
//        float[][] d = new float[nRows][nCols];
//        for (int i = 0; i < nRows; i++) {
//            for (int j = 0; j < nCols; j++) {
//                d[i][j] = val;
//            }
//        }
//        return d;
//    }
    /**
     * generates two dimensional int array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
    public static int[][] matrixIntZeros(int nRows, int nCols) {
        return new int[nRows][nCols];
    }

    public static int[] matrixIntZeros1D(int nRows) {
        return new int[nRows];
    }

    public static int[][] matrixIntZeros(int p) {
        return matrixIntZeros(p, p);
    }

    public static int[] matrixIntOnes1D(int nRows) {
        int[] d = new int[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = 1;
        }
        return d;
    }

    public static int[] matrixIntValue1D(int nRows, int val) {
        int[] d = new int[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = 1;
        }
        return d;
    }

    public static int[][] matrixIntOnes(int p) {
        return matrixIntValue(p, p, 1);
    }

    public static int[][] matrixIntOnes(int nRows, int nCols) {
        return matrixIntValue(nRows, nCols, 1);
    }

    public static int[][] matrixIntValue(int p, int val) {
        return matrixIntValue(p, p, val);
    }

    public static int[][] matrixIntValue(int nRows, int nCols, int val) {
        int[][] d = new int[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    /**
     * generates two dimensional byte array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
    public static byte[][] matrixByteZeros(int nRows, int nCols) {
        return new byte[nRows][nCols];
    }

    public static byte[] matrixByteZeros1D(int nRows) {
        return new byte[nRows];
    }

    public static byte[][] matrixByteZeros(int p) {
        return matrixByteZeros(p, p);
    }

    public static byte[] matrixByteOnes1D(int nRows) {
        byte[] d = new byte[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = 1;
        }
        return d;
    }

    public static byte[] matrixByteValue1D(int nRows, byte val) {
        byte[] d = new byte[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = val;
        }
        return d;
    }

    public static byte[][] matrixByteOnes(int p) {
        return matrixByteValue(p, p, (byte) 1);
    }

    public static byte[][] matrixByteOnes(int nRows, int nCols) {
        return matrixByteValue(nRows, nCols, (byte) 1);
    }

    public static byte[][] matrixByteValue(int p, byte val) {
        return matrixByteValue(p, p, val);
    }

    public static byte[][] matrixByteValue(int nRows, int nCols, byte val) {
        byte[][] d = new byte[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    /**
     * generates two dimensional long array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
    public static long[][] matrixLongZeros(int nRows, int nCols) {
        return new long[nRows][nCols];
    }

    public static long[] matrixLongZeros1D(int nRows) {
        return new long[nRows];
    }

    public static long[][] matrixLongZeros(int p) {
        return matrixLongZeros(p, p);
    }

    public static long[] matrixLongOnes1D(int nRows) {
        long[] d = new long[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = 1;
        }
        return d;
    }

    public static long[] matrixLongValue1D(int nRows, long val) {
        long[] d = new long[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = val;
        }
        return d;
    }

    public static long[][] matrixLongOnes(int p) {
        return matrixLongValue(p, p, (long) 1);
    }

    public static long[][] matrixLongOnes(int nRows, int nCols) {
        return matrixLongValue(nRows, nCols, (long) 1);
    }

    public static long[][] matrixLongValue(int p, long val) {
        return matrixLongValue(p, p, val);
    }

    public static long[][] matrixLongValue(int nRows, int nCols, long val) {
        long[][] d = new long[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    /**
     * generates two dimensional short array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
    public static short[][] matrixShortZeros(int nRows, int nCols) {
        return new short[nRows][nCols];
    }

    public static short[] matrixShortZeros1D(int nRows) {
        return new short[nRows];
    }

    public static short[] matrixShortOnes1D(int nRows) {
        short[] d = new short[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = 1;
        }
        return d;
    }

    public static short[] matrixShortValue1D(int nRows, short val) {
        short[] d = new short[nRows];
        for (int i = 0; i < d.length; i++) {
            d[i] = val;
        }
        return d;
    }

    public static short[][] matrixShortZeros(int p) {
        return matrixShortZeros(p, p);
    }

    public static short[][] matrixShortOnes(int p) {
        return matrixShortValue(p, p, (short) 1);
    }

    public static short[][] matrixShortOnes(int nRows, int nCols) {
        return matrixShortValue(nRows, nCols, (short) 1);
    }

    public static short[][] matrixShortValue(int p, short val) {
        return matrixShortValue(p, p, val);
    }

    public static short[][] matrixShortValue(int nRows, int nCols, short val) {
        short[][] d = new short[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    /**
     * generates two dimensional boolean array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
    public static boolean[][] matrixBooleanFalse(int nRows, int nCols) {
        return new boolean[nRows][nCols];
    }

    public static boolean[][] matrixBooleanFalse(int p) {
        return matrixBooleanFalse(p, p);
    }

    public static boolean[] matrixBooleanFalse1D(int p) {
        return new boolean[p];
    }

    public static boolean[][] matrixBooleanTrue(int p) {
        return matrixBooleanValue(p, p, true);
    }

    public static boolean[] matrixBooleanTrue1D(int p) {
        return matrixBooleanValue1D(p, true);
    }

    public static boolean[][] matrixBooleanTrue(int nRows, int nCols) {
        return matrixBooleanValue(nRows, nCols, true);
    }

    public static boolean[][] matrixBooleanValue(int p, boolean val) {
        return matrixBooleanValue(p, p, val);
    }

    public static boolean[][] matrixBooleanValue(int nRows, int nCols, boolean val) {
        boolean[][] d = new boolean[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    public static boolean[] matrixBooleanValue1D(int nRows, boolean val) {
        boolean[] d = new boolean[nRows];
        for (int i = 0; i < nRows; i++) {
            d[i] = val;
        }
        return d;
    }

    /**
     * generates two dimensional char array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
    public static char[][] matrixCharEmpty(int nRows, int nCols) {
        return new char[nRows][nCols];
    }

    public static char[][] matrixCharEmpty(int p) {
        return matrixCharEmpty(p, p);
    }

    public static char[] matrixCharEmpty1D(int p) {
        return new char[p];
    }

    public static char[] matrixCharValue1D(int p, char val) {
        char[] d = new char[p];
        for (int i = 0; i < p; i++) {
            d[i] = val;
        }
        return d;
    }

    public static char[][] matrixCharValue(int p, char val) {
        char[][] d = new char[p][p];
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < p; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    public static char[][] matrixCharValue(int nRows, int nCols, char val) {
        char[][] d = new char[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    /**
     * generates two dimensional String array
     *
     * @param nRows:i
     * @param nCols:i
     * @return q
     */
    public static String[][] matrixStringEmpty(int nRows, int nCols) {
        return new String[nRows][nCols];
    }

    public static String[][] matrixStringEmpty(int p) {
        return matrixStringEmpty(p, p);
    }

    public static String[] matrixStringEmpty1D(int p) {
        return new String[p];
    }

    public static String[] matrixStringValue1D(int p, String val) {
        String[] d = new String[p];
        for (int i = 0; i < d.length; i++) {
            d[i] = val;
        }
        return d;
    }

    public static String[][] matrixStringValue(int p, String val) {
        String[][] d = new String[p][p];
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < p; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    public static String[][] matrixStringValue(int nRows, int nCols, String val) {
        String[][] d = new String[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                d[i][j] = val;
            }
        }
        return d;
    }

    /**
     * ************************************************************************
     * Clone Operation : using fastest method of System.arraycopy()
     * ************************************************************************
     * @param input:i
     * @return q
     */
    public static int[][] clone(int[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        int[][] result = new int[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static float[][][] clone(float[][][] input) {
        if (input == null) {
            return null;
        }
        int n1 = input.length;
        int n2 = input[0].length;
        int n3 = input[0][0].length;
        float[][][] result = new float[n1][n2][n3];
        for (int i = 0; i < n1; i++) {
            result[i] = clone(input[i]);
        }
        return result;
    }

    public static double[][][] clone(double[][][] input) {
        if (input == null) {
            return null;
        }
        int n1 = input.length;
        int n2 = input[0].length;
        int n3 = input[0][0].length;
        double[][][] result = new double[n1][n2][n3];
        for (int i = 0; i < n1; i++) {
            result[i] = clone(input[i]);
        }
        return result;
    }

    public static int[] clone(int[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int[] result = new int[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

    public static float[][] clone(float[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        float[][] result = new float[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static double[][] clone(double[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        double[][] result = new double[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static float[] clone(float[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        float[] result = new float[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

    public static double[] clone(double[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        double[] result = new double[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

//    public static float[][] clone(float[][] input) {
//        if (input == null) {
//            return null;
//        }
//        int r = input.length;
//        int c = input[0].length;
//        float[][] result = new float[r][c];
//        for (int i = 0; i < r; i++) {
//            System.arraycopy(input[i], 0, result[i], 0, c);
//        }
//        return result;
//    }
//    public static float[] clone(float[] input) {
//        if (input == null) {
//            return null;
//        }
//        int r = input.length;
//        float[] result = new float[r];
//        System.arraycopy(input, 0, result, 0, r);
//        return result;
//    }
    public static short[][] clone(short[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        short[][] result = new short[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static short[] clone(short[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        short[] result = new short[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

    public static long[][] clone(long[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        long[][] result = new long[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static long[] clone(long[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        long[] result = new long[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

    public static byte[][] clone(byte[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        byte[][] result = new byte[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static byte[] clone(byte[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        byte[] result = new byte[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

    public static boolean[][] clone(boolean[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        boolean[][] result = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static boolean[] clone(boolean[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        boolean[] result = new boolean[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

    public static char[][] clone(char[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        char[][] result = new char[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static char[] clone(char[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        char[] result = new char[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

    public static String[][] clone(String[][] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        int c = input[0].length;
        String[][] result = new String[r][c];
        for (int i = 0; i < r; i++) {
            System.arraycopy(input[i], 0, result[i], 0, c);
        }
        return result;
    }

    public static String[] clone(String[] input) {
        if (input == null) {
            return null;
        }
        int r = input.length;
        String[] result = new String[r];
        System.arraycopy(input, 0, result, 0, r);
        return result;
    }

    public static void dump(float[][] p) {
        System.out.println(toString(p));
    }

    public static void dump(float[] p) {
        System.out.println(toString(p));
    }

    public static void dump(String str, float[][] p) {
        System.out.println(str);
        System.out.println(toString(p));
    }

    public static void dump(String str, float[] p) {
        System.out.println(str);
        System.out.println(toString(p));
    }

    public static void println(float[][] p) {
        System.out.println(toString(p));
    }

    public static void println(String[][] p) {
        System.out.println(toString(p));
    }

    public static void println(float[] p) {
        System.out.println(toString(p));
    }

    public static String toString(float[][] p) {
        StringBuilder str = new StringBuilder("[" + p.length + "x" + p[0].length + "]=\n");
        for (float[] p1 : p) {
            for (int j = 0; j < p[0].length; j++) {
                str.append(p1[j]);
                str.append("\t");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public static String toString(double[][] p) {
        StringBuilder str = new StringBuilder("[" + p.length + "x" + p[0].length + "]=\n");
        for (double[] p1 : p) {
            for (int j = 0; j < p[0].length; j++) {
                str.append(p1[j]);
                str.append("\t");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public static String toString(String[][] p) {
        StringBuilder str = new StringBuilder("[" + p.length + "x" + p[0].length + "]=\n");
        for (String[] p1 : p) {
            for (int j = 0; j < p[0].length; j++) {
                str.append(p1[j]);
                str.append("\t");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public static String toString(float[] p) {
        StringBuilder str = new StringBuilder("[" + p.length + "]=\n");
        for (float p1 : p) {
            str.append(p1);
            str.append("\t");
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * ************************************************************************
     * Matrix Transpose Methods : faster approach is used here
     * ************************************************************************
     * @param d:i
     * @return q
     */
    public static float[][] transposeSlower(float[][] d) {
        int r = d.length;
        int c = d[0].length;
        float[][] ret = new float[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                ret[j][i] = d[i][j];
            }
        }
        return ret;
    }

    /**
     * Efficient and fast matrix transpose in terms of memory and computation
     *
     * @param d:i
     * @return q
     */
    public static float[][] transposeSquare(float[][] d) {
        float temp;
        int r = d.length;
        int c = d[0].length;
        for (int i = 0; i < (r / 2 + 1); i++) {
            for (int j = i; j < c; j++) {
                temp = d[i][j];
                d[i][j] = d[j][i];
                d[j][i] = temp;
            }
        }
        return d;
    }

    public static float[][] transposeBlock(float[][] d) {
        int r = d.length;
        int c = d[0].length;
        float[][] ret = new float[c][r];
        if (r == c) {
            ret = transposeSquare(d);
        } else if (r > BLOCK_THR && c > BLOCK_THR) {
            ret = transposeBlock(d, BLOCK_WIDTH);
        } else {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    ret[j][i] = d[i][j];
                }
            }
        }
        return ret;
    }

    private static float[][] transposeBlock(float[][] d, final int blockLength) {
        long time = FactoryUtils.tic();
        int r = d.length;
        int c = d[0].length;
        float[][] t = new float[c][r];
        float[] d1 = FactoryUtils.toFloatArray1D(d);
        time = FactoryUtils.toc("1d cost:", time);
        float[] t1 = new float[r * c];

        for (int i = 0; i < r; i += blockLength) {
            int blockHeight = Math.min(blockLength, r - i);

            int indexSrc = i * c;
            int indexDst = i;

            for (int j = 0; j < c; j += blockLength) {
                int blockWidth = Math.min(blockLength, c - j);
                int indexSrcEnd = indexSrc + blockWidth;
                for (; indexSrc < indexSrcEnd; indexSrc++) {
                    int rowSrc = indexSrc;
                    int rowDst = indexDst;
                    int end = rowDst + blockHeight;
                    for (; rowDst < end; rowSrc += c) {
                        // faster to write in sequence than to read in sequence
                        t1[rowDst++] = d1[rowSrc];
                    }
                    indexDst += r;
                }
            }
        }
        time = FactoryUtils.toc("block cost:", time);
        t = FactoryUtils.toFloatArray2D(t1, c, r);
        time = FactoryUtils.toc("2d cost:", time);
        return t;
    }

    public static float[][] transpose(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                ret[i][j] = d[j][i];
            }
        }
        return ret;
    }

//    public static float[][] transpose(float[][] d) {
//        int nr = d.length;
//        int nc = d[0].length;
//        float[][] ret = new float[nc][nr];
//        for (int i = 0; i < nc; i++) {
//            for (int j = 0; j < nr; j++) {
//                ret[i][j] = d[j][i];
//            }
//        }
//        return ret;
//    }
    public static int[][] transpose(int[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        int[][] ret = new int[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                ret[i][j] = d[j][i];
            }
        }
        return ret;
    }

    public static long[][] transpose(long[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        long[][] ret = new long[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                ret[i][j] = d[j][i];
            }
        }
        return ret;
    }

    public static byte[][] transpose(byte[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        byte[][] ret = new byte[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                ret[i][j] = d[j][i];
            }
        }
        return ret;
    }

    public static short[][] transpose(short[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        short[][] ret = new short[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                ret[i][j] = d[j][i];
            }
        }
        return ret;
    }

    public static char[][] transpose(char[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        char[][] ret = new char[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                ret[i][j] = d[j][i];
            }
        }
        return ret;
    }

    public static boolean[][] transpose(boolean[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        boolean[][] ret = new boolean[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                ret[i][j] = d[j][i];
            }
        }
        return ret;
    }

    public static String[][] transpose(String[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        String[][] ret = new String[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                ret[i][j] = d[j][i];
            }
        }
        return ret;
    }

    public static <T> T[][] deepCopy(T[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

    public static <T> T[][] deepClone(T[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

    public static Object[][] transpose(Object[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        Object[][] dc = deepCopy(d);
        Object[][] ret = new Object[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                //ret[i][j] = FactoryUtils.deepClone(dc[j][i]);
                ret[i][j] = dc[j][i];
            }
        }
        return ret;
    }

    /**
     * *************************************************************************
     * Populate Array with Random number
     * *************************************************************************
     * @param p:i
     * @return q
     */
    public static float[][] matrixFloatRandom(int p) {
        return matrixFloatValue(p, (float) Math.random());
    }

    public static float[][] matrixFloatRandom(int r, int c) {
        return matrixFloatValue(r, c, (float) Math.random());
    }

    public static float[][] matrixFloatRandom(int r, int c, int from, int to) {
        return matrixFloatValue(r, c, (float) Math.random() * (to - from) + from);
    }

//    public static float[][] matrixFloatRandom(int p) {
//        return matrixFloatValue(p, (float) Math.random());
//    }
//
//    public static float[][] matrixFloatRandom(int r, int c) {
//        return matrixFloatValue(r, c, (float) Math.random());
//    }
//
//    public static float[][] matrixFloatRandom(int r, int c, int from, int to) {
//        return matrixFloatValue(r, c, (float) (Math.random() * (to - from) + from));
//    }
    public static int[][] matrixIntRandom(int p) {
        return matrixIntValue(p, (int) Math.round(Math.random()));
    }

    public static int[][] matrixIntRandom(int r, int c) {
        return matrixIntValue(r, c, (int) Math.round(Math.random()));
    }

    public static int[][] matrixIntRandom(int r, int c, int from, int to) {
        return matrixIntValue(r, c, (int) Math.round(Math.random() * (to - from) + from));
    }

    public static byte[][] matrixByteRandom(int p) {
        return matrixByteValue(p, (byte) Math.round(Math.random()));
    }

    public static byte[][] matrixByteRandom(int r, int c) {
        return matrixByteValue(r, c, (byte) Math.round(Math.random()));
    }

    public static byte[][] matrixByteRandom(int r, int c, int from, int to) {
        return matrixByteValue(r, c, (byte) Math.round(Math.random() * (to - from) + from));
    }

    public static short[][] matrixShortRandom(int p) {
        return matrixShortValue(p, (short) Math.round(Math.random()));
    }

    public static short[][] matrixShortRandom(int r, int c) {
        return matrixShortValue(r, c, (short) Math.round(Math.random()));
    }

    public static short[][] matrixShortRandom(int r, int c, int from, int to) {
        return matrixShortValue(r, c, (short) Math.round(Math.random() * (to - from) + from));
    }

    public static long[][] matrixLongRandom(int p) {
        return matrixLongValue(p, (long) Math.round(Math.random()));
    }

    public static long[][] matrixLongRandom(int r, int c) {
        return matrixLongValue(r, c, (long) Math.round(Math.random()));
    }

    public static long[][] matrixLongRandom(int r, int c, int from, int to) {
        return matrixLongValue(r, c, (long) Math.round(Math.random() * (to - from) + from));
    }

    public static float[][] excludeRows(float[][] p1, float[][] p2) {
        int r = p1.length;
        int c = p1[0].length;
        ArrayList<Integer> lst_index = new ArrayList();
        for (int i = 0; i < r; i++) {
            for (float[] item : p2) {
                if (Arrays.equals(p1[i], item)) {
                    if (!lst_index.contains(i)) {
                        if (lst_index.size() == p2.length) {
                            break;
                        }
                        lst_index.add(i);
                    }
                }
            }
        }
        float[][] ret = new float[r - lst_index.size()][c];
        int k = 0;
        for (int i = 0; i < r; i++) {
            if (!lst_index.contains(i)) {
                ret[k++] = Arrays.copyOf(p1[i], c);
            }
        }
        return ret;
    }

//    /**
//     * B.Matlabdeki randperm in aynısı Görevi kendisine verilen n sayısına kadar
//     * random indexler üretmek
//     *
//     * @param n
//     * @return
//     */
    public static int[] randPermInt(int n) {
        List<Integer> lst
                = IntStream.range(0, n) // <-- creates a stream of ints
                        .boxed() // <-- converts them to Integers
                        .collect(Collectors.toList());          // <-- collects the values to a list

        Collections.shuffle(lst);
//        int[] m = new int[n];
//        List<Integer> lst=Arrays.asList(IntStream.range(6, 10));
//        Collections.shuffle(Arrays.asList(m));
//        ArrayList<Integer> v = new ArrayList<Integer>();
//        for (int i = 0; i < n; i++) {
//            v.add(i);
//        }
//        for (int i = 0; i < n; i++) {
//            int a = new Random().nextInt(n - i);
//            m[i] = v.get(a);
//            v.remove(a);
//        }
        int[] array = lst.stream().mapToInt(i -> i).toArray();
        return array;
    }

    public static float[][] shuffle(float[][] ds) {
        float[][] ret = clone(ds);
        int[] d = randPermInt(ds.length);
        int n = d.length;
        for (int i = 0; i < n; i++) {
            ret[i] = ds[d[i]];
        }
        return ret;
    }

    public static float[][] shuffleRows(float[][] ds) {
        float[][] ret = clone(ds);
        int[] d = randPermInt(ds.length);
        int n = d.length;
        for (int i = 0; i < n; i++) {
            ret[i] = ds[d[i]];
        }
        return ret;
    }

    /**
     * it splits the dataset into nFolds disjoint folds for test and remaining
     * as train sets. Hence, it holds 2D CMatrix array from which, first
     * dimensions train and second one is test
     *
     * @param d:i
     * @param nFolds:i
     * @return q
     */
    public static float[][][][] crossValidationSets(float[][] d, int nFolds) {
//        float[][] d=clone(input);
        float[][][][] ret = new float[nFolds][2][d.length][d[0].length];
        int nRows = d.length;
        int nCols = d[0].length;
        int delta = nRows / nFolds;
        float[][] test = new float[delta][d[0].length];
        float[][] train = new float[d.length - delta][d[0].length];
        for (int i = 0; i < nFolds; i++) {
            int from = i * delta;
            int to = from + delta;
            int k = 0;
            for (int j = from; j < to; j++) {
                test[k++] = clone(d[j]);
            }
            if (from == 0) {
                int t = 0;
                for (int j = to; j < nRows; j++) {
                    train[t++] = clone(d[j]);
                }
            } else {
                int t = 0;
                for (int j = 0; j < from; j++) {
                    train[t++] = clone(d[j]);
                }
                for (int j = to; j < nRows; j++) {
                    train[t++] = clone(d[j]);
                }
            }
            ret[i][0] = clone(train);
            ret[i][1] = clone(test);
        }
        return ret;
    }

    /**
     * Tries to interpret String parameter as Matlab vectorization commands like
     * that interpret(d,"5:end","0:3") means get subset based on the specified
     * criteria
     *
     * @param d:i
     * @param p1:i
     * @param p2:i
     * @return q
     */
    public static float[][] interpret(float[][] d, String p1, String p2) {
        p1 = p1.replace("[", "").replace("]", "").replace("(", "").replace(")", "");
        p2 = p2.replace("[", "").replace("]", "").replace("(", "").replace(")", "");
        float[][] ret = clone(d);
        if (p1.equals(":") && p2.equals(":")) {
            return ret;
        }
        if (p1.equals(":")) {
            if (p2.equals(":")) {
                return d;
            } else if (p2.length() == 1) {
                int[] p = {Integer.parseInt(p2)};
                ret = columns(d, p);
            } else {
                int[] p = checkParam(p2, d[0].length);
                ret = columns(d, p);
            }
        } else if (p2.equals(":")) {
            if (p1.equals(":")) {
                return d;
            } else if (p1.length() == 1) {
                int[] p = {Integer.parseInt(p1)};
                ret = rows(d, p);
            } else {
                int[] p = checkParam(p1, d.length);
                ret = rows(d, p);
            }
        } else {
            int[] pp1 = checkParam(p1, d.length);
            int[] pp2 = checkParam(p2, d[0].length);
            ret = transpose(subset(d, pp1, pp2));
        }
        return ret;
    }

    /**
     * subset tries to extract new matrix from the original one based on the
     * specified rows and then columns as in matlab like that
     * a=b([1,4,11],[3,5]); It supports dynamic parameter which means you can
     * call the subset methods with one or more vector
     *
     * Biiznillah Matlab de herhangi bir matrisin içeriğini mesela
     * a=b([1,4,11]); şeklinde alabiliyorduk burada aynı işlem yapılmaya
     * çalışılmıştır. 1 parametre girilse row lardan ilgili index teki verileri
     * filtreler 2 paramatre girilirse hem row hem column filtresi yapar.
     *
     * dynamic parameter
     *
     * @param input:i
     * @param p:i
     * @return q
     */
    public static float[][] subset(float[][] input, int[]... p) {
        float[][] d;
        if (p.length == 0) {
            return null;
        } else if (p.length == 1) {
            int[] rows = p[0];
            d = new float[rows.length][input[0].length];
            for (int i = 0; i < rows.length; i++) {
                d[i] = input[rows[i]];
            }
        } else {
            int[] rows = p[0];
            int[] cols = p[1];
            d = new float[rows.length][input[0].length];
            for (int i = 0; i < rows.length; i++) {
                d[i] = input[rows[i]];
            }
            d = transpose(d);
            float[][] d2 = new float[cols.length][d[0].length];
            for (int i = 0; i < cols.length; i++) {
                d2[i] = d[cols[i]];
            }
            d = transpose(d2);
        }
        float[][] ret = clone(d);
        return ret;
    }

    private static int[] checkParam(String p, int n) {
//        String s = p.substring(1, p.length() - 1);
        String s = p;
        int[] ret = null;
        char[] chr = s.toCharArray();
        if (s.indexOf(":") != -1) {
            String[] ss = s.split(":");
            if (ss.length <= 2) {
                if (ss[1].indexOf("end") != -1) {
                    ss[1] = ss[1].replace("end", (n - 1) + "");
                }
                ret = FactoryUtils.toIntArray1D(FactoryUtils.toFloatArray1D(vector(Integer.parseInt(ss[0]) * 1.0f, Integer.parseInt(ss[1]) * 1.0f)));
            } else {
                if (ss[2].indexOf("end") != -1) {
                    ss[2] = ss[2].replace("end", (n - 1) + "");
                }
                ret = FactoryUtils.toIntArray1D(FactoryUtils.toFloatArray1D(vector(Integer.parseInt(ss[0]) * 1.0f, Integer.parseInt(ss[1]) * 1.0f, Integer.parseInt(ss[2]) * 1.0f)));
            }
        } else {
            if (s.length() > 1) {
//                String[] str = str = s.split(chr[1] + "");
                String[] str;
                if (s.contains(" ")) {
                    str = s.split(" ");
                } else if (s.contains(",")) {
                    str = s.split(",");
                } else if (s.contains(";")) {
                    str = s.split(";");
                } else {
                    ret = new int[1];
                    ret[0] = Integer.parseInt(s);
                    return ret;
                }
                ret = new int[str.length];
                for (int i = 0; i < str.length; i++) {
                    ret[i] = Integer.parseInt(str[i]);
                }
            } else {
//                ret = new int[s.length()];
//                ret[0] = Integer.parseInt(s.charAt(0) + "");
                ret = new int[1];
                ret[0] = Integer.parseInt(s);
                return ret;
            }
        }
        return ret;
    }

    public static float[][] vector(float from, float to) {
        if (from > to) {
            throw new UnsupportedOperationException("from should be smaller than to, other wise use other constructor");
        }
        int n = (int) (Math.abs(to - from) + 1);
        float[][] ret = new float[1][n];
        for (int i = 0; i < n; i++) {
            ret[0][i] = from + i;
        }
        return transpose(ret);
    }

    public static float[][] vector(float from, float incr, float to) {
        if (from < to && incr < 0) {
            throw new UnsupportedOperationException("incr should be positive");
        }
        if (from > to && incr > 0) {
            throw new UnsupportedOperationException("incr should be negative");
        }
        float delta = Math.abs(to - from);
        int n = (int) (delta / incr);
        float[][] ret = new float[1][n];
        for (int i = 0; i < n; i++) {
            ret[0][i] = from + i * incr;
        }
        return transpose(ret);
    }

    public static float[][] columns(float[][] input, int[] cols) {
        float[][] yedek = transpose(input);
        float[][] d = new float[cols.length][yedek[0].length];
        for (int i = 0; i < cols.length; i++) {
            d[i] = yedek[cols[i]];
        }
        float[][] ret = transpose(d);
        return ret;
    }

    public static float[][] rows(float[][] input, int[] rows) {
        float[][] ret = new float[rows.length][input[0].length];
        for (int i = 0; i < rows.length; i++) {
            ret[i] = input[rows[i]];
        }
        return ret;
    }

    /**
     * Sutun bazlı toplama işlemi yapar mxn matrisinin tüm elemanlarını toplamak
     * istiyorsanız sumTotal veya iki defa sum çağırınız
     *
     * @param d:i
     * @return CMatrix
     */
    public static float[] sum(float[][] d) {
        float[] ret = new float[d[0].length];
        float[][] cm = transpose(clone(d));
        for (int i = 0; i < cm.length; i++) {
            ret[i] = FactoryUtils.sum(cm[i]);
        }
        return ret;
    }

    public static float sum(float[] d) {
        float ret = 0;
        for (int i = 0; i < d.length; i++) {
            ret += d[i];
        }
        return ret;
    }

    public static float sumTotal(float[][] d) {
        float ret = 0;
        int r = d.length;
        int c = d[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                ret += d[i][j];
            }
        }
        return ret;
    }

    /**
     * Column based inner multiplication similar to the sum method if you want
     * to get total product you should call prodTotal or prod(prod())
     *
     * @param d : input 2D float array
     * @return float[] : column vector
     */
    public static float[] prod(float[][] d) {
        float[] ret = new float[d[0].length];
        float[][] cm = transpose(clone(d));
        for (int i = 0; i < cm.length; i++) {
            ret[i] = FactoryUtils.prod(cm[i]);
        }
        return ret;
    }

    /**
     * get the column wise product of the content in the array
     *
     * @param d:i
     * @return float
     */
    public static float prod(float[] d) {
        float ret = 1;
        for (int i = 0; i < d.length; i++) {
            ret *= d[i];
        }
        return ret;
    }

    public static float prodTotal(float[][] d) {
        float ret = 1;
        int r = d.length;
        int c = d[0].length;
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < 10; j++) {
                ret *= d[i][j];
            }
        }
        return ret;
    }

    public static float[][] getMinMax(float[][] m) {
        int nr = m.length;
        float[][] ret = new float[nr][2];
        for (int i = 0; i < nr; i++) {
            ret[i][0] = FactoryUtils.getMinimum(m[i]);
            ret[i][1] = FactoryUtils.getMaximum(m[i]);
        }
        return ret;
    }

    public static float[][] dot(float[][] ret, float[][] cm) {
        return dot(ret, cm, Dimension.column);
    }

    public static float[][] dot(float[][] ret, float[][] cm, Dimension dm) {
        int nr = ret.length;
        int nc = ret[0].length;
        if (dm == Dimension.row) {
            float[][] d = new float[nc][1];
            for (int i = 0; i < nc; i++) {
                float sum = 0;
                for (int j = 0; j < nr; j++) {
                    sum += ret[j][i] * cm[j][i];
                }
                d[i][0] = sum;
            }
            return d;
        } else {
            float[][] d = new float[nr][1];
            for (int i = 0; i < nr; i++) {
                float sum = 0;
                for (int j = 0; j < nc; j++) {
                    sum += ret[i][j] * cm[i][j];
                }
                d[i][0] = sum;
            }
            return d;
        }
    }

    /**
     * column based variance calculation (N-1) based
     *
     * @param array:i
     * @return 2D float array
     */
    public static float[] var(float[][] array) {
        float[][] d = transpose(array);
        int nr = array.length;
        int nc = array[0].length;
        float[] ret = new float[nc];
        for (int i = 0; i < nc; i++) {
            ret[i] = FactoryUtils.var(d[i]);
        }
        return ret;
    }

    /**
     * column based standard deviation calculation (N-1) based
     *
     * @param array:i
     * @return 2D float array
     */
    public static float[] std(float[][] array) {
        float[][] d = transpose(array);
        int nr = array.length;
        int nc = array[0].length;
        float[] ret = new float[nc];
        for (int i = 0; i < nc; i++) {
            ret[i] = FactoryUtils.std(d[i]);
        }
        return ret;
    }

    /**
     * column based mean calculation based
     *
     * @param array:i
     * @return 2D float array
     */
    public static float[] mean(float[][] array) {
        float[][] d = transpose(array);
        int nr = array.length;
        int nc = array[0].length;
        float[] ret = new float[nc];
        for (int i = 0; i < nc; i++) {
            ret[i] = FactoryUtils.mean(d[i]);
        }
        return ret;
    }

    /**
     * column based covariance calculation based
     *
     * @param array:i
     * @return 2D float array
     */
    public static float[][] cov(float[][] array) {
        float[][] d = transpose(array);
        int nr = array.length;
        int nc = array[0].length;
        float[][] ret = new float[nc][nc];
        float m_i = 0;
        float m_j = 0;

        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nc; j++) {
                m_i = FactoryUtils.mean(d[i]);
                m_j = FactoryUtils.mean(d[j]);
                float sum = 0;
                for (int k = 0; k < nr; k++) {
                    sum += (d[i][k] - m_i) * (d[j][k] - m_j);
                }
                if (i != j) {
                    sum = sum / (nr - 1);
                } else {
                    sum = FactoryUtils.var(d[i]);
                }
                ret[i][j] = sum;
            }
        }
        return ret;
    }

    /**
     * column based correlation coefficient values
     *
     * @param array
     * @return 2D float array
     */
    public static float[][] corrcoef(float[][] array) {
        float[][] cov = cov(array);
        float[] std = std(array);
        int nr = cov.length;
        int nc = cov[0].length;
        float[][] corr = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                corr[i][j] = cov[i][j] / (std[i] * std[j]);
            }
        }
        return corr;
    }

    /**
     * Calculates pseudo inverse of the given matrix (2D float array)
     *
     * @param array:i
     * @return q
     */
    public static float[][] pinv(float[][] array) {
        Matrix a = new Matrix(FactoryUtils.toDoubleArray2D(array));
        a = pinv(a);
        return FactoryUtils.toFloatArray2D(a.getArray());
    }

    /**
     * Computes the Moore–Penrose pseudoinverse using the SVD method.
     *
     * Modified version of the original implementation by Kim van der Linde.
     */
    private static Matrix pinv(Matrix x) {
        int rows = x.getRowDimension();
        int cols = x.getColumnDimension();
        if (rows < cols) {
            Matrix result = pinv(x.transpose());
            if (result != null) {
                result = result.transpose();
            }
            return result;
        }
        SingularValueDecomposition svdX = new SingularValueDecomposition(x);
        if (svdX.rank() < 1) {
            return null;
        }
        float[] singularValues = FactoryUtils.toFloatArray1D(svdX.getSingularValues());
        float tol = Math.max(rows, cols) * singularValues[0] * MACHEPS;
        float[] singularValueReciprocals = new float[singularValues.length];
        for (int i = 0; i < singularValues.length; i++) {
            if (Math.abs(singularValues[i]) >= tol) {
                singularValueReciprocals[i] = 1.0f / singularValues[i];
            }
        }
        float[][] u = FactoryUtils.toFloatArray2D(svdX.getU().getArray());
        float[][] v = FactoryUtils.toFloatArray2D(svdX.getV().getArray());
        int min = Math.min(cols, u[0].length);
        float[][] inverse = new float[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < u.length; j++) {
                for (int k = 0; k < min; k++) {
                    inverse[i][j] += v[i][k] * singularValueReciprocals[k] * u[j][k];
                }
            }
        }
        return new Matrix(FactoryUtils.toDoubleArray2D(inverse));
    }

    public static float[][] sigmoid(float[][] array) {
        int nr = array.length;
        int nc = array[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (float) (1.0 / (1 + Math.exp(-array[i][j])));
            }
        }
        return ret;
    }

    public static float[][] sigmoid(float[][] array, float beta) {
        int nr = array.length;
        int nc = array[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (float) (1.0 / (1 + Math.exp(-beta * array[i][j])));
            }
        }
        return ret;
    }

    public static float[][] sigmoid(float[][] array, float alpha, float beta) {
        int nr = array.length;
        int nc = array[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (float) (1.0 / (1 + Math.exp(-alpha * (array[i][j] - beta))));
            }
        }
        return ret;
    }

    /**
     * Matlab compliants padding around the matrix with value padding amount is
     * determined by nr and nc
     *
     * @param d : original matrix
     * @param nr: padding number of rows (padding width as vertical)
     * @param nc: padding number of columns (padding width as horizontal)
     * @param val : padding value
     * @return q
     */
    public static float[][] padarray(float[][] d, int nr, int nc, float val) {
        int r = d.length + 2 * nr;
        int c = d[0].length + 2 * nc;
        float[][] ret = matrixFloatValue(r, c, val);
        for (int i = nr; i < r - nr; i++) {
            for (int j = nc; j < c - nc; j++) {
                ret[i][j] = d[i - nr][j - nc];
            }
        }
        return ret;
    }

    /**
     * Matlab compliants padding around the matrix with value padding amount is
     * determined by nr and nc
     *
     * @param d : original matrix
     * @param nr: padding number of rows (padding width as vertical)
     * @param nc: padding number of columns (padding width as horizontal)
     * @param val : padding value
     * @return q
     */
//    public static float[][] padarray(float[][] d, int nr, int nc, float val) {
//        int r = d.length + 2 * nr;
//        int c = d[0].length + 2 * nc;
//        float[][] ret = matrixFloatValue(r, c, val);
//        for (int i = nr; i < r - nr; i++) {
//            for (int j = nc; j < c - nc; j++) {
//                ret[i][j] = d[i - nr][j - nc];
//            }
//        }
//        return ret;
//    }
    /**
     * Matlab compliants padding around the matrix with value padding amount is
     * determined by nr and nc
     *
     * @param d : original matrix
     * @param nr: padding number of rows (padding width as vertical)
     * @param nc: padding number of columns (padding width as horizontal)
     * @param val : padding value
     * @return q
     */
    public static int[][] padarray(int[][] d, int nr, int nc, int val) {
        int r = d.length + 2 * nr;
        int c = d[0].length + 2 * nc;
        int[][] ret = matrixIntValue(r, c, val);
        for (int i = nr; i < r - nr; i++) {
            for (int j = nc; j < c - nc; j++) {
                ret[i][j] = d[i - nr][j - nc];
            }
        }
        return ret;
    }

    /**
     * Matlab compliants padding around the matrix with value padding amount is
     * determined by nr and nc
     *
     * @param d : original matrix
     * @param nr: padding number of rows (padding width as vertical)
     * @param nc: padding number of columns (padding width as horizontal)
     * @param val : padding value
     * @return q
     */
    public static long[][] padarray(long[][] d, int nr, int nc, long val) {
        int r = d.length + 2 * nr;
        int c = d[0].length + 2 * nc;
        long[][] ret = matrixLongValue(r, c, val);
        for (int i = nr; i < r - nr; i++) {
            for (int j = nc; j < c - nc; j++) {
                ret[i][j] = d[i - nr][j - nc];
            }
        }
        return ret;
    }

    /**
     * Matlab compliants padding around the matrix with value padding amount is
     * determined by nr and nc
     *
     * @param d : original matrix
     * @param nr: padding number of rows (padding width as vertical)
     * @param nc: padding number of columns (padding width as horizontal)
     * @param val : padding value
     * @return q
     */
    public static short[][] padarray(short[][] d, int nr, int nc, short val) {
        int r = d.length + 2 * nr;
        int c = d[0].length + 2 * nc;
        short[][] ret = matrixShortValue(r, c, val);
        for (int i = nr; i < r - nr; i++) {
            for (int j = nc; j < c - nc; j++) {
                ret[i][j] = d[i - nr][j - nc];
            }
        }
        return ret;
    }

    /**
     * Matlab compliants padding around the matrix with value padding amount is
     * determined by nr and nc
     *
     * @param d : original matrix
     * @param nr: padding number of rows (padding width as vertical)
     * @param nc: padding number of columns (padding width as horizontal)
     * @param val : padding value
     * @return q
     */
    public static byte[][] padarray(byte[][] d, int nr, int nc, byte val) {
        int r = d.length + 2 * nr;
        int c = d[0].length + 2 * nc;
        byte[][] ret = matrixByteValue(r, c, val);
        for (int i = nr; i < r - nr; i++) {
            for (int j = nc; j < c - nc; j++) {
                ret[i][j] = d[i - nr][j - nc];
            }
        }
        return ret;
    }

    /**
     * Matlab compliants padding around the matrix with value padding amount is
     * determined by nr and nc
     *
     * @param d : original matrix
     * @param nr: padding number of rows (padding width as vertical)
     * @param nc: padding number of columns (padding width as horizontal)
     * @param val : padding value
     * @return q
     */
    public static String[][] padarray(String[][] d, int nr, int nc, String val) {
        int r = d.length + 2 * nr;
        int c = d[0].length + 2 * nc;
        String[][] ret = matrixStringValue(r, c, val);
        for (int i = nr; i < r - nr; i++) {
            for (int j = nc; j < c - nc; j++) {
                ret[i][j] = d[i - nr][j - nc];
            }
        }
        return ret;
    }

    public static float[] getHistogram(int[] d, int n) {
        float[] ret = new float[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < d.length; j++) {
                if (d[j] == i) {
                    ret[i]++;
                }
            }
        }
        return ret;
    }

    public static float[] getHistogram(float[] d, int n) {
        float[] ret = new float[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < d.length; j++) {
                if (d[j] == i) {
                    ret[i]++;
                }
            }
        }
        return ret;
    }

    public static float[] getHistogram(float[][] d, int nBins) {
        float[] ret = new float[nBins];
        int nr = d.length;
        int nc = d[0].length;
        float min = FactoryUtils.getMinimum(d);
        float max = FactoryUtils.getMaximum(d);
        float delta = (max - min) / nBins;
        for (int i = 0; i < nBins; i++) {
            for (int j = 0; j < nr; j++) {
                for (int k = 0; k < nc; k++) {
                    if (d[j][k] >= i * delta + min && d[j][k] <= (i + 1) * delta + min) {
                        ret[i]++;
                    }
                }
            }
        }
        return ret;
    }

    public static float[] getHistogram(int[][] d, int n) {
        float[] ret = new float[n];
        int nr = d.length;
        int nc = d[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < nr; j++) {
                for (int k = 0; k < nc; k++) {
                    if (d[j][k] == i) {
                        ret[i]++;
                    }
                }
            }
        }
        return ret;
    }

//    public static float[] getHistogram(float[][] d, int n) {
//        float[] ret = new float[n];
//        int nr = d.length;
//        int nc = d[0].length;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < nr; j++) {
//                for (int k = 0; k < nc; k++) {
//                    if (d[j][k] == i) {
//                        ret[i]++;
//                    }
//                }
//            }
//        }
//        return ret;
//    }
    public static float[] getHistogram(short[] d, int n) {
        float[] ret = new float[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < d.length; j++) {
                if (d[j] == i) {
                    ret[i]++;
                }
            }
        }
        return ret;
    }

    public static float[][] getHistogram(int[][][] d, int n) {
        int n1 = d.length;
        int n2 = d[0].length;
        int n3 = d[0][0].length;
        float[][] ret = new float[n3 - 1][n];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n3; j++) {
                for (int k = 0; k < n1; k++) {
                    for (int m = 0; m < n2; m++) {
                        if (d[k][m][j] == i) {
                            ret[j - 1][i]++;
                        }
                    }
                }
            }
        }
        return ret;
    }

    public static float[][] getHistogram(float[][][] d, int n) {
        int n1 = d.length;
        int n2 = d[0].length;
        int n3 = d[0][0].length;
        float[][] ret = new float[n3 - 1][n];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n3; j++) {
                for (int k = 0; k < n1; k++) {
                    for (int m = 0; m < n2; m++) {
                        if (d[k][m][j] == i) {
                            ret[j - 1][i]++;
                        }
                    }
                }
            }
        }
        return ret;
    }

    public static float[][] reverseOrder(float[][] d) {
        float[][] temp = transpose(d);
        float[][] rt = clone(temp);
        int nr = rt.length;
        for (int i = 0; i < nr; i++) {
            rt[i] = reverseVector(temp[i]);
        }
        return transpose(rt);
    }

    private static float[] reverseVector(float[] d) {
        float[] rt = clone(d);
        int n = d.length;
        for (int i = 0; i < n; i++) {
            rt[i] = d[n - 1 - i];
        }
        return rt;
    }

    public static float[][] absDifference(float[][] d1, float[][] d2) {
        float[][] rt = clone(d1);
        int nr = rt.length;
        int nc = rt[0].length;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                rt[i][j] = Math.abs(d1[i][j] - d2[i][j]);
                if (rt[i][j] > 10) {
                    int a = 1;
                }
            }
        }
        return rt;
    }

    public static float[][] bitPlaneMSB(float[][] a) {
        float[][] ret = clone(a);
        int nr = a.length;
        int nc = a[0].length;
        float[][] d7 = bitPlane(a, 7);
        float[][] d6 = bitPlane(a, 6);
        float[][] d5 = bitPlane(a, 5);
        float[][] d4 = bitPlane(a, 4);
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
//                ret[i][j]=ret[i][j]-15;
                ret[i][j] = d7[i][j] + d6[i][j] + d5[i][j] + d4[i][j];
            }
        }
        return ret;
    }

    public static float[][] bitPlane(float[][] a, int planeNumber) {
        float[][] ret = clone(a);
        int nr = a.length;
        int nc = a[0].length;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                char c = FactoryUtils.formatBinary((int) ret[i][j]).toCharArray()[7 - planeNumber];
                char[] cd = "00000000".toCharArray();
                cd[7 - planeNumber] = c;
                ret[i][j] = Integer.parseInt(String.valueOf(cd), 2);
            }
        }
        return ret;
    }

    public static float[][] smoothColumns(float[][] array, int size) {
        float[][] d = clone(array);
        d = transpose(d);
        for (int i = 0; i < d.length; i++) {
            d[i] = smoothVector(d[i], size);
        }
        return transpose(d);
    }

    public static float[] smoothVector(float[] d, int size) {
        return convolveMean1D(d, size);
    }

    public static float[] convolveMean1D(float[] d, int size) {
        if (size % 2 == 0) {
            size++;
            System.out.println("kernel size adjusted to the odd due to the center point for convolution process");
        }
        float[] kernel = new float[size];
        int mp = size / 2;  //mid point
        float mean = 0;
        float[] ret = clone(d);
        for (int i = mp; i < d.length - (mp + 1); i++) {
            mean = 0;
            for (int j = -mp; j <= mp; j++) {
                mean += d[i + j];
            }
            mean = (int) (mean / size);
            ret[i] = mean;
        }
        return ret;
    }

    public static float[][] getMagnitude(float[][] array) {
        float[][] d = clone(array);
        d = transpose(d);
        float[][] ret = new float[d.length][1];
        for (int i = 0; i < ret.length; i++) {
            ret[i][0] = getMagnitude(d[i]);
        }
        return ret;
    }

    public static float getMagnitude(float[] d) {
        float ret = 0;
        float top = 0;
        for (int i = 0; i < d.length; i++) {
            top += d[i] * d[i];
        }
        ret = (float) Math.sqrt(top);
        return ret;
    }

    public static float[] log1p(float[] d) {
        float[] ret = clone(d);
        int length = ret.length;
        for (int i = 0; i < length; i++) {
            if (d[i] <= 0) {
                ret[i] = (float) Math.log1p(Math.abs(d[i]));
            } else {
                ret[i] = (float) Math.log(d[i]);
            }
        }
        return ret;
    }

    /**
     * Get Wavelet Transform Type based on the user input
     *
     * @param type:i
     * @return default is "haar"
     */
    public static Transform getTransform(String type) {
        Transform t = new Transform(new FastWaveletTransform(new Haar1()));
        switch (type) {
            case "haar": {
                t = new Transform(new FastWaveletTransform(new Haar1()));
                break;
            }
            case "db2": {
                t = new Transform(new FastWaveletTransform(new Daubechies2()));
                break;
            }
            case "db3": {
                t = new Transform(new FastWaveletTransform(new Daubechies3()));
                break;
            }
            case "db4": {
                t = new Transform(new FastWaveletTransform(new Daubechies4()));
                break;
            }
            case "db5": {
                t = new Transform(new FastWaveletTransform(new Daubechies5()));
                break;
            }
            case "db6": {
                t = new Transform(new FastWaveletTransform(new Daubechies6()));
                break;
            }
            case "db7": {
                t = new Transform(new FastWaveletTransform(new Daubechies7()));
                break;
            }
        }
        return t;
    }

    public static float[][] fwt_1D_decompose(float[] d, String type) {
        Transform t = getTransform(type);
        double[][] ret = t.decompose(FactoryUtils.toDoubleArray1D(d));
        return FactoryUtils.toFloatArray2D(ret);
    }

    public static float[] fwt_1D_forward(float[] d, String type, int depth) {
        Transform t = getTransform(type);
        double[] ret = t.forward(FactoryUtils.toDoubleArray1D(d), depth);
        return FactoryUtils.toFloatArray1D(ret);
    }

    public static float[] fwt_1D_reverse(float[] d, String type, int depth) {
        Transform t = getTransform(type);
        double[] ret = t.reverse(FactoryUtils.toDoubleArray1D(d), depth);
        return FactoryUtils.toFloatArray1D(ret);
    }

    public static float[] concatenateRows(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        float[] ret = new float[nr * nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[j + i * nc] = d[i][j];
            }
        }
        return ret;
    }

    public static float[] concatenateColumns(float[][] d) {
        return concatenateRows(FactoryMatrix.transpose(d));
    }

    public static float[][] cat(String type, float[][] f1, float[] f2) {
        float[][] ret = null;
        if (type.equals("horizontal")) {
            if (f1.length != f2.length) {
                FactoryUtils.showMessage("number of rows of both matrices should be same for horizontal concatenation");
                return f1;
            } else {
                int nr = f1.length;
                int nc = f1[0].length;
                ret = new float[nr][nc + 1];
                for (int i = 0; i < nr; i++) {
                    for (int j = 0; j < nc; j++) {
                        ret[i][j] = f1[i][j];
                    }
                    ret[i][nc] = f2[i];
                }
            }
        } else if (type.equals("vertical")) {
            if (f1[0].length != f2.length) {
                FactoryUtils.showMessage("number of columns of first matrix should be the same as number of rows of the seconf array for vertical concatenation");
                return f1;
            } else {
                int nr = f1.length;
                int nc = f1[0].length;
                ret = new float[nr + 1][nc];
                for (int i = 0; i < nr; i++) {
                    for (int j = 0; j < nc; j++) {
                        ret[i][j] = f1[i][j];
                    }
                }
                for (int j = 0; j < nc; j++) {
                    ret[nr][j] = f2[j];
                }
            }
        }
        return ret;
    }

    public static float[][] cat(String type, float[][] f1, float[][] f2) {
        float[][] ret = null;
        if (type.equals("horizontal")) {
            if (f1.length != f2.length) {
                FactoryUtils.showMessage("number of rows of both matrices should be same for horizontal concatenation");
                return f1;
            } else {
                int nr = f1.length;
                int nc = f1[0].length;
                int ncc = f2[0].length;
                ret = new float[nr][nc + ncc];
                for (int i = 0; i < nr; i++) {
                    for (int j = 0; j < nc; j++) {
                        ret[i][j] = f1[i][j];
                    }
                    for (int j = 0; j < ncc; j++) {
                        ret[i][nc + j] = f2[i][j];
                    }
                }
            }
        } else if (type.equals("vertical")) {
            if (f1[0].length != f2[0].length) {
                FactoryUtils.showMessage("number of columns of first matrix should be the same as number of rows of the seconf array for vertical concatenation");
                return f1;
            } else {
                int nr = f1.length;
                int nrr = f2.length;
                int nc = f1[0].length;
                ret = new float[nr + nrr][nc];
                for (int i = 0; i < nr; i++) {
                    for (int j = 0; j < nc; j++) {
                        ret[i][j] = f1[i][j];
                    }
                }
                for (int i = 0; i < nrr; i++) {
                    for (int j = 0; j < nc; j++) {
                        ret[i + nr][j] = f2[i][j];
                    }
                }
            }
        }
        return ret;
    }

    public static float[][] deleteColumn(float[][] f1, int index) {
        float[][] ret = null;
        if (index < 0 || index >= f1[0].length) {
            FactoryUtils.showMessage("erroneous column index");
            return f1;
        } else {
            float[][] f2 = transpose(f1);
            ret = new float[f1[0].length - 1][f1.length];
            for (int i = 0; i < index; i++) {
                ret[i] = clone(f2[i]);
            }
            for (int i = index; i < ret.length; i++) {
                ret[i] = clone(f2[i + 1]);
            }
        }
        return transpose(ret);
    }

    public static float[][] deleteRow(float[][] f1, int index) {
        float[][] ret = null;
        if (index < 0 || index >= f1.length) {
            FactoryUtils.showMessage("erroneous column index");
            return f1;
        } else {
            ret = new float[f1.length][f1[0].length - 1];
            for (int i = 0; i < index; i++) {
                ret[i] = clone(f1[i]);
            }
            for (int i = index; i < f1.length; i++) {
                ret[i] = clone(f1[i + 1]);
            }
        }
        return ret;
    }

    public static float[][] deleteRows(float[][] f1, int from, int to) {
        float[][] ret = null;
        if (from < 0 || from >= f1.length || from >= to || to < 0 || to >= f1.length) {
            FactoryUtils.showMessage("erroneous column index");
            return f1;
        } else {
            int delta = to - from;
            List lst1 = FactoryUtils.toListFrom2DArray(f1);
            List lst2 = FactoryUtils.toListFrom2DArray(f1);
            for (int i = from; i < to; i++) {
                lst1.remove(lst2.get(i));
            }
            ret = FactoryUtils.toFloatArray2D(lst1);
        }
        return ret;
    }

    public static float[][] filterMembershipFunction(float[][] d, float[] f) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (int) Math.round(f[(int) d[i][j]] * d[i][j]);
            }
        }
        return ret;
    }

    public static float[][] highPass(float[][] d1, float[][] d2) {
        int nr = d1.length;
        int nc = d2[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (d1[i][j] >= d2[i][j]) {
                    ret[i][j] = d1[i][j];
                } else {
                    ret[i][j] = d2[i][j];
                }
            }
        }
        return ret;
    }

    public static float[][] lowPass(float[][] d1, float[][] d2) {
        int nr = d1.length;
        int nc = d2[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (d1[i][j] <= d2[i][j]) {
                    ret[i][j] = d1[i][j];
                } else {
                    ret[i][j] = d2[i][j];
                }
            }
        }
        return ret;
    }

    public static float[] filterMedian1D(float[] d, int window_size) {
        int n = d.length;
        float[] ret = clone(d);
        int center = window_size / 2;
        float[] dm = new float[window_size];
        for (int i = 0; i < center; i++) {
            dm = crop1D(d, i, center);
            Arrays.sort(dm);
            ret[i] = dm[0];
        }
        for (int i = center; i < n - center; i++) {
            dm = crop1D(d, i - center, window_size);
            Arrays.sort(dm);
            ret[i] = dm[center];
        }
        for (int i = n - center; i < n; i++) {
            dm = crop1D(d, i - center, center);
            Arrays.sort(dm);
            ret[i] = dm[center - 1];
        }
        return ret;
    }

    private static float[] filterMedian1DLossy(float[] d, int window_size) {
        int n = d.length;
        float[] ret = clone(d);
        int center = window_size / 2;
        float[] dm = new float[window_size];
        for (int i = center; i < n - center; i++) {
            dm = crop1D(d, i - center, window_size);
            Arrays.sort(dm);
            ret[i] = dm[center];
        }
        return ret;
    }

    public static float[] crop1D(float[] d, int from, int size) {
        float[] ret = new float[size];
        for (int i = from; i < from + size; i++) {
            ret[i - from] = d[i];
        }
        return ret;
    }

    public static float[] filterMean1D(float[] d, int window_size) {
        int n = d.length;
        float[] ret = clone(d);
        int center = window_size / 2;
        float[] dm = new float[window_size];
        float temp = 0;
        for (int i = 0; i < center; i++) {
            dm = crop1D(d, i, center);
            temp = FactoryUtils.mean(dm);
            ret[i] = temp;
        }
        for (int i = center; i < n - center; i++) {
            dm = crop1D(d, i - center, window_size);
            temp = FactoryUtils.mean(dm);
            ret[i] = temp;
        }
        for (int i = n - center; i < n; i++) {
            dm = crop1D(d, i - center, center);
            temp = FactoryUtils.mean(dm);
            ret[i] = temp;
        }
        return ret;
    }

    private static float[] filterMean1DLossy(float[] d, int window_size) {
        int n = d.length;
        float[] ret = clone(d);
        int center = window_size / 2;
        float[] dm = new float[window_size];
        float temp = 0;
        for (int i = center; i < n - center; i++) {
            dm = crop1D(d, i - center, window_size);
            temp = FactoryUtils.mean(dm);
            ret[i] = temp;
        }
        return ret;
    }

    public static float[][] sort(float[][] array, String dim, String order) {
        float[][] ret = FactoryMatrix.clone(array);
        if (order.equals("ascend") && dim.equals("column")) {
            ret = FactoryMatrix.transpose(ret);
            int nr = ret.length;
            for (int i = 0; i < nr; i++) {
                ret[i] = FactoryUtils.sortArrayAscend(ret[i]);
            }
            ret = FactoryMatrix.transpose(ret);
        } else if (order.equals("ascend") && dim.equals("row")) {
            int nr = ret.length;
            for (int i = 0; i < nr; i++) {
                ret[i] = FactoryUtils.sortArrayAscend(ret[i]);
            }
        } else if (order.equals("descend") && dim.equals("column")) {
            ret = FactoryMatrix.transpose(ret);
            int nr = ret.length;
            for (int i = 0; i < nr; i++) {
                ret[i] = FactoryUtils.sortArrayDescend(ret[i]);
            }
            ret = FactoryMatrix.transpose(ret);
        } else if (order.equals("descend") && dim.equals("row")) {
            int nr = ret.length;
            for (int i = 0; i < nr; i++) {
                ret[i] = FactoryUtils.sortArrayDescend(ret[i]);
            }
        }
        return ret;
    }

    public static float[][] clone(float[][] d, int nr, int nc) {
        float[][] ret = new float[nr][nc];
        int delta_row = nr - d.length;
        int delta_col = nc - d[0].length;
        if (delta_row == 0 && delta_col == 0) {
            return clone(d);
        } else if (delta_row == 0 && delta_col > 0) {
            for (int i = 0; i < nr; i++) {
                System.arraycopy(d[i], 0, ret[i], 0, nc - delta_col);
            }
        } else if (delta_col == 0 && delta_row > 0) {
            for (int i = 0; i < nr - delta_row; i++) {
                System.arraycopy(d[i], 0, ret[i], 0, nc);
            }
        } else if (delta_row > 0 && delta_col > 0) {
            for (int i = 0; i < nr - delta_row; i++) {
                System.arraycopy(d[i], 0, ret[i], 0, nc - delta_col);
            }
        } else if ((delta_row == 0 && delta_col < 0) || (delta_col == 0 && delta_row < 0) || (delta_col < 0 && delta_row < 0)) {
            for (int i = 0; i < nr; i++) {
                System.arraycopy(d[i], 0, ret[i], 0, nc);
            }
        }
        return ret;
    }

    public static float[][] catHorizontal(float[][] d, int... val) {
        int ek = val.length;
        float[][] ret = clone(d, d.length, d[0].length + ek);
        int nr = ret.length;
        int nc = ret[0].length;
        int q = d[0].length;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < ek; j++) {
                ret[i][q + j] = val[j];
            }
        }
        return ret;
    }

    /**
     * Concatenates two float vectors horizontally [A,B]
     *
     * @param c1:i
     * @param c2:i
     * @return q
     */
    public static float[][] catHorizontalMatrix(float[] c1, float[] c2) {
        if (c1.length != c2.length) {
            System.err.println("array dimension mismatch by FactoryMatrix.catHorizontal(float[],float[])");
            return new float[1][1];
        }
        int n = c1.length;
        float[][] ret = new float[2][n];
        ret[0] = FactoryMatrix.clone(c1);
        ret[1] = FactoryMatrix.clone(c2);
        ret = FactoryMatrix.transpose(ret);
        return ret;
    }

    /**
     * Concatenates two float vectors vertically [A;B] which produce single
     * column matrix
     *
     * @param c1:i
     * @param c2:i
     * @return q
     */
    public static float[][] catVerticalMatrix(float[] c1, float[] c2) {
        int n = c1.length + c2.length;
        float[][] ret = new float[n][1];
        for (int i = 0; i < c1.length; i++) {
            ret[i][0] = c1[i];
        }
        for (int i = c1.length; i < n; i++) {
            ret[i][0] = c2[i - c1.length];
        }
        return ret;
    }

    public static float[] cat(float[] c1, float[] c2) {
        int n = c1.length + c2.length;
        float[] ret = new float[n];
        for (int i = 0; i < c1.length; i++) {
            ret[i] = c1[i];
        }
        for (int i = c1.length; i < n; i++) {
            ret[i] = c2[i - c1.length];
        }
        return ret;
    }

    public static float[][] catVertical(float[][] d, int... val) {
        int ek = val.length;
        float[][] ret = clone(d, d.length + ek, d[0].length);
        int nr = ret.length;
        int nc = ret[0].length;
        int q = d.length;
        for (int i = 0; i < ek; i++) {
            for (int j = 0; j < nc; j++) {
                ret[q + i][j] = val[i];
            }
        }
        return ret;
    }

    public static float[] generateRandomNormal_1D(int n, float mean, float var) {
        SecureRandom r = new SecureRandom();
        float[] d = new float[n];
        for (int i = 0; i < d.length; i++) {
            d[i] = (float) (mean + r.nextGaussian() * var);
        }
        return d;
    }

    public static float[][] generateRandomNormal_2D(int nr, int nc, float mean, float var) {
        SecureRandom r = new SecureRandom();
        float[][] d = new float[nc][nr];
        for (int i = 0; i < nc; i++) {
            for (int j = 0; j < nr; j++) {
                d[i][j] = (float) (mean + r.nextGaussian() * var);
            }
        }
        return transpose(d);
    }

    public static float[][] fillRandMatrix(float[][] d) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = r.nextFloat();
            }
        }
        return d;
    }

    public static float[][] fillRandMatrix(float[][] d, Random rnd) {
        int nr = d.length;
        int nc = d[0].length;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
//                d[i][j] = rnd.nextFloat();
                d[i][j] = (float) Math.random();
            }
        }
        return d;
    }

    public static float[][] fillRandMatrixWithSeed(float[][] d, int seed) {
        int nr = d.length;
        int nc = d[0].length;
        SplittableRandom r = new SplittableRandom(seed);
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                d[i][j] = (float) r.nextDouble();
            }
        }
        return d;
    }

    public static float[][] fillRandMatrix(float[][] d, int max) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = r.nextFloat() * max;
            }
        }
        return d;
    }

    public static float[][] fillRandMatrix(float[][] d, int max, Random rnd) {
//        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = rnd.nextFloat() * max;
            }
        }
        return d;
    }

    public static float[][] fillRandMatrix(float[][] d, float max, Random rnd) {
//        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = rnd.nextFloat() * max;
            }
        }
        return d;
    }

    public static float[][] fillRandMatrixWithSeed(float[][] d, float max, int seed) {
        SplittableRandom rnd = new SplittableRandom(seed);
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (rnd.nextDouble() * max);
            }
        }
        return d;
    }

    public static float[][] fillRandMatrix(float[][] d, float max) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = r.nextFloat() * max;
            }
        }
        return d;
    }

    public static float[][] fillRandMatrix(float[][] d, int min, int max, Random rnd) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = min + rnd.nextFloat() * (max - min);
            }
        }
        return d;
    }

    public static float[][] fillRandMatrixTimeSeries(float[][] d, float min, float max, Random rnd) {
        int nr = d.length;
        int nc = d[0].length;
        d = fillRandMatrix(d, min, max, rnd);
        float[][] ret = new float[nr][nc];
        for (int j = 0; j < nc; j++) {
            ret[0][j] = d[0][j];
            for (int i = 1; i < nr; i++) {
                ret[i][j] = ret[i - 1][j] + d[i][j];
            }
        }
        return ret;
    }

    public static float[][] fillRandMatrix(float[][] d, int min, int max) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = min + r.nextFloat() * (max - min);
            }
        }
        return d;
    }

    public static float[][] fillRandMatrix(float[][] d, float min, float max, Random rnd) {
//        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = min + rnd.nextFloat() * (max - min);
            }
        }
        return d;
    }

    public static float[][] fillRandMatrixWithSeed(float[][] d, float min, float max, int seed) {
        SplittableRandom rnd = new SplittableRandom(seed);
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (min + rnd.nextDouble() * (max - min));
            }
        }
        return d;
    }

    public static float[][] fillRandMatrix(float[][] d, float min, float max) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = min + r.nextFloat() * (max - min);
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, Random rnd) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) rnd.nextGaussian();
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) r.nextGaussian();
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, int max, Random rnd) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (rnd.nextGaussian() * max);
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, int max) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (r.nextGaussian() * max);
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, float max, Random rnd) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (rnd.nextGaussian() * max);
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, float max) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (r.nextGaussian() * max);
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, int min, int max, Random rnd) {
        float q = 0;
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                q = (float) rnd.nextGaussian();
                d[i][j] = min + q * (max - min);
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, int min, int max) {
        SecureRandom r = new SecureRandom();
        float q = 0;
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                q = (float) r.nextGaussian();
                d[i][j] = min + q * (max - min);
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, float min, float max, Random rnd) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (min + rnd.nextGaussian() * (max - min));
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrix(float[][] d, float min, float max) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (min + r.nextGaussian() * (max - min));
            }
        }
        return d;
    }

    public static float[][] fillRandNormalMatrixMeanVar(float[][] d, float mean, float var, Random rnd) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (mean + rnd.nextGaussian() * var);
            }
        }
        return d;
    }

    public static float[] fillRandNormal(float[] d, float mean, float var, Random rnd) {
        int nr = d.length;
        for (int i = 0; i < nr; i++) {
            d[i] = (float) (mean + rnd.nextGaussian() * var);
        }
        return d;
    }

    public static float[] fillRandNormal(int nr, float mean, float var, Random rnd) {
        float[] d = new float[nr];
        for (int i = 0; i < nr; i++) {
            d[i] = (float) (mean + rnd.nextGaussian() * var);
        }
        return d;
    }

    public static float[] zeros(int nr) {
        float[] ret = new float[nr];
        return ret;
    }

    public static float[] ones(int nr) {
        return values(nr, 1);
    }

    public static float[] values(int nr, float val) {
        float[] ret = new float[nr];
        for (int i = 0; i < nr; i++) {
            ret[i] = val;
        }
        return ret;
    }

    public static float[][] zeros(int nr, int nc) {
        float[][] ret = new float[nr][nc];
        return ret;
    }

    public static float[][] ones(int nr, int nc) {
        return values(nr, nc, 1);
    }

    public static float[][] values(int nr, int nc, float val) {
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = val;
            }
        }
        return ret;
    }

    public static float[][] fillRandNormalMatrixMeanVar(float[][] d, float mean, float var) {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = (float) (mean + r.nextGaussian() * var);
            }
        }
        return d;
    }

    public static int[] rand(int n, int scale, Random rnd) {
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[i] = rnd.nextInt(scale);
        }
        return ret;
    }

    public static int[] rand(int n, int scale) {
        SecureRandom r = new SecureRandom();
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[i] = r.nextInt(scale);
        }
        return ret;
    }

    public static float[][] meshGridX(float from, float to, int numberOf) {
        float[][] ret = new float[numberOf][numberOf];
//        float incr = (to - from + 1) / numberOf;
        float incr = (to - from) / (numberOf - 1);
        for (int i = 0; i < numberOf; i++) {
            for (int j = 0; j < numberOf; j++) {
                ret[i][j] = from + j * incr;
            }
        }
        return ret;
    }

    public static float[][] meshGridY(float from, float to, int numberOf) {
        return transpose(meshGridX(from, to, numberOf));
    }

    public static float[][] meshGridX(float[][] array, float from, float to) {
        int nr = array.length;
        int nc = array[0].length;
        float incr = (to - from) / (nc - 1);
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                array[i][j] = from + j * incr;
            }
        }
        return array;
    }

    public static float[][] meshGridY(float[][] array, float from, float to) {
        return transpose(meshGridX(array, from, to));
    }

    public static float[][] meshGridIterateForward(float[][] array) {
        int nr = array.length;
        int nc = array[0].length;
        float[][] ret = clone(array);
        int mid = nc / 2;
        float[] left = new float[mid];
        float[] right = new float[mid];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < mid - 1; j++) {
                ret[i][j] = array[i][j + 1];
            }
            ret[i][mid - 1] = array[i][0];
            //ret[i][mid]=array[i][1];
            ret[i][mid] = 0;
            ret[i][mid + 1] = array[i][nc - 1];

            for (int j = mid + 2; j < nc; j++) {
                ret[i][j] = array[i][j - 1];
            }
        }
        return ret;
    }

    public static float[][] randMatrix(int dim) {
        float[][] ret = new float[dim][dim];
        SplittableRandom rnd = new SplittableRandom();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                ret[i][j] = (float) rnd.nextDouble();
            }
        }
        return ret;
    }

    public static float[][] randMatrix(int nr, int nc) {
        float[][] ret = new float[nr][nc];
        SplittableRandom rnd = new SplittableRandom();
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (float) rnd.nextDouble();
            }
        }
        return ret;
    }

    public static float[][] randMatrix(int nr, int nc, float bound) {
        float[][] ret = new float[nr][nc];
        SplittableRandom rnd = new SplittableRandom();
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (float) rnd.nextDouble(bound);
            }
        }
        return ret;
    }

    public static float[][] randMatrix(int nr, int nc, float from, float to) {
        float[][] ret = new float[nr][nc];
        SplittableRandom rnd = new SplittableRandom();
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (float) rnd.nextDouble(from, to);
            }
        }
        return ret;
    }

    public static float[][] make_blobs(int n_samples, int n_features, int n_groups, int mean_scale, int var_scale, Random random) {
        if (n_samples < n_groups) {
            FactoryUtils.showMessage("number of samples can't be less than number of groups");
            return null;
        }
        float[][] ret = new float[n_samples][n_features + 1];
        for (int i = 0; i < n_groups; i++) {
            float[][] cm = new float[n_samples][1];

            for (int j = 0; j < n_features; j++) {
                float mean = (float) (Math.random() * mean_scale - mean_scale / 2);
                float var = (float) (Math.sqrt(var_scale) + Math.random() * var_scale);
                float[] f = fillRandNormal(n_samples, mean, var, random);
                cm = cat("horizontal", cm, f);
            }

            cm = deleteColumn(cm, 0);
            cm = cat("horizontal", cm, values(n_samples, i));
            ret = cat("vertical", ret, cm);
        }
        ret = deleteRows(ret, 0, n_samples);
        ret = shuffleRows(ret);
        return ret;
    }

    public static float[] getLastColumn(float[][] f) {
        float[] ret = new float[f.length];
        int nr = f.length;
        int nc = f[0].length;
        for (int i = 0; i < nr; i++) {
            ret[i] = f[i][nc - 1];
        }
        return ret;
    }

    public static float[] getFirstColumn(float[][] f) {
        float[] ret = new float[f.length];
        int nr = f.length;
        int nc = f[0].length;
        for (int i = 0; i < nr; i++) {
            ret[i] = f[i][0];
        }
        return ret;
    }

    public static float[] getLastRow(float[][] f) {
        return f[f.length - 1];
    }

    public static float[] getFirstRow(float[][] f) {
        return f[0];
    }

    public static float[][] inverseLog(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (float) Math.pow(10, Math.log(d[i][j] + 1));
            }
        }
        return ret;
    }

    public static float[][] inversePower(float x, float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (float) Math.pow(x, d[i][j]);
            }
        }
        return ret;
    }

    public static float[][] addClassLabel(float[][] cm, float cl) {
        int nr = cm.length;
        int nc = cm[0].length;
        float[][] ret = new float[nr][nc + 1];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = cm[i][j];
            }
            ret[i][nc] = cl;
        }
        return ret;
    }

    public static float[][] reshape(float[][] d, int r, int c) {
        if (d.length * d[0].length != r * c) {
            System.err.println("size mismatch");
            return d;
        }
        float[][] ret = new float[r][c];
        float[] a = toFloatArray1D(d);
        int k = 0;
        for (int j = 0; j < c; j++) {
            for (int i = 0; i < r; i++) {
                ret[i][j] = a[k++];
            }
        }
        return ret;
    }

    public static Object[][] reshape(Object[][] d, int r, int c) {
        if (d.length * d[0].length != r * c) {
            System.err.println("size mismatch");
            return d;
        }
        Object[][] ret = new Object[r][c];
        Object[] a = toFloatArray1D(d);
        int k = 0;
        for (int j = 0; j < c; j++) {
            for (int i = 0; i < r; i++) {
                ret[i][j] = a[k++];
            }
        }
        return ret;
    }

    public static float[][] reshape(float[] d, int r, int c) {
        float[][] ret = new float[r][c];
        if (d.length != r * c) {
            showMessage("size mismatch");
            return ret;
        }
        int k = 0;
        for (int j = 0; j < c; j++) {
            for (int i = 0; i < r; i++) {
                ret[i][j] = d[k++];
            }
        }
        return ret;
    }

    public static float[][] reshapeBasedOnRows(float[] d, int r, int c) {
        float[][] ret = new float[r][c];
        if (d.length != r * c) {
            showMessage("size mismatch");
            return ret;
        }
        int k = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                ret[i][j] = d[k++];
            }
        }
        return ret;
    }

    public static Object[][] reshape(Object[] d, int r, int c) {
        Object[][] ret = new Object[r][c];
        if (d.length != r * c) {
            showMessage("size mismatch");
            return ret;
        }
        int k = 0;
        for (int j = 0; j < c; j++) {
            for (int i = 0; i < r; i++) {
                ret[i][j] = d[k++];
            }
        }
        return ret;
    }

    public static float[][] perlinNoise(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;

        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = PerlinNoise.noise(i * 0.1f, j * 0.1f, 0);
            }
        }
        return ret;
    }

    public static float[][] perlinNoise(float[][] d, float scale) {
        int nr = d.length;
        int nc = d[0].length;

        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = PerlinNoise.noise(i * scale, j * scale, 1.44f);
            }
        }
        return ret;
    }

    public static float[][] convolve(float[][] d, float[][] kernel) {
        int mid = kernel.length / 2;
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr - 2 * mid][nc - 2 * mid];
        for (int i = mid; i < nr - mid; i++) {
            for (int j = mid; j < nc - mid; j++) {
                float t = 0;
                for (int k = 0; k < kernel.length; k++) {
                    for (int l = 0; l < kernel[0].length; l++) {
                        t += kernel[k][l] * d[i - mid + k][j - mid + l];
                    }
                    ret[i - mid][j - mid] = t / (kernel.length * kernel[0].length);
                }
            }
        }
        return ret;
    }

    public static float[][] convolveLBP(float[][] d, int size) {
        int mid = size / 2;
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr - 2 * mid][nc - 2 * mid];
        for (int i = mid; i < nr- 2 * mid; i++) {
            for (int j = mid; j < nc- 2 * mid; j++) {
                String str = "";
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        if (d[i][j] > d[i-mid + k][j-mid + l]) {
                            str += "1";
                        } else {
                            str += "0";
                        }
                    }
                }
                int r = Integer.parseInt(str, 2);
                //System.out.println("r = " + r);
                ret[i - mid][j - mid] = r;
            }
        }
        return ret;
    }

    public static float[][] applyFunction(float[][] d, float[] f) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr][nc];
        int val = 0;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                val = (int) d[i][j];
                ret[i][j] = f[val];
            }
        }
        return ret;
    }

    public static float[][] range2D(float from_inclusive, float to_exclusive, float step, int nrows) {
        int delta = (int) ((to_exclusive - from_inclusive) / step);
        float[][] ret = new float[nrows][delta];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < delta; j++) {
                ret[i][j] = from_inclusive + j * step;
            }
            //ret[i][0] = from_inclusive + i * step;
        }

        return ret;
    }

    public static float[][] range2D(float from_inclusive, float to_exclusive, int nrows) {
        float step = 1;
        int delta = (int) ((to_exclusive - from_inclusive) / step);
        float[][] ret = new float[nrows][delta];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < delta; j++) {
                ret[i][j] = from_inclusive + j * step;
            }
            //ret[i][0] = from_inclusive + i * step;
        }

        return ret;
    }

    public static float[][] range(float from_inclusive, float to_exclusive, float step, int ncols) {
        int delta = (int) ((to_exclusive - from_inclusive) / step);
        float[][] ret = new float[delta][ncols];
        for (int i = 0; i < delta; i++) {
            for (int j = 0; j < ncols; j++) {
                ret[i][j] = from_inclusive + j * step;
            }
            ret[i][0] = from_inclusive + i * step;
        }
        return ret;
    }

    public static String[] toStringArray(char[] lst) {
        String[] st = new String[lst.length];
        for (int i = 0; i < lst.length; i++) {
            st[i] = String.valueOf(lst[i]);
        }
        return st;
    }

    public static float[][] eye(int n, float val) {
        float[][] ret = new float[n][n];
        for (int i = 0; i < n; i++) {
            ret[i][i] = val;
        }
        return ret;
    }

    public static float[][] shuffleRows(float[][] d, int[] shuffleIndexes) {
        int nr = d.length;
        float[][] ret = new float[nr][d[0].length];
        int[] ind = randPermInt(d.length);
        for (int i = 0; i < ind.length; i++) {
            shuffleIndexes[i] = ind[i];
        }
        for (int i = 0; i < nr; i++) {
            ret[i] = d[shuffleIndexes[i]];
        }
        return ret;
    }

    public static float[][] shuffleRowsAndColumns(float[][] d, int[] shuffleIndexes) {
        int nr = d.length;
        int nc = d[0].length;
        int[] ind = randPermInt(nr * nc);
        int m = nr * nc;
        for (int i = 0; i < m; i++) {
            shuffleIndexes[i] = ind[i];
        }
        float[] p = FactoryUtils.toFloatArray1D(d);
        float[] q = new float[m];
        for (int i = 0; i < m; i++) {
            q[i] = p[shuffleIndexes[i]];
        }
        float[][] ret = FactoryMatrix.reshape(q, nr, nc);
        return ret;
    }

    public static float[][] deShuffleRows(float[][] d, int[] shuffleIndexes) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            ret[shuffleIndexes[i]] = FactoryUtils.clone(d[i]);
        }
        return ret;

    }

    public static float[][] deShuffleRowsAndColumns(float[][] d, int[] shuffleIndexes) {
        int nr = d.length;
        int nc = d[0].length;
        int size = nr * nc;
        float[] p = FactoryUtils.toFloatArray1D(d);
        float[] q = new float[size];
        for (int i = 0; i < size; i++) {
            q[shuffleIndexes[i]] = p[i];
        }
        float[][] ret = FactoryMatrix.reshape(q, nr, nc);
        return ret;

    }

    public static float[][] setValue(float[][] d, String p1, String p2, float val) {
        float[][] ret = clone(d);
        p1 = p1.replace("[", "").replace("]", "").replace("(", "").replace(")", "");
        p2 = p2.replace("[", "").replace("]", "").replace("(", "").replace(")", "");
        int nr = d.length;
        int nc = d[0].length;

        //eğer komut tüm matrisi ilgilendir diyorsa
        if (p1.equals(":") && p2.equals(":")) {
            for (int i = 0; i < nr; i++) {
                for (int j = 0; j < nc; j++) {
                    ret[i][j] = val;
                }
            }
            return ret;
        } //eğer komut tüm satırları ama belirli sutunları ilgilendiriyorsa
        else if (p1.equals(":") && !p2.equals(":")) {
            if (p2.length() == 1) {
                int c = Integer.parseInt(p2);
                if (c < nc) {
                    for (int i = 0; i < nr; i++) {
                        ret[i][c] = val;
                    }
                    return ret;
                }
            } else {
                float[] p = FactoryUtils.resolveParam(p2, nc);
                for (int i = 0; i < nr; i++) {
                    for (int j = 0; j < p.length; j++) {
                        ret[i][(int) p[j]] = val;
                    }
                }
            }
            //eğer komut belirli satırları ama tüm sutunları ilgilendiriyor ise
        } else if (p2.equals(":") && !p1.equals(":")) {
            if (p1.length() == 1) {
                int r = Integer.parseInt(p2);
                if (r < nr) {
                    for (int i = 0; i < nc; i++) {
                        ret[r][i] = val;
                    }
                    return ret;
                }
            } else {
                float[] p = FactoryUtils.resolveParam(p1, nr);
                for (int i = 0; i < p.length; i++) {
                    for (int j = 0; j < nc; j++) {
                        ret[(int) p[i]][j] = val;
                    }
                }
            }
        } else {
            float[] pr = FactoryUtils.resolveParam(p1, nr);
            float[] pc = FactoryUtils.resolveParam(p2, nc);
            for (int i = 0; i < pr.length; i++) {
                for (int j = 0; j < pc.length; j++) {
                    ret[(int) pr[i]][(int) pc[j]] = val;
                }
            }
        }
        return ret;
    }

    public static float[][] cmd(float[][] d, String p1, String p2) {
        float[][] ret = clone(d);
        p1 = p1.replace("[", "").replace("]", "").replace("(", "").replace(")", "");
        p2 = p2.replace("[", "").replace("]", "").replace("(", "").replace(")", "");
        int nr = d.length;
        int nc = d[0].length;

        //eğer komut tüm matrisi ilgilendir diyorsa tüm matrisi ger gönder
        if (p1.equals(":") && p2.equals(":")) {
            return ret;
        } //eğer komut tüm satırları ama belirli sutunları ilgilendiriyorsa
        else if (p1.equals(":") && !p2.equals(":")) {
            if (p2.length() == 1) {
                int c = Integer.parseInt(p2);
                if (c < nc) {
                    ret = columns(ret, new int[]{c});
                    return ret;
                }
            } else {
                float[] p = FactoryUtils.resolveParam(p2, nc);
                ret = columns(ret, FactoryUtils.toIntArray1D(p));
                return ret;
            }
            //eğer komut belirli satırları ama tüm sutunları ilgilendiriyor ise
        } else if (p2.equals(":") && !p1.equals(":")) {
            if (p1.length() == 1) {
                int r = Integer.parseInt(p1);
                if (r < nr) {
                    ret = rows(ret, new int[]{r});
                    return ret;
                }
            } else {
                float[] p = FactoryUtils.resolveParam(p1, nr);
                ret = rows(ret, FactoryUtils.toIntArray1D(p));
                return ret;
            }
        } //her iki parametre de birer matris aralığı belirtiyor ise
        //ilkönce matrisin ilgili satırlarını slice'la sonra sutunlarına geç
        else {
            int[] pr = FactoryUtils.toIntArray1D(FactoryUtils.resolveParam(p1, nr));
            int[] pc = FactoryUtils.toIntArray1D(FactoryUtils.resolveParam(p2, nc));
            ret = rows(ret, pr);
            ret = columns(ret, pc);
            return ret;
        }
        return ret;
    }

    private static float[][] getColumns(float[][] d, int[] indices) {
        int nr = d.length;
        float[][] ret = new float[nr][indices.length];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < indices.length; j++) {
                ret[i][j] = d[i][indices[j]];
            }
        }
        return ret;
    }

    private static float[][] getRows(float[][] d, int[] indices) {
        int nr = d.length;
        float[][] ret = new float[indices.length][d[0].length];
        for (int i = 0; i < indices.length; i++) {
            ret[i] = d[indices[i]];
        }
        return ret;
    }

    public static float[][] resize(float[][] d, int n) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr * n][nc * n];
        int q = 0, p = 0;
        for (int i = 0; i < nr; i++) {
            for (int m = 0; m < n; m++) {
                for (int j = 0; j < nc; j++) {
                    for (int k = 0; k < n; k++) {
                        ret[q][p] = d[i][j];
                        p++;
                    }
                }
                q++;
                p = 0;
            }
        }
        return ret;
    }

    public static float[][] dotProduct(float[][] p1, float[][] p2) {
        float[][] ret = new float[p1.length][p2[0].length];
        int nr = ret.length;
        int nc = ret[0].length;
        float[][] pt = clone(p2);
        pt = transpose(pt);

        for (int i = 0; i < nr; i++) {
            float sum = 0;
            for (int j = 0; j < nc; j++) {
                ret[i][j] = dot(p1[i], pt[j]);
            }
        }

        return ret;
    }

    public static float dot(float[] v1, float[] v2) {
        float ret = 0;
        int nr = v1.length;
        for (int i = 0; i < nr; i++) {
            ret += v1[i] * v2[i];
        }
        return ret;
    }

    public static int[][] ceil(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        int[][] ret = new int[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (int) Math.ceil(d[i][j]);
            }
        }
        return ret;
    }

    public static int[][] floor(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        int[][] ret = new int[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (int) Math.floor(d[i][j]);
            }
        }
        return ret;
    }

    public static int[][] mod(float[][] d, int n) {
        int nr = d.length;
        int nc = d[0].length;
        int[][] ret = new int[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = (int) d[i][j] % n;
            }
        }
        return ret;
    }

    public static String[][] toStringArray2DAsInt(float[][] m) {
        String[][] ret = new String[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                ret[i][j] = String.valueOf((int) m[i][j]);
//                ret[i][j] = String.valueOf(formatFloat(m[i][j], 3));
            }
        }
        return ret;
    }

    public static float[][] flipRight(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = d[i][nc - 1 - j];
            }
        }
        return ret;
    }

    public static float[][] flipDown(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = d[nr - 1 - i][j];
            }
        }
        return ret;
    }

    public static float[][] flipBoth(float[][] d) {
        int nr = d.length;
        int nc = d[0].length;
        float[][] ret = new float[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = d[nr - 1 - i][nc - 1 - j];
            }
        }
        return ret;
    }

}
