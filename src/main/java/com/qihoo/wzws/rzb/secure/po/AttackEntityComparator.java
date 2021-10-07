/*   */ package com.qihoo.wzws.rzb.secure.po;
/*   */ 
/*   */ import java.util.Comparator;
/*   */ 
/*   */ public class AttackEntityComparator
/*   */   implements Comparator<AttackEntity>
/*   */ {
/*   */   public int compare(AttackEntity o1, AttackEntity o2) {
/* 9 */     return o2.getCount() - o1.getCount();
/*   */   }
/*   */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\po\AttackEntityComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */