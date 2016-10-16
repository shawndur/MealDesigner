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
 * This is the Adapter class for the Recycler view used in recipe info.
 * This is used to create and modify the views for the recipe components
 */

class RecipeInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TITLE = 0; //Value for title type view
    private static final int ITEM = 1; //Value for item type view
    private Map<String,List<String>> _dataset; //holds names and values of components
    private String _name;

    /**
     * holds and maintains references to title/header views
     */
    private static class ViewHolderHeader extends RecyclerView.ViewHolder {
        TextView _title;
        /**
         * Saves references to the views and subviews
         * @param v view to be referenced
         */
        ViewHolderHeader(View v) {
            super(v);
            _title = (TextView) itemView.findViewById(R.id.recipe_name_text);
        }
    }

    /**
     * Holds and maintains references to regular views
     */
    private static class ViewHolderItem extends RecyclerView.ViewHolder {
        private TextView _header,_text; //textviews used to display recipe info
        /**
         * Saves references to the views and subviews
         * @param v view to be referenced
         */
        ViewHolderItem(View v) {
            super(v);
            _header = (TextView) v.findViewById(R.id.info_item_header);
            _text = (TextView) v.findViewById(R.id.info_item_text);
        }

        /**
         * Sets the header for the recipe component
         * @param header String value of the header
         */
        void setHeader(String header){
            _header.setText(header);
        }

        /**
         * Sets the text of the recipe component
         * @param text String value of the text
         */
        void setText(List<String> text){
            _text.setText("");
            for(String s : text){
                _text.append(s + '\n');
            }
        }
    }

    /**
     * Constructor for the adapter, stores the dataset
     * @param name Name to be used ase title
     * @param myDataset Dataset to be used for the components
     */
    RecipeInfoAdapter(String name,Map<String,List<String>> myDataset) {
        _name = name;
        _dataset = myDataset;
    }

    /**
     * Called by the layout manager to create a view
     * @param parent ViewGroup to where the view is inflated
     * @param viewType Type of the new view
     * @return A newly created view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;
        if(viewType == ITEM) { // Create item view
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_recipe_info_item, parent, false);
            return new ViewHolderItem(v);
        }else{ //create title/header view
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_recipe_info_header, parent, false);
            return new ViewHolderHeader(v);
        }
    }

    /**
     * Called by the layout manager to set/update views
     * @param holder View holder holding the views to be modified
     * @param position Position in the list/dataset
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){ //Title view, set title
            ((ViewHolderHeader)holder)._title.setText(_name);
            return;
        }
        //get iterator at position i
        Iterator<Map.Entry<String,List<String>>> it = _dataset.entrySet().iterator();
        for(int i=0;i<position && it.hasNext();++i ) it.next();

        if(it.hasNext()) {
            //get key value pair at position and update in viewholder
            Map.Entry<String, List<String>> entry = it.next();
            ((ViewHolderItem)holder).setHeader(entry.getKey());
            ((ViewHolderItem)holder).setText(entry.getValue());
        }
    }

    /**
     * Calle by layout manager to vet the type of layout at a position
     * @param position Position of the item
     * @return the type of view
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TITLE;
        return ITEM;
    }

    /**
     * Used by the layout manager to get the size of the recycler view
     * @return The size of the dataset + the header
     */
    @Override
    public int getItemCount() {
        return _dataset.size() + 1 ;
    }

}
