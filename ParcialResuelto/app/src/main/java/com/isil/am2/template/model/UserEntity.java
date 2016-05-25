package com.isil.am2.template.model;

import java.io.Serializable;

/**
 * Created by em on 11/05/16.
 */
public class UserEntity implements Serializable {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
