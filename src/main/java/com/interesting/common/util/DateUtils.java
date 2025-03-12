package com.interesting.common.util;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 类描述：时间操作定义类
 *
 * @Author: interesting-boot
 * @Date:2012-12-8 12:15:03
 * @Version 1.0
 */
public class DateUtils extends PropertyEditorSupport {

    public static String date_sdf = "yyyy-MM-dd";
    public static String yyyyMMdd = "yyyyMMdd";
    public static String date_sdf_wz = "M月dd日 HH:mm";
    public static String date_sdf_wz2 = "M月dd日";
    public static String time_sdf = "yyyy-MM-dd HH:mm";
    public static String yyyymmddhhmmss = "yyyymmddhhmmss";
    public static String short_time_sdf = "HH:mm";
    public static String datetimeFormat = "yyyy-MM-dd HH:mm:ss";

    // 以毫秒表示的时间
    private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
    private static final long HOUR_IN_MILLIS = 3600 * 1000;
    private static final long MINUTE_IN_MILLIS = 60 * 1000;
    private static final long SECOND_IN_MILLIS = 1000;


    // ////////////////////////////////////////////////////////////////////////////
    // getDate
    // 各种方式获取的Date
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 当前日期
     *
     * @return 系统当前时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 指定毫秒数表示的日期
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static Date getDate(long millis) {
        return new Date(millis);
    }

    /**
     * 指定毫秒数表示的日期
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static Date getDate(Integer millis) {
        return new Date(millis * 1000);
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @param sdf
     * @return
     */
    public static Date str2Date(String str, String sdf) {
        if (null == str || "".equals(str)) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern(sdf));
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 日期转换为字符串
     *
     * @param date     日期
     * @param date_sdf 日期格式
     * @return 字符串
     */
    public static String date2Str(Date date, String date_sdf) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(date_sdf));
    }

    /**
     * 日期转换为字符串
     *
     * @param format 日期格式
     * @return 字符串
     */
    public static String getDate(String format) {
        Date date = new Date();
        return date2Str(date, format);
    }

    /**
     * 日期转换为字符串
     *
     * @return 字符串
     */
    public static String getFormatDate() {
        Date date = new Date();
        return date2Str(date, datetimeFormat);
    }
    /**
     * 日期转换为字符串
     *
     * @return 字符串
     */
    public static String getFormatDateDay() {
        Date date = new Date();
        return date2Str(date, date_sdf);
    }


    /**
     * 系统当前的时间戳
     *
     * @return 系统当前的时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(zdt);
        return format;
    }

    public static void main(String[] args) {
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(zdt);
        int cc = 0;

    }

    public static String nowTime(String pattern) {
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String format = dateTimeFormatter.format(zdt);
        return format;
    }
    // ////////////////////////////////////////////////////////////////////////////
    // getMillis
    // 各种方式获取的Millis
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 系统时间的毫秒数
     *
     * @return 系统时间的毫秒数
     */
    public static long getMillis() {
        return System.currentTimeMillis();
    }
    /**
     * 系统时间的毫秒数
     *
     * @return 系统时间的毫秒数
     */
    public static Integer getSecond() {
        return Math.toIntExact(System.currentTimeMillis() / 1000);
    }

    /**
     * 指定日历的毫秒数
     *
     * @param cal 指定日历
     * @return 指定日历的毫秒数
     */
    public static long getMillis(Calendar cal) {
        // --------------------return cal.getTimeInMillis();
        return cal.getTime().getTime();
    }

    /**
     * 指定日期的毫秒数
     *
     * @param date 指定日期
     * @return 指定日期的毫秒数
     */
    public static long getMillis(Date date) {
        return date.getTime();
    }

    /**
     * 指定时间戳的毫秒数
     *
     * @param ts 指定时间戳
     * @return 指定时间戳的毫秒数
     */
    public static long getMillis(Timestamp ts) {
        return ts.getTime();
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatDate
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日
     *
     * @return 默认日期按“年-月-日“格式显示
     */
    public static String formatDate() {
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dateTimeFormatter.format(zdt);
        return format;
    }

    /**
     * 默认方式表示的系统日期，具体格式：年-月-日
     *
     * @param addDay 在当前时间添加几天
     * @return 默认日期按“年-月-日“格式显示
     */
    public static String formatDate(Integer addDay) {
        ZonedDateTime zdt = ZonedDateTime.now();
        ZonedDateTime zonedDateTime = zdt.plusDays(addDay);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dateTimeFormatter.format(zonedDateTime);
        return format;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // parseDate
    // parseCalendar
    // parseTimestamp
    // 将字符串按照一定的格式转化为日期或时间
    // ////////////////////////////////////////////////////////////////////////////


    // ////////////////////////////////////////////////////////////////////////////
    // dateDiff
    // 计算两个日期之间的差值
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 类型转换成时间
     *
     * @param type 1,今天 2，明天  3,后天
     * @return
     */
    public static String typeToDate(Integer type) {
        if (type == null) {
            type = 1;
        }
        switch (type) {
            case 1:
                return formatDate();
            default:
                return formatDate(type);
        }
    }

    public static int getYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getDate());
        return calendar.get(Calendar.YEAR);
    }

    public static String[] division(Integer time) {
        Long aLong = Long.parseLong(time + "000");
        String s = date2Str(new Date(aLong), date_sdf);
        if (s != null) {
            String[] split = s.split("-");
            return split;
        }
        return new String[3];
    }

    public static int getYear(Integer time) {
        String[] split = division(time);

        return Integer.parseInt(split[0]);
    }

    public static int getMonth(Integer time) {
        String[] split = division(time);

        return Integer.parseInt(split[1]);
    }

    public static int getDay(Integer time) {
        String[] split = division(time);
        return Integer.parseInt(split[2]);
    }

    public static long getMonthDiff(Date startDate, Date endDate) throws ParseException {
        long monthday;
        Calendar starCal = Calendar.getInstance();
        starCal.setTime(startDate);
        int sYear = starCal.get(Calendar.YEAR);
        int sMonth = starCal.get(Calendar.MONTH);
        int sDay = starCal.get(Calendar.DAY_OF_MONTH);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int eYear = endCal.get(Calendar.YEAR);
        int eMonth = endCal.get(Calendar.MONTH);
        int eDay = endCal.get(Calendar.DAY_OF_MONTH);
        monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
        //这里计算零头的情况，根据实际确定是否要加1 还是要减1
        if (sDay < eDay) {
            monthday = monthday + 1;
        }
        return monthday;
    }

    /**
     * 获取周期月
     *
     * @param startDate
     * @param endDate
     * @param num
     * @return
     */
    public static List<Date> getMonthList(Date startDate, Date endDate, Integer num, Long remainder, Integer payCycle) {
        List<Date> monthList = new ArrayList<>();
        Calendar starCal = Calendar.getInstance();
        starCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        Calendar nowCal = Calendar.getInstance();
        int count = 1;
        while (true) {
            starCal.add(Calendar.MONTH, num);
            nowCal.setTime(starCal.getTime());
            nowCal.add(Calendar.HOUR, -24);
//            System.out.println("starCal  "+format.format(starCal.getTime()));
//            System.out.println("nowCal  "+format.format(nowCal.getTime()));
            if (starCal.getTimeInMillis() >= endDate.getTime()) {
                monthList.add(endDate);
            } else {
                monthList.add(nowCal.getTime());
            }

            if (count == payCycle ) {
                if(remainder != null && remainder > 0){
                    monthList.set(monthList.size() - 1, endDate);
                }
                break;
            } else {
                count++;
            }
        }
       /* if (remainder != 0) {
            monthList.remove(monthList.size() - 1);
            *//*Calendar endAt =Calendar.getInstance();
            endAt.setTime(monthList.get(monthList.size()-1));
            endAt.add(Calendar.MONTH, remainder.intValue());*//*
            monthList.set(monthList.size() - 1, endDate);
        }*/
        return monthList;
    }


    public static List<Date> getDivideTheTime(Date startDate, Date endDate, Integer num) {
        Long startAt = startDate.getTime() / 1000;
        Long endAt = endDate.getTime() / 1000;
        Long addNum = (startAt - endAt) / num;


        return null;
    }


    /**
     * 获取当月天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当月的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @param timeZone  如 GMT+8:00
     * @return
     */
    public static Long getMonthEndTime(Long timeStamp, String timeZone) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 获取当前月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月的开始时间戳
     *
     * @return
     */
    public static Long getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

}
