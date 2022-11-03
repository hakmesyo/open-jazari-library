/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.factory;

/**
 *
 * @author musa-atas
 */
public class FactorySimilarityDistance {

    /**
     * Musa ATAŞ Various similarity/dissimilarity measures based on manhattan
     * euclidean chebyshev canberra sorensen angular_seperation
     * correlation_coefficient
     *
     * @param d1
     * @param d2
     * @return
     */
    public static float getEuclideanDistance(float[] d1, float[] d2) {
        float dist = 0;
        for (int i = 0; i < d1.length; i++) {
            dist += (d1[i] - d2[i]) * (d1[i] - d2[i]);
        }
        return (float)Math.sqrt(dist);
    }

    public static float getManhattanDistance(float[] d1, float[] d2) {
        float dist = 0;
        for (int i = 0; i < d1.length; i++) {
            dist += Math.abs(d1[i] - d2[i]);
        }
        return dist;
    }

    public static float getChebyshevDistance(float[] d1, float[] d2) {
        float dist = 0;
        for (int i = 0; i < d1.length; i++) {
            if (Math.abs(d1[i] - d2[i]) > dist) {
                dist = Math.abs(d1[i] - d2[i]);
            }
        }
        return dist;
    }

    public static float getCanberraDistance(float[] d1, float[] d2) {
        float dist = 0;
        for (int i = 0; i < d1.length; i++) {
            if (Math.abs(d1[i] - d2[i]) == 0 || (d1[i] + d2[i]) == 0) {
                continue;
            }
            dist += Math.abs(d1[i] - d2[i]) / (d1[i] + d2[i]);
        }
        return dist;
    }

    public static float getSorensenDistance(float[] d1, float[] d2) {
        float dist = 0;
        float absdif = 0;
        float total = 0;
        for (int i = 0; i < d1.length; i++) {
            absdif += Math.abs(d1[i] - d2[i]);
            total += d1[i] + d2[i];
        }
        dist = absdif / total;
        return dist;
    }

    public static float getAngularDistance(float[] d1, float[] d2) {
        float dist = 0;
        float pay = 0;
        float payda1 = 0;
        float payda2 = 0;
        for (int i = 0; i < d1.length; i++) {
            pay += d1[i] * d2[i];
            payda1 += d1[i] * d1[i];
            payda2 += d2[i] * d2[i];
        }
        dist = pay / (float)Math.sqrt(payda1 * payda2);
        return Math.abs(dist);
    }

    public static float getCorrelationCoefficientDistance(float[] d1, float[] d2) {
        float dist = 0;
        float pay = 0;
        float payda1 = 0;
        float m1 = getMean(d1);
        float payda2 = 0;
        float m2 = getMean(d2);
        for (int i = 0; i < d1.length; i++) {
            pay += (d1[i] - m1) * (d2[i] - m2);
            payda1 += (d1[i] - m1) * (d1[i] - m1);
            payda2 += (d2[i] - m2) * (d2[i] - m2);
        }
        dist = pay / (float)Math.sqrt(payda1 * payda2);
        return Math.abs(dist);
    }

    public static float getMean(float[] d) {
        float t = 0;
        for (int i = 0; i < d.length; i++) {
            t += d[i];
        }
        return t / d.length;
    }
}
