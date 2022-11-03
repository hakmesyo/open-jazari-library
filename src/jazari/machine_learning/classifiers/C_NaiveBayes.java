/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.machine_learning.classifiers;

import jazari.matrix.CMatrix;
import jazari.types.TMatrixOperator;
import jazari.factory.FactoryUtils;

public class C_NaiveBayes {

    public enum AttributeType {
        Categorical, Real
    };

    public static void main(String[] args) {
        String trainPath = "src\\cezeri\\classifiers\\tic-tac-toe_train.txt";
        String testPath = "src\\cezeri\\classifiers\\tic-tac-toe_test.txt";
        evaluateModelTrainTest(AttributeType.Categorical, trainPath, testPath);
    }

    public static void evaluateModelTrainTest(AttributeType at, String trainPath, String testPath) {
        if (at.equals(AttributeType.Categorical)) {
            evaluateModelCategoricalTrainTest(trainPath, testPath);
        }
        if (at.equals(AttributeType.Real)) {
            evaluateModelRealTrainTest(trainPath, testPath);
        }
    }

    public static void evaluateModelCrossValidation(AttributeType at, String path, int nFolds) {
        if (at.equals(AttributeType.Categorical)) {
            evaluateModelCategoricalCV(path, nFolds);
        }
        if (at.equals(AttributeType.Real)) {
            evaluateModelRealCV(path, nFolds);
        }
    }

    private static void evaluateModelCategoricalTrainTest(String trainPath, String testPath) {
        CMatrix train = CMatrix.getInstanceFromFile(trainPath, ",").shuffleRows();
        CMatrix test = CMatrix.getInstanceFromFile(testPath, ",").shuffleRows();

        CMatrix positives = CMatrix.getInstance();
        CMatrix negatives = CMatrix.getInstance();

        int class_label_index = train.getColumnNumber() - 1;
        CMatrix positive_indexes = train.findIndex(TMatrixOperator.EQUALS, 1, ":", "" + class_label_index);
        CMatrix negative_indexes = train.findIndex(TMatrixOperator.EQUALS, -1, ":", "" + class_label_index);

        if (positive_indexes.getSize().column == 1) {
            positives = train.setArray(positive_indexes.toIntArray1D());
        }
        if (negative_indexes.getSize().column == 1) {
            negatives = train.setArray(negative_indexes.toIntArray1D());
        }

        int num_rows = train.getRowNumber();
        int num_columns = train.getColumnNumber();
        int numr_positive = positives.getRowNumber();
        int numr_negative = negatives.getRowNumber();

        float prior_positive = 1.0f * numr_positive / num_rows;
        float prior_negative = 1.0f * numr_negative / num_rows;

        float[][] evidence = new float[num_columns - 1][3];
        float[][][] likelihood = new float[num_columns - 1][3][2];

        for (int i = 0; i < num_columns - 1; i++) {
            CMatrix a = train.cmd(":", "" + i);
            evidence[i][0] = a.findIndex(TMatrixOperator.EQUALS, 2).getRowNumber() * 1.0f / num_rows;
            evidence[i][1] = a.findIndex(TMatrixOperator.EQUALS, 3).getRowNumber() * 1.0f / num_rows;
            evidence[i][2] = a.findIndex(TMatrixOperator.EQUALS, 4).getRowNumber() * 1.0f / num_rows;

            CMatrix p = positives.cmd(":", "" + i);
            CMatrix n = negatives.cmd(":", "" + i);

            //frequency of x in positive and negative for each attribute  
            //lines below computes likelihood probability
            likelihood[i][0][0] = p.findIndex(TMatrixOperator.EQUALS, 2).getRowNumber() * 1.0f / numr_positive;
            likelihood[i][0][1] = n.findIndex(TMatrixOperator.EQUALS, 2).getRowNumber() * 1.0f / numr_negative;

            likelihood[i][1][0] = p.findIndex(TMatrixOperator.EQUALS, 3).getRowNumber() * 1.0f / numr_positive;
            likelihood[i][1][1] = n.findIndex(TMatrixOperator.EQUALS, 3).getRowNumber() * 1.0f / numr_negative;

            likelihood[i][2][0] = p.findIndex(TMatrixOperator.EQUALS, 4).getRowNumber() * 1.0f / numr_positive;
            likelihood[i][2][1] = n.findIndex(TMatrixOperator.EQUALS, 4).getRowNumber() * 1.0f / numr_negative;
        }

        // conditional probality calculation from the naive bayes formula
        // posterior means conditional probability 
        // posterior=prior*likelihood/evidence
        // p(c|x)=p(c)*p(x|c)/p(x)
        float[] p_positive = new float[test.getRowNumber()];
        float[] p_negative = new float[test.getRowNumber()];

        float[] norm_positive = new float[test.getRowNumber()];
        float[] norm_negative = new float[test.getRowNumber()];

        float[] confidence = new float[test.getRowNumber()];
        float[] result = new float[test.getRowNumber()];

        for (int i = 0; i < test.getRowNumber(); i++) {

            CMatrix row = test.cmd("" + i, "0:8");

            float[] likely_positive = new float[row.getRowNumber()];
            float[] likely_negative = new float[row.getRowNumber()];
            for (int j = 0; j < row.getRowNumber(); j++) {
                int value = (int) (row.getValue(j, 0) - 2);
                likely_positive[j] = likelihood[j][value][0];
                likely_negative[j] = likelihood[j][value][1];
            }
            p_positive[i] = prior_positive * CMatrix.getInstance(likely_positive).prod().getValue();
            p_negative[i] = prior_negative * CMatrix.getInstance(likely_negative).prod().getValue();

            //normalized posterirors
            norm_positive[i] = p_positive[i] / (p_positive[i] + p_negative[i]) * 100;
            norm_negative[i] = 100 - norm_positive[i];
            if (norm_positive[i] > norm_negative[i]) {
                result[i] = 1;
                confidence[i] = norm_positive[i];
            } else {
                result[i] = -1;
                confidence[i] = norm_negative[i];
            }
        }
        float accuracy = 0;
        for (int i = 0; i < test.getRowNumber(); i++) {
            if (test.getValue(i, 9) == result[i]) {
                accuracy += 1;
            }
        }
        accuracy = accuracy / test.getRowNumber() * 100;
        System.out.println("accuracy:" + accuracy);
//        CMatrix.getInstance(confidence).formatDouble(2).println("Confidence levels of the classifier are");
    }

    private static void evaluateModelRealTrainTest(String trainPath, String testPath) {
        CMatrix train = CMatrix.getInstanceFromFile(trainPath, ",").shuffleRows();
        CMatrix test = CMatrix.getInstanceFromFile(testPath, ",").shuffleRows();
        CMatrix positives = CMatrix.getInstance();
        CMatrix negatives = CMatrix.getInstance();
        int class_label_index = train.getColumnNumber() - 1;
        CMatrix positive_indexes = train.findIndex(TMatrixOperator.EQUALS, 1, ":", "" + class_label_index);
        CMatrix negative_indexes = train.findIndex(TMatrixOperator.EQUALS, -1, ":", "" + class_label_index);
        positives = train.setArray(positive_indexes.toIntArray1D());
        negatives = train.setArray(negative_indexes.toIntArray1D());
        int num_rows = train.getRowNumber();
        int num_columns = train.getColumnNumber();
        int numr_positive = positives.getRowNumber();
        int numr_negative = negatives.getRowNumber();
        float prior_positive = 1.0f * numr_positive / num_rows;
        float prior_negative = 1.0f * numr_negative / num_rows;
        float[] meanYes = new float[num_columns - 1];
        float[] meanNo = new float[num_columns - 1];
        float[] stdYes = new float[num_columns - 1];
        float[] stdNo = new float[num_columns - 1];
        for (int i = 0; i < num_columns - 1; i++) {
            CMatrix a = train.cmd(":", "" + i);
            meanYes[i] = a.cmd(positive_indexes.toIntArray1D()).meanTotal();
            meanNo[i] = a.cmd(negative_indexes.toIntArray1D()).meanTotal();
            stdYes[i] = a.cmd(positive_indexes.toIntArray1D()).stdTotal();
            stdNo[i] = a.cmd(negative_indexes.toIntArray1D()).stdTotal();
        }
        float accuracy = 0;
        for (int i = 0; i < test.getRowNumber(); i++) {
            CMatrix row = test.cmd("" + i, ":");
            float p_yes = prior_positive;
            float p_no = prior_negative;
            for (int j = 0; j < row.getColumnNumber() - 1; j++) {
                float x = row.toFloatArray1D()[j];
                float pdf_yes = (float)((1.0 / (Math.sqrt(2 * Math.PI) * stdYes[j])
                        * Math.exp(-1.0 * (((x - meanYes[j]) * (x - meanYes[j])) / (2 * stdYes[j] * stdYes[j])))));
                p_yes *= pdf_yes;
                float pdf_no = (float)((1.0 / (Math.sqrt(2 * Math.PI) * stdNo[j])
                        * Math.exp(-1.0 * (((x - meanNo[j]) * (x - meanNo[j])) / (2 * stdNo[j] * stdNo[j])))));
                p_no *= pdf_no;
            }
            float np_yes = p_yes / (p_yes + p_no);
            float np_no = p_no / (p_yes + p_no);

            if (np_yes > np_no) {
                if (row.toDoubleArray1D()[row.getColumnNumber() - 1] == 1) {
                    accuracy = accuracy + 1;
                }
            } else {
                if (row.toDoubleArray1D()[row.getColumnNumber() - 1] == -1) {
                    accuracy = accuracy + 1;
                }
            }
        }
        accuracy = accuracy / test.getRowNumber() * 100;
        System.out.println("accuracy:" + accuracy);
    }

    private static void evaluateModelCategoricalCV(String path, int nFolds) {
        CMatrix ds = CMatrix.getInstanceFromFile(path, ",").shuffleRows();
        CMatrix positives = CMatrix.getInstance();
        CMatrix negatives = CMatrix.getInstance();
        CMatrix[][] cv = ds.crossValidationSets(nFolds);
        float[] class_labels = FactoryUtils.getDistinctValues(ds.getColumn(ds.getColumnNumber() - 1));
        float[] values = FactoryUtils.getDistinctValues(ds.getColumn(0));
        float avgAccuracy = 0;
        for (int k = 0; k < nFolds; k++) {
            CMatrix train = cv[k][0];
            CMatrix test = cv[k][1];

            int class_label_index = train.getColumnNumber() - 1;
            CMatrix positive_indexes = train.findIndex(TMatrixOperator.EQUALS, class_labels[0], ":", "" + class_label_index);
            CMatrix negative_indexes = train.findIndex(TMatrixOperator.EQUALS, class_labels[1], ":", "" + class_label_index);

            if (positive_indexes.getSize().column == 1) {
                positives = train.setArray(positive_indexes.toIntArray1D());
            }
            if (negative_indexes.getSize().column == 1) {
                negatives = train.setArray(negative_indexes.toIntArray1D());
            }

            int num_rows = train.getRowNumber();
            int num_columns = train.getColumnNumber();
            int numr_positive = positives.getRowNumber();
            int numr_negative = negatives.getRowNumber();

            float prior_positive = 1.0f * numr_positive / num_rows;
            float prior_negative = 1.0f * numr_negative / num_rows;

            float[][] evidence = new float[num_columns - 1][3];
            float[][][] likelihood = new float[num_columns - 1][3][2];

            for (int i = 0; i < num_columns - 1; i++) {
                CMatrix a = train.cmd(":", "" + i);

                evidence[i][0] = a.findIndex(TMatrixOperator.EQUALS, values[0]).getRowNumber() * 1.0f / num_rows;
                evidence[i][1] = a.findIndex(TMatrixOperator.EQUALS, values[1]).getRowNumber() * 1.0f / num_rows;
                evidence[i][2] = a.findIndex(TMatrixOperator.EQUALS, values[2]).getRowNumber() * 1.0f / num_rows;

                CMatrix p = positives.cmd(":", "" + i);
                CMatrix n = negatives.cmd(":", "" + i);

                //frequency of x in positive and negative for each attribute  
                //lines below computes likelihood probability
                likelihood[i][0][0] = p.findIndex(TMatrixOperator.EQUALS, values[0]).getRowNumber() * 1.0f / numr_positive;
                likelihood[i][0][1] = n.findIndex(TMatrixOperator.EQUALS, values[0]).getRowNumber() * 1.0f / numr_negative;

                likelihood[i][1][0] = p.findIndex(TMatrixOperator.EQUALS, values[1]).getRowNumber() * 1.0f / numr_positive;
                likelihood[i][1][1] = n.findIndex(TMatrixOperator.EQUALS, values[1]).getRowNumber() * 1.0f / numr_negative;

                likelihood[i][2][0] = p.findIndex(TMatrixOperator.EQUALS, values[2]).getRowNumber() * 1.0f / numr_positive;
                likelihood[i][2][1] = n.findIndex(TMatrixOperator.EQUALS, values[2]).getRowNumber() * 1.0f / numr_negative;
            }

            // conditional probality calculation from the naive bayes formula
            // posterior means conditional probability 
            // posterior=prior*likelihood/evidence
            // p(c|x)=p(c)*p(x|c)/p(x)
            float[] p_positive = new float[test.getRowNumber()];
            float[] p_negative = new float[test.getRowNumber()];

            float[] norm_positive = new float[test.getRowNumber()];
            float[] norm_negative = new float[test.getRowNumber()];

            float[] confidence = new float[test.getRowNumber()];
            float[] result = new float[test.getRowNumber()];

            for (int i = 0; i < test.getRowNumber(); i++) {

                CMatrix row = test.cmd("" + i, "0:8");

                float[] likely_positive = new float[row.getRowNumber()];
                float[] likely_negative = new float[row.getRowNumber()];
                for (int j = 0; j < row.getRowNumber(); j++) {
                    int value = (int) (row.getValue(j, 0) - 2);
                    likely_positive[j] = likelihood[j][value][0];
                    likely_negative[j] = likelihood[j][value][1];
                }
                p_positive[i] = prior_positive * CMatrix.getInstance(likely_positive).prod().getValue();
                p_negative[i] = prior_negative * CMatrix.getInstance(likely_negative).prod().getValue();

                //normalized posterirors
                norm_positive[i] = p_positive[i] / (p_positive[i] + p_negative[i]) * 100;
                norm_negative[i] = 100 - norm_positive[i];
                if (norm_positive[i] > norm_negative[i]) {
                    result[i] = class_labels[0];
                    confidence[i] = norm_positive[i];
                } else {
                    result[i] = class_labels[1];
                    confidence[i] = norm_negative[i];
                }
            }
            float accuracy = 0;
            for (int i = 0; i < test.getRowNumber(); i++) {
                if (test.getValue(i, 9) == result[i]) {
                    accuracy += 1;
                }
            }
            accuracy = accuracy / test.getRowNumber() * 100;
//            System.out.println("accuracy:" + accuracy);
//            CMatrix.getInstance(confidence).formatDouble(2).println("Confidence levels of the classifier are");
            avgAccuracy += accuracy;
        }
        avgAccuracy = avgAccuracy / nFolds;
        System.out.println("average accuracy:" + avgAccuracy);
    }

    private static void evaluateModelRealCV(String path, int nFolds) {
        CMatrix ds = CMatrix.getInstanceFromFile(path, ",").shuffleRows();
        CMatrix positives = CMatrix.getInstance();
        CMatrix negatives = CMatrix.getInstance();
        CMatrix[][] cv = ds.crossValidationSets(nFolds);
        float[] class_labels = FactoryUtils.getDistinctValues(ds.getColumn(ds.getColumnNumber() - 1));
        float avgAccuracy = 0;
        for (int k = 0; k < nFolds; k++) {
            CMatrix train = cv[k][0];
            CMatrix test = cv[k][1];
            int class_label_index = train.getColumnNumber() - 1;
            CMatrix positive_indexes = train.findIndex(TMatrixOperator.EQUALS, class_labels[0], ":", "" + class_label_index);
            CMatrix negative_indexes = train.findIndex(TMatrixOperator.EQUALS, class_labels[1], ":", "" + class_label_index);
            positives = train.setArray(positive_indexes.toIntArray1D());
            negatives = train.setArray(negative_indexes.toIntArray1D());
            int num_rows = train.getRowNumber();
            int num_columns = train.getColumnNumber();
            int numr_positive = positives.getRowNumber();
            int numr_negative = negatives.getRowNumber();
            float prior_positive = 1.0f * numr_positive / num_rows;
            float prior_negative = 1.0f * numr_negative / num_rows;
            float[] meanYes = new float[num_columns - 1];
            float[] meanNo = new float[num_columns - 1];
            float[] stdYes = new float[num_columns - 1];
            float[] stdNo = new float[num_columns - 1];
            for (int i = 0; i < num_columns - 1; i++) {
                CMatrix a = train.cmd(":", "" + i);
                meanYes[i] = a.cmd(positive_indexes.toIntArray1D()).meanTotal();
                meanNo[i] = a.cmd(negative_indexes.toIntArray1D()).meanTotal();
                stdYes[i] = a.cmd(positive_indexes.toIntArray1D()).stdTotal();
                stdNo[i] = a.cmd(negative_indexes.toIntArray1D()).stdTotal();
            }
            float accuracy = 0;
            for (int i = 0; i < test.getRowNumber(); i++) {
                CMatrix row = test.cmd("" + i, ":");
                float p_yes = prior_positive;
                float p_no = prior_negative;
                for (int j = 0; j < row.getColumnNumber() - 1; j++) {
                    float x = row.toFloatArray1D()[j];
                    float pdf_yes = (float)((1.0 / (Math.sqrt(2 * Math.PI) * stdYes[j])
                            * Math.exp(-1.0 * (((x - meanYes[j]) * (x - meanYes[j])) / (2 * stdYes[j] * stdYes[j])))));
                    p_yes *= pdf_yes;
                    float pdf_no = (float)(1.0 / (Math.sqrt(2 * Math.PI) * stdNo[j])
                            * Math.exp(-1.0 * (((x - meanNo[j]) * (x - meanNo[j])) / (2 * stdNo[j] * stdNo[j]))));
                    p_no *= pdf_no;
                }
                float np_yes = p_yes / (p_yes + p_no);
                float np_no = p_no / (p_yes + p_no);

                if (np_yes > np_no) {
                    if (row.toDoubleArray1D()[row.getColumnNumber() - 1] == class_labels[0]) {
                        accuracy = accuracy + 1;
                    }
                } else {
                    if (row.toDoubleArray1D()[row.getColumnNumber() - 1] == class_labels[1]) {
                        accuracy = accuracy + 1;
                    }
                }
            }
            accuracy = accuracy / test.getRowNumber() * 100;
            //System.out.println(k + ".accuracy:" + accuracy);
            avgAccuracy += accuracy;
        }
        avgAccuracy = avgAccuracy / nFolds;
        System.out.println("average accuracy:" + avgAccuracy);
    }

}
