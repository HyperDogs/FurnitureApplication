package com.example.ton.furnitureapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Model.TBUserLoginModel;

/**
 * Created by marisalom on 25/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "SQLiteDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERLOGIN = "TBUserLogin";

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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
                + ")";
        db.execSQL(CREATE_USERLOGIN_TABLE);
        db.execSQL("INSERT INTO " + TABLE_USERLOGIN + " (" + COL_USERLOGINID + ", " + COL_NAME + ", " + COL_PASS + " ) VALUES ('1', 'admin', 'admin');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_USERLOGIN);
        onCreate(db);
    }

    public void insertRecord(TBUserLoginModel userLogin){
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

    public void insertRecordAlternate(TBUserLoginModel userLogin) {
        database = this.getReadableDatabase();
        database.execSQL("INSERT INTO " + TABLE_USERLOGIN + "(" + COL_USERLOGINID + ","
                + COL_NAME + ","
                + COL_PASS + ","
                + COL_DESC + ","
                + COL_GROUPID + ","
                + COL_STATUS + ","
                + COL_EMPLOYEEID + ","
                + COL_BRANCHID + ","
                + COL_SETPERMISSION + ","
                + COL_REMOTEADDR
                + ") VALUES('"
                + userLogin.getUlUserLoginId() + "','"
                + userLogin.getUlName() + "','"
                + userLogin.getUlPass() + "','"
                + userLogin.getUlDesc() + "','"
                + userLogin.getUlGroupId() + "','"
                + userLogin.getUlStatus() + "','"
                + userLogin.getUlEmployeeId() + "','"
                + userLogin.getUlBranchId() + "','"
                + userLogin.getUlSetPermission() + "','"
                + userLogin.getUlRemoteAddr() + "')");
        database.close();
    }

    public void updateRecord(TBUserLoginModel userLogin) {
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

    public void updateRecordAlternate(TBUserLoginModel userLogin) {
        database = this.getReadableDatabase();
        database.execSQL("update " + TABLE_USERLOGIN + " set "
                + COL_NAME + " = '" + userLogin.getUlName() + "', "
                + COL_PASS + " = '" + userLogin.getUlPass() + "', "
                + COL_DESC + " = '" + userLogin.getUlDesc() + "', "
                + COL_GROUPID + " = '" + userLogin.getUlGroupId() + "', "
                + COL_STATUS + " = '" + userLogin.getUlStatus() + "', "
                + COL_EMPLOYEEID + " = '" + userLogin.getUlEmployeeId() + "', "
                + COL_BRANCHID + " = '" + userLogin.getUlBranchId() + "', "
                + COL_SETPERMISSION + " = '" + userLogin.getUlSetPermission() + "', "
                + COL_REMOTEADDR + " = '" + userLogin.getUlRemoteAddr()
                + "' where "
                + COL_USERLOGINID + " = '" + userLogin.getUlUserLoginId() + "'");
        database.close();
    }

    public void deleteRecord(TBUserLoginModel userLogin) {
        database = this.getReadableDatabase();
        database.delete(TABLE_USERLOGIN, COL_USERLOGINID + " = ?", new String[]{userLogin.getUlUserLoginId()});
        database.close();
    }

    public void deleteRecordAlternate(TBUserLoginModel userLogin) {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + TABLE_USERLOGIN + " where " + COL_USERLOGINID + " = '" + userLogin.getUlUserLoginId() + "'");
        database.close();
    }

    public ArrayList<TBUserLoginModel> getAllRecords() {
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
}
