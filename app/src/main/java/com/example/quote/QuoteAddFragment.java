package com.example.quote;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class QuoteAddFragment extends Fragment {

    ImageButton imageButton;
    ImageButton imageButton2;
    ImageButton closeButton;
    ImageView imageView;
    RecyclerView selectImage;
    RelativeLayout closeLayout;
    EditText editText1;
    EditText editText2;
    ImageAdapter adapter;
    long k_id;
    int c_font;
    int image;
    Image c_image;
    final String FONT = "font";
    public final String PREFERENCE = "com.example.quote";
    FavoriteFragment favoriteFragment;

    static final int ran[] = { R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5,
            R.drawable.bg6, R.drawable.bg7, R.drawable.bg8, R.drawable.bg9, R.drawable.bg10,
            R.drawable.bg11, R.drawable.bg12, R.drawable.bg13, R.drawable.bg14};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_quote_add, container, false);
        selectImage = viewGroup.findViewById(R.id.selectImage);
        imageView = viewGroup.findViewById(R.id.imageView);
        closeLayout = viewGroup.findViewById(R.id.closeImage);
        editText1 = viewGroup.findViewById(R.id.editText1);
        editText2 = viewGroup.findViewById(R.id.editText2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        selectImage.setLayoutManager(layoutManager);

        Bundle bundle = getArguments();
        k_id = bundle.getLong("id");

        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        c_font = pref.getInt(FONT, 0);

        Random random = new Random();
        int index = random.nextInt(ran.length);
        image = ran[index];

        imageView.setImageResource(image);


        fontChange(c_font);
        imageCycle();
        click();

        // 백스탭 클릭 금지
        viewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        // 닫기
        closeButton = viewGroup.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage.setVisibility(View.GONE);
                closeLayout.setVisibility(View.GONE);
                imageButton.setVisibility(View.VISIBLE);
                imageButton2.setVisibility(View.VISIBLE);

            }
        });

        // 열기
        imageButton = viewGroup.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage.setVisibility(View.VISIBLE);
                closeLayout.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.GONE);
                imageButton2.setVisibility(View.GONE);

            }
        });

        imageButton2 = viewGroup.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "빈 칸이 존재합니다! \n빈 칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    insert_C_Quote();
                    Toast.makeText(getContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();

                    MainActivity mainActivity = (MainActivity) getActivity();
                    favoriteFragment = new FavoriteFragment();

                    Bundle bundle = new Bundle();
                    bundle.putLong("id", k_id);
                    favoriteFragment.setArguments(bundle);

                    mainActivity.replaceFragment2(favoriteFragment);

                }

            }
        });

        return viewGroup;
    }

    public void imageCycle() {
        adapter = new ImageAdapter();
        ArrayList<Image> list = new ArrayList<>();
        Image image = null;
        for (int i = 0; i < ran.length; i++) {
            image = new Image();

            image.setImage(String.valueOf(ran[i]));
            list.add(image);
        }
        adapter.setItems(list);
        selectImage.setAdapter(adapter);
    }

    public void click() {

        adapter.setOnQuoteItemClickListener(new OnQuoteItemClickListener3() {
            @Override
            public void onItemClick(ImageAdapter.ViewHolder viewHolder, View view, int position) {
                c_image = adapter.getItem(position);
                imageView.setImageResource(Integer.parseInt(c_image.getImage()));
            }
        });

    }

    public void fontChange(int font) {
        Typeface typeface;
        switch (font) {
            case R.id.font1:
                typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/font1.ttf");
                editText1.setTypeface(typeface);
                editText2.setTypeface(typeface);
                editText1.setTextSize(23);
                editText2.setTextSize(23);
                break;
            case R.id.font2:
                typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/font2.ttf");
                editText1.setTypeface(typeface);
                editText2.setTypeface(typeface);
                break;
            case R.id.font3:
                typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/font3.ttf");
                editText1.setTypeface(typeface);
                editText2.setTypeface(typeface);
                break;
        }
    }

    public void insert_C_Quote() {
        try {
            if (c_image == null) {
                new Mysql_Db4().execute("0", String.valueOf(k_id), "My", editText1.getText().toString(), editText2.getText().toString(), String.valueOf(image), "insert");
            } else {
                new Mysql_Db4().execute("0", String.valueOf(k_id), "My", editText1.getText().toString(), editText2.getText().toString(), c_image.getImage(), "insert");
            }
            //    Toast.makeText(getContext(), "디비인설트업뎃" + result, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DB
 /*   public void insert_C_Quote() {
        String uriString = "content://com.example.quote/c_quote";

        Uri uri = new Uri.Builder().build().parse(uriString);

        ContentValues values = new ContentValues();


        values.put("number", "0");
        values.put("id", k_id);
        values.put("subject", "My");
        values.put("quote", editText1.getText().toString());
        values.put("writer", editText2.getText().toString());
        if (c_image == null) {
            values.put("image", image);
        } else {
            values.put("image", c_image.getImage());
        }

        getContext().getContentResolver().insert(uri, values);

    }*/


}