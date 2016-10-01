package letseat.mealdesigner.long_term_memory;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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



    public RecipeHead(String recipeName)
    {
//        _equipmentList = _equiLast = _ingredientList = _ingrLast = _procedureList = _procLast = _commentList = _commLast = null;

//        _equiCount = _ingrCount = _procCount = _commCount = 0;

        _recipeName = recipeName;

        _counts = new HashMap<ComponentType,Integer>();

        _heads = new HashMap<ComponentType, ListComponent>();

//        _tails = new HashMap<ComponentType, ListComponent>();

    }

    public RecipeHead(String recipeName, ArrayList<String> equipment_list, ArrayList<String> ingredients_list, ArrayList<String> procedures_list, ArrayList<String> comments_list)
    {
        _recipeName = recipeName;

        _counts = new HashMap<ComponentType,Integer>();

        _heads = new HashMap<ComponentType, ListComponent>();

        // this loop adds all members of equipment_list to this recipe
        for(int i = 0; i < equipment_list.size(); i++)
        {
            String current = equipment_list.get(i);

            if(current.length() == 0)   // ignores all empty strings
            {
                continue;
            }

            // needs to parse on commas

        }
    }

    private int modifyComponentCount(final ListComponent component, int change)
    {
        // calling this function once makes more sense than calling it a bunch of times in the return line.
        ListComponent.ComponentType componentType = component.getComponentType();

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
        ComponentType nodeType = node.getComponentType();

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

    public void addEquipment(String equipment_name, double quantity_needed, ArrayList<String> comments)
    {

        addComponent(new ListComponent(equipment_name, quantity_needed, comments));

    }

    public void addIngredient(String name, double quantity, String preparation_procedure, ArrayList<String> comments, ListComponent.UnitOfMeasure unit_of_measure)
    {

        addComponent(new ListComponent(name, quantity, preparation_procedure, comments, unit_of_measure));

    }

    public void addProcedureWithTimer(String name, double timer_in_seconds, String hazards_and_critical_control_points, ArrayList<String> comments)
    {

        addComponent(new ListComponent(name, timer_in_seconds, hazards_and_critical_control_points, comments));

    }

    public void addProcedureWithoutTimer(String name, String hazards_and_critical_control_points, ArrayList<String> comments)
    {

        addComponent(new ListComponent(name, hazards_and_critical_control_points, comments));

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

//    public boolean removeComponent(ComponentType componentType, int order)
//    {
//        if(!_heads.containsKey(componentType))
//        {
//            return false;
//        }
//
//        ListComponent current = _heads.get(componentType);
//
//        while(current.order() != order && current != null)
//        {
//            current = current.next();
//        }
//
//        return (current == null)? false : removeComponent(current);
//    }

    public boolean removeComponent(ComponentType componentType, String text)
    {
        if(!_heads.containsKey(componentType))
        {
            return false;
        }

        ListComponent current = _heads.get(componentType);

        while(current != null && current.getText().compareToIgnoreCase(text) != 0)
        {
            current = current.next();
        }

        return (current == null)? false : removeComponent(current);
    }

    public boolean removeComponent(ListComponent node)
    {
        // in the case of there is nothing before or after the node, the corresponding entry in _heads, _tails (???), and _counts must be removed.
        if(node.previous() == null && node.next() == null)
        {
            _heads.remove(node.getComponentType());
            _counts.remove(node.getComponentType());

            return !(_heads.containsKey(node.getComponentType()) && _counts.containsKey(node.getComponentType()));
//            _tails.remove(node.getComponentType());
        }

        // in the case that a node is at the head of its list
        if(node.previous() == null && node.next() != null)
        {
            _heads.put(node.getComponentType(), node.next());

            node.next().setPrevious(null);

            decrementComponentCount(node);

            return _heads.containsKey(node.getComponentType())? _heads.get(node.getComponentType()) == node.next() : false;
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
        ArrayList<ListComponent> output = new ArrayList();

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
     * A debugging method
     */
    public void printAll()
    {
        if(_heads.containsKey(ComponentType.EQUIPMENT))
        {
            _heads.get(ComponentType.EQUIPMENT).printAllInfo();
        }

        if(_heads.containsKey(ComponentType.INGREDIENT))
        {
            _heads.get(ComponentType.INGREDIENT).printAllInfo();
        }

        if(_heads.containsKey(ComponentType.PROCEDURE))
        {
            _heads.get(ComponentType.PROCEDURE).printAllInfo();
        }

        if(_heads.containsKey(ComponentType.PROCEDURE_WITH_TIMER))
        {
            _heads.get(ComponentType.PROCEDURE_WITH_TIMER).printAllInfo();
        }

        if(_heads.containsKey(ComponentType.COMMENT))
        {
            _heads.get(ComponentType.COMMENT).printAllInfo();
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
