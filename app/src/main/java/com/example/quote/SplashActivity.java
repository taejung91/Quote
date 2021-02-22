package com.example.quote;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new SplashThread().start();
    }

    @Override
    public void onBackPressed() {
        //뒤로가기 버튼 못누르게 함
    }

    class SplashThread extends Thread{
        public void run(){
            try{
                Thread.sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();;
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}