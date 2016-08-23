package com.scnu.bangzhu.simplezhihudemo.util;

/**
 * Created by bangzhu on 2016/8/21.
 */
public class UrlUtil {
    private static final String  BASE_URL = "http://news.at.zhihu.com/api/4/news/";
    private static final String LATEST_NEWS = "latest";
    private static final String HOT_NEWS = "http://news-at.zhihu.com/api/3/news/hot";

    public static String getLatestNews(){
        return BASE_URL + LATEST_NEWS;
    }

    public static String getHotNews(){
        return HOT_NEWS;
    }
}
