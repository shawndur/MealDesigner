

package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk4 extends AppCompatActivity {
    private EditText editText1;
    ArrayList<String> steps  = new ArrayList<>();
    ArrayAdapter<String> m_adapter;
    Recipe newRecipe;
    Database x = (Database) ((MealDesignerApp) getApplication()).getDatabase().getTempRecipe();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk4);

        //has no toolbar
        editText1 = (EditText) findViewById(R.id.editText7);

    }
    final String input1 = editText1.getText().toString();

    public void addStep(View view){
        if (input1.matches("")) {
            Toast.makeText(this, "You did not enter a step", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            steps.add(input1);
        }
    }
    //next step button
    public void step5(View view){
        newRecipe.setSteps(steps);
        x.setTempRecipe(newRecipe);
        Intent intent = new Intent(this,RecipeWalk5.class);
        startActivity(intent);
    }

    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
