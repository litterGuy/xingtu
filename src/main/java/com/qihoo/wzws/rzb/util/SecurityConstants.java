
package com.qihoo.wzws.rzb.util;


import java.util.HashMap;
import java.util.Map;


public class SecurityConstants {
    public static Map<String, String> secDimMap = new HashMap<String, String>();


    static {

        secDimMap.put("SQL注入攻击", "数据");

        secDimMap.put("SQL盲注攻击探测", "数据");

        secDimMap.put("Xpath注入", "数据");


        secDimMap.put("IIS服务器攻击", "服务器");

        secDimMap.put("Nginx文件解析漏洞", "服务器");

        secDimMap.put("拒绝服务恶意脚本", "服务器");

        secDimMap.put("BashShellShock漏洞", "服务器");

        secDimMap.put("远程代码执行漏洞攻击", "服务器");


        secDimMap.put("Struts2远程代码执行攻击", "应用");

        secDimMap.put("CSRF漏洞攻击探测", "应用");

        secDimMap.put("LDAP漏洞攻击", "应用");

        secDimMap.put("跨站脚本攻击(XSS)", "应用");

        secDimMap.put("swfupload跨站", "应用");

        secDimMap.put("文件包含漏洞攻击", "应用");


        secDimMap.put("敏感文件探测", "文件");

        secDimMap.put("可疑文件访问", "文件");

        secDimMap.put("可疑HTTP请求访问", "文件");

        secDimMap.put("异常HTTP请求探测", "文件");

        secDimMap.put("敏感目录访问", "文件");


        secDimMap.put("WebCruiser扫描", "其他");

        secDimMap.put("Unknown扫描", "其他");

        secDimMap.put("w3af扫描", "其他");

        secDimMap.put("WVS扫描", "其他");

        secDimMap.put("360Webscan扫描", "其他");

        secDimMap.put("安恒Web扫描", "其他");

        secDimMap.put("超长字符串", "其他");

        secDimMap.put("特殊字符URL访问", "其他");

        secDimMap.put("非法UA", "其他");

    }


    private static final String dataSec = "数据";
    private static final String serverSec = "服务器";
    private static final String appSec = "应用";
    private static final String fileSec = "文件";
    private static final String otherSec = "其他";
    public static Map<String, Integer> secDimCountMap = new HashMap<String, Integer>();

    static {

        secDimCountMap.put("数据", Integer.valueOf(0));

        secDimCountMap.put("服务器", Integer.valueOf(0));

        secDimCountMap.put("应用", Integer.valueOf(0));

        secDimCountMap.put("文件", Integer.valueOf(0));

        secDimCountMap.put("其他", Integer.valueOf(0));

    }


    public static Map<String, String> attackTypeNameIdMap = new HashMap<String, String>();


    static {

        attackTypeNameIdMap.put("SQL注入攻击", "sqlinject");

        attackTypeNameIdMap.put("SQL盲注攻击探测", "sqlblind");

        attackTypeNameIdMap.put("Xpath注入", "xpath");


        attackTypeNameIdMap.put("IIS服务器攻击", "iis");

        attackTypeNameIdMap.put("Nginx文件解析漏洞", "nginx");

        attackTypeNameIdMap.put("拒绝服务恶意脚本", "denialService");

        attackTypeNameIdMap.put("BashShellShock漏洞", "bshell");

        attackTypeNameIdMap.put("远程代码执行漏洞攻击", "remtoe");


        attackTypeNameIdMap.put("Struts2远程代码执行攻击", "struts2");

        attackTypeNameIdMap.put("CSRF漏洞攻击探测", "csrf");

        attackTypeNameIdMap.put("LDAP漏洞攻击", "ldap");

        attackTypeNameIdMap.put("跨站脚本攻击(XSS)", "xss");

        attackTypeNameIdMap.put("swfupload跨站", "swfupload");

        attackTypeNameIdMap.put("文件包含漏洞攻击", "fileinclude");


        attackTypeNameIdMap.put("敏感文件探测", "sensitiveFile");

        attackTypeNameIdMap.put("可疑文件访问", "suspectfile");

        attackTypeNameIdMap.put("可疑HTTP请求访问", "suspecthttp");

        attackTypeNameIdMap.put("异常HTTP请求探测", "http");

        attackTypeNameIdMap.put("敏感目录访问", "sensitiveDir");


        attackTypeNameIdMap.put("WebCruiser扫描", "webcruiserScan");

        attackTypeNameIdMap.put("Unknown扫描", "nknownScan");

        attackTypeNameIdMap.put("w3af扫描", "w3afScan");

        attackTypeNameIdMap.put("WVS扫描", "wvsScan");

        attackTypeNameIdMap.put("360Webscan扫描", "360Scan");

        attackTypeNameIdMap.put("安恒Web扫描", "anhengScan");

        attackTypeNameIdMap.put("超长字符串", "longstring");

        attackTypeNameIdMap.put("特殊字符URL访问", "specialUrl");

        attackTypeNameIdMap.put("非法UA", "unusualUA");

    }

}