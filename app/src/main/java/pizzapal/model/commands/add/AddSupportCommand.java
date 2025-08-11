package pizzapal.model.commands.add;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;

public class AddSupportCommand extends AddCommand {

    private final Storage storage;

    private final Support support;

    public AddSupportCommand(Storage storage, Support support) {
        this.storage = storage;
        this.support = support;
    }

    @Override
    public void execute() {
        super.execute();
        support.putInStorage(storage);
    }

    @Override
    public void undo() {
        storage.removeSupport(support);
        // TODO: Take out of Storage
        support.setStorage(null);
        support.delete();
    }

    public Support getSupport() {
        return support;
    }

}
