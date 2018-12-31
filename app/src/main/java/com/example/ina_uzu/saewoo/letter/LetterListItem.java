package com.example.ina_uzu.saewoo.letter;

class LetterListItem {
    private int id;
    private int sender;
    private int date;
    private String title;
    private String cont;

    public LetterListItem() {
    }

    public LetterListItem(int id, int sender, int date, String title, String cont){
        this.id=id;
        this.sender=sender;
        this.date = date;
        this.title=title;
        this.cont=cont;
    }
    public LetterListItem(int sender, int date, String title, String cont){
        this.sender=sender;
        this.date=date;
        this.title=title;
        this.cont=cont;
    }

    public LetterListItem(int sender, int year, int month, int day, String title, String cont){
        this.sender=sender;
        this.date = year*10000 + month*100 + day;
        this.title=title;
        this.cont=cont;
    }

    void setId(int id){
        this.id=id;
    }
    void setSender(int sender){
        this.sender=sender;
    }

    void setTitleCont(String title, String cont){
        this.title=title;
        this.cont=cont;
    }

    void setDate(int y, int m, int d){
        this.date = y*10000 + m*100 + d;
    }

    void setDate(int date){
        this.date=date;
    }

    int getId(){
        return id;
    }
    int getSender(){
        return this.sender;
    }

    int getDate(){
        return date;
    }

    String getTitle(){
        return title;
    }

    String getCont(){
        return cont;
    }
}
