package com.example.ton.furnitureapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.ManuInspectModel;
import resource.AsyncTaskSave;
import resource.CreateFile;


public class Allfile extends AppCompatActivity {

    private RecyclerView recyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allfile);

        // ButterKnife.bind(this);
        findViews();
        initToolbar();
        setAdapter();


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
        }
    };

    public void initToolbar() {
        back_btn=(Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        search_btn=(Button)findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog getDialog = new Dialog(Allfile.this);
                getDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                getDialog.setContentView(R.layout.custom_alert_dialog);
                getDialog.setCanceledOnTouchOutside(false);
                getDialog.setCancelable(false);

                //Grab the window of the dialog, and change the width
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = getDialog.getWindow();
                lp.copyFrom(window.getAttributes());
                //This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);

                final EditText dateF_edt= (EditText) getDialog.findViewById(R.id.dateF_edt);
                final EditText dateT_edt = (EditText) getDialog.findViewById(R.id.dateF_edt);
                final Button progressCloseBtn = (Button) getDialog.findViewById(R.id.dialogCloseBtn);
                final Button dialogConfirm = (Button) getDialog.findViewById(R.id.dialogConfirmBtn);
                final Button dialogCancel = (Button) getDialog.findViewById(R.id.dialogCancelBtn);

                    progressCloseBtn.setVisibility(View.VISIBLE);
                    progressCloseBtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            getDialog.dismiss();
                        }
                    });

                    dialogConfirm.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            getDialog.dismiss();
                        }
                    });

                    dialogCancel.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            getDialog.dismiss();
                        }
                    });


                getDialog.show();


            }
        });

    }

    private void setAdapter() {
        AbstractModel b;
        String status;
        DatabaseHelper db = new DatabaseHelper(Allfile.this);
        List<ManuInspectModel> manuInspectList = db.getAllManuInspect();
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
                startActivity(home);
                System.gc();
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


}
