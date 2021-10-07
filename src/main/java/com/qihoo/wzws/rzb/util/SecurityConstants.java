/*    */ package com.qihoo.wzws.rzb.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SecurityConstants
/*    */ {
/*  8 */   public static Map<String, String> secDimMap = new HashMap<String, String>();
/*    */   
/*    */   static {
/* 11 */     secDimMap.put("SQL注入攻击", "数据");
/* 12 */     secDimMap.put("SQL盲注攻击探测", "数据");
/* 13 */     secDimMap.put("Xpath注入", "数据");
/*    */     
/* 15 */     secDimMap.put("IIS服务器攻击", "服务器");
/* 16 */     secDimMap.put("Nginx文件解析漏洞", "服务器");
/* 17 */     secDimMap.put("拒绝服务恶意脚本", "服务器");
/* 18 */     secDimMap.put("BashShellShock漏洞", "服务器");
/* 19 */     secDimMap.put("远程代码执行漏洞攻击", "服务器");
/*    */     
/* 21 */     secDimMap.put("Struts2远程代码执行攻击", "应用");
/* 22 */     secDimMap.put("CSRF漏洞攻击探测", "应用");
/* 23 */     secDimMap.put("LDAP漏洞攻击", "应用");
/* 24 */     secDimMap.put("跨站脚本攻击(XSS)", "应用");
/* 25 */     secDimMap.put("swfupload跨站", "应用");
/* 26 */     secDimMap.put("文件包含漏洞攻击", "应用");
/*    */     
/* 28 */     secDimMap.put("敏感文件探测", "文件");
/* 29 */     secDimMap.put("可疑文件访问", "文件");
/* 30 */     secDimMap.put("可疑HTTP请求访问", "文件");
/* 31 */     secDimMap.put("异常HTTP请求探测", "文件");
/* 32 */     secDimMap.put("敏感目录访问", "文件");
/*    */     
/* 34 */     secDimMap.put("WebCruiser扫描", "其他");
/* 35 */     secDimMap.put("Unknown扫描", "其他");
/* 36 */     secDimMap.put("w3af扫描", "其他");
/* 37 */     secDimMap.put("WVS扫描", "其他");
/* 38 */     secDimMap.put("360Webscan扫描", "其他");
/* 39 */     secDimMap.put("安恒Web扫描", "其他");
/* 40 */     secDimMap.put("超长字符串", "其他");
/* 41 */     secDimMap.put("特殊字符URL访问", "其他");
/* 42 */     secDimMap.put("非法UA", "其他");
/*    */   }
/*    */ 
/*    */   
/*    */   private static final String dataSec = "数据";
/*    */   private static final String serverSec = "服务器";
/*    */   private static final String appSec = "应用";
/*    */   private static final String fileSec = "文件";
/*    */   private static final String otherSec = "其他";
/* 51 */   public static Map<String, Integer> secDimCountMap = new HashMap<String, Integer>();
/*    */   static {
/* 53 */     secDimCountMap.put("数据", Integer.valueOf(0));
/* 54 */     secDimCountMap.put("服务器", Integer.valueOf(0));
/* 55 */     secDimCountMap.put("应用", Integer.valueOf(0));
/* 56 */     secDimCountMap.put("文件", Integer.valueOf(0));
/* 57 */     secDimCountMap.put("其他", Integer.valueOf(0));
/*    */   }
/*    */   
/* 60 */   public static Map<String, String> attackTypeNameIdMap = new HashMap<String, String>();
/*    */   
/*    */   static {
/* 63 */     attackTypeNameIdMap.put("SQL注入攻击", "sqlinject");
/* 64 */     attackTypeNameIdMap.put("SQL盲注攻击探测", "sqlblind");
/* 65 */     attackTypeNameIdMap.put("Xpath注入", "xpath");
/*    */     
/* 67 */     attackTypeNameIdMap.put("IIS服务器攻击", "iis");
/* 68 */     attackTypeNameIdMap.put("Nginx文件解析漏洞", "nginx");
/* 69 */     attackTypeNameIdMap.put("拒绝服务恶意脚本", "denialService");
/* 70 */     attackTypeNameIdMap.put("BashShellShock漏洞", "bshell");
/* 71 */     attackTypeNameIdMap.put("远程代码执行漏洞攻击", "remtoe");
/*    */     
/* 73 */     attackTypeNameIdMap.put("Struts2远程代码执行攻击", "struts2");
/* 74 */     attackTypeNameIdMap.put("CSRF漏洞攻击探测", "csrf");
/* 75 */     attackTypeNameIdMap.put("LDAP漏洞攻击", "ldap");
/* 76 */     attackTypeNameIdMap.put("跨站脚本攻击(XSS)", "xss");
/* 77 */     attackTypeNameIdMap.put("swfupload跨站", "swfupload");
/* 78 */     attackTypeNameIdMap.put("文件包含漏洞攻击", "fileinclude");
/*    */     
/* 80 */     attackTypeNameIdMap.put("敏感文件探测", "sensitiveFile");
/* 81 */     attackTypeNameIdMap.put("可疑文件访问", "suspectfile");
/* 82 */     attackTypeNameIdMap.put("可疑HTTP请求访问", "suspecthttp");
/* 83 */     attackTypeNameIdMap.put("异常HTTP请求探测", "http");
/* 84 */     attackTypeNameIdMap.put("敏感目录访问", "sensitiveDir");
/*    */     
/* 86 */     attackTypeNameIdMap.put("WebCruiser扫描", "webcruiserScan");
/* 87 */     attackTypeNameIdMap.put("Unknown扫描", "nknownScan");
/* 88 */     attackTypeNameIdMap.put("w3af扫描", "w3afScan");
/* 89 */     attackTypeNameIdMap.put("WVS扫描", "wvsScan");
/* 90 */     attackTypeNameIdMap.put("360Webscan扫描", "360Scan");
/* 91 */     attackTypeNameIdMap.put("安恒Web扫描", "anhengScan");
/* 92 */     attackTypeNameIdMap.put("超长字符串", "longstring");
/* 93 */     attackTypeNameIdMap.put("特殊字符URL访问", "specialUrl");
/* 94 */     attackTypeNameIdMap.put("非法UA", "unusualUA");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\SecurityConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */