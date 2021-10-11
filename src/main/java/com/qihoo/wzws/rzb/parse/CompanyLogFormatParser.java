package com.qihoo.wzws.rzb.parse;

import com.qihoo.wzws.rzb.exception.LogParserException;
import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.util.DateUtil;
import com.qihoo.wzws.rzb.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理公司内部nginx日志格式：
 * log_format  access  '$remote_addr - $remote_user [$time_local] "$request" '
 * '$status $body_bytes_sent $request_time $upstream_response_time "$http_referer" '
 * '"$http_user_agent" $http_x_forwarded_for';
 */
public class CompanyLogFormatParser {

    private static String nginxLogPatternStr = "^([0-9.,]+)\\s([-]{1})\\s([\\w\\.-]+)\\s\\[([/a-zA-Z0-9+-: ]+)\\]\\s\"(.+)\"\\s(\\d{3})\\s(\\d+)\\s([0-9.-]+)\\s([0-9.-]+)\\s\"(.+)\"\\s\"(.+)\"\\s([0-9.,]+)$";
    private static Pattern pattern;

    /**
     * 校验格式
     *
     * @param logFile
     * @return
     * @throws SystemConfigException
     */
    public static boolean matcherLogFormatType(String logFile) {
        File file = null;
        File fileDir = new File(logFile);
        if (fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if (files.length >= 1) {
                file = Utils.getlastestFileFromDir(files);
            }
            fileDir = null;
        } else {
            file = fileDir;
        }

        List<String> list = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString);
                if (list.size() >= 10) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        if (list != null && list.size() > 0) {
            if (mattcherLogFormatTypeByTemplate(list)) {
                AutomaticLogFormatParser.XINGTU_LOGTYPE_INT = 9;
                return true;
            }
        }
        return false;
    }

    /**
     * 正则匹配格式
     *
     * @param list
     * @return
     * @throws SystemConfigException
     */
    private static boolean mattcherLogFormatTypeByTemplate(List<String> list) {
        pattern = Pattern.compile(nginxLogPatternStr);
        if (list == null || list.size() == 0) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (matches(pattern, list.get(i))) {
                sum++;
            }
        }
        if (sum > list.size() / 2) {
            return true;
        }
        return false;
    }

    private static boolean matches(Pattern pattern, String str) {
        try {
            return pattern.matcher(str).find();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 序列化结构
     *
     * @param line
     * @param host
     * @return
     */
    public static String parse(String line, String host) throws LogParserException {
        StringBuilder sb = new StringBuilder("");
        Matcher m = pattern.matcher(line);
        if (m.matches()) {
            if (m.groupCount() > 11) {
                //remote_addr
                sb.append(Utils.getRemoteIp(m.group(1)));
                sb.append("\t");
                //time_local
                String timeZone = m.group(4);
                if (timeZone != null && !timeZone.isEmpty()) {
                    DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
                    String strDate = DateUtil.formatStrDate(timeZone, dateFormatter);
                    if (strDate != null) {
                        sb.append(strDate);
                    }
                }
                sb.append("\t");
                //host
                sb.append(host);
                sb.append("\t");
                //request_uri
                String rquestLine = m.group(5);
                if (rquestLine != null && (rquestLine.split("\\s")).length == 3) {
                    sb.append(rquestLine.split("\\s")[1]);
                } else {
                    sb.append(rquestLine);
                }
                sb.append("\t");
                //status
                sb.append(m.group(6));
                sb.append("\t");
                //contentLength
                sb.append(m.group(7));
                sb.append("\t");
                //referer
                sb.append(m.group(10));
                sb.append("\t");
                //ua
                sb.append(m.group(11));
                sb.append("\t");
                //http_x_forwarded_for
                sb.append(m.group(12));
                sb.append("\t");
            }
        }
        return sb.toString();
    }
}
