
package com.qihoo.wzws.rzb.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateUtil {
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int DATE = 5;
    public static final int HOUR = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int MILLISECOND = 14;


    public static String formatDate(Date date) {

        if (date == null) {
            date = now();
        }

        return formatDate(date, "yyyy-MM-dd");

    }


    public static String formatDateTime(Date date) {

        if (date == null) {
            date = now();
        }

        return formatDate(date, "yyyy-MM-dd HH:mm:ss");

    }


    public static String formatTime(Date date) {

        if (date == null)
            date = now();

        return formatDate(date, "HH:mm:ss");

    }


    public static String formatDate(Date date, String pattern) {

        if (date == null) {

            date = now();

        }

        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);

    }


    public static Date parseDate(String date) {

        if (date == null) {
            return now();
        }

        return parseDate(date, "yyyy-MM-dd");

    }


    public static Date parseDateTime(String datetime) {

        if (datetime == null) {
            return now();
        }

        return parseDate(datetime, "yyyy-MM-dd HH:mm:ss");

    }


    public static Date parseDate(Date datetime) {

        if (datetime == null) {
            return now();
        }

        return parseDate(datetime, "yyyy-MM-dd");

    }


    public static Date parseDateTime(Date datetime) {

        if (datetime == null) {
            return now();
        }

        return parseDate(datetime, "yyyy-MM-dd  HH:mm:ss");

    }


    public static Date parseDate(Date datetime, String pattern) {

        if (datetime == null) {
            return now();
        }

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        try {

            return formatter.parse(formatter.format(datetime));

        } catch (ParseException e) {

            return null;

        }

    }


    public static Date parseDate(String date, String pattern) {

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);


        if (date == null || date.equals("")) {

            return now();

        }

        try {

            return formatter.parse(date);

        } catch (ParseException e) {

            return null;

        }

    }


    public static String formatDate() {

        return formatDate(now(), "yyyy-MM-dd");

    }


    public static String formatDateHour() {

        return formatDate(now(), "yyyyMMddHH");

    }


    public static String formatDateTime() {

        return formatDate(now(), "yyyy-MM-dd HH:mm:ss");

    }


    public static String formatTime() {

        return formatDate(now(), "HH:mm:ss");

    }


    public static String formatDate(String patten) {

        return formatDate(now(), patten);

    }


    public static Date now() {

        return new Date();

    }


    public static Date nowDate() {

        return parseDate(formatDate());

    }


    public static Date nowDateTime() {

        return parseDateTime(formatDateTime());

    }


    public static Date add(Date date, int field, int amount) {

        if (date == null) {

            date = now();

        }

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(field, amount);


        return cal.getTime();

    }


    public static Date addDay(Date date, int amount) {

        return add(date, 5, amount);

    }


    public static Date addMonth(Date date, int amount) {

        return add(date, 2, amount);

    }


    public static Date addHour(Date date, int amount) {

        return add(date, 11, amount);

    }


    public static Date preMonth(Date date) {

        return add(date, 2, -1);

    }


    public static Date nextMonth(Date date) {

        return add(date, 2, 1);

    }


    public static Date preDay(Date date) {

        return add(date, 5, -1);

    }


    public static Date nextDay(Date date) {

        return add(date, 5, 1);

    }


    public static Date addDate(String dateStr, int field, int amount) {

        Date date = parseDate(dateStr);

        if (date == null) {

            date = now();

        }

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(field, amount);


        return cal.getTime();

    }


    public static Date addDate(Date date, int field, int amount) {

        return addDate(formatDate(date), field, amount);

    }


    public static Date addDateTime(String dateStr, int field, int amount) {

        Date date = parseDateTime(dateStr);


        if (date == null) {

            date = now();

        }

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(field, amount);


        return cal.getTime();

    }


    public static Date addDateTime(Date date, int field, int amount) {

        return addDateTime(formatDateTime(date), field, amount);

    }


    public static int diffDate(int i, Date k, Date d) {

        int diffnum = 0;

        int needdiff = 0;

        switch (i) {

            case 13:

                needdiff = 1000;

                break;


            case 12:

                needdiff = 60000;

                break;


            case 11:

                needdiff = 3600000;

                break;


            case 5:

                needdiff = 86400000;

                break;

        }


        if (needdiff != 0) {

            diffnum = (int) (d.getTime() / needdiff) - (int) (k.getTime() / needdiff);

        }


        return diffnum;

    }


    public static Date beginningOfDay(Date date) {

        if (date == null)
            date = now();

        return parseDate(date);

    }


    public static Date endOfDay(Date date) {

        if (date == null)
            date = now();

        return addDate(nextDay(parseDate(date)), 13, -1);

    }


    public static Date getLastDateByMonth() {

        return getLastDateByMonth(new Date());

    }


    public static Date getLastDateByMonth(Date date) {

        if (date == null)
            date = now();

        Calendar now = Calendar.getInstance();

        now.setTime(date);

        now.set(2, now.get(2) + 1);

        now.set(5, 1);

        now.set(5, now.get(5) - 1);

        now.set(11, 0);

        now.set(12, 0);

        now.set(13, 0);

        return now.getTime();

    }


    public static Date getFirstDateByMonth() {

        return getFirstDateByMonth(new Date());

    }


    public static Date getFirstDateByMonth(Date d) {

        if (d == null)
            d = now();

        Calendar now = Calendar.getInstance();

        now.setTime(d);

        now.set(5, 1);

        now.set(11, 0);

        now.set(12, 0);

        now.set(13, 0);

        return now.getTime();

    }


    public static Date getWeekDay(Date date, int k) {

        if (date == null)
            date = now();

        Calendar c = Calendar.getInstance();

        c.setTime(date);

        c.set(7, k);

        c.set(11, 0);

        c.set(12, 0);

        c.set(13, 0);

        return c.getTime();

    }


    public static Date getUtilDateFromSql(Date paraDate) {

        return new Date(paraDate.getTime());

    }


    public static Timestamp getSqlDateFromUtil(Date paraDate) {

        if (paraDate == null)
            return null;

        String dateFormat = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return Timestamp.valueOf(sdf.format(paraDate));

    }


    public static String formatDate(Object o) {

        if (o == null)
            return "";

        if (o.getClass() == String.class)
            return formatDate((String) o);

        if (o.getClass() == Date.class)
            return formatDate((Date) o);

        if (o.getClass() == Timestamp.class) {

            return formatDate(new Date(((Timestamp) o).getTime()));

        }

        return o.toString();

    }


    public static String formatDateTime(Object o) {

        if (o.getClass() == String.class)
            return formatDateTime(o);

        if (o.getClass() == Date.class)
            return formatDateTime((Date) o);

        if (o.getClass() == Timestamp.class) {

            return formatDateTime(new Date(((Timestamp) o).getTime()));

        }

        return o.toString();

    }


    public static List<String> getDateZone(String fromdate, String todate) {

        String format = "yyyy-MM-dd";

        List<String> dateZone = new ArrayList<String>();


        long timediff = 86400000L;

        dateZone.add(fromdate);

        Date nowdate = null;

        Date fd = parseDate(fromdate, format);

        Date td = parseDate(todate, format);

        nowdate = new Date(fd.getTime() + timediff);


        while (nowdate.getTime() < td.getTime()) {

            dateZone.add(formatDate(nowdate, format));

            nowdate = new Date(nowdate.getTime() + timediff);

        }

        dateZone.add(todate);


        return dateZone;

    }


    public static List<String> getDateZone(Date fromdate, Date todate) {

        return getDateZone(formatDate(fromdate), formatDate(todate));

    }


    public static List<String> getHourZone(String fromdate, String todate) {

        String format = "yyyy-MM-dd HH:mm:ss";

        List<String> dateZone = new ArrayList<String>();


        long timediff = 3600000L;

        dateZone.add(fromdate);

        SimpleDateFormat sf = new SimpleDateFormat(format);

        Date nowdate = null;

        Date fd = null;

        Date td = null;

        try {

            fd = sf.parse(fromdate);

            td = sf.parse(todate);

        } catch (ParseException e) {

            return null;

        }

        nowdate = new Date(fd.getTime() + timediff);


        while (nowdate.getTime() < td.getTime()) {

            dateZone.add(sf.format(nowdate));

            nowdate = new Date(nowdate.getTime() + timediff);

        }


        dateZone.add(todate);


        return dateZone;

    }


    public static List<String> getHourZone(Date fromdate, Date todate) {

        return getHourZone(formatDate(fromdate), formatDate(todate));

    }


    public static int getDatePart(Date date, int datepart) {

        if (date == null)
            date = now();

        Calendar c = Calendar.getInstance();

        c.setTime(date);

        return c.get(datepart);

    }


    public static int getYear(Date date) {

        return getDatePart(date, 1);

    }


    public static int getMonth(Date date) {

        return getDatePart(date, 2);

    }


    public static int getDate(Date date) {

        return getDatePart(date, 5);

    }


    public static int getHour(Date date) {

        return getDatePart(date, 11);

    }


    public static int getMinute(Date date) {

        return getDatePart(date, 12);

    }


    public static int getSecond(Date date) {

        return getDatePart(date, 13);

    }


    public static Date stringToDate(String time) {

        time = time.trim();


        int tempPos = time.indexOf("AD");

        time = time.trim();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");

        if (tempPos > -1) {

            time = time.substring(0, tempPos) + "公元" + time.substring(tempPos + "AD".length());


            formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");

        }

        tempPos = time.indexOf("-");

        if (tempPos > -1 && time.indexOf(" ") < 0) {

            formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");

        } else if (time.matches("\\d{14}")) {

            formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        } else if (time.indexOf("/") > -1 && time.indexOf(" ") > -1) {

            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        } else if (time.indexOf("-") > -1 && time.indexOf(" ") > -1) {

            if (time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else if (time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            } else if (time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3}")) {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            }

        } else if ((time.indexOf("/") > -1 && time.indexOf("am") > -1) || time.indexOf("pm") > -1) {


            formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");

        } else if ((time.indexOf("-") > -1 && time.indexOf("am") > -1) || time.indexOf("pm") > -1) {


            formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");

        }

        ParsePosition pos = new ParsePosition(0);

        Date ctime = formatter.parse(time, pos);


        return ctime;

    }


    public static String formatStrDate(String strDate, DateFormat df) {

        if (strDate == null || strDate.isEmpty()) {

            return null;

        }


        Date date = null;

        try {

            date = df.parse(strDate);

        } catch (ParseException e) {

            e.printStackTrace();

        }


        return formatDateTime(date);

    }


    public static void main(String[] args) {
    }

}