package com.example.ton.furnitureapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.ManuInspectImageModel;
import Model.ManuInspectModel;
import resource.BitmapManager;
import resource.CreateFile;

public class Home extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private static AlbumsAdapter adapter;
    private List<Album> albumList;
    private  ImageView mainPic,listFurnitureBtn;
    private int headerImage = 1001;
    private File file;
    private Uri uri;
    private LinearLayout basicInfo;
    private ImageButton saveBtn,saveAndSendBtn;
    private TextView dateV,cusNoV,itemV,colorV,coV,inspV;
    private BasicInfomation basicInfomation;
    private ManuInspectModel manuInspectModel = new ManuInspectModel();
    private ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
    private Bitmap bitmap,bitmapDtl;
    private int currentPositon  =-1;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initCollapsingToolbar();
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




        //Header
        mainPic = (ImageView) findViewById(R.id.mainpic);
        mainPic.setOnClickListener(onClickTakePic);
        basicInfo = (LinearLayout) findViewById(R.id.info);
        basicInfo.setOnClickListener(onClickBasicInfo);
        listFurnitureBtn = (ImageView) findViewById(R.id.listFunitueBtn);
        listFurnitureBtn.setOnClickListener(onClickListFurniture);
        saveBtn = (ImageButton) findViewById(R.id.saveBth);
        saveBtn.setOnClickListener(onClickSaveBtn);
        saveAndSendBtn = (ImageButton)findViewById(R.id.saveAndSendBtn);
        saveAndSendBtn.setOnClickListener(onClickSaveAndSend);



        //InfoHeader
        dateV = (TextView)findViewById(R.id.dateV);
        cusNoV = (TextView)findViewById(R.id.cusNoV);
        itemV = (TextView)findViewById(R.id.itemV);
        colorV = (TextView)findViewById(R.id.colorV);
        coV = (TextView)findViewById(R.id.coV);
        inspV = (TextView)findViewById(R.id.inspV);
        dateV.setText(basicInfomation.getFileHeader_date());
        cusNoV.setText(basicInfomation.getFileHeader_customerNo());
        itemV.setText(basicInfomation.getFileHeader_itemNo());
        colorV.setText(basicInfomation.getFileHeader_colorNo());
        coV.setText(basicInfomation.getFileHeader_coNo());
        inspV.setText(basicInfomation.getFileHeader_inspector());

    }

    @Override
    protected void onResume() {
        super.onResume();
        dateV.setText(basicInfomation.getFileHeader_date());
        cusNoV.setText(basicInfomation.getFileHeader_customerNo());
        itemV.setText(basicInfomation.getFileHeader_itemNo());
        colorV.setText(basicInfomation.getFileHeader_colorNo());
        coV.setText(basicInfomation.getFileHeader_coNo());
        inspV.setText(basicInfomation.getFileHeader_inspector());
    }

    private View.OnClickListener onClickSaveBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (bitmap != null){
                if (basicInfomation.getFileHeader_date().equals("")
                        || basicInfomation.getFileHeader_customerNo().equals("")
                        || basicInfomation.getFileHeader_itemNo().equals("")
                        || basicInfomation.getFileHeader_colorNo().equals("")
                        || basicInfomation.getFileHeader_coNo().equals("")
                        || basicInfomation.getFileHeader_inspector().equals("")) {

                    Toast.makeText(Home.this,"กรณากรอกข้อมูลสินค้าให้ครบถ้วน",Toast.LENGTH_SHORT).show();

                }else {
                    new AlertDialog.Builder(Home.this)
                            .setTitle("Save")
                            .setMessage("Do you want to save ?")
                            .setIcon(R.drawable.ic_save)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    List<ManuInspectImageModel> manuInspectImageModelList = new ArrayList<>();
                                    //Header
                                    manuInspectModel.setMpDocCode("IN01");
                                    manuInspectModel.setMpDocument(getDeviceImei(Home.this));
                                    manuInspectModel.setMpDocBranch("01");
                                    manuInspectModel.setMpDocSeq("0");
                                    manuInspectModel.setMpDocDate(basicInfomation.getFileHeader_date());
                                    manuInspectModel.setMpCustomerNo(basicInfomation.getFileHeader_customerNo());
                                    manuInspectModel.setMpItemNo(basicInfomation.getFileHeader_itemNo());
                                    manuInspectModel.setMpColorNo(basicInfomation.getFileHeader_colorNo());
                                    manuInspectModel.setMpCoNo(basicInfomation.getFileHeader_coNo());
                                    manuInspectModel.setMpEmployeeName(basicInfomation.getFileHeader_inspector());

                                    //Detail
                                    for (int i = 0; i < albumList.size(); i++) {
                                        Album album =  albumList.get(i);
                                        if (album.DETAIL_BITMAP[i] != null) {
                                            ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
                                            manuInspectImageModel.setMpgDoccode(manuInspectModel.getMpDocCode());
                                            manuInspectImageModel.setMpgDocument(manuInspectModel.getMpDocument());
                                            manuInspectImageModel.setMpgDocBranch(manuInspectModel.getMpDocBranch());
                                            manuInspectImageModel.setMpgDocSeq(manuInspectModel.getMpDocSeq());
                                            manuInspectImageModel.setMpgMemo(album.getName());
                                            manuInspectImageModel.setMpgImagePath(album.getFileName());
                                            manuInspectImageModelList.add(manuInspectImageModel);
                                            Log.d("album.DETAIL_FILE : ", String.valueOf(i));
                                            Log.d("album.DETAIL_FILE : ", String.valueOf(album.DETAIL_BITMAP[i]));
                                            manuInspectModel.setManuInspectImageModelList(manuInspectImageModelList);
                                        }
                                    }

                                    DatabaseHelper dbHelper = new DatabaseHelper(Home.this);
                                    //dbHelper.insertManuInspect(manuInspectModel);
                                    dbHelper.save(manuInspectModel);
                                    Toast.makeText(Home.this, "Saved!!", Toast.LENGTH_SHORT).show();
                                    clearActivity();

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }

            }else{
                Toast.makeText(Home.this,"กรุณาใส่ข้อมูลให้ครบถ้วนก่อนบันทึก",Toast.LENGTH_LONG).show();
            }

        }
    };
    private View.OnClickListener onClickSaveAndSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatabaseHelper helper = new DatabaseHelper(Home.this);
            Toast.makeText(Home.this,""+helper.getAllManuInspect().size(),Toast.LENGTH_LONG).show();
            Toast.makeText(Home.this,""+helper.getAllManuInspectImage().size(),Toast.LENGTH_LONG).show();
        }
    };
    private View.OnClickListener onClickListFurniture = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent listFuniture = new Intent(Home.this,Allfile.class);
            startActivity(listFuniture);
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

    public static void updateView(){
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == headerImage && resultCode == RESULT_OK) {

            try {
                if (file !=null) {
                    bitmap = BitmapFactory.decodeFile(file.getPath());
                    Picasso.with(Home.this).load(Utility.getImageUri(Home.this, bitmap)).fit().centerCrop().into(mainPic);
                    mainPic.setAlpha((float) 1.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (Album.DETAIL_FILE != null) {
                Log.d("POSITION : ", String.valueOf(requestCode));
                Bitmap bitmap = BitmapManager.decode(Album.DETAIL_FILE.getPath(), 300, 350);
                Album.DETAIL_BITMAP[requestCode] = bitmap;
                updateView();
            }
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
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
    private void clearActivity(){
        bitmap = null;
        bitmapDtl = null;
        file = null;
        Album.DETAIL_FILE = null;
        Album.DETAIL_BITMAP = new Bitmap[100];
        Album album = new Album();
        ManuInspectModel manuInspectModel = new ManuInspectModel();
        ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
        basicInfomation.setFileHeader_date(null);
        basicInfomation.setFileHeader_customerNo(null);
        basicInfomation.setFileHeader_itemNo(null);
        basicInfomation.setFileHeader_colorNo(null);
        basicInfomation.setFileHeader_coNo(null);
        basicInfomation.setFileHeader_inspector(null);
        recreate();
    }

}
