module com.sw2.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires gluegen.rt;


    opens com.sw2.c195 to javafx.fxml;
    exports com.sw2.c195;
    exports controller;
    opens controller to javafx.fxml;
}