package com.dhb.qqhook;

import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by DHB on 2015/12/31.
 */
public class Common extends Application {

    //    public static final String path = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.tencent.mobileqq/dhb";
    public static final String DIRECTORY_PATH = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.dhb.qqhook/";
    public static final String path = DIRECTORY_PATH + "dhb";
    public static final String QQ_PATH_TEMP_FILE = DIRECTORY_PATH + "qqtemp";
    public static final String QQ_KEY_PATH_TEMP_FILE = DIRECTORY_PATH + "qqkeytemp";

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        Toast.makeText(context, "已复制", Toast.LENGTH_LONG).show();
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }
}
