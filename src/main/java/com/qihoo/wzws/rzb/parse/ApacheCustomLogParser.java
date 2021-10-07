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
/*     */ public class ApacheCustomLogParser
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
/*  36 */   private static Pattern regexUrl = Pattern.compile(" \"\\w{3,5} (.*) HTTP/\\d.\\d\" ");
/*  37 */   private static Pattern regexStatusAContentLength = Pattern.compile(" (\\d{3}) (\\d{1,}+) ");
/*  38 */   private static Pattern regexRefferer = Pattern.compile(" \"(http\\S+)\" ");
/*     */   
/*  40 */   private static Pattern regexUserAgent = Pattern.compile(" \"(\\w{5,}/[.\\w]+ \\(.*\\).*)\"");
/*     */   
/*  42 */   private static String rengxDate = new String("\\d{2}.\\w{3}.\\d{4}.\\d{2}\\:\\d{2}\\:\\d{2}");
/*     */   
/*     */   public static boolean mattcherApacheCustomLogList(List<String> list) {
/*  45 */     if (list == null || list.size() == 0) {
/*  46 */       return false;
/*     */     }
/*     */     
/*  49 */     String first = Utils.getLongestFromList(list);
/*  50 */     ApacheDefaultFormatParser.FormatType type = null;
/*     */     
/*  52 */     if (mattcherApacheCustomLog(first)) {
/*  53 */       type = ApacheDefaultFormatParser.FormatType.LNMP;
/*     */     }
/*     */ 
/*     */     
/*  57 */     if (type != null) {
/*  58 */       int sum = 0;
/*  59 */       for (int i = 0; i < list.size(); i++) {
/*  60 */         if (mattcherApacheCustomLog(list.get(i))) {
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
/*     */   public static boolean mattcherApacheCustomLog(String line) {
/*  82 */     Matcher m_ip = regexIp.matcher(line);
/*  83 */     if (m_ip.find() && m_ip.groupCount() >= 1 && m_ip.group(1) != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       ipIndex = 0;
/*     */     }
/*     */ 
/*     */     
/*  95 */     Matcher m_datetime = regexDateTime.matcher(line);
/*  96 */     if (m_datetime.find() && m_datetime.groupCount() >= 1 && m_datetime.group(1) != null)
/*     */     {
/*     */ 
/*     */       
/* 100 */       datetimeIndex = 0;
/*     */     }
/*     */ 
/*     */     
/* 104 */     Matcher m_url = regexUrl.matcher(line);
/* 105 */     if (m_url.find() && m_url.groupCount() >= 1)
/*     */     {
/*     */       
/* 108 */       requestUrlIndex = 0;
/*     */     }
/*     */ 
/*     */     
/* 112 */     Matcher m_statusAcontentLength = regexStatusAContentLength.matcher(line);
/* 113 */     if (m_statusAcontentLength.find() && m_statusAcontentLength.groupCount() >= 2 && m_statusAcontentLength.group(1) != null && m_statusAcontentLength.group(2) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       reponseCodeIndex = 0;
/* 119 */       contentLengthIndex = 0;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     Matcher m_referer = regexRefferer.matcher(line);
/* 124 */     if (m_referer.find() && m_referer.groupCount() >= 1 && m_referer.group(1) != null)
/*     */     {
/*     */ 
/*     */       
/* 128 */       refererIndex = 0;
/*     */     }
/*     */ 
/*     */     
/* 132 */     Matcher m_ua = regexUserAgent.matcher(line);
/* 133 */     if (m_ua.find() && m_ua.groupCount() >= 1 && m_ua.group(1) != null)
/*     */     {
/*     */ 
/*     */       
/* 137 */       uaIndex = 0;
/*     */     }
/*     */ 
/*     */     
/* 141 */     if (datetimeIndex > -1 && ipIndex > -1 && requestUrlIndex > -1 && reponseCodeIndex > -1 && contentLengthIndex > -1) {
/* 142 */       return true;
/*     */     }
/*     */     
/* 145 */     return false;
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
/*     */   public static String parseApacheCustomLog(String line, String host) {
/* 159 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*     */     
/* 162 */     Matcher m_ip = regexIp.matcher(line);
/* 163 */     if (m_ip.find() && m_ip.groupCount() >= 1) {
/* 164 */       if (m_ip.groupCount() >= 2 && m_ip.group(2) != null) {
/*     */         
/* 166 */         sb.append(Utils.getRemoteIp(m_ip.group(2)));
/*     */       } else {
/*     */         
/* 169 */         sb.append(Utils.getRemoteIp(m_ip.group(1)));
/*     */       } 
/*     */     }
/*     */     
/* 173 */     sb.append("\t");
/*     */ 
/*     */     
/* 176 */     Matcher m_datetime = regexDateTime.matcher(line);
/* 177 */     if (m_datetime.find() && m_datetime.groupCount() >= 1) {
/* 178 */       String a = m_datetime.group(1);
/* 179 */       if (a.matches(rengxDate)) {
/* 180 */         DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
/* 181 */         sb.append(DateUtil.formatStrDate(a, dateFormatter));
/*     */       } else {
/* 183 */         sb.append(a);
/*     */       } 
/*     */     } 
/*     */ 
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
/*     */     else {
/*     */       
/* 234 */       sb.append("-");
/*     */     } 
/*     */     
/* 237 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 242 */     String line = "2014-10-23 23:58:53 123.125.71.51 200 6903 \"GET /zuowen/chuzhongzuowen/450zi/91175.html HTTP/1.1\" \"-\" \"Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)\" 10.10.10.88";
/*     */     
/* 244 */     String line1 = "2014-10-23 23:58:53 123.125.71.51 200 6903 \"GET /zuowen/chuzhongzuowen/450zi/91175.html HTTP/1.1\" \"-\" \"Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)\"";
/* 245 */     System.out.println(parseApacheCustomLog(line, "www.test.cn"));
/* 246 */     System.out.println(parseApacheCustomLog(line1, "www.test.cn"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\parse\ApacheCustomLogParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */