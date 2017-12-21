package resource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.ton.furnitureapplication.Album;
import com.example.ton.furnitureapplication.Allfile;
import com.example.ton.furnitureapplication.BasicInfomation;
import com.example.ton.furnitureapplication.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;

import Model.EmployeesModel;
import Model.ManuInspectImageModel;
import Model.ManuInspectModel;
import Model.TBUserLoginModel;

public class AsyncTaskSave extends AsyncTask<String, Void, String> {
    private Activity activity;

    private ProgressDialog progressDialog;
    private String Imei,imgName;
    private boolean SAVE_STATUS = false;
    private Handler handler = new Handler();
    private String docNo;
    private boolean sendMail = false;
    ManuInspectModel manuInspectModel;
    BasicInfomation basicInfomation;
    List<Album> albumList;


    public AsyncTaskSave(Activity a, ManuInspectModel manuInspectModel, BasicInfomation basicInfomation,String Imei,List<Album> albumList,String imgName,String docNo, boolean sendMail){
        this.activity = a;
        this.manuInspectModel = manuInspectModel;
        this.basicInfomation = basicInfomation;
        this.albumList = albumList;
        this.Imei = Imei;
        this.imgName = imgName;
        this.docNo = docNo;
        this.sendMail = sendMail;
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
                    manuInspectModel.setMpEmployeeNo(EmployeesModel.id);
                    manuInspectModel.setMpEmployeeName(basicInfomation.getFileHeader_inspector());
                    manuInspectModel.setMpImagePath(imgName);
                    manuInspectModel.setMpLastModifyDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                    manuInspectModel.setMpLastModifyTime(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
                    manuInspectModel.setMpLastModifyByUserNo(TBUserLoginModel.ulUserLoginId);
                    manuInspectModel.setMpLastModifyByUserName(TBUserLoginModel.ulName);
                    if (!docNo.equals("0")) {
                        manuInspectModel.setMpDocumentNo(Integer.parseInt(docNo));
                    }
                    if(sendMail){
                        manuInspectModel.setMpLastSendmailDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                        manuInspectModel.setMpLastSendmailTime(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
                        manuInspectModel.setMpLastSendmailByUserNo(EmployeesModel.id);
                        manuInspectModel.setMpLastSendmailByUserName(EmployeesModel.employeeName);
                    }


                    //Detail
                    for (int i = 0; i < albumList.size(); i++) {
                        Album album = albumList.get(i);
                        if (album.DETAIL_BITMAP[i] != null) {
                            ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
                            manuInspectImageModel.setMpgDoccode(manuInspectModel.getMpDocCode());
                            manuInspectImageModel.setMpgDocumentno(manuInspectModel.getMpDocumentNo());
                            manuInspectImageModel.setMpgDocument(manuInspectModel.getMpDocument());
                            manuInspectImageModel.setMpgDocBranch(manuInspectModel.getMpDocBranch());
                            manuInspectImageModel.setMpgDocSeq(String.valueOf(i));
                            manuInspectImageModel.setMpgMemo(Album.DETAIL_MEMO[i]);
                            manuInspectImageModel.setMpgImagePath(Album.DETAIL_FILENAME[i]);
                            manuInspectImageModel.setMpgLastModifyDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                            manuInspectImageModel.setMpgLastModifyTime(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                            manuInspectImageModel.setMpgLastModifyByUserNo(TBUserLoginModel.ulUserLoginId);
                            manuInspectImageModel.setMpgLastModifyByUserName(TBUserLoginModel.ulName);
                            manuInspectImageModelList.add(manuInspectImageModel);
                            manuInspectModel.setManuInspectImageModelList(manuInspectImageModelList);

                            if (Album.DETAIL_MEMO[i]!= null) {
                                Log.d("MEMO__________: ", Album.DETAIL_MEMO[i]);
                            }

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
                        Intent intent = new Intent(activity, Allfile.class);
                        activity.startActivity(intent);
                        activity.finish();
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
        Album.DETAIL_BITMAP = new Bitmap[100];
        Album.DETAIL_MEMO = new String[100];
        Album.DETAIL_FILENAME = new String[100];
        Album album = new Album();
        ManuInspectModel manuInspectModel = new ManuInspectModel();
        ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
        basicInfomation.setFileHeader_date(null);
        basicInfomation.setFileHeader_inspector(null);
        basicInfomation.setFileHeader_coNo(null);
        basicInfomation.setFileHeader_colorNo(null);
        basicInfomation.setFileHeader_itemNo(null);
        basicInfomation.setFileHeader_customerNo(null);
        basicInfomation.setFileHeader_mail(null);
    }

}
