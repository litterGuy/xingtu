/*     */ package com.qihoo.wzws.rzb.parse;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.exception.SystemConfigException;
/*     */ import com.qihoo.wzws.rzb.util.ConfigUtil;
/*     */ import com.qihoo.wzws.rzb.util.DateUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomLogFormatParser
/*     */ {
/*     */   private static final String KEY_logtempletName = "logformat_use";
/*     */   private static final String KEY_suffix_delimited = "_delimited";
/*     */   private static final String KEY_suffix_dateformat = "_dateformat";
/*     */   private static final String KEY_suffix_logtemplate = "_logtemplate";
/*     */   private static final String KEY_suffix_fieldssize = "_fieldssize";
/*  23 */   private static Map<String, Integer> logTemplateMap = new HashMap<String, Integer>();
/*     */   
/*  25 */   private static int timeIndex = -1;
/*  26 */   private static int ipIndex = -1;
/*  27 */   private static int reponseCodeIndex = -1;
/*  28 */   private static int hostIndex = -1;
/*  29 */   private static int requestUrlIndex = -1;
/*  30 */   private static int contentLengthIndex = -1;
/*     */ 
/*     */   
/*  33 */   private static int refererIndex = -1;
/*  34 */   private static int uaIndex = -1;
/*     */ 
/*     */   
/*  37 */   private static String global_delimited = "";
/*  38 */   private static int global_fieldssize = 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected enum CustomFormatFields
/*     */   {
/*  58 */     time_local,
/*  59 */     remote_addr,
/*  60 */     reponse_code,
/*  61 */     host,
/*  62 */     request_url,
/*  63 */     content_length,
/*  64 */     http_referer,
/*  65 */     http_user_agent;
/*     */   }
/*     */ 
/*     */   
/*  69 */   private static final Pattern timeZonePattern = Pattern.compile("^(0[1-9]|1[0-9]|2[0-9]|3[01])[///](Jun|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[///][0-9]{4}[:](0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2} ([- /+|-]\\d{4})$");
/*     */   
/*  71 */   private static final Pattern datetimePattern = Pattern.compile("^[0-9]{4}[-](0[1-9]|1[0-2])[-](0[1-9]|1[0-9]|2[0-9]|3[01])[ ](0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2}$");
/*     */   
/*     */   public static boolean matches(Pattern pattern, String str) {
/*  74 */     return pattern.matcher(str).find();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadCustomLogFormatConfig() throws SystemConfigException {
/*  82 */     String logtempletName = (String)ConfigUtil.formatConfig.get("logformat_use");
/*  83 */     if (logtempletName == null || logtempletName.trim().isEmpty())
/*     */     {
/*  85 */       throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[] { "logformat_use" }));
/*     */     }
/*     */     
/*  88 */     String delimited = (String)ConfigUtil.formatConfig.get(logtempletName + "_delimited");
/*     */     
/*  90 */     String logtemplate = (String)ConfigUtil.formatConfig.get(logtempletName + "_logtemplate");
/*  91 */     String fieldssizeStr = (String)ConfigUtil.formatConfig.get(logtempletName + "_fieldssize");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (logtemplate == null || logtemplate.trim().isEmpty())
/*     */     {
/* 100 */       throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[] { logtempletName + "_logtemplate" }));
/*     */     }
/* 102 */     if (fieldssizeStr == null || fieldssizeStr.trim().isEmpty())
/*     */     {
/* 104 */       throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[] { logtempletName + "_fieldssize" }));
/*     */     }
/*     */     
/* 107 */     int fieldssize = 0;
/*     */     try {
/* 109 */       fieldssize = Integer.valueOf(fieldssizeStr.trim()).intValue();
/* 110 */     } catch (NumberFormatException ex) {
/*     */       
/* 112 */       throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[] { logtempletName + "_fieldssize" }));
/*     */     } 
/*     */     
/* 115 */     String[] fields = logtemplate.trim().split(delimited.trim());
/* 116 */     if (fields.length != fieldssize) {
/* 117 */       throw new SystemConfigException(String.format("系统配置config.ini中中:参数%s配置的日志字段个数与%s中不一致！", new Object[] { logtempletName + "_fieldssize", logtempletName + "_logtemplate" }));
/*     */     }
/*     */     
/* 120 */     for (int index = 0; index < fields.length; index++)
/*     */     {
/* 122 */       logTemplateMap.put(fields[index].trim(), Integer.valueOf(index));
/*     */     }
/*     */ 
/*     */     
/* 126 */     if (logTemplateMap.get(CustomFormatFields.time_local.toString()) != null) {
/* 127 */       timeIndex = ((Integer)logTemplateMap.get(CustomFormatFields.time_local.toString())).intValue();
/*     */     }
/* 129 */     if (logTemplateMap.get(CustomFormatFields.remote_addr.toString()) != null) {
/* 130 */       ipIndex = ((Integer)logTemplateMap.get(CustomFormatFields.remote_addr.toString())).intValue();
/*     */     }
/*     */     
/* 133 */     if (logTemplateMap.get(CustomFormatFields.host.toString()) != null) {
/* 134 */       hostIndex = ((Integer)logTemplateMap.get(CustomFormatFields.host.toString())).intValue();
/*     */     }
/* 136 */     if (logTemplateMap.get(CustomFormatFields.request_url.toString()) != null) {
/* 137 */       requestUrlIndex = ((Integer)logTemplateMap.get(CustomFormatFields.request_url.toString())).intValue();
/*     */     }
/* 139 */     if (logTemplateMap.get(CustomFormatFields.reponse_code.toString()) != null) {
/* 140 */       reponseCodeIndex = ((Integer)logTemplateMap.get(CustomFormatFields.reponse_code.toString())).intValue();
/*     */     }
/*     */     
/* 143 */     if (logTemplateMap.get(CustomFormatFields.content_length.toString()) != null) {
/* 144 */       contentLengthIndex = ((Integer)logTemplateMap.get(CustomFormatFields.content_length.toString())).intValue();
/*     */     }
/*     */     
/* 147 */     if (logTemplateMap.get(CustomFormatFields.http_referer.toString()) != null) {
/* 148 */       refererIndex = ((Integer)logTemplateMap.get(CustomFormatFields.http_referer.toString())).intValue();
/*     */     }
/* 150 */     if (logTemplateMap.get(CustomFormatFields.http_user_agent.toString()) != null) {
/* 151 */       uaIndex = ((Integer)logTemplateMap.get(CustomFormatFields.http_user_agent.toString())).intValue();
/*     */     }
/* 153 */     if (timeIndex == -1 || ipIndex == -1 || requestUrlIndex == -1 || reponseCodeIndex == -1) {
/* 154 */       throw new SystemConfigException(String.format("系统配置config.ini中:参数logtemplate中必须需包含以下字段：remote_addr、time_local、request_url、reponse_code", new Object[0]));
/*     */     }
/*     */ 
/*     */     
/* 158 */     global_delimited = delimited;
/* 159 */     global_fieldssize = fieldssize;
/*     */     
/* 161 */     AutomaticLogFormatParser.XINGTU_LOGTYPE_INT = 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void loadCustomLogFormatConfigFromProperties() throws SystemConfigException {
/* 171 */     String logtempletName = ConfigUtil.getConfig("logformat_use");
/* 172 */     if (logtempletName.trim().isEmpty())
/*     */     {
/* 174 */       throw new SystemConfigException(String.format("系统配置中: %s参数需要配置！", new Object[] { "logformat_use" }));
/*     */     }
/*     */     
/* 177 */     String delimited = ConfigUtil.getConfig(logtempletName + "_delimited").trim();
/* 178 */     String dateformat = ConfigUtil.getConfig(logtempletName + "_dateformat").trim();
/* 179 */     String logtemplate = ConfigUtil.getConfig(logtempletName + "_logtemplate").trim();
/* 180 */     String fieldssizeStr = ConfigUtil.getConfig(logtempletName + "_fieldssize").trim();
/*     */ 
/*     */     
/* 183 */     if (dateformat.trim().isEmpty())
/*     */     {
/* 185 */       throw new SystemConfigException(String.format("系统配置中: %s参数需要配置！", new Object[] { logtempletName + "_dateformat" }));
/*     */     }
/* 187 */     if (logtemplate.trim().isEmpty())
/*     */     {
/* 189 */       throw new SystemConfigException(String.format("系统配置中: %s参数需要配置！", new Object[] { logtempletName + "_logtemplate" }));
/*     */     }
/* 191 */     if (fieldssizeStr.trim().isEmpty())
/*     */     {
/* 193 */       throw new SystemConfigException(String.format("系统配置中: %s参数需要配置！", new Object[] { logtempletName + "_fieldssize" }));
/*     */     }
/*     */     
/* 196 */     int fieldssize = 0;
/*     */     try {
/* 198 */       fieldssize = Integer.valueOf(fieldssizeStr).intValue();
/* 199 */     } catch (NumberFormatException ex) {
/*     */       
/* 201 */       throw new SystemConfigException(String.format("系统配置中: %s参数配置有无，请填写日志字段个数！", new Object[] { logtempletName + "_fieldssize" }));
/*     */     } 
/*     */     
/* 204 */     String[] fields = logtemplate.split(delimited);
/* 205 */     if (fields.length != fieldssize) {
/* 206 */       throw new SystemConfigException(String.format("系统配置中: %s参数配置的日志字段个数与%s中不一致！", new Object[] { logtempletName + "_fieldssize", logtempletName + "_logtemplate" }));
/*     */     }
/*     */     
/* 209 */     for (int index = 0; index < fields.length - 1; index++) {
/* 210 */       logTemplateMap.put(fields[index].trim(), Integer.valueOf(index));
/*     */     }
/*     */ 
/*     */     
/* 214 */     timeIndex = ((Integer)logTemplateMap.get(CustomFormatFields.time_local.toString())).intValue();
/* 215 */     ipIndex = ((Integer)logTemplateMap.get(CustomFormatFields.remote_addr.toString())).intValue();
/* 216 */     hostIndex = ((Integer)logTemplateMap.get(CustomFormatFields.host.toString())).intValue();
/* 217 */     requestUrlIndex = ((Integer)logTemplateMap.get(CustomFormatFields.request_url.toString())).intValue();
/* 218 */     reponseCodeIndex = ((Integer)logTemplateMap.get(CustomFormatFields.reponse_code.toString())).intValue();
/* 219 */     contentLengthIndex = ((Integer)logTemplateMap.get(CustomFormatFields.content_length.toString())).intValue();
/*     */ 
/*     */     
/* 222 */     global_delimited = delimited;
/* 223 */     global_fieldssize = fieldssize;
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
/*     */   public static String parse(String line, String host) {
/* 235 */     if (line.startsWith("#")) {
/* 236 */       return null;
/*     */     }
/*     */     
/* 239 */     String[] fields = line.split(global_delimited);
/* 240 */     if (fields.length >= global_fieldssize) {
/* 241 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 243 */       sb.append(fields[ipIndex]);
/* 244 */       sb.append("\t");
/*     */       
/* 246 */       String time = fields[timeIndex];
/* 247 */       if (time != null && !time.isEmpty()) {
/* 248 */         if (matches(timeZonePattern, time)) {
/* 249 */           DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
/* 250 */           sb.append(DateUtil.formatStrDate(time, dateFormatter));
/* 251 */         } else if (matches(datetimePattern, time)) {
/* 252 */           sb.append(time);
/*     */         } 
/*     */       }
/* 255 */       sb.append("\t");
/*     */ 
/*     */       
/* 258 */       if (hostIndex > -1 && !fields[hostIndex].isEmpty()) {
/* 259 */         sb.append(fields[hostIndex]);
/*     */       } else {
/* 261 */         sb.append(host);
/*     */       } 
/* 263 */       sb.append("\t");
/*     */       
/* 265 */       sb.append(fields[requestUrlIndex]);
/* 266 */       sb.append("\t");
/*     */       
/* 268 */       sb.append(fields[reponseCodeIndex]);
/* 269 */       sb.append("\t");
/*     */       
/* 271 */       if (contentLengthIndex > -1) {
/* 272 */         sb.append(fields[contentLengthIndex]);
/*     */       } else {
/* 274 */         sb.append("0");
/*     */       } 
/*     */ 
/*     */       
/* 278 */       if (refererIndex > -1 && uaIndex > -1) {
/* 279 */         sb.append("\t");
/* 280 */         sb.append(fields[refererIndex]);
/* 281 */         sb.append("\t");
/* 282 */         sb.append(fields[uaIndex]);
/*     */       } 
/*     */       
/* 285 */       return sb.toString();
/*     */     } 
/*     */ 
/*     */     
/* 289 */     System.out.println(String.format("Unable to parse: %s with default regular expression", new Object[] { line }));
/*     */     
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws SystemConfigException {
/* 302 */     String timezone = "10/Oct/2000:13:55:36 -0700";
/* 303 */     String datetime = "2014-07-30 00:02:20";
/*     */     
/* 305 */     System.out.println(matches(timeZonePattern, timezone));
/* 306 */     System.out.println(matches(datetimePattern, datetime));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\parse\CustomLogFormatParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */