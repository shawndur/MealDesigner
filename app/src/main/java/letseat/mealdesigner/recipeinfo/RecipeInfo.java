package letseat.mealdesigner.recipeinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import letseat.mealdesigner.MainActivity;
import letseat.mealdesigner.R;
import letseat.mealdesigner.cook.Cook;
import letseat.mealdesigner.favorites.Favorites;

public class RecipeInfo extends AppCompatActivity {

    /**
     * Instance variables to hold recipe info
     */
    ArrayList<String> _steps = new ArrayList<>();
    ArrayList<String> _ingredients = new ArrayList<>();
    ArrayList<String> _tools = new ArrayList<>();
    String _name = new String();


    /**
     * Initializes instance variables. Currently uses hardcoded strings
     */
    // TODO: 10/1/16 retrieve recipe info from storage 
    private void initVars(){
        _name = "Toast";

        _ingredients.add("1 Slices of Bread");
        _ingredients.add("1 Tbsp of Jam");

        _steps.add("1) Put bread in toaster");
        _steps.add("2) Toast bread at desired setting");
        _steps.add("3) When done retrieve bread from toaster");
        _steps.add("4) Using a butter knife spread jam on toast");
        
        _tools.add("1 toaster");
        _tools.add("1 butter knife");
        _tools.add("1 plate");

    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.recipe_info));

        //initialize instance variables
        initVars();

        //set text views with recipe info
        TextView text  = (TextView) findViewById(R.id.recipe_name_text);
        text.setText(_name);
        text  = (TextView) findViewById(R.id.recipe_steps_text);
        text.setText(getSteps());
        text  = (TextView) findViewById(R.id.recipe_ingredients_text);
        text.setText(getIngredients());
        text  = (TextView) findViewById(R.id.recipe_tools_text);
        text.setText(getTools());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_recipeinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle toolbar button presses
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //back button pressed, navigate to main task
                onBackPressed();
                break;
            case R.id.toolbar_fav:
                addToFavs();
                break;
            case R.id.toolbar_list:
                addToList();
                break;
            case R.id.toolbar_cook:
                goToCook();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // These functions convert the arraylists of strings to a single string
    private String getSteps(){
        String result = "";

        for(String step:_steps){
            result = result+"\n"+step;
        }

        return result;
    }

    private String getIngredients(){
        String result = "";
        for(String Ingredient : _ingredients ){
            result = result+"\n"+Ingredient;
        }
        return result;
    }
    
    private String getTools(){
        String result = "";
        for(String tool : _tools ){
            result = result+"\n"+tool;
        }
        return result;
    }

    //These functions handle toolbar button presses.
    // TODO: 10/1/16 add to storage
    private void addToList(){
        Toast.makeText(getApplicationContext(),"Recipe Added To Shopping List",Toast.LENGTH_LONG).show();
    }

    private void addToFavs(){
        Toast.makeText(getApplicationContext(),"Recipe Added To Favorites",Toast.LENGTH_LONG).show();
    }

    private void goToCook(){
        //create intent and pass steps
        Intent intent = new Intent(this,Cook.class);
        intent.putExtra("Steps",_steps);
        startActivity(intent);
    }

    /*@Override
    public void onBackPressed() {
        //handle navbar back button press
        NavUtils.navigateUpFromSameTask(this);
    }*/

}
