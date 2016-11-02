package letseat.mealdesigner.recipies;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import letseat.mealdesigner.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<String> _dataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView _textView;
        public ViewHolder(View v) {
            super(v);
            _textView = (TextView) v.findViewById(R.id.recipe_name_text);
        }
    }

    public RecipeAdapter(ArrayList<String> dataset) {
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
