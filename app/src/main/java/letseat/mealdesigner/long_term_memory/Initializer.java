package letseat.mealdesigner.long_term_memory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        try
        {
            FileInputStream inStream = new FileInputStream("Index_File.txt");
            return true;
        }
        catch(FileNotFoundException e)
        {
            return false;
        }

    }

    public void initialize()
    {

    }
}
