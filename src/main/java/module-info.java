module nz.vortus.printserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires kafka.clients;
    requires java.desktop;
    requires org.apache.pdfbox;

    opens nz.vortus.printserver to javafx.fxml;
    exports nz.vortus.printserver;
}