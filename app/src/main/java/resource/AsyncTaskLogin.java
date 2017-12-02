package resource;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.example.ton.furnitureapplication.DatabaseHelper;
import com.example.ton.furnitureapplication.Home;
import com.example.ton.furnitureapplication.MainActivity;
import com.example.ton.furnitureapplication.MainMenu;

import java.util.ArrayList;

import Model.TBUserLoginModel;

public class AsyncTaskLogin extends AsyncTask<String, Void, String> {
    private Activity activity;

    private ProgressDialog progressDialog;
    private Dialog dialog;
    private String url,getUser,getPassword;
    private boolean LOGIN_STATUS = false;
    private Handler handler = new Handler();

    //DB
    SQLiteDatabase mDb;
    DatabaseHelper mHelper;
    ArrayList<TBUserLoginModel> loginUser = null;
    Cursor mCursor;

    public AsyncTaskLogin(Activity a,String loginUser, String loginPassword){
        this.activity = a;
        this.getUser = loginUser;
        this.getPassword = loginPassword;
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
                createDB();
                /// Login
                if(mHelper.checkUserLogin(getUser,getPassword)){
                    LOGIN_STATUS = true;
                } else {
                    LOGIN_STATUS = false;
                }

                handler.postDelayed(new Runnable() {
                    public void run() {
                    dialog.dismiss();

                    if (LOGIN_STATUS == true) {
                        /// ให้ไปยังหน้าหลัก
                        Intent intent = new Intent(activity, Home.class);
                        activity.startActivity(intent);
                    } else if (LOGIN_STATUS == false) {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(activity, "ชื่อผู้ใช้หรือรหัสผ่านผิดพลาด กรุณาลองใหม่", Toast.LENGTH_SHORT).show();
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
    private void createDB(){
        //SQLite
        mHelper = new DatabaseHelper(activity);
        mDb = mHelper.getWritableDatabase();
    }
}
