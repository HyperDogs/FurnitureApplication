package com.example.ton.furnitureapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

import resource.BitmapManager;

public class Preview extends AppCompatActivity {
    ImageView preview;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preveiw_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent getData = getIntent();
        int imgIndex = getData.getIntExtra("IMG_INDEX", 0);


        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.previewImg);
        //imageView.setImage(ImageSource.cachedBitmap(Album.DETAIL_BITMAP[imgIndex]));
        //imageView.setImage(ImageSource.resource(R.drawable.example));

        //preview = (ImageView)findViewById(R.id.previewImg);
       // Glide.with(PreveiwImage.this).load(R.drawable.example).into(preview);
       // Glide.with(PreveiwImage.this).load(R.drawable.example).fi
       //z Picasso.with(PreveiwImage.this).load(R.drawable.example).fit().centerCrop().into(preview);
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "DCIM" + File.separator + "Camera" + File.separator + Album.DETAIL_FILENAME[imgIndex]);
         bitmap = resource.BitmapManager.decode(file.getPath(), 1000, 1500);

        if (file.exists()) {
            //Picasso.with(getApplicationContext()).load(Uri.fromFile(file)).fit().centerCrop().into(preview);
            imageView.setImage(ImageSource.cachedBitmap(bitmap));

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)
            bitmap.recycle();
            System.gc();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
