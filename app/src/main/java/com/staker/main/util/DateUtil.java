package com.staker.main.util;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @{# DateUtil.java Create on 2013-4-9
 * 
 *     class desc:
 * 
 *     <p>
 *     Copyright: Copyright(c) 2013
 *     </p>
 *     <p>
 *     Company: morelap
 *     </p>
 * @Version 1.0
 * @Author <a href="mailto:morelap@morelap.com">Morelap</a>
 * 
 * 
 */
public class DateUtil {
	public static final long Minute_MilliSecond = 60 * 1000;
	public static final long Hour_MilliSecond = Minute_MilliSecond * 60;
	/**
	 * milliseconds of a day
	 */
	public static final long Day_MilliSecond = Hour_MilliSecond * 24;
	/**
	 * milliseconds of a week
	 */
	public static final long Week_MilliSecond = Day_MilliSecond * 7;
	/**
	 * milliseconds of a month
	 */
	public static final long Month_MilliSecond = Week_MilliSecond * 30;
	/**
	 * yyyyMMdd
	 */
	public static final String Date_Default_Formate = "yyyyMMdd";
	/**
	 * yyyy-MM-dd HH:mm:ss 2010-05-11 17:22:26
	 */
	public static final String Date_Formate_All = "yyyy-MM-dd HH:mm:ss";
	/**
	 * dd/MM/yyyy, hh:mm
	 */
	public static final String DATE_FORMATE_TRANSACTION = "dd/MM/yyyy, hh:mm";
	/**
	 * MM/dd HH:mm
	 */
	public static final String DATE_FORMATE_DAY_HOUR_MINUTE = "MM/dd HH:mm";
	/**
	 * HH:mm
	 */
	public static final String DATE_FORMATE_HOUR_MINUTE = "HH:mm";
	public static final String DATE_FORMATE_HOUR_MINUTE_SECOND = "HH:mm:ss";

	public static SimpleDateFormat dateFormate = new SimpleDateFormat();

	/**
	 * 获取当前的字符串日期
	 * 
	 * @param splite
	 *            格式的分割线如 - 则获取的日期时间格式如下：
	 * @return 返回2013-07-19
	 */
	public static String getNowStringDate(String splite) {
		StringBuffer format = new StringBuffer();
		if (splite == null) {
			format.append(Date_Default_Formate);
		} else {
			format.append("yyyy").append(splite).append("MM").append(splite)
					.append("dd");
		}
		return (new SimpleDateFormat(format.toString())).format(new Date());
	}

	/**
	 * 获取当前的字符串日期时间
	 * 
	 * @param splite
	 *            格式的分割线如 - 则获取的日期时间格式如下：
	 * @return 返回2013-07-19 09:08:22
	 */
	public static String getNowStringDateTime(String splite) {
		StringBuffer format = new StringBuffer();
		if (splite == null) {
			format.append(Date_Default_Formate);
		} else {
			format.append("yyyy").append(splite).append("MM").append(splite)
					.append("dd").append(" HH:mm:ss");
		}
		return (new SimpleDateFormat(format.toString())).format(new Date());
	}

	/**
	 * 获取当前的字符串期时间
	 * 
	 *            格式的分割线如 - 则获取的日期时间格式如下：
	 * @return 返回09:08:22
	 */
	public static String getNowStringTime() {
		StringBuffer format = new StringBuffer(DATE_FORMATE_HOUR_MINUTE_SECOND);
		return (new SimpleDateFormat(format.toString())).format(new Date());

	}

	/**
	 * 获取昨天的日期
	 * 
	 * @return Date
	 */
	public static Date getYesterdayDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期的后面的一天 即日期+1
	 * 
	 * @param stringDate
	 *            格式为：2012-02-02
	 * @return Date
	 */
	public static Date getForwardDate(String stringDate) {
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		int day = Integer.parseInt(stringDate.substring(8, 10));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);

		c.add(Calendar.DATE, 1);// 在此日期的基础上+1
		return c.getTime();
	}

	/**
	 * 获取指定日期的后面的一天 即日期+1
	 * 
	 * @param stringDate
	 *            格式为：2012-02-02
	 * @return Date
	 */
	public static String getForwardStringDate(String stringDate, String splite) {
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		int day = Integer.parseInt(stringDate.substring(8, 10));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.DATE, 1);// 在此日期的基础上+1
		return getStringDateByDate(c.getTime(), splite);
	}


	/**
	 * 获取指定日期的前面面的一天 即日期-1
	 *
	 * @param stringDate
	 *            格式为：2012-02-02
	 * @return Date
	 */
	public static String getBackStringDate(String stringDate, String splite) {
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		int day = Integer.parseInt(stringDate.substring(8, 10));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.DATE, -1);// 在此日期的基础上-1
		return getStringDateByDate(c.getTime(), splite);
	}











	/**
	 * 根据传进来的日期date 和格式分割线返回一个字符串的日期
	 * 
	 * @param date
	 *            日期
	 * @param splite
	 *            日期格式的分割线 如：-
	 * @return 2013-07-19
	 */
	public static String getStringDateByDate(Date date, String splite) {
		StringBuffer format = new StringBuffer();
		if (splite == null) {
			format.append(Date_Default_Formate);
		} else {
			format.append("yyyy").append(splite).append("MM").append(splite)
					.append("dd");
		}
		if (date == null) {
			date = new Date();
		}
		return (new SimpleDateFormat(format.toString())).format(date);
	}

	/**
	 * 根据传进来的日期stringdate 和格式分割线返回一个字符串的日期
	 * 
	 * @param stringDate
	 *            日期
	 * @param splite
	 *            日期格式的分割线 如：-
	 * @return 2013-07-19
	 */
	public static String getStringDateByStringDate(String stringDate,
                                                   String splite) {
		Date date = getDateByStringTime(stringDate);
		return getStringDateByDate(date, splite);
	}

	/**
	 * 根据传进来的整数获取一个日期Date
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return Date
	 */
	public static Date getDateByIntegerDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	/**
	 * 根据传进来的字符串日期格式 获得一个date
	 * 
	 * @param stringTime
	 *            格式如：2013-02-13 中间的-可以为其他字符
	 * @return date
	 */
	public static Date getDateByStringTime(String stringTime) {
		if(stringTime==null||stringTime.length()<10){
			return getDateByIntegerDate(1990, 01, 01);
		}
		int year = Integer.parseInt(stringTime.substring(0, 4));
		int month = Integer.parseInt(stringTime.substring(5, 7));
		int day = Integer.parseInt(stringTime.substring(8, 10));
		return getDateByIntegerDate(year, month, day);
	}

	/**
	 * 获得指定日期 在1970年到现在的天数
	 * 
	 * @param date
	 * @return 天数
	 */
	public static long getDaysFrom1970(Date date) {
		String stringTime = DateUtil.getStringDateByDate(date, "-");
		int year = Integer.parseInt(stringTime.substring(0, 4));
		int month = Integer.parseInt(stringTime.substring(5, 7));
		int day = Integer.parseInt(stringTime.substring(8, 10));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		return (int) (c.getTimeInMillis() / 86400000);
	}

	/**
	 * 获得指定日期 在1970年到现在的天数
	 * 
	 * @param   月 日
	 * @return 天数
	 */
	public static long getDaysFrom1970(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		return (int) (c.getTimeInMillis() / 86400000);
	}

	/**
	 * 获得指定日期 在1970年到现在的天数
	 * 
	 * @param  格式2013-03-05 中间的-可以是其他符号 或者没有符号
	 * @param hasSplite 代表中间是否有符号，默认是有的，如果为false则日期的格式为：20130305
	 * @return 天数
	 */
	public static long getDaysFrom1970(String stringTime, boolean hasSplite) {
		int year,month,day;
		if(hasSplite){
			year = Integer.parseInt(stringTime.substring(0, 4));
			month = Integer.parseInt(stringTime.substring(5, 7));
			day = Integer.parseInt(stringTime.substring(8, 10));
		}else{
			year = Integer.parseInt(stringTime.substring(0, 4));
			month = Integer.parseInt(stringTime.substring(4, 6));
			day = Integer.parseInt(stringTime.substring(6, 8));
		}	
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		return (int) (c.getTimeInMillis() / 86400000);
	}
	/**
	 * 获得今年年初的第一天日期 如2013-01-01
	 * 
	 * @param splite
	 *            分隔符号
	 * @return 2013-01-01（字符串形式）
	 */
	public static String getYearStartString(String splite) {
		String sDate = getNowStringDate(splite);
		int year = Integer.parseInt(sDate.substring(0, 4));
		String yearStart = new StringBuffer().append(year).append(splite)
				.append("01").append(splite).append("01").toString();
		return yearStart;
	}

	/**
	 * 获得今年年初的第一天日期 如2013-01-01
	 * 
	 * @return 2013-01-01日期格式
	 */
	public static Date getYearStartDate() {
		String sDate = getNowStringDate("-");
		int year = Integer.parseInt(sDate.substring(0, 4));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 获得上个周末的 字符串日期， 指星期日 
	 *  @param splite
	 *            分隔符号 -
	 * @return String 格式：2013-06-22
	 */
	public static String getLastWeekStringDate(String splite) {
		String stringDate=getNowStringDate("-");
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		int day = Integer.parseInt(stringDate.substring(8, 10));
		int sundayPlus=getSundayPlus();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.DATE, -sundayPlus);// 在此日期的基础上减去sundayPlus就是上个周末的日期
		return getStringDateByDate(c.getTime(), splite);
	}

	/**
	 * 获得上个周末的日期，date格式
	 * 
	 * @return Date
	 */
	public static Date getLastWeekDate() {
		String stringDate=getNowStringDate("-");
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		int day = Integer.parseInt(stringDate.substring(8, 10));
		int sundayPlus=getSundayPlus();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.DATE, -sundayPlus);// 在此日期的基础上减去sundayPlus就是上个周末的日期
		return c.getTime();

	}

	/**
	 * 获得上个月末的 字符串日期
	 * @return String
	 */
	public static String getLastMonthStringDate(String splite) {
		String stringDate=getNowStringDate("-");
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		int day = Integer.parseInt(stringDate.substring(8, 10));
		int monthPlus=getMonthPlus();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.DATE, -monthPlus);// 在此日期的基础上减去sundayPlus就是上个周末的日期
		return getStringDateByDate(c.getTime(), splite);
	}

	/**
	 * 获得上个月末的日期，date格式
	 * 
	 * @return Date
	 */
	public static Date getLastMonthDate() {
		String stringDate=getNowStringDate("-");
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		int day = Integer.parseInt(stringDate.substring(8, 10));
		int monthPlus=getMonthPlus();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.DATE, -monthPlus);// 在此日期的基础上减去sundayPlus就是上个周末的日期
		return c.getTime();

	}
	
	
	/**
	 * 获得上个季度末的 字符串日期
	 * @return String 2012-03-30
	 */
	public static String getLastQuarterStringDate(String splite) {
		String stringDate=getNowStringDate("-");
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		switch (month) {
		case 1:
		case 2:
		case 3:
			month=12;
			year--;
			break;
		case 4:
		case 5:
		case 6:
			month=3;
			break;
		case 7:
		case 8:
		case 9:
			month=6;
			break;
		case 10:
		case 11:
		case 12:
			month=9;
			break;
		default:
			break;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);//这里月份本来是要-1的，所以这里就相当于月份month+1，
		c.set(Calendar.DAY_OF_MONTH, 1);//此时的日期刚好为month+1月份的一号
		c.add(Calendar.DATE, -1);// 在1号的基础上减去一天，就是month月末的日期
		return getStringDateByDate(c.getTime(), splite);
	}

	/**
	 * 获得上个季度末的日期，date格式 
	 * @return Date
	 */
	public static Date getLastQuarterDate() {
		String stringDate=getNowStringDate("-");
		int year = Integer.parseInt(stringDate.substring(0, 4));
		int month = Integer.parseInt(stringDate.substring(5, 7));
		switch (month) {
		case 1:
		case 2:
		case 3:
			month=12;
			year--;
			break;
		case 4:
		case 5:
		case 6:
			month=3;
			break;
		case 7:
		case 8:
		case 9:
			month=6;
			break;
		case 10:
		case 11:
		case 12:
			month=9;
			break;
		default:
			break;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);//这里月份本来是要-1的，所以这里就相当于月份month+1，
		c.set(Calendar.DAY_OF_MONTH, 1);//此时的日期刚好为month+1月份的一号
		c.add(Calendar.DATE, -1);// 在1号的基础上减去一天，就是month月末的日期
		return c.getTime();
	}
	/**
	 * 获得指定月份的，月末日期
	 * @param month 月
	 * @return String
	 */
	public static String getMonthEndStringDateByMonth(int month, String splite){
		String stringDate=getNowStringDate("-");
		int year = Integer.parseInt(stringDate.substring(0, 4));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);//这里月份本来是要-1的，所以这里就相当于月份month+1，
		c.set(Calendar.DAY_OF_MONTH, 1);//此时的日期刚好为month+1月份的一号
		c.add(Calendar.DATE, -1);// 在1号的基础上减去一天，就是month月末的日期
		return getStringDateByDate(c.getTime(), splite);
	}


	/*
	 获得今天是星期几
	 返回  1 2 3 4 5 6 7
	 */
	public static int getWeekDay(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得当前日期与上周日相差的天数 按照外国的理解：星期日是第一天，星期一是第二天. 中国：星期一是第一天.
	 * 
	 * @return int今天和上周日的相差日期
	 */
	public static int getSundayPlus() {
		Calendar c = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一,作为第一天所以这里减1
		if(dayOfWeek==0){
			dayOfWeek=7;
		}
		return dayOfWeek;
	}

	/**
	 * 获取指定日期加多少天之后的日期
	 * @param date 指定日期
	 * @param days
	 * @return
	 */
	public static String getPlusDayDate(String date,int days){
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8, 10));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);//这里月份本来是要-1的，所以这里就相当于月份month+1，
		calendar.set(Calendar.DAY_OF_MONTH, day);//此时的日期刚好为month+1月份的一号
		calendar.add(Calendar.DATE, days);
		return getStringDateByDate(calendar.getTime(), "-");
	}


	/**
	 * 获得当前日期与上个月末日期的相差天数
	 * 
	 * @return int 今天和上月末的相差日期
	 */
	public static int getMonthPlus() {
		Calendar c = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		return dayOfMonth;
	}
	/**
	 * 获得两个时间相差的时间间隔，以分钟来计算
	 * @param time01   格式为：2012-02-02 08:08:20
	 * @param time02  格式为：2012-02-02 08:08:20
	 * @return 相差的分钟数
	 */
	public static long getTwoTimeInterval(String time01, String time02){
		long t01=stringTimeToMilliseconds(time01);
		long t02=stringTimeToMilliseconds(time02);	
		return Math.abs((t01-t02)/60000);
	}

	/**
	 * 获取两个日期之间相差的天数
	 * @param date01 格式为：2012-02-02
	 * @param date02 格式为：2012-02-02
	 * @return
	 */
	public static long getTwoTimeIntervalDays(String date01, String date02){
		Log.e("staker","test 01");
		long t01=stringTimeToMilliseconds(date01+" 00:00:00");
		Log.e("staker","test 02");
		long t02=stringTimeToMilliseconds(date02+" 00:00:00");
		Log.e("staker","test 03");
		return Math.abs((t01-t02)/86400000);
	}
	/**
	 * 把一个字符串日期 转换成毫秒数 
	 * @param time  格式为：2012-02-02 08:08:20
	 * @return 毫秒数
	 */
	public static long stringTimeToMilliseconds(String time){
		int year = Integer.parseInt(time.substring(0, 4));
		int month = Integer.parseInt(time.substring(5, 7));
		int day = Integer.parseInt(time.substring(8, 10));
		int hour= Integer.parseInt(time.substring(11, 13));
		int minus= Integer.parseInt(time.substring(14, 16));
		int second= Integer.parseInt(time.substring(17, 19));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.MINUTE, minus);
		c.add(Calendar.SECOND, second);
		return c.getTimeInMillis();		
	}
	// java.util.Date 是 java.sql.Date 的父类
	// java.sql.Date转为java.util.Date
	// java.sql.Date date=new java.sql.Date();
	// java.util.Date d=new java.util.Date (date.getTime());
	//

	//
	// java.util.Date转为java.sql.Date
	// java.util.Date utilDate=new Date();
	// java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());

	
	/**
	 * 转换成描述类型的时间 如：10分钟以前
	 * 
	 * @param time
	 *            格式：2013-07-19 09:08:22
	 * @return String
	 */
	public static String toDescribeTime(String time) {
		if(time==null){
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		long time01=calendar.getTimeInMillis()/1000;
		long time02=getMilliSecondByTime(time)/1000;
		long seconds=time01-time02;
		if(seconds<0){
			return "刚刚";
		}else if (seconds < 60) {
			return seconds + "秒前";
		} else if (seconds/60 < 60) {
			return seconds/60 + "分钟前";
		} else if (seconds /60/60< 24) {
			return seconds/60/60 + "小时前";
		} else if (seconds/60/60/24 < 30) {
			return seconds/60/60/24 + "天前";
		}
//		else if (minutes < 12 * 30 * 24 * 60 * 60) {
//			return (currentDate.getMonth() - month) + "月前";
//		} 
		else {
			return time;
		}

	}
	/**
	 * 转换成描述类型的时间 如：10分钟以前
	 * @param longTime 毫秒数
	 * @return String
	 */
	public static String toDescribeTime(long longTime) {
		Calendar calendar = Calendar.getInstance();
		long time01=calendar.getTimeInMillis()/1000;
		long time02=longTime/1000;
		long seconds=time01-time02;
		if (seconds < 60) {
			return "刚刚";
		} else if (seconds/60 < 60) {
			return seconds/60 + "分钟前";
		} else if (seconds /60/60< 24) {
			return seconds/60/60 + "小时前";
		} else if (seconds/60/60/24 < 30) {
			return seconds/60/60/24 + "天前";
		}else {
			return toTime(longTime).substring(0,10);
		}

	}
	/**
	 * 获得一个时间的转化后的毫秒数
	 * @param time
	 * 格式：2013-07-19 09:08:22
	 * @return long
	 */
	public static long getMilliSecondByTime(String time){
		if(time==null){
			return System.currentTimeMillis();
		}
		try {
			int year = Integer.parseInt(time.substring(0, 4));
			int month = Integer.parseInt(time.substring(5, 7));
			int day = Integer.parseInt(time.substring(8, 10));

			int hour = Integer.parseInt(time.substring(11, 13));
			int minute = Integer.parseInt(time.substring(14, 16));
			int second = Integer.parseInt(time.substring(17, 19));
			
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month - 1);
			c.set(Calendar.DAY_OF_MONTH, day);
			
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.set(Calendar.MINUTE, minute);
			c.set(Calendar.SECOND, second);
			return c.getTimeInMillis();
		} catch (Exception e) {
		}
		return System.currentTimeMillis();
	}
	/**
	 * 
	 * @param milliseconds
	 * @return the time formated by "2012-12-08 08:06:33"
	 */
	public static String toTime(long milliseconds) {
		return toTime(new Date(milliseconds), Date_Formate_All);
	}

	/**
	 * 
	 * @param milliseconds
	 * @param pattern
	 * @return the time formated
	 */
	public static String toTime(long milliseconds, String pattern) {
		return toTime(new Date(milliseconds), pattern);
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toTime(Date date, String pattern) {
		if (TextUtils.isEmpty(pattern)) {
			pattern = Date_Default_Formate;
		}
		dateFormate.applyPattern(pattern);
		if (date == null) {
			date = new Date();
		}
		try {
			return dateFormate.format(date);
		} catch (Exception e) {
			return "";
		}
	}
}
