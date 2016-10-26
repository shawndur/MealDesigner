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
import android.widget.AdapterView.OnItemSelectedListener;


import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;

import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk2 extends AppCompatActivity {
    private EditText editText1;
    ArrayList<String> equipment  = new ArrayList<>();
    ArrayAdapter<String> m_adapter;
    Recipe newRecipe;

    Database x = (Database) ((MealDesignerApp) getApplication()).getDatabase().getTempRecipe();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk2);

        //Intent intent= getIntent();
        //ArrayList name= intent.getStringArrayListExtra(newRecipe);

        editText1 = (EditText) findViewById(R.id.editText13);
        //has no toolbar

        //declare spinner
        Spinner equip_spinner = (Spinner) findViewById(R.id.equip_spinner);

        //populate spinner
        String[] items = new String[] { "Fork", "Spoon", "Knife", "Measuring Cup", "Skillet", "Large Pot", "Strainer" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        // Specify the layout to be more spaced out
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //apply the adapter
        equip_spinner.setAdapter(adapter);

        equip_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override //what to do with selected item
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String label = parent.getItemAtPosition(position).toString();
                equipment.add(label);
              //  Recipe newRecipe;
              //  x.setRecipe().setIngredients();
            }

            @Override //what to do if no item selected
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }
    String input1 = editText1.getText().toString();

    //add equipment button
    public void addEquip(View view){
        if (input1.matches("")) {
            //void method to not add if empty
        }
        else{
            equipment.add(input1);
        }
    }
    //next step button
    public void step3(View view){
        newRecipe.setTools(equipment);
        x.setTempRecipe(newRecipe);
        Intent intent = new Intent(this,RecipeWalk3.class);
        startActivity(intent);
    }
    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
