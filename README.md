# am2_group2016_1
Curso de Aplicaciones Móviles II - Grupo 2016-I

# Conexión Remota

Editar y Eliminar elementos 

Agregamos a nuestra clase "ApiClient las acciones de editar y eliminar"

```
package com.isil.mynotes.storage.request;

import com.isil.mynotes.storage.entity.EditNoteRaw;
import com.isil.mynotes.storage.entity.LogInRaw;
import com.isil.mynotes.storage.entity.LogInResponse;
import com.isil.mynotes.storage.entity.NoteRaw;
import com.isil.mynotes.storage.entity.NoteResponse;
import com.isil.mynotes.storage.entity.NotesResponse;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by em on 8/06/16.
 */
public class ApiClient {

    private static final String TAG = "ApiClient";
    private static final String PATH="http://api.backendless.com";

    private static ServicesApiInterface servicesApiInterface;

    public static ServicesApiInterface getMyApiClient() {

        if (servicesApiInterface == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(PATH)
                    .setClient(new OkClient(getClient()))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            servicesApiInterface = restAdapter.create(ServicesApiInterface.class);
        }
        return servicesApiInterface;
    }

    public interface ServicesApiInterface {

        ///<version name>/users/login
        //@Headers({"Content-Type: application/json"})


        @Headers({
                "Content-Type: application/json",
                "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
                "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
                "application-type: REST"
        })
        //v1/users/login
        @POST("/v1/users/login")
        void login(@Body LogInRaw raw, Callback<LogInResponse> callback);


        @Headers({
                "Content-Type: application/json",
                "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
                "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
                "application-type: REST"
        })
        //v1/data/Notes
        @GET("/v1/data/Notes")
        void notes( Callback<NotesResponse> callback);


        @Headers({
                "Content-Type: application/json",
                "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
                "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
                "application-type: REST"
        })
        @POST("/v1/data/Notes")
        void addNote(@Body NoteRaw raw, Callback<NoteResponse> callback);

        //Editar notas
        /*
        {
             "objectId" : "28325E9F-2DED-D3CA-FFC6-C76911AFBB00"
             "name" : "James Bond",
             "age" : 33,
             "phoneNumber" : "+44123456789",
            }
         */
        @Headers({
                "Content-Type: application/json",
                "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
                "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
                "application-type: REST"
        })
        @PUT("/v1/data/Notes")
        void editNote(@Body EditNoteRaw raw, Callback<NoteResponse> callback);

        @Headers({
                "Content-Type: application/json",
                "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
                "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
                "application-type: REST"
        })
        //Eliminar Notas
        @DELETE("/v1/data/Notes/{id}")
        void deleteNote(@Path("object-id") int objectId, Callback<NoteResponse> callback);

    }

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(2, TimeUnit.MINUTES);
        client.setReadTimeout(2, TimeUnit.MINUTES);
        return client;
    }
}
```
1. Editar :

EditNotePresenter
```
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

```
Implementación en el fragment "DetailsFragment"

```
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

```

2. Eliminar :


5 .  Referencias
Librerías que vamos a usar, Retrofit, OkHttp y GSON

- http://square.github.io/retrofit/
- http://square.github.io/okhttp/
- https://github.com/google/gson

