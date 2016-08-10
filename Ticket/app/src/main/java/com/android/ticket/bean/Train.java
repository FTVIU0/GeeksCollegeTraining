package com.android.ticket.bean;

import java.io.Serializable;

public class Train implements Serializable {
    /**
     * id : 2
     * num : D2266
     * from_city : 广州
     * to_city : 韶关
     * start_time : 11:40
     * start_date : 2017-08-01
     * seat_number : 8
     */

    private int id;
    private String num;
    private String from_city;
    private String to_city;
    private String start_time;
    private String start_date;
    private int seat_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFrom_city() {
        return from_city;
    }

    public void setFrom_city(String from_city) {
        this.from_city = from_city;
    }

    public String getTo_city() {
        return to_city;
    }

    public void setTo_city(String to_city) {
        this.to_city = to_city;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public int getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(int seat_number) {
        this.seat_number = seat_number;
    }



    /*private int id;
    private String num;
    private String start_date;
    private String start_time;
    private String from_city;
    private String to_city;
    private String from_mycity;
    private String to_mycity;
    private String seat_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFrom_city() {
        return from_city;
    }

    public void setFrom_city(String from_city) {
        this.from_city = from_city;
    }

    public String getTo_city() {
        return to_city;
    }

    public void setTo_city(String to_city) {
        this.to_city = to_city;
    }

    public String getFrom_mycity() {
        return from_mycity;
    }

    public void setFrom_mycity(String from_mycity) {
        this.from_mycity = from_mycity;
    }

    public String getTo_mycity() {
        return to_mycity;
    }

    public void setTo_mycity(String to_mycity) {
        this.to_mycity = to_mycity;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }*/
}
