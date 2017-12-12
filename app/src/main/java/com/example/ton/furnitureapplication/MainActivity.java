package com.example.ton.furnitureapplication;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import Model.ManuInspectImageModel;
import Model.TBUserLoginModel;
import resource.AsyncTaskLogin;

public class MainActivity extends Activity {

    Button loginBth;
    EditText username,password;
    String usernameTxt,passwordTxt;
    //DB
    SQLiteDatabase mDb;
    DatabaseHelper mHelper;
    ArrayList<TBUserLoginModel> loginUser = null;

    Cursor mCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBth = (Button)findViewById(R.id.loginBtn);
        username = (EditText)findViewById(R.id.usernameEdt);
        password = (EditText)findViewById(R.id.passwordEdt);
        loginBth.setOnClickListener(doLogin);
        accessPermission();
        createDB();

    }

    private View.OnClickListener doLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            usernameTxt = username.getText().toString();
            passwordTxt = password.getText().toString();

            if (usernameTxt.matches("") || passwordTxt.matches("")){
                Intent i = new Intent(MainActivity.this,Home.class);
                startActivity(i);
                Toast.makeText(MainActivity.this,"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_LONG).show();
            }else {
                AsyncTaskLogin atlLogin = new AsyncTaskLogin(MainActivity.this,usernameTxt,passwordTxt);
                atlLogin.execute();
            }
        }
    };

    private void createDB(){
        //SQLite
        mHelper = new DatabaseHelper(MainActivity.this);
        mDb = mHelper.getWritableDatabase();
    }

    private void accessPermission(){
        //ขออนุญาติ
        Dexter.initialize(MainActivity.this);
        Dexter.checkPermissions(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                List<PermissionGrantedResponse> permissionGrantedResponses = report.getGrantedPermissionResponses();

                for(PermissionGrantedResponse grantedResponse : permissionGrantedResponses)
                {
                    grantedResponse.getPermissionName();
                }

                report.areAllPermissionsGranted();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Exit")
                    .setMessage("Do you want to exit ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            System.exit(0);

                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
