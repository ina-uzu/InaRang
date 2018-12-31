package com.example.ina_uzu.saewoo.calendar;

import com.example.ina_uzu.saewoo.login.LoginInfo;

public class CalendarInfo {
    private int id;
    private int who = LoginInfo.getWho();
    private int year;
    private int month;
    private int date;
    private String sched;

    public CalendarInfo(){

    }

    public CalendarInfo(int year, int month , int date){
        this.date=date;
        this.month = month;
        this.year = year;
    }

    public CalendarInfo(int year, int month , int date, String sched){
        this.date=date;
        this.month = month;
        this.year = year;
        this.sched = sched;
    }

    public CalendarInfo(int who, int year, int month , int date, String sched){
        this.who=who;
        this.date=date;
        this.month = month;
        this.year = year;
        this.sched = sched;
    }
    public int getId(){
        return id;
    }

    public int getYear(){
        return year;
    }

    public int getMonth(){
        return month;
    }

    public int getDate(){
        return date;
    }

    public int getWho(){
        return who;
    }

    public  String getSched(){
        return sched;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWho(int writer) {
        this.who = writer;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDate(int date) {
        this.date=date;
    }


    public void setSched(String sched) {
        this.sched = sched;
    }
}

