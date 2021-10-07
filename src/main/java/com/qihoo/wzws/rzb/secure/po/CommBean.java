/*     */ package com.qihoo.wzws.rzb.secure.po;
/*     */ 
/*     */ public class CommBean
/*     */ {
/*     */   private String name;
/*     */   private int count;
/*     */   private String countDisplay;
/*     */   private String countRate;
/*     */   private int count2;
/*     */   
/*     */   public CommBean(String name, String countDisplay, String countRate) {
/*  12 */     this.name = name;
/*  13 */     this.countDisplay = countDisplay;
/*  14 */     this.countRate = countRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CommBean(String name, String countDisplay) {
/*  21 */     this.name = name;
/*  22 */     this.countDisplay = countDisplay;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommBean(String name, int count, int count2) {
/*  27 */     this.name = name;
/*  28 */     this.count = count;
/*  29 */     this.count2 = count2;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommBean(String name, int count) {
/*  34 */     this.name = name;
/*  35 */     this.count = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  42 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  48 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  54 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCount(int count) {
/*  60 */     this.count = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCountDisplay() {
/*  67 */     return this.countDisplay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCountDisplay(String countDisplay) {
/*  74 */     this.countDisplay = countDisplay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount2() {
/*  81 */     return this.count2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCount2(int count2) {
/*  88 */     this.count2 = count2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCountRate() {
/*  95 */     return this.countRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCountRate(String countRate) {
/* 102 */     this.countRate = countRate;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\CommBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */