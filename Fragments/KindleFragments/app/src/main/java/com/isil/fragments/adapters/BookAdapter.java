package com.isil.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isil.fragments.R;
import com.isil.fragments.model.BookEntity;

import java.util.List;

/**
 * Created by em on 30/03/16.
 */
public class BookAdapter extends BaseAdapter{

    private Context context;
    private final List<BookEntity> books;

    public BookAdapter(List<BookEntity> books, Context context) {
        this.books = books;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.books.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (convertView == null) {
            view= inflater.inflate(R.layout.row_books,null);
        }else
        {
            view =  convertView;
        }
        TextView tviName= (TextView)view.findViewById(R.id.tviName);
        BookEntity bookEntity=books.get(position);
        if(bookEntity!=null)
        {
            tviName.setText(bookEntity.getName());
        }
        return view;
    }
}
