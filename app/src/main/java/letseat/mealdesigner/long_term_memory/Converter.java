package letseat.mealdesigner.long_term_memory;

import android.util.Log;

/**
 * Created by George_Sr on 11/16/2016.
 */

public class Converter
{
    enum Unit_of_Measure
    {
        // English Dry
        POUND, OUNCE,

        // English Wet
        TEASPOON, TABLESPOON, FLUID_OUNCE, CUP, PINT, QUART, GALLON,

        // Metric dry:
        GRAM, KILOGRAM,

        // Metric Wet:
        MILLILITER, LITER;
    }

    private static final double ONE_EIGHTH = 0.125, ONE_QUARTER = 0.25, ONE_THIRD = 0.333333333, THREE_EIGHTHS = 0.375, ONE_HALF = 0.5, FIVE_EIGHTHS = 0.625, TWO_THIRDS = 0.666666666, THREE_QUARTERS = 0.75;

    public String convert(double given_quantity, Unit_of_Measure given_unit_of_measure)
    {
        UoMNode lowest = findLowestQuantityGreaterThanOrEqualToOne( getNewList(given_quantity, given_unit_of_measure) );
        if(lowest._unitOfMeasure == Unit_of_Measure.MILLILITER || lowest._unitOfMeasure == Unit_of_Measure.LITER)
        {
            return trimDouble(lowest._quantity) + " " + lowest._unitOfMeasure;
        }
        return approximateDouble(lowest._quantity) + " " + lowest._unitOfMeasure;
    }

    public String modifyQuantities(double multiplier, double recipe_quantity, Unit_of_Measure recipe_unit_of_measure)
    {
        if(multiplier <= 0.0)
        {
            multiplier = 1.0;
        }

        return convert((recipe_quantity * multiplier), recipe_unit_of_measure);
    }

    public UoMNode getNewList(double quantity, Unit_of_Measure unit_of_measure)
    {

        return new UoMNode(quantity, unit_of_measure,null,null);
    }

    private String trimDouble(double input)
    {
        String output = ""+ input;

        return output.substring(0, output.indexOf('.') + 3);
    }

    private String approximateDouble(double input)
    {
        String output = "" + (int) input;

        double decOnly = input - Math.floor(input);

        if( decOnly == 0.0)
        {
            return output;
        }

        if((int) input == 0)
        {
            output = "";
        }

        boolean inLessThanOne = (int) input == 0;

        if( decOnly > 0 && decOnly <= ONE_EIGHTH)
        {
            return inLessThanOne? "1/8" : output + "-1/8";
        }

        if( decOnly > ONE_EIGHTH && decOnly <= ONE_QUARTER )
        {

            return inLessThanOne? "1/4" : output + "-1/4";
        }

        if( decOnly > ONE_QUARTER && decOnly <= ONE_THIRD )
        {
            return inLessThanOne? "1/3" : output + "-1/3";
        }

        if( decOnly > ONE_THIRD && decOnly <= THREE_EIGHTHS )
        {
            return inLessThanOne? "3/8" : output + "-3/8";
        }

        if( decOnly > THREE_EIGHTHS && decOnly <= ONE_HALF )
        {
            return inLessThanOne? "1/2" : output + "-1/2";
        }

        if( decOnly > ONE_HALF && decOnly <= FIVE_EIGHTHS )
        {
            return inLessThanOne? "5/8" : output + "-5/8";
        }

        if( decOnly > FIVE_EIGHTHS && decOnly <= TWO_THIRDS )
        {
            return inLessThanOne? "2/3" : output + "-2/3";
        }

        if( decOnly > TWO_THIRDS && decOnly <= THREE_QUARTERS )
        {
            return inLessThanOne? "3/4" : output + "-3/4";
        }

        return ""+((int) input + 1);

    }

    public void printListInfo(UoMNode randomNodeInList)
    {
        UoMNode current = randomNodeInList;

        while(current._previous != null)
        {
            current = current._previous;
        }

        current.printAll();
    }

    public UoMNode findLowestQuantityGreaterThanOrEqualToOne(UoMNode randomNodeInList)
    {
        UoMNode current = randomNodeInList;

        while(current._previous != null)
        {
            current = current._previous;
        }

        while(current._next != null && current._next._quantity > 1.0)
        {
            current = current._next;
        }

        return current;
    }

    class UoMNode
    {
        public UoMNode _previous, _next;
        public double _quantity;
        public Unit_of_Measure _unitOfMeasure;

        public UoMNode(double quantity, Unit_of_Measure unitOfMeasure, UoMNode previous, UoMNode next)
        {
//			_unitOfMeasure = unitOfMeasure;
            _quantity = quantity;

            _previous = previous;
            _next = next;

            switch(_unitOfMeasure)
            {

                case MILLILITER:
                {
                    setNextConditional(_quantity / 1000, Unit_of_Measure.LITER);
                    _previous = null;

                    break;
                }

                case LITER:
                {
                    setPreviousConditional(_quantity * 1000, Unit_of_Measure.MILLILITER);
                    _next = null;

                    break;
                }

                case POUND:
                {
                    setPreviousConditional(_quantity * 16, Unit_of_Measure.OUNCE);
                    _next = null;

                    break;
                }

                case OUNCE:
                {
                    setNextConditional(_quantity / 16, Unit_of_Measure.POUND);
                    _previous = null;

                    break;
                }

                case GRAM:
                {
                    setNextConditional(_quantity / 1000, Unit_of_Measure.KILOGRAM);
                    _previous = null;

                    break;
                }

                case KILOGRAM:
                {
                    setPreviousConditional(_quantity * 1000, Unit_of_Measure.GRAM);
                    _next = null;

                    break;
                }

                case TEASPOON:
                {
                    setNextConditional(_quantity / 3, Unit_of_Measure.TABLESPOON);
                    _previous = null;	// a special case since there is no quantized uom lower than teaspoon

                    break;
                }
                case TABLESPOON:
                {
                    setNextConditional(_quantity / 2, Unit_of_Measure.FLUID_OUNCE);
                    setPreviousConditional(_quantity * 3, Unit_of_Measure.TEASPOON);
                    break;
                }

                case FLUID_OUNCE:
                {
                    setNextConditional(_quantity / 8, Unit_of_Measure.CUP);
                    setPreviousConditional(_quantity * 2, Unit_of_Measure.TABLESPOON);
                    break;
                }

                case CUP:
                {
                    setNextConditional(_quantity / 2, Unit_of_Measure.PINT);
                    setPreviousConditional(_quantity * 8, Unit_of_Measure.FLUID_OUNCE);
                    break;
                }

                case PINT:
                {
//					System.out.println(_quantity + " pints here!");
                    setNextConditional(_quantity / 2, Unit_of_Measure.QUART);
                    setPreviousConditional(_quantity * 2, Unit_of_Measure.CUP);
                    break;
                }

                case QUART:
                {
                    setNextConditional(_quantity / 4, Unit_of_Measure.GALLON);
                    setPreviousConditional(_quantity * 2, Unit_of_Measure.PINT);
                    break;
                }

                case GALLON:
                {
                    _next = null;
                    setPreviousConditional(_quantity * 4, Unit_of_Measure.QUART);
                    break;
                }
            }
        }

        private void setNextConditional(double qty, Unit_of_Measure uom)
        {
//			System.out.println("Next node is "+ ((_next == null)? "null." : "not null.") );
            if(_next == null)
            {
                _next = getUoMNode(qty,uom,this,null);
//				_next._previous = this;
//				return;
            }
//			return (_next == null)? getUoMNode(qty,uom) : _next;
        }
        private void setPreviousConditional(double qty, Unit_of_Measure uom)
        {
//			System.out.println("Previous node is "+ ((_previous == null)? "null." : "not null.") );
            if(_previous == null)
            {
                _previous = getUoMNode(qty,uom,null,this);
//				_previous._next = this;
//				return;
            }


//			return (_next == null)? getUoMNode(qty,uom) : _next;
        }
        private UoMNode getUoMNode(double qty, Unit_of_Measure uom, UoMNode prev, UoMNode next)
        {
            return new UoMNode(qty,uom,prev,next);
        }

        public void printAll()
        {
//			System.out.println(_quantity + " " + _unitOfMeasure.toString());
//            System.out.print(_quantity + " " + _unitOfMeasure.toString() + ", ");

            Log.d("status", _quantity + " " + _unitOfMeasure.toString() + ", ");
            if(_next == null)
            {
                return;
            }

            _next.printAll();
        }
    }
}