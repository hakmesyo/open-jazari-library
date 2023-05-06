/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.denemeler.face_Detection_paddle;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.ndarray.NDList;
import ai.djl.translate.NoBatchifyTranslator;
import ai.djl.translate.TranslatorContext;
import java.util.Arrays;
import java.util.List;
import static jazari.deep_learning.ai.djl.examples.denemeler.face_Detection_paddle.Deneme.processImageInput;
import static jazari.deep_learning.ai.djl.examples.denemeler.face_Detection_paddle.Deneme.processImageOutput;

/**
 *
 * @author cezerilab
 */
public class FaceTranslator implements NoBatchifyTranslator<Image, DetectedObjects> {

    private float shrink;
    private float threshold;
    private List<String> className;

    FaceTranslator(float shrink, float threshold) {
        this.shrink = shrink;
        this.threshold = threshold;
        className = Arrays.asList("Not Face", "Face");
    }

    @Override
    public DetectedObjects processOutput(TranslatorContext ctx, NDList list) {
        return processImageOutput(list, className, threshold);
    }

    @Override
    public NDList processInput(TranslatorContext ctx, Image input) {
        return processImageInput(ctx.getNDManager(), input, shrink);
    }
}
