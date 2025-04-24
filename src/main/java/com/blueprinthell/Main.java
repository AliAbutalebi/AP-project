package com.blueprinthell;

import com.blueprinthell.model.ScreenDimensions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
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
