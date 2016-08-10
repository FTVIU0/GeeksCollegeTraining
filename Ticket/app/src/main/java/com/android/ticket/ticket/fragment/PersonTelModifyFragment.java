package com.android.ticket.ticket.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.ticket.R;
import com.android.ticket.utils.MyServerUrl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.android.ticket.ticket.fragment.UserFragment.LOGIN_RESPONSE;

public class PersonTelModifyFragment extends Fragment implements View.OnClickListener {
	
	private EditText oldTelTxt;
	private EditText newTelTxt;
	private Button saveBtn;
	private String loginfile = "loginfile";
	private SharedPreferences sp;
	private String username;
	private Handler handler;
	private ProgressDialog pd;
	private String url = MyServerUrl.url + "PersonTelUpdate.jsp";
	private StringBuilder response;
	private String datas;
	private Button cancelTelBtn;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_person_tel_update, container, false);
		
		oldTelTxt = (EditText) view.findViewById(R.id.et_Telmodify_oldTel);
		newTelTxt = (EditText) view.findViewById(R.id.et_Telmodify_newTel);
		saveBtn = (Button) view.findViewById(R.id.bt_modifyTel);
		cancelTelBtn = (Button) view.findViewById(R.id.bt_canclemodifyTel);
		
		sp = getActivity().getSharedPreferences(loginfile ,Context.MODE_PRIVATE);
		String tel = sp.getString("tel", "");
		username = sp.getString("username", "");
		oldTelTxt.setText(tel);

		saveBtn.setOnClickListener(this);
		cancelTelBtn.setOnClickListener(this);

		handler = new Handler(){

			public void handleMessage(Message msg){
				pd.dismiss();
				if(response.toString().equals("success")){
					Editor editor = sp.edit();
					editor.putString("Tel", newTelTxt.getEditableText().toString());
					editor.commit();
					Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
					getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.fl_container, new PersonInfoModifyFragment()).commit();
				}else if(response.toString().equals("error")){
					Toast.makeText(getActivity(), "原号码输入错误", Toast.LENGTH_SHORT).show();
				}
			}
		};
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.bt_modifyTel:
				pd = new ProgressDialog(getActivity());
				pd.setMessage("保存中，请稍后...");
				pd.setCancelable(false);
				pd.show();
				sendRequest();
				break;
			case R.id.bt_canclemodifyTel:
				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.fl_container, new PersonInfoModifyFragment()).commit();
				break;
		}



	}

	private void sendRequest() {

		datas = "&username=" + username + "&tel=" + newTelTxt.getEditableText().toString();

		new Thread(new Runnable() {

			public void run() {
				HttpURLConnection connection = null;
				response = new StringBuilder();
				URL urlConn = null;
				try {
					urlConn = new URL(url);
					connection = (HttpURLConnection) urlConn.openConnection();
					connection.setRequestMethod("POST");
					DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
					dataOutputStream.write(datas.getBytes());
					InputStream in = connection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						response.append(line);
					}
					Message message = new Message();
					message.what = LOGIN_RESPONSE;
					message.obj = response.toString();
					handler.sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}


