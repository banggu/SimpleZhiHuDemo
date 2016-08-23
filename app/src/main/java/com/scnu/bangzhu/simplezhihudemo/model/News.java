package com.scnu.bangzhu.simplezhihudemo.model;

import org.json.JSONArray;

/**
 * Created by bangzhu on 2016/8/21.
 */
public class News {
    private int id;
    private int type;
    private String images;
    private String ga_prefix;
    private String title;

    public News() {
    }

    public News(int id, int type, String images, String ga_prefix, String title) {
        this.id = id;
        this.type = type;
        this.images = images;
        this.ga_prefix = ga_prefix;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
