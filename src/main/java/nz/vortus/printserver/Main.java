package nz.vortus.printserver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.vortus.printserver.PrintQueue.PrintQueueThread;
import nz.vortus.printserver.tabs.Tab1;
import nz.vortus.printserver.tabs.Tab2;
import nz.vortus.printserver.tabs.TabFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;



public class Main extends Application {
    private TrayIcon trayIcon;
    private SystemTray tray;

    public static final String PROGRAM_TITLE = "Vortus Print Server";
    File file = new File(PROGRAM_ICON_LOCATION);
    URL url = file.toURI().toURL();
    private static final String PROGRAM_ICON_LOCATION = "src/main/documents/Favicon.png";

    private ChoiceBox<String> paperSize = new ChoiceBox<>();

    private Stage primaryStage;
    private VBox mainPane;

    public Main() throws MalformedURLException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        setProgramTitle();
        setIcon();
        mainPane = new VBox();
        TabPane tabPane = TabFactory.createTabPane(mainPane);
        Tab1.createTab1(tabPane);
        Tab2.createTab2(tabPane);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // Hide the window instead of closing it
                primaryStage.hide();
                // Perform any additional cleanup or background tasks here
            }
        });

        primaryStage.setScene(new Scene(mainPane, 600, 400));
        primaryStage.show();
        PrintQueueThread printQueue = new PrintQueueThread();
        printQueue.start();


        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            Image trayImage = new Image(url.openStream());
            PopupMenu trayPopupMenu = new PopupMenu();
            MenuItem exitMenuItem = new MenuItem("Exit");
            exitMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Platform.exit();
                    tray.remove(trayIcon);
                }
            });

            trayPopupMenu.add(exitMenuItem);
            // Add "Open" menu item
            MenuItem openMenuItem = new MenuItem("Open");
            openMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            primaryStage.show();
                        }
                    });
                }
            });


            trayPopupMenu.add(openMenuItem);


            trayIcon = new TrayIcon(SwingFXUtils.fromFXImage(trayImage, null), PROGRAM_TITLE, trayPopupMenu);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) { // Double-click detected
                            Platform.runLater(() -> primaryStage.show());
                        }
                    }
            });


            tray.add(trayIcon);
        }
    }

    private void setProgramTitle() {
        primaryStage.setTitle(PROGRAM_TITLE);
    }


    private void setIcon() {
        File file = new File(PROGRAM_ICON_LOCATION);
        String absolutePath = file.getAbsolutePath();
        String url = "file:///" + absolutePath;
        Image icon = new Image(url);
        primaryStage.getIcons().add(icon);
    }





    public static void main(String[] args) {
        launch(args);
    }
}
