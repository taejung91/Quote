package com.example.quote;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    QuoteAdapter adapter;
    EditText editText;
    static final int ran[] = { R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5,
            R.drawable.bg6, R.drawable.bg7, R.drawable.bg8, R.drawable.bg9, R.drawable.bg10,
            R.drawable.bg11, R.drawable.bg12, R.drawable.bg13, R.drawable.bg14};
    String k_id;
    final String FONT = "font";
    public final String PREFERENCE = "com.example.quote";
    int font;
    ExtendFragment extendFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = viewGroup.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getArguments();
        k_id = String.valueOf(bundle.getLong("id"));

        // 폰트
        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        font = pref.getInt(FONT, 0);

        // 첫 명언 리스트
        quote();
        click();

        editText = viewGroup.findViewById(R.id.editText);

        // 검색
        Button search = viewGroup.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                quote3(str);
                click();

            }
        });

        // 전체
        Button button1 = viewGroup.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quote();
                click();
            }
        });
        // 용기
        Button button2 = viewGroup.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "용기";
                quote2(str);
                click();
            }
        });
        // 자존감
        Button button3 = viewGroup.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "자존감";
                quote2(str);
                click();
            }
        });
        // 친구
        Button button4 = viewGroup.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "친구";
                quote2(str);
                click();
            }
        });
        // 존중&이해
        Button button5 = viewGroup.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "존중&이해";
                quote2(str);
                click();
            }
        });
        // 사랑
        Button button6 = viewGroup.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "사랑";
                quote2(str);
                click();
            }
        });
        // 노력
        Button button7 = viewGroup.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "노력";
                quote2(str);
                click();
            }
        });
        // 도전
        Button button8 = viewGroup.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "도전";
                quote2(str);
                click();
            }
        });
        // 희망&가족
        Button button9 = viewGroup.findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "희망&가족";
                quote2(str);
                click();
            }
        });

        return viewGroup;
    }

    // 즐겨찾기 버튼
    public void click() {
        adapter.setOnQuoteItemClickListener(new OnQuoteItemClickListener() {
            @Override
            public void onItemClick(QuoteAdapter.ViewHolder viewHolder, View view, int position) {

                Quote item;
                switch (view.getId()) {
                    case R.id.imageView:
                        extendFragment = new ExtendFragment();
                        item = adapter.getItem(position);
                        item.getQuote();
                        item.getWriter();
                        item.getImage();
                        item.getFont();

                        Bundle result = new Bundle();
                        result.putString("ex_Quote", item.getQuote());
                        result.putString("ex_Writer", item.getWriter());
                        result.putInt("ex_Image", item.getImage());
                        result.putInt("ex_Font", item.getFont());

                        extendFragment.setArguments(result);

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.replaceFragment(extendFragment);
                        break;
                    case R.id.button1:
                        item = adapter.getItem(position);

                        String number2 = query_C_Quote2(item);

                        if (number2.equals(item.getNumber())) {
                            delete_C_Quote(item);
                            viewHolder.button.setImageResource(R.drawable.favorite);
                            Toast.makeText(getContext(), "즐겨찾기가 취소되었습니다.", Toast.LENGTH_SHORT).show();

                        } else {
                            //  추가
                            insert_C_Quote(item);
                            viewHolder.button.setImageResource(R.drawable.favorite2);
                            Toast.makeText(getContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }

        });

    }

    //-----------------------------------------------//
    //--------------------- MySQL -------------------//
    //-----------------------------------------------//

    public void quote() {
        adapter = new QuoteAdapter();

        try {
            String result = new Mysql_Db8().execute("all").get();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String number = jsonObject.getString("number");
                String subject = jsonObject.getString("subject");
                String quote = jsonObject.getString("quote");
                String writer = jsonObject.getString("writer");

                Random random = new Random();
                // size = ran.length + 1;
                int index = random.nextInt(ran.length);
                int image = ran[index];

                adapter.addItem(new Quote(quote, writer, number, subject, image, font, k_id));

            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
        }
        recyclerView.setAdapter(adapter);
    }

    public void quote2(String str) {
        adapter = new QuoteAdapter();
        try {
            String result = new Mysql_Db8().execute(str).get();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String number = jsonObject.getString("number");
                String subject = jsonObject.getString("subject");
                String quote = jsonObject.getString("quote");
                String writer = jsonObject.getString("writer");

                Random random = new Random();
                // size = ran.length + 1;
                int index = random.nextInt(ran.length);
                int image = ran[index];

                adapter.addItem(new Quote(quote, writer, number, subject, image, font, k_id));
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
        recyclerView.setAdapter(adapter);
    }

    public void quote3(String str) {
        adapter = new QuoteAdapter();
        try {
            String result = new Mysql_Db8().execute("all").get();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String number = jsonObject.getString("number");
                String subject = jsonObject.getString("subject");
                String quote = jsonObject.getString("quote");
                String writer = jsonObject.getString("writer");

                Random random = new Random();
                // size = ran.length + 1;
                int index = random.nextInt(ran.length);
                int image = ran[index];

                if (quote.contains(str) || writer.contains(str)) {
                    adapter.addItem(new Quote(quote, writer, number, subject, image, font, k_id));
                }
            }
            if (adapter.getItemCount() == 0) {
                Toast.makeText(getContext(), "해당하는 내용을 찾을 수 없습니다.\n 다시 입력해주세요.", Toast.LENGTH_LONG).show();
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
        recyclerView.setAdapter(adapter);
    }

    public void insert_C_Quote(Quote item) {

        try {
            new Mysql_Db4().execute(item.getNumber(), k_id.toString(), item.getSubject(), item.getQuote(), item.getWriter(), String.valueOf(item.getImage()), "insert");
            //    Toast.makeText(getContext(), "디비인설트업뎃" + result, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String query_C_Quote2(Quote item) {
        String result = "";
        try {
            result = new Mysql_Db5().execute(k_id.toString(), item.getNumber(), "select").get();
        } catch (Exception e) {
        }
        return result;
    }

    public void delete_C_Quote(Quote item){

        new Mysql_Db5().execute(k_id.toString(), item.getNumber(), "delete");
    }


    // 전체
 /*   public void quote() {
        adapter = new QuoteAdapter();
        //  SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        //   font = pref.getInt(FONT,0);

        try {
            InputStream is = getActivity().getAssets().open("quotes.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            String quotes = jsonObject.getString("명언들");
            JSONArray jsonArray = new JSONArray(quotes);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject subJsonObject = jsonArray.getJSONObject(i);

                String quote = subJsonObject.getString("명언");
                String writer = subJsonObject.getString("작가");
                String number = subJsonObject.getString("번호");
                String subject = subJsonObject.getString("주제");

                Random random = new Random();
               // size = ran.length + 1;
                int index = random.nextInt(ran.length);
                int image = ran[index];


                adapter.addItem(new Quote(quote, writer, number, subject, image, font, k_id));

            }

            adapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);


    }*/
/*
    public void quote2(String str) {
        adapter = new QuoteAdapter();

        try {
            InputStream is = getActivity().getAssets().open("quotes.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            String quotes = jsonObject.getString("명언들");
            JSONArray jsonArray = new JSONArray(quotes);
            String quote = "";
            String writer = "";

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject subJsonObject = jsonArray.getJSONObject(i);

                String subject = subJsonObject.getString("주제");

                if (subject.equals(str)) {
                    quote = subJsonObject.getString("명언");
                    writer = subJsonObject.getString("작가");
                    String number = subJsonObject.getString("번호");

                    Random random = new Random();
                //    size = ran.length + 1;
                    int index = random.nextInt(ran.length);
                    int image = ran[index];

                    adapter.addItem(new Quote(quote, writer, number, subject, image, font, k_id));
                }

            }


            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);
    } */


/*
    public void quote3(String str) {
        adapter = new QuoteAdapter();
        quote = new Quote();
        try {
            InputStream is = getActivity().getAssets().open("quotes.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            String quotes = jsonObject.getString("명언들");
            JSONArray jsonArray = new JSONArray(quotes);
            String quoteList = "";
            String writers = "";
            String number = "";
            String subject = "";

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject subJsonObject = jsonArray.getJSONObject(i);

                quoteList = subJsonObject.getString("명언");
                writers = subJsonObject.getString("작가");
                number = subJsonObject.getString("번호");
                subject = subJsonObject.getString("주제");

                Random random = new Random();
             //   int size = ran.length + 1;
                int index = random.nextInt(ran.length);
                int image = ran[index];


                if (quoteList.contains(str) || writers.contains(str)) {

                    adapter.addItem(new Quote(quoteList, writers, number, subject, image, font, k_id));
                }
            }

            if (adapter.getItemCount() == 0) {
                Toast.makeText(getContext(), "해당하는 내용을 찾을 수 없습니다.\n 다시 입력해주세요.", Toast.LENGTH_LONG).show();
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);
    }*/



    // DB
  /*  public void insert_C_Quote(Quote item) {
        String uriString = "content://com.example.quote/c_quote";

        Uri uri = new Uri.Builder().build().parse(uriString);

        ContentValues values = new ContentValues();

        values.put("number", item.getNumber());
        values.put("id", k_id);
        values.put("subject", item.getSubject());
        values.put("quote", item.getQuote());
        values.put("writer", item.getWriter());
        values.put("image", item.getImage());
        getContext().getContentResolver().insert(uri, values);
    }
*/

/*
    public ArrayList<Quote_Db> query_C_Quote() {
        quote_dbList = new ArrayList<Quote_Db>();

        try {
            String uriString = "content://com.example.quote/c_quote";
            Uri uri = new Uri.Builder().build().parse(uriString);
            String[] columns = new String[]{"number"};
            String selection = "id = ?";
            String[] selectionArgs = new String[]{String.valueOf(k_id)};

            Cursor cursor = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);

            while (cursor.moveToNext()) {
                Quote_Db quote_db = new Quote_Db();
                quote_db.setNumber(cursor.getString(cursor.getColumnIndex(columns[0])));

                quote_dbList.add(quote_db);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quote_dbList;
    }
*/

/*
    public String query_C_Quote2(Quote item) {
        String number2 = "";
        try {
            String uriString = "content://com.example.quote/c_quote";
            Uri uri = new Uri.Builder().build().parse(uriString);
            String[] columns = new String[]{"number"};
            String selection = "id = ? AND number = ?";
            String[] selectionArgs = new String[]{String.valueOf(k_id), item.getNumber()};


            Cursor cursor = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);


            while (cursor.moveToNext()) {

                number2 = cursor.getString(cursor.getColumnIndex(columns[0]));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number2;

    }*/


/*
    public void delete_C_Quote(Quote item) {
        String uriString = "content://com.example.quote/c_quote";
        Uri uri = new Uri.Builder().build().parse(uriString);
        String selection = "id = ? AND number = ?";
        String[] selectionArgs = new String[]{String.valueOf(k_id), item.getNumber()};
        getContext().getContentResolver().delete(uri, selection, selectionArgs);
    }
*/





}