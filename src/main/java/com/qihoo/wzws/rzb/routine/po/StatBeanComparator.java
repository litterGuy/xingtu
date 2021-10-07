/*   */ package com.qihoo.wzws.rzb.routine.po;
/*   */ 
/*   */ import java.util.Comparator;
/*   */ 
/*   */ public class StatBeanComparator
/*   */   implements Comparator<StatBean>
/*   */ {
/*   */   public int compare(StatBean o1, StatBean o2) {
/* 9 */     return o2.getVisit() - o1.getVisit();
/*   */   }
/*   */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\routine\po\StatBeanComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */