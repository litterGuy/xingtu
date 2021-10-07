/*    */ package com.qihoo.wzws.rzb.secure.po;
/*    */ 
/*    */ public class OverViewEntity
/*    */   implements Comparable
/*    */ {
/*    */   private String name;
/*    */   private long count;
/*    */   
/*    */   public OverViewEntity(String name, long count) {
/* 10 */     this.name = name;
/* 11 */     this.count = count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 18 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long getCount() {
/* 24 */     return this.count;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setName(String name) {
/* 30 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCount(long count) {
/* 36 */     this.count = count;
/*    */   }
/*    */   
/*    */   public int compareTo(Object o) {
/* 40 */     return (int)(((OverViewEntity)o).getCount() - getCount());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\OverViewEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */