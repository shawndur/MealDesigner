package letseat.mealdesigner.long_term_memory;

import android.util.Log;

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

        return (indexFileContents.get(0).length() == 0)? true : indexFileContents.get(0).charAt(0) != 0x0FF && indexFileContents.get(0).charAt(0) != 0x0FE;

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

    public boolean initialize(boolean betaTest) {
        if (checkIfIndexFileExists()) {
            Log.d("status", "index file exists");
            return true;
        }
        Log.d("status", "index file does not exists, creating it");

//
//          // if we want to hardcode a way to keep people from using the price-reduced beta release after a certain date, here is how we can cause the app to crash fairly easily.
//        if(Integer.parseInt(_lti.getDAT().substring(6,9)) >= 2017)
//        {
//            throw new NullPointerException();
//        }
//

        // program only proceeds to next segment if no index file was located.  In other words, this is the first time the app has been launched on this device.

        ArrayList<String> indexFileKernel = new ArrayList<String>();

        //char indexFileDelim = _lti.getIndexFileDelimiter();

        //indexFileKernel.add("Chicken Stock"+indexFileDelim+"aaa");
        //indexFileKernel.add("Meatloaf"+indexFileDelim+"aab");
        //indexFileKernel.add("Lots of bacon at once"+indexFileDelim+"aac");
//        indexFileKernel.add("Knife skills:  Dice"+indexFileDelim+"aad");
//        indexFileKernel.add("Knife skills:  Julienne"+indexFileDelim+"aae");
//        indexFileKernel.add("Sauces: Bechamel (white) Sauce" + indexFileDelim + "aaf");
//        indexFileKernel.add("Sauces: Veloute (gravy)" + indexFileDelim + "aag");
//        indexFileKernel.add("Sauces: Hollandaise" + indexFileDelim + "aah");
//        indexFileKernel.add("Sauces: Red (tomato) Sauce" + indexFileDelim + "aai");
//        indexFileKernel.add("Thickener: Roux"+ indexFileDelim + "aaj");
//        indexFileKernel.add("Thickener: Slurry"+ indexFileDelim + "aak");
//        indexFileKernel.add("Sauces: Demi-glace" + indexFileDelim + "aal");
        //indexFileKernel.add("Mirepoix"+indexFileDelim+"aam");
        //Log.d("status",indexFileKernel.toString());
        if (!_lti.writeToFile("IndexFile", indexFileKernel)) {
            Log.d("Status", "Failed to create Index File kernel in long-term memory");
            return false;
        }

        if (!_lti.writeToFile("Favorites", new ArrayList<String>())) {
            Log.d("Status", "Failed to create \"Favorites\" file in long-term memory.  Non-critical error, but do investigate.");
        }



        if (!_lti.writeToFile("ShopList", new ArrayList<String>()))
        {
            Log.d("Status", "Failed to create \"Shopping List\" file in long-term memory.  Non-critical error, but do investigate.");
        }

        //_lti.writeRecipeToFile("Chicken Stock",_lti.convertRecipeToWriteable(chickenStock()));
        //_lti.writeRecipeToFile("Lots of bacon at once",_lti.convertRecipeToWriteable(lotsOfBacon()));
        //_lti.writeRecipeToFile("Mirepoix",_lti.convertRecipeToWriteable(mirepoix()));
        _lti.setRecipe(chickenStock());
        _lti.setRecipe(lotsOfBacon());
        _lti.setRecipe(meatloaf());
        _lti.setRecipe(mirepoix());
        _lti.setRecipe(saucesBechamel());
        _lti.setRecipe(saucesVeloute());
        _lti.setRecipe(saucesHollandaise());
        _lti.setRecipe(italianMeatballs());

        ArrayList<String> favKernel = new ArrayList<String>();

        favKernel.add("Lots of bacon at once" + "" + _lti.getIndexFileDelimiter() + "" + _lti.getFilename("Lots of bacon at once") );

        return true;
    }

    private RecipeHead saucesHollandaise()
    {
        RecipeHead hol = new RecipeHead("Sauces:  Hollandaise");

        hol.addEquipment("High-walled sauce pot with 1-inch of water in it.",1, "");
        hol.addEquipment("Glass or Stainless Steel Bowl", 1, "At least big enough such that it seats in the sauce pot without touching the water");
        hol.addEquipment("Heat-resistant pad or a dry, clean, dish towel", 1, "To be used to hold the bowl steady while it is hot.");
        hol.addEquipment("Wire whisk", 1, "");
        hol.addEquipment("1-ounce ladle", 1, "");

        hol.addIngredient("Egg yolks", 3, ListComponent.UnitOfMeasure.EACH, "Some egg white is okay, but too much will only make the job take longer.");
        hol.addIngredient("Butter", 1, ListComponent.UnitOfMeasure.CUP, "Melted and clarified (all solids skimmed from the top)");
        hol.addIngredient("Lemon juice", 2, ListComponent.UnitOfMeasure.EACH, "Must be freshly-squeezed.  Store-bought does not have the correct acidity.");
        hol.addIngredient("Hot sauce", 1, ListComponent.UnitOfMeasure.TEASPOON, "");
        hol.addIngredient("Worcestershire sauce", 1, ListComponent.UnitOfMeasure.TEASPOON,"");


        hol.addProcedureWithoutTimer("Bring water to just barely boiling, then place bowl inside top of the pot.","If the water touches the bottom of the bowl, pour some out until the bowl is well-above the water.");
        hol.addProcedureWithoutTimer("Add egg yolks to the bowl and whisk vigorously until foamy.","");
        hol.addProcedureWithoutTimer("Add hot sauce and Worcestershire sauce, continue whisking","");
        hol.addProcedureWithoutTimer("Add half the lemon juice and continue whisking until the mixture thickens slightly.", "");
        hol.addProcedureWithoutTimer("Pour one ladle-full of the clarified butter, one-at-a-time, SLOWLY into the bowl while whisking constantly","Pouring down the side will help greatly.");
        hol.addProcedureWithoutTimer("When the mixture starts to thicken noticeably, add the second half of the lemon juice.","");
        hol.addProcedureWithoutTimer("Continue adding ladles of the clarified butter, one-at-a-time, to the bowl, whisking constantly.","As the amount of butter you have added increases, it may be necessary to increase the heat under the pot.");
        hol.addProcedureWithoutTimer("When the sauce takes three seconds to settle after passing a spoon through, the Hollandaise is done.","Serve within thirty minutes.");

        hol.addComment("This sauce has a relatively short shelf life of 30 minutes.  It will separate eventually, so make this recipe towards the end of whatever you're cooking. (the eggs which bind the sauce together are cooked, so they lose some ability to hold the sauce together.");
        hol.addComment("If the sauce should separate at any time during cooking, remove from heat, add a tablespoon of mayonnaise, and whisk vigorously until the sauce comes back together.");

        return hol;
    }

    private RecipeHead saucesVeloute()
    {
        RecipeHead vel = new RecipeHead("Sauces:  Veloute");

        vel.addEquipment("Sauce pot", 1, "At least 2 quarts");
        vel.addEquipment("Wire whisk", 1, "If using a non-stick pot, use rubber-coated wire whisk.");

        vel.addIngredient("Fat", 1, ListComponent.UnitOfMeasure.OUNCE, "Can use butter, or fat from whatever meat is used to provide the cooking liquid.");
        vel.addIngredient("Flour", 1, ListComponent.UnitOfMeasure.OUNCE,"");
        vel.addIngredient("Stock or Broth", 1, ListComponent.UnitOfMeasure.PINT, "Most poultry will make a good veloute, as well as certain other meats like pork.  For deeper flavor, use the drippings from roasted meats.");

        vel.addProcedureWithoutTimer("Melt fat in pan over low heat.","");
        vel.addProcedureWithoutTimer("Add flour.","");
        vel.addProcedureWithoutTimer("Whisk until the Fat-Flour mix looks a bit like wet sand, adding a small amount of either Fat or Flour to achieve this texture.","If using pan drippings or stock made from roasted bones, allow the roux to darken slightly. (about 7 minutes at heat)");
        vel.addProcedureWithTimer("Remove from heat and let cool for a minute.",60,"");
        vel.addProcedureWithoutTimer("Turn heat to medium and then pour cold stock, broth, or drippings into the still-hot roux.","");
        vel.addProcedureWithoutTimer("Whisk vigorously to dissolve the Butter-coated Flour granules.","Take care to not allow the bottom of the pan to scorch:  Always stir to the bottom of the pot!");
        vel.addProcedureWithoutTimer("Continue whisking until desired thickness is achieved.","Roux-thickened sauces will continue to thicken as they remain over heat.");
        vel.addProcedureWithoutTimer("Salt and Pepper to taste","");

        vel.addComment("This is a common variant of the French mother sauce, Bechamel.");
        vel.addComment("This recipe can be used to produce a myriad of gravies and pan-sauces.");
        vel.addComment("If using this to make gravy, add some thyme and flat-leaf parsely, simmer gently for 20 minutes, then pass through a fine-mesh strainer.");

        return vel;
    }

    private RecipeHead saucesBechamel()
    {
        RecipeHead bech = new RecipeHead("Sauces:  Bechamel");

        bech.addEquipment("Sauce pot", 1, "At least 2 quarts");
        bech.addEquipment("Wire whisk", 1, "If using a non-stick pot, use rubber-coated wire whisk.");

        bech.addIngredient("Butter", 1, ListComponent.UnitOfMeasure.OUNCE, "");
        bech.addIngredient("Flour", 1, ListComponent.UnitOfMeasure.OUNCE,"");
        bech.addIngredient("Whole Milk", 1, ListComponent.UnitOfMeasure.PINT, "2% Milk will do fine, but any lower percentage will affect the creaminess of the finished product.");

        bech.addProcedureWithoutTimer("Melt butter in pan over low heat.","");
        bech.addProcedureWithoutTimer("Add flour.","");
        bech.addProcedureWithoutTimer("Whisk until the Butter-Flour mix looks a bit like wet sand, adding a small amount of either Butter or Flour to achieve this texture.","Do not keep over heat for more than 5 minutes, otherwise the roux begins to darken.  If this happens you will need to start over.");
        bech.addProcedureWithTimer("Remove from heat and let cool for a minute.",60,"");
        bech.addProcedureWithoutTimer("Turn heat to medium and then pour cold milk into the still-hot roux.","");
        bech.addProcedureWithoutTimer("Whisk vigorously to dissolve the Butter-coated Flour granules.","Take care to not allow the bottom of the pan to scorch.");
        bech.addProcedureWithoutTimer("Continue whisking until desired thickness is achieved.","Roux-thickened sauces will continue to thicken as they remain over heat.");

        bech.addComment("This is a French mother sauce, meaning it can be used to create many different variants.");
        bech.addComment("Probably the most-popular sauce that can be derived from Bechamel is cheese sauce for Macaroni and Cheese.  To do this, add fresh-grated cheddar, swiss, and gruyere cheese once the sauce warms back up after adding the milk, and whisk until the cheese melts to a smooth, creamy texture.");

        return bech;

    }

    private RecipeHead italianMeatballs()
    {
        RecipeHead mb = new RecipeHead("Italian Meatballs");

        mb.addEquipment("Oven", 1, "Heated to 380 degrees fahrenheit");
        mb.addEquipment("Large stainless steel bowl", 1, "");
        mb.addEquipment("Cookie sheet", 1, "Lined with aluminum foil");
        mb.addEquipment("Small bowl", 1, "About the size of a breakfast bowl");
        mb.addEquipment("Non-stick frying pan", 1, "");

        mb.addIngredient("Ground Beef", 1, ListComponent.UnitOfMeasure.POUND, "");
        mb.addIngredient("Ground Veal", 1, ListComponent.UnitOfMeasure.POUND, "");
        mb.addIngredient("Ground Pork", 1, ListComponent.UnitOfMeasure.POUND, "");

        mb.addIngredient("Extra-Virgin Olive Oil", 1, ListComponent.UnitOfMeasure.FLUID_OUNCE, "");
        mb.addIngredient("Diced Onions", 3, ListComponent.UnitOfMeasure.OUNCE, "Sweat in the Extra-Virgin Olive Oil over low heat until translucent.");
        mb.addIngredient("Italian-Seasoned Breadcrumbs", 4, ListComponent.UnitOfMeasure.OUNCE, "");
        mb.addIngredient("Egg", 1, ListComponent.UnitOfMeasure.EACH, "Beaten well.");
        mb.addIngredient("Whole Milk", 3, ListComponent.UnitOfMeasure.FLUID_OUNCE, "No lower than 2% Milk.");
        mb.addIngredient("Worcestershire Sauce", 1, ListComponent.UnitOfMeasure.FLUID_OUNCE, "");

        mb.addIngredient("Flat-Leaf Parsely", 1, ListComponent.UnitOfMeasure.CUP, "");
        mb.addIngredient("Oregano", 4, ListComponent.UnitOfMeasure.FLUID_OUNCE, "");
        mb.addIngredient("Parmesan Cheese", 3, ListComponent.UnitOfMeasure.FLUID_OUNCE, "");

        mb.addProcedureWithoutTimer("Mix egg, milk, and Worcestershire sauce in the smaller bowl", "");
        mb.addProcedureWithoutTimer("Combine and loosely mix the beef, pork, veal, onions, breadcrumbs, parsely, oregano, and cheese in the larger bowl.", "");
        mb.addProcedureWithoutTimer("Pour a portion of the egg-milk-Worcestershire mixture over the meat mixture, mix until evenly-distributed, and repeat until a sticky mixture forms.", "You may not use all of the egg-milk-Worcestershire mixture.  Discard after cooking.");
        mb.addProcedureWithTimer("Form a small patty (about 1-inch diameter) and cook in frying pan over medium-low heat until cooked", (5*60), "Use this patty to determine if adjustments to seasoning is necessary.");
        mb.addProcedureWithoutTimer("Add salt and pepper to the mixture and combine, based on how the taste-test went.","You may not need any salt and pepper at all.");
        mb.addProcedureWithoutTimer("Form into balls (approximately 1-inch to 2-inches in diameter and arrange evenly on cookie sheet","Leave about 1/2-inch between the balls.");
        mb.addProcedureWithoutTimer("Place meatballs in middle of oven.","");
        mb.addProcedureWithoutTimer("Pour enough water into the pan to give 1/4-inch layer of water on pan.", "This will prevent sticking.");
        mb.addProcedureWithTimer("Roast in oven for approximately 40 minutes", (40 * 60), "Meatballs will be done when they are springy, but firm.");

        return mb;

    }

    private RecipeHead meatloaf()
    {
        RecipeHead lf = new RecipeHead("Meatloaf");

        lf.addEquipment("Large stainless steel bowl.",1,"");
        lf.addEquipment("Metal bread pans.",2,"");
        lf.addEquipment("Medium bowl.",1,"");
        lf.addEquipment("Whisk",1,"");
        lf.addEquipment("Saute pan",1,"Non-stick is best, on medium-low heat.");  // what other equipment is there?

        lf.addIngredient("Ground Beef",1, ListComponent.UnitOfMeasure.POUND,"No more than 20% fat.");
        lf.addIngredient("Ground Pork",0.5, ListComponent.UnitOfMeasure.POUND,"");
        lf.addIngredient("Ground Veal",0.5, ListComponent.UnitOfMeasure.POUND,"");

        lf.addIngredient("Egg",1, ListComponent.UnitOfMeasure.EACH,"Well-beaten");
        lf.addIngredient("Worcestershire Sauce",1, ListComponent.UnitOfMeasure.TABLESPOON,"");
        lf.addIngredient("Ketchup",1, ListComponent.UnitOfMeasure.FLUID_OUNCE,"");
        lf.addIngredient("Whole Milk",0.5, ListComponent.UnitOfMeasure.CUP,"2% Milk will also work, but whole milk is best.");

        lf.addIngredient("Panko Breadcrumbs",4, ListComponent.UnitOfMeasure.OUNCE,"");
        lf.addIngredient("Parsley",1, ListComponent.UnitOfMeasure.OUNCE,"");
        lf.addIngredient("Oregano",0.5, ListComponent.UnitOfMeasure.OUNCE,"");
        lf.addIngredient("Onions",8, ListComponent.UnitOfMeasure.OUNCE,"Diced");
        lf.addIngredient("Garlic",2, ListComponent.UnitOfMeasure.OUNCE,"Diced");
        lf.addIngredient("Butter",0.5, ListComponent.UnitOfMeasure.OUNCE,"");
        lf.addIngredient("Kosher Salt",2, ListComponent.UnitOfMeasure.OUNCE,"");
        lf.addIngredient("Pepper",2, ListComponent.UnitOfMeasure.OUNCE,"");


        lf.addProcedureWithoutTimer("Sweat the onions and garlic in the saute pan with the butter","When the onions become slightly translucent, remove from heat and allow to cool off for a few minutes.");
        lf.addProcedureWithoutTimer("Combine all the meat, salt, pepper, breadcrumbs, parsely, oregano, and onion-garlic mixture in the large stainless steel bowl.  Mix until all components are thoroughly distributed.","");
        lf.addProcedureWithoutTimer("In the medium bowl, combine milk, eggs, Worcestershire Sauce, ketchup, and mix until well-distributed.","");
        lf.addProcedureWithoutTimer("Slowly pour the combined liquids into the meat mixture until the meat mixture feels wet but can still hold nearly any shape you can form of it.","Take care to combine the meat and liquids thoroughly every few seconds.");
        lf.addProcedureWithoutTimer("Pick the entire meat mixture up and drop it into the bowl five times.","(This will help remove any air bubbles!)");
        lf.addProcedureWithoutTimer("Place enough meat mixture in the loaf roasting pans to fill them 9/10 the way up.","The top of the loaf should be rounded, and the crest should be flush with the lip of the pan.");
        lf.addProcedureWithTimer("Place both pans on a cookie sheet and transfer that sheet to the oven.",(40*60),"Internal temperature should be approximately 160 degrees Fahrenheit when removed from oven, or 165 degrees Fahrenheit after a few minutes out of the oven.");
        lf.addProcedureWithTimer("If eating right away, allow the loaves to rest for five minutes before enjoying.",(60*5),"If not eating right away, then allow the loaves to cool for 40 minutes before removing from the pans and transferring to the fridge.");

        lf.addComment("Use the drippings in the loaf pans as the \"fat\" and \"stock\" components to make gravy. (gravy recipe is included with this app!)");
        lf.addComment("If you have the time, form a small patty out of the raw-meat-and-liquids combination and cook it over medium-low heat in the saute pan, to gauge the flavor.  You may need to adjust the seasoning, depending on your preferences");

        return lf;


    }

    private RecipeHead mirepoix()
    {
        RecipeHead mir = new RecipeHead("Mirepoix");

        mir.addEquipment("Chef's knife",1,"");
        mir.addEquipment("Cutting board",1,"When your knife is laid diagonally on it, there should be at least an inch between either end of the knife and the closest corners to those ends.");
        mir.addEquipment("Large bowl or tupperware container",1,"");

        mir.addIngredient("Onions",2, ListComponent.UnitOfMeasure.POUND,"Small diced (onion pieces should be approximately 1/4\" on any cut edge)");
        mir.addIngredient("Carrots",1, ListComponent.UnitOfMeasure.POUND,"Cleaned and peeled, small diced (1/4\" cubes)");
        mir.addIngredient("Celery",1, ListComponent.UnitOfMeasure.POUND,"Wash under cold water to remove all dirt, small diced (1/4\" on any cut edge)");

        mir.addProcedureWithoutTimer("Combine all ingredients.","");

        mir.addComment("Depending on the usage and your preference, you may wish to leave the carrots separate so you can cook them longer.");
        mir.addComment("Being able to produce a good mirepoix sets you up to be able to make just about any soup, sauce, or stew your heart desires.");
        mir.addComment("Try to make sure all the cuts are approximately the same size so that they receive equal heat during cooking.");
        mir.addComment("Mirepoix will stay good in your fridge for a few days.  If you are making a lot of soups, sauces, turkey stuffing, etc. over a few days, then you can make one a big batch and have it ready");

        return mir;

    }

    public RecipeHead lotsOfBacon()
    {
        RecipeHead bac = new RecipeHead("Lots of bacon at once");

        bac.addEquipment("Cookie sheet",1,"Medium-gauge or thicker is preferred.");
        bac.addEquipment("Parchment paper",1,"Sheet should cover the pan.");
        bac.addEquipment("Oven",1,"Set to 380 Fahrenheit.");

        bac.addIngredient("Bacon",8, ListComponent.UnitOfMeasure.STRIP,"");

        bac.addProcedureWithoutTimer("Arrange the bacon strips flat on the cookie sheet","They can overlap a little bit");
        bac.addProcedureWithTimer("Place the sheet in the oven.",(60*10),"");
        bac.addProcedureWithTimer("Check the bacon",(60*3),"If it is starting to look very well done, then it is done cooking; otherwise, keep it in the oven.  Be careful to not let any grease drip in the oven.");
        bac.addProcedureWithTimer("Check the bacon again.",60,"By now the baoon should be fully-cooked; if not, then continue to the next step.");
        bac.addProcedureWithoutTimer("The bacon should be done by now","If not, your oven may need some service.  Either you aren't getting enough BTUs out of it or its thermostat needs to be calibrated -- this is common with older ovens!");

        bac.addComment("This method is particularly useful when you have to make breakfast for everyone.  It saves space on your stovetop, and it gives you much more control and consistency over how you cook your bacon.");
        bac.addComment("Double-smoked bacon is widely regarded as a high-end bacon.");
        bac.addComment("Generally, the more meat which runs through the strip, the less that strip will shrink during cooking.");

        return bac;
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
        chx.addProcedureWithoutTimer("CAREFULLY remove as many bones as you can and discard them without disturbing the stock.","Do not worry about getting all the bones out, it is more important that you try to keep them from breaking apart.");
        chx.addProcedureWithoutTimer("Place the cheesecloth inside the strainer, and place the strainer in the tupperware or glass container","Make sure all children and pets are clear of the area.");
        chx.addProcedureWithoutTimer("Pour the remaining liquid into the bowl through the cheesecloth-strainer setup","If you can, use the tongs to hold the remaining bones in place.");
        chx.addProcedureWithoutTimer("Lay the clear plastic wrap gently on top of the liquid and place the container in the fridge","Leave until the stock has cooled.  It may take on a \"jiggly\" texture; the \"jigglier\" the better!");
        chx.addProcedureWithoutTimer("When the stock is fully-cooled (may take overnight) remove the plastic wrap and scrape the fat into the custard cup -sized container","");
        chx.addProcedureWithoutTimer("Use a spoon to remove any fat that has accumulated at the top of the stock","");
        chx.addProcedureWithoutTimer("Save the fat you removed when for you want to make gravy or thick soups wit this stock.","The fat can be frozen for long periods of time.");

        chx.addComment("If the stock does come out cloudy, don't worry:  There is nothing wrong with it -- it will taste the same.  Professional chefs who charge $20 per plate are really the only ones who worry about the clarity of the stock.");
        chx.addComment("Making stock is a great skill to have, there are so many recipes you can make using this stock.");
        chx.addComment("For a really awesome variation, use the bones from a turkey dinner instead of chicken bones.");
        chx.addComment("Stock will keep fresh in the freezer for a few months, but portioning it into 1 quart sizes is strongly advised.");

        return chx;


    }
}
