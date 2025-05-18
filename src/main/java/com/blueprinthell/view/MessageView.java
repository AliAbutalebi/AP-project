package com.blueprinthell.view;

import com.blueprinthell.model.ScreenDimensions;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class MessageView extends StackPane {

    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    private static final Color BACKGROUND_COLOR = Color.web("#E6E6E6");
    private static final Color BORDER_COLOR = Color.web("#B3B3B3");
    private static final double MESSAGE_WIDTH = screenDimensions.getWidth() / 5;
    private static final double MESSAGE_HEIGHT = screenDimensions.getHeight() / 10;

    private Label label;

    public MessageView(String message, double time) {
        setPrefSize(MESSAGE_WIDTH, MESSAGE_HEIGHT);
        setAlignment(Pos.CENTER);

        Rectangle background = new Rectangle(MESSAGE_WIDTH, MESSAGE_HEIGHT);
        background.setFill(BACKGROUND_COLOR);
        background.setStroke(BORDER_COLOR);
        background.setStrokeWidth(5);
        background.setArcWidth(10);
        background.setArcHeight(10);
        getChildren().add(background);

        label = new Label(message.toUpperCase());
        getChildren().add(label);
        label.setPrefSize(MESSAGE_WIDTH, MESSAGE_HEIGHT);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("monograf-bold");
        label.setWrapText(true);

        setTimer(time);
    }

    private void setTimer(double time) {
        PauseTransition pause = new PauseTransition(Duration.seconds(time));
        pause.setOnFinished(event -> {
            if (this.getParent() != null) {
                ((javafx.scene.layout.Pane) this.getParent()).getChildren().remove(this);
            }
        });
        pause.play();
    }

}
