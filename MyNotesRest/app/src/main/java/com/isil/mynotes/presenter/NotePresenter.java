package com.isil.mynotes.presenter;

import android.util.Log;

import com.isil.mynotes.model.entity.NoteEntity;
import com.isil.mynotes.model.entity.UserEntity;
import com.isil.mynotes.storage.entity.LogInRaw;
import com.isil.mynotes.storage.entity.LogInResponse;
import com.isil.mynotes.storage.entity.NoteRaw;
import com.isil.mynotes.storage.entity.NoteResponse;
import com.isil.mynotes.storage.request.ApiClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by em on 8/06/16.
 */
public class NotePresenter {

    private static final String TAG = "NotePresenter";
    private AddNoteView addNoteView;
    private String name,description;

    public   void attachedView(AddNoteView addNoteView){
        this.addNoteView = addNoteView;
    }

    public  void detachView(){
        this.addNoteView=null;
    }

    public void addNote(String name, String desc ){
        NoteRaw noteRaw= new NoteRaw();
        noteRaw.setName(name);
        noteRaw.setDescription(desc);

        ApiClient.getMyApiClient().addNote(noteRaw, new Callback<NoteResponse>() {
            @Override
            public void success(NoteResponse noteResponse, Response response) {
                addNoteSuccess(noteResponse,response);
            }

            @Override
            public void failure(RetrofitError error) {
                addNoteError(error.toString());
            }
        });
    }
    public void addNoteSuccess(NoteResponse noteResponse, Response response){

        if(noteResponse.isSuccess()){
            NoteEntity noteEntity= new NoteEntity();
            noteEntity.setObjectId(noteResponse.getObjectId());
            noteEntity.setName(noteResponse.getName());
            noteEntity.setDescription(noteResponse.getDescription());
        }
        addNoteView.hideLoading();
        addNoteView.onAddNoteSuccess();
    }

    public void addNoteError(String messageError){
        addNoteView.hideLoading();
        addNoteView.onMessageError(messageError);
    }
}
