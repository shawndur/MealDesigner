package letseat.mealdesigner.long_term_memory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by George_Sr on 10/24/2016.
 */

public class Initializer
{

    private Long_Term_Interface _lti;

    public Initializer(Long_Term_Interface lti)
    {
        _lti = lti;
    }

    public boolean checkIfIndexFileExists()
    {

        ArrayList<String> indexFileContents = _lti.getIndexFileLines();

        if(indexFileContents.size() == 0)
        {
            // is this a problem?
            return true;
        }

        return (indexFileContents.get(0).length() == 0)? true : indexFileContents.get(0).charAt(0) != 0x0FF;

//        try
//        {
//            FileInputStream inStream = new FileInputStream("Index_File.txt");
//            return true;
//        }
//        catch(FileNotFoundException e)
//        {
//            return false;
//        }

    }

    public boolean initialize(boolean betaTest)
    {
        if(checkIfIndexFileExists())
        {
            return true;
        }

//
//          // if we want to hardcode a way to keep people from using the price-reduced beta release after a certain date, here is how we can cause the app to crash fairly easily.
//        if(Integer.parseInt(_lti.getDAT().substring(6,9)) >= 2017)
//        {
//            throw new NullPointerException();
//        }
//

        // program only proceeds to next segment if no index file was located.  In other words, this is the first time the app has been launched on this device.

        ArrayList<String> indexFileKernel  = new ArrayList<String>();

        char indexFileDelim = _lti.getIndexFileDelimiter();

        indexFileKernel.add("Chicken Stock"+indexFileDelim+"aaa");
        indexFileKernel.add("Meatloaf"+indexFileDelim+"aab");
        indexFileKernel.add("Lots of bacon at once"+indexFileDelim+"aac");
        indexFileKernel.add("Knife skills:  Dice"+indexFileDelim+"aad");
        indexFileKernel.add("Knife skills:  Julienne"+indexFileDelim+"aae");
        indexFileKernel.add("Sauces: Bechamel (white) Sauce" + indexFileDelim + "aaf");
        indexFileKernel.add("Sauces: Veloute (gravy)" + indexFileDelim + "aag");
        indexFileKernel.add("Sauces: Hollandaise" + indexFileDelim + "aah");
        indexFileKernel.add("Sauces: Red (tomato) Sauce" + indexFileDelim + "aai");
        indexFileKernel.add("Thickener: Roux"+ indexFileDelim + "aaj");
        indexFileKernel.add("Thickener: Slurry"+ indexFileDelim + "aak");
        indexFileKernel.add("Sauces: Demi-glace" + indexFileDelim + "aal");
        indexFileKernel.add("Mirepoix"+indexFileDelim+"aam");

        if(!_lti.writeToFile("IndexFile",indexFileKernel))
        {
            System.out.println("Failed to create Index File kernel in long-term memory");
            return false;
        }

        RecipeHead chickStock = chickenStock();

        _lti.writeRecipeToFile("Chicken Stock",_lti.convertRecipeToWriteable(chickStock));

        return true;
    }

    private RecipeHead chickenStock()
    {
        RecipeHead chx = new RecipeHead("Chicken Stock");

        chx.addEquipment("Large pot",1,"At least one gallon");
        chx.addEquipment("Strainer",1,"Any size mesh will suffice");
        chx.addEquipment("Cheesecloth",2,"One piece should be large enough such that you can drape it inside your strainer, the other piece should be a square 6 inches on all sides.");
        chx.addEquipment("Tongs",1,"Wide gripping surface preferred, but not necessary");
        chx.addEquipment("Tupperware container or glass bowl",2,"One container should be 5 qts at least, the other should be custard cup sized (plastic tupperware with lid is preferred)");
        chx.addEquipment("Clear plastic wrap",1,"Should be big enough to lay flat on the container you are using.");
        chx.addEquipment("Wire spider or slotted serving spoon",1,"");

        chx.addIngredient("Chicken bones",5, ListComponent.UnitOfMeasure.POUND,"Necks and backs are preferred.  NO GIBLETS!");
        chx.addIngredient("Mirepoix (the recipe for mirepoix included with this app!)",2,ListComponent.UnitOfMeasure.POUND,"Cut into large chunks");
        chx.addIngredient("Sache d'epices (the recipe for sache d'epices is included with this app!)",2.0,ListComponent.UnitOfMeasure.EACH,"You can combine the ingredients into one pouch of cheesecloth.");
//        chx.addIngredient("Sache d'epices (the recipe for a sache d'epices is included with this app!)"+1.0+ListComponent.UnitOfMeasure.EACH,"6 fresh parseley stems, 3 bay leaves, 6 sprigs of fresh thyme (with leaves), and 6 black peppercorns");
        chx.addIngredient("Kosher Salt",1,ListComponent.UnitOfMeasure.FLUID_OUNCE,"Add to taste, but no more than one fluid ounce.");

        chx.addProcedureWithoutTimer("Place the bones in the bottom of the empty pot","");
        chx.addProcedureWithoutTimer("Place the mirepoix on top of the bones","");
        chx.addProcedureWithoutTimer("Fill the pot with enough cold water to cover the top of the mirepoix","Must be cold water!");
        chx.addProcedureWithTimer("Simmer on medium-low heat for 3 hours",(3*3600),"Do not stir, but do use the spider or slotted serving spoon to skim any foamy material off the top from time-to-time.  Some bubbles should break the surface, but do not let it reach a full boil.  Ideal temperature of the liquid is between 180 and 200 degrees Fahrenheit");
        chx.addProcedureWithoutTimer("Add the sache d'epices to the liquid","Use a piece of unwaxed twine to tie the sache to the pot's handle for easy retrieval!");
        chx.addProcedureWithTimer("Continue simmering for another hour",3600,"Make sure you have a garbage bin ready for the next step!");
        chx.addProcedureWithoutTimer("CAREFULLY remove as many bones as you can without disturbing the stock.","Do not worry about getting all the bones out, it is more important that you try to keep them from breaking apart.");
        chx.addProcedureWithoutTimer("Place the cheesecloth inside the strainer, and place the strainer in the tupperware or glass container","Make sure all children and pets are clear of the area.");
        chx.addProcedureWithoutTimer("Pour the remaining liquid into the bowl through the cheesecloth-strainer setup","If you can, use the tongs to hold the remaining bones in place.");
        chx.addProcedureWithoutTimer("Lay the clear plastic wrap gently on top of the liquid and place the container in the fridge","Leave until ");
        chx.addProcedureWithoutTimer("When the stock is fully-cooled (may take overnight) remove the plastic wrap and scrape the fat into the custard cup -sized container","");
        chx.addProcedureWithoutTimer("Use a spoon to remove any fat that has accumulated at the top of the stock","");
        chx.addProcedureWithoutTimer("Save the fat you removed when for you want to make gravy or thick soups wit this stock.","The fat can be frozen for long periods of time");

        chx.addComment("If the stock does come out cloudy, don't worry:  There is nothing wrong with it -- it will taste the same.  Professional chefs who charge $20 per plate are really the only ones who worry about the clarity of the stock.");
        chx.addComment("Making stock is a great skill to have, there are so many recipes you can make using this stock.");
        chx.addComment("For a really awesome variation, use the bones from a turkey dinner instead of chicken bones.");
        chx.addComment("Stock will keep fresh in the freezer for a few months, but portioning it into 1 quart sizes is strongly advised.");

        return chx;


    }
}
