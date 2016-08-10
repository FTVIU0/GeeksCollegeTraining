package com.android.ticket.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OrderListTask extends AsyncTask<String, Void, String>{
	private Context ctx;
	public OrderListTask(Context ctx){
		this.ctx = ctx;
	}

	@Override
	protected String doInBackground(String... params) {

		String url = MyServerUrl.url+"SearchOrder.jsp";
		HttpURLConnection connection = null;
		StringBuilder response = new StringBuilder();
		URL urlConn = null;

		String  data = "&username=" + "123"
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
		System.out.println("登录"+result);
		Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
		
	}
    
}
