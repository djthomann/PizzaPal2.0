package pizzapal.ui.view.app.mainmenu.submenu;

import pizzapal.model.repository.StorageRepository;
import pizzapal.ui.components.TextButton;
import pizzapal.ui.view.app.editor.EditorViewController;
import pizzapal.utils.SceneManager;

public class NewStorageView extends SubMenuView {

    public NewStorageView() {
        super("New Storage", "Start your journey!");

        TextButton createButton = new TextButton("Create Storage");
        createButton.setOnAction(_ -> {
            SceneManager.getInstance().showView(new EditorViewController(StorageRepository.createStorage()).getView());
        });

        addControlOnTop(createButton);
    }

}
