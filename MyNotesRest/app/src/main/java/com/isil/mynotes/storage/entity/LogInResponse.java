package com.isil.mynotes.storage.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by em on 8/06/16.
 */
public class LogInResponse {

    private static final int SUCCESS=0;
    private int code;
    private String message;

    private String name;

    @SerializedName("___class")
    private String type;

    @SerializedName("user-token")
    private String token;

    private String email;

    private String objectId;



    public boolean isSuccess()
    {
        if(this.code==SUCCESS)return true;
        return false;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
