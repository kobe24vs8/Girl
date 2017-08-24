package com.example.administrator.girl.ui.adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.example.administrator.girl.R;
import com.example.administrator.girl.data.entity.Gank;
import com.example.administrator.girl.ui.WebActivity;
import com.example.administrator.girl.util.StringStyles;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui.adapter
 * 文件名:   GankListAdapter
 * 创建者:   LDW
 * 创建时间: 2017/8/23  9:53
 * 描述:    TODO
 */
public class GankListAdapter extends AnimRecyclerViewAdapter<GankListAdapter.ViewHolder>{

    private List<Gank> mGankList;

    public GankListAdapter(List<Gank> gankList) {
        mGankList = gankList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gank, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //对子项进行赋值操作
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //拿到List中的第position个数据
        Gank gank = mGankList.get(position);
        if (position == 0) {
            showCategory(holder);
        } else {
            boolean theCategoryOfLastEqualsToThis = mGankList.get(
                    position - 1).type.equals(mGankList.get(position).type);
            if (!theCategoryOfLastEqualsToThis) {
                showCategory(holder);
            }
            else {
                hideCategory(holder);
            }
        }

        holder.category.setText(gank.type);
        SpannableStringBuilder builder = new SpannableStringBuilder(gank.desc).append(
                StringStyles.format(holder.gank.getContext(), " (via. " +
                        gank.who +
                        ")", R.style.ViaTextAppearance));
        CharSequence gankText = builder.subSequence(0, builder.length());

        holder.gank.setText(gankText);
        showItemAnim(holder.gank, position);
    }

    private void showCategory(ViewHolder viewHolder) {
        if (!isVisibleOf(viewHolder.category)) {
            viewHolder.category.setVisibility(View.VISIBLE);
        }
    }

    private void hideCategory(ViewHolder viewHolder) {
        if (isVisibleOf(viewHolder.category)) {
            viewHolder.category.setVisibility(View.GONE);
        }
    }

    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.title)
        TextView gank;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //Linearlayout的点击事件
        @OnClick(R.id.gank_layout)
        void onGank(View view) {
            Gank gank = mGankList.get(getLayoutPosition());
            Intent intent = WebActivity.newIntent(view.getContext(), gank.url, gank.desc);
            view.getContext().startActivity(intent);
        }
    }
}
