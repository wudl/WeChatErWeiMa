package com.plan.my.mytoolslibrary.file;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * �ļ���������
 * 
 * @author amgl
 * 
 */
public class FileManager {

	/**
	 * SD��Ŀ¼
	 * 
	 * @return
	 */
	public static String getSdCardPath(Context context) {
		if (isExternalStorageMounted()) {
			File path = context.getExternalCacheDir();
			if (path != null) {
				return path.getAbsolutePath();
				// } else {
				// if (!cantReadBecauseOfAndroidBugPermissionProblem) {
				// cantReadBecauseOfAndroidBugPermissionProblem = true;
				// final Activity activity = GlobalContext.getInstance()
				// .getActivity();
				// if (activity == null || activity.isFinishing()) {
				// GlobalContext.getInstance().getUIHandler()
				// .post(new Runnable() {
				// @Override
				// public void run() {
				// Toast.makeText(
				// GlobalContext.getInstance(),
				// R.string.please_deleted_cache_dir,
				// Toast.LENGTH_SHORT).show();
				// }
				// });
				//
				// return "";
				// }
				// activity.runOnUiThread(new Runnable() {
				// @Override
				// public void run() {
				// new AlertDialog.Builder(activity)
				// .setTitle(R.string.something_error)
				// .setMessage(
				// R.string.please_deleted_cache_dir)
				// .setPositiveButton(
				// R.string.ok,
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(
				// DialogInterface dialog,
				// int which) {
				//
				// }
				// }).show();
				//
				// }
				// });
				// }
				// }
			}
		} else {
			return "";
		}

		return "";
	}

	public File getAlbumStorageDir(String albumName) {

		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				albumName);
		if (!file.mkdirs()) {
//			AppLogger.e("Directory not created");
		}
		return file;
	}

	public static boolean isExternalStorageMounted() {

		boolean canRead = Environment.getExternalStorageDirectory().canRead();
		boolean onlyRead = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED_READ_ONLY);
		boolean unMounted = Environment.getExternalStorageState().equals(
				Environment.MEDIA_UNMOUNTED);

		return !(!canRead || onlyRead || unMounted);
	}

	public static File createNewFile(String absolutePath) {
		File file = new File(absolutePath);
		if (file.exists()) {
			return file;
		} else {
			File dir = file.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}

			try {
				if (file.createNewFile()) {
					return file;
				}
			} catch (IOException e) {
//				AppLogger.d(e.getMessage());
				return null;

			}
		}
		return null;
	}

	public static File createNewFileInSDCard(String absolutePath) {
		if (!isExternalStorageMounted()) {
//			AppLogger.e("sdcard unavailiable");
			return null;
		}

		File file = new File(absolutePath);
		if (file.exists()) {
			return file;
		} else {
			File dir = file.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}

			try {
				if (file.createNewFile()) {
					return file;
				}
			} catch (IOException e) {
//				AppLogger.d(e.getMessage());
				return null;

			}

		}
		return null;

	}

	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null) {
				inBuff.close();
			}
			if (outBuff != null) {
				outBuff.close();
			}
		}
	}
}
