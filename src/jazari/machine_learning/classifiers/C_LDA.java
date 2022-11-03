package jazari.machine_learning.classifiers;




/**
 * Copyright (c) 2010, Dr. Wolfgang Lenhard, Psychometrica.de
 * All rights reserved.
 *
 * This code serves for calculating a linear discriminant analysis (LDA) and it is based on the
 * tutorial of Kardi Teknomo (http://people.revoledu.com/kardi/tutorial/LDA/index.html). You will
 * need JAMA (A Java Matrix Package; http://math.nist.gov/javanumerics/jama/) to run this routines.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Dr. Wolfgang Lenhard, 2010
 * @version 0.1, 07/31/2010
 *
 * Quotation:
 * Lenhard, W. (2010). Realisation of Linear Discriminant Analysis in Java. Bibergau: Psychometrica.
 * 
 * 
 */
import java.util.ArrayList;

import Jama.Matrix;
import jazari.factory.FactoryUtils;

public class C_LDA {
	private float[][] groupMean;
	private float[][] pooledInverseCovariance;
	private float[] probability;
	private ArrayList<Integer> groupList = new ArrayList<Integer>();

	/**
	 * Calculates a linear discriminant analysis (LDA) with all necessary
	 * 
	 * @param p
	 *            Set to true, if the probability estimation should be based on
	 *            the real group sizes (true), or if the share of each group
	 *            should be equal
	 */
	
	public C_LDA(float[][] tr,boolean p){
		float[][] d=new float[tr.length][tr[0].length-1];
		int[] g=new int[tr.length];
		for (int i = 0; i <tr.length; i++) {
			g[i]=(int)tr[i][tr[0].length-1];
			//println("group:"+g[i]);
			for (int j = 0; j < d[0].length; j++) {
				d[i][j]=tr[i][j];
				//println("data:"+d[i][j]);
			}
		}
		LDAM(d,g,p);
	}
	
	@SuppressWarnings("unchecked")
	public void LDAM(float[][] d, int[] g, boolean p) {
		// check if data and group array have the same size
		if (d.length != g.length)
			return;

		float[][] data = new float[d.length][d[0].length];
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				data[i][j] = d[i][j];
			}
		}
		int[] group = new int[g.length];
		for (int j = 0; j < g.length; j++) {
			group[j] = g[j];
		}

		float[] globalMean;
		float[][][] covariance;

		// determine number and label of groups
		for (int i = 0; i < group.length; i++) {
			if (!groupList.contains(group[i])) {
				groupList.add(group[i]);
			}
		}

		// divide data into subsets
		ArrayList<float[]>[] subset = new ArrayList[groupList.size()];
		for (int i = 0; i < subset.length; i++) {
			subset[i] = new ArrayList<float[]>();
			for (int j = 0; j < data.length; j++) {
				if (group[j] == groupList.get(i)) {
					subset[i].add(data[j]);
				}
			}
		}

		// calculate group mean
		groupMean = new float[subset.length][data[0].length];
		for (int i = 0; i < groupMean.length; i++) {
			for (int j = 0; j < groupMean[i].length; j++) {
				groupMean[i][j] = getGroupMean(j, subset[i]);
			}
		}

		// calculate global mean
		globalMean = new float[data[0].length];
		for (int i = 0; i < data[0].length; i++) {
			globalMean[i] = getGlobalMean(i, data);
		}

		// correct subset data
		for (int i = 0; i < subset.length; i++) {
			for (int j = 0; j < subset[i].size(); j++) {
				float[] v = subset[i].get(j);

				for (int k = 0; k < v.length; k++)
					v[k] = v[k] - globalMean[k];

				subset[i].set(j, v);
			}
		}

		// calculate covariance
		covariance = new float[subset.length][globalMean.length][globalMean.length];
		for (int i = 0; i < covariance.length; i++) {
			for (int j = 0; j < covariance[i].length; j++) {
				for (int k = 0; k < covariance[i][j].length; k++) {
					for (int l = 0; l < subset[i].size(); l++)
						covariance[i][j][k] += (subset[i].get(l)[j] * subset[i]
								.get(l)[k]);

					covariance[i][j][k] = covariance[i][j][k]
							/ subset[i].size();
				}
			}
		}

		// calculate pooled within group covariance matrix and invert it
		pooledInverseCovariance = new float[globalMean.length][globalMean.length];
		for (int j = 0; j < pooledInverseCovariance.length; j++) {
			for (int k = 0; k < pooledInverseCovariance[j].length; k++) {
				for (int l = 0; l < subset.length; l++) {
					pooledInverseCovariance[j][k] += ((float) subset[l].size() / (float) data.length)
							* covariance[l][j][k];
				}
			}
		}

		pooledInverseCovariance = FactoryUtils.toFloatArray2D(new Matrix(FactoryUtils.toDoubleArray2D(pooledInverseCovariance)).inverse()
				.getArray());

		// calculate probability for different groups
		this.probability = new float[subset.length];
		if (!p) {
			float prob = 1.0f / groupList.size();
			for (int i = 0; i < groupList.size(); i++) {
				this.probability[i] = prob;
			}
		} else {
			for (int i = 0; i < subset.length; i++) {
				this.probability[i] = (float) subset[i].size()
						/ (float) data.length;
			}
		}
	}

	private float getGroupMean(int column, ArrayList<float[]> data) {
		float[] d = new float[data.size()];
		for (int i = 0; i < data.size(); i++) {
			d[i] = data.get(i)[column];
		}

		return getMean(d);
	}

	private float getGlobalMean(int column, float data[][]) {
		float[] d = new float[data.length];
		for (int i = 0; i < data.length; i++) {
			d[i] = data[i][column];
		}

		return getMean(d);
	}

	/**
	 * Calculates the discriminant function values for the different groups
	 * 
	 * @param values
	 * @return
	 */
	public float[] getDiscriminantFunctionValues(float[] values) {
		float[] function = new float[groupList.size()];
		for (int i = 0; i < groupList.size(); i++) {
			float[] tmp = matrixMultiplication(groupMean[i],
					pooledInverseCovariance);
			function[i] = (matrixMultiplication(tmp, values))
					- (.5f * matrixMultiplication(tmp, groupMean[i]))
					+ (float)Math.log(probability[i]);
		}

		return function;
	}

	/**
	 * Calculates the discriminant function values for the different groups based on Mahalanobis distance
	 * 
	 * @param values
	 * @return
	 */
	// TODO has to be tested yet
	public float[] getMahalanobisDistance(float[] values) {
		float[] function = new float[groupList.size()];
		for (int i = 0; i < groupList.size(); i++) {
			float[] dist = new float[groupMean[i].length];
			for (int j = 0; j < dist.length; j++)
				dist[j] = values[j] - groupMean[i][j];
			function[i] = matrixMultiplication(matrixMultiplication(dist,
					this.pooledInverseCovariance), dist);
		}

		return function;
	}

	/**
	 * Predict the membership of an object to one of the different groups based on Mahalanobis distance
	 * 
	 * @param values
	 * @return the group
	 */
	// TODO has to be tested yet
	public int predictM(float[] values) {
		int group = -1;
		float max = Float.NEGATIVE_INFINITY;
		float[] discr = this.getMahalanobisDistance(values);
		for (int i = 0; i < discr.length; i++) {
			if (discr[i] > max) {
				max = discr[i];
				group = groupList.get(i);
			}
		}

		return group;
	}

	/**
	 * Calculates the probability for the membership in the different groups
	 * 
	 * @param values
	 * @return the probabilities
	 */
	public float[] getProbabilityEstimates(float[] values) {
		// TODO
		return new float[] {};
	}

	/**
	 * Returns the weight for the linear fisher's discrimination functions
	 * 
	 * @return the weights
	 */
	public float[] getFisherWeights() {
		// TODO
		return new float[] {};
	}

	/**
	 * Predict the membership of an object to one of the different groups.
	 * 
	 * @param values
	 * @return the group
	 */
	public int predict(float[] values) {
		int group = -1;
		float max = Float.NEGATIVE_INFINITY;
		float[] discr = this.getDiscriminantFunctionValues(values);
		for (int i = 0; i < discr.length; i++) {
			if (discr[i] > max) {
				max = discr[i];
				group = groupList.get(i);
			}
		}

		return group;
	}

	/**
	 * Multiplies two matrices and returns the result as a float[][]-array.
	 * Please not, that the number of rows in matrix a must be equal to the
	 * number of columns in matrix b
	 * 
	 * @param a
	 *            the first matrix
	 * @param b
	 *            the second matrix
	 * @return the resulting matrix
	 */
	@SuppressWarnings("unused")
	private float[][] matrixMultiplication(final float[][] matrixA,
			final float[][] matrixB) {
		int rowA = matrixA.length;
		int colA = matrixA[0].length;
		int colB = matrixB[0].length;

		float c[][] = new float[rowA][colB];
		for (int i = 0; i < rowA; i++) {
			for (int j = 0; j < colB; j++) {
				c[i][j] = 0;
				for (int k = 0; k < colA; k++) {
					c[i][j] = c[i][j] + matrixA[i][k] * matrixB[k][j];
				}
			}
		}

		return c;
	}

	/**
	 * Multiplies two matrices and returns the result as a float[]-array.
	 * Please not, that the number of rows in matrix a must be equal to the
	 * number of columns in matrix b
	 * 
	 * @param a
	 *            the first matrix
	 * @param b
	 *            the second matrix
	 * @return the resulting matrix
	 */
	private float[] matrixMultiplication(float[] matrixA, float[][] matrixB) {

		float c[] = new float[matrixA.length];
		for (int i = 0; i < matrixA.length; i++) {
			c[i] = 0;
			for (int j = 0; j < matrixB[i].length; j++) {
				c[i] += matrixA[i] * matrixB[i][j];
			}
		}

		return c;
	}

	/**
	 * Multiplies two matrices and returns the result as a float (the second
	 * matrix is transposed automatically). Please note, that the number of rows
	 * in matrix a must be equal to the number of columns in matrix b
	 * 
	 * @param a
	 *            the first matrix
	 * @param b
	 *            the second matrix
	 * @return the resulting matrix
	 */
	private float matrixMultiplication(float[] matrixA, float[] matrixB) {

		float c = 0f;
		for (int i = 0; i < matrixA.length; i++) {
			c += matrixA[i] * matrixB[i];
		}

		return c;
	}

	/**
	 * Transposes a matrix
	 * 
	 * @param matrix
	 *            the matrix to transpose
	 * @return the transposed matrix
	 */
	@SuppressWarnings("unused")
	private float[][] transpose(final float[][] matrix) {
		float[][] trans = new float[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				trans[j][i] = matrix[i][j];
			}
		}

		return trans;
	}

	/**
	 * Transposes a matrix
	 * 
	 * @param matrix
	 *            the matrix to transpose
	 * @return the transposed matrix
	 */
	@SuppressWarnings("unused")
	private float[][] transpose(final float[] matrix) {
		float[][] trans = new float[1][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			trans[0][i] = matrix[i];
		}

		return trans;
	}

	/**
	 * Returns the mean of the given values. On error or empty data returns 0.
	 * 
	 * @param values
	 *            The values.
	 * @return The mean.
	 * @since 1.5
	 */
	public static float getMean(final float[] values) {
		if (values == null || values.length == 0)
			return Float.NaN;

		float mean = 0.0f;

		for (int index = 0; index < values.length; index++)
			mean += values[index];

		return mean / (float) values.length;
	}

	/**
	 * Test case with the original values from the tutorial of Kardi Teknomo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		int[] group = { 1, 1, 1, 1, 2, 2, 2 };
//		float[][] data = { { 2.95, 6.63 }, { 2.53, 7.79 }, { 3.57, 5.65 },
//				{ 3.16, 5.47 }, { 2.58, 4.46 }, { 2.16, 6.22 }, { 3.27, 3.52 } };
//		int[] group = {1,2,1,2,1,1,2};
//		float[][] data = { { 2.53, 7.79 }, { 2.58, 4.46 }, { 3.57, 5.65 }, { 3.27, 3.52 },
//				{ 3.16, 5.47 },{ 2.95, 6.63 }, { 2.16, 6.22 } };
		//int[] group = { 1, 1, 1, 1, 2, 2, 2 };
//		float[][] trData = { 
//				{ 2.95, 6.63,2.34,0 },
//				{ 2.58, 4.46,3.43,1 }, 
//				{ 2.53, 7.79,2.41,0 }, 
//				{ 2.16, 6.22,4.44,1 }, 
//				{ 3.57, 5.65,2.55,0 },
//				{ 3.27, 3.52,3.01,1 }, 
//				{ 3.16, 5.47,2.78,0 } 
//				};
		
		float[][] tr={
//				{-0.199f,-0.321f,-0.079f,-0.094,2.018,0 },
//				{-0.199f,-0.321f,-0.079f,-0.198,0.766,0 },
//				{-0.199f,-0.321f,-0.079f,-0.484,0.781,0 },
				{-0.199f,-0.321f,-0.079f,3.054f,-0.35f,0 },
				{-0.199f,-0.321f,-0.079f,1.819f,-0.544f,0 },
				{-0.199f,-0.321f,-0.079f,0.794f,-0.497f,0 },
				{-0.199f,3.088f,-0.079f,-0.462f,0.694f,0 },
				{-0.199f,-0.321f,-0.079f,-0.551f,1.024f,0 },
				{-0.199f,-0.321f,-0.079f,-0.489f,0.932f,0 },
				{-0.199f,-0.321f,-0.079f,-0.653f,0.741f,0 },
				{-0.199f,3.088f,-0.079f,-0.697f,0.764f,0 },
				{-0.144f,3.088f,-0.079f,-0.609f,0.833f,0 },
				{-0.199f,-0.321f,-0.079f,-0.673f,0.682f,0 },
				{2.182f,-0.321f,-0.079f,-0.417f,0.687f,0 },
				{-0.199f,-0.321f,-0.079f,-0.719f,0.584f,0 },
				{-0.199f,-0.321f,-0.079f,-0.159f,0.7f,0 },
				{-0.199f,3.088f,-0.079f,-0.597f,0.181f,0 },
				{-0.199f,-0.321f,-0.079f,-0.665f,-1.661f,0 },
				{-0.199f,-0.321f,-0.079f,-0.534f,0.554f,0 },
				{1.019f,-0.321f,-0.079f,-0.661f,0.582f,0 },
				{4.73f,-0.321f,-0.079f,-0.664f,-1.618f,0 },
				{-0.199f,-0.321f,-0.079f,-0.14f,0.84f,0 },
				{-0.199f,-0.321f,-0.079f,-0.537f,-1.666f,0 },
				{-0.199f,-0.321f,-0.079f,-0.655f,0.945f,0 },
				{-0.088f,-0.321f,-0.079f,0.349f,0.651f,0 },
				{-0.199f,-0.321f,-0.079f,0.516f,0.703f,0 },
				{-0.088f,-0.321f,-0.079f,-0.452f,0.485f,0 },
				{-0.199f,-0.321f,-0.079f,-0.448f,0.311f,0 },
				{-0.144f,-0.321f,-0.079f,-0.606f,0.326f,0 },
				{-0.199f,-0.321f,-0.079f,-0.374f,0.39f,0 },
				{-0.199f,-0.321f,-0.079f,-0.659f,0.562f,0 },
				{-0.199f,-0.321f,-0.079f,-0.706f,0.625f,0 },
				{-0.199f,-0.321f,-0.079f,-0.7f,0.017f,0 },
				{-0.199f,-0.321f,-0.079f,-0.382f,-1.652f,0 },
				{-0.199f,-0.321f,-0.079f,-0.592f,0.576f,0 },
				{-0.199f,-0.321f,-0.079f,-0.495f,1.105f,0 },
				{-0.144f,-0.321f,-0.079f,-0.716f,-1.601f,0 },
				{-0.199f,-0.321f,-0.079f,-0.714f,0.748f,0 },
				{-0.199f,-0.321f,-0.079f,-0.521f,-1.616f,0 },
				{0.132f,-0.321f,-0.079f,-0.338f,1.748f,0 }, 
				{-0.199f,-0.321f,-0.079f,1.382f,0.048f,0 },
				{-0.199f,-0.321f,-0.079f,0.085f,0.019f,0 },
				{-0.199f,-0.321f,-0.079f,-0.736f,1.09f,0 },
				{-0.199f,-0.321f,-0.079f,-0.738f,-1.517f,0 },
				{-0.199f,-0.321f,-0.079f,-0.738f,1.013f,0 },
				{-0.199f,-0.321f,-0.079f,0.5f,-1.456f,0 },
				{-0.199f,-0.321f,-0.079f,-0.235f,0.469f,0 },
				{-0.199f,-0.321f,-0.079f,0.833f,0.303f,0 },
				{-0.199f,-0.321f,-0.079f,-0.642f,0.654f,0 },
				{-0.199f,-0.321f,-0.079f,-0.709f,0.777f,0 },
				{-0.199f,-0.321f,-0.079f,-0.704f,-1.605f,0 },
				{-0.199f,-0.321f,-0.079f,-0.653f,1.763f,0 },
				{-0.199f,3.088f,-0.079f,-0.71f,0.741f,0 },
				{-0.199f,-0.321f,-0.079f,-0.71f,0.871f,0 },
				{-0.199f,-0.321f,-0.079f,-0.521f,0.511f,0 },
				{-0.199f,-0.321f,-0.079f,-0.595f,1.045f,0 },
				{-0.199f,-0.321f,-0.079f,-0.599f,0.923f,0 },
				{-0.199f,-0.321f,-0.079f,-0.126f,0.567f,0 },
				{-0.199f,-0.321f,-0.079f,-0.481f,0.088f,0 },
				{-0.199f,-0.321f,-0.079f,-0.467f,0.036f,0 },
				{10.38f,-0.321f,-0.079f,0.484f,-0.192f,0 },
				{1.351f,-0.321f,-0.079f,-0.403f,-1.514f,0 },
				{0.52f,-0.321f,-0.079f,-0.284f,0.488f,0 },
				{-0.199f,-0.321f,-0.079f,-0.729f,1.098f,0 },
				{-0.033f,-0.321f,-0.079f,-0.736f,1.034f,0 },
				{-0.144f,-0.321f,-0.079f,-0.736f,0.966f,0 },
				{-0.199f,-0.321f,-0.079f,-0.331f,-1.62f,0 },
				{-0.199f,-0.321f,-0.079f,-0.391f,-1.623f,0 },
				{-0.199f,3.088f,-0.079f,-0.533f,0.704f,0 }, 
				{-0.199f,-0.321f,-0.079f,0.411f,0.213f,0 },
				{-0.199f,-0.321f,-0.079f,-0.641f,0.318f,0 },
				{-0.199f,-0.321f,-0.079f,-0.375f,0.41f,0 },
				{-0.199f,-0.321f,-0.079f,-0.716f,-1.597f,0 },
				{-0.199f,-0.321f,-0.079f,-0.724f,-1.598f,0 },
				{-0.199f,3.088f,-0.079f,-0.708f,1.132f,0 },
				{-0.199f,-0.321f,-0.079f,-0.678f,0.912f,0 },
				{-0.199f,-0.321f,-0.079f,-0.495f,-1.59f,0 },
				{-0.199f,-0.321f,-0.079f,-0.679f,1.095f,0 },
				{-0.199f,-0.321f,-0.079f,0.885f,0.091f,0 },
				{0.243f,3.088f,-0.079f,-0.531f,0.305f,0 },
				{-0.199f,-0.321f,-0.079f,-0.462f,0.204f,0 },
				{0.575f,-0.321f,-0.079f,3.335f,-0.22f,0 },
				{-0.033f,-0.321f,-0.079f,0.241f,-1.684f,0 },
				{-0.199f,-0.321f,-0.079f,0.886f,-1.682f,0 },
				{-0.199f,-0.321f,-0.079f,-0.594f,0.88f,0 },
				{-0.199f,-0.321f,-0.079f,-0.635f,-1.646f,0 },
				{-0.144f,-0.321f,-0.079f,-0.619f,0.887f,0 },
				{0.077f,-0.321f,-0.079f,1.102f,-1.679f,1 },
				{0.465f,-0.321f,-0.079f,1.26f,0.124f,1 },
				{-0.033f,-0.321f,-0.079f,0.21f,0.41f,1 },
				{-0.199f,-0.321f,-0.079f,2.574f,0.566f,1 },
				{-0.199f,-0.321f,-0.079f,2.574f,0.566f,1 },
				{-0.199f,-0.321f,-0.079f,0.741f,-1.654f,1 },
				{-0.199f,-0.321f,-0.079f,0.146f,0.386f,1 },
				{-0.199f,-0.321f,-0.079f,0.52f,0.404f,1 },
				{-0.199f,-0.321f,-0.079f,-0.61f,-0.301f,1 },
				{-0.199f,3.088f,-0.079f,-0.218f,0.494f,1 },
				{-0.199f,-0.321f,-0.079f,-0.687f,0.359f,1 },
				{-0.199f,-0.321f,-0.079f,-0.703f,0.579f,1 },
				{-0.199f,-0.321f,-0.079f,-0.594f,0.652f,1 },
				{-0.088f,-0.321f,-0.079f,1.411f,0.88f,1 },
				{-0.199f,-0.321f,-0.079f,0.093f,0.051f,1 },
				{-0.199f,-0.321f,-0.079f,-0.459f,0.209f,1 },
				{-0.199f,-0.321f,-0.079f,-0.645f,-1.542f,1 },
				{-0.199f,-0.321f,-0.079f,-0.696f,0.653f,1 },
				{-0.199f,-0.321f,-0.079f,-0.706f,-1.534f,1 },
				{-0.144f,-0.321f,-0.079f,0.647f,0.45f,1 },
				{-0.199f,-0.321f,-0.079f,0.169f,1.465f,1 },
				{-0.199f,-0.321f,-0.079f,-0.452f,0.491f,1 },
				{-0.199f,-0.321f,-0.079f,-0.653f,0.642f,1 },
				{-0.199f,-0.321f,-0.079f,-0.599f,0.432f,1 },
				{-0.199f,-0.321f,-0.079f,-0.46f,0.482f,1 },
				{-0.199f,-0.321f,-0.079f,0.766f,0.238f,1 },
				{0.021f,-0.321f,-0.079f,1.058f,0.273f,1 },
				{-0.199f,-0.321f,-0.079f,1.551f,0.401f,1 },
				{-0.088f,-0.321f,-0.079f,1.325f,-1.437f,1 },
				{0.243f,-0.321f,-0.079f,2.085f,-0.0040f,1 },
				{-0.144f,-0.321f,-0.079f,-0.317f,0.33f,1 },
				{-0.199f,-0.321f,-0.079f,0.041f,-1.625f,1 },
				{-0.199f,-0.321f,-0.079f,0.342f,-1.611f,1 },
				{-0.199f,-0.321f,-0.079f,-0.057f,-1.627f,1 },
				{-0.199f,-0.321f,-0.079f,2.912f,0.168f,1 },
				{-0.199f,-0.321f,-0.079f,0.77f,0.351f,1 },
				{-0.199f,3.088f,-0.079f,1.817f,0.163f,1 },
				{-0.199f,3.088f,-0.079f,2.135f,0.29f,1 },
				{-0.144f,-0.321f,-0.079f,1.14f,-1.672f,1 },
				{-0.199f,-0.321f,-0.079f,1.461f,-1.672f,1 },
				{-0.144f,-0.321f,-0.079f,0.708f,-1.657f,1 },
				{-0.199f,-0.321f,-0.079f,-0.075f,0.369f,1 },
				{0.354f,-0.321f,-0.079f,0.498f,0.34f,1 },
				{-0.199f,-0.321f,-0.079f,-0.613f,0.258f,1 },
				{-0.199f,-0.321f,-0.079f,-0.57f,-1.66f,1 },
				{-0.199f,-0.321f,-0.079f,-0.667f,-1.65f,1 },
				{-0.199f,-0.321f,-0.079f,0.269f,-0.04f,1 },
				{0.132f,3.088f,-0.079f,0.117f,-1.66f,1 },
				{-0.199f,-0.321f,-0.079f,-0.502f,0.355f,1 },
				{-0.199f,-0.321f,-0.079f,0.405f,0.458f,1 },
				{-0.199f,-0.321f,-0.079f,-0.252f,-1.61f,1 },
				{-0.199f,-0.321f,-0.079f,-0.285f,0.422f,1 },
				{-0.199f,-0.321f,-0.079f,-0.242f,-1.637f,1 },
				{-0.199f,-0.321f,-0.079f,-0.338f,0.614f,1 },
				{-0.199f,-0.321f,-0.079f,-0.571f,-1.647f,1 },
				{-0.199f,-0.321f,-0.079f,-0.355f,0.113f,1 },
				{-0.199f,-0.321f,-0.079f,-0.622f,0.656f,1 },
				{-0.199f,-0.321f,-0.079f,-0.5f,0.673f,1 },
				{-0.199f,-0.321f,12.53f,-0.598f,-1.591f,1 },
				{0.077f,-0.321f,-0.079f,-0.633f,-1.619f,1 },
				{0.021f,-0.321f,-0.079f,-0.717f,-1.569f,1 },
				{-0.199f,-0.321f,-0.079f,5.481f,0.394f,1 },
				{-0.199f,-0.321f,-0.079f,3.723f,0.276f,1 },
				{-0.199f,-0.321f,-0.079f,1.749f,0.349f,1 },
//				{-0.199f,-0.321f,-0.079f,-0.619,0.862,1 },
//				{-0.199f,3.088,-0.079f,-0.655,-1.171,1 },
//				{-0.199f,-0.321f,-0.079f,-0.679,0.736,1 },
				{-0.199f,-0.321f,-0.079f,0.847f,0.399f,1 },
				{-0.199f,-0.321f,-0.079f,0.847f,0.399f,1 },
				{3.733f,3.088f,-0.079f,-0.486f,0.531f,1 },
				{0.188f,3.088f,-0.079f,0.671f,0.542f,1 },
				{-0.144f,-0.321f,-0.079f,1.187f,0.917f,1 },
				{-0.199f,-0.321f,-0.079f,-0.087f,-1.634f,1 },
				{-0.199f,-0.321f,-0.079f,0.167f,0.81f,1 }

				
		};
		//println(data.length+":"+data[0].length);
		
		C_LDA test = new C_LDA(tr,true);
//		float[] t1={-0.199f,-0.321f,-0.079f,-0.094,2.018 };
//		float[] t2={-0.199f,-0.321f,-0.079f,-0.198,0.766 };
//		float[] t3={-0.199f,-0.321f,-0.079f,-0.484,0.781 };

		float[] t1={-0.179f,-0.421f,-0.019f,-0.699f,1.862f };
//		float[] t2={-0.199f,3.088,-0.079f,-0.655,-1.171 };
//		float[] t3={-0.199f,-0.321f,-0.079f,-0.679,0.736 };
//
//		float[] testData = { 2.81, 5.46,1.78 };
		
		//test
		//float[] values = test.getDiscriminantFunctionValues(testData);
//		float[] values = test.getDiscriminantFunctionValues(t1);
//		for(int i = 0; i < values.length; i++){
//			System.out.println("Discriminant function " + (i+1) + ": " + values[i]);	
//		}
//		
		System.out.println("Predicted group: " + test.predict(t1));
	}

//	private static void println(String str) {
//		System.out.println(str);
//	}
}
