package com.example.ton.furnitureapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.FileProvider;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.EmployeesModel;
import Model.ManuInspectImageModel;
import Model.ManuInspectModel;
import resource.AsyncTaskSave;
import resource.CreateFile;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static AlbumsAdapter adapter;
    private List<Album> albumList;
    private ImageView mainPic, listFurnitureBtn;
    private int headerImage = 1001, headerPickImage = 1002;
    private File file;
    private Uri uri;
    private LinearLayout basicInfo;
    private FloatingActionMenu menuFab;
    private FloatingActionButton fabAdd, fabSwap, fabRemove, fabRemoveHeader;
    private ImageButton saveBtn, saveAndSendBtn, pickImg;
    private TextView dateV, cusNoV, itemV, colorV, coV, inspV, mailV;
    private ManuInspectModel manuInspectModel = new ManuInspectModel();
    public static Bitmap bitmap;
    public String getFileNameHeader = "";
    private String docNo;
    private RelativeLayout mainLayout, swapLayout, removeLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appbar;
    private Button doneswap, doneremove, onfirmRemove;
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
        actionClass = new ActionClass();

        if (intent.hasExtra("docNo")) {
            Bundle bundle = getIntent().getExtras();
            if (!bundle.getString("docNo").equals(null)) {
                docNo = bundle.getString("docNo");

                MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                        .content("Loading...")
                        .positiveText("");

                final MaterialDialog dialog = builder.build();
                dialog.show();


                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createGirdViewEdit(docNo);
                        actionClass.setActionMode("EDIT");
                        dialog.dismiss();
                    }
                }, 1500);

            }
        } else {
            actionClass.setActionMode("CREATE");
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
        fabRemoveHeader.setOnClickListener(onClickFabRemoveHeader);
        doneswap.setOnClickListener(onClickDoneSwap);
        doneremove.setOnClickListener(onClickDoneRemove);
        onfirmRemove.setOnClickListener(onClickConfirmRemove);


        //InfoHeader
        dateV.setText(BasicInfomation.getFileHeader_date());
        cusNoV.setText(BasicInfomation.getFileHeader_customerNo());
        itemV.setText(BasicInfomation.getFileHeader_itemNo());
        colorV.setText(BasicInfomation.getFileHeader_colorNo());
        coV.setText(BasicInfomation.getFileHeader_coNo());
        inspV.setText(EmployeesModel.employeeName);

        String sentMailStatus = "";

        if (BasicInfomation.getFileHeader_mail() != null) {
            sentMailStatus = BasicInfomation.getFileHeader_mail();
        }


        mailV.setText(sentMailStatus);

        if (!sentMailStatus.equals("")) {
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


        /*if(!CreateFile.headerFilePath.equals("")) {
            file = new File(CreateFile.headerFilePath);
            Picasso.with(Home.this).load(Uri.fromFile(file)).fit().centerCrop().into(mainPic);
            Toast.makeText(getApplicationContext(),CreateFile.headerFilePath,Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"TEST",Toast.LENGTH_LONG).show();
        }*/
    }

    private void setView() {
        mainLayout = findViewById(R.id.mainLayout);
        swapLayout = findViewById(R.id.swapLayout);
        removeLayout = findViewById(R.id.removeLayout);
        appbar = findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mainPic = (ImageView) findViewById(R.id.mainpic);
        basicInfo = (LinearLayout) findViewById(R.id.info);
        listFurnitureBtn = (ImageView) findViewById(R.id.listFunitueBtn);
        menuFab = (FloatingActionMenu) findViewById(R.id.menu_red);
        saveBtn = (ImageButton) findViewById(R.id.saveBth);
        saveAndSendBtn = (ImageButton) findViewById(R.id.saveAndSendBtn);
        pickImg = (ImageButton) findViewById(R.id.pickImage);
        fabAdd = findViewById(R.id.fabAdd);
        fabSwap = findViewById(R.id.fabSwap);
        fabRemove = findViewById(R.id.fabRemove);
        fabRemoveHeader= findViewById(R.id.fabRemoveHeader);
        doneswap = findViewById(R.id.swapdone);
        doneremove = findViewById(R.id.doneremove);
        onfirmRemove = findViewById(R.id.confirmRemove);


        dateV = (TextView) findViewById(R.id.dateV);
        cusNoV = (TextView) findViewById(R.id.cusNoV);
        itemV = (TextView) findViewById(R.id.itemV);
        colorV = (TextView) findViewById(R.id.colorV);
        coV = (TextView) findViewById(R.id.coV);
        inspV = (TextView) findViewById(R.id.inspV);
        mailV = (TextView) findViewById(R.id.mailV);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "RESUMED", Toast.LENGTH_SHORT).show();
        dateV.setText(BasicInfomation.getFileHeader_date());
        cusNoV.setText(BasicInfomation.getFileHeader_customerNo());
        itemV.setText(BasicInfomation.getFileHeader_itemNo());
        colorV.setText(BasicInfomation.getFileHeader_colorNo());
        coV.setText(BasicInfomation.getFileHeader_coNo());
        inspV.setText(EmployeesModel.employeeName);


    }

    private View.OnClickListener onClickSaveBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if
            // (bitmap != null) {
            if (BasicInfomation.FileHeader_date.equals("")
                    || BasicInfomation.FileHeader_customerNo.equals("")
                    || BasicInfomation.FileHeader_itemNo.equals("")
                    || BasicInfomation.FileHeader_colorNo.equals("")
                    || BasicInfomation.FileHeader_coNo.equals("")) {

                Toast.makeText(Home.this, R.string.alertInfo, Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(Home.this)
                        .setTitle("Save")
                        .setMessage("Would you like to save ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (docNo == null) {
                                    docNo = "0";
                                }


                                AsyncTaskSave atlLogin = new AsyncTaskSave(Home.this, manuInspectModel, getDeviceImei(Home.this), albumList, getFileNameHeader, docNo, false);
                                atlLogin.execute();
                                dialog.dismiss();
                                if (bitmap != null) {
                                    bitmap.recycle();
                                }

                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }

            //} else {
            //    Toast.makeText(Home.this, R.string.alertInfo, Toast.LENGTH_LONG).show();
            //}

        }
    };
    private View.OnClickListener onClickSaveAndSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //if (bitmap != null) {
            if (BasicInfomation.getFileHeader_date().equals("")
                    || BasicInfomation.getFileHeader_customerNo().equals("")
                    || BasicInfomation.getFileHeader_itemNo().equals("")
                    || BasicInfomation.getFileHeader_colorNo().equals("")
                    || BasicInfomation.getFileHeader_coNo().equals("")) {

                Toast.makeText(Home.this, R.string.alertInfo, Toast.LENGTH_SHORT).show();

            } else {
                new AlertDialog.Builder(Home.this)
                        .setTitle("Send Mail")
                        .setMessage("Would you like to send E-Mail?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (docNo == null) {
                                    docNo = "0";
                                }



                                AsyncTaskSave atlLogin = new AsyncTaskSave(Home.this, manuInspectModel, getDeviceImei(Home.this), albumList, getFileNameHeader, docNo, true);
                                atlLogin.execute();
                                dialog.dismiss();
                                //bitmap.recycle();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }

            //} else {
            //    Toast.makeText(Home.this, R.string.alertInfo, Toast.LENGTH_LONG).show();
            //}
        }
    };

    private View.OnClickListener onClickPickImg = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, headerPickImage);
        }
    };

    private View.OnClickListener onClickFabRemoveHeader = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(Home.this)
                    .setTitle("Remove Header Photo")
                    .setMessage("Would you like to remove ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            getFileNameHeader = null;
                            CreateFile.headerFilePath = "";
                            Picasso.with(getApplicationContext()).load(R.drawable.camera2).noFade()
                                    .fit().centerCrop().into(mainPic);
                            menuFab.close(true);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
    };


    private View.OnClickListener onClickListFurniture = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean detailExist = false;

            for(int i=0; i<Album.DETAIL_FILENAME.length; i++){
                if(Album.DETAIL_FILENAME[i] != null){
                    detailExist = true;
                }
            }

            if(detailExist
                    || !BasicInfomation.FileHeader_date.equals("")
                    || !BasicInfomation.FileHeader_customerNo.equals("")
                    || !BasicInfomation.FileHeader_itemNo.equals("")
                    || !BasicInfomation.FileHeader_colorNo.equals("")
                    || !BasicInfomation.FileHeader_coNo.equals("")
                    || !getFileNameHeader.equals("")) {


                new AlertDialog.Builder(Home.this)
                        .setTitle("Your data is not now saved !!")
                        .setMessage("Would you like to view all files?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                BasicInfomation.setFileHeader_date("");
                                BasicInfomation.setFileHeader_coNo("");
                                BasicInfomation.setFileHeader_colorNo("");
                                BasicInfomation.setFileHeader_itemNo("");
                                BasicInfomation.setFileHeader_customerNo("");
                                BasicInfomation.setFileHeader_mail("");
                                Album.DETAIL_FILENAME = new String[100];
                                //Album.DETAIL_BITMAP = new Bitmap[100];
                                Album.DETAIL_MEMO = new String[100];
                                getFileNameHeader = "";

                                Intent listFuniture = new Intent(Home.this, Allfile.class);
                                startActivity(listFuniture);
                                System.gc();
                                albumList.clear();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            } else {
                Intent listFuniture = new Intent(Home.this, Allfile.class);
                startActivity(listFuniture);
                System.gc();
                albumList.clear();
            }

            //finish();
        }
    };

    private View.OnClickListener onClickBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Home.this, Infomation.class);
            i.putExtra("date", BasicInfomation.getFileHeader_date());
            i.putExtra("cusNo", BasicInfomation.getFileHeader_customerNo());
            i.putExtra("itemNo", BasicInfomation.getFileHeader_itemNo());
            i.putExtra("colorNo", BasicInfomation.getFileHeader_colorNo());
            i.putExtra("coNo", BasicInfomation.getFileHeader_coNo());
            i.putExtra("inspector", BasicInfomation.getFileHeader_inspector());
            i.putExtra("ACTION_MODE", actionClass.getActionMode());
            startActivity(i);
        }
    };

    private View.OnClickListener onClickTakePic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = CreateFile.createUnique();
            uri = FileProvider.getUriForFile(Home.this, BuildConfig.APPLICATION_ID + ".provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, headerImage);


        }
    };
    private View.OnClickListener onClickFabAdd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Intent home = new Intent(Home.this, Home.class);
            BasicInfomation.setFileHeader_date("");
            BasicInfomation.setFileHeader_coNo("");
            BasicInfomation.setFileHeader_colorNo("");
            BasicInfomation.setFileHeader_itemNo("");
            BasicInfomation.setFileHeader_customerNo("");
            BasicInfomation.setFileHeader_mail("");

            Album.DETAIL_FILENAME = new String[100];
            //Album.DETAIL_BITMAP = new Bitmap[100];
            Album.DETAIL_MEMO = new String[100];
            //startActivity(home);
            //finish();

            getFileNameHeader = "";
            CreateFile.headerFilePath = "";
            Picasso.with(getApplicationContext()).load(R.drawable.camera2).noFade()
                    .fit().centerCrop().into(mainPic);

            dateV.setText("");
            cusNoV.setText("");
            itemV.setText("");
            colorV.setText("");
            coV.setText("");
            mailV.setText("");

            adapter.notifyItemRangeChanged(0, 99);
            menuFab.close(true);
        }
    };

    private View.OnClickListener onClickFabSwap = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            swapLayout.setVisibility(RelativeLayout.ABOVE);
            mainLayout.setVisibility(RelativeLayout.GONE);
            removeLayout.setVisibility(RelativeLayout.GONE);
            menuFab.hideMenu(true);
            menuFab.close(true);


            actionClass.setOnSwap(true);
            adapter.setOnSwap(actionClass.getOnSwap());
            //adapter.notifyDataSetChanged();
            adapter.notifyItemRangeChanged(0, 99);
        }
    };

    private View.OnClickListener onClickFabRemove = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            removeLayout.setVisibility(RelativeLayout.ABOVE);
            mainLayout.setVisibility(RelativeLayout.GONE);
            swapLayout.setVisibility(RelativeLayout.GONE);
            menuFab.hideMenu(true);
            menuFab.close(true);

            actionClass.setOnRemove(true);
            adapter.setOnRemove(actionClass.getOnRemove());
            //adapter.notifyDataSetChanged();
            adapter.notifyItemRangeChanged(0, 99);
        }
    };

    private View.OnClickListener onClickDoneSwap = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            swapLayout.setVisibility(RelativeLayout.GONE);
            mainLayout.setVisibility(RelativeLayout.ABOVE);
            removeLayout.setVisibility(RelativeLayout.GONE);
            menuFab.showMenu(true);

            actionClass.setOnSwap(false);
            adapter.setOnSwap(actionClass.getOnSwap());
            //adapter.notifyDataSetChanged();
            adapter.notifyItemRangeChanged(0, 99);
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
                            swapLayout.setVisibility(RelativeLayout.GONE);
                            mainLayout.setVisibility(RelativeLayout.ABOVE);
                            removeLayout.setVisibility(RelativeLayout.GONE);
                            menuFab.showMenu(true);

                            for (int i = 0; i < Album.DETAIL_REMOVED_INDEX.size(); i++) {
                                //Album.DETAIL_BITMAP[Album.DETAIL_REMOVED_INDEX.get(i)] = null;
                                Album.DETAIL_MEMO[Album.DETAIL_REMOVED_INDEX.get(i)] = null;
                                Album.DETAIL_FILENAME[Album.DETAIL_REMOVED_INDEX.get(i)] = null;
                            }

                            Album.DETAIL_REMOVED_INDEX = new ArrayList<Integer>();

                            actionClass.setOnRemove(false);
                            adapter.setOnRemove(actionClass.getOnRemove());
                            //adapter.notifyDataSetChanged();
                            adapter.notifyItemRangeChanged(0, 99);
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
            menuFab.showMenu(true);

            actionClass.setOnRemove(false);
            adapter.setOnRemove(actionClass.getOnRemove());
            //adapter.notifyDataSetChanged();
            adapter.notifyItemRangeChanged(0, 99);
        }
    };


    public static void updateView() {
        adapter.notifyItemRangeChanged(0, 99);
        //adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == headerImage) {

            try {
                if (file != null && resultCode == RESULT_OK) {
                    //bitmap = BitmapManager.decode(file.getPath(), 300, 350);
                    getFileNameHeader = CreateFile.getFileName();
                    //CreateFile.headerFilePath = CreateFile.getFilePath();
                    Picasso.with(Home.this).load(Uri.fromFile(file)).fit().centerCrop().into(mainPic);
                    mainPic.setAlpha((float) 1.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == 1003 || requestCode == headerPickImage) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();

                Bitmap bitmap2 = null;

                try {
                    bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                progressDialog = ProgressDialog.show(Home.this, "",
                        "Loading...", true);

                final Bitmap finalBitmap = bitmap2;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ImageView setImgView = null;
                        if (requestCode == headerPickImage) {
                            setImgView = mainPic;
                        }

                        CreateFileFromBitmap createFileFromBitmap = new CreateFileFromBitmap(Home.this, finalBitmap, getApplicationContext(), setImgView);
                        createFileFromBitmap.execute();
                        progressDialog.dismiss();
                    }
                }, 2500);
            }

        } else {
            if (Album.DETAIL_FILE != null) {
                if (resultCode == RESULT_OK) {
                    Log.d("POSITION : ", String.valueOf(requestCode));
                    //Bitmap bitmap = BitmapManager.decode(Album.DETAIL_FILE.getPath(), 1000, 1500);
                    //Album.DETAIL_BITMAP[requestCode] = bitmap;
                    Album.DETAIL_FILENAME[requestCode] = CreateFile.getFileName();
                    updateView();
                }
            }
        }
    }

    public void setFileHeader(String fileName){
        getFileNameHeader = fileName;
    }

    private void initCollapsingToolbar() {
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        ///appBarLayout.setExpanded(true);


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

    private void createGirdViewEdit(String docNo) {
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

        appbar.setExpanded(true);
        recyclerView.setNestedScrollingEnabled(false);

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
                Toast.makeText(Home.this, "direction" + direction, Toast.LENGTH_LONG).show();
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
                //adapter.notifyDataSetChanged();
                adapter.notifyItemRangeChanged(0, 99);
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
        ManuInspectModel manuInspectModel = helper.getDataForUpdate(no);
        getFileNameHeader = manuInspectModel.getMpImagePath();
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera" + File.separator + manuInspectModel.getMpImagePath());
        //Bitmap bitmapEdit = BitmapManager.decode(file.getPath(),300,350);
        //Bitmap bitmapEdit = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera" + File.separator + manuInspectModel.getMpImagePath());

        if (manuInspectModel.getMpImagePath() != null) {
            //bitmap = bitmapEdit;
            //Picasso.with(Home.this).load(Uri.fromFile(file)).fit().centerCrop().into(mainPic);
            Glide.with(Home.this).load(Uri.fromFile(file)).override(mainPic.getDrawable().getIntrinsicWidth(), mainPic.getDrawable().getIntrinsicHeight()).fitCenter().centerCrop().into(mainPic);
            //mainPic.setImageBitmap(bitmapEdit);
            mainPic.setAlpha((float) 1.0);
            //bitmapEdit = null;
        } else {
            ImageView mainPicEdt = (ImageView) findViewById(R.id.mainpic);
            Picasso.with(Home.this).load(R.drawable.camera2).fit().centerCrop().noFade().into(mainPicEdt);
        }
        if (manuInspectModel.getMpLastSendmailByUserName() != null) {
            status = "Sent";
        } else {
            status = "Not Sent";
        }
        Log.d("XXXXXX", manuInspectModel.getMpDocDate().toString());
        BasicInfomation.setFileHeader_date(manuInspectModel.getMpDocDate().toString());
        BasicInfomation.setFileHeader_customerNo(manuInspectModel.getMpCustomerNo());
        BasicInfomation.setFileHeader_itemNo(manuInspectModel.getMpItemNo());
        BasicInfomation.setFileHeader_colorNo(manuInspectModel.getMpColorNo());
        BasicInfomation.setFileHeader_coNo(manuInspectModel.getMpCoNo());
        BasicInfomation.setFileHeader_inspector(EmployeesModel.employeeName);
        BasicInfomation.setFileHeader_mail(status);


        //InfoHeader
        dateV.setText(BasicInfomation.getFileHeader_date());
        cusNoV.setText(BasicInfomation.getFileHeader_customerNo());
        itemV.setText(BasicInfomation.getFileHeader_itemNo());
        colorV.setText(BasicInfomation.getFileHeader_colorNo());
        coV.setText(BasicInfomation.getFileHeader_coNo());
        inspV.setText(BasicInfomation.getFileHeader_inspector());
        mailV.setText(BasicInfomation.getFileHeader_mail());

        if (mailV.getText().toString().equals("Sent")) {
            mailV.setBackgroundColor(Color.parseColor("#FFFFFF"));
            mailV.setTextColor(Color.parseColor("#52b071"));
        } else {
            mailV.setTextColor(Color.parseColor("#C3333A"));
            mailV.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        Album b;
        String coverStr = "xxx";
        int[] cover = new int[99];
        for (int i = 0; i < cover.length; i++) {
            cover[i] = R.drawable.camera2;
            b = new Album(coverStr, i, cover[i]);
            albumList.add(b);
        }

        List<ManuInspectImageModel> dtlList = new ArrayList();
        dtlList = manuInspectModel.getManuInspectImageModelList();
        for (int i = 0; i < dtlList.size(); i++) {
            ManuInspectImageModel manuInspectImageModel = dtlList.get(i);
            File fileIm = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera" + File.separator + manuInspectImageModel.getMpgImagePath());
            //Bitmap bitmap = BitmapManager.decode(fileIm.getPath(), 1000, 1500);
            //Drawable d = new BitmapDrawable(getResources(), bitmap);
            int reqCode = Integer.parseInt(manuInspectImageModel.getMpgDocSeq());
            //Album.DETAIL_BITMAP[reqCode] = bitmap;
            Album.DETAIL_FILENAME[reqCode] = manuInspectImageModel.getMpgImagePath();
            Album.DETAIL_MEMO[reqCode] = manuInspectImageModel.getMpgMemo();
        }

        adapter.notifyItemRangeChanged(0, dtlList.size());
    }

    private void createGirdView() {
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
        appbar.setExpanded(true);
        recyclerView.setNestedScrollingEnabled(false);

        // Extend the Callback class Drag Card View
        /*ItemTouchHelper.Callback ithCallback = new ItemTouchHelper.Callback() {

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
                Toast.makeText(Home.this, "direction" + direction, Toast.LENGTH_LONG).show();
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
        };*/

        //ItemTouchHelper ith = new ItemTouchHelper(ithCallback);
        //ith.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        prepareAlbums();
    }

    private void prepareAlbums() {

        Album b;
        String coverStr = "Test";
        int[] cover = new int[99];
        for (int i = 0; i < cover.length; i++) {
            cover[i] = R.drawable.camera2;
            b = new Album(coverStr, i, cover[i]);
            albumList.add(b);
        }

        adapter.notifyItemRangeChanged(0, cover.length);
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
                            Intent i = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            BasicInfomation.setFileHeader_date("");
                            BasicInfomation.setFileHeader_inspector("");
                            BasicInfomation.setFileHeader_coNo("");
                            BasicInfomation.setFileHeader_colorNo("");
                            BasicInfomation.setFileHeader_itemNo("");
                            BasicInfomation.setFileHeader_customerNo("");
                            BasicInfomation.setFileHeader_mail("");
                            Album.DETAIL_FILENAME = new String[100];
                            //Album.DETAIL_BITMAP = new Bitmap[100];
                            Album.DETAIL_MEMO = new String[100];
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
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

    /**
     * IMEI
     */
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

    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}
