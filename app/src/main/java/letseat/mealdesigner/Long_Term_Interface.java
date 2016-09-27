package letseat.mealdesigner;

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
import java.util.Date;

/**
 * This is the only class permitted (by group convention) to access the long-term memory of the device.
 * If the functionality of this class is desired, use this as an inherited class.
 * Created by George_Sr on 9/20/2016.
 */
public class Long_Term_Interface
{
    private MainActivity _top;
    private File _appHomeDir;                           // the internal directory of the app
    private static final String EXTENSION = ".scgc";    // this can be changed, but all files which exist with the outdated extension need to be updated
    private static final String DEFAULT = "default_filename_";  // the default filename to be used if a given filename is invalid


    public Long_Term_Interface(MainActivity top)
    {
        _top = top;
        _appHomeDir = _top.getFilesDir();

    }

    // TODO:    FileInputStream may not be the class needed.  Research this.
    public ArrayList<String> getLinesFromFile(String filename)
    {
        ArrayList<String> output = new ArrayList();

        FileInputStream inStream;

        try
        {
            // since the caller is not necessarily expecting the filename to have been switched by the writeToFile function, this function must check the given filename first
            // if the given filename causes a FileNotFoundException, then proceed to give the user options for retrieving from the Default Files
            inStream = _top.openFileInput(filename);   // TODO:  Research then fix this!

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
                // something clever should go here
            }



        }
        catch(FileNotFoundException FNFE)
        {
            // code to fetch default files and present them to the user goes here.
        }

        return output;
    }

    /**
     * Takes in a single, raw line that has been read from long-term memory, then parses it into a useable format for this
     * @param input
     * @return
     */
    public ArrayList<String> parseLine(String input)
    {
        ArrayList<String> output = new ArrayList();

        String recipeName = input.substring(0,input.indexOf(0x81));

        input = input.substring(input.indexOf(0x81));

        String equipment = input.substring(0, input.indexOf(0x82));

        input = input.substring(input.indexOf(0x82));

        String ingredients = input.substring(0, input.indexOf(0x83));

        input = input.substring(input.indexOf(0x83));

        String steps = input.substring(0, input.indexOf(0x84));

        input = input.substring(input.indexOf(0x84));

        String comments = input;

        


        return output;
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
            oStream = _top.openFileOutput(finalizeFilename(filename), Context.MODE_PRIVATE);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Unable to open "+filename);
            e.printStackTrace();
            return false;
        }

        String current_line = "";   // \_ declaring outside the scope of the for-loop so that these variables can be used when catching an IOException.
        int i = 0;                  // /

        boolean outStream_writing = true;

        try
        {
            for(; i < dataSize; i++)
            {
                current_line = data.get(i);
                oStream.write(current_line.getBytes());
            }

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
     * @return  a string representation of the current date and time, in the format "Month_Day_Year_Hour_Minute_Second_Millisecond"
     */
    public String getDAT()
    {
        return new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss_ms").format(new Date());
    }

    /**
     *
     * TODO:  Keep this accessible to the caller
     * TODO:  Write a public function validates (by boolean return value) whether a prospective filename meets the same criteria checked by this function.
     * Access to this function will be changed to private once its functionality is verified.
     * If the filename reverts to default, it will still be searchable.  This is intended as the last safety before writing something to long-term memory.
     *      If the caller can verify a proper filename before using this function, then the default filename should never be neccessary.
     * Characteristics of a valid filename:
     *      non-empty;
     *      only contains one '.', which demarks the end of the actual name and the beginning of the extension;
     *      has correct extension at the end;
     *      does not begin with '.';
     *      does begin with '\\';
     *      does not contain any spaces (replace ' ' with '_';
     *      not the default name ("default_filename_[date and time]");
     * @param filename the prospective name of the file to be created or accessed
     * @return if the argument (filename) can be resolved to a valid filename, it will return that valid filename; else, it will return a generic filename.
     */
    public String finalizeFilename(String filename)
    {
        int filename_length;

        // if filename is an empty string, then the default filename will be used:
        if((filename_length = filename.length()) == 0)
        {
            return DEFAULT + getDAT() + EXTENSION;
        }

        // if filename contains more than one '.', then the filename cannot be used, since the '.' may have been used to denote a sub-branch relating to another file of the same name
        // the safest remedy is to use a default filename
        if(filename.indexOf(".") != filename.lastIndexOf("."))
        {
            return DEFAULT + getDAT() + EXTENSION;
        }

        // if the EXTENSION substring begins where it should (in "abcdef.scgc", the starting index of the extension is 11-5 = 6)
        // the logic of the conditional will ONLY allow filenames wherein the extension is in the proper location.
        if(filename.indexOf(EXTENSION) != filename_length - EXTENSION.length())
        {
            // if the correct extension cannot be located AND the given filename contains a '.'
            // then the default filename must be returned
            if(filename.contains("."))
            {
                return DEFAULT + getDAT() + EXTENSION;
            }

            filename += EXTENSION;
            filename_length = filename.length();
        }

        // if filename does not begin with '\\', then it must be added in
        if(filename.charAt(0) != '\\')
        {
            filename = '\\' + filename;
            filename_length = filename.length();
        }

        // if filename starts with the default filename, then the whole thing must be converted to the proper default filename
        // this is to ensure correct and consistent flow when retrieving unknown files (which have been assigned the default filename)
        if(filename.indexOf(DEFAULT) == 0)
        {
            return DEFAULT + getDAT() + EXTENSION;
        }

        // if filename contains any spaces, those spaces must be converted to underscores
        if(filename.contains(" "))
        {

            String temp_filename = "";


            for(int i = 0; i< filename_length; i++)
            {
                char current = filename.charAt(i);
                temp_filename += (current == ' ')? '_' : current;
            }

            filename = temp_filename;
        }

        return filename;
    }

}
