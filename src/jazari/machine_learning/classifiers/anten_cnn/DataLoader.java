package jazari.machine_learning.classifiers.anten_cnn;


import java.io.IOException;
import jazari.machine_learning.classifiers.anten_cnn.matrix.Matrix;

/**
 * Interface to be implemented by a class that loads training data from disk on-demand.
 * Implement when training dataset is too large to fit in memory.
 *
 * @author Johannes Amtén
 */
public interface DataLoader {

    /**
     *
     * @param xIDs Some type of id numbers for the datapoints to load.
     * @return Training data for the specified ids
     * @throws IOException
     */
    public Matrix loadData(Matrix xIDs) throws IOException;

    /**
     *
     * @return Number of values for each datapoint
     */
    public int getDataSize();

}
