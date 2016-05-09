package com.imooc.festivalsms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.imooc.festivalsms.bean.SendedMsg;

/**
 * Created by Administrator on 2015/12/1.
 */
public class SmsDBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "sms.db";
    public static final int DB_VERSION = 1;


    public SmsDBOpenHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    private static SmsDBOpenHelper mHelper;

    public static SmsDBOpenHelper getInstance(Context context) {
        if (mHelper == null) {
            synchronized (SmsDBOpenHelper.class) {
                if (mHelper == null) {
                    mHelper = new SmsDBOpenHelper(context);
                }
            }
        }

        return mHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + SendedMsg.TABLE_NAME + "(" + "_id integer primary key autoincrement ,"
                + SendedMsg.COLUMN_DATE + "integer,"
                + SendedMsg.COLUMN_FES_NAME + "text,"
                + SendedMsg.COLUMN_MSG + "text,"
                + SendedMsg.COLUMN_NAMES + "text,"
                + SendedMsg.COLUMN_NUMBERS + "text"
                + ")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
