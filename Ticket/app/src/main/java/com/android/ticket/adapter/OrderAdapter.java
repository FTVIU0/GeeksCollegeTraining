package com.android.ticket.adapter;

import android.content.Context;
import android.drm.DrmStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.ticket.R;
import com.android.ticket.bean.Order;

import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
public class OrderAdapter extends BaseAdapter {

    private Context context;
    private List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.order_ticket, null);

            viewHolder.tvFromMyCity = (TextView) view.findViewById(R.id.from_mycity_txt_id);
            viewHolder.tvTrainNum = (TextView) view.findViewById(R.id.train_num_txt_id);
            viewHolder.tvToMyCity = (TextView) view.findViewById(R.id.to_mycity_txt_id);
            viewHolder.tvOrderUserName = (TextView) view.findViewById(R.id.tv_order_username);
            viewHolder.tvFromTime = (TextView) view.findViewById(R.id.tv_from_time);

            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvFromMyCity.setText(orders.get(i).getFrom_City());
        viewHolder.tvTrainNum.setText(orders.get(i).getTrain_num());
        viewHolder.tvToMyCity.setText(orders.get(i).getTo_City());
        viewHolder.tvOrderUserName.setText(orders.get(i).getName());
        viewHolder.tvFromTime.setText(orders.get(i).getFrom_time());

        return view;
    }

    class ViewHolder{
        TextView tvFromMyCity;
        TextView tvTrainNum;
        TextView tvToMyCity;
        TextView tvOrderUserName;
        TextView tvFromTime;

    }
}
