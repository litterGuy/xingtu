/*     */ package com.qihoo.wzws.rzb.util;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.secure.AnalyzeSingle;
/*     */ import com.qihoo.wzws.rzb.secure.ReportOutput;
/*     */ import com.qihoo.wzws.rzb.util.security.SignatureFactory;
/*     */ import com.qihoo.wzws.rzb.util.security.SignatureManager;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnServerHandler
/*     */ {
/*     */   public static final String S_PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB";
/*     */   private static final String S_URL = "http://101.226.4.56/rzb-server/upd";
/*     */   
/*     */   public static void requestUpdate(String mac, String clientVer, String ruleVer) {
/*  20 */     String data = new String("type=1,uid=" + mac + ",clientver=" + clientVer + ",rulever=" + ruleVer);
/*     */ 
/*     */     
/*  23 */     SignatureManager signatureManager = SignatureFactory.getSignature("0001");
/*  24 */     String sign = signatureManager.sign(data, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");
/*     */ 
/*     */     
/*  27 */     String base64Data = "";
/*     */     try {
/*  29 */       base64Data = signatureManager.encrypt(data.getBytes("utf-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB");
/*  30 */     } catch (UnsupportedEncodingException e) {
/*  31 */       e.printStackTrace();
/*  32 */     } catch (Exception e) {
/*  33 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  38 */     HttpChannel.getInstance().update("http://101.226.4.56/rzb-server/upd", sign, base64Data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateUseInfo(String mac, String clientVer, String ruleVer, long logLines, long attackCount) {
/*  46 */     String data = new String("type=2,uid=" + mac + ",clientver=" + clientVer + ",rulever=" + ruleVer + ",logLines=" + logLines + ",attackCount=" + attackCount);
/*     */ 
/*     */     
/*  49 */     SignatureManager signatureManager = SignatureFactory.getSignature("0001");
/*  50 */     String sign = signatureManager.sign(data, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");
/*     */ 
/*     */     
/*  53 */     String base64Data = "";
/*     */     try {
/*  55 */       base64Data = signatureManager.encrypt(data.getBytes("utf-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB");
/*  56 */     } catch (UnsupportedEncodingException e) {
/*     */       
/*  58 */       e.printStackTrace();
/*  59 */     } catch (Exception e) {
/*     */       
/*  61 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  66 */     HttpChannel.getInstance().updateUseInfo("http://101.226.4.56/rzb-server/upd", sign, base64Data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateAttackInfo(String mac, String clientVer, String ruleVer, String attacks) {
/*     */     String data;
/*     */     try {
/*  74 */       data = new String("type=3,uid=" + mac + ",clientver=" + clientVer + ",rulever=" + ruleVer + ",data=" + URLEncoder.encode(attacks, "utf-8"));
/*  75 */     } catch (UnsupportedEncodingException e1) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  80 */     SignatureManager signatureManager = SignatureFactory.getSignature("0001");
/*  81 */     String sign = signatureManager.sign(data, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");
/*     */ 
/*     */     
/*  84 */     String base64Data = "";
/*     */     try {
/*  86 */       base64Data = signatureManager.encrypt(data.getBytes("utf-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB");
/*  87 */     } catch (UnsupportedEncodingException e) {
/*  88 */       e.printStackTrace();
/*  89 */     } catch (Exception e) {
/*  90 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     HttpChannel.getInstance().updateUseInfo("http://101.226.4.56/rzb-server/upd", sign, base64Data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendMail(String mac, String clientVer, String ruleVer, String mail, String log_file, long records, long attacksCounts, long costTime, String content) {
/* 102 */     String data = new String("type=4,uid=" + mac + ",clientver=" + clientVer + ",rulever=" + ruleVer + ",mail=" + mail + ",log_file=" + log_file + ",records=" + records + ",attacksCounts=" + attacksCounts + ",costTime=" + costTime + ",content=" + content);
/*     */ 
/*     */ 
/*     */     
/* 106 */     SignatureManager signatureManager = SignatureFactory.getSignature("0001");
/* 107 */     String sign = signatureManager.sign(data, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");
/*     */ 
/*     */     
/* 110 */     String base64Data = "";
/*     */     try {
/* 112 */       base64Data = signatureManager.encrypt(data.getBytes("utf-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB");
/* 113 */     } catch (UnsupportedEncodingException e) {
/* 114 */       e.printStackTrace();
/* 115 */     } catch (Exception e) {
/* 116 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 119 */     HttpChannel.getInstance().updateUseInfo("http://101.226.4.56/rzb-server/upd", sign, base64Data);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 123 */     AnalyzeSingle.basePath = "C:\\Users\\wangpeng3-s\\Desktop\\rzb\\full-v2.0\\";
/* 124 */     String mac = "F0-92-1C-E2-5D-E8";
/* 125 */     String clientVer = "0.6.2";
/* 126 */     String ruleVer = "20140830";
/* 127 */     long logLines = 1000L;
/* 128 */     long attackCount = 2L;
/*     */ 
/*     */ 
/*     */     
/* 132 */     ReportOutput.generateUid();
/*     */     
/* 134 */     requestUpdate(mac, clientVer, ruleVer);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\ConnServerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */