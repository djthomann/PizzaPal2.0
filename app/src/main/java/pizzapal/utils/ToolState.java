package pizzapal.utils;

import java.util.HashMap;
import java.util.Map;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Entity;
import pizzapal.model.domain.entities.Ingredient;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.observability.ObservableField;
import pizzapal.model.repository.IngredientRepository;

public class ToolState {

    // TODO: Make static fields invisible?

    private ObservableField<Tool> currentTool;

    private Class<? extends Entity> selectedEntity;

    private final Map<EntityField, Object> fieldMap = new HashMap<>();
    private final ObservableField<Boolean> mapChange;
    private boolean mapInitiliased = false;

    private Ingredient itemIngredient;
    public static final Ingredient STANDARD_ITEM_INGREDIENT = IngredientRepository.getInstance()
            .getIngredient("Tomato");

    public enum Tool {
        SELECT, PICKCOLOR, SUPPORT, BOARD, ITEM
    }

    public ToolState() {
        currentTool = new ObservableField<ToolState.Tool>(Tool.SUPPORT);
        selectedEntity = Support.class;
        mapChange = new ObservableField<Boolean>(true);
    }

    public void setCurrentToolByEntity(Class<? extends Entity> clazz) {
        selectedEntity = clazz;
        if (clazz == Support.class) {
            setCurrentTool(Tool.SUPPORT);
        } else if (clazz == Board.class) {
            setCurrentTool(Tool.BOARD);
        } else if (clazz == Item.class) {
            setCurrentTool(Tool.ITEM);
        }
    }

    public ObservableField<Tool> toolObservable() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool.setValue(currentTool);
    }

    public Tool getCurrentTool() {
        return currentTool.getValue();
    }

    public Ingredient getItemIngredient() {
        return itemIngredient;
    }

    public void setItemIngredient(Ingredient itemIngredient) {
        this.itemIngredient = itemIngredient;
    }

    public void putValue(EntityField field, Object value) {
        fieldMap.put(field, value);
        if (mapInitiliased) {
            mapChange.setValue(true);
        }
    }

    public Object getValue(EntityField field) {
        return fieldMap.get(field);
    }

    public Class<? extends Entity> getSelectedEntityClass() {
        return selectedEntity;
    }

    public ObservableField<Boolean> mapChangeObservable() {
        return mapChange;
    }

    public void setInitialised() {
        mapInitiliased = true;
    }

}
