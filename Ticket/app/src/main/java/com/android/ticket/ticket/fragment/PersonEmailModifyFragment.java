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


public class PersonEmailModifyFragment extends Fragment implements View.OnClickListener {

    private EditText oldEmailTxt;
    private EditText newEmailTxt;
    private Button saveBtn;
    private String loginfile = "loginfile";
    private SharedPreferences sp;
    private String username;
    private Handler handler;
    private ProgressDialog pd;
    public String url = MyServerUrl.url + "PersonEmailUpdate.jsp";
    private StringBuilder response;
    private String datas;
    private String oldEmail;
    private Button calcelEmailBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_person_email_update, container, false);

        oldEmailTxt = (EditText) view.findViewById(R.id.et_emailmodify_oldEmail);
        newEmailTxt = (EditText) view.findViewById(R.id.et_Emailmodify_newEmail);
        saveBtn = (Button) view.findViewById(R.id.bt_modifyEmail);
        calcelEmailBtn = (Button) view.findViewById(R.id.bt_canclemodifyEmail);

        saveBtn.setOnClickListener(this);
        calcelEmailBtn.setOnClickListener(this);

        sp = getActivity().getSharedPreferences(loginfile, Context.MODE_PRIVATE);
        oldEmail = sp.getString("email", "");
        username = sp.getString("username", "");
        oldEmailTxt.setText(oldEmail);


        handler = new Handler() {

            public void handleMessage(Message msg) {
                pd.dismiss();
                if (response.toString().equals("success")) {
                    Editor editor = sp.edit();
                    editor.putString("email", newEmailTxt.getEditableText().toString());
                    editor.commit();

                    Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, new PersonInfoModifyFragment()).commit();

                } else if (response.toString().equals("error")) {
                    Toast.makeText(getActivity(), "原邮箱输入错误", Toast.LENGTH_SHORT).show();
                }
            }
        };

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_modifyEmail:
                pd = new ProgressDialog(getActivity());
                pd.setMessage("保存中，请稍后...");
                pd.setCancelable(false);
                pd.show();
                sendRequest();
                break;
            case R.id.bt_canclemodifyEmail:
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, new PersonInfoModifyFragment()).commit();
                break;
        }



    }

    private void sendRequest() {

        datas = "&username=" + username + "&email=" + newEmailTxt.getEditableText().toString();

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
/*	private class MyThread implements Runnable{

		public void run(){
			//// TODO: 2016/7/17 更改网络请求方式
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url );
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("email", newEmailTxt.getEditableText().toString()));
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
				post.setEntity(entity);
				HttpResponse response = client.execute(post);
				if(response.getStatusLine().getStatusCode() == 200)
				{
					HttpEntity httpEntity = response.getEntity();
					InputStream is = httpEntity.getContent();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					receiveStr = br.readLine();
					Message msg = new Message();
					msg.what = 1;					
					handler.sendMessage(msg);
					
				}
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}*/

    }
}

