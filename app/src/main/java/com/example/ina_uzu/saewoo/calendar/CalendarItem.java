package com.example.ina_uzu.saewoo.calendar;

public class CalendarItem {
    String date;
    boolean ina;
    boolean jaewoo;

    CalendarItem(){
        this.date="";
    }
    CalendarItem(String date){
        this.date=date;
    }

    CalendarItem(String date, boolean ina, boolean jaewoo){
        this.date=date;
        this.ina =ina;
        this.jaewoo = jaewoo;
    }
}
