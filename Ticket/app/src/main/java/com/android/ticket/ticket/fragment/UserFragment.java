package com.android.ticket.ticket.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.ticket.R;
import com.android.ticket.ticket.RegisterActivity;
import com.android.ticket.utils.Constant;
import com.android.ticket.utils.MyServerUrl;
import com.android.ticket.utils.SharePreferenceUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class UserFragment extends Fragment implements View.OnClickListener {
    public static final int LOGIN_RESPONSE = 1;
    private EditText username;
    private EditText password;
    private Button btnlogin;
    private ProgressDialog pd;
    protected String loginfile = "loginfile";
    protected SharedPreferences sp;
    private Button btnregesiter;
    private String url = MyServerUrl.url + "LoginCheck.jsp";
    public String receiveStr;
    private Handler handler;
    private String datas;
    protected StringBuilder response;
    private CheckBox cbrememberPsw;
    private Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_user, container, false);

        username = (EditText) view.findViewById(R.id.et_login_username);
        password = (EditText) view.findViewById(R.id.et_login_password);
        btnlogin = (Button) view.findViewById(R.id.bt_login);
        btnregesiter = (Button) view.findViewById(R.id.bt_register);
        cbrememberPsw = (CheckBox) view.findViewById(R.id.cb_rememberPsw);
        //// TODO: 2016/7/19 待测试
        cbrememberPsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharePreferenceUtil.putBoolean(getActivity(), Constant.ISREMENBERPSW, b);
            }
        });

        btnlogin.setOnClickListener(this);
        btnregesiter.setOnClickListener(this);

        if (SharePreferenceUtil.getBoolean(getActivity(), Constant.ISREMENBERPSW, false)) {
            username.setText(SharePreferenceUtil.getString(getActivity(), Constant.USERNAME, ""));
            password.setText(SharePreferenceUtil.getString(getActivity(), Constant.PASSWORD, ""));
            cbrememberPsw.setChecked(true);
        } else {
            username.setText("");
            password.setText("");
            cbrememberPsw.setChecked(false);
        }


        handler = new Handler() {

            public void handleMessage(Message msg) {
                pd.dismiss();
                if (response.toString().equals("success")) {

                    SharePreferenceUtil.putString(getActivity(), Constant.USERNAME, username.getText().toString());
                    SharePreferenceUtil.putString(getActivity(), Constant.PASSWORD, password.getText().toString());
                    SharePreferenceUtil.putBoolean(getActivity(), Constant.ISLOGIN, true);

                    //getActivity().getFragmentManager().popBackStackImmediate(null, 1);
                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, new PersonCenterFragment())
                            .commit();

                } else if (response.toString().equals("error")) {

                    Toast.makeText(getActivity(), "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        };


        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_login:
                pd = new ProgressDialog(getActivity());
                pd.setMessage("登录中，请稍后...");
                pd.setCancelable(false);
                pd.show();

//                editor = sp.edit();
//                if (ckbrememberPsw.isChecked()){
//                    editor.putBoolean("rememberPsw", true);
//                    editor.putString("account", account);
//                    editor.putString("password", password);
//                }

                sendLoignRequest();

                break;

            case R.id.bt_register:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

    private void sendLoignRequest() {
        datas = "username=" + username.getText().toString()
                + "&password=" + password.getText().toString();
        new Thread(new Runnable() {

            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL urlConn = new URL(url);
                    connection = (HttpURLConnection) urlConn.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                    dataOutputStream.write(datas.getBytes());
                    InputStream in = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    response = new StringBuilder();
                    response.append(bufferedReader.readLine());
                    Message message = new Message();
                    message.what = LOGIN_RESPONSE;
                    message.obj = response.toString();
                    handler.sendMessage(message);

                } catch (Exception e) {
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
