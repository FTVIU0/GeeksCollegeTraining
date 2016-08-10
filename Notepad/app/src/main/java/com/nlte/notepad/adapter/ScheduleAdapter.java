package com.nlte.notepad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nlte.notepad.R;
import com.nlte.notepad.bean.Schedule;

import java.util.List;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/7 0007 20:17
 */
public class ScheduleAdapter extends BaseAdapter {
    private Context mContext;
    private List<Schedule> mScheduleList;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        mScheduleList = scheduleList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mScheduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mScheduleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.schedule_item, null);

            viewHolder.tvRemindTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tvScheduleContent = (TextView) convertView.findViewById(R.id.tv_schedule_content);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvRemindTime.setText(String.valueOf(mScheduleList.get(position).getTime()));
        viewHolder.tvScheduleContent.setText(mScheduleList.get(position).getSchedule());

        return convertView;
    }

    private static class ViewHolder {
        TextView tvRemindTime;
        TextView tvScheduleContent;
    }
}
