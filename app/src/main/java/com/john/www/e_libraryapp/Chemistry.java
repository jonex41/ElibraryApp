package com.john.www.e_libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.john.www.e_libraryapp.SelectionsAcftivity.Organic;
import com.john.www.e_libraryapp.SelectionsAcftivity.Physical;

public class Chemistry extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout organicchemistry, pyhsicalchemistry;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemistry);

        organicchemistry = (LinearLayout) findViewById(R.id.organicchemistry);
        pyhsicalchemistry = (LinearLayout) findViewById(R.id.physicalchemistry);

        organicchemistry.setOnClickListener(this);
        pyhsicalchemistry.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.physicalchemistry:
                Intent java = new Intent(getApplicationContext(), Physical.class);
                java.putExtra("math", "physical");
                startActivity(java);
                break;
            case R.id.organicchemistry:
                Intent mata = new Intent(getApplicationContext(), Organic.class);
                mata.putExtra("math", "organic");
                startActivity(mata);
                break;
        }
    }
}