package jazari.factory;

public class FactoryNormalization {

    /**
     *
     * @return It's a pretty simple concept. Suppose that a teacher wants to
     * rank her students on a scale of 1-10. She uses their average test score
     * percentages to rank them.
     *
     * If the best average score is 95, and the worst is 23, then she wants a
     * normalization function f(x) such that
     *
     * f(minimum average test score) = minimum rank f(maximum average test
     * score) = maximum rank
     *
     * So if her normalization function is linear, then she can write it as
     *
     * f(x) = Ax + B
     *
     * We can solve for A and B by using the two known values:
     *
     * A * (minimum score) + B = minimum rank A * (maximum score) + B = maximum
     * rank
     *
     * So A = (maximum rank - minimum rank)/(maximum score - minimum score) and
     * B = minimum rank - A * (minimum score)
     *
     * For the example above, A = (10-1)/(95-23) = 0.125. B = 1 - 0.125 * 23 =
     * -1.875
     *
     * So the students who score 95 will have rank 10, and those who score 23
     * will have rank 1, and those who score in the middle, say 50, will have
     * rank:
     *
     * 0.125*50 - 1.875 = 4.375
     *
     * Min-max normalisation just means linearly mapping your data so that the
     * max and min of your input data are mapped to the predefined min and max
     * of your desired scale.
     *
     * Other kinds of normalization map the input data to achieve a predefined
     * mean and standard deviation, instead of a predefined minimum and maximum.
     * Normalization in general just means mapping your data into some more
     * standard form.
     *
     * Xnew=(X-Xmin)/(Xmax-Xmin)
     */
    public static float[][] normalizeZScore(float[][] p) {
        float[][] ret = FactoryUtils.transpose(FactoryMatrix.clone(p));
        for (int i = 0; i < ret.length; i++) {
//            ret[i] = normalizeUnitVector(ret[i]);
            ret[i] = normalizeZScore(ret[i]);
        }
        ret = FactoryUtils.transpose(ret);
        return ret;
    }

    public static float[] normalizeZScore(float[] v) {
        float[] ret = FactoryMatrix.clone(v);
        float mean = FactoryUtils.getMean(v);
        float std = FactoryStatistic.getStandardDeviation(v);
        for (int i = 0; i < v.length; i++) {
            if (std == 0.0) {
                ret[i] = 0;
            } else {
                ret[i] = FactoryUtils.formatFloat((v[i] - mean) / std);
            }
        }
        return ret;
    }

    public static float[][] normalizeUnitVector(float[][] p) {
        float[][] ret = FactoryUtils.transpose(FactoryMatrix.clone(p));
        for (int i = 0; i < ret.length; i++) {
            ret[i] = normalizeUnitVector(ret[i]);
        }
        ret = FactoryUtils.transpose(ret);
        return ret;
    }

    public static float[] normalizeUnitVector(float[] v) {
        float[] ret = FactoryMatrix.clone(v);
        float magnitude = FactoryUtils.getMagnitude(ret);
        for (int i = 0; i < v.length; i++) {
            ret[i] = FactoryUtils.formatFloat(v[i] / magnitude);
        }
        return ret;
    }

    public static float[][] normalizeTanH(float[][] p) {
        float[][] ret = FactoryUtils.transpose(FactoryMatrix.clone(p));
        for (int i = 0; i < ret.length; i++) {
            ret[i] = normalizeTanH(ret[i]);
        }
        ret = FactoryUtils.transpose(ret);
        return ret;
    }

    public static float[] normalizeTanH(float[] v) {
        float[] ret = FactoryMatrix.clone(v);
        float mean = FactoryUtils.getMean(v);
        float std = FactoryStatistic.getStandardDeviation(v);
        for (int i = 0; i < v.length; i++) {
            ret[i] = FactoryUtils.formatFloat((float)(0.5f * (Math.tanh(0.01f * ((v[i] - mean) / std)) + 1.0f)));
        }
        return ret;
    }

    public static float[][] normalizeSigmoidal(float[][] p) {
        float[][] ret = FactoryUtils.transpose(FactoryMatrix.clone(p));
        for (int i = 0; i < ret.length; i++) {
            ret[i] = normalizeSigmoidal(ret[i]);
        }
        ret = FactoryUtils.transpose(ret);
        return ret;
    }

    public static float[] normalizeSigmoidal(float[] v) {
        float[] ret = FactoryMatrix.clone(v);
        for (int i = 0; i < v.length; i++) {
            ret[i] = FactoryUtils.formatFloat((float)(1.0f / (1 + Math.exp(-v[i]))));
        }
        return ret;
    }

    public static float[][] normalizeMinMax(float[][] p) {
        float[][] ret = FactoryUtils.transpose(FactoryMatrix.clone(p));
        for (int i = 0; i < ret.length; i++) {
            ret[i] = normalizeMinMax(ret[i]);
        }
        ret = FactoryUtils.transpose(ret);
        return ret;
    }
    
//    public static float[][] normalizeMinMax(float[][] p) {
//        float[][] ret = FactoryUtils.transpose(FactoryMatrix.clone(p));
//        for (int i = 0; i < ret.length; i++) {
//            ret[i] = normalizeMinMax(ret[i]);
//        }
//        ret = FactoryUtils.transpose(ret);
//        return ret;
//    }

    public static int[] normalizeMinMax(int[] p) {
        int min = FactoryUtils.getMinimum(p);
        int max = FactoryUtils.getMaximum(p);
        int[] r = new int[p.length];
        int delta = (max - min);
        for (int i = 0; i < p.length; i++) {
            r[i] = (p[i] - min) / delta;
        }
        return r;
    }

    public static float[] normalizeMinMax(float[] v) {
        float[] ret = FactoryMatrix.clone(v);
        float min = FactoryUtils.getMinimum(v);
        float max = FactoryUtils.getMaximum(v);
        float delta = (max - min);
        for (int i = 0; i < v.length; i++) {
            ret[i] = FactoryUtils.formatFloat((v[i] - min) / delta);
        }
        return ret;
    }
    
//    public static float[] normalizeMinMax(float[] v) {
//        float[] ret = FactoryMatrix.clone(v);
//        float min = FactoryUtils.getMinimum(v);
//        float max = FactoryUtils.getMaximum(v);
//        float delta = (max - min);
//        for (int i = 0; i < v.length; i++) {
//            ret[i] = (float)FactoryUtils.formatDouble((v[i] - min) / delta);
//        }
//        return ret;
//    }

    public static float[] normalizeIntensity(float[] v, float dmin, float dmax) {
        float[] ret = FactoryMatrix.clone(v);
        float min = FactoryUtils.getMinimum(v);
        float max = FactoryUtils.getMaximum(v);
        float delta1 = (max - min);
        float delta2 = (dmax - dmin);
        float r = delta2 / delta1;
        for (int i = 0; i < v.length; i++) {
            ret[i] = FactoryUtils.formatFloat((v[i] - min) * r + dmin);
        }
        return ret;
    }

    public static float[][] normalizeWithRange(float[][] d, float min, float max) {        
        float[][] temp = d.clone();        
        float range = max - min;
        float mn=FactoryUtils.getMinimum(d);
        float mx=FactoryUtils.getMaximum(d);
        float r=mx-mn;
        int nr=d.length,nc=d[0].length;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                temp[i][j] = min+range*((temp[i][j] - mn) / r);
            }
        }

//        for (int i = 0; i < param.length; i++) {
//            for (int j = 0; j < param[0].length; j++) {
//                float tmp = ((param[i][j] * (float) max) / maximum);
//                if (tmp < min) {
//                    tmp = min;
//                }
//                temp[i][j] = tmp;
//            }
//        }
        return temp;
    }
    public static float[][] normalizeWithRange_sorunlu(float[][] param, float min, float max) {        
        float[][] temp = param.clone();
        float[][] temp2=normalizeMinMax(param.clone());
        float range = max - min;
        for (int i = 0; i < param.length; i++) {
            for (int j = 0; j < param[0].length; j++) {
                float d=temp2[i][j];
                float tmp = d*range+min;
                temp[i][j] = tmp;
            }
        }

//        for (int i = 0; i < param.length; i++) {
//            for (int j = 0; j < param[0].length; j++) {
//                float tmp = ((param[i][j] * (float) max) / maximum);
//                if (tmp < min) {
//                    tmp = min;
//                }
//                temp[i][j] = tmp;
//            }
//        }
        return temp;
    }

    public static float[][] normalizeWithRange(float[][] param, int min, int max) {
        float maximum = FactoryUtils.getMaximum(param);
        float[][] temp = param.clone();
        for (int i = 0; i < param.length; i++) {
            for (int j = 0; j < param[0].length; j++) {
                int tmp = (int) ((param[i][j] * (float) max) / maximum);
                if (tmp < min) {
                    tmp = min;
                }
                temp[i][j] = tmp;
            }
        }
        return temp;
    }
}
