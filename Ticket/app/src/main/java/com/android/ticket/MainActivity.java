package com.android.ticket;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.ticket.ticket.fragment.LoginBtnFragment;
import com.android.ticket.ticket.fragment.OrderFragment;
import com.android.ticket.ticket.fragment.PersonCenterFragment;
import com.android.ticket.ticket.fragment.ReserveFragment;
import com.android.ticket.ticket.fragment.UserFragment;
import com.android.ticket.utils.Constant;
import com.android.ticket.utils.SharePreferenceUtil;

public class MainActivity extends Activity implements OnClickListener {

    private TextView tvReserve;
    private TextView tvOrder;
    private TextView tvUser;
    private SharedPreferences sp;
    private String loginfile = "loginfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        getFragmentManager().beginTransaction()
                .add(R.id.fl_container, new ReserveFragment()).commit();

        tvReserve = (TextView) findViewById(R.id.tv_reserve);
        tvOrder = (TextView) findViewById(R.id.tv_order);
        tvUser = (TextView) findViewById(R.id.tv_user);

        tvReserve.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        tvUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reserve:
               // getFragmentManager().popBackStackImmediate(null, 1);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, new ReserveFragment(), Constant.RESERVE_FRAGMENT).commit();
                break;
            case R.id.tv_order:


                getFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, new OrderFragment(), Constant.ORDER_FRAGMENT).commit();
                getFragmentManager().popBackStackImmediate(null, 1);
                break;
            case R.id.tv_user:
                if (SharePreferenceUtil.getBoolean(this, Constant.ISLOGIN, false)){
                    //getFragmentManager().popBackStackImmediate(null, 1);

                    getFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, new PersonCenterFragment(), Constant.PERSON_CENTER_FRAGMENT).commit();
                }else {
                    //getFragmentManager().popBackStackImmediate(null, 1);

                    getFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, new UserFragment(), Constant.USER_FRAGMENT).commit();
                }
                break;

            default:
                break;
        }
    }
}
