package com.android.ticket.ticket.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.ticket.R;
import com.android.ticket.liucanwen.citylist.CityListActivity;
import com.android.ticket.utils.GetDateTime;
import com.android.ticket.utils.SearchTrainTask;

public class ReserveFragment extends Fragment implements OnClickListener {
	private ImageButton changeIb;
	private Button checkBt;

	private TextView originTv;
	private TextView terminusTv;
	private SharedPreferences sp;
	private String loginfile = "loginfile";

	private View view;
	private TextView startDate;
	private TextView startTime;
	protected String time;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_reserve, container, false);

		initView();

		return view;
	}

	private void initView() {

		changeIb = (ImageButton) view.findViewById(R.id.ib_main_change);
		originTv = (TextView) view.findViewById(R.id.tv_main_origin);
		terminusTv = (TextView) view.findViewById(R.id.tv_main_terminus);
		startDate = (TextView) view.findViewById(R.id.tv_startDate);
		startTime = (TextView) view.findViewById(R.id.tv_startTime);
		checkBt = (Button) view.findViewById(R.id.bt_main_check);

		changeIb.setOnClickListener(this);
		originTv.setOnClickListener(this);
		terminusTv.setOnClickListener(this);
		startDate.setOnClickListener(this);
		startTime.setOnClickListener(this);
		checkBt.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_main_change: //互换起始站点
			String originStr = originTv.getText().toString();
			String terminusStr = terminusTv.getText().toString();
			originTv.setText(terminusStr);
			terminusTv.setText(originStr);
			break;
		case R.id.tv_main_origin://输入出发点
			Intent originIntent = new Intent(getActivity(),
					CityListActivity.class);
			originIntent.putExtra("fromcity", "fromcity");
			startActivityForResult(originIntent, 1);
			break;
		case R.id.tv_main_terminus://输入终点
			Intent TerminusIntent = new Intent(getActivity(),
					CityListActivity.class);
			TerminusIntent.putExtra("tocity", "tocity");
			startActivityForResult(TerminusIntent, 2);
			break;
		case R.id.tv_startDate://设置出发日期
			DatePickerDialog dP = new DatePickerDialog(getActivity(),
					new DatePickerDialog.OnDateSetListener() {

						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
//							int moth = monthOfYear + 1;
//							 String string = year + "-" + moth + "-"
//									+ dayOfMonth;
							String DateStr = String.format("%1$04d-%2$02d-%3$02d",year,monthOfYear+1,dayOfMonth);
							startDate.setText(DateStr);
						}
					}, GetDateTime.getYear(), GetDateTime.getMonth(),
					GetDateTime.getDay());
			dP.show();
			break;
		case R.id.tv_startTime://设置出发时间
			final String[] times = { "00:00-06:00", "06:00-12:00",
					"12:00-18:00", "18:00-24:00" };

			AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
			b.setSingleChoiceItems(times, 0,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							time = times[which];

						}
					})
					.setTitle("设置时间")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									startTime.setText(time);
								}
							}).create().show();
			break;
		case R.id.bt_main_check://查询车票，用异步任务
			SearchTrainTask st = new SearchTrainTask(getActivity());
			st.execute(originTv.getText().toString(),
					terminusTv.getText().toString(),
					startDate.getText().toString(),
					startTime.getText().toString());

			break;
		default:
			break;
		}
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			String fromcity = data.getStringExtra("fromcity");
			originTv.setText(fromcity);
		}

		if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
			String tocity = data.getStringExtra("tocity");
			terminusTv.setText(tocity);
		}
	}

}
