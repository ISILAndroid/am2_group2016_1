package com.isil.mynotes.storage.entity;

/**
 * Created by em on 8/06/16.
 *
 * {
 "created": 1465426355000,
 "name": "nota desde android",
 "___class": "Notes",
 "description": "esta es una nota de prueba",
 "ownerId": null,
 "updated": null,
 "objectId": "F9E7E58F-4409-08CB-FFCB-64D5BB741100",
 "__meta": "{\"relationRemovalIds\":{},\"selectedProperties\":[\"created\",\"___saved\",\"name\",\"___class\",\"description\",\"ownerId\",\"updated\",\"objectId\"],\"relatedObjects\":{}}"
 }
 */
public class NoteResponse {

    private static final int SUCCESS=0;
    private int code;

    private String name;
    private String description;
    private String objectId;


    public boolean isSuccess()
    {
        if(this.code==SUCCESS)return true;
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
