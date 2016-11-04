package letseat.mealdesigner.long_term_memory;

import android.util.Log;

import java.util.ArrayList;

//import java.util.ArrayList;
//import java.util.List;

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
        NO_UOM, UOM_ERR,

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
    //    private ArrayList<String> _comments;	// TODO:  Try replacing this with a single string.  Giving the user too much leeway to input information might cause confusion and high runtime down the line.  Even may be possible to eliminate this field altogether!
    private UnitOfMeasure _unitOfMeasure;

    private ListComponent _next, _previous;


    /**
     * Equipment constructor.  Calling this constructor automatically selects ComponentType.EQUIPMENT for the object thus created.
     */
    public ListComponent(String name, int quantity/*, ArrayList<String> comments*/,String text2)
    {
        _compType = ComponentType.EQUIPMENT;

        commonComponentConstructorCode(name,(double) quantity, text2, UnitOfMeasure.EACH);
//        commonComponentConstructorCode(name, quantity, "", comments, UnitOfMeasure.NO_UOM);
    }
    /**
     * Ingredient constructor.  Use _additionalText as preparation procedure! (e.g.:  If the onions need to be diced, _additionalText could be assigned to the string "Diced")   Calling this constructor automatically selects ComponentType.INGREDIENT for the object thus created.
     */
    public ListComponent(String name, double quantity, String preparation_procedure, /*ArrayList<String> comments,*/ UnitOfMeasure unit_of_measure)
    {
        _compType = ComponentType.INGREDIENT;

        commonComponentConstructorCode(name, quantity, preparation_procedure, unit_of_measure);
//        commonComponentConstructorCode(name, quantity, preparation_procedure, comments, unit_of_measure);

    }

    /**
     * Procedure constructor, with timer.  Calling this constructor automatically selects ComponentType.PROCEDURE_WITH_TIMER for the object thus created.
     */
    public ListComponent(String name, double timer_in_seconds, String hazards_and_critical_control_points/*, ArrayList<String> comments*/)
    {
        _compType = ComponentType.PROCEDURE_WITH_TIMER;

        commonComponentConstructorCode(name, timer_in_seconds, hazards_and_critical_control_points, UnitOfMeasure.NO_UOM);
//        commonComponentConstructorCode(name, timer_in_seconds, hazards_and_critical_control_points, comments, UnitOfMeasure.NO_UOM);
    }

    /**
     * Procedure constructor, without timer.  Calling this constructor automatically selects ComponentType.PROCEDURE_WITHOUT_TIMER for the object thus created.
     */
    public ListComponent(String name, String hazards_and_critical_control_points/*, ArrayList<String> comments*/)
    {
        _compType = ComponentType.PROCEDURE;

        commonComponentConstructorCode(name, -1.0, hazards_and_critical_control_points, UnitOfMeasure.NO_UOM);
//        commonComponentConstructorCode(name, -1.0, hazards_and_critical_control_points, comments, UnitOfMeasure.NO_UOM);
    }

    /**
     * Comment Constructor: ( Calling this constructor automatically selects ComponentType.EQUIPMENT for the object thus created)
     * The ListComponent with _compType == COMMENT is different from the ArrayList of comments that are added within each other instantiation of this class.
     * This is because those comments pertain to an individual step, ingredient, or piece of machinery.
     * This constructor creates a ListComponent object which contains information which pertains to the overall recipe,
     * right down to the user who may wish to include a story about the recipe which is unrelated to any actual information about cooking.
     */
    public ListComponent(String name)
    {
        _compType = ComponentType.COMMENT;

        commonComponentConstructorCode(name, -1.0, "", UnitOfMeasure.NO_UOM);
//        commonComponentConstructorCode(name, -1.0, "", null, UnitOfMeasure.NO_UOM);
    }

    private void commonComponentConstructorCode(String name, double qty, String additionalText,/* ArrayList<String> comments,*/ UnitOfMeasure uom)
    {
        _name = name;

        _quantity = qty;

//        _comments = comments;

        _unitOfMeasure = uom;

        _additionalText = additionalText;

        _next = _previous = null;
    }

    /**
     * Appends the ListComponent object to the end of the list
     * @param nodeNew   The ListComponent which is to be appended to the end of the list
     */
    public void addComponentToEnd(ListComponent nodeNew)
    {
        if(_next != null)
        {
            _next.addComponentToEnd(nodeNew);
        }
        else
        {
            _next = nodeNew;
            nodeNew.setPrevious(this);
        }
    }

    /**
     * Inserts a new node directly into the list; specifically, immediately after the node which is having this function called.
     *
     * @param nodeNew
     * @return true if the insertion was executed fully; false if, for any reason, the insertion could not be completed (typically due to null argument or mismatch on "Component.Types >..."
     */
    public boolean addNewNextComponent(ListComponent nodeNew) {
        if (nodeNew == null || nodeNew.componentType() != componentType())
        {
            return false;
        }

        if(_next == null)
        {
            _next = nodeNew;
            nodeNew.setPrevious(this);

            return true;
        }

        _next.setPrevious(nodeNew);
        nodeNew.setPrevious(this);
        nodeNew.setNext(_next);
        _next = nodeNew;

        return true;
    }

// Deprecated
//    public void addComponent(ListComponent nodeNew, int order)
//    {
//        if(_next == null)
//        {
//            _next = nodeNew;
//            nodeNew.setPrevious(this);
//            return;
//        }
//        if(_next.order() >= order)
//        {
//            _next.setOrderCascade(_next.order());   // increases the order ranking of each subsequent node starting at _next
//
//            _next.setPrevious(nodeNew);
//            nodeNew.setNext(_next);
//            nodeNew.setPrevious(this);
//            _next = nodeNew;
//
//            return;
//        }
//
//        // in the case of _next.order() < order
//
//        _next.addComponent(nodeNew,order);
//
////        if(_order < order)
////        {
////            if(_next != null)
////            {
////                _next.addComponent(nodeNew,order);
////            }
////            else
////            {
////                _next = nodeNew;
////                nodeNew.setPrevious(this);
////            }
////        }
////        else    // in the case of _order >= order
//    }

    public boolean hasNext()
    {
        return _next != null;
    }

    public ListComponent next()
    {
        return _next;
    }

    /**
     * @param next a new node in to be appended to the end of whatever list for which this component is used.
     * @return the argument as it was passed so that the caller's "_last" variable can keep up-to-date without any extra runtime, (since AAPCS dictates that r1 be used for both arguments and return variables, the value of the variable doesn't require any additional machine language!)
     */
    public ListComponent setNext(final ListComponent next)
    {
        return _next = next;
    }

    public ListComponent previous()
    {
        return _previous;
    }

    /**
     * @param previous The previous node before this.
     * @return The argument which was passed into this function
     */
    public ListComponent setPrevious(final ListComponent previous)
    {
        return _previous = previous;
    }



    public ComponentType componentType()
    {
        return _compType;
    }


    /**
     * This should be used sparingly, as changing the name of a component of a dish may cause confusion.
     * @param name the new name for this component.
     */
    public void setName(String name)
    {
        _name = name;
    }

    public String name()
    {
        return _name;
    }

    /**
     * If a component requires some additional information, it may be entered here.  For instance, if the component is and ingredient, onions, which need to be diced, this field may be set to "Diced."
     * @param additionalText More information about this component.
     */
    public void setAdditionalText(String additionalText)
    {
        _additionalText = additionalText;
    }

    /**
     * If this component has any particular instructions, this function will be used to access that information.
     * @return A String containing additional information concerning this component.
     */
    public String additionalText()
    {
        return _additionalText;
    }

    /**
     * The amount per unit of measurement (which must be accessed in conjunction with this recipe for ease of use)
     * @return A double which is the amount per unit of measurement that is required by this component for a recipe.
     */
    public double getQuantity()
    {
        return _quantity;
    }

    /**
     * Some component types do not require use of the _quantity field.  This function will help to clarify when that is the case.
     * @return True if _quantity field is greater than, or equal to, zero
     */
    public boolean hasQuantity()
    {
        return _quantity >= 0.0;
    }

//    public double getQuantity(double multiplier)
//    {
//        return multiplier*_quantity;
//    }

//    public ArrayList<String> getAllComments()
//    {
//        return _comments;
//    }
//
//    public int commentSize()
//    {
//    	return _comments.size();
//    }
//
//    /**
//     * Because spinners in the App will need to be populated by String arrays, this function provides a carbon-copy of the ArrayList _comments in a bare-bones String[] array.  For ease of use, please use ListComponent.commentSize() to obtain the current size of the _comments object.
//     * @return A String[] array which contains all information within _comments for this component.
//     */
//    public String[] getAllCommentsInAnArray()
//    {
//    	int size = _comments.size();
//
//    	String[] output = new String[size];
//
//    	for(int i = 0; i < size; i++)
//    	{
//    		output[i] = _comments.get(i);
//    	}
//
//
//    	return output;
//    }
//
//    /**
//     * if the specific index of a comment is known, it may be used to access its location in _comments.
//     * @param i
//     * @return If the arg is valid, it returns the comment; if the arg is not valid (i.e.:  Not a valid index for _comments) then a generic error message is returned:  "Indexing error while attempting to retrieve comment from given position."
//     */
//    public String getSpecificComment(int i)
//    {
//
//        return (_comments.size() > i)? _comments.get(i) : "Indexing error while attempting to retrieve comment from given position.";
//    }
//
//    public void addComment(String commentIn)
//    {
//        _comments.add(commentIn);
//    }
//
//    public void deleteComment(int i)
//    {
//    	if(i >= _comments.size())
//    	{
//    		Log.d("status","Cannot delete comment within "+ _name +" component, because index at " + i + " is an invalid index.");
//    		return;
//    	}
//        _comments.remove(i);
//    }
//
//    /**
//     * removes all occurrences of the given string from the comments, irrespective of capitalization
//     * @param toDelete the string to be removed
//     */
//    public void deleteComment(String toDelete)
//    {
//        for(int i = 0; i < _comments.size(); i++)
//        {
//            if(_comments.get(i).compareToIgnoreCase(toDelete) == 0)
//            {
//                _comments.remove(i);
//            }
//        }
//    }

    public UnitOfMeasure unitOfMeasure()
    {
        return _unitOfMeasure;
    }

// some more deprecated functions
//    public int order()
//    {
//        return _order;
//    }
//
//    /**
//     *
//     * @param order the desired order for this
//     * @return
//     */
//    public void setOrder(int order)
//    {
//        _order = order;
//    }
//
//    /**
//     * Recursively calls all subsequent members of the particular list, such that inserting a new node before the initial operator had this function called.
//     * @param order The new order of this node; all nodes connected through _next will also be updated.  When dealing with an ordered list (where the zeroth element is the head) this is also the index within the list at which the new element is to be inserted.
//     */
//    public void setOrderCascade(int order)
//    {
//        if(_next != null)
//        {
//            _next.setOrderCascade( order + 1);
//        }
//
//        _order = order;
//    }


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

    public ArrayList<String> getAllInfo()
    {
        ArrayList<String> output = new ArrayList<String>();

        output.add(_compType.toString());
        output.add(_name);
        output.add(""+_quantity);
        output.add(_unitOfMeasure.toString());
        output.add(_additionalText);

        return output;
    }

    public String getInfoForWrite()
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


        switch(_compType)
        {
            case EQUIPMENT:
            {
                return _COMPONENT + _name + _COMMA + _quantity + _COMMA + _additionalText + _end_COMPONENT;

            }
            case INGREDIENT:
            {
                return _COMPONENT + _name + _COMMA + _quantity + _COMMA + _unitOfMeasure + _COMMA + _additionalText + _end_COMPONENT;
            }
            case PROCEDURE:
            {
                return _COMPONENT + _name + _COMMA + "" + _COMMA + _additionalText + _end_COMPONENT;
            }
            case PROCEDURE_WITH_TIMER:
            {
                return _COMPONENT + _name + _COMMA + _quantity + _COMMA + _additionalText + _end_COMPONENT;
            }
            case COMMENT:
            {
                return _COMPONENT + _name + _end_COMPONENT;
            }
            default:
            {
                return "";
            }

        }

//        switch(_compType)
//        {
//            case EQUIPMENT:
//            {
//                output += _EQUIPMENT;
//
//                output += _name;
//                if(_quantity >= 0)
//                {
//                    output.add(_quantity+"");
//                    output.add(_unitOfMeasure.toString());
//                }
//                else
//                {
//                    output.add("");
//                    output.add("");
//                }
//                output.add(_additionalText);
//
////    			Log.d("status",_name+", " + ((_quantity > 0.0)? _quantity+" "+_unitOfMeasure : "") + ", " + _additionalText);
//                return output;
//            }
//            case INGREDIENT:
//            {
//                output.add(_name);
//                output.add(_quantity + "");
//                output.add(_unitOfMeasure.toString());
//                output.add(_additionalText);
//
//                return output;
//
////    			Log.d("status",_name + ", " + _quantity + " " + _unitOfMeasure.toString() + ", " + _additionalText);
////    			break;
//            }
//            case PROCEDURE_WITH_TIMER:
//            {
//                output.add(_name);
//                output.add(_quantity+"");
//                output.add(_unitOfMeasure.toString());
//                output.add(_additionalText);
//
//                return output;
//
////    			Log.d("status",_name + " for " + _quantity + " seconds.  "+_additionalText);
////    			break;
//            }
//            case PROCEDURE:
//            {
//                output.add(_name);
//                output.add(_additionalText);
//
//                return output;
//
////    			Log.d("status",_name + ", " + _additionalText);
////    			break;
//            }
//            case COMMENT:
//            {
//                output.add(_name);
//
//                return output;
//
////    			Log.d("status",_name);
////    			break;
//            }
//            default:
//            {
//                return output;
//            }
//        }

    }


    public ArrayList<String> getRelevantInfo()
    {
        ArrayList<String> output = new ArrayList<String>();

        switch(_compType)
        {
            case EQUIPMENT:
            {
                output.add(_name);
                if(_quantity >= 0)
                {
                    output.add(_quantity+"");
                    output.add(_unitOfMeasure.toString());
                }
                else
                {
                    output.add("");
                    output.add("");
                }
                output.add(_additionalText);

//    			Log.d("status",_name+", " + ((_quantity > 0.0)? _quantity+" "+_unitOfMeasure : "") + ", " + _additionalText);
                return output;
            }
            case INGREDIENT:
            {
                output.add(_name);
                output.add(_quantity + "");
                output.add(_unitOfMeasure.toString());
                output.add(_additionalText);

                return output;

//    			Log.d("status",_name + ", " + _quantity + " " + _unitOfMeasure.toString() + ", " + _additionalText);
//    			break;
            }
            case PROCEDURE_WITH_TIMER:
            {
                output.add(_name);
                output.add(_quantity+"");
                output.add(_unitOfMeasure.toString());
                output.add(_additionalText);

                return output;

//    			Log.d("status",_name + " for " + _quantity + " seconds.  "+_additionalText);
//    			break;
            }
            case PROCEDURE:
            {
                output.add(_name);
                output.add(_additionalText);

                return output;

//    			Log.d("status",_name + ", " + _additionalText);
//    			break;
            }
            case COMMENT:
            {
                output.add(_name);

                return output;

//    			Log.d("status",_name);
//    			break;
            }
            default:
            {
                return output;
            }
        }

    }

    public void printRelevantInfo()
    {
        switch(_compType)
        {
            case EQUIPMENT:
            {
                Log.d("status",_name+", " + ((_quantity > 0.0)? _quantity+" "+_unitOfMeasure : "") + ", " + _additionalText);
                break;
            }
            case INGREDIENT:
            {
                Log.d("status",_name + ", " + _quantity + " " + _unitOfMeasure.toString() + ", " + _additionalText);
                break;
            }
            case PROCEDURE_WITH_TIMER:
            {
                Log.d("status",_name + " for " + _quantity + " seconds.  "+_additionalText);
                break;
            }
            case PROCEDURE:
            {
                Log.d("status",_name + ", " + _additionalText);
                break;
            }
            case COMMENT:
            {
                Log.d("status",_name);
                break;
            }
        }

        if(_next != null)
        {
            _next.printRelevantInfo();
        }
    }

    /**
     * a debugging method
     */
    public void printAllInfo()
    {
        Log.d("status",_compType.toString() + " >> " + _name + ", " + _quantity + " " + _unitOfMeasure + ", " + _additionalText);

        if(_next != null)
        {
            _next.printAllInfo();
        }

        return;
    }


}