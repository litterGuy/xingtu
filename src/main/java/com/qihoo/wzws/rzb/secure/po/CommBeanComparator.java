/*    */ package com.qihoo.wzws.rzb.secure.po;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class CommBeanComparator
/*    */   implements Comparator<CommBean> {
/*    */   public int compare(CommBean o1, CommBean o2) {
/*  8 */     long comp = (o2.getCount() - o1.getCount());
/*    */     
/* 10 */     if (comp > 0L)
/* 11 */       return 1; 
/* 12 */     if (comp < 0L) {
/* 13 */       return -1;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 26 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\CommBeanComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */