/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.detection;

import ai.djl.modality.cv.output.Rectangle;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.training.dataset.Record;
import ai.djl.util.Pair;
import ai.djl.util.PairList;
import java.io.IOException;

/**
 * A helper to create {@link ai.djl.training.dataset.Dataset}s for {@link
 * ai.djl.Application.CV#OBJECT_DETECTION}.
 */
public abstract class ObjectDetectionDataset extends ImageDataset {

    /**
     * Creates a new instance of {@link ObjectDetectionDataset} with the given necessary
     * configurations.
     *
     * @param builder a builder with the necessary configurations
     */
    public ObjectDetectionDataset(ImageDataset.BaseBuilder<?> builder) {
        super(builder);
    }

    /** {@inheritDoc} */
    @Override
    public Record get(NDManager manager, long index) throws IOException {
        NDList data = new NDList(getRecordImage(manager, index));

        PairList<Long, Rectangle> objects = getObjects(index);
        float[][] labelsSplit = new float[objects.size()][5];
        for (int i = 0; i < objects.size(); i++) {
            Pair<Long, Rectangle> obj = objects.get(i);
            labelsSplit[i][0] = obj.getKey();

            Rectangle location = obj.getValue();
            labelsSplit[i][1] = (float) location.getX();
            labelsSplit[i][2] = (float) location.getY();
            labelsSplit[i][3] = (float) location.getWidth();
            labelsSplit[i][4] = (float) location.getHeight();
        }
        NDList labels = new NDList(manager.create(labelsSplit));
        return new Record(data, labels);
    }

    /**
     * Returns the list of objects in the image at the given index.
     *
     * @param index the index (if the dataset is a list of data items)
     * @return the list of objects in the image. The long is the class number of the index into the
     *     list of classes of the desired class name. The rectangle is the location of the object
     *     inside the image.
     * @throws IOException if the data could not be loaded
     */
    public abstract PairList<Long, Rectangle> getObjects(long index) throws IOException;
}