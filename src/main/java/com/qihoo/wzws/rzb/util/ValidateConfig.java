/*     */ package com.qihoo.wzws.rzb.util;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.exception.SystemConfigException;
/*     */ import com.qihoo.wzws.rzb.parse.AutomaticLogFormatParser;
/*     */ import com.qihoo.wzws.rzb.parse.CustomLogFormatParser;
/*     */ import com.qihoo.wzws.rzb.secure.ReportOutput;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidateConfig
/*     */ {
/*  20 */   private static int xingtu_logtype = 0;
/*  21 */   private static int xingtu_pagetype = 0;
/*  22 */   private static int xingtu_httpcode = 0;
/*  23 */   private static int xingtu_urltype = 0;
/*  24 */   private static int xingtu_uniq = 0;
/*  25 */   private static int xingtu_rules = 0;
/*  26 */   private static int xingtu_output = 0;
/*     */ 
/*     */   
/*  29 */   private static String globalHost = "";
/*     */ 
/*     */   
/*  32 */   private static int concurrent_request = 1000;
/*  33 */   private static double request_growth = 0.5D;
/*  34 */   private static double ip_rate = 0.5D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validateSysConf(String[] args) {
/*  40 */     if (!ConfigUtil.initSysConf(args[0])) {
/*  41 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  45 */     String uid = ConfigUtil.formatConfig.get("sign");
/*  46 */     if (uid == null || uid.trim().length() == 0) {
/*  47 */       ReportOutput.generateUid();
/*  48 */       ConfigUtil.initSysConf(args[0]);
/*     */     } 
/*     */     
/*  51 */     String host = ConfigUtil.formatConfig.get("host");
/*  52 */     String logfile = ConfigUtil.formatConfig.get("log_file");
/*  53 */     if (logfile == null || logfile.trim().length() == 0) {
/*  54 */       System.out.println("请在配置文件config.ini中设置日志存放路径[log_file配置项]");
/*  55 */       return false;
/*     */     } 
/*     */     
/*  58 */     File logF = new File(logfile);
/*  59 */     if (!logF.exists()) {
/*  60 */       System.out.println("请确认配置文件config.ini中设置的日志存放路径[log_file配置项]是否有效");
/*  61 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  65 */     int xingtu_logtype = ((Integer)ConfigUtil.m_config.get("xingtu_logtype")).intValue();
/*     */     try {
/*  67 */       if (xingtu_logtype == 2) {
/*  68 */         CustomLogFormatParser.loadCustomLogFormatConfig();
/*     */       } else {
/*     */         
/*  71 */         boolean parseTrue = false;
/*     */         try {
/*  73 */           parseTrue = AutomaticLogFormatParser.matcherLogFormatType(logfile);
/*  74 */         } catch (SystemConfigException ex) {
/*  75 */           System.out.println(ex.getMessage());
/*  76 */           return false;
/*     */         } 
/*  78 */         if (!parseTrue) {
/*     */           
/*  80 */           List<String> sample = AutomaticLogFormatParser.LOG_SAMPLE;
/*  81 */           System.out.println("暂不支持该类日志,请反馈到星图官方群。谢谢！");
/*     */           
/*  83 */           return false;
/*     */         } 
/*     */       } 
/*  86 */     } catch (SystemConfigException ex) {
/*  87 */       System.out.println(ex.getMessage());
/*  88 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  93 */     String scheduleAnalysis = ConfigUtil.formatConfig.get("schedule_analysis");
/*  94 */     if (scheduleAnalysis == null || scheduleAnalysis.trim().length() == 0) {
/*  95 */       System.out.println("请在配置文件config.ini中设置[schedule_analysis配置项]");
/*  96 */       return false;
/*     */     } 
/*  98 */     if (!"1".equals(scheduleAnalysis) && !"2".equals(scheduleAnalysis)) {
/*  99 */       System.out.println("请在配置文件config.ini中设置[schedule_analysis配置项]");
/* 100 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 104 */     String xingtu_email = ConfigUtil.formatConfig.get("xingtu_email");
/* 105 */     if (xingtu_email != null && xingtu_email.length() > 1 && 
/* 106 */       !Utils.checkEmail(xingtu_email)) {
/* 107 */       System.out.println("请配置文件config.ini中正确设置[" + xingtu_email + "配置项]");
/* 108 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     String common_analysis = ConfigUtil.formatConfig.get("common_analysis");
/* 113 */     if (!"1".equals(common_analysis) && !"2".equals(common_analysis)) {
/* 114 */       System.out.println("请在配置文件config.ini中设置[common_analysis配置项]");
/* 115 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 119 */     String ccAnalysis = ConfigUtil.formatConfig.get("cc_analysis");
/* 120 */     if (!"1".equals(ccAnalysis) && !"2".equals(ccAnalysis)) {
/* 121 */       System.out.println("请在配置文件config.ini中设置[cc_analysis配置项]");
/* 122 */       return false;
/*     */     } 
/* 124 */     if ("2".equals(ccAnalysis)) {
/*     */       try {
/* 126 */         Integer.valueOf(ConfigUtil.formatConfig.get("cc_concurrent_request"));
/* 127 */         Double.valueOf(ConfigUtil.formatConfig.get("cc_request_growth"));
/* 128 */         Double.valueOf(ConfigUtil.formatConfig.get("cc_ip_rate"));
/* 129 */       } catch (Exception ex) {
/* 130 */         System.out.println("请在配置文件config.ini中正确设置cc攻击自定义配置");
/* 131 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 135 */     System.out.println("设置的host为:" + host);
/* 136 */     System.out.println("日志路径为:" + logfile);
/*     */     
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void validateRuleConf(String rulePath) throws SystemConfigException {
/* 143 */     ConfigUtil.initRuleConf(rulePath);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws SystemConfigException {
/* 148 */     if (args.length != 3) {
/* 149 */       System.err.printf("参数校验程序\nUsage : %s [generic options]<系统配置文件> <规则配置文件> <自定义规则文件>\n", new Object[] { ValidateConfig.class.getName() });
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\ValidateConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */