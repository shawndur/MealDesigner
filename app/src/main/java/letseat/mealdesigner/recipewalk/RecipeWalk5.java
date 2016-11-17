

package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk5 extends AppCompatActivity {
    Database x; //= ((MealDesignerApp) getApplication()).getDatabase();
    Recipe new_recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk5);
        x = ((MealDesignerApp) getApplication()).getDatabase();
        //has no toolbar
        new_recipe= x.getTempRecipe();

    }

    //next step button to allergies
    public void step6(View view){
        Intent intent = new Intent(this,RecipeWalk6.class);
        startActivity(intent);
    }
    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }
    //button to created recipe page
    public void create(View view){
        x.setRecipe(new_recipe);
        x.clearTemp();
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}