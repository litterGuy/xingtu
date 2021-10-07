/*     */ package com.qihoo.wzws.rzb.secure;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.secure.po.AttackEntity;
/*     */ import com.qihoo.wzws.rzb.secure.po.LogEntity;
/*     */ import com.qihoo.wzws.rzb.util.ConfigUtil;
/*     */ import com.qihoo.wzws.rzb.util.MD5Builder;
/*     */ import com.qihoo.wzws.rzb.util.UAUtils;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ public class AttackAnalyzeSingle
/*     */ {
/*     */   public void execute(LogEntity log) {
/*  16 */     long start = System.currentTimeMillis();
/*     */     
/*  18 */     if (log == null) {
/*     */       return;
/*     */     }
/*     */     
/*  22 */     AttackReportCache.records.incrementAndGet();
/*     */ 
/*     */     
/*  25 */     String remote_addr = log.getIp();
/*  26 */     String time_local = log.getTime();
/*  27 */     String host = log.getHost();
/*  28 */     String request_uri = log.getRequestUrl();
/*  29 */     String status = log.getResponseCode();
/*     */ 
/*     */     
/*     */     try {
/*     */       String xingtu_pagetype_particular, xingtu_httpcode_particular;
/*     */       
/*  35 */       switch (((Integer)ConfigUtil.m_config.get("xingtu_pagetype")).intValue()) {
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/*  40 */           if (request_uri.contains(".html") || request_uri.contains(".js") || request_uri.contains(".css") || request_uri.contains(".jpg") || request_uri.contains(".png") || request_uri.contains(".gif")) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/*  47 */           xingtu_pagetype_particular = (String)ConfigUtil.formatConfig.get("xingtu_pagetype_particular");
/*  48 */           if (xingtu_pagetype_particular != null && xingtu_pagetype_particular.length() >= 3) {
/*  49 */             String[] xingtu_pagetype_particulars = xingtu_pagetype_particular.split(",");
/*  50 */             boolean isContains = false;
/*  51 */             for (String s : xingtu_pagetype_particulars) {
/*  52 */               if (request_uri.contains(s)) {
/*  53 */                 isContains = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/*  57 */             if (!isContains) {
/*     */               return;
/*     */             }
/*     */             break;
/*     */           } 
/*  62 */           if (request_uri.contains(".php") || request_uri.contains(".aspx")) {
/*     */             break;
/*     */           }
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       switch (((Integer)ConfigUtil.m_config.get("xingtu_httpcode")).intValue()) {
/*     */ 
/*     */         
/*     */         case 2:
/*  78 */           if (status.equals("200")) {
/*     */             break;
/*     */           }
/*     */           return;
/*     */ 
/*     */         
/*     */         case 3:
/*  85 */           xingtu_httpcode_particular = (String)ConfigUtil.formatConfig.get("xingtu_httpcode_particular");
/*  86 */           if (xingtu_httpcode_particular != null && xingtu_httpcode_particular.length() >= 3) {
/*  87 */             String[] xingtu_httpcode_particulars = xingtu_httpcode_particular.split(",");
/*  88 */             boolean isContains = false;
/*  89 */             for (String s : xingtu_httpcode_particulars) {
/*  90 */               if (status.contains(s)) {
/*  91 */                 isContains = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/*  95 */             if (!isContains) {
/*     */               return;
/*     */             }
/*     */             break;
/*     */           } 
/* 100 */           if (status.equals("302") || status.equals("502")) {
/*     */             break;
/*     */           }
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       switch (((Integer)ConfigUtil.m_config.get("xingtu_urltype")).intValue()) {
/*     */ 
/*     */         
/*     */         case 2:
/* 116 */           if (request_uri.contains("?")) {
/*     */             break;
/*     */           }
/*     */           return;
/*     */ 
/*     */         
/*     */         case 3:
/* 123 */           if (!request_uri.contains("?")) {
/*     */             break;
/*     */           }
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       if (request_uri.length() >= 512) {
/*     */         
/* 136 */         ReportOutput.writeFile(new String(host + "\t" + "超长URL" + "\t" + remote_addr + "\t" + request_uri + "\t" + time_local + "\t" + status + "\t" + '\001'));
/* 137 */         AttackReportCache.longUrlCount.incrementAndGet();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 142 */       if (AnalyzeSingle.useCC && AttackReportCache.records.get() > 0L && AttackReportCache.records.get() % 500000L == 0L)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 147 */         System.gc();
/*     */       }
/*     */ 
/*     */       
/* 151 */       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */       
/* 153 */       for (String rule : ConfigUtil.rules) {
/* 154 */         String[] ritems = rule.split(":", 2);
/* 155 */         if (ritems.length == 2) {
/* 156 */           String rname = ritems[0];
/* 157 */           String rfeatures = ritems[1];
/* 158 */           String[] features = rfeatures.split("\\|");
/* 159 */           for (String feature : features) {
/* 160 */             if (request_uri.contains(feature)) {
/*     */               
/* 162 */               AttackEntity entity = new AttackEntity(host, rname, remote_addr, request_uri.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;"), time_local, status, 1);
/* 163 */               AttackReportCache.put2AttMap(MD5Builder.getMD5String(new String(host + remote_addr + request_uri.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;") + time_local + status)), entity);
/*     */               
/* 165 */               if (AnalyzeSingle.useRoutine) {
/* 166 */                 AttackReportCache.attackCount.incrementAndGet();
/* 167 */                 AttackReportCache.put2OverViewMap(rname);
/* 168 */                 String[] day = time_local.split(" ");
/* 169 */                 String[] hour = time_local.split(":");
/* 170 */                 AttackReportCache.put2dayAttackMap(day[0]);
/* 171 */                 AttackReportCache.put2hourAttackMap(hour[0]);
/*     */                 
/* 173 */                 Date attacktime = dateFormat.parse(time_local);
/* 174 */                 if (AttackReportCache.startTime == null || AttackReportCache.startTime.after(attacktime)) {
/* 175 */                   AttackReportCache.startTime = attacktime;
/* 176 */                   AttackReportCache.firstAttack = entity;
/*     */                 } 
/* 178 */                 if (AttackReportCache.endTime == null || AttackReportCache.endTime.before(attacktime)) {
/* 179 */                   AttackReportCache.endTime = attacktime;
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 190 */       if (log.getUa() != null) {
/* 191 */         String ua = log.getUa();
/* 192 */         for (String feature : UAUtils.uaList) {
/* 193 */           if (ua.contains(feature)) {
/*     */             
/* 195 */             AttackEntity entity = new AttackEntity(host, "非法UA", remote_addr, request_uri, time_local, status, 1);
/* 196 */             AttackReportCache.put2AttMap(MD5Builder.getMD5String(new String(host + remote_addr + request_uri + time_local + status)), entity);
/* 197 */             AttackReportCache.attackCount.incrementAndGet();
/* 198 */             AttackReportCache.put2OverViewMap("非法UA");
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 205 */     } catch (Exception ex) {
/* 206 */       ex.printStackTrace();
/*     */       return;
/*     */     } 
/* 209 */     long then = System.currentTimeMillis();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\AttackAnalyzeSingle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */