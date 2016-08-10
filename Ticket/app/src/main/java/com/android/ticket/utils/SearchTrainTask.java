package com.android.ticket.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.android.ticket.bean.Train;
import com.android.ticket.ticket.fragment.SearchTrainListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SearchTrainTask extends AsyncTask<String, Void, String> {
    private Context ctx;

    public SearchTrainTask(Context ctx) {
        this.ctx = ctx;
    }

    private String data;

    @Override
    protected String doInBackground(String... params) {

        String fromCity = params[0];
        String toCity = params[1];
        String fromDate = params[2];
        String fromTime = params[3];


        data = "&fromCity=" + fromCity
                + "&toCity=" + toCity
                + "&fromDate=" + fromDate
                + "&fromTime=" + fromTime;


        String url = MyServerUrl.url + "SearchTicket.jsp";
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        URL urlConn = null;
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
        System.out.println("response.toString" + response.toString());
        return response.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("+++++++++++++"+result);

//        Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

        List<Train> ls = new ArrayList<>();
        try {
            JSONArray ja = null;
            ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jobj = ja.getJSONObject(i);
                int id = jobj.getInt("id");
                String num = jobj.getString("num");
                String start_date = jobj.getString("start_date");
                String start_time = jobj.getString("start_time");
                String from_city = jobj.getString("from_city");
                String to_city = jobj.getString("to_city");
                int seat_number = jobj.getInt("seat_number");

                Train train = new Train();
                train.setId(id);
                train.setNum(num);
                train.setStart_date(start_date);
                train.setStart_time(start_time);
                train.setFrom_city(from_city);
                train.setTo_city(to_city);
                train.setSeat_number(seat_number);
                ls.add(train);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent it = new Intent(ctx, SearchTrainListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("trainlist", (Serializable) ls);
        it.putExtras(bundle);
        ctx.startActivity(it);
    }

}
