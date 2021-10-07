/*     */ package com.qihoo.wzws.rzb.parse;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.util.DateUtil;
/*     */ import com.qihoo.wzws.rzb.util.Utils;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LNMPDefaultLogParser
/*     */ {
/*  22 */   private static int ipIndex = -1;
/*  23 */   private static int datetimeIndex = -1;
/*  24 */   private static int requestUrlIndex = -1;
/*  25 */   private static int reponseCodeIndex = -1;
/*  26 */   private static int contentLengthIndex = -1;
/*  27 */   private static int refererIndex = -1;
/*  28 */   private static int uaIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private static Pattern regexIp = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) .* (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})?");
/*  35 */   private static Pattern regexDateTime = Pattern.compile("((\\d{2}.\\w{3}.\\d{4}|\\d{4}.\\d{2}.\\d{2}).\\d{2}\\:\\d{2}\\:\\d{2})\\s");
/*  36 */   private static String rengxDate = new String("\\d{2}.\\w{3}.\\d{4}.\\d{2}\\:\\d{2}\\:\\d{2}");
/*     */   
/*  38 */   private static Pattern regexUrl = Pattern.compile(" \\w{3,5} (.*) HTTP/\\d.\\d ");
/*  39 */   private static Pattern regexStatusAContentLength = Pattern.compile(" (\\d{3}) (\\d{1,}+) ");
/*  40 */   private static Pattern regexRefferer = Pattern.compile(" (http\\S+) ");
/*  41 */   private static Pattern regexUserAgent = Pattern.compile(" (\\w{5,}/[.\\w]+ \\(.*\\).*)");
/*     */ 
/*     */   
/*     */   public static boolean mattcherLNMPLogList(List<String> list) {
/*  45 */     if (list == null || list.size() == 0) {
/*  46 */       return false;
/*     */     }
/*     */     
/*  49 */     String first = Utils.getLongestFromList(list);
/*  50 */     ApacheDefaultFormatParser.FormatType type = null;
/*     */     
/*  52 */     if (mattcherLNMPLog(first)) {
/*  53 */       type = ApacheDefaultFormatParser.FormatType.LNMP;
/*     */     }
/*     */ 
/*     */     
/*  57 */     if (type != null) {
/*  58 */       int sum = 0;
/*  59 */       for (int i = 0; i < list.size(); i++) {
/*  60 */         if (mattcherLNMPLog(list.get(i))) {
/*  61 */           sum++;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  66 */       if (sum > list.size() / 2) {
/*  67 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return false;
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
/*     */   public static boolean mattcherLNMPLog(String line) {
/*  83 */     Matcher m_ip = regexIp.matcher(line);
/*  84 */     if (m_ip.find() && m_ip.groupCount() >= 1 && m_ip.group(1) != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       ipIndex = 0;
/*     */     }
/*     */ 
/*     */     
/*  96 */     Matcher m_datetime = regexDateTime.matcher(line);
/*  97 */     if (m_datetime.find() && m_datetime.groupCount() >= 1 && m_datetime.group(1) != null)
/*     */     {
/*     */ 
/*     */       
/* 101 */       datetimeIndex = 0;
/*     */     }
/*     */ 
/*     */     
/* 105 */     Matcher m_url = regexUrl.matcher(line);
/* 106 */     if (m_url.find() && m_url.groupCount() >= 1)
/*     */     {
/*     */       
/* 109 */       requestUrlIndex = 0;
/*     */     }
/*     */ 
/*     */     
/* 113 */     Matcher m_statusAcontentLength = regexStatusAContentLength.matcher(line);
/* 114 */     if (m_statusAcontentLength.find() && m_statusAcontentLength.groupCount() >= 2 && m_statusAcontentLength.group(1) != null && m_statusAcontentLength.group(2) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       reponseCodeIndex = 0;
/* 120 */       contentLengthIndex = 0;
/*     */     } 
/*     */ 
/*     */     
/* 124 */     Matcher m_referer = regexRefferer.matcher(line);
/* 125 */     if (m_referer.find() && m_referer.groupCount() >= 1 && m_referer.group(1) != null)
/*     */     {
/*     */ 
/*     */       
/* 129 */       refererIndex = 0;
/*     */     }
/*     */ 
/*     */     
/* 133 */     Matcher m_ua = regexUserAgent.matcher(line);
/* 134 */     if (m_ua.find() && m_ua.groupCount() >= 1 && m_ua.group(1) != null)
/*     */     {
/*     */ 
/*     */       
/* 138 */       uaIndex = 0;
/*     */     }
/*     */ 
/*     */     
/* 142 */     if (datetimeIndex > -1 && ipIndex > -1 && requestUrlIndex > -1 && reponseCodeIndex > -1 && contentLengthIndex > -1) {
/* 143 */       return true;
/*     */     }
/*     */     
/* 146 */     return false;
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
/*     */   
/*     */   public static String parseLNMPLog(String line, String host) {
/* 160 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*     */     
/* 163 */     Matcher m_ip = regexIp.matcher(line);
/* 164 */     if (m_ip.find() && m_ip.groupCount() >= 1) {
/* 165 */       if (m_ip.groupCount() >= 2 && m_ip.group(2) != null) {
/*     */         
/* 167 */         sb.append(Utils.getRemoteIp(m_ip.group(2)));
/*     */       } else {
/*     */         
/* 170 */         sb.append(Utils.getRemoteIp(m_ip.group(1)));
/*     */       } 
/*     */     }
/*     */     
/* 174 */     sb.append("\t");
/*     */ 
/*     */     
/* 177 */     Matcher m_datetime = regexDateTime.matcher(line);
/* 178 */     if (m_datetime.find() && m_datetime.groupCount() >= 1) {
/* 179 */       String a = m_datetime.group(1);
/* 180 */       if (a.matches(rengxDate)) {
/* 181 */         DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
/* 182 */         sb.append(DateUtil.formatStrDate(a, dateFormatter));
/*     */       } else {
/* 184 */         sb.append(a);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 189 */     sb.append("\t");
/* 190 */     sb.append(host);
/* 191 */     sb.append("\t");
/*     */ 
/*     */     
/* 194 */     Matcher m_url = regexUrl.matcher(line);
/* 195 */     if (m_url.find() && m_url.groupCount() >= 1) {
/* 196 */       sb.append(m_url.group(1));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 201 */     sb.append("\t");
/*     */ 
/*     */     
/* 204 */     Matcher m_statusAcontentLength = regexStatusAContentLength.matcher(line);
/* 205 */     if (m_statusAcontentLength.find() && m_statusAcontentLength.groupCount() >= 2) {
/* 206 */       sb.append(m_statusAcontentLength.group(1));
/* 207 */       sb.append("\t");
/* 208 */       sb.append(m_statusAcontentLength.group(2));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     Matcher m_referer = regexRefferer.matcher(line);
/* 216 */     if (m_referer.find() && m_referer.groupCount() >= 1) {
/* 217 */       sb.append("\t");
/* 218 */       sb.append(m_statusAcontentLength.group(1));
/*     */     }
/*     */     else {
/*     */       
/* 222 */       sb.append("\t");
/* 223 */       sb.append("-");
/*     */     } 
/*     */ 
/*     */     
/* 227 */     Matcher m_ua = regexUserAgent.matcher(line);
/* 228 */     if (m_ua.find() && m_ua.groupCount() >= 1) {
/* 229 */       sb.append("\t");
/* 230 */       sb.append(m_ua.group(1));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 240 */     String line = "58.49.105.195 - - [14/Oct/2014:04:07:47 +0800] GET /show/20140825/153120.html HTTP/1.1 200 5948 - Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm) 157.55.39.97";
/* 241 */     String line1 = "58.49.105.195 - - [2014-10-14 04:07:47 +0800] GET /show/20140825/153120.html HTTP/1.1 200 5948 - Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm) 157.55.39.97";
/*     */ 
/*     */     
/* 244 */     System.out.println(parseLNMPLog(line, "www.test.cn"));
/* 245 */     System.out.println(parseLNMPLog(line1, "www.test.cn"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\parse\LNMPDefaultLogParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */