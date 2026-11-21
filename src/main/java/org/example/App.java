package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader =
                new FXMLLoader(App.class.getResource("HomePage.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage = primaryStage;
        stage.setScene(scene);
        stage.setTitle("Auction Management System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}