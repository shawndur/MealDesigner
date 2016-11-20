

package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    Recipe newRecipe;
    Database x ;//= ((MealDesignerApp) getApplication()).getDatabase();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk6);
        x = ((MealDesignerApp) getApplication()).getDatabase();
        newRecipe = x.getTempRecipe();
        //has no toolbar

        editText1 = (EditText) findViewById(R.id.editText8); //dairy
        editText2 = (EditText) findViewById(R.id.editText9); //nuts
        editText3 = (EditText) findViewById(R.id.editText10); //eggs
        editText4 = (EditText) findViewById(R.id.editText11); //soy
        editText5 = (EditText) findViewById(R.id.editText12); //fish
    }


    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }
    //button to created recipe page
    public void finalCreate(View view){
        boolean dairy = editText1.getText().toString().toLowerCase().trim().equals("yes");
        boolean nuts = editText2.getText().toString().toLowerCase().trim().equals("yes");
        boolean eggs = editText3.getText().toString().toLowerCase().trim().equals("yes");
        boolean soy = editText4.getText().toString().toLowerCase().trim().equals("yes");
        boolean fish = editText5.getText().toString().toLowerCase().trim().equals("yes");
        newRecipe.setAllergens(dairy,nuts,eggs,soy,fish);
        x.setRecipe(newRecipe);
        x.clearTemp();
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
