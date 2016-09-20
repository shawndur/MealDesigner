package letseat.mealdesigner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the only class permitted (by group convention) to access the long-term memory of the device.
 * If the functionality of this class is desired, use this as an inherited class.
 * Created by George_Sr on 9/20/2016.
 */
public class Long_Term_Interface
{
    private File _appHomeDir;                           // the internal directory of the app
    private static final String EXTENSION = ".scgc";    // this can be changed, but all files which exist with the outdated extension need to be updated
    private static final String DEFAULT = "default_filename_";  // the default filename to be used if a given filename is invalid


    public Long_Term_Interface(MainActivity top)
    {
        _appHomeDir = top.getFilesDir();

    }

    /**
     * @return  a string representation of the current date and time, in the format "Month_Day_Year_Hour_Minute_Second_Millisecond"
     */
    public String getDAT()
    {
        return new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss_ms").format(new Date());
    }

    /**
     * Access to this function will be changed to private once its functionality is verified.
     * @param filename the prospective name of the file to be created or accessed
     * @return if the argument (filename) can be resolved to a valid filename, it will return that valid filename; else, it will return a generic filename.
     */
    public String finalizeFilename(String filename)
    {
        int filename_length;

//        if(!filename.contains(EXTENSION))
//        {
//            filename += EXTENSION;
//        }

        /**
         * Characteristics of a valid filename:
         *      non-empty
         *      only contains one '.', which demarks the end of the actual name and the beginning of the extension
         *      has correct extension at the end
         *      does not begin with '.'
         *      does begin with '\\'
         *      does not contain any spaces (replace ' ' with '_'
         *      not the default name ("default_filename_[date and time]")
         */

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
