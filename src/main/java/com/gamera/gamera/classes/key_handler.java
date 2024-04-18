package com.gamera.gamera.classes;

import com.gamera.gamera.MainApplication;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;

public class key_handler {

    private final Scene scene;
    private final player player;
    private final ProgressBar health_bar;
    private final TitledPane inventory_window;
    private final TitledPane character_inventory_window;

    boolean goNorth, goSouth, goWest, goEast;

    public key_handler(player player, ProgressBar health_bar, TitledPane inventory_window, TitledPane character_inventory_window) {
        scene = MainApplication.getScene();
        this.player = player;
        this.health_bar = health_bar;
        this.inventory_window = inventory_window;
        this.character_inventory_window = character_inventory_window;
        movement(); // Initialize movement controls
        startGameLoop(); // Start the game loop
    }

    private void movementLoop() {
        double speed = 5;
        double playerX = player.getLayoutX();
        double playerY = player.getLayoutY();

        if (goNorth) playerY -= speed;
        if (goSouth) playerY += speed;
        if (goWest) playerX -= speed;
        if (goEast) playerX += speed;

        // Update the player's position on the JavaFX application thread
        scene.getRoot().requestFocus(); // Ensure the scene has focus
        player.relocate(playerX, playerY); // This method should update the player's position
    }

    void movement() {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    goNorth = true;
                    break;
                case S:
                    goSouth = true;
                    break;
                case A:
                    goWest = true;
                    break;
                case D:
                    goEast = true;
                    break;
                case H:
                    player.changeCurrent_health_points(5);
                    health_bar.setProgress(player.getHealth_points());
                    break;
                case J:
                    player.changeCurrent_health_points(-5);
                    health_bar.setProgress(player.getHealth_points());
                    break;
                case C:
                    character_inventory_window.setVisible(!character_inventory_window.isVisible());
                    break;
                case I:
                    inventory_window.setVisible(!inventory_window.isVisible());
                    break;
            }
        });
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W:
                    goNorth = false;
                    break;
                case S:
                    goSouth = false;
                    break;
                case A:
                    goWest = false;
                    break;
                case D:
                    goEast = false;
                    break;
            }
        });
    }

    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                movementLoop();
            }
        };
        timer.start();
    }
}
