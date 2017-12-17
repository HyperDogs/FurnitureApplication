package com.example.ton.furnitureapplication;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Lincoln on 18/05/16.
 */
public class Album {
    private String name,fileName;
    private int numOfSongs;
    private int thumbnail;
    public static File DETAIL_FILE;
    public static Bitmap[] DETAIL_BITMAP = new Bitmap[100];
    public static String[] DETAIL_MEMO = new String[100];
    public static String[] DETAIL_FILENAME = new String[100];
    public Album(){

    }

    public Album(String name, int numOfSongs, int thumbnail) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
