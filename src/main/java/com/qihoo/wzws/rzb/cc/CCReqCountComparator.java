/*    */ package com.qihoo.wzws.rzb.cc;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class CCReqCountComparator
/*    */   implements Comparator<CC> {
/*    */   public int compare(CC o1, CC o2) {
/*  9 */     return o2.getCount() - o1.getCount();
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 13 */     CC[] list = { new CC("A", 1), new CC("A", 1), new CC("A", 2), new CC("A", 5), new CC("A", 3), new CC("A", 4) };
/* 14 */     Arrays.sort(list, new CCReqCountComparator());
/* 15 */     for (CC c : list)
/* 16 */       System.out.println(c.getRequestKey() + " " + c.getCount()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\cc\CCReqCountComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */