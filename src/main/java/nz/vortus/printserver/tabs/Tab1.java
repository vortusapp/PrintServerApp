package nz.vortus.printserver.tabs;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nz.vortus.printserver.printers.AvailablePrinters;
import nz.vortus.printserver.printers.CustomPaper;
import nz.vortus.printserver.printers.PrintSettings;
import nz.vortus.printserver.repository.Config;

public class Tab1 {
//    private static ChoiceBox<String> printers;
    private static ChoiceBox<String> printers = new ChoiceBox<>();
    private static ChoiceBox<String> paperSize = new ChoiceBox<>();
    private static Label printersLabel;
    private static Label paperSizeLabel;
    private static Printer selectedPrinter;
    private static CustomPaper selectedPaperSize;
    static VBox tab1;

    public static void createTab1(TabPane tabPane) {
        loadPrinters();
        tab1 = TabFactory.createTab("Printer Settings", tabPane);
        addTabContents();
    }

    private static void addTabContents() {
        addSelectPrinterDropdown();
        addSelectPaperSizeDropdown();
        addHorizontalSeparator();
        addSaveCancelButtonsBox();
        loadConfig();
    }

    private static void addHorizontalSeparator() {
        Separator separator = new Separator();
        separator.setPrefWidth(tab1.getWidth());
        separator.setOrientation(Orientation.HORIZONTAL);
        tab1.getChildren().add(separator);
    }

    private static void addSelectPrinterDropdown() {
        printersLabel = new Label("Select a Printer");
        tab1.getChildren().add(printersLabel);
        tab1.getChildren().add(printers);
    }

    private static void addSaveCancelButtonsBox(){
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = TabFactory.createHBox();
        buttonBox.getChildren().addAll(saveButton, cancelButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        tab1.getChildren().add(buttonBox);
        onSaveListener(saveButton);
        onCancelListener(cancelButton);


    }

    private static void onSaveListener(Button saveButton){
        saveButton.setOnAction(event -> {
            // Save selected printer and paper size to a configuration file or database
            // You can access the selected printer and paper size using the selectedPrinter and selectedPaperSize variables
            // Once saved, you can retrieve these values later in the program

            // Example code to save to a configuration file

            Config.save(new  PrintSettings().paperPrinterName(selectedPrinter.getName())
                    .paperPrinterSize(selectedPaperSize));
        });
    }

    private static void onCancelListener(Button cancelButton){
        // Cancel button event listener
        cancelButton.setOnAction(event -> {
            // Reset the printer and paper size to the previously saved values
            loadConfig();
        });
    }

    private static void loadConfig() {
        PrintSettings printSettings = Config.load();
        printers.getSelectionModel().select(printSettings.getPaperPrinterName());
        paperSize.getSelectionModel().select(printSettings.getPaperPrinterSize().getName());
    }

    private static void addSelectPaperSizeDropdown(){
        paperSizeLabel = new Label("Select Paper Size");
        tab1.getChildren().add(paperSizeLabel);
        tab1.getChildren().add(paperSize);

    }

    private static void loadPrinters() {
        printers = new ChoiceBox<>(AvailablePrinters.getPrintersList());
        printers.setPrefWidth(300);


        loadPaperSize();

        printers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedPrinter = AvailablePrinters.getSelectedPrinter(newValue);
            paperSize.setItems(AvailablePrinters.getPaperSizeList(newValue));
        });


    }

    private static void loadPaperSize() {
          paperSize.setPrefWidth(300);
        paperSize.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedPaperSize = AvailablePrinters.getSelectedPaperSize(newValue);

        });

    }
}
