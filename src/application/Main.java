package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Image icon = new Image("icon.jpeg");
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Fuel System Login Page");
            primaryStage.show();      
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
