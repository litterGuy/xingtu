/*    */ package com.qihoo.wzws.rzb.secure.po;
/*    */ 
/*    */ import com.qihoo.wzws.rzb.util.DateUtil;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class AttackStatBean
/*    */   implements Comparable<AttackStatBean> {
/*    */   private String date;
/*    */   
/*    */   public AttackStatBean(String date, int attackCount, int ccCount) {
/* 11 */     this.date = date;
/* 12 */     this.attackCount = attackCount;
/* 13 */     this.ccCount = ccCount;
/*    */   }
/*    */ 
/*    */   
/*    */   private int attackCount;
/*    */   
/*    */   private int ccCount;
/*    */ 
/*    */   
/*    */   public int compareTo(AttackStatBean o) {
/* 23 */     Date a = DateUtil.parseDate(this.date);
/* 24 */     Date b = DateUtil.parseDate(o.getDate());
/*    */     
/* 26 */     if (a.before(b))
/* 27 */       return 1; 
/* 28 */     if (a.after(b)) {
/* 29 */       return -1;
/*    */     }
/*    */     
/* 32 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDate() {
/* 39 */     return this.date;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDate(String date) {
/* 47 */     this.date = date;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getAttackCount() {
/* 54 */     return this.attackCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCcCount() {
/* 61 */     return this.ccCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAttackCount(int attackCount) {
/* 68 */     this.attackCount = attackCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCcCount(int ccCount) {
/* 75 */     this.ccCount = ccCount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\AttackStatBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */