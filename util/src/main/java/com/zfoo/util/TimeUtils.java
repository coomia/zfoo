package com.zfoo.util;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.09 12:04
 */
public abstract class TimeUtils {

    // 一秒钟对应的毫秒数
    public static final long MILLIS_PER_SECOND = 1 * 1000;
    // 一分钟对应的毫秒数
    public static final long MILLIS_PER_MINUTE = 1 * 60 * MILLIS_PER_SECOND;
    // 一个小时对应的毫秒数
    public static final long MILLIS_PER_HOUR = 1 * 60 * MILLIS_PER_MINUTE;
    // 一天对应的毫秒数
    public static final long MILLIS_PER_DAY = 1 * 24 * MILLIS_PER_HOUR;

    // 统一的时间格式模板
    public static final String DATE_FORMAT_TEMPLATE = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT_TEMPLATE);
        }
    };

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }


    // --------------------------------------日期格式--------------------------------------

    /**
     * 把日期字符串转换成Date对象，统一的日期模板要遵循DATE_FORMAT_TEMPLATE="yyyy-MM-dd HH:mm:ss"
     *
     * @param dateString 日期字符串，如：2018-02-12 10:12:50
     * @return <code>Date</code>
     * @throws ParseException
     */
    public static Date stringToDate(String dateString) throws ParseException {
        return dateFormat.get().parse(dateString);
    }

    /**
     * 把long类型的时间戳转换成字符串格式的时间
     *
     * @param time 时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String timeToString(long time) {
        return dateToString(new Date(time));
    }

    public static String dateToString(Date date) {
        return dateFormat.get().format(date);
    }

    // --------------------------------------日期判断--------------------------------------

    /**
     * <p>Checks if two date objects are on the same day ignoring time.</p>
     * <p>
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p>Checks if two calendar objects are on the same day ignoring time.</p>
     * <p>
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 判断两个日期是否是同一周，设置周一为一周的第一天
     * <p>
     * 2004-12-25”是星期六，也就是说它是2004年中第52周的星期六，那么“2004-12-26”到底是2004年的第几周哪，java中经测试取得的它的Week值是1，
     * 那么也就是说它被看作2005年的第一周了，这个处理是比较好的。可以用来判断“2004-12-26”和“2005-1-1”是同一周。
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameWeek(long time1, long time2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);
        cal2.setTimeInMillis(time2);
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        cal2.setFirstDayOfWeek(Calendar.MONDAY);

        int yearDiff = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (yearDiff == 0 && cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
            // yearDiff==0,说明是同一年
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (yearDiff == 1 && cal2.get(Calendar.MONTH) == 11) {
            //yearDiff==1,说明cal比cal2大一年;java的一月用"0"标识，那么12月用"11"
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (yearDiff == -1 && cal1.get(Calendar.MONTH) == 11) {
            //yearDiff==-1,说明cal比cal2小一年
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSameMonth(long time1, long time2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);
        cal2.setTimeInMillis(time2);
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }

    // --------------------------------------获取相关时间戳--------------------------------------

    /**
     * 获取给定时间戳对应的日期的0点时间戳
     *
     * @param time
     * @return
     */
    public static long getZeroTimeOfDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    // 获取给定时间戳对应的日期的最后时刻时间戳
    public static long getLastTimeOfDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    // 获取给定时间戳对应的日期的昨天这个时刻的时间戳
    public static long getYesterdayTimeOfDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.add(Calendar.DATE, -1);
        return cal.getTimeInMillis();
    }

    // 获取给定时间戳对应周的第一天的起始时间戳
    public static long getStartTimeOfWeek(int time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK));
        return cal.getTimeInMillis();
    }

    // --------------------------------------cron表达式--------------------------------------
    public static Date getNextDateByCronExpression(String expression, Date currentDate) {
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(expression);
        Date nextDate = cronSequenceGenerator.next(currentDate);
        return nextDate;
    }


    public void CalendarToDate() {
        // 月份：一月是0，二月是1，以此类推，12月是11
        // 星期：周日是1，周一是2，。。。周六是11
        Calendar calendar = new GregorianCalendar();
        // 2016-7-10 13:53:33
        // calendar.set(2016, 6, 10, 13, 53, 33);
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DATE, 10);// 默认会用系统的时间
        System.out.println(calendar.get(Calendar.YEAR));// 年份
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));// 一周中的第几天
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));// 一个月中的第几天
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));// 一个月中的第几周
        System.out.println(calendar.getActualMaximum(Calendar.DATE));// 这个月有多少天

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {// 增加逼格
            System.out.println("星期天！！！");
        }

        // Date和Calendar之间相互转化
        Date date = calendar.getTime();
        System.out.println(date);
        calendar.setTime(date);
        System.out.println(date);

        // 测试日期的计算
        calendar.add(Calendar.YEAR, 10);
        calendar.add(Calendar.MONTH, 1);// 也可以是负数，直接减-10
        calendar.add(Calendar.DATE, 10);
        date = calendar.getTime();
        System.out.println(date);

        System.out.println(calendar.get(Calendar.YEAR));
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar);
        System.out.println(calendar.getTime());
        test();
    }

    public static void test() {
        // 全局显示定义最省事安全
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        System.out.println(cal);
        System.out.println(cal.getTime());
    }
}
