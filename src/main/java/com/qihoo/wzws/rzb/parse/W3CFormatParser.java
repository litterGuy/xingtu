/*     */ package com.qihoo.wzws.rzb.parse;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.exception.SystemConfigException;
/*     */ import com.qihoo.wzws.rzb.util.ConfigUtil;
/*     */ import com.qihoo.wzws.rzb.util.Utils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CFormatParser
/*     */ {
/*  23 */   private static String W3C_TEMPLATE = "";
/*     */   
/*     */   private static final String date = "date";
/*     */   
/*     */   private static final String time = "time";
/*     */   
/*     */   private static final String cip = "c-ip";
/*     */   
/*     */   private static final String csuristem = "cs-uri-stem";
/*     */   
/*     */   private static final String csuriquery = "cs-uri-query";
/*     */   
/*     */   private static final String scstatus = "sc-status";
/*     */   
/*     */   private static final String scbytes = "sc-bytes";
/*     */   private static final String cshost = "cs-host";
/*     */   private static final String referer = "cs(Referer)";
/*     */   private static final String ua = "cs(User-Agent)";
/*  41 */   private static Map<String, Integer> w3cTemplateMap = new HashMap<String, Integer>();
/*     */   
/*  43 */   private static int dateIndex = -1;
/*  44 */   private static int timeIndex = -1;
/*  45 */   private static int ipIndex = -1;
/*  46 */   private static int reponseCodeIndex = -1;
/*  47 */   private static int hostIndex = -1;
/*  48 */   private static int requestUriIndex = -1;
/*  49 */   private static int requestQueryIndex = -1;
/*  50 */   private static int contentLengthIndex = -1;
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static int refererIndex = -1;
/*  55 */   private static int uaIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initW3CFormat() throws SystemConfigException {
/*  62 */     String formatStr = (String)ConfigUtil.formatConfig.get("wc3_template");
/*     */     
/*  64 */     if (formatStr == null || formatStr.isEmpty()) {
/*  65 */       throw new SystemConfigException(String.format("系统配置config.ini中:参数%s需要设置！", new Object[] { "wc3_template" }));
/*     */     }
/*     */     
/*  68 */     String[] fields = formatStr.trim().split(" ");
/*     */     
/*  70 */     for (int index = 0; index < fields.length - 1; index++) {
/*  71 */       w3cTemplateMap.put(fields[index].trim(), Integer.valueOf(index));
/*     */     }
/*     */ 
/*     */     
/*  75 */     if (w3cTemplateMap.get("date") != null) {
/*  76 */       dateIndex = ((Integer)w3cTemplateMap.get("date")).intValue();
/*     */     }
/*  78 */     if (w3cTemplateMap.get("time") != null) {
/*  79 */       timeIndex = ((Integer)w3cTemplateMap.get("time")).intValue();
/*     */     }
/*  81 */     if (w3cTemplateMap.get("c-ip") != null) {
/*  82 */       ipIndex = ((Integer)w3cTemplateMap.get("c-ip")).intValue();
/*     */     }
/*     */     
/*  85 */     if (w3cTemplateMap.get("cs-host") != null) {
/*  86 */       hostIndex = ((Integer)w3cTemplateMap.get("cs-host")).intValue();
/*     */     }
/*  88 */     if (w3cTemplateMap.get("cs-uri-stem") != null) {
/*  89 */       requestUriIndex = ((Integer)w3cTemplateMap.get("cs-uri-stem")).intValue();
/*     */     }
/*  91 */     if (w3cTemplateMap.get("cs-uri-query") != null) {
/*  92 */       requestQueryIndex = ((Integer)w3cTemplateMap.get("cs-uri-query")).intValue();
/*     */     }
/*  94 */     if (w3cTemplateMap.get("sc-status") != null) {
/*  95 */       reponseCodeIndex = ((Integer)w3cTemplateMap.get("sc-status")).intValue();
/*     */     }
/*  97 */     if (w3cTemplateMap.get("sc-bytes") != null) {
/*  98 */       contentLengthIndex = ((Integer)w3cTemplateMap.get("sc-bytes")).intValue();
/*     */     }
/*     */     
/* 101 */     if (w3cTemplateMap.get("cs(Referer)") != null) {
/* 102 */       refererIndex = ((Integer)w3cTemplateMap.get("cs(Referer)")).intValue();
/*     */     }
/* 104 */     if (w3cTemplateMap.get("cs(User-Agent)") != null) {
/* 105 */       uaIndex = ((Integer)w3cTemplateMap.get("cs(User-Agent)")).intValue();
/*     */     }
/*     */     
/* 108 */     if (dateIndex == -1 || timeIndex == -1 || ipIndex == -1 || requestQueryIndex == -1 || reponseCodeIndex == -1) {
/* 109 */       throw new SystemConfigException(String.format("系统配置config.ini中:参数wc3_template中必须需包含以下字段：date、time、cip、csuristem、csuriquery、scstatus", new Object[0]));
/*     */     }
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
/*     */   public static String parse(String line, String host) {
/* 124 */     StringBuilder sb = new StringBuilder("");
/* 125 */     String[] fields = line.split(" ");
/*     */     
/* 127 */     if (fields.length >= 8) {
/*     */       
/* 129 */       if (ipIndex > -1) {
/* 130 */         sb.append(fields[ipIndex]);
/*     */       }
/* 132 */       sb.append("\t");
/*     */       
/* 134 */       if (dateIndex > -1 && timeIndex > -1) {
/* 135 */         sb.append(fields[dateIndex] + " " + fields[timeIndex]);
/*     */       }
/* 137 */       sb.append("\t");
/*     */       
/* 139 */       if (hostIndex > -1) {
/* 140 */         sb.append(fields[hostIndex]);
/*     */       } else {
/* 142 */         sb.append(host);
/*     */       } 
/* 144 */       sb.append("\t");
/*     */ 
/*     */       
/* 147 */       if (requestUriIndex > -1 && !"-".equals(fields[requestUriIndex])) {
/* 148 */         sb.append(fields[requestUriIndex]);
/* 149 */         if (requestQueryIndex > -1 && !"-".equals(fields[requestQueryIndex])) {
/* 150 */           sb.append("?" + fields[requestQueryIndex]);
/*     */         }
/* 152 */         sb.append("\t");
/*     */       }
/* 154 */       else if (requestQueryIndex > -1 && !"-".equals(fields[requestQueryIndex])) {
/* 155 */         sb.append(fields[requestQueryIndex]);
/*     */         
/* 157 */         sb.append("\t");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 162 */       if (reponseCodeIndex > -1) {
/* 163 */         sb.append(fields[reponseCodeIndex]);
/*     */       }
/* 165 */       sb.append("\t");
/*     */       
/* 167 */       if (contentLengthIndex > -1) {
/* 168 */         sb.append(fields[contentLengthIndex]);
/*     */       } else {
/* 170 */         sb.append("0");
/*     */       } 
/*     */ 
/*     */       
/* 174 */       if (refererIndex > -1) {
/* 175 */         sb.append("\t");
/* 176 */         sb.append(fields[refererIndex]);
/*     */       } else {
/* 178 */         sb.append("\t");
/* 179 */         sb.append("-");
/*     */       } 
/* 181 */       if (uaIndex > -1) {
/* 182 */         sb.append("\t");
/* 183 */         sb.append(fields[uaIndex]);
/*     */       } else {
/* 185 */         sb.append("\t");
/* 186 */         sb.append("-");
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 196 */       System.out.println(String.format("Unable to parse: %s with default regular expression", new Object[] { line }));
/*     */     } 
/*     */     
/* 199 */     return sb.toString();
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
/*     */   public static void matcherLogFormatType(String dir) throws SystemConfigException {
/* 211 */     File file = null;
/* 212 */     File fileDir = new File(dir);
/* 213 */     if (fileDir.isDirectory()) {
/* 214 */       File[] files = fileDir.listFiles();
/* 215 */       if (files.length >= 1) {
/* 216 */         file = Utils.getlastestFileFromDir(files);
/*     */       }
/* 218 */       fileDir = null;
/*     */     } else {
/* 220 */       file = fileDir;
/*     */     } 
/*     */     
/* 223 */     if (file != null) {
/* 224 */       String wc3_template = "";
/* 225 */       BufferedReader reader = null;
/*     */       try {
/* 227 */         reader = new BufferedReader(new FileReader(file));
/* 228 */         String tempString = null;
/* 229 */         while ((tempString = reader.readLine()) != null) {
/*     */           
/* 231 */           if (tempString.startsWith("#Fields:")) {
/* 232 */             wc3_template = tempString.substring("#Fields:".length()).trim();
/*     */             break;
/*     */           } 
/*     */         } 
/* 236 */       } catch (IOException e) {
/* 237 */         e.printStackTrace();
/*     */       } finally {
/* 239 */         if (reader != null) {
/*     */           try {
/* 241 */             reader.close();
/* 242 */           } catch (IOException e1) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 247 */       if (wc3_template.length() > 0) {
/* 248 */         String[] fields = wc3_template.trim().split(" ");
/*     */         
/* 250 */         for (int index = 0; index < fields.length - 1; index++) {
/* 251 */           w3cTemplateMap.put(fields[index].trim(), Integer.valueOf(index));
/*     */         }
/*     */ 
/*     */         
/* 255 */         if (w3cTemplateMap.get("date") != null) {
/* 256 */           dateIndex = ((Integer)w3cTemplateMap.get("date")).intValue();
/*     */         }
/* 258 */         if (w3cTemplateMap.get("time") != null) {
/* 259 */           timeIndex = ((Integer)w3cTemplateMap.get("time")).intValue();
/*     */         }
/* 261 */         if (w3cTemplateMap.get("c-ip") != null) {
/* 262 */           ipIndex = ((Integer)w3cTemplateMap.get("c-ip")).intValue();
/*     */         }
/*     */         
/* 265 */         if (w3cTemplateMap.get("cs-host") != null) {
/* 266 */           hostIndex = ((Integer)w3cTemplateMap.get("cs-host")).intValue();
/*     */         }
/* 268 */         if (w3cTemplateMap.get("cs-uri-stem") != null) {
/* 269 */           requestUriIndex = ((Integer)w3cTemplateMap.get("cs-uri-stem")).intValue();
/*     */         }
/* 271 */         if (w3cTemplateMap.get("cs-uri-query") != null) {
/* 272 */           requestQueryIndex = ((Integer)w3cTemplateMap.get("cs-uri-query")).intValue();
/*     */         }
/* 274 */         if (w3cTemplateMap.get("sc-status") != null) {
/* 275 */           reponseCodeIndex = ((Integer)w3cTemplateMap.get("sc-status")).intValue();
/*     */         }
/* 277 */         if (w3cTemplateMap.get("sc-bytes") != null) {
/* 278 */           contentLengthIndex = ((Integer)w3cTemplateMap.get("sc-bytes")).intValue();
/*     */         }
/*     */ 
/*     */         
/* 282 */         if (w3cTemplateMap.get("cs(Referer)") != null) {
/* 283 */           refererIndex = ((Integer)w3cTemplateMap.get("cs(Referer)")).intValue();
/*     */         }
/* 285 */         if (w3cTemplateMap.get("cs(User-Agent)") != null) {
/* 286 */           uaIndex = ((Integer)w3cTemplateMap.get("cs(User-Agent)")).intValue();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 291 */         if (dateIndex == -1 || timeIndex == -1 || ipIndex == -1 || requestQueryIndex == -1 || reponseCodeIndex == -1)
/*     */         {
/* 293 */           throw new SystemConfigException(String.format("未能识别格式%s，请反馈到星图官方群。", new Object[] { wc3_template }));
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 298 */         throw new SystemConfigException(String.format("请确认一下config.ini中日志文件类型【xingtu_logtype参数项】设置是否正确，如仍有问题，请反馈到星图官方群。谢谢！[%s]", new Object[] { wc3_template }));
/*     */       } 
/*     */     } else {
/* 301 */       throw new SystemConfigException("请确保设置的日志存放路径[log_file配置项]是有效的文件或者文件夹");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean mattcherW3CLogFormatTypeByTemplate(List<String> list) throws SystemConfigException {
/* 313 */     if (list == null || list.size() == 0) {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     String wc3_template = "";
/* 318 */     for (String tempString : list) {
/*     */       
/* 320 */       if (tempString.startsWith("#Fields:")) {
/* 321 */         wc3_template = tempString.substring("#Fields:".length()).trim();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 326 */     if (wc3_template.length() > 0) {
/* 327 */       W3C_TEMPLATE = wc3_template;
/* 328 */       return true;
/*     */     } 
/*     */     
/* 331 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initW3CLogFormatTypeByTemplate() throws SystemConfigException {
/* 342 */     String wc3_template = W3C_TEMPLATE;
/* 343 */     if (wc3_template.length() > 0) {
/* 344 */       String[] fields = wc3_template.trim().split(" ");
/*     */       
/* 346 */       for (int index = 0; index < fields.length - 1; index++) {
/* 347 */         w3cTemplateMap.put(fields[index].trim(), Integer.valueOf(index));
/*     */       }
/*     */ 
/*     */       
/* 351 */       if (w3cTemplateMap.get("date") != null) {
/* 352 */         dateIndex = ((Integer)w3cTemplateMap.get("date")).intValue();
/*     */       }
/* 354 */       if (w3cTemplateMap.get("time") != null) {
/* 355 */         timeIndex = ((Integer)w3cTemplateMap.get("time")).intValue();
/*     */       }
/* 357 */       if (w3cTemplateMap.get("c-ip") != null) {
/* 358 */         ipIndex = ((Integer)w3cTemplateMap.get("c-ip")).intValue();
/*     */       }
/*     */       
/* 361 */       if (w3cTemplateMap.get("cs-host") != null) {
/* 362 */         hostIndex = ((Integer)w3cTemplateMap.get("cs-host")).intValue();
/*     */       }
/* 364 */       if (w3cTemplateMap.get("cs-uri-stem") != null) {
/* 365 */         requestUriIndex = ((Integer)w3cTemplateMap.get("cs-uri-stem")).intValue();
/*     */       }
/* 367 */       if (w3cTemplateMap.get("cs-uri-query") != null) {
/* 368 */         requestQueryIndex = ((Integer)w3cTemplateMap.get("cs-uri-query")).intValue();
/*     */       }
/* 370 */       if (w3cTemplateMap.get("sc-status") != null) {
/* 371 */         reponseCodeIndex = ((Integer)w3cTemplateMap.get("sc-status")).intValue();
/*     */       }
/* 373 */       if (w3cTemplateMap.get("sc-bytes") != null) {
/* 374 */         contentLengthIndex = ((Integer)w3cTemplateMap.get("sc-bytes")).intValue();
/*     */       }
/*     */ 
/*     */       
/* 378 */       if (w3cTemplateMap.get("cs(Referer)") != null) {
/* 379 */         refererIndex = ((Integer)w3cTemplateMap.get("cs(Referer)")).intValue();
/*     */       }
/* 381 */       if (w3cTemplateMap.get("cs(User-Agent)") != null) {
/* 382 */         uaIndex = ((Integer)w3cTemplateMap.get("cs(User-Agent)")).intValue();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 387 */       if (dateIndex == -1 || timeIndex == -1 || ipIndex == -1 || requestQueryIndex == -1 || reponseCodeIndex == -1)
/*     */       {
/* 389 */         throw new SystemConfigException(String.format("未能识别格式%s，请反馈到星图官方群。", new Object[] { wc3_template }));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 394 */       throw new SystemConfigException(String.format("请确认一下config.ini中日志文件类型【xingtu_logtype参数项】设置是否正确，如仍有问题，请反馈到星图官方群。谢谢！[%s]", new Object[] { wc3_template }));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/* 400 */       matcherLogFormatType("C:\\Users\\wangpeng3-s\\Desktop\\iis_access.log");
/* 401 */     } catch (SystemConfigException e) {
/* 402 */       System.out.println(e.getMessage());
/* 403 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\parse\W3CFormatParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */