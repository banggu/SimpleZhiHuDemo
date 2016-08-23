package com.scnu.bangzhu.simplezhihudemo.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.scnu.bangzhu.simplezhihudemo.R;
import com.scnu.bangzhu.simplezhihudemo.model.News;
import com.scnu.bangzhu.simplezhihudemo.util.BitmapCache;

import java.util.List;

/**
 * Created by bangzhu on 2016/8/21.
 */
public class ViewPagerAdapter extends PagerAdapter{
    private List<News> mNewsList;
    private Context mContext;
    private RequestQueue mQueue;

    public ViewPagerAdapter(Context context, List<News> viewList){
        mContext = context;
        mNewsList = viewList;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        News news = mNewsList.get(position);
        ImageView imageView = new ImageView(mContext);
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(news.getImages(), imageListener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((ViewPager)container).addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
