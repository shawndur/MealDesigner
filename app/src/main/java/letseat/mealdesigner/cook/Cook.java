package letseat.mealdesigner.cook;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import letseat.mealdesigner.R;

public class Cook extends AppCompatActivity
        implements CookRegularStep.OnFragmentInteractionListener {

    private ArrayList<String> _steps;
    private int currentStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        //set toolbar widget as action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.navdrawer_cook));

        Intent intent = getIntent();
        _steps = intent.getStringArrayListExtra("Steps");

        //skip if being restored
        if (savedInstanceState != null) {
            return;
        }

        //create fragment
        CookRegularStep firstFragment = CookRegularStep.newInstance(_steps.get(currentStep));

        // Add fragment to FrameLayout
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_cook, menu);
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void prevStep(View view){
        if(currentStep == 0) return;
        --currentStep;
        onBackPressed();
    }

    public void nextStep(View view){
        if(currentStep == _steps.size()-1) return;
        ++currentStep;
        //create fragment
        CookRegularStep fragment = CookRegularStep.newInstance(_steps.get(currentStep));

        //get transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //replace fragment and add to backstack
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        //commit changes
        transaction.commit();
    }
    // TODO: 10/5/16 fix back button presses
    /*@Override
    public void onBackPressed() {
        //handle navbar back button press
        NavUtils.navigateUpFromSameTask(this);
    }*/
}
