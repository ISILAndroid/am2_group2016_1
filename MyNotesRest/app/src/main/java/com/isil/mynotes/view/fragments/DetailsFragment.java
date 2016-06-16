package com.isil.mynotes.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.isil.mynotes.R;
import com.isil.mynotes.model.entity.NoteEntity;
import com.isil.mynotes.presenter.EditNotePresenter;
import com.isil.mynotes.presenter.EditNoteView;
import com.isil.mynotes.presenter.NotePresenter;
import com.isil.mynotes.view.listeners.OnNoteListener;

public class DetailsFragment extends Fragment implements EditNoteView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button btnDeleteNote;
    private Button btnEditNote;
    private EditText eteName;
    private EditText eteDesc;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnNoteListener mListener;
    private NoteEntity noteEntity;
    private EditNotePresenter editNotePresenter;

    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnNoteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
        btnDeleteNote=(Button)getView().findViewById(R.id.btnDeleteNote);
        btnEditNote=(Button)getView().findViewById(R.id.btnEditNote);

        eteName= (EditText)getView().findViewById(R.id.eteName);
        eteDesc= (EditText)getView().findViewById(R.id.eteDesc);


        editNotePresenter= new EditNotePresenter();
        editNotePresenter.attachedView(this);

        if(getArguments()!=null)
        {
            noteEntity= (NoteEntity)getArguments().getSerializable("NOTE");
        }
        if(noteEntity!=null)
        {
            //TODO mostrar INFO
            String name= noteEntity.getName();
            String desc= noteEntity.getDescription();

            eteName.setText(name);
            eteDesc.setText(desc);
        }

        btnDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.deleteNote(noteEntity);
            }
        });

        btnEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editNote();
                editNoteCloud();
            }
        });
    }

    private void editNoteCloud() {
        String objectId= noteEntity.getObjectId();
        String name= eteName.getText().toString().trim();
        String desc= eteDesc.getText().toString().trim();
        editNotePresenter.editNote(objectId,name,desc);
    }

    private void editNote()
    {
        //TODO validar campos
        //extraer lo valores
        String name= eteName.getText().toString().trim();
        String desc= eteDesc.getText().toString().trim();
        NoteEntity nNoteEntity=new NoteEntity();
        nNoteEntity.setName(name);
        nNoteEntity.setDescription(desc);
        nNoteEntity.setId(noteEntity.getId());

        //llamar el método de crudoperation
        mListener.getCrudOperations().updateNote(nNoteEntity);

        //cerrar la pantalla
        getActivity().finish();
    }

    @Override
    public void showLoading() {
        mListener.showParentLoading();
    }

    @Override
    public void hideLoading() {
        mListener.hideParentLoading();
    }

    @Override
    public void onMessageError(String message) {
        mListener.showMessage(message);
    }

    @Override
    public void editNoteSuccess() {
        getActivity().finish();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
