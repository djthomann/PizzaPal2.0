package pizzapal.model.storage;

import java.util.ArrayList;
import java.util.List;

import pizzapal.model.entities.Board;
import pizzapal.model.entities.Support;

public class Storage {

    // In meters
    private float width;

    // In meters
    private float height;

    private List<Support> supports;

    private List<Board> boards;

    public Storage(float width, float height) {
        this.width = width;
        this.height = height;

        supports = new ArrayList<>();
        boards = new ArrayList<>();
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public List<Support> getSupports() {
        return supports;
    }

    public void setSupports(List<Support> supports) {
        this.supports = supports;
    }

    public void addSupport(Support support) {
        supports.add(support);
    }

    public void removeSupport(Support support) {
        supports.remove(support);
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void addBoard(Board board) {
        boards.add(board);
    }

    public void removeBoard(Board board) {
        boards.remove(board);
    }

}
