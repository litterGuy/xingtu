/*     */
package com.qihoo.wzws.rzb.util;
/*     */
/*     */

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class DateUtil
        /*     */ {
    /*     */   public static final int YEAR = 1;
    /*     */   public static final int MONTH = 2;
    /*     */   public static final int DATE = 5;
    /*     */   public static final int HOUR = 11;
    /*     */   public static final int MINUTE = 12;
    /*     */   public static final int SECOND = 13;
    /*     */   public static final int MILLISECOND = 14;

    /*     */
    /*     */
    public static String formatDate(Date date) {
        /*  45 */
        if (date == null)
            /*  46 */ {
            date = now();
        }
        /*  47 */
        return formatDate(date, "yyyy-MM-dd");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatDateTime(Date date) {
        /*  57 */
        if (date == null)
            /*  58 */ {
            date = now();
        }
        /*  59 */
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatTime(Date date) {
        /*  69 */
        if (date == null)
            /*  70 */ date = now();
        /*  71 */
        return formatDate(date, "HH:mm:ss");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatDate(Date date, String pattern) {
        /*  86 */
        if (date == null) {
            /*  87 */
            date = now();
            /*     */
        }
        /*  89 */
        if (pattern == null)
            /*  90 */ {
            pattern = "yyyy-MM-dd";
        }
        /*  91 */
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        /*  92 */
        return sdf.format(date);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date parseDate(String date) {
        /* 105 */
        if (date == null)
            /* 106 */ {
            return now();
        }
        /* 107 */
        return parseDate(date, "yyyy-MM-dd");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date parseDateTime(String datetime) {
        /* 117 */
        if (datetime == null)
            /* 118 */ {
            return now();
        }
        /* 119 */
        return parseDate(datetime, "yyyy-MM-dd HH:mm:ss");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date parseDate(Date datetime) {
        /* 132 */
        if (datetime == null)
            /* 133 */ {
            return now();
        }
        /* 134 */
        return parseDate(datetime, "yyyy-MM-dd");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date parseDateTime(Date datetime) {
        /* 147 */
        if (datetime == null)
            /* 148 */ {
            return now();
        }
        /* 149 */
        return parseDate(datetime, "yyyy-MM-dd  HH:mm:ss");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date parseDate(Date datetime, String pattern) {
        /* 162 */
        if (datetime == null)
            /* 163 */ {
            return now();
        }
        /* 164 */
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        /*     */
        try {
            /* 166 */
            return formatter.parse(formatter.format(datetime));
            /* 167 */
        } catch (ParseException e) {
            /* 168 */
            return null;
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date parseDate(String date, String pattern) {
        /* 182 */
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        /*     */
        /* 184 */
        if (date == null || date.equals("")) {
            /* 185 */
            return now();
            /*     */
        }
        /*     */
        try {
            /* 188 */
            return formatter.parse(date);
            /* 189 */
        } catch (ParseException e) {
            /* 190 */
            return null;
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatDate() {
        /* 206 */
        return formatDate(now(), "yyyy-MM-dd");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatDateHour() {
        /* 217 */
        return formatDate(now(), "yyyyMMddHH");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatDateTime() {
        /* 228 */
        return formatDate(now(), "yyyy-MM-dd HH:mm:ss");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatTime() {
        /* 239 */
        return formatDate(now(), "HH:mm:ss");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatDate(String patten) {
        /* 250 */
        return formatDate(now(), patten);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date now() {
        /* 259 */
        return new Date();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date nowDate() {
        /* 270 */
        return parseDate(formatDate());
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date nowDateTime() {
        /* 281 */
        return parseDateTime(formatDateTime());
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date add(Date date, int field, int amount) {
        /* 306 */
        if (date == null) {
            /* 307 */
            date = now();
            /*     */
        }
        /* 309 */
        Calendar cal = Calendar.getInstance();
        /* 310 */
        cal.setTime(date);
        /* 311 */
        cal.add(field, amount);
        /*     */
        /* 313 */
        return cal.getTime();
        /*     */
    }

    /*     */
    /*     */
    public static Date addDay(Date date, int amount) {
        /* 317 */
        return add(date, 5, amount);
        /*     */
    }

    /*     */
    /*     */
    public static Date addMonth(Date date, int amount) {
        /* 321 */
        return add(date, 2, amount);
        /*     */
    }

    /*     */
    /*     */
    public static Date addHour(Date date, int amount) {
        /* 325 */
        return add(date, 11, amount);
        /*     */
    }

    /*     */
    /*     */
    public static Date preMonth(Date date) {
        /* 329 */
        return add(date, 2, -1);
        /*     */
    }

    /*     */
    /*     */
    public static Date nextMonth(Date date) {
        /* 333 */
        return add(date, 2, 1);
        /*     */
    }

    /*     */
    /*     */
    public static Date preDay(Date date) {
        /* 337 */
        return add(date, 5, -1);
        /*     */
    }

    /*     */
    /*     */
    public static Date nextDay(Date date) {
        /* 341 */
        return add(date, 5, 1);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date addDate(String dateStr, int field, int amount) {
        /* 354 */
        Date date = parseDate(dateStr);
        /* 355 */
        if (date == null) {
            /* 356 */
            date = now();
            /*     */
        }
        /* 358 */
        Calendar cal = Calendar.getInstance();
        /* 359 */
        cal.setTime(date);
        /* 360 */
        cal.add(field, amount);
        /*     */
        /* 362 */
        return cal.getTime();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date addDate(Date date, int field, int amount) {
        /* 376 */
        return addDate(formatDate(date), field, amount);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date addDateTime(String dateStr, int field, int amount) {
        /* 390 */
        Date date = parseDateTime(dateStr);
        /*     */
        /* 392 */
        if (date == null) {
            /* 393 */
            date = now();
            /*     */
        }
        /* 395 */
        Calendar cal = Calendar.getInstance();
        /* 396 */
        cal.setTime(date);
        /* 397 */
        cal.add(field, amount);
        /*     */
        /* 399 */
        return cal.getTime();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date addDateTime(Date date, int field, int amount) {
        /* 413 */
        return addDateTime(formatDateTime(date), field, amount);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static int diffDate(int i, Date k, Date d) {
        /* 434 */
        int diffnum = 0;
        /* 435 */
        int needdiff = 0;
        /* 436 */
        switch (i) {
            /*     */
            case 13:
                /* 438 */
                needdiff = 1000;
                /*     */
                break;
            /*     */
            /*     */
            case 12:
                /* 442 */
                needdiff = 60000;
                /*     */
                break;
            /*     */
            /*     */
            case 11:
                /* 446 */
                needdiff = 3600000;
                /*     */
                break;
            /*     */
            /*     */
            case 5:
                /* 450 */
                needdiff = 86400000;
                /*     */
                break;
            /*     */
        }
        /*     */
        /* 454 */
        if (needdiff != 0) {
            /* 455 */
            diffnum = (int) (d.getTime() / needdiff) - (int) (k.getTime() / needdiff);
            /*     */
        }
        /*     */
        /*     */
        /*     */
        /* 460 */
        return diffnum;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date beginningOfDay(Date date) {
        /* 476 */
        if (date == null)
            /* 477 */ date = now();
        /* 478 */
        return parseDate(date);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date endOfDay(Date date) {
        /* 490 */
        if (date == null)
            /* 491 */ date = now();
        /* 492 */
        return addDate(nextDay(parseDate(date)), 13, -1);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date getLastDateByMonth() {
        /* 499 */
        return getLastDateByMonth(new Date());
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date getLastDateByMonth(Date date) {
        /* 506 */
        if (date == null)
            /* 507 */ date = now();
        /* 508 */
        Calendar now = Calendar.getInstance();
        /* 509 */
        now.setTime(date);
        /* 510 */
        now.set(2, now.get(2) + 1);
        /* 511 */
        now.set(5, 1);
        /* 512 */
        now.set(5, now.get(5) - 1);
        /* 513 */
        now.set(11, 0);
        /* 514 */
        now.set(12, 0);
        /* 515 */
        now.set(13, 0);
        /* 516 */
        return now.getTime();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date getFirstDateByMonth() {
        /* 523 */
        return getFirstDateByMonth(new Date());
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date getFirstDateByMonth(Date d) {
        /* 530 */
        if (d == null)
            /* 531 */ d = now();
        /* 532 */
        Calendar now = Calendar.getInstance();
        /* 533 */
        now.setTime(d);
        /* 534 */
        now.set(5, 1);
        /* 535 */
        now.set(11, 0);
        /* 536 */
        now.set(12, 0);
        /* 537 */
        now.set(13, 0);
        /* 538 */
        return now.getTime();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date getWeekDay(Date date, int k) {
        /* 549 */
        if (date == null)
            /* 550 */ date = now();
        /* 551 */
        Calendar c = Calendar.getInstance();
        /* 552 */
        c.setTime(date);
        /* 553 */
        c.set(7, k);
        /* 554 */
        c.set(11, 0);
        /* 555 */
        c.set(12, 0);
        /* 556 */
        c.set(13, 0);
        /* 557 */
        return c.getTime();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date getUtilDateFromSql(Date paraDate) {
        /* 570 */
        return new Date(paraDate.getTime());
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Timestamp getSqlDateFromUtil(Date paraDate) {
        /* 580 */
        if (paraDate == null)
            /* 581 */ return null;
        /* 582 */
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        /* 583 */
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        /* 584 */
        return Timestamp.valueOf(sdf.format(paraDate));
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatDate(Object o) {
        /* 595 */
        if (o == null)
            /* 596 */ return "";
        /* 597 */
        if (o.getClass() == String.class)
            /* 598 */ return formatDate((String) o);
        /* 599 */
        if (o.getClass() == Date.class)
            /* 600 */ return formatDate((Date) o);
        /* 601 */
        if (o.getClass() == Timestamp.class) {
            /* 602 */
            return formatDate(new Date(((Timestamp) o).getTime()));
            /*     */
        }
        /* 604 */
        return o.toString();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String formatDateTime(Object o) {
        /* 615 */
        if (o.getClass() == String.class)
            /* 616 */ return formatDateTime(o);
        /* 617 */
        if (o.getClass() == Date.class)
            /* 618 */ return formatDateTime((Date) o);
        /* 619 */
        if (o.getClass() == Timestamp.class) {
            /* 620 */
            return formatDateTime(new Date(((Timestamp) o).getTime()));
            /*     */
        }
        /* 622 */
        return o.toString();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static List<String> getDateZone(String fromdate, String todate) {
        /* 638 */
        String format = "yyyy-MM-dd";
        /* 639 */
        List<String> dateZone = new ArrayList<String>();
        /*     */
        /* 641 */
        long timediff = 86400000L;
        /* 642 */
        dateZone.add(fromdate);
        /* 643 */
        Date nowdate = null;
        /* 644 */
        Date fd = parseDate(fromdate, format);
        /* 645 */
        Date td = parseDate(todate, format);
        /* 646 */
        nowdate = new Date(fd.getTime() + timediff);
        /*     */
        /* 648 */
        while (nowdate.getTime() < td.getTime()) {
            /* 649 */
            dateZone.add(formatDate(nowdate, format));
            /* 650 */
            nowdate = new Date(nowdate.getTime() + timediff);
            /*     */
        }
        /* 652 */
        dateZone.add(todate);
        /*     */
        /* 654 */
        return dateZone;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static List<String> getDateZone(Date fromdate, Date todate) {
        /* 667 */
        return getDateZone(formatDate(fromdate), formatDate(todate));
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static List<String> getHourZone(String fromdate, String todate) {
        /* 680 */
        String format = "yyyy-MM-dd HH:mm:ss";
        /* 681 */
        List<String> dateZone = new ArrayList<String>();
        /*     */
        /* 683 */
        long timediff = 3600000L;
        /* 684 */
        dateZone.add(fromdate);
        /* 685 */
        SimpleDateFormat sf = new SimpleDateFormat(format);
        /* 686 */
        Date nowdate = null;
        /* 687 */
        Date fd = null;
        /* 688 */
        Date td = null;
        /*     */
        try {
            /* 690 */
            fd = sf.parse(fromdate);
            /* 691 */
            td = sf.parse(todate);
            /* 692 */
        } catch (ParseException e) {
            /* 693 */
            return null;
            /*     */
        }
        /* 695 */
        nowdate = new Date(fd.getTime() + timediff);
        /*     */
        /* 697 */
        while (nowdate.getTime() < td.getTime()) {
            /* 698 */
            dateZone.add(sf.format(nowdate));
            /* 699 */
            nowdate = new Date(nowdate.getTime() + timediff);
            /*     */
        }
        /*     */
        /* 702 */
        dateZone.add(todate);
        /*     */
        /* 704 */
        return dateZone;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static List<String> getHourZone(Date fromdate, Date todate) {
        /* 709 */
        return getHourZone(formatDate(fromdate), formatDate(todate));
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static int getDatePart(Date date, int datepart) {
        /* 722 */
        if (date == null)
            /* 723 */ date = now();
        /* 724 */
        Calendar c = Calendar.getInstance();
        /* 725 */
        c.setTime(date);
        /* 726 */
        return c.get(datepart);
        /*     */
    }

    /*     */
    /*     */
    public static int getYear(Date date) {
        /* 730 */
        return getDatePart(date, 1);
        /*     */
    }

    /*     */
    /*     */
    public static int getMonth(Date date) {
        /* 734 */
        return getDatePart(date, 2);
        /*     */
    }

    /*     */
    /*     */
    public static int getDate(Date date) {
        /* 738 */
        return getDatePart(date, 5);
        /*     */
    }

    /*     */
    /*     */
    public static int getHour(Date date) {
        /* 742 */
        return getDatePart(date, 11);
        /*     */
    }

    /*     */
    /*     */
    public static int getMinute(Date date) {
        /* 746 */
        return getDatePart(date, 12);
        /*     */
    }

    /*     */
    /*     */
    public static int getSecond(Date date) {
        /* 750 */
        return getDatePart(date, 13);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static Date stringToDate(String time) {
        /* 769 */
        time = time.trim();
        /*     */
        /* 771 */
        int tempPos = time.indexOf("AD");
        /* 772 */
        time = time.trim();
        /* 773 */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        /* 774 */
        if (tempPos > -1) {
            /* 775 */
            time = time.substring(0, tempPos) + "公元" + time.substring(tempPos + "AD".length());
            /*     */
            /* 777 */
            formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
            /*     */
        }
        /* 779 */
        tempPos = time.indexOf("-");
        /* 780 */
        if (tempPos > -1 && time.indexOf(" ") < 0) {
            /* 781 */
            formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
            /* 782 */
        } else if (time.matches("\\d{14}")) {
            /* 783 */
            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            /* 784 */
        } else if (time.indexOf("/") > -1 && time.indexOf(" ") > -1) {
            /* 785 */
            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            /* 786 */
        } else if (time.indexOf("-") > -1 && time.indexOf(" ") > -1) {
            /* 787 */
            if (time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"))
                /* 788 */ {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            /* 789 */
            else if (time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}"))
                /* 790 */ {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            }
            /* 791 */
            else if (time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3}"))
                /* 792 */ {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            }
            /* 793 */
        } else if ((time.indexOf("/") > -1 && time.indexOf("am") > -1) || time.indexOf("pm") > -1) {
            /*     */
            /* 795 */
            formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
            /* 796 */
        } else if ((time.indexOf("-") > -1 && time.indexOf("am") > -1) || time.indexOf("pm") > -1) {
            /*     */
            /* 798 */
            formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
            /*     */
        }
        /* 800 */
        ParsePosition pos = new ParsePosition(0);
        /* 801 */
        Date ctime = formatter.parse(time, pos);
        /*     */
        /* 803 */
        return ctime;
        /*     */
    }

    /*     */
    /*     */
    public static String formatStrDate(String strDate, DateFormat df) {
        /* 807 */
        if (strDate == null || strDate.isEmpty()) {
            /* 808 */
            return null;
            /*     */
        }
        /*     */
        /* 811 */
        Date date = null;
        /*     */
        try {
            /* 813 */
            date = df.parse(strDate);
            /* 814 */
        } catch (ParseException e) {
            /* 815 */
            e.printStackTrace();
            /*     */
        }
        /*     */
        /* 818 */
        return formatDateTime(date);
        /*     */
    }

    /*     */
    /*     */
    public static void main(String[] args) {
    }
    /*     */
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\DateUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */