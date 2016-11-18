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
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.favorites.Favorites;
import letseat.mealdesigner.recipeinfo.RecipeInfo;
import letseat.mealdesigner.recipewalk.RecipeWalk1;
import letseat.mealdesigner.shoppinglist.ShoppingList;
import letseat.mealdesigner.storage.Database;

public class MainRecipe extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
        DeleteDialog.DeleteDialogListener{

    private Database _db;
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;
    private ArrayList<String> _dataset;
    private ArrayList<String> _favs;
    private EditText text;


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

        //View inflatedView = getLayoutInflater().inflate(R.layout.activity_main_recipe_content, null);
        text = (EditText) findViewById(R.id.searchEditText);//inflatedView.findViewById(R.id.searchEditText);

        ArrayList<String> names = ((MealDesignerApp)getApplicationContext()).getDatabase().getListOfRecipes();
        _db = ((MealDesignerApp)getApplicationContext()).getDatabase();

    }

    @Override
    public void onResume(){
        super.onResume();
        _dataset = _db.getListOfRecipes();
        _favs = _db.getListOfFavorites();
        Log.d("status",""+_favs);
        /*ArrayList<String> names = new ArrayList<>();
        names.add("Toast");
        names.add("Toast");
        names.add("Toast");*/
        _recyclerView = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        _recyclerView.setHasFixedSize(true);
        _layoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_layoutManager);
        _adapter = new RecipeAdapter(_dataset,_favs,this);
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

    public void deleteRecipe(String name,int id){
        DeleteDialog deleteDialog = new DeleteDialog();
        Bundle args = new Bundle();
        args.putString("recipe",name);
        args.putInt("id",id);
        deleteDialog.setArguments(args);
        deleteDialog.show(getSupportFragmentManager(),"delete dialog");
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

    public void onButtonPress(boolean delete,int id){
        if(!delete) return;
        Log.d("status","going to delete "+_dataset.get(id));
        boolean result = ((MealDesignerApp)getApplication()).getDatabase().delete(_dataset.get(id));
        Log.d("status","Deleted? "+result);
        _dataset.remove(id);
        _adapter.notifyDataSetChanged();
    }

    public void searchRecipe(View view){

        String searchName = text.getText().toString();

        ArrayList<String> names = ((MealDesignerApp)getApplicationContext()).getDatabase().getListOfRecipes();
        ArrayList<String> parsedNames = new ArrayList<String>();
        for(int i=0;i<names.size();i++){
            if(names.get(i).contains(searchName)) {
                parsedNames.add(names.get(i));
            }
        }
        _adapter = new RecipeAdapter(parsedNames);
        _recyclerView.setAdapter(_adapter);


        Log.d("CHANG","searchName: "+searchName);
    }
}
