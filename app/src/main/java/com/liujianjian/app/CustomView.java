package com.liujianjian.app;

import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * created by liujianjian
 * on 2021/6/1 6:45 下午
 */
public class CustomView extends androidx.appcompat.widget.AppCompatImageView {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public CustomView(Context context) {
        super(context);

        threadLocal.set("abc");

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                CustomView.this.getMeasuredHeight();
                CustomView.this.getMeasuredWidth();
            }
        });
    }


}
