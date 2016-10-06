package letseat.mealdesigner.cook;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import letseat.mealdesigner.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CookRegularStep.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CookRegularStep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CookRegularStep extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_STEP = "step";

    // TODO: Rename and change types of parameters
    private String mStep;

    private OnFragmentInteractionListener mListener;

    public CookRegularStep() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step step to be outputted.
     * @return A new instance of fragment CookRegularStep.
     */
    public static CookRegularStep newInstance(String step) {
        CookRegularStep fragment = new CookRegularStep();
        Bundle args = new Bundle();
        args.putString(ARG_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStep = getArguments().getString(ARG_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cook_regular_step, container, false);
        TextView output = (TextView) view.findViewById(R.id.cook_step_output);
        output.setText(mStep);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {}
}
