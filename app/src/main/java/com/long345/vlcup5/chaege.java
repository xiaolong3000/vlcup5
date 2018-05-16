package com.long345.vlcup5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */

public class chaege {
    public static void main(String[] args) {

    }

    public static String mainip="192.168.1.40";
    public static String [] bumens={"上北外勤","上南外勤","下北外勤","下南外勤","客场外勤"};
    public static String [] bumens2={"南商检","北商检"};
    public static String [] bumens3={"商检","北外勤","南外勤"};
    public static final String[] bancis={"白班","夜班"};




    public  static final String nowbanben="waiqin";//需要更改
    public static final String[] thebumen=bumens;//需要更改








    public static final Map<String,String> map_name=new HashMap<>();

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

            case "商检":s="sj";break;
            case "北外勤":s="bwq";break;
            case "南外勤":s="nwq";break;
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
