/*    */ package com.qihoo.wzws.rzb.util.security;
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
/*    */ 
/*    */ public class SignatureFactory
/*    */ {
/*    */   public static SignatureManager getSignature(String securityId) {
/* 17 */     if ("0001".equals(securityId)) {
/* 18 */       return new RSASignatureManager();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 23 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\security\SignatureFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */