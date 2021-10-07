/*     */ package com.qihoo.wzws.rzb.secure.po;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogEntity
/*     */ {
/*     */   private String ip;
/*     */   private String time;
/*     */   private String host;
/*     */   private String requestUrl;
/*     */   private String responseCode;
/*     */   private long contentLength;
/*     */   private String referer;
/*     */   private String ua;
/*     */   
/*     */   public LogEntity(String ip, String time, String host, String requestUrl, String responseCode, long contentLength, String referer, String ua) {
/*  19 */     this.ip = ip;
/*  20 */     this.time = time;
/*  21 */     this.host = host;
/*  22 */     this.requestUrl = requestUrl;
/*  23 */     this.responseCode = responseCode;
/*  24 */     this.contentLength = contentLength;
/*  25 */     this.referer = referer;
/*  26 */     this.ua = ua;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIp() {
/*  33 */     return this.ip;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTime() {
/*  39 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/*  45 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRequestUrl() {
/*  51 */     return this.requestUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResponseCode() {
/*  57 */     return this.responseCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getContentLength() {
/*  63 */     return this.contentLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIp(String ip) {
/*  69 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(String time) {
/*  75 */     this.time = time;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHost(String host) {
/*  81 */     this.host = host;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequestUrl(String requestUrl) {
/*  87 */     this.requestUrl = requestUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResponseCode(String responseCode) {
/*  93 */     this.responseCode = responseCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentLength(long contentLength) {
/*  99 */     this.contentLength = contentLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return "LogEntity [ip=" + this.ip + ", time=" + this.time + ", host=" + this.host + ", requestUrl=" + this.requestUrl + ", responseCode=" + this.responseCode + ", contentLength=" + this.contentLength + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReferer() {
/* 116 */     return this.referer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUa() {
/* 123 */     return this.ua;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReferer(String referer) {
/* 130 */     this.referer = referer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUa(String ua) {
/* 137 */     this.ua = ua;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\LogEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */