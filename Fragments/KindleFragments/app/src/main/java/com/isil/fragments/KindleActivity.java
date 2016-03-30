package com.isil.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.isil.fragments.view.KindleListener;
import com.isil.fragments.view.fragments.BooksFragment;
import com.isil.fragments.view.fragments.FilterFragment;

public class KindleActivity extends AppCompatActivity implements KindleListener{

    private static final String TAG = "KindleActivity";
    private FilterFragment fragmentFilter;
    private BooksFragment booksFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindle);
        app();
    }

    private void app() {

        fragmentManager= getSupportFragmentManager();
        fragmentFilter= (FilterFragment)fragmentManager.findFragmentById(R.id.fragmentFilter);
        booksFragment= (BooksFragment)fragmentManager.findFragmentById(R.id.fragmentBooks);
    }

    @Override
    public void filterSelected(int position, Object object) {
        Log.v(TAG, "2. filterSelected " + position + " obj " + object);
    }
}
