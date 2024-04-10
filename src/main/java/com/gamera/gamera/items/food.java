package com.gamera.gamera.items;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class food extends Label implements item {
    //int id;
    String type; // forage | vegetable | fruit
    String name;
    BigDecimal growth = BigDecimal.valueOf(0); // 0 -> 1
    BigDecimal freshness = BigDecimal.valueOf(1); // 1 -> -0.2
    BigDecimal increment = BigDecimal.valueOf(0.1);
    BigDecimal quality = BigDecimal.valueOf(1);
    BigDecimal base_price;
    BigDecimal price;

    public food(String type, String name, double base_price) {
        super();
        this.type = type;
        this.name = name;
        this.base_price = BigDecimal.valueOf(base_price);
        updatePrice();
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

    public BigDecimal getGrowth(){
        return growth;
    }

    public void setGrowth(double growth) {
        this.growth = BigDecimal.valueOf(growth);
        updatePrice();
    }

    public void updateGrowth() {
        if(growth.compareTo(BigDecimal.valueOf(1)) != 0){
            growth = growth.add(increment);
            updatePrice();
        }
    }

    public void updateFreshness() {
        if(freshness.compareTo(BigDecimal.valueOf(-0.2)) != 0){
            freshness = freshness.subtract(increment);
            updatePrice();
        }
    }

    public void updatePrice(){
        price = growth.add(freshness).multiply(quality).multiply(base_price);
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return STR."Growth : \{growth}\n Freshness : \{freshness}\n Quality : \{quality}\n Price : \{price}";
    }

}
