package com.john.www.e_libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String spinnerdicision, regno;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         spinnerdicision = getIntent().getStringExtra("spinnerdecision");

         regno = getIntent().getStringExtra("regno");
      //  Toast.makeText(this, regno, Toast.LENGTH_LONG).show();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("spinner", spinnerdicision).apply();
 PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("regno", regno).apply();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), PostBookActivity.class));
            }
        });
        viewPager = (ViewPager) findViewById(R.id.content);
        setUpViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

            switch (spinnerdicision) {

                case "student":
                    fab.setVisibility(View.GONE);
                    break;
                case "admin":
                    fab.setVisibility(View.VISIBLE);
                    break;


            }


    }

    private void setUpViewPager(ViewPager viewPager) {



        switch (spinnerdicision){

            case "student":
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                adapter.addFragment(new BooksFrafmment(), "Books");
                adapter.addFragment(new FauoritesFragment(), "Downloads");
                viewPager.setAdapter(adapter);
                break;
            case "admin":
                ViewPagerAdapter adapter2 = new ViewPagerAdapter(getSupportFragmentManager());
                adapter2.addFragment(new BooksFrafmment(), "Books");
                adapter2.addFragment(new BorowerFragment(), "Review");

                viewPager.setAdapter(adapter2);
                break;


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
