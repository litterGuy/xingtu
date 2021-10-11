
package com.qihoo.wzws.rzb.util;


import com.daima.common.exception.ErrorCode;
import com.daima.common.exception.NSException;
import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.parse.AutomaticLogFormatParser;
import com.qihoo.wzws.rzb.parse.CompanyLogFormatParser;
import com.qihoo.wzws.rzb.parse.CustomLogFormatParser;
import com.qihoo.wzws.rzb.secure.ReportOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;

@Slf4j
public class ValidateConfig {
    private static int xingtu_logtype = 0;
    private static int xingtu_pagetype = 0;
    private static int xingtu_httpcode = 0;
    private static int xingtu_urltype = 0;
    private static int xingtu_uniq = 0;
    private static int xingtu_rules = 0;
    private static int xingtu_output = 0;


    private static String globalHost = "";


    private static int concurrent_request = 1000;
    private static double request_growth = 0.5D;
    private static double ip_rate = 0.5D;


    public static boolean validateSysConf(String[] args) throws NSException {

        if (!ConfigUtil.initSysConf(args[0])) {

            return false;

        }

        if (StringUtils.isEmpty(args[2])) {
            return false;
        }


        String uid = ConfigUtil.formatConfig.get("sign");

        if (uid == null || uid.trim().length() == 0) {

            ReportOutput.generateUid();

            ConfigUtil.initSysConf(args[0]);

        }


        String host = ConfigUtil.formatConfig.get("host");

        String logfile = args[2];

        if (logfile == null || logfile.trim().length() == 0) {

            log.error("传递分析日志地址为空");

            return false;

        }


        File logF = new File(logfile);

        if (!logF.exists()) {

            log.error("请确认传递分析日志地址是否有效");

            return false;

        }


        int xingtu_logtype = ((Integer) ConfigUtil.m_config.get("xingtu_logtype")).intValue();

        try {

            if (xingtu_logtype == 2) {

                CustomLogFormatParser.loadCustomLogFormatConfig();

            } else if (xingtu_logtype == 3) {
                // 处理公司内部nginx日志格式
                boolean parseTrue = CompanyLogFormatParser.matcherLogFormatType(logfile);
                if (!parseTrue) {
                    log.error("暂不支持该类日志,请反馈到钉钉群。谢谢！");
                    throw new NSException(new ErrorCode(1000, "暂不支持该类日志,请反馈到钉钉群。谢谢！"));
                }
            } else {


                boolean parseTrue = false;

                try {

                    parseTrue = AutomaticLogFormatParser.matcherLogFormatType(logfile);

                } catch (SystemConfigException ex) {

                    System.out.println(ex.getMessage());

                    return false;

                }

                if (!parseTrue) {


                    List<String> sample = AutomaticLogFormatParser.LOG_SAMPLE;

                    log.error("暂不支持该类日志,请反馈到星图官方群。谢谢！");


                    return false;

                }

            }

        } catch (SystemConfigException ex) {

            log.error(ex.getMessage());

            return false;

        }


        String scheduleAnalysis = ConfigUtil.formatConfig.get("schedule_analysis");

        if (scheduleAnalysis == null || scheduleAnalysis.trim().length() == 0) {

            log.error("请在配置文件config.ini中设置[schedule_analysis配置项]");

            return false;

        }

        if (!"1".equals(scheduleAnalysis) && !"2".equals(scheduleAnalysis)) {

            log.error("请在配置文件config.ini中设置[schedule_analysis配置项]");

            return false;

        }


        String xingtu_email = ConfigUtil.formatConfig.get("xingtu_email");

        if (xingtu_email != null && xingtu_email.length() > 1 &&
                !Utils.checkEmail(xingtu_email)) {

            log.error("请配置文件config.ini中正确设置[" + xingtu_email + "配置项]");

            return false;

        }


        String common_analysis = ConfigUtil.formatConfig.get("common_analysis");

        if (!"1".equals(common_analysis) && !"2".equals(common_analysis)) {

            log.error("请在配置文件config.ini中设置[common_analysis配置项]");

            return false;

        }


        String ccAnalysis = ConfigUtil.formatConfig.get("cc_analysis");

        if (!"1".equals(ccAnalysis) && !"2".equals(ccAnalysis)) {

            log.error("请在配置文件config.ini中设置[cc_analysis配置项]");

            return false;

        }

        if ("2".equals(ccAnalysis)) {

            try {

                Integer.valueOf(ConfigUtil.formatConfig.get("cc_concurrent_request"));

                Double.valueOf(ConfigUtil.formatConfig.get("cc_request_growth"));

                Double.valueOf(ConfigUtil.formatConfig.get("cc_ip_rate"));

            } catch (Exception ex) {

                log.error("请在配置文件config.ini中正确设置cc攻击自定义配置");

                return false;

            }

        }


        log.info("设置的host为:" + host);

        log.info("日志路径为:" + logfile);


        return true;

    }


    public static void validateRuleConf(String rulePath) throws NSException {
        ConfigUtil.initRuleConf(rulePath);
    }


    public static void main(String[] args) throws SystemConfigException {

        if (args.length != 3) {

            System.err.printf("参数校验程序\nUsage : %s [generic options]<系统配置文件> <规则配置文件> <自定义规则文件>\n", new Object[]{ValidateConfig.class.getName()});

            return;

        }

    }

}