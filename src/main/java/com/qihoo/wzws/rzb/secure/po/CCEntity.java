/*     */ package com.qihoo.wzws.rzb.secure.po;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CCEntity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2893993256380110029L;
/*     */   private long ccdid;
/*     */   private String host;
/*     */   private String ip;
/*     */   private String url;
/*     */   private String starttime;
/*     */   private int count;
/*     */   
/*     */   public CCEntity() {}
/*     */   
/*     */   public CCEntity(String host, String ip, String url, String starttime, int count) {
/*  23 */     this.host = host;
/*  24 */     this.ip = ip;
/*  25 */     this.url = url;
/*  26 */     this.starttime = starttime;
/*  27 */     this.count = count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getCcdid() {
/*  33 */     return Long.valueOf(this.ccdid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/*  39 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIp() {
/*  45 */     return this.ip;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUrl() {
/*  51 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStarttime() {
/*  57 */     return this.starttime;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCcdid(Long ccdid) {
/*  63 */     this.ccdid = ccdid.longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHost(String host) {
/*  69 */     this.host = host;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIp(String ip) {
/*  75 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUrl(String url) {
/*  81 */     this.url = url;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStarttime(String starttime) {
/*  87 */     this.starttime = starttime;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCount(int count) {
/*  93 */     this.count = count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  99 */     return this.count;
/*     */   }
/*     */   
/*     */   public String output() {
/* 103 */     return new String(this.host + "\t" + this.ip + "\t" + this.url + "\t" + this.starttime + "\t" + this.count + "\r\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\CCEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */