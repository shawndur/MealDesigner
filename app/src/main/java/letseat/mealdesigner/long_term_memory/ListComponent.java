package letseat.mealdesigner.long_term_memory;

import java.util.ArrayList;

import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.GALLON;
import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.NO_UOM;
import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.OUNCE;
import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.POUND;

/**
 * Created by George_Sr on 9/27/2016.
 */

public class ListComponent
{
    enum ComponentType
    {
        EQUIPMENT, INGREDIENT, PROCEDURE, PROCEDURE_WITH_TIMER, COMMENT
    }

    enum UnitOfMeasure
    {
        // in the case of no UoM
        NO_UOM,

        // non-empirical units
        EACH, BAG, POUCH, STRIP, // more to come!

        // English dry
        OUNCE, POUND,

        // English wet
        TEASPOON, TABLESPOON, FLUID_OUNCE, CUP, PINT, QUART, GALLON,

        // Metric dry
        GRAM, KILOGRAM,

        // Metric wet
        MILLILITER, LITER
    }

    private final ComponentType _compType;

    private double _quantity;
    private String _name, _additionalText;
    private ArrayList<String> _comments;
    private UnitOfMeasure _unitOfMeasure;


    /**
     * Equipment constructor
     */
    public ListComponent(ComponentType compType, String name, double quantity, ArrayList<String> comments)
    {
        _compType = ComponentType.EQUIPMENT;

        commonComponentConstructorCode(name, quantity, "", comments, NO_UOM);
    }
    /**
     * Ingredient constructor
     * use _additionalText as preparation procedure!
     */
    public ListComponent(String name, double quantity, String preparation_procedure, ArrayList<String> comments, UnitOfMeasure unit_of_measure)
    {
        _compType = ComponentType.INGREDIENT;

        commonComponentConstructorCode(name, quantity, preparation_procedure, comments, unit_of_measure);

    }

    /**
     * Procedure constructor, with timer
     */
    public ListComponent(String name, double timer_in_seconds, String hazards_and_critical_control_points, ArrayList<String> comments)
    {
        _compType = ComponentType.PROCEDURE_WITH_TIMER;

        commonComponentConstructorCode(name, timer_in_seconds, hazards_and_critical_control_points, comments, NO_UOM);
    }

    /**
     * Procedure constructor, without timer
     */
    public ListComponent(String name, String hazards_and_critical_control_points, ArrayList<String> comments)
    {
        _compType = ComponentType.PROCEDURE;

        commonComponentConstructorCode(name, -1.0, hazards_and_critical_control_points, comments, NO_UOM);
    }

    /**
     * Comment Constructor
     */
    public ListComponent(String name)
    {
        _compType = ComponentType.COMMENT;

        commonComponentConstructorCode(name, -1.0, "", null, NO_UOM);
    }

    public void commonComponentConstructorCode(String name, double qty, String additionalText, ArrayList<String> comments, UnitOfMeasure uom)
    {
        _name = name;
    }



    public ComponentType getComponentType()
    {
        return _compType;
    }

    public double getQuantity()
    {
        return _quantity;
    }

    public boolean hasQuantity()
    {
        return _quantity >= 0.0;
    }

    public double getQuantity(double multiplier)
    {
        return multiplier*_quantity;
    }

    public String getText()
    {
        return _name;
    }

    public String getAdditionalText()
    {
        return _additionalText;
    }

    public ArrayList<String> getAllComments()
    {
        return _comments;
    }

    public String getSpecificComment(int i)
    {

        return (_comments.size() > i)? _comments.get(i) : "Indexing error while attempting to retrieve comment from given position.";
    }

    public void addComment(String commentIn)
    {
        _comments.add(commentIn);
    }

    public void deleteComment(int i)
    {
        _comments.remove(i);
    }

    /**
     * removes all occurrences of the given string from the comments
     * @param toDelete the string to be removed
     */
    public void deleteComment(String toDelete)
    {
        for(int i = 0; i < _comments.size(); i++)
        {
            if(_comments.get(i).compareToIgnoreCase(toDelete) == 0)
            {
                _comments.remove(i);
            }
        }
    }

    public UnitOfMeasure getUnitOfMeasure()
    {
        return _unitOfMeasure;
    }

    public ConversionBundle getUnitOfMeasure(double multiplier) // this will probably be renamed to scaleRecipe
    {
        double rawConvertedQuantity = _quantity * multiplier;

        ConversionBundle output = new ConversionBundle(rawConvertedQuantity, _unitOfMeasure, -1.0, NO_UOM, (multiplier > 10));

        if(multiplier > 10)
        {
            output._conversionWarning = true;
        }

        switch(_unitOfMeasure)
        {
            case NO_UOM:
            case EACH:
            case BAG:
            case POUCH:
            case STRIP:
            {
                return output;
            }

            case OUNCE:
            {
                if((rawConvertedQuantity)> 16)
                {
                    output._primaryQuantity = rawConvertedQuantity / 16;
                    output._primaryUnitOfMeasure = POUND;

                    output._secondaryQuantity = (rawConvertedQuantity % 16 != 0)? rawConvertedQuantity%16 : -1.0;
                    output._secondaryUnitOfMeasure = (rawConvertedQuantity % 16 != 0)? OUNCE : NO_UOM;
                }
                return output;
            }

            case POUND:
            {
                if((rawConvertedQuantity) < 1)
                {
                    output._primaryQuantity = rawConvertedQuantity * 16;
                    output._primaryUnitOfMeasure = OUNCE;

                    // no need to touch the secondaries, since pounds are already being converted to lower units (ounces) and there are none lower!
                }
                return output;
            }

            case TEASPOON:
            {
                if((rawConvertedQuantity) > (3*2*8*2*2*4))  // teaspoons to gallons
                {


                    output._primaryQuantity = rawConvertedQuantity / (3*2*8*2*2*4);
                    output._primaryUnitOfMeasure = GALLON;

                    rawConvertedQuantity = rawConvertedQuantity % (3*2*8*2*2*4);    // should be a number no greater than 768

                    if(rawConvertedQuantity / 4 == )    // TODO:  Set this up so it selects the correct unit of measure, such that it doesn't give 600 teaspoons nor .003 gallons!
                }
            }
        }
    }


    class ConversionBundle
    {
        public double _primaryQuantity, _secondaryQuantity;
        public UnitOfMeasure _primaryUnitOfMeasure, _secondaryUnitOfMeasure;
        public boolean _conversionWarning;
        private String _warningMessage = "Warning:  If the quantities of a recipe are increased significantly, the ingredients' quantities may need to be fine-tuned to taste.";


        public ConversionBundle(double quantity, UnitOfMeasure unitOfMeasure, double secondQuantity, UnitOfMeasure secondUOM, boolean conversionWarning)
        {
            _primaryQuantity = quantity;
            _primaryUnitOfMeasure = unitOfMeasure;
            _secondaryQuantity = secondQuantity;
            _secondaryUnitOfMeasure = secondUOM;
            _conversionWarning = conversionWarning;

        }

        /**
         * If the conversion warning is set for this ingredient, this function will return an advisory to the user that the listed ingredient may need to be fine-tuned.
         * @return
         */
        public String getWarning()
        {
            return (_conversionWarning)? _warningMessage : "";
        }
    }


}

