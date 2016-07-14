package com.isil.am2.template.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.isil.am2.template.R;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FacultyEntity facultyEntity = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_faculty, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tviName);

        if(facultyEntity!=null)
        {
            String name= facultyEntity.getName()!=null?( facultyEntity.getName()):("");
            tvName.setText(name);
        }

        return convertView;
    }
}
