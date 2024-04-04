package com.gamera.gamera;

import com.gamera.gamera.classes.player;
import com.gamera.gamera.classes.player_movement;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainController {
    //Variables
    private player player;
    //FXML Variables
    @FXML
    AnchorPane main_window;
    @FXML
    Pane game_scene;
    @FXML
    MenuItem menu_item_start;
    @FXML
    ProgressBar health_bar;
    //FXML Functions
    @FXML
    void menu_item_start_action(){
        player = new player();
        player_setup();
        player.setLayoutX((game_scene.getWidth()-player.getWidth())/2);
        player.setLayoutY((game_scene.getHeight()-player.getHeight())/2);
        game_scene.getChildren().add(player);

        health_bar.setLayoutX((game_scene.getWidth()-health_bar.getWidth())/2);
        health_bar.setLayoutY(game_scene.getHeight()-50);
        health_bar.setProgress(player.getHealth_points());
        health_bar.setVisible(true);
    }
    //Functions
    void player_setup(){
        new player_movement(player, health_bar);

    }
}