package cn.xtan.wechaterweima.utils;

import android.content.Context;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cn.xtan.wechaterweima.R;


public class DateUtils {

	public enum FormatType {
		YYYYMMDDHHMMSS_1, // 20130515
		YYYYMMDDHHMMSS_2, // 2013-05-15 02:11:09
		YYYYMMDDHHMMSS_3, // 2013年05月15日 02时11分09秒
		HHMMSS_1, // 02:11:09
		MMSS_1, // 11:09
		MMDD_1, // 07/15
		MMDD_2, // 7月15日
		MM_1, // 07
		DD_1, // 15
		YYYY_MM_DD, // 2013-07-15
		YYYY_MM_DD_2, // 2013年07月15日
		HH_MM_1, // 14:45
	}

	private static final String FORMAT_YYYYMMDDHHMMSS_1 = "yyyyMMddHHmmss";
	private static final String FORMAT_YYYYMMDDHHMMSS_2 = "yyyy-MM-dd HH:mm:ss";
	private static final String FORMAT_YYYYMMDDHHMMSS_3 = "yyyy年MM月dd日 HH时mm分ss秒";
	private static final String FORMAT_HHMMSS_1 = "HH:mm:ss";
	private static final String FORMAT_MMSS_1 = "mm:ss";
	private static final String FORMAT_MMDD_1 = "MM/dd";
	private static final String FORMAT_MMDD_2 = "MM月dd日";
	private static final String FORMAT_MM = "MM";
	private static final String FORMAT_DD = "dd";
	public static final String FORMAT_YYYY_MM_DD_1 = "yyyy-MM-dd";
	private static final String FORMAT_YYYY_MM_DD_2 = "yyyy年MM月dd日";
	private static final String FORMAT_HHMM = "HH:mm";

	public static long getMillisecondTime() {
//		Date date = new Date();
//		return date.getTime();
		return System.currentTimeMillis();
	}

	/**
	 * 
	 * 获取当前时间的millisecond值
	 * 
	 * 
	 * 
	 * @return
	 */
	public static long geCurrenttMillisecond() {
//		Date date = new Date();
//		return date.getTime();
		return System.currentTimeMillis();
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
			Locale.SIMPLIFIED_CHINESE);
	private static SimpleDateFormat yeardf = new SimpleDateFormat("yyyy",
			Locale.SIMPLIFIED_CHINESE);
	
	/**
	 * 根据long型的时间格式来获取我们自己想要的时间格式
	 * 
	 * @param mContext
	 * @param mill
	 * @return
	 */
	public static String getSureDateByMillisecond(Context mContext, long mill){
		if(mill ==0){
			return "";
		}
		long nowLongTime = System.currentTimeMillis(); //本地获取的时间1441954798523   后台的时间1442459040

		//mill为你龙类型的时间戳
		Date date=new Date(mill * 1000);
		String startTime="";
		try {
			//yyyy表示年MM表示月dd表示日//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
			SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS_2);
			//进行格式化
			startTime=sdf.format(date);
			// 5:时间
			Calendar cal = Calendar.getInstance();
			String yearStr = yeardf.format(cal.getTime());
			String todayStr = sdf.format(cal.getTime());
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
			String yesterday = sdf.format(cal.getTime());
			
			if (null == startTime || startTime.equals("")
					|| startTime.toUpperCase().equals("NULL")) {
				startTime = "";
			} else {
				if (startTime.startsWith(todayStr)) {
					startTime = startTime.substring(11, 16);
				} else if (startTime.startsWith(yesterday)) {
					startTime = mContext.getString(R.string.qn_yesterday) + " " + startTime.substring(11, 16);
				} else if (startTime.startsWith(yearStr)) {
					startTime = startTime.substring(5, 16);//5, 10
				} else {
					startTime = startTime.substring(0, 10);
				}
			}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		return startTime;
	}


	/**
	 * 根据long获取yyyy-MM-dd HH:mm:ss格式的时间
	 * @param mContext
	 * @param mill
	 * @return
	 */
	public static String getCuDateByMillisecond(Context mContext, long mill){
//		long nowLongTime = System.currentTimeMillis(); //本地获取的时间1441954798523   后台的时间1442459040

		//mill为你龙类型的时间戳
		Date date=new Date(mill * 1000);
		String startTime="";
		try {
			//yyyy表示年MM表示月dd表示日//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
			SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS_2);
			//进行格式化
			startTime=sdf.format(date);
		}catch(Exception e){
			startTime = getCurrentTime();
		}
		return startTime;
	}
	
	

	/**
	 * 时间传入长度19
	 * 今天获取到的时间是HH:mm
	 * 昨天获取到的时间是 昨天 HH:mm
	 * 今年的MM-dd HH:mm
	 * 不是今年的yyyy-MM-dd
	 * @param mContext
	 * @param mill   2011-11-11 12:34:56
	 * @return
	 */
	public static String getMyTime(Context mContext, String mill){
		if(mill == null || mill.length() !=19){
			return "";
		}
//		yyyy-MM-dd aa:bb:cc
		//mill为你龙类型的时间戳
//		Date date=new Date(mill);
		String startTime=mill;
		try {
			//yyyy表示年MM表示月dd表示日//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
//			SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS_2);
			//进行格式化
//			startTime=sdf.format(date);
			// 5:时间
			Calendar cal = Calendar.getInstance();
			String yearStr = yeardf.format(cal.getTime());
			String todayStr = sdf.format(cal.getTime());//cal.getTime()
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
			String yesterday = sdf.format(cal.getTime());
			
			if (null == startTime || startTime.equals("")
					|| startTime.toUpperCase().equals("NULL")) {
				startTime = "";
			} else {
				if (startTime.startsWith(todayStr)) {
					startTime = startTime.substring(11, 16);
				} else if (startTime.startsWith(yesterday)) {
					startTime = mContext.getString(R.string.qn_yesterday) + " " + startTime.substring(11, 16);
				} else if (startTime.startsWith(yearStr)) {
					startTime = startTime.substring(5, 16);//5, 10
				} else {
					startTime = startTime.substring(0, 10);
				}
			}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		return startTime;
	}

	/**
	 * 时间传入长度19
	 * 今天获取到的时间是MM-dd
	 * 昨天获取到的时间是 MM-dd
	 * 今年的MM-dd
	 * 不是今年的yyyy-MM-dd
	 * @param mContext
	 * @param mill   2011-11-11 12:34:56
	 * @return
	 */
	public static String getNMyTime(Context mContext, String mill){
		if(mill == null || mill.length() !=19){
			return "";
		}
//		yyyy-MM-dd aa:bb:cc
		//mill为你龙类型的时间戳
//		Date date=new Date(mill);
		String startTime=mill;
		try {
			//yyyy表示年MM表示月dd表示日//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
//			SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS_2);
			//进行格式化
//			startTime=sdf.format(date);
			// 5:时间
			Calendar cal = Calendar.getInstance();
			String yearStr = yeardf.format(cal.getTime());
			String todayStr = sdf.format(cal.getTime());//cal.getTime()
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
			String yesterday = sdf.format(cal.getTime());

			if (null == startTime || startTime.equals("")
					|| startTime.toUpperCase().equals("NULL")) {
				startTime = "";
			} else {
				if (startTime.startsWith(todayStr)) {
					startTime = startTime.substring(5, 10);
				} else if (startTime.startsWith(yesterday)) {
					startTime = startTime.substring(5, 10);
				} else if (startTime.startsWith(yearStr)) {
					startTime = startTime.substring(5, 10);//5, 10
				} else {
					startTime = startTime.substring(0, 10);
				}
				startTime = startTime.replace("-",".");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return startTime;
	}
	/**
	 * 获取格式为yyyy-MM-dd HH:mm:ss 的当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		return String.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format((System.currentTimeMillis())));
	}

	/**
	 * 获取格式为yyyy-MM-dd的当前日期
	 * @return
	 */
	public static String getCurrentDate(){
		return String.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd").format((System.currentTimeMillis())));
	}
	
	/**
	 * 
	 * 将制定格式的时间字符串转换为millisecond
	 * 
	 * 
	 * 
	 * @param time
	 * 
	 * @param formatType
	 * 
	 * @return
	 */
	public static long getMillisecondTime(String time, FormatType formatType) {
		String pattern = null;
		switch (formatType) {
		case YYYYMMDDHHMMSS_1:
			pattern = FORMAT_YYYYMMDDHHMMSS_1;
			break;
		case YYYYMMDDHHMMSS_2:
			pattern = FORMAT_YYYYMMDDHHMMSS_2;
			break;
		case YYYYMMDDHHMMSS_3:
			pattern = FORMAT_YYYYMMDDHHMMSS_3;
			break;
		case HHMMSS_1:
			pattern = FORMAT_HHMMSS_1;
			break;
		case MMSS_1:
			pattern = FORMAT_MMSS_1;
			break;
		case MMDD_1:
			pattern = FORMAT_MMDD_1;
			break;
		case MMDD_2:
			pattern = FORMAT_MMDD_2;
			break;
		case MM_1:
			pattern = FORMAT_MM;
			break;
		case DD_1:
			pattern = FORMAT_DD;
			break;
		case YYYY_MM_DD:
			pattern = FORMAT_YYYY_MM_DD_1;
			break;
		case YYYY_MM_DD_2:
			pattern = FORMAT_YYYY_MM_DD_2;
			break;
		case HH_MM_1:
			pattern = FORMAT_HHMM;
			break;
		default:
			return 0;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern,
				Locale.SIMPLIFIED_CHINESE);
		Date date = null;
		try {
			date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 将String转换成Date 
	 * 转换不成的话就返回null
	 * @param timeStr
	 * @return
	 */
	public static Date StringToDate(String timeStr){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS_2,
				Locale.SIMPLIFIED_CHINESE);
		Date date = null;
		try {
			date = sdf.parse(timeStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Calendar StringToCalendar(String timeStr){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS_2,
				Locale.SIMPLIFIED_CHINESE);
		Date date = null;
	    Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(timeStr);
		    calendar.setTime(date);  
			return calendar;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}
	
	/**
	 * 在date上加多少秒
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSeconds(Date date, int seconds) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);  
	    calendar.add(Calendar.SECOND, seconds);
	    return calendar.getTime();  
	}  
	
	/**
	 * 
	 * 将指定的Date转换为字符串
	 * 
	 * 
	 * 
	 * @param date
	 * 
	 * @param formatType
	 * 
	 * @return
	 */
	public static String getDateTimeFormatString(Date date,
												 FormatType formatType) {
		String pattern = null;
		switch (formatType) {
		case YYYYMMDDHHMMSS_1:
			pattern = FORMAT_YYYYMMDDHHMMSS_1;
			break;
		case YYYYMMDDHHMMSS_2:
			pattern = FORMAT_YYYYMMDDHHMMSS_2;
			break;
		case YYYYMMDDHHMMSS_3:
			pattern = FORMAT_YYYYMMDDHHMMSS_3;
			break;
		case HHMMSS_1:
			pattern = FORMAT_HHMMSS_1;
			break;
		case MMSS_1:
			pattern = FORMAT_MMSS_1;
			break;
		case MMDD_1:
			pattern = FORMAT_MMDD_1;
			break;
		case MMDD_2:
			pattern = FORMAT_MMDD_2;
			break;
		case MM_1:
			pattern = FORMAT_MM;
			break;
		case DD_1:
			pattern = FORMAT_DD;
			break;
		case YYYY_MM_DD:
			pattern = FORMAT_YYYY_MM_DD_1;
			break;
		case YYYY_MM_DD_2:
			pattern = FORMAT_YYYY_MM_DD_2;
			break;
		case HH_MM_1:
			pattern = FORMAT_HHMM;
			break;
		default:
			pattern = FORMAT_YYYYMMDDHHMMSS_1;
			break;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern,
				Locale.SIMPLIFIED_CHINESE);
		return format.format(date);
	}

	/**
	 * 
	 * 转换时间格式：XXX秒转为XX小时XX分XX秒
	 * 
	 * 
	 * 
	 * @param context
	 * 
	 * @param time
	 * 
	 *            单位：秒
	 * 
	 * @return
	 */
	public static String transformSS2HHMMSS(Context context, String time) {
		if (!"".equals(time) && time.length() > 0) {
			int secordTimeSpanInt = 0;
			try {
				secordTimeSpanInt = Integer.parseInt(time);
			} catch (NumberFormatException e) {
				return "";
			}
			time = "";
			long h0 = secordTimeSpanInt / 60 / 60;
			if (h0 != 0) {
				time = h0 + context.getString(R.string.qn_hour);
			}
			long m0 = (secordTimeSpanInt / 60) % 60;
			long s0 = secordTimeSpanInt % 60;
			if (h0 != 0 || m0 != 0) {
				if (s0 != 0) {
					time = time + (m0 + 1)
							+ context.getString(R.string.qn_minute);
				} else {
					time = time + m0 + context.getString(R.string.qn_minute);
				}
			}
			if (h0 == 0 && m0 != 0) {
				if (s0 != 0) {
					if ((m0 + 1) == 60) {
						time = (h0 + 1) + context.getString(R.string.qn_hour)
								+ 0 + context.getString(R.string.qn_minute);
					}
				}
			}
			if (h0 == 0 && m0 == 0) {
				time = s0 + context.getString(R.string.qn_second);
			}
		}
		return time;
	}

	public static String getWeekString(Context context, Calendar c) {
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 1:
			// 周日
			return context.getResources().getString(R.string.qn_sun_str);
		case 2:
			// 周一
			return context.getResources().getString(R.string.qn_monday_str);
		case 3:
			// 周二
			return context.getResources().getString(R.string.qn_tuesday_str);
		case 4:
			// 周三
			return context.getResources().getString(R.string.qn_wednesday_str);
		case 5:
			// 周四
			return context.getResources().getString(R.string.qn_thursday_str);
		case 6:
			// 周五
			return context.getResources().getString(R.string.qn_friday_str);
		case 7:
			// 周六
			return context.getResources().getString(R.string.qn_saturday_str);
		}
		return "";
	}

	public static String getWeekStringTwo(Context context, Calendar c) {
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			// 星期日
			return context.getResources().getString(R.string.qn_sun_str_two);
		case Calendar.MONDAY:
			// 星期一
			return context.getResources().getString(R.string.qn_monday_str_two);
		case Calendar.TUESDAY:
			// 星期二
			return context.getResources()
					.getString(R.string.qn_tuesday_str_two);
		case Calendar.WEDNESDAY:
			// 星期三
			return context.getResources().getString(
					R.string.qn_wednesday_str_two);
		case Calendar.THURSDAY:
			// 星期四
			return context.getResources().getString(
					R.string.qn_thursday_str_two);
		case Calendar.FRIDAY:
			// 星期五
			return context.getResources().getString(R.string.qn_friday_str_two);
		case Calendar.SATURDAY:
			// 星期六
			return context.getResources().getString(
					R.string.qn_saturday_str_two);
		}
		return "";
	}
	
	public static String getFirstdayofThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return String.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format((cal.getTime())));
	
	//	return cal.getTime();
	}
	
	public static String timeToNormal(String time) {
		if(time == null || time.length() <12){
			return "";
		}
		//20140102121212
		return time.substring(0, 4) + "-" + time.substring(4, 6) + "-" +time.substring(6, 8) + " " + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
//		return getDateTimeFormatString(StringToDate(time),FormatType.YYYYMMDDHHMMSS_2);
	
	}
	
	//转换时间格式
	public static String getDate(Context context, Long date) {
		if (date==0) {
			return "";
		}
		String startTime = String
				.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(date));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.SIMPLIFIED_CHINESE);
		SimpleDateFormat yeardf = new SimpleDateFormat("yyyy",
				Locale.SIMPLIFIED_CHINESE);

		Calendar cal = Calendar.getInstance();
		String yearStr = yeardf.format(cal.getTime());
		String todayStr = sdf.format(cal.getTime());
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
		String yesterday = sdf.format(cal.getTime());
		
		if (null == startTime || startTime.equals("")
				|| startTime.toUpperCase().equals("NULL")) {
			startTime = "";
		} else {
			if (startTime.startsWith(todayStr)) {
				startTime = startTime.substring(11, 16);
			} else if (startTime.startsWith(yesterday)) {
				startTime = context.getString(R.string.qn_yesterday);
			} else if (startTime.startsWith(yearStr)) {
				startTime = startTime.substring(5, 10);
			} else {
				startTime = startTime.substring(0, 10);
			}
		}
		return startTime;
	}

	
	//转换时间格式
	public static String getDateForString(Context context, String startTime) {
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.SIMPLIFIED_CHINESE);
		SimpleDateFormat yeardf = new SimpleDateFormat("yyyy",
				Locale.SIMPLIFIED_CHINESE);

		Calendar cal = Calendar.getInstance();
		String yearStr = yeardf.format(cal.getTime());
		String todayStr = sdf.format(cal.getTime());
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
		String yesterday = sdf.format(cal.getTime());
		
		if (null == startTime || startTime.equals("")
				|| startTime.toUpperCase().equals("NULL")) {
			startTime = "";
		} else {
			if (startTime.startsWith(todayStr)) {
				startTime = startTime.substring(11, 16);
			} else if (startTime.startsWith(yesterday)) {
				startTime = context.getString(R.string.qn_yesterday);
			} else if (startTime.startsWith(yearStr)) {
				startTime = startTime.substring(5, 10);
			} else {
				startTime = startTime.substring(0, 10);
			}
		}
		return startTime;
	}
	
	public static Date parseToDate(String dateStr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getFormattedDateString(Date date, String pattern) {
		if (null == date)
			return "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
}
