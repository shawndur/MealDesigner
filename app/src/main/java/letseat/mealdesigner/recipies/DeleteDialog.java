package letseat.mealdesigner.recipies;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DeleteDialog extends DialogFragment {

    DeleteDialogListener _listener;
    String _recipeName;

    public interface DeleteDialogListener{
        void onButtonPress(boolean delete,String name);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _recipeName = getArguments().getString("recipe");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete " +_recipeName+ " Recipe?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _listener.onButtonPress(true,_recipeName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _listener.onButtonPress(false,_recipeName);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        _listener = (DeleteDialogListener) getActivity();
    }
}
