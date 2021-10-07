/*     */ package com.qihoo.wzws.rzb.util;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.util.ip.IPDataHandler;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ public class Utils
/*     */ {
/*  26 */   public static Map<String, String> sosuoMap = new HashMap<String, String>(); private static final String TIME_LOCAL = "time_local"; private static final String REMOTE_ADDR = "remote_addr";
/*     */   static {
/*  28 */     sosuoMap.put("googlebot", "google");
/*  29 */     sosuoMap.put("qh360bot", "360");
/*  30 */     sosuoMap.put("baidubot", "baidu");
/*  31 */     sosuoMap.put("sogou", "sogou");
/*  32 */     sosuoMap.put("bingbot", "bing");
/*  33 */     sosuoMap.put("yahoobot", "yahoo");
/*  34 */     sosuoMap.put("sosobot", "soso");
/*     */   }
/*     */   private static final String STATUS = "status"; private static final String HOST = "host";
/*     */   private static final String REQUEST_URI = "request_uri";
/*     */   
/*  39 */   public enum LineCounters { ALL_LINES,
/*  40 */     VALID_LINES,
/*  41 */     BAD_LINES,
/*     */ 
/*     */     
/*  44 */     MOBILE_LINES,
/*  45 */     NOMOBILE_LINES,
/*  46 */     MATCH_LINES,
/*  47 */     NOMATCH_LINES,
/*     */ 
/*     */     
/*  50 */     PC_LINES,
/*  51 */     OTHER_LINES,
/*     */ 
/*     */     
/*  54 */     GETREGION_LINES,
/*  55 */     NO_REGION_LINES,
/*  56 */     CN_REGION_LINES,
/*  57 */     NO_CNREGION_LINES; }
/*     */ 
/*     */   
/*     */   protected enum CustomFormatFields {
/*  61 */     time_local,
/*  62 */     remote_addr,
/*  63 */     status,
/*  64 */     host,
/*  65 */     request_uri;
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
/*  76 */   private static int timeIndex = 0;
/*  77 */   private static int ipIndex = 0;
/*  78 */   private static int statusIndex = 0;
/*  79 */   private static int hostIndex = 0;
/*  80 */   private static int urlIndex = 0;
/*     */ 
/*     */   
/*  83 */   private static Map<String, Integer> map = new HashMap<String, Integer>();
/*     */ 
/*     */   
/*     */   public static void loadSystemConfig(String sysConfigPath) {
/*  87 */     InputStream is = null;
/*  88 */     BufferedReader br = null;
/*     */     
/*     */     try {
/*  91 */       is = Utils.class.getResourceAsStream(sysConfigPath);
/*  92 */       br = new BufferedReader(new InputStreamReader(is));
/*  93 */       String s1 = null;
/*  94 */       String[] items = null;
/*     */       
/*  96 */       while ((s1 = br.readLine()) != null) {
/*  97 */         if (!s1.startsWith("#")) {
/*  98 */           items = s1.split("\\|");
/*  99 */           int size = items.length;
/* 100 */           if (items.length > 0) {
/* 101 */             for (int i = 0; i < items.length - 1; i++) {
/* 102 */               map.put(items[i], Integer.valueOf(i));
/*     */             }
/*     */           }
/*     */ 
/*     */           
/* 107 */           timeIndex = ((Integer)map.get("time_local")).intValue();
/* 108 */           ipIndex = ((Integer)map.get("remote_addr")).intValue();
/* 109 */           statusIndex = ((Integer)map.get("status")).intValue();
/* 110 */           hostIndex = ((Integer)map.get("host")).intValue();
/* 111 */           urlIndex = ((Integer)map.get("request_uri")).intValue();
/*     */         } 
/*     */       } 
/*     */       
/* 115 */       br.close();
/*     */     }
/* 117 */     catch (IOException e) {
/* 118 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 123 */   private static final Set<String> set = new HashSet<String>(); private static final String emailPatternStr = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
/*     */   static {
/* 125 */     set.add("|");
/* 126 */     set.add("*");
/* 127 */     set.add("+");
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
/*     */   public static String[] parseLog(String log, String separator) {
/* 139 */     if (set.contains(separator)) {
/* 140 */       separator = "\\" + separator;
/*     */     }
/*     */     
/* 143 */     String[] items = log.split(separator);
/* 144 */     return items;
/*     */   }
/*     */   
/*     */   public static String getMac() {
/* 148 */     StringBuilder sb = new StringBuilder();
/*     */     try {
/* 150 */       InetAddress address = InetAddress.getLocalHost();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 156 */       NetworkInterface ni = NetworkInterface.getByInetAddress(address);
/* 157 */       if (ni != null) {
/* 158 */         byte[] mac = ni.getHardwareAddress();
/* 159 */         if (mac != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 164 */           for (int i = 0; i < mac.length; i++) {
/* 165 */             String s = String.format("%02X%s", new Object[] { Byte.valueOf(mac[i]), (i < mac.length - 1) ? "-" : "" });
/* 166 */             sb.append(s.toLowerCase());
/*     */           } 
/*     */         } else {
/* 169 */           System.out.println("Address doesn't exist or is not accessible.");
/*     */         } 
/*     */       } else {
/* 172 */         System.out.println("Network Interface for the specified address is not found.");
/*     */       } 
/* 174 */     } catch (UnknownHostException e) {
/*     */       
/* 176 */       e.printStackTrace();
/* 177 */     } catch (SocketException se) {
/* 178 */       se.printStackTrace();
/*     */     } 
/*     */     
/* 181 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/* 185 */   private static final Pattern emailPattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
/*     */   public static boolean checkEmail(String email) {
/* 187 */     if (null == email || "".equals(email) || email.trim().length() > 50) {
/* 188 */       return false;
/*     */     }
/* 190 */     Matcher m = emailPattern.matcher(email);
/* 191 */     return m.matches();
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
/*     */   
/*     */   public static boolean isPVbyContentType(String sent_http_content_type, String status) {
/* 204 */     if ((sent_http_content_type.contains("text/html") || sent_http_content_type.contains("text/plain") || sent_http_content_type.contains("text/xml") || sent_http_content_type.contains("text/json")) && status.equals("200"))
/*     */     {
/*     */ 
/*     */       
/* 208 */       return true;
/*     */     }
/*     */     
/* 211 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isPV(String request_uri, String status) {
/* 215 */     if ("200".equals(status) && !request_uri.contains(".js") && !request_uri.contains(".css") && !request_uri.contains(".jpg") && !request_uri.contains(".png") && !request_uri.contains(".gif") && !request_uri.contains(".ico") && !request_uri.contains(".txt") && !request_uri.contains(".log"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       return true;
/*     */     }
/*     */     
/* 227 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isPage(String request_uri) {
/* 231 */     if (!request_uri.contains(".js") && !request_uri.contains(".css") && !request_uri.contains(".jpg") && !request_uri.contains(".png") && !request_uri.contains(".gif") && !request_uri.contains(".ico") && !request_uri.contains(".txt") && !request_uri.contains(".log"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 239 */       return true;
/*     */     }
/*     */     
/* 242 */     return false;
/*     */   }
/*     */   
/*     */   public static File getlastestFileFromDir(File[] files) {
/* 246 */     if (files.length == 1) {
/* 247 */       return files[0];
/*     */     }
/*     */     
/* 250 */     List<Long> fileUpdateList = new ArrayList<Long>();
/* 251 */     Map<Long, Integer> fileListIndexMap = new HashMap<Long, Integer>();
/* 252 */     for (int i = 0; i < files.length; i++) {
/* 253 */       fileUpdateList.add(Long.valueOf(files[i].lastModified()));
/* 254 */       fileListIndexMap.put(Long.valueOf(files[i].lastModified()), Integer.valueOf(i));
/*     */     } 
/*     */     
/* 257 */     Collections.sort(fileUpdateList);
/* 258 */     int lastestIndex = fileUpdateList.size() - 1;
/*     */     
/* 260 */     return files[((Integer)fileListIndexMap.get(fileUpdateList.get(lastestIndex))).intValue()];
/*     */   }
/*     */   
/*     */   public static String getIPRegionLocal(String ip) {
/* 264 */     String ipLocation = "";
/* 265 */     String line = IPDataHandler.findGeography(ip);
/* 266 */     if (line != null) {
/* 267 */       String[] strArray = line.split("\t");
/* 268 */       if (strArray.length >= 2) {
/* 269 */         if ("中国".equals(strArray[0])) {
/* 270 */           ipLocation = "中国-" + strArray[1];
/* 271 */           if (strArray.length >= 3) {
/* 272 */             if (!strArray[1].equals(strArray[2])) {
/* 273 */               ipLocation = ipLocation + strArray[2];
/*     */             }
/* 275 */             if (strArray.length == 4) {
/* 276 */               ipLocation = ipLocation + "-" + strArray[3];
/*     */             }
/*     */           } 
/*     */         } else {
/* 280 */           ipLocation = strArray[0];
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 285 */     return ipLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRemoteIp(String ips) {
/* 294 */     if (ips.indexOf(",") > 1) {
/* 295 */       int index = ips.indexOf(",");
/* 296 */       if (index > 1 && index < ips.length()) {
/* 297 */         return ips.substring(0, index);
/*     */       }
/*     */     } 
/* 300 */     return ips;
/*     */   }
/*     */   
/*     */   public static String getLongestFromList(List<String> list) {
/* 304 */     int maxlength = ((String)list.get(0)).length();
/* 305 */     int maxlengthIndex = 0;
/*     */     
/* 307 */     for (int i = 1; i < list.size(); i++) {
/* 308 */       int length = ((String)list.get(i)).length();
/* 309 */       if (length > maxlength) {
/* 310 */         maxlength = length;
/* 311 */         maxlengthIndex = i;
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     return list.get(maxlengthIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 325 */     System.out.println(getRemoteIp("192.168.1.1,192.168.1.2,192.168.1.3"));
/* 326 */     System.out.println(getRemoteIp("192.168.1.5"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */