package com.gamera.gamera.items;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class food extends Label implements item {
    int id;
    String type; // forage | vegetable | fruit
    String name;
    double growth = 0;
    double freshness = 1;
    double quality = 1;
    double base_price;

    public food(String type, String name, double base_price) {
        super();
        this.type = type;
        this.name = name;
        this.base_price = base_price;
        draw();
    }

    void draw() {
        Group group = new Group();
        Rectangle bounds = new Rectangle(25, 25);
        bounds.fillProperty().set(null);
        bounds.setStroke(Color.WHITE);
        group.getChildren().add(bounds);
        Text text = new Text("F");
        text.setFont(Font.font(12));
        text.setFill(Color.RED);
        text.setX(bounds.getX() + bounds.getWidth() / 2 - text.getBoundsInLocal().getWidth() / 2);
        text.setY(bounds.getY() + bounds.getHeight() / 2 + text.getBoundsInLocal().getHeight() / 4);
        group.getChildren().add(text);

        setGraphic(group);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return base_price;
    }

    public String getDetails() {
        return STR."Growth : \{growth}\n Freshness : \{freshness}\n Quality : \{quality}\n Price : \{base_price}";
    }

}
