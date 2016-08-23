package com.scnu.bangzhu.simplezhihudemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.scnu.bangzhu.simplezhihudemo.R;
import com.scnu.bangzhu.simplezhihudemo.model.HotNews;
import com.scnu.bangzhu.simplezhihudemo.util.BitmapCache;

import java.util.List;

/**
 * Created by bangzhu on 2016/8/23.
 */
public class HotNewsListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HotNews> mHotNewsList;
    private RequestQueue mQueue;

    public HotNewsListAdapter(Context context, List<HotNews> list){
        mContext = context;
        mHotNewsList = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public int getCount() {
        return mHotNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHotNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_list_item, parent, false);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.tv_news_title);
            viewHolder.newsImage = (ImageView) convertView.findViewById(R.id.iv_news_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HotNews hotNews = mHotNewsList.get(position);
        viewHolder.newsTitle.setText(hotNews.getTitle());
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.newsImage, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(hotNews.getThumbnail(), imageListener);
        return convertView;
    }

    public final class ViewHolder{
        public TextView newsTitle;
        public ImageView newsImage;
    }
}
