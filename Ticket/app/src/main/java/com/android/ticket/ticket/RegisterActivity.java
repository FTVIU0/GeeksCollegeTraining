package com.android.ticket.ticket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.ticket.R;
import com.android.ticket.utils.MyServerUrl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends Activity implements OnClickListener {
	public static final int REG_REQUEST = 0;
	private String url = MyServerUrl.url + "Reg.jsp";
	private Button registerBtn;
	private EditText usernameTxt;
	private EditText pswTxt;
	private EditText nameTxt;
	private EditText telTxt;
	private EditText emailTxt;
	private EditText idCardTxt;
	private View saveBtn;
	private ProgressDialog pd;
	private Handler handler;
	private String datas;
	private StringBuilder response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				pd.dismiss();
				if (response.toString().equals("success")) {

					// Intent it = new
					// Intent(RegisterActivity.this,LoginActivity.class);
					// startActivity(it);

					finish();
				} else if (response.toString().equals("error")) {

					Toast.makeText(RegisterActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();

				}

			}

		};
	}

	private void initView() {
		registerBtn = (Button) findViewById(R.id.bt_register);
		usernameTxt = (EditText) findViewById(R.id.et_regesiter_username);
		pswTxt = (EditText) findViewById(R.id.et_regesiter_password);
		nameTxt = (EditText) findViewById(R.id.et_regesiter_realname);
		telTxt = (EditText) findViewById(R.id.et_regesiter_phone);
		emailTxt = (EditText) findViewById(R.id.et_regesiter_email);
		idCardTxt = (EditText) findViewById(R.id.et_regesiter_idcard);
		registerBtn.setOnClickListener(this);
	}

	public void onClick(View v) {
		pd = new ProgressDialog(this);
		pd.setMessage("注册中，请稍后...");
		pd.setCancelable(false);
		pd.show();

		sendRegRegeuest();
	}

	private void sendRegRegeuest() {
		datas = "username=" + usernameTxt.getText().toString() + "&password="
				+ pswTxt.getText().toString() + "&name="
				+ nameTxt.getText().toString() + "&tel="
				+ telTxt.getText().toString() + "&email="
				+ emailTxt.getText().toString() + "&idcard="
				+ idCardTxt.getText().toString();
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL urlConn = new URL(url);
					connection = (HttpURLConnection) urlConn.openConnection();
					connection.setRequestMethod("POST");
					DataOutputStream dataOutputStream = new DataOutputStream(
							connection.getOutputStream());
					dataOutputStream.write(datas.getBytes());
					InputStream in = connection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(in));
					response = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						response.append(line);
					}
					Message message = new Message();
					message.what = REG_REQUEST;
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