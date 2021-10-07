
package com.qihoo.wzws.rzb.parse;


import com.qihoo.wzws.rzb.exception.SystemConfigException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AgentLogFormatParser {
    private static String AGENT_TEMPLATE = "";

    private static final String date = "Date";

    private static final String time = "Time";

    private static final String cip = "ClientIpAddress";

    private static final String csuristem = "UriStem";

    private static final String csuriquery = "UriQuery";

    private static final String scstatus = "SubStatus";

    private static final String scbytes = "BytesSent";

    private static final String cshost = "ServerIpAddress";

    private static Map<String, Integer> agentTemplateMap = new HashMap<String, Integer>();

    private static int dateIndex = -1;
    private static int timeIndex = -1;
    private static int ipIndex = -1;
    private static int reponseCodeIndex = -1;
    private static int hostIndex = -1;
    private static int requestUriIndex = -1;
    private static int requestQueryIndex = -1;
    private static int contentLengthIndex = -1;


    public static void initAgentLogFormatTypeByTemplate() throws SystemConfigException {

        String agent_template = AGENT_TEMPLATE;

        if (agent_template.length() > 0) {

            String[] fields = agent_template.trim().split(" ");


            for (int index = 0; index < fields.length - 1; index++) {

                agentTemplateMap.put(fields[index].trim(), Integer.valueOf(index));

            }


            if (agentTemplateMap.get("Date") != null) {

                dateIndex = ((Integer) agentTemplateMap.get("Date")).intValue();

            }

            if (agentTemplateMap.get("Time") != null) {

                timeIndex = ((Integer) agentTemplateMap.get("Time")).intValue();

            }

            if (agentTemplateMap.get("ClientIpAddress") != null) {

                ipIndex = ((Integer) agentTemplateMap.get("ClientIpAddress")).intValue();

            }


            if (agentTemplateMap.get("ServerIpAddress") != null) {

                hostIndex = ((Integer) agentTemplateMap.get("ServerIpAddress")).intValue();

            }

            if (agentTemplateMap.get("UriStem") != null) {

                requestUriIndex = ((Integer) agentTemplateMap.get("UriStem")).intValue();

            }

            if (agentTemplateMap.get("UriQuery") != null) {

                requestQueryIndex = ((Integer) agentTemplateMap.get("UriQuery")).intValue();

            }

            if (agentTemplateMap.get("SubStatus") != null) {

                reponseCodeIndex = ((Integer) agentTemplateMap.get("SubStatus")).intValue();

            }

            if (agentTemplateMap.get("BytesSent") != null) {

                contentLengthIndex = ((Integer) agentTemplateMap.get("BytesSent")).intValue();

            }


            if (dateIndex == -1 || timeIndex == -1 || ipIndex == -1 || requestQueryIndex == -1 || reponseCodeIndex == -1) {

                throw new SystemConfigException(String.format("未能识别格式%s，请反馈到星图官方群。", new Object[]{agent_template}));

            }

        } else {


            throw new SystemConfigException(String.format("请确认一下config.ini中日志文件类型【xingtu_logtype参数项】设置是否正确，如仍有问题，请反馈到星图官方群。谢谢！[%s]", new Object[]{agent_template}));

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


        } else {


            System.out.println(String.format("Unable to parse: %s with default regular expression", new Object[]{line}));

        }


        return sb.toString();

    }


    public static boolean mattcherAgentLogFormatTypeByTemplate(List<String> list) throws SystemConfigException {

        if (list == null || list.size() == 0) {

            return false;

        }


        String agent_template = "";

        for (String tempString : list) {


            if (tempString.startsWith("#Fields:")) {

                agent_template = tempString.substring("#Fields:".length()).trim();


                break;

            }

        }

        if (agent_template.length() > 0) {

            agent_template = agent_template.replace("DateTime", "Date Time");

            String[] agentArray = agent_template.split(" ");

            for (String agent : agentArray) {


                if (agent.equals("LogFilename")) {

                    AGENT_TEMPLATE = agent_template;

                    return true;

                }

            }

        }


        return false;

    }

}