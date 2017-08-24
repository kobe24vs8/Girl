package com.example.administrator.girl.ui.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.girl.R;
import com.example.administrator.girl.data.entity.Meizhi;
import com.example.administrator.girl.func.OnMeizhiTouchListener;
import com.example.administrator.girl.widget.RatioImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui.adapter
 * 文件名:   MeizhiListAdapter
 * 创建者:   LDW
 * 创建时间: 2017/8/11  9:40
 * 描述:    TODO
 */
public class MeizhiListAdapter  extends RecyclerView.Adapter<MeizhiListAdapter.ViewHolder>{

    private List<Meizhi> mList;
    private Context mContext;

    private OnMeizhiTouchListener mOnMeizhiTouchListener;

    public MeizhiListAdapter(Context mContext, List<Meizhi> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    //加载布局，创建实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meizhi, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //对RecyclerView的子项进行赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //获取当前的list的position
        Meizhi meizhi = mList.get(position);
        String text = meizhi.desc;

//        Log.d("TAG", "position........" + position);
//        Log.d("TAG", "TAG............." + text);
//        Log.d("TAG", "URL............." + meizhi.url);

        holder.meizhi = meizhi;
        holder.titleView.setText(text);
        holder.card.setTag(meizhi.desc);

        //imageView加载图片
        Glide.with(mContext)
                .load(meizhi.url)
                .into(holder.meizhiView);
    }

    //RecyclerView的子项的个数
    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.meizhi)
        RatioImageView meizhiView;
        @BindView(R.id.title)
        TextView titleView;
        View card;
        Meizhi meizhi;

        public ViewHolder(View view) {
            super(view);
            card = view;
            ButterKnife.bind(this, view);
            meizhiView.setOnClickListener(this);
            card.setOnClickListener(this);
            //设置图片定宽和高
            meizhiView.setOriginalSize(50, 50);
        }

        @Override
        public void onClick(View v) {
            mOnMeizhiTouchListener.onTouch(v, meizhiView, card, meizhi);
        }
    }

    public void setOnMeizhiTouchListener(OnMeizhiTouchListener OnMeizhiTouchListener) {
        this.mOnMeizhiTouchListener = OnMeizhiTouchListener;
    }

}
