package pizzapal.ui.view.app.mainmenu.submenu;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.repository.StorageRepository;
import pizzapal.ui.components.InputFieldWithLabel;
import pizzapal.ui.components.TextButton;
import pizzapal.ui.view.app.editor.EditorViewController;
import pizzapal.utils.SceneManager;

public class NewStorageView extends SubMenuView {

    private int currentIndex = 0;
    private final List<Color> colors = List.of(
            Color.LIGHTBLUE,
            Color.LIGHTGREEN,
            Color.LIGHTCORAL,
            Color.LIGHTGOLDENRODYELLOW,
            Color.LIGHTPINK);

    public NewStorageView() {
        super("New Storage", "Start your journey!");

        Region colorBox = new Region();
        colorBox.setPrefSize(150, 100);
        colorBox.setBackground(new Background(new BackgroundFill(colors.get(currentIndex), null, null)));

        Button leftBtn = new Button("←");
        Button rightBtn = new Button("→");

        leftBtn.setOnAction(e -> changeColor(colorBox, -1));
        rightBtn.setOnAction(e -> changeColor(colorBox, 1));

        InputFieldWithLabel nameInput = new InputFieldWithLabel("Name", "New Storage", 150);
        InputFieldWithLabel widthInput = new InputFieldWithLabel("Width", "9.0", 70);
        InputFieldWithLabel heightInput = new InputFieldWithLabel("Height", "5.0", 70);

        HBox dimensionInput = new HBox(widthInput, heightInput);
        dimensionInput.setSpacing(10);
        dimensionInput.setMaxWidth(150);

        Text backgroundText = new Text("Background");

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        Region spacer3 = new Region();
        HBox.setHgrow(spacer3, Priority.ALWAYS);

        HBox backgroundInput = new HBox(leftBtn, spacer2, backgroundText, spacer3, rightBtn);
        backgroundInput.setAlignment(Pos.CENTER);
        backgroundInput.setMaxWidth(150);

        TextButton createButton = new TextButton("Create Storage");
        createButton.setOnAction(_ -> {
            if (nameInput.getText().isBlank() || widthInput.getText().isBlank() || heightInput.getText().isBlank()) {

                SceneManager.getInstance()
                        .showView(new EditorViewController(StorageRepository.createStorage()).getView());

            } else {

                String name = nameInput.getText();
                float width = Float.parseFloat(widthInput.getText());
                float height = Float.parseFloat(heightInput.getText());

                Storage storage = StorageRepository.createStorage(name, width, height);

                SceneManager.getInstance().showView(new EditorViewController(storage).getView());
            }
        });

        addControlOnTop(spacer1);
        addControlOnTop(createButton);
        addControlOnTop(backgroundInput);
        addControlOnTop(dimensionInput);
        addControlOnTop(nameInput);
    }

    private void changeColor(Region box, int direction) {
        currentIndex = (currentIndex + direction + colors.size()) % colors.size();
        this.setBackground(new Background(new BackgroundFill(colors.get(currentIndex), null, null)));
    }

}
