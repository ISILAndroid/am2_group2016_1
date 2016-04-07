package com.isil.fragments.model;

import java.io.Serializable;

/**
 * Created by em on 30/03/16.
 */
public class BookEntity implements Serializable {
    private int id;
    private String name;
    private int category;

    private boolean downloaded;
    private boolean archived;
    private int periodicity;

    public BookEntity(int id, String name, int category, boolean downloaded, boolean archived, int periodicity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.downloaded = downloaded;
        this.archived = archived;
        this.periodicity = periodicity;
    }

    public BookEntity() {
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }
}
