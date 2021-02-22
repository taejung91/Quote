package com.example.quote;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;


import static android.content.Context.MODE_PRIVATE;


public class FavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    QuoteAdapter2 adapter2;
    long k_id;
    EditText editText;
    final String FONT = "font";
    public final String PREFERENCE = "com.example.quote";
    int font;
    ExtendFragment extendFragment;
    QuoteAddFragment addFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = viewGroup.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getArguments();
        k_id = bundle.getLong("id");

        // 폰트
        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        font = pref.getInt(FONT, 0);

        // 즐찾 리스트
        favorite();
        click();

        editText = viewGroup.findViewById(R.id.editText);

        // 명언 추가
        Button edit = viewGroup.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment = new QuoteAddFragment();
                MainActivity mainActivity = (MainActivity) getActivity();

                Bundle bundle = new Bundle();
                bundle.putLong("id", k_id);
                addFragment.setArguments(bundle);

                mainActivity.replaceFragment(addFragment);

            }
        });

        // 검색
        Button search = viewGroup.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = editText.getText().toString();
                favorite3(subject);
                click();
            }
        });

        // 전체
        Button button1 = viewGroup.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorite();
                click();
            }
        });

        // MY
        Button button10 = viewGroup.findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "My";
                favorite2(subject);
                click();

            }
        });

        // 용기
        Button button2 = viewGroup.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "용기";
                favorite2(subject);
                click();
            }
        });
        // 자존감
        Button button3 = viewGroup.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "자존감";
                favorite2(subject);
                click();
            }
        });
        // 친구
        Button button4 = viewGroup.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "친구";
                favorite2(subject);
                click();
            }
        });
        // 존중&이해
        Button button5 = viewGroup.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "존중&이해";
                favorite2(subject);
                click();
            }
        });
        // 사랑
        Button button6 = viewGroup.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "사랑";
                favorite2(subject);
                click();
            }
        });
        // 노력
        Button button7 = viewGroup.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "노력";
                favorite2(subject);
                click();
            }
        });
        // 도전
        Button button8 = viewGroup.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "도전";
                favorite2(subject);
                click();
            }
        });
        // 희망&가족
        Button button9 = viewGroup.findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "희망&가족";
                favorite2(subject);
                click();
            }
        });

        return viewGroup;
    }

    // 즐찾 삭제
    public void click() {
        adapter2.setOnQuoteItemClickListener(new OnQuoteItemClickListener2() {
            @Override
            public void onItemClick(QuoteAdapter2.ViewHolder viewHolder, View view, int position) {
                Quote_Db item;
                switch (view.getId()) {
                    case R.id.imageView:
                        extendFragment = new ExtendFragment();
                        item = adapter2.getItem(position);
                        item.getQuote();
                        item.getWriter();
                        item.getImage();
                        item.getFont();

                        Bundle result = new Bundle();
                        result.putString("ex_Quote", item.getQuote());
                        result.putString("ex_Writer", item.getWriter());
                        result.putInt("ex_Image", Integer.parseInt(item.getImage()));
                        result.putInt("ex_Font", item.getFont());

                        extendFragment.setArguments(result);

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.replaceFragment(extendFragment);
                        break;

                    case R.id.button:
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date time = new Date();
                        String cur_time = sdf.format(time);
                        saveImage(viewHolder.layout, cur_time + "_capture");
                        break;
                    case R.id.button1:

                        item = adapter2.getItem(position);

                        delete_C_Quote(item);
                        Toast.makeText(getContext(), "즐겨찾기가 취소되었습니다.", Toast.LENGTH_SHORT).show();

                        adapter2.getItems().remove(position);
                        adapter2.notifyItemRemoved(position);
                        adapter2.notifyItemRangeChanged(position, adapter2.getItemCount());
                        break;
                   /* case R.id.button2:

                        kakaoLink();

                        Toast.makeText(getContext(), "dd", Toast.LENGTH_SHORT).show();
                        break;*/
                }


            }

        });

    }

    //-----------------------------------------------//
    //--------------------- MySQL -------------------//
    //-----------------------------------------------//

    public void favorite() {
        adapter2 = new QuoteAdapter2();
        ArrayList<Quote_Db> list = new ArrayList<Quote_Db>();
        String result = "";
        try {
            result = new Mysql_Db6().execute(String.valueOf(k_id)).get();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String number = jsonObject.getString("number");
                String id = jsonObject.getString("id");
                String subject = jsonObject.getString("subject");
                String quote = jsonObject.getString("quote");
                String writer = jsonObject.getString("writer");
                String image = jsonObject.getString("image");
                String c_number = jsonObject.getString("c_number");

                Quote_Db quote_db = new Quote_Db();
                quote_db.setNumber(number);
                quote_db.setId(id);
                quote_db.setSubject(subject);
                quote_db.setQuote(quote);
                quote_db.setWriter(writer);
                quote_db.setImage(image);
                quote_db.setC_number(c_number);
                quote_db.setFont(font);

                list.add(quote_db);

            }

        } catch (Exception e) {

        }
        adapter2.setItems(list);
        recyclerView.setAdapter(adapter2);
    }

    public void favorite2(String subject) {
        adapter2 = new QuoteAdapter2();
        ArrayList<Quote_Db> list = new ArrayList<Quote_Db>();
        String result = "";
        try {
            result = new Mysql_Db7().execute(String.valueOf(k_id), subject).get();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String number = jsonObject.getString("number");
                String id = jsonObject.getString("id");
                String subject2 = jsonObject.getString("subject");
                String quote = jsonObject.getString("quote");
                String writer = jsonObject.getString("writer");
                String image = jsonObject.getString("image");
                String c_number = jsonObject.getString("c_number");

                Quote_Db quote_db = new Quote_Db();
                quote_db.setNumber(number);
                quote_db.setId(id);
                quote_db.setSubject(subject2);
                quote_db.setQuote(quote);
                quote_db.setWriter(writer);
                quote_db.setImage(image);
                quote_db.setC_number(c_number);
                quote_db.setFont(font);

                list.add(quote_db);

            }

        } catch (Exception e) {

        }
        adapter2.setItems(list);
        recyclerView.setAdapter(adapter2);
    }

    public void favorite3(String subject) {
        adapter2 = new QuoteAdapter2();
        ArrayList<Quote_Db> list = new ArrayList<Quote_Db>();
        String result = "";
        try {
            result = new Mysql_Db6().execute(String.valueOf(k_id)).get();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String quote = jsonObject.getString("quote");
                String writer = jsonObject.getString("writer");

                if (quote.contains(subject) || writer.contains(subject)) {

                    String number = jsonObject.getString("number");
                    String id = jsonObject.getString("id");
                    String subject2 = jsonObject.getString("subject");
                    String image = jsonObject.getString("image");
                    String c_number = jsonObject.getString("c_number");

                    Quote_Db quote_db = new Quote_Db();
                    quote_db.setNumber(number);
                    quote_db.setId(id);
                    quote_db.setSubject(subject2);
                    quote_db.setQuote(quote);
                    quote_db.setWriter(writer);
                    quote_db.setImage(image);
                    quote_db.setC_number(c_number);
                    quote_db.setFont(font);

                    list.add(quote_db);
                }
            }

            if (list.size() == 0) {
                Toast.makeText(getContext(), "해당하는 내용을 찾을 수 없습니다.\n 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                adapter2.setItems(list);
            }

        } catch (Exception e) {

        }
        recyclerView.setAdapter(adapter2);
    }

    public void delete_C_Quote(Quote_Db item){

        new Mysql_Db5().execute(String.valueOf(k_id), item.getC_number(), "f_Delete");
    }

    // 명언 저장
    public void saveImage(View view, String title) {

        if (view == null) {
            return;
        }

        //캡쳐 파일 저장
        view.buildDrawingCache(); //캐시 비트 맵 만들기
        Bitmap bitmap = view.getDrawingCache();
        FileOutputStream fos;

        /* 저장할 폴더 Setting */
        File uploadFolder = Environment.getExternalStoragePublicDirectory("/DCIM/Camera/"); //저장 경로 (File Type형 변수)


        if (!uploadFolder.exists()) { //만약 경로에 폴더가 없다면
            uploadFolder.mkdir(); //폴더 생성
        }

        /* 파일 저장 */
        String Str_Path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";

        try {
            fos = new FileOutputStream(Str_Path + title + ".jpg"); // 경로 + 제목 + .jpg로 FileOutputStream Setting

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
        }

        // 미디어 스캔
        MainActivity mainActivity = (MainActivity) getActivity();

        MediaScanner ms = MediaScanner.newInstance(mainActivity.getApplicationContext());
        try {
            ms.mediaScanning(Str_Path + title + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }



    // SQLite
  /*  public void favorite() {
        adapter2 = new QuoteAdapter2();
        ArrayList<Quote_Db> list = new ArrayList<Quote_Db>();
        Quote_Db quote_db1 = null;
        try {
            String uriString = "content://com.example.quote/c_quote";
            Uri uri = new Uri.Builder().build().parse(uriString);
            String[] columns = new String[]{"number, id, subject, quote, writer, image, c_number"};
            String selection = "id = ?";
            String[] selectionArgs = new String[]{String.valueOf(k_id)};

            Cursor cursor = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);

            while (cursor.moveToNext()) {
                quote_db1 = new Quote_Db();

                quote_db1.setNumber(cursor.getString(0));
                quote_db1.setId(cursor.getString(1));
                quote_db1.setSubject(cursor.getString(2));
                quote_db1.setQuote(cursor.getString(3));
                quote_db1.setWriter(cursor.getString(4));
                quote_db1.setImage(cursor.getString(5));
                quote_db1.setC_number(cursor.getString(6));
                quote_db1.setFont(font);
                list.add(quote_db1);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter2.setItems(list);
        recyclerView.setAdapter(adapter2);

    }*/

/*
    public void favorite2(String subject) {
        adapter2 = new QuoteAdapter2();
        ArrayList<Quote_Db> list = new ArrayList<Quote_Db>();
        Quote_Db quote_db1 = null;
        try {
            String uriString = "content://com.example.quote/c_quote";
            Uri uri = new Uri.Builder().build().parse(uriString);
            String[] columns = new String[]{"number, id, subject, quote, writer, image, c_number"};
            String selection = "id = ? AND subject = ?";
            String[] selectionArgs = new String[]{String.valueOf(k_id), subject};

            Cursor cursor = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);

            while (cursor.moveToNext()) {
                quote_db1 = new Quote_Db();

                quote_db1.setNumber(cursor.getString(0));
                quote_db1.setId(cursor.getString(1));
                quote_db1.setSubject(cursor.getString(2));
                quote_db1.setQuote(cursor.getString(3));
                quote_db1.setWriter(cursor.getString(4));
                quote_db1.setImage(cursor.getString(5));
                quote_db1.setC_number(cursor.getString(6));

                quote_db1.setFont(font);
                list.add(quote_db1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter2.setItems(list);
        recyclerView.setAdapter(adapter2);
    }
*/
/*
//    public void favorite3(String subject) {
        adapter2 = new QuoteAdapter2();
        ArrayList<Quote_Db> list = new ArrayList<Quote_Db>();
        Quote_Db quote_db1 = null;
        try {
            String uriString = "content://com.example.quote/c_quote";
            Uri uri = new Uri.Builder().build().parse(uriString);
            String[] columns = new String[]{"number, id, subject, quote, writer, image, c_number"};
            String selection = "id = ?";
            //   String selection = "(quote = ? OR writer = ?) AND id = ?";

            String[] selectionArgs = new String[]{String.valueOf(k_id)};

            Cursor cursor = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);
            while (cursor.moveToNext()) {
                quote_db1 = new Quote_Db();

                String quote = cursor.getString(3);
                String writer = cursor.getString(4);

                if (quote.contains(subject) || writer.contains(subject)) {
                    quote_db1.setNumber(cursor.getString(0));
                    quote_db1.setId(cursor.getString(1));
                    quote_db1.setSubject(cursor.getString(2));
                    quote_db1.setQuote(quote);
                    quote_db1.setWriter(writer);
                    quote_db1.setImage(cursor.getString(5));
                    quote_db1.setC_number(cursor.getString(6));
                    quote_db1.setFont(font);
                    list.add(quote_db1);
                }

            }

            if (list.size() == 0) {
                Toast.makeText(getContext(), "해당하는 내용을 찾을 수 없습니다.\n 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                adapter2.setItems(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter2);

    } */


/*
    public void delete_C_Quote(Quote_Db item) {
        String uriString = "content://com.example.quote/c_quote";
        Uri uri = new Uri.Builder().build().parse(uriString);
        String selection = "id = ? AND c_number = ?";
        String[] selectionArgs = new String[]{String.valueOf(k_id), item.getC_number()};
        getContext().getContentResolver().delete(uri, selection, selectionArgs);

    }
*/


    // 카카오 링크
/*
    public void kakaoLink() {


        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("QUOTE",
                        "https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/191/791/81191791_1555664874860_1_600x600.JPG",
                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption("명언 명언")
                        .build())
                .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("https://developers.kakao.com").setMobileWebUrl("https://developers.kakao.com").build()))
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build();

        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");

        MainActivity mainActivity = (MainActivity) getActivity();

        KakaoLinkService.getInstance().sendDefault(mainActivity.getApplicationContext(), params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {

                Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {

                Toast.makeText(getContext(), "성공", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getContext(), "테스트", Toast.LENGTH_SHORT).show();

    }

    */
}

