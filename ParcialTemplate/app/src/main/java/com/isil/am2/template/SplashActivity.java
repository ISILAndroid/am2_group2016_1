package com.isil.am2.template;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.isil.am2.template.R;
import com.isil.am2.template.ui.LogInActivity;
import com.isil.am2.template.ui.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        app();
    }

    private void app() {

        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                Intent intent;
                boolean session= false;
                if(session)
                {
                    intent=new Intent(SplashActivity.this, MainActivity.class);
                }else {
                    intent = new Intent(SplashActivity.this, LogInActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
