package com.android.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.ticket.R;
import com.android.ticket.bean.Train;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public class SearchListAdapter extends BaseAdapter {
   private Context context;
    private List<Train> trains;

    public SearchListAdapter(Context context, List<Train> trains) {
        this.context = context;
        this.trains = trains;
    }

    @Override
    public int getCount() {
        return trains.size();
    }

    @Override
    public Object getItem(int i) {
        return trains.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){

            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.seach_list_item, null);

            viewHolder.tvOrder = (TextView) view.findViewById(R.id.tv_order);
            viewHolder.tvFromCity = (TextView) view.findViewById(R.id.tv_from_city);
            viewHolder.tvToCity = (TextView) view.findViewById(R.id.tv_to_city);
            viewHolder.tvSeatNum = (TextView) view.findViewById(R.id.tv_ticket_num);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvOrder.setText(trains.get(i).getNum());
        viewHolder.tvFromCity.setText(trains.get(i).getFrom_city());
        viewHolder.tvToCity.setText(trains.get(i).getTo_city());
        viewHolder.tvSeatNum.setText(trains.get(i).getSeat_number()+"");

        return view;
    }
    class ViewHolder{
        TextView tvOrder;
        TextView tvFromCity;
        TextView tvToCity;
        TextView tvSeatNum;
    }
}
