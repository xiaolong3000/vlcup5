package com.long345.vlcup5;

/**
 * Created by Administrator on 2017/12/11.
 */

public class chaege {
    public static void main(String[] args) {

    }

    public static String mainip="192.168.1.40";
    public static final String [] bumens={"上北外勤","上南外勤","下北外勤","下南外勤","客场外勤"};
    public static final String [] bumens2={"南商检","北商检"};
    public static final String [] renyuans={"kc11","kc12","kc13","kc14"};
    public static final String[] bancis={"白班","夜班"};
    public  static final String nowbanben="version-shangjian";//更换时需要改


    public static String chage_bumen(String bumen){
        String s="error";
        switch (bumen){
            case "上北外勤":s="sbwq";break;
            case "上南外勤":s="snwq";break;
            case "下北外勤":s="xbwq";break;
            case "下南外勤":s="xnwq";break;
            case "客场外勤":s="kcwq";break;
            case "南商检" : s="nsj";break;
            case "北商检" : s="bsj";break;
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
