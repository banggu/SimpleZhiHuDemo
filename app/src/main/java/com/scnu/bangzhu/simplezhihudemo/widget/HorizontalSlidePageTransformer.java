package com.scnu.bangzhu.simplezhihudemo.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by bangzhu on 2016/8/23.
 */
public class HorizontalSlidePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        if(position < -1) { // [-Infinity, -1]
            page.setAlpha(0);
        } else if(position <= 0) { // [-1, 0]
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        } else if(position <= 1) { // (0, 1]
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        } else{ // (1, +Infinity]
            page.setAlpha(0);
        }
    }
}
