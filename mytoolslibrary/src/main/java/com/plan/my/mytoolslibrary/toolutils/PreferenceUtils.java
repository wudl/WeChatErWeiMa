package com.plan.my.mytoolslibrary.toolutils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



public class PreferenceUtils {
	private static final String PREFERENCE_FILE = "parttime.preferences";
	private Context context;
	private DesUtil des;

	public PreferenceUtils(Context context) {
		this.context = context;
		des = new DesUtil(Constant.KEY_PW);
	}

	
	/**
	 * 保存参数MODE_PRIVATE
	 */
	public void save(String key, String val) {
		SharedPreferences preferences = context.getSharedPreferences(
				PREFERENCE_FILE, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		if("psw".compareTo(key) == 0){
			try {
				val = des.encrypt(val);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		editor.putString(key, val);
		editor.commit();
	}

	/**
	 * 获取各项配置参数
	 * 
	 * @return
	 */
	public String getPreferences(String key) {
		SharedPreferences preferences = context.getSharedPreferences(
				PREFERENCE_FILE, Context.MODE_PRIVATE);
		if("psw".compareTo(key) == 0){
			try {
				return des.decrypt(preferences.getString(key, ""));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return preferences.getString(key, "");
	}
	
	/**
	 * 删除本地缓存参数
	 * 
	 * @return
	 */
	public boolean deletePreferences() {
		boolean isCommit = false;
		try {
			SharedPreferences preferences = context.getSharedPreferences(
					PREFERENCE_FILE, Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.clear();
			editor.commit();
			isCommit = true;
		} catch (Exception e) {
			isCommit = false;
		}
		
		return isCommit;
	}
}
