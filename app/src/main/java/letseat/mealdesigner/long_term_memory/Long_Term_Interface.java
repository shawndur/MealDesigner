package letseat.mealdesigner.long_term_memory;

import android.app.Application;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

import static letseat.mealdesigner.long_term_memory.ListComponent.UnitOfMeasure.UOM_ERR;


/**
 * This is the only class permitted (by group convention) to access the long-term memory of the device.
 * If the functionality of this class is desired, use this as an inherited class.
 * Created by George_Sr on 9/20/2016.
 */
public class Long_Term_Interface
{
    private static final char RECIPE_NAME = (char) 0x80, end_RECIPE_NAME = (char) 0x90,
            EQUIPMENT = (char) 0x81, end_EQUIPMENT = (char) 0x91,
            INGREDIENT = (char) 0x82, end_INGREDIENT = (char) 0x92,
            PROCEDURE = (char) 0x83, end_PROCEDURE = (char) 0x93,
            COMMENTS = (char) 0x84, end_COMMENTS = (char) 0x94,
            END_OF_RECIPE = (char) 0x85,
            COMPONENT = (char) 0x86, end_COMPONENT = (char) 0x96,
            COMMA = (char) 0x87, INDEX_FILE_DELIM = (char) 0x97,
            _shopList_QTY = (char) 0x88, end_shopList_QTY = (char) 0x98,
            _shopList_PRICE = (char) 0x89, end_shopList_PRICE = (char) 0x99,
            _shopList_STORE = (char) 0x8a, end_shopList_STORE = (char) 0x9a,
            _shopList_RECIPE_ADDED = (char) 0x8b, end_shopList_RECIPE_ADDED = (char) 0x9b,
            _shopList_IN_CART = (char) 0x8c, end_shopList_IN_CART = (char) 0x9c,
            _shopList_UOM = (char) 0x8d, end_shopList_UOM = (char) 0x9d;




    private static final double INDEX_FILE_TOLERANCE = 2.5;


    private Application _top;
    private File _appHomeDir;                           // the internal directory of the app
    private static final String EXTENSION = ".txt";    // this can be changed, but all files which exist with the outdated extension need to be updated
    private static final String DEFAULT = "default_filename_";  // the default filename to be used if a given filename is invalid



    public Long_Term_Interface(Application top)
    {
        _top = top;
        _appHomeDir = _top.getFilesDir();

    }


    /**
     * Retrieves all contents from the specified file in an ArrayList<String>
     *
     * In the case of an IO Exception, the output will contain, as its last member, a string with a single char whose value is 0x0FF.
     * In the case of a File Not Found Exception, the output will contain a single string, which consists of a single char whose value is 0x0FE
     *
     */
    public ArrayList<String> getLinesFromFile(String filename)
    {
        ArrayList<String> output = new ArrayList();

        FileInputStream inStream;

        try
        {
            // since the caller is not necessarily expecting the filename to have been switched by the writeToFile function, this function must check the given filename first
            // if the given filename causes a FileNotFoundException, then proceed to give the user options for retrieving from the Default Files
            inStream = _top.openFileInput(filename);

            InputStreamReader reader = new InputStreamReader(inStream);

            BufferedReader buffer = new BufferedReader(reader);

            StringBuffer strBuff = new StringBuffer();

            try
            {
                String currentLineFromFile = "";
                while((currentLineFromFile = buffer.readLine()) != null)    // no real increase in runtime or space cost, since the return value will exist in registers anyway.
                {
                    output.add(currentLineFromFile);    // this line only adds valid, non-null strings
                }
            }
            catch(IOException IOE)
            {
                char error_negative1 = 0x0FF;
                String error1_string = "" + error_negative1;
                output.add(error1_string);

                // something clever should go here
            }



        }
        catch(FileNotFoundException FNFE)
        {
            // A single-character-string is passed back
            char error_negative2 = 0x0FE;
            String error2_string = ""+error_negative2;
            output.add(error2_string);
        }

        return output;
    }

    /**
     * Fully updated as of 10/05/2016 at 12:19am
     * @return The accurate substring with all demarkers trimmed off.  If any IndexOutOfBoundsException occurs, a blank string is returned;
     *
     */
    private String specialSubstring(String input, final char beginDemarker, final char endDemarker)
    {
//		System.out.println("Generating special substring from:  "+input + ", [" + input.indexOf(beginDemarker) +","+ input.indexOf(endDemarker) +"]");


        try
        {
            return input.substring(input.indexOf(beginDemarker)+1, input.indexOf(endDemarker));
        }
        catch(IndexOutOfBoundsException e)
        {
            System.out.println("Error on " + ((input.length() <= 10)? input : input.substring(0,10) + "... ") +":\n\tCannot parse because of demarkerBegin = " + input.indexOf(beginDemarker) + " or demarkerEnd = " + input.indexOf(endDemarker));
            return "";
        }

    }

    /**
     * Takes in a string representing a unit of measure, and returns an enum (from the ListComponent class) which coresponds to that particular unit of measure.
     * @param input The String which represents a unit of measure
     * @return The corresponding enum of type "ListComponent.UnitOfMeasure", or ListComponent.UnitOfMeasure.UOM_ERR if the unit of measure cannot be parsed.
     */
    private ListComponent.UnitOfMeasure parseUnitOfMeasure(String input)
    {
        if(input.length() < 3)
        {
            return UOM_ERR; // 3 is the minimum length for any valid Unit of Measure
        }

        input = input.toLowerCase();

        char[] inChars = input.toCharArray();
//
// for ease of reference:
//        enum UnitOfMeasure
//        {
//            // in the case of no UoM
//            NO_UOM, UOM_ERR,
//
//            // non-empirical units
//            EACH, BAG, POUCH, STRIP, SLICE, // more to come!
//
//            // English dry
//            OUNCE, POUND,
//
//            // English wet
//            TEASPOON, TABLESPOON, FLUID_OUNCE, CUP, PINT, QUART, GALLON,
//
//            // Metric dry
//            GRAM, KILOGRAM,
//
//            // Metric wet
//            MILLILITER, LITER
//        }
//	    }
        switch(inChars[0])
        {
            case 'l':
            {
                return ListComponent.UnitOfMeasure.LITER;
            }

            case 'm':
            {
                return ListComponent.UnitOfMeasure.MILLILITER;
            }

            case 'k':
            {
                return ListComponent.UnitOfMeasure.KILOGRAM;
            }

            case 'g':
            {
                if(inChars[1] == 'r')
                {
                    return ListComponent.UnitOfMeasure.GRAM;
                }
                return ListComponent.UnitOfMeasure.GALLON;
            }

            case 'q':
            {
                return ListComponent.UnitOfMeasure.QUART;
            }

            case 'c':
            {
                return ListComponent.UnitOfMeasure.CUP;
            }

            case 'f':
            {
                return ListComponent.UnitOfMeasure.FLUID_OUNCE;
            }
            case 't':
            {
                if(inChars[1] == 'e')
                {
                    return ListComponent.UnitOfMeasure.TEASPOON;
                }
                return ListComponent.UnitOfMeasure.TABLESPOON;
            }

            case 'o':
            {
                return ListComponent.UnitOfMeasure.OUNCE;
            }

            case 's':
            {
                if(inChars[1] == 't')
                {
                    return ListComponent.UnitOfMeasure.STRIP;
                }
                return ListComponent.UnitOfMeasure.SLICE;
            }
            case 'n':
            {
                return ListComponent.UnitOfMeasure.NO_UOM;
            }
            case 'e':
            {
                return ListComponent.UnitOfMeasure.EACH;
            }
            case 'b':
            {
                return ListComponent.UnitOfMeasure.BAG;
            }
            case 'p':
            {
                if(inChars[1] == 'i')
                {
                    return ListComponent.UnitOfMeasure.PINT;
                }
                if(inChars[3] == 'c')
                {
                    return ListComponent.UnitOfMeasure.POUCH;
                }
                return ListComponent.UnitOfMeasure.POUND;
            }
            default:
            {
                return UOM_ERR;
            }

        }

    }


    public RecipeHead parseLineToRecipe(String input)
    {

//        input =             // for testing only!
//                RECIPE_NAME + "Bacon sandwich" +end_RECIPE_NAME +
//                    EQUIPMENT +
//                        COMPONENT + "non-stick pan" + COMMA +"1" +COMMA + "on medium-low heat" + end_COMPONENT +
//                        COMPONENT + "Toaster" + COMMA +"1" + COMMA + /*"{single-line comment}" + */end_COMPONENT +
//                    end_EQUIPMENT +
//                    INGREDIENT +
//                        COMPONENT + "Bread"+COMMA+"2" + COMMA +"slices"+COMMA+"toasted"+ end_COMPONENT +
//                        COMPONENT + "Bacon" + COMMA +"8"+COMMA+ "strips" + COMMA + "get double-smoked bacon" + end_COMPONENT +
//                    end_INGREDIENT +
//                    PROCEDURE +
//                        COMPONENT + "Fry bacon over medium heat"+COMMA+"{timer value}"+COMMA+"do not let it get too hot"+end_COMPONENT +
//                        COMPONENT + "arrange bacon on the toasted bread"+COMMA+"{timer value}"+COMMA+"let the bacon drain off excess grease first" + end_COMPONENT +
//                    end_PROCEDURE +
//                    COMMENTS +
//                        COMPONENT + "This recipe was brought to the USA by my great-uncle Smalgard.  He loved him some bacon between two slices of toast." + end_COMPONENT +
//                        COMPONENT + "He carried this recipe all the way across the Atlantic on a barge he'd hitched a ride on from Britany in France." + end_COMPONENT +
//                        COMPONENT + "Marauders, who had also stowed away on the barge, tried to take the recipe from him, thinking it was a treasure map, but he fought them off with one hand while keeping this recipe safe in the other." + end_COMPONENT +
//                        COMPONENT + "They were almost sunk by a German U-boat, which was weird because WWI had ended four years earlier.  Uncle Smalgard threw the Marauders at the U-Boat, sinking it." + end_COMPONENT +
//                        COMPONENT + "Uncle Smalgard was adamant that mayonnaise would detract from the awesomeness of this recipe." + end_COMPONENT +
//                        COMPONENT + "Unfortunately for dear Uncle Smalgard, he was killed less than a week after landing in NYC by a freak incident involving an exploding chicken and laundry detergent, but we just assumed it was an accident." + end_COMPONENT +
//                    end_COMMENTS +
//                END_OF_RECIPE +
//                  Allergen_flags (4 bytes);



        HashMap<Character, ArrayList<String>> bySection = new HashMap<Character, ArrayList<String>>();


        /**
         * Populate all values in bySection with an ArrayList<String> consisting of the information between the COMPONENT and end_COMPONENT tags
         * Each ArrayList's contents correspond with a particular attribute, which is mapped by the tag which begins that particular attribute
         * The information being put in must still be parsed by individual fields
         */
//        bySection.put(EQUIPMENT, parseByComponents(specialSubstring(input, EQUIPMENT, end_EQUIPMENT)));
//        bySection.put(INGREDIENT, parseByComponents(specialSubstring(input, INGREDIENT, end_INGREDIENT)));
//        bySection.put(PROCEDURE, parseByComponents(specialSubstring(input, PROCEDURE, end_PROCEDURE)));
//        bySection.put(COMMENTS, parseByComponents(specialSubstring(input, COMMENTS, end_COMMENTS)));

        // parseComponentsFromSpecialSubstring takes in the original value of the argument input,
        // it then trims input down to a substring which represents contiguous component types,
        // then splits that substring into individual components
        // It returns an ArrayList<String> of all components between two componentType demarkers
        // here those ArrayList objects are being managed and tracked by a HashMap<Character, ArrayList<String> object
        // indexed by the componentType demarkers.
        bySection.put(EQUIPMENT, parseComponentsFromSpecialSubstring(input,EQUIPMENT,end_EQUIPMENT));
        bySection.put(INGREDIENT, parseComponentsFromSpecialSubstring(input,INGREDIENT, end_INGREDIENT));
        bySection.put(PROCEDURE, parseComponentsFromSpecialSubstring(input, PROCEDURE, end_PROCEDURE));
        bySection.put(COMMENTS, parseComponentsFromSpecialSubstring(input, COMMENTS, end_COMMENTS));

        // added 10/24
        String allergenInfo = input.substring(end_COMMENTS+1);  // under initial operation this is only one char long (as more food allergens are discovered this will become more than one char)



//		System.out.println(bySection.toString());

        RecipeHead recipeHead = new RecipeHead(specialSubstring(input, RECIPE_NAME, end_RECIPE_NAME));

        recipeHead.decipherAllergenFlags(allergenInfo); // added 10/24


        for(int i = 0; i < bySection.get(EQUIPMENT).size(); i++)
        {
            // the raw line as delivered from the map>arrayList bySection
            String rawLine = bySection.get(EQUIPMENT).get(i);

            // extracting the name of the piece of equipment
            String name = rawLine.substring(0, rawLine.indexOf(COMMA));

            rawLine = rawLine.substring(rawLine.indexOf(COMMA)+1);

            // extracting the number of pieces of equipment required
            int qty = Integer.parseInt(rawLine.substring(0,rawLine.indexOf(COMMA)));

            rawLine = rawLine.substring(rawLine.indexOf(COMMA)+1);

            String additionalText = rawLine;

//			// all confirmed, good to go!
//			System.out.println("Parsed info:\n\tName:\t"+ name +"\n\tQty:\t"+ (qty+3.1) + "\n\tText2:\t"+ additionalText);

            recipeHead.addEquipment(name, qty, additionalText);	// in the future, just add rawLine

        }
//		System.out.println('\n');
        for(int i = 0; i < bySection.get(INGREDIENT).size(); i++)
        {
            String rawLine = bySection.get(INGREDIENT).get(i);

            String name = rawLine.substring(0,rawLine.indexOf(COMMA));

            rawLine = rawLine.substring(rawLine.indexOf(COMMA)+1);

//			System.out.println(rawLine);

            double qty;
            try
            {
                qty = Double.parseDouble(rawLine.substring(0,rawLine.indexOf(COMMA)));

            }
            catch(NumberFormatException e)
            {
                System.out.println("Blank or invalid string passed to Double.parseDouble(String) while parsing quantity information for "+ name +" in recipe "+recipeHead.name()+".\n\tPlease investigate, or prompt user for correct information.  By default, qty will be set to -1.");
                qty = -1.0;
            }

            rawLine = rawLine.substring(rawLine.indexOf(COMMA)+1);

            ListComponent.UnitOfMeasure uom = parseUnitOfMeasure(rawLine.substring(0,rawLine.indexOf(COMMA)));

            rawLine = rawLine.substring(rawLine.indexOf(COMMA)+1);

//			System.out.println(rawLine);

//			System.out.println("Ingredient info:\n\tName:\t" + name + "\n\tQuantity:\t"+qty+"\n\tUofM:\t"+uom.toString()+"\n\tText2:\t"+additionalText);

            recipeHead.addIngredient(name, qty, uom, rawLine);
        }
//		System.out.println('\n');
        for(int i = 0; i < bySection.get(PROCEDURE).size(); i++)
        {
            String rawLine = bySection.get(PROCEDURE).get(i);

            String name = rawLine.substring(0,rawLine.indexOf(COMMA));

            rawLine = rawLine.substring(rawLine.indexOf(COMMA)+1);

            double timer;

            try
            {
                timer = Double.parseDouble(rawLine.substring(0,rawLine.indexOf(COMMA)));
                recipeHead.addProcedureWithTimer(name, timer, rawLine.substring(rawLine.indexOf(COMMA)+1));

            }
            catch(NumberFormatException e)
            {
//                timer = -1;
                System.out.println("Blank or invalid string passed to Double.parseDouble(String) while parsing quantity information for "+ name +" in recipe "+recipeHead.name()+".\n\tPlease investigate, or prompt user for correct information.  By default, the timer will not be utilized, and this component of the Procedures list will have ComponentType.PROCEDURE set instead of ComponentType.PROCEDURE_WITH_TIMER.");
                recipeHead.addProcedureWithoutTimer(name, rawLine.substring(rawLine.indexOf(COMMA)+1));

            }

            rawLine = rawLine.substring(rawLine.indexOf(COMMA)+1);

//			System.out.println("Procedure parsing:\n\tName:\t"+ name+"\n\tTimer:\t"+ timer+"\n\tText2:\t"+rawLine);

            recipeHead.addProcedureWithoutTimer(name, rawLine);


        }
//		System.out.println('\n');

        for(int i = 0; i< bySection.get(COMMENTS).size(); i++)
        {
//			String rawLine = bySection.get(COMMENTS).get(i);

            recipeHead.addComment(bySection.get(COMMENTS).get(i));
        }

        return recipeHead;
    }

    private ArrayList<String> parseComponentsFromSpecialSubstring(String input, final char beginDemarker, final char endDemarker)
    {
        return parseByComponents(specialSubstring(input,beginDemarker,endDemarker));
    }

    private ArrayList<String> parseByComponents(String input)
    {
        ArrayList<String> output = new ArrayList<String>();


        while(input.lastIndexOf(end_COMPONENT) >= 0)
        {
//			System.out.println("Parsing over:  "+input);

            output.add(specialSubstring(input,COMPONENT,end_COMPONENT));

//			System.out.println("In");
            input = input.substring(input.indexOf(end_COMPONENT)+1);

        }


        return output;
    }

    /**
     * provides a single string which represents all the data contained in a RecipeHead object
     */
    public String convertRecipeToWriteable(RecipeHead recipe)
    {
        return recipe.getRecipeInfoInStringFormat();
    }

    /**
     * Takes a single line, which represents a recipe, then writes it to the designated filename retrieved from the index file.
     * @warning DO NOT use the user-supplied filename as the actual filename in memory.
     * @param filename The return value of
     * @param data
     * @return
     */
    public boolean writeRecipeToFile(String filename, String data)
    {
        FileOutputStream oStream;

        try
        {
            oStream = _top.openFileOutput(filename, Context.MODE_PRIVATE);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Unable to access " + filename);
            e.printStackTrace();
            return false;
        }

        boolean outStream_writing = true;

        try
        {
            oStream.write(data.getBytes());
            outStream_writing = false;
            oStream.flush();
            oStream.close();

        }
        catch(IOException e)
        {
            System.out.println(outStream_writing? "<"+data+"> could not be printed." : "FileOutputStream experienced an error while closing.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Writes the contents of an ArrayList<String> object to a given filename, in the app's home directory.
     * @param filename  The target filename for the data.
     * @param data  An ArrayList<String> which contains lines of data to be written
     * @return  false if any problems should arise and the requested write cannot be completed; true otherwise.
     */
    public boolean writeToFile(String filename, ArrayList<String> data)
    {
        int dataSize = data.size();

        FileOutputStream oStream;

        try
        {
            oStream = _top.openFileOutput(filename, Context.MODE_PRIVATE);
//            oStream = _top.openFileOutput(finalizeFilename(filename), Context.MODE_PRIVATE);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Unable to access "+filename);
            e.printStackTrace();
            return false;
        }

        String current_line = "";   // \_ declaring outside the scope of the for-loop so that these variables can be used when catching an IOException.
        int i = 0;                  // /

        boolean outStream_writing = true;

        try
        {
//            for(; i < dataSize; i++)
            while(i < dataSize)
            {
                current_line = data.get(i++);
                oStream.write(current_line.getBytes());
            }

            oStream.flush();
            oStream.close();

            outStream_writing = false;
        }
        catch(IOException e)
        {
            System.out.println(outStream_writing? "<"+current_line+"> could not be printed, write to file abandoned on line "+i : "FileOutputStream experienced an error while closing.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * @return  a string representation of the current date and time, in the format "Month_Day_Year_Hour_Minute_Second_Millisecond" (yes, we need to know the millisecond!)
     */
    public String getDAT()
    {
        return new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss_ms").format(new Date());
    }

    /**
     * Note:  With use of an index file, this may be obsolete.
     * Checks whether a proposed filename is valid.
     * @param filename The filename to be checked for good syntax and form
     * @return True if the filename is acceptable; False if the filename may cause errors.
     */
    public boolean validateFilename(String filename)
    {
        int filename_length;

        // if filename is an empty string, then the filename is not valid:
        if((filename_length = filename.length()) == 0)
        {
            System.out.println("Filename is an empty string.");
            return false;
        }

        // if filename contains more than one '.', then the filename cannot be used, since the '.' may have been used to denote a sub-branch relating to another file of the same name
        if(filename.indexOf(".") != filename.lastIndexOf("."))
        {
            System.out.println("Filename \"" + filename +"\" has more than one '.' char.");
            return false;
        }

        // if the EXTENSION substring begins where it should (in "abcdef.scgc", the starting index of the extension is 11-5 = 6)
        // the logic of the conditional will ONLY allow filenames wherein the proper extension is in the proper location; any deviation will return false.
        if(filename.indexOf(EXTENSION) != filename_length - EXTENSION.length())
        {
            // if the correct extension cannot be located AND the given filename contains a '.'
            // then the default filename must be returned
            if(filename.contains("."))
            {
                System.out.println("Filename \"" + filename + "\" does not contain correct extension");
                return false;
            }
        }

        // if filename does not begin with '\\', then it must be added in
        if(filename.charAt(0) != '\\')
        {
            System.out.println("Filename \"" + filename +"\" does not start with '\\' char.");
            return false;

//            filename = '\\' + filename;
//            filename_length = filename.length();
        }

        // if filename contains any spaces, those spaces must be converted to underscores
        if(filename.contains(" "))
        {

            System.out.println("Filename \"" + filename +"\" contains spaces.");
            return false;

        }

        return true;
    }

    /**
     * These next few functions facilitate access to an index file, which is similar to how a page file and virtual addresses in a computer operates:
     *      User-supplied filenames are untrustworthy and likely very bad if they were ever actually used.
     *      The solution?
     *      Map user-supplied filenames to a program-generated filename.
     *      If the algorithm can guarante a one-to-one relation, then this will work every time.
     *
     * So user-supplied recipe name may look like this:
     *      "Aunt Mabel's Ultra-yummylicious cookie recipe.  To die for!"
     *
     *      Obviously, this is a terrible filename, and it is highly-unlikely that any user will search for exactly that recipe name, nor be able to find exactly that file on the first try.
     *
     *      That recipe name would map to something that looks more like this:
     *      F93Z
     *
     *
     */

    public ArrayList<String> getIndexFileLines()
    {
        return getLinesFromFile("IndexFile"+EXTENSION);
    }

    public boolean testUserSuppliedFileExists(String input)
    {
        ArrayList<String> indexFileLines = getIndexFileLines();

        return indexFileLines.contains(input);

    }

    public boolean testUserSuppliedFileExists(String input,ArrayList<String> indexFileLines)
    {
        return indexFileLines.contains(input);
    }


    /**
     * Takes in two strings and returns a double which represents how similar are two string
     * @return 0 if the strings are identical (case insensitive); the higher the return value, the less-similar are the two strings.  Any return value less than 2.5 probably indicates a close-enough match, but this is only an educated guess
     */
    public double strengthOfStringMatch(String input0, String input1)
    {

        int oLength = input0.length(), tLength = input1.length(),
                shorterLength = (oLength > tLength)? tLength : oLength,
                longerLength = (oLength > tLength)? oLength : tLength;

        int[] diffs = new int[longerLength];


        input0 = input0.toLowerCase();
        input1 = input1.toLowerCase();

//		System.out.print("{");
        for(int i = 0; i < shorterLength; i++)
        {
            diffs[i] = Math.abs(input0.charAt(i) - input1.charAt(i));
//			System.out.print(diffs[i]+ " ");
        }
//		System.out.println("}");
        // by providing a linearly increasing set of integers to fill out the remainder of diffs[], strings which differ in length by one char are penalized less than strings which differ by more than one char
        for(int i = shorterLength; i < longerLength; i++)
        {
            diffs[i] = i-shorterLength+1;
        }

        int sum = 0;

        for(int i = 0; i < longerLength; i++)
        {
            sum += diffs[i];
        }

//        double average = ((double)sum)/ ((double)longerLength);
        return ((double)sum)/ ((double)longerLength);

    }


    /**
     * Use in conjunction with "testUserSuppliedFileExists(...)" to guarantee that the caller knows when it can trust the output of this function, or when to provide the user with options for selecting the correct file.
     * If the exact user-supplied name matches (case insensitive) one found in the index file, then the actual filename is returned as the sole member of the output.
     * If the user-supplied name does not match, then a list of possible user-supplied recipe names are returned.
     * Hence why it is important to use this function after checking the return value of " public boolean testUserSuppliedFileExists(...) ".
     */
    public ArrayList<String> getFilename(String userSupplied)
    {
        ArrayList<String> indexFileLines = getIndexFileLines(), output = new ArrayList<String>();

        for(int i = 0; i < indexFileLines.size(); i++)
        {

            String current = indexFileLines.get(i), key = current.substring(0,current.indexOf(INDEX_FILE_DELIM)), value = current.substring(current.indexOf(INDEX_FILE_DELIM)+1);

            if(key.compareToIgnoreCase(userSupplied) == 0)
            {
                output.add(value);
                return output;
            }
        }
        // from this point on, the filename is guaranteed to not have been located.



        return trimToUserGeneratedRecipeNamesOnly(getPossibleSimilarFilenames(userSupplied, indexFileLines, INDEX_FILE_TOLERANCE));
    }

    public String getFilenameFromIndex(ArrayList<String> rawLinesFromIndexFile, int index)
    {
        if(index >= rawLinesFromIndexFile.size())
        {
            return "";
        }

        String output = rawLinesFromIndexFile.get(index);

        return output.substring(output.indexOf(INDEX_FILE_DELIM)+1);
    }

    /**
     * Generates a new, computer-friendly filename, which can be mapped in the caller to a user-supplied recipe name
     */
    public String generateFilename(String userSupplied)
    {
        ArrayList<String> IFLines = getIndexFileLines();

        String lastFilenameInIFLines = IFLines.get(IFLines.size()-1);

        lastFilenameInIFLines = lastFilenameInIFLines.substring(lastFilenameInIFLines.indexOf(INDEX_FILE_DELIM)+1);

        char[] lastFNameArray = lastFilenameInIFLines.toCharArray();

        int lastSize = lastFilenameInIFLines.length();

        boolean carry = true, overflow = false;

        for(int i = lastSize -1; i >= 0; i--)
        {
            if(!carry)
            {
                continue;
            }
            if(i == 0)
            {
                overflow = carry;
                break;
            }

            if(carry)
            {
                if(lastFNameArray[i] + 1 <= 'z')
                {
                    lastFNameArray[i] = (char) (lastFNameArray[i] + 1);
                    carry = false;
                    continue;
                }
                if(lastFNameArray[i] + 1 > 'z')
                {
                    lastFNameArray[i] = 'a';
                    carry = true;
                    continue;
                }

            }


        }

        String output = (overflow)? "a" : "";

        for(int i = 0; i < lastSize; i++)
        {
            output += lastFNameArray[i];
        }

        return output;

    }

    public ArrayList<String> trimToUserGeneratedRecipeNamesOnly(ArrayList<String> rawSimilarLinesFromIndexFile)
    {
        ArrayList<String> output = new ArrayList<String>();

        for(int i = 0; i < rawSimilarLinesFromIndexFile.size(); i++)
        {
            output.add(rawSimilarLinesFromIndexFile.get(i).substring(0,INDEX_FILE_DELIM));
        }

        return output;
    }

    /**
     * Compares strings lexicographically, and returns any matches whose comparison factor is less than, or equal to, a caller-supplied tolerance. (2.5 is a decent tolerance)
     */
    public ArrayList<String> getPossibleSimilarFilenames(String input, ArrayList<String> allLines, double tolerance)
    {
        ArrayList<String> possibleMatches = new ArrayList<String>();

        for(int i = 0; i < allLines.size(); i++)
        {
            if(strengthOfStringMatch(input,allLines.get(i).substring(0,INDEX_FILE_DELIM)) <= tolerance) // compares only the left-hand side of a member of allLines
            {
                possibleMatches.add(allLines.get(i));
            }
        }



        return possibleMatches;
    }

    public char getIndexFileDelimiter()
    {
        return INDEX_FILE_DELIM;
    }

    public ArrayList<String> convertShoppingListToWriteable(ArrayList<ShoppingNode> shopList)
    {
        ArrayList<String> writeable = new ArrayList<String>();

        for(int i = 0; i < shopList.size(); i++)
        {
            ShoppingNode current = shopList.get(i);
            String itemName = INGREDIENT + current.name + end_INGREDIENT,
                storeToPurchase = _shopList_STORE + current.store + end_shopList_STORE,
                srcRecipe = _shopList_RECIPE_ADDED + current.recipeAddedFrom + end_shopList_RECIPE_ADDED,
                addedToCart = _shopList_IN_CART + ( (current.in_cart)? "TRUE" : "FALSE" ) + end_shopList_IN_CART,
                qtyToBuy = _shopList_QTY + "" + current.quantity + "" + end_shopList_QTY,
                uom = _shopList_UOM + current.unit_of_measure.toString() + end_shopList_UOM;

            String toAdd = itemName + storeToPurchase + srcRecipe + addedToCart + qtyToBuy + uom;



            writeable.add(toAdd);

        }

        return writeable;
/*
 * for ease of reference:
 *

public class ShoppingNode
{
    public String name = "", store = "", recipeAddedFrom = "";
//        RecipeHead recipeAddedFrom = null;    // whichever you decide to use...
    public double quantity = -1.0, price = -1.0;
    public boolean in_cart = false;
}

*/
    }

    public boolean writeToShoppingList(ArrayList<String> data)
    {
        return writeToFile("ShoppingList", data);
    }

    public boolean convertShoppingNodesAndWrite(ArrayList<ShoppingNode> input)
    {
        return writeToShoppingList( convertShoppingListToWriteable( input ) );
    }

    public ArrayList<ShoppingNode> getShoppingList()
    {
        ArrayList<String> shoplistLines = getLinesFromFile("ShoppingList");

        ArrayList<ShoppingNode> output = new ArrayList<ShoppingNode>();

        for(int i = 0; i < shoplistLines.size(); i++)
        {
            String current = shoplistLines.get( i );

            String item = specialSubstring( current, INGREDIENT, end_INGREDIENT),
                store = specialSubstring( current, _shopList_STORE, end_shopList_STORE),
                addedFrom = specialSubstring( current, _shopList_RECIPE_ADDED, end_shopList_RECIPE_ADDED);

            ListComponent.UnitOfMeasure uom = parseUnitOfMeasure( specialSubstring( current, _shopList_UOM, end_shopList_UOM));

//            Double qty = Double.parseDouble( specialSubstring( current,_shopList_QTY, end_shopList_QTY)),
//                price = Double.parseDouble( specialSubstring( current, _shopList_PRICE, end_shopList_PRICE));

            boolean inCart = specialSubstring( current, _shopList_IN_CART, end_shopList_IN_CART).compareToIgnoreCase("TRUE") == 0;

            double qty = -1.0, price = -1.0;

            try
            {
                qty = Double.parseDouble( specialSubstring( current, _shopList_QTY, end_shopList_QTY));
                if(testBadOrNegativeDouble(qty))
                {
                    throw new NumberFormatException();
                }
//                switch(qty) // rats.  JVM won't allow this.
//                {
//                    case Double.NEGATIVE_INFINITY:
//                    case Double.POSITIVE_INFINITY:
//                    case Double.MAX_EXPONENT:
//                    case Double.MAX_VALUE:
//                    {
//                        throw new NumberFormatException();
//                    }
//                    default:
//                    {
//                        break;
//                    }
//                }
            }
            catch(NumberFormatException q)
            {
                System.out.println("Error parsing quantity information while retrieving the shopping list.  Non-critical error, quantity will be set to -1.0");
                qty = -1.0;
            }
            try
            {
                price = Double.parseDouble( specialSubstring( current, _shopList_PRICE, end_shopList_PRICE));
                if(testBadOrNegativeDouble(price))
                {
                    throw new NumberFormatException();
                }
            }
            catch(NumberFormatException p)
            {
                System.out.println("Error parsing price information while retrieving the shopping list.  Non-critical error, price will be set to -1.0");
                price = -1.0;
            }

            ShoppingNode sNode = new ShoppingNode();

            sNode.in_cart = inCart;
            sNode.name = item;
            sNode.price = price;
            sNode.quantity = qty;
            sNode.store = store;
            sNode.recipeAddedFrom = addedFrom;
            sNode.unit_of_measure = uom;

            output.add(sNode);




        }

        return output;

    }

    private boolean testBadOrNegativeDouble(double input)
    {
        return input < 0 || Double.isInfinite(input) || Double.isNaN(input) || input == Double.MAX_EXPONENT || input == Double.MAX_VALUE;
    }

    /**
     * All variables of ShoppingNode are initialized to some obviously-wrong value.  This is to make it adequately obvious when something has gone wrong.
     */
    public class ShoppingNode
    {
        public String name = "", store = "", recipeAddedFrom = "";
//        RecipeHead recipeAddedFrom = null;    // whichever you decide to use...
        public double quantity = -1.0, price = -1.0;
        public boolean in_cart = false;
        public ListComponent.UnitOfMeasure unit_of_measure = UOM_ERR;



    }



}
