package com.example.quote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "customer.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "customer";
    public static final String TABLE_NAME2 = "one_quote";
    public static final String TABLE_NAME3 = "c_quote";



   // 사용자
    public static final String CUSTOMER_ID = "id";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_PW = "pw";

    // 사용자 명언
    public static final String C_NUM = "number";
    public static final String C_SUBJECT = "subject";
    public static final String C_QUOTE = "quote";
    public static final String C_WRITER = "writer";
    public static final String C_IMAGE = "image";
    public static final String C_NUMBER = "c_number";

    public static final String[] ALL_COLUMNS3 = {C_NUM, CUSTOMER_ID, C_SUBJECT, C_QUOTE, C_WRITER, C_IMAGE, C_NUMBER};
    private static final String CREATE_TABLE3 =
            "CREATE TABLE " + TABLE_NAME3 + "("
                    + C_NUM + " text, "
                    + CUSTOMER_ID + " text, "
                    + C_SUBJECT + " text, "
                    + C_QUOTE + " text, "
                    + C_WRITER + " text, "
                    + C_IMAGE + " text, "
                    + C_NUMBER + " integer PRIMARY KEY autoincrement" + ")";




    // 1일 명언 DB
    public static final String NUM = "number";
    public static final String ONE_SUBJECT = "subject";
    public static final String ONE_QUOTE = "quote";
    public static final String ONE_WRITER = "writer";
    public static final String ONE_IMAGE = "image";
    public static final String ONE_TIME = "time";

    public static final String[] ALL_COLUMNS2 = {NUM, CUSTOMER_ID, ONE_QUOTE, ONE_SUBJECT, ONE_WRITER, ONE_IMAGE, ONE_TIME};
    private static final String CREATE_TABLE2 =
            "CREATE TABLE " + TABLE_NAME2 + "("
                    + NUM + " text, "
                    + CUSTOMER_ID + " text, "
                    + ONE_SUBJECT + " text, "
                    + ONE_QUOTE + " text, "
                    + ONE_WRITER + " text, "
                    + ONE_IMAGE + " text, "
                    + ONE_TIME + " integer "+ ")";


/*
    public static final String QUOTE = "quote";
    public static final String WRITER = "writer";
    public static final String SUBJECT = "subject";
    public static final String NUM = "number";

    public static final String[] ALL_COLUMNS2 = {QUOTE, WRITER, SUBJECT};
    private static final String CREATE_TABLE2 =
            "CREATE TABLE " + TABLE_NAME2 + "("
            + NUM + " integer PRIMARY KEY autoincrement, "
            + SUBJECT + " text, "
            + QUOTE + " text, "
            + WRITER + " text" + ")";
*/

    public static final String[] ALL_COLUMNS = {CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_PW};
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + CUSTOMER_ID + " text PRIMARY KEY, "
            + CUSTOMER_PW + " text, "
            + CUSTOMER_NAME + " text" + ")";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);

        onCreate(db);
    }
}
