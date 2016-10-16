package letseat.mealdesigner.recipeinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.cook.Cook;
import letseat.mealdesigner.long_term_memory.Long_Term_Interface;
import letseat.mealdesigner.long_term_memory.RecipeHead;

public class RecipeInfo extends AppCompatActivity {

    private Long_Term_Interface _LTI;
    //RecipeHead _recipe;
    private LinkedHashMap<String,List<String>> _dataset;

    
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
        String name = intent.getStringExtra("recipe_name");
        if(name == null){
            // TODO: 10/14/16 Handle null name
            /*
            throw new RuntimeException("No Recipe Name Found or Supplied");
            //*/
            /*
            Toast.makeText(getApplicationContext(),"No Recipe Found",Toast.LENGTH_LONG).show();
            onBackPressed();
            //*/
            //*
            name = "Toast";
            //*/
        }

        _LTI = ((MealDesignerApp) getApplication()).getLTI();
        ArrayList<String> lines = _LTI.getLinesFromFile(name);
        if(lines.isEmpty()){
            // TODO: 10/15/16 handle empty file
            /*
            throw new RuntimeException("Empty or Nonexistent File For: "+_name);
            //*/
            /*
            Toast.makeText(getApplicationContext(),"Error No/Empty File",Toast.LENGTH_LONG).show();
            onBackPressed();
            //*/
            //*
            _dataset = new LinkedHashMap<>();

            String key = "Ingredients";
            ArrayList<String> data = new ArrayList<>();
            data.add("1 Tbsp of Jam");
            data.add("1 Slices of Bread");
            _dataset.put(key,data);

            key = "Tools";
            data = new ArrayList<>();
            data.add("1 toaster");
            data.add("1 butter knife");
            data.add("1 plate");
            _dataset.put(key,data);

            key = "Steps";
            data = new ArrayList<>();
            data.add("1) Put bread in toaster");
            data.add("2) Toast bread at desired setting");
            data.add("3) When done retrieve bread from toaster");
            data.add("4) Using a butter knife spread jam on toast");
            _dataset.put(key,data);
            //*/
        }else{
            // TODO: 10/16/16 Retrieve from mem
            RecipeHead recipe = _LTI.parseLineToRecipe(lines.get(0));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeInfoAdapter(name,_dataset));
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

    //These functions handle toolbar button presses.
    // TODO: 10/1/16 add to storage
    private void addToList(){
        Toast.makeText(getApplicationContext(),"Recipe Added To Shopping List",Toast.LENGTH_LONG).show();
    }

    private void addToFavs(){
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
