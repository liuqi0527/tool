package com.server.tool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

	private static final SimpleDateFormat dateFormat;
	static {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 检查两天是否为一天（最大计算单位为相邻的两年）
	 * 
	 * @return 0为一天 大于0（例：currDate（今天）-targetDate（昨天） = 1天）小于0（例：昨天-今天 = -1 天）
	 */
	public static int checkDateIsOneDay(Date currDate, Date targetDate) {
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.setTime(currDate);
		Calendar targetCalendar = new GregorianCalendar();
		targetCalendar.setTime(targetDate);
		// 跨年
		if (currCalendar.get(Calendar.YEAR) != targetCalendar
				.get(Calendar.YEAR)) {
			// 该时间为负数。证明当前天小于目标天数
			// 当前年最大天数 - 当前时间所在年的天数 + 目标所在年的天数 = 当前天数与目标天数相差多少天
			return -((currCalendar.getActualMaximum(Calendar.DAY_OF_YEAR) - currCalendar
					.get(Calendar.DAY_OF_YEAR)) + targetCalendar
					.get(Calendar.DAY_OF_YEAR));
		} else {
			return currCalendar.get(Calendar.DAY_OF_YEAR)
					- targetCalendar.get(Calendar.DAY_OF_YEAR);
		}
	}

	/**
	 * 获取目标时间距离今天0点的时间
	 * 
	 * @param targetDate
	 *            目标时间
	 * @return 与目标0点之间相差的毫秒
	 */
	public static long getDayForDate(Date targetDate) {
		return targetDate.getTime() - getCurrDay(targetDate).getTime();
	}

	/**
	 * 获得目标时间凌晨0点
	 * 
	 * @return
	 */
	public static Date getCurrDay(Date targetDate) {
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.setTime(targetDate);
		currCalendar.clear(Calendar.HOUR);
		currCalendar.clear(Calendar.HOUR_OF_DAY);
		currCalendar.clear(Calendar.MINUTE);
		currCalendar.clear(Calendar.SECOND);
		currCalendar.clear(Calendar.MILLISECOND);
		currCalendar.clear(Calendar.AM_PM);

		return currCalendar.getTime();
	}

	/**
	 * 获取周一
	 */
	public static Date getWeekOne(Date targetDate) {
		Date date = getCurrDay(targetDate);
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.setTime(date);
		int week = currCalendar.get(Calendar.DAY_OF_WEEK);
		week = week == 1 ? 6 : week - 2;
		return new Date(date.getTime() - week * 24 * 60 * 60 * 1000);
	}

	/**
	 * 获得目标时间是星期几
	 */
	public static int getTargetDateWeek(Date targetDate) {
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.setTime(targetDate);
		int week = currCalendar.get(Calendar.DAY_OF_WEEK);
		return week == 1 ? 7 : (week - 1);
	}

	public static boolean checkDateIsOneWeek(Date currDate, Date targetDate) {
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.setTime(currDate);
		Calendar targetCalendar = new GregorianCalendar();
		targetCalendar.setTime(targetDate);
		if (currCalendar.get(Calendar.YEAR) != targetCalendar
				.get(Calendar.YEAR)) {
			if (getTargetDateWeek(currDate) == 7) {
				return false;
			}
			return true;
		}
		return currCalendar.get(Calendar.WEEK_OF_YEAR) == targetCalendar
				.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取两个时间段范围内的星期所在的天数，如果两个时间不是一个星期的话，以endDate的周一开始计算
	 */
	public static Set<Integer> getWeeksForDate(Date startDate, Date endDate) {
		if (!checkDateIsOneWeek(startDate, endDate)) {
			startDate = getWeekOne(endDate);
		}

		Set<Integer> result = new HashSet<Integer>();
		int days = checkDateIsOneDay(startDate, endDate);
		result.add(getTargetDateWeek(startDate));
		for (int i = 1; i <= days; i++) {
			result.add(getTargetDateWeek(new Date(startDate.getTime() + i * 24
					* 60 * 60 * 1000)));
		}
		result.add(getTargetDateWeek(endDate));
		return result;

	}

	/**
	 * 判断字符串是否能够匹配成日期格式
	 */
	public static boolean checkIsDateFormat(String string) {
		String DatePattern = "^(?:([0-9]{4}-(?:(?:0?[1,3-9]|1[0-2])-(?:29|30)|"
				+ "((?:0?[13578]|1[02])-31)))|"
				+ "([0-9]{4}-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1\\d|2[0-8]))|"
				+ "(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|"
				+ "(?:0[48]00|[2468][048]00|[13579][26]00))-0?2-29)))$";
		Pattern p = Pattern.compile(DatePattern);
		Matcher m = p.matcher(string);
		return m.matches();
	}

	public static boolean checkIsDateRange(Date date, Date startDate,
			Date endDate) {
		long dateNum = date.getTime();
		long startNum = startDate.getTime();
		long endNum = endDate.getTime();
		return dateNum >= startNum && dateNum < endNum;
	}

	public static Date parseDateTimeForString(String str) {
		if (str == null || str.equals("")) {
			return null;
		}
		try {
			return dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}
}
