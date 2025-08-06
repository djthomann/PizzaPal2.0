package pizzapal.model.commands.add;

import pizzapal.model.commands.Command;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;

public class AddSupportCommand implements Command {

    private final Storage storage;

    private final Support support;

    public AddSupportCommand(Storage storage, Support support) {
        this.storage = storage;
        this.support = support;
    }

    @Override
    public void execute() {
        storage.addSupport(support);
    }

    @Override
    public void undo() {
        storage.removeSupport(support);
        support.setStorage(null);
        support.delete();
    }

    public Support getSupport() {
        return support;
    }

}
