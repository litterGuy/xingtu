package com.qihoo.wzws.rzb.util.security;

public interface SignatureManager {
  public static final String UTF8_CHARSET = "utf-8";
  
  String sign(String paramString1, String paramString2);
  
  String encrypt(byte[] paramArrayOfbyte, String paramString) throws Exception;
  
  byte[] decrypt(byte[] paramArrayOfbyte) throws Exception;
  
  boolean verify(String paramString1, String paramString2, String paramString3) throws Exception;
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\security\SignatureManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */