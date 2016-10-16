package letseat.mealdesigner.recipeinfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import letseat.mealdesigner.R;

/**
 * Created by shawn on 10/14/16.
 */

class RecipeInfoAdapter extends RecyclerView.Adapter<RecipeInfoAdapter.ViewHolder> {

    private Map<String,List<String>> _dataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView _header,_text;
        ViewHolder(View v) {
            super(v);
            _header = (TextView) v.findViewById(R.id.info_item_header);
            _text = (TextView) v.findViewById(R.id.info_item_text);
        }

        void setHeader(String header){
            _header.setText(header);
        }

        void setText(List<String> text){
            _text.setText("");
            for(String s : text){
                _text.append(s);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    RecipeInfoAdapter(Map<String,List<String>> myDataset) {
        _dataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recipe_info_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Iterator<Map.Entry<String,List<String>>> it = _dataset.entrySet().iterator();
        for(int i=0;i<position && it.hasNext();++i ) it.next();

        if(it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            holder.setHeader(entry.getKey());
            holder.setText(entry.getValue());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return _dataset.size();
    }

}
