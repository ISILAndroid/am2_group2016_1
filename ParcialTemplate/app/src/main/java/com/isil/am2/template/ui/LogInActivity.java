package com.isil.am2.template.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.isil.am2.template.R;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        app();
    }

    private void app() {

        findViewById(R.id.btnLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });
    }

    private void gotoMain() {

        Intent intent=new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
