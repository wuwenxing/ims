package com.gwghk.ims.marketingtool.util;

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
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }
    
    /**
     * 获取当天的前?天结束时间,精确到秒
     * 
     * @param day 需要设置前?天的天数
     * @return
     */
    public static Date getCurrentDateEndTimeByDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
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
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
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
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当天的开始时间,精确到秒
     */
    public static Date getCurrentDateStartTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间,精确到秒
     */
    public static Date getCurrentDateEndTime() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
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
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
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
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
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
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
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
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
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

	public static Date getBeginTimeOfDay(java.util.Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(GregorianCalendar.YEAR), cal
				.get(GregorianCalendar.MONTH), cal
				.get(GregorianCalendar.DATE), 0, 0, 0);
		return cal.getTime();
	}
	
	public static Date getEndTimeOfDay(java.util.Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(GregorianCalendar.YEAR), cal
				.get(GregorianCalendar.MONTH), cal
				.get(GregorianCalendar.DATE), 23, 59, 59);
		return cal.getTime();
	}
	
	public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
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


	/**
	 * 格式化GMT+0時間
	 * 
	 * @param timetype
	 * @param dateString
	 * @return
	 */
	public static String getNewTimeForGMT(String timetype, String dateString, String platform) {
		Date date = null;
		String nowTime = "";
		Calendar cal = Calendar.getInstance();// 默認GMT+0
		cal.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			cal.setTime(formatter.parse(dateString));
			Calendar day = Calendar.getInstance();
			day.setTimeZone(TimeZone.getTimeZone("GMT" + timetype));
			day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			day.set(Calendar.DATE, cal.get(Calendar.DATE));
			day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
			day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
			day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
			date = day.getTime();
			nowTime = formatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//System.out.println("传入时间"+dateString+",返回时间:"+nowTime);
		return nowTime;
	}
	
	public static String getDateDiffInHours(String startDate, String endDate, String format) {  
		//按照传入的格式生成一个simpledateformate对象  
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
//		long nd = 1000*24*60*60;//一天的毫秒数  
		long nh = 1000*60*60;//一小时的毫秒数  
		long nm = 1000*60;//一分钟的毫秒数  
		long ns = 1000;//一秒钟的毫秒数  
		long diff;  
		//获得两个时间的毫秒时间差异 
		//输出结果  
		String hourStr = ""; //计算差多少小时  
		String minStr = "";//计算差多少分钟  
		String secStr = "";//计算差多少秒  
		try {
			diff = sdf.parse(endDate).getTime() - sdf.parse(startDate).getTime();
	//		diff = endDate.getTime() - startDate.getTime();  
	//		long day = diff/nd;//计算差多少天  
			long hour = diff/nh;//计算差多少小时  
			long min = diff%nh/nm;//计算差多少分钟  
			long sec = diff%nh%nm/ns;//计算差多少秒  

			if(hour < 10){
				hourStr = "0" + hour;
			}else{
				hourStr = "" + hour;
			}
			if(min < 10){
				minStr = "0" + min;
			}else{
				minStr = "" + min;
			}
			if(sec < 10){
				secStr = "0" + sec;
			}else{
				secStr = "" + sec;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return hourStr + ":" + minStr + ":" + secStr;
	} 
	
	/**
	 * 功能：获得本周一0点时间  
	 * @return Date
	 */
    public static Date getTimesStartWeek() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
        return  cal.getTime();  
    }  
  
    /**
	 * 功能：获得本周日23:59:59时间  
	 * @return Date
	 */
    public  static Date getTimesEndWeek() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getTimesStartWeek());  
        cal.add(Calendar.DAY_OF_WEEK, 6);  
        cal.set(Calendar.HOUR_OF_DAY, 23);  
        cal.set(Calendar.SECOND, 59);  
        cal.set(Calendar.MINUTE, 59);  
        return cal.getTime();  
    }  
    
    /**
	 * 功能：获得指定时间的周一0点时间  
	 * @return Date
	 */
    public static Date getTimesStartWeek(Date curDate) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(curDate);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
        return  cal.getTime();  
    }  
  
    /**
	 * 功能：获得指定时间的周日23:59:59时间  
	 * @return Date
	 */
    public  static Date getTimesEndWeek(Date curDate) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getTimesStartWeek(curDate));  
        cal.add(Calendar.DAY_OF_WEEK, 6);  
        cal.set(Calendar.HOUR_OF_DAY, 23);  
        cal.set(Calendar.SECOND, 59);  
        cal.set(Calendar.MINUTE, 59);  
        return cal.getTime();  
    } 
    
    /**
     * 
     * 功能：获取指定时间当天的开始时间 （yyyy-MM-dd 00:00:00） 
     * @param curDate
     * @return Date
     */
    public static Date getCurrentStartDate(Date curDate) {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 
     * 功能：获取指定时间当天的结束时间（yyyy-MM-dd 23:59:59） 
     * @param curDate
     * @return Date
     */
    public static Date getCurrentEndDate(Date curDate) {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
	
    public static void main(String[] args) {
    	/*System.out.println(getCurrentDateStartTime());*/
    	
    	
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
       /* String str1 = "2016-06-16 11:30:30";
        String str2 = "2016-06-19 10:29:32";
        Date start = DateUtil.stringToDate(str1, "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.stringToDate(str2, "yyyy-MM-dd HH:mm:ss");*/
//    	System.out.println(getDateDiffInHours(start, end));
//    	System.out.println(formatDateToString(stringToDate("2016-12-06"), "d/M/y"));
//    	System.out.println(formatDateToString(new Date(),"d/MMM/y",Locale.ENGLISH));
//    	System.out.println(getNewTimeForGMT("+8", "2017-07-25 13:45:29", "MTF"));
//    	System.out.println(addSeconds(new Date(),-30));
      /*  Date addHours = addHours(start, -8);
        System.out.println(DateUtil.formatDateToString(addHours, "yyyy-MM-dd HH:mm:ss"));
        DateUtil.getNewTimeForGMT("+8", str1, "MTF");*/
    	Date date = new Date(1514861399841L);
    	System.out.println(DateUtil.formatDateToString(date, DateUtil.DATETIME_PATTERN));
    	Date date1 = new Date(1473862360021L);
    	System.out.println(DateUtil.formatDateToString(date1, DateUtil.DATETIME_PATTERN));
    	Date date2 = new Date(1513862658924L);
    	System.out.println(DateUtil.formatDateToString(date2, DateUtil.DATETIME_PATTERN));
    	Date date3 = new Date(1514561631025L);
    	System.out.println(DateUtil.formatDateToString(date3, DateUtil.DATETIME_PATTERN));
    }

}
