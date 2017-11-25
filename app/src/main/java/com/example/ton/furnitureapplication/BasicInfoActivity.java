package com.example.ton.furnitureapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Optimize on 26/9/2560.
 */

public class BasicInfoActivity extends Activity {
    @BindView(R.id.date_edt)
    EditText date_edt;
    @BindView(R.id.customer_no_edt)
    EditText customer_no_edt;
    @BindView(R.id.item_no_edt)
    EditText item_no_edt;
    @BindView(R.id.color_no_edt)
    EditText color_no_edt;
    @BindView(R.id.co_no_edt)
    EditText co_no_edt;
    @BindView(R.id.inspector_edt)
    EditText inspector_edt;


    @BindView(R.id.back_btn)
    Button back_btn;
    @BindView(R.id.update_btn)
    Button update_btn;


    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener datePick;
    private BasicInfomation basicInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_info);
        ButterKnife.bind(this);

        myCalendar = Calendar.getInstance();
        datePick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                date_edt.setText(sdf.format(myCalendar.getTime()));
            }

        };

    }

    @OnClick(R.id.date_edt)
    public void changeLanguage_onClick() {
        // TODO Auto-generated method stub
        new DatePickerDialog(BasicInfoActivity.this, datePick, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.back_btn)
    public void backToMainActivity_onClick() {
        Intent mainIntent = new Intent(BasicInfoActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    @OnClick(R.id.update_btn)
    public void updateInfomation_onClick() {
        basicInfo.FileHeader_date=date_edt.getText().toString().trim();
        basicInfo.FileHeader_customerNo=customer_no_edt.getText().toString().trim();
        basicInfo.FileHeader_itemNo=item_no_edt.getText().toString().trim();
        basicInfo.FileHeader_colorNo=color_no_edt.getText().toString().trim();
        basicInfo.FileHeader_coNo=co_no_edt.getText().toString().trim();
        basicInfo.FileHeader_inspector=inspector_edt.getText().toString().trim();

        Intent mainIntent = new Intent(BasicInfoActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
