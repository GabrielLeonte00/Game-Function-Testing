package com.gamera.gamera;

import com.gamera.gamera.items.armor;
import com.gamera.gamera.items.item;
import com.gamera.gamera.classes.key_handler;
import com.gamera.gamera.classes.player;
import com.gamera.gamera.items.food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    //Variables
    private double offset_initialX_inventory, offset_initialY_inventory;
    private final List<item> list = new ArrayList<>();
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
        in_game_menu.setVisible(true);
        player player = new player();

        new key_handler(player, health_bar, inventory_window);
        inventory_functions();

        //Position player and the health_bar
        player.setLayoutX((game_scene.getWidth() - player.getWidth()) / 2);
        player.setLayoutY((game_scene.getHeight() - player.getHeight()) / 2);
        game_scene.getChildren().add(player);

        food apple = new food("fruit", "apple", 10);
        apple.setLayoutX(100);
        apple.setLayoutY(100);
        game_scene.getChildren().add(apple);

        health_bar.setLayoutX((game_scene.getWidth() - health_bar.getWidth()) / 2);
        health_bar.setLayoutY(game_scene.getHeight() - 50);
        health_bar.setProgress(player.getHealth_points());
        health_bar.setVisible(true);
    }

    @FXML
    void menu_item_addsomething_action() {
        food apple = new food("fruit", "apple", 10);
        food carrot = new food("vegetable", "carrot", 7);
        food apricot = new food("forage", "apricot", 20);
        armor headpiece1 = new armor("head", "Wolf headpiece armor", 1000);
        armor headpiece2 = new armor("head", "Leather headpiece armor", 200);
        armor chestPlate = new armor("chest", "Iron chest plate", 300);
        list.add(apple);
        list.add(carrot);
        list.add(apricot);
        list.add(headpiece1);
        list.add(headpiece2);
        list.add(chestPlate);

        ObservableList<item> inventoryList = FXCollections.observableArrayList();
        inventoryList.addAll(list);
        inventory_list_view.setItems(inventoryList);
    }

    //Functions
    void inventory_functions() {
        inventory_list_view.setCellFactory(_ -> new ListCell<>() {
            private final Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                    setOnMouseEntered(event -> {
                        Bounds boundsInScreen = localToScreen(getBoundsInLocal());
                        double y = boundsInScreen.getMinY();
                        tooltip.setText(item.getDetails());
                        tooltip.show(getScene().getWindow(), event.getScreenX() + 25, y + 25);
                    });
                    setOnMouseExited(event -> {
                        tooltip.hide();
                    });
                }
            }
        });
        inventory_window.setOnMousePressed(e -> {
            offset_initialX_inventory = e.getSceneX() - inventory_window.getTranslateX();
            offset_initialY_inventory = e.getSceneY() - inventory_window.getTranslateY();
        });
        inventory_window.setOnMouseDragged(e -> {
            inventory_window.setTranslateX(e.getSceneX() - offset_initialX_inventory);
            inventory_window.setTranslateY(e.getSceneY() - offset_initialY_inventory);
        });
        /*inventory_list_view.setOnMouseClicked(e -> {
            if (e.getTarget() instanceof ListCell && !((ListCell<?>) e.getTarget()).isEmpty()) {
                item selectedItem = inventory_list_view.getSelectionModel().getSelectedItem();
                System.out.println(selectedItem.getName());
            }
        });*/
        inventory_list_view.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                System.out.println(inventory_list_view.getSelectionModel().getSelectedItem().getName());
            }
        });
    }
}