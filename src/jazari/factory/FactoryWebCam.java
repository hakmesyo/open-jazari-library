/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.factory;

import jazari.gui.FrameImage;
import jazari.image_processing.ImageProcess;
import jazari.matrix.CMatrix;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author cezerilab
 */
public class FactoryWebCam {

    //private FactoryWebCam factWebCam = new FactoryWebCam();
    public Webcam webCam;
    public WebcamPanel panel;
    private FrameImage frm = new FrameImage();
    private boolean isMotionDetectionImage = false;
    private boolean isMotionDetectionVideo = false;
    private boolean isVideoRecord = false;
    private boolean isImageRecord = false;
    private static String folderPath = "recorded";
    private static Dimension size;
    private static boolean isFlipped = false;
    public static BufferedImage currentImage;

    public FactoryWebCam() {
//        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frm.setVisible(true);

    }

    public FactoryWebCam openWebCam() {
        webCam = Webcam.getDefault();
        size = WebcamResolution.VGA.getSize();
        webCam.setViewSize(size);
        webCam.open(true);
        return this;
    }

    public FactoryWebCam openWebCam(int cameraIndex) {
        webCam = Webcam.getWebcams().get(cameraIndex);
        size = WebcamResolution.VGA.getSize();
        webCam.setViewSize(size);
        webCam.open(true);
        return this;
    }

    public FactoryWebCam openWebCam(Dimension size) {
        webCam = Webcam.getDefault();
        webCam.setCustomViewSizes(size); // register custom resolutions
        //size = WebcamResolution.VGA.getSize();
        webCam.setViewSize(size);
        webCam.open(true);
        return this;
    }

    public FactoryWebCam openWebCam(int cameraIndex, Dimension size) {
        webCam = Webcam.getWebcams().get(cameraIndex);
        webCam.setCustomViewSizes(size); // register custom resolutions
        webCam.setViewSize(size);
        //size = WebcamResolution.VGA.getSize();
        webCam.open(true);
        return this;
    }

    public FactoryWebCam startWebCAM(Dimension dim) {
        panel = new WebcamPanel(webCam);
        panel.setImageSizeDisplayed(true);
//        panel.setFPSLimited(true);
//        panel.setFPSLimit(fps);
        panel.setFPSDisplayed(true);

        JFrame window = new JFrame("Webcam");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(dim);
        window.pack();
        window.setVisible(true);
        return this;
    }

    public FactoryWebCam startWebCAM(int fps) {
        panel = new WebcamPanel(webCam);
        panel.setImageSizeDisplayed(true);
        panel.setFPSLimited(true);
        panel.setFPSLimit(fps);
        panel.setFPSDisplayed(true);

        JFrame window = new JFrame("Webcam");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(640, 480));
        window.pack();
        window.setVisible(true);

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                long t1=FactoryUtils.tic();
                while (true) {
                    try {
                        currentImage = webCam.getImage();
                        frm.setImage(currentImage,"","");
                        t1=FactoryUtils.toc(t1);
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FactoryWebCam.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }).start();
         */
        return this;
    }

    public FactoryWebCam startWebCAM(Dimension resize, int fps) {
        panel = new WebcamPanel(webCam);
        panel.setImageSizeDisplayed(true);
        //panel.setFPSDisplayed(true);
        panel.setFPSLimited(true);
        panel.setFPSLimit(fps);

        JFrame window = new JFrame("Webcam");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(resize);
        window.pack();
        window.setVisible(true);

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                long t1 = FactoryUtils.tic();
                while (true) {
                    try {
                        currentImage = webCam.getImage();
                        currentImage = ImageProcess.toBufferedImage(currentImage, 5);
                        currentImage = ImageProcess.resize(currentImage, resize.width, resize.height);
                        frm.setImage(currentImage, "", "");
                        t1 = FactoryUtils.toc(t1);
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FactoryWebCam.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }).start();
         */
        return this;
    }

    public FactoryWebCam startWithGUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                operates();
            }

        }).start();
        return this;
    }

    private void operates() {
        BufferedImage bf = webCam.getImage();
        BufferedImage prev_bf = webCam.getImage();
        float[][] bf_m = ImageProcess.bufferedImageToArray2D(bf);
        float[][] bf_prev_m = ImageProcess.bufferedImageToArray2D(prev_bf);
        float[][] diff = FactoryMatrix.clone(bf_m);
        int k = 0;
        String timeStamp = "temp";
        BufferedImage bf_rgb = null;
        long t_start = FactoryUtils.tic();
        long elapsed_time = 0;
        boolean isRecord = false;
        FactoryUtils.makeDirectory(folderPath);

        //for video
        File file = null;
        IMediaWriter writer = null;
        long start = System.currentTimeMillis();

        //warming up
        for (int i = 0; i < 1; i++) {
            bf = webCam.getImage();
            frm.setImage(bf, "", "");
            bf = ImageProcess.filterGaussian(bf, 5);
            bf = ImageProcess.toGrayLevel(bf);

            bf_m = ImageProcess.bufferedImageToArray2D(bf);
            float diffRatio = calculateDifferentPixels(bf_m, bf_prev_m);
            bf_prev_m = ImageProcess.bufferedImageToArray2D(bf);
        }

        while (true) {
            bf = webCam.getImage();
            if (isFlipped) {
                bf = ImageProcess.flipVertical(bf);
            }
            frm.setImage(bf, "", "");
            bf_rgb = ImageProcess.clone(bf);
            bf = ImageProcess.filterGaussian(bf, 5);
            bf = ImageProcess.toGrayLevel(bf);
            bf_m = ImageProcess.bufferedImageToArray2D(bf);
            float diffRatio = calculateDifferentPixels(bf_m, bf_prev_m);
            bf_prev_m = ImageProcess.bufferedImageToArray2D(bf);
            //System.out.println("diffRatio = " + diffRatio);
            if (isMotionDetectionVideo) {
                if (diffRatio != 1 && diffRatio > 0.15) {
                    if (!isRecord) {
                        k = 0;
                        timeStamp = FactoryUtils.getDateTime();
                        FactoryUtils.makeDirectory(folderPath + "/" + timeStamp);
                        file = new File(folderPath + "/" + timeStamp + "/video_captured.ts");
                        writer = ToolFactory.makeWriter(file.getAbsolutePath());
                        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
                        isRecord = true;
                        t_start = System.currentTimeMillis();
                        System.out.println(folderPath + "/" + timeStamp + "/video_captured.ts" + " videosu kaydediliyor");
                    }
                }
                elapsed_time = System.currentTimeMillis() - t_start;
                //System.out.println("<<<<<<<<<<<<<<<<<<<<<   **********************   elapsed_time = " + elapsed_time);

                if (isRecord && elapsed_time >= 5000) {
                    System.out.println("kay??t i??lemi bitti");
                    writer.close();
                    t_start = System.currentTimeMillis();
                    isRecord = false;
                }
                if (isRecord && elapsed_time < 5000) {
                    BufferedImage image = ConverterFactory.convertToType(bf_rgb, BufferedImage.TYPE_3BYTE_BGR);
                    IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);
                    IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
                    frame.setKeyFrame(k == 0);
                    frame.setQuality(0);
                    writer.encodeVideo(0, frame);
                }
                k++;

            }
            if (isMotionDetectionImage) {
                if (diffRatio != 1 && diffRatio > 0.15 && diffRatio < 0.5) {
                    if (!isRecord) {
                        k = 0;
                        timeStamp = FactoryUtils.getDateTime();
                        FactoryUtils.makeDirectory(folderPath + "/" + timeStamp);
                        isRecord = true;
                        t_start = System.currentTimeMillis();
                    }
                }
                elapsed_time = System.currentTimeMillis() - t_start;
                //System.out.println("<<<<<<<<<<<<<<<<<<<<<   **********************   elapsed_time = " + elapsed_time);

                if (isRecord && elapsed_time < 5000) {
                    System.out.println(k + ".resim kaydedildi");
                    ImageProcess.saveImageAsJPEG(folderPath + "/" + timeStamp, bf_rgb, ++k);
                } else {
                    isRecord = false;
                }

            }
        }
    }

    private static float calculateDifferentPixels(float[][] bf_m, float[][] bf_prev_m) {
        float diffRatio = 0;
        int nr = bf_m.length;
        int nc = bf_m[0].length;
        int cnt = 0;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (Math.abs((bf_m[i][j] - bf_prev_m[i][j])) >= 3) {
                    cnt++;
                }
            }
        }
        //System.out.println("cnt = " + cnt);
        diffRatio = 1.0f * cnt / (nr * nc);
        //System.out.println("diffRatio = " + diffRatio);
        return diffRatio;
    }

    public BufferedImage getImage() {
        return webCam.getImage();
    }

    public FactoryWebCam flipImageAlongVerticalAxis() {
        isFlipped = !isFlipped;
        return this;
    }

    public FactoryWebCam startMotionDetectionImage() {
        isMotionDetectionImage = true;
        isMotionDetectionVideo = false;
        return this;
    }

    public FactoryWebCam startMotionDetectionImage(String folderPath) {
        FactoryWebCam.folderPath = folderPath;
        isMotionDetectionImage = true;
        isMotionDetectionVideo = false;
        return this;
    }

    public FactoryWebCam stopMotionDetectionImage() {
        isMotionDetectionImage = false;
        return this;
    }

    public FactoryWebCam startMotionDetectionVideo(String folderPath) {
        FactoryWebCam.folderPath = folderPath;
        isMotionDetectionVideo = true;
        isMotionDetectionImage = false;
        return this;
    }

    public FactoryWebCam stopMotionDetectionVideo() {
        isMotionDetectionVideo = false;
        return this;
    }

}
