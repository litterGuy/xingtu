/*   */ package com.qihoo.wzws.rzb.secure.po;
/*   */ 
/*   */ import java.util.Comparator;
/*   */ 
/*   */ public class CCEntityComparator
/*   */   implements Comparator<CCEntity> {
/*   */   public int compare(CCEntity o1, CCEntity o2) {
/* 8 */     return o2.getCount() - o1.getCount();
/*   */   }
/*   */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\CCEntityComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */