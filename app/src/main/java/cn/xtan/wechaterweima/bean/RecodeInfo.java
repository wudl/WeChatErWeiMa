package cn.xtan.wechaterweima.bean;

import android.graphics.Bitmap;

/**
 * 图片返回info
 * @author wudl
 *
 *2015年10月23日下午8:01:41
 */
public class RecodeInfo {

	private int code;
	private String tid;
	private String vCode;
	private Bitmap bitmap;

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getvCode() {
		return vCode;
	}
	public void setvCode(String vCode) {
		this.vCode = vCode;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}
