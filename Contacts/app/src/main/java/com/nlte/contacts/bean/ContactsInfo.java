package com.nlte.contacts.bean;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/3 0003 16:22
 */
public class ContactsInfo {
    private String contactId;
    private String name;
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
