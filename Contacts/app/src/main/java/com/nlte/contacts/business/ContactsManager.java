package com.nlte.contacts.business;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.nlte.contacts.bean.ContactsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/4 0004 2:06
 */
public class ContactsManager {
    private static ContentResolver mContentResolver;

    //获取联系人信息
    public static List<ContactsInfo> getContactsInfo(Context context) {

        List<ContactsInfo> contactsList = new ArrayList<>();

        //获取内容提供者
        mContentResolver = context.getContentResolver();
        Cursor cursor = mContentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        assert cursor != null;
        while (cursor.moveToNext()) {

            ContactsInfo contactsInfo = new ContactsInfo();

            //获取联系人姓名
            String contactName = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            //获取联系人ID
            String contactId = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts._ID));
            int id = Integer.parseInt(contactId);

            //获取联系人号码
            String contactPhoneNum = "";
            if (id > 0) {
                Cursor phoneNumCursor = mContentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{contactId}, null);

                assert phoneNumCursor != null;
                while (phoneNumCursor.moveToNext()) {
                    contactPhoneNum = phoneNumCursor.getString(
                            phoneNumCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phoneNumCursor.close();
            }

            contactsInfo.setContactId(contactId);
            contactsInfo.setName(contactName);

            //去除号码中的空格
            contactPhoneNum = contactPhoneNum.replaceAll(" ", "");

            int length = contactPhoneNum.length();
            if (!contactPhoneNum.contains("-")) {
                if (length > 11) {
                    contactPhoneNum = contactPhoneNum.substring(length - 11);
                }
            }
            contactsInfo.setPhoneNum(contactPhoneNum);

            contactsList.add(contactsInfo);
        }
        cursor.close();
        return contactsList;
    }


    //添加联系人
    public static void addContact(String contactName, String phoneNum) {
        //首先插入空值
        ContentValues values = new ContentValues();
        Uri rawContactUri = mContentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactsId = ContentUris.parseId(rawContactUri);

        //往刚才的记录插入姓名
        values.clear();
        values.put(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID, rawContactsId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactName);
        mContentResolver.insert(ContactsContract.Data.CONTENT_URI, values);

        //插入电话
        values.clear();
        values.put(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNum);
        mContentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
    }
}
