package com.scnu.bangzhu.simplezhihudemo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scnu.bangzhu.simplezhihudemo.R;
import com.scnu.bangzhu.simplezhihudemo.fragment.IndexFragment;

public class MainActivity extends Activity  implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private ImageView mTopbarIcon;
    private LinearLayout mLeftDrawer;
    private boolean isDrawerOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (LinearLayout) findViewById(R.id.ll_left_drawer);
        mTopbarIcon = (ImageView) findViewById(R.id.iv_topbar_icon);
    }

    private void setListener(){
        mTopbarIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_topbar_icon:
                if(!mDrawerLayout.isDrawerOpen(mLeftDrawer)){
                    mDrawerLayout.openDrawer(mLeftDrawer);

                }else{
                    mDrawerLayout.closeDrawer(mLeftDrawer);
                }
                break;
        }
    }
}
