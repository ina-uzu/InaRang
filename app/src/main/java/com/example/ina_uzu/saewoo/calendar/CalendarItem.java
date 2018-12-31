package com.example.ina_uzu.saewoo.calendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarItem {
    int y, m, d;
    String date;
    boolean ina;
    boolean jaewoo;
    List<ScheduleItem> schedList ;

    CalendarItem(){
        this.date="";
        schedList= new ArrayList<ScheduleItem>();
    }

    CalendarItem(int y, int m, int d, String date){
        this.date=date;
        this.y = y;
        this.m=m;
        this.d=d;
        schedList= new ArrayList<ScheduleItem>();
    }

    CalendarItem(int y, int m, int d, String date, boolean ina, boolean jaewoo){
        this.date=date;
        this.ina =ina;
        this.jaewoo = jaewoo;

        this.y = y;
        this.m=m;
        this.d=d;
        schedList= new ArrayList<ScheduleItem>();

    }

    CalendarItem(int y, int m, int d, String date, boolean ina, boolean jaewoo, List<ScheduleItem> list){
        this.date=date;
        this.ina =ina;
        this.jaewoo = jaewoo;

        this.y = y;
        this.m=m;
        this.d=d;

        this.schedList= list;
    }

    public void addSchedule(ScheduleItem scheduleItem){
        if( schedList==null)
            schedList = new ArrayList<ScheduleItem>();
        schedList.add(scheduleItem);
    }
}
