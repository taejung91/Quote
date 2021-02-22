package com.example.quote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

public class SettingFragment extends Fragment {

    MainActivity mainActivity;
    public final String PREFERENCE = "com.example.quote";
    final String FONT = "font";
    TextView textView;
    Button button3;
    Button logout;
    Long k_id;

    LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        logout = viewGroup.findViewById(R.id.logout);
        button3 = viewGroup.findViewById(R.id.button3);
        textView = viewGroup.findViewById(R.id.text1);

        layout = viewGroup.findViewById(R.id.layout);

        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        k_id = bundle.getLong("id");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                Toast.makeText(getContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontChange();

            }
        });
/*
        Button button4 = viewGroup.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeReset();
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.TIMER = 0;
                Toast.makeText(getContext(), "시간이 초기화되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
*/
        return viewGroup;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    public void fontChange() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.show();
        RadioGroup radioGroup = dialog.findViewById(R.id.group);

        int f_check = mainActivity.getFontPreferenceInt(FONT);

        if (f_check == 0) {
            f_check = R.id.font3;
        }
        radioGroup.check(f_check);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getId() == R.id.group) {
                    switch (checkedId) {
                        case R.id.font1:
                          //  Toast.makeText(getContext(), "check1", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.font2:
                          //  Toast.makeText(getContext(), "check2", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.font3:
                           // Toast.makeText(getContext(), "check3", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });

        Button button1 = dialog.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int font = radioGroup.getCheckedRadioButtonId();
                switch (font) {
                    case R.id.font1:
                        mainActivity.setFontPreference(FONT, R.id.font1);
                        Toast.makeText(getContext(), "글꼴 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                    case R.id.font2:
                        mainActivity.setFontPreference(FONT, R.id.font2);
                        Toast.makeText(getContext(), "글꼴 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                    case R.id.font3:
                        mainActivity.setFontPreference(FONT, R.id.font3);
                        Toast.makeText(getContext(), "글꼴 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                }

            }
        });

        Button button2 = dialog.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    /*
    public void fontChange2() {
        Typeface typeface;
        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        int font = pref.getInt(FONT, 0);
        switch (font) {
            case R.id.font1:
                typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/font1.ttf");
                //   textView.setTypeface(typeface);
                //  textView.setTextSize(23);
                //  button3.setTypeface(typeface);
                //  logout.setTypeface(typeface);

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

    }*/
    /*

    public void timeChange() {


        TimePickerDialog.OnTimeSetListener mTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(getContext(), "hourOfDay : " + hourOfDay + " / minute : " + minute, Toast.LENGTH_SHORT).show();
                    }
                };

        Calendar _cal = Calendar.getInstance();
        int _hour = _cal.get(Calendar.HOUR_OF_DAY);
        int _minute = -_cal.get(Calendar.MINUTE);
        TimePickerDialog _alert = new TimePickerDialog(getContext(), mTimeSetListener, _hour, _minute, false);

        _alert.show();
    }
*/
 /*   public void timeReset() {

        try {
            String result = new Mysql_Db3().execute(String.valueOf(k_id), String.valueOf(0), "time").get();
            // Toast.makeText(getContext(), "타임업뎃" + result, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}










