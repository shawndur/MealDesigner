package letseat.mealdesigner.long_term_memory;

import android.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
import java.util.Iterator;
import java.util.Map;
import letseat.mealdesigner.long_term_memory.ListComponent.ComponentType;
import letseat.mealdesigner.storage.Recipe;

import static letseat.mealdesigner.long_term_memory.ListComponent.ComponentType.*;
import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.NO_UOM;
import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.UOM_ERR;
import static letseat.mealdesigner.long_term_memory.RecipeHead.Allergen.*;
//import static letseat.mealdesigner.long_term_memory.RecipeHead.Allergen.FISH;
//import static letseat.mealdesigner.long_term_memory.RecipeHead.Allergen.PEANUTS;
//import static letseat.mealdesigner.long_term_memory.RecipeHead.Allergen.TREE_NUTS;

//import static letseat.mealdesigner.long_term_memory.RecipeHead.List_Type.EQUIPMENT;
//import static letseat.mealdesigner.long_term_memory.RecipeHead.List_Type.INGREDIENTS;

/**
 * Created by George_Sr on 9/28/2016.
 */

public class RecipeHead implements Recipe
{
    //    private ListComponent _equipmentList, _equiLast, _ingredientList, _ingrLast, _procedureList, _procLast, _commentList, _commLast;
//    private int _equiCount, _ingrCount, _procCount, _commCount;
    private Map<ComponentType, Integer> _counts;
    private Map<ComponentType, ListComponent> _heads /* , _tails */;
    private Map<Allergen, Boolean> _allergens;
    private String _recipeName;

//    /**
//     * Bit  Description
//     * 7    Gluten (including wheat, barley, rye, oats)
//     * 6    Soy
//     * 5    Eggs
//     * 4    Dairy / Lactose
//     * 3    Shellfish
//     * 2    Fish
//     * 1    Tree nuts
//     * 0    Peanuts
//     */
//    private int _allergenFlags = 0x00;

    public enum Allergen
    {
        PEANUTS, TREE_NUTS, FISH, SHELLFISH, DAIRY_LACTOSE, EGGS, SOY, GLUTEN, UNKNOWN
    }



    /**
     * Used to create a recipe in dynamic memory, with no additional information given to the constructor other than the name of the recipe. (this allows more generalized functionality)
     */
    public RecipeHead(String recipeName)
    {
//        _equipmentList = _equiLast = _ingredientList = _ingrLast = _procedureList = _procLast = _commentList = _commLast = null;

//        _equiCount = _ingrCount = _procCount = _commCount = 0;

        _recipeName = recipeName;

        _counts = new HashMap<ComponentType,Integer>();

        _heads = new HashMap<ComponentType, ListComponent>();

        _allergens = new HashMap<Allergen, Boolean>();

        initializeAllergenMapToFalse();



//        _tails = new HashMap<ComponentType, ListComponent>();

    }

    private void initializeAllergenMapToFalse()
    {
        _allergens.put(PEANUTS, false);
        _allergens.put(TREE_NUTS, false);
        _allergens.put(FISH, false);
        _allergens.put(SHELLFISH, false);
        _allergens.put(DAIRY_LACTOSE, false);
        _allergens.put(EGGS, false);
        _allergens.put(SOY, false);
        _allergens.put(GLUTEN, false);
    }

    public String name()
    {
        return _recipeName;
    }

//    /**
//     * Used to create a recipe in dynamic memory, with all details given up front in the arguments.  This will probably not be developed.  Yeah, forget this
//     */
//    public RecipeHead(String recipe_name, HashMap<Character, ArrayList<String>> recipe_details)
//    {
//
//    }

    private int modifyComponentCount(final ListComponent component, int change)
    {
        // calling this function once makes more sense than calling it a bunch of times in the return line.
        ListComponent.ComponentType componentType = component.componentType();

        // gonna make that compiler work!
        // this return statement first checks to ensure that _counts actually contains a non-null KV pair for the given component,
        // 		if true, it adds the incoming change to the existing value then places that value back in the key
        //		if false, it creates a key for the component then sets its value to change, but only if change is positive or zero; if change is negative it sets the key's value to zero.
        // It then checks if the displaced KV pair was null
        //		if null, it returns change if change is positive or zero, or zero if change is negative (you can't have -3 pans in a recipe, now can you?)
        //		if non-null, it checks if the displaced value was positive or zero and returns that value plus change, or in the case of a negative value in (change plus the displaced KV pair), returns zero.
        return (_counts.put(componentType, (_counts.containsKey(componentType))? _counts.get(componentType) + change : (change >= 0)? change : 0) == null)? ((change >= 0)? change : 0) : ((_counts.get(componentType) + change) >= 0)? (_counts.get(componentType) + change) : 0;
    }

    private int decrementComponentCount(final ListComponent component)
    {
        return modifyComponentCount(component,-1);
    }

    private int incrementComponentCount(final ListComponent component)
    {
        return modifyComponentCount(component,1);
    }

    private void addComponent(ListComponent node)
    {
        ComponentType nodeType = node.componentType();

        incrementComponentCount(node);

        if(_heads.containsKey(nodeType))
        {
            _heads.get(nodeType).addComponentToEnd(node);
            return;
        }
        _heads.put(nodeType,node);

// rewrite this to work with a linear insertion.
//
//        if(incrementComponentCount(node) == 1)
//        {
//
//            _heads.put(nodeType, node);
//
////            _tails.put(nodeType, node);
//
//            return;	// I think this was causing the stack overflow...  Bingo!!!
//        }
//
////        _tails.put(nodeType, node).setNext(node);	// this is the problem right here!
    }

    public void addEquipment(String equipment_name, double quantity_needed/*, ArrayList<String> comments*/,String additionalText)
    {

        addComponent(new ListComponent(equipment_name, quantity_needed, additionalText));
//        addComponent(new ListComponent(equipment_name, quantity_needed, comments));

    }

    public void addIngredient(String name, double quantity, ListComponent.UnitOfMeasure unit_of_measure, String preparation_procedure/*, ArrayList<String> comments,*/)
    {

        addComponent(new ListComponent(name, quantity, preparation_procedure, unit_of_measure));
//        addComponent(new ListComponent(name, quantity, preparation_procedure, comments, unit_of_measure));

    }

    public void addProcedureWithTimer(String name, double timer_in_seconds, String critical_points/*, ArrayList<String> comments*/)
    {

        addComponent(new ListComponent(name, timer_in_seconds, critical_points));
//        addComponent(new ListComponent(name, timer_in_seconds, hazards_and_critical_control_points, comments));

    }

    public void addProcedureWithoutTimer(String name, String critical_points/*, ArrayList<String> comments*/)
    {

        addComponent(new ListComponent(name, critical_points));
//        addComponent(new ListComponent(name, critical_points, comments));

    }

    public void addComment(String text)
    {
        addComponent(new ListComponent(text));

    }

    public int listSize(ComponentType componentType)
    {
        return (_counts.containsKey(componentType))? _counts.get(componentType) : 0;
    }

    /**
     *
     * @warning It is highly recommended that RecipeHead.listSize(ComponentType) should be used to retrieve the size of the list being returned by this function.
     * @warning It is the responsibility of the caller to ensure the case of a null return value is accounted for.
     * @param componentType From ListComponent.ComponentType
     * @return The head node of the relevant list, IFF it exists as a non-null member in this; else, null.  A linked-list is returned.
     */
    public ListComponent getList(ComponentType componentType)
    {
        return (_heads.containsKey(componentType))? _heads.get(componentType) : null;
    }


    /**
     * To iterate through a particular list in this recipe and remove a node with a particular name, (could be a user-supplied name) use this function.  This removal is NOT case-sensitive!
     * @param componentType The particular list in which the node may be found
     * @param text The name (ingredient name, equipment name, procedure instructions, or a comment) to be matched with a member of the list.
     * @return True if the removal is executed successfully, false if the removal could not be completed. (either the wrong name was passed or that particular list has not yet been created)
     */
    public boolean removeComponent(ComponentType componentType, String text)
    {
        if(!_heads.containsKey(componentType))
        {
            return false;
        }

        ListComponent current = _heads.get(componentType);

        while(current != null && current.name().compareToIgnoreCase(text) != 0)
        {
            current = current.next();
        }

        return (current == null)? false : removeComponent(current); // if the while loop reached the end of the list (where current == null is true) without reaching the matching node, false is return; otherwise this function branches into the actual removal and returns whatever the value of that function is.
    }

    /**
     * A function useful for safely removing a node from a recipe.
     * @param node The actual instance of the node that is to be removed from its respective list.
     * @return True if the removal is confirmed, false if there is an error.
     */
    public boolean removeComponent(ListComponent node)
    {
        // in the case of there is nothing before or after the node, the corresponding entry in _heads, _tails (???), and _counts must be removed.
        if(node.previous() == null && node.next() == null)
        {
            _heads.remove(node.componentType());
            _counts.remove(node.componentType());

            return !(_heads.containsKey(node.componentType()) && _counts.containsKey(node.componentType()));
//            _tails.remove(node.getComponentType());
        }

        // in the case that a node is at the head of its list
        if(node.previous() == null && node.next() != null)
        {
            _heads.put(node.componentType(), node.next());

            node.next().setPrevious(null);

            decrementComponentCount(node);

            return _heads.containsKey(node.componentType())? _heads.get(node.componentType()) == node.next() : false;
        }

        // in the case that a node is at the tail of its list
        if(node.previous() != null && node.next() == null)
        {
            node.previous().setNext(null);

            decrementComponentCount(node);

            return node.previous().next() == null;
        }

        // all remaining cases are where the node is in the list and not at the head or tail thereof
        node.previous().setNext(node.next());

        node.next().setPrevious(node.previous());

        decrementComponentCount(node);

        return node.previous().next() == node.next() && node.next().previous() == node.previous();
    }

    /**
     * Particularly intended for functionality wherein the entire recipe data needs to be displayed on-screen at once.
     * @param componentType The particular, requested component of the recipe.
     * @return if the requested component of the recipe exists in _heads, an array list with all nodes is returned; if the requested component does not exist in _heads, a blank ArrayList<ListComponent> object is returned.
     */
    public ArrayList<ListComponent> getAllNodes(ComponentType componentType)
    {
        ArrayList<ListComponent> output = new ArrayList<ListComponent>();

        // in the case of there not being any nodes assigned to a particular recipe component
        if(!_heads.containsKey(componentType))
        {
            return output;
        }

        for(ListComponent current = _heads.get(componentType) ; current != null ; current = current.next())
        {
            output.add(current);
        }

        return output;


    }

    public String getRecipeInfoInStringFormat()
    {

        // This is a duplicate of the fields at the top of Long_Term_Interface
        final char _RECIPE_NAME = (char) 0x80, _end_RECIPE_NAME = (char) 0x90,
                _EQUIPMENT = (char) 0x81, _end_EQUIPMENT = (char) 0x91,
                _INGREDIENT = (char) 0x82, _end_INGREDIENT = (char) 0x92,
                _PROCEDURE = (char) 0x83, _end_PROCEDURE = (char) 0x93,
                _COMMENTS = (char) 0x84, _end_COMMENTS = (char) 0x94,
                _END_OF_RECIPE = (char) 0x85,
                _COMPONENT = (char) 0x86, _end_COMPONENT = (char) 0x96,
                _COMMA = (char) 0x87;

        String output = _RECIPE_NAME + _recipeName + _end_RECIPE_NAME + _EQUIPMENT;

        ListComponent current = _heads.get(ComponentType.EQUIPMENT);

        while(current != null)
        {
            output += current.getInfoForWrite();
            current = current.next();
        }

        output += _end_EQUIPMENT+ "" + _INGREDIENT;

        current = _heads.get(ComponentType.INGREDIENT);

        while(current != null)
        {
            output += current.getInfoForWrite();
            current = current.next();
        }

        output += _end_INGREDIENT+ "" + _PROCEDURE;

        current = _heads.get(PROCEDURE);

        while(current != null)
        {
            output += current.getInfoForWrite();
            current = current.next();
        }

        output += _end_PROCEDURE+ "" + _COMMENTS;

        current = _heads.get(ComponentType.COMMENT);

        while(current != null)
        {
            output += current.getInfoForWrite();
            current = current.next();
        }

        return output + _END_OF_RECIPE +""+ getAllergensForMemory();
//        return output + _END_OF_RECIPE;   // changed to ^




    }

    /**
     * Use this function to allow the user to write changes in the order of ListComponent objects for a particular component type.  Use RecipeHead.validateWholeSectionRewrite(...) to ensure all members of the argument for this function have the same value for their Component Type field.
     * @param listNew The new list of ListComponent objects.
     */
    public void resetNodesInASection(ArrayList<ListComponent> listNew)
    {
        ListComponent headNew = listNew.get(0);

        _heads.put(headNew.componentType(), headNew);

        // from this point, headNew will have its value changed to the next member of listNew
        //	this is so that the loop can iterate through listNew and set the headNew._next to the next member of listNew
        for(int i = 1; i < listNew.size(); i++)
        {
            // since setNext(...) returns the argument unchanged, and since that argument is the next node in the list, setPrevious(headNew) can be used directly on it.
            headNew = headNew.setNext(listNew.get(i)).setPrevious(headNew).next();


        }

    }

    public boolean validateWholeSectionRewrite(ArrayList<ListComponent> candidate)
    {
        int candSize;

        if((candSize = candidate.size()) == 0)
        {
            return false;
        }

        ListComponent.ComponentType targetType = candidate.get(0).componentType();

        for(int i = 0; i < candSize; i++)
        {
            if(candidate.get(i).componentType() != targetType)
            {
                if(candidate.get(i).componentType() == PROCEDURE && targetType == ComponentType.PROCEDURE_WITH_TIMER
                        ||
                        candidate.get(i).componentType() == ComponentType.PROCEDURE_WITH_TIMER && targetType == PROCEDURE)
                {
                    // since it is okay to have PROCEDURE and PROCEDURE_WITH_TIMER in the same list together, there is no need to reject the candidate.
                    continue;
                }
                return false;
            }
        }

        return true;
    }



    /**
     * A debugging method, which prints all information on the recipe
     */
    public void printAll()
    {





        Log.d("status","Equipment:");
        if(_heads.containsKey(ComponentType.EQUIPMENT))
        {
//            _heads.get(ComponentType.EQUIPMENT).printAllInfo();
            _heads.get(ComponentType.EQUIPMENT).printRelevantInfo();
        }
        Log.d("status","\nIngredients:");
        if(_heads.containsKey(ComponentType.INGREDIENT))
        {
//            _heads.get(ComponentType.INGREDIENT).printAllInfo();
            _heads.get(ComponentType.INGREDIENT).printRelevantInfo();
        }

        Log.d("status","\nProcedures:");
        if(_heads.containsKey(PROCEDURE))
        {
//            _heads.get(ComponentType.PROCEDURE).printAllInfo();
            _heads.get(PROCEDURE).printRelevantInfo();
        }
        if(_heads.containsKey(ComponentType.PROCEDURE_WITH_TIMER))
        {
//            _heads.get(ComponentType.PROCEDURE_WITH_TIMER).printAllInfo();
            _heads.get(ComponentType.PROCEDURE_WITH_TIMER).printRelevantInfo();
        }

        Log.d("status","\nComments:");
        if(_heads.containsKey(ComponentType.COMMENT))
        {
//            _heads.get(ComponentType.COMMENT).printAllInfo();
            _heads.get(ComponentType.COMMENT).printRelevantInfo();
        }

    }


    public boolean setAllergen(Allergen alerg)
    {
        _allergens.put(alerg,true);

        Log.d("status","Allergen \"" + alerg + "\" has been set to \"" + _allergens.get(alerg) + "\"");

        return _allergens.get(alerg);
    }

    public boolean clearAllergen(Allergen alerg)
    {
        _allergens.put(alerg, false);

        Log.d("status","Allergen \"" + alerg + "\" has been set to \"" + _allergens.get(alerg) + "\"");

        return _allergens.get(alerg);
    }


//    /**
//     * Bit  Description
//     * 7    Gluten (including wheat, barley, rye, oats)
//     * 6    Soy
//     * 5    Eggs
//     * 4    Dairy / Lactose
//     * 3    Shellfish
//     * 2    Fish
//     * 1    Tree nuts
//     * 0    Peanuts
//     */
//
//    public enum Allergen
//    {
//        PEANUTS, TREE_NUTS, FISH, SHELLFISH, DAIRY_LACTOSE, EGGS, SOY, GLUTEN, UNKNOWN
//    }
    public int getAllergenFlags()
    {
        int output = 0;


        if(_allergens.get(PEANUTS))
        {
            output |= 0x01;
        }
        if(_allergens.get(TREE_NUTS))
        {
            output |= 0x02;
        }
        if(_allergens.get(FISH))
        {
            output |= 0x04;
        }
        if(_allergens.get(SHELLFISH))
        {
            output |= 0x08;
        }
        if(_allergens.get(DAIRY_LACTOSE))
        {
            output |= 0x10;
        }
        if(_allergens.get(EGGS))
        {
            output |= 0x20;
        }
        if(_allergens.get(SOY))
        {
            output |= 0x40;
        }
        if(_allergens.get(GLUTEN))
        {
            output |= 0x80;
        }

        return output;

    }

    public String getAllergensForMemory()
    {
        String output = "";

        int allrgFlags = getAllergenFlags();

        if(allrgFlags > 0x0FF)  // in the future if more food allergens are discovered, this conditional may be necessary, but as it is today there are only 8 common food allergens which fits nicely into a single byte (or a char)
        {
            // code to handle extra allergens go here in case more are discovered in the future.
        }

        return (char) allrgFlags + "\0\0\0";

    }

    public void decipherAllergenFlags(String allergenFlags)
    {
        if(allergenFlags.length() == 0)
        {
            return;
        }

        char current = allergenFlags.charAt(0);

        Allergen cur_allrg = PEANUTS;

        for(int stamp = 0x01; stamp < 257; stamp <<= 1)
        {
            if((current & stamp) == stamp)
            {
                setAllergen(cur_allrg);
            }
            cur_allrg = nextAllergen(cur_allrg);
        }


    }

    private Allergen nextAllergen(Allergen current)
    {
        switch(current)
        {
            case PEANUTS:
                return TREE_NUTS;
            case TREE_NUTS:
                return FISH;
            case SHELLFISH:
                return DAIRY_LACTOSE;
            case DAIRY_LACTOSE:
                return EGGS;
            case EGGS:
                return SOY;
            case SOY:
                return GLUTEN;
            default:
                return UNKNOWN;
        }
    }

    /**
     * a debugging method
     */
    public void println(String printable)
    {
        Log.d("status",printable);
    }

    /**
     * a debugging method
     */
    public void print(String printable)
    {
        System.out.print(printable);
    }


    /****************************
     * Interface Implementation *
     *   -Shawn Durandetto      *
     **************************/

    public ArrayList<String> getSteps(){
        ArrayList<String> strsteps = new ArrayList<>();
        ListComponent steps =  getList( PROCEDURE );
        if(steps == null) return strsteps;
        for(;steps.hasNext();steps = steps.next()){
            strsteps.add(steps.name());
        }
        strsteps.add(steps.name());
        return strsteps;
    }

    public boolean setSteps(ArrayList<String> steps){
        ListComponent head = null;
        ListComponent curr = null;
        for(String step : steps){
            if(head == null){
                head = new ListComponent(step,"");
                curr=head;
            }else{
                curr.setNext(new ListComponent(step,""));
                curr = curr.next();
            }
        }
        if(_heads.containsKey(PROCEDURE)) _heads.remove(PROCEDURE);
        _heads.put(PROCEDURE,head);
        return true;
    }
    
    public ArrayList<String> getTools(){
        ArrayList<String> strtools = new ArrayList<>();
        ListComponent tools =  getList( EQUIPMENT );
        if(tools == null) return strtools;
        for(;tools.hasNext();tools = tools.next()){
            strtools.add(tools.name());
        }
        strtools.add(tools.name());
        return strtools;
    }
    
    public boolean setTools(ArrayList<String> tools){
        ListComponent head = null;
        ListComponent curr = null;
        for(String tool : tools){
            if(head == null){
                head = new ListComponent(tool,0,"");
                curr=head;
            }else{
                curr.setNext(new ListComponent(tool,0,""));
                curr = curr.next();
            }
        }
        if(_heads.containsKey(EQUIPMENT)) _heads.remove(EQUIPMENT);
        _heads.put(EQUIPMENT,head);
        return true;
    }
    
    public ArrayList<String> getIngredients(){
        ArrayList<String> stringr = new ArrayList<>();
        ListComponent ingr =  getList( INGREDIENT );
        if(ingr == null) return stringr;
        for(;ingr.hasNext();ingr = ingr.next()){
            String toAdd = "";
            if(ingr.hasQuantity()) toAdd += ingr.getQuantity()+" ";
            if(ingr.unitOfMeasure() != NO_UOM &&  ingr.unitOfMeasure() != UOM_ERR)
                toAdd += ingr.unitOfMeasure()+" ";
            toAdd += ingr.name();
            stringr.add(toAdd);
        }
        String toAdd = "";
        if(ingr.hasQuantity()) toAdd += ingr.getQuantity()+" ";
        if(ingr.unitOfMeasure() != NO_UOM &&  ingr.unitOfMeasure() != UOM_ERR)
            toAdd += ingr.unitOfMeasure()+" ";
        toAdd += ingr.name();
        stringr.add(toAdd);
        return stringr;
    }
    
    public boolean setIngredients(ArrayList<String> ingredients){
        ListComponent head = null;
        ListComponent curr = null;
        for(String ingredient : ingredients){
            if(head == null){
                head = new ListComponent(ingredient,0,"", NO_UOM);
                curr=head;
            }else{
                curr.setNext(new ListComponent(ingredient,0,"", NO_UOM));
                curr = curr.next();
            }
        }
        if(_heads.containsKey(INGREDIENT)) _heads.remove(INGREDIENT);
        _heads.put(INGREDIENT,head);
        return true;
    }

    public boolean setTempRecipePass(ArrayList<String> temp) {
        return false;
    }
}
