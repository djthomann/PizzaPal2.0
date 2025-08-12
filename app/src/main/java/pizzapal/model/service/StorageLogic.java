package pizzapal.model.service;

import java.util.List;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;

public class StorageLogic {

    private Storage storage;

    private StorageService service;

    public StorageLogic(Storage storage, StorageService service) {
        this.storage = storage;
        this.service = service;
    }

    public boolean positionInStorage(float posX, float posY) {

        float storageWidth = storage.getWidth();
        float storageHeight = storage.getHeight();

        // System.out.println("Nicht im Lager");

        return posX >= 0 && posY >= 0 && posX <= storageWidth && posY <= storageHeight;
    }

    public boolean positionInSupport(float posX) {
        for (Support s : storage.getSupports()) {
            if (s.getPosX() <= posX && s.getPosX() + s.getWidth() >= posX) {
                return true;
            }
        }

        return false;
    }

    public boolean movingSupportLeavesEnoughRoomsForItems(Support support, float posX) {
        boolean movingRight = support.getPosX() < posX;
        // Check enough space for items
        if (movingRight) {

            List<Board> boardsRight = support.getBoardsRight();

            if (boardsRight.isEmpty()) {
                // No need to check for items --> possible to move
                return true;
            }

            Support right = boardsRight.get(0).getSupportRight();

            for (Board b : boardsRight) {

                for (Item i : b.getItems()) {

                    float itemRightSide = posX + support.getWidth() + i.getOffsetX() + i.getWidth();
                    float rightSupportLeftSide = right.getPosX();

                    if (itemRightSide > rightSupportLeftSide) {
                        return false;
                    }

                }

            }

        } else {

            List<Board> boardsLeft = support.getBoardsLeft();

            if (boardsLeft.isEmpty()) {
                // No need to check for items --> possible to move
                return true;
            }

            for (Board b : boardsLeft) {

                for (Item i : b.getItems()) {

                    float itemRightSide = i.getPosX() + i.getWidth();
                    float newSupportLeftSide = posX;

                    if (itemRightSide > newSupportLeftSide) {
                        return false;
                    }

                }

            }

        }

        return true;
    }

    public boolean storageHasSpaceForSupportAt(float width, float posX) {

        float leftSide = posX;
        float rightSide = posX + width;
        // Check for collisions
        for (Support s : storage.getSupports()) {

            boolean leftSideInside = s.getPosX() < leftSide && s.getPosX() + s.getWidth() > leftSide;
            boolean rightSideInside = s.getPosX() < rightSide && s.getPosX() + s.getWidth() > rightSide;

            if (leftSideInside || rightSideInside) {
                // Collision
                return false;

            }
        }

        return true;

    }

    public boolean supportDoesntCollideWithOtherSupports(Support support, float posX) {

        float leftSide = posX;
        float rightSide = posX + support.getWidth();
        // Check for collisions
        for (Support s : storage.getSupports()) {

            boolean leftSideInside = s.getPosX() < leftSide && s.getPosX() + s.getWidth() > leftSide;
            boolean rightSideInside = s.getPosX() < rightSide && s.getPosX() + s.getWidth() > rightSide;

            if (leftSideInside || rightSideInside) {
                // Collision
                if (!s.equals(support)) {
                    return false;
                }

            }
        }

        return true;
    }

    public boolean movingThroughSupport(Support support, float posX) {

        boolean movingRight = support.getPosX() < posX;

        if (movingRight) {
            List<Board> boards = support.getBoardsRight();
            if (boards.isEmpty()) {
                return false;
            }
            Support right = boards.get(0).getSupportRight();
            if (right == null) {
                // Shouldn't be possible
                return false;
            } else {
                return right.getPosX() < posX;
            }
        } else {

            List<Board> boards = support.getBoardsLeft();
            if (boards.isEmpty()) {
                return false;
            }
            Support left = boards.get(0).getSupportLeft();
            if (left == null) {
                System.out.println("Not possible");
                // Shouldn't be possible
                return false;
            } else {
                return left.getPosX() + left.getWidth() > posX;
            }
        }
    }

    public boolean isPositionBetweenTwoSupports(float posX) {
        Support left = service.getSupportLeftOfPos(posX);
        Support right = service.getSupportRightOfPos(posX);

        return left != null && right != null;
    }

    public boolean storageIsEmpty() {
        return storage.isEmpty();
    }

    public boolean supportHasSpaceForBoardRight(Support support, Board board, float offsetY) {

        List<Board> boardsRight = support.getBoardsRight();

        for (Board b : boardsRight) {
            if (!b.equals(board)) {

                boolean enoughSpaceAbove = offsetY < b.getOffsetY() && offsetY + board.getHeight() < b.getOffsetY();
                boolean enoughSpaceBelow = offsetY > b.getOffsetY() + b.getHeight();

                return enoughSpaceAbove || enoughSpaceBelow;
            }
        }

        return true;
    }

    public boolean enoughSpaceForItemsLeftAbove(Support support, Board board, float offsetY) {

        Board above = service.getBoardAboveOffset(support.getBoardsRight(), offsetY);

        if (above == null || above.equals(board)) {
            // nothing above, or only the same board
            return true;
        } else {

            List<Item> items = board.getItems();

            if (items.isEmpty()) {
                // no items moving up
                return true;
            } else {
                // items moving up
                for (Item i : items) {

                    float newItemPosY = support.getHeight() - offsetY + i.getHeight();
                    float undersideAbove = above.getPosY() - above.getHeight();

                    if (newItemPosY > undersideAbove) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public boolean enoughSpaceForItemsLeftBelow(Support support, Board board, float offsetY) {

        Board below = service.getBoardBelowOffset(support.getBoardsRight(), offsetY);

        if (below == null || below.equals(board)) {
            // nothing below, or only the same board
            return true;
        } else {

            List<Item> items = below.getItems();

            if (items.isEmpty()) {
                // no items below
                return true;
            } else {
                // check items below
                for (Item i : items) {

                    float itemPosY = i.getPosY();
                    float undersideAbove = support.getHeight() - offsetY - board.getHeight();

                    if (itemPosY > undersideAbove) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public boolean boardsCollide(Board board1, Board board2) {

        return !boardsDontCollide(board1, board2);
    }

    private boolean boardsDontCollide(Board board1, Board board2) {
        boolean boardAbove = board1.getPosY() < board2.getPosY()
                && board1.getPosY() + board1.getHeight() < board2.getPosY();
        boolean boardBelow = board1.getPosY() > board2.getPosY() + board2.getHeight()
                && board1.getPosY() + board1.getHeight() > board2.getPosY() + board2.getHeight();

        return boardAbove || boardBelow;
    }

    public boolean boardHasEnoughSpaceForItem(Board board, Item item, float offsetX) {

        for (Item i : board.getItems()) {
            boolean leftSideInside = i.getOffsetX() < offsetX && i.getOffsetX() + i.getWidth() > offsetX;
            System.out.println("Left: " + leftSideInside);
            boolean rightSideInside = offsetX + item.getWidth() > i.getOffsetX()
                    && offsetX + item.getWidth() < i.getOffsetX() + i.getWidth();
            System.out.println("Right: " + rightSideInside);

            if (!i.equals(item) && (leftSideInside || rightSideInside)) {
                return false;
            }
        }
        return true;

    }

}
