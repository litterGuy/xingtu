/*     */ package com.qihoo.wzws.rzb.secure.po;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttackEntity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6807835469251514642L;
/*     */   private long atdid;
/*     */   private String host;
/*     */   private String rule;
/*     */   private String ip;
/*     */   private String url;
/*     */   private String starttime;
/*     */   private String status;
/*     */   private int count;
/*     */   
/*     */   public AttackEntity(String host, String rule, String ip, String url, String starttime, String status, int count) {
/*  24 */     this.host = host;
/*  25 */     this.rule = rule;
/*  26 */     this.ip = ip;
/*  27 */     this.url = url;
/*  28 */     this.starttime = starttime;
/*  29 */     this.status = status;
/*  30 */     this.count = count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getAtdid() {
/*  36 */     return Long.valueOf(this.atdid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/*  42 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRule() {
/*  48 */     return this.rule;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIp() {
/*  54 */     return this.ip;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUrl() {
/*  60 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStarttime() {
/*  66 */     return this.starttime;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatus() {
/*  72 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAtdid(Long atdid) {
/*  78 */     this.atdid = atdid.longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHost(String host) {
/*  84 */     this.host = host;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRule(String rule) {
/*  90 */     this.rule = rule;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIp(String ip) {
/*  96 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUrl(String url) {
/* 102 */     this.url = url;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStarttime(String starttime) {
/* 108 */     this.starttime = starttime;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(String status) {
/* 114 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 120 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCount(int count) {
/* 126 */     this.count = count;
/*     */   }
/*     */   
/*     */   public String output() {
/* 130 */     return new String(this.host + "\t" + this.rule + "\t" + this.ip + "\t" + this.url + "\t" + this.starttime + "\t" + this.status + "\t" + this.count + "\r\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\AttackEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */