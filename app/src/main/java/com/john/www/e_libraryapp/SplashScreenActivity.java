package com.john.www.e_libraryapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.john.www.e_libraryapp.Rgistration.RegisterActivity;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

          Thread thread = new Thread(){
            @Override
            public void run() {

                try{
                    sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {

startActivity(new Intent(SplashScreenActivity.this, RegisterActivity.class));
finish();


                }
            }
        };
        thread.start();
    }
}
