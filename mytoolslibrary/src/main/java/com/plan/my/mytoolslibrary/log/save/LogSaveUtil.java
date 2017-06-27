package com.plan.my.mytoolslibrary.log.save;

import android.util.Log;


public class LogSaveUtil {

	public static final String CALLLOG_TAG = "calllogTag";

	// Log关闭变量
	public static final boolean bOpenLog = true;
	// Log保存到文件开关变量
	public static final boolean bOpenSaveLogToFile = true;

	public static int d(String msg) {
		if (bOpenLog) {
			saveLogToFile(CALLLOG_TAG + ":" + msg);
			return Log.d(CALLLOG_TAG, msg);
		} else {
			return 0;
		}
	}

    public static int debug(String tag, String msg) {
        if (bOpenLog) {
            saveLogToFile(tag + ":" + msg);
            return Log.d(tag, msg);
        } else {
            return 0;
        }
    }

	public static int d(String tag, String msg) {
		if (bOpenLog) {
			saveLogToFile(CALLLOG_TAG + ":【" +tag +"】" + msg);
			return Log.d(CALLLOG_TAG, msg);
		} else {
			return 0;
		}
	}

	public static int i(String msg) {
		if (bOpenLog) {
			saveLogToFile(CALLLOG_TAG + ":" + msg);
			return Log.i(CALLLOG_TAG, msg);
		} else {
			return 0;
		}
	}

	public static int e(String msg) {
		if (bOpenLog) {
			saveLogToFile(CALLLOG_TAG + ":" + msg);
			return Log.e(CALLLOG_TAG, msg);
		} else {
			return 0;
		}
	}

	public static int e(Throwable e) {
		if (bOpenLog) {
			saveLogToFile(CALLLOG_TAG + ":" + e);
			return Log.e(CALLLOG_TAG, "", e);
		} else {
			return 0;
		}
	}

	public static int e(String tag, String msg) {
		if (bOpenLog) {
			saveLogToFile(CALLLOG_TAG + ":" + msg);
			return Log.e(CALLLOG_TAG, msg);
		} else {
			return 0;
		}
	}

	public static int e(String msg, Throwable e) {
		if (bOpenLog) {
			saveLogToFile(CALLLOG_TAG + ":" + msg);
			return Log.e(CALLLOG_TAG, msg, e);
		} else {
			return 0;
		}
	}

	/**
	 * @author: liyb
	 * @Title: saveLogToFile
	 * @Description: 保存日志到文件
	 * @param strMessage
	 *            待保存的日志
	 * @date: 2012-10-13 下午08:12:28
	 */
	private static void saveLogToFile(String strMessage) {
		if (bOpenSaveLogToFile) {
			FileService.saveLogToFile(strMessage);
		}
	}
}
