package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk1 extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    String preptime = "Preptime: ";
    String cooktime = "Cook TIme: ";
    String portionsize= "Recipe Yield: ";
    ArrayList<String> recipe  = new ArrayList<>();
    Recipe newRecipe;
    Database x ;//= ((MealDesignerApp) getApplication()).getDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk1);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        x = ((MealDesignerApp) getApplication()).getDatabase();
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);


        //has no toolbar

    }


    //next step button
    public void step2(View view){
        String input1 = editText1.getText().toString();
        String input2 = preptime + editText2.getText().toString();
        String input3 = cooktime + editText3.getText().toString();
        String input4 = portionsize + editText4.getText().toString();
        newRecipe = x.newRecipe(input1); //
      //newRecipe = newRecipe(input1); gives off error
        recipe.add(input1);
        recipe.add(input2);
        recipe.add(input3);
        recipe.add(input4);
        newRecipe.setTempRecipePass(recipe);
        x.setTempRecipe(newRecipe);

        Intent intent = new Intent(this,RecipeWalk2.class);
        startActivity(intent);
    }
    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
