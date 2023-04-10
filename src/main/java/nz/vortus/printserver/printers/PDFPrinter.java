package nz.vortus.printserver.printers;


import nz.vortus.printserver.repository.Config;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.printing.PDFPageable;


import javax.print.PrintService;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public  class PDFPrinter {

    private  File file = null;
    private  String pathname ;
    private URL url;
    private PrintService printService;
    static PrintSettings printSettings;


    public PDFPrinter(File file) throws PrinterException {
        printSettings = Config.load();
        printService = AvailablePrinters.getPrintService(printSettings.getPaperPrinterName());
        this.file = file;
        print();
    }
    PDFPrinter(URL url) throws PrinterException {
        this.url = url;
        print();
    }
        void print(){

            try (PDDocument document = file != null ? PDDocument.load(file) : PDDocument.load(url.openStream())) {

                SetPaperSize(document);
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintService(printService);

                SetToPageable(document, job);
                if(job.printDialog()) {
                    job.print();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PrinterException e) {
                throw new RuntimeException(e);
            }
        }

    static void SetToPageable(PDDocument document, PrinterJob job) {
        job.setPageable(new PDFPageable(document));
    }

    static void SetPaperSize(PDDocument document) {
        CustomPaper paper = printSettings.getPaperPrinterSize();
        if (!paper.getName().equalsIgnoreCase("default")) {
            PDRectangle pageSize = new PDRectangle((float) paper.getWidth(), (float) paper.getHeight());
            for (PDPage page : document.getPages()) {
                page.setMediaBox(pageSize);
            }
        }
    }
}

