package com.example.ton.furnitureapplication;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button loginBth;
    EditText username,password;
    String usernameTxt,passwordTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBth = (Button)findViewById(R.id.loginBtn);
        username = (EditText)findViewById(R.id.usernameEdt);
        password = (EditText)findViewById(R.id.passwordEdt);
        loginBth.setOnClickListener(doLogin);

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
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    private View.OnClickListener doLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            usernameTxt = username.getText().toString();
            passwordTxt = password.getText().toString();
            if (usernameTxt.matches("") || passwordTxt.matches("")){
                Toast.makeText(MainActivity.this,"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_LONG).show();
            }else if (usernameTxt.matches("Admin") || passwordTxt.matches("password")) {
                Intent i = new Intent(MainActivity.this, MainMenu.class);
                startActivity(i);
            }


        }
    };


}
