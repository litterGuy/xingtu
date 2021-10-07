/*     */ package com.qihoo.wzws.rzb.parse;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.exception.SystemConfigException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class AgentLogFormatParser
/*     */ {
/*  11 */   private static String AGENT_TEMPLATE = "";
/*     */   
/*     */   private static final String date = "Date";
/*     */   
/*     */   private static final String time = "Time";
/*     */   
/*     */   private static final String cip = "ClientIpAddress";
/*     */   
/*     */   private static final String csuristem = "UriStem";
/*     */   
/*     */   private static final String csuriquery = "UriQuery";
/*     */   
/*     */   private static final String scstatus = "SubStatus";
/*     */   
/*     */   private static final String scbytes = "BytesSent";
/*     */   
/*     */   private static final String cshost = "ServerIpAddress";
/*     */   
/*  29 */   private static Map<String, Integer> agentTemplateMap = new HashMap<String, Integer>();
/*     */   
/*  31 */   private static int dateIndex = -1;
/*  32 */   private static int timeIndex = -1;
/*  33 */   private static int ipIndex = -1;
/*  34 */   private static int reponseCodeIndex = -1;
/*  35 */   private static int hostIndex = -1;
/*  36 */   private static int requestUriIndex = -1;
/*  37 */   private static int requestQueryIndex = -1;
/*  38 */   private static int contentLengthIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initAgentLogFormatTypeByTemplate() throws SystemConfigException {
/*  47 */     String agent_template = AGENT_TEMPLATE;
/*  48 */     if (agent_template.length() > 0) {
/*  49 */       String[] fields = agent_template.trim().split(" ");
/*     */       
/*  51 */       for (int index = 0; index < fields.length - 1; index++) {
/*  52 */         agentTemplateMap.put(fields[index].trim(), Integer.valueOf(index));
/*     */       }
/*     */ 
/*     */       
/*  56 */       if (agentTemplateMap.get("Date") != null) {
/*  57 */         dateIndex = ((Integer)agentTemplateMap.get("Date")).intValue();
/*     */       }
/*  59 */       if (agentTemplateMap.get("Time") != null) {
/*  60 */         timeIndex = ((Integer)agentTemplateMap.get("Time")).intValue();
/*     */       }
/*  62 */       if (agentTemplateMap.get("ClientIpAddress") != null) {
/*  63 */         ipIndex = ((Integer)agentTemplateMap.get("ClientIpAddress")).intValue();
/*     */       }
/*     */       
/*  66 */       if (agentTemplateMap.get("ServerIpAddress") != null) {
/*  67 */         hostIndex = ((Integer)agentTemplateMap.get("ServerIpAddress")).intValue();
/*     */       }
/*  69 */       if (agentTemplateMap.get("UriStem") != null) {
/*  70 */         requestUriIndex = ((Integer)agentTemplateMap.get("UriStem")).intValue();
/*     */       }
/*  72 */       if (agentTemplateMap.get("UriQuery") != null) {
/*  73 */         requestQueryIndex = ((Integer)agentTemplateMap.get("UriQuery")).intValue();
/*     */       }
/*  75 */       if (agentTemplateMap.get("SubStatus") != null) {
/*  76 */         reponseCodeIndex = ((Integer)agentTemplateMap.get("SubStatus")).intValue();
/*     */       }
/*  78 */       if (agentTemplateMap.get("BytesSent") != null) {
/*  79 */         contentLengthIndex = ((Integer)agentTemplateMap.get("BytesSent")).intValue();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  86 */       if (dateIndex == -1 || timeIndex == -1 || ipIndex == -1 || requestQueryIndex == -1 || reponseCodeIndex == -1)
/*     */       {
/*  88 */         throw new SystemConfigException(String.format("未能识别格式%s，请反馈到星图官方群。", new Object[] { agent_template }));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  93 */       throw new SystemConfigException(String.format("请确认一下config.ini中日志文件类型【xingtu_logtype参数项】设置是否正确，如仍有问题，请反馈到星图官方群。谢谢！[%s]", new Object[] { agent_template }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String parse(String line, String host) {
/*  99 */     StringBuilder sb = new StringBuilder("");
/* 100 */     String[] fields = line.split(" ");
/*     */     
/* 102 */     if (fields.length >= 8) {
/*     */       
/* 104 */       if (ipIndex > -1) {
/* 105 */         sb.append(fields[ipIndex]);
/*     */       }
/* 107 */       sb.append("\t");
/*     */       
/* 109 */       if (dateIndex > -1 && timeIndex > -1) {
/* 110 */         sb.append(fields[dateIndex] + " " + fields[timeIndex]);
/*     */       }
/* 112 */       sb.append("\t");
/*     */       
/* 114 */       if (hostIndex > -1) {
/* 115 */         sb.append(fields[hostIndex]);
/*     */       } else {
/* 117 */         sb.append(host);
/*     */       } 
/* 119 */       sb.append("\t");
/*     */ 
/*     */       
/* 122 */       if (requestUriIndex > -1 && !"-".equals(fields[requestUriIndex])) {
/* 123 */         sb.append(fields[requestUriIndex]);
/* 124 */         if (requestQueryIndex > -1 && !"-".equals(fields[requestQueryIndex])) {
/* 125 */           sb.append("?" + fields[requestQueryIndex]);
/*     */         }
/* 127 */         sb.append("\t");
/*     */       }
/* 129 */       else if (requestQueryIndex > -1 && !"-".equals(fields[requestQueryIndex])) {
/* 130 */         sb.append(fields[requestQueryIndex]);
/*     */         
/* 132 */         sb.append("\t");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 137 */       if (reponseCodeIndex > -1) {
/* 138 */         sb.append(fields[reponseCodeIndex]);
/*     */       }
/* 140 */       sb.append("\t");
/*     */       
/* 142 */       if (contentLengthIndex > -1) {
/* 143 */         sb.append(fields[contentLengthIndex]);
/*     */       } else {
/* 145 */         sb.append("0");
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 150 */       System.out.println(String.format("Unable to parse: %s with default regular expression", new Object[] { line }));
/*     */     } 
/*     */     
/* 153 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static boolean mattcherAgentLogFormatTypeByTemplate(List<String> list) throws SystemConfigException {
/* 157 */     if (list == null || list.size() == 0) {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     String agent_template = "";
/* 162 */     for (String tempString : list) {
/*     */       
/* 164 */       if (tempString.startsWith("#Fields:")) {
/* 165 */         agent_template = tempString.substring("#Fields:".length()).trim();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 170 */     if (agent_template.length() > 0) {
/* 171 */       agent_template = agent_template.replace("DateTime", "Date Time");
/* 172 */       String[] agentArray = agent_template.split(" ");
/* 173 */       for (String agent : agentArray) {
/*     */         
/* 175 */         if (agent.equals("LogFilename")) {
/* 176 */           AGENT_TEMPLATE = agent_template;
/* 177 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\parse\AgentLogFormatParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */