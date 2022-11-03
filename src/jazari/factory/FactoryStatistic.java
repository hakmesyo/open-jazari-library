/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.factory;

import jazari.enums.EPerformanceMetrics;
import static jazari.factory.FactoryUtils.getSum;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Vector;

/**
 *
 * @author BAP1
 */
public final class FactoryStatistic {

    private static float R = 0;             //Correlation Coefficient
    private static float R2 = 0;            //R Square
    private static float CRCF = 0;          //Correlation Coefficient
    private static float NSEC = 0;          //Nash–Sutcliffe efficiency coefficient
    private static float RELATIVE_NSEC = 0; //Relative NSEC
    private static float MSE = 0;           //Mean Squared Error
    private static float RMSE = 0;          //Root Mean Squared Error
    private static float MAE = 0;           //Mean Absolute Error
    private static float MPE = 0;           //Mean Percent Error İsa HFSS
    private static float IOA = 0;           //Index of Agreement
    private static float ARE = 0;           //Average Relative Error
    private static float RAE = 0;           //Relative Absolute Error
    private static float RRSE = 0;          //Root Relative Squared Error
    private static float PEARSON = 0;       //PEARSON
    private static float KENDALL = 0;       //KENDALL
    private static float SPEARMAN = 0;      //SPEARMAN

    public static ArrayList<Float> calculateStatistics(float[] obs, float[] sim,ArrayList<String> attr){
        ArrayList<Float> ret=new ArrayList<>();
        for (String x : attr) {
            if (x.equals(EPerformanceMetrics.R.name())) {
                ret.add(R(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.CRCF.name())) {
                ret.add(R(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.R2.name())) {
                ret.add(R2(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.NSEC.name())) {
                ret.add(NSEC(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.RELATIVE_NSEC.name())) {
                ret.add(RELATIVE_NSEC(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.MSE.name())) {
                ret.add(MSE(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.RMSE.name())) {
                ret.add(RMSE(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.MAE.name())) {
                ret.add(MAE(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.MPE.name())) {
                ret.add(MPE(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.IOA.name())) {
                ret.add(IOA(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.ARE.name())) {
                ret.add(ARE(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.RAE.name())) {
                ret.add(RAE(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.RRSE.name())) {
                ret.add(RRSE(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.KENDALL.name())) {
                ret.add(KENDALL(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.PEARSON.name())) {
                ret.add(PEARSON(obs, sim));
            }
            if (x.equals(EPerformanceMetrics.SPEARMAN.name())) {
                ret.add(SPEARMAN(obs, sim));
            }
        }
        return ret;
    }
    public static String calculateStatistics(float[] obs, float[] sim) {
        R = R(obs, sim);
        CRCF = R;
        R2 = R * R;
        NSEC = NSEC(obs, sim);
        RELATIVE_NSEC = RELATIVE_NSEC(obs, sim);
        MSE = MSE(obs, sim);
        RMSE = (float)Math.sqrt(MSE);
        MAE = MAE(obs, sim);
        MPE = MPE(obs, sim);
        IOA = IOA(obs, sim);
        ARE = ARE(obs, sim);
        RAE = RAE(obs, sim);
        RRSE = RRSE(obs, sim);
        KENDALL = KENDALL(obs, sim);
        PEARSON = PEARSON(obs, sim);
        SPEARMAN = SPEARMAN(obs, sim);
        return toString("");
    }

    public static String toString(String str) {
        String s = "Statistic Metrics\n";
        s += "R:" + R + "\n";
        s += "CRCF:" + CRCF + "\n";
        s += "R2:" + R2 + "\n";
        s += "NSEC:" + NSEC + "\n";
        s += "RELATIVE_NSEC:" + RELATIVE_NSEC + "\n";
        s += "IOA:" + IOA + "\n";
        s += "MSE:" + MSE + "\n";
        s += "RMSE:" + RMSE + "\n";
        s += "MAE:" + MAE + "\n";
        s += "MPE:" + MPE + "\n";
        s += "ARE:" + ARE + "\n";
        s += "RAE:" + RAE + "\n";
        s += "RRSE:" + RRSE + "\n";
        s += "PEARSON:" + PEARSON + "\n";
        s += "KENDALL:" + KENDALL + "\n";
        s += "SPEARMAN:" + SPEARMAN + "\n";
        return s;
    }

    public static float KENDALL(float[] x, float[] y) {
        assert x.length == y.length;
        int x_n = x.length;
        int y_n = y.length;
        float[] x_rank = new float[x_n];
        float[] y_rank = new float[y_n];

        TreeMap<Float, HashSet<Integer>> sorted = new TreeMap<Float, HashSet<Integer>>();
        for (int i = 0; i < x_n; i++) {
            float v = x[i];
            if (sorted.containsKey(v) == false) {
                sorted.put(v, new HashSet<Integer>());
            }
            sorted.get(v).add(i);
        }

        int c = 1;
        for (float v : sorted.descendingKeySet()) {
            float r = 0;
            for (int i : sorted.get(v)) {
                r += c;
                c++;
            }

            r /= sorted.get(v).size();

            for (int i : sorted.get(v)) {
                x_rank[i] = r;
            }
        }

        sorted.clear();
        for (int i = 0; i < y_n; i++) {
            float v = y[i];
            if (sorted.containsKey(v) == false) {
                sorted.put(v, new HashSet<Integer>());
            }
            sorted.get(v).add(i);
        }

        c = 1;
        for (float v : sorted.descendingKeySet()) {
            float r = 0;
            for (int i : sorted.get(v)) {
                r += c;
                c++;
            }

            r /= sorted.get(v).size();

            for (int i : sorted.get(v)) {
                y_rank[i] = r;
            }
        }

        return kendallTauBeta(x_rank, y_rank);
    }

    public static float kendallTauBeta(float[] x, float[] y) {
        assert x.length == y.length;

        int c = 0;
        int d = 0;
        HashMap<Float, HashSet<Integer>> xTies = new HashMap<Float, HashSet<Integer>>();
        HashMap<Float, HashSet<Integer>> yTies = new HashMap<Float, HashSet<Integer>>();

        for (int i = 0; i < x.length - 1; i++) {
            for (int j = i + 1; j < x.length; j++) {
                if (x[i] > x[j] && y[i] > y[j]) {
                    c++;
                } else if (x[i] < x[j] && y[i] < y[j]) {
                    c++;
                } else if (x[i] > x[j] && y[i] < y[j]) {
                    d++;
                } else if (x[i] < x[j] && y[i] > y[j]) {
                    d++;
                } else {
                    if (x[i] == x[j]) {
                        if (xTies.containsKey(x[i]) == false) {
                            xTies.put(x[i], new HashSet<Integer>());
                        }
                        xTies.get(x[i]).add(i);
                        xTies.get(x[i]).add(j);
                    }

                    if (y[i] == y[j]) {
                        if (yTies.containsKey(y[i]) == false) {
                            yTies.put(y[i], new HashSet<Integer>());
                        }
                        yTies.get(y[i]).add(i);
                        yTies.get(y[i]).add(j);
                    }
                }
            }
        }

        int diff = c - d;
        float denom = 0;

        float n0 = (x.length * (x.length - 1)) / 2.0f;
        float n1 = 0;
        float n2 = 0;

        for (float t : xTies.keySet()) {
            float s = xTies.get(t).size();
            n1 += (s * (s - 1)) / 2;
        }

        for (float t : yTies.keySet()) {
            float s = yTies.get(t).size();
            n2 += (s * (s - 1)) / 2;
        }

        denom = (float)Math.sqrt((n0 - n1) * (n0 - n2));

        float t = diff / denom;

        assert t >= -1 && t <= 1 : t;

        return t;
    }

    public static float PEARSON(float[] x, float[] y) {
        assert x.length == y.length;
        float mean_x = 0;
        float mean_y = 0;
        int n_x = x.length;
        int n_y = y.length;
        for (int i = 0; i < n_x; i++) {
            mean_x += x[i];
            mean_y += y[i];
        }
        mean_x /= n_x;
        mean_y /= n_y;

        float cov = 0;
        float sd_x = 0;
        float sd_y = 0;

        for (int i = 0; i < n_x; i++) {
            cov += (x[i] - mean_x) * (y[i] - mean_y);
            sd_x += (x[i] - mean_x) * (x[i] - mean_x);
            sd_y += (y[i] - mean_y) * (y[i] - mean_y);
        }

        if (cov == 0) {
            return 0;
        } else {
            float r = (float)(cov / (Math.sqrt(sd_x) * Math.sqrt(sd_y)));
            assert r >= -1 && r <= 1 : r + "\n" + printArray(x) + printArray(y) + "Mean x = " + mean_x + ", Mean y = " + mean_y + ", Cov = " + cov + ", SD x = " + sd_x + ", SD y = " + sd_y + "\n";
            return r;
        }
    }

    private static String printArray(float[] a) {
        String ret = "";
        for (float d : a) {
            ret = ret + d + " ";
        }
        ret = ret + "\n";
        return ret;
    }

    public static float SPEARMAN(float[] x, float[] y) {
        assert x.length == y.length;
        int x_n = x.length;
        int y_n = y.length;
        float[] x_rank = new float[x_n];
        float[] y_rank = new float[y_n];

        TreeMap<Float, HashSet<Integer>> sorted = new TreeMap<Float, HashSet<Integer>>();
        for (int i = 0; i < x_n; i++) {
            float v = x[i];
            if (sorted.containsKey(v) == false) {
                sorted.put(v, new HashSet<Integer>());
            }
            sorted.get(v).add(i);
        }

        int c = 1;
        for (float v : sorted.descendingKeySet()) {
            float r = 0;
            for (int i : sorted.get(v)) {
                r += c;
                c++;
            }

            r /= sorted.get(v).size();

            for (int i : sorted.get(v)) {
                x_rank[i] = r;
            }
        }

        sorted.clear();
        for (int i = 0; i < y_n; i++) {
            float v = y[i];
            if (sorted.containsKey(v) == false) {
                sorted.put(v, new HashSet<Integer>());
            }
            sorted.get(v).add(i);
        }

        c = 1;
        for (float v : sorted.descendingKeySet()) {
            float r = 0;
            for (int i : sorted.get(v)) {
                r += c;
                c++;
            }

            r /= sorted.get(v).size();

            for (int i : sorted.get(v)) {
                y_rank[i] = r;
            }
        }
        return PEARSON(x_rank, y_rank);
    }

    public static float R(float[] obs, float[] sim) {
        float mObs = FactoryUtils.getMean(obs);
        float mSim = FactoryUtils.getMean(sim);
        float tDiff = 0;
        float tObs2 = 0;
        float tSim2 = 0;
        for (int i = 0; i < obs.length; i++) {
            tDiff += (obs[i] - mObs) * (sim[i] - mSim);
            tObs2 += (obs[i] - mObs) * (obs[i] - mObs);
            tSim2 += (sim[i] - mSim) * (sim[i] - mSim);
        }
        float ret = (float)(tDiff / Math.sqrt(tObs2 * tSim2));
        return ret;
    }

    public static float R2(float[] obs, float[] sim) {
        float ret = R(obs, sim);
        return ret * ret;
    }

    public static float CRCF(float[] obs, float[] sim) {
        return R(obs, sim);
    }

    public static float NSEC(float[] obs, float[] sim) {
        float mObs = FactoryUtils.getMean(obs);
        float s1 = 0;
        float s2 = 0;
        for (int i = 0; i < obs.length; i++) {
            s1 += (obs[i] - sim[i]) * (obs[i] - sim[i]);
            s2 += (obs[i] - mObs) * (obs[i] - mObs);
        }
        float ret = 1 - s1 / s2;
        return ret;
    }

    public static float RELATIVE_NSEC(float[] obs, float[] sim) {
        float mObs = FactoryUtils.getMean(obs);
        float s1 = 0;
        float s2 = 0;
        for (int i = 0; i < obs.length; i++) {
            s1 += Math.pow((obs[i] - sim[i]) / obs[i], 2);
            s2 += Math.pow((obs[i] - mObs) / mObs, 2);
        }
        float ret = 1 - s1 / s2;
        return ret;
    }

    public static float MSE(float[] obs, float[] sim) {
        float err = 0;
        for (int i = 0; i < obs.length; i++) {
            err += Math.pow((obs[i] - sim[i]), 2);
        }
        float ret = err / obs.length;
        return ret;
    }

    public static float RMSE(float[] obs, float[] sim) {
        return (float)Math.sqrt(MSE(obs, sim));
    }

    /**
     * Mean Absolute Error
     *
     * @param obs
     * @param sim
     * @return
     */
    public static float MAE(float[] obs, float[] sim) {
        float err = 0;
        for (int i = 0; i < obs.length; i++) {
            err += Math.abs(obs[i] - sim[i]);
        }
        float ret = err / obs.length;
        return ret;
    }

    /**
     * Mean Percentage Error or itis known as percent error in general it is
     * similar to ARE except ARE uses simulated value at denominator but MPE
     * uses observed one
     *
     * @param obs
     * @param sim
     * @return
     */
    public static float MPE(float[] obs, float[] sim) {
        float err = 0;
        for (int i = 0; i < obs.length; i++) {
            err += Math.abs(obs[i] - sim[i]) / Math.abs(obs[i]);
        }
        float ret = err / obs.length * 100;
        return ret;
    }

    /**
     * Mean value of Absolute Relative Error
     *
     * @param obs
     * @param sim
     * @return
     */
    public static float ARE(float[] obs, float[] sim) {
        float err = 0;
        for (int i = 0; i < obs.length; i++) {
            err += Math.abs(obs[i] - sim[i]) / Math.abs(sim[i]);
        }
        float ret = err / obs.length * 100;
        return ret;
    }

    /**
     * Index of Agreement
     *
     * @param obs
     * @param sim
     * @return
     */
    public static float IOA(float[] obs, float[] sim) {
        float m = FactoryUtils.getMean(obs);
        float s1 = 0;
        float s2 = 0;
        for (int i = 0; i < obs.length; i++) {
            s1 += Math.pow((obs[i] - sim[i]), 2);
            s2 += Math.pow(Math.abs(sim[i] - m) + Math.abs(obs[i] - m), 2);
        }
        float ret = 1 - s1 / s2;
        return ret;
    }

    /**
     * Relative Absolute Error
     *
     * @param obs
     * @param sim
     * @return
     */
    public static float RAE(float[] obs, float[] sim) {
        float mObs = FactoryUtils.getMean(obs);
        float s1 = 0;
        float s2 = 0;
        for (int i = 0; i < obs.length; i++) {
            s1 += Math.abs(obs[i] - sim[i]);
            s2 += Math.abs(obs[i] - mObs);
        }
        float ret = s1 / s2 * 100;
        return ret;
    }

    public static float RRSE(float[] obs, float[] sim) {
        float mObs = FactoryUtils.getMean(obs);
        float rmse = RMSE(obs, sim);
        return rmse / mObs;
    }

    public static float getStandardDeviation(float[] m) {
        float mean = FactoryUtils.getMean(m);
        float deviation = 0.0f;
        float variance = 0.0f;

        for (int i = 0; i < m.length; i++) {
            deviation = m[i] - mean;
            variance += Math.pow(deviation, 2);
        }
        variance /= (m.length - 1);
        return (float)Math.sqrt(variance);
    }

    public static float getVariance(float[] m) {
        float mean = FactoryUtils.getMean(m);
        float deviation = 0.0f;
        float variance = 0.0f;

        for (int i = 0; i < m.length; i++) {
            deviation = m[i] - mean;
            variance += Math.pow(deviation, 2);
        }
        variance /= (m.length - 1);
        return variance;
    }

}
