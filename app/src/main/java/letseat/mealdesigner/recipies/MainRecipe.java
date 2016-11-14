package letseat.mealdesigner.recipies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.favorites.Favorites;
import letseat.mealdesigner.recipeinfo.RecipeInfo;
import letseat.mealdesigner.recipewalk.RecipeWalk1;
import letseat.mealdesigner.shoppinglist.ShoppingList;

public class MainRecipe extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe);
        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.navdrawer_recipes));
        //set up nav drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<String> names = ((MealDesignerApp)getApplicationContext()).getDatabase().getListOfRecipes();
        /*ArrayList<String> names = new ArrayList<>();
        names.add("Toast");
        names.add("Toast");
        names.add("Toast");*/
        _recyclerView = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        _recyclerView.setHasFixedSize(true);
        _layoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_layoutManager);
        _adapter = new RecipeAdapter(names,this);
        _recyclerView.setAdapter(_adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //close drawer if open
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_main_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.toolbar_add_recipe:
                createRecipe(getCurrentFocus());
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_list:
                intent = new Intent(this,ShoppingList.class);
                startActivity(intent);
                break;
            case R.id.nav_recipe:
                break;
            case R.id.nav_favorite:
                intent = new Intent(this,Favorites.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void openRecipeInfo(String name){
        Intent intent = new Intent(this,RecipeInfo.class);
        intent.putExtra("recipe_name",name);
        startActivity(intent);
    }
    public void createRecipe(View view){
        Intent intent = new Intent(this, RecipeWalk1.class);
        startActivity(intent);
    }
}
