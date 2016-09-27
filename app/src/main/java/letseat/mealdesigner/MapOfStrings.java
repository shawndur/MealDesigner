package letseat.mealdesigner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An object which holds all information in an encapsulated data structure.
 * Created by George_Sr on 9/27/2016.
 */

public class MapOfStrings
{

    enum ListType
    {
        RECIPE, MEAL_STEPS, GROCERY_LIST;
    }

    enum Stage
    {
        EQUIPMENT, INGREDIENTS, STEPS, NOTES;
    }

    enum UOM
    {
        // discrete, abstract, and non-empirical quantities
        EACH, TO_TASTE, BAG,

        // English wet
        TEASPOON, TABLESPOON, FLUID_OUNCE, CUP, PINT, QUART, GALLON,

        // English dry
        OUNCE, POUND,

        // Metric wet
        MILLILITER, LITER,

        // Metric dry
        GRAM, KILOGRAM;
    }

    private MapOfStrings _next;
    private ArrayList<String> _contents;
    private Map<String, Double> _quantities;
    private Map<String, UOM> _UOMs;
    private ListType _listType;


    public MapOfStrings(MapOfStrings next, ListType listType)
    {
        _next = next;
        commonConstructorCode(listType);
    }

    public MapOfStrings(ArrayList<String> contents, ListType listType)
    {
        _contents = contents;
        commonConstructorCode(listType);
    }

    public MapOfStrings(MapOfStrings next, ArrayList<String> contents, ListType listType)
    {
        _next = next;
        _contents = contents;
        commonConstructorCode(listType);
    }

    public MapOfStrings(ListType listType)
    {
        _next = null;
        _contents = new ArrayList();
        commonConstructorCode(listType);
    }

    private void commonConstructorCode(ListType listType)
    {
        _listType = listType;
    }

    /**
     * This function will add duplicates, and it preserves the order in which ingredients are added, FIFO
     * @param ingredient
     * @param qty
     * @param unitOfMeasure
     */
    public void addIngredient(String ingredient, int qty, UOM unitOfMeasure)
    {
        _contents.add(ingredient);
        _quantities[ingredient] =
    }
}
