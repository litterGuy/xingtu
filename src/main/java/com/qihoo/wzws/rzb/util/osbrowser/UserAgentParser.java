/*     */ package com.qihoo.wzws.rzb.util.osbrowser;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.util.osbrowser.pc.PCUserAgentParser;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ public class UserAgentParser
/*     */ {
/*     */   public UserAgent parse(String userAgentString) {
/*  10 */     UserAgent userAgent = new UserAgent();
/*  11 */     userAgent.setPlatform(platform(userAgentString));
/*  12 */     browser(userAgentString, userAgent);
/*     */     
/*  14 */     return userAgent;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String platform(String userAgentString) {
/*  19 */     if (matches(Platform.AndroidPattern, userAgentString))
/*  20 */       return "Android"; 
/*  21 */     if (matches(Platform.iPadPattern, userAgentString))
/*  22 */       return "iOS"; 
/*  23 */     if (matches(Platform.iPodPattern, userAgentString))
/*  24 */       return "iOS"; 
/*  25 */     if (matches(Platform.iPhonePattern, userAgentString))
/*  26 */       return "iOS"; 
/*  27 */     if (matches(Platform.WindowsPhonePattern, userAgentString))
/*  28 */       return "WindowsPhone"; 
/*  29 */     if (matches(Platform.SymbianPattern, userAgentString))
/*  30 */       return "Symbian"; 
/*  31 */     if (matches(Platform.JavaPattern, userAgentString))
/*  32 */       return "Java"; 
/*  33 */     if (matches(Platform.BlackberryPattern, userAgentString)) {
/*  34 */       return "Blackberry";
/*     */     }
/*  36 */     return "Unknown";
/*     */   }
/*     */   
/*     */   public void browser(String userAgentString, UserAgent userAgent) {
/*  40 */     if (matches(Browser.MQQBrowserPattern, userAgentString)) {
/*  41 */       userAgent.setBrowser("MQQBrowser");
/*  42 */     } else if (matches(Browser.UCWEBPattern, userAgentString)) {
/*  43 */       userAgent.setBrowser("UCBrowser");
/*  44 */     } else if (matches(Browser.QIHOOPattern, userAgentString)) {
/*  45 */       userAgent.setBrowser("360browser");
/*  46 */     } else if (matches(Browser.baiduPattern, userAgentString)) {
/*  47 */       userAgent.setBrowser("baidubrowser");
/*  48 */     } else if (matches(Browser.OperaPattern, userAgentString) || matches(Browser.OperaChinaPattern, userAgentString)) {
/*  49 */       userAgent.setBrowser("Opera");
/*  50 */     } else if (matches(Browser.MicroMessengerPattern, userAgentString)) {
/*  51 */       userAgent.setBrowser("MicroMessenger");
/*  52 */     } else if (matches(Browser.WeiboBrowserPattern, userAgentString)) {
/*  53 */       userAgent.setBrowser("Weibo");
/*  54 */     } else if (matches(Browser.KJAVAPattern, userAgentString)) {
/*  55 */       userAgent.setBrowser("KJAVABrowser");
/*  56 */     } else if (matches(Browser.IEMobilePattern, userAgentString)) {
/*  57 */       userAgent.setBrowser("IEMobile");
/*     */     
/*     */     }
/*  60 */     else if ("Android".equals(userAgent.getPlatform())) {
/*     */       
/*  62 */       userAgent.setBrowser("AndriodBrowser");
/*  63 */     } else if ("iOS".equals(userAgent.getPlatform())) {
/*  64 */       userAgent.setBrowser("MobileSafari");
/*  65 */     } else if ("Symbian".equals(userAgent.getPlatform())) {
/*  66 */       userAgent.setBrowser("SymbianBrowser");
/*  67 */     } else if ("WindowsPhone".equals(userAgent.getPlatform())) {
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matches(Pattern pattern, String userAgentStr) {
/*  76 */     return pattern.matcher(userAgentStr).find();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMobileAccess(String userAgentStr) {
/*  86 */     if ("Unknown".equals(platform(userAgentStr))) {
/*  87 */       return false;
/*     */     }
/*  89 */     return true;
/*     */   }
/*     */   
/*  92 */   public static PCUserAgentParser pcUserAgentParser = new PCUserAgentParser();
/*     */   public static UserAgent getUA(String userAgentStr) {
/*  94 */     UserAgent ua = null;
/*     */     try {
/*  96 */       if (isMobileAccess(userAgentStr)) {
/*  97 */         ua = new UserAgent();
/*  98 */         ua.setPlatform("移动端-操作系统");
/*  99 */         ua.setBrowser("移动端-浏览器");
/*     */       } else {
/* 101 */         ua = pcUserAgentParser.parse(userAgentStr);
/*     */       } 
/* 103 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */     
/* 107 */     return ua;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\osbrowser\UserAgentParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */