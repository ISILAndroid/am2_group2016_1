package com.isil.mynotes.presenter;

import com.isil.mynotes.model.entity.NoteEntity;
import com.isil.mynotes.storage.entity.NotesResponse;
import com.isil.mynotes.storage.request.ApiClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by em on 8/06/16.
 */
public class NotesPresenter {

    private static final String TAG = "NotesPresenter";
    private NotesView notesView;

    public   void attachedView(NotesView notesView){
        this.notesView = notesView;
    }

    public  void detachView(){
        this.notesView=null;
    }

    public void loadNotes(){
        notesView.showLoading();

        ApiClient.getMyApiClient().notes(new Callback<NotesResponse>() {
            @Override
            public void success(NotesResponse notesResponse, Response response) {
                    notesSuccess(notesResponse,response);
            }

            @Override
            public void failure(RetrofitError error) {
                notesError("");
            }
        });
    }

    private void notesSuccess(NotesResponse notesResponse, Response response) {
        notesView.hideLoading();

        if(notesResponse!=null){
            List<NoteEntity> notes= notesResponse.getData();
            notesView.renderNotes(notes);
        }

    }
    private void notesError(String messageError){
        notesView.hideLoading();
        notesView.onMessageError(messageError);
    }
}
