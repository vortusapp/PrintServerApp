package nz.vortus.printserver.tabs;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class Tab2 {
    private static final String LOG_FILE_LOCATION = "src/main/documents/log.txt";
    static String absolutePath;
    static TextArea textArea;
    static VBox tab;

    public static void createTab2(TabPane tabPane) throws IOException {
        tab = TabFactory.createTab("Log", tabPane);
        addTabContents();
    }

    private static void addTabContents() throws IOException {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        File file = new File(LOG_FILE_LOCATION);
        absolutePath = file.getAbsolutePath();
        String content = new String(Files.readAllBytes(Paths.get(absolutePath)));

        // Set the text area's text to the contents of the file
        textArea.setText(content);

        fileUpdate();
        // Create a scroll pane and add the text area to it
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Create a stack pane and add the scroll pane to it
        tab.getChildren().add(scrollPane);
        addHorizontalSeparator();
    }

    private static void addHorizontalSeparator() {
        Separator separator = new Separator();
        separator.setPrefWidth(tab.getWidth());
        separator.setOrientation(Orientation.HORIZONTAL);
        tab.getChildren().add(separator);
    }
private static void fileUpdate() {
    // Watch the file for changes in a separate thread
    new Thread(() ->

    {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path filePath = Paths.get(absolutePath);
            filePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey key = watchService.poll(1, TimeUnit.SECONDS);
                if (key != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path eventPath = ((Path) key.watchable()).resolve((Path) event.context());
                        if (eventPath.toAbsolutePath().toString().equals(absolutePath)) {
                            updateTextArea();
                            break;
                        }
                    }
                    key.reset();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }).start();
}

    private static void updateTextArea () {
        Platform.runLater(() -> {

            try {
                String fileContent = Files.readString(Paths.get(absolutePath));
                System.out.println("works");
                textArea.setText(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

