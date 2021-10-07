/*     */ package com.qihoo.wzws.rzb.parse;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.exception.SystemConfigException;
/*     */ import com.qihoo.wzws.rzb.util.DateUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ public class IISFormatParser
/*     */ {
/*  24 */   private static Pattern regexIp = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
/*     */   
/*  26 */   private static Pattern regexDate = Pattern.compile("\\d{1,2}.\\d{1,2}.\\d{4}");
/*     */   
/*  28 */   private static Pattern regexTime = Pattern.compile("\\d{1,2}\\:\\d{2}\\:\\d{2}");
/*  29 */   private static Pattern regexStatus = Pattern.compile("\\d{3}");
/*  30 */   private static Pattern regexContentLength = Pattern.compile("\\d{1,}+");
/*  31 */   private static Pattern regexRequestUri = Pattern.compile("/\\w*");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int clientIPIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int dateIndex = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int timeIndex = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int serverIPIndex = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int contentLengthIndex = 9;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int reponseCodeIndex = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int requestUriIndex = 13;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int requestParasIndex = 14;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String parse(String line, String host) {
/*  85 */     StringBuilder sb = new StringBuilder("");
/*  86 */     String[] fields = line.split(",\\s");
/*     */     
/*  88 */     if (fields.length == 15) {
/*     */       
/*  90 */       String clientIP = fields[0];
/*  91 */       String date = fields[2];
/*  92 */       String time = fields[3];
/*  93 */       String requestUri = fields[13];
/*  94 */       String requestParas = fields[14];
/*  95 */       String status = fields[10];
/*  96 */       String contentLength = fields[9];
/*     */ 
/*     */       
/*  99 */       if (clientIP != null) {
/* 100 */         sb.append(clientIP.trim());
/*     */       }
/* 102 */       sb.append("\t");
/*     */ 
/*     */       
/* 105 */       if (date != null && time != null) {
/* 106 */         String datetime = date.trim() + " " + time.trim();
/*     */         
/*     */         try {
/* 109 */           DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
/* 110 */           String strDate = DateUtil.formatStrDate(datetime, dateFormatter);
/* 111 */           if (strDate != null) {
/* 112 */             sb.append(strDate);
/*     */           }
/* 114 */         } catch (Exception ex) {
/* 115 */           return null;
/*     */         } 
/*     */       } else {
/* 118 */         return null;
/*     */       } 
/* 120 */       sb.append("\t");
/*     */ 
/*     */       
/* 123 */       sb.append(host);
/* 124 */       sb.append("\t");
/*     */ 
/*     */       
/* 127 */       if (requestUri != null) {
/* 128 */         sb.append(requestUri.trim());
/* 129 */         if (requestParas.endsWith(",")) {
/* 130 */           requestParas = requestParas.substring(0, requestParas.length() - 1);
/* 131 */           if (!"-".equals(requestParas)) {
/* 132 */             if (requestParas.indexOf("?") == -1) {
/* 133 */               sb.append("?" + requestParas.trim());
/*     */             } else {
/* 135 */               sb.append(requestParas.trim());
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } else {
/* 140 */         return null;
/*     */       } 
/* 142 */       sb.append("\t");
/*     */ 
/*     */       
/* 145 */       if (status != null) {
/* 146 */         sb.append(status.trim());
/*     */       }
/* 148 */       sb.append("\t");
/*     */ 
/*     */       
/* 151 */       if (contentLength != null) {
/* 152 */         sb.append(contentLength.trim());
/*     */       } else {
/* 154 */         sb.append("0");
/*     */       } 
/*     */     } else {
/*     */       
/* 158 */       System.out.println(String.format("Unable to parse: %s with default regular expression", new Object[] { line }));
/*     */     } 
/*     */     
/* 161 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean mattcherIISLogFormatType(List<String> list) throws SystemConfigException {
/* 171 */     if (list == null || list.size() == 0) {
/* 172 */       return false;
/*     */     }
/*     */     
/* 175 */     String first = "";
/* 176 */     for (String tempString : list) {
/*     */       
/* 178 */       if (!tempString.startsWith("#") && tempString.indexOf(",") > 0) {
/* 179 */         first = tempString;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 184 */     ApacheDefaultFormatParser.FormatType type = null;
/*     */     
/* 186 */     if (mattcherIISLog(first)) {
/* 187 */       type = ApacheDefaultFormatParser.FormatType.IIS;
/*     */     }
/*     */ 
/*     */     
/* 191 */     if (type != null) {
/* 192 */       int sum = 0;
/* 193 */       for (int i = 0; i < list.size(); i++) {
/* 194 */         String tempString = list.get(i);
/* 195 */         if (!tempString.startsWith("#") && tempString.indexOf(",") > 0 && 
/* 196 */           mattcherIISLog(list.get(i))) {
/* 197 */           sum++;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 203 */       if (sum > list.size() / 2) {
/* 204 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 208 */     return false;
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
/*     */   public static boolean mattcherIISLog(String line) {
/* 220 */     String[] fields = line.split(",\\s");
/* 221 */     if (fields.length == 15) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       Matcher m_cip = regexIp.matcher(fields[0].trim());
/* 232 */       Matcher m_date = regexDate.matcher(fields[2].trim());
/* 233 */       Matcher m_time = regexTime.matcher(fields[3].trim());
/* 234 */       Matcher m_sip = regexIp.matcher(fields[6].trim());
/* 235 */       Matcher m_status = regexStatus.matcher(fields[10]);
/* 236 */       Matcher m_contentLength = regexContentLength.matcher(fields[9].trim());
/* 237 */       Matcher m_requestUri = regexRequestUri.matcher(fields[13].trim());
/*     */ 
/*     */       
/* 240 */       if (m_cip.find() && m_date.find() && m_time.find() && m_sip.find() && m_status.find() && m_contentLength.find() && m_requestUri.find())
/*     */       {
/* 242 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 246 */     return false;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 250 */     String line = "222.173.30.138, -, 11/2/2014, 0:00:53, W3SVC1, WIN-XC7TLE0W5RF, 192.168.240.103, 46, 321, 7699, 200, 0, GET, /show.aspx, lmid=1947&xxid=48081&pgid=395,";
/* 251 */     String line1 = "68.180.228.169, -, 11/3/2014, 0:00:01, W3SVC3, WIN-GJ4VMT9P551, 213.154.30.11, 1203, 211, 5079, 200, 0, GET, /, q-634.html?list=/1480.html,";
/*     */     
/* 253 */     List<String> list = new ArrayList<String>();
/* 254 */     list.add(line);
/* 255 */     list.add(line1);
/*     */     
/*     */     try {
/* 258 */       System.out.println(mattcherIISLogFormatType(list));
/* 259 */     } catch (SystemConfigException e) {
/*     */       
/* 261 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 264 */     System.out.println(parse(line, "default"));
/* 265 */     System.out.println(parse(line1, "default"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\parse\IISFormatParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */