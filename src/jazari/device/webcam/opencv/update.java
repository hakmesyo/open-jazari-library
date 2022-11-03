/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.device.webcam.opencv;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
public class update  extends Application {
    // TODO Auto-generated method stub
       Mat matrix = null;
       public void start(Stage stage) throws FileNotFoundException, IOException {
          update o = new update();
          WritableImage Imgw = o.capureSnapShot();
          o.saveImage();
          ImageView img = new ImageView(Imgw);
          img.setFitHeight(400);
          img.setFitWidth(600);
          img.setPreserveRatio(true);
          Group r = new Group(img);
          Scene s = new Scene(r, 600, 400);
          stage.setTitle("Capturing an image");
          stage.setScene(s);
          stage.show();
       }
       public WritableImage capureSnapShot() {
          WritableImage WritableImage = null;
          System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
          VideoCapture c = new VideoCapture(1);
          c.set(Videoio.CAP_PROP_FRAME_WIDTH, 1280);
          c.set(Videoio.CAP_PROP_FRAME_HEIGHT, 720);
          Mat matrix = new Mat();
          c.read(matrix);
          if( c.isOpened()) {
               if (c.read(matrix)) {
                BufferedImage image = new BufferedImage(matrix.width(), 
                   matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
                
                WritableRaster raster = image.getRaster();
                DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
                byte[] d = dataBuffer.getData();
                matrix.get(0, 0, d);
                this.matrix = matrix;
                WritableImage = SwingFXUtils.toFXImage(image, null);
             }
          }
          return WritableImage;
       }
       public void saveImage() {
          String f = "c:/opencv/i.jpg";
              Imgcodecs imageCodecs = new Imgcodecs();
              imageCodecs.imwrite(f, matrix);
       }
       public static void main(String args[]) {
          launch(args);
        }
}