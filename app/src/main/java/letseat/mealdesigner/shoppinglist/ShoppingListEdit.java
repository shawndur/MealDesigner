package letseat.mealdesigner.shoppinglist;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;

import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import letseat.mealdesigner.recipeinfo.RecipeInfo;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Ingredient;
import letseat.mealdesigner.storage.ShopList;

public class ShoppingListEdit extends AppCompatActivity {

    ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();
    Database x ;//= ((MealDesignerApp) getApplication()).getDatabase();
    ShopList shopList;// = x.getShopList();
    ArrayList<Ingredient> ingredients ;//= shopList.getIngredients();



    // id : key = checkBox ID, value = table row ID
    HashMap<Integer, Integer> id = new HashMap<Integer, Integer>();
    // checkBox: key = checkBox ID, value = if it is checked or not
    HashMap<Integer, Boolean> boxChecked = new HashMap<Integer, Boolean>();

    boolean carrotChecked = false;
    boolean tomatoChcked = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_edit);
        x = ((MealDesignerApp) getApplication()).getDatabase();
        shopList = x.getShopList();
        ingredients = shopList.getIngredients();
        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.navdrawer_list_edit));


        /*
        ArrayList<String> ingredientArray = new ArrayList<String>();
        ingredientArray.add("Bacon");
        ingredientArray.add("8");
        ingredientArray.add("1.5");
        ingredientArray.add("Wegmens");
        ingredientsList.add(ingredientArray);
        */

        TableLayout ll = (TableLayout) findViewById(R.id.shoplist_edit_table);
        showEditIngredients(ll);
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
        //NavUtils.navigateUpFromSameTask(this);
        Intent intent = new Intent(this, ShoppingList.class);
        startActivity(intent);
    }

    public void saveEditedShoppingList(View view) {
        ingredientsList.clear();

        TableLayout ll = (TableLayout) findViewById(R.id.shoplist_edit_table);
        for(int i =1; i < ll.getChildCount();i++){
            TableRow row = (TableRow) ll.getChildAt(i);
            String name = ((EditText) row.getChildAt(1)).getText().toString();
            String qty = ((EditText) row.getChildAt(2)).getText().toString();
            String price = ((EditText) row.getChildAt(3)).getText().toString();
            String place = ((EditText) row.getChildAt(4)).getText().toString();


            Ingredient tempIngredient = shopList.newIngredient();
            tempIngredient.setName(name);
            tempIngredient.setAmount(qty);
            tempIngredient.setPrice(price);
            tempIngredient.setStore(place);
            ingredientsList.add(tempIngredient);

        }

        shopList.setIngredients(ingredientsList);
        x.setShopList(shopList);


        Toast.makeText(ShoppingListEdit.this, "Saved !!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ShoppingList.class);
        startActivity(intent);

    }

    public void addEditedShoppingList(View view) {
        Toast.makeText(ShoppingListEdit.this, "Added !!", Toast.LENGTH_SHORT).show();
        TableLayout ll = (TableLayout) findViewById(R.id.shoplist_edit_table);
        TableRow row = new TableRow(this);
        int rowID = findUnusedId();
        row.setId(rowID);

        final CheckBox checkBox = new CheckBox(this);
        final int checkID = findUnusedId();
        checkBox.setId(checkID);
        checkBox.setWidth(100);
        id.put(checkID,rowID);
        boxChecked.put(checkID,false);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(true);
                boxChecked.put(checkID,true);

                Log.d("Test","Checkbox?? " + Boolean.toString(checkBox.isChecked()));
            }
        });



        EditText nameView = new EditText(this);
        nameView.setText("");
        nameView.setGravity(Gravity.CENTER);
        nameView.setWidth(400);

        EditText quantityView = new EditText(this);
        quantityView.setText("");
        quantityView.setGravity(Gravity.CENTER);
        quantityView.setWidth(200);

        EditText priceView = new EditText(this);
        priceView.setText("");
        priceView.setGravity(Gravity.CENTER);
        priceView.setWidth(200);

        EditText placeView = new EditText(this);
        placeView.setText("");
        placeView.setGravity(Gravity.CENTER);
        placeView.setWidth(500);

        row.addView(checkBox);
        row.addView(nameView);
        row.addView(quantityView);
        row.addView(priceView);
        row.addView(placeView);

        ll.addView(row, id.size());

    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        for(int checkBoxID:id.keySet()) {
            if (view.getId() == checkBoxID) {
                if (checked) {
                    boxChecked.put(checkBoxID, true);
                }
                // Put some meat on the sandwich
                else {
                    boxChecked.put(checkBoxID, false);
                }
            }
        }

    }

    public void removeIngredient(View view) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.shoplist_edit_table);

        for(int checkBoxID : boxChecked.keySet()){
            Log.d("Test","Checkbox ID: "+Integer.toString(checkBoxID));
            Log.d("Test","Checked: "+boxChecked.get(checkBoxID));
            if(boxChecked.get(checkBoxID)){
                tableLayout.removeView(findViewById(id.get(checkBoxID)));
            }
        }

        Toast.makeText(ShoppingListEdit.this, "Removed!", Toast.LENGTH_SHORT).show();
    }

    public void showEditIngredients(TableLayout ll) {

        TableRow titleRow = new TableRow(this);

        TextView titleCheckView = new TextView(this);
        titleCheckView.setText("");
        titleCheckView.setGravity(Gravity.CENTER);
        titleCheckView.setWidth(100);

        TextView titleNameView = new TextView(this);
        titleNameView.setText("Ingredients");
        titleNameView.setGravity(Gravity.CENTER);
        titleNameView.setWidth(400);

        TextView titleQuantityView = new TextView(this);
        titleQuantityView.setText("QTY");
        titleQuantityView.setGravity(Gravity.CENTER);
        titleQuantityView.setWidth(200);

        TextView titlePriceView = new TextView(this);
        titlePriceView.setText("Price");
        titlePriceView.setGravity(Gravity.CENTER);
        titlePriceView.setWidth(200);

        TextView titlePlaceView = new TextView(this);
        titlePlaceView.setText("Market");
        titlePlaceView.setGravity(Gravity.CENTER);
        titlePlaceView.setWidth(500);

        titleRow.addView(titleCheckView);
        titleRow.addView(titleNameView);
        titleRow.addView(titleQuantityView);
        titleRow.addView(titlePriceView);
        titleRow.addView(titlePlaceView);


        ll.addView(titleRow, 0);

        for (int i = 0; i < ingredientsList.size(); i++) {
            Ingredient currentIngredient = ingredientsList.get(i);
            TableRow row = new TableRow(this);
            int rowID = findUnusedId();
            row.setId(rowID);

            final CheckBox checkBox = new CheckBox(this);
            final int checkID = findUnusedId();
            checkBox.setId(checkID);
            checkBox.setWidth(100);
            id.put(checkID,rowID);
            boxChecked.put(checkID,false);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(true);
                    boxChecked.put(checkID,true);

                    Log.d("Test","Checkbox?? " + Boolean.toString(checkBox.isChecked()));
                }
            });

            // Name
            EditText nameView = new EditText(this);
            nameView.setText(currentIngredient.getName());
            nameView.setGravity(Gravity.CENTER);
            nameView.setWidth(400);

            // Amount
            EditText quantityView = new EditText(this);
            quantityView.setText(currentIngredient.getAmount());
            quantityView.setGravity(Gravity.CENTER);
            quantityView.setWidth(200);

            // Price
            EditText priceView = new EditText(this);
            priceView.setText(currentIngredient.getPrice());
            priceView.setGravity(Gravity.CENTER);
            priceView.setWidth(200);

            // Store
            EditText placeView = new EditText(this);
            placeView.setText(currentIngredient.getStore());
            placeView.setGravity(Gravity.CENTER);
            placeView.setWidth(500);

            row.addView(checkBox);
            row.addView(nameView);
            row.addView(quantityView);
            row.addView(priceView);
            row.addView(placeView);

            ll.addView(row, i + 1);
        }
    }

    public int findUnusedId() {
        int IDnum = 0;
        while (findViewById(++IDnum) != null) ;
        return IDnum;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ShoppingListEdit Page") // TODO: Define a title for the content shown.
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
}
