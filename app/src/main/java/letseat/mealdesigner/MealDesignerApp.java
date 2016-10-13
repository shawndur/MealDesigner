package letseat.mealdesigner;

import android.app.Application;
import android.util.Log;

import letseat.mealdesigner.long_term_memory.Long_Term_Interface;

/**
 * Created by shawn on 10/12/16.
 */

public class MealDesignerApp extends Application {
    private Long_Term_Interface _storage;

    @Override
    public void onCreate(){
        _storage = new Long_Term_Interface(this);
        super.onCreate();
    }

    public Long_Term_Interface getLTI(){
        return _storage;
    }

}
