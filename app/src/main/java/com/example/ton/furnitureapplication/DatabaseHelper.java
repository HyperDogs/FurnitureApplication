package com.example.ton.furnitureapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.BranchCompanyCodeModel;
import Model.EmployeesModel;
import Model.ManuInspectImageModel;
import Model.ManuInspectModel;
import Model.TBUserLoginModel;
//hi
/**
 * Created by marisalom on 25/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "SQLiteDatabase.db";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_USERLOGIN = "TBUserLogin";
    public static final String TABLE_MANUINSPECT = "ManuInspect";
    public static final String TABLE_MANUINSPECTIMAGE = "ManuInspectImage";
    public static final String TABLE_EMPLOYEES = "Employees";
    public static final String TABLE_BRANCHCOMPANYCODE = "sysBranchCompanyCode";

    public static final String COL_ULUSERLOGINID = "ulUserLoginId";
    public static final String COL_ULNAME = "ulName";
    public static final String COL_ULPASS = "ulPass";
    public static final String COL_ULDESC = "ulDesc";
    public static final String COL_ULGROUPID = "ulGroupId";
    public static final String COL_ULSTATUS = "ulStatus";
    public static final String COL_ULEMPLOYEEID = "ulEmployeeId";
    public static final String COL_ULBRANCHID = "ulBranchId";
    public static final String COL_ULSETPERMISSION = "ulSetPermission";
    public static final String COL_ULREMOTEADDR = "ulRemoteAddr";

    public static final String COL_MPDOCCODE = "mpDocCode";
    public static final String COL_MPDOCUMENT = "mpDocument";
    public static final String COL_MPDOCUMENTNO = "mpDocumentNo";
    public static final String COL_MPDOCBRANCH = "mpDocBranch";
    public static final String COL_MPDOCSEQ = "mpDocSeq";
    public static final String COL_MPDOCDATE = "mpDocDate";
    public static final String COL_MPDOCTIME = "mpDocTime";
    public static final String COL_MPEMPLOYEENO = "mpEmployeeNo";
    public static final String COL_MPEMPLOYEENAME = "mpEmployeeName";
    public static final String COL_MPCUSTOMERNO = "mpCustomerNo";
    public static final String COL_MPCUSTOMERNAME = "mpCustomerName";
    public static final String COL_MPITEMNO = "mpItemNo";
    public static final String COL_MPITEMNAME = "mpItemName";
    public static final String COL_MPCOLORNO = "mpColorNo";
    public static final String COL_MPCOLORNAME = "mpColorName";
    public static final String COL_MPCONO = "mpCoNo";
    public static final String COL_MPIMAGEPATH = "mpImagePath";
    public static final String COL_MPIMAGEBLOB = "mpImageBlob";
    public static final String COL_MPLASTSENDBYMAIL = "mpLastSendBymail";
    public static final String COL_MPLASTSENDMAILBYUSERNO = "mpLastSendmailByUserNo";
    public static final String COL_MPLASTSENDMAILBYUSERNAME = "mpLastSendmailByUserName";
    public static final String COL_MPLASTSENDMAILDATE = "mpLastSendmailDate";
    public static final String COL_MPLASTSENDMAILTIME = "mpLastSendmailTime";
    public static final String COL_MPLASTMODIFYDATE = "mpLastModifyDate";
    public static final String COL_MPLASTMODIFYTIME = "mpLastModifyTime";
    public static final String COL_MPLASTMODIFYBYUSERNO = "mpLastModifyByUserNo";
    public static final String COL_MPLASTMODIFYBYUSERNAME = "mpLastModifyByUserName";

    public static final String COL_MPGDOCCODE = "mpgDocCode";
    public static final String COL_MPGDOCUMENT = "mgeDocument";
    public static final String COL_MPGDOCUMENTNO = "mpgDocumentno";
    public static final String COL_MPGDOCBRANCH = "mpgDocBranch";
    public static final String COL_MPGDOCSEQ = "mpgDocSeq";
    public static final String COL_MPGCAUSE = "mpgCause";
    public static final String COL_MPGSOLUTION = "mpgSolution";
    public static final String COL_MPGMEMO = "mpgMemo";
    public static final String COL_MPGIMAGEPATH = "mpgImagePath";
    public static final String COL_MPGIMAGEBLOB = "mpgImageBlob";
    public static final String COL_MPGCAUSEBYEMPLOYEENO = "mpgCauseByEmployeeNo";
    public static final String COL_MPGCAUSEBYEMPLOYEENAME = "mpgCauseByEmployeeName";
    public static final String COL_MPGSOLUTIONBYEMPLOYEENO = "mpgSolutionByEmployeeNo";
    public static final String COL_MPGSOLUTIONBYEMPLOYEENAME = "mpgSolutionByEmployeeName";
    public static final String COL_MPGLASTMODIFYDATE = "mpgLastModifyDate";
    public static final String COL_MPGLASTMODIFYTIME = "mpgLastModifyTime";
    public static final String COL_MPGLASTMODIFYBYUSERNO = "mpgLastModifyByUserNo";
    public static final String COL_MPGLASTMODIFYBYUSERNAME = "mpgLastModifyByUserName";

    public static final String COL_ID = "id";
    public static final String COL_EMPLOYEENAME = "employeeName";
    public static final String COL_EMPGROUP = "empGroup";
    public static final String COL_POSITION = "position";
    public static final String COL_DEPARTMENT = "department";
    public static final String COL_SYSTEMGROUP = "systemGroup";
    public static final String COL_PASSWD = "passwd";
    public static final String COL_SALARY = "salary";
    public static final String COL_MAXPARTDISC = "maxPartDisc";
    public static final String COL_MAXSERVICEDISC = "maxServiceDisc";
    public static final String COL_STARTDATE = "startDate";
    public static final String COL_EXPIREDDATE = "expiredDate";
    public static final String COL_YTDPRODUCTION = "ytdProduction";
    public static final String COL_STATUS = "status";
    public static final String COL_CURRENTJOB = "currentJob";
    public static final String COL_CURRENTREF = "currentPef";
    public static final String COL_CURRENTOPERATION = "currentOperation";
    public static final String COL_CURRENTCOUNTER = "currentCounter";
    public static final String COL_CURRENTSALETYPE = "currentSaleType";
    public static final String COL_QUEUE = "queue";
    public static final String COL_CURRENTREFINT = "currentRefInt";
    public static final String COL_CURRENTIO = "currentIO";
    public static final String COL_CURRENTJOBSTATUS = "currentJobStatus";
    public static final String COL_STATUSON = "statusOn";
    public static final String COL_STATUSONDATE = "statusOnDate";
    public static final String COL_BRANCH = "branch";
    public static final String COL_WAREHOUSE = "warehouse";

    public static final String COL_BCCOMPANYCODE = "bcCompanyCode";
    public static final String COL_BCBRANCHCODE = "bcBranchCode";
    public static final String COL_BCCODE = "bcCode";
    public static final String COL_BCBRANCHNAME = "bcBranchName";
    public static final String COL_BCADDRESS1 = "bcAddress1";
    public static final String COL_BCADDRESS2 = "bcAddress2";
    public static final String COL_BCKHET = "bcKhet";
    public static final String COL_BCPROVINCE = "bcProvince";
    public static final String COL_BCZIPCODE = "bcZipCode";
    public static final String COL_BCTEL = "bcTel";
    public static final String COL_BCFAX = "bcFax";
    public static final String COL_BCEMAIL = "bcEmail";
    public static final String COL_BCTAXID = "bcTaxID";
    public static final String COL_BCTAXBRANCH = "bcTaxBranch";
    public static final String COL_BCBRANCHNAMEENG = "bcBranchNameEng";
    public static final String COL_BCADDRESS1ENG = "bcAddress1Eng";
    public static final String COL_BCADDRESS2ENG = "bcAddress2Eng";
    public static final String COL_BCKHETENG = "bcKhetEng";
    public static final String COL_BCPROVINCEENG = "bcProvinceEng";
    public static final String COL_BCZIPCODEENG = "bcZipCodeEng";
    public static final String COL_BCTELENG = "bcTelEng";
    public static final String COL_BCFAXENG = "bcFaxEng";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTABLE_USERLOGIN(db);
        createTABLE_MANUINSPECT(db);
        createTABLE_MANUINSPECTIMAGE(db);
        createTABLE_EMPLOYEES(db);
        createTABLE_BRANCHCOMPANYCODE(db);
    }

    private void createTABLE_USERLOGIN(SQLiteDatabase db){
        String CREATE_USERLOGIN_TABLE = "CREATE TABLE " + TABLE_USERLOGIN + "("
                + COL_ULUSERLOGINID + " CHAR(10) PRIMARY KEY, "
                + COL_ULNAME + " VARCHAR(20), "
                + COL_ULPASS + " VARCHAR(80), "
                + COL_ULDESC + " VARCHAR(50), "
                + COL_ULGROUPID + " VARCHAR(10), "
                + COL_ULSTATUS + " CHAR(1), "
                + COL_ULEMPLOYEEID + " VARCHAR(10), "
                + COL_ULBRANCHID + " VARCHAR(10), "
                + COL_ULSETPERMISSION + " CHAR(1), "
                + COL_ULREMOTEADDR + " VARCHAR(15) "
                + ") ";
        db.execSQL(CREATE_USERLOGIN_TABLE);
        db.execSQL("INSERT INTO " + TABLE_USERLOGIN + " (" + COL_ULUSERLOGINID + ", " + COL_ULNAME + ", " + COL_ULPASS + ", " + COL_ULDESC + ", "+ COL_ULEMPLOYEEID +", " + COL_ULBRANCHID +" ) " +
                   "VALUES " +
        "('admin', 'admin', '1234', '867007020839046', '01', '01'), " +
                "('party', '1589', 'Party Suwit', '867007020839046', '04', '01'), " +
                "('2', 'admin', 'admin', '357220073447263', '02', '01'), " +
                "('3', 'admin', 'admin', '357221073447261', '03', '01');");
    }

    private void createTABLE_MANUINSPECT(SQLiteDatabase db){
        String CREATE_UMANUINSPECT_TABLE = " CREATE TABLE " + TABLE_MANUINSPECT + "("
                + COL_MPDOCCODE  + " CHAR(4), "
                + COL_MPDOCUMENT + " VARCHAR(20), "
                + COL_MPDOCUMENTNO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MPDOCBRANCH + " CHAR(5), "
                + COL_MPDOCSEQ + " INTEGER, "
                + COL_MPDOCDATE + " DATETIME, "
                + COL_MPDOCTIME + " CHAR(6), "
                + COL_MPEMPLOYEENO + " VARCHAR(20), "
                + COL_MPEMPLOYEENAME + " VARCHAR(50), "
                + COL_MPCUSTOMERNO + " VARCHAR(20), "
                + COL_MPCUSTOMERNAME + " VARCHAR(100), "
                + COL_MPITEMNO + " VARCHAR(30), "
                + COL_MPITEMNAME + " VARCHAR(100), "
                + COL_MPCOLORNO + " VARCHAR(20), "
                + COL_MPCOLORNAME + " VARCHAR(100), "
                + COL_MPCONO + " VARCHAR(50), "
                + COL_MPIMAGEPATH + " VARCHAR(220), "
                + COL_MPIMAGEBLOB + " VARCHAR(50), "
                + COL_MPLASTSENDBYMAIL + " VARCHAR(220), "
                + COL_MPLASTSENDMAILBYUSERNO + " VARCHAR(20), "
                + COL_MPLASTSENDMAILBYUSERNAME + " VARCHAR(20), "
                + COL_MPLASTSENDMAILDATE + " DATETIME, "
                + COL_MPLASTSENDMAILTIME + " CHAR(6), "
                + COL_MPLASTMODIFYDATE + " DATETIME, "
                + COL_MPLASTMODIFYTIME + " CHAR(6), "
                + COL_MPLASTMODIFYBYUSERNO + " VARCHAR(20), "
                + COL_MPLASTMODIFYBYUSERNAME + " VARCHAR(50) "
                + ") ";
        db.execSQL(CREATE_UMANUINSPECT_TABLE);
    }

    private void createTABLE_MANUINSPECTIMAGE(SQLiteDatabase db){
        String CREATE_UMANUINSPECTIMAGE_TABLE = " CREATE TABLE " + TABLE_MANUINSPECTIMAGE + "("
                + COL_MPGDOCCODE  + " CHAR(4), "
                + COL_MPGDOCUMENT + " VARCHAR(20), "
                + COL_MPGDOCUMENTNO + " INTEGER, "
                + COL_MPGDOCBRANCH + " CHAR(5), "
                + COL_MPGDOCSEQ + " INTEGER, "
                + COL_MPGCAUSE + " INTEGER, "
                + COL_MPGSOLUTION + " VARCHAR(220), "
                + COL_MPGMEMO + " VARCHAR(220), "
                + COL_MPGIMAGEPATH + " VARCHAR(220), "
                + COL_MPGIMAGEBLOB + " VARCHAR(50), "
                + COL_MPGCAUSEBYEMPLOYEENO + " VARCHAR(20), "
                + COL_MPGCAUSEBYEMPLOYEENAME + " VARCHAR(50), "
                + COL_MPGSOLUTIONBYEMPLOYEENO + " VARCHAR(20), "
                + COL_MPGSOLUTIONBYEMPLOYEENAME + " VARCHAR(50), "
                + COL_MPGLASTMODIFYDATE + " DATETIME, "
                + COL_MPGLASTMODIFYTIME + " CHAR(6), "
                + COL_MPGLASTMODIFYBYUSERNO + " VARCHAR(20), "
                + COL_MPGLASTMODIFYBYUSERNAME + " VARCHAR(50), "
                + "PRIMARY KEY ("+ COL_MPGDOCCODE + ", "+ COL_MPGDOCUMENT + ", "+ COL_MPGDOCUMENTNO +", "+ COL_MPGDOCBRANCH +", "+ COL_MPGDOCSEQ +") "
                + ") ";
        db.execSQL(CREATE_UMANUINSPECTIMAGE_TABLE);
    }

    private void createTABLE_EMPLOYEES(SQLiteDatabase db){
        String CREATE_EMPLOYEES_TABLE = " CREATE TABLE " + TABLE_EMPLOYEES + "("
                + COL_ID  + " VARCHAR(20) PRIMARY KEY, "
                + COL_EMPLOYEENAME + " VARCHAR(50), "
                + COL_EMPGROUP + " VARCHAR(20), "
                + COL_POSITION + " VARCHAR(30), "
                + COL_DEPARTMENT + " VARCHAR(30), "
                + COL_SYSTEMGROUP + " INTEGER, "
                + COL_PASSWD + " VARCHAR(15), "
                + COL_SALARY + " INTEGER, "
                + COL_MAXPARTDISC + " VARCHAR(220), "
                + COL_MAXSERVICEDISC + " VARCHAR(220), "
                + COL_STARTDATE + " DATETIME, "
                + COL_EXPIREDDATE + " DATETIME, "
                + COL_YTDPRODUCTION + " INTEGER, "
                + COL_STATUS + " VARCHAR(1), "
                + COL_CURRENTJOB + " INTEGER, "
                + COL_CURRENTREF + " VARCHAR(20), "
                + COL_CURRENTOPERATION + " VARCHAR(20), "
                + COL_CURRENTCOUNTER + " INTEGER, "
                + COL_CURRENTSALETYPE + " CHAR(2), "
                + COL_QUEUE + " INTEGER, "
                + COL_CURRENTREFINT + " INTEGER, "
                + COL_CURRENTIO + " INTEGER, "
                + COL_CURRENTJOBSTATUS + " CHAR(2), "
                + COL_STATUSON + " CHAR(1), "
                + COL_STATUSONDATE + " DATETIME, "
                + COL_BRANCH + " CHAR(5), "
                + COL_WAREHOUSE + " CHAR(2) "
                + ") ";
        db.execSQL(CREATE_EMPLOYEES_TABLE);
        db.execSQL("INSERT INTO " + TABLE_EMPLOYEES + " (" + COL_ID + ", " + COL_EMPLOYEENAME + " ) " +
                   "VALUES ('01', 'admin'), ('02', 'admin'), ('03', 'admin');");
    }

    private void createTABLE_BRANCHCOMPANYCODE(SQLiteDatabase db){
        String CREATE_BRANCHCOMPANYCODE_TABLE = "CREATE TABLE " + TABLE_BRANCHCOMPANYCODE + "("
                + COL_BCCOMPANYCODE + " VARCHAR(5), "
                + COL_BCBRANCHCODE + " VARCHAR(5), "
                + COL_BCCODE + " VARCHAR(5), "
                + COL_BCBRANCHNAME + " VARCHAR(80), "
                + COL_BCADDRESS1 + " VARCHAR(80), "
                + COL_BCADDRESS2 + " VARCHAR(80), "
                + COL_BCKHET + " VARCHAR(80), "
                + COL_BCPROVINCE + " VARCHAR(30), "
                + COL_BCZIPCODE + " VARCHAR(30), "
                + COL_BCTEL + " VARCHAR(30), "
                + COL_BCFAX + " VARCHAR(30), "
                + COL_BCEMAIL + " VARCHAR(80), "
                + COL_BCTAXID + " VARCHAR(20), "
                + COL_BCTAXBRANCH + " VARCHAR(50), "
                + COL_BCBRANCHNAMEENG + " VARCHAR(80), "
                + COL_BCADDRESS1ENG + " VARCHAR(80), "
                + COL_BCADDRESS2ENG + " VARCHAR(80), "
                + COL_BCKHETENG + " VARCHAR(80), "
                + COL_BCPROVINCEENG + " VARCHAR(30), "
                + COL_BCZIPCODEENG + " VARCHAR(30), "
                + COL_BCTELENG + " VARCHAR(30), "
                + COL_BCFAXENG + " VARCHAR(30) "
                + ") ";
        db.execSQL(CREATE_BRANCHCOMPANYCODE_TABLE);
        db.execSQL("INSERT INTO " + TABLE_BRANCHCOMPANYCODE + " (" + COL_BCCOMPANYCODE + ", " + COL_BCBRANCHCODE + ", " + COL_BCCODE + ", " + COL_BCBRANCHNAME + ") VALUES ('0', '1', '01', 'สำนักงานใหญ่');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_USERLOGIN);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_MANUINSPECT);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_MANUINSPECTIMAGE);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_BRANCHCOMPANYCODE);
        onCreate(db);
    }

    public boolean insertUserLogin(TBUserLoginModel userLogin){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean result = false;
        try{
            database.beginTransaction();
            contentValues.put(COL_ULUSERLOGINID, userLogin.getUlBranchId());
            contentValues.put(COL_ULNAME, userLogin.getUlName());
            contentValues.put(COL_ULPASS, userLogin.getUlPass());
            contentValues.put(COL_ULDESC, userLogin.getUlDesc());
            contentValues.put(COL_ULGROUPID, userLogin.getUlGroupId());
            contentValues.put(COL_ULSTATUS, userLogin.getUlStatus());
            contentValues.put(COL_ULEMPLOYEEID, userLogin.getUlEmployeeId());
            contentValues.put(COL_ULBRANCHID, userLogin.getUlBranchId());
            contentValues.put(COL_ULSETPERMISSION, userLogin.getUlSetPermission());
            contentValues.put(COL_ULREMOTEADDR, userLogin.getUlRemoteAddr());
            database.insert(TABLE_USERLOGIN, null, contentValues);
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public boolean updateUserLogin(TBUserLoginModel userLogin) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean result = false;
        try{
            database.beginTransaction();
            contentValues.put(COL_ULNAME, userLogin.getUlName());
            contentValues.put(COL_ULPASS, userLogin.getUlPass());
            contentValues.put(COL_ULDESC, userLogin.getUlDesc());
            contentValues.put(COL_ULGROUPID, userLogin.getUlGroupId());
            contentValues.put(COL_ULSTATUS, userLogin.getUlStatus());
            contentValues.put(COL_ULEMPLOYEEID, userLogin.getUlEmployeeId());
            contentValues.put(COL_ULBRANCHID, userLogin.getUlBranchId());
            contentValues.put(COL_ULSETPERMISSION, userLogin.getUlSetPermission());
            contentValues.put(COL_ULREMOTEADDR, userLogin.getUlRemoteAddr());
            database.update(TABLE_USERLOGIN, contentValues, COL_ULUSERLOGINID + " = ?", new String[]{userLogin.getUlUserLoginId()});
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public boolean deleteUserLogin(TBUserLoginModel userLogin) {
        database = this.getReadableDatabase();
        boolean result = false;
        try{
            database.beginTransaction();
            database.delete(TABLE_USERLOGIN, COL_ULUSERLOGINID + " = ?", new String[]{userLogin.getUlUserLoginId()});
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public ArrayList<TBUserLoginModel> getAllUserLogin() {
        database = this.getReadableDatabase();
        ArrayList<TBUserLoginModel> userLogins = new ArrayList<TBUserLoginModel>();
        try{
            database.beginTransaction();
            Cursor cursor = database.query(TABLE_USERLOGIN, null, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    TBUserLoginModel userLoginModel = new TBUserLoginModel();
                    userLoginModel.setUlUserLoginId(cursor.getString(0));
                    userLoginModel.setUlName(cursor.getString(1));
                    userLoginModel.setUlPass(cursor.getString(2));
                    userLoginModel.setUlDesc(cursor.getString(3));
                    userLoginModel.setUlGroupId(cursor.getString(4));
                    userLoginModel.setUlStatus(cursor.getString(5));
                    userLoginModel.setUlEmployeeId(cursor.getString(6));
                    userLoginModel.setUlBranchId(cursor.getString(7));
                    userLoginModel.setUlSetPermission(cursor.getString(8));
                    userLoginModel.setUlRemoteAddr(cursor.getString(9));
                    userLogins.add(userLoginModel);
                }
            }
            cursor.close();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return userLogins;
    }

    public String checkUserLogin(String userName, String passWord, String imei){
        String result = "";

        database = this.getReadableDatabase();
        try{
            database.beginTransaction();


            // CHECK USERNAME, PASSWORD
            Cursor cursor = database.query(TABLE_USERLOGIN, null, COL_ULUSERLOGINID + " = ? AND " + COL_ULPASS + " = ? ", new String[]{userName, passWord}, null, null, null);
            if(cursor.getCount() > 0){
                result = "";
            } else {
                result = "Invalid Username or Password";
            }
            cursor.close();

            // CHECK LOGIN
            if(result.equals("")) {
                cursor = database.query(TABLE_USERLOGIN, null, COL_ULUSERLOGINID + " = ? AND " + COL_ULPASS + " = ? AND " + COL_ULDESC + " = ? ", new String[]{userName, passWord, imei}, null, null, null);
                if (cursor.getCount() > 0) {
                    result = "";
                } else {
                    result = "Your device is not support !!";
                }
                cursor.close();
            }
            database.setTransactionSuccessful();
        }catch (SQLException e){
            result = e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }

        return result;
    }

    public boolean insertManuInspect(ManuInspectModel manuInspect){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean result = false;
        try{
            database.beginTransaction();
            contentValues.put(COL_MPDOCCODE, manuInspect.getMpDocCode());
            contentValues.put(COL_MPDOCUMENT, manuInspect.getMpDocument());
            contentValues.put(COL_MPDOCUMENTNO, manuInspect.getMpDocumentNo());
            contentValues.put(COL_MPDOCBRANCH, manuInspect.getMpDocBranch());
            contentValues.put(COL_MPDOCSEQ, manuInspect.getMpDocSeq());
            contentValues.put(COL_MPDOCDATE, manuInspect.getMpDocDate());
            contentValues.put(COL_MPDOCTIME, manuInspect.getMpDocTime());
            contentValues.put(COL_MPEMPLOYEENO, manuInspect.getMpEmployeeNo());
            contentValues.put(COL_MPEMPLOYEENAME, manuInspect.getMpEmployeeNo());
            contentValues.put(COL_MPCUSTOMERNO, manuInspect.getMpCustomerNo());
            contentValues.put(COL_MPCUSTOMERNAME, manuInspect.getMpCustomerName());
            contentValues.put(COL_MPITEMNO, manuInspect.getMpItemNo());
            contentValues.put(COL_MPITEMNO, manuInspect.getMpCustomerName());
            contentValues.put(COL_MPCOLORNO, manuInspect.getMpColorNo());
            contentValues.put(COL_MPCOLORNAME, manuInspect.getMpColorName());
            contentValues.put(COL_MPCONO, manuInspect.getMpCoNo());
            contentValues.put(COL_MPIMAGEPATH, manuInspect.getMpImagePath());
            contentValues.put(COL_MPIMAGEBLOB, manuInspect.getMpImageBlob());
            contentValues.put(COL_MPLASTSENDBYMAIL, manuInspect.getMpLastSendBymail());
            contentValues.put(COL_MPLASTSENDMAILBYUSERNO, manuInspect.getMpLastSendmailByUserNo());
            contentValues.put(COL_MPLASTSENDMAILBYUSERNAME, manuInspect.getMpLastSendmailByUserName());
            contentValues.put(COL_MPLASTSENDMAILDATE, manuInspect.getMpLastSendmailDate());
            contentValues.put(COL_MPLASTSENDMAILTIME, manuInspect.getMpLastSendmailTime());
            contentValues.put(COL_MPLASTMODIFYDATE, manuInspect.getMpLastModifyDate());
            contentValues.put(COL_MPLASTMODIFYTIME, manuInspect.getMpLastModifyTime());
            contentValues.put(COL_MPLASTMODIFYBYUSERNO, manuInspect.getMpLastModifyByUserNo());
            contentValues.put(COL_MPLASTMODIFYBYUSERNAME, manuInspect.getMpLastModifyByUserName());
            database.insert(TABLE_MANUINSPECT, null, contentValues);
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public boolean updateManuInspect(ManuInspectModel manuInspect) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean result = false;
        try{
            database.beginTransaction();
            contentValues.put(COL_MPDOCDATE, manuInspect.getMpDocDate());
            contentValues.put(COL_MPDOCTIME, manuInspect.getMpDocTime());
            contentValues.put(COL_MPEMPLOYEENO, manuInspect.getMpEmployeeNo());
            contentValues.put(COL_MPEMPLOYEENAME, manuInspect.getMpEmployeeNo());
            contentValues.put(COL_MPCUSTOMERNO, manuInspect.getMpCustomerNo());
            contentValues.put(COL_MPCUSTOMERNAME, manuInspect.getMpCustomerName());
            contentValues.put(COL_MPITEMNO, manuInspect.getMpItemNo());
            contentValues.put(COL_MPITEMNO, manuInspect.getMpCustomerName());
            contentValues.put(COL_MPCOLORNO, manuInspect.getMpColorNo());
            contentValues.put(COL_MPCOLORNAME, manuInspect.getMpColorName());
            contentValues.put(COL_MPCONO, manuInspect.getMpCoNo());
            contentValues.put(COL_MPIMAGEPATH, manuInspect.getMpImagePath());
            contentValues.put(COL_MPIMAGEBLOB, manuInspect.getMpImageBlob());
            contentValues.put(COL_MPLASTSENDBYMAIL, manuInspect.getMpLastSendBymail());
            contentValues.put(COL_MPLASTSENDMAILBYUSERNO, manuInspect.getMpLastSendmailByUserNo());
            contentValues.put(COL_MPLASTSENDMAILBYUSERNAME, manuInspect.getMpLastSendmailByUserName());
            contentValues.put(COL_MPLASTSENDMAILDATE, manuInspect.getMpLastSendmailDate());
            contentValues.put(COL_MPLASTSENDMAILTIME, manuInspect.getMpLastSendmailTime());
            contentValues.put(COL_MPLASTMODIFYDATE, manuInspect.getMpLastModifyDate());
            contentValues.put(COL_MPLASTMODIFYTIME, manuInspect.getMpLastModifyTime());
            contentValues.put(COL_MPLASTMODIFYBYUSERNO, manuInspect.getMpLastModifyByUserNo());
            contentValues.put(COL_MPLASTMODIFYBYUSERNAME, manuInspect.getMpLastModifyByUserName());
            database.update(TABLE_MANUINSPECT, contentValues,
                    COL_MPDOCCODE + " = ? AND " + COL_MPDOCUMENT + " = ? AND " + COL_MPDOCUMENTNO + " = ? AND " + COL_MPDOCBRANCH + " = ? AND " + COL_MPDOCSEQ + " = ? ",
                    new String[]{manuInspect.getMpDocCode(), manuInspect.getMpDocument(), String.valueOf(manuInspect.getMpDocumentNo()), manuInspect.getMpDocBranch(), manuInspect.getMpDocSeq() });
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public ArrayList<ManuInspectModel> getAllManuInspect() {
        database = this.getReadableDatabase();
        ArrayList<ManuInspectModel> manuInspects = new ArrayList<ManuInspectModel>();
        try{
            database.beginTransaction();
            Cursor cursor = database.query(TABLE_MANUINSPECT, null, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    ManuInspectModel manuInspectModel = new ManuInspectModel();
                    manuInspectModel.setMpDocCode(cursor.getString(0));
                    manuInspectModel.setMpDocument(cursor.getString(1));
                    manuInspectModel.setMpDocumentNo(cursor.getInt(2));
                    manuInspectModel.setMpDocBranch(cursor.getString(3));
                    manuInspectModel.setMpDocSeq(cursor.getString(4));
                    manuInspectModel.setMpDocDate(cursor.getString(5));
                    manuInspectModel.setMpDocTime(cursor.getString(6));
                    manuInspectModel.setMpEmployeeNo(cursor.getString(7));
                    manuInspectModel.setMpEmployeeName(cursor.getString(8));
                    manuInspectModel.setMpCustomerNo(cursor.getString(9));
                    manuInspectModel.setMpCustomerName(cursor.getString(10));
                    manuInspectModel.setMpItemNo(cursor.getString(11));
                    manuInspectModel.setMpItemName(cursor.getString(12));
                    manuInspectModel.setMpColorNo(cursor.getString(13));
                    manuInspectModel.setMpColorName(cursor.getString(14));
                    manuInspectModel.setMpCoNo(cursor.getString(15));
                    manuInspectModel.setMpImagePath(cursor.getString(16));
                    manuInspectModel.setMpImageBlob(cursor.getString(17));
                    manuInspectModel.setMpLastSendBymail(cursor.getString(18));
                    manuInspectModel.setMpLastSendmailByUserNo(cursor.getString(19));
                    manuInspectModel.setMpLastSendmailByUserName(cursor.getString(20));
                    manuInspectModel.setMpLastSendmailDate(cursor.getString(21));
                    manuInspectModel.setMpLastSendmailTime(cursor.getString(22));
                    manuInspectModel.setMpLastModifyDate(cursor.getString(23));
                    manuInspectModel.setMpLastModifyTime(cursor.getString(24));
                    manuInspectModel.setMpLastModifyByUserNo(cursor.getString(25));
                    manuInspectModel.setMpLastModifyByUserName(cursor.getString(26));
                    manuInspects.add(manuInspectModel);
                }
            }
            cursor.close();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return manuInspects;
    }

    public boolean insertManuInspectImage(ManuInspectImageModel manuInspectImage){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean result = false;
        try{
            database.beginTransaction();
            contentValues.put(COL_MPGDOCCODE, manuInspectImage.getMpgDoccode());
            contentValues.put(COL_MPGDOCUMENT, manuInspectImage.getMpgDoccode());
            contentValues.put(COL_MPGDOCUMENTNO, manuInspectImage.getMpgDocumentno());
            contentValues.put(COL_MPGDOCBRANCH, manuInspectImage.getMpgDocBranch());
            contentValues.put(COL_MPGDOCSEQ, manuInspectImage.getMpgDocSeq());
            contentValues.put(COL_MPGCAUSE, manuInspectImage.getMpgCause());
            contentValues.put(COL_MPGSOLUTION, manuInspectImage.getMpgSolution());
            contentValues.put(COL_MPGMEMO, manuInspectImage.getMpgMemo());
            contentValues.put(COL_MPGIMAGEPATH, manuInspectImage.getMpgImagePath());
            contentValues.put(COL_MPGIMAGEBLOB, manuInspectImage.getMpgImageBlob());
            contentValues.put(COL_MPGCAUSEBYEMPLOYEENO, manuInspectImage.getMpgCauseByEmployeeNo());
            contentValues.put(COL_MPGCAUSEBYEMPLOYEENAME, manuInspectImage.getMpgCauseByEmployeeName());
            contentValues.put(COL_MPGSOLUTIONBYEMPLOYEENO, manuInspectImage.getMpgSolutionByEmployeeNo());
            contentValues.put(COL_MPGSOLUTIONBYEMPLOYEENAME, manuInspectImage.getMpgSolutionByEmployeeName());
            contentValues.put(COL_MPGLASTMODIFYDATE, manuInspectImage.getMpgLastModifyDate());
            contentValues.put(COL_MPGLASTMODIFYTIME, manuInspectImage.getMpgLastModifyTime());
            contentValues.put(COL_MPGLASTMODIFYBYUSERNO, manuInspectImage.getMpgLastModifyByUserNo());
            contentValues.put(COL_MPGLASTMODIFYBYUSERNAME, manuInspectImage.getMpgLastModifyByUserName());
            database.insert(TABLE_MANUINSPECTIMAGE, null, contentValues);
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public boolean updateManuInspectImage(ManuInspectImageModel manuInspectImage) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean result = false;
        try{
            database.beginTransaction();
            contentValues.put(COL_MPGCAUSE, manuInspectImage.getMpgCause());
            contentValues.put(COL_MPGSOLUTION, manuInspectImage.getMpgSolution());
            contentValues.put(COL_MPGMEMO, manuInspectImage.getMpgMemo());
            contentValues.put(COL_MPGIMAGEPATH, manuInspectImage.getMpgImagePath());
            contentValues.put(COL_MPGIMAGEBLOB, manuInspectImage.getMpgImageBlob());
            contentValues.put(COL_MPGCAUSEBYEMPLOYEENO, manuInspectImage.getMpgCauseByEmployeeNo());
            contentValues.put(COL_MPGCAUSEBYEMPLOYEENAME, manuInspectImage.getMpgCauseByEmployeeName());
            contentValues.put(COL_MPGSOLUTIONBYEMPLOYEENO, manuInspectImage.getMpgSolutionByEmployeeNo());
            contentValues.put(COL_MPGSOLUTIONBYEMPLOYEENAME, manuInspectImage.getMpgSolutionByEmployeeName());
            contentValues.put(COL_MPGLASTMODIFYDATE, manuInspectImage.getMpgLastModifyDate());
            contentValues.put(COL_MPGLASTMODIFYTIME, manuInspectImage.getMpgLastModifyTime());
            contentValues.put(COL_MPGLASTMODIFYBYUSERNO, manuInspectImage.getMpgLastModifyByUserNo());
            contentValues.put(COL_MPGLASTMODIFYBYUSERNAME, manuInspectImage.getMpgLastModifyByUserName());
            database.update(TABLE_MANUINSPECTIMAGE, contentValues,
                    COL_MPGDOCCODE + " = ? AND " + COL_MPGDOCUMENT + " = ? AND " + COL_MPGDOCUMENTNO + " = ? AND " + COL_MPGDOCBRANCH + " = ? AND " + COL_MPGDOCSEQ + " = ? ",
                    new String[]{manuInspectImage.getMpgDoccode(), manuInspectImage.getMpgDocument(), String.valueOf(manuInspectImage.getMpgDocumentno()), manuInspectImage.getMpgDocBranch(), manuInspectImage.getMpgDocSeq()});
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public boolean deleteManuInspectImage(ManuInspectImageModel manuInspectImage) {
        database = this.getReadableDatabase();
        boolean result = false;
        try{
            database.beginTransaction();
            database.delete(TABLE_MANUINSPECTIMAGE,
                    COL_MPGDOCCODE + " = ? AND " + COL_MPGDOCUMENT + " = ? AND " + COL_MPGDOCUMENTNO + " = ? AND " + COL_MPGDOCBRANCH + " = ? AND " + COL_MPGDOCSEQ + " = ? ",
                    new String[]{manuInspectImage.getMpgDoccode(), manuInspectImage.getMpgDocument(), String.valueOf(manuInspectImage.getMpgDocumentno()), manuInspectImage.getMpgDocBranch(), manuInspectImage.getMpgDocSeq()});
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public ArrayList<ManuInspectImageModel> getAllManuInspectImage() {
        database = this.getReadableDatabase();
        ArrayList<ManuInspectImageModel> manuInspectImages = new ArrayList<ManuInspectImageModel>();
        try{
            database.beginTransaction();
            Cursor cursor = database.query(TABLE_MANUINSPECTIMAGE, null, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
                    manuInspectImageModel.setMpgDoccode(cursor.getString(0));
                    manuInspectImageModel.setMpgDocument(cursor.getString(1));
                    manuInspectImageModel.setMpgDocumentno(cursor.getInt(2));
                    manuInspectImageModel.setMpgDocBranch(cursor.getString(3));
                    manuInspectImageModel.setMpgDocSeq(cursor.getString(4));
                    manuInspectImageModel.setMpgCause(cursor.getString(5));
                    manuInspectImageModel.setMpgSolution(cursor.getString(6));
                    manuInspectImageModel.setMpgMemo(cursor.getString(7));
                    manuInspectImageModel.setMpgImagePath(cursor.getString(8));
                    manuInspectImageModel.setMpgImageBlob(cursor.getString(9));
                    manuInspectImageModel.setMpgCauseByEmployeeNo(cursor.getString(10));
                    manuInspectImageModel.setMpgCauseByEmployeeName(cursor.getString(11));
                    manuInspectImageModel.setMpgSolutionByEmployeeNo(cursor.getString(12));
                    manuInspectImageModel.setMpgSolutionByEmployeeName(cursor.getString(13));
                    manuInspectImageModel.setMpgLastModifyDate(cursor.getString(14));
                    manuInspectImageModel.setMpgLastModifyTime(cursor.getString(15));
                    manuInspectImageModel.setMpgLastModifyByUserNo(cursor.getString(16));
                    manuInspectImageModel.setMpgLastModifyByUserName(cursor.getString(17));
                    manuInspectImages.add(manuInspectImageModel);
                }
            }
            cursor.close();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return manuInspectImages;
    }

    public EmployeesModel getEmployee(String userName, String passWord, String imei){
        String employeeId = "";
        EmployeesModel employeesModel = new EmployeesModel();
        database = this.getReadableDatabase();
        try{
            database.beginTransaction();
            //get userLoginId
            Cursor cursor_log = database.query(TABLE_USERLOGIN, new String[]{COL_ULEMPLOYEEID}, COL_ULUSERLOGINID + " = ? AND " + COL_ULPASS + " = ? AND " + COL_ULDESC + " = ? ", new String[]{userName, passWord, imei}, null, null, null);
            if(cursor_log.getCount() > 0){
                cursor_log.moveToNext();
                employeeId = cursor_log.getString(0);
            }
            //get employeeName
            Cursor cursor_emp = database.query(TABLE_EMPLOYEES, new String[]{COL_ID, COL_EMPLOYEENAME, COL_BRANCH}, COL_ID + " = ? ", new String[]{employeeId}, null, null, null);
            if(cursor_emp.getCount() > 0){
                cursor_emp.moveToNext();
                employeesModel.setId(cursor_emp.getString(0));
                employeesModel.setEmployeeName(cursor_emp.getString(1));
                employeesModel.setBranch(cursor_emp.getString(2));
            }
            cursor_log.close();
            cursor_emp.close();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return employeesModel;
    }

    public TBUserLoginModel getUserLogin(String userName, String passWord, String imei){
        TBUserLoginModel tbUserLoginModel = new TBUserLoginModel();
        database = this.getReadableDatabase();
        try{
            database.beginTransaction();
            Cursor cursor = database.query(TABLE_USERLOGIN, new String[]{COL_ULUSERLOGINID, COL_ULNAME, COL_ULEMPLOYEEID, COL_ULBRANCHID}, COL_ULUSERLOGINID + " = ? AND " + COL_ULPASS + " = ? AND " + COL_ULDESC + " = ? ", new String[]{userName, passWord, imei}, null, null, null);
            if(cursor.getCount() > 0){
                cursor.moveToNext();
                tbUserLoginModel.setUlUserLoginId(cursor.getString(0));
                tbUserLoginModel.setUlName(cursor.getString(1));
                tbUserLoginModel.setUlEmployeeId(cursor.getString(2));
                tbUserLoginModel.setUlBranchId(cursor.getString(3));
            }
            cursor.close();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return tbUserLoginModel;
    }

    public BranchCompanyCodeModel getBranchCompanyCode(String userName, String passWord, String imei){
        String branchCode = "";
        BranchCompanyCodeModel branchCompanyCodeModel = new BranchCompanyCodeModel();
        database = this.getReadableDatabase();
        try{
            database.beginTransaction();
            //get userLoginId
            Cursor cursor_log = database.query(TABLE_USERLOGIN, new String[]{COL_ULBRANCHID}, COL_ULUSERLOGINID + " = ? AND " + COL_ULPASS + " = ? AND " + COL_ULDESC + " = ? ", new String[]{userName, passWord, imei}, null, null, null);
            if(cursor_log.getCount() > 0){
                cursor_log.moveToNext();
                branchCode = cursor_log.getString(0);
            }
            //get employeeName
            Cursor cursor_bc = database.query(TABLE_BRANCHCOMPANYCODE, new String[]{COL_BCCOMPANYCODE, COL_BCBRANCHCODE, COL_BCCODE, COL_BCBRANCHNAME}, COL_BCCODE + " = ? ", new String[]{branchCode}, null, null, null);
            if(cursor_bc.getCount() > 0){
                cursor_bc.moveToNext();
                branchCompanyCodeModel.setBcCompanyCode(cursor_bc.getString(0));
                branchCompanyCodeModel.setBcBranchCode(cursor_bc.getString(1));
                branchCompanyCodeModel.setBcCode(cursor_bc.getString(2));
                branchCompanyCodeModel.setBcBranchName(cursor_bc.getString(3));
            }
            cursor_log.close();
            cursor_bc.close();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return branchCompanyCodeModel;
    }

    public boolean save(ManuInspectModel manuInspect){
        boolean result = false;
        try{
            if(manuInspect.getMpDocumentNo()> 0){
                result = update(manuInspect);
            }else{
                result = insert(manuInspect);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return result;
    }

    public boolean insert(ManuInspectModel manuInspect){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean result = false;
        try{
            database.beginTransaction();
            contentValues.put(COL_MPDOCCODE, manuInspect.getMpDocCode());
            contentValues.put(COL_MPDOCUMENT, manuInspect.getMpDocument());
            contentValues.put(COL_MPDOCBRANCH, manuInspect.getMpDocBranch());
            contentValues.put(COL_MPDOCSEQ, manuInspect.getMpDocSeq());
            contentValues.put(COL_MPDOCDATE, formatSQLDate(manuInspect.getMpDocDate()));
            contentValues.put(COL_MPDOCTIME, manuInspect.getMpDocTime());
            contentValues.put(COL_MPEMPLOYEENO, manuInspect.getMpEmployeeNo());
            contentValues.put(COL_MPEMPLOYEENAME, manuInspect.getMpEmployeeName());
            contentValues.put(COL_MPCUSTOMERNO, manuInspect.getMpCustomerNo());
            contentValues.put(COL_MPCUSTOMERNAME, manuInspect.getMpCustomerName());
            contentValues.put(COL_MPITEMNO, manuInspect.getMpItemNo());
            contentValues.put(COL_MPITEMNAME, manuInspect.getMpItemName());
            contentValues.put(COL_MPCOLORNO, manuInspect.getMpColorNo());
            contentValues.put(COL_MPCOLORNAME, manuInspect.getMpColorName());
            contentValues.put(COL_MPCONO, manuInspect.getMpCoNo());
            contentValues.put(COL_MPIMAGEPATH, manuInspect.getMpImagePath());
            contentValues.put(COL_MPIMAGEBLOB, manuInspect.getMpImageBlob());
            contentValues.put(COL_MPLASTSENDBYMAIL, manuInspect.getMpLastSendBymail());
            contentValues.put(COL_MPLASTSENDMAILBYUSERNO, manuInspect.getMpLastSendmailByUserNo());
            contentValues.put(COL_MPLASTSENDMAILBYUSERNAME, manuInspect.getMpLastSendmailByUserName());
            contentValues.put(COL_MPLASTSENDMAILDATE, manuInspect.getMpLastSendmailDate());
            contentValues.put(COL_MPLASTSENDMAILTIME, manuInspect.getMpLastSendmailTime());
            long success = database.insert(TABLE_MANUINSPECT, null, contentValues);

            if(success > 0){
                Cursor cursor = database.query(TABLE_MANUINSPECT, new String[]{COL_MPDOCUMENTNO}, null, null, null, null, null);
                cursor.moveToLast();
                int mpgDocumentNo = cursor.getInt(0);
                if(manuInspect.getManuInspectImageModelList() != null){
                   List<ManuInspectImageModel> manuInspectImageModelList = manuInspect.getManuInspectImageModelList();
                    for (int i = 0; i < manuInspectImageModelList.size(); i++) {
                        ManuInspectImageModel manuInspectImage = manuInspectImageModelList.get(i);
                        contentValues = new ContentValues();
                        contentValues.put(COL_MPGDOCCODE, manuInspectImage.getMpgDoccode());
                        contentValues.put(COL_MPGDOCUMENT, manuInspectImage.getMpgDocument());
                        contentValues.put(COL_MPGDOCUMENTNO, mpgDocumentNo);
                        contentValues.put(COL_MPGDOCBRANCH, manuInspectImage.getMpgDocBranch());
                        contentValues.put(COL_MPGDOCSEQ, manuInspectImage.getMpgDocSeq());
                        contentValues.put(COL_MPGCAUSE, manuInspectImage.getMpgCause());
                        contentValues.put(COL_MPGSOLUTION, manuInspectImage.getMpgSolution());
                        contentValues.put(COL_MPGMEMO, manuInspectImage.getMpgMemo());
                        contentValues.put(COL_MPGIMAGEPATH, manuInspectImage.getMpgImagePath());
                        contentValues.put(COL_MPGIMAGEBLOB, manuInspectImage.getMpgImageBlob());
                        contentValues.put(COL_MPGCAUSEBYEMPLOYEENO, manuInspectImage.getMpgCauseByEmployeeNo());
                        contentValues.put(COL_MPGCAUSEBYEMPLOYEENAME, manuInspectImage.getMpgCauseByEmployeeName());
                        contentValues.put(COL_MPGSOLUTIONBYEMPLOYEENO, manuInspectImage.getMpgSolutionByEmployeeNo());
                        contentValues.put(COL_MPGSOLUTIONBYEMPLOYEENAME, manuInspectImage.getMpgSolutionByEmployeeName());
                        database.insert(TABLE_MANUINSPECTIMAGE, null, contentValues);
                    }
                }
                cursor.close();
            }
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public boolean update(ManuInspectModel manuInspect){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean result = false;
        try{
            database.beginTransaction();
            contentValues.put(COL_MPDOCDATE, formatSQLDate(manuInspect.getMpDocDate()));
            contentValues.put(COL_MPDOCTIME, manuInspect.getMpDocTime());
            contentValues.put(COL_MPEMPLOYEENO, manuInspect.getMpEmployeeNo());
            contentValues.put(COL_MPEMPLOYEENAME, manuInspect.getMpEmployeeName());
            contentValues.put(COL_MPCUSTOMERNO, manuInspect.getMpCustomerNo());
            contentValues.put(COL_MPCUSTOMERNAME, manuInspect.getMpCustomerName());
            contentValues.put(COL_MPITEMNO, manuInspect.getMpItemNo());
            contentValues.put(COL_MPITEMNAME, manuInspect.getMpItemName());
            contentValues.put(COL_MPCOLORNO, manuInspect.getMpColorNo());
            contentValues.put(COL_MPCOLORNAME, manuInspect.getMpColorName());
            contentValues.put(COL_MPCONO, manuInspect.getMpCoNo());
            contentValues.put(COL_MPIMAGEPATH, manuInspect.getMpImagePath());
            contentValues.put(COL_MPIMAGEBLOB, manuInspect.getMpImageBlob());
            contentValues.put(COL_MPLASTSENDBYMAIL, manuInspect.getMpLastSendBymail());
            contentValues.put(COL_MPLASTSENDMAILBYUSERNO, manuInspect.getMpLastSendmailByUserNo());
            contentValues.put(COL_MPLASTSENDMAILBYUSERNAME, manuInspect.getMpLastSendmailByUserName());
            contentValues.put(COL_MPLASTSENDMAILDATE, manuInspect.getMpLastSendmailDate());
            contentValues.put(COL_MPLASTSENDMAILTIME, manuInspect.getMpLastSendmailTime());
            contentValues.put(COL_MPLASTMODIFYDATE, manuInspect.getMpLastModifyDate());
            contentValues.put(COL_MPLASTMODIFYTIME, manuInspect.getMpLastModifyTime());
            contentValues.put(COL_MPLASTMODIFYBYUSERNO, manuInspect.getMpLastModifyByUserNo());
            contentValues.put(COL_MPLASTMODIFYBYUSERNAME, manuInspect.getMpLastModifyByUserName());
            long success = database.update(TABLE_MANUINSPECT, contentValues,
                    COL_MPDOCCODE + " = ? AND " + COL_MPDOCUMENT + " = ? AND " + COL_MPDOCUMENTNO + " = ? AND " + COL_MPDOCBRANCH + " = ? AND " + COL_MPDOCSEQ + " = ? ",
                    new String[]{manuInspect.getMpDocCode(), manuInspect.getMpDocument(), String.valueOf(manuInspect.getMpDocumentNo()), manuInspect.getMpDocBranch(), manuInspect.getMpDocSeq() });

            if(success > 0){
                Cursor cursor = database.query(TABLE_MANUINSPECTIMAGE, new String[]{COL_MPGDOCCODE, COL_MPGDOCUMENT, COL_MPGDOCUMENTNO, COL_MPGDOCBRANCH}
                        , COL_MPGDOCCODE + " = ? AND " + COL_MPGDOCUMENT + " = ? AND " + COL_MPGDOCUMENTNO + " = ? AND " + COL_MPGDOCBRANCH + " = ? "
                        , new String[]{manuInspect.getMpDocCode(), manuInspect.getMpDocument(), String.valueOf(manuInspect.getMpDocumentNo()), manuInspect.getMpDocBranch()}, null, null, null);
                if(cursor.getCount() > 0) {
                    cursor.moveToNext();
                    ManuInspectImageModel manuInspectImageTmp = new ManuInspectImageModel();
                    manuInspectImageTmp.setMpgDoccode(cursor.getString(0));
                    manuInspectImageTmp.setMpgDocument(cursor.getString(1));
                    manuInspectImageTmp.setMpgDocumentno(cursor.getInt(2));
                    manuInspectImageTmp.setMpgDocBranch(cursor.getString(3));

                    database.delete(TABLE_MANUINSPECTIMAGE,
                            COL_MPGDOCCODE + " = ? AND " + COL_MPGDOCUMENT + " = ? AND " + COL_MPGDOCUMENTNO + " = ? AND " + COL_MPGDOCBRANCH + " = ? ",
                            new String[]{manuInspectImageTmp.getMpgDoccode(), manuInspectImageTmp.getMpgDocument(), String.valueOf(manuInspectImageTmp.getMpgDocumentno()), manuInspectImageTmp.getMpgDocBranch()});

                }

                if(manuInspect.getManuInspectImageModelList() != null){
                    List<ManuInspectImageModel> manuInspectImageModelList = manuInspect.getManuInspectImageModelList();
                    for (int i = 0; i < manuInspectImageModelList.size(); i++) {
                        ManuInspectImageModel manuInspectImage = manuInspectImageModelList.get(i);
                        contentValues = new ContentValues();
                        contentValues.put(COL_MPGDOCCODE, manuInspectImage.getMpgDoccode());
                        contentValues.put(COL_MPGDOCUMENT, manuInspectImage.getMpgDocument());
                        contentValues.put(COL_MPGDOCUMENTNO, manuInspectImage.getMpgDocumentno());
                        contentValues.put(COL_MPGDOCBRANCH, manuInspectImage.getMpgDocBranch());
                        contentValues.put(COL_MPGDOCSEQ, manuInspectImage.getMpgDocSeq());
                        contentValues.put(COL_MPGCAUSE, manuInspectImage.getMpgCause());
                        contentValues.put(COL_MPGSOLUTION, manuInspectImage.getMpgSolution());
                        contentValues.put(COL_MPGMEMO, manuInspectImage.getMpgMemo());
                        contentValues.put(COL_MPGIMAGEPATH, manuInspectImage.getMpgImagePath());
                        contentValues.put(COL_MPGIMAGEBLOB, manuInspectImage.getMpgImageBlob());
                        contentValues.put(COL_MPGCAUSEBYEMPLOYEENO, manuInspectImage.getMpgCauseByEmployeeNo());
                        contentValues.put(COL_MPGCAUSEBYEMPLOYEENAME, manuInspectImage.getMpgCauseByEmployeeName());
                        contentValues.put(COL_MPGSOLUTIONBYEMPLOYEENO, manuInspectImage.getMpgSolutionByEmployeeNo());
                        contentValues.put(COL_MPGSOLUTIONBYEMPLOYEENAME, manuInspectImage.getMpgSolutionByEmployeeName());
                        contentValues.put(COL_MPGLASTMODIFYDATE, manuInspectImage.getMpgLastModifyDate());
                        contentValues.put(COL_MPGLASTMODIFYTIME, manuInspectImage.getMpgLastModifyTime());
                        contentValues.put(COL_MPGLASTMODIFYBYUSERNO, manuInspectImage.getMpgLastModifyByUserNo());
                        contentValues.put(COL_MPGLASTMODIFYBYUSERNAME, manuInspectImage.getMpgLastModifyByUserName());
                        database.insert(TABLE_MANUINSPECTIMAGE, null, contentValues);
                    }
                }
                cursor.close();
            }
            database.setTransactionSuccessful();
            result = true;
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }

    public ManuInspectModel getDataForUpdate(int documentNo) {
        database = this.getReadableDatabase();
        ManuInspectModel manuInspectModel =  new ManuInspectModel();
        try{
            database.beginTransaction();
            Cursor cursor_mp = database.query(TABLE_MANUINSPECT, null, COL_MPDOCUMENTNO + " = ? ", new String[]{String.valueOf(documentNo)}, null, null, null);
            if (cursor_mp.getCount() > 0) {
                cursor_mp.moveToNext();
                manuInspectModel.setMpDocCode(cursor_mp.getString(0));
                manuInspectModel.setMpDocument(cursor_mp.getString(1));
                manuInspectModel.setMpDocumentNo(cursor_mp.getInt(2));
                manuInspectModel.setMpDocBranch(cursor_mp.getString(3));
                manuInspectModel.setMpDocSeq(cursor_mp.getString(4));
                manuInspectModel.setMpDocDate(formatDate(cursor_mp.getString(5)));
                manuInspectModel.setMpDocTime(cursor_mp.getString(6));
                manuInspectModel.setMpEmployeeNo(cursor_mp.getString(7));
                manuInspectModel.setMpEmployeeName(cursor_mp.getString(8));
                manuInspectModel.setMpCustomerNo(cursor_mp.getString(9));
                manuInspectModel.setMpCustomerName(cursor_mp.getString(10));
                manuInspectModel.setMpItemNo(cursor_mp.getString(11));
                manuInspectModel.setMpItemName(cursor_mp.getString(12));
                manuInspectModel.setMpColorNo(cursor_mp.getString(13));
                manuInspectModel.setMpColorName(cursor_mp.getString(14));
                manuInspectModel.setMpCoNo(cursor_mp.getString(15));
                manuInspectModel.setMpImagePath(cursor_mp.getString(16));
                manuInspectModel.setMpImageBlob(cursor_mp.getString(17));
                manuInspectModel.setMpLastSendBymail(cursor_mp.getString(18));
                manuInspectModel.setMpLastSendmailByUserNo(cursor_mp.getString(19));
                manuInspectModel.setMpLastSendmailByUserName(cursor_mp.getString(20));
                manuInspectModel.setMpLastSendmailDate(cursor_mp.getString(21));
                manuInspectModel.setMpLastSendmailTime(cursor_mp.getString(22));
                manuInspectModel.setMpLastModifyDate(cursor_mp.getString(23));
                manuInspectModel.setMpLastModifyTime(cursor_mp.getString(24));
                manuInspectModel.setMpLastModifyByUserNo(cursor_mp.getString(25));
                manuInspectModel.setMpLastModifyByUserName(cursor_mp.getString(26));

                Cursor cursor_mpg = database.query(TABLE_MANUINSPECTIMAGE, null
                        ,COL_MPGDOCCODE + " = ?  AND " + COL_MPGDOCUMENT + " = ? AND " + COL_MPGDOCUMENTNO + " = ? AND " + COL_MPGDOCBRANCH + " = ? "
                        , new String[]{manuInspectModel.getMpDocCode(), manuInspectModel.getMpDocument(), String.valueOf(manuInspectModel.getMpDocumentNo()), manuInspectModel.getMpDocBranch()}, null, null, null);
                List<ManuInspectImageModel> manuInspectImageList = new ArrayList<ManuInspectImageModel>();
                if (cursor_mpg.getCount() > 0) {
                    for (int i = 0; i < cursor_mpg.getCount(); i++) {
                        cursor_mpg.moveToNext();
                        ManuInspectImageModel manuInspectImageModel = new ManuInspectImageModel();
                        manuInspectImageModel.setMpgDoccode(cursor_mpg.getString(0));
                        manuInspectImageModel.setMpgDocument(cursor_mpg.getString(1));
                        manuInspectImageModel.setMpgDocumentno(cursor_mpg.getInt(2));
                        manuInspectImageModel.setMpgDocBranch(cursor_mpg.getString(3));
                        manuInspectImageModel.setMpgDocSeq(cursor_mpg.getString(4));
                        manuInspectImageModel.setMpgCause(cursor_mpg.getString(5));
                        manuInspectImageModel.setMpgSolution(cursor_mpg.getString(6));
                        manuInspectImageModel.setMpgMemo(cursor_mpg.getString(7));
                        manuInspectImageModel.setMpgImagePath(cursor_mpg.getString(8));
                        manuInspectImageModel.setMpgImageBlob(cursor_mpg.getString(9));
                        manuInspectImageModel.setMpgCauseByEmployeeNo(cursor_mpg.getString(10));
                        manuInspectImageModel.setMpgCauseByEmployeeName(cursor_mpg.getString(11));
                        manuInspectImageModel.setMpgSolutionByEmployeeNo(cursor_mpg.getString(12));
                        manuInspectImageModel.setMpgSolutionByEmployeeName(cursor_mpg.getString(13));
                        manuInspectImageModel.setMpgLastModifyDate(cursor_mpg.getString(14));
                        manuInspectImageModel.setMpgLastModifyTime(cursor_mpg.getString(15));
                        manuInspectImageModel.setMpgLastModifyByUserNo(cursor_mpg.getString(16));
                        manuInspectImageModel.setMpgLastModifyByUserName(cursor_mpg.getString(17));
                        manuInspectImageList.add(manuInspectImageModel);
                    }
                }
                cursor_mpg.close();
                manuInspectModel.setManuInspectImageModelList(manuInspectImageList);
            }
            cursor_mp.close();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return manuInspectModel;
    }

    public ArrayList<ManuInspectModel> searchManuInspect(String dateFrom, String dateTo, String sendMail) {
        database = this.getReadableDatabase();
        ArrayList<ManuInspectModel> manuInspects = new ArrayList<ManuInspectModel>();
        String strSelection = null;
        String[] strSelectionArgs = null;
        try{

            if((dateFrom != "" && dateFrom != null) && (dateTo != "" && dateTo != null)){
                strSelection = " strftime('%Y-%m-%d', "+COL_MPDOCDATE+") BETWEEN ? AND ? ";
                strSelectionArgs = new String[]{formatSQLDate(dateFrom), formatSQLDate(dateTo)};
            }
            if(sendMail != "" && sendMail != null){
                if(sendMail.equals("Y")){
                    strSelection = strSelection + "AND "+COL_MPLASTSENDMAILBYUSERNO+" IS NOT NULL ";
                }else{
                    strSelection = strSelection + "AND "+COL_MPLASTSENDMAILBYUSERNO+" IS NULL ";
                }

            }

            database.beginTransaction();
            Cursor cursor = database.query(TABLE_MANUINSPECT, null
                    , strSelection
                    , strSelectionArgs, null, null, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    ManuInspectModel manuInspectModel = new ManuInspectModel();
                    manuInspectModel.setMpDocCode(cursor.getString(0));
                    manuInspectModel.setMpDocument(cursor.getString(1));
                    manuInspectModel.setMpDocumentNo(cursor.getInt(2));
                    manuInspectModel.setMpDocBranch(cursor.getString(3));
                    manuInspectModel.setMpDocSeq(cursor.getString(4));
                    manuInspectModel.setMpDocDate(formatDate(cursor.getString(5)));
                    manuInspectModel.setMpDocTime(cursor.getString(6));
                    manuInspectModel.setMpEmployeeNo(cursor.getString(7));
                    manuInspectModel.setMpEmployeeName(cursor.getString(8));
                    manuInspectModel.setMpCustomerNo(cursor.getString(9));
                    manuInspectModel.setMpCustomerName(cursor.getString(10));
                    manuInspectModel.setMpItemNo(cursor.getString(11));
                    manuInspectModel.setMpItemName(cursor.getString(12));
                    manuInspectModel.setMpColorNo(cursor.getString(13));
                    manuInspectModel.setMpColorName(cursor.getString(14));
                    manuInspectModel.setMpCoNo(cursor.getString(15));
                    manuInspectModel.setMpImagePath(cursor.getString(16));
                    manuInspectModel.setMpImageBlob(cursor.getString(17));
                    manuInspectModel.setMpLastSendBymail(cursor.getString(18));
                    manuInspectModel.setMpLastSendmailByUserNo(cursor.getString(19));
                    manuInspectModel.setMpLastSendmailByUserName(cursor.getString(20));
                    manuInspectModel.setMpLastSendmailDate(cursor.getString(21));
                    manuInspectModel.setMpLastSendmailTime(cursor.getString(22));
                    manuInspectModel.setMpLastModifyDate(cursor.getString(23));
                    manuInspectModel.setMpLastModifyTime(cursor.getString(24));
                    manuInspectModel.setMpLastModifyByUserNo(cursor.getString(25));
                    manuInspectModel.setMpLastModifyByUserName(cursor.getString(26));
                    manuInspects.add(manuInspectModel);
                }
            }
            cursor.close();
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.endTransaction();
            database.close();
        }
        return manuInspects;
    }

    private String formatDate(String strDate){
        String dateFormat = null;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
            dateFormat = new SimpleDateFormat("dd-MM-yyyy").format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return dateFormat;
    }

    private String formatSQLDate(String strDate){
        String dateFormat = null;
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return dateFormat;
    }
}
