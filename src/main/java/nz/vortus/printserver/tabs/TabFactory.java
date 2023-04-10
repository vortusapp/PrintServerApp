package nz.vortus.printserver.tabs;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Box;

public class TabFactory {
    public static TabPane createTabPane(VBox parent){
        TabPane tabPane = new TabPane();
        parent.getChildren().add(tabPane);
        return tabPane;
    }

    public static VBox createTab(String title, TabPane parent){
        Tab tab = new Tab(title);
        tab.setClosable(false);
        VBox contentsPane = createVBox();
        tab.setContent(contentsPane);
        parent.getTabs().add(tab);
        return contentsPane;

    }
    public static VBox createVBox(){
        VBox vBox = new VBox();
        VBox.setVgrow(vBox, Priority.ALWAYS);
        Insets spacing = new Insets(10, 10, 10, 10);
        VBox.setMargin(vBox,spacing);
        vBox.setSpacing(10 );
        vBox.setPadding(spacing);
        return vBox;
    }

    public static HBox createHBox(){
        HBox hBox = new HBox();
        HBox.setHgrow(hBox, Priority.ALWAYS);
        Insets spacing = new Insets(10, 10, 10, 10);
        HBox.setMargin(hBox,spacing);
        hBox.setSpacing(10 );
        hBox.setPadding(spacing);
        return hBox;
    }


}
