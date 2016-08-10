package com.android.ticket.ticket.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ticket.R;
import com.android.ticket.adapter.OrderAdapter;
import com.android.ticket.bean.Order;
import com.android.ticket.bean.Train;
import com.android.ticket.utils.MyServerUrl;
import com.google.gson.Gson;
import com.google.zxing.common.reedsolomon.GenericGF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment implements View.OnClickListener {
    private ListView listView;
    private String url = MyServerUrl.url + "OrderList.jsp";
    private SharedPreferences sp;
    protected String loginfile = "loginfile";

    private TextView tvNOFinishOrder;
    private TextView tvTodayOrder;
    private TextView tvAllOrder;
    private List<Order> orders;

    String ifigfjgf = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_order_list, container, false);
        orders = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.order_lv_id);

        tvAllOrder = (TextView) view.findViewById(R.id.tv_all_order);
        tvNOFinishOrder = (TextView) view.findViewById(R.id.tv_no_finish_order);
        tvTodayOrder = (TextView) view.findViewById(R.id.tv_today_order);

        tvNOFinishOrder.setOnClickListener(this);
        tvTodayOrder.setOnClickListener(this);
        tvAllOrder.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all_order:
                OrderListTask orderListTask = new OrderListTask(getActivity());
                orderListTask.execute("2");
                break;
            case R.id.tv_today_order:

                break;
            case R.id.tv_no_finish_order:

                break;
            default:
                break;

        }
    }

    class OrderListTask extends AsyncTask<String, Void, String> {
        private Context ctx;

        public OrderListTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... params) {

            String url = MyServerUrl.url + "SearchOrder.jsp";
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();
            URL urlConn = null;

            String data = "&username=" + "123"
                    + "&search_type=" + "2";
//SharePreferenceUtil.getString(ctx, Constant.USERNAME, "")
            try {
                urlConn = new URL(url);
                connection = (HttpURLConnection) urlConn.openConnection();
                connection.setRequestMethod("POST");
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(data.getBytes());
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("登录" + result);
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();



            List<Order> ls = new ArrayList<>();
            try {
                JSONArray ja = null;
                ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jobj = ja.getJSONObject(i);
                    int id = jobj.getInt("id");
                    String price = jobj.getString("price");
                    int is_back = jobj.getInt("is_back");
                    String seat = jobj.getString("seat");
                    String from_time = jobj.getString("from_time");
                    String train_num = jobj.getString("train_num");
                    String order_datetime = jobj.getString("order_datetime");
                    String name = jobj.getString("name");
                    int is_pay = jobj.getInt("is_pay");
                    String to_City = jobj.getString("to_City");
                    String from_City = jobj.getString("from_City");

                    Order order1 = new Order();
                    order1.setId(id);
                    order1.setPrice(price);
                    order1.setIs_back(is_back);
                    order1.setSeat(seat);
                    order1.setFrom_time(from_time);
                    order1.setTrain_num(train_num);
                    order1.setOrder_datetime(order_datetime);
                    order1.setName(name);
                    order1.setIs_pay(is_pay);
                    order1.setTo_City(to_City);
                    order1.setFrom_City(from_City);


                    ls.add(order1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listView.setAdapter(new OrderAdapter(getActivity(), ls));

        }

    }
}
