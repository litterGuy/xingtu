package com.qihoo.wzws.rzb.parse;

import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.util.ConfigUtil;
import com.qihoo.wzws.rzb.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


public class CustomLogFormatParser {
    private static final String KEY_logtempletName = "logformat_use";
    private static final String KEY_suffix_delimited = "_delimited";
    private static final String KEY_suffix_dateformat = "_dateformat";
    private static final String KEY_suffix_logtemplate = "_logtemplate";
    private static final String KEY_suffix_fieldssize = "_fieldssize";
    private static Map<String, Integer> logTemplateMap = new HashMap<String, Integer>();

    private static int timeIndex = -1;
    private static int ipIndex = -1;
    private static int reponseCodeIndex = -1;
    private static int hostIndex = -1;
    private static int requestUrlIndex = -1;
    private static int contentLengthIndex = -1;


    private static int refererIndex = -1;
    private static int uaIndex = -1;


    private static String global_delimited = "";
    private static int global_fieldssize = 0;


    protected enum CustomFormatFields {
        time_local,
        remote_addr,
        reponse_code,
        host,
        request_url,
        content_length,
        http_referer,
        http_user_agent;
    }


    private static final Pattern timeZonePattern = Pattern.compile("^(0[1-9]|1[0-9]|2[0-9]|3[01])[///](Jun|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[///][0-9]{4}[:](0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2} ([- /+|-]\\d{4})$");

    private static final Pattern datetimePattern = Pattern.compile("^[0-9]{4}[-](0[1-9]|1[0-2])[-](0[1-9]|1[0-9]|2[0-9]|3[01])[ ](0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2}$");

    public static boolean matches(Pattern pattern, String str) {
        return pattern.matcher(str).find();
    }


    public static void loadCustomLogFormatConfig() throws SystemConfigException {
        String logtempletName = (String) ConfigUtil.formatConfig.get("logformat_use");
        if (logtempletName == null || logtempletName.trim().isEmpty()) {
            throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[]{"logformat_use"}));
        }

        String delimited = (String) ConfigUtil.formatConfig.get(logtempletName + "_delimited");

        String logtemplate = (String) ConfigUtil.formatConfig.get(logtempletName + "_logtemplate");
        String fieldssizeStr = (String) ConfigUtil.formatConfig.get(logtempletName + "_fieldssize");


        if (logtemplate == null || logtemplate.trim().isEmpty()) {
            throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[]{logtempletName + "_logtemplate"}));
        }
        if (fieldssizeStr == null || fieldssizeStr.trim().isEmpty()) {
            throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[]{logtempletName + "_fieldssize"}));
        }

        int fieldssize = 0;
        try {
            fieldssize = Integer.valueOf(fieldssizeStr.trim()).intValue();
        } catch (NumberFormatException ex) {

            throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[]{logtempletName + "_fieldssize"}));
        }

        String[] fields = logtemplate.trim().split(delimited.trim());
        if (fields.length != fieldssize) {
            throw new SystemConfigException(String.format("系统配置config.ini中中:参数%s配置的日志字段个数与%s中不一致！", new Object[]{logtempletName + "_fieldssize", logtempletName + "_logtemplate"}));
        }

        for (int index = 0; index < fields.length; index++) {
            logTemplateMap.put(fields[index].trim(), Integer.valueOf(index));
        }


        if (logTemplateMap.get(CustomFormatFields.time_local.toString()) != null) {
            timeIndex = ((Integer) logTemplateMap.get(CustomFormatFields.time_local.toString())).intValue();
        }
        if (logTemplateMap.get(CustomFormatFields.remote_addr.toString()) != null) {
            ipIndex = ((Integer) logTemplateMap.get(CustomFormatFields.remote_addr.toString())).intValue();
        }

        if (logTemplateMap.get(CustomFormatFields.host.toString()) != null) {
            hostIndex = ((Integer) logTemplateMap.get(CustomFormatFields.host.toString())).intValue();
        }
        if (logTemplateMap.get(CustomFormatFields.request_url.toString()) != null) {
            requestUrlIndex = ((Integer) logTemplateMap.get(CustomFormatFields.request_url.toString())).intValue();
        }
        if (logTemplateMap.get(CustomFormatFields.reponse_code.toString()) != null) {
            reponseCodeIndex = ((Integer) logTemplateMap.get(CustomFormatFields.reponse_code.toString())).intValue();
        }

        if (logTemplateMap.get(CustomFormatFields.content_length.toString()) != null) {
            contentLengthIndex = ((Integer) logTemplateMap.get(CustomFormatFields.content_length.toString())).intValue();
        }

        if (logTemplateMap.get(CustomFormatFields.http_referer.toString()) != null) {
            refererIndex = ((Integer) logTemplateMap.get(CustomFormatFields.http_referer.toString())).intValue();
        }
        if (logTemplateMap.get(CustomFormatFields.http_user_agent.toString()) != null) {
            uaIndex = ((Integer) logTemplateMap.get(CustomFormatFields.http_user_agent.toString())).intValue();
        }
        if (timeIndex == -1 || ipIndex == -1 || requestUrlIndex == -1 || reponseCodeIndex == -1) {
            throw new SystemConfigException(String.format("系统配置config.ini中:参数logtemplate中必须需包含以下字段：remote_addr、time_local、request_url、reponse_code", new Object[0]));
        }


        global_delimited = delimited;
        global_fieldssize = fieldssize;

        AutomaticLogFormatParser.XINGTU_LOGTYPE_INT = 4;
    }


    @Deprecated
    public static void loadCustomLogFormatConfigFromProperties() throws SystemConfigException {
        String logtempletName = ConfigUtil.getConfig("logformat_use");
        if (logtempletName.trim().isEmpty()) {
            throw new SystemConfigException(String.format("系统配置中: %s参数需要配置！", new Object[]{"logformat_use"}));
        }

        String delimited = ConfigUtil.getConfig(logtempletName + "_delimited").trim();
        String dateformat = ConfigUtil.getConfig(logtempletName + "_dateformat").trim();
        String logtemplate = ConfigUtil.getConfig(logtempletName + "_logtemplate").trim();
        String fieldssizeStr = ConfigUtil.getConfig(logtempletName + "_fieldssize").trim();


        if (dateformat.trim().isEmpty()) {
            throw new SystemConfigException(String.format("系统配置中: %s参数需要配置！", new Object[]{logtempletName + "_dateformat"}));
        }
        if (logtemplate.trim().isEmpty()) {
            throw new SystemConfigException(String.format("系统配置中: %s参数需要配置！", new Object[]{logtempletName + "_logtemplate"}));
        }
        if (fieldssizeStr.trim().isEmpty()) {
            throw new SystemConfigException(String.format("系统配置中: %s参数需要配置！", new Object[]{logtempletName + "_fieldssize"}));
        }

        int fieldssize = 0;
        try {
            fieldssize = Integer.valueOf(fieldssizeStr).intValue();
        } catch (NumberFormatException ex) {

            throw new SystemConfigException(String.format("系统配置中: %s参数配置有无，请填写日志字段个数！", new Object[]{logtempletName + "_fieldssize"}));
        }

        String[] fields = logtemplate.split(delimited);
        if (fields.length != fieldssize) {
            throw new SystemConfigException(String.format("系统配置中: %s参数配置的日志字段个数与%s中不一致！", new Object[]{logtempletName + "_fieldssize", logtempletName + "_logtemplate"}));
        }

        for (int index = 0; index < fields.length - 1; index++) {
            logTemplateMap.put(fields[index].trim(), Integer.valueOf(index));
        }


        timeIndex = ((Integer) logTemplateMap.get(CustomFormatFields.time_local.toString())).intValue();
        ipIndex = ((Integer) logTemplateMap.get(CustomFormatFields.remote_addr.toString())).intValue();
        hostIndex = ((Integer) logTemplateMap.get(CustomFormatFields.host.toString())).intValue();
        requestUrlIndex = ((Integer) logTemplateMap.get(CustomFormatFields.request_url.toString())).intValue();
        reponseCodeIndex = ((Integer) logTemplateMap.get(CustomFormatFields.reponse_code.toString())).intValue();
        contentLengthIndex = ((Integer) logTemplateMap.get(CustomFormatFields.content_length.toString())).intValue();


        global_delimited = delimited;
        global_fieldssize = fieldssize;
    }


    public static String parse(String line, String host) {
        if (line.startsWith("#")) {
            return null;
        }

        String[] fields = line.split(global_delimited);
        if (fields.length >= global_fieldssize) {
            StringBuilder sb = new StringBuilder();

            sb.append(fields[ipIndex]);
            sb.append("\t");

            String time = fields[timeIndex];
            if (time != null && !time.isEmpty()) {
                if (matches(timeZonePattern, time)) {
                    DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
                    sb.append(DateUtil.formatStrDate(time, dateFormatter));
                } else if (matches(datetimePattern, time)) {
                    sb.append(time);
                }
            }
            sb.append("\t");


            if (hostIndex > -1 && !fields[hostIndex].isEmpty()) {
                sb.append(fields[hostIndex]);
            } else {
                sb.append(host);
            }
            sb.append("\t");

            sb.append(fields[requestUrlIndex]);
            sb.append("\t");

            sb.append(fields[reponseCodeIndex]);
            sb.append("\t");

            if (contentLengthIndex > -1) {
                sb.append(fields[contentLengthIndex]);
            } else {
                sb.append("0");
            }


            if (refererIndex > -1 && uaIndex > -1) {
                sb.append("\t");
                sb.append(fields[refererIndex]);
                sb.append("\t");
                sb.append(fields[uaIndex]);
            }

            return sb.toString();
        }


        System.out.println(String.format("Unable to parse: %s with default regular expression", new Object[]{line}));

        return null;
    }


    public static void main(String[] args) throws SystemConfigException {
        String timezone = "10/Oct/2000:13:55:36 -0700";
        String datetime = "2014-07-30 00:02:20";

        System.out.println(matches(timeZonePattern, timezone));
        System.out.println(matches(datetimePattern, datetime));
    }
}