package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;


import letseat.mealdesigner.R;

import letseat.mealdesigner.recipies.MainRecipe;

public class RecipeWalk2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk2);

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
            }

            @Override //what to do if no item selected
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }

    //next step button
    public void step3(View view){
        Intent intent = new Intent(this,RecipeWalk3.class);
        startActivity(intent);
    }
    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
