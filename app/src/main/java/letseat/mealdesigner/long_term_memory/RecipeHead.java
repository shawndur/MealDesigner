package letseat.mealdesigner.long_term_memory;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
import java.util.Map;
import letseat.mealdesigner.long_term_memory.ListComponent.ComponentType;

//import static letseat.mealdesigner.long_term_memory.RecipeHead.List_Type.EQUIPMENT;
//import static letseat.mealdesigner.long_term_memory.RecipeHead.List_Type.INGREDIENTS;

/**
 * Created by George_Sr on 9/28/2016.
 */

public class RecipeHead
{
    //    private ListComponent _equipmentList, _equiLast, _ingredientList, _ingrLast, _procedureList, _procLast, _commentList, _commLast;
//    private int _equiCount, _ingrCount, _procCount, _commCount;
    private Map<ComponentType, Integer> _counts;
    private Map<ComponentType, ListComponent> _heads /* , _tails */;
    private String _recipeName;



    /**
     * Used to create a recipe in dynamic memory, with no additional information given to the constructor other than the name of the recipe.
     */
    public RecipeHead(String recipeName)
    {
//        _equipmentList = _equiLast = _ingredientList = _ingrLast = _procedureList = _procLast = _commentList = _commLast = null;

//        _equiCount = _ingrCount = _procCount = _commCount = 0;

        _recipeName = recipeName;

        _counts = new HashMap<ComponentType,Integer>();

        _heads = new HashMap<ComponentType, ListComponent>();

//        _tails = new HashMap<ComponentType, ListComponent>();

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

    public void addEquipment(String equipment_name, int quantity_needed/*, ArrayList<String> comments*/,String additionalText)
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
                if(candidate.get(i).componentType() == ComponentType.PROCEDURE && targetType == ComponentType.PROCEDURE_WITH_TIMER
                        ||
                        candidate.get(i).componentType() == ComponentType.PROCEDURE_WITH_TIMER && targetType == ComponentType.PROCEDURE)
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





        System.out.println("Equipment:");
        if(_heads.containsKey(ComponentType.EQUIPMENT))
        {
//            _heads.get(ComponentType.EQUIPMENT).printAllInfo();
            _heads.get(ComponentType.EQUIPMENT).printRelevantInfo();
        }
        System.out.println("\nIngredients:");
        if(_heads.containsKey(ComponentType.INGREDIENT))
        {
//            _heads.get(ComponentType.INGREDIENT).printAllInfo();
            _heads.get(ComponentType.INGREDIENT).printRelevantInfo();
        }

        System.out.println("\nProcedures:");
        if(_heads.containsKey(ComponentType.PROCEDURE))
        {
//            _heads.get(ComponentType.PROCEDURE).printAllInfo();
            _heads.get(ComponentType.PROCEDURE).printRelevantInfo();
        }
        if(_heads.containsKey(ComponentType.PROCEDURE_WITH_TIMER))
        {
//            _heads.get(ComponentType.PROCEDURE_WITH_TIMER).printAllInfo();
            _heads.get(ComponentType.PROCEDURE_WITH_TIMER).printRelevantInfo();
        }

        System.out.println("\nComments:");
        if(_heads.containsKey(ComponentType.COMMENT))
        {
//            _heads.get(ComponentType.COMMENT).printAllInfo();
            _heads.get(ComponentType.COMMENT).printRelevantInfo();
        }

    }

    /**
     * a debugging method
     */
    public void println(String printable)
    {
        System.out.println(printable);
    }

    /**
     * a debugging method
     */
    public void print(String printable)
    {
        System.out.print(printable);
    }



}
