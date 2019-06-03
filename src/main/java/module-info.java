module fh.swei {
    requires javafx.controls;
    requires javafx.fxml;
    requires metadata.extractor;
    requires java.desktop;

    opens fh.swei to javafx.fxml;
    exports fh.swei;
}