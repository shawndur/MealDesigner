package letseat.mealdesigner.storage;

import java.util.ArrayList;

/**
 * The interface that declares functions that provide access to the shopping list
 */
public interface ShopList {
    /**
     * Gets the Ingredients in the shopping list
     * @return An ArrayList of Ingredients
     */
    ArrayList<Ingredient> getIngredients();

    /**
     * Stores the Ingredients in the shopping list
     *      Note: This will overwrite the current shopping list
     * @param ingredients An ArrayList of ingredients
     * @return Returns true if successful
     */
    boolean setIngredients(ArrayList<Ingredient> ingredients);

    /**
     * Creates a new Ingredient
     * @return Returns a newly created ingredient
     */
    Ingredient newIngredient();
}
