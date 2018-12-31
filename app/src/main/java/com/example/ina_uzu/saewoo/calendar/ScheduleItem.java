package com.example.ina_uzu.saewoo.calendar;

public class ScheduleItem {
    String cont;
    int who;
    private int id;

    ScheduleItem(int who, String cont){
        this.who=who;
        this.cont=cont;
    }
    public void setId(int i){
        id =i;
    }

    public int getId(){
        return id;
    }
}
