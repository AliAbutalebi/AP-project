package com.blueprinthell.view;

import com.blueprinthell.model.ScreenDimensions;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class ImpactView extends Circle {

    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();
    private AnimationTimer timer;

    public ImpactView(Point2D center) {
        setCenterX(center.getX());
        setCenterY(center.getY());
        setFill(Color.TRANSPARENT);
        setStroke(Color.WHITE);
        setStrokeWidth(2);
        setRadius(0);
        setOpacity(1);

        setupAnimation();
    }

    private void setupAnimation() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                setRadius(getRadius() + 2);
                setOpacity(getOpacity() - 0.005);
                handleFinish();
            }
        };
        timer.start();
    }

    private void handleFinish() {
        if (timer != null) {
            if (getRadius() > screenDimensions.getWidth()) {
                timer.stop();
                AnchorPane parent = (AnchorPane) getParent();
                parent.getChildren().remove(this);
            }
        }
    }


}
