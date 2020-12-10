package com.zmh.coupon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public final class DateUtil {

    // 默认显示日期的格式
    public static final String DATAFORMAT_STR = "yyyy-MM-dd";

    // 默认显示日期的格式
    public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";

    // 默认显示日期时间的格式
    public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";

    // 默认显示日期时间的格式
    public static final String DATATIMEF_STR2 = "yyyyMMdd HH:mm:ss";

    // 默认显示日期时间的格式 精确到毫秒
    public static final String DATATIMEF_STR_MIS = "yyyyMMddHHmmssSSS";

    // 默认显示日期时间的格式 精确到分钟
    public static final String DATATIMEF_STR_MI = "yyyy-MM-dd HH:mm";

    public static final String DATATIMEF_STR_MDHm = "MM.dd HH:mm";

    public static final String HH_STR = "HH";

    // 精确到秒
    public static final String DATATIMEF_STR_SEC = "yyyyMMddHHmmss";

    // 默认显示简体中文日期的格式
    public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";

    // 默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";

    // 默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";

    // 默认显示月份和日期的格式
    public static final String MONTHANDDATE_STR = "MM.dd";

    public static final String DATATIMEF_STR_MIN = "yyyyMMddHHmm";

    public static final String DATAFORMAT_STR3 = "yyyyMM";

    public static final String DATAFORMAT_STR4 = "yyyyMMdd";

    public static final String HOUR_END = " 23:59:59";

    public static final String HOUR_START = " 00:00:00";

    public static final int NUMBER_VALUE_31 = 31;

    public static final int NUMBER_VALUE_60 = 60;

    public static final int NUMBER_VALUE_1000 = 1000;

    public static final int NUMBER_VALUE_60000 = 60000;

    public static final int NUMBER_VALUE_86400 = 86400;

    public static final int NUMBER_VALUE_3600000 = 3600000;

    public static final int NUMBER_VALUE_11 = 11;

    public static final int NUMBER_VALUE_24 = 24;

    private DateUtil() {
    }

    public static Date now() {
        return Calendar.getInstance(Locale.CHINESE).getTime();
    }

    /**
     * 获取指定时间的凌晨时间
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间的凌晨时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 增加天数
     *
     * @param baseDate
     * @param increaseDate
     * @return
     */
    public static Date yearsAfter(Date baseDate, int increaseDate) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(baseDate);
        calendar.add(Calendar.YEAR, increaseDate);
        return calendar.getTime();
    }

    /**
     * 增加年
     *
     * @param baseDate
     * @param increaseDate
     * @return
     */
    public static Date daysAfter(Date baseDate, int increaseDate) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(baseDate);
        calendar.add(Calendar.DATE, increaseDate);
        return calendar.getTime();
    }

    /**
     * 增加小时
     *
     * @param baseDate
     * @param increaseHours
     * @return
     */
    public static Date hoursAfter(Date baseDate, int increaseHours) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(baseDate);
        calendar.add(Calendar.HOUR_OF_DAY, increaseHours);
        return calendar.getTime();
    }

    /**
     * 增加分钟
     *
     * @param baseDate
     * @param increaseMinute
     * @return
     */
    public static Date minuteAfter(Date baseDate, int increaseMinute) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(baseDate);
        calendar.add(Calendar.MINUTE, increaseMinute);
        return calendar.getTime();
    }

    /**
     * 增加月份
     *
     * @param baseDate
     * @param increaseMonths
     * @return
     */
    public static Date monthAfter(Date baseDate, int increaseMonths) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(baseDate);
        calendar.add(Calendar.MONTH, increaseMonths);
        return calendar.getTime();
    }

    /**
     * 当前时间增加天数
     *
     * @param d
     * @param days
     * @return
     */
    public static Date getInternalDateByDay(Date d, int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DATE, days);
        return now.getTime();
    }

    /**
     * 当前时间增加分钟数
     *
     * @param d
     * @param minutes
     * @return
     */
    public static Date getInternalDateByMinute(Date d, int minutes) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MINUTE, minutes);
        return now.getTime();
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date
     * @return
     */
    public static String dateToDateString(Date date) {
        return dateToDateString(date, DATATIMEF_STR);
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd”的字符串
     *
     * @param date
     * @return
     */
    public static String dateToDayString(Date date) {
        return dateToDateString(date, DATAFORMAT_STR);
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date
     * @return
     */
    public static String dateToDateString2(Date date) {
        return dateToDateString(date, DATATIMEF_STR2);
    }

    /**
     * 将Date转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr) {
        if (date == null) {
            return null;
        }
        java.text.DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
     *
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date getDate(String dateTimeStr, String formatStr) {
        try {
            if (dateTimeStr == null || dateTimeStr.equals("")) {
                return null;
            }
            java.text.DateFormat sdf = new SimpleDateFormat(formatStr);
            Date d = sdf.parse(dateTimeStr);
            return d;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCurDate() {
        return dateToDateString(Calendar.getInstance().getTime(), DATAFORMAT_STR);
    }

    public static String getCurHour() {
        return dateToDateString(Calendar.getInstance().getTime(), HH_STR);
    }

    /**
     * 当前月份
     *
     * @return
     */
    public static int getThisMonth() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        int month = c.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获取当前月份 是 第几天
     *
     * @return
     */
    public static int getCurrentMonthDay() {
        Calendar ca = Calendar.getInstance();
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static SimpleDateFormat getDateFormat(final String formatStr) {
        return new SimpleDateFormat(formatStr);
    }

    @SuppressWarnings("deprecation")
    public static String getFirstDateOfMonth(Date now) {
        SimpleDateFormat df1 = new SimpleDateFormat(DATATIMEF_STR);
        Date da = new Date(now.getYear(), now.getMonth(), 01);
        return df1.format(da);
    }

    @SuppressWarnings("deprecation")
    public static String getLastDateOfMonth(Date now) {
        SimpleDateFormat df1 = new SimpleDateFormat(DATATIMEF_STR);
        Date da = new Date(now.getYear(), now.getMonth(), NUMBER_VALUE_31);
        return df1.format(da);
    }

    /**
     * 获取前半年/后半年的结束时间
     *
     * @param date
     * @return
     */
    public static Date getHalfYearEndTime(Date date) {
        SimpleDateFormat shortSdf = new SimpleDateFormat(DATAFORMAT_STR);
        SimpleDateFormat longSdf = new SimpleDateFormat(DATATIMEF_STR);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + HOUR_END);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }


    /**
     * 获取两个毫秒间隔的分钟
     *
     * @param t1
     * @param t2
     * @return
     */
    public static int getMinutesBetweenMillis(long t1, long t2) {
        return (int) ((t1 - t2) / (NUMBER_VALUE_60 * NUMBER_VALUE_1000));
    }

    /**
     * 判断目标时间是否处于某一时间段内
     *
     * @param target
     * @param begin
     * @param end
     * @return
     */
    public static boolean compareTargetTime(Date target, String begin, String end) {
        // 格式化时间 暂时不考虑传入参数的判断，其他地方如果要调用，最好扩展判断一下入参问题
        String targetTime = dateToDateString(target, DATATIMEF_STR).substring(NUMBER_VALUE_11);// HH:mm:ss
        if (targetTime.compareTo(begin) >= 0 && end.compareTo(targetTime) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * @param time1
     * @param time2
     * @return time1 小于 time 2 返回 true
     */
    public static boolean compareTime(Date time1, Date time2) {
        if (time1 == null || time2 == null) {
            return false;
        }
        return time1.getTime() < time2.getTime();
    }

    /**
     * @param time1
     * @param time2
     * @return time1 等于 time 2 返回 true
     */
    public static boolean sameDate(Date time1, Date time2) {
        if (time1 == null || time2 == null) {
            return false;
        }
        LocalDate localDate1 = ZonedDateTime.ofInstant(time1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(time2.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    /**
     * 取得两个时间段的时间间隔 return t2 与t1的间隔天数 throws ParseException
     * 如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getBetweenDays(String t1, String t2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c1 = c2;
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    /**
     * 格式化时间 yyyy-MM-dd
     *
     * @return
     */
    public static String getFormatDate(Date date) {
        return new SimpleDateFormat().format(date);
    }

    /**
     * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
     *
     * @param dateTimer
     * @param formatStr
     * @return
     */
    public static Date getFormatDate(Date dateTimer, String formatStr) {
        try {
            if (dateTimer == null) {
                return null;
            }
            java.text.DateFormat sdf = new SimpleDateFormat(formatStr);
            String timeStr = sdf.format(dateTimer);
            Date formateDate = sdf.parse(timeStr);
            return formateDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 对应 日期格式 字符串
     *
     * @param dateTimer
     * @param formatStr
     * @return
     */
    public static String getFormatDateStr(Date dateTimer, String formatStr) {
        try {
            if (dateTimer == null) {
                return null;
            }
            java.text.DateFormat sdf = new SimpleDateFormat(formatStr);
            String timeStr = sdf.format(dateTimer);
            return timeStr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取两个时间之间相差的分钟
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long getQuot(String time1, String time2) {
        long quot = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date1.getTime() - date2.getTime();
            quot = quot / NUMBER_VALUE_1000 / NUMBER_VALUE_60 / NUMBER_VALUE_60 / NUMBER_VALUE_24;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return quot;
    }

    public static long getQuot(Date time1, Date time2) {
        if (time1 == null || time2 == null) {
            return -1;
        }
        long quot = 0;
        quot = time1.getTime() - time2.getTime();
        quot = quot / NUMBER_VALUE_1000 / NUMBER_VALUE_60;
        return quot;
    }

    /**
     * 获取和当前时间相差的分钟数
     *
     * @param begin
     * @return
     */
    public static long getDiffenceValue(Date begin) {
        long value;
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        value = (begin.getTime() - now.getTime()) / NUMBER_VALUE_1000 / NUMBER_VALUE_60;
        return value;
    }

    public static long getMillsBetweenTwoDate(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }

    /**
     * 求多少天前/后的日期
     *
     * @param field 单位：年，月，日
     * @param day   多少天
     * @return
     */
    public static Date addDate(int field, int day) {
        Calendar nowCalendar = Calendar.getInstance(Locale.CHINESE);
        nowCalendar.setTime(DateUtil.now());
        nowCalendar.add(field, day);
        return nowCalendar.getTime();
    }

    /**
     * 求多少秒前/后的日期
     *
     * @param second 多少秒
     * @return
     */
    public static Date addDateSecond(int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }


    /**
     * 获取本月第一天
     *
     * @return
     */
    public static String getCurrFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(c.getTime());
        return first;
    }

    /**
     * 获取本月最后一天
     *
     * @return
     */
    public static String getCurrLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return last;
    }

    /**
     * 获取指定月第一天
     *
     * @return
     */
    public static Date getAssignCurrFirstDay(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(c.getTime());
        return format.parse(first);
    }

    /**
     * 获取指定月最后一天
     *
     * @return
     */
    public static Date getAssignCurrLastDay(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return format.parse(last);
    }

    public static Date startOneDay(Date date) {

        try {
            String halfFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(halfFormat + HOUR_START);
        } catch (ParseException e) {
            return date;
        }
    }

    public static Date endOneDay(Date date) {

        try {
            String halfFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(halfFormat + HOUR_END);
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        String last = format.format(ca.getTime());
        return last;
    }

    /**
     * 获取当前时间的秒数
     *
     * @param calendar
     * @return
     */
    public static long getFragmentInSeconds(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }

        long result = 0;

        result += (calendar.get(Calendar.HOUR_OF_DAY) * NUMBER_VALUE_3600000) / NUMBER_VALUE_1000;
        result += (calendar.get(Calendar.MINUTE) * NUMBER_VALUE_60000) / NUMBER_VALUE_1000;
        result += (calendar.get(Calendar.SECOND) * NUMBER_VALUE_1000) / NUMBER_VALUE_1000;
        result += (calendar.get(Calendar.MILLISECOND) * 1) / NUMBER_VALUE_1000;

        return result;
    }

    /**
     * 从现在到凌晨还剩下多少时间(秒数)
     *
     * @param calendar
     * @return
     */
    public static long getDistanceSeconds(Calendar calendar) {
        return NUMBER_VALUE_86400 - getFragmentInSeconds(calendar);
    }

    public static long getRemainingSecondsFromNowToDayOff() {
        return NUMBER_VALUE_86400 - getFragmentInSeconds(Calendar.getInstance());
    }

    /**
     * 从现在到月底还剩下多少时间(秒数)
     *
     * @param c
     * @return
     * @Description
     * @author qianbao
     */

    public static long getDistanceMonthSeconds(Calendar c) {
        int d = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = c.get(Calendar.DAY_OF_MONTH);

        int a = (d - now) * NUMBER_VALUE_24 * NUMBER_VALUE_60 * NUMBER_VALUE_60;

        return a + getDistanceSeconds(c);
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取指定年份
     *
     * @return
     */
    public static Integer getYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return Integer.valueOf(sdf.format(date));
    }

    /**
     * 获取两个时间月份之差
     *
     * @param str1
     * @param str2
     * @return
     */
    public static Integer getMonthSpace(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
            bef.setTime(sdf.parse(str1));
            aft.setTime(sdf.parse(str2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result) + 1;
    }

    /**
     * 日期转换(chinapay支付使用)
     *
     * @param dateStr
     * @return
     */
    public static Date formatTime(String dateStr) {
        if (null == dateStr) {
            return null;
        }
        if (dateStr.length() == 14) {
            try {
                return new SimpleDateFormat("yyyyMMddHHmmss").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                Date date = new SimpleDateFormat("yyyyMMdd").parse(dateStr);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 日期转换
     *
     * @param dateStr
     * @return
     */
    public static Date getDate(String dateStr) {
        if (null == dateStr) {
            return null;
        }
        if (dateStr.length() > 14) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * 是否为 闰年
     *
     * @param year
     * @return
     */
    public static boolean isRunYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getStringFeeMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        StringBuffer result = new StringBuffer();
        result.append(year).append("年");
        try {
            if (month >= 1 && month <= 6) {
                result.append("1-6月份");
            } else if (month >= 7 && month <= 12) {
                result.append("7-12月份");
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateByString(String dateStr) {
        if (null == dateStr) {
            return null;
        }
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int year = c.get(Calendar.YEAR);
            return new SimpleDateFormat(DATAFORMAT_STR).parse(year + "-" + dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getBetweenDays(Date startTime, Date endTime) {
        long start = startTime.getTime();
        long end = endTime.getTime();
        return (int) (Math.abs(end - start) / (24 * 3600 * 1000));
    }

    /**
     * 比较两个时间相差秒数
     *
     * @param startDate 开始时间（小）
     * @param endDate   结束时间 （大）
     * @return
     */
    public static int differTimeSecond(Date startDate, Date endDate) {
        long end = endDate.getTime();
        long start = startDate.getTime();
        return (int) (end - start) / 1000;
    }

    /**
     * 计算两时间相差小时数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getBetweenHours(Date startTime, Date endTime) {
        long start = startTime.getTime();
        long end = endTime.getTime();
        return (int) (Math.abs(end - start) / (1000 * 60 * 60));
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //不同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //同一年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static int getSecondTimestampTwo(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    /**
     * 支付获取订单超时时间
     *
     * @param overTime
     * @return
     */
    public static String getOverTime(Date overTime) {
        if (Objects.nonNull(overTime)) {
            long diff = getDiffenceValue(overTime);
            if (diff > 0) {
                return DateUtil.getFormatDateStr(DateUtil.minuteAfter(DateUtil.now(), diff > 60 ? 60 : (int) diff), DateUtil.DATATIMEF_STR_SEC);
            }
        }
        return null;
    }
}
