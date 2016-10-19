package letseat.mealdesigner.storage;

import java.util.ArrayList;

/**
 * The interface that declares functions that provide access to an ingredient
 */
public interface Ingredient {
    //These functions are getters to get each field
    String getName();
    String getAmount();
    String getPrice();
    String getStore();
    ArrayList<String> getRecipes();

    //These functions are setters for each field
    //      Note: They will overwrite each existing field
    boolean setName(String name);
    boolean setAmount(String amount);
    boolean setPrice(String price);
    boolean setStore(String store);
    boolean setRecipes(ArrayList<String> recipes);
}
