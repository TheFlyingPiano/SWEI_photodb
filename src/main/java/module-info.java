module fh.swei {
    requires javafx.controls;
    requires javafx.fxml;
    requires metadata.extractor;
    requires java.desktop;
    requires java.sql;

    opens fh.swei to javafx.fxml;
    exports fh.swei;
}