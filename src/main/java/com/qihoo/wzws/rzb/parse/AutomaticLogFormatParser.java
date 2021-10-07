
package com.qihoo.wzws.rzb.parse;


import com.qihoo.wzws.rzb.exception.LogParserException;
import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.secure.po.LogEntity;
import com.qihoo.wzws.rzb.util.ConfigUtil;
import com.qihoo.wzws.rzb.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AutomaticLogFormatParser {
    public static int XINGTU_LOGTYPE_INT;
    private static ApacheDefaultFormatParser.FormatType LOGTYPE;
    public static List<String> LOG_SAMPLE;


    public static boolean matcherLogFormatType(String logFile) throws SystemConfigException {

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

            if (AgentLogFormatParser.mattcherAgentLogFormatTypeByTemplate(list)) {

                AgentLogFormatParser.initAgentLogFormatTypeByTemplate();

                LOGTYPE = ApacheDefaultFormatParser.FormatType.Agent_Log;

                XINGTU_LOGTYPE_INT = 8;

            } else if (W3CFormatParser.mattcherW3CLogFormatTypeByTemplate(list)) {

                W3CFormatParser.initW3CLogFormatTypeByTemplate();

                LOGTYPE = ApacheDefaultFormatParser.FormatType.W3C_ALL;

                XINGTU_LOGTYPE_INT = 1;

            } else if (ApacheDefaultFormatParser.mattcherApacheLogFormatTypeByTemplate(list)) {

                LOGTYPE = ApacheDefaultFormatParser.current_apache_logType;

                if (ApacheDefaultFormatParser.FormatType.Apache_Common.equals(LOGTYPE)) {

                    XINGTU_LOGTYPE_INT = 2;

                } else {

                    XINGTU_LOGTYPE_INT = 3;

                }

            } else if (LNMPDefaultLogParser.mattcherLNMPLogList(list)) {

                LOGTYPE = ApacheDefaultFormatParser.FormatType.LNMP;

                XINGTU_LOGTYPE_INT = 5;

            } else if (ApacheCustomLogParser.mattcherApacheCustomLogList(list)) {

                LOGTYPE = ApacheDefaultFormatParser.FormatType.Apache_Custom;

                XINGTU_LOGTYPE_INT = 6;

            } else if (IISFormatParser.mattcherIISLogFormatType(list)) {

                LOGTYPE = ApacheDefaultFormatParser.FormatType.IIS;

                XINGTU_LOGTYPE_INT = 7;

            }

        }


        if (XINGTU_LOGTYPE_INT > 0) {

            return true;

        }


        LOG_SAMPLE = list;


        return false;

    }


    public static LogEntity parse(String line) {

        String globalHost = (String) ConfigUtil.formatConfig.get("host");


        String fmtLog = "";

        switch (XINGTU_LOGTYPE_INT) {

            case 1:

                fmtLog = W3CFormatParser.parse(line, globalHost);

                break;


            case 2:

                try {

                    fmtLog = ApacheDefaultFormatParser.parse(line, LOGTYPE, globalHost);

                } catch (LogParserException e) {

                    e.printStackTrace();

                }

                break;


            case 3:

                try {

                    fmtLog = ApacheDefaultFormatParser.parse(line, LOGTYPE, globalHost);

                } catch (LogParserException e) {

                    e.printStackTrace();

                }

                break;

            case 4:

                fmtLog = CustomLogFormatParser.parse(line, globalHost);

                break;


            case 5:

                try {

                    fmtLog = LNMPDefaultLogParser.parseLNMPLog(line, globalHost);

                } catch (Exception e) {

                    e.printStackTrace();

                }

                break;


            case 6:

                try {

                    fmtLog = ApacheCustomLogParser.parseApacheCustomLog(line, globalHost);

                } catch (Exception e) {

                    e.printStackTrace();

                }

                break;


            case 7:

                try {

                    fmtLog = IISFormatParser.parse(line, globalHost);

                } catch (Exception e) {

                    e.printStackTrace();

                }

                break;


            case 8:

                try {

                    fmtLog = AgentLogFormatParser.parse(line, globalHost);

                } catch (Exception e) {

                    e.printStackTrace();

                }

                break;

        }


        if (fmtLog == null || fmtLog.isEmpty()) {

            return null;

        }


        String[] item = fmtLog.split("\t");


        String remote_addr = item[0];

        String time_local = item[1];

        String host = item[2];

        String request_uri = item[3];

        String status = item[4];

        String contentLength = (item.length >= 6) ? item[5] : "0";

        String referer = "-";

        String ua = "-";

        if (item.length == 8) {

            referer = item[6];

            ua = item[7];

        }


        long band = 0L;

        try {

            band = Long.valueOf(contentLength).longValue();

        } catch (Exception ex) {
        }


        return new LogEntity(remote_addr, time_local, host, request_uri, status, band, referer, ua);

    }

}