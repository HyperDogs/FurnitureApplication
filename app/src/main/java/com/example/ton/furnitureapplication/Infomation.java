package com.example.ton.furnitureapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Model.EmployeesModel;
import butterknife.ButterKnife;


public class Infomation extends AppCompatActivity {
    //@BindView(R.id.date_edt)EditText date_edt;
    EditText date_edt,customer_no_edt,item_no_edt,color_no_edt,co_no_edt,inspector_edt;

    //@BindView(R.id.update_btn)Button update_btn;
    Button update_btn;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener datePick;
    private BasicInfomation basicInfo;
    private String mActionMode = "";
    private Utility mUtil = new Utility();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //Date
        myCalendar = Calendar.getInstance();
        date_edt = findViewById(R.id.date_edt);
        date_edt.setOnClickListener(pickDate);
        datePick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                date_edt.setText(sdf.format(myCalendar.getTime()));
            }

        };

        //edt
        date_edt = findViewById(R.id.date_edt);
        customer_no_edt = findViewById(R.id.customer_no_edt);
        item_no_edt = findViewById(R.id.item_no_edt);
        color_no_edt = findViewById(R.id.color_no_edt);
        co_no_edt = findViewById(R.id.co_no_edt);
        inspector_edt = findViewById(R.id.inspector_edt);
        //updateBtn
        update_btn = findViewById(R.id.update_btn);
        update_btn.setOnClickListener(updateBtn);

        //Bundle
        Bundle bundle = getIntent().getExtras();
        date_edt.setText(bundle.getString("date"));
        customer_no_edt.setText(bundle.getString("cusNo"));
        item_no_edt.setText(bundle.getString("itemNo"));
        color_no_edt.setText(bundle.getString("colorNo"));
        co_no_edt.setText(bundle.getString("coNo"));
        inspector_edt.setText(EmployeesModel.employeeName);
        mActionMode = bundle.getString("ACTION_MODE");

        if(mActionMode.equals("CREATE")){
            date_edt.setEnabled(false);
            date_edt.setText(mUtil.getCurrentDateTime("dd-MM-yyyy"));
            BasicInfomation.setFileHeader_date(mUtil.getCurrentDateTime("dd-MM-yyyy"));
        } else if(mActionMode.equals("EDIT")){
            date_edt.setEnabled(true);
        }
    }

    private  View.OnClickListener updateBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String dateStr = date_edt.getText().toString().trim(),
                    cusNoStr = customer_no_edt.getText().toString().trim(),
                    itemStr = item_no_edt.getText().toString().trim(),
                    colorStr = color_no_edt.getText().toString().trim(),
                    coNoStr = co_no_edt.getText().toString().trim(),
                    alertMsg = getString(R.string.alertInfo);

            if (dateStr.equals("") || cusNoStr.equals("") || itemStr.equals("") || colorStr.equals("") || coNoStr.equals("")){
                Toast.makeText(Infomation.this,alertMsg,Toast.LENGTH_SHORT).show();
            }else {
                BasicInfomation.setFileHeader_date(date_edt.getText().toString().trim());
                BasicInfomation.setFileHeader_customerNo(customer_no_edt.getText().toString().trim());
                BasicInfomation.setFileHeader_itemNo(item_no_edt.getText().toString().trim());
                BasicInfomation.setFileHeader_colorNo(color_no_edt.getText().toString().trim());
                BasicInfomation.setFileHeader_coNo(co_no_edt.getText().toString().trim());
                BasicInfomation.setFileHeader_inspector(inspector_edt.getText().toString().trim());

                onBackPressed();

            }
        }
    };

    private View.OnClickListener pickDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(Infomation.this, datePick, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            BasicInfomation.FileHeader_date= "";
            BasicInfomation.FileHeader_customerNo= "";
            BasicInfomation.FileHeader_itemNo= "";
            BasicInfomation.FileHeader_colorNo= "";
            BasicInfomation.FileHeader_coNo = "";
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
