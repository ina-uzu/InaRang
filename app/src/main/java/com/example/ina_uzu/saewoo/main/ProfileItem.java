package com.example.ina_uzu.saewoo.main;

public class ProfileItem {
    String title;
    String cont;
    ProfileItem(String title, String cont){
        this.cont=cont;
        this.title=title;
    }

    String getTitle(){
        return title;
    }

    String getCont(){
        return cont;
    }
}
