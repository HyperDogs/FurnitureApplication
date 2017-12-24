package com.example.ton.furnitureapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import resource.CreateFile;

/**
 * Created by Optimize on 24/12/2560.
 */

public class CreateFileFromBitmap extends AsyncTask<Void, Integer, String> {

        Context context;
        Bitmap bitmap;
        File file;
        public CreateFileFromBitmap(Bitmap bitmap, Context context) {
            this.bitmap = bitmap;
            this.context= context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before executing doInBackground
            // update your UI
            // exp; make progressbar visible
        }

        @Override
        protected String doInBackground(Void... params) {
            File file = CreateFile.createUnique();
            String filePath = CreateFile.getFilePath();

            Album.DETAIL_FILENAME[Album.CURRENT_PICK_IMG_POSITION]  = CreateFile.getFileName();

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            //choose another format if PNG doesn't suit you
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);

            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Album.DETAIL_BITMAP[Album.CURRENT_PICK_IMG_POSITION] = bitmap;


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // back to main thread after finishing doInBackground
            // update your UI or take action after
            // exp; make progressbar gone

            Home.updateView();
        }
    }

