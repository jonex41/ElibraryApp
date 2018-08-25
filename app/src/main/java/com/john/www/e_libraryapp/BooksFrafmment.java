package com.john.www.e_libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BooksFrafmment extends Fragment  implements View.OnClickListener{

    private LinearLayout math, physics, java, html;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.book_fragment, container, false);



        math = v.findViewById(R.id.math);
        physics = v.findViewById(R.id.physics);
        java = v.findViewById(R.id.computersices);
        html = v.findViewById(R.id.chemistry);

        math.setOnClickListener(this);
        physics.setOnClickListener(this);
        java.setOnClickListener(this);
        html.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.math:
                Intent math = new Intent(getContext(), MathActivty.class);
                math.putExtra("math", "math");
                startActivity(math);
                break;
            case R.id.physics:
                Intent pyhsics = new Intent(getContext(), PhysicsActivity.class);
                pyhsics.putExtra("math", "math");
                startActivity(pyhsics);
                break;
            case R.id.computersices:
                Intent java = new Intent(getContext(), ComputerScience.class);
                java.putExtra("math", "math");
                startActivity(java);
                break;
            case R.id.chemistry:
                Intent html = new Intent(getContext(), Chemistry.class);
                html.putExtra("math", "math");
                startActivity(html);
                break;


        }
    }
}
