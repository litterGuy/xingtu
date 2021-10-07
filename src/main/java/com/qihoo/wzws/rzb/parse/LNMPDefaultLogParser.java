
package com.qihoo.wzws.rzb.parse;


import com.qihoo.wzws.rzb.util.DateUtil;
import com.qihoo.wzws.rzb.util.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LNMPDefaultLogParser {
    private static int ipIndex = -1;
    private static int datetimeIndex = -1;
    private static int requestUrlIndex = -1;
    private static int reponseCodeIndex = -1;
    private static int contentLengthIndex = -1;
    private static int refererIndex = -1;
    private static int uaIndex = -1;


    private static Pattern regexIp = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) .* (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})?");
    private static Pattern regexDateTime = Pattern.compile("((\\d{2}.\\w{3}.\\d{4}|\\d{4}.\\d{2}.\\d{2}).\\d{2}\\:\\d{2}\\:\\d{2})\\s");
    private static String rengxDate = new String("\\d{2}.\\w{3}.\\d{4}.\\d{2}\\:\\d{2}\\:\\d{2}");

    private static Pattern regexUrl = Pattern.compile(" \\w{3,5} (.*) HTTP/\\d.\\d ");
    private static Pattern regexStatusAContentLength = Pattern.compile(" (\\d{3}) (\\d{1,}+) ");
    private static Pattern regexRefferer = Pattern.compile(" (http\\S+) ");
    private static Pattern regexUserAgent = Pattern.compile(" (\\w{5,}/[.\\w]+ \\(.*\\).*)");


    public static boolean mattcherLNMPLogList(List<String> list) {

        if (list == null || list.size() == 0) {

            return false;

        }


        String first = Utils.getLongestFromList(list);

        ApacheDefaultFormatParser.FormatType type = null;


        if (mattcherLNMPLog(first)) {

            type = ApacheDefaultFormatParser.FormatType.LNMP;

        }


        if (type != null) {

            int sum = 0;

            for (int i = 0; i < list.size(); i++) {

                if (mattcherLNMPLog(list.get(i))) {

                    sum++;

                }

            }


            if (sum > list.size() / 2) {

                return true;

            }

        }


        return false;

    }


    public static boolean mattcherLNMPLog(String line) {

        Matcher m_ip = regexIp.matcher(line);

        if (m_ip.find() && m_ip.groupCount() >= 1 && m_ip.group(1) != null) {


            ipIndex = 0;

        }


        Matcher m_datetime = regexDateTime.matcher(line);

        if (m_datetime.find() && m_datetime.groupCount() >= 1 && m_datetime.group(1) != null) {


            datetimeIndex = 0;

        }


        Matcher m_url = regexUrl.matcher(line);

        if (m_url.find() && m_url.groupCount() >= 1) {


            requestUrlIndex = 0;

        }


        Matcher m_statusAcontentLength = regexStatusAContentLength.matcher(line);

        if (m_statusAcontentLength.find() && m_statusAcontentLength.groupCount() >= 2 && m_statusAcontentLength.group(1) != null && m_statusAcontentLength.group(2) != null) {


            reponseCodeIndex = 0;

            contentLengthIndex = 0;

        }


        Matcher m_referer = regexRefferer.matcher(line);

        if (m_referer.find() && m_referer.groupCount() >= 1 && m_referer.group(1) != null) {


            refererIndex = 0;

        }


        Matcher m_ua = regexUserAgent.matcher(line);

        if (m_ua.find() && m_ua.groupCount() >= 1 && m_ua.group(1) != null) {


            uaIndex = 0;

        }


        if (datetimeIndex > -1 && ipIndex > -1 && requestUrlIndex > -1 && reponseCodeIndex > -1 && contentLengthIndex > -1) {

            return true;

        }


        return false;

    }


    public static String parseLNMPLog(String line, String host) {

        StringBuilder sb = new StringBuilder();


        Matcher m_ip = regexIp.matcher(line);

        if (m_ip.find() && m_ip.groupCount() >= 1) {

            if (m_ip.groupCount() >= 2 && m_ip.group(2) != null) {


                sb.append(Utils.getRemoteIp(m_ip.group(2)));

            } else {


                sb.append(Utils.getRemoteIp(m_ip.group(1)));

            }

        }


        sb.append("\t");


        Matcher m_datetime = regexDateTime.matcher(line);

        if (m_datetime.find() && m_datetime.groupCount() >= 1) {

            String a = m_datetime.group(1);

            if (a.matches(rengxDate)) {

                DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);

                sb.append(DateUtil.formatStrDate(a, dateFormatter));

            } else {

                sb.append(a);

            }

        }


        sb.append("\t");

        sb.append(host);

        sb.append("\t");


        Matcher m_url = regexUrl.matcher(line);

        if (m_url.find() && m_url.groupCount() >= 1) {

            sb.append(m_url.group(1));

        }


        sb.append("\t");


        Matcher m_statusAcontentLength = regexStatusAContentLength.matcher(line);

        if (m_statusAcontentLength.find() && m_statusAcontentLength.groupCount() >= 2) {

            sb.append(m_statusAcontentLength.group(1));

            sb.append("\t");

            sb.append(m_statusAcontentLength.group(2));

        }


        Matcher m_referer = regexRefferer.matcher(line);

        if (m_referer.find() && m_referer.groupCount() >= 1) {

            sb.append("\t");

            sb.append(m_statusAcontentLength.group(1));

        } else {


            sb.append("\t");

            sb.append("-");

        }


        Matcher m_ua = regexUserAgent.matcher(line);

        if (m_ua.find() && m_ua.groupCount() >= 1) {

            sb.append("\t");

            sb.append(m_ua.group(1));

        }


        return sb.toString();

    }


    public static void main(String[] args) {

        String line = "58.49.105.195 - - [14/Oct/2014:04:07:47 +0800] GET /show/20140825/153120.html HTTP/1.1 200 5948 - Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm) 157.55.39.97";

        String line1 = "58.49.105.195 - - [2014-10-14 04:07:47 +0800] GET /show/20140825/153120.html HTTP/1.1 200 5948 - Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm) 157.55.39.97";


        System.out.println(parseLNMPLog(line, "www.test.cn"));

        System.out.println(parseLNMPLog(line1, "www.test.cn"));

    }

}