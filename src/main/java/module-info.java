module main.summative {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens main.summative to javafx.fxml;
    exports main.summative;
}