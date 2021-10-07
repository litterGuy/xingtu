
package com.qihoo.wzws.rzb.parse;


import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.util.ConfigUtil;
import com.qihoo.wzws.rzb.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class W3CFormatParser {
    private static String W3C_TEMPLATE = "";

    private static final String date = "date";

    private static final String time = "time";

    private static final String cip = "c-ip";

    private static final String csuristem = "cs-uri-stem";

    private static final String csuriquery = "cs-uri-query";

    private static final String scstatus = "sc-status";

    private static final String scbytes = "sc-bytes";
    private static final String cshost = "cs-host";
    private static final String referer = "cs(Referer)";
    private static final String ua = "cs(User-Agent)";
    private static Map<String, Integer> w3cTemplateMap = new HashMap<String, Integer>();

    private static int dateIndex = -1;
    private static int timeIndex = -1;
    private static int ipIndex = -1;
    private static int reponseCodeIndex = -1;
    private static int hostIndex = -1;
    private static int requestUriIndex = -1;
    private static int requestQueryIndex = -1;
    private static int contentLengthIndex = -1;


    private static int refererIndex = -1;
    private static int uaIndex = -1;


    public static void initW3CFormat() throws SystemConfigException {

        String formatStr = (String) ConfigUtil.formatConfig.get("wc3_template");


        if (formatStr == null || formatStr.isEmpty()) {

            throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[]{"wc3_template"}));

        }


        String[] fields = formatStr.trim().split(" ");


        for (int index = 0; index < fields.length - 1; index++) {

            w3cTemplateMap.put(fields[index].trim(), Integer.valueOf(index));

        }


        if (w3cTemplateMap.get("date") != null) {

            dateIndex = ((Integer) w3cTemplateMap.get("date")).intValue();

        }

        if (w3cTemplateMap.get("time") != null) {

            timeIndex = ((Integer) w3cTemplateMap.get("time")).intValue();

        }

        if (w3cTemplateMap.get("c-ip") != null) {

            ipIndex = ((Integer) w3cTemplateMap.get("c-ip")).intValue();

        }


        if (w3cTemplateMap.get("cs-host") != null) {

            hostIndex = ((Integer) w3cTemplateMap.get("cs-host")).intValue();

        }

        if (w3cTemplateMap.get("cs-uri-stem") != null) {

            requestUriIndex = ((Integer) w3cTemplateMap.get("cs-uri-stem")).intValue();

        }

        if (w3cTemplateMap.get("cs-uri-query") != null) {

            requestQueryIndex = ((Integer) w3cTemplateMap.get("cs-uri-query")).intValue();

        }

        if (w3cTemplateMap.get("sc-status") != null) {

            reponseCodeIndex = ((Integer) w3cTemplateMap.get("sc-status")).intValue();

        }

        if (w3cTemplateMap.get("sc-bytes") != null) {

            contentLengthIndex = ((Integer) w3cTemplateMap.get("sc-bytes")).intValue();

        }


        if (w3cTemplateMap.get("cs(Referer)") != null) {

            refererIndex = ((Integer) w3cTemplateMap.get("cs(Referer)")).intValue();

        }

        if (w3cTemplateMap.get("cs(User-Agent)") != null) {

            uaIndex = ((Integer) w3cTemplateMap.get("cs(User-Agent)")).intValue();

        }


        if (dateIndex == -1 || timeIndex == -1 || ipIndex == -1 || requestQueryIndex == -1 || reponseCodeIndex == -1) {

            throw new SystemConfigException(String.format("系统配置config.ini中:参数wc3_template中必须需包含以下字段：date、time、cip、csuristem、csuriquery、scstatus", new Object[0]));

        }

    }


    public static String parse(String line, String host) {

        StringBuilder sb = new StringBuilder("");

        String[] fields = line.split(" ");


        if (fields.length >= 8) {


            if (ipIndex > -1) {

                sb.append(fields[ipIndex]);

            }

            sb.append("\t");


            if (dateIndex > -1 && timeIndex > -1) {

                sb.append(fields[dateIndex] + " " + fields[timeIndex]);

            }

            sb.append("\t");


            if (hostIndex > -1) {

                sb.append(fields[hostIndex]);

            } else {

                sb.append(host);

            }

            sb.append("\t");


            if (requestUriIndex > -1 && !"-".equals(fields[requestUriIndex])) {

                sb.append(fields[requestUriIndex]);

                if (requestQueryIndex > -1 && !"-".equals(fields[requestQueryIndex])) {

                    sb.append("?" + fields[requestQueryIndex]);

                }

                sb.append("\t");

            } else if (requestQueryIndex > -1 && !"-".equals(fields[requestQueryIndex])) {

                sb.append(fields[requestQueryIndex]);


                sb.append("\t");

            }


            if (reponseCodeIndex > -1) {

                sb.append(fields[reponseCodeIndex]);

            }

            sb.append("\t");


            if (contentLengthIndex > -1) {

                sb.append(fields[contentLengthIndex]);

            } else {

                sb.append("0");

            }


            if (refererIndex > -1) {

                sb.append("\t");

                sb.append(fields[refererIndex]);

            } else {

                sb.append("\t");

                sb.append("-");

            }

            if (uaIndex > -1) {

                sb.append("\t");

                sb.append(fields[uaIndex]);

            } else {

                sb.append("\t");

                sb.append("-");


            }


        } else {


            System.out.println(String.format("Unable to parse: %s with default regular expression", new Object[]{line}));

        }


        return sb.toString();

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


        if (file != null) {

            String wc3_template = "";

            BufferedReader reader = null;

            try {

                reader = new BufferedReader(new FileReader(file));

                String tempString = null;

                while ((tempString = reader.readLine()) != null) {


                    if (tempString.startsWith("#Fields:")) {

                        wc3_template = tempString.substring("#Fields:".length()).trim();

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


            if (wc3_template.length() > 0) {

                String[] fields = wc3_template.trim().split(" ");


                for (int index = 0; index < fields.length - 1; index++) {

                    w3cTemplateMap.put(fields[index].trim(), Integer.valueOf(index));

                }


                if (w3cTemplateMap.get("date") != null) {

                    dateIndex = ((Integer) w3cTemplateMap.get("date")).intValue();

                }

                if (w3cTemplateMap.get("time") != null) {

                    timeIndex = ((Integer) w3cTemplateMap.get("time")).intValue();

                }

                if (w3cTemplateMap.get("c-ip") != null) {

                    ipIndex = ((Integer) w3cTemplateMap.get("c-ip")).intValue();

                }


                if (w3cTemplateMap.get("cs-host") != null) {

                    hostIndex = ((Integer) w3cTemplateMap.get("cs-host")).intValue();

                }

                if (w3cTemplateMap.get("cs-uri-stem") != null) {

                    requestUriIndex = ((Integer) w3cTemplateMap.get("cs-uri-stem")).intValue();

                }

                if (w3cTemplateMap.get("cs-uri-query") != null) {

                    requestQueryIndex = ((Integer) w3cTemplateMap.get("cs-uri-query")).intValue();

                }

                if (w3cTemplateMap.get("sc-status") != null) {

                    reponseCodeIndex = ((Integer) w3cTemplateMap.get("sc-status")).intValue();

                }

                if (w3cTemplateMap.get("sc-bytes") != null) {

                    contentLengthIndex = ((Integer) w3cTemplateMap.get("sc-bytes")).intValue();

                }


                if (w3cTemplateMap.get("cs(Referer)") != null) {

                    refererIndex = ((Integer) w3cTemplateMap.get("cs(Referer)")).intValue();

                }

                if (w3cTemplateMap.get("cs(User-Agent)") != null) {

                    uaIndex = ((Integer) w3cTemplateMap.get("cs(User-Agent)")).intValue();

                }


                if (dateIndex == -1 || timeIndex == -1 || ipIndex == -1 || requestQueryIndex == -1 || reponseCodeIndex == -1) {

                    throw new SystemConfigException(String.format("未能识别格式%s，请反馈到星图官方群。", new Object[]{wc3_template}));

                }

            } else {


                throw new SystemConfigException(String.format("请确认一下config.ini中日志文件类型【xingtu_logtype参数项】设置是否正确，如仍有问题，请反馈到星图官方群。谢谢！[%s]", new Object[]{wc3_template}));

            }

        } else {

            throw new SystemConfigException("请确保设置的日志存放路径[log_file配置项]是有效的文件或者文件夹");

        }

    }


    public static boolean mattcherW3CLogFormatTypeByTemplate(List<String> list) throws SystemConfigException {

        if (list == null || list.size() == 0) {

            return false;

        }


        String wc3_template = "";

        for (String tempString : list) {


            if (tempString.startsWith("#Fields:")) {

                wc3_template = tempString.substring("#Fields:".length()).trim();


                break;

            }

        }

        if (wc3_template.length() > 0) {

            W3C_TEMPLATE = wc3_template;

            return true;

        }


        return false;

    }


    public static void initW3CLogFormatTypeByTemplate() throws SystemConfigException {

        String wc3_template = W3C_TEMPLATE;

        if (wc3_template.length() > 0) {

            String[] fields = wc3_template.trim().split(" ");


            for (int index = 0; index < fields.length - 1; index++) {

                w3cTemplateMap.put(fields[index].trim(), Integer.valueOf(index));

            }


            if (w3cTemplateMap.get("date") != null) {

                dateIndex = ((Integer) w3cTemplateMap.get("date")).intValue();

            }

            if (w3cTemplateMap.get("time") != null) {

                timeIndex = ((Integer) w3cTemplateMap.get("time")).intValue();

            }

            if (w3cTemplateMap.get("c-ip") != null) {

                ipIndex = ((Integer) w3cTemplateMap.get("c-ip")).intValue();

            }


            if (w3cTemplateMap.get("cs-host") != null) {

                hostIndex = ((Integer) w3cTemplateMap.get("cs-host")).intValue();

            }

            if (w3cTemplateMap.get("cs-uri-stem") != null) {

                requestUriIndex = ((Integer) w3cTemplateMap.get("cs-uri-stem")).intValue();

            }

            if (w3cTemplateMap.get("cs-uri-query") != null) {

                requestQueryIndex = ((Integer) w3cTemplateMap.get("cs-uri-query")).intValue();

            }

            if (w3cTemplateMap.get("sc-status") != null) {

                reponseCodeIndex = ((Integer) w3cTemplateMap.get("sc-status")).intValue();

            }

            if (w3cTemplateMap.get("sc-bytes") != null) {

                contentLengthIndex = ((Integer) w3cTemplateMap.get("sc-bytes")).intValue();

            }


            if (w3cTemplateMap.get("cs(Referer)") != null) {

                refererIndex = ((Integer) w3cTemplateMap.get("cs(Referer)")).intValue();

            }

            if (w3cTemplateMap.get("cs(User-Agent)") != null) {

                uaIndex = ((Integer) w3cTemplateMap.get("cs(User-Agent)")).intValue();

            }


            if (dateIndex == -1 || timeIndex == -1 || ipIndex == -1 || requestQueryIndex == -1 || reponseCodeIndex == -1) {

                throw new SystemConfigException(String.format("未能识别格式%s，请反馈到星图官方群。", new Object[]{wc3_template}));

            }

        } else {


            throw new SystemConfigException(String.format("请确认一下config.ini中日志文件类型【xingtu_logtype参数项】设置是否正确，如仍有问题，请反馈到星图官方群。谢谢！[%s]", new Object[]{wc3_template}));

        }

    }


    public static void main(String[] args) {

        try {

            matcherLogFormatType("C:\\Users\\wangpeng3-s\\Desktop\\iis_access.log");

        } catch (SystemConfigException e) {

            System.out.println(e.getMessage());

            e.printStackTrace();

        }

    }

}