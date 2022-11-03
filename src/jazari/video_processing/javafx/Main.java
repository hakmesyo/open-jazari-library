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

// launches the application
public class Main extends Application {

    Player player;
    FileChooser fileChooser;
    private Line line;

    public void start(final Stage primaryStage) throws MalformedURLException {
        Group root=new Group();
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
                        player = new Player(file.toURI().toURL().toExternalForm(),root);
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
        player = new Player("C:\\Users\\cezerilab\\Desktop\\traffic_1.mp4",root);

        // Setting the menu at the top
        player.setTop(menu);        

        line = new Line();
        // Adding player to the Scene
        root.getChildren().add(player);
        root.getChildren().add(line);
        
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
