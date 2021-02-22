package com.example.quote;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    ImageView imageView;
    ConstraintLayout layout;
    TextView time;
    CountDownTimer countDownTimer;
    MainActivity mainActivity;
    int res;
    static final int ran[] = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5,
            R.drawable.bg6, R.drawable.bg7, R.drawable.bg8, R.drawable.bg9, R.drawable.bg10,
            R.drawable.bg11, R.drawable.bg12, R.drawable.bg13, R.drawable.bg14};
    public int TIMER;
    public int curTime1;
    public int curTime2;

    public final String PREFERENCE = "com.example.quote";
    public final String QUOTE = "quote";
    public final String QUOTE2 = "quote2";

    Bundle bundle;
    Long k_id;

    String quote = "";
    String writer;
    String subject;
    String num;
    int image;

    TextView textView;
    final String FONT = "font";

    Quote quoteVO;
    Mysql_Db2 mysql_db2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        imageView = viewGroup.findViewById(R.id.imageView);
        time = viewGroup.findViewById(R.id.time);
        layout = viewGroup.findViewById(R.id.layout);
        textView = viewGroup.findViewById(R.id.textView);
        mainActivity = new MainActivity();

        bundle = getArguments();
        k_id = bundle.getLong("id");
        quoteVO = new Quote();

        db_Select();
        fontChange();

        if (quote.equals("null")) {

            layout.setEnabled(true);

            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_UP) {
                        Random random = new Random();
                        int index = random.nextInt(ran.length);
                        res = ran[index];
                        imageView.setImageResource(res);
                        time.setVisibility(View.VISIBLE);
                        layout.setEnabled(false);

                        quote();
                        db_Insert_Update();
                        /*  SQLite*/
        /*                if (quote != null) {
                            updateQuote();

                            Log.d("테스트", "첫번째");
                        } else {
                            quote();
                            insertQuote();

                            Log.d("테스트", "두번째");
                        }*/

                        //updateTime();
                        countDown();
                        // 시간0
                        //   setPreferenceClear();
                        //   setPreferenceClear2();
                        db_Select();
                        quote = quoteVO.getQuote();
                        // quote = queryQuote();
                        //   timer();
                    }

                    //                 db_Insert_Update();
/*
                    try {
                        String result = new Mysql_Db().execute(num, k_id.toString(), subject, quote, writer, String.valueOf(res)).get();
                        Toast.makeText(getContext(), "디비연결성공" + result, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                    return true;
                }
            });

        } else if(!quote.equals("null") ){

            quote = quoteVO.getQuote();
            writer = quoteVO.getWriter();

            image = quoteVO.getImage();

            textView.setText(quote + "\n" + "-" + writer);
            imageView.setImageResource(image);
            layout.setEnabled(false);
            countDown();

            if (countDownTimer == null) {
                layout.setEnabled(true);

            }

            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_UP) {
                        Random random = new Random();
                        int index = random.nextInt(ran.length);
                        res = ran[index];
                        imageView.setImageResource(res);
                        time.setVisibility(View.VISIBLE);
                        layout.setEnabled(false);

                        quote();
                        //insertQuote();

                        db_Insert_Update();
                        //  updateQuote();
                        //  updateTime();
                        countDown();

                    }
                    // 시간 0으로없뎃

                    //  setPreferenceClear();
                    //  setPreferenceClear2();
                    //   timer();

                    return true;
                }
            });

        }

        return viewGroup;
    }

    public int timer() {
        Quote quote = new Quote();
        //   quote.setTime(getPreferenceInt(QUOTE));

        db_Select();
        quoteVO.getTime();
        //quote.setTime(queryTime());

        if (quoteVO.getTime() == 0) {

            //없뎃구문 현재시간
            db_Insert_Update2();  //now
            //updateTime2();
            //curTime();
            TIMER = 30000;

            // TIMER = 86400;
        } else {

            // 30초 기준
            curTime1 = quoteVO.getTime();

            // quote.setTime2(getFontPreferenceInt(QUOTE2));

           // curTime2 = curTime2();

            curTime2 = Integer.parseInt(curTime_Db());

            // 여기서 시간차함수
            // 값 여부로

            if ( curTime2 <= 30) {

                TIMER = (30 - curTime2) * 1000;
                // TIMER = 30000;
            } else if (curTime2 >= 30) {
                TIMER = 0;
            } else {
                TIMER = 30000;
                //TIMER = 86400;
                // 30000
            }
        }

        return TIMER;
    }

    public void countDown() {

        TIMER = timer();
        if (countDownTimer == null && quote == null) {

            countDownTimer = new CountDownTimer(TIMER, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long diffSec = millisUntilFinished / 1000;
                    time.setText(diffSec + " 초 남았습니다.");
                }

                @Override
                public void onFinish() {

                    time.setText("화면을 눌러주세요");
                    layout.setEnabled(true);
                    TIMER = 0;
                    countDownTimer = null;

                }
            };
            countDownTimer.start();

        } else if (countDownTimer == null) {

            countDownTimer = new CountDownTimer(TIMER, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    long diffSec = millisUntilFinished / 1000;
                    time.setText(diffSec + " 초 남았습니다.");
                }

                @Override
                public void onFinish() {

                    time.setText("화면을 터치해주세요");
                    layout.setEnabled(true);
                    TIMER = 0;
                    countDownTimer = null;

                }
            };
            countDownTimer.start();

        } else {

        }

    }

/*
    public void quote() {
        try {
            InputStream is = getActivity().getAssets().open("quotes.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            String quotes = jsonObject.getString("명언들");
            JSONArray jsonArray = new JSONArray(quotes);

            Random random = new Random();
            index = random.nextInt(117);


                JSONObject subJsonObject = jsonArray.getJSONObject(index);

                quote = subJsonObject.getString("명언");
                writer = subJsonObject.getString("작가");
                subject = subJsonObject.getString("주제");
                num = subJsonObject.getString("번호");

                textView.setText(quote + "\n" + "\n" + "- " + writer);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public void fontChange() {
        Typeface typeface;
        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        int font = pref.getInt(FONT, 0);
        switch (font) {
            case R.id.font1:
                typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/font1.ttf");
                textView.setTypeface(typeface);
                textView.setTextSize(23);
                break;
            case R.id.font2:
                typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/font2.ttf");
                textView.setTypeface(typeface);
                break;
            case R.id.font3:
                typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/font3.ttf");
                textView.setTypeface(typeface);
                break;
        }

    }

    //-----------------------------------------------//
    //--------------------- MySQL -------------------//
    //-----------------------------------------------//

    public void quote() {

        String TAG_JSON = "one_Quote";
        String TAG_QUOTE = "quote";
        String TAG_WRITER = "writer";
        String TAG_SUBJECT = "subject";
        String TAG_NUM = "number";
        try {
            String result = new Mysql_Db8().execute("random").get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                quote = item.getString(TAG_QUOTE);
                writer = item.getString(TAG_WRITER);
                subject = item.getString(TAG_SUBJECT);
                num = item.getString(TAG_NUM);
                textView.setText(quote + "\n" + "\n" + "- " + writer);

            }

        } catch (Exception e) {

        }
    }

    public void db_Insert_Update() {

        try {
            String result = new Mysql_Db().execute(num, k_id.toString(), subject, quote, writer, String.valueOf(res), String.valueOf(0), "all").get();
            //    Toast.makeText(getContext(), "디비인설트업뎃" + result, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void db_Insert_Update2() {

        try {
            String result = new Mysql_Db3().execute(k_id.toString(), String.valueOf(curTime()), "time").get();
            // Toast.makeText(getContext(), "타임업뎃" + result, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void db_Select() {
        String TAG_JSON = "one_Quote";
        String TAG_QUOTE = "quote";
        String TAG_WRITER = "writer";
        String TAG_IMAGE = "image";
        String TAG_TIME = "time";
        try {
            String result = new Mysql_Db2().execute(k_id.toString()).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                quote = item.getString(TAG_QUOTE);
                String writer = item.getString(TAG_WRITER);
                String image = item.getString(TAG_IMAGE);
                String time = item.getString(TAG_TIME);


                quoteVO.setQuote(quote);
                quoteVO.setWriter(writer);
                quoteVO.setImage(Integer.parseInt(image));
                quoteVO.setTime(Integer.parseInt(time));

                //   Log.d("확인", quote + writer + image);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String curTime_Db() {
        String result = null;
        try {
            result = new Mysql_Db9().execute(k_id.toString()).get();
            // Toast.makeText(getContext(), "타임업뎃" + result, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int curTime() {

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);


        Calendar baseCal = new GregorianCalendar(year, month, day, c_hour, c_min, c_sec);
        //  Calendar baseCal = new GregorianCalendar(c_hour, c_min, c_sec);
        Calendar targetCal = new GregorianCalendar(year, month, day, c_hour + 24, 0, 0);

        //    int hourTime = (int) Math.floor((double) (diffSec / 3600));
        //   int minTime = (int) Math.floor((double) (((diffSec - (3600 * hourTime)) / 60)));
        //  int secTime = (int) Math.floor((double) (((diffSec - (3600 * hourTime)) - (60 * minTime))));

        long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
        //   long time = System.currentTimeMillis() / 1000;

        int time = Long.valueOf(diffSec).intValue();

        return time;

        //  setPreference(QUOTE,  Long.valueOf(diffSec).intValue());

    }


/*
    public String getTime() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);

        Calendar baseCal = new GregorianCalendar(year, month, day, c_hour, c_min, c_sec);

        // 오후 9시 기준
        Calendar targetCal = new GregorianCalendar(year, month, day, c_hour, c_min, c_sec);

        long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24 * 60 * 60);

        targetCal.add(Calendar.DAY_OF_MONTH, (int) (-diffDays));

        int hourTime = (int) Math.floor((double) (diffSec / 3600));
        int minTime = (int) Math.floor((double) (((diffSec - (3600 * hourTime)) / 60)));
        int secTime = (int) Math.floor((double) (((diffSec - (3600 * hourTime)) - (60 * minTime))));


        String hour = String.format("%02d", hourTime);
        String min = String.format("%02d", minTime);
        String sec = String.format("%02d", secTime);


        return hour + " 시간 " + min + " 분 " + sec + " 초 남았습니다.";

    }*/



/*
    public int curTime2() {

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);


        Calendar baseCal = new GregorianCalendar(year, month, day, c_hour, c_min, c_sec);
        //  Calendar baseCal = new GregorianCalendar(c_hour, c_min, c_sec);
        //Calendar targetCal = new GregorianCalendar(year, month, day + 1, c_hour, 0, 0);
        Calendar targetCal = new GregorianCalendar(year, month, day, c_hour + 24, 0, 0);
        long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
        //long time = baseCal.getTimeInMillis() / 1000;

        // curTime2 = Long.valueOf(baseCal.getTimeInMillis()).intValue();
        long time = System.currentTimeMillis();

        //setPreference(QUOTE2,  Integer.parseInt(str));
        //  setPreference(QUOTE2,  Long.valueOf(diffSec).intValue());

        //  long diffSec = (baseCal.getTimeInMillis() - getPreferenceInt(QUOTE));
        curTime2 = Long.valueOf(diffSec).intValue();

        return curTime2;
    }*/
/*
    public void insertQuote() {
        String uriString = "content://com.example.quote/quote";

        Uri uri = new Uri.Builder().build().parse(uriString);

        ContentValues values = new ContentValues();

        values.put("number", num);
        values.put("id", k_id);
        values.put("subject", subject);
        values.put("quote", quote);
        values.put("writer", writer);
        values.put("image", res);

        values.put("time", curTime());
        getContext().getContentResolver().insert(uri, values);

        // quote2.setQuote(quote);
        //  quote2.setWriter(writer);

    }*/
/*
    public void updateQuote() {
        String uriString = "content://com.example.quote/quote";
        Uri uri = new Uri.Builder().build().parse(uriString);
        // where 조건
        String selection = "id = ?";
        // where 조건 값
        String[] selectionArgs = new String[]{k_id.toString()};
        ContentValues updateValue = new ContentValues();
        updateValue.put("number", num);
        updateValue.put("subject", subject);
        updateValue.put("quote", quote);
        updateValue.put("writer", writer);
        updateValue.put("image", res);

        getContext().getContentResolver().update(uri, updateValue, selection, selectionArgs);

    }

    public void updateTime() {
        String uriString = "content://com.example.quote/quote";
        Uri uri = new Uri.Builder().build().parse(uriString);
        // where 조건
        String selection = "id = ?";
        // where 조건 값
        String[] selectionArgs = new String[]{k_id.toString()};
        ContentValues updateValue = new ContentValues();
        updateValue.put("time", 0);

        getContext().getContentResolver().update(uri, updateValue, selection, selectionArgs);

    }

    public void updateTime2() {
        String uriString = "content://com.example.quote/quote";
        Uri uri = new Uri.Builder().build().parse(uriString);
        // where 조건
        String selection = "id = ?";
        // where 조건 값
        String[] selectionArgs = new String[]{k_id.toString()};
        ContentValues updateValue = new ContentValues();

        updateValue.put("time", curTime());

        getContext().getContentResolver().update(uri, updateValue, selection, selectionArgs);

    }*/

/*
    public String queryQuote() {

        try {
            String uriString = "content://com.example.quote/quote";
            Uri uri = new Uri.Builder().build().parse(uriString);
            String[] columns = new String[]{"quote", "writer", "image"};
            String selection = "id = ?";
            String[] selectionArgs = new String[]{k_id.toString()};


            Cursor cursor = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);

            int index = 0;
            while (cursor.moveToNext()) {
                quote = cursor.getString(cursor.getColumnIndex(columns[0]));
                writer = cursor.getString(cursor.getColumnIndex(columns[1]));
                image = cursor.getString(cursor.getColumnIndex(columns[2]));


                index += 1;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quote;
    }
*/
   /* public int queryTime() {

        int time = 0;
        try {
            String uriString = "content://com.example.quote/quote";
            Uri uri = new Uri.Builder().build().parse(uriString);
            String[] columns = new String[]{"time"};
            String selection = "id = ?";
            String[] selectionArgs = new String[]{k_id.toString()};

            Cursor cursor = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);

            if (cursor.moveToNext()) {
                time = cursor.getInt(cursor.getColumnIndex(columns[0]));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }
*/
}




