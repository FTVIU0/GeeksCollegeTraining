package com.android.ticket.ticket.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ticket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginBtnFragment extends Fragment {


    public LoginBtnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_btn, container, false);

        view.findViewById(R.id.btn_login_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().popBackStackImmediate(null, 1);

                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fl_container, new UserFragment()).commit();
            }
        });
        return view;
    }

}
