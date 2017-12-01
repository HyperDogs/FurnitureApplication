package com.example.ton.furnitureapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Model.ManuInspectImageModel;
import Model.ManuInspectModel;
import Model.TBUserLoginModel;

/**
 * Created by marisalom on 25/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "SQLiteDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERLOGIN = "TBUserLogin";
    public static final String TABLE_MANUINSPECT = "ManuInspect";
    public static final String TABLE_MANUINSPECTIMAGE = "ManuInspectImage";

    public static final String COL_USERLOGINID = "ulUserLoginId";
    public static final String COL_NAME = "ulName";
    public static final String COL_PASS = "ulPass";
    public static final String COL_DESC = "ulDesc";
    public static final String COL_GROUPID = "ulGroupId";
    public static final String COL_STATUS = "ulStatus";
    public static final String COL_EMPLOYEEID = "ulEmployeeId";
    public static final String COL_BRANCHID = "ulBranchId";
    public static final String COL_SETPERMISSION = "ulSetPermission";
    public static final String COL_REMOTEADDR = "ulRemoteAddr";

    public static final String COL_DOCCODE = "mpDocCode";
    public static final String COL_DOCUMENT = "mpDocument";
    public static final String COL_DOCUMENTNO = "mpDocumentNo";
    public static final String COL_DOCBRANCH = "mpDocBranch";
    public static final String COL_DOCSEQ = "mpDocSeq";
    public static final String COL_DOCDATE = "mpDocDate";
    public static final String COL_DOCTIME = "mpDocTime";
    public static final String COL_EMPLOYEENO = "mpEmployeeNo";
    public static final String COL_EMPLOYEENAME = "mpEmployeeName";
    public static final String COL_CUSTOMERNO = "mpCustomerNo";
    public static final String COL_CUSTOMERNAME = "mpCustomerName";
    public static final String COL_ITEMNO = "mpItemNo";
    public static final String COL_ITEMNAME = "mpItemName";
    public static final String COL_COLORNO = "mpColorNo";
    public static final String COL_COLORNAME = "mpColorName";
    public static final String COL_CONO = "mpCoNo";
    public static final String COL_IMAGEPATH = "mpImagePath";
    public static final String COL_IMAGEBLOB = "mpImageBlob";
    public static final String COL_LASTSENDBYMAIL = "mpLastSendBymail";
    public static final String COL_LASTSENDMAILBYUSERNO = "mpLastSendmailByUserNo";
    public static final String COL_LASTSENDMAILBYUSERNAME = "mpLastSendmailByUserName";
    public static final String COL_LASTSENDMAILDATE = "mpLastSendmailDate";
    public static final String COL_LASTSENDMAILTIME = "mpLastSendmailTime";
    public static final String COL_LASTMODIFYDATE = "mpLastModifyDate";
    public static final String COL_LASTMODIFYTIME = "mpLastModifyTime";
    public static final String COL_LASTMODIFYBYUSERNO = "mpLastModifyByUserNo";
    public static final String COL_LASTMODIFYBYUSERNAME = "mpLastModifyByUserName";

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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTABLE_USERLOGIN(db);
        createTABLE_MANUINSPECT(db);
        createTABLE_MANUINSPECTIMAGE(db);
    }

    private void createTABLE_USERLOGIN(SQLiteDatabase db){
        String CREATE_USERLOGIN_TABLE = "CREATE TABLE " + TABLE_USERLOGIN + "("
                + COL_USERLOGINID + " CHAR(10) PRIMARY KEY, "
                + COL_NAME + " VARCHAR(20), "
                + COL_PASS + " VARCHAR(80), "
                + COL_DESC + " VARCHAR(50), "
                + COL_GROUPID + " VARCHAR(10), "
                + COL_STATUS + " CHAR(1), "
                + COL_EMPLOYEEID + " VARCHAR(10), "
                + COL_BRANCHID + " VARCHAR(10), "
                + COL_SETPERMISSION + " CHAR(1), "
                + COL_REMOTEADDR + " VARCHAR(15) "
                + ") ";
        db.execSQL(CREATE_USERLOGIN_TABLE);
        db.execSQL("INSERT INTO " + TABLE_USERLOGIN + " (" + COL_USERLOGINID + ", " + COL_NAME + ", " + COL_PASS + " ) VALUES ('1', 'admin', 'admin');");
    }
    private void createTABLE_MANUINSPECT(SQLiteDatabase db){
        String CREATE_UMANUINSPECT_TABLE = " CREATE TABLE " + TABLE_MANUINSPECT + "("
                + COL_DOCCODE  + " CHAR(4) PRIMARY KEY, "
                + COL_DOCUMENT + " VARCHAR(20), "
                + COL_DOCUMENTNO + " INTEGER, "
                + COL_DOCBRANCH + " CHAR(5), "
                + COL_DOCSEQ + " INTEGER, "
                + COL_DOCDATE + " DATETIME, "
                + COL_DOCTIME + " CHAR(6), "
                + COL_EMPLOYEENO + " VARCHAR(20), "
                + COL_EMPLOYEENAME + " VARCHAR(50), "
                + COL_CUSTOMERNO + " VARCHAR(20), "
                + COL_CUSTOMERNAME + " VARCHAR(100), "
                + COL_ITEMNO + " VARCHAR(30), "
                + COL_ITEMNAME + " VARCHAR(100), "
                + COL_COLORNO + " VARCHAR(20), "
                + COL_COLORNAME + " VARCHAR(100), "
                + COL_CONO + " VARCHAR(50), "
                + COL_IMAGEPATH + " VARCHAR(220), "
                + COL_IMAGEBLOB + " VARCHAR(50), "
                + COL_LASTSENDBYMAIL + " VARCHAR(220), "
                + COL_LASTSENDMAILBYUSERNO + " VARCHAR(20), "
                + COL_LASTSENDMAILBYUSERNAME + " VARCHAR(20), "
                + COL_LASTSENDMAILDATE + " DATETIME, "
                + COL_LASTSENDMAILTIME + " CHAR(6), "
                + COL_LASTMODIFYDATE + " DATETIME, "
                + COL_LASTMODIFYTIME + " CHAR(6), "
                + COL_LASTMODIFYBYUSERNO + " VARCHAR(20), "
                + COL_LASTMODIFYBYUSERNAME + " VARCHAR(50) "
                + ") ";
        db.execSQL(CREATE_UMANUINSPECT_TABLE);
    }

    private void createTABLE_MANUINSPECTIMAGE(SQLiteDatabase db){
        String CREATE_UMANUINSPECTIMAGE_TABLE = " CREATE TABLE " + TABLE_MANUINSPECTIMAGE + "("
                + COL_MPGDOCCODE  + " CHAR(4) PRIMARY KEY, "
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
                + COL_MPGLASTMODIFYBYUSERNAME + " VARCHAR(50) "
                + ") ";
        db.execSQL(CREATE_UMANUINSPECTIMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_USERLOGIN);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_MANUINSPECT);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_MANUINSPECTIMAGE);
        onCreate(db);
    }

    public void insertUserLogin(TBUserLoginModel userLogin){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERLOGINID, userLogin.getUlBranchId());
        contentValues.put(COL_NAME, userLogin.getUlName());
        contentValues.put(COL_PASS, userLogin.getUlPass());
        contentValues.put(COL_DESC, userLogin.getUlDesc());
        contentValues.put(COL_GROUPID, userLogin.getUlGroupId());
        contentValues.put(COL_STATUS, userLogin.getUlStatus());
        contentValues.put(COL_EMPLOYEEID, userLogin.getUlEmployeeId());
        contentValues.put(COL_BRANCHID, userLogin.getUlBranchId());
        contentValues.put(COL_SETPERMISSION, userLogin.getUlSetPermission());
        contentValues.put(COL_REMOTEADDR, userLogin.getUlRemoteAddr());
        database.insert(TABLE_USERLOGIN, null, contentValues);
        database.close();
    }

    public void updateUserLogin(TBUserLoginModel userLogin) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, userLogin.getUlName());
        contentValues.put(COL_PASS, userLogin.getUlPass());
        contentValues.put(COL_DESC, userLogin.getUlDesc());
        contentValues.put(COL_GROUPID, userLogin.getUlGroupId());
        contentValues.put(COL_STATUS, userLogin.getUlStatus());
        contentValues.put(COL_EMPLOYEEID, userLogin.getUlEmployeeId());
        contentValues.put(COL_BRANCHID, userLogin.getUlBranchId());
        contentValues.put(COL_SETPERMISSION, userLogin.getUlSetPermission());
        contentValues.put(COL_REMOTEADDR, userLogin.getUlRemoteAddr());
        database.update(TABLE_USERLOGIN, contentValues, COL_USERLOGINID + " = ?", new String[]{userLogin.getUlUserLoginId()});
        database.close();
    }

    public void deleteUserLogin(TBUserLoginModel userLogin) {
        database = this.getReadableDatabase();
        database.delete(TABLE_USERLOGIN, COL_USERLOGINID + " = ?", new String[]{userLogin.getUlUserLoginId()});
        database.close();
    }

    public ArrayList<TBUserLoginModel> getAllUserLogin() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_USERLOGIN, null, null, null, null, null, null);
        ArrayList<TBUserLoginModel> userLogins = new ArrayList<TBUserLoginModel>();
        TBUserLoginModel userLoginModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                userLoginModel = new TBUserLoginModel();
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
        database.close();
        return userLogins;
    }

    public boolean checkUserLogin(String userName, String passWord){
        boolean result = false;
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_USERLOGIN, null, COL_NAME + " = ? AND " + COL_PASS + " = ? ", new String[]{userName, passWord}, null, null, null);
        if(cursor.getCount() > 0){
            result = true;
        }
        return result;
    }

    public void insertManuInspect(ManuInspectModel manuInspect){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DOCCODE, manuInspect.getMpDocCode());
        contentValues.put(COL_DOCUMENT, manuInspect.getMpDocument());
        contentValues.put(COL_DOCUMENTNO, manuInspect.getMpDocumentNo());
        contentValues.put(COL_DOCBRANCH, manuInspect.getMpDocBranch());
        contentValues.put(COL_DOCSEQ, manuInspect.getMpDocSeq());
        contentValues.put(COL_DOCDATE, manuInspect.getMpDocDate().toString());
        contentValues.put(COL_DOCTIME, manuInspect.getMpDocTime());
        contentValues.put(COL_EMPLOYEENO, manuInspect.getMpEmployeeNo());
        contentValues.put(COL_EMPLOYEENAME, manuInspect.getMpEmployeeNo());
        contentValues.put(COL_CUSTOMERNO, manuInspect.getMpCustomerNo());
        contentValues.put(COL_CUSTOMERNAME, manuInspect.getMpCustomerName());
        contentValues.put(COL_ITEMNO, manuInspect.getMpItemNo());
        contentValues.put(COL_ITEMNO, manuInspect.getMpCustomerName());
        contentValues.put(COL_COLORNO, manuInspect.getMpColorNo());
        contentValues.put(COL_COLORNAME, manuInspect.getMpColorName());
        contentValues.put(COL_CONO, manuInspect.getMpCoNo());
        contentValues.put(COL_IMAGEPATH, manuInspect.getMpImagePath());
        contentValues.put(COL_IMAGEBLOB, manuInspect.getMpImageBlob());
        contentValues.put(COL_LASTSENDBYMAIL, manuInspect.getMpLastSendBymail());
        contentValues.put(COL_LASTSENDMAILBYUSERNO, manuInspect.getMpLastSendmailByUserNo());
        contentValues.put(COL_LASTSENDMAILBYUSERNAME, manuInspect.getMpLastSendmailByUserName());
        contentValues.put(COL_LASTSENDMAILDATE, manuInspect.getMpLastSendmailDate());
        contentValues.put(COL_LASTSENDMAILTIME, manuInspect.getMpLastSendmailTime());
        contentValues.put(COL_LASTMODIFYDATE, manuInspect.getMpLastModifyDate());
        contentValues.put(COL_LASTMODIFYTIME, manuInspect.getMpLastModifyTime());
        contentValues.put(COL_LASTMODIFYBYUSERNO, manuInspect.getMpLastModifyByUserNo());
        contentValues.put(COL_LASTMODIFYBYUSERNAME, manuInspect.getMpLastModifyByUserName());
        database.insert(TABLE_MANUINSPECT, null, contentValues);
        database.close();
    }

    public void updateManuInspect(ManuInspectModel manuInspect) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DOCDATE, manuInspect.getMpDocDate());
        contentValues.put(COL_DOCTIME, manuInspect.getMpDocTime());
        contentValues.put(COL_EMPLOYEENO, manuInspect.getMpEmployeeNo());
        contentValues.put(COL_EMPLOYEENAME, manuInspect.getMpEmployeeNo());
        contentValues.put(COL_CUSTOMERNO, manuInspect.getMpCustomerNo());
        contentValues.put(COL_CUSTOMERNAME, manuInspect.getMpCustomerName());
        contentValues.put(COL_ITEMNO, manuInspect.getMpItemNo());
        contentValues.put(COL_ITEMNO, manuInspect.getMpCustomerName());
        contentValues.put(COL_COLORNO, manuInspect.getMpColorNo());
        contentValues.put(COL_COLORNAME, manuInspect.getMpColorName());
        contentValues.put(COL_CONO, manuInspect.getMpCoNo());
        contentValues.put(COL_IMAGEPATH, manuInspect.getMpImagePath());
        contentValues.put(COL_IMAGEBLOB, manuInspect.getMpImageBlob());
        contentValues.put(COL_LASTSENDBYMAIL, manuInspect.getMpLastSendBymail());
        contentValues.put(COL_LASTSENDMAILBYUSERNO, manuInspect.getMpLastSendmailByUserNo());
        contentValues.put(COL_LASTSENDMAILBYUSERNAME, manuInspect.getMpLastSendmailByUserName());
        contentValues.put(COL_LASTSENDMAILDATE, manuInspect.getMpLastSendmailDate());
        contentValues.put(COL_LASTSENDMAILTIME, manuInspect.getMpLastSendmailTime());
        contentValues.put(COL_LASTMODIFYDATE, manuInspect.getMpLastModifyDate());
        contentValues.put(COL_LASTMODIFYTIME, manuInspect.getMpLastModifyTime());
        contentValues.put(COL_LASTMODIFYBYUSERNO, manuInspect.getMpLastModifyByUserNo());
        contentValues.put(COL_LASTMODIFYBYUSERNAME, manuInspect.getMpLastModifyByUserName());
        database.update(TABLE_MANUINSPECT, contentValues,
                COL_DOCCODE + " = ? AND " + COL_DOCUMENT + " = ? AND " + COL_DOCUMENTNO + " = ? AND " + COL_DOCBRANCH + " = ? AND " + COL_DOCSEQ + " = ? ",
                new String[]{manuInspect.getMpDocCode(), manuInspect.getMpDocument(), manuInspect.getMpDocumentNo(), manuInspect.getMpDocBranch(), manuInspect.getMpDocSeq() });
        database.close();
    }

    public void deleteManuInspect(ManuInspectModel manuInspect) {
        database = this.getReadableDatabase();
        database.delete(TABLE_MANUINSPECT,
                COL_DOCCODE + " = ? AND " + COL_DOCUMENT + " = ? AND " + COL_DOCUMENTNO + " = ? AND " + COL_DOCBRANCH + " = ? AND " + COL_DOCSEQ + " = ? ",
                new String[]{manuInspect.getMpDocCode(), manuInspect.getMpDocument(), manuInspect.getMpDocumentNo(), manuInspect.getMpDocBranch(), manuInspect.getMpDocSeq() });
        database.close();
    }

    public ArrayList<ManuInspectModel> getAllManuInspect() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_MANUINSPECT, null, null, null, null, null, null);
        ArrayList<ManuInspectModel> manuInspects = new ArrayList<ManuInspectModel>();
        ManuInspectModel manuInspectModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                manuInspectModel = new ManuInspectModel();
                manuInspectModel.setMpDocCode(cursor.getString(0));
                manuInspectModel.setMpDocument(cursor.getString(1));
                manuInspectModel.setMpDocumentNo(cursor.getString(2));
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
        database.close();
        return manuInspects;
    }

    public void insertManuInspectImage(ManuInspectImageModel manuInspectImage){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
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
        database.close();
    }

    public void updateManuInspectImage(ManuInspectImageModel manuInspectImage) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
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
                new String[]{manuInspectImage.getMpgDoccode(), manuInspectImage.getMpgDocument(), manuInspectImage.getMpgDocumentno(), manuInspectImage.getMpgDocBranch(), manuInspectImage.getMpgDocSeq()});
        database.close();
    }

    public void deleteManuInspectImage(ManuInspectImageModel manuInspectImage) {
        database = this.getReadableDatabase();
        database.delete(TABLE_MANUINSPECTIMAGE,
                COL_MPGDOCCODE + " = ? AND " + COL_MPGDOCUMENT + " = ? AND " + COL_MPGDOCUMENTNO + " = ? AND " + COL_MPGDOCBRANCH + " = ? AND " + COL_MPGDOCSEQ + " = ? ",
                new String[]{manuInspectImage.getMpgDoccode(), manuInspectImage.getMpgDocument(), manuInspectImage.getMpgDocumentno(), manuInspectImage.getMpgDocBranch(), manuInspectImage.getMpgDocSeq()});
        database.close();
    }

    public ArrayList<ManuInspectImageModel> getAllManuInspectImage() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_MANUINSPECTIMAGE, null, null, null, null, null, null);
        ArrayList<ManuInspectImageModel> manuInspectImages = new ArrayList<ManuInspectImageModel>();
        ManuInspectImageModel manuInspectImageModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                manuInspectImageModel = new ManuInspectImageModel();
                manuInspectImageModel.setMpgDoccode(cursor.getString(0));
                manuInspectImageModel.setMpgDocument(cursor.getString(1));
                manuInspectImageModel.setMpgDocumentno(cursor.getString(2));
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
        database.close();
        return manuInspectImages;
    }
}
