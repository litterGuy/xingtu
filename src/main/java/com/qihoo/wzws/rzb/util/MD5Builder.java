/*     */ package com.qihoo.wzws.rzb.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MD5Builder
/*     */ {
/*     */   private static String encryptFile(File file, String enc) throws Exception {
/*  15 */     FileInputStream fis = null;
/*  16 */     MessageDigest md = null;
/*  17 */     String strDes = null;
/*     */     try {
/*  19 */       fis = new FileInputStream(file);
/*  20 */       byte[] buffer = new byte[2048];
/*  21 */       int length = -1;
/*  22 */       while ((length = fis.read(buffer)) != -1) {
/*  23 */         md.update(buffer, 0, length);
/*     */       }
/*  25 */       byte[] bt = md.digest();
/*     */       
/*  27 */       md = MessageDigest.getInstance(enc);
/*  28 */       md.update(bt);
/*  29 */       strDes = bytes2Hex(md.digest());
/*  30 */     } catch (Exception e) {
/*  31 */       throw e;
/*     */     } 
/*  33 */     return strDes;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String encryptString(String strSrc, String enc) throws NoSuchAlgorithmException {
/*  38 */     MessageDigest md = null;
/*  39 */     String strDes = null;
/*     */     
/*  41 */     byte[] bt = strSrc.getBytes();
/*     */     try {
/*  43 */       md = MessageDigest.getInstance(enc);
/*  44 */       md.update(bt);
/*  45 */       strDes = bytes2Hex(md.digest());
/*  46 */     } catch (NoSuchAlgorithmException e) {
/*  47 */       throw e;
/*     */     } 
/*  49 */     return strDes;
/*     */   }
/*     */   
/*     */   private static String bytes2Hex(byte[] bts) {
/*  53 */     String des = "";
/*  54 */     String tmp = null;
/*  55 */     for (int i = 0; i < bts.length; i++) {
/*  56 */       tmp = Integer.toHexString(bts[i] & 0xFF);
/*  57 */       if (tmp.length() == 1) {
/*  58 */         des = des + "0";
/*     */       }
/*  60 */       des = des + tmp;
/*     */     } 
/*  62 */     return des;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getMD5File(File file) {
/*     */     try {
/*  68 */       return encryptFile(file, "MD5");
/*  69 */     } catch (Exception e) {
/*  70 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getMD5String(String str) {
/*     */     try {
/*  76 */       return encryptString(str, "MD5");
/*  77 */     } catch (NoSuchAlgorithmException e) {
/*  78 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSHA1File(File file) {
/*     */     try {
/*  85 */       return encryptFile(file, "SHA-1");
/*  86 */     } catch (Exception e) {
/*  87 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getSHA1String(String str) {
/*     */     try {
/*  93 */       return encryptString(str, "SHA-1");
/*  94 */     } catch (NoSuchAlgorithmException e) {
/*  95 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String encrypt(byte[] obj) {
/* 100 */     String s = null;
/*     */     try {
/* 102 */       MessageDigest md5 = MessageDigest.getInstance("MD5");
/* 103 */       md5.update(obj);
/* 104 */       s = bytes2Hex(md5.digest());
/*     */     }
/* 106 */     catch (NoSuchAlgorithmException e) {
/* 107 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 110 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\MD5Builder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */