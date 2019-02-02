package SensoPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Senso.fxml"));
        primaryStage.setTitle("Seneso");
        Scene sc = new Scene(root, 600, 400);
        primaryStage.setScene(sc);
        Image ic = new Image(getClass().getResource("/Bilder/Senso.jpg").toString());
        primaryStage.getIcons().add(ic);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
