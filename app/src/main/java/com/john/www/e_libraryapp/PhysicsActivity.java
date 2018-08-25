package com.john.www.e_libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.john.www.e_libraryapp.SelectionsAcftivity.Atom;
import com.john.www.e_libraryapp.SelectionsAcftivity.Matter;

public class PhysicsActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout atom, matter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pyhsicslayout);

        atom = (LinearLayout) findViewById(R.id.atom);
        matter =(LinearLayout) findViewById(R.id.matters);

        atom.setOnClickListener(this);
        matter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.atom:
                Intent java = new Intent(getApplicationContext(), Atom.class);
                java.putExtra("math", "atoms");
                startActivity(java);
                break;
            case R.id.matters:
                Intent mata = new Intent(getApplicationContext(), Matter.class);
                mata.putExtra("math", "matter");
                startActivity(mata);
                break;
        }
    }
}
