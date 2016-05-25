package com.isil.am2.template.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.isil.am2.template.model.FacultyEntity;

import java.util.List;

/**
 * Created by em on 11/05/16.
 */
public class FacultyAdapter extends ArrayAdapter<FacultyEntity> {

    private Context context;
    private List<FacultyEntity> facultyEntities;

    public FacultyAdapter(Context context, int resource, List<FacultyEntity> objects) {
        super(context, resource, objects);

        this.context= context;
        this.facultyEntities= objects;
    }
}
