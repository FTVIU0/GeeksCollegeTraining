package com.android.ticket.ticket.fragment;



import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.ticket.R;
import com.android.ticket.adapter.SettingAdapter;
import com.android.ticket.utils.Constant;
import com.android.ticket.utils.SharePreferenceUtil;

public class PersonCenterFragment extends Fragment implements AdapterView.OnItemClickListener {
	
	private ListView lvSetting;

    private String[] datas = new String[]{"我的信息中心", "其他功能", "其他功能", "注销用户"};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.activity_setting, container, false);
		
		
		 lvSetting = (ListView) view.findViewById(R.id.lv_setting);

	        lvSetting.setAdapter(new SettingAdapter(getActivity(), datas));

	        lvSetting.setOnItemClickListener(this);
	        
	        return view;
	}
	
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        
//	    	Toast.makeText(getActivity(), "item " + position, Toast.LENGTH_SHORT).show();
	    	
	        switch (position) {
			case 0:
				getActivity().getFragmentManager().beginTransaction()
				.addToBackStack(null)
				.replace(R.id.fl_container, new PersonInfoModifyFragment()).commit();

				break;
			case 1:

				break;
			case 2:

				break;
			case 3:
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

				builder.setTitle("提醒")
						.setMessage("是否注销登录")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								SharePreferenceUtil.putBoolean(getActivity(), Constant.ISLOGIN, false);
								SharePreferenceUtil.putString(getActivity(), Constant.USERNAME, "");
								SharePreferenceUtil.putString(getActivity(), Constant.PASSWORD, "");

								Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_SHORT).show();

								//getFragmentManager().popBackStackImmediate(null, 1);

								getFragmentManager().beginTransaction()
										.replace(R.id.fl_container, new UserFragment())
										.commit();
							}
						})
						.setNegativeButton("取消", null)
						.show();

				break;
			default:
				break;
			}
	        
	    }

}
