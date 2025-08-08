package pizzapal.ui.view.entities.support;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.EntityView;
import pizzapal.utils.Helper;

public class SupportView extends EntityView<Support> {

    public SupportView(Color color, float width, float height, float posX, float posY) {
        super(color, width, height, posX, posY);

    }

    public void updateFromModel(Support support) {

        resetRectangle();

        this.setLayoutX(Helper.convertMetersToPixel(support.getPosX()));
        this.setLayoutY(Helper.getPixelPositionYInStorage(support.getStorage(), support.getHeight()));
    }

}
