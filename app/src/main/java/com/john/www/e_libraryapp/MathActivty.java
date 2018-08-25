package com.john.www.e_libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.john.www.e_libraryapp.SelectionsAcftivity.Calculus;
import com.john.www.e_libraryapp.SelectionsAcftivity.Differentiation;

public class MathActivty extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout calculus, diffrential;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mathlayout);

        calculus = (LinearLayout) findViewById(R.id.calculus);
        diffrential = (LinearLayout) findViewById(R.id.differentiation);

        calculus.setOnClickListener(this);
        diffrential.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.calculus:
                Intent java = new Intent(getApplicationContext(), Calculus.class);
                java.putExtra("math", "calculus");
                startActivity(java);
                break;
            case R.id.differentiation:
                Intent mata = new Intent(getApplicationContext(), Differentiation.class);
                mata.putExtra("math", "differentiation");
                startActivity(mata);
                break;
        }
    }
}
