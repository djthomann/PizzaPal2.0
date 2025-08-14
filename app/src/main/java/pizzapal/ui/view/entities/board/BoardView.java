package pizzapal.ui.view.entities.board;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Board;
import pizzapal.ui.view.entities.EntityView;

public class BoardView extends EntityView<Board> {

    public BoardView(Color color, float width, float height, float posX, float posY) {
        super(color, width, height, posX, posY);

    }
}
