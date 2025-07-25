package pizzapal.model.storage.commands;

import pizzapal.model.entities.Board;
import pizzapal.model.entities.Support;

public class MoveBoardCommand implements Command {

    private final Board board;

    private final Support newSupportLeft;
    private final Support oldSupportLeft;

    private final Support newSupportRight;
    private final Support oldSupportRight;

    private final float newY;
    private final float oldY;

    public MoveBoardCommand(Board board, Support newSupportLeft, Support newSupportRight, float newY) {
        this.board = board;
        this.newSupportLeft = newSupportLeft;
        this.oldSupportLeft = board.getSupportLeft();
        this.newSupportRight = newSupportRight;
        this.oldSupportRight = board.getSupportRight();
        this.newY = newY;
        this.oldY = board.getPosY();
    }

    @Override
    public void execute() {
        System.out.println("Moving to by execute: " + newY);
        board.move(newSupportLeft, newSupportRight, newY);
    }

    @Override
    public void undo() {
        board.move(oldSupportLeft, oldSupportRight, oldY);
    }

}
