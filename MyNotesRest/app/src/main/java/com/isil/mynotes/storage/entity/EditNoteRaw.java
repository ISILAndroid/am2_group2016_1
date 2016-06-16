package com.isil.mynotes.storage.entity;

/**
 * Created by Alumno-J on 15/06/2016.
 */
public class EditNoteRaw {
    private String objectId;
    private String name;
    private String description;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
