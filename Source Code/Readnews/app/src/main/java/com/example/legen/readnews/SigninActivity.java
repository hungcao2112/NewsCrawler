package com.example.legen.readnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();
    }
}
