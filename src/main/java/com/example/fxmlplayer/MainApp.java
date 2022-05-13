package com.example.fxmlplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main.fxml"));
        String css = getClass().getResource("style.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(css);
        stage.setTitle("Rethabile Media Player");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}