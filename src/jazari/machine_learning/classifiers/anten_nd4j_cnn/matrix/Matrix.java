package jazari.machine_learning.classifiers.anten_nd4j_cnn.matrix;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 * Wrapper class around MTJs Matrix.
 * @link https://github.com/fommil/matrix-toolkits-java
 *
 * Having a wrapper class makes it easier to change underlying matrix lib.
 * Also, implements a few methods lacking in MTJ
 *
 * @author Johannes Amtén
 *
 */
public class Matrix implements Iterable<MatrixElement>, Serializable {

    private CMatrix myMatrix;

    public Matrix(int numRows, int numColumns) {
        myMatrix = CMatrix.getInstance(numRows, numColumns);
    }

    public Matrix(double[][] values) {
        myMatrix = CMatrix.getInstance(values);
    }

    private Matrix(CMatrix m) {
        myMatrix = m;
    }

    public int numRows() {
        return myMatrix.getRowNumber();
    }

    public int numColumns() {
        return myMatrix.getColumnNumber();
    }

    public Matrix copy() {
        Matrix ret=new Matrix(myMatrix.clone());
        return ret;
    }

    public double get(int row, int col) {
        return myMatrix.getValue(row, col);
    }

    public double[] getRow(int row) {
        double[] data = FactoryUtils.toDoubleArray1D(myMatrix.getRow(row));
        return data;
    }

    public double[] getCol(int col) {
        double[] data = FactoryUtils.toDoubleArray1D(myMatrix.getColumn(col));
        return data;
    }

    public void set(int row, int col, double v) {
        myMatrix.setValue(row, col, (float) v);
    }

    public void fill(double value) {
        myMatrix.setValue(value);
    }

    private void checkSize(Matrix m2) {
        if (numRows() != m2.numRows()) {
            throw new IndexOutOfBoundsException("A.numRows != B.numRows ("
                    + numRows() + " != " + m2.numRows() + ")");
        }
        if (numColumns() != m2.numColumns()) {
            throw new IndexOutOfBoundsException(
                    "A.numColumns != B.numColumns (" + numColumns() + " != "
                    + m2.numColumns() + ")");
        }
    }

    public Matrix add(double c, Matrix m2) {
        checkSize(m2);
        myMatrix = myMatrix.add(m2.myMatrix.multiplyScalar(c));
        return this;
    }

    public Matrix add(double c) {
        myMatrix = myMatrix.addScalar(c);
        return this;
    }

    public void add(int row, int col, double v) {
        set(row, col, get(row, col) + v);
    }

    public Matrix mult(Matrix m2) {
        myMatrix = myMatrix.mmul(m2.myMatrix);
        return this;
    }

    public Matrix mult(Matrix m2, Matrix res) {
        myMatrix=myMatrix.mmul(m2.myMatrix).mmul(res.myMatrix);
        return this;
    }

    public Matrix multElements(Matrix m2) {
        checkSize(this);
        checkSize(m2);
        myMatrix = myMatrix.multiplyElement(m2.myMatrix);
        return this;
    }

    public Matrix multElements(Matrix m2, Matrix res) {
        checkSize(m2);
        checkSize(res);
        res.myMatrix=myMatrix.multiplyElement(m2.myMatrix);
        return res;
    }

    public Matrix trans1mult(Matrix m2) {
        Matrix ret=new Matrix(numColumns(),m2.numColumns());
        m2.myMatrix=m2.myMatrix.transpose();
        myMatrix=m2.myMatrix.mmul(ret.myMatrix);
        return this;
    }

    public Matrix trans2mult(Matrix m2) {
        Matrix ret=new Matrix(numRows(),m2.numRows());
        ret.myMatrix=ret.myMatrix.transpose();
        m2.myMatrix=m2.myMatrix.transpose();
        myMatrix=m2.myMatrix.mmul(ret.myMatrix);
        return this;
    }

    public Matrix scale(double c) {
        myMatrix=myMatrix.timesScalar(c);
        return this;
    }

    public double[] getData() {
        return myMatrix.array.ravel().toDoubleVector();
    }

    public Matrix addColumns(Matrix m2) {
        if (numRows() != m2.numRows()) {
            throw new IndexOutOfBoundsException("A.numRows != B.numRows ("
                    + numRows() + " != " + m2.numRows() + ")");
        }
        myMatrix=myMatrix.cat(1, m2.myMatrix);
        return this;
    }

    public Matrix getColumns(int startCol, int endCol) {
        endCol = endCol == -1 ? numColumns() - 1 : endCol;
        myMatrix=myMatrix.clone().getColumns(startCol,endCol);
        return this;
    }

    public Matrix getRows(int startRow, int endRow) {
        endRow = endRow == -1 ? numRows() - 1 : endRow;
        myMatrix=myMatrix.getRows(startRow,endRow);
        return this;
    }

    public Iterator<MatrixElement> iterator() {
        return new Iterator<MatrixElement>() {
            private MatrixElement me = new MatrixElement(Matrix.this);

            public boolean hasNext() {
                return me.myPos < me.myData.length - 1;
            }

            public MatrixElement next() {
                me.myPos++;
                return me;
            }

            public void remove() {
                throw new UnsupportedOperationException("Nope.");
            }
        };
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < numRows(); row++) {
            for (int col = 0; col < numColumns(); col++) {
                sb.append(get(row, col)).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Needed because MTJs Matrix is not serializable
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeInt(numRows());
        out.writeInt(numColumns());
        out.writeObject(getData());
    }

    /**
     * Needed because MTJs Matrix is not serializable
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        myMatrix = CMatrix.getInstance(in.readInt(), in.readInt());
        double[] data = (double[]) in.readObject();
        System.arraycopy(data, 0, myMatrix.toDoubleArray1D(), 0, data.length);
    }
}
