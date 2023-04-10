package nz.vortus.printserver.printers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.print.Paper;
import javafx.print.Printer;
import org.apache.kafka.common.record.UnalignedMemoryRecords;


import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AvailablePrinters {

    static Set<Paper> paperSizes;
    public static ObservableList getPrintersList() {
        ObservableList<String> printerList = FXCollections.observableArrayList();
        ObservableSet<Printer> allPrinters = Printer.getAllPrinters();
        allPrinters.forEach(printer -> {
            printerList.add(printer.getName());
        });
        return printerList;

    }

    public static ObservableList getPaperSizeList(String selectedPrinter){
        ObservableList<String> paperSizeList = FXCollections.observableArrayList();
        ObservableSet<Printer> allPrinters = Printer.getAllPrinters();
        allPrinters.forEach(printer -> {
            if (printer.getName().equals(selectedPrinter)){
                paperSizes = printer.getPrinterAttributes().getSupportedPapers();
                Set<String> paperSizeNames = new HashSet<>();
                paperSizeNames.add("default");
                for (Paper paper: paperSizes
                     ) { paperSizeNames.add(paper.getName());

                }
                paperSizeList.addAll(paperSizeNames);
            }

        });

        return paperSizeList;
    }


    public static Printer getSelectedPrinter(String printerName) {
        Printer returnPrinter = null;
        for(Printer printer: Printer.getAllPrinters()){
            if(printer.getName().equals(printerName)){
                returnPrinter=printer;
            }
        };
        return returnPrinter;

    }


    public static CustomPaper getSelectedPaperSize(String paperName) {
        CustomPaper customPaper = new CustomPaper();
        for (Paper paper: paperSizes){
            if (paper.getName().equals(paperName)){
                customPaper.fromPaper(paper);
            }

        }
        //Todo Make the paper size reflect the papersize selected;
        return customPaper;
    }

    public static PrintService getPrintService(String printerName) {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService printService = null;
        if (services != null && services.length > 0) {
            printService = Arrays.stream(services)
                    .filter(service -> service.getName().contains(printerName))
                    .findFirst()
                    .orElse(null);

        }
        return printService;
    }
}

