package com.example.ton.furnitureapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Model.ManuInspectModel;


public class Allfile extends AppCompatActivity {

    private RecyclerView recyclerView;
    private View positiveAction;
    private EditText dateFrom,dateTo;
    private DatePickerDialog.OnDateSetListener datePickFrom,datePickTo;
    private Calendar myCalendar;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;

    //@BindView(R.id.toolbar)
    //Toolbar toolbar;
    private Toolbar toolbar;

    //@BindView(R.id.fab)
    //FloatingActionButton fab;
    private FloatingActionButton fab;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<AbstractModel> modelList = new ArrayList<>();
    private Button back_btn,search_btn;
    private String dateFromStr,dateToStr,statusStr;
    private CheckBox sentBox,notSentBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allfile);

        // ButterKnife.bind(this);
        myCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        dateFromStr = sdf.format(myCalendar.getTime()).toString();
        dateToStr = sdf.format(myCalendar.getTime()).toString();
        statusStr = null;

        findViews();
        initToolbar();
        setAdapter(dateFromStr,dateToStr,statusStr);


    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(fabClick);

    }
    private View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent home = new Intent(Allfile.this,Home.class);
            BasicInfomation basicInfomation = new BasicInfomation();
            basicInfomation.setFileHeader_date(null);
            basicInfomation.setFileHeader_inspector(null);
            basicInfomation.setFileHeader_coNo(null);
            basicInfomation.setFileHeader_colorNo(null);
            basicInfomation.setFileHeader_itemNo(null);
            basicInfomation.setFileHeader_customerNo(null);
            basicInfomation.setFileHeader_mail(null);
            Album.DETAIL_FILENAME = new String[100];
            Album.DETAIL_BITMAP = new Bitmap[100];
            Album.DETAIL_MEMO = new String[100];
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home);
            finish();
        }
    };

    public void initToolbar() {
        back_btn=(Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Allfile.this,Home.class);
                BasicInfomation basicInfomation = new BasicInfomation();
                basicInfomation.setFileHeader_date(null);
                basicInfomation.setFileHeader_inspector(null);
                basicInfomation.setFileHeader_coNo(null);
                basicInfomation.setFileHeader_colorNo(null);
                basicInfomation.setFileHeader_itemNo(null);
                basicInfomation.setFileHeader_customerNo(null);
                basicInfomation.setFileHeader_mail(null);
                Album.DETAIL_FILENAME = new String[100];
                Album.DETAIL_BITMAP = new Bitmap[100];
                Album.DETAIL_MEMO = new String[100];
                startActivity(i);
                modelList.clear();
                finish();
            }
        });
        search_btn=(Button)findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog dialog =
                        new MaterialDialog.Builder(Allfile.this)
                                .title("Search")
                                .customView(R.layout.dialog_customview, true)
                                .positiveText("OK")
                                .negativeText(android.R.string.cancel)
                                .build();

                positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
                positiveAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            dateFromStr = dateFrom.getText().toString();
                            dateToStr = dateTo.getText().toString();
                            if (sentBox.isChecked() && notSentBox.isChecked()){
                                statusStr = null;
                            }else if (sentBox.isChecked()){
                                statusStr = "Y";
                            }else if (notSentBox.isChecked()){
                                statusStr = "N";
                            }else {
                                statusStr = null;
                            }
                        modelList.clear();
                        setAdapter(dateFromStr,dateToStr,statusStr);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();


                    }
                });
                //noinspection ConstantConditions
                //Date

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                myCalendar = Calendar.getInstance();
                dateFrom = dialog.getCustomView().findViewById(R.id.dateFrom);
                dateTo = dialog.getCustomView().findViewById(R.id.dateTo);
                dateFrom.setOnClickListener(pickDateF);
                dateTo.setOnClickListener(pickDateT);
                dateFrom.setText(sdf.format(myCalendar.getTime()));
                dateTo.setText(sdf.format(myCalendar.getTime()));

                datePickFrom = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        dateFrom.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                datePickTo = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        dateTo.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                // Toggling the show password CheckBox will mask or unmask the password input EditText
                sentBox = dialog.getCustomView().findViewById(R.id.sent);
                notSentBox = dialog.getCustomView().findViewById(R.id.notSent);

                int widgetColor = ThemeSingleton.get().widgetColor;
                MDTintHelper.setTint(
                        sentBox, widgetColor == 0 ? ContextCompat.getColor(Allfile.this, R.color.colorPrimary) : widgetColor);

                MDTintHelper.setTint(
                        notSentBox, widgetColor == 0 ? ContextCompat.getColor(Allfile.this, R.color.colorPrimary) : widgetColor);
               /* MDTintHelper.setTint(
                        passwordInput,
                        widgetColor == 0 ? ContextCompat.getColor(Allfile.this, R.color.colorPrimary) : widgetColor);
*/
                dialog.show();
                positiveAction.setEnabled(true); // disabled by default

            }
        });

    }
    private View.OnClickListener pickDateF = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(Allfile.this, datePickFrom, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };
    private View.OnClickListener pickDateT = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(Allfile.this, datePickTo, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };
    private void setAdapter(String dateFrom,String dateTo,String status) {
        AbstractModel b;
        DatabaseHelper db = new DatabaseHelper(Allfile.this);
        //List<ManuInspectModel> manuInspectList = db.getAllManuInspect();
        List<ManuInspectModel> manuInspectList = db.searchManuInspect(dateFrom,dateTo,status);
        for (int i = 0 ;i<manuInspectList.size();i++){
            ManuInspectModel manuInspectModel =  manuInspectList.get(i);
            if (manuInspectModel.getMpLastSendmailByUserName() != null){
                status = "Sent";
            }else {
                status = "Not Sent";
            }

             b = new AbstractModel(manuInspectModel.getMpImagePath()
                     ,manuInspectModel.getMpDocDate()
                     ,manuInspectModel.getMpCustomerNo()
                     ,manuInspectModel.getMpItemNo()
                     ,manuInspectModel.getMpColorNo()
                     ,manuInspectModel.getMpCoNo()
                     ,manuInspectModel.getMpEmployeeName()
                     ,status
                     ,manuInspectModel.getMpDocumentNo());
             modelList.add(b);


        }


        mAdapter = new RecyclerViewAdapter(Allfile.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, AbstractModel model) {

                //handle item click events here
                Toast.makeText(Allfile.this, "" + model.getDocNo(), Toast.LENGTH_SHORT).show();
                Intent home = new Intent(Allfile.this,Home.class);
                home.putExtra("docNo",String.valueOf(model.getDocNo()));
                Album.DETAIL_FILENAME = new String[100];
                Album.DETAIL_BITMAP = new Bitmap[100];
                Album.DETAIL_MEMO = new String[100];
                startActivity(home);
                modelList.clear();
                finish();


            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private Toast toast;
    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    protected void onDestroy() {
        freeMemory();
        super.onDestroy();
    }
    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

}
