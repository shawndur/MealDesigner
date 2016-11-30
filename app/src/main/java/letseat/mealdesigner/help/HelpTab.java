package letseat.mealdesigner.help;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;

/**
 * Created by Christina on 11/30/2016.
 */

public class HelpTab extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.help));
        }



    }
    @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_help, menu);
        return true;
  }
  //  @Override
  //  public boolean onOptionsItemSelected(MenuItem item) {
  //      //handle toolbar button presses
  //      switch (item.getItemId()) {
  //          // Respond to the action bar's Up/Home button
  //          case android.R.id.home:
  //              //back button pressed, navigate to main task
  //              NavUtils.navigateUpFromSameTask(this);
  //              return true;
   //     }
   //     return super.onOptionsItemSelected(item);
   // }

}
