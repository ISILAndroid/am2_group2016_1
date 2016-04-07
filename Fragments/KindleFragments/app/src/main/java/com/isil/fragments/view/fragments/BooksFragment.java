package com.isil.fragments.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.isil.fragments.R;
import com.isil.fragments.adapters.BookAdapter;
import com.isil.fragments.model.BookEntity;
import com.isil.fragments.view.KindleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {} interface
 * to handle interaction events.
 * Use the {@link BooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private KindleListener mListener;
    private GridView gviewBooks;
    private List<BookEntity> books;

    public BooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BooksFragment newInstance(String param1, String param2) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof KindleListener) {
            mListener = (KindleListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gviewBooks= (GridView)getView().findViewById(R.id.gviewBooks);
        books= new ArrayList<>();
        books.add(new BookEntity(1,"Matemática 1",1,true,false,1));
        books.add(new BookEntity(2, "Física Aplicada",2,false,true,0));
        books.add(new BookEntity(3, "Química Básica",2,false,false,2));
        books.add(new BookEntity(4, "Física 1",2,true,false,3));
        books.add(new BookEntity(5, "Física 2",2,true,false,2));
        books.add(new BookEntity(6, "Algoritmos",3,false,false,5));
        books.add(new BookEntity(7, "Computación",3,true,true,10));


        final BookAdapter bookAdapter= new BookAdapter(books,getActivity());
        gviewBooks.setAdapter(bookAdapter);

        gviewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BookEntity bookEntity= (BookEntity)adapterView.getAdapter().getItem(position);
                selectedBook(bookEntity);
            }
        });
    }

    private void selectedBook(BookEntity bookEntity) {
        if(mListener!=null)
        {
            mListener.gotoBookDetail(bookEntity);
        }
    }

    public void filtrar(int position)
    {
        switch (position)
        {
            case 0:
                    //allBooks();
                break;
            case 1:
                    filterDownloaded();
                break;
            case 2:
                    //filterArchived();
                break;
            case 3:
                    //filterPeriodicals();
                break;

        }
    }
    //TODO 1. filtrar datos, mostrar datos
    private void filterDownloaded()
    {
        List<BookEntity> booksDownloaded= new ArrayList<>();

        /*for (int i = 0; i <; i++) {
            BookEntity mbBookEntity= books.get(i);
        }*/
        for (BookEntity bookEntity:books) {
            if(bookEntity.isDownloaded())
            {
                booksDownloaded.add(bookEntity);
            }
        }
        Log.v("CONSOLE ", "total " + booksDownloaded.size());
        populateFilter(booksDownloaded);
    }
    //TODO pintar datos
    private void populateFilter(List<BookEntity> bookEntities)
    {
        BookAdapter bookAdapter= new BookAdapter(bookEntities,
                getActivity());
        gviewBooks.setAdapter(bookAdapter);
    }




}
