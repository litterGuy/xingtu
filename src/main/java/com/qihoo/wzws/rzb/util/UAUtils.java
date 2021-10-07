/*    */ package com.qihoo.wzws.rzb.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class UAUtils
/*    */ {
/*    */   public static final String LABEL = "非法UA";
/*  9 */   public static List<String> uaList = new ArrayList<String>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 15 */     uaList.add("ddos");
/* 16 */     uaList.add("Ddos");
/* 17 */     uaList.add("Referer");
/* 18 */     uaList.add("Python-urllib/2.7");
/* 19 */     uaList.add("_USERAGENT_");
/* 20 */     uaList.add("CustomAgent");
/* 21 */     uaList.add("Mozilla/5.0 ()");
/* 22 */     uaList.add("jakarta commons-httpclient");
/* 23 */     uaList.add("Apache-HttpClient");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\UAUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */