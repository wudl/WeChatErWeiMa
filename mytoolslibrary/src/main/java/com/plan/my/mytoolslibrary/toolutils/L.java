package com.plan.my.mytoolslibrary.toolutils;

import android.util.Log;

/**
 * Log输出
 *
 * Created by wudl on 2016/9/9 11:34
 * <p/>
 * 邮箱 770616344@qq.com
 */
public class L {

    // 控制Log开关变量
    public static final boolean bOpenLog = true;

    public static void d(String tag, String msg) {
        if (bOpenLog) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (bOpenLog) {
            Log.w(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (bOpenLog) {
            Log.v(tag, msg);
        }
    }


    public static void i(String tag, String msg) {
        if (bOpenLog) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (bOpenLog) {
            Log.e(tag, msg);
        }
    }
}
