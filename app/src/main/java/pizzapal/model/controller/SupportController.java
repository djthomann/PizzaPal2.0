package pizzapal.model.controller;

import javafx.scene.paint.Color;
import pizzapal.model.commands.add.AddSupportCommand;
import pizzapal.model.commands.delete.DeleteSupportCommand;
import pizzapal.model.commands.edit.EditSupportCommand;
import pizzapal.model.commands.move.MoveSupportCommand;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.service.StorageLogic;
import pizzapal.model.service.StorageService;
import pizzapal.utils.NotificationManager;

public class SupportController extends EntityController<Support> {

    public SupportController(StorageLogic logic, StorageService service) {
        super(logic, service);
    }

    public MoveSupportCommand moveSupport(Support support, float posX, float posY) {

        if (!logic.positionInStorage(posX, posY)) {
            NotificationManager.getInstance().addNotification("Not inside Storage");
            return null;
        }

        if (logic.movingThroughSupport(support, posX)) {
            NotificationManager.getInstance().addNotification("Moving through other support");
            return null;
        }

        if (!logic.supportDoesntCollideWithOtherSupports(support, posX)) {
            NotificationManager.getInstance().addNotification("Collision with other support");
            return null;
        }

        if (!logic.movingSupportLeavesEnoughRoomsForItems(support, posX)) {
            NotificationManager.getInstance().addNotification("Not enough space for items");
            return null;
        }

        MoveSupportCommand moveCommand = new MoveSupportCommand(support, posX, posY);
        return moveCommand;
    }

    // TODO: Fix bug where you can put another support directly on an existing one
    public AddSupportCommand addSupport(float width, float height, Color color, float posX, float posY) {

        if (!logic.storageHasSpaceForSupportAt(width, posX)) { // TODO: refactor!
            NotificationManager.getInstance().addNotification("No space for new support");
            return null;
        }

        Support support = new Support(color, width, height, posX, posY);
        AddSupportCommand addCommand = new AddSupportCommand(service.getStorage(), support);

        return addCommand;
    }

    public EditSupportCommand editSupport(Support support, float newWidth, float newHeight, Color newColor) {

        EditSupportCommand editCommand = new EditSupportCommand(support, newWidth, newHeight, newColor);

        return editCommand;

    }

    public DeleteSupportCommand delete(Support support) {
        if (support.getBoardsLeft().isEmpty() && support.getBoardsRight().isEmpty()) {
            DeleteSupportCommand deleteCommand = new DeleteSupportCommand(support);
            return deleteCommand;
        } else {
            NotificationManager.getInstance().addNotification("Can't delete Support");
            return null;
        }

    }

}
