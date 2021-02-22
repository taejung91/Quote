package com.example.quote;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtendFragment extends Fragment {
    String quote;
    String writer;
    int font;
    int image;
    TextView textView;
    ImageView imageView;
    Typeface typeface;
    ImageButton button;
    ConstraintLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle result = getArguments();
        if(result != null){

            quote = result.getString("ex_Quote");
            writer = result.getString("ex_Writer");
            font = result.getInt("ex_Font");
            image = result.getInt("ex_Image");
        }

        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_extend, container, false);

        layout = viewGroup.findViewById(R.id.layout);

        textView = viewGroup.findViewById(R.id.textView);
        textView.setText(quote + "\n" + "\n" + "- " + writer);
        fontChange(font);

        imageView = viewGroup.findViewById(R.id.imageView);
        imageView.setImageResource(image);

        viewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        button = viewGroup.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date time = new Date();
                String cur_time = sdf.format(time);
                saveImage(layout, cur_time + "_capture");
            }
        });

        return viewGroup;
    }

    public void fontChange(int font) {
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

    public void saveImage(View view, String title){

        button.setVisibility(View.INVISIBLE);

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
        button.setVisibility(View.VISIBLE);

        // 미디어 스캔
        MainActivity mainActivity = (MainActivity)getActivity();

        MediaScanner ms = MediaScanner.newInstance(mainActivity.getApplicationContext());
        try {
            ms.mediaScanning(Str_Path + title + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}