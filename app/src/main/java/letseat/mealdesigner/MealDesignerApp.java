package letseat.mealdesigner;

import android.app.Application;

import letseat.mealdesigner.storage.Database;

/**
 * This Is the application class for meal designer.
 * It extends the default class adding:
 *      -A instance variable for the Long_Term_Interface object
 *      -It instantiates the Long_Term_Interface object
 *      -It provides a getter for the Long_Term_Interface object
 * Created by shawn on 10/12/16.
 */

public class MealDesignerApp extends Application {
    private Database _storage; //Global Long_Term_Interface object

    @Override
    public void onCreate(){
        //_storage = new Long_Term_Interface(this);
        super.onCreate();
    }

    /***
     * This is a getter method for the global Long_Term_Interface
     * @return a Long_Term_Interface object
     */
    public Database getDatabase(){
        return _storage;
    }

}
