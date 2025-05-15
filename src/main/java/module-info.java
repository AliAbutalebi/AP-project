module com.blueprinthell {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jdk.jshell;
    requires java.desktop;
    requires javafx.media;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome6;


    opens com.blueprinthell to javafx.fxml;
    opens com.blueprinthell.controller to javafx.fxml;
    exports com.blueprinthell;
    exports com.blueprinthell.controller to javafx.fxml;
}