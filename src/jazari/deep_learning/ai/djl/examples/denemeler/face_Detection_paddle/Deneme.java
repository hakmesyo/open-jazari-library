/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.denemeler.face_Detection_paddle;

/**
 *
 * @author cezerilab
 */
import ai.djl.*;
import ai.djl.inference.*;
import ai.djl.modality.*;
import ai.djl.modality.cv.*;
import ai.djl.modality.cv.output.*;
import ai.djl.modality.cv.transform.*;
import ai.djl.modality.cv.translator.*;
import ai.djl.modality.cv.util.*;
import ai.djl.ndarray.*;
import ai.djl.ndarray.types.Shape;
import ai.djl.repository.zoo.*;
import ai.djl.translate.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Deneme {

    public static void main(String[] args) throws IOException, ModelNotFoundException, MalformedModelException, TranslateException {
        String url = "https://raw.githubusercontent.com/PaddlePaddle/PaddleHub/release/v1.5/demo/mask_detection/python/images/mask.jpg";
        Image img = ImageFactory.getInstance().fromUrl(url);
        img.getWrappedImage();

        processImageInput(NDManager.newBaseManager(), img, 0.5f);

        NDArray tempOutput = NDManager.newBaseManager().create(new float[]{1f, 0.99f, 0.1f, 0.1f, 0.2f, 0.2f}, new Shape(1, 6));
        DetectedObjects testBox = processImageOutput(new NDList(tempOutput), Arrays.asList("Not Face", "Face"), 0.7f);
        Image newImage = img.duplicate();
        newImage.drawBoundingBoxes(testBox);
        newImage.getWrappedImage();

        Criteria<Image, DetectedObjects> criteria = Criteria.builder()
                .setTypes(Image.class, DetectedObjects.class)
                .optModelUrls("djl://ai.djl.paddlepaddle/face_detection/0.0.1/mask_detection")
                .optFilter("flavor", "server")
                .optTranslator(new FaceTranslator(0.5f, 0.7f))
                .build();

        ZooModel model = criteria.loadModel();
        Predictor predictor = model.newPredictor();

        DetectedObjects inferenceResult = (DetectedObjects) (predictor.predict(img));
        newImage = img.duplicate();
        newImage.drawBoundingBoxes(inferenceResult);
        newImage.getWrappedImage();

        List<DetectedObjects.DetectedObject> faces = inferenceResult.items();
        getSubImage(img, faces.get(2).getBoundingBox()).getWrappedImage();

        Criteria criteria_1 = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optModelUrls("djl://ai.djl.paddlepaddle/mask_classification/0.0.1/mask_classification")
                .optFilter("flavor", "server")
                .optTranslator(
                        ImageClassificationTranslator.builder()
                                .addTransform(new Resize(128, 128))
                                .addTransform(new ToTensor()) // HWC -> CHW div(255)
                                .addTransform(
                                        new Normalize(
                                                new float[]{0.5f, 0.5f, 0.5f},
                                                new float[]{1.0f, 1.0f, 1.0f}))
                                .addTransform(nd -> nd.flip(0)) // RGB -> GBR
                                .build())
                .build();

        ZooModel classifyModel = criteria_1.loadModel();
        Predictor classifier = classifyModel.newPredictor();

        List<String> names = new ArrayList<>();
        List<Double> prob = new ArrayList<>();
        List<BoundingBox> rect = new ArrayList<>();
        for (DetectedObjects.DetectedObject face : faces) {
            Image subImg = getSubImage(img, face.getBoundingBox());
            Classifications classifications = (Classifications)classifier.predict(subImg);
            names.add(classifications.best().getClassName());
            prob.add(face.getProbability());
            rect.add(face.getBoundingBox());
        }

        newImage = img.duplicate();
        newImage.drawBoundingBoxes(new DetectedObjects(names, prob, rect));
        newImage.getWrappedImage();

    }

    public static Image getSubImage(Image img, BoundingBox box
    ) {
        Rectangle rect = box.getBounds();
        int width = img.getWidth();
        int height = img.getHeight();
        int[] squareBox
                = extendSquare(
                        rect.getX() * width,
                        rect.getY() * height,
                        rect.getWidth() * width,
                        rect.getHeight() * height,
                        0.18);
        return img.getSubImage(squareBox[0], squareBox[1], squareBox[2], squareBox[2]);
    }

    public static int[] extendSquare(
            double xmin, double ymin, double width, double height, double percentage
    ) {
        double centerx = xmin + width / 2;
        double centery = ymin + height / 2;
        double maxDist = Math.max(width / 2, height / 2) * (1 + percentage);
        return new int[]{
            (int) (centerx - maxDist), (int) (centery - maxDist), (int) (2 * maxDist)
        };
    }

    public static NDList processImageInput(NDManager manager, Image input, float shrink) {
        NDArray array = input.toNDArray(manager);
        Shape shape = array.getShape();
        array = NDImageUtils.resize(
                array, (int) (shape.get(1) * shrink), (int) (shape.get(0) * shrink));
        array = array.transpose(2, 0, 1).flip(0); // HWC -> CHW BGR -> RGB
        NDArray mean = manager.create(new float[]{104f, 117f, 123f}, new Shape(3, 1, 1));
        array = array.sub(mean).mul(0.007843f); // normalization
        array = array.expandDims(0); // make batch dimension
        return new NDList(array);
    }

    public static DetectedObjects processImageOutput(NDList list, List<String> className, float threshold) {
        NDArray result = list.singletonOrThrow();
        float[] probabilities = result.get(":,1").toFloatArray();
        List<String> names = new ArrayList<>();
        List<Double> prob = new ArrayList<>();
        List<BoundingBox> boxes = new ArrayList<>();
        for (int i = 0; i < probabilities.length; i++) {
            if (probabilities[i] >= threshold) {
                float[] array = result.get(i).toFloatArray();
                names.add(className.get((int) array[0]));
                prob.add((double) probabilities[i]);
                boxes.add(
                        new Rectangle(
                                array[2], array[3], array[4] - array[2], array[5] - array[3]));
            }
        }
        return new DetectedObjects(names, prob, boxes);
    }

}
