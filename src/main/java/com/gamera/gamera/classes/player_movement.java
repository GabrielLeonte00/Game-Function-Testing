package com.gamera.gamera.classes;

import com.gamera.gamera.MainApplication;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;

public class player_movement {

    private final Scene scene;
    private final player player;
    private final ProgressBar health_bar;
    boolean goNorth, goSouth, goWest, goEast;

    public player_movement(player player, ProgressBar health_bar) {
        scene = MainApplication.getScene();
        this.player = player;
        this.health_bar = health_bar;
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
