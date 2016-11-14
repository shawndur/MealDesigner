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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk3 extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextAmount;
    private TextView textView;
    ArrayList<String> ingredient  = new ArrayList<>();
    StringBuilder b = new StringBuilder();
    String unit = "";
    String finalIngredient;
    Recipe newRecipe;
    //declare spinner
    Spinner measure_spinner; // = (Spinner) findViewById(R.id.measure_spinner);
    Database x ;//= ((MealDesignerApp) getApplication()).getDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk3);
        x = ((MealDesignerApp) getApplication()).getDatabase();
        measure_spinner = (Spinner) findViewById(R.id.measure_spinner);
        newRecipe = x.getTempRecipe();
        //has no toolbar
        textView = (TextView) findViewById(R.id.textViewIngredients);
        textView.setText("");
        editTextName = (EditText) findViewById(R.id.editText5);
        editTextAmount = (EditText) findViewById(R.id.editText6);


        //populate spinner
        String[] items = new String[] { "Select a unit", "Cup", "Ounce", "Gram", "Tablespoon", "Teaspoon", "Fld Ounce", "Liter" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        // Specify the layout to be more spaced out
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter
        measure_spinner.setAdapter(adapter);
        measure_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override //what to do with selected item
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    String label = parent.getItemAtPosition(position).toString();
                    unit = label;
            }
            @Override //what to do if no item selected
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    //get string from fill in boxes
    //add ingredient button
    public void addIngredient(View view){
        String name = editTextName.getText().toString();
        String amounts = editTextAmount.getText().toString();
        if (name.matches("")) {
            Toast.makeText(this, "You did not fill in an ingredient", Toast.LENGTH_SHORT).show();
        }
        else{
            finalIngredient= amounts + " " + unit+ " " +name;
            ingredient.add(finalIngredient);
            //clear all fill ins/selection
            measure_spinner.setSelection(0);
            editTextAmount.setText("");
            editTextName.setText("");
            b.append(finalIngredient+"\n");
            textView.setText(b.toString());
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
