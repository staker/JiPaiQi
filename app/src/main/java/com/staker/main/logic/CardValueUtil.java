package com.staker.main.logic;

import android.text.TextUtils;

import com.staker.master.R;

import java.util.ArrayList;

public class CardValueUtil {



    public static ArrayList<String> getListByValue(String value,ArrayList<String> list){
        ArrayList<String> valueList=new ArrayList<String>();
        if(TextUtils.isEmpty(value)||list==null){
            return valueList;
        }

        for(String temp : list){
            if(value.equals(temp)){
                valueList.add(temp);
            }
        }
        return valueList;
    }


    public static int getHeiImgIdByValue(String value){
        int imgId= R.drawable.hei_2;
        switch (value+""){
            case "1":
                imgId=R.drawable.hei_a;
                break;
            case "2":
                imgId=R.drawable.hei_2;
                break;
            case "3":
                imgId=R.drawable.hei_3;
                break;
            case "4":
                imgId=R.drawable.hei_4;
                break;
            case "5":
                imgId=R.drawable.hei_5;
                break;
            case "6":
                imgId=R.drawable.hei_6;
                break;
            case "7":
                imgId=R.drawable.hei_7;
                break;
            case "8":
                imgId=R.drawable.hei_8;
                break;
            case "9":
                imgId=R.drawable.hei_9;
                break;
            case "10":
                imgId=R.drawable.hei_10;
                break;
            case "11":
                imgId=R.drawable.hei_j;
                break;
            case "12":
                imgId=R.drawable.hei_q;
                break;
            case "13":
                imgId=R.drawable.hei_k;
                break;

        }
        return imgId;
    }


    public static int getHongImgIdByValue(String value){
        int imgId= R.drawable.hong_2;
        switch (value+""){
            case "1":
                imgId=R.drawable.hong_a;
                break;
            case "2":
                imgId=R.drawable.hong_2;
                break;
            case "3":
                imgId=R.drawable.hong_3;
                break;
            case "4":
                imgId=R.drawable.hong_4;
                break;
            case "5":
                imgId=R.drawable.hong_5;
                break;
            case "6":
                imgId=R.drawable.hong_6;
                break;
            case "7":
                imgId=R.drawable.hong_7;
                break;
            case "8":
                imgId=R.drawable.hong_8;
                break;
            case "9":
                imgId=R.drawable.hong_9;
                break;
            case "10":
                imgId=R.drawable.hong_10;
                break;
            case "11":
                imgId=R.drawable.hong_j;
                break;
            case "12":
                imgId=R.drawable.hong_q;
                break;
            case "13":
                imgId=R.drawable.hong_k;
                break;

        }
        return imgId;
    }



    public static int getMeiImgIdByValue(String value){
        int imgId= R.drawable.mei_2;
        switch (value+""){
            case "1":
                imgId=R.drawable.mei_a;
                break;
            case "2":
                imgId=R.drawable.mei_2;
                break;
            case "3":
                imgId=R.drawable.mei_3;
                break;
            case "4":
                imgId=R.drawable.mei_4;
                break;
            case "5":
                imgId=R.drawable.mei_5;
                break;
            case "6":
                imgId=R.drawable.mei_6;
                break;
            case "7":
                imgId=R.drawable.mei_7;
                break;
            case "8":
                imgId=R.drawable.mei_8;
                break;
            case "9":
                imgId=R.drawable.mei_9;
                break;
            case "10":
                imgId=R.drawable.mei_10;
                break;
            case "11":
                imgId=R.drawable.mei_j;
                break;
            case "12":
                imgId=R.drawable.mei_q;
                break;
            case "13":
                imgId=R.drawable.mei_k;
                break;

        }
        return imgId;
    }




    public static int getFangImgIdByValue(String value){
        int imgId= R.drawable.fang_2;
        switch (value+""){
            case "1":
                imgId=R.drawable.fang_a;
                break;
            case "2":
                imgId=R.drawable.fang_2;
                break;
            case "3":
                imgId=R.drawable.fang_3;
                break;
            case "4":
                imgId=R.drawable.fang_4;
                break;
            case "5":
                imgId=R.drawable.fang_5;
                break;
            case "6":
                imgId=R.drawable.fang_6;
                break;
            case "7":
                imgId=R.drawable.fang_7;
                break;
            case "8":
                imgId=R.drawable.fang_8;
                break;
            case "9":
                imgId=R.drawable.fang_9;
                break;
            case "10":
                imgId=R.drawable.fang_10;
                break;
            case "11":
                imgId=R.drawable.fang_j;
                break;
            case "12":
                imgId=R.drawable.fang_q;
                break;
            case "13":
                imgId=R.drawable.fang_k;
                break;

        }
        return imgId;
    }
}
