package com.android.ticket.bean;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Order {


    /**
     * id : 2
     * price : 20.0
     * is_back : 0
     * seat : B01
     * from_time : 11:40
     * train_num : D2266
     * order_datetime : 2016-07-18 11:05:24
     * name : 123
     * is_pay : 0
     * fromt_date : 2017-08-01
     * to_City : 韶关
     * from_City : 广州
     */

    private int id;
    private String price;
    private int is_back;
    private String seat;
    private String from_time;
    private String train_num;
    private String order_datetime;
    private String name;
    private int is_pay;
    private String fromt_date;
    private String to_City;
    private String from_City;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIs_back() {
        return is_back;
    }

    public void setIs_back(int is_back) {
        this.is_back = is_back;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTrain_num() {
        return train_num;
    }

    public void setTrain_num(String train_num) {
        this.train_num = train_num;
    }

    public String getOrder_datetime() {
        return order_datetime;
    }

    public void setOrder_datetime(String order_datetime) {
        this.order_datetime = order_datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public String getFromt_date() {
        return fromt_date;
    }

    public void setFromt_date(String fromt_date) {
        this.fromt_date = fromt_date;
    }

    public String getTo_City() {
        return to_City;
    }

    public void setTo_City(String to_City) {
        this.to_City = to_City;
    }

    public String getFrom_City() {
        return from_City;
    }

    public void setFrom_City(String from_City) {
        this.from_City = from_City;
    }
}
