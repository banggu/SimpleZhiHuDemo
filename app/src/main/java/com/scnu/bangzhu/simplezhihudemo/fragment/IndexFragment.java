package com.scnu.bangzhu.simplezhihudemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.scnu.bangzhu.simplezhihudemo.R;
import com.scnu.bangzhu.simplezhihudemo.adapter.HotNewsListAdapter;
import com.scnu.bangzhu.simplezhihudemo.adapter.ViewPagerAdapter;
import com.scnu.bangzhu.simplezhihudemo.model.HotNews;
import com.scnu.bangzhu.simplezhihudemo.model.News;
import com.scnu.bangzhu.simplezhihudemo.util.BitmapCache;
import com.scnu.bangzhu.simplezhihudemo.util.JSONUtil;
import com.scnu.bangzhu.simplezhihudemo.util.UrlUtil;
import com.scnu.bangzhu.simplezhihudemo.widget.HorizontalSlidePageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by bangzhu on 2016/8/20.
 */
public class IndexFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewPager mViewPager;
    private TextView mViewPagerTitle;
    private LinearLayout mDotGroup;
    private ViewPagerAdapter mVPAdapter;
    private ListView mListView;
    private RequestQueue mQueue;
    private List<News> mNewsList;
    private HotNewsListAdapter hotNewsListAdapter;
    private List<HotNews> mHotNewsList;
    private int currentItem = 0;
    private ScheduledExecutorService mScheduleExecutorService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_index, container, false);
            initView();
            bindView();
            setContent();
            setListener();
        }
        return rootView;
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mViewPagerTitle = (TextView) rootView.findViewById(R.id.tv_viewpager_title);
        mDotGroup = (LinearLayout) rootView.findViewById(R.id.ll_dotgroup);
        mListView = (ListView) rootView.findViewById(R.id.lv_hotnews_list);

    }

    private void bindView() {
        //设置 刷新圈的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_scheme_color);
        //设置 刷新圈的大小
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.swipe_bg_color);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, 24);
        mSwipeRefreshLayout.setProgressViewEndTarget(true, 200);

        mNewsList = new ArrayList<News>();
        mVPAdapter = new ViewPagerAdapter(getActivity(), mNewsList);
        mViewPager.setAdapter(mVPAdapter);
        mViewPager.setPageTransformer(true, new HorizontalSlidePageTransformer());

        mHotNewsList = new ArrayList<>();
        hotNewsListAdapter = new HotNewsListAdapter(getActivity(), mHotNewsList);
        mListView.setAdapter(hotNewsListAdapter);
    }

    private void setContent() {
        mQueue = Volley.newRequestQueue(getActivity());
        getLatestNews();
        initDotGroup();
        getHotNews();

    }

    private void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {

    }

    //加载最新新闻
    private void getLatestNews() {
        JsonObjectRequest request = new JsonObjectRequest(UrlUtil.getLatestNews(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray stories = jsonObject.optJSONArray("stories");
                            for (int i = 0; i < stories.length(); i++) {
                                News news = new News();
                                JSONObject obj = (JSONObject) stories.get(i);
                                JSONArray images = obj.optJSONArray("images");
                                news.setId(obj.getInt("id"));
                                news.setType(obj.getInt("type"));
                                news.setImages(images.get(0).toString());
                                news.setGa_prefix(obj.getString("ga_prefix"));
                                news.setTitle(obj.getString("title"));
                                String str = news.getTitle();
                                mNewsList.add(news);
                                Log.i("HZWING", "onResponse "+mNewsList.size()+"=================");
                            }
                            mVPAdapter.notifyDataSetChanged();
                            Message msg = new Message();
                            msg.what = 12581;
                            mHandler.sendMessageAtTime(msg, 10);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(new String(response.data, "UTF-8"));
                    return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return Response.error(new ParseError(e));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return Response.error(new ParseError(e));
                }
            }
        };
        mQueue.add(request);
    }

    //初始化点列表视图
    private void initDotGroup(){
        for(int i=0;i<5;i++){
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(0, 0, 20, 0);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.point);
            mDotGroup.addView(imageView);
        }
        ((ImageView)mDotGroup.getChildAt(currentItem)).setImageResource(R.drawable.point_selected);
    }

    //加载热闻
    private void getHotNews(){
        JsonObjectRequest request = new JsonObjectRequest(UrlUtil.getHotNews(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray recent = response.optJSONArray("recent");
                        mHotNewsList.addAll(JSONUtil.getJsonList(recent.toString(), HotNews.class));
                        hotNewsListAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(new String(response.data, "UTF-8"));
                        return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return Response.error(new ParseError(e));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return Response.error(new ParseError(e));
                    }
                }
        };
        mQueue.add(request);
    }

    //开启轮播自动滚动
    private void startAutoScroll(){
        mScheduleExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduleExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5, 5, TimeUnit.SECONDS);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ImageView iv1 = (ImageView) mDotGroup.getChildAt(position);
            ImageView iv2 = (ImageView) mDotGroup.getChildAt(currentItem);
            if(iv1 != null){
                iv1.setImageResource(R.drawable.point_selected);
            }
            if(iv2 != null){
                iv2.setImageResource(R.drawable.point);
            }
            currentItem = position;
            mViewPagerTitle.setText(mNewsList.get(position).getTitle());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem+1)%5;
            Message msg = new Message();
            msg.what = 123;
            mHandler.sendMessage(msg);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 12581:
                    mViewPagerTitle.setText(mNewsList.get(currentItem).getTitle());
                    break;
                case 123:
                    mViewPager.setCurrentItem(currentItem);
                    mViewPagerTitle.setText(mNewsList.get(currentItem).getTitle());
                    ((ImageView)mDotGroup.getChildAt(currentItem)).setImageResource(R.drawable.point_selected);
                    int prev = currentItem -1;
                    if(prev<0)
                        prev = 4;
                    ((ImageView)mDotGroup.getChildAt(prev)).setImageResource(R.drawable.point);
                    break;
            }
        }
    };
}
