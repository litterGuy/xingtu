/*   */ package com.qihoo.wzws.rzb.routine.po;
/*   */ 
/*   */ import java.util.Comparator;
/*   */ 
/*   */ public class CommonStatBeanComparator
/*   */   implements Comparator<CommonStatBean>
/*   */ {
/*   */   public int compare(CommonStatBean o1, CommonStatBean o2) {
/* 9 */     return o2.getCount() - o1.getCount();
/*   */   }
/*   */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\routine\po\CommonStatBeanComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */