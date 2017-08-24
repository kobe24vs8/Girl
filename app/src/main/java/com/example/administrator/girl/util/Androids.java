package com.example.administrator.girl.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.util
 * 文件名:   Androids
 * 创建者:   LDW
 * 创建时间: 2017/8/22  15:41
 * 描述:    粘贴板的调用
 */
public class Androids {
    public static void copyToClipBoard(Context mContext, String text, String success) {
        ClipData clipData = ClipData.newPlainText("meizhi_copy", text);
        ClipboardManager manager = (ClipboardManager) mContext.getSystemService(Context.
                CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        Toast.makeText(mContext, success, Toast.LENGTH_SHORT).show();
    }
}
