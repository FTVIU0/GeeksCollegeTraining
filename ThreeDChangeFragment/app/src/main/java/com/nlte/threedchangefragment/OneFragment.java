package com.nlte.threedchangefragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        view.findViewById(R.id.btnGoToTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.fragment_two_enter, R.animator.fragment_one_exit)
                        .addToBackStack(null)
                        .replace(R.id.rootView, new TwoFragment())
                        .commit();

            }
        });

        return view;
    }

}
