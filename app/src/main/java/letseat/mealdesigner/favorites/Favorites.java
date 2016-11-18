package letseat.mealdesigner.favorites;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipeinfo.RecipeInfo;
import letseat.mealdesigner.recipies.RecipeAdapter;
import letseat.mealdesigner.storage.Database;

public class Favorites extends AppCompatActivity {

    private Database _db;
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;
    private ArrayList<String> _favs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.navdrawer_fav));

        _db = ((MealDesignerApp)getApplicationContext()).getDatabase();
        _favs = _db.getListOfFavorites();
        _recyclerView = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        _recyclerView.setHasFixedSize(true);
        _layoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_layoutManager);
        _adapter = new FavAdapter(_favs,this);
        _recyclerView.setAdapter(_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle toolbar button presses
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //back button pressed, navigate to main task
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //handle navbar back button press
        NavUtils.navigateUpFromSameTask(this);
    }

    public void openRecipeInfo(String name){
        Intent intent = new Intent(this,RecipeInfo.class);
        intent.putExtra("recipe_name",name);
        startActivity(intent);
    }

    public void favoriteRecipe(String name){
        if(_favs.contains(name)){
            Log.d("status",name+" is favorited");
            boolean result = _db.setFavorite(name,false);
            Log.d("status","unfavorited? "+result);
            _favs.remove(name);
        }else{
            Log.d("status",name+" is not favorited");
            boolean result = _db.setFavorite(name,true);
            Log.d("status","favorited? "+result);
            _favs.add(name);
        }
    }
}
