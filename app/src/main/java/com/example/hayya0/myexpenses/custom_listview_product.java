package com.example.hayya0.myexpenses;

/**
 * Created by hayya0 on 10/11/2016.
 */
public class custom_listview_product {
    private int id;
    private int cash;
    private String comment;
    private String time;
    private String date;


    public custom_listview_product(int id, int cash, String comment, String time, String date) {
        this.id = id;
        this.cash = cash;
        this.comment = comment;
        this.time = time;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
