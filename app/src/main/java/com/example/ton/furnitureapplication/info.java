package com.example.ton.furnitureapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class info extends AppCompatActivity {
    //@BindView(R.id.date_edt)EditText date_edt;
    EditText date_edt;
    @BindView(R.id.customer_no_edt)EditText customer_no_edt;
    @BindView(R.id.item_no_edt)EditText item_no_edt;
    @BindView(R.id.color_no_edt)EditText color_no_edt;
    @BindView(R.id.co_no_edt)EditText co_no_edt;
    @BindView(R.id.inspector_edt)EditText inspector_edt;

    @BindView(R.id.update_btn)Button update_btn;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener datePick;
    private BasicInfomation basicInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //Date
        myCalendar = Calendar.getInstance();
        date_edt = (EditText)findViewById(R.id.date_edt);
        date_edt.setOnClickListener(pickDate);
        datePick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                date_edt.setText(sdf.format(myCalendar.getTime()));
            }

        };



    }

    private View.OnClickListener pickDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(info.this, datePick, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    @OnClick(R.id.update_btn)
    public void updateInfomation_onClick() {
        basicInfo.FileHeader_date=date_edt.getText().toString().trim();
        basicInfo.FileHeader_customerNo=customer_no_edt.getText().toString().trim();
        basicInfo.FileHeader_itemNo=item_no_edt.getText().toString().trim();
        basicInfo.FileHeader_colorNo=color_no_edt.getText().toString().trim();
        basicInfo.FileHeader_coNo=co_no_edt.getText().toString().trim();
        basicInfo.FileHeader_inspector=inspector_edt.getText().toString().trim();

        Intent mainIntent = new Intent(info.this, Home.class);
        startActivity(mainIntent);
    }

}
