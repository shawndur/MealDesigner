

package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import letseat.mealdesigner.R;
import letseat.mealdesigner.recipeinfo.RecipeInfo;
import letseat.mealdesigner.recipewalk.RecipeWalk6;
import letseat.mealdesigner.recipies.MainRecipe;

public class RecipeWalk5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk5);

        //has no toolbar

    }

    //next step button
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
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
