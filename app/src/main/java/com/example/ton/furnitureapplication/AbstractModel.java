package com.example.ton.furnitureapplication;

import java.util.ArrayList;

public class AbstractModel {

    private String title;

    private String message;

    private int image;



    public AbstractModel(String title, String message, int image) {
        this.title = title;
        this.message = message;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getImage() {return image;}

    public void setImage(int image) {this.image = image;}
}
