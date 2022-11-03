/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.training;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.Predictor;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jazari.deep_learning.ai.djl.examples.training.TrainTrafikLevha.getSsdPredictBlock;
import static jazari.deep_learning.ai.djl.examples.training.TrainTrafikLevha.getSsdTrainBlock;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestTrafikLevha {

    public static void main(String[] args) throws IOException, MalformedModelException, TranslateException {
        int n = predict("build/model", "C:\\ds_teknofest\\recorded_images\\ds_cv\\images_ds\\train\\" + (50) + ".jpg");
//        long t = FactoryUtils.tic();
//        for (int i = 0; i < 50; i++) {
////            int n = predict("build/model", "images/teknofest_test/" + i + ".jpg");
//            int n = predict("build/model", "C:\\ds_teknofest\\recorded_images\\ds_cv\\images_ds\\train\\" + (50 + i) + ".jpg");
//            System.out.println("n = " + n);
//            t = FactoryUtils.toc(t);
//            CMatrix cm = CMatrix.getInstance()
//                    .imread("build/model/levha_output.png")
//                    .imshowRefresh();
//        }

    }

    public static String lbl = "none,"
            + "trafik_isiklari,"
            + "trafik_levha_azami_hiz_20,"
            + "trafik_levha_azami_hiz_30,"
            + "trafik_levha_dur,"
            + "trafik_levha_durak,"
            + "trafik_levha_engel,"
            + "trafik_levha_engelli,"
            + "trafik_levha_giris_yok,"
            + "trafik_levha_hiz_sinirlamasi_sonu,"
            + "trafik_levha_ileri_veya_saga_mecburi_yol,"
            + "trafik_levha_ileri_veya_sola_mecburi_yol,"
            + "trafik_levha_ileriden_saga_mecburi_yol,"
            + "trafik_levha_ileriden_sola_mecburi_yol,"
            + "trafik_levha_park,"
            + "trafik_levha_park_etmek_yasaktir,"
            + "trafik_levha_park_etmek_yasaktir_2,"
            + "trafik_levha_saga_donulmez,"
            + "trafik_levha_sola_donulmez,"
            + "trafik_levha_trafige_kapali,"
            + "trafik_levha_yaya_gecidi,"
            + "trafik_levha_yol_calismasi,"
            + "yaya_insan,"
            + "zemin_durak_siyah,"
            + "zemin_engel,"
            + "zemin_yaya_gecidi,"
            + "trafik_isiklari_red,"
            + "trafik_isiklari_green";

    public static int predict(String outputDir, String imageFile)
            throws IOException, MalformedModelException, TranslateException {

//        List lst = Arrays.asList(lbl.split(","));
//        List lst = Arrays.asList("trafik_levha_durak");
//        System.out.println("lst = " + lst.size());
        List<String> lst = new ArrayList();
        lst.add("trafik_levha_durak");

        try (Model model = Model.newInstance("levha-ssd")) {
            float detectionThreshold = 0.1f;
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
                            //                            .optSynset(lst)
                            //                            .optSynset(Collections.singletonList(lbl))
//                            .optSynset(Arrays.asList(lbl.split(",")))
                                                        .optSynset(Arrays.asList("Durak", "None"))
//                                                        .optSynset(Collections.singletonList("Durak"))
                            .optThreshold(detectionThreshold)
                            .build();
            try (Predictor<Image, DetectedObjects> predictor = model.newPredictor(translator)) {
                Image image = ImageFactory.getInstance().fromFile(imagePath);
                DetectedObjects detectedObjects = predictor.predict(image);
                image.drawBoundingBoxes(detectedObjects);
                Path out = Paths.get(outputDir).resolve("levha_output.png");
                image.save(Files.newOutputStream(out), "png");
                return detectedObjects.getNumberOfObjects();
            }
        }
    }
}
