package nz.vortus.printserver.printers;

import javafx.print.Paper;

import java.io.Serializable;

public class CustomPaper implements Serializable {
    String name;
    double width;
    double height;


    public void fromPaper(Paper paper) {
        name = paper.getName();
        height = paper.getHeight();
        width = paper.getWidth();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public String getName(){
        if (name==null){
            name="default";
        }
        return name;
    }
}
