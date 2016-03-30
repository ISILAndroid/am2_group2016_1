package com.isil.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.isil.fragments.view.OnMessageListener;
import com.isil.fragments.view.fragments.AFragment;
import com.isil.fragments.view.fragments.BFragment;
import com.isil.fragments.view.fragments.OneFragment;
import com.isil.fragments.view.fragments.TwoFragment;


public class MessageActivity extends ActionBarActivity implements OnMessageListener{

    private static final String TAG = "MessageActivity";
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        app();
    }

    private void app() {

        fragmentManager= getSupportFragmentManager();
        oneFragment= (OneFragment)fragmentManager.findFragmentById(R.id.fragmentOne);
        twoFragment= (TwoFragment)fragmentManager.findFragmentById(R.id.fragmentTWo);
    }

    public void recibiryEnviarMensaje(String message)
    {
        Log.v(TAG, "2 recibiryEnviarMensaje " + message);
        twoFragment.mostrarMensaje(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
