/*     */ package com.qihoo.wzws.rzb.parse;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.exception.LogParserException;
/*     */ import com.qihoo.wzws.rzb.exception.SystemConfigException;
/*     */ import com.qihoo.wzws.rzb.secure.po.LogEntity;
/*     */ import com.qihoo.wzws.rzb.util.ConfigUtil;
/*     */ import com.qihoo.wzws.rzb.util.Utils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutomaticLogFormatParser
/*     */ {
/*     */   public static int XINGTU_LOGTYPE_INT;
/*     */   private static ApacheDefaultFormatParser.FormatType LOGTYPE;
/*     */   public static List<String> LOG_SAMPLE;
/*     */   
/*     */   public static boolean matcherLogFormatType(String logFile) throws SystemConfigException {
/*  34 */     File file = null;
/*  35 */     File fileDir = new File(logFile);
/*  36 */     if (fileDir.isDirectory()) {
/*  37 */       File[] files = fileDir.listFiles();
/*  38 */       if (files.length >= 1) {
/*  39 */         file = Utils.getlastestFileFromDir(files);
/*     */       }
/*  41 */       fileDir = null;
/*     */     } else {
/*  43 */       file = fileDir;
/*     */     } 
/*     */ 
/*     */     
/*  47 */     List<String> list = new ArrayList<String>();
/*  48 */     BufferedReader reader = null;
/*     */     try {
/*  50 */       reader = new BufferedReader(new FileReader(file));
/*  51 */       String tempString = null;
/*  52 */       while ((tempString = reader.readLine()) != null) {
/*     */         
/*  54 */         list.add(tempString);
/*  55 */         if (list.size() >= 10) {
/*     */           break;
/*     */         }
/*     */       } 
/*  59 */     } catch (IOException e) {
/*  60 */       e.printStackTrace();
/*     */     } finally {
/*  62 */       if (reader != null) {
/*     */         try {
/*  64 */           reader.close();
/*  65 */         } catch (IOException e1) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (list != null && list.size() > 0)
/*     */     {
/*  73 */       if (AgentLogFormatParser.mattcherAgentLogFormatTypeByTemplate(list)) {
/*  74 */         AgentLogFormatParser.initAgentLogFormatTypeByTemplate();
/*  75 */         LOGTYPE = ApacheDefaultFormatParser.FormatType.Agent_Log;
/*  76 */         XINGTU_LOGTYPE_INT = 8;
/*     */       }
/*  78 */       else if (W3CFormatParser.mattcherW3CLogFormatTypeByTemplate(list)) {
/*  79 */         W3CFormatParser.initW3CLogFormatTypeByTemplate();
/*  80 */         LOGTYPE = ApacheDefaultFormatParser.FormatType.W3C_ALL;
/*  81 */         XINGTU_LOGTYPE_INT = 1;
/*  82 */       } else if (ApacheDefaultFormatParser.mattcherApacheLogFormatTypeByTemplate(list)) {
/*  83 */         LOGTYPE = ApacheDefaultFormatParser.current_apache_logType;
/*  84 */         if (ApacheDefaultFormatParser.FormatType.Apache_Common.equals(LOGTYPE)) {
/*  85 */           XINGTU_LOGTYPE_INT = 2;
/*     */         } else {
/*  87 */           XINGTU_LOGTYPE_INT = 3;
/*     */         } 
/*  89 */       } else if (LNMPDefaultLogParser.mattcherLNMPLogList(list)) {
/*  90 */         LOGTYPE = ApacheDefaultFormatParser.FormatType.LNMP;
/*  91 */         XINGTU_LOGTYPE_INT = 5;
/*  92 */       } else if (ApacheCustomLogParser.mattcherApacheCustomLogList(list)) {
/*  93 */         LOGTYPE = ApacheDefaultFormatParser.FormatType.Apache_Custom;
/*  94 */         XINGTU_LOGTYPE_INT = 6;
/*  95 */       } else if (IISFormatParser.mattcherIISLogFormatType(list)) {
/*  96 */         LOGTYPE = ApacheDefaultFormatParser.FormatType.IIS;
/*  97 */         XINGTU_LOGTYPE_INT = 7;
/*     */       } 
/*     */     }
/*     */     
/* 101 */     if (XINGTU_LOGTYPE_INT > 0)
/*     */     {
/* 103 */       return true;
/*     */     }
/*     */     
/* 106 */     LOG_SAMPLE = list;
/*     */     
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LogEntity parse(String line) {
/* 120 */     String globalHost = (String)ConfigUtil.formatConfig.get("host");
/*     */ 
/*     */     
/* 123 */     String fmtLog = "";
/* 124 */     switch (XINGTU_LOGTYPE_INT) {
/*     */       case 1:
/* 126 */         fmtLog = W3CFormatParser.parse(line, globalHost);
/*     */         break;
/*     */       
/*     */       case 2:
/*     */         try {
/* 131 */           fmtLog = ApacheDefaultFormatParser.parse(line, LOGTYPE, globalHost);
/* 132 */         } catch (LogParserException e) {
/* 133 */           e.printStackTrace();
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 3:
/*     */         try {
/* 139 */           fmtLog = ApacheDefaultFormatParser.parse(line, LOGTYPE, globalHost);
/* 140 */         } catch (LogParserException e) {
/* 141 */           e.printStackTrace();
/*     */         } 
/*     */         break;
/*     */       case 4:
/* 145 */         fmtLog = CustomLogFormatParser.parse(line, globalHost);
/*     */         break;
/*     */       
/*     */       case 5:
/*     */         try {
/* 150 */           fmtLog = LNMPDefaultLogParser.parseLNMPLog(line, globalHost);
/* 151 */         } catch (Exception e) {
/* 152 */           e.printStackTrace();
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/*     */         try {
/* 159 */           fmtLog = ApacheCustomLogParser.parseApacheCustomLog(line, globalHost);
/* 160 */         } catch (Exception e) {
/* 161 */           e.printStackTrace();
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/*     */         try {
/* 168 */           fmtLog = IISFormatParser.parse(line, globalHost);
/* 169 */         } catch (Exception e) {
/* 170 */           e.printStackTrace();
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 8:
/*     */         try {
/* 176 */           fmtLog = AgentLogFormatParser.parse(line, globalHost);
/* 177 */         } catch (Exception e) {
/* 178 */           e.printStackTrace();
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     if (fmtLog == null || fmtLog.isEmpty()) {
/* 189 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 193 */     String[] item = fmtLog.split("\t");
/*     */     
/* 195 */     String remote_addr = item[0];
/* 196 */     String time_local = item[1];
/* 197 */     String host = item[2];
/* 198 */     String request_uri = item[3];
/* 199 */     String status = item[4];
/* 200 */     String contentLength = (item.length >= 6) ? item[5] : "0";
/* 201 */     String referer = "-";
/* 202 */     String ua = "-";
/* 203 */     if (item.length == 8) {
/* 204 */       referer = item[6];
/* 205 */       ua = item[7];
/*     */     } 
/*     */     
/* 208 */     long band = 0L;
/*     */     try {
/* 210 */       band = Long.valueOf(contentLength).longValue();
/* 211 */     } catch (Exception ex) {}
/*     */ 
/*     */     
/* 214 */     return new LogEntity(remote_addr, time_local, host, request_uri, status, band, referer, ua);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\parse\AutomaticLogFormatParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */