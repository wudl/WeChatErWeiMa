package cn.xtan.wechaterweima.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImageUtils {
	
	public static String IMAGE_NAME = DateUtils.getMillisecondTime() +".png";
	
	public static final String IMAGE_PATH = //Environment.getRootDirectory()+ "/isysoft/img/";
			Environment.getExternalStorageDirectory() + "/小程序二维码/" ;//"/isysoft/img/" ;

	/**二维码图片存放路径**/
	public static final String IMAGE_PATH_QRCODE = Environment
			.getExternalStorageDirectory() + "/小程序二维码/" ;
	
	public static String getImageName(){
		//精确到秒就可以
		return DateUtils.getDateTimeFormatString(new Date(), DateUtils.FormatType.YYYYMMDDHHMMSS_1) +".png";
//		return DateUtils.getMillisecondTime() +".png";
	}
	
	/**
	 * 判断图片是否是gif图片
	 * @param url
	 * @return
	 */
	public static boolean isThisPictureGif(String url) {
		return !TextUtils.isEmpty(url) && url.endsWith(".gif");
	}
	
	
	public void changeImage(Bitmap bitmap){
		Bitmap bmp = null;
	       if (bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
	    	   bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.RGB_565);
		        Canvas c = new Canvas(bmp);
//		        bitmap.draw(c);

	        }
	}

	public static void saveBitmap(Bitmap bitmap, String filepath) {
		File f = new File(filepath);
		try {
			if (f.exists()) {
				f.delete();
			} else {
				if (!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将bitmap存储为文件
	 */
	public static void saveBitmap1(Bitmap bitmap, String filename) {
		File f = new File(IMAGE_PATH,filename);
		if (!f.exists()) {
			try {
				f.mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	// 上传图片
	public static String uploadFile(Context context, File uploadFile) {
		String imgPath = "";
//		LoginValueUtils loginValueUtils = new LoginValueUtils(context);
//		String jessionId = loginValueUtils.getSessionId();
		String jessionId = "";
//		String appNodeUrl = loginValueUtils.getAppNodeUrl();
		String appNodeUrl = "";
		if (jessionId == null || "".equals(jessionId)) {
			return imgPath;
		}
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		DataOutputStream ds = null;
		try {
			URL url = new URL(appNodeUrl + "" + ";jsessionid="
					+ jessionId);//URLs.UPLOAD_FILE
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "GBK");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"img\";filename=\"" + uploadFile.getName() + "\""
					+ end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(uploadFile);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			int code = con.getResponseCode();
			String value = "";
			if (code == 200) {
				InputStream is = con.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "GBK"));
				int ch;
				StringBuffer b = new StringBuffer();
				while ((ch = reader.read()) != -1) {
					b.append((char) ch);
				}
				value = b.toString();
			}
			try {
				JSONObject jsonObject = new JSONObject(value);
				if ("00".equals(jsonObject.getString("returnCode"))) {
					imgPath = jsonObject.getString("imgPath");
				} else if ("-1".equals(jsonObject.getString("returnCode"))) {
					// QNManageLog.D("" + jsonObject.getString("returnMsg"));
				} else {
					// QNManageLog.D("上传图片异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgPath;
	}

	// 上传图片
	public static String uploadFile(Context context, File uploadFile,
									int pictureOrder) {
//		LoginValueUtils loginValueUtils = new LoginValueUtils(context);
//		String jessionId = loginValueUtils.getSessionId();
		String jessionId = "";
//		String appNodeUrl = loginValueUtils.getAppNodeUrl();
		String appNodeUrl = "";
		String imgPath = "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("number", String.valueOf(pictureOrder));

		if (jessionId == null || "".equals(jessionId)) {
			return imgPath;
		}
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		DataOutputStream ds = null;
		try {
			URL url = new URL(appNodeUrl + "" + ";jsessionid="
					+ jessionId);//URLs.UPLOAD_PICTURE_FOR_MICRO_WEB
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "GBK");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			ds = new DataOutputStream(con.getOutputStream());

			FileInputStream fStream = new FileInputStream(uploadFile);

			StringBuffer sb = null;
			String params = "";
			/***
			 * 以下是用于上传参数
			 */
			if (param != null && param.size() > 0) {
				Iterator<String> it = param.keySet().iterator();
				while (it.hasNext()) {
					sb = null;
					sb = new StringBuffer();
					String key = it.next();
					String value = param.get(key);
					sb.append(twoHyphens).append(boundary).append(end);
					sb.append("Content-Disposition: form-data; name=\"")
							.append(key).append("\"").append(end)
							.append(end);
					sb.append(value).append(end);
					params = sb.toString();
				//	Log.i(TAG, key + "=" + params + "##");
					ds.writeBytes(params);
					// ds.flush();
				}
			}
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"img\";filename=\"" + uploadFile.getName() + "\""
					+ end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			int code = con.getResponseCode();
			String value = "";
			if (code == 200) {
				InputStream is = con.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "GBK"));
				int ch;
				StringBuffer b = new StringBuffer();
				while ((ch = reader.read()) != -1) {
					b.append((char) ch);
				}
				value = b.toString();
			}
			Log.e("上传微官网头图", "图片上传结束后返回值：" + value);
			try {
				JSONObject jsonObject = new JSONObject(value);
				if ("00".equals(jsonObject.getString("returnCode"))) {
					imgPath = jsonObject.getString("imgPath");
				} else if ("-1".equals(jsonObject.getString("returnCode"))) {
					//QNManageLog.D("" + jsonObject.getString("returnMsg"));
				} else {
				//	QNManageLog.D("上传图片异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			ds.close();
//			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgPath;
	}

    // 下载图片通过url
    public static Drawable loadImageFromUrl(String url) {
        Log.d("TAG", "HttpUtil loadImageFromUrl start,imgUrl:" + url);
        URL m;
        InputStream i = null;
        Drawable d = null;
        try {
            m = new URL(url);
            i = (InputStream) m.getContent();
            d = Drawable.createFromStream(i, "src");
        } catch (MalformedURLException e1) {
        	Log.d("TAG", "HttpUtil loadImageFromUrl MalformedURLException", e1);
            d = null;
        } catch (IOException e) {
        	Log.d("TAG", "HttpUtil loadImageFromUrl IOException", e);
            d = null;
        } catch (Exception e) {
        	Log.d("TAG", "HttpUtil loadImageFromUrl Exception", e);
            d = null;
        }
        return d;
    }

    /**
     *
     * @param src  二维码的背景图
     * @param watermark   加上二维码
     * @return
     */
    public  static Bitmap createBitmap(Bitmap src, Bitmap watermark, float left, float top){
		if( src == null ){
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		//create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888);//创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas( newb );
		//draw src into
		cv.drawBitmap( src, 0, 0, null );//在 0，0坐标开始画入src
		//draw watermark into
		cv.drawBitmap( watermark, left, top, null );//在src的固定位置划入二维码
		//save all clip
		cv.save( Canvas.ALL_SAVE_FLAG );//保存
		//store
		cv.restore();//存储
		return newb;

		}

    /**
     * 在图片上面写字()
     *
     * @param bitmap
     * @param str
     * @param textsize
     * @param color
     */
    public static Bitmap drawWordToBitmap(Bitmap bitmap, String str, float left, float top, float textsize, int color) {

		int width = bitmap.getWidth();
		int hight = bitmap.getHeight();
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Config.ARGB_8888); // 建立一个空的BitMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些

		Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(bitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setTextSize(textsize);// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(color);// 采用的颜色
		canvas.drawText(str, left, top, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制20, 26,
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return icon;
    }



    public static Bitmap drawCenterWordToBitmap(Bitmap bitmap, String str, float top, float textsize, int color) {

//    	Paint paint= new Paint();Rect rect = new Rect();
//    	//返回包围整个字符串的最小的一个Rect区域
//    	paint.getTextBounds(str, 0, 1, rect);
//    	int strwidth = rect.width();
//    	int strheight = rect.height();

 		int width = bitmap.getWidth();
 		int hight = bitmap.getHeight();
 		Bitmap icon = Bitmap
 				.createBitmap(width, hight, Config.ARGB_8888); // 建立一个空的BitMap
 		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

 		Paint photoPaint = new Paint(); // 建立画笔
 		photoPaint.setDither(true); // 获取跟清晰的图像采样
 		photoPaint.setFilterBitmap(true);// 过滤一些

 		Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
 		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
 		canvas.drawBitmap(bitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
 		// dst使用的填充区photoPaint

 		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
 				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
 		textPaint.setTextSize(textsize);// 字体大小
 		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
 		textPaint.setColor(color);// 采用的颜色

// 		Rect rect = new Rect();
    	//返回包围整个字符串的最小的一个Rect区域
// 		textPaint.getTextBounds(str, 0, 1, rect);
//    	int strwidth = rect.width();
//    	int strheight = rect.height();

    	float strwidth = textPaint.measureText(str);
    	int a= (bitmap.getWidth() - ((int)(strwidth+0.5f)))/2;


 		canvas.drawText(str, a, top, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制20, 26,
 		canvas.save(Canvas.ALL_SAVE_FLAG);
 		canvas.restore();

 		return icon;
     }




    public static Bitmap drawHalfCenterWordToBitmap(Bitmap bitmap, String str, float top, float textsize, int color) {

//    	Paint paint= new Paint();Rect rect = new Rect();
//    	//返回包围整个字符串的最小的一个Rect区域
//    	paint.getTextBounds(str, 0, 1, rect);
//    	int strwidth = rect.width();
//    	int strheight = rect.height();

 		int width = bitmap.getWidth();
 		int hight = bitmap.getHeight();
 		Bitmap icon = Bitmap
 				.createBitmap(width, hight, Config.ARGB_8888); // 建立一个空的BitMap
 		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

 		Paint photoPaint = new Paint(); // 建立画笔
 		photoPaint.setDither(true); // 获取跟清晰的图像采样
 		photoPaint.setFilterBitmap(true);// 过滤一些

 		Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
 		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
 		canvas.drawBitmap(bitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
 		// dst使用的填充区photoPaint

 		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
 				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
 		textPaint.setTextSize(textsize);// 字体大小
 		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
 		textPaint.setColor(color);// 采用的颜色
 		
// 		Rect rect = new Rect();
    	//返回包围整个字符串的最小的一个Rect区域
// 		textPaint.getTextBounds(str, 0, 1, rect);
//    	int strwidth = rect.width();
//    	int strheight = rect.height();
    	
    	float strwidth = textPaint.measureText(str);
    	int a= (int)(((bitmap.getWidth()/10)*6.5 - ((strwidth+0.5f)))/2 + 0.5f);

    	
 		canvas.drawText(str, a, top, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制20, 26, 
 		canvas.save(Canvas.ALL_SAVE_FLAG);
 		canvas.restore();
 		
 		return icon;
     }
    
    
	/**
	 * 将bitmap存储为文件
	 */
	public static boolean saveBitmap2(Bitmap bitmap, String filename) {
		File f = new File(IMAGE_PATH);

		if (!f.exists()) {
			f.mkdir();
		}

		f = new File(IMAGE_PATH, filename);
		  FileOutputStream fOut = null;
		  try {
		   f.createNewFile();
		   fOut = new FileOutputStream(f);
		   bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

		   fOut.flush();
		   fOut.close();
		  } catch (FileNotFoundException e) {
		   return false;
		  } catch (IOException e) {
			   return false;
		  }

		return true;
	}
    
	
	/**
	 * 将bitmap存储为文件(二维码专用)
	 */
	public static boolean saveBitmap_QrCode(Bitmap bitmap, String filename) {
		File f = new File(IMAGE_PATH_QRCODE);
		if (!f.exists()) {
			f.mkdir();
		}

		f = new File(IMAGE_PATH_QRCODE, filename);
		  FileOutputStream fOut = null;
		  try {
		   f.createNewFile();
		   fOut = new FileOutputStream(f);
		   bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		   fOut.flush();
		   fOut.close();
		  } catch (FileNotFoundException e) {
		   return false;
		  } catch (IOException e) {
			   return false;
		  }

		return true;
	}
    
	
	
	
}
