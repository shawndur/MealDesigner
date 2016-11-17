package letseat.mealdesigner.favorites;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import letseat.mealdesigner.R;
import letseat.mealdesigner.recipies.MainRecipe;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private ArrayList<String> _favs;
    private Favorites _recipeList;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView _textView;
        public ToggleButton _fav;
        public int _id;
        public Favorites _recipeList;

        public ViewHolder(View v, Favorites recipeList) {
            super(v);
            _recipeList = recipeList;
            _textView = (TextView) v.findViewById(R.id.recipe_name_text);
            _fav = (ToggleButton) v.findViewById(R.id.favorite_button);
            _textView.setOnClickListener(this);
            _fav.setOnClickListener(this);
            _fav.setChecked(true);
        }

        public void bind(ArrayList<String> favs,int id){
            _id = id;
            _textView.setText(favs.get(id));
        }

        public void onClick(View view){
            if(view == _textView){
                _recipeList.openRecipeInfo(_textView.getText().toString());
            }else if(view == _fav){
                Log.d("status","favorite button pressed for "+_id+_textView.getText().toString());
                _recipeList.favoriteRecipe(_textView.getText().toString());
            }
        }
    }

    public FavAdapter(ArrayList<String> favs, Favorites recipelist) {
        _recipeList = recipelist;
        _favs = favs;
    }


    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_favorites_item, parent, false);

        ViewHolder vh = new ViewHolder(v,_recipeList);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(_favs,position);
    }

    @Override
    public int getItemCount() {
        return _favs.size();
    }
}
