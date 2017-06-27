package com.plan.my.mytoolslibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plan.my.mytoolslibrary.R;


public class UITools {

	/**
	 * @耗时操作时，启动该dialog,操作结束时，销毁
	 * @param context
	 * @param msg
	 *            提示的文字
	 * @return
	 * @author wudl
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loadingdialog, null);// 得到加载view   toast_lay
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_lay);//dialog_lay  toast_lay
		// 加载布局
		// main.xml中的ImageView
		// ImageView spaceshipImage = (ImageView) v.findViewById(R.id.image);
		TextView tipTextView = (TextView) v.findViewById(R.id.loading_txt);//loading_txt  toast_txt
		// 提示文字
		// 加载动画
		// Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
		// context, R.anim.load_animation);
		// 使用ImageView显示动画
		// spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		if (msg != null) {
			tipTextView.setText(msg);// 设置加载信息
		}
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		// 创建自定义样式dialog
		// loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setCanceledOnTouchOutside(false);// 点击外部边缘不可以取消,点击返回键可以取消.
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}

	
	public static Dialog createNewLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_newdialog, null);// 得到加载view   toast_lay
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_lay);//dialog_lay  toast_lay
		// 加载布局
		// main.xml中的ImageView
		 ImageView spaceshipImage = (ImageView) v.findViewById(R.id.loading_img);
		TextView tipTextView = (TextView) v.findViewById(R.id.loading_txt);//loading_txt  toast_txt
		// 提示文字
		// 加载动画
//		 Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
//		 context, R.anim.load_animation);
		 spaceshipImage.setBackgroundResource(R.drawable.load_animation);
		 AnimationDrawable anim = (AnimationDrawable) spaceshipImage.getBackground();
		 anim.start();
		// 使用ImageView显示动画
//		 spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		if (msg != null) {
			tipTextView.setText(msg);// 设置加载信息
		}
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		// 创建自定义样式dialog
		// loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setCanceledOnTouchOutside(false);// 点击外部边缘不可以取消,点击返回键可以取消.
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}
	
	/**
	 * @公用的Toast
	 * @param str
	 *            提示的内容
	 * @author wudl
	 */
	public static void Toast(String str, Context mContext) {
		  /*
		   LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
		   View layout = inflater.inflate(R.layout.toast_lay,
		     null);
		   TextView text = (TextView) layout.findViewById(R.id.toast_txt);
		   text.setText(str);
		   android.widget.Toast toast = new android.widget.Toast(mContext);
			toast.setGravity(Gravity.CENTER, 0, 0);
		   toast.setDuration(android.widget.Toast.LENGTH_SHORT);
		   toast.setView(layout);
		   toast.show();
		 */
		android.widget.Toast toast = android.widget.Toast.makeText(mContext,
				str, android.widget.Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
	}

	/**
	 * @公用的Toast
	 * @param str
	 * @author wudl
	 */
	public static void Toast(String str) {
		  /*
		   LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
		   View layout = inflater.inflate(R.layout.toast_lay,
		     null);
		   TextView text = (TextView) layout.findViewById(R.id.toast_txt);
		   text.setText(str);
		   android.widget.Toast toast = new android.widget.Toast(mContext);
			toast.setGravity(Gravity.CENTER, 0, 0);
		   toast.setDuration(android.widget.Toast.LENGTH_SHORT);
		   toast.setView(layout);
		   toast.show();
		 */

		/*****
		android.widget.Toast toast = android.widget.Toast.makeText(GlobalContext.getInstance().getApplicationContext(),
				str, android.widget.Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		***/
	}
	
	/**
	 * 针对网络异常，进行友好提示
	 * 
	 * @param context
	 * @param str
	 *            异常信息
	 * @return
	 */
	public static String fromError(Context context, String str) {
		if (str.indexOf("SocketTimeoutException") > -1) {
			return context.getString(R.string.error_so_timeout);
		}
		if (str.indexOf("ConnectTimeoutException") > -1) {
			return context.getString(R.string.error_connect_timeout);
		}
		return context.getString(R.string.error_so_timeout);
	}
}
