package com.blueprinthell.view;

import com.blueprinthell.model.SquarePort;
import com.blueprinthell.model.TrianglePort;
import com.blueprinthell.model.Wire;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class WireView extends Line {
    private static final double WIRE_WIDTH = 5;
    private Wire wire;
    private boolean onValidPort = false;

    public WireView(Wire wire) {
        super(wire.getStartPoint().getWidth(), wire.getStartPoint().getHeight(), wire.getEndPoint().getWidth(), wire.getEndPoint().getHeight());
        this.wire = wire;
        setUserData(wire);

        setWireStyle();
    }

    private void setWireStyle() {
        setStrokeWidth(WIRE_WIDTH);
        if (onValidPort) {
            if (wire.getSourcePort() instanceof SquarePort) {
                setStroke(Color.web("#00FF00"));
            }
            else if (wire.getSourcePort() instanceof TrianglePort) {
                setStroke(Color.web("#FFFF00"));
            }
        }
        else {
            setStroke(Color.web("#666666"));
        }
    }


}
