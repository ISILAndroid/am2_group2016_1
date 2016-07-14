package com.isil.am2.template.model;

import java.io.Serializable;

/**
 * Created by em on 11/05/16.
 */
public class FacultyEntity implements Serializable {

    private int id;
    private String name;
    private int photo;


    public FacultyEntity() {
    }

    public FacultyEntity(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
