package com.example.crud3.utils;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Calendar;

import static com.example.crud3.utils.Global.savepath;

public class savePicture {

    public void savePicture(Mat img,int id){

        String time=getTime();
        Imgcodecs.imwrite(savepath+time,img);
    }

    public static String getTime(){

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int min = minute/10;
        int second = c.get(Calendar.SECOND);
        String month1 = "";
        String date1 ="";
        if(month<10){
            month1="0"+month;
        }else {
            month1=""+month;
        }
        if(date<10){
            date1="0"+date;
        }else {
            date1=""+date;
        }

        String savedate=year+"-"+month1+"-"+date1+"-"+hour+"-"+min+".jpg";
        return savedate;
    }

}