package com.isil.am2.template.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.isil.am2.template.R;
import com.isil.am2.template.storage.PreferencesHelper;
import com.isil.am2.template.ui.fragments.FacultiesFragment;
import com.isil.am2.template.ui.fragments.NotesFragment;
import com.isil.am2.template.ui.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnMainListener{


    private View flayContainer;
    private View btnProfile,btnNotes,btnFaculties,btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app();
    }

    private void app() {
        btnProfile= findViewById(R.id.btnProfile);
        btnNotes= findViewById(R.id.btnNotes);
        btnFaculties= findViewById(R.id.btnFaculties);
        btnLogOut= findViewById(R.id.btnLogOut);

        btnProfile.setOnClickListener(this);
        btnNotes.setOnClickListener(this);
        btnFaculties.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnProfile:
                changeFragment(new Bundle(), ProfileFragment.newInstance(null,null));
                break;

            case R.id.btnNotes:
                changeFragment(new Bundle(), NotesFragment.newInstance(null,null));
                break;

            case R.id.btnFaculties:
                changeFragment(new Bundle(), FacultiesFragment.newInstance(null,null));
                break;

            case R.id.btnLogOut:
                logout();
                break;

        }
    }

    private void logout() {
        //LIMPIAR SESION
        PreferencesHelper.clearSession(this);
        startActivity(new Intent(this,LogInActivity.class));
        finish();
    }

    private  void changeFragment(Bundle bundle,Fragment fragment)
    {
        if(fragment!=null)
        {
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flayContainer, fragment)
                    .commit();
        }
    }


}
