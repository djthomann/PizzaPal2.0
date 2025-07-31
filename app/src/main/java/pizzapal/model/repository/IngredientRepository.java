package pizzapal.model.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Ingredient;

public class IngredientRepository {

    private static IngredientRepository instance;

    private Map<String, Ingredient> ingredients = new HashMap<>();

    private IngredientRepository() {
        ingredients.put("Tomato", new Ingredient("Tomato", Color.RED));
        ingredients.put("Cheese", new Ingredient("Cheese", Color.YELLOW));
    }

    public static IngredientRepository getInstance() {
        if (instance == null) {
            instance = new IngredientRepository();
        }
        return instance;
    }

    public Set<String> getAllIngredientNames() {
        return ingredients.keySet();
    }

    public Ingredient getIngredient(String name) {
        return ingredients.get(name);
    }

    public void addIngredient(String name, Color color) {
        ingredients.put(name, new Ingredient(name, color));
    }

    public void removeIngredient(String name) {
        ingredients.remove(name);
    }

}
