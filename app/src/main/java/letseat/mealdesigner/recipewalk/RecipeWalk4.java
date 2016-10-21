

package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;

public class RecipeWalk4 extends AppCompatActivity {
    Database x = ((MealDesignerApp) getApplication()).getDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk4);

        //has no toolbar


    }

    //next step button
    public void step5(View view){
        Intent intent = new Intent(this,RecipeWalk5.class);
        startActivity(intent);
    }

    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
