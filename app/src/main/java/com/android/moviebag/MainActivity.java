package com.android.moviebag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.moviebag.fragments.DashboardFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, DashboardFragment.class, null)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}