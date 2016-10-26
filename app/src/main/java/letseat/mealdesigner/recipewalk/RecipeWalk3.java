package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Ingredient;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk3 extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextAmount;
    ArrayList<String> ingredient  = new ArrayList<>();
    ArrayList<String> amount  = new ArrayList<>();
    ArrayAdapter<String> m_adapter;
    String unit = "";
    String finalIngredient;
    Recipe newRecipe;
    Ingredient newIngredient;
    Database x = (Database) ((MealDesignerApp) getApplication()).getDatabase().getTempRecipe();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk3);

        //has no toolbar

        editTextName = (EditText) findViewById(R.id.editText5);
        editTextAmount = (EditText) findViewById(R.id.editText6);
        //declare spinner
        Spinner measure_spinner = (Spinner) findViewById(R.id.measure_spinner);

        //populate spinner
        String[] items = new String[] { "   ", "Cup", "Ounce", "Gram", "Tablespoon", "Teaspoon", "Fld Ounce", "Liter" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        // Specify the layout to be more spaced out
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //apply the adapter
        measure_spinner.setAdapter(adapter);

        measure_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override //what to do with selected item

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    final String label = parent.getItemAtPosition(position).toString();
                    //newIngredient.setName(label);
                    //newIngredient.setAmount(input2);
                    unit = label;
                    //ingredient.add(label);
                    //String finalIngredient = input2 + label;
                    //ingredient.add(finalIngredient);
                    //     Recipe newRecipe;
                    //     newRecipe.setName();

            }

            @Override //what to do if no item selected
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    final String input1 = editTextName.getText().toString();
    final String input2 = editTextAmount.getText().toString();

    //add ingredient button
    public void addIngredient(View view){
        if (input1.matches("")) {
            Toast.makeText(this, "You did not fill in an ingredient", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            finalIngredient= input1 + " " + input2+ " " +unit;
            ingredient.add(finalIngredient);
        }
    }
    //next step button
    public void step4(View view){
        newRecipe.setIngredients(ingredient);
        x.setTempRecipe(newRecipe);
        Intent intent = new Intent(this,RecipeWalk4.class);
        startActivity(intent);
    }
    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
