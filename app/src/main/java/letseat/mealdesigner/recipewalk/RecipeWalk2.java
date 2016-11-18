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
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;

import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk2 extends AppCompatActivity {
    private EditText editText1;
    ArrayList<String> equipment  = new ArrayList<>();
    Recipe newRecipe;
    Spinner equip_spinner ;//= (Spinner) findViewById(R.id.equip_spinner);
    String equip_drop_down="";
    StringBuilder b = new StringBuilder();
    private TextView textView;


    Database x;// = ((MealDesignerApp) getApplication()).getDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk2);
        x = ((MealDesignerApp) getApplication()).getDatabase();
        newRecipe = x.getTempRecipe();
        equip_spinner = (Spinner) findViewById(R.id.equip_spinner);
        editText1 = (EditText) findViewById(R.id.editText13);

        textView = (TextView) findViewById(R.id.textViewEquip);
        textView.setText("");
        //has no toolbar



        //populate spinner
        String[] items = new String[] { "Select an Item", "Fork", "Spoon", "Knife", "Measuring Cup", "Skillet", "Large Pot", "Strainer" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        // Specify the layout to be more spaced out
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter
        equip_spinner.setAdapter(adapter);
        equip_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override //what to do with selected item from spinner
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String label = parent.getItemAtPosition(position).toString();
                equip_drop_down = label;
                //equipment.add(label);
              //  Recipe newRecipe;
              //  x.setRecipe().setIngredients();
            }

            @Override //what to do if no item selected
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }

    //add equipment button
    public void addEquip(View view) {
        //get string from fill in blank
        String equipname = editText1.getText().toString();
        //both blanks are empty
        //if(equipname.matches("") && equip_drop_down.matches("") ){
        //    Toast.makeText(this, "You did not fill in an equipment", Toast.LENGTH_SHORT).show();
        //}
        //no equipment name written
        //else
        if (equipname.matches("")) {
            equipment.add(equip_drop_down);
            equip_spinner.setSelection(0);
            b.append(equip_drop_down + "\n");
            textView.setText(b.toString());
        }

        //equipment name was written

        else{
            equipment.add(equipname);
            b.append(equipname+"\n");
            textView.setText(b.toString());
            //clear fill in blank
            editText1.setText("");
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
