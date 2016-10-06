package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;

public class RecipeWalk3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk3);

        //has no toolbar

        //declare spinner
        Spinner measure_spinner = (Spinner) findViewById(R.id.measure_spinner);

        //populate spinner
        String[] items = new String[] { "Cup", "Ounce", "Gram", "Tablespoon", "Teaspoon", "Fld Ounce", "Liter" };

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
            }

            @Override //what to do if no item selected
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    //next step button
    public void step4(View view){
        Intent intent = new Intent(this,RecipeWalk4.class);
        startActivity(intent);
    }
    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
