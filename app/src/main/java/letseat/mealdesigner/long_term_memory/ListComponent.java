package letseat.mealdesigner.long_term_memory;

import java.util.ArrayList;

//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.CUP;
//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.FLUID_OUNCE;
//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.GALLON;
//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.NO_UOM;
//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.OUNCE;
//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.PINT;
//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.POUND;
//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.QUART;
//import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.TABLESPOON;

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
        EACH, BAG, POUCH, STRIP, SLICE, // more to come!

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
    private int _order;

    private ListComponent _next;


    /**
     * Equipment constructor
     */
    public ListComponent(String name, double quantity, ArrayList<String> comments)
    {
        _compType = ComponentType.EQUIPMENT;

        commonComponentConstructorCode(name, quantity, "", comments, UnitOfMeasure.NO_UOM);
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

        commonComponentConstructorCode(name, timer_in_seconds, hazards_and_critical_control_points, comments, UnitOfMeasure.NO_UOM);
    }

    /**
     * Procedure constructor, without timer
     */
    public ListComponent(String name, String hazards_and_critical_control_points, ArrayList<String> comments)
    {
        _compType = ComponentType.PROCEDURE;

        commonComponentConstructorCode(name, -1.0, hazards_and_critical_control_points, comments, UnitOfMeasure.NO_UOM);
    }

    /**
     * Comment Constructor:
     * The ListComponent with _compType == COMMENT is different from the ArrayList of comments that are added within each other instantiation of this class.
     * This is because those comments pertain to an individual step, ingredient, or piece of machinery.
     * This constructor creates a ListComponent object which contains information which pertains to the overall recipe,
     * right down to the user who may wish to include a story about the recipe which is unrelated to any actual information about cooking.
     */
    public ListComponent(String name)
    {
        _compType = ComponentType.COMMENT;

        commonComponentConstructorCode(name, -1.0, "", null, UnitOfMeasure.NO_UOM);
    }

    private void commonComponentConstructorCode(String name, double qty, String additionalText, ArrayList<String> comments, UnitOfMeasure uom)
    {
        _name = name;

        _quantity = qty;

        _comments = comments;

        _unitOfMeasure = uom;

        _additionalText = additionalText;
    }

    public ListComponent next()
    {
        return _next;
    }

    /**
     *
     * @param next a new node in to be appended to the end of whatever list for which this component is used.
     * @return the argument as it was passed so that the caller's "_last" variable can keep up-to-date without any extra runtime, (since AAPCS dictates that r1 be used for both arguments and return variables, the value of the variable doesn't require any additional machine language!)
     */
    public ListComponent setNext(ListComponent next)
    {
        return _next = next;
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



    public int setOrder(int order)
    {
        return (_order = order)+1;
    }


/*
* This is eating up too much time in development.
* will revisit this later!
*
  public ConversionBundle getUnitConversion(double multiplier) // this will probably be renamed to scaleRecipe
  {
      double rawConvertedQuantity = _quantity * multiplier;

      ConversionBundle output = new ConversionBundle(rawConvertedQuantity, _unitOfMeasure, -1.0, NO_UOM, (multiplier > 10));

      if(multiplier > 10)
      {
          output._conversionWarning = true;
      }

      EnglishWetNode wetHead = new EnglishWetNode(_quantity, _unitOfMeasure);

      EnglishWetNode current = wetHead;

      // this searches the EnglishWetNode list for the highest
      // then exits the loop when either the next node is null (indicating end-of-valid nodes)
      //      or when the quantity being passed becomes zero
      while (current._next != null && current._next._qty > 0)
      {
          current = current._next;
      }

      output._primaryQuantity = current._qty;
      output._primaryUnitOfMeasure = current._uom;

      wetHead = new EnglishWetNode(qty % )









/*
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

                  EnglishWetNode secondaryCandidates = new EnglishWetNode(qty % 768, TEASPOON)


//                  rawConvertedQuantity = rawConvertedQuantity % (3*2*8*2*2*4);    // should be a number no greater than 768



//                  if(rawConvertedQuantity / 4 == )    // TODO:  Set this up so it selects the correct unit of measure, such that it doesn't give 600 teaspoons nor .003 gallons!
              }
          }

      }
*
/

      return output;
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
       * /
      public String getWarning()
      {
          return (_conversionWarning)? _warningMessage : "";
      }
  }

  /**
   * A singly-linked list which provides all the equivalent measurements of a given quantity in terms of successively larger units of measure.
   * /
  class EnglishWetNode
  {
      public EnglishWetNode _next;
      public UnitOfMeasure _uom;
      public double _qty;

      enum UOM
      {
          TSP, TBSP, FLOZ, C, PT, QT, G;
      }

      public EnglishWetNode(double qty, UnitOfMeasure uom)
      {

          _qty = qty;
          _uom = uom;

          switch(uom)
          {
              case TEASPOON:
              {
                  _next = new EnglishWetNode(qty / 3, TABLESPOON);
                  break;
              }

              case TABLESPOON:
              {
                  _next = new EnglishWetNode(qty/2, FLUID_OUNCE);
                  break;
              }

              case FLUID_OUNCE:
              {
                  _next = new EnglishWetNode(qty/8, CUP);
                  break;
              }

              case CUP:
              {
                  _next = new EnglishWetNode(qty/2, PINT);
                  break;
              }

              case PINT:
              {
                  _next = new EnglishWetNode(qty/2, QUART);
                  break;
              }

              case QUART:
              {
                  _next = new EnglishWetNode(qty/4, GALLON);
              }

              default:
              {
                  _next = null;
              }


          }
      }
  }
*/

    /**
     * a debugging method
     */
    public void printAllInfo()
    {
        System.out.println(_compType.toString() + " >> " + _name + ", " + _quantity + " " + _unitOfMeasure + ", " + _additionalText);

        if(_next != null)
        {
            _next.printAllInfo();
        }

        return;
    }


}

