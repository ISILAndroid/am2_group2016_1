package com.isil.mynotes.presenter;

import com.isil.mynotes.storage.entity.EditNoteRaw;
import com.isil.mynotes.storage.entity.NoteResponse;
import com.isil.mynotes.storage.request.ApiClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Alumno-J on 15/06/2016.
 */
public class DeleteNotePresenter {

    private static final String TAG = "DeleteNotePresenter";
    private DeleteNoteView deleteNoteView;
    private String objectId;

    public   void attachedView(DeleteNoteView deleteNoteView){

        this.deleteNoteView = deleteNoteView;
    }

    public  void detachView(){
        this.deleteNoteView=null;
    }

    public void deleteNote(String objectId ) {
        this.deleteNoteView.showLoading();
        ApiClient.getMyApiClient().deleteNote(objectId, new Callback<NoteResponse>() {
            @Override
            public void success(NoteResponse noteResponse, Response response) {
                deleteNoteSuccess(noteResponse, response);
            }

            @Override
            public void failure(RetrofitError error) {
                deleteNoteError(error.toString());
            }
        });
    }

    private void deleteNoteSuccess(NoteResponse noteResponse, Response response) {
        this.deleteNoteView.hideLoading();
        this.deleteNoteView.deleteNoteSuccess();
    }
    private void deleteNoteError(String messageError){
        this.deleteNoteView.hideLoading();
        this.deleteNoteView.onMessageError(messageError);
    }
}
