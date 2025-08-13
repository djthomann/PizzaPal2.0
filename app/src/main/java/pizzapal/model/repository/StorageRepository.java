package pizzapal.model.repository;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pizzapal.model.domain.core.Storage;
import pizzapal.utils.NotificationManager;
import pizzapal.utils.SceneManager;

public class StorageRepository {

    private static final Logger logger = LoggerFactory.getLogger(StorageRepository.class);

    public static Storage createStorage(String name, float width, float height) {
        return new Storage(name, width, height);
    }

    public static Storage createStorage() {
        return createStorage("Test Storage", 9.0f, 5f);
    }

    @Deprecated
    public Storage createDummyStorage() {
        /*
         * Support support1 = new Support(storage, 0.2f, 2f, 1f, 0f);
         * Support support2 = new Support(storage, 0.2f, 2f, 2f, 0f);
         * 
         * Support support3 = new Support(storage, 0.2f, 2f, 4f, 0f);
         * Support support4 = new Support(storage, 0.2f, 2f, 5f, 0f);
         * 
         * Board board1 = new Board(support1, support2, 0.3f, 0.2f);
         * 
         * Item item1 = new Item(board1, Color.YELLOW, 1, 0.2f, 0.2f, 0.3f);
         * 
         * board1.addItem(item1);
         * 
         * storage.addSupport(support1);
         * storage.addSupport(support2);
         * storage.addSupport(support3);
         * storage.addSupport(support4);
         * 
         * storage.addBoard(board1);
         */
        return null;
    }

    public void saveStorage(Storage storage) {

    }

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void saveToFile(Storage storage) throws IOException {
        logger.info("Saving Storage to File: " + storage.getName());
        NotificationManager.getInstance().addNotification("Saving Storage to File: " + storage.getName());
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(storage.getName() + ".storage"), storage);
    }

    private static Storage loadFromFile(String filename) throws IOException {
        logger.info("Trying to read from file: " + filename);
        NotificationManager.getInstance().addNotification("Trying to read from file: " + filename);
        return mapper.readValue(new File(filename), Storage.class);
    }

    private static File chooseFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Storage Files", "*.storage"));

        Stage stage = SceneManager.getInstance().getStage();

        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;

    }

    public static Storage loadFromFileChooser() {
        File selectedFile = chooseFile();
        Storage storage = null;

        if (selectedFile != null) {
            try {
                storage = loadFromFile(selectedFile.getAbsolutePath());
                storage.initListeners();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return storage;
    }

}
