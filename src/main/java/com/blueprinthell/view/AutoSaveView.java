package com.blueprinthell.view;

import com.blueprinthell.map.MapManager;
import com.blueprinthell.model.ScreenDimensions;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AutoSaveView extends StackPane {

    public AutoSaveView() {
        setup();
    }

    private Pane splitterPane;
    private VBox contentPane;
    private final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();
    private final MapManager mapManager = MapManager.getInstance();
    private final PauseTransition pause = new PauseTransition();
    private final Color BACKGROUND_COLOR = Color.web("#E6E6E6");
    private final Color BORDER_COLOR = Color.web("#B3B3B3");
    private final double BACKGROUND_WIDTH = screenDimensions.getWidth() * 0.6;
    private final double BACKGROUND_HEIGHT = screenDimensions.getHeight() * 0.6;

    private Runnable onClose;

    private void setup() {
        splitterPane = new Pane();
        splitterPane.setPrefSize(screenDimensions.getWidth(), screenDimensions.getHeight());
        splitterPane.setStyle("-fx-background-color: #000000");
        splitterPane.setOpacity(0.5);
        getChildren().add(splitterPane);

        Rectangle background = new Rectangle(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        background.setFill(BACKGROUND_COLOR);
        background.setStroke(BORDER_COLOR);
        background.setStrokeWidth(5);
        background.setArcWidth(10);
        background.setArcHeight(10);
        getChildren().add(background);

        contentPane = new VBox();
        contentPane.setMaxSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        contentPane.setSpacing(20);
        contentPane.setAlignment(Pos.CENTER);
        getChildren().add(contentPane);

        Label autoSave = new Label("Wanna checkout the last AutoSave?");
        autoSave.setStyle(String.format("-fx-font-size: %f", BACKGROUND_HEIGHT * 0.05));
        autoSave.getStyleClass().add("space-mono");
        contentPane.getChildren().add(autoSave);

        HBox buttonsPane = new HBox();
        buttonsPane.setPrefWidth(BACKGROUND_WIDTH);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setSpacing(10);
        contentPane.getChildren().add(buttonsPane);

        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            mapManager.setAutoSave(false);
            close();

        });
        buttonsPane.getChildren().add(noButton);
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            mapManager.setAutoSave(true);
            close();
        });
        buttonsPane.getChildren().add(yesButton);
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    private void close() {
        if (onClose != null) onClose.run();
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(this);
        }
    }
}
