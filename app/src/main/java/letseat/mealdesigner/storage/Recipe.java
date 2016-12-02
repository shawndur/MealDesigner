package letseat.mealdesigner.storage;

import java.util.ArrayList;

/**
 * The interface that declares functions that provide access to a Recipe
 */
public interface Recipe {
    /**
     * Get a list of steps for the recipe
     * @return A ArrayList of strings where each string is a step
     */
    ArrayList<String> getSteps();

    /**
     * Stores the steps in the recipe
     *      Note: Will overwrite any steps currently in the recipe
     * @param steps An ArrayList of strings where each string is a step
     * @return Returns true if successful
     */
    boolean setSteps(ArrayList<String> steps);

    /**
     * Get a list of tools used in the recipe
     * @return An ArrayList of strings where each string is a tool
     */
    ArrayList<String> getTools();

    /**
     * Store the tools in this recipe
     *      Note: Will overwrite any existing tools
     * @param tools An ArrayList of strings where each string is a tool
     * @return Returns true if successful
     */
    boolean setTools(ArrayList<String> tools);

    /**
     * Get a list of ingredients used in the recipe
     * @return Returns an ArrayList of strings where each string is a ingredient
     */
    ArrayList<String> getIngredients();

    /**
     * Store the ingredients in the recipe
     * @param ingredients An ArrayList of strings where each string is a ingredients
     * @return Returns true if successful
     */
    boolean setIngredients(ArrayList<String> ingredients);

    /**
     * Store the ingredients in the recipe
     * @param temp An ArrayList of strings where each string passed through the creation walk through
     * @return Returns true if successful
     */
    boolean setTempRecipePass(ArrayList<String> temp);

    /**
     * Sets the allergens of the recipe
     * @param dairy True if the recipe contains this allergen
     * @param nuts True if the recipe contains this allergen
     * @param eggs True if the recipe contains this allergen
     * @param soy True if the recipe contains this allergen
     * @param fish True if the recipe contains this allergen
     * @return true if successful
     */
    boolean setAllergens(boolean dairy, boolean nuts, boolean eggs,boolean soy, boolean fish);

    /**
     * Fetches the allergens of the recipe
     * @return an arraylist of strings for allergens
     */
    ArrayList<String> getAllergens();
}


