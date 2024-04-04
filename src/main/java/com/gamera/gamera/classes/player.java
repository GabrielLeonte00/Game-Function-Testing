package com.gamera.gamera.classes;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class player extends Label {
    //private String name;
    private double current_health_points = 100;
    private final double max_health_points = 100;

    public player() {
        super();
        createPlayer();
    }

    public double getHealth_points() {
        return current_health_points / max_health_points;
    }

    public void changeCurrent_health_points(double value) {
        if (current_health_points + value < 0)
            current_health_points = 0;
        else current_health_points = Math.min(current_health_points + value, max_health_points);
    }

    void createPlayer() {
        Group group = new Group();

        Rectangle bounds = new Rectangle(25, 45);
        bounds.fillProperty().set(Color.PURPLE);
        bounds.setStroke(Color.PURPLE);
        group.getChildren().add(bounds);

        setGraphic(group);
    }
}
