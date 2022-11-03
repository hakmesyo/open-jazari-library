/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.training;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.Predictor;
//import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.SingleShotDetectionTranslator;
import ai.djl.nn.Block;
import ai.djl.translate.TranslateException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jazari.deep_learning.ai.djl.examples.training.TrainPikachu.getSsdPredictBlock;
import static jazari.deep_learning.ai.djl.examples.training.TrainPikachu.getSsdTrainBlock;
import jazari.factory.FactoryUtils;

/**
 *
 * @author cezerilab
 */
public class TestPikachu {

    public static void main(String[] args) {
        try {
            int n = predict("build/model", "images/pikachu.jpg");
            /*
            long t = FactoryUtils.tic();
            for (int i = 0; i < 100; i++) {
            //            int n = predict("build/model", "images/teknofest_test/" + i + ".jpg");
            int n;
            try {
            n = predict("build/model", "C:\\Users\\cezerilab\\.djl.ai\\cache\\repo\\dataset\\cv\\ai\\djl\\basicdataset\\pikachu\\1.0\\train\\img_" + i + ".jpg");
            System.out.println("n = " + n);
            } catch (IOException ex) {
            Logger.getLogger(TestPikachu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedModelException ex) {
            Logger.getLogger(TestPikachu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TranslateException ex) {
            Logger.getLogger(TestPikachu.class.getName()).log(Level.SEVERE, null, ex);
            }
            t = FactoryUtils.toc(t);
            }
            */
        } catch (IOException ex) {
            Logger.getLogger(TestPikachu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedModelException ex) {
            Logger.getLogger(TestPikachu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TranslateException ex) {
            Logger.getLogger(TestPikachu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int predict(String outputDir, String imageFile)
            throws IOException, MalformedModelException, TranslateException {
        try (Model model = Model.newInstance("pikachu-ssd")) {
            float detectionThreshold = 0.8f;
            // load parameters back to original training block
            model.setBlock(getSsdTrainBlock());
            model.load(Paths.get(outputDir));
            // append prediction logic at end of training block with parameter loaded
            Block ssdTrain = model.getBlock();
            model.setBlock(getSsdPredictBlock(ssdTrain));
            Path imagePath = Paths.get(imageFile);
            SingleShotDetectionTranslator translator
                    = SingleShotDetectionTranslator.builder()
                            .addTransform(new ToTensor())
                            .optSynset(Arrays.asList("none","pikachu"))
                            .optThreshold(detectionThreshold)
                            .build();
            try (Predictor<Image, DetectedObjects> predictor = model.newPredictor(translator)) {
                Image image = ImageFactory.getInstance().fromFile(imagePath);
                DetectedObjects detectedObjects = predictor.predict(image);
                image.drawBoundingBoxes(detectedObjects);
                Path out = Paths.get(outputDir).resolve("pikachu_output.png");
                image.save(Files.newOutputStream(out), "png");
                // return number of pikachu detected
                return detectedObjects.getNumberOfObjects();
            }
        }
    }

}
