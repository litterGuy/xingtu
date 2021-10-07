/*    */ package com.qihoo.wzws.rzb.util.osbrowser;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Platform
/*    */ {
/* 11 */   public static final Pattern AndroidPattern = Pattern.compile("android|Linux; *U", 2);
/* 12 */   public static final Pattern iPhonePattern = Pattern.compile("iphone|iOS", 2);
/* 13 */   public static final Pattern iPadPattern = Pattern.compile("ipad", 2);
/* 14 */   public static final Pattern iPodPattern = Pattern.compile("ipod", 2);
/*    */   
/* 16 */   public static final Pattern WindowsPhonePattern = Pattern.compile("windows;?? (ce|phone|mobile|U; wds)( os)?", 2);
/* 17 */   public static final Pattern SymbianPattern = Pattern.compile("nokia|symbian(os)?", 2);
/* 18 */   public static final Pattern JavaPattern = Pattern.compile("java|J2ME|MAUI WAP", 2);
/* 19 */   public static final Pattern BlackberryPattern = Pattern.compile("blackberry", 2);
/*    */   
/*    */   public static final String Android = "Android";
/*    */   
/*    */   public static final String iOS = "iOS";
/*    */   public static final String iPhone = "iPhone";
/*    */   public static final String iPad = "iPad";
/*    */   public static final String iPod = "iPod";
/*    */   public static final String WindowsPhone = "WindowsPhone";
/*    */   public static final String Symbian = "Symbian";
/*    */   public static final String Java = "Java";
/*    */   public static final String Blackberry = "Blackberry";
/*    */   public static final String Unknown = "Unknown";
/* 32 */   public static final List<String> mobilePlatforms = Arrays.asList(new String[] { "Android", "iPhone", "iPad", "iPod", "WindowsPhone", "Symbian", "Java", "Blackberry" });
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\osbrowser\Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */