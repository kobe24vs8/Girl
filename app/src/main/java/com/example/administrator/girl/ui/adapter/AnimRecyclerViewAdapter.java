package com.example.administrator.girl.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.administrator.girl.R;

import static android.R.attr.action;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui.adapter
 * 文件名:   AnimRecyclerViewAdapter
 * 创建者:   LDW
 * 创建时间: 2017/8/23  9:52
 * 描述:    TODO
 */
public class AnimRecyclerViewAdapter<T extends RecyclerView.ViewHolder>
                                        extends RecyclerView.Adapter<T>{
    private static final int DELAY = 138;
    private int mLastPosition = -1;


    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public void showItemAnim(final View view, final int position) {
        Context context = view.getContext();
        if (position > mLastPosition) {
            //设置渐变的透明度
            view.setAlpha(0);
            view.postDelayed(()->{
                //设置加载动画的效果
                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.slide_in_right);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setAlpha(1);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }, DELAY * position);
            mLastPosition = position;
        }
    }
}
