package letseat.mealdesigner.long_term_memory;

import java.util.ArrayList;
import java.util.HashMap;
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
    private Map<ComponentType, ListComponent> _heads, _tails;
    private String _recipeName;



    public RecipeHead(String recipeName)
    {
//        _equipmentList = _equiLast = _ingredientList = _ingrLast = _procedureList = _procLast = _commentList = _commLast = null;

//        _equiCount = _ingrCount = _procCount = _commCount = 0;

        _recipeName = recipeName;

        _counts = new HashMap<ComponentType,Integer>();

        _heads = new HashMap<ComponentType, ListComponent>();

        _tails = new HashMap<ComponentType, ListComponent>();

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

    private void updateFirstAndLast(ListComponent node)
    {
        ComponentType nodeType = node.getComponentType();

        if(incrementComponentCount(node) == 1)
        {

            _heads.put(nodeType, node);

            _tails.put(nodeType, node);

            return;	// I think this was causing the stack overflow...  Bingo!!!
        }

        _tails.put(nodeType, node).setNext(node);	// this is the problem right here!
    }

    public void addEquipment(String equipment_name, double quantity_needed, ArrayList<String> comments)
    {

        updateFirstAndLast(new ListComponent(equipment_name, quantity_needed, comments));

//        ListComponent equipmentComponent = new ListComponent(equipment_name, quantity_needed, comments);

//        incrementComponentCount(equipmentComponent);
//
//        if(_equipmentList == null)
//        {
//            _equiLast = _equipmentList = equipmentComponent;
//
//
//        }
//        else
//        {
//            _equiLast = _equiLast.setNext(equipmentComponent);
//        }
    }

    public void addIngredient(String name, double quantity, String preparation_procedure, ArrayList<String> comments, ListComponent.UnitOfMeasure unit_of_measure)
    {

        updateFirstAndLast(new ListComponent(name, quantity, preparation_procedure, comments, unit_of_measure));

//        ListComponent ingredientComponent = new ListComponent(name, quantity, preparation_procedure, comments, unit_of_measure);
//
//        incrementComponentCount(ingredientComponent);
//
//        if(_ingredientList == null)
//        {
//            _ingrLast = _ingredientList = ingredientComponent;
//        }
//        else
//        {
//            _ingrLast = _ingrLast.setNext(ingredientComponent);
//        }
    }

    public void addProcedureWithTimer(String name, double timer_in_seconds, String hazards_and_critical_control_points, ArrayList<String> comments)
    {

        updateFirstAndLast(new ListComponent(name, timer_in_seconds, hazards_and_critical_control_points, comments));

//        ListComponent procedureComponentWithTimer = new ListComponent(name, timer_in_seconds, hazards_and_critical_control_points, comments);
//
////        _procCount++;
//
//        incrementComponentCount(procedureComponentWithTimer);
//
//
//
//        if(_procedureList == null)
//        {
//            _procLast = _procedureList = procedureComponentWithTimer;
//        }
//        else
//        {
//            _procLast = _procLast.setNext(procedureComponentWithTimer);
//        }
    }

    public void addProcedureWithoutTimer(String name, String hazards_and_critical_control_points, ArrayList<String> comments)
    {

        updateFirstAndLast(new ListComponent(name, hazards_and_critical_control_points, comments));

//        ListComponent procedureComponentWithoutTimer = new ListComponent(name, hazards_and_critical_control_points, comments);
//
////        _procCount++;
//
//        incrementComponentCount(procedureComponentWithoutTimer);
//
//        if(_procedureList == null)
//        {
//            _procLast = _procedureList = procedureComponentWithoutTimer;
//        }
//        else
//        {
//            _procLast = _procLast.setNext(procedureComponentWithoutTimer);
//        }
    }

    public void addComment(String text)
    {
        updateFirstAndLast(new ListComponent(text));

//        ListComponent commentComponent = new ListComponent(text);
//
////        _commCount++;
//
//        incrementComponentCount(commentComponent);
//
//        if(_commentList == null)
//        {
//            _commLast = _commentList = commentComponent;
//        }
//        else
//        {
//            _commLast = _commLast.setNext(commentComponent);
//        }
    }

    public int listSize(ComponentType componentType)
    {
        return _counts.get(componentType);
    }

//    public void sortComponents(ComponentType componentType)
//    {
//        _tails.remove(componentType);
//
//        _heads
//    }


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
