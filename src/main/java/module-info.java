module fh.swei {
    requires javafx.controls;
    requires javafx.fxml;
    requires metadata.extractor;

    opens fh.swei to javafx.fxml;
    exports fh.swei;
}