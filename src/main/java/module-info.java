module com.sw2.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires gluegen.rt;


    //opens com.sw2.c195 to javafx.fxml;
    opens com.sw2.c195 to javafx.graphics, javafx.fxml, javafx.base;
    exports com.sw2.c195;
    exports controller;
    opens controller to javafx.fxml;

    opens model to javafx.graphics, javafx.fxml, javafx.base;
}