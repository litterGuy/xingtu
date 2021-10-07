/*     */ package com.qihoo.wzws.rzb.util.security;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import javax.crypto.Cipher;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RSASignatureManager
/*     */   implements SignatureManager
/*     */ {
/*     */   private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
/*     */   private static final String KEY_ALGORITHM = "RSA";
/*     */   public static final String C_PRI = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=";
/*     */   private static final int MAX_ENCRYPT_BLOCK = 117;
/*     */   private static final int MAX_DECRYPT_BLOCK = 128;
/*     */   
/*     */   public String sign(String content, String privateKey) {
/*     */     try {
/*  44 */       PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
/*     */       
/*  46 */       KeyFactory keyf = KeyFactory.getInstance("RSA");
/*  47 */       PrivateKey priKey = keyf.generatePrivate(priPKCS8);
/*     */       
/*  49 */       Signature signature = Signature.getInstance("SHA1WithRSA");
/*     */       
/*  51 */       signature.initSign(priKey);
/*  52 */       signature.update(content.getBytes("utf-8"));
/*     */       
/*  54 */       byte[] signed = signature.sign();
/*     */       
/*  56 */       return Base64.encodeBase64URLSafeString(signed);
/*  57 */     } catch (Exception e) {
/*  58 */       e.printStackTrace();
/*     */ 
/*     */       
/*  61 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String encrypt(byte[] data, String key) throws Exception {
/*  77 */     byte[] keyBytes = Base64.decodeBase64(key);
/*     */ 
/*     */     
/*  80 */     X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
/*     */     
/*  82 */     KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/*     */     
/*  84 */     Key publicKey = keyFactory.generatePublic(x509KeySpec);
/*     */ 
/*     */     
/*  87 */     Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
/*     */     
/*  89 */     cipher.init(1, publicKey);
/*     */     
/*  91 */     int inputLen = data.length;
/*  92 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*  93 */     int offSet = 0;
/*     */     
/*  95 */     int i = 0;
/*     */     
/*  97 */     while (inputLen - offSet > 0) {
/*  98 */       byte[] cache; if (inputLen - offSet > 117) {
/*  99 */         cache = cipher.doFinal(data, offSet, 117);
/*     */       } else {
/* 101 */         cache = cipher.doFinal(data, offSet, inputLen - offSet);
/*     */       } 
/* 103 */       out.write(cache, 0, cache.length);
/* 104 */       i++;
/* 105 */       offSet = i * 117;
/*     */     } 
/* 107 */     byte[] encryptedData = out.toByteArray();
/* 108 */     out.close();
/*     */     
/* 110 */     return Base64.encodeBase64URLSafeString(encryptedData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] encryptedData) throws Exception {
/* 126 */     byte[] keyBytes = Base64.decodeBase64("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");
/*     */ 
/*     */     
/* 129 */     PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
/*     */     
/* 131 */     KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/*     */     
/* 133 */     Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
/*     */ 
/*     */     
/* 136 */     Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
/*     */     
/* 138 */     cipher.init(2, privateKey);
/*     */     
/* 140 */     int inputLen = encryptedData.length;
/* 141 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 142 */     int offSet = 0;
/*     */     
/* 144 */     int i = 0;
/*     */     
/* 146 */     while (inputLen - offSet > 0) {
/* 147 */       byte[] cache; if (inputLen - offSet > 128) {
/* 148 */         cache = cipher.doFinal(encryptedData, offSet, 128);
/*     */       } else {
/*     */         
/* 151 */         cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
/*     */       } 
/*     */       
/* 154 */       out.write(cache, 0, cache.length);
/* 155 */       i++;
/* 156 */       offSet = i * 128;
/*     */     } 
/* 158 */     byte[] decryptedData = out.toByteArray();
/* 159 */     out.close();
/* 160 */     return decryptedData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verify(String content, String publicKey, String sign) throws Exception {
/* 182 */     byte[] keyBytes = Base64.decodeBase64(publicKey);
/*     */ 
/*     */     
/* 185 */     X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
/*     */ 
/*     */     
/* 188 */     KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/*     */ 
/*     */     
/* 191 */     PublicKey pubKey = keyFactory.generatePublic(keySpec);
/*     */     
/* 193 */     Signature signature = Signature.getInstance("SHA1WithRSA");
/*     */     
/* 195 */     signature.initVerify(pubKey);
/*     */     
/* 197 */     signature.update(content.getBytes("utf-8"));
/*     */ 
/*     */     
/* 200 */     return signature.verify(Base64.decodeBase64(sign));
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws UnsupportedEncodingException, Exception {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\security\RSASignatureManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */