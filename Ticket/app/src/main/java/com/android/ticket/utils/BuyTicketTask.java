package com.android.ticket.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.ticket.ticket.fragment.OrderResultActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BuyTicketTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private String data;
    private String mUsername;
    private String mTrainId;
    private String mFromMyCity;
    private String mToMyCity;

    public BuyTicketTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = MyServerUrl.url + "BuyTicket.jsp";

        mUsername = params[0];
        mTrainId = params[1];
        mFromMyCity = params[2];
        mToMyCity = params[3];

        System.out.println(mUsername + "   " + mTrainId + "   " + mFromMyCity + "    " + mToMyCity);

        data = "&username=" + mUsername
                + "&trainId=" + mTrainId
                + "&fromMyCity=" + mFromMyCity
                + "&toMyCity=" + mToMyCity;

        HttpURLConnection connection = null;
        StringBuilder response2 = new StringBuilder();
        URL urlConn = null;
        try {
            urlConn = new URL(url);
            connection = (HttpURLConnection) urlConn.openConnection();
            connection.setRequestMethod("POST");

            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(data.getBytes());
            InputStream in = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            response2.append(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response2.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("--------------" + result);
        Intent it = new Intent(ctx, OrderResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Username", mUsername);
        bundle.putString("TrainId", mFromMyCity);
        bundle.putString("FromMyCity", mToMyCity);
        bundle.putString("ToMyCity", mTrainId);
        it.putExtras(bundle);
        ctx.startActivity(it);
    }

}
