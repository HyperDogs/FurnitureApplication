package com.example.ton.furnitureapplication;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Optimize on 26/9/2560.
 */

public class BasicInfomation {
    public static String FileHeader_date="", FileHeader_customerNo="", FileHeader_itemNo="",
            FileHeader_colorNo="", FileHeader_coNo="", FileHeader_inspector="",FileHeader_mail="";
    //public static String / = "";

    public static ArrayList<String> FileDetail_memo_list = new ArrayList<>();
    public static Bitmap[] FileDetail_images;
    public static Bitmap FileHeader_image;



    public static String getFileHeader_mail() {
        return FileHeader_mail;
    }

    public static void setFileHeader_mail(String fileHeader_mail) {
        FileHeader_mail = fileHeader_mail;
    }

    public static String getFileHeader_date() {
        return FileHeader_date;
    }

    public static void setFileHeader_date(String fileHeader_date) {
        FileHeader_date = fileHeader_date;
    }

    public static String getFileHeader_customerNo() {
        return FileHeader_customerNo;
    }

    public static void setFileHeader_customerNo(String fileHeader_customerNo) {
        FileHeader_customerNo = fileHeader_customerNo;
    }

    public static String getFileHeader_itemNo() {
        return FileHeader_itemNo;
    }

    public static void setFileHeader_itemNo(String fileHeader_itemNo) {
        FileHeader_itemNo = fileHeader_itemNo;
    }

    public static String getFileHeader_colorNo() {
        return FileHeader_colorNo;
    }

    public static void setFileHeader_colorNo(String fileHeader_colorNo) {
        FileHeader_colorNo = fileHeader_colorNo;
    }

    public static String getFileHeader_coNo() {
        return FileHeader_coNo;
    }

    public static void setFileHeader_coNo(String fileHeader_coNo) {
        FileHeader_coNo = fileHeader_coNo;
    }

    public static String getFileHeader_inspector() {
        return FileHeader_inspector;
    }

    public static void setFileHeader_inspector(String fileHeader_inspector) {
        FileHeader_inspector = fileHeader_inspector;
    }
}
