

package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;

public class RecipeWalk6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk6);

        //has no toolbar

    }

    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }
    //button to created recipe page
    public void finalCreate(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
