package com.isil.am2.template.presenter;

import android.util.Log;

import com.isil.am2.template.model.UserEntity;
import com.isil.am2.template.storage.entity.LogInRaw;
import com.isil.am2.template.storage.entity.LogInResponse;
import com.isil.am2.template.storage.request.ApiClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by em on 8/06/16.
 */
public class LogInPresenter {

    private static final String TAG = "LogInPresenter";
    private LogInView logInView;
    private String email;
    private String password;

    public   void attachedView(LogInView logInView){
        this.logInView = logInView;
    }

    public  void detachView(){
        this.logInView=null;
    }

    public void logIn(String email,String password) {
        this.email = email;
        this.password = password;
        LogInRaw logInRaw= new LogInRaw();
        logInRaw.setLogin(this.email);
        logInRaw.setPassword(this.password);

        logInView.showLoading();

        ApiClient.getMyApiClient().login(logInRaw, new Callback<LogInResponse>() {
            @Override
            public void success(LogInResponse loginResponse, Response response) {
                loginSuccess(loginResponse,response);
            }

            @Override
            public void failure(RetrofitError error)
            {
                String json="Error ";
                try {
                    json= new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                }catch (NullPointerException e) {}
                Log.v(TAG, "json >>>> " + json);

                loginError(json);

            }
        });

    }
    public void loginSuccess(LogInResponse loginResponse, Response response){
        if(loginResponse!=null){
            UserEntity userEntity= new UserEntity();
            userEntity.setEmail(loginResponse.getEmail());
            userEntity.setName(loginResponse.getName());
            userEntity.setObjectId(loginResponse.getObjectId());
            userEntity.setToken(loginResponse.getToken());
        }
        logInView.hideLoading();
        logInView.gotoMain();
    }
    public void loginError(String messageError){
        logInView.hideLoading();
        logInView.onMessageError(messageError);
    }
}
