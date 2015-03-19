/*
 * @(#)TimeConverter.java
 *
 * Copyright 2004 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */
package om.gov.moh.eab.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import om.gov.moh.eab.constants.Constants;


/**
 * @author farid.haq
 * 
 * This class acts as a general utility for all time related conversions and
 * calculations. This class provides useful methods for working around with
 * java.util.Date, java.util.Calendar, java.sql.TimeStamp etc
 */

/*
 * Revision History
 * Revision		Date			Author					Description
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 *	0.1		    May 21 2013		farid.haq			Created the class
 *  
 *
 */

public class DateUtils {

	public static final int HOURS_IN_DAY = 24;
	public static final int MINUTES_IN_DAY = 1440;
	public static final int MINUTES_IN_HOUR = 60;
	public static final long SECONDS_IN_MINUTE = 60;
	public static final long MILLIS_IN_MINUTE = SECONDS_IN_MINUTE * 1000;

	public static final String GMT = "gmt";
	public static final String HOUR_MINUTE_DELIMITER = ":";
	public static final TimeZone GMT_ZONE = TimeZone.getTimeZone(GMT);

	protected static String weekdays[] = { "",
			"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", 
			"FRIDAY", "SATURDAY"};	
	/**
	 * This method converts a calendar encapsulating a date in any timezone
	 * to a date in the default timezone of the jvm. This can be used to
	 * tackle the possible changes in date and time when a date is used
	 * across various jvms running in different timezones.
	 *
	 * Example:-
	 * When a date, say 21-06-2002 is passed from one jvm to another jvm
	 * in different timezones, it can be converted to 20-06-2002 or 22-06-2002.
	 * This method returns the same date 21-06-2002 irrespective of timezones.
	 *
	 * @param Calendar date which has to be converted to the default timezone
	 * @return Calendar date converted to default timezone.
	 */
	public static Calendar toDefaultTimeZone(Calendar calendar) {
		Calendar localDate = new GregorianCalendar();
		localDate.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
					  calendar.get(Calendar.DAY_OF_MONTH),
					  calendar.get(Calendar.HOUR_OF_DAY),
					  calendar.get(Calendar.MINUTE),
					  calendar.get(Calendar.SECOND));
		localDate.set(Calendar.MILLISECOND,calendar.get(Calendar.MILLISECOND));
		return localDate;
	}

	/**
	 * This method converts a calendar encapsulating a date in any timezone
	 * to a date in the default timezone of the jvm. This can be used to
	 * tackle the possible changes in date and time when a date is used
	 * across various jvms running in different timezones.
	 *
	 * Example:-
	 * When a date, say 21-06-2002 is passed from one jvm to another jvm
	 * in different timezones, it can be converted to 20-06-2002 or 22-06-2002.
	 * This method returns the same date 21-06-2002 irrespective of timezones.
	 *
	 * @param Calendar date which has to be converted to the default timezone
	 * @return Date date converted to default timezone.
	 */
	public static Date getDate(Calendar calendar) {
		return toDefaultTimeZone(calendar).getTime();
	}

	/**
	 * This method converts a calendar encapsulating a date in any timezone
	 * to a Timestamp in the default timezone of the jvm.
	 *
	 * @param Calendar date which has to be converted to the default timezone
	 * @return Timestamp timestamp in default timezone.
	 */
	public static Timestamp getTimestamp(Calendar calendar) {
		return new Timestamp(getDate(calendar).getTime());
	}

	/**
	 * This method converts a date in the server time zone to a calendar
	 * in the same timezone
	 *
	 * @param Date date which has to be converted to Calendar in
	 *        the default timezone
	 * @return Calendar in default timezone.
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * This method forces the hours, minutes, seconds, and milliseconds
	 * in a date object to 0 but keeps the date part the same.
	 * This will return the date in the default timezone of the jvm
	 * in which this class is loaded.
	 *
	 * @param Date the date whose time fields are to be trimmed off.
	 * @return Date a date object with all time fields trimmed off.
	 */
	public static Date dateOnly(Date dateWithTime) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateWithTime);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		return calendar.getTime();
	}

	/**
	 * This method forces the hours, minutes, seconds, and milliseconds
	 * in a calendar object to 0 but keeps the date part the same.
	 *
	 * @param dateWithTime the calendar whose time fields are to be trimmed off.
	 * @return a modified calendar object with all time fields trimmed off.
	 */
	public static Calendar dateOnly(Calendar dateWithTime) {

		dateWithTime.set(Calendar.MILLISECOND, 0);
		dateWithTime.set(Calendar.SECOND,0);
		dateWithTime.set(Calendar.MINUTE,0);
		dateWithTime.set(Calendar.HOUR_OF_DAY,0);
		return dateWithTime;
	}

	/**
	 * This method converts a date in the default timezone to a Calendar in GMT.
	 *
	 * @param Date the date which has to be converted into GMT.
	 * @return Calendar containing the GMT date.
	 */
	public static Calendar toGMTCalendar(Date localDate) {
		Calendar gmtCalendar = new GregorianCalendar();
		gmtCalendar.setTimeZone(GMT_ZONE);
		gmtCalendar.setTime(localDate);
		return gmtCalendar;
	}

	/**
	 * This method converts a GMT time String to a Calendar in GMT.
	 *
	 * @param String the date which has to be converted into GMT.
	 * @param String the date format
	 * @return Calendar containing the GMT date.
	 */
	public static Calendar toGMTCalendar(String date, String format)
		throws ParseException {
		Calendar gmtCalendar = Calendar.getInstance(GMT_ZONE);
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(GMT_ZONE);
		gmtCalendar.setTime(formatter.parse(date));
		return gmtCalendar;
	}

	/**
	 * This method converts a date in the default timezone to a date in GMT.
	 *
	 * @param Date the date which has to be converted into GMT.
	 * @return Date the GMT date.
	 */
	public static Date toGMTDate(Date localDate) {
		return toDefaultTimeZone(toGMTCalendar(localDate)).getTime();
	}

	/**
	 * This method converts a date in the default timezone to a date in GMT
	 * after discarding the time fields
	 *
	 * @param Date the date which has to be converted into GMT.
	 * @return Date the GMT date with no time.
	 */
	public static Date toGMTDateOnly(Date localDate) {
		return dateOnly(toGMTDate(localDate));
	}

	/**
	 * This method returns the current date in the default time zone
	 *
	 * @return Date current date
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * This method returns the current date in the GMT time zone
	 *
	 * @return Date current date in GMT timezone
	 */
	public static Date getCurrentGMTDate() {
		return toGMTDate(new Date());
	}

	/**
	 * This method returns the current date as a Calendar in the GMT zone
	 *
	 * @return Calendar current date in GMT timezone
	 */
	public static Calendar getCurrentGMTCalendar() {
		return toGMTCalendar(new Date());
	}

	/**
	 * This method converts a time String in a specified timezone to
	 * to a Calendar in default timezone of the JVM.
	 *
	 * @param String the date which has to be converted into default timezone.
	 * @param String the date format
	 * @param Timezone the timezone of the date String
	 * @return Calendar containing the date in default timezone
	 */
	public static Calendar toDefaultTimeZone(
		String date, TimeZone timezone, String format)
		throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(timezone);
		formatter.parse(date);
		Calendar calendar = dateOnly(formatter.getCalendar());
		return toDefaultTimeZone(calendar);
	}

	/**
	 * This method converts a date in the default timezone to a Calendar in
	 * any other time zone.
	 *
	 * @param Date the date which has to be converted.
	 * @param String timezone into which the date has to be converted.
	 * @return Calendar the date converted to another time zone.
	 */
	public static Calendar convertTimeZone(Date localDate, String timezone) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(TimeZone.getTimeZone(timezone));
		calendar.setTime(localDate);
		return calendar;
	}

	/**
	 * This method converts a date in any timezone to a date in
	 * any other time zone.
	 *
	 * @param Calendar the date which has to be converted.
	 * @param String timezone into which the Calendar has to be converted.
	 * @return Calendar the date converted to another time zone.
	 */
	public static Calendar convertTimeZone(Calendar date, String timezone) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(TimeZone.getTimeZone(timezone));
		calendar.setTimeInMillis(date.getTimeInMillis());
		return calendar;
	}

	/**
	 * This method converts a date in any timezone to a date string in
	 * any other time zone specified as argument.
	 *
	 * @param Calendar the date which has to be converted.
	 * @param String timezone into which the Calendar has to be converted.
	 * @param the format to which the date has to be converted
	 * @return String the date converted to another time zone and to String format.
	 */
	public static String toZoneConvertedString(
		Calendar date, String timezone, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getTimeZone(timezone));
		return formatter.format(convertTimeZone(date,timezone).getTime());
	}

	/**
	 * This method returns the Timestamp value in GMT corresponding to
	 * the Date passed as argument.
	 *
	 * @param Date the date in the default time zone.
	 * @return Timestamp gmt timestamp
	 */
	public static Timestamp toGMTTimestamp(Date localDate) {
		return new Timestamp(toDefaultTimeZone(toGMTCalendar(localDate)).
							 getTimeInMillis());
	}

	/**
	 * This method returns the Timestamp value in GMT corresponding to
	 * the Calendar passed as argument.
	 *
	 * @param Calendar the date in the default time zone.
	 * @return Timestamp gmt timestamp
	 */
	public static Timestamp toGMTTimestamp(Calendar localDate) {
		return new Timestamp(toDefaultTimeZone(convertTimeZone(localDate,GMT)).
							 getTimeInMillis());
	}

	/**
	 * This method returns the current Timestamp value in GMT
	 *
	 * @return Timestamp gmt timestamp
	 */
	public static Timestamp getCurrentGMTTimestamp() {
		return toGMTTimestamp(new Date());
	}

	/**
	 * This method returns the current time stamp in the default time zone
	 *
	 * @return Timestamp current date
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * This method merges a date field without time and a time value
	 * into a date with time fields.If the date object contains time fields,
	 * that time will be discarded and only the specified time will be appended.
	 * All the dates will be in the default time zone of the jvm
	 *
	 * @param Date date without time fields
	 * @param int time to be appended to the date
	 * @return Date merged date and time
	 */
	public static Date mergeDateAndTime(Date date, int time) {

		int days = 0;
		int minutes = 0;
		int hours = 0;
		int currentMinutes = 0;
		int totalTimeInMinutes = 0;
		Calendar calendar = null;
		if(time == 0) {
			return date;
		}
		calendar = new GregorianCalendar();
		calendar.setTime(date);
		currentMinutes = (calendar.get(Calendar.HOUR_OF_DAY) * MINUTES_IN_HOUR) +
						 (calendar.get(Calendar.MINUTE));
		totalTimeInMinutes = currentMinutes + time;
		if(totalTimeInMinutes >= MINUTES_IN_DAY) {
			days = totalTimeInMinutes / MINUTES_IN_DAY;
			date = addDay(dateOnly(calendar.getTime()),days);
			minutes = totalTimeInMinutes - (days * MINUTES_IN_DAY);
			hours = minutes / MINUTES_IN_HOUR;
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY,hours);
			calendar.set(Calendar.MINUTE, minutes % MINUTES_IN_HOUR);
			return calendar.getTime();
		}
		hours = totalTimeInMinutes / MINUTES_IN_HOUR;
		calendar.setTime(dateOnly(date));
		calendar.set(Calendar.HOUR_OF_DAY,hours);
		calendar.set(Calendar.MINUTE, totalTimeInMinutes % MINUTES_IN_HOUR);
		return calendar.getTime();
	}

	/**
	 * This method return the date after a specified number of days
	 * All the dates will be in the default timezone of the jvm
	 *
	 * @param Date date to which the number of days have to be added.
	 * @param int days - number of days to be added.
	 * @return Date after adding the specified number of days
	 */
	public static Date addDay(Date date, int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * This method return the date after a specified number of days
	 * All the dates will be in the default timezone of the jvm
	 *
	 * @param Date date to which the number of days have to be added.
	 * @param int days - number of days to be added.
	 * @return Calendar after adding the specified number of days
	 */
	public static Calendar dayAfterCalendar(Date date, int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar;
	}
	
	/**
	 * This method return the date after a specified number of months
	 * All the dates will be in the default timezone of the jvm
	 * 
	 * @param Date date - to which the number of months have to be added.
	 * @param int months - number of months to be added.
	 * @return Date after adding the specified number of months.
	 */
	public static Date addMonth(Date date, int months) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	/**
	 * This method return the total value of all time fields in minutes
	 * from a given date in default time zone
	 *
	 * @param Date date whose time value is required.
	 * @return int total time in minutes
	 */
	public static int getTimeInMinutes(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return (calendar.get(Calendar.HOUR_OF_DAY) * MINUTES_IN_HOUR) +
			   (calendar.get(Calendar.MINUTE));
	}

	/**
	 * Returns the difference, in milliseconds, between date1 and date2.
	 *
	 * @@param date1 start date
	 * @@param date2 end date
	 * @@return The number of milliseconds between date1 and date2.
	 * (i.e. (date2 - date1) expresed in milliseconds)
	 */
	public static long delta(Date date1, Date date2) {
		return date2.getTime() - date1.getTime();
	}

	/**
	 * Returns the difference, in minutes, between date1 and date2.
	 *
	 * @@param date1 start date
	 * @@param date2 end date
	 * @@return The number of minutes between date1 and date2.
	 * (i.e. (date2 - date1) expresed in minutes)
	 */
	public static int deltaMinutes(Date date1, Date date2) {
		return (int)(delta(date1, date2) / MILLIS_IN_MINUTE);
	}

	public static int delta(Calendar first, Calendar other) {
		first = dateOnly(first);
		other = dateOnly(other);
		return deltaMinutes(first.getTime(),other.getTime())/MINUTES_IN_DAY;
	}

	/**
	 * This method formats time in minutes into the format HH:mm
	 * Example:-
	 * If the time in minutes is 930, then the HH:mm format will be 15:30
	 *
	 * @param int minutes
	 * @return String
	 */
	public static String toHHmmFormat(int minutes) {

		int days = 0;
		int hours = 0;
		StringBuffer buffer = new StringBuffer();
		if(minutes >= MINUTES_IN_DAY) {
			days = minutes / MINUTES_IN_DAY;
			minutes = minutes - (days * MINUTES_IN_DAY);
			hours = days * HOURS_IN_DAY;
		}
		hours = hours + (minutes / MINUTES_IN_HOUR);
		minutes = minutes % MINUTES_IN_HOUR;
		if(hours < 10) {
			buffer.append(0);
		}
		buffer.append(hours);
		buffer.append(HOUR_MINUTE_DELIMITER);
		minutes = Math.abs(minutes);
		if(minutes < 10) {
			buffer.append(0);
		}
		buffer.append(minutes);
		return buffer.toString();
	}

	/**
	 * This method converts a date string in the dafault format to a
	 * date object
	 *
	 * @param String date string
	 * @return Date object
	 */
	public static Date toDate(String dateString) throws ParseException {
		return toDate(dateString, Constants.DATE_FORMAT_dd_MM_yyyy);
	}

	/**
	 * This method converts a date string in the dafault format to a
	 * Calendar object
	 *
	 * @param String date string
	 * @return Calendar object
	 */
	public static Calendar toCalendar(String dateString) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate(dateString,  Constants.DATE_FORMAT_dd_MM_yyyy));
		return calendar;
	}

	/**
	 * This method converts a date string in a specified format to a
	 * date object
	 *
	 * @param String date string
	 * @param String format
	 * @return Date object
	 */
	public static Date toDate(String dateString, String format)
		throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(dateString);
	}

	/**
	 * This method converts a date string in a specified format to a
	 * Calendar object
	 *
	 * @param String date string
	 * @return Calendar object
	 */
	public static Calendar toCalendar(String dateString, String format)
		throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate(dateString, format));
		return calendar;
	}

	/**
	 * This method converts a date object into the default string format
	 *
	 * @param Date date object
	 * @return String normal format
	 */
	public static String toNormalString(Date date) {
		DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_dd_MM_yyyy);
		return formatter.format(date);
	}

	/**
	 * This method converts a date object into the specified string format
	 *
	 * @param Date date object
	 * @param String specified format
	 * @return String date string
	 */
	public static String toStringFormat(Date date, String format) {
		if(date == null)
			return "";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * This method converts a Calendar object into the specified string format
	 *
	 * @param Calendar date object
	 * @param String specified format
	 * @return String date string
	 */
	public static String toStringFormat(Calendar date, String format) {
		if(date == null)
			return "";

		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date.getTime());
	}

	/**
	 * This method converts a date object in the default time zone to
	 * the GMT string in the default format
	 *
	 * @param Date date object
	 * @return String GMT date string normal format
	 */
	public static String toGMTString(Date localDate) {
		DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_dd_MM_yyyy);
		formatter.setTimeZone(GMT_ZONE);
		return formatter.format(localDate);
	}

	/**
	 * This method converts a date object in the default time zone to
	 * the GMT string in a specified format
	 *
	 * @param Date date object
	 * @param String specified format
	 * @return String GMT date string in the specified format
	 */
	public static String toGMTString(Date localDate, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(GMT_ZONE);
		return formatter.format(localDate);
	}

	/**
	 * Checks whether the <code>Calendar</code> instances represent the same
	 * date ignoring timezone.
	 *
	 * @param firstDate the first <code>Calendar</code> object
	 * @param secondDate the second <code>Calendar</code> object
	 * @return true if both represent the same date
	 */
	public static boolean isDateSameIgnoringTimezone(
												Calendar firstDate,
												Calendar secondDate) {
		if ((firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR))
			&& (firstDate.get(Calendar.MONTH) == secondDate.get(Calendar.MONTH))
			&& (firstDate.get(Calendar.DAY_OF_MONTH)
									== secondDate.get(Calendar.DAY_OF_MONTH))) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Checks whether the <code>Calendar</code> instances represent the same
	 * date & time ignoring timezone.
	 *
	 * @param firstDate the first <code>Calendar</code> object
	 * @param secondDate the second <code>Calendar</code> object
	 * @return true if both represent the same date
	 */
	public static boolean isTimeSameIgnoringTimezone(
												Calendar firstDate,
												Calendar secondDate) {
		if(isDateSameIgnoringTimezone(firstDate,secondDate) &&
		   (firstDate.get(Calendar.HOUR_OF_DAY)
				   == secondDate.get(Calendar.HOUR_OF_DAY)) &&
		   (firstDate.get(Calendar.MINUTE)
				   == secondDate.get(Calendar.MINUTE)) &&
		   (firstDate.get(Calendar.SECOND)
				   == secondDate.get(Calendar.SECOND))) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * checks if the string date is a valid date in format given
	 * @param date as String
	 * @param format with which the string date has to be validated
	 * @return boolean - true if valid date of the
	 * format given, false if invalid date or
	 * invalid format
	 */
	public static boolean isValidDateFormat(String date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		boolean flag = true;
		try {
			Date theDate = dateFormat.parse(date);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * This method converts a date in any timezone to a date in the default
	 * time zone of the jvm. This is different from <code>toDefaultTimeZone(
	 * Calendar)</code> which does a copy of certain Calendar fields (YEAR,
	 * MONTH, DAY_OF_MONTH, HOUR_OF_DAY, MINUTE and SECOND).
	 *
	 * @param Calendar the date which has to be converted.
	 * @return Calendar the date converted to another time zone.
	 */
	public static Calendar convertTimeZone(Calendar date) {
		Calendar local = new GregorianCalendar();
		local.setTimeInMillis(date.getTimeInMillis());
		return local;
	}



		/**
	 * Returns the first day of the current month
	 * @return Calendar first day of current month
	 */
	public static Calendar getCurrentMonthStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar;
	}

	/**
	 * Returns the first day of the current month
	 * @return Timestamp first day of current month
	 */
	public static Timestamp currentMonthStartTimeStamp() {
		return getTimestamp(getCurrentMonthStart());
	}

	public static String getCurrentMonthStart(String format) {
		return toStringFormat(getCurrentMonthStart(),format);
	}

	/**
	 * Returns the last day of the current month
	 * @return Calendar last day of current month
	 */
	public static Calendar getCurrentMonthLast() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar;
	}

	public static String getCurrentMonthLast(String format) {
		return toStringFormat(getCurrentMonthLast(),format);
	}

	/**
	 * Returns the first day of the current month
	 * @return Timestamp first day of current month
	 */
	public static Timestamp currentMonthEndTimeStamp() {
		return getTimestamp(getCurrentMonthLast());
	}

	public static Collection getSucceedingDays(
		String startDate, String dateFormat,int duration)
		throws ParseException {

		Collection succedingDays = new ArrayList();
		Date date;
		date = toDate(startDate,dateFormat);
		succedingDays.add(startDate);
		for(int i = 1; i < duration; i++) {
			date = addDay(date,1);
			succedingDays.add(toStringFormat(date,dateFormat));
		}
		return succedingDays;
	}
	
	/**
	 * This method will return the string representation of the
	 * day of week for the given date
	 * @param date
	 * @return day of week String
	 */
	public static String getDayString(Calendar date){
		String  day ="";
		if(date != null){
			day = weekdays[date.get(Calendar.DAY_OF_WEEK)];
		}
		return day;
	}

	public static void main(String[] args){
		try{
			//System.out.println("Going to Print Date");
			Date d = toDate("23-Jun-2004","dd-MMM-yyyy");
			//System.out.println(toStringFormat(d,"dd-MMM-yyyy"));
		} catch(Exception e){
			e.printStackTrace();
		}

	}
}
