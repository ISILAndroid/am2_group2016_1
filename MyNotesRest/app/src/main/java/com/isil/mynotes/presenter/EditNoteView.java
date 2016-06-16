package com.isil.mynotes.presenter;

import android.content.Context;

import com.isil.mynotes.model.entity.NoteEntity;

import java.util.List;

/**
 * Created by Alumno-J on 15/06/2016.
 */
public interface EditNoteView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void editNoteSuccess();
}
