package com.long345.vlcup5;

/**
 * Created by Administrator on 2017/12/11.
 */

public class chaege {
    public static void main(String[] args) {

    }

    public static String mainip="192.168.31.190";
    public static final String [] bumens={"客楼","测试"};
    public static final String [] renyuans={"kc11","kc12","kc13","kc14"};
    public static final String[] bancis={"白班","夜班"};



    public static String chage_bumen(String bumen){
        String s="error";
        switch (bumen){
            case "客楼":s="kl";break;
            case "测试":s="ceshi";break;
        }
        return s;
    }

    public static String chage_banci(String ban){
        String s="error";
        switch (ban){
            case "白班":s="day";break;
            case "夜班":s="night";break;
        }
        return s;
    }
}
