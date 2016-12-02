

package letseat.mealdesigner.recipewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import letseat.mealdesigner.MealDesignerApp;
import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;
import letseat.mealdesigner.storage.Database;
import letseat.mealdesigner.storage.Recipe;

public class RecipeWalk4 extends AppCompatActivity {
    private EditText editText1;
    private TextView textView;
    ArrayList<String> steps  = new ArrayList<>();
    Recipe newRecipe;
    Database x ;
    StringBuilder b = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_walk4);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        x = ((MealDesignerApp) getApplication()).getDatabase();
        newRecipe = x.getTempRecipe();

        //has no toolbar
        editText1 = (EditText) findViewById(R.id.editText7);
        textView = (TextView) findViewById(R.id.textViewSteps);
        textView.setText("");

    }

    public void addStep(View view){
        String step = editText1.getText().toString();
        if (step.matches("")) {
            Toast.makeText(this, "You did not enter a step", Toast.LENGTH_SHORT).show();
        }
        else{
            steps.add(step);
            editText1.setText("");
            b.append(step+"\n");
            textView.setText(b.toString());
        }
    }
    //next step button
    public void step5(View view){
        newRecipe.setSteps(steps);
        x.setTempRecipe(newRecipe);
        Intent intent = new Intent(this,RecipeWalk5.class);
        startActivity(intent);
    }

    //exit button back to main recipe page
    public void exit(View view){
        Intent intent = new Intent(this,MainRecipe.class);
        startActivity(intent);
    }


}
