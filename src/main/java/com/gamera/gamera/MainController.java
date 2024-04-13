package com.gamera.gamera;

import com.gamera.gamera.items.armor;
import com.gamera.gamera.items.item;
import com.gamera.gamera.classes.key_handler;
import com.gamera.gamera.classes.player;
import com.gamera.gamera.items.food;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static javafx.scene.input.MouseButton.PRIMARY;
import static javafx.scene.input.MouseButton.SECONDARY;

public class MainController {
    //Variables
    private double offset_initialX, offset_initialY;
    private final List<item> list = new ArrayList<>();
    private player player;
    //FXML Variables
    @FXML
    AnchorPane main_window;
    @FXML
    Menu in_game_menu;
    @FXML
    Pane game_scene;
    @FXML
    ProgressBar health_bar;
    @FXML
    TitledPane inventory_window;
    @FXML
    ListView<item> inventory_list_view;

    //FXML Functions
    @FXML
    void menu_item_start_action() {
        Timeline timeline = getTimeline();
        timeline.play();

        in_game_menu.setVisible(true);
        player = new player();

        new key_handler(player, health_bar, inventory_window);
        inventory_functions();

        //Position player and the health_bar
        player.setLayoutX((game_scene.getWidth() - player.getWidth()) / 2);
        player.setLayoutY((game_scene.getHeight() - player.getHeight()) / 2);
        game_scene.getChildren().add(player);

        food apple = new food("fruit", "golden apple", 10);
        apple.setLayoutX(100);
        apple.setLayoutY(100);
        addItemToGameScene(apple);
        armor armor = new armor("shoes", "Draconic shoes", 520);
        armor.setLayoutX(150);
        armor.setLayoutY(100);
        addItemToGameScene(armor);

        health_bar.setLayoutX((game_scene.getWidth() - health_bar.getWidth()) / 2);
        health_bar.setLayoutY(game_scene.getHeight() - 50);
        health_bar.setProgress(player.getHealth_points());
        health_bar.setVisible(true);
    }

    @FXML
    void menu_item_addSomething_action() {
        food apple = new food("fruit", "apple", 10);
        apple.setGrowth(1);
        food apple2 = new food("fruit", "apple", 10);
        apple2.setGrowth(0.5);
        food carrot = new food("vegetable", "carrot", 7);
        carrot.setGrowth(1);
        food apricot = new food("forage", "apricot", 20);
        apricot.setGrowth(1);
        armor headpiece1 = new armor("head", "Wolf headpiece armor", 1000);
        armor headpiece2 = new armor("head", "Leather headpiece armor", 200);
        armor chestPlate = new armor("chest", "Iron chest plate", 300);
        list.add(apple);
        list.add(apple2);
        list.add(carrot);
        list.add(apricot);
        list.add(headpiece1);
        list.add(headpiece2);
        list.add(chestPlate);

        inventory_list_view.getItems().addAll(list);
    }

    //Functions
    @NotNull
    private Timeline getTimeline() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), _ -> {
                    for (Node food : game_scene.getChildren()) {
                        if (food instanceof food)
                            if (((food) food).getGrowth().compareTo(BigDecimal.valueOf(1)) == 0)
                                ((food) food).updateFreshness();
                            else {
                                ((food) food).updateGrowth();
                            }
                    }
                    for (item food : inventory_list_view.getItems()) {
                        if (food instanceof food)
                            ((food) food).updateFreshness();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    void inventory_functions() {
        inventory_list_view.setCellFactory(_ -> new ListCell<>() {
            private final Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setOnMouseEntered(null);
                    setOnMouseExited(null);
                    setOnMouseClicked(null);
                } else {
                    setText(item.getName());
                    setOnMouseEntered(event -> {
                        Bounds boundsInScreen = localToScreen(getBoundsInLocal());
                        double y = boundsInScreen.getMinY();
                        tooltip.setText(item.getDetails());
                        tooltip.show(getScene().getWindow(), event.getScreenX() + 25, y + 25);
                    });
                    setOnMouseExited(_ -> tooltip.hide());
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 1) {
                            if (event.getButton() == PRIMARY)
                                System.out.println(item.getName());
                            else if (event.getButton() == SECONDARY) {
                                ((Node) item).setLayoutX(player.getLayoutX());
                                ((Node) item).setLayoutY(player.getLayoutY() + player.getHeight() - ((Node) item).getBoundsInLocal().getHeight());
                                addItemToGameScene(item);
                                inventory_list_view.getItems().remove(item);
                                tooltip.hide();
                            }
                        }
                    });
                }
            }
        });
        inventory_window.setOnMousePressed(e -> {
            offset_initialX = e.getSceneX() - inventory_window.getTranslateX();
            offset_initialY = e.getSceneY() - inventory_window.getTranslateY();
        });
        inventory_window.setOnMouseDragged(e -> {
            inventory_window.setTranslateX(e.getSceneX() - offset_initialX);
            inventory_window.setTranslateY(e.getSceneY() - offset_initialY);
        });
    }

    private void addItemToGameScene(item item) {
        game_scene.getChildren().add((Node) item);

        AtomicBoolean isDragging = new AtomicBoolean(false);

        ((Node) item).setOnMouseReleased(e -> {
            if (!isDragging.get()) {
                if (item instanceof food) {
                    if (((food) item).getGrowth().compareTo(BigDecimal.valueOf(0.2)) >= 0) {
                        inventory_list_view.getItems().add(item);
                        game_scene.getChildren().remove(item);
                    } else {
                        System.out.println(STR."\{item.getName()} is not ripe yet");
                    }
                } else if (item instanceof armor) {
                    inventory_list_view.getItems().add(item);
                    game_scene.getChildren().remove(item);
                }
                e.consume();
            }
        });
        ((Node) item).setOnMousePressed(e -> {
            offset_initialX = e.getSceneX() - ((Node) item).getTranslateX();
            offset_initialY = e.getSceneY() - ((Node) item).getTranslateY();
            isDragging.set(false);
        });
        ((Node) item).setOnMouseDragged(e -> {
            ((Node) item).setTranslateX(e.getSceneX() - offset_initialX);
            ((Node) item).setTranslateY(e.getSceneY() - offset_initialY);
            isDragging.set(true);
        });
    }
}