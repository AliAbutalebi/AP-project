module com.blueprinthell {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jdk.jshell;
    requires java.desktop;
    requires javafx.media;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome6;
    requires mp3agic;


    opens com.blueprinthell to javafx.fxml;
    opens com.blueprinthell.controller to javafx.fxml;
    opens com.blueprinthell.model to com.google.gson;
    exports com.blueprinthell;
    exports com.blueprinthell.controller to javafx.fxml;
    opens com.blueprinthell.model.shop to com.google.gson;
}