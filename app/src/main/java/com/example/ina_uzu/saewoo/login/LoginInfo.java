package com.example.ina_uzu.saewoo.login;

public class LoginInfo {
    private static String inaID = "inarang";
    private static String inaPWD = "ina118";
    private static String jaewooID = "jaewoorang";
    private static String jaewooPWD = "jaewoo118";
    private static int who;

    public final static int ina =0;
    public final static int jaewoo=1;

    public static String getInaID(){
        return inaID;
    }
    public static  String getInaPWD(){
        return inaPWD;
    }
    public static String getJaewooID(){
        return jaewooID;
    }
    public static String getJaewooPWD(){
        return jaewooPWD;
    }
    public static int getWho(){
        return who;
    }

    public  static  void setWho(int i){
        who=i;
    }
}
