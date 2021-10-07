/*     */ package com.qihoo.wzws.rzb.routine.po;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatBean
/*     */ {
/*     */   private String ip;
/*     */   private String country;
/*     */   private int visit;
/*     */   private String visitStr;
/*     */   private String visitRate;
/*     */   private long band;
/*  13 */   private String bandStr = "-";
/*  14 */   private String bandRate = "-";
/*     */   
/*     */   private int stat200count;
/*     */   
/*     */   private int stat404count;
/*  19 */   private int visitflag = 0;
/*  20 */   private int bandflag = 0;
/*     */ 
/*     */   
/*     */   private String url;
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVisit() {
/*  28 */     return this.visit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getBand() {
/*  34 */     return this.band;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStat404count() {
/*  40 */     return this.stat404count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisit(int visit) {
/*  46 */     this.visit = visit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBand(long band) {
/*  52 */     this.band = band;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStat404count(int stat404count) {
/*  58 */     this.stat404count = stat404count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStat200count() {
/*  64 */     return this.stat200count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStat200count(int stat200count) {
/*  70 */     this.stat200count = stat200count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIp() {
/*  76 */     return this.ip;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIp(String ip) {
/*  82 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCountry() {
/*  88 */     return this.country;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVisitRate() {
/*  94 */     return this.visitRate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBandRate() {
/* 100 */     return this.bandRate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCountry(String country) {
/* 106 */     this.country = country;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisitRate(String visitRate) {
/* 112 */     this.visitRate = visitRate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBandRate(String bandRate) {
/* 118 */     this.bandRate = bandRate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBandStr() {
/* 124 */     return this.bandStr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBandStr(String bandStr) {
/* 130 */     this.bandStr = bandStr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUrl() {
/* 136 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUrl(String url) {
/* 142 */     this.url = url;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVisitflag() {
/* 148 */     return this.visitflag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBandflag() {
/* 154 */     return this.bandflag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisitflag(int visitflag) {
/* 160 */     this.visitflag = visitflag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBandflag(int bandflag) {
/* 166 */     this.bandflag = bandflag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVisitStr() {
/* 172 */     return this.visitStr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisitStr(String visitStr) {
/* 178 */     this.visitStr = visitStr;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\routine\po\StatBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */