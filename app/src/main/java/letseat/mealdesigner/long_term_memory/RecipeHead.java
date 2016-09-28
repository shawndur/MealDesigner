package letseat.mealdesigner.long_term_memory;

import java.util.ArrayList;

/**
 * Created by George_Sr on 9/28/2016.
 */

public class RecipeHead
{
    private ListComponent _equipmentList, _equiLast, _ingredientList, _ingrLast, _procedureList, _procLast, _commentList, _commLast;
    private int _equiCount, _ingrCount, _procCount, _commCount;
    private String _recipeName;

    public RecipeHead()
    {
        _equipmentList = _equiLast = _ingredientList = _ingrLast = _procedureList = _procLast = _commentList = _commLast = null;

        _equiCount = _ingrCount = _procCount = _commCount = 0;
    }

    public void addEquipment(String equipment_name, double quantity_needed, ArrayList<String> comments)
    {
        ListComponent toAdd = new ListComponent(equipment_name, quantity_needed, comments);

        _equiCount++;

        if(_equipmentList == null)
        {
            _equiLast = _equipmentList = toAdd;


        }
        else
        {
            _equiLast.setNext(toAdd);
        }
    }

    public void addIngredient(String name, double quantity, String preparation_procedure, ArrayList<String> comments, ListComponent.UnitOfMeasure unit_of_measure)
    {
        ListComponent toAdd = new ListComponent(name, quantity, preparation_procedure, comments, unit_of_measure);

        _ingrCount++;

        if(_ingredientList == null)
        {
            _ingrLast = _ingredientList = toAdd;
        }
        else
        {
            _equiLast.setNext(toAdd);
        }
    }

    public void addProcedureWithTimer(String name, double timer_in_seconds, String hazards_and_critical_control_points, ArrayList<String> comments)
    {
        ListComponent toAdd = new ListComponent(name, timer_in_seconds, hazards_and_critical_control_points, comments);

        _procCount++;

        if(_procedureList == null)
        {
            _procLast = _procedureList = toAdd;
        }
        else
        {
            _procLast.setNext(toAdd);
        }
    }

    public void addProcedureWithoutTimer(String name, String hazards_and_critical_control_points, ArrayList<String> comments)
    {
        ListComponent toAdd = new ListComponent(name, hazards_and_critical_control_points, comments);

        _procCount++;

        if(_procedureList == null)
        {
            _procLast = _procedureList = toAdd;
        }
        else
        {
            _procLast.setNext(toAdd);
        }
    }

    // still needs comments, but I need to sleep!



}
