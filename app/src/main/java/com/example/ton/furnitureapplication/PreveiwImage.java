package com.example.ton.furnitureapplication;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;

import java.security.Guard;

public class PreveiwImage extends AppCompatActivity {
    ImageView preview;

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

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.previewImg);
        imageView.setImage(ImageSource.resource(R.drawable.example));

       // preview = (ImageView)findViewById(R.id.previewImg);
       // Glide.with(PreveiwImage.this).load(R.drawable.example).into(preview);
       // Glide.with(PreveiwImage.this).load(R.drawable.example).fi
       // Picasso.with(PreveiwImage.this).load(R.drawable.example).fit().centerCrop().into(preview);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
