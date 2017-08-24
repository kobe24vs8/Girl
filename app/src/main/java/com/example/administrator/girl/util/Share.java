package com.example.administrator.girl.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.administrator.girl.R;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.util
 * 文件名:   Share
 * 创建者:   LDW
 * 创建时间: 2017/8/13  10:44
 * 描述:    TODO
 */
public class Share {

    public static void share(Context mContext, int stringRes) {
        share(mContext, mContext.getString(stringRes));
    }

    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static void share(Context mContext, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, mContext.getString(R.string.action_share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(
                Intent.createChooser(intent, mContext.getString(R.string.action_share)));
    }
}
