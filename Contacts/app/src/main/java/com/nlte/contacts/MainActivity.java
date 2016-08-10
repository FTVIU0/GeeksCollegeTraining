package com.nlte.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nlte.contacts.adapter.ContactsAdapter;
import com.nlte.contacts.bean.ContactsInfo;
import com.nlte.contacts.business.ContactsManager;

import java.util.List;
import java.util.regex.Pattern;

;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText mEtContactName;
    private EditText mEtContactPhoneNumber;
    private List<ContactsInfo> contactsInfoList;
    private ContactsAdapter mContactsAdapter;
    private ListView mLvContacts;
    private FloatingActionButton mFabAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化界面
        initView();

        //添加事件
        assert mFabAdd != null;
        mFabAdd.setOnClickListener(this);
        mLvContacts.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        contactsInfoList = ContactsManager.getContactsInfo(this);

        mContactsAdapter = new ContactsAdapter(this, contactsInfoList);
        assert mLvContacts != null;
        mLvContacts.setAdapter(mContactsAdapter);
    }

    private void initView() {
        mLvContacts = (ListView) findViewById(R.id.lv_contacts);
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        contactsInfoList = ContactsManager.getContactsInfo(this);

        mContactsAdapter = new ContactsAdapter(this, contactsInfoList);
        assert mLvContacts != null;
        mLvContacts.setAdapter(mContactsAdapter);
    }


    //检查手机号码的正确性
    public static boolean isMobileNumber(String mobiles) {
        return Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}")
                .matcher(mobiles).matches();
    }

    @Override
    public void onClick(View v) {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, (ViewGroup) findViewById(R.id.dialog));
        mEtContactName = (EditText) view.findViewById(R.id.et_contact_name);
        mEtContactPhoneNumber = (EditText) view.findViewById(R.id.et_contact_phone_number);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.add_contact))
                .setView(view)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String contactName = mEtContactName.getText().toString().trim();
                        String phoneNum = mEtContactPhoneNumber.getText().toString().trim();

                        if (TextUtils.isEmpty(contactName) || TextUtils.isEmpty(phoneNum)) {
                            Toast.makeText(MainActivity.this, R.string.toast_input, Toast.LENGTH_SHORT).show();
                        } else {
                            if (isMobileNumber(phoneNum)) {

                                //添加联系人
                                ContactsInfo contactsInfo = new ContactsInfo();
                                contactsInfo.setName(contactName);
                                contactsInfo.setPhoneNum(phoneNum);
                                contactsInfoList.add(contactsInfo);

                                //将联系人添加到数据库中
                                ContactsManager.addContact(contactName, phoneNum);

                                //刷新界面
                                mContactsAdapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(MainActivity.this, R.string.toast_format, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
        new AlertDialog.Builder(MainActivity.this)
                .setItems(new String[]{"打电话", "发短信"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNum = contactsInfoList.get(position).getPhoneNum();
                        switch (which) {
                            case 0:
                                actionDirectly(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                                break;
                            case 1:
                                actionDirectly(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNum));
                                break;
                        }
                    }
                })
                .show();
    }

    private void actionDirectly(String actionCall, Uri parse) {
        Intent intent = new Intent();
        intent.setAction(actionCall);
        intent.setData(parse);
        startActivity(intent);
    }
}
