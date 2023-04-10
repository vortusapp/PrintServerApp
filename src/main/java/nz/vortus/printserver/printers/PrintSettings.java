package nz.vortus.printserver.printers;



import java.io.Serializable;


public class PrintSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    String paperPrinterName;
    CustomPaper paperPrinterSize;
    String labelPrinterName;
    CustomPaper labelPrinterSize;
    String postagePrinterName;
    CustomPaper postagePrinterSize;

    public PrintSettings (){

    }


    public PrintSettings paperPrinterName(String paperPrinterName) {
        this.paperPrinterName = paperPrinterName;
        return this;
    }

    public PrintSettings paperPrinterSize(CustomPaper paperPrinterSize) {
        this.paperPrinterSize = paperPrinterSize;
        return this;
    }

    public PrintSettings labelPrinterName(String labelPrinterName) {
        this.labelPrinterName = labelPrinterName;
        return this;
    }

    public PrintSettings labelPrinterSize(CustomPaper labelPrinterSize) {
        this.labelPrinterSize = labelPrinterSize;
        return this;
    }

    public PrintSettings postagePrinterName(String postagePrinterName) {
        this.postagePrinterName = postagePrinterName;
        return this;
    }

    public PrintSettings postagePrinterSize(CustomPaper postagePrinterSize) {
        this.postagePrinterSize = postagePrinterSize;
        return this;
    }

    public String getPaperPrinterName() {
        return paperPrinterName;
    }

    public void setPaperPrinterName(String paperPrinterName) {
        this.paperPrinterName = paperPrinterName;
    }

    public CustomPaper getPaperPrinterSize() {
        return paperPrinterSize;
    }

    public void setPaperPrinterSize(CustomPaper paperPrinterSize) {
        this.paperPrinterSize = paperPrinterSize;
    }

    public String getLabelPrinterName() {
        return labelPrinterName;
    }

    public void setLabelPrinterName(String labelPrinterName) {
        this.labelPrinterName = labelPrinterName;
    }

    public CustomPaper getLabelPrinterSize() {
        return labelPrinterSize;
    }

    public void setLabelPrinterSize(CustomPaper labelPrinterSize) {
        this.labelPrinterSize = labelPrinterSize;
    }

    public String getPostagePrinterName() {
        return postagePrinterName;
    }

    public void setPostagePrinterName(String postagePrinterName) {
        this.postagePrinterName = postagePrinterName;
    }

    public CustomPaper getPostagePrinterSize() {
        return postagePrinterSize;
    }

    public void setPostagePrinterSize(CustomPaper postagePrinterSize) {
        this.postagePrinterSize = postagePrinterSize;
    }

}
