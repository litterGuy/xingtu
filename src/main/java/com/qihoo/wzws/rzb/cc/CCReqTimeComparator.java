/*    */ package com.qihoo.wzws.rzb.cc;
/*    */ 
/*    */ import com.qihoo.wzws.rzb.util.DateUtil;
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class CCReqTimeComparator
/*    */   implements Comparator<CC>
/*    */ {
/*    */   public int compare(CC o1, CC o2) {
/* 12 */     Date date1 = DateUtil.parseDate(o1.getTime());
/* 13 */     Date date2 = DateUtil.parseDate(o2.getTime());
/*    */     
/* 15 */     return date1.compareTo(date2);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 19 */     CC[] list = { new CC("A", 1), new CC("A", 1), new CC("A", 2), new CC("A", 5), new CC("A", 3), new CC("A", 4) };
/* 20 */     Arrays.sort(list, new CCReqTimeComparator());
/* 21 */     for (CC c : list)
/* 22 */       System.out.println(c.getRequestKey() + " " + c.getCount()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\cc\CCReqTimeComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */