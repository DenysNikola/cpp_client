module com.company.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.company.client to javafx.fxml;
    exports com.company.client;
}