package letseat.mealdesigner.shoppinglist;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import letseat.mealdesigner.R;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import android.widget.TableRow;
import android.widget.Toast;
import android.widget.CheckBox;
import java.util.ArrayList;

import letseat.mealdesigner.recipeinfo.RecipeInfo;

public class ShoppingListEdit extends AppCompatActivity {

    boolean carrotChecked = false;
    boolean tomatoChcked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_edit);

        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.navdrawer_list_edit));
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
        Intent intent = new Intent(this,ShoppingList.class);
        startActivity(intent);
    }

    public void saveEditedShoppingList(View view){
        Toast.makeText(ShoppingListEdit.this, "Saved !!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,ShoppingList.class);
        startActivity(intent);
        //NavUtils.navigateUpFromSameTask(this);
        //Intent intent = new Intent(this,ShoppingListEdit.class);
        //startActivity(intent);
    }

    public void addEditedShoppingList(View view){
        Toast.makeText(ShoppingListEdit.this, "Added !!", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this,ShoppingList.class);
        //startActivity(intent);
        //NavUtils.navigateUpFromSameTask(this);
        //Intent intent = new Intent(this,ShoppingListEdit.class);
        //startActivity(intent);
    }



    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_carrot:
                if (checked){
                    carrotChecked = true;
                }
                // Put some meat on the sandwich
                else{

                }
                // Remove the meat
                break;
            case R.id.checkbox_tomato:
                if (checked){
                    tomatoChcked = true;
                }
                // Cheese me
                else{

                }
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
        }
    }

    public void removeIngredient(View view){
        //for(int i=0; i < removeList.size(); i++){
            //String ingredientName = removeList.get(i);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.shopping_list_edit_layout);

        if(carrotChecked){
            TableRow carrotRow = (TableRow) findViewById(R.id.row_carrot);
            tableLayout.removeView(carrotRow);
        }

        if(tomatoChcked){
            TableRow tomatoRow = (TableRow) findViewById(R.id.row_tomato);
            tableLayout.removeView(tomatoRow);
        }

        Toast.makeText(ShoppingListEdit.this, "Removed!", Toast.LENGTH_SHORT).show();


//            EditText editTextName = (EditText) findViewById(R.id.name_carrot);
//            EditText editTextQuantity = (EditText) findViewById(R.id.quantity_carrot);
//            EditText editTextPrice = (EditText) findViewById(R.id.price_carrot);
//            EditText editTextPlace = (EditText) findViewById(R.id.place_carrot);


//            editTextName.setVisibility(View.GONE);
//            editTextQuantity.setVisibility(View.GONE);
//            editTextPrice.setVisibility(View.GONE);
//            editTextPlace.setVisibility(View.GONE);
//
//            ll.removeView(editTextName);
//            ll.removeView(editTextQuantity);
//            ll.removeView(editTextPrice);
//            ll.removeView(editTextPlace);


            //button.setVisibility(View.GONE);
       // }
    }

}
