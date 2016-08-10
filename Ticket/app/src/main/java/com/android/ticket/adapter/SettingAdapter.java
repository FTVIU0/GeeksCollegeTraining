package com.android.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.ticket.R;


public class SettingAdapter extends BaseAdapter {
    private Context mContext;
    private String[] datas;

    public SettingAdapter(Context contexts, String[] datas) {
    	
        this.datas = datas;
        mContext = contexts;
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);

            TextView text = (TextView) convertView.findViewById(R.id.tv_text);

            text.setText(datas[position]);
        }

        return convertView;
    }
}
