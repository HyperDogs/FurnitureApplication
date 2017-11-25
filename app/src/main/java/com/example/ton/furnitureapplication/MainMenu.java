package com.example.ton.furnitureapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import resource.CreateFile;

public class MainMenu extends AppCompatActivity {
    //@BindView(R.id.changeLang_btn) Button changeLang_btn;
    //@BindView(R.id.canvasView) FrameLayout canvas;
    @BindView(R.id.basic_info_block)
    LinearLayout basicInfoBlock;
    ImageView mainPic;
    int headerImage = 1001;
    File file;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
         /*Configuration config = new Configuration();
        config.locale = new Locale("th");
        getResources().updateConfiguration(config, null);
        onCreate(null);*/

        mainPic = (ImageView)findViewById(R.id.product_photo_imv);
        //ver Btn
        mainPic.setOnClickListener(onClickTakePic);


    }

    @OnClick(R.id.basic_info_block)
    public void changeLanguage_onClick() {
        Intent basicInfo = new Intent(MainMenu.this, BasicInfoActivity.class);
        startActivity(basicInfo);
    }


    private View.OnClickListener onClickTakePic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = CreateFile.createUnique();
            uri = FileProvider.getUriForFile(MainMenu.this,BuildConfig.APPLICATION_ID + ".provider",file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, headerImage);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == headerImage && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap  = BitmapFactory.decodeFile(file.getPath());
                Picasso.with(MainMenu.this).load(getImageUri(MainMenu.this,bitmap)).fit().centerCrop().into(mainPic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}

