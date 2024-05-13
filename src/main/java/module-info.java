module com.matheus.leite {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.matheus.leite to javafx.fxml;
    opens com.matheus.leite.model to javafx.base;
    exports com.matheus.leite;
}
