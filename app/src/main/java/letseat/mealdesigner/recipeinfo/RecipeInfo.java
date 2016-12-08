package letseat.mealdesigner.recipeinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.cook.Cook;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Ingredient;
import letseat.mealdesigner.storage.Recipe;
import letseat.mealdesigner.storage.ShopList;

public class RecipeInfo extends AppCompatActivity {

    private Database _db;
    private LinkedHashMap<String,List<String>> _dataset;
    private String _name;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.recipe_info));
        }

        Intent intent = getIntent();
        _name = intent.getStringExtra("recipe_name");
        if(_name == null){
            Toast.makeText(getApplicationContext(),"No Recipe Found",Toast.LENGTH_LONG).show();
            onBackPressed();
        }

        _db = ((MealDesignerApp) getApplication()).getDatabase();
        Recipe recipe = _db.getRecipe(_name);
        _dataset = new LinkedHashMap<>();

        if(recipe == null){
            Toast.makeText(getApplicationContext(),"Error No/Empty File",Toast.LENGTH_LONG).show();
            onBackPressed();
        }

        ArrayList<String> data = recipe.getAllergens();
        if(data.size()>0)_dataset.put("Allergens",data);
        data = recipe.getIngredients();
        if(data.size()>0)_dataset.put("Ingredients",data);
        data = recipe.getTools();
        if(data.size()>0)_dataset.put("Tools",data);
        data = recipe.getSteps();
        if(data.size()>0)_dataset.put("Steps",data);

        Log.d("status", "dataset= "+_dataset);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeInfoAdapter(_name,_dataset));
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
            /*case R.id.toolbar_list:
                addToList();
                break;*/
            case R.id.toolbar_cook:
                goToCook();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //These functions handle toolbar button presses.
    private void addToList(){
        if(!_dataset.containsKey("Ingredients")){
            Toast.makeText(getApplicationContext(),"No Ingredients",Toast.LENGTH_LONG).show();
            return;
        }

        ShopList list = _db.getShopList();
        ArrayList<Ingredient> ingredients = list.getIngredients();

        Toast.makeText(getApplicationContext(),"Recipe Added To Shopping List",Toast.LENGTH_LONG).show();
    }

    private void addToFavs(){
        _db.setFavorite(_name,true);
        Toast.makeText(getApplicationContext(),"Recipe Added To Favorites",Toast.LENGTH_LONG).show();
    }

    private void goToCook(){
        if(_dataset.containsKey("Steps")) {
            //create intent and pass steps
            Intent intent = new Intent(this, Cook.class);
            intent.putExtra("Steps", (ArrayList<String>)_dataset.get("Steps"));
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"No Steps Exist",Toast.LENGTH_LONG).show();
        }
    }

    /*@Override
    public void onBackPressed() {
        //handle navbar back button press
        NavUtils.navigateUpFromSameTask(this);
    }*/

}
