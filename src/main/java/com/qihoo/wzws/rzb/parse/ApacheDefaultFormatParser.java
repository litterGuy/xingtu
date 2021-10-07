
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApacheDefaultFormatParser {
    public static FormatType current_apache_logType = null;

    private static final String prefix = "^";

    private static final String ipPattern = "([0-9.,]+)\\s";

    private static final String identityPattern = "([\\w\\.-]+)\\s";

    private static final String useridPattern = "([\\w\\.-]+)\\s";

    private static final String timePattern = "\\[([/a-zA-Z0-9+-: ]+)\\]\\s";

    private static final String requestPattern = "\"(.+)\"\\s";

    private static final String responseCodePattern = "(\\d{3})\\s";

    private static final String contentLength = "(\\d+|-)";

    private static final String ipCommon = "(-|(\\d{1,3}\\.){3}\\d{1,3})";

    private static final String refererPattern = "\"(-|http\\S+)\"\\s";
    private static final String useragentPattern = "\"(-|.*)\"";
    private static final String allPattern = ".*";
    private static final String blackPattern = "\\s";
    private static final String suffix = "$";
    private static final String quote = "\"{0,1}";
    private static final String anmp_identityPattern = "-\\s";
    private static final String anmp_useridPattern = "-\\s";
    private static final String anmp_requestPattern = "(.+)\"\\s";
    private static StringBuilder commonPatternStr = new StringBuilder();
    private static StringBuilder apacheCommonPatternStr = new StringBuilder();
    private static StringBuilder apacheCombinedPatternStr = new StringBuilder();
    private static StringBuilder apacheCombinedExtPatternStr = new StringBuilder();
    private static StringBuilder cnpcPatternStr = new StringBuilder();

    private static Pattern apacheCommonPattern = null;
    private static Pattern apacheCombinedPattern = null;
    private static Pattern apacheCombinedExtPattern = null;
    private static Pattern cnpcPattern = null;

    protected static EnumMap<FormatType, Pattern> formatPattern;


    static {

        commonPatternStr.append("([0-9.,]+)\\s");

        commonPatternStr.append("([\\w\\.-]+)\\s");

        commonPatternStr.append("([\\w\\.-]+)\\s");

        commonPatternStr.append("\\[([/a-zA-Z0-9+-: ]+)\\]\\s");

        commonPatternStr.append("\"(.+)\"\\s");

        commonPatternStr.append("(\\d{3})\\s");

        commonPatternStr.append("(\\d+|-)");


        apacheCommonPatternStr.append("^");

        apacheCommonPatternStr.append(commonPatternStr);

        apacheCommonPatternStr.append(" ?.*");


        apacheCommonPattern = Pattern.compile(apacheCommonPatternStr.toString());


        apacheCombinedPatternStr.append("^");

        apacheCombinedPatternStr.append(commonPatternStr);

        apacheCombinedPatternStr.append("\\s");

        apacheCombinedPatternStr.append("\"(-|http\\S+)\"\\s");

        apacheCombinedPatternStr.append("\"(-|.*)\"");

        apacheCombinedPatternStr.append(".*");


        apacheCombinedPattern = Pattern.compile(apacheCombinedPatternStr.toString());


        apacheCombinedExtPatternStr.append("^");

        apacheCombinedExtPatternStr.append(commonPatternStr);

        apacheCombinedExtPatternStr.append("\\s");

        apacheCombinedExtPatternStr.append("\"(-|http\\S+)\"\\s");

        apacheCombinedExtPatternStr.append("\"(-|.*)\"");

        apacheCombinedExtPatternStr.append("\\s");

        apacheCombinedExtPatternStr.append("\"{0,1}");

        apacheCombinedExtPatternStr.append("(-|(\\d{1,3}\\.){3}\\d{1,3})");

        apacheCombinedExtPatternStr.append("\"{0,1}");

        apacheCombinedExtPatternStr.append(".*");


        apacheCombinedExtPattern = Pattern.compile(apacheCombinedExtPatternStr.toString());


        cnpcPatternStr.append("^");

        cnpcPatternStr.append("([0-9.,]+)\\s");

        cnpcPatternStr.append("([\\w\\.-]+)\\s");

        cnpcPatternStr.append("([\\w\\.-]+)\\s");

        cnpcPatternStr.append("\\[([/a-zA-Z0-9+-: ]+)\\]\\s");

        cnpcPatternStr.append("\"(.+)\"\\s");

        cnpcPatternStr.append("(\\d{3})\\s");

        cnpcPatternStr.append("(\\d+|-)");

        cnpcPatternStr.append(".*");


        cnpcPattern = Pattern.compile(cnpcPatternStr.toString());


        formatPattern = new EnumMap<FormatType, Pattern>(FormatType.class);


        formatPattern.put(FormatType.Apache_Common, apacheCommonPattern);

        formatPattern.put(FormatType.Apache_Combined, apacheCombinedPattern);

        formatPattern.put(FormatType.Apache_CombinedExt, apacheCombinedExtPattern);

        formatPattern.put(FormatType.CNPC, cnpcPattern);

    }


    public enum FormatType {
        Agent_Log, Apache_Common, Apache_Combined, Apache_CombinedExt, Nginx_ANMP, CNPC, W3C_ALL, LNMP, Apache_Custom, IIS, NULL;

    }


    public static void matcherLogFormatType(String dir) throws SystemConfigException {

        File file = null;

        File fileDir = new File(dir);

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


                if (!tempString.startsWith("#")) {

                    list.add(tempString);

                    if (list.size() >= 5) {

                        break;

                    }

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


        if (list == null || list.size() == 0) {

            throw new SystemConfigException("请确保设置的日志存放路径config.ini中[log_file配置项]是有效的文件或者文件夹");

        }


        String first = list.get(0);

        FormatType type = null;


        if (matches(apacheCombinedExtPattern, first)) {

            type = FormatType.Apache_CombinedExt;

        } else if (matches(apacheCombinedPattern, first)) {

            type = FormatType.Apache_Combined;

        } else if (matches(apacheCommonPattern, first)) {

            type = FormatType.Apache_Common;

        }


        if (type != null) {

            int sum = 1;

            for (int i = 1; i < list.size(); i++) {

                if (matches(formatPattern.get(type), first)) {

                    sum++;

                }

            }


            if (list.size() == sum) {

                current_apache_logType = type;

            }

        } else {


            throw new SystemConfigException(String.format("请确认一下config.ini中日志文件类型【xingtu_logtype参数项】设置是否正确，如仍有问题，请反馈到星图官方群。谢谢！[%s]", new Object[]{first}));

        }

    }


    public static boolean mattcherApacheLogFormatTypeByTemplate(List<String> list) throws SystemConfigException {

        if (list == null || list.size() == 0) {

            return false;

        }


        String first = Utils.getLongestFromList(list);


        FormatType type = null;


        if (matches(apacheCombinedExtPattern, first)) {

            type = FormatType.Apache_CombinedExt;

        } else if (matches(apacheCombinedPattern, first)) {

            type = FormatType.Apache_Combined;

        } else if (matches(apacheCommonPattern, first)) {

            type = FormatType.Apache_Common;

        }


        if (type != null) {

            int sum = 0;

            for (int i = 0; i < list.size(); i++) {

                if (matches(formatPattern.get(type), first)) {

                    sum++;

                }

            }


            if (sum > list.size() / 2) {

                current_apache_logType = type;

                return true;

            }

        }


        return false;

    }


    public static boolean matches(Pattern pattern, String str) {

        try {

            return pattern.matcher(str).find();

        } catch (Exception ex) {

            return false;

        }

    }


    public static String parse(String line, FormatType type, String host) throws LogParserException {

        StringBuilder sb = new StringBuilder("");

        Matcher m = ((Pattern) formatPattern.get(type)).matcher(line);

        if (m.matches()) {

            switch (type) {


                case Apache_Common:

                case Apache_Combined:

                case Apache_CombinedExt:

                    if (m.groupCount() >= 7) {


                        if (m.groupCount() >= 10 && !m.group(10).equals("-")) {

                            sb.append(Utils.getRemoteIp(m.group(10)));

                        } else {

                            sb.append(Utils.getRemoteIp(m.group(1)));

                        }

                        sb.append("\t");


                        String timeZone = m.group(4);

                        if (timeZone != null && !timeZone.isEmpty()) {

                            DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

                            String strDate = DateUtil.formatStrDate(timeZone, dateFormatter);

                            if (strDate != null) {

                                sb.append(strDate);

                            }

                        }

                        sb.append("\t");


                        sb.append(host);

                        sb.append("\t");


                        String rquestLine = m.group(5);

                        if (rquestLine != null && (rquestLine.split("\\s")).length == 3) {

                            sb.append(rquestLine.split("\\s")[1]);

                        } else {

                            sb.append(rquestLine);

                        }

                        sb.append("\t");


                        sb.append(m.group(6));

                        sb.append("\t");


                        sb.append(m.group(7));

                        if (m.groupCount() >= 9) {

                            sb.append("\t");


                            sb.append(m.group(8));

                            sb.append("\t");


                            sb.append(m.group(9));

                            sb.append("\t");

                        }

                    }

                    break;

            }


        }

        return sb.toString();

    }


    public static String parseNginxANMP(String line, FormatType type, String host) throws LogParserException {

        StringBuilder sb = new StringBuilder("");

        Matcher m = ((Pattern) formatPattern.get(type)).matcher(line);

        if (m.matches()) {

            switch (type) {


                case Apache_Common:

                case Apache_Combined:

                case Apache_CombinedExt:

                    if (m.groupCount() >= 7) {


                        if (m.groupCount() >= 10) {

                            sb.append(Utils.getRemoteIp(m.group(10)));

                        } else {

                            sb.append(Utils.getRemoteIp(m.group(1)));

                        }

                        sb.append("\t");


                        String timeZone = m.group(4);

                        if (timeZone != null && !timeZone.isEmpty()) {

                            DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

                            String strDate = DateUtil.formatStrDate(timeZone, dateFormatter);

                            if (strDate != null) {

                                sb.append(strDate);

                            }

                        }

                        sb.append("\t");


                        sb.append(host);

                        sb.append("\t");


                        String rquestLine = m.group(5);

                        if (rquestLine != null && (rquestLine.split("\\s")).length == 3) {

                            sb.append(rquestLine.split("\\s")[1]);

                        } else {

                            sb.append(rquestLine);

                        }

                        sb.append("\t");


                        sb.append(m.group(6));

                        sb.append("\t");


                        sb.append(m.group(7));

                        if (m.groupCount() >= 9) {

                            sb.append("\t");


                            sb.append(m.group(8));

                            sb.append("\t");


                            sb.append(m.group(9));

                            sb.append("\t");

                        }

                    }

                    break;

            }


        }

        return sb.toString();

    }


    public static String parseByMatcher(String line, String host) throws LogParserException {

        StringBuilder sb = new StringBuilder("");

        Matcher m = ((Pattern) formatPattern.get(current_apache_logType)).matcher(line);

        if (m.matches()) {

            switch (current_apache_logType) {


                case Apache_Common:

                case Apache_Combined:

                case Apache_CombinedExt:

                    if (m.groupCount() >= 7) {


                        if (m.groupCount() >= 10) {

                            sb.append(Utils.getRemoteIp(m.group(10)));

                        } else {

                            sb.append(Utils.getRemoteIp(m.group(1)));

                        }

                        sb.append("\t");


                        String timeZone = m.group(4);

                        if (timeZone != null && !timeZone.isEmpty()) {

                            DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

                            String strDate = DateUtil.formatStrDate(timeZone, dateFormatter);

                            if (strDate != null) {

                                sb.append(strDate);

                            }

                        }

                        sb.append("\t");


                        sb.append(host);

                        sb.append("\t");


                        String rquestLine = m.group(5);

                        if (rquestLine != null && (rquestLine.split("\\s")).length == 3) {

                            sb.append(rquestLine.split("\\s")[1]);

                        } else {

                            sb.append(rquestLine);

                        }

                        sb.append("\t");


                        sb.append(m.group(6));

                        sb.append("\t");


                        sb.append(m.group(7));

                        if (m.groupCount() >= 9) {

                            sb.append("\t");


                            sb.append(m.group(8));

                            sb.append("\t");


                            sb.append(m.group(9));

                            sb.append("\t");

                        }

                    }

                    break;

            }


        }

        return sb.toString();

    }


    public static String formatDateTime(Date date) {

        return DateUtil.formatDateTime(date);

    }


    public static void main(String[] args) throws LogParserException {

        String referer = "\"(-|\\S+)\"\\s.*";

        String line = "\"http://www.swanksalon.info/\" \"Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D201 Safari/9537.53\"";

        System.out.println(line.matches(referer));

    }

}