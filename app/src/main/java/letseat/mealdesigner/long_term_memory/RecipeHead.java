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



    public RecipeHead()
    {
//        _equipmentList = _equiLast = _ingredientList = _ingrLast = _procedureList = _procLast = _commentList = _commLast = null;

//        _equiCount = _ingrCount = _procCount = _commCount = 0;

        _counts = new HashMap<ComponentType,Integer>();

        _heads = new HashMap<ComponentType, ListComponent>();

        _tails = new HashMap<ComponentType, ListComponent>();

    }

    private int modifyComponentCount(final ListComponent component, int change)
    {
        return _counts.put(component.getComponentType(), _counts.get(component.getComponentType()) + change);
    }

    private int decrementComponentCount(final ListComponent component)
    {
        return modifyComponentCount(component,-1);
    }

    private int incrementComponentCount(final ListComponent component)
    {
        return modifyComponentCount(component,1);
    }

    private ListComponent updateFirstAndLast(ListComponent node)
    {
        ComponentType nodeType = node.getComponentType();

        if(incrementComponentCount(node) == 1)
        {
            return _heads.put(nodeType, _tails.put(nodeType, _heads.get(nodeType)));
        }

        return _tails.put(nodeType, _tails.get(nodeType).setNext(node));

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


}
