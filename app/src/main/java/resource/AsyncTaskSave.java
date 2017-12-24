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
import com.example.ton.furnitureapplication.CopyImageToServer;
import com.example.ton.furnitureapplication.DatabaseHelper;
import com.example.ton.furnitureapplication.OkHttpHelper;
import com.example.ton.furnitureapplication.WebServiceURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Model.EmployeesModel;
import Model.ManuInspectImageModel;
import Model.ManuInspectModel;
import Model.TBUserLoginModel;
import okhttp3.FormBody;

public class AsyncTaskSave extends AsyncTask<String, Void, String> {
    private Activity activity;

    private ProgressDialog progressDialog;
    private String Imei,imgName;
    private boolean SAVE_STATUS = false;
    private Handler handler = new Handler();
    private String docNo;
    private boolean sendMail = false;

    private OkHttpHelper mOkHttpHelper;
    private WebServiceURL mWebServiceURL;
    private CopyImageToServer mUploadPhoto;
    private int mNewDocumentNo = 0;

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

        this.mOkHttpHelper = new OkHttpHelper();
        this.mWebServiceURL = new WebServiceURL();
        this.mUploadPhoto = new CopyImageToServer();
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
                    String errDesc = "";
                    String detailMemo = "";


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
                    manuInspectModel.setMpEmployeeName(EmployeesModel.employeeName);
                    manuInspectModel.setMpImagePath(imgName);

                    if (!docNo.equals("0")) {
                        manuInspectModel.setMpLastModifyDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                        manuInspectModel.setMpLastModifyTime(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
                        manuInspectModel.setMpLastModifyByUserNo(TBUserLoginModel.ulUserLoginId);
                        manuInspectModel.setMpLastModifyByUserName(TBUserLoginModel.ulName);
                        manuInspectModel.setMpDocumentNo(Integer.parseInt(docNo));
                    }


                    if(sendMail){
                        manuInspectModel.setMpLastSendmailDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                        manuInspectModel.setMpLastSendmailTime(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
                        manuInspectModel.setMpLastSendmailByUserNo(TBUserLoginModel.ulUserLoginId);
                        manuInspectModel.setMpLastSendmailByUserName(TBUserLoginModel.ulName);
                        errDesc = sendHeaderToServer();
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

                            if (!docNo.equals("0")) {
                                manuInspectImageModel.setMpgLastModifyDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                                manuInspectImageModel.setMpgLastModifyTime(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                                manuInspectImageModel.setMpgLastModifyByUserNo(TBUserLoginModel.ulUserLoginId);
                                manuInspectImageModel.setMpgLastModifyByUserName(TBUserLoginModel.ulName);
                            }

                            manuInspectImageModelList.add(manuInspectImageModel);
                            manuInspectModel.setManuInspectImageModelList(manuInspectImageModelList);



                            if (Album.DETAIL_MEMO[i]!= null) {
                                Log.d("MEMO__________: ", Album.DETAIL_MEMO[i]);
                                detailMemo = Album.DETAIL_MEMO[i];
                            } else {
                                detailMemo = "";
                            }

                            if(sendMail == true){
                                errDesc = sendDetailToServer(String.valueOf(i), detailMemo, Album.DETAIL_FILENAME[i]);
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
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                            if (!docNo.equals("0")) {
                                Toast.makeText(activity, "Successfully Updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, "Successfully Created.", Toast.LENGTH_SHORT).show();
                            }
                            }
                        });

                        // ให้ไปยังหน้าหลัก
                        Intent intent = new Intent(activity, Allfile.class);
                        activity.startActivity(intent);
                        //activity.finish();
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
        /*Album.DETAIL_BITMAP = new Bitmap[100];
        Album.DETAIL_MEMO = new String[100];
        Album.DETAIL_FILENAME = new String[100];
        Album album = new Album();
        ManuInspectModel manuInspectModel = new ManuInspectModel();
        ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
        basicInfomation.setFileHeader_date(null);
        basicInfomation.setFileHeader_coNo(null);
        basicInfomation.setFileHeader_colorNo(null);
        basicInfomation.setFileHeader_itemNo(null);
        basicInfomation.setFileHeader_customerNo(null);
        basicInfomation.setFileHeader_mail(null);*/
    }

    private String sendHeaderToServer(){
        mUploadPhoto.sendFileToServer(imgName, mWebServiceURL.URL_uploadFile,"HEADER");

        FormBody params = new FormBody.Builder()
                .add("ACTION_MODE","HEADER")
                .add("DOC_CODE", "IN01")
                .add("DOCUMENT", Imei)
                .add("DOC_BRANCH", "01")
                .add("DEC_SEQ","0")
                .add("DOC_DATE",basicInfomation.getFileHeader_date())
                .add("CUTOMER_NO",basicInfomation.getFileHeader_customerNo())
                .add("ITEM_NO",basicInfomation.getFileHeader_itemNo())
                .add("COLOR_NO",basicInfomation.getFileHeader_colorNo())
                .add("CO_NO",basicInfomation.getFileHeader_coNo())
                .add("EMPLOYEE_NO",EmployeesModel.id)
                .add("EMPLOYEE_NAME",EmployeesModel.employeeName)
                .add("IMG_PATH",imgName)
                .add("USER_NO",TBUserLoginModel.ulUserLoginId)
                .add("USERNAME",TBUserLoginModel.ulName)
                .build();


        try {
            JSONArray data = new JSONArray(mOkHttpHelper.serverRequest(mWebServiceURL.URL_createNew,params));
            JSONObject c = data.getJSONObject(0);

            Log.d("NEW_DOCUMENT_NO",String.valueOf(c.getInt("NEW_DOCNO")));
            mNewDocumentNo = c.getInt("NEW_DOCNO");
            if(c.getInt("STATUS") == 0){
                return c.getString("DESCRIPTION");
            } else {
                return "";
            }
        } catch (JSONException e) {
            return e.getMessage().toString();
        }
    }

    private String sendDetailToServer(String seq, String memo, String fileName){
        FormBody params = new FormBody.Builder()
                .add("ACTION_MODE","DETAIL")
                .add("NEW_DOCUMENT_NO",String.valueOf(mNewDocumentNo))
                .add("DOC_CODE", "IN01")
                .add("DOC_CODE", "IN01")
                .add("DOCUMENT", Imei)
                .add("DOC_BRANCH", "01")
                .add("DEC_SEQ",seq)
                .add("MEMO",memo)
                .add("EMPLOYEE_NO",EmployeesModel.id)
                .add("EMPLOYEE_NAME",EmployeesModel.employeeName)
                .add("USER_NO",TBUserLoginModel.ulUserLoginId)
                .add("USERNAME",TBUserLoginModel.ulName)
                .add("IMG_PATH",fileName)
                .build();

        mUploadPhoto.sendFileToServer(fileName, mWebServiceURL.URL_uploadFile,"DETAIL");

        try {
            JSONArray data = new JSONArray(mOkHttpHelper.serverRequest(mWebServiceURL.URL_createNew,params));
            JSONObject c = data.getJSONObject(0);


            if(c.getInt("STATUS") == 0){
                return c.getString("DESCRIPTION");
            } else {
                return "";
            }
        } catch (JSONException e) {
            return e.getMessage().toString();
        }
    }
}
