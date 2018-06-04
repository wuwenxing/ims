package com.gwghk.ims.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 摘要：活动时间工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年6月10日
 */
public class ActDateUtil {
	private static final Logger logger = LoggerFactory.getLogger(ActDateUtil.class);

	private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30,
			31, 31, 30, 31, 30, 31 };
	private static SimpleDateFormat sdf = new SimpleDateFormat();

	public static synchronized Calendar getCalendar() {
		return GregorianCalendar.getInstance();
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateMilliFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMilliFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMilliFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMilliFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarMilliFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateMilliFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateSecondFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateSecondFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarSecondFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateSecondFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * 功能：获取yyyyMMddHHmmss格式字符串
	 */
	public final static String toYyyymmddHhmmss(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMinuteFormat(cal);
	}

	/**
	 * 
	 * @param date
	 * @return date
	 */
	public static synchronized java.util.Date getPreviousDay(java.util.Date date) {		
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -1);
		return gc.getTime();
	}
	
	public static synchronized String getYear() {		
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();		
		String year = Integer.toString(gc.get(Calendar.YEAR));
		return year;
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarMinuteFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateMinuteFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateDayFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateDayFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateDayFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 将日期转为yyyy-MM-dd 字符串
	 */
	public static synchronized String getDateDayFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateFileFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateFileFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateFileFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateFileFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFileFormat(String strDate) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFileFormat(String strDate) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateW3CFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateW3CFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateW3CFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateW3CFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param cal
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Calendar cal,
			String pattern) {
		return getDateFormat(cal.getTime(), pattern);
	}

	/**
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Date date,
			String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFormat(String strDate,
			String pattern) {
		synchronized (sdf) {
			Calendar cal = null;
			sdf.applyPattern(pattern);
			try {
				sdf.parse(strDate);
				cal = sdf.getCalendar();
			} catch (Exception e) {
			}
			return cal;
		}
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFormat(String strDate,
			String pattern) {
		synchronized (sdf) {
			Date date = null;
			sdf.applyPattern(pattern);
			try {
				date = sdf.parse(strDate);
			} catch (Exception e) {
			}
			return date;
		}
	}

	public static synchronized int getLastDayOfMonth(int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear()) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	public static synchronized int getLastDayOfMonth(int year, int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear(year)) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	public static synchronized int getDayOfWeek(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static synchronized boolean isLeapYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static synchronized boolean isLeapYear(int year) {
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	public static synchronized boolean isLeapYear(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static synchronized boolean isLeapYear(java.util.Calendar gc) {
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static synchronized java.util.Date getPreviousWeekDay(
			java.util.Date date) {
			GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
			gc.setTime(date);
			return getPreviousWeekDay(gc);
	}

	public static synchronized java.util.Date getPreviousWeekDay(
			java.util.Calendar gc) {
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
			case (Calendar.MONDAY):
				gc.add(Calendar.DATE, -3);
				break;
			case (Calendar.SUNDAY):
				gc.add(Calendar.DATE, -2);
				break;
			default:
				gc.add(Calendar.DATE, -1);
				break;
		}
		return gc.getTime();
	}

	public static synchronized java.util.Date getNextWeekDay(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 2);
			break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextWeekDay(
			java.util.Calendar gc) {
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 2);
			break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc;
	}

	public static synchronized boolean isTodaySaturday(Date today) {
		java.util.Calendar gc = Calendar.getInstance();
		gc.setTime(today);
		if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}
		return false;
	}

	public static synchronized boolean isTodaySaturday() {
		java.util.Calendar gc = Calendar.getInstance();
		if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}
		return false;
	}

	public static synchronized java.util.Date getLastDayOfNextMonth(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextMonth(gc.getTime()));
		gc.setTime(getLastDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized java.util.Date getLastDayOfNextWeek(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextWeek(gc.getTime()));
		gc.setTime(getLastDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized java.util.Date getFirstDayOfNextMonth(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextMonth(gc.getTime()));
		gc.setTime(getFirstDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfNextMonth(
			java.util.Calendar gc) {
		gc.setTime(getNextMonth(gc.getTime()));
		gc.setTime(getFirstDayOfMonth(gc.getTime()));
		return gc;
	}

	public static synchronized java.util.Date getFirstDayOfNextWeek(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextWeek(gc.getTime()));
		gc.setTime(getFirstDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfNextWeek(
			java.util.Calendar gc) {
		gc.setTime(getNextWeek(gc.getTime()));
		gc.setTime(getFirstDayOfWeek(gc.getTime()));
		return gc;
	}

	public static synchronized java.util.Date getLastMonth(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, -1);
		return gc.getTime();
	}

	public static synchronized java.util.Date getLastMonth(java.util.Date date,
			int n) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, -n);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getLastMonth(
			java.util.Calendar gc) {
		gc.add(Calendar.MONTH, -1);
		return gc;
	}

	public static synchronized java.util.Date getNextMonth(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, 1);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextMonth(
			java.util.Calendar gc) {
		gc.add(Calendar.MONTH, 1);
		return gc;
	}

	public static synchronized java.util.Date getNextDay(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextDay(java.util.Calendar gc) {
		gc.add(Calendar.DATE, 1);
		return gc;
	}

	public static synchronized java.util.Date getPreviousWeek(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -7);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getPreviousWeek(
			java.util.Calendar gc) {
		gc.add(Calendar.DATE, -7);
		return gc;
	}

	public static synchronized java.util.Date getNextWeek(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 7);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextWeek(
			java.util.Calendar gc) {
		gc.add(Calendar.DATE, 7);
		return gc;
	}

	public static synchronized java.util.Date getLastDayOfWeek(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 5);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, 4);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, 2);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 1);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 0);
			break;
		}
		return gc.getTime();
	}

	public static synchronized java.util.Date getFirstDayOfWeek(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfWeek(
			java.util.Calendar gc) {
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		}
		return gc;
	}

	public static synchronized java.util.Date getLastDayOfMonth(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		}
		// �������
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getLastDayOfMonth(
			java.util.Calendar gc) {
		switch (gc.get(Calendar.MONTH)) {
			case 0:
				gc.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 1:
				gc.set(Calendar.DAY_OF_MONTH, 28);
				break;
			case 2:
				gc.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 3:
				gc.set(Calendar.DAY_OF_MONTH, 30);
				break;
			case 4:
				gc.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 5:
				gc.set(Calendar.DAY_OF_MONTH, 30);
				break;
			case 6:
				gc.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 7:
				gc.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 8:
				gc.set(Calendar.DAY_OF_MONTH, 30);
				break;
			case 9:
				gc.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 10:
				gc.set(Calendar.DAY_OF_MONTH, 30);
				break;
			case 11:
				gc.set(Calendar.DAY_OF_MONTH, 31);
				break;
		}
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc;
	}

	public static synchronized java.util.Date getFirstDayOfMonth(
			java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfMonth(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.����Ϊ1��
		 */
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc;
	}

	public static synchronized String toOraString(Date theDate, boolean hasTime) {
		DateFormat theFormat;
		if (hasTime) {
			theFormat = getOraDateTimeFormat();
		} else {
			theFormat = getOraDateFormat();
		}
		return toString(theDate, theFormat);
	}

	public static synchronized String toString(Date theDate, boolean hasTime) {
		DateFormat theFormat;
		if (hasTime) {
			theFormat = getDateTimeFormat();
		} else {
			theFormat = getDateFormat();
		}
		return toString(theDate, theFormat);
	}

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"MM/dd/yyyy");
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm");
	@SuppressWarnings("unused")
	private static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm:ss");
	
	private static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMdd");
	private static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmm");
	
	@SuppressWarnings("unused")
	private static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static synchronized DateFormat getDateFormat() {
		SimpleDateFormat theDateFormat = (SimpleDateFormat) DATE_FORMAT.clone();
		theDateFormat.setLenient(false);
		return theDateFormat;
	}

	public static synchronized DateFormat getDateTimeFormat() {
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DATE_TIME_FORMAT
				.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	public static synchronized DateFormat getOraDateFormat() {
		SimpleDateFormat theDateFormat = (SimpleDateFormat) ORA_DATE_FORMAT
				.clone();
		theDateFormat.setLenient(false);
		return theDateFormat;
	}

	public static synchronized DateFormat getOraDateTimeFormat() {
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) ORA_DATE_TIME_FORMAT.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	public static synchronized java.util.Date getPreviousNDay(
			java.util.Date date, int n) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, n);
		return gc.getTime();
	}

	public static synchronized String toString(Date theDate,
			DateFormat theDateFormat) {
		if (theDate == null)
			return "";
		return theDateFormat.format(theDate);
	}

	public static String getDateByMillTime(long millSeconds){
		Calendar gc = Calendar.getInstance(); 
		gc.setTimeInMillis(millSeconds * 1000-8*3600*1000);
		
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(gc.getTime());
	}
   
	/**
	 * 将秒转化为yyyy-MM-dd HH:mm:ss字符串
	 * @param millSeconds 秒
	 */
	public static String formatSecondsTo14s(long millSeconds){
		Calendar gc = Calendar.getInstance(); 
		gc.setTimeInMillis(millSeconds * 1000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(gc.getTime());
	}
	
	/**
	 * 将秒转化为yyyy-MM-dd字符串
	 * @param millSeconds 秒
	 */
	public static String formatSecondsTo8s(long millSeconds){
		Calendar gc = Calendar.getInstance(); 
		gc.setTimeInMillis(millSeconds * 1000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(gc.getTime());
	}
	
	public static Date getFirstDayOfThisMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), calendar
				.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}
	
	

	public static Date getFirstDayOfNextMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(GregorianCalendar.MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), calendar
				.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getYesterday() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), calendar
				.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getLastSecondOfToday() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), calendar
				.get(GregorianCalendar.DATE), 23, 59, 59);
		return calendar.getTime();
	}

	public static Date getMidDayOfThisMonthExptSunday() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), 15, 0, 0, 0);
		int days = calendar.get(Calendar.DAY_OF_WEEK);
		if (days == 1) {
			calendar.add(Calendar.DATE, 1);
		}
		Date midMonth = calendar.getTime();
		Date clearingBeginDate = getFirstDayOfThisMonth();
		if (today.after(midMonth)) {
			clearingBeginDate = midMonth;
		}
		return clearingBeginDate;
	}

	public static Date getMaxDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(2999, 1, 1, 0, 0, 0);
		return calendar.getTime();
	}

	public static Timestamp now() {
		Calendar currDate = Calendar.getInstance();
		return new Timestamp((currDate.getTime()).getTime());
	}

	public static String getTimePast(long beginAt) {
		long completeAt = System.currentTimeMillis();
		long interval = completeAt - beginAt;
		long second = interval / 1000;
		long minute = 0;
		long hour = 0;
		String timeStr = second + "s";
		if (second >= 60) {
			minute = second / 60;
			second = second % 60;
			timeStr = minute + "m " + second + "s";
		}
		if (minute >= 60) {
			hour = minute / 60;
			minute = minute % 60;
			timeStr = hour + "h " + minute + "m " + second + "s";
		}

		return timeStr;
	}
	
	public static Date getLastSatOfThisMonth() {
		Calendar cal = Calendar.getInstance();		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		cal.add(Calendar.DAY_OF_YEAR, -1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		while (dayOfWeek != Calendar.SATURDAY){
			cal.add(Calendar.DAY_OF_YEAR, -1);
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);			
		}
		
		return cal.getTime();
	}
	
	//Time A 
	public static Date get3DayBeforeLastSatOfThisMonth() {
		Calendar cal = Calendar.getInstance();		
		cal.setTime(getLastSatOfThisMonth());
		cal.add(Calendar.DAY_OF_YEAR, -3);
		return cal.getTime();
	}
	
	public static Date getLastSatOfLastMonth() {
		Calendar cal = Calendar.getInstance();		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		cal.add(Calendar.DAY_OF_YEAR, -1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		while (dayOfWeek != Calendar.SATURDAY){
			cal.add(Calendar.DAY_OF_YEAR, -1);
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);			
		}
		
		return cal.getTime();
	}
	
	public static Double roundDouble(double val, int precision) {   
        Double ret = null;   
        try {
            double factor = Math.pow(10, precision);   
            ret = Math.floor(val * factor + 0.5) / factor;   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
  
        return ret;   
    }
	
	public final static String toYyMmdd(Date aDate){
		if (aDate == null)
			return "";
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		StringBuilder sb = new StringBuilder();
		int nYear = cal.get(Calendar.YEAR);
		nYear = nYear % 100;
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);
		if (nYear < 10)
			sb.append('0');
		sb.append(nYear);
		if (nMonth < 10)
			sb.append('0');
		sb.append(nMonth);
		if (nDay < 10)
			sb.append('0');
		sb.append(nDay);
		return sb.toString();
	}
	
	public final static String toYyyymmddHhmmss(Date aDate){
		if (aDate == null)
			return "";
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		int nYear = cal.get(Calendar.YEAR);
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);
		int nHour = cal.get(Calendar.HOUR_OF_DAY);
		int nMInute = cal.get(Calendar.MINUTE);
		int nSeconf= cal.get(Calendar.SECOND);

		StringBuilder sb = new StringBuilder();
		sb.append(nYear);
		sb.append('-');
		if (nMonth < 10)
			sb.append('0');
		sb.append(nMonth);
		sb.append('-');
		if (nDay < 10)
			sb.append('0');
		sb.append(nDay);

		sb.append(' ');
		
		if (nHour < 10)
			sb.append('0');
		sb.append(nHour);
		sb.append(':');
		if (nMInute < 10)
			sb.append('0');
		sb.append(nMInute);
		sb.append(':');
		if (nSeconf < 10)
			sb.append('0');
		sb.append(nSeconf);
		
		return sb.toString();
	}
	
	/**
	 * 功能: 获取当前时间的秒数
	 * @return  当前时间的秒数
	 */
	public static String time(){
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 功能：获取相对当前时间的N天
	 * @param  n 如果是前几个小时，n传入负数，如果是后几个小时，n传入正数
	 */
	public static String getPreNDay(int n){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DAY_OF_MONTH, n);
	    return sdf.format(cal.getTime());
	}
	
	/**
	 * 功能：获取当前所在月的第一天
	 */
	public static String getfirstDayOfMonth(String str){
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(parseDateDayFormat(str));
	    cal.set(Calendar.DAY_OF_MONTH, 1);//本月第一天
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.format(cal.getTime());
	}
	
	/**
	 * 功能：比较两个时间的大小
	 * @param date1   
	 * @param date2
	 */
	public static int compareToDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 0;
            } else if (dt1.getTime() < dt2.getTime()) {
            	return 1;
            }else{
            	return 2;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return 3;
        }
	}
	
    
    /**
     * 功能：获取某段日期的所有日期
     * @param begin 开始日期
     * @param end   结束日期
     */
    public static String[] getAllDay(String begin,String end){
    	try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            Date dBegin = sdf.parse(begin);  
            Date dEnd = sdf.parse(end);  
            List<Date> resultList = findDates(dBegin, dEnd);
            if(resultList != null && resultList.size() > 0){
            	String[] result = new String[resultList.size()];
            	for (int i= 0;i<resultList.size();i++) {
            		result[i] = sdf.format(resultList.get(i));
        	    }
            	return result;
            }
            return null;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * 功能：获取某段日期的所有日期
     * @param begin 开始日期
     * @param end   结束日期
     */
    public static List<Date> findDates(Date begin,Date end) {  
        List<Date> lDate = new ArrayList<Date>();  
        lDate.add(begin);  
       
        Calendar calBegin = Calendar.getInstance();  
        calBegin.setTime(begin);  
        Calendar calEnd = Calendar.getInstance();  
        calEnd.setTime(end);  
        
        while(end.after(calBegin.getTime())) {  // 测试此日期是否在指定日期之后    
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量    
            calBegin.add(Calendar.DAY_OF_MONTH, 1);  
            lDate.add(calBegin.getTime());  
        }  
        return lDate;  
    }
    
    public static Date stringToDate(String date, String format) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat(format);
        Date day = null;
        try {
            day = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
	
	public static void main(String args[]) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1);
			System.out.println(isTodaySaturday(calendar.getTime()));
			System.out.println(getYear());
			System.out.println(getDateByMillTime(1379174340));
			System.out.println(Calendar.getInstance().getTimeInMillis() / 1000);
			System.out.println(time());
			System.out.println(compareToDate(getDateSecondFormat(Calendar.getInstance()),"2014-04-26 23:59:59"));
			System.out.println(ActDateUtil.parseDateFormat("2018-03-10"+" 23:59:59"));
		} catch (Exception e) {
			logger.error("error", e);
		}
	}
}
