package com.isil.am2.template.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.isil.am2.template.R;
import com.isil.am2.template.storage.PreferencesHelper;

public class LogInActivity extends AppCompatActivity {

    private EditText eteUserName,etePassword;
    private Button btnLogIn;

    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        app();
    }

    private void app() {

        eteUserName= (EditText)findViewById(R.id.eteUserName) ;
        etePassword= (EditText)findViewById(R.id.etePassword) ;

        findViewById(R.id.btnLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForn())
                {
                    savePreferences();
                    gotoMain();
                }
            }
        });
    }

    private void savePreferences() {
        PreferencesHelper.saveSession(this, username,password);
    }

    private boolean validateForn() {
        username= eteUserName.getText().toString().trim();
        password= etePassword.getText().toString().trim();

        eteUserName.setError(null);
        etePassword.setError(null);

        if(username.isEmpty()){
            eteUserName.setText("Campo inválido");
            return false;
        }
        if(password.isEmpty())
        {
            etePassword.setText("Campo inválido");
            return false;
        }
        return true;
    }

    private void gotoMain() {

        Intent intent=new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
