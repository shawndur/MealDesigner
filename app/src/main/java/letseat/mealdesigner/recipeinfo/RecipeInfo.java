package letseat.mealdesigner.recipeinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.cook.Cook;
import letseat.mealdesigner.long_term_memory.ListComponent;
import letseat.mealdesigner.long_term_memory.Long_Term_Interface;
import letseat.mealdesigner.long_term_memory.RecipeHead;

public class RecipeInfo extends AppCompatActivity {

    ArrayList<String> _steps;
    String _name ;
    Long_Term_Interface _LTI;
    RecipeHead _recipe;

    
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
        if(_name.length()==0){
            // TODO: 10/14/16 Dont throw exception do something "nicer"
            throw new RuntimeException("No Recipe Name Found or Supplied");
        }

        _LTI = ((MealDesignerApp) getApplication()).getLTI();
        _recipe = _LTI.parseLineToRecipe( _LTI.getLinesFromFile("").get(0) );

        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        String [] s = {"a","b","c"};
        mAdapter = new RecipeInfoAdapter(s);
        mRecyclerView.setAdapter(mAdapter);

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
