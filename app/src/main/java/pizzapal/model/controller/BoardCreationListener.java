package pizzapal.model.controller;

import pizzapal.model.domain.entities.Board;

public interface BoardCreationListener extends CreationListener {
    void onBoardCreate(Board board);
}
