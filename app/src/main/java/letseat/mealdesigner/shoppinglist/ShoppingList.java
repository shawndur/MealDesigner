package letseat.mealdesigner.shoppinglist;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import letseat.mealdesigner.R;
import letseat.mealdesigner.favorites.Favorites;
import letseat.mealdesigner.recipeinfo.RecipeInfo;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.storage.Ingredient;
import letseat.mealdesigner.storage.ShopList;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class ShoppingList extends AppCompatActivity {

    ArrayList<Integer> rowIDs = new ArrayList<>();
    ArrayList<ArrayList<String>> ingredientsList = new ArrayList<ArrayList<String>>();


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);


        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.navdrawer_list));

        /*
        Database x = ((MealDesignerApp) getApplication()).getDatabase();
        ShopList shopList = x.getShopList();
        ArrayList<Ingredient> ingredients = shopList.getIngredients();
        for(Ingredient ingredient: ingredients){
            ArrayList<String> ingredientArray = new ArrayList<String>();
            ingredientArray.add(ingredient.getName());
            ingredientArray.add(ingredient.getAmount());
            ingredientArray.add(ingredient.getPrice());
            ingredientArray.add(ingredient.getStore());
            ingredientsList.add(ingredientArray);
        }
        */
        ArrayList<String> ingredientArray = new ArrayList<String>();
        ingredientArray.add("Bacon");
        ingredientArray.add("8");
        ingredientArray.add("1.5");
        ingredientArray.add("Wegmens");
        ingredientsList.add(ingredientArray);

        TableLayout ll = (TableLayout) findViewById(R.id.shoplist_table);
        showIngredients(ll);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_shoppinglist, menu);
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

    public void openRecipeInfo2() {
        Intent intent = new Intent(this, RecipeInfo.class);
        startActivity(intent);
    }

    public void openRecipeInfo(View view) {
        Intent intent = new Intent(this, RecipeInfo.class);
        startActivity(intent);
    }

    public void openShoppingListEdit(View view) {
        Intent intent = new Intent(this, ShoppingListEdit.class);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ShoppingList Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void showIngredients(TableLayout ll) {
        /*
        ArrayList<String> name = new ArrayList<>();
        name.add("Carrot");
        name.add("Tomato");

        ArrayList<String> quantity = new ArrayList<>();
        quantity.add("5lb");
        quantity.add("3lb");

        ArrayList<Integer> price = new ArrayList<>();
        price.add(50);
        price.add(30);

        ArrayList<String> place = new ArrayList<>();
        place.add("Walmart");
        place.add("Wegmens");
        */

        // Title part
        TableRow titleRow = new TableRow(this);

        TextView titleNameView = new TextView(this);
        titleNameView.setText("Ingredients");
        titleNameView.setGravity(Gravity.CENTER);
        titleNameView.setWidth(350);

        TextView titleQuantityView = new TextView(this);
        titleQuantityView.setText("QTY");
        titleQuantityView.setGravity(Gravity.CENTER);
        titleQuantityView.setWidth(150);

        TextView titlePriceView = new TextView(this);
        titlePriceView.setText("Price");
        titlePriceView.setGravity(Gravity.CENTER);
        titlePriceView.setWidth(150);

        TextView titlePlaceView = new TextView(this);
        titlePlaceView.setText("Market");
        titlePlaceView.setGravity(Gravity.CENTER);
        titlePlaceView.setWidth(400);

        TextView titleRecipeView = new TextView(this);
        titleRecipeView.setText("Recipe");
        titleRecipeView.setGravity(Gravity.CENTER);
        titleRecipeView.setWidth(300);

        titleRow.addView(titleNameView);
        titleRow.addView(titleQuantityView);
        titleRow.addView(titlePriceView);
        titleRow.addView(titlePlaceView);
        titleRow.addView(titleRecipeView);

        ll.addView(titleRow, 0);

        for (int i = 0; i < ingredientsList.size(); i++) {
            TableRow row = new TableRow(this);

            TextView nameView = new TextView(this);
            nameView.setText(ingredientsList.get(i).get(0));
            nameView.setGravity(Gravity.CENTER);
            nameView.setWidth(350);

            TextView quantityView = new TextView(this);
            quantityView.setText(ingredientsList.get(i).get(1));
            quantityView.setGravity(Gravity.CENTER);
            quantityView.setWidth(150);

            TextView priceView = new TextView(this);
            priceView.setText(ingredientsList.get(i).get(2));
            priceView.setGravity(Gravity.CENTER);
            priceView.setWidth(150);

            TextView placeView = new TextView(this);
            placeView.setText(ingredientsList.get(i).get(3));
            placeView.setGravity(Gravity.CENTER);
            placeView.setWidth(400);

            Spinner dropdown = new Spinner(this);
            String[] items = new String[]{"Recipes", "-Loaf"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
            dropdown.setAdapter(adapter);
            dropdown.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if(id != 0){
                        // TODO
                        Toast.makeText(ShoppingList.this, "Spinner: position=" + position + " id=" + id, Toast.LENGTH_SHORT).show();
                        openRecipeInfo2();  // each id represents ordered item in the list.
                    }

                    // TODO
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            row.addView(nameView);
            row.addView(quantityView);
            row.addView(priceView);
            row.addView(placeView);
            row.addView(dropdown);

            ll.addView(row, i + 1);
        }
    }

}
