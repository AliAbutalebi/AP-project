package com.blueprinthell.view;

import com.blueprinthell.audio.SoundEffectManager;
import com.blueprinthell.controller.GameController;
import com.blueprinthell.log.Logger;
import com.blueprinthell.model.*;
import com.blueprinthell.model.shop.ItemType;
import com.blueprinthell.model.shop.Shop;
import com.blueprinthell.model.shop.ShopItem;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ShopView extends StackPane {
    private static ShopView instance;

    private Shop shop = Shop.getInstance();

    private static final HUD hud = HUD.getInstance();

    private static final SoundEffectManager soundEffectManager = SoundEffectManager.getInstance();

    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    private static final Logger logger = Logger.getInstance();

    private static final double SHOP_WIDTH = screenDimensions.getWidth() * 0.8;
    private static final double SHOP_HEIGHT = screenDimensions.getHeight() * 0.95;
    private static final double TITLE_HEIGHT = SHOP_HEIGHT / 10;
    private static final double ITEM_WIDTH = SHOP_WIDTH / 5;
    private static final double ITEM_HEIGHT = SHOP_HEIGHT / 2.6;
    private static final double BUTTON_WIDTH = ITEM_WIDTH * 0.8;
    private static final double BUTTON_HEIGHT = SHOP_HEIGHT / 15;
    private static final Color BACKGROUND_COLOR = Color.web("#E6E6E6");
    private static final Color BORDER_COLOR = Color.web("#B3B3B3");
    private static final Color ITEM_BACKGROUND_COLOR = Color.web("#666666");
    private static final Color ITEM_BORDER_COLOR = Color.web("#4d4d4d");
    private static final Color TEXT_COLOR = Color.WHITE;

    private Pane splitterPane;
    private VBox contentPane;
    private GridPane itemsPane = new GridPane();

    private Button returnButton;

    private ShopView() {
        setupBackground();
        contentPane.getChildren().add(itemsPane);
        setupReturnButton();
        setupItems();
    }

    public static ShopView getInstance() {
        if (instance == null) {
            instance = new ShopView();
        }
        return instance;
    }

    private void setupBackground() {
        setPrefSize(screenDimensions.getWidth(), screenDimensions.getHeight());
        setAlignment(Pos.CENTER);

        splitterPane = new Pane();
        splitterPane.setPrefSize(screenDimensions.getWidth(), screenDimensions.getHeight());
        splitterPane.setStyle("-fx-background-color: #000000");
        splitterPane.setOpacity(0.5);
        getChildren().add(splitterPane);

        Rectangle background = new Rectangle(SHOP_WIDTH, SHOP_HEIGHT);
        background.setFill(BACKGROUND_COLOR);
        background.setStroke(BORDER_COLOR);
        background.setStrokeWidth(5);
        background.setArcWidth(10);
        background.setArcHeight(10);
        getChildren().add(background);

        contentPane = new VBox();
        contentPane.setMaxSize(SHOP_WIDTH, SHOP_HEIGHT);
        getChildren().add(contentPane);
        contentPane.setSpacing(30);

        HBox titlePane = new HBox();
        titlePane.setAlignment(Pos.CENTER);
        titlePane.setPrefSize(SHOP_WIDTH, TITLE_HEIGHT);
        contentPane.getChildren().add(titlePane);

        Label title = new Label("SHOP");
        title.getStyleClass().add("monograf-bold");
        title.setStyle(String.format("-fx-font-size: %d;", (int) TITLE_HEIGHT));
        titlePane.getChildren().add(title);
    }

    private void setupItems() {
        itemsPane.setAlignment(Pos.CENTER);
        itemsPane.setPrefSize(SHOP_WIDTH, ITEM_HEIGHT);
        itemsPane.setHgap(20);
        itemsPane.setVgap(20);

        for (int i = 0; i < shop.getItemTypes().size(); i++) {
            ItemType item = shop.getItemTypes().get(i);
            StackPane itemPane = new StackPane();
            itemPane.setPrefSize(ITEM_WIDTH, ITEM_HEIGHT);
            itemPane.setAlignment(Pos.CENTER);
            itemsPane.add(itemPane, i - ((i / 3) * 3), i / 3);

            Rectangle itemBackground = new Rectangle(ITEM_WIDTH, ITEM_HEIGHT);
            itemBackground.setFill(ITEM_BACKGROUND_COLOR);
            itemBackground.setStroke(ITEM_BORDER_COLOR);
            itemBackground.setStrokeWidth(5);
            itemBackground.setArcWidth(10);
            itemBackground.setArcHeight(10);
            itemPane.getChildren().add(itemBackground);

            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.setSpacing(5);
            box.setMaxSize(ITEM_WIDTH - 20, ITEM_HEIGHT - 20);
            itemPane.getChildren().add(box);

            StackPane iconPane = new StackPane();
            iconPane.setAlignment(Pos.CENTER);
            ImageView icon = new ImageView(getItemIcon(item));
            icon.setSmooth(true);
            iconPane.getChildren().add(icon);
            box.getChildren().add(iconPane);

            Label itemName = new Label(item.getName());
            itemName.setPrefWidth(ITEM_WIDTH);
            itemName.setAlignment(Pos.CENTER);
            itemName.getStyleClass().add("monograf-bold");
            itemName.setTextFill(TEXT_COLOR);
            itemName.setStyle(String.format("-fx-font-size: %d;", (int) ITEM_HEIGHT / 18));
            box.getChildren().add(itemName);

            Label itemDescription = new Label(item.getDescription());
            itemDescription.setPrefWidth(ITEM_WIDTH);
            itemDescription.setPrefHeight(100);
            itemDescription.setAlignment(Pos.CENTER);
            itemDescription.getStyleClass().add("monograf-regular");
            itemDescription.setTextFill(TEXT_COLOR);
            itemDescription.setStyle(String.format("-fx-font-size: %d;", (int) ITEM_HEIGHT / 25));
            itemDescription.setWrapText(true);
            box.getChildren().add(itemDescription);

            Label itemPrice = new Label(item.getPrice() + " Coins");
            itemPrice.setPrefWidth(ITEM_WIDTH);
            itemPrice.setAlignment(Pos.CENTER);
            itemPrice.getStyleClass().add("space-mono");
            itemPrice.setTextFill(TEXT_COLOR);
            itemPrice.setStyle(String.format("-fx-font-size: %d;", (int) ITEM_HEIGHT / 20));
            box.getChildren().add(itemPrice);

            Button itemActivateButton = new Button("ACTIVATE");
            itemActivateButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            box.getChildren().add(itemActivateButton);
            itemActivateButton.getStyleClass().add("monograf-bold");
            itemActivateButton.setStyle(String.format("-fx-font-size: %d;", (int) BUTTON_HEIGHT / 3));
            if (shop.canActivate(item)) {
                itemActivateButton.setDisable(true);
                itemActivateButton.setText("ACTIVATED");
            }
            else if (shop.isCoolingDown(item)) {
                itemActivateButton.setDisable(true);
                itemActivateButton.setText("COOLING DOWN");
            }
            else if (hud.getCoins() < item.getPrice()) {
                itemActivateButton.setDisable(true);
                itemActivateButton.setText("INSUFFICIENT COINS");
                itemActivateButton.setStyle(String.format("-fx-font-size: %d;", (int) BUTTON_HEIGHT / 5));
            }
            itemActivateButton.setOnAction(event -> {
                shop.getActiveItems().add(new ShopItem(item));
                soundEffectManager.play("click");
                GameController.newMessage(item.getName() + " Activated.", 3);
                update();
                logger.info(item.getName() + " Activated.");
            });
        }
    }

    private void setupReturnButton() {
        Pane returnPane = new Pane();
        getChildren().add(1, returnPane);
        returnPane.setLayoutX(20);
        returnPane.setLayoutY(20);

        FontIcon returnIcon = new FontIcon(FontAwesomeSolid.ARROW_LEFT);
        returnButton = new Button();
        returnButton.setGraphic(returnIcon);
        returnButton.setPrefSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
        returnPane.getChildren().add(returnButton);
        returnButton.setLayoutX(20);
        returnButton.setLayoutY(20);
    }

    public void update() {
        itemsPane.getChildren().clear();
        setupItems();
    }

    public Button getReturnButton() {
        return returnButton;
    }

    private Image getItemIcon(ItemType itemType) {
        switch (itemType) {
            case OATAR -> {
                return new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/oatar.png").toURI().toString());
            }
            case OAIRYAMAN -> {
                return new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/oairyaman.png").toURI().toString());
            }
            case OANAHITA -> {
                return new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/oanahita.png").toURI().toString());
            }
            case AERGIA -> {
                return new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/aergia.png").toURI().toString());
            }
            case SISYPHUS -> {
                return new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/sisyphus.png").toURI().toString());
            }
            case ELIPHAS -> {
                return new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/eliphas.png").toURI().toString());
            }
        }
        return null;
    }

}
