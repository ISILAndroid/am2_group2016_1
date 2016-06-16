package com.isil.mynotes.presenter;

import com.isil.mynotes.storage.entity.EditNoteRaw;
import com.isil.mynotes.storage.entity.NoteRaw;
import com.isil.mynotes.storage.entity.NoteResponse;
import com.isil.mynotes.storage.request.ApiClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Alumno-J on 15/06/2016.
 */
public class EditNotePresenter {

    private static final String TAG = "EditNotePresenter";
    private EditNoteView editNoteView;
    private String objectId, name,description;

    public   void attachedView(EditNoteView editNoteView){

        this.editNoteView = editNoteView;
    }

    public  void detachView(){
        this.editNoteView=null;
    }

    public void editNote(String objectId,String name, String desc ) {
        EditNoteRaw editNoteRaw = new EditNoteRaw();
        editNoteRaw.setObjectId(objectId);
        editNoteRaw.setName(name);
        editNoteRaw.setDescription(desc);

        editNoteView.showLoading();
        ApiClient.getMyApiClient().editNote(editNoteRaw, new Callback<NoteResponse>() {
            @Override
            public void success(NoteResponse noteResponse, Response response) {
                editNoteSuccess(noteResponse, response);
            }

            @Override
            public void failure(RetrofitError error) {
                editNoteError(error.toString());
            }
        });
    }

    private void editNoteSuccess(NoteResponse noteResponse, Response response) {
        editNoteView.hideLoading();
        editNoteView.editNoteSuccess();
    }

    private void editNoteError(String messageError) {
        editNoteView.hideLoading();
        editNoteView.onMessageError(messageError);
    }
}
