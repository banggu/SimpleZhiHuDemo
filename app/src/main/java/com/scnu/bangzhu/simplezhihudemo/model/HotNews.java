package com.scnu.bangzhu.simplezhihudemo.model;

/**
 * Created by bangzhu on 2016/8/23.
 */
public class HotNews {
    private int news_id;
    private String url;
    private String thumbnail;
    private String title;

    public HotNews() {
    }

    public HotNews(int news_id, String url, String thumbnail, String title) {
        this.news_id = news_id;
        this.url = url;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
