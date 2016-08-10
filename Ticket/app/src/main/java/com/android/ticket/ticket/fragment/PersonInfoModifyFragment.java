package com.android.ticket.ticket.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.ticket.R;
import com.android.ticket.adapter.SettingAdapter;
import com.android.ticket.utils.Constant;

public class PersonInfoModifyFragment extends Fragment implements AdapterView.OnItemClickListener {
	
	private ListView lvSetting;

    private String[] datas = new String[]{"密码修改", "邮箱修改", "联系方式修改"};

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
				.replace(R.id.fl_container, new PersonPswModifyFragment()).commit();
				break;
			case 1:
				getActivity().getFragmentManager().beginTransaction()
				.addToBackStack(null)
				.replace(R.id.fl_container, new PersonEmailModifyFragment()).commit();
				break;
			case 2:
				getActivity().getFragmentManager().beginTransaction()
				.addToBackStack(null)
				.replace(R.id.fl_container, new PersonTelModifyFragment()).commit();
				break;

			default:
				break;
			}
	        
	    }

}
