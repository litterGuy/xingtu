
package com.qihoo.wzws.rzb.parse;


import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IISFormatParser {
    private static Pattern regexIp = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

    private static Pattern regexDate = Pattern.compile("\\d{1,2}.\\d{1,2}.\\d{4}");

    private static Pattern regexTime = Pattern.compile("\\d{1,2}\\:\\d{2}\\:\\d{2}");
    private static Pattern regexStatus = Pattern.compile("\\d{3}");
    private static Pattern regexContentLength = Pattern.compile("\\d{1,}+");
    private static Pattern regexRequestUri = Pattern.compile("/\\w*");


    private static final int clientIPIndex = 0;


    private static final int dateIndex = 2;


    private static final int timeIndex = 3;


    private static final int serverIPIndex = 6;


    private static final int contentLengthIndex = 9;


    private static final int reponseCodeIndex = 10;


    private static final int requestUriIndex = 13;


    private static final int requestParasIndex = 14;


    public static String parse(String line, String host) {

        StringBuilder sb = new StringBuilder("");

        String[] fields = line.split(",\\s");


        if (fields.length == 15) {


            String clientIP = fields[0];

            String date = fields[2];

            String time = fields[3];

            String requestUri = fields[13];

            String requestParas = fields[14];

            String status = fields[10];

            String contentLength = fields[9];


            if (clientIP != null) {

                sb.append(clientIP.trim());

            }

            sb.append("\t");


            if (date != null && time != null) {

                String datetime = date.trim() + " " + time.trim();


                try {

                    DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);

                    String strDate = DateUtil.formatStrDate(datetime, dateFormatter);

                    if (strDate != null) {

                        sb.append(strDate);

                    }

                } catch (Exception ex) {

                    return null;

                }

            } else {

                return null;

            }

            sb.append("\t");


            sb.append(host);

            sb.append("\t");


            if (requestUri != null) {

                sb.append(requestUri.trim());

                if (requestParas.endsWith(",")) {

                    requestParas = requestParas.substring(0, requestParas.length() - 1);

                    if (!"-".equals(requestParas)) {

                        if (requestParas.indexOf("?") == -1) {

                            sb.append("?" + requestParas.trim());

                        } else {

                            sb.append(requestParas.trim());

                        }

                    }

                }

            } else {

                return null;

            }

            sb.append("\t");


            if (status != null) {

                sb.append(status.trim());

            }

            sb.append("\t");


            if (contentLength != null) {

                sb.append(contentLength.trim());

            } else {

                sb.append("0");

            }

        } else {


            System.out.println(String.format("Unable to parse: %s with default regular expression", new Object[]{line}));

        }


        return sb.toString();

    }


    public static boolean mattcherIISLogFormatType(List<String> list) throws SystemConfigException {

        if (list == null || list.size() == 0) {

            return false;

        }


        String first = "";

        for (String tempString : list) {


            if (!tempString.startsWith("#") && tempString.indexOf(",") > 0) {

                first = tempString;


                break;

            }

        }

        ApacheDefaultFormatParser.FormatType type = null;


        if (mattcherIISLog(first)) {

            type = ApacheDefaultFormatParser.FormatType.IIS;

        }


        if (type != null) {

            int sum = 0;

            for (int i = 0; i < list.size(); i++) {

                String tempString = list.get(i);

                if (!tempString.startsWith("#") && tempString.indexOf(",") > 0 &&
                        mattcherIISLog(list.get(i))) {

                    sum++;

                }

            }


            if (sum > list.size() / 2) {

                return true;

            }

        }


        return false;

    }


    public static boolean mattcherIISLog(String line) {

        String[] fields = line.split(",\\s");

        if (fields.length == 15) {


            Matcher m_cip = regexIp.matcher(fields[0].trim());

            Matcher m_date = regexDate.matcher(fields[2].trim());

            Matcher m_time = regexTime.matcher(fields[3].trim());

            Matcher m_sip = regexIp.matcher(fields[6].trim());

            Matcher m_status = regexStatus.matcher(fields[10]);

            Matcher m_contentLength = regexContentLength.matcher(fields[9].trim());

            Matcher m_requestUri = regexRequestUri.matcher(fields[13].trim());


            if (m_cip.find() && m_date.find() && m_time.find() && m_sip.find() && m_status.find() && m_contentLength.find() && m_requestUri.find()) {

                return true;

            }

        }


        return false;

    }


    public static void main(String[] args) {

        String line = "222.173.30.138, -, 11/2/2014, 0:00:53, W3SVC1, WIN-XC7TLE0W5RF, 192.168.240.103, 46, 321, 7699, 200, 0, GET, /show.aspx, lmid=1947&xxid=48081&pgid=395,";

        String line1 = "68.180.228.169, -, 11/3/2014, 0:00:01, W3SVC3, WIN-GJ4VMT9P551, 213.154.30.11, 1203, 211, 5079, 200, 0, GET, /, q-634.html?list=/1480.html,";


        List<String> list = new ArrayList<String>();

        list.add(line);

        list.add(line1);


        try {

            System.out.println(mattcherIISLogFormatType(list));

        } catch (SystemConfigException e) {


            e.printStackTrace();

        }


        System.out.println(parse(line, "default"));

        System.out.println(parse(line1, "default"));

    }

}