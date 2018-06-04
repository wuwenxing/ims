package com.gwghk.ims.datacleaning.utils;

import java.util.Calendar;

public class DateUtil {
	
	public static Calendar getGMT0Calendar(){
		Calendar nowCal = Calendar.getInstance();
		nowCal.add(Calendar.HOUR_OF_DAY, -8);
		return nowCal;
	}
}
