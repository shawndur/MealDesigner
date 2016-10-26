package letseat.mealdesigner.storage;

import java.util.ArrayList;

// TODO: 10/17/16 How do we handle non existient recipes on get recipe?

/**
 * The interface that declares functions that provide access to the database
 */
public interface Database {
    /**
     * Returns a new Recipe with the provided name
     * @param name name of the recipe to be created
     * @return The newly created recipe
     */
    Recipe newRecipe(String name);

    /**
     * Retrieves a recipe from storage
     * @param name The name of the recipe to be created
     * @return The retrieved recipe
     */
    Recipe getRecipe(String name);

    /**
     * Saves the recipe in storage.
     * @param recipe Recipe to be saved
     * @return Returns true if successful
     */
    boolean setRecipe(Recipe recipe);

    /**
     * Retrieves the shopping list from storage
     * @return The shopping list
     */
    ShopList getShopList();

    /**
     * Saves the shopping list in storage.
     *      Note: Will overwrite existing shopping list
     * @param shopList The shopping list to be saved
     * @return True if successful
     */
    boolean setShopList(ShopList shopList);

    /**
     * Retrieves a list of recipes in storage
     * @return An ArrayList of strings, where each string is a recipe name
     */
    ArrayList<String> getListOfRecipes();

    /**
     * Store all data temporarily in the recipe before setting
     * @param recipe An ArrayList of strings to carry the recipe through creation walk through
     * @return Returns true if successful
     */
    boolean setTempRecipe(Recipe recipe);

    /**
     * Get a list of ingredients used in the recipe
     * @return Returns an ArrayList of strings where each string is a ingredient
     */
    ArrayList<String> getTempRecipe();
}