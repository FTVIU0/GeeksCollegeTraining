package com.nlte.contacts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nlte.contacts.bean.ContactsInfo;
import com.nlte.contacts.R;

import java.util.List;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/3 0003 21:08
 */
public class ContactsAdapter extends BaseAdapter {

    private Context mContext;
    private List<ContactsInfo> mContactsInfoList;

    public ContactsAdapter(Context context, List<ContactsInfo> contactsInfoList) {
        mContext = context;
        mContactsInfoList = contactsInfoList;
    }

    @Override
    public int getCount() {
        return mContactsInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactsInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.contacts_item_list, null);
            viewHolder.tvContactName = (TextView) convertView.findViewById(R.id.tv_contact_name);
            viewHolder.tvContactPhoneNum = (TextView) convertView.findViewById(R.id.tv_contact_phone_number);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvContactName.setText(mContactsInfoList.get(position).getName());
        //// TODO: 2016/7/3 0003  
        viewHolder.tvContactPhoneNum.setText(mContactsInfoList.get(position).getPhoneNum());

        return convertView;
    }
    static class ViewHolder{
        TextView tvContactName;
        TextView tvContactPhoneNum;
    }
}
