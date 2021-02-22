package com.example.quote;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class CustomerProvider extends ContentProvider {

    // 외부 접근시 권한 파악
    private static final String AUTHORITY = "com.example.quote";
    private static final String BASE_PATH = "customer";
    private static final String BASE_PATH2 = "quote";
    private static final String BASE_PATH3 = "c_quote";
    private static final String BASE_PATH4 = "time";




    // 외부 접근시 주소 uri
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final Uri CONTENT_URI2 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH2);
    public static final Uri CONTENT_URI3 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH3);


    private static final int CUSTOMERS = 1;
    private static final int PERSON_ID = 2;
    private static final int QUOTE = 3;
    private static final int C_QUOTE = 4;


    // uri 접근 체크
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, CUSTOMERS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", PERSON_ID);
        uriMatcher.addURI(AUTHORITY, BASE_PATH2, QUOTE);
        uriMatcher.addURI(AUTHORITY, BASE_PATH3, C_QUOTE);

    }

    private SQLiteDatabase database;


    public CustomerProvider() {
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        // Implement this to handle requests to delete one or more rows.

        int count = 0;
        // 접근에 대한 사용여부 판단
        switch (uriMatcher.match(uri)) {
            case CUSTOMERS:
                count = database.delete(DatabaseHelper.TABLE_NAME, s, strings);
                break;
            case QUOTE:
                count = database.delete(DatabaseHelper.TABLE_NAME2, s, strings);
                break;
            case C_QUOTE:
                count = database.delete(DatabaseHelper.TABLE_NAME3, s, strings);
                break;

            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        // 알림 설정                                              // 사용x null
        getContext().getContentResolver().notifyChange(uri, null);
//        throw new UnsupportedOperationException("Not yet implemented");
        return count;
    }


    // 제공하는 데이터를 반환
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.

        switch (uriMatcher.match(uri)) {
            case CUSTOMERS:
                return "vnd.android.cursor.dir/persons";
            case QUOTE:
                return "vnd.android.cursor.dir/quote";
            case C_QUOTE:
                return "vnd.android.cursor.dir/c_quote";
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    // 외부 데이터 등록
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        switch (uriMatcher.match(uri)) {
            case CUSTOMERS:
                long id = database.insert(DatabaseHelper.TABLE_NAME, null, values);
                if (id > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
            case QUOTE:
                long num = database.insert(DatabaseHelper.TABLE_NAME2, null, values);
                if (num > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI2, num);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
            case C_QUOTE:
                long c_num = database.insert(DatabaseHelper.TABLE_NAME3, null, values);
                if (c_num > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI3, c_num);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;

        }

        throw new SQLException("추가 실패 -> URI : " + uri);


//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.

        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s,
                        String[] strings1, String s1) {
        // TODO: Implement this to handle query requests from clients.

        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CUSTOMERS:
                cursor = database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.ALL_COLUMNS,
                        s, null, null, null, DatabaseHelper.CUSTOMER_ID + " ASC");
                break;
            case QUOTE:
                cursor = database.query(DatabaseHelper.TABLE_NAME2, DatabaseHelper.ALL_COLUMNS2,
                        s, strings1, null, null, null);
                break;
            case C_QUOTE:
                cursor = database.query(DatabaseHelper.TABLE_NAME3, DatabaseHelper.ALL_COLUMNS3,
                        s, strings1, null, null, null);
                break;

            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

        //       throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String s,
                      String[] strings) {
        // TODO: Implement this to handle requests to update one or more rows.

        int count = 0;
        switch (uriMatcher.match(uri)) {
            case CUSTOMERS:
                count = database.update(DatabaseHelper.TABLE_NAME, contentValues, s, strings);
                break;
            case QUOTE:
                count = database.update(DatabaseHelper.TABLE_NAME2, contentValues, s, strings);
                break;
            case C_QUOTE:
                count = database.update(DatabaseHelper.TABLE_NAME3, contentValues, s, strings);
                break;

            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}