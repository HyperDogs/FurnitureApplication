package com.example.ton.furnitureapplication;

import android.graphics.Bitmap;

import java.util.ArrayList;

import resource.BitmapManager;

public class AbstractModel {



    private String image;
    private String date,cusNo,itemNo,colorNo,coNo,inspector,mail;

    public AbstractModel(String image, String date, String cusNo, String itemNo, String colorNo, String coNo, String inspector, String mail) {
        this.image = image;
        this.date = date;
        this.cusNo = cusNo;
        this.itemNo = itemNo;
        this.colorNo = colorNo;
        this.coNo = coNo;
        this.inspector = inspector;
        this.mail = mail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getColorNo() {
        return colorNo;
    }

    public void setColorNo(String colorNo) {
        this.colorNo = colorNo;
    }

    public String getCoNo() {
        return coNo;
    }

    public void setCoNo(String coNo) {
        this.coNo = coNo;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}
}
