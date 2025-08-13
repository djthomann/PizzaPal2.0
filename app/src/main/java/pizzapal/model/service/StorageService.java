package pizzapal.model.service;

import java.util.List;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class StorageService {

    private Storage storage;

    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    public Support getSupportLeftOfPos(float posX) {
        Support left = null;

        for (Support s : storage.getSupports()) {
            if (s.getPosX() < posX) {
                if (left == null || s.getPosX() > left.getPosX()) {
                    left = s;
                }
            }
        }

        return left;
    }

    public Support getSupportRightOfPos(float posX) {
        Support right = null;

        for (Support s : storage.getSupports()) {
            if (s.getPosX() > posX) {
                if (right == null || s.getPosX() < right.getPosX()) {
                    right = s;
                }
            }
        }

        return right;
    }

    public Board getBoardBelow(float posX, float posY) {

        List<Board> boards = getBoardsAt(posX);

        return getBoardBelow(boards, posY);
    }

    public List<Board> getBoardsAt(float posX) {
        Support left = getSupportLeftOfPos(posX);
        Support right = getSupportRightOfPos(posX);

        if (left == null) {
            return null;
        }

        if (right == null) {
            return null;
        }

        return left.getBoardsRight();
    }

    public Board getBoardAboveOffset(List<Board> boards, float offsetY) {

        Board board = null;

        for (Board b : boards) {
            if (b.getOffsetY() < offsetY) {
                if (board == null) {
                    board = b;
                }

                if (board.getOffsetY() < b.getOffsetY()) {
                    board = b;
                }
            }
        }

        return board;

    }

    public Board getBoardBelowOffset(List<Board> boards, float offsetY) {

        Board board = null;

        for (Board b : boards) {
            if (b.getOffsetY() > offsetY) {
                if (board == null) {
                    board = b;
                }

                if (board.getOffsetY() > b.getOffsetY()) {
                    board = b;
                }
            }
        }

        return board;

    }

    public Board getBoardBelow(List<Board> boards, float posY) {
        Board board = null;

        for (Board b : boards) {
            if (b.getPosY() <= posY) {
                if (board == null) {
                    board = b;
                }

                if (board.getPosY() <= b.getPosY()) {
                    board = b;
                }
            }
        }

        return board;

    }

    public Board getBoardAbove(List<Board> boards, float posY) {
        Board board = null;

        for (Board b : boards) {
            if (b.getPosY() > posY) {
                if (board == null) {
                    board = b;
                }

                if (board.getPosY() > b.getPosY()) {
                    board = b;
                }
            }
        }

        return board;
    }
}
