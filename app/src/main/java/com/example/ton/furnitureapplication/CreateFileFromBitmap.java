package com.example.ton.furnitureapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        File mFile;
        ImageView mImgView;
        private Home mHomeClass;

        public CreateFileFromBitmap(Home homeClass, Bitmap bitmap, Context context, ImageView imgView) {
            this.bitmap = bitmap;
            this.context= context;
            this.mImgView = imgView;
            this.mHomeClass = homeClass;
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
            mFile = CreateFile.createUnique();
            String filePath = CreateFile.getFilePath();


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

            if(mImgView == null) {
                //Album.DETAIL_BITMAP[Album.CURRENT_PICK_IMG_POSITION] = bitmap;
                Album.DETAIL_FILENAME[Album.CURRENT_PICK_IMG_POSITION] = CreateFile.getFileName();
            } else {
                Home.bitmap = bitmap;
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // back to main thread after finishing doInBackground
            // update your UI or take action after
            // exp; make progressbar gone
            if(mImgView != null) {
                Picasso.with(context).load(Uri.fromFile(mFile)).fit().centerCrop().into(mImgView);
                //Picasso.with(context).load(Utility.getImageUri(context, bitmap)).fit().centerCrop().into(mImgView);
                mImgView.setAlpha((float) 1.0);
                mHomeClass.setFileHeader(CreateFile.getFileName());
            }

            Home.updateView();
        }
    }

