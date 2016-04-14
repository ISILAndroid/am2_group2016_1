package com.isil.am2.fragmentsamples;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.isil.am2.fragmentsamples.model.ContactEntity;
import com.isil.am2.fragmentsamples.view.ContactListener;
import com.isil.am2.fragmentsamples.view.OnFragmentListener;
import com.isil.am2.fragmentsamples.view.fragments.ContactsFragment;
import com.isil.am2.fragmentsamples.view.fragments.DetailContactFragment;


public class HomeActivity extends ActionBarActivity implements ContactListener {

    private static final String TAG = "HomeActivity";

    private ContactsFragment fragmentContacts;
    private DetailContactFragment fragmentDetail;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        fragmentManager= getSupportFragmentManager();
        fragmentContacts= (ContactsFragment)fragmentManager.
                findFragmentById(R.id.fragmentContacts);
        fragmentDetail= (DetailContactFragment)fragmentManager.
                findFragmentById(R.id.fragmentDetail);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void recibirContactoyEnviarDetalle(ContactEntity contactEntity) {
        Log.v("CONSOLE","Paso 2, recibir contacto y enviarlo ");
        fragmentDetail.mostrarContacto(contactEntity);
    }
}
