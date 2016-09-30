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
import letseat.mealdesigner.recipewalk.RecipeWalk4;
import letseat.mealdesigner.favorites.Favorites;
import letseat.mealdesigner.recipies.MainRecipe;

public class RecipeWalk3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk2);

        //has no toolbar

    }

    //next step button
    public void step4(View view){
        Intent intent = new Intent(this,RecipeWalk4.class);
        startActivity(intent);
    }
    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
