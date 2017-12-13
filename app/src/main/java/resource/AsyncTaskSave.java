package resource;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.ton.furnitureapplication.Album;
import com.example.ton.furnitureapplication.Allfile;
import com.example.ton.furnitureapplication.BasicInfomation;
import com.example.ton.furnitureapplication.DatabaseHelper;
import com.example.ton.furnitureapplication.Home;

import java.util.ArrayList;
import java.util.List;

import Model.ManuInspectImageModel;
import Model.ManuInspectModel;
import Model.TBUserLoginModel;

public class AsyncTaskSave extends AsyncTask<String, Void, String> {
    private Activity activity;

    private ProgressDialog progressDialog;
    private String Imei;
    private boolean SAVE_STATUS = false;
    private Handler handler = new Handler();
    ManuInspectModel manuInspectModel;
    BasicInfomation basicInfomation;
    List<Album> albumList;


    public AsyncTaskSave(Activity a, ManuInspectModel manuInspectModel, BasicInfomation basicInfomation,String Imei,List<Album> albumList){
        this.activity = a;
        this.manuInspectModel = manuInspectModel;
        this.basicInfomation = basicInfomation;
        this.albumList = albumList;
        this.Imei = Imei;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity, "",
                "Loading. Please wait...", true);
    }

    protected String doInBackground(String... urls)   {
        String result="";
        new Thread(new Runnable() {
            @Override
            public void run() {
                /// เขียนโค้ด Login ไว้ตรงนี้และเซ็ทตัวแปร Login Status
                /// Return from SQLite >> LOGIN_STATUS = true หรือ false
                /// Save
                if (Imei.equals("")){
                    SAVE_STATUS = false;
                }else {
                    List<ManuInspectImageModel> manuInspectImageModelList = new ArrayList<>();
                    //Header
                    manuInspectModel.setMpDocCode("IN01");
                    manuInspectModel.setMpDocument(Imei);
                    manuInspectModel.setMpDocBranch("01");
                    manuInspectModel.setMpDocSeq("0");
                    manuInspectModel.setMpDocDate(basicInfomation.getFileHeader_date());
                    manuInspectModel.setMpCustomerNo(basicInfomation.getFileHeader_customerNo());
                    manuInspectModel.setMpItemNo(basicInfomation.getFileHeader_itemNo());
                    manuInspectModel.setMpColorNo(basicInfomation.getFileHeader_colorNo());
                    manuInspectModel.setMpCoNo(basicInfomation.getFileHeader_coNo());
                    manuInspectModel.setMpEmployeeName(basicInfomation.getFileHeader_inspector());
                    Log.d("album.DETAIL_FILE : ", String.valueOf(basicInfomation.getFileHeader_itemNo()));

                    //Detail
                    for (int i = 0; i < albumList.size(); i++) {
                        Album album = albumList.get(i);
                        if (album.DETAIL_BITMAP[i] != null) {
                            ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
                            manuInspectImageModel.setMpgDoccode(manuInspectModel.getMpDocCode());
                            manuInspectImageModel.setMpgDocument(manuInspectModel.getMpDocument());
                            manuInspectImageModel.setMpgDocBranch(manuInspectModel.getMpDocBranch());
                            manuInspectImageModel.setMpgDocSeq(String.valueOf(i));
                            manuInspectImageModel.setMpgMemo(album.getName());
                            manuInspectImageModel.setMpgImagePath(album.getFileName());
                            manuInspectImageModelList.add(manuInspectImageModel);
                            Log.d("album.DETAIL_FILE : ", String.valueOf(i));
                            Log.d("album.DETAIL_FILE : ", String.valueOf(album.DETAIL_BITMAP[i]));
                            manuInspectModel.setManuInspectImageModelList(manuInspectImageModelList);
                        }
                    }

                    DatabaseHelper dbHelper = new DatabaseHelper(activity);
                    dbHelper.save(manuInspectModel);
                    SAVE_STATUS = true;
                    clearActivity(activity);
                }

                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();

                    if (SAVE_STATUS == true) {
                        // ให้ไปยังหน้าหลัก
                        activity.finish();
                        Intent intent = new Intent(activity, Home.class);
                        activity.startActivity(intent);
                    } else if (SAVE_STATUS == false) {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(activity, "Can't Save", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    }
                }, 1500);
            }
        }).start();
        return result;
    }

    protected void onPostExecute(String result)  {
        
    }
    private void clearActivity(Activity activity){
        //bitmap = null;
        //bitmapDtl = null;
        //file = null;
        Album.DETAIL_FILE = null;
        Album.DETAIL_BITMAP = new Bitmap[100];
        Album album = new Album();
        ManuInspectModel manuInspectModel = new ManuInspectModel();
        ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
        basicInfomation.setFileHeader_date(null);
        basicInfomation.setFileHeader_inspector(null);
        basicInfomation.setFileHeader_coNo(null);
        basicInfomation.setFileHeader_colorNo(null);
        basicInfomation.setFileHeader_itemNo(null);
        basicInfomation.setFileHeader_customerNo(null);
    }

}
