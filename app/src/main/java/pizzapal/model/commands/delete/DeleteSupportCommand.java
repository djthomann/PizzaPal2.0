package pizzapal.model.commands.delete;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;

public class DeleteSupportCommand extends DeleteCommand {

    private final Storage storage;
    private final Support support;

    public DeleteSupportCommand(Storage storage, Support support) {
        this.storage = storage;
        this.support = support;
    }

    @Override
    public void execute() {
        super.execute();
        support.delete();
    }

    @Override
    public void undo() {
        support.putInStorage(storage);
    }

    public Support getSupport() {
        return support;
    }

}
