package com.blueprinthell;

import com.blueprinthell.log.Logger;
import com.blueprinthell.model.ScreenDimensions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Logger.getInstance().run();
        Font.loadFont(getClass().getResourceAsStream("/com/blueprinthell/font/Monograf/monograf-bold.ttf"), 24);
        Font.loadFont(getClass().getResourceAsStream("/com/blueprinthell/font/Monograf/monograf-regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/com/blueprinthell/font/Space-mono/SpaceMono-BoldItalic.ttf"), 10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/blueprinthell/view/MainMenu.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Blueprint Hell");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
        ScreenDimensions screenDimensions = ScreenDimensions.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
