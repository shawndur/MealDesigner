

package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk6 extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    ArrayList<String> allergies  = new ArrayList<>();
    ArrayAdapter<String> m_adapter;
    Recipe newRecipe;
    Database x = (Database) ((MealDesignerApp) getApplication()).getDatabase().getTempRecipe();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk6);

        //has no toolbar

        editText1 = (EditText) findViewById(R.id.editText8);
        editText2 = (EditText) findViewById(R.id.editText9);
        editText3 = (EditText) findViewById(R.id.editText10);
        editText4 = (EditText) findViewById(R.id.editText11);
        editText5 = (EditText) findViewById(R.id.editText12);
    }

    final String input1 = editText1.getText().toString();
    final String input2 = editText2.getText().toString();
    final String input3 = editText3.getText().toString();
    final String input4 = editText4.getText().toString();
    final String input5 = editText5.getText().toString();

    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }
    //button to created recipe page
    public void finalCreate(View view){
        allergies.add(input1);
        allergies.add(input2);
        allergies.add(input3);
        allergies.add(input4);
        allergies.add(input5);
        newRecipe.setSteps(allergies);
        x.setTempRecipe(newRecipe);
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
