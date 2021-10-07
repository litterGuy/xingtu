/*    */ package com.qihoo.wzws.rzb.routine.po;
/*    */ 
/*    */ 
/*    */ public class CommonStatBean
/*    */ {
/*    */   private String key;
/*    */   private int count;
/*    */   private String countStr;
/*    */   private String rate;
/* 10 */   private int flag = 0;
/*    */   
/*    */   public CommonStatBean() {}
/*    */   
/*    */   public CommonStatBean(String key, int count) {
/* 15 */     this.key = key;
/* 16 */     this.count = count;
/*    */   }
/*    */   
/*    */   public CommonStatBean(String key, int count, String rate) {
/* 20 */     this.key = key;
/* 21 */     this.count = count;
/* 22 */     this.rate = rate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 29 */     return this.key;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 35 */     return this.count;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setKey(String key) {
/* 41 */     this.key = key;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCount(int count) {
/* 47 */     this.count = count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRate() {
/* 54 */     return this.rate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRate(String rate) {
/* 61 */     this.rate = rate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFlag() {
/* 68 */     return this.flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFlag(int flag) {
/* 75 */     this.flag = flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCountStr() {
/* 82 */     return this.countStr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCountStr(String countStr) {
/* 89 */     this.countStr = countStr;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\routine\po\CommonStatBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */