package com.gwghk.ims.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "hh:mm:ss";

    private SimpleDateFormat sdf = null ;
	private TimeZone timeZone = null;

    public DateUtil(String GMT){
		sdf = new SimpleDateFormat();
		timeZone = TimeZone.getTimeZone(GMT);
		sdf.setTimeZone(timeZone);
	}
	
	
	public static DateUtil getInstance(Integer timezone){
		String GMT = "";
		if(timezone == null){
			GMT = "GMT+0";
		}else if(timezone >=0){
			GMT = "GMT+" + timezone;
		}else{
			GMT = "GMT" + timezone;
		}
		
		DateUtil dateUtil = new DateUtil(GMT);
		return dateUtil;
	}
 
	
    public static String getCurrentDate() {
        return formatDateToString(new Date(), "yyyy-MM-dd");
    }

    public static String getCurrentTime() {
        return formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
    
    public static String formatDateToString(Date date, String format, Locale locale) {
        if (date == null) {
            return null;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(format,locale);
        return dateFormat.format(date);
    }
    
	public Date parseDateFormat(String strDate, String pattern) {
		Date date = null;
		try {
			sdf.applyPattern(pattern);
			date = sdf.parse(strDate);
		} catch (Exception e) {
		}
		return date;
	}

    public static String formatDateToString(Date date) {
        return formatDateToString(date, "yyyy-MM-dd");
    }

    public static String getDateSecondFormat(java.util.Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return getDateFormat(date, pattern);
    }

    public static String getDateFormat(java.util.Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    public static String formatDateToString(long millis, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(millis);
        return dateFormat.format(c.getTime());
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

    /**
     * 
     * 方法用途: 对日期进行小时操作<br>
     * 实现步骤: <br>
     * 
     * @param date
     * @param hh
     * @return
     */
    public static Date operationalAddHour(Date date, int hh) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.HOUR_OF_DAY, hh);
        return c1.getTime();
    }

    /**
     * 
     * 方法用途: 对日期进行分钟操作<br>
     * 实现步骤: <br>
     * 
     * @param date
     * @param mm
     * @return
     */
    public static Date operationalAddMinute(Date date, int mm) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.MINUTE, mm);
        return c1.getTime();
    }

    public static Date stringToDate(String date) {
        return stringToDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取当天的前?天开始时间,精确到秒
     * 
     * @param day 需要设置前?天的天数
     * @return
     */
    public static Date getCurrentDateStartTimeByDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当天的前一天开始时间,精确到秒
     */
    public static Date getCurrentDateBeforeStartTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当天的前一天结束时间,精确到秒
     */
    public static Date getCurrentDateBeforeEndTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当天的开始时间,精确到秒
     */
    public static Date getCurrentDateStartTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间,精确到秒
     */
    public static Date getCurrentDateEndTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当月的开始时间,精确到秒
     */
    public static Date getCurrentMonthStartTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置当月的第1天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当月的结束时间,精确到秒
     */
    public static Date getCurrentMonthEndTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));// 设置当月的最后一天
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当年的开始时间,精确到秒
     */
    public static Date getCurrentYearStartTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH));// 设置为1月
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置当月的第1天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当年的结束时间,精确到秒
     */
    public static Date getCurrentYearEndTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH) + 11);// 设置为12月
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));// 设置当月的最后一天
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 根据日期获取当天开始时间,精确到秒
     */
    public static Date getStartTimeByDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        date += " 00:00:00";
        return stringToDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据日期获取当天结束时间,精确到秒
     */
    public static Date getEndTimeByDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        date += " 23:59:59";
        return stringToDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 功能：获取yyyyMMddHHmmss格式字符串
     */
    public final static String toYyyymmddHhmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public final static String toYyyymmddHhmmss(Date aDate) {
        if (aDate == null) {
            return "";
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(aDate);
        int nYear = cal.get(Calendar.YEAR);
        int nMonth = cal.get(Calendar.MONTH);
        nMonth++;
        int nDay = cal.get(Calendar.DAY_OF_MONTH);
        int nHour = cal.get(Calendar.HOUR_OF_DAY);
        int nMInute = cal.get(Calendar.MINUTE);
        int nSeconf = cal.get(Calendar.SECOND);

        StringBuilder sb = new StringBuilder();
        sb.append(nYear);
        sb.append('-');
        if (nMonth < 10) {
            sb.append('0');
        }
        sb.append(nMonth);
        sb.append('-');
        if (nDay < 10) {
            sb.append('0');
        }
        sb.append(nDay);

        sb.append(' ');

        if (nHour < 10) {
            sb.append('0');
        }
        sb.append(nHour);
        sb.append(':');
        if (nMInute < 10) {
            sb.append('0');
        }
        sb.append(nMInute);
        sb.append(':');
        if (nSeconf < 10) {
            sb.append('0');
        }
        sb.append(nSeconf);

        return sb.toString();
    }

    public final static String toYyyymmdd(Date aDate) {
        if (aDate == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(aDate);
    }
    
    
	/**
	 * 周一是1,依次加1.
	 */
	public static int getDayOfWeekFromMonday(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(dayWeek==0)
			dayWeek = 7;
		return dayWeek;
	}
	
	
	public static Date getDatePreAfterDayFormat(int days){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		cal.add(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	
	/**
	 * 根据date，获取前day天，小时+hour
	 * @param date
	 * @param day
	 * @param hour
	 * @return
	 */
	public static java.util.Date getPreviousNDay(
			java.util.Date date, int day, int hour) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.add(Calendar.DATE, day);
		gc.add(Calendar.HOUR, hour);
		
		return gc.getTime();
	}
	
	public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }
	
	/**
	 * 根据date，加N天
	 * @param date
	 * @param day 天数
	 * @return Date
	 */
	public static Date addDay(Date date, int day) {
		if (null == date) {
			return null;
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 根据date，加N小时
	 * @param date
	 * @param hours
	 * @return Date
	 */
	public static Date addHours(Date date, int hours) {
		if (null == date) {
			return null;
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}
	
	/**
	 * 根据date，加N天
	 * @param date
	 * @param hours
	 * @return Date
	 */
	public static Date addDays(Date date, int day) {
		if (null == date) {
			return null;
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}
	
	/**
	 * 根据date，加N个月
	 * @param date
	 * @param hours
	 * @return Date
	 */
	public static Date addMonth(Date date, int month) {
		if (null == date) {
			return null;
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	/**
	 * 根据date，加N分钟
	 * @param date
	 * @param minutes
	 * @return Date
	 */
	public static Date addMinutes(Date date, int minutes) {
		if (null == date) {
			return null;
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}
	
	/**
	 * 根据date，加N秒
	 * @param date
	 * @param minutes
	 * @return Date
	 */
	public static Date addSeconds(Date date, int seconds) {
		if (null == date) {
			return null;
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
	
    public static void main(String[] args) {
    	/*
        DateUtil.getCurrentDateStartTimeByDay(30);
        DateUtil.getCurrentDateBeforeStartTime();
        DateUtil.getCurrentDateBeforeEndTime();
        DateUtil.getCurrentDateStartTime();
        DateUtil.getCurrentDateEndTime();
        DateUtil.getCurrentMonthStartTime();
        DateUtil.getCurrentMonthEndTime();
        DateUtil.getCurrentYearStartTime();
        DateUtil.getCurrentYearEndTime();

        String str1 = "2016-06-16 11:30:30";
        String str2 = "2016-06-16 11:40:30";
        Date date1 = DateUtil.stringToDate(str1, "yyyy-MM-dd HH:mm:ss");
        Date date2 = DateUtil.stringToDate(str2, "yyyy-MM-dd HH:mm:ss");
        if (date1.getTime() < date2.getTime()) {
            System.out.println("date2时间更大");
        }*/
    	
    	
//    	System.out.println(formatDateToString(stringToDate("2016-12-06"), "d/M/y"));
    	System.out.println(formatDateToString(new Date(),"d/MMM/y",Locale.ENGLISH));
    }

}
