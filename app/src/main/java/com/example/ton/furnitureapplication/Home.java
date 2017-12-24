package com.example.ton.furnitureapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import Model.EmployeesModel;
import Model.ManuInspectImageModel;
import Model.ManuInspectModel;
import okhttp3.FormBody;
import resource.AsyncTaskLogin;
import resource.AsyncTaskSave;
import resource.BitmapManager;
import resource.CreateFile;

public class Home extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private static AlbumsAdapter adapter;
    private List<Album> albumList;
    private  ImageView mainPic,listFurnitureBtn;
    private int headerImage = 1001,headerPickImage = 1002;
    private File file;
    private Uri uri;
    private LinearLayout basicInfo;
    private FloatingActionMenu menuFab;
    private FloatingActionButton fabAdd,fabSwap,fabRemove;
    private ImageButton saveBtn,saveAndSendBtn,pickImg;
    private TextView dateV,cusNoV,itemV,colorV,coV,inspV,mailV;
    private BasicInfomation basicInfomation;
    private ManuInspectModel manuInspectModel = new ManuInspectModel();
    private ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
    private Bitmap bitmap, bitmapDtl;
    private String docNo,getFileNameHeader;
    private RelativeLayout mainLayout,swapLayout,removeLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appbar;
    private Button doneswap,doneremove,onfirmRemove;
    private boolean headImgStatus = false;
    private ActionClass actionClass;
    private ProgressDialog progressDialog;
    private Handler mHandler = new Handler();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        setView();
        initCollapsingToolbar();
        if(intent.hasExtra("docNo")){
            Bundle bundle  = getIntent().getExtras();
            if(!bundle.getString("docNo").equals(null)){
                docNo = bundle.getString("docNo");


                progressDialog = ProgressDialog.show(Home.this, "",
                        "Loading...", true);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createGirdViewEdit(docNo);
                        progressDialog.dismiss();
                    }
                }, 4500);

            }
        }else {

            createGirdView();
        }
        //Header
        mainPic.setOnClickListener(onClickTakePic);
        basicInfo.setOnClickListener(onClickBasicInfo);
        listFurnitureBtn.setOnClickListener(onClickListFurniture);
        menuFab.setClosedOnTouchOutside(true);
        saveBtn.setOnClickListener(onClickSaveBtn);
        saveAndSendBtn.setOnClickListener(onClickSaveAndSend);
        pickImg.setOnClickListener(onClickPickImg);
        fabAdd.setOnClickListener(onClickFabAdd);
        fabSwap.setOnClickListener(onClickFabSwap);
        fabRemove.setOnClickListener(onClickFabRemove);
        doneswap.setOnClickListener(onClickDoneSwap);
        doneremove.setOnClickListener(onClickDoneRemove);
        onfirmRemove.setOnClickListener(onClickConfirmRemove);


        //InfoHeader
        dateV.setText(basicInfomation.getFileHeader_date());
        cusNoV.setText(basicInfomation.getFileHeader_customerNo());
        itemV.setText(basicInfomation.getFileHeader_itemNo());
        colorV.setText(basicInfomation.getFileHeader_colorNo());
        coV.setText(basicInfomation.getFileHeader_coNo());
        inspV.setText(EmployeesModel.employeeName);

        String sentMailStatus = "";

        if(basicInfomation.getFileHeader_mail() != null){
            sentMailStatus = basicInfomation.getFileHeader_mail();
        }


        mailV.setText(sentMailStatus);

        if(!sentMailStatus.equals("")) {
            if (sentMailStatus.equals("Sent")) {
                mailV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mailV.setTextColor(Color.parseColor("#52b071"));
            } else {
                mailV.setTextColor(Color.parseColor("#C3333A"));
                mailV.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        } else {
            mailV.setBackgroundColor(Color.parseColor("#00000000"));
        }

        actionClass = new ActionClass();
    }

    private void setView(){
        mainLayout = findViewById(R.id.mainLayout);
        swapLayout = findViewById(R.id.swapLayout);
        removeLayout = findViewById(R.id.removeLayout);
        appbar = findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mainPic = (ImageView)findViewById(R.id.mainpic);
        basicInfo = (LinearLayout) findViewById(R.id.info);
        listFurnitureBtn = (ImageView) findViewById(R.id.listFunitueBtn);
        menuFab = (FloatingActionMenu)findViewById(R.id.menu_red);
        saveBtn = (ImageButton) findViewById(R.id.saveBth);
        saveAndSendBtn = (ImageButton) findViewById(R.id.saveAndSendBtn);
        pickImg = (ImageButton) findViewById(R.id.pickImage);
        fabAdd = findViewById(R.id.fabAdd);
        fabSwap = findViewById(R.id.fabSwap);
        fabRemove = findViewById(R.id.fabRemove);
        doneswap =findViewById(R.id.swapdone);
        doneremove = findViewById(R.id.doneremove);
        onfirmRemove = findViewById(R.id.confirmRemove);


        dateV = (TextView)findViewById(R.id.dateV);
        cusNoV = (TextView)findViewById(R.id.cusNoV);
        itemV = (TextView)findViewById(R.id.itemV);
        colorV = (TextView)findViewById(R.id.colorV);
        coV = (TextView)findViewById(R.id.coV);
        inspV = (TextView)findViewById(R.id.inspV);
        mailV = (TextView)findViewById(R.id.mailV);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dateV.setText(basicInfomation.getFileHeader_date());
        cusNoV.setText(basicInfomation.getFileHeader_customerNo());
        itemV.setText(basicInfomation.getFileHeader_itemNo());
        colorV.setText(basicInfomation.getFileHeader_colorNo());
        coV.setText(basicInfomation.getFileHeader_coNo());
        inspV.setText(EmployeesModel.employeeName);
    }

    private View.OnClickListener onClickSaveBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (bitmap != null || headImgStatus){
                if (basicInfomation.getFileHeader_date().equals("")
                        || basicInfomation.getFileHeader_customerNo().equals("")
                        || basicInfomation.getFileHeader_itemNo().equals("")
                        || basicInfomation.getFileHeader_colorNo().equals("")
                        || basicInfomation.getFileHeader_coNo().equals("")) {

                    Toast.makeText(Home.this,"กรณากรอกข้อมูลสินค้าให้ครบถ้วน",Toast.LENGTH_SHORT).show();

                }else {
                    new AlertDialog.Builder(Home.this)
                            .setTitle("Save")
                            .setMessage("Would you like to save ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (docNo == null){
                                        docNo = "0";
                                    }
                                    AsyncTaskSave atlLogin = new AsyncTaskSave(Home.this,manuInspectModel,basicInfomation,getDeviceImei(Home.this),albumList,getFileNameHeader,docNo, false);
                                    atlLogin.execute();
                                    dialog.dismiss();
                                    if (bitmap != null){
                                    bitmap.recycle();
                                    }

                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }

            }else{
                Toast.makeText(Home.this,R.string.alertInfo,Toast.LENGTH_LONG).show();
            }

        }
    };
    private View.OnClickListener onClickSaveAndSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (bitmap != null){
                if (basicInfomation.getFileHeader_date().equals("")
                        || basicInfomation.getFileHeader_customerNo().equals("")
                        || basicInfomation.getFileHeader_itemNo().equals("")
                        || basicInfomation.getFileHeader_colorNo().equals("")
                        || basicInfomation.getFileHeader_coNo().equals("")) {

                    Toast.makeText(Home.this,R.string.alertInfo,Toast.LENGTH_SHORT).show();

                }else {
                    new AlertDialog.Builder(Home.this)
                            .setTitle("Send Mail")
                            .setMessage("Would you like to send E-Mail?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (docNo == null){
                                        docNo = "0";
                                    }
                                    AsyncTaskSave atlLogin = new AsyncTaskSave(Home.this, manuInspectModel, basicInfomation, getDeviceImei(Home.this), albumList, getFileNameHeader, docNo, true);
                                    atlLogin.execute();
                                    dialog.dismiss();
                                    bitmap.recycle();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }

            }else{
                Toast.makeText(Home.this,R.string.alertInfo,Toast.LENGTH_LONG).show();
            }
        }
    };

    private View.OnClickListener onClickPickImg = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , headerPickImage);
        }
    };
    private View.OnClickListener onClickListFurniture = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent listFuniture = new Intent(Home.this,Allfile.class);
            startActivity(listFuniture);
            System.gc();
            albumList.clear();
            //finish();
        }
    };

    private  View.OnClickListener onClickBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Home.this,info.class);
            i.putExtra("date",basicInfomation.getFileHeader_date());
            i.putExtra("cusNo",basicInfomation.getFileHeader_customerNo());
            i.putExtra("itemNo",basicInfomation.getFileHeader_itemNo());
            i.putExtra("colorNo",basicInfomation.getFileHeader_colorNo());
            i.putExtra("coNo",basicInfomation.getFileHeader_coNo());
            i.putExtra("inspector",basicInfomation.getFileHeader_inspector());
            startActivity(i);

        }
    };
    private View.OnClickListener onClickTakePic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = CreateFile.createUnique();
            uri = FileProvider.getUriForFile(Home.this,BuildConfig.APPLICATION_ID + ".provider",file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, headerImage);


        }
    };
    private View.OnClickListener onClickFabAdd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent home = new Intent(Home.this,Home.class);
            BasicInfomation basicInfomation = new BasicInfomation();
            basicInfomation.setFileHeader_date(null);
            basicInfomation.setFileHeader_coNo(null);
            basicInfomation.setFileHeader_colorNo(null);
            basicInfomation.setFileHeader_itemNo(null);
            basicInfomation.setFileHeader_customerNo(null);
            basicInfomation.setFileHeader_mail(null);
            Album.DETAIL_FILENAME = new String[100];
            Album.DETAIL_BITMAP = new Bitmap[100];
            Album.DETAIL_MEMO = new String[100];
            startActivity(home);
            finish();
        }
    };

    private View.OnClickListener onClickFabSwap = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            appbar.setExpanded(true);
            swapLayout.setVisibility(RelativeLayout.ABOVE);
            mainLayout.setVisibility(RelativeLayout.GONE);
            removeLayout.setVisibility(RelativeLayout.GONE);
            recyclerView.setNestedScrollingEnabled(false);
            menuFab.hideMenu(true);
            menuFab.close(true);


            actionClass.setOnSwap(true);
            adapter.setOnSwap(actionClass.getOnSwap());
            adapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener onClickFabRemove = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            appbar.setExpanded(true);
            removeLayout.setVisibility(RelativeLayout.ABOVE);
            mainLayout.setVisibility(RelativeLayout.GONE);
            swapLayout.setVisibility(RelativeLayout.GONE);
            recyclerView.setNestedScrollingEnabled(false);
            menuFab.hideMenu(true);
            menuFab.close(true);

            actionClass.setOnRemove(true);
            adapter.setOnRemove(actionClass.getOnRemove());
            adapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener onClickDoneSwap = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            swapLayout.setVisibility(RelativeLayout.GONE);
            mainLayout.setVisibility(RelativeLayout.ABOVE);
            removeLayout.setVisibility(RelativeLayout.GONE);
            appbar.setExpanded(false);
            recyclerView.setNestedScrollingEnabled(true);
            menuFab.showMenu(true);

            actionClass.setOnSwap(false);
            adapter.setOnSwap(actionClass.getOnSwap());
            adapter.notifyDataSetChanged();
        }
    };




    private View.OnClickListener onClickConfirmRemove = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {

            new AlertDialog.Builder(Home.this)
                    .setTitle("Remove Photos")
                    .setMessage("Would you like to remove ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                            progressDialog = ProgressDialog.show(Home.this, "",
                                    "Removing. Please wait...", true);

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    swapLayout.setVisibility(RelativeLayout.GONE);
                                    mainLayout.setVisibility(RelativeLayout.ABOVE);
                                    removeLayout.setVisibility(RelativeLayout.GONE);
                                    appbar.setExpanded(false);
                                    recyclerView.setNestedScrollingEnabled(true);
                                    menuFab.showMenu(true);

                                    for(int i=0; i<Album.DETAIL_REMOVED_INDEX.size(); i++){
                                        Album.DETAIL_BITMAP[Album.DETAIL_REMOVED_INDEX.get(i)] = null;
                                    }
                                    Album.DETAIL_REMOVED_INDEX = new ArrayList<Integer>();

                                    actionClass.setOnRemove(false);
                                    adapter.setOnRemove(actionClass.getOnRemove());
                                    adapter.notifyDataSetChanged();

                                    progressDialog.dismiss();
                                }
                            }, 500);


                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();

        }
    };

    private View.OnClickListener onClickDoneRemove = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            swapLayout.setVisibility(RelativeLayout.GONE);
            mainLayout.setVisibility(RelativeLayout.ABOVE);
            removeLayout.setVisibility(RelativeLayout.GONE);
            appbar.setExpanded(false);
            recyclerView.setNestedScrollingEnabled(true);
            menuFab.showMenu(true);

            actionClass.setOnRemove(false);
            adapter.setOnRemove(actionClass.getOnRemove());
            adapter.notifyDataSetChanged();
        }
    };



    public static void updateView(){
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == headerImage) {

            try {
                if (file !=null && resultCode == RESULT_OK) {
                    bitmap = BitmapManager.decode(file.getPath(),300,350);
                    //BitmapFactory.decodeFile(file.getPath());
                    getFileNameHeader = CreateFile.getFileName();
                    Picasso.with(Home.this).load(Utility.getImageUri(Home.this, bitmap)).fit().centerCrop().into(mainPic);
                    //Picasso.with(Home.this).load(file.getPath()).fit().centerCrop().into(mainPic);
                    mainPic.setAlpha((float) 1.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestCode == headerPickImage){
            Uri selectedImage = data.getData();
            Picasso.with(Home.this).load(selectedImage).fit().centerCrop().into(mainPic);
            mainPic.setAlpha((float) 1.0);


        }else if (requestCode == 1003){
            Uri selectedImage = data.getData();
            Bundle bundle = data.getExtras();
            String position = bundle.getString("position");
            Toast.makeText(Home.this,position,Toast.LENGTH_SHORT).show();

             /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            Album.DETAIL_BITMAP[requestCode] = bitmap;
            headImgStatus = true;
            updateView();*/

        }else {
            if (Album.DETAIL_FILE != null) {
                Log.d("POSITION : ", String.valueOf(requestCode));
                Bitmap bitmap = BitmapManager.decode(Album.DETAIL_FILE.getPath(), 1000, 1500);
                Album.DETAIL_BITMAP[requestCode] = bitmap;
                Album.DETAIL_FILENAME[requestCode] = CreateFile.getFileName();
                updateView();
            }
        }
    }

    private void initCollapsingToolbar() {
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void createGirdViewEdit(String docNo){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(2000);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        // Extend the Callback class Drag Card View
        ItemTouchHelper.Callback ithCallback = new ItemTouchHelper.Callback() {

            //and in your imlpementaion of

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // get the viewHolder's and target's positions in your adapter data, swap them
                Collections.swap(albumList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // and notify the adapter that its dataset has changed
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;


            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO
                Toast.makeText(Home.this,"direction"+direction,Toast.LENGTH_LONG).show();
            }

            //defines the enabled move directions in each state (idle, swiping, dragging).
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);

            }

            @Override
            public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder selected, List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {

                return super.chooseDropTarget(selected, dropTargets, curX, curY);
            }


            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                adapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper ith = new ItemTouchHelper(ithCallback);
        ith.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        prepareAlbumsEdit(docNo);


    }
    private void prepareAlbumsEdit(String docNo) {
        String status;
        DatabaseHelper helper = new DatabaseHelper(Home.this);
        int no = Integer.parseInt(docNo);
        ManuInspectModel manuInspectModel =  helper.getDataForUpdate(no);
        getFileNameHeader = manuInspectModel.getMpImagePath();
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "DCIM" + File.separator + "Camera" + File.separator + manuInspectModel.getMpImagePath());
        //Bitmap bitmapEdit = BitmapManager.decode(file.getPath(),300,350);
        Bitmap bitmapEdit = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+File.separator + "DCIM" + File.separator + "Camera" + File.separator + manuInspectModel.getMpImagePath());

        if (manuInspectModel.getMpImagePath() !=null) {
            bitmap = bitmapEdit;
            //Picasso.with(Home.this).load(Uri.fromFile(file)).fit().centerCrop().into(mainPic);
            Glide.with(Home.this).load(Uri.fromFile(file)).override(mainPic.getDrawable().getIntrinsicWidth(),mainPic.getDrawable().getIntrinsicHeight()).fitCenter().centerCrop().into(mainPic);
            //mainPic.setImageBitmap(bitmapEdit);
            mainPic.setAlpha((float)1.0);
            bitmapEdit = null;
        }else{
            ImageView mainPicEdt  = (ImageView)findViewById(R.id.mainpic);
            Picasso.with(Home.this).load(R.drawable.camera2).fit().centerCrop().into(mainPicEdt);
        }
        if (manuInspectModel.getMpLastSendmailByUserName() != null){
            status = "Sent";
        }else {
            status = "Not Sent";
        }
        Log.d("XXXXXX",manuInspectModel.getMpDocDate().toString());
        basicInfomation.setFileHeader_date(manuInspectModel.getMpDocDate().toString());
        basicInfomation.setFileHeader_customerNo(manuInspectModel.getMpCustomerNo());
        basicInfomation.setFileHeader_itemNo(manuInspectModel.getMpItemNo());
        basicInfomation.setFileHeader_colorNo(manuInspectModel.getMpColorNo());
        basicInfomation.setFileHeader_coNo(manuInspectModel.getMpCoNo());
        basicInfomation.setFileHeader_inspector(EmployeesModel.employeeName);
        basicInfomation.setFileHeader_mail(status);


        //InfoHeader
        dateV.setText(basicInfomation.getFileHeader_date());
        cusNoV.setText(basicInfomation.getFileHeader_customerNo());
        itemV.setText(basicInfomation.getFileHeader_itemNo());
        colorV.setText(basicInfomation.getFileHeader_colorNo());
        coV.setText(basicInfomation.getFileHeader_coNo());
        inspV.setText(basicInfomation.getFileHeader_inspector());
        mailV.setText(basicInfomation.getFileHeader_mail());

            if (mailV.getText().toString().equals("Sent")) {
                mailV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mailV.setTextColor(Color.parseColor("#52b071"));
            } else {
                mailV.setTextColor(Color.parseColor("#C3333A"));
                mailV.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

        Album b;
        String coverStr = "xxx";
        int[] cover = new int[100];
        for (int i=0;i<cover.length;i++){
            cover[i] = R.drawable.camera2;
            b = new Album(coverStr,i,cover[i]);
            albumList.add(b);
        }

        List<ManuInspectImageModel> dtlList = new ArrayList();
        dtlList = manuInspectModel.getManuInspectImageModelList();
        for (int i = 0;i<dtlList.size();i++){
            ManuInspectImageModel manuInspectImageModel = dtlList.get(i);
            File fileIm = new File(Environment.getExternalStorageDirectory()+File.separator + "DCIM" + File.separator + "Camera" + File.separator + manuInspectImageModel.getMpgImagePath());
            Bitmap bitmap = BitmapManager.decode(fileIm.getPath(),1000,1500);
            //Drawable d = new BitmapDrawable(getResources(), bitmap);
            int reqCode = Integer.parseInt(manuInspectImageModel.getMpgDocSeq());
            Album.DETAIL_BITMAP[reqCode] = bitmap;
            Album.DETAIL_FILENAME[reqCode] = manuInspectImageModel.getMpgImagePath();
            Album.DETAIL_MEMO[reqCode] = manuInspectImageModel.getMpgMemo();
        }
        adapter.notifyDataSetChanged();
    }

    private void createGirdView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(2000);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        // Extend the Callback class Drag Card View
        ItemTouchHelper.Callback ithCallback = new ItemTouchHelper.Callback() {

            //and in your imlpementaion of

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // get the viewHolder's and target's positions in your adapter data, swap them
                Collections.swap(albumList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // and notify the adapter that its dataset has changed
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;


            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO
                Toast.makeText(Home.this,"direction"+direction,Toast.LENGTH_LONG).show();
            }

            //defines the enabled move directions in each state (idle, swiping, dragging).
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);

            }

            @Override
            public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder selected, List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {

                return super.chooseDropTarget(selected, dropTargets, curX, curY);
            }


            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                adapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper ith = new ItemTouchHelper(ithCallback);
        ith.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        prepareAlbums();
    }
    private void prepareAlbums() {

        Album b;
        String coverStr = "Test";
        int[] cover = new int[100];
        for (int i=0;i<cover.length;i++){
            cover[i] = R.drawable.camera2;
            b = new Album(coverStr,i,cover[i]);
            albumList.add(b);
        }

        adapter.notifyDataSetChanged();
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(Home.this)
                    .setTitle("Logout")
                    .setMessage("Would you like to logout ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            Intent i = new Intent(Home.this,MainActivity.class);
                            startActivity(i);

                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    /** IMEI*/
    @SuppressLint("MissingPermission")
    public static String getDeviceImei(Context ctx) {
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    @Override
    protected void onDestroy() {
        freeMemory();
        super.onDestroy();
    }
    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}
