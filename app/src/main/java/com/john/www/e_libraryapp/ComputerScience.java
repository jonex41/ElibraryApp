package com.john.www.e_libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.john.www.e_libraryapp.R;
import com.john.www.e_libraryapp.SelectionsAcftivity.Html;
import com.john.www.e_libraryapp.SelectionsAcftivity.Java;

public class ComputerScience extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout java, html;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computerscience);

        java = (LinearLayout) findViewById(R.id.java);
        html = (LinearLayout) findViewById(R.id.html);

        java.setOnClickListener(this);
        html.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.java:
                Intent java = new Intent(getApplicationContext(), Java.class);
                java.putExtra("math", "java");
                startActivity(java);
                break;
            case R.id.html:
                Intent mata = new Intent(getApplicationContext(), Html.class);
                mata.putExtra("math", "html");
                startActivity(mata);
                break;
        }
    }
}
