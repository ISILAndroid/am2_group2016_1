package com.isil.am2.template.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.isil.am2.template.R;
import com.isil.am2.template.model.FacultyEntity;
import com.isil.am2.template.storage.CRUDOperations;
import com.isil.am2.template.storage.MyDatabase;
import com.isil.am2.template.storage.PreferencesHelper;
import com.isil.am2.template.ui.fragments.AllItemsFragment;
import com.isil.am2.template.ui.fragments.ByCategoryFragment;
import com.isil.am2.template.ui.fragments.FacultiesFragment;
import com.isil.am2.template.ui.fragments.NotesFragment;
import com.isil.am2.template.ui.fragments.ProfileFragment;
import com.isil.am2.template.ui.fragments.SettingsFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnMainListener{


    private static final String TAG = "MainActivity";
    private View flayContainer;
    private View mnuAllItems,mnuByCategory,mnuSettings,mnuLogout;
    private EditText eteSearch;
    private View iviSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app();

        //Ejecutar un sola vez
        //populate();
    }

    /*private void populate() {
        CRUDOperations crudOperations= new CRUDOperations(new MyDatabase(this));
        crudOperations.addFaculty(new FacultyEntity("Odontología DB",0));
        crudOperations.addFaculty(new FacultyEntity("Medicina Humana DB", 0));
        crudOperations.addFaculty(new FacultyEntity("Derecho DB", 0));
        crudOperations.addFaculty(new FacultyEntity("Ingeniería y Arquitectura DB", 0));
        crudOperations.addFaculty(new FacultyEntity("Estadísticas DB", 0));
        crudOperations.addFaculty(new FacultyEntity("Ingeniería Química DB", 0));
    }*/

    private void app() {
        mnuAllItems= findViewById(R.id.mnuAllItems);
        mnuByCategory= findViewById(R.id.mnuByCategory);
        mnuSettings= findViewById(R.id.mnuSettings);
        mnuLogout= findViewById(R.id.mnuLogout);
        eteSearch= (EditText)findViewById(R.id.eteSearch);
        iviSearch= (ImageView)findViewById(R.id.iviSearch);

        mnuAllItems.setOnClickListener(this);
        mnuByCategory.setOnClickListener(this);
        mnuSettings.setOnClickListener(this);
        mnuLogout.setOnClickListener(this);

        //events
        changeFragment(new Bundle(), AllItemsFragment.newInstance(null, null));

        eteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = s.toString();
                Log.v(TAG, "value " + s);
                filterBooks(value);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        iviSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value =eteSearch.getText().toString().trim();
                filterBooks(value);
            }
        });

    }

    private void filterBooks(String value) {
        Fragment fragment= getVisibleFragment();
        Log.v(TAG, "fragment "+fragment);
        if(fragment!=null && fragment instanceof AllItemsFragment){
            ((AllItemsFragment)fragment).filter(value);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.mnuAllItems:
                changeFragment(new Bundle(), AllItemsFragment.newInstance(null, null));
                break;

            case R.id.mnuByCategory:
                changeFragment(new Bundle(), ByCategoryFragment.newInstance(null, null));
                break;

            case R.id.mnuSettings:
                changeFragment(new Bundle(), SettingsFragment.newInstance(null, null));
                break;

            case R.id.mnuLogout:
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

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }


}
