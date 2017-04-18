package com.example.legen.readnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class WelcomeActivity extends AppCompatActivity {
    ImageButton login, skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        login = (ImageButton) findViewById(R.id.wc_login_btn);
        skip = (ImageButton) findViewById(R.id.wc_skip_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent_login);
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                Intent intent_login = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent_login);
                finish();
            }
        });
    }
    private void showProgress() {


    }
}