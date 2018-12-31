package com.example.ina_uzu.saewoo.bucketlist;

class BucketListItem {
    static int count = 0;
    private int id;
    private boolean isChecked;
    private String cont;

    public BucketListItem(){
    }

    public BucketListItem(String cont){
        isChecked=false;
        this.cont=cont;
    }

    public BucketListItem(String cont, boolean isChecked){
        this.isChecked=isChecked;
        this.cont=cont;
    }

    void setId(int i){
        this.id=i;
    }
    void setCont(String cont){
        this.cont = cont;
    }

    void setChecked(boolean checked){
        isChecked = checked;
    }

    int getId(){
        return this.id;
    }
    String getCont(){
        return this.cont;
    }
    boolean getIsChecked(){
        return this.isChecked;
    }

}
