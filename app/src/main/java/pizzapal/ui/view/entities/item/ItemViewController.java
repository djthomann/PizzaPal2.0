package pizzapal.ui.view.entities.item;

import pizzapal.model.domain.entities.Item;
import pizzapal.ui.view.entities.EntityViewController;
import pizzapal.utils.Helper;

public class ItemViewController extends EntityViewController<Item> {

    private Item item;

    public ItemViewController(Item item) {

        super(new ItemView(item.getColor(), Helper.convertMetersToPixel(item.getWidth()),
                Helper.convertMetersToPixel(item.getHeight()), Helper.convertMetersToPixel(item.getPosX()),
                Helper.convertMetersToPixel(item.getPosY())));
        this.item = item;

        item.addListener(model -> {
            view.updateFromModel(model);
        });

    }

    @Override
    protected void onMouseReleased(float xInView, float yInView) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onMouseReleased'");
    }

}
