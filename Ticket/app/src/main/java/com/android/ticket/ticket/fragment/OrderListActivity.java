package com.android.ticket.ticket.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.ticket.R;
import com.android.ticket.utils.MyServerUrl;


import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.SimpleAdapter;


public class OrderListActivity extends Activity {

    private ListView listView;
    private String url = MyServerUrl.url + "OrderList.jsp";
    private SharedPreferences sp;
    protected String loginfile = "loginfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        sp = getSharedPreferences("loginfile", Context.MODE_PRIVATE);
        String name = sp.getString("username", "");
        initUI();
        if (name.equals("")) {
        } else {
        }
    }

    private void initUI() {


        listView = (ListView) findViewById(R.id.order_lv_id);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        final String[] str = {"全部订单", "未出行订单", "历史订单", "已退订单"};
        for (int i = 0; i < str.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("item", str[i]);
            data.add(map);
        }

        SimpleAdapter sa = new SimpleAdapter(this,
                data,
                R.layout.order_info_item,
                new String[]{"item"},
                new int[]{R.id.order_info_item_id});
        listView.setAdapter(sa);
    }


}
