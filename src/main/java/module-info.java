module com.blueprinthell {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.blueprinthell to javafx.fxml;
    opens com.blueprinthell.controller to javafx.fxml;
    exports com.blueprinthell;
    exports com.blueprinthell.controller to javafx.fxml;
}