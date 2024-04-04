package com.gamera.gamera;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gamera");
        stage.setScene(scene);
        stage.setResizable(false);
        maximizeStage();
        stage.show();
    }

    public static Scene getScene() {
        return scene;
    }

    protected void maximizeStage() {
        Screen primaryScreen = Screen.getPrimary();
        Rectangle2D visualBounds = primaryScreen.getVisualBounds();

        stage.setX(visualBounds.getMinX());
        stage.setY(visualBounds.getMinY());
        stage.setWidth(visualBounds.getWidth());
        stage.setHeight(visualBounds.getHeight());
    }
}