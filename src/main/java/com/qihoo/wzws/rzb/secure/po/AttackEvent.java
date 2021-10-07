/*     */ package com.qihoo.wzws.rzb.secure.po;
/*     */ 
/*     */ import java.util.Date;
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
/*     */ public class AttackEvent
/*     */ {
/*     */   private String sumIP;
/*     */   private String sumAtt;
/*     */   private Date firstStartTime;
/*     */   private String firstStartTimeStr;
/*     */   private String firstIp;
/*     */   private String firstRegion;
/*     */   private String firstAttackType;
/*     */   private String firstUri;
/*     */   private int firstCount;
/*     */   private String firstCountStr;
/*     */   private String hotIP;
/*     */   private String hostRegion;
/*     */   private String hotDayHour;
/*     */   private int hotDayhourCount;
/*     */   private String hotDayhourCountStr;
/*     */   
/*     */   public Date getFirstStartTime() {
/*  36 */     return this.firstStartTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirstIp() {
/*  43 */     return this.firstIp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirstRegion() {
/*  50 */     return this.firstRegion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirstAttackType() {
/*  57 */     return this.firstAttackType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirstUri() {
/*  64 */     return this.firstUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstCount() {
/*  71 */     return this.firstCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHotIP() {
/*  78 */     return this.hotIP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHotDayHour() {
/*  85 */     return this.hotDayHour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstStartTime(Date firstStartTime) {
/*  92 */     this.firstStartTime = firstStartTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstIp(String firstIp) {
/*  99 */     this.firstIp = firstIp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstRegion(String firstRegion) {
/* 106 */     this.firstRegion = firstRegion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstAttackType(String firstAttackType) {
/* 113 */     this.firstAttackType = firstAttackType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstUri(String firstUri) {
/* 120 */     this.firstUri = firstUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstCount(int firstCount) {
/* 127 */     this.firstCount = firstCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHotIP(String hotIP) {
/* 134 */     this.hotIP = hotIP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHotDayHour(String hotDayHour) {
/* 141 */     this.hotDayHour = hotDayHour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHostRegion() {
/* 148 */     return this.hostRegion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHostRegion(String hostRegion) {
/* 155 */     this.hostRegion = hostRegion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHotDayhourCount() {
/* 162 */     return this.hotDayhourCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHotDayhourCount(int hotDayhourCount) {
/* 169 */     this.hotDayhourCount = hotDayhourCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirstCountStr() {
/* 176 */     return this.firstCountStr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHotDayhourCountStr() {
/* 183 */     return this.hotDayhourCountStr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstCountStr(String firstCountStr) {
/* 190 */     this.firstCountStr = firstCountStr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHotDayhourCountStr(String hotDayhourCountStr) {
/* 197 */     this.hotDayhourCountStr = hotDayhourCountStr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSumIP() {
/* 204 */     return this.sumIP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSumAtt() {
/* 211 */     return this.sumAtt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSumIP(String sumIP) {
/* 218 */     this.sumIP = sumIP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSumAtt(String sumAtt) {
/* 225 */     this.sumAtt = sumAtt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirstStartTimeStr() {
/* 232 */     return this.firstStartTimeStr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstStartTimeStr(String firstStartTimeStr) {
/* 239 */     this.firstStartTimeStr = firstStartTimeStr;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\AttackEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */