/*     */ package com.qihoo.wzws.rzb.routine;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.routine.po.SearchKeyWordBean;
/*     */ import com.qihoo.wzws.rzb.routine.po.StatBean;
/*     */ import com.qihoo.wzws.rzb.secure.po.LogEntity;
/*     */ import com.qihoo.wzws.rzb.util.DateUtil;
/*     */ import com.qihoo.wzws.rzb.util.MD5Builder;
/*     */ import com.qihoo.wzws.rzb.util.Utils;
/*     */ import com.qihoo.wzws.rzb.util.keyword.KWUtils;
/*     */ import com.qihoo.wzws.rzb.util.osbrowser.UserAgent;
/*     */ import com.qihoo.wzws.rzb.util.osbrowser.UserAgentParser;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoutineAnalyze
/*     */ {
/*     */   public void execute(LogEntity log) {
/*  39 */     if (RoutineReportCache.visits > 0 && RoutineReportCache.visits % 500000 == 0) {
/*  40 */       System.out.println(String.valueOf(DateUtil.formatDateTime()) + ":已处理" + RoutineReportCache.visits + "条访问日志...");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     RoutineReportCache.visits++;
/*  51 */     RoutineReportCache.totalband += log.getContentLength();
/*     */     
/*  53 */     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*  54 */     String[] day = log.getTime().split(" ");
/*     */     
/*     */     try {
/*  57 */       if (!RoutineReportCache.dataFrom.contains(dateFormat.parse(day[0]))) {
/*  58 */         RoutineReportCache.dataFrom.add(dateFormat.parse(day[0]));
/*     */       }
/*  60 */     } catch (ParseException e) {
/*     */       
/*  62 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  65 */     if (RoutineReportCache.ipStaMap.containsKey(log.getIp())) {
/*  66 */       StatBean ipStatBean = RoutineReportCache.ipStaMap.get(log.getIp());
/*  67 */       ipStatBean.setBand(ipStatBean.getBand() + log.getContentLength());
/*  68 */       ipStatBean.setVisit(ipStatBean.getVisit() + 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  76 */       RoutineReportCache.ipStaMap.put(log.getIp(), ipStatBean);
/*     */     } else {
/*     */       
/*  79 */       StatBean ipStatBean = new StatBean();
/*  80 */       ipStatBean.setIp(log.getIp());
/*  81 */       ipStatBean.setBand(log.getContentLength());
/*  82 */       ipStatBean.setVisit(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  90 */       RoutineReportCache.ipStaMap.put(log.getIp(), ipStatBean);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (Utils.isPage(log.getRequestUrl())) {
/*  96 */       if ("200".equals(log.getResponseCode())) {
/*  97 */         RoutineReportCache.PV++;
/*     */       }
/*     */ 
/*     */       
/* 101 */       StatBean statBean = RoutineReportCache.pageVisitMap.get(log.getRequestUrl());
/* 102 */       if (statBean != null) {
/* 103 */         statBean.setVisit(statBean.getVisit() + 1);
/* 104 */         statBean.setBand(statBean.getBand() + log.getContentLength());
/*     */       } else {
/* 106 */         statBean = new StatBean();
/* 107 */         statBean.setUrl(log.getRequestUrl());
/* 108 */         statBean.setVisit(1);
/* 109 */         statBean.setBand(log.getContentLength());
/* 110 */         RoutineReportCache.pageVisitMap.put(log.getRequestUrl(), statBean);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 115 */       StatBean statBean = RoutineReportCache.staticPageVisitMap.get(log.getRequestUrl());
/* 116 */       if (statBean != null) {
/* 117 */         statBean.setVisit(statBean.getVisit() + 1);
/* 118 */         statBean.setBand(statBean.getBand() + log.getContentLength());
/*     */       } else {
/* 120 */         statBean = new StatBean();
/* 121 */         statBean.setUrl(log.getRequestUrl());
/* 122 */         statBean.setVisit(1);
/* 123 */         statBean.setBand(log.getContentLength());
/* 124 */         RoutineReportCache.staticPageVisitMap.put(log.getRequestUrl(), statBean);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     if ("404".equals(log.getResponseCode())) {
/* 131 */       StatBean statBean = RoutineReportCache._404pageVisitMap.get(log.getRequestUrl());
/* 132 */       if (statBean != null) {
/* 133 */         statBean.setVisit(statBean.getVisit() + 1);
/* 134 */         statBean.setBand(statBean.getBand() + log.getContentLength());
/*     */       } else {
/* 136 */         statBean = new StatBean();
/* 137 */         statBean.setUrl(log.getRequestUrl());
/* 138 */         statBean.setVisit(1);
/* 139 */         statBean.setBand(log.getContentLength());
/* 140 */         RoutineReportCache._404pageVisitMap.put(log.getRequestUrl(), statBean);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 145 */     if (!"-".equals(log.getUa())) {
/* 146 */       String key = MD5Builder.getMD5String(new String(String.valueOf(log.getIp()) + log.getUa()));
/* 147 */       StatBean statBean = RoutineReportCache.UVMap.get(key);
/* 148 */       if (statBean == null) {
/*     */ 
/*     */ 
/*     */         
/* 152 */         statBean = new StatBean();
/*     */         
/* 154 */         statBean.setVisit(1);
/*     */         
/* 156 */         RoutineReportCache.UVMap.put(key, statBean);
/*     */ 
/*     */ 
/*     */         
/* 160 */         UserAgent ua = UserAgentParser.getUA(log.getUa());
/* 161 */         if (ua != null && ua.getPlatform() != null && ua.getBrowser() != null) {
/*     */           
/* 163 */           RoutineReportCache.osCount++;
/*     */ 
/*     */           
/* 166 */           String osKey = ua.getPlatform();
/* 167 */           if (ua.getOsVersion() != null) {
/* 168 */             osKey = String.valueOf(osKey) + ua.getOsVersion();
/*     */           }
/* 170 */           Integer osCount = RoutineReportCache.osMap.get(osKey);
/* 171 */           if (osCount != null) {
/* 172 */             RoutineReportCache.osMap.put(osKey, Integer.valueOf(osCount.intValue() + 1));
/*     */           } else {
/* 174 */             RoutineReportCache.osMap.put(osKey, Integer.valueOf(1));
/*     */           } 
/*     */ 
/*     */           
/* 178 */           Integer browerCount = RoutineReportCache.browserMap.get(ua.getBrowser());
/* 179 */           if (browerCount != null) {
/* 180 */             RoutineReportCache.browserMap.put(ua.getBrowser(), Integer.valueOf(browerCount.intValue() + 1));
/*     */           } else {
/* 182 */             RoutineReportCache.browserMap.put(ua.getBrowser(), Integer.valueOf(1));
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 187 */         if (!log.getIp().equals("-")) {
/* 188 */           String countryKey = Utils.getIPRegionLocal(log.getIp());
/* 189 */           Integer countryCount = RoutineReportCache.countryMap.get(countryKey);
/* 190 */           if (countryCount != null) {
/* 191 */             RoutineReportCache.countryMap.put(countryKey, Integer.valueOf(countryCount.intValue() + 1));
/*     */           } else {
/* 193 */             RoutineReportCache.countryMap.put(countryKey, Integer.valueOf(1));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 199 */       String soKey = "";
/* 200 */       if (log.getUa().toLowerCase().contains("googlebot")) {
/* 201 */         soKey = "googlebot";
/* 202 */       } else if (log.getUa().toLowerCase().contains("360spider")) {
/* 203 */         soKey = "qh360bot";
/* 204 */       } else if (log.getUa().toLowerCase().contains("baiduspider")) {
/* 205 */         soKey = "baidubot";
/* 206 */       } else if (log.getUa().toLowerCase().contains("sogou")) {
/* 207 */         soKey = "sogou";
/* 208 */       } else if (log.getUa().toLowerCase().contains("bing")) {
/* 209 */         soKey = "bingbot";
/* 210 */       } else if (log.getUa().toLowerCase().contains("slurp")) {
/* 211 */         soKey = "yahoobot";
/* 212 */       } else if (log.getUa().toLowerCase().contains("sosospider")) {
/* 213 */         soKey = "sosobot";
/* 214 */       } else if (log.getUa().toLowerCase().contains("bingbot")) {
/* 215 */         soKey = "bingbot";
/*     */       } 
/*     */       
/* 218 */       if (soKey.length() > 0) {
/* 219 */         if (RoutineReportCache.soVisitMap.containsKey(soKey)) {
/* 220 */           StatBean soStatBean = RoutineReportCache.soVisitMap.get(soKey);
/* 221 */           soStatBean.setVisit(soStatBean.getVisit() + 1);
/* 222 */           soStatBean.setBand(soStatBean.getBand() + log.getContentLength());
/*     */         } else {
/* 224 */           StatBean soStatBean = new StatBean();
/* 225 */           soStatBean.setVisit(1);
/* 226 */           soStatBean.setUrl((String)Utils.sosuoMap.get(soKey));
/* 227 */           soStatBean.setBand(log.getContentLength());
/* 228 */           RoutineReportCache.soVisitMap.put(soKey, soStatBean);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 234 */     if (!"-".equals(log.getReferer())) {
/* 235 */       String refererKey = "";
/*     */       
/* 237 */       if (log.getReferer().startsWith("http://") || log.getReferer().startsWith("https://")) {
/* 238 */         int index = log.getReferer().indexOf("?");
/* 239 */         if (index > 0 && index <= log.getReferer().length() - 1) {
/* 240 */           refererKey = log.getReferer().substring(0, index);
/*     */         } else {
/* 242 */           refererKey = log.getReferer();
/*     */         } 
/*     */ 
/*     */         
/* 246 */         Integer refererCount = RoutineReportCache.refererMap.get(refererKey);
/* 247 */         if (refererCount != null) {
/* 248 */           RoutineReportCache.refererMap.put(refererKey, Integer.valueOf(refererCount.intValue() + 1));
/*     */         } else {
/* 250 */           RoutineReportCache.refererMap.put(refererKey, Integer.valueOf(1));
/*     */         } 
/*     */         
/* 253 */         SearchKeyWordBean kwBean = KWUtils.getKWAndSE(log.getReferer());
/* 254 */         if (kwBean != null) {
/* 255 */           SearchKeyWordBean bean = RoutineReportCache.keyWordsMap.get(String.valueOf(kwBean.getSe()) + "@" + kwBean.getKw());
/* 256 */           if (bean != null) {
/* 257 */             bean.setCount(bean.getCount() + 1);
/*     */           } else {
/* 259 */             bean = new SearchKeyWordBean();
/* 260 */             bean.setSe(kwBean.getSe());
/* 261 */             bean.setKw(kwBean.getKw());
/* 262 */             bean.setCount(1);
/* 263 */             RoutineReportCache.keyWordsMap.put(String.valueOf(kwBean.getSe()) + "@" + kwBean.getKw(), bean);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
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
/* 300 */     if (log.getResponseCode().startsWith("2")) {
/* 301 */       RoutineReportCache.stat2XXcount++;
/* 302 */     } else if (log.getResponseCode().startsWith("3")) {
/* 303 */       RoutineReportCache.stat3XXcount++;
/* 304 */     } else if (log.getResponseCode().startsWith("4")) {
/* 305 */       RoutineReportCache.stat4XXcount++;
/* 306 */     } else if (log.getResponseCode().startsWith("5")) {
/* 307 */       RoutineReportCache.stat5XXcount++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\routine\RoutineAnalyze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */