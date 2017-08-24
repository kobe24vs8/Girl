package com.example.administrator.girl.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.girl.ui.adapter.MeizhiListAdapter;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.widget
 * 文件名:   RatioImageView
 * 创建者:   LDW
 * 创建时间: 2017/8/11  18:51
 * 描述:    自定义View继承ImageView
 */
public class RatioImageView extends ImageView {

    private int originalWidth;
    private int originalHeight;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    //设置图片的定宽和定高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originalHeight > 0 && originalWidth > 0) {

            float ratio = (float) originalWidth / (float) originalHeight;

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            // TODO: 现在只支持固定宽度
            if (width > 0) {
                height = (int) ((float) width / ratio);
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


}
