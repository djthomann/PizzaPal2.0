package pizzapal.model.commands.delete;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;

public class DeleteSupportCommand extends DeleteCommand<Support> {

    private final Storage storage;

    public DeleteSupportCommand(Support support) {
        super(support);
        this.storage = support.getStorage();
    }

    @Override
    public void execute() {
        super.execute();
        entity.delete();
    }

    @Override
    public void undo() {
        super.undo();
        entity.putInStorage(storage);
    }

    public Support getSupport() {
        return entity;
    }

}
