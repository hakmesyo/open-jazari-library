/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.detection;

import ai.djl.Application.CV;
import ai.djl.basicdataset.BasicDatasets;
import ai.djl.basicdataset.cv.ImageDataset;
import ai.djl.basicdataset.cv.ObjectDetectionDataset;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.Point;
import ai.djl.modality.cv.output.Rectangle;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.repository.Artifact;
import ai.djl.repository.MRL;
import ai.djl.repository.Repository;
import ai.djl.translate.Pipeline;
import ai.djl.util.JsonUtils;
import ai.djl.util.PairList;
import ai.djl.util.Progress;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author cezerilab
 */
public class DetectionTrafikLevha extends ObjectDetectionDataset {

    private static final String VERSION = "1.0";
    private static final String ARTIFACT_ID = "pikachu";

    private Usage usage;
    private List<Path> imagePaths;
    private PairList<Long, Rectangle> labels;

    private MRL mrl;
    private boolean prepared;

    protected DetectionTrafikLevha(Builder builder) {
        super(builder);
        usage = builder.usage;
        mrl = builder.getMrl();
        imagePaths = new ArrayList<>();
        labels = new PairList<>();
    }

    /**
     * Creates a new builder to build a {@link DetectionTrafikLevha}.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare(Progress progress) throws IOException {
        if (prepared) {
            return;
        }

        Artifact artifact = mrl.getDefaultArtifact();
        mrl.prepare(artifact, progress);

        Path root = mrl.getRepository().getResourceDirectory(artifact);
        Path usagePath;
        switch (usage) {
            case TRAIN:
                usagePath = Paths.get("train");
                break;
            case TEST:
                usagePath = Paths.get("test");
                break;
            case VALIDATION:
            default:
                throw new UnsupportedOperationException("Validation data not available.");
        }
        usagePath = root.resolve(usagePath);
        Path indexFile = usagePath.resolve("index.file");
        try (Reader reader = Files.newBufferedReader(indexFile)) {
            Type mapType = new TypeToken<Map<String, List<List<Float>>>>() {
            }.getType();
            Map<String, List<List<Float>>> metadata = JsonUtils.GSON.fromJson(reader, mapType);
            for (Map.Entry<String, List<List<Float>>> entry : metadata.entrySet()) {
                String imgName = entry.getKey();

                List<List<Float>> list_label = entry.getValue();
                for (List<Float> label : list_label) {
                    long objectClass = label.get(4).longValue();
                    Rectangle objectLocation
                            = new Rectangle(
                                    new Point(label.get(5), label.get(6)), label.get(7), label.get(8));
                    labels.add(objectClass, objectLocation);
                    imagePaths.add(usagePath.resolve(imgName));
                }
            }
        }
        prepared = true;
    }
    
    public void prepare(Progress progress,String path) throws IOException {
        if (prepared) {
            return;
        }

        Artifact artifact = mrl.getDefaultArtifact();
        mrl.prepare(artifact, progress);

        Path root = mrl.getRepository().getResourceDirectory(artifact);
        Path usagePath;
        switch (usage) {
            case TRAIN:
                usagePath = Paths.get("train");
                break;
            case TEST:
                usagePath = Paths.get("test");
                break;
            case VALIDATION:
            default:
                throw new UnsupportedOperationException("Validation data not available.");
        }
//        usagePath = root.resolve(usagePath);
        usagePath = Paths.get(path).resolve(usagePath);
        Path indexFile = usagePath.resolve("index.file");
        try (Reader reader = Files.newBufferedReader(indexFile)) {
            Type mapType = new TypeToken<Map<String, List<Float>>>() {}.getType();
            Map<String, List<Float>> metadata = JsonUtils.GSON.fromJson(reader, mapType);
            for (Map.Entry<String, List<Float>> entry : metadata.entrySet()) {
                String imgName = entry.getKey();
                imagePaths.add(usagePath.resolve(imgName));

                List<Float> label = entry.getValue();
                long objectClass = label.get(4).longValue();
                Rectangle objectLocation =
                        new Rectangle(
                                new Point(label.get(5), label.get(6)), label.get(7), label.get(8));
                labels.add(objectClass, objectLocation);
            }
            
//            Type mapType = new TypeToken<Map<String, List<List<Float>>>>() {}.getType();
//            Map<String, List<List<Float>>> metadata = JsonUtils.GSON.fromJson(reader, mapType);
//            for (Map.Entry<String, List<List<Float>>> entry : metadata.entrySet()) {
//                String imgName = entry.getKey();
//
//                List<List<Float>> list_label = entry.getValue();
//                for (List<Float> label : list_label) {
//                    long objectClass = label.get(4).longValue();
//                    Rectangle objectLocation
//                            = new Rectangle(
//                                    new Point(label.get(5), label.get(6)), label.get(7), label.get(8));
//                    labels.add(objectClass, objectLocation);
//                    imagePaths.add(usagePath.resolve(imgName));
//                }
//            }
        }
        prepared = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PairList<Long, Rectangle> getObjects(long index) {
        return new PairList<>(Collections.singletonList(labels.get((int) index)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long availableSize() {
        return imagePaths.size();
    }

    @Override
    protected Image getImage(long index) throws IOException {
        int idx = Math.toIntExact(index);
        return ImageFactory.getInstance().fromFile(imagePaths.get(idx));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getImageWidth() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getImageHeight() {
        return Optional.empty();
    }

    /**
     * A builder for a {@link DetectionTrafikLevha}.
     */
    public static final class Builder extends ImageDataset.BaseBuilder<Builder> {

        Repository repository;
        String groupId;
        String artifactId;
        Usage usage;

        /**
         * Constructs a new builder.
         */
        Builder() {
            repository = BasicDatasets.REPOSITORY;
            groupId = BasicDatasets.GROUP_ID;
            artifactId = ARTIFACT_ID;
            usage = Usage.TRAIN;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder self() {
            return this;
        }

        /**
         * Sets the optional usage.
         *
         * @param usage the usage
         * @return this builder
         */
        public Builder optUsage(Usage usage) {
            this.usage = usage;
            return self();
        }

        /**
         * Sets the optional repository.
         *
         * @param repository the repository
         * @return this builder
         */
        public Builder optRepository(Repository repository) {
            this.repository = repository;
            return self();
        }

        /**
         * Sets optional groupId.
         *
         * @param groupId the groupId}
         * @return this builder
         */
        public Builder optGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        /**
         * Sets the optional artifactId.
         *
         * @param artifactId the artifactId
         * @return this builder
         */
        public Builder optArtifactId(String artifactId) {
            if (artifactId.contains(":")) {
                String[] tokens = artifactId.split(":");
                groupId = tokens[0];
                this.artifactId = tokens[1];
            } else {
                this.artifactId = artifactId;
            }
            return this;
        }

        /**
         * Builds the {@link DetectionTrafikLevha}.
         *
         * @return the {@link DetectionTrafikLevha}
         */
        public DetectionTrafikLevha build() {
            if (pipeline == null) {
                pipeline = new Pipeline(new ToTensor());
            }
            return new DetectionTrafikLevha(this);
        }

        MRL getMrl() {
            return repository.dataset(CV.ANY, groupId, artifactId, VERSION);
        }
    }
}
