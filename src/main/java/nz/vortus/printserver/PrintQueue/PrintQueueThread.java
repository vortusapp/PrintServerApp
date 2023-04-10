package nz.vortus.printserver.PrintQueue;

import java.awt.print.PrinterException;
import java.io.IOException;

public class PrintQueueThread extends Thread{
    public void run(){
        try {
            PrintQueueConsumer.startPrintQueueConsumer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (PrinterException e) {
            throw new RuntimeException(e);
        }
    }
}
