/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.video_processing.javafx;

// Java program to Build a Media
// Player in JavaFX
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

// launches the application
public class Main extends Application {

    Player player;
    FileChooser fileChooser;
    private Line line;
    private Rectangle rectangle;
    private double anchorX;
    private double anchorY;
    private double mouseOffsetFromNodeZeroX;
    private double mouseOffsetFromNodeZeroY;
    private double lastx;
    private double lasty;
    private double px;
    private double py;

    public void start(final Stage primaryStage) throws MalformedURLException {
        Group root = new Group();
        // setting up the stages
        MenuItem open = new MenuItem("Open");
        Menu file = new Menu("File");
        MenuBar menu = new MenuBar();

        // Connecting the above three
        file.getItems().add(open); // it would connect open with file
        menu.getMenus().add(file);

        // Adding functionality to switch to different videos
        fileChooser = new FileChooser();
        open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // Pausing the video while switching
                player.player.pause();
                File file = fileChooser.showOpenDialog(primaryStage);

                // Choosing the file to play
                if (file != null) {
                    try {
                        player = new Player(file.toURI().toURL().toExternalForm(), root);
                        Scene scene = new Scene(player, 640, 430, Color.BLACK);
                        primaryStage.setScene(scene);
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        );

        // here you can choose any video
        //player = new Player("file:/// F:/songs/srk.mp4");
        //player = new Player("data/temp_1.mp4");
        player = new Player("C:\\Users\\cezerilab\\Desktop\\traffic_1.mp4", root);

        // Setting the menu at the top
        player.setTop(menu);

        rectangle = new Rectangle(50, 50, 150, 250);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.LIGHTGREEN);
        rectangle.setStrokeWidth(3);
        
        
        
        rectangle.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            px=rectangle.getX();
            py=rectangle.getY();
            //System.out.println("px = " + anchorX+" py = " + anchorY);
        });
        rectangle.setOnMouseDragged(event -> {
            rectangle.setX(event.getSceneX() - anchorX + px);
            rectangle.setY(event.getSceneY() - anchorY + py);
            //System.out.println("x = " + event.getSceneX()+" y = " + event.getSceneY());
        });
        rectangle.setOnMouseReleased(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            //System.out.println("lx = " + anchorX+" ly = " + anchorY);
            //commit changes to LayoutX and LayoutY
            //rectangle.setLayoutX(lastx);
            //rectangle.setLayoutY(lasty);
            //clear changes from TranslateX and TranslateY
//            rectangle.setTranslateX(lastx);
//            rectangle.setTranslateY(lasty);
//            
//            rectangle.setLayoutX(anchorX);
//            rectangle.setLayoutY(anchorY);
        });

        // Adding player to the Scene
        root.getChildren().add(player);
        root.getChildren().add(rectangle);

        Scene scene = new Scene(root, 640, 430, Color.WHEAT);

        // height and width of the video player
        // background color set to Black
        primaryStage.setScene(scene); // Setting the scene to stage

        primaryStage.show(); // Showing the stage
    }
    // Main function to launch the application

    public static void main(String[] args) {
        launch(args);
    }
}
