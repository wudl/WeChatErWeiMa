package com.plan.my.mytoolslibrary.log.save;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class FileService {
//	public static final String FILEPATH = Constant
//			.getSystemFilePath(Constant.FLASH_PATH) + "/NubePhone/CalllogLog/";

	public static final String FILEPATH = //Environment.getRootDirectory()+ "/isysoft/img/";
			Environment.getExternalStorageDirectory() + "/留/SaveLog/" ;//"/isysoft/img/" ;
	
	private static String logDate = "s";

	private static boolean isDeletingFile = false;

	private static final int LOG_FILE_DELETE_DELAY = 10;

	public static void saveLogToFile(String content) {
		File dest = new File(FILEPATH);
		if (!dest.exists()) {
			dest.mkdir();
		}
		dest = null;
		// 得到当前时间戳
		String date =  System.nanoTime() + "";//DateUtils.getCurrentTime()
//		String date = DateUtil.getYMDHMSTime(0);
		// 日志内容加上时间前缀
		content = date + "---" + content;

		// 取得YYYY-MM-DD形式的日期
		if (!TextUtils.isEmpty(date) && date.length() >= 10) {
			date = date.substring(0, 10);
		}

		// 判断日志是否超过时限（暂定10天）
		if (TextUtils.isEmpty(logDate)) {
			logDate = date;
		} else {
			// 当天的判断，删除当天之前的固定天数的日志文件
			if (!isDeletingFile) {
				if (!logDate.equals(date)) {
					logDate = date;
					// 正在删除文件的标记
					isDeletingFile = true;
					new Thread(new Runnable() {
						@Override
						public void run() {
							Log.d("zrp", "进入删除多余的日志文件线程");
							try {
								File path = new File(FILEPATH);
								File[] files = path.listFiles();
								if (null != files
										&& files.length > (LOG_FILE_DELETE_DELAY - 1)) {
									for (File file : files) {
										if (file.lastModified() < (System
												.currentTimeMillis() - (long) LOG_FILE_DELETE_DELAY
												* 24 * 60 * 60 * 1000)) {
											file.delete();
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println(e.getMessage());
							} finally {
								isDeletingFile = false;
							}
						}
					}).run();

				}
			}

		}

		String fileName = "";
		// 文件名
		fileName = FILEPATH + date + ".log";
		File file = new File(fileName);

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			out.write("\n");
			out.write(content);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

}
