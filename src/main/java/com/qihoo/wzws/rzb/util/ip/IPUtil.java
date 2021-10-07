/*    */ package com.qihoo.wzws.rzb.util.ip;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IPUtil
/*    */ {
/*    */   public static IPRegion getIPRegionLocal(String ip) {
/* 13 */     String line = IPDataHandler.findGeography(ip);
/* 14 */     if (line != null) {
/* 15 */       IPRegion region = new IPRegion();
/* 16 */       String[] strArray = line.split("\t");
/* 17 */       if (strArray.length >= 2) {
/* 18 */         region.setIp(ip);
/* 19 */         region.setCountry(strArray[0]);
/* 20 */         region.setProvince(strArray[1]);
/*    */       } 
/* 22 */       if (strArray.length >= 3) {
/* 23 */         region.setCity(strArray[2]);
/*    */       }
/*    */       
/* 26 */       return region;
/*    */     } 
/*    */     
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public static String getRegion(String ip) {
/* 33 */     String rv = "";
/* 34 */     IPRegion ipRegion = getIPRegionLocal(ip);
/* 35 */     if (ipRegion.getCountry().indexOf("中国") > -1) {
/* 36 */       if (ipRegion.getCity() != null && !ipRegion.getProvince().equals(ipRegion.getCity())) {
/* 37 */         rv = ipRegion.getProvince() + ipRegion.getCity();
/*    */       } else {
/* 39 */         rv = ipRegion.getProvince();
/*    */       } 
/*    */     } else {
/* 42 */       rv = ipRegion.getCountry();
/*    */     } 
/*    */     
/* 45 */     return rv;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {}
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\ip\IPUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */