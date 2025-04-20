module com.blueprinthell {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.blueprinthell to javafx.fxml;
    exports com.blueprinthell;
}