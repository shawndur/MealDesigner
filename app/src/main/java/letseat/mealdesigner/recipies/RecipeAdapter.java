package letseat.mealdesigner.recipies;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import letseat.mealdesigner.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<String> _dataset;
    private MainRecipe _recipeList;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView _textView;
        public ToggleButton _fav;
        public Button _delete;
        public int _id;

        public ViewHolder(View v) {
            super(v);
            _textView = (TextView) v.findViewById(R.id.recipe_name_text);
            _fav = (ToggleButton) v.findViewById(R.id.favorite_button);
            _delete = (Button) v.findViewById(R.id.delete_button);
            _textView.setOnClickListener(this);
            _fav.setOnClickListener(this);
            _delete.setOnClickListener(this);
        }

        public void bind(ArrayList<String> dataset, int id){
            _id = id;
            _textView.setText(dataset.get(id));
        }

        public void onClick(View view){
            if(view == _textView){

            }else if(view == _fav){

            }else if(view == _delete){

            }
        }
    }

    public RecipeAdapter(ArrayList<String> dataset, MainRecipe recipelist) {
        _recipeList = recipelist;
        _dataset = dataset;
    }


    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_recipe_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._textView.setText(_dataset.get(position));

    }

    @Override
    public int getItemCount() {
        return _dataset.size();
    }
}
