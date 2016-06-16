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
