package pizzapal.ui.view.entities.item;

import javafx.scene.layout.Pane;
import pizzapal.Helper;
import pizzapal.model.domain.entities.Item;
import pizzapal.ui.view.entities.ViewController;

public class ItemViewController implements ViewController {

    private Item item;

    private ItemView view;

    public ItemViewController(Item item) {

        this.item = item;

        float widthPx = Helper.convertMetersToPixel(item.getWidth());
        float heightPx = Helper.convertMetersToPixel(item.getHeight());
        float posX = Helper.convertMetersToPixel(item.getPosX());
        float posY = Helper.convertMetersToPixel(item.getPosY());

        view = new ItemView(item.getColor(), widthPx, heightPx, posX, posY);

        item.addListener(model -> {
            view.updateFromModel(model);
        });

    }

    @Override
    public void initDragAndDrop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initDragAndDrop'");
    }

    @Override
    public Pane getView() {
        return view;
    }

}
